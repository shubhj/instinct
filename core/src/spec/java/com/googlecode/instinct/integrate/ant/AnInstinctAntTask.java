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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.apache.tools.ant.Task;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnInstinctAntTask {
    private static final String FAILURE_PROPERTY = "specifications-failed";
    @Subject private InstinctAntTask antTask;

    @BeforeSpecification
    public void setUpSubject() {
        antTask = new InstinctAntTask();
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(InstinctAntTask.class, Task.class);
    }

    @Specification
    public void containsASingleNoArgumentConstructor() {
        expect.that(InstinctAntTask.class.getConstructors()).isOfSize(1);
        expect.that(InstinctAntTask.class.getConstructors()[0].getParameterTypes()).isOfSize(0);
    }

    @Specification
    public void allowsTheSettingOfAFailureProperty() {
        antTask.setFailureProperty(FAILURE_PROPERTY);
    }

    @Specification
    public void hasACloneMethodToSupportTaskApi() throws CloneNotSupportedException {
        antTask.clone();
    }

    @Specification
    public void willAcceptMultipleFormatters() {
        antTask.addFormatter(new Formatter());
        antTask.addFormatter(new Formatter());
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptANullFormatter() {
        antTask.addFormatter(null);
    }
}
