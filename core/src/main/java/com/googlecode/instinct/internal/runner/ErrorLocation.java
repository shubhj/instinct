/*
 * Copyright 2006-2008 Workingmouse
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

public enum ErrorLocation {
    CLASS_INITIALISATION("Class initialisation"), AUTO_WIRING("Actor auto wiring"), BEFORE_SPECIFICATION("Before specification methods"),
    SPECIFICATION("Specification"), AFTER_SPECIFICATION("After specification methods"), MOCK_VERIFICATION("Mock verification");
    private final String message;

    ErrorLocation(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
