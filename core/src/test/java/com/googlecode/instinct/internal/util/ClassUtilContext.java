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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.internal.locate.AnnotatedMethodLocatorImpl;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class ClassUtilContext {

    @Subject private final ClassUtil subject = new ClassUtilImpl();

    @Specification
    public void shouldDetectJavaLibaryClasses() {
        expectJavaLibary(Object.class);
        expectJavaLibary(StringReader.class);
        expectJavaLibary(DocumentBuilder.class);
    }

    @Specification
    public void shouldNotDetectNonJavaLibaries() {
       expectNotJavaLibrary(InstinctRunner.class);
       expectNotJavaLibrary(AnnotatedMethodLocatorImpl.class);
       expectNotJavaLibrary(ClassUtilContext.class);
    }

    private void expectNotJavaLibrary(final Class<?> clazz) {
        expect.that(subject.isJavaLibraryClass(clazz)).isFalse();
    }

    private void expectJavaLibary(final Class<?> clazz) {
        expect.that(subject.isJavaLibraryClass(clazz)).isTrue();
    }
}
