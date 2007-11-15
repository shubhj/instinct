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
import java.io.IOException;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;

public final class FileCheckerImplAtomicTest extends InstinctTestCase {
    private FileChecker checkerWithExistingFile;
    private FileChecker checkerWithNonExistingFile;

    public void testConformsToClassTraits() {
        checkPublic(FileCheckerImpl.class);
    }

    @Override
    public void setUpSubject() {
        final File tempFile;
        try {
            tempFile = File.createTempFile("Foo", "Bar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        checkerWithNonExistingFile = new FileCheckerImpl(new File("this-file-does-not-exist"));
        checkerWithExistingFile = new FileCheckerImpl(tempFile);
    }

    public void testFailsIfFileDoesNotExist() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                checkerWithNonExistingFile.exists();
            }
        });
    }

    public void testPassesIfFileExists() {
        checkerWithExistingFile.exists();
    }
}