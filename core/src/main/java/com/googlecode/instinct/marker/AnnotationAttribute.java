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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.lang.Primordial;

@SuppressWarnings({"StaticVariableOfConcreteClass"})
public final class AnnotationAttribute extends Primordial {
    public static final AnnotationAttribute IGNORE = new AnnotationAttribute("AnyAttributeName", "IGNORE");
    private final String attributeName;
    private final Object attributeValue;

    public AnnotationAttribute(final String attributeName, final Object attributeValue) {
        checkNotNull(attributeName, attributeValue);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public Object getAttributeValue() {
        return attributeValue;
    }
}
