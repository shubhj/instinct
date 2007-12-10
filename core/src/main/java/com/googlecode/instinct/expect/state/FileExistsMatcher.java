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

package com.googlecode.instinct.expect.state;

import java.io.File;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class FileExistsMatcher extends TypeSafeMatcher<File> {
    private FileExistsMatcher() {
    }

    @Override
    public boolean matchesSafely(final File item) {
        return item.exists();
    }

    public void describeTo(final Description description) {
        description.appendText("exists");
    }

    @Factory
    public static Matcher<File> exists() {
        return new FileExistsMatcher();
    }
}
