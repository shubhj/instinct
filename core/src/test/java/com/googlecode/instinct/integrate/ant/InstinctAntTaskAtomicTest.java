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
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.apache.tools.ant.Task;

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class InstinctAntTaskAtomicTest extends InstinctTestCase {
    private static final String FAILURE_PROPERTY = "specifications-failed";
    private InstinctAntTask antTask;

    @Override
    public void setUpSubject() {
        antTask = new InstinctAntTask();
    }

    public void testConformsToClassTraits() {
        checkClass(InstinctAntTask.class, Task.class);
    }

    public void testContainsASinngleNoArgumentConstructor() {
        assertEquals(1, InstinctAntTask.class.getConstructors().length);
        assertEquals(0, InstinctAntTask.class.getConstructors()[0].getParameterTypes().length);
    }

    public void testSetFailureProperty() {
        antTask.setFailureProperty(FAILURE_PROPERTY);
    }

    public void testHasACloneMethodToSupportTaskApi() throws CloneNotSupportedException {
        antTask.clone();
    }
}
