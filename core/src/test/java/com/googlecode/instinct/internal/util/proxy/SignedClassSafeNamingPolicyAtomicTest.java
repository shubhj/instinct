/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.util.proxy;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;
import org.jmock.Expectations;

public final class SignedClassSafeNamingPolicyAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private NamingPolicy signedClassNamingPolicy;
    @Mock private NamingPolicy defaultNamingPolicy;
    @Stub private String prefix;
    @Stub private String source;
    @Stub private String defaultClassName;
    @Dummy private Object key;
    @Dummy private Predicate names;

    @Override
    public void setUpSubject() {
        signedClassNamingPolicy = createSubject(SignedClassSafeNamingPolicy.class, defaultNamingPolicy);
    }

    public void testConformsToClassTraits() {
        checkClass(SignedClassSafeNamingPolicy.class, NamingPolicy.class);
    }

    public void testReturnsClassNameWithinInstinctPackageHeirarchy() {
        expect.that(new Expectations() {
            {
                one(defaultNamingPolicy).getClassName(prefix, source, key, names);
                will(returnValue(defaultClassName));
            }
        });
        final String signedClassSafeName = signedClassNamingPolicy.getClassName(prefix, source, key, names);
        expect.that(signedClassSafeName).equalTo("com.googlecode.instinct.gen." + defaultClassName);
    }
}
