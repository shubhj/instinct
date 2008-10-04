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

package com.googlecode.instinct.internal.report;

import com.googlecode.instinct.internal.runner.Formatter;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ResultMessageBuilder;

public final class ResultMessageBuilderFactoryImpl implements ResultMessageBuilderFactory {
    public ResultMessageBuilder createFor(final Formatter formatter) {
        checkNotNull(formatter);
        checkNotNull(formatter.getType());
        ResultMessageBuilder builder = null;
        switch (formatter.getType()) {
            case BRIEF:
                builder = new BriefResultMessageBuilder();
                break;
            case QUIET:
                builder = new QuietResultMessageBuilder();
                break;
            case VERBOSE:
                builder = new VerboseResultMessageBuilder();
                break;
            case XML:
                builder = new XmlResultMessageBuilder();
                break;
        }
        return builder;
    }
}
