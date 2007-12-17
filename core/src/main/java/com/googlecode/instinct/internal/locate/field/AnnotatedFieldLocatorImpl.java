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

package com.googlecode.instinct.internal.locate.field;

import com.googlecode.instinct.internal.locate.AnnotationChecker;
import com.googlecode.instinct.internal.locate.AnnotationCheckerImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class AnnotatedFieldLocatorImpl implements AnnotatedFieldLocator {
    private AnnotationChecker annotationChecker = new AnnotationCheckerImpl();

    public <A extends Annotation, T> Field[] locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        return findAnnotatedFields(cls.getDeclaredFields(), runtimeAnnotationType);
    }

    private <A extends Annotation> Field[] findAnnotatedFields(final Field[] declaredFields, final Class<A> runtimeAnnotationType) {
        final List<Field> annotatedFields = new ArrayList<Field>();
        for (final Field field : declaredFields) {
            if (annotationChecker.isAnnotated(field, runtimeAnnotationType, IGNORE)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields.toArray(new Field[annotatedFields.size()]);
    }
}
