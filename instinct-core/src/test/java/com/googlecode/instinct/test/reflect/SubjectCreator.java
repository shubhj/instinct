package com.googlecode.instinct.test.reflect;

import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Add another method to use constructor directly.")
public final class SubjectCreator {
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactoryImpl();

    private SubjectCreator() {
        throw new UnsupportedOperationException();
    }

    public static <T> T createSubject(final Class<T> subjectClass, final Object... dependencies) {
        checkNotNull(subjectClass, dependencies);
        return createSubjectWithConstructorArgs(subjectClass, new Object[]{}, dependencies);
    }

    public static <T> T createSubjectWithConstructorArgs(final Class<T> subjectClass, final Object[] constructorArgs, final Object... dependencies) {
        checkNotNull(subjectClass, dependencies);
        final T subject = OBJECT_FACTORY.create(subjectClass, constructorArgs);
        for (final Object dependency : dependencies) {
            Reflector.insertFieldValueUsingInferredType(subject, dependency);
        }
        return subject;
    }
}
