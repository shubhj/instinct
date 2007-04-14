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

    @SuppressWarnings({"unchecked"})
    public static <T> T createSubject(final Class<T> subjectClass, final Object... dependencies) {
        checkNotNull(subjectClass, dependencies);
        final T subject = OBJECT_FACTORY.create(subjectClass);
        for (final Object dependency : dependencies) {
            Reflector.insertFieldValueUsingInferredType(subject, dependency);
        }
        return subject;
    }
}
