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

package com.googlecode.instinct.test.actor;

import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Subject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings({"CatchGenericClass"})
public final class SubjectCreatorImpl implements SubjectCreator {
    private final ClassEdge classEdge = new ClassEdgeImpl();

    @Suggest("Support subjects that use constructor, setter or field DI.")
    public Object create(final Field field) {
        ensureValidSubjectType(field.getType());
        try {
            return doCreateSubjectFieldValue(field);
        } catch (Exception e) {
            final String message = "Unable to create value for marked subject field '" + field.getName() + "' of type " + field.getType().getName() +
                    ".\n" + "\tThis may be because the type of the subject is an interface or abstract class.\n" +
                    "\tSubject classes can be manually specified using the 'implementationClass' field of the Subject annotation or " +
                    "by using a naming convention of Default<InterfaceName> or <InterfaceName>Impl when creating your classes.\n";
            throw new RuntimeException(message, e);
        }
    }

    private Object doCreateSubjectFieldValue(final Field field) {
        if (field.getType().isInterface() || isAbstract(field.getType())) {
            return createInterfaceSubjectImplementation(field);
        } else {
            return createConcreteClassSubjectImplementation(field);
        }
    }

    private Object createInterfaceSubjectImplementation(final Field field) {
        if (subjectAnnotationContainsImplementationClass(field)) {
            return createSubjectUsingSuppliedImplementationClass(field);
        } else {
            return createSubjectUsingNamingConvention(field);
        }
    }

    private Object createConcreteClassSubjectImplementation(final Field field) {
        try {
            return instantiateClass(field.getType());
        } catch (Exception throwable) {
            final String message = "Unable to invoke nullary constructor of " + field.getType().getSimpleName() + " for subject field '" +
                    field.getName() + "', only nullary constructors are supported";
            throw new RuntimeException(message, throwable);
        }
    }

    private Object createSubjectUsingSuppliedImplementationClass(final AnnotatedElement field) {
        final Class<?> implementationClass = getSuppliedAnnotationClass(field);
        return instantiateClass(implementationClass);
    }

    @Suggest("Support 'Default' naming convention.")
    private Object createSubjectUsingNamingConvention(final Field field) {
        final String implementationClassName = field.getType().getName() + "Impl";
        return instantiateClass(classEdge.forName(implementationClassName));
    }

    private boolean subjectAnnotationContainsImplementationClass(final AnnotatedElement subjectField) {
        final Class<?> suppliedImplementationClass = getSuppliedAnnotationClass(subjectField);
        return !Subject.SubjectClassIsImplementationClass.class.equals(suppliedImplementationClass);
    }

    private Class<?> getSuppliedAnnotationClass(final AnnotatedElement subjectField) {
        final Subject annotation = subjectField.getAnnotation(Subject.class);
        return annotation.implementation();
    }

    private <T> void ensureValidSubjectType(final Class<T> fieldType) {
        if (fieldType.isArray()) {
            throw new IllegalArgumentException("Unable to autowire a subject that is an array");
        }
    }

    @Suggest("Add support for DI, etc.")
    private <T> Object instantiateClass(final Class<T> typeToInstantiate) {
        return classEdge.newInstance(typeToInstantiate);
    }

    private <T> boolean isAbstract(final Class<T> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }
}
