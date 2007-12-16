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

package com.googlecode.instinct.marker;

import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.lang.Primordial;
import com.googlecode.instinct.marker.naming.NamingConvention;
import java.lang.annotation.Annotation;

@SuppressWarnings({"MethodParameterOfConcreteClass", "InstanceVariableOfConcreteClass", "MethodReturnOfConcreteClass"})
@Fix({"Test drive."})
public final class MarkingSchemeImpl extends Primordial implements MarkingScheme {
    private final Class<? extends Annotation> annotationType;
    private final NamingConvention namingConvention;
    private final AnnotationAttribute attributeConstraint;

    // TODO: Groups, remove the attributeConstraint
    public <A extends Annotation> MarkingSchemeImpl(final Class<A> annotationType, final NamingConvention namingConvention,
            final AnnotationAttribute attributeConstraint) {
        checkNotNull(annotationType, namingConvention, attributeConstraint);
        this.annotationType = annotationType;
        this.namingConvention = namingConvention;
        this.attributeConstraint = attributeConstraint;
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Annotation> Class<A> getAnnotationType() {
        return (Class<A>) annotationType;
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public AnnotationAttribute getAttributeConstraint() {
        return attributeConstraint;
    }
}
