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

package com.googlecode.instinct.integrate.ant;

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.report.ResultFormat;
import static com.googlecode.instinct.report.ResultFormat.valueOf;
import com.googlecode.instinct.report.ResultMessageBuilder;
import static java.util.Arrays.asList;

public final class Formatter {
    private ResultFormat type;

    public void setType(final String type) {
        checkNotWhitespace(type);
        boolean isAMatchedFormat = false;
        for (final ResultFormat resultFormat : ResultFormat.values()) {
            isAMatchedFormat = isAMatchedFormat || type.equalsIgnoreCase(resultFormat.name());
        }

        if (!isAMatchedFormat) {
            throw new UnsupportedOperationException(
                    "Formatter type '" + type + "' is not supported, supported types " + asList(ResultFormat.values()));
        }
        this.type = valueOf(type.toUpperCase());
    }

    public ResultMessageBuilder createMessageBuilder() {
        return type.getMessageBuilder();
    }
}
