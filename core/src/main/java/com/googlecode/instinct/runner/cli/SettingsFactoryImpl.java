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

package com.googlecode.instinct.runner.cli;

import fj.data.List;

public final class SettingsFactoryImpl implements SettingsFactory {

    public Settings extractFrom(final List<String> arguments) {
        if (arguments == null) {
            return null;
        }
        final Settings settings = new Settings();
        int index = 0;
        while (index < arguments.length()) {
            final String nextArgument = arguments.index(index++);
            if (settings.getArguments().isEmpty()) {
                index = lookForNextOption(arguments, settings, index, nextArgument);
            } else {
                settings.addArgument(nextArgument);
            }
        }
        return settings;
    }

    private int lookForNextOption(final List<String> arguments, final Settings settings, final int startIndex, final String nextArgument) {
        int index = startIndex;
        final OptionArgument optionKey = OptionArgument.indicatedBy(nextArgument);
        if (optionKey == null) {
            settings.addArgument(nextArgument);
        } else {
            if (index == arguments.length()) {
                throw new IllegalArgumentException(String.format("Option %s requires a value, but none was given", optionKey));
            }
            final String value = arguments.index(index++);
            settings.setOption(optionKey, value);
        }
        return index;
    }
}
