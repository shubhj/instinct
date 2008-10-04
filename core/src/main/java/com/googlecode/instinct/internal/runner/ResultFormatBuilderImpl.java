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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.report.ResultFormat;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import fj.data.HashMap;
import fj.data.List;
import fj.pre.Equal;
import fj.pre.Hash;
import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ResultFormatBuilderImpl implements ResultFormatBuilder {
    private static final Pattern DECLARATION_NAME_ONLY_PATTERN = Pattern.compile("^(\\p{Alpha}*)(\\{.*\\}$|$)");
    private static final Pattern DECLARATION_NAME_AND_PARAMETERS_PATTERN = Pattern.compile("^\\p{Alpha}*\\{(.*)\\}$");
    private static final List<Formatter> DEFAULT_FORMATTERS = List.list(new Formatter(BRIEF));

    public List<Formatter> build(final String formatters) {
        if (formatters == null) {
            return DEFAULT_FORMATTERS;
        }
        List<Formatter> formats = List.nil();
        if (!"".equals(formatters.trim())) {
            for (final String formatterDeclaration : formatters.split(",")) {
                final String formatterName = parseName(formatterDeclaration);
                final HashMap<String, String> parameters = parseParameters(formatterDeclaration);
                try {
                    final ResultFormat format = ResultFormat.valueOf(formatterName.toUpperCase());
                    final Formatter formatter = new Formatter(format);
                    if (parameters.contains("toDir")) {
                        formatter.setToDir(new File(parameters.get("toDir").some()));
                    }
                    formats = formats.snoc(formatter);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(String.format("Could not parse formatters list \"%s\"", formatters), e);
                }
            }
        }
        return formats;
    }

    private String parseName(final String declaration) {
        final Matcher matcher = DECLARATION_NAME_ONLY_PATTERN.matcher(declaration);
        return matcher.find() ? declaration.substring(matcher.start(1), matcher.end(1)) : declaration;
    }

    private HashMap<String, String> parseParameters(final String declaration) {
        final HashMap<String, String> map = new HashMap<String, String>(Equal.stringEqual, Hash.stringHash);
        final Matcher matcher = DECLARATION_NAME_AND_PARAMETERS_PATTERN.matcher(declaration);
        if (matcher.find()) {
            final String param = declaration.substring(matcher.start(1), matcher.end(1));
            if (param.contains("{") || param.contains("}")) {
                throw new IllegalArgumentException(MessageFormat.format("Formatter delcaration contains a parameter extraneous braces: {0}", param));
            }
            final String[] keyValuePair = param.split("=");
            if (keyValuePair.length != 2) {
                throw new IllegalArgumentException(
                        MessageFormat.format("Formatter delcaration contains a parameter without key value pair: {0}", param));
            }
            map.set(keyValuePair[0], keyValuePair[1]);
        }
        return map;
    }
}
