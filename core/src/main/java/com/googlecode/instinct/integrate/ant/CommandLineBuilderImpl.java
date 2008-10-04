/*
 * Copyright 2008 Jeremy Mawson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.util.Fj.toFjList;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.CommandLineRunner;
import fj.Effect;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;

public final class CommandLineBuilderImpl implements CommandLineBuilder {
    private CommandlineJava javaCommandLine = new CommandlineJava();

    public CommandlineJava build(final List<Formatter> formatters, final List<Specifications> specifications) {
        checkNotNull(formatters, specifications);
        javaCommandLine.setClassname(CommandLineRunner.class.getName());
        if (formatters.isNotEmpty()) {
            javaCommandLine.createArgument().setValue("--formatters");
            javaCommandLine.createArgument().setValue(asArgumentString(formatters));
        }
        final String contexts = getContextNamesToRun(specifications);
        javaCommandLine.createArgument().setValue(contexts);
        return javaCommandLine;
    }

    public Path createClasspath(final Project project) {
        return javaCommandLine.createClasspath(project);
    }

    private String getContextNamesToRun(final List<Specifications> specifications) {
        final List<List<Character>> contexts = findContextsFromAllAggregators(specifications).map(new F<JavaClassName, List<Character>>() {
            public List<Character> f(final JavaClassName contextClass) {
                return fromString(contextClass.getFullyQualifiedName());
            }
        });
        return asString(join(contexts.intersperse(fromString(RunnableItemBuilder.ITEM_SEPARATOR))));
    }

    private String asArgumentString(final List<Formatter> formatters) {
        final StringBuilder builder = new StringBuilder();
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                if (!formatter.equals(formatters.head())) {
                    builder.append(",");
                }
                builder.append(formatter.getType().name());
                if (formatter.getToDir() != null) {
                    builder.append("{toDir=").append(formatter.getToDir().getAbsolutePath()).append("}");
                }
            }
        });
        return builder.toString();
    }

    private List<JavaClassName> findContextsFromAllAggregators(final List<Specifications> specifications) {
        return join(specifications.map(new F<Specifications, List<JavaClassName>>() {
            public List<JavaClassName> f(final Specifications locator) {
                return toFjList(locator.getContextClasses());
            }
        }));
    }
}
