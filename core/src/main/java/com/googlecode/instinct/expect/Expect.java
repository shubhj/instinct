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

package com.googlecode.instinct.expect;

/**
 * Static wrapper for making expectations about an object. This class is designed to be statically imported, usage is as follows:
 * <pre>
 * import static com.googlecode.instinct.expect.Expect.expect;
 * ...
 * expect.that("instinct").isEqualTo("instinct");
 * </pre>
 * If you prefer not to statically import, create an instance of an {@link com.googlecode.instinct.expect.ExpectThat} implementation directly.
 */
public final class Expect {
    // Note. Lower case name to read nicely when statically imported.
    public static final ExpectThat expect = new ExpectThatImpl();

    private Expect() {
        throw new UnsupportedOperationException();
    }
}
