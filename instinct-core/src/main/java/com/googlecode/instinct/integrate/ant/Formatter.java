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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.report.BriefContextResultMessageBuilder;
import com.googlecode.instinct.report.ContextResultMessageBuilder;
import com.googlecode.instinct.report.VerboseContextResultMessageBuilder;

public final class Formatter {
    private String type;

    public void setType(final String type) {
        checkNotWhitespace(type);
        if (!type.equals("brief") && !type.equals("verbose")) {
            throw new UnsupportedOperationException("Formatter type '" + type + "' is not supported, supported types are 'brief' & 'verbose'");
        }
        this.type = type;
    }

    public ContextResultMessageBuilder createMessageBuilder() {
        if (type.equals("brief")) {
            return new BriefContextResultMessageBuilder();
        } else {
            return new VerboseContextResultMessageBuilder();
        }
    }
}
