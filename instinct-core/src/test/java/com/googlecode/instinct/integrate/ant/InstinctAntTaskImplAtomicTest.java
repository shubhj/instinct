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

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.TestingException;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassPropertiesSuperClass;
import org.apache.tools.ant.Task;

@SuppressWarnings({"LocalVariableOfConcreteClass"})
public final class InstinctAntTaskImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClassPropertiesSuperClass(InstinctAntTask.class, Task.class);
    }

    public void testContainsANoArgumentConstructor() {
        new InstinctAntTask();
    }

    public void testSetFailureProperty() {
        final InstinctAntTask task = new InstinctAntTask();
        task.setFailureProperty("specifications-failed");
    }

    public void testHasACloneMethodToSupportTaskApi() {
        try {
            new InstinctAntTask().clone();
        } catch (CloneNotSupportedException e) {
            throw new TestingException(e);
        }
    }
}
