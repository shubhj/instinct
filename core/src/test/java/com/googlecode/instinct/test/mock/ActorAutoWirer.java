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

package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.actor.ActorAutoWirerImpl;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.TestingException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

@SuppressWarnings({"CatchGenericClass"})
public final class ActorAutoWirer {
    private static final SubjectCreator SUBJECT_CREATOR = new SubjectCreatorImpl();
    private static final com.googlecode.instinct.actor.ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();

    private ActorAutoWirer() {
        throw new UnsupportedOperationException();
    }

    public static void autoWireDoubleFields(final Object instanceToAutoWire) {
        actorAutoWirer.autoWireFields(instanceToAutoWire);
    }

    public static void autoWireSubjectFields(final Object instanceToAutoWire) {
        final Field[] fields = instanceToAutoWire.getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Subject.class) && askedToAutoWireSubject(field)) {
                injectSubject(instanceToAutoWire, field);
            }
        }
    }

    @SuppressWarnings({"OverlyBroadCatchBlock"})
    private static void injectSubject(final Object instanceToAutoWire, final Field field) {
        field.setAccessible(true);
        try {
            field.set(instanceToAutoWire, SUBJECT_CREATOR.create(field));
        } catch (Throwable throwable) {
            final String message =
                    "Unable to autowire a " + "subject" + " value into field '" + field.getName() + "' of type " + field.getType().getSimpleName();
            throw new TestingException(message, throwable);
        }
    }

    private static boolean askedToAutoWireSubject(final AnnotatedElement subjectField) {
        final Subject annotation = subjectField.getAnnotation(Subject.class);
        return annotation.auto();
    }
}
