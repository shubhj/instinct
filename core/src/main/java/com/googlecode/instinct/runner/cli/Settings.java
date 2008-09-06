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

import fj.F;
import fj.data.HashMap;
import fj.data.List;
import fj.pre.Equal;
import fj.pre.Hash;

public final class Settings {
    private List<String> arguments;
    private HashMap<OptionArgument, String> optionValues;

    public Settings() {
        arguments = List.nil();
        optionValues = new HashMap<OptionArgument, String>(Equal.equal(new F<OptionArgument, F<OptionArgument, Boolean>>() {
            public F<OptionArgument, Boolean> f(final OptionArgument field) {
                return new F<OptionArgument, Boolean>() {
                    public Boolean f(final OptionArgument otherField) {
                        return field.equals(otherField);
                    }
                };
            }
        }), Hash.hash(new F<OptionArgument, Integer>() {
            public Integer f(final OptionArgument argument) {
                return argument.hashCode();
            }
        }));
    }

    public List<OptionArgument> getOptions() {
        return optionValues.keys();
    }

    public String getOption(final OptionArgument argument) {
        return optionValues.get(argument).orSome((String) null);
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setOption(final OptionArgument key, final String value) {
        if (optionValues.contains(key)) {
            throw new IllegalArgumentException(String.format("Option %1$s has already been set", key));
        }
        optionValues.set(key, value);
    }

    public void addArgument(final String argument) {
        arguments = arguments.snoc(argument);
    }
}
