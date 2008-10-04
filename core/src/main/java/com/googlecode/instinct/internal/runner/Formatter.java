/*
 * Copyright 2006-2007 Tom Adams
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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.report.ResultFormat;
import static com.googlecode.instinct.report.ResultFormat.isEquivalentToAny;
import static com.googlecode.instinct.report.ResultFormat.valueOf;
import java.io.File;
import static java.util.Arrays.asList;

public final class Formatter {
    private ResultFormat type;
    private File toDir;

    public Formatter() {
    }

    public Formatter(final ResultFormat type) {
        this.type = type;
    }

    public Formatter(final ResultFormat type, final File toDir) {
        this.type = type;
        this.toDir = toDir;
    }

    public ResultFormat getType() {
        return type;
    }

    public void setType(final String type) {
        checkNotWhitespace(type);
        if (!isEquivalentToAny(type)) {
            throw new UnsupportedOperationException(
                    "Formatter type '" + type + "' is not supported, supported types " + asList(ResultFormat.values()));
        }
        this.type = valueOf(type.toUpperCase());
    }

    public File getToDir() {
        return toDir;
    }

    public void setToDir(final File toDir) {
        this.toDir = toDir;
    }

    @Override
    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "RedundantIfStatement"})
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Formatter formatter = (Formatter) o;
        if (toDir != null ? !toDir.equals(formatter.toDir) : formatter.toDir != null) {
            return false;
        }
        if (type != formatter.type) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings({"UnnecessaryParentheses"})
    public int hashCode() {
        int result;
        result = type != null ? type.hashCode() : 0;
        result = 31 * result + (toDir != null ? toDir.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Formatter: ").append(type).append(" to ").append(toDir == null ? "sysout" : toDir.getAbsolutePath()).toString();
    }
}
