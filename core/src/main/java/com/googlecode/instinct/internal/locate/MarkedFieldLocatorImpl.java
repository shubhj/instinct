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

package com.googlecode.instinct.internal.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.naming.NamingConvention;

public final class MarkedFieldLocatorImpl implements MarkedFieldLocator {
    private AnnotatedFieldLocator annotatedFieldLocator = new AnnotatedFieldLocatorImpl();

    @Suggest("Write other locators, then include them here")
    public <A extends Annotation, T> Field[] locateAll(final Class<T> cls, final Class<A> annotationType, final NamingConvention namingConvention) {
        checkNotNull(cls, annotationType, namingConvention);
        return findFieldsByAnnotation(cls, annotationType);
    }

    private <A extends Annotation, T> Field[] findFieldsByAnnotation(final Class<T> cls, final Class<A> annotationType) {
        return annotatedFieldLocator.locate(cls, annotationType);
    }
}
