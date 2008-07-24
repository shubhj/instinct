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

package com.googlecode.instinct.internal.locate.method;

import com.googlecode.instinct.internal.locate.AnnotationChecker;
import com.googlecode.instinct.internal.locate.AnnotationCheckerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import fj.F;
import fj.data.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotatedMethodLocatorImpl implements AnnotatedMethodLocator {
    private final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
    private final SuperClassTraversingMethodLocator methodLocator = new SuperClassTraversingMethodLocatorImpl();

    public <A extends Annotation, T> List<Method> locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        return methodLocator.locate(cls).filter(new F<Method, Boolean>() {
            public Boolean f(final Method method) {
                return annotationChecker.isAnnotated(method, runtimeAnnotationType, IGNORE);
            }
        });
    }
}
