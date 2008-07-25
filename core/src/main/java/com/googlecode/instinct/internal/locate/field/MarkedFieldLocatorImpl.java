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

import static com.googlecode.instinct.internal.util.FieldEquality.fieldEquals;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import fj.data.List;
import java.lang.reflect.Field;

public final class MarkedFieldLocatorImpl implements MarkedFieldLocator {
    private AnnotatedFieldLocator annotatedFieldLocator = new AnnotatedFieldLocatorImpl();
    private NamedFieldLocator namedFieldLocator = new NamedFieldLocatorImpl();

    public <T> List<Field> locateAll(final Class<T> cls, final MarkingScheme markingScheme) {
        checkNotNull(cls, markingScheme);
        return findFieldsByAnnotation(cls, markingScheme).append(findFieldsByNamingConvention(cls, markingScheme)).nub(fieldEquals());
    }

    private <T> List<Field> findFieldsByAnnotation(final Class<T> cls, final MarkingScheme markingScheme) {
        return annotatedFieldLocator.locate(cls, markingScheme.getAnnotationType());
    }

    private <T> List<Field> findFieldsByNamingConvention(final Class<T> cls, final MarkingScheme markingScheme) {
        return namedFieldLocator.locate(cls, markingScheme.getNamingConvention());
    }
}
