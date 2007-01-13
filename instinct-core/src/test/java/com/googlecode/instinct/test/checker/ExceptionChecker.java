package com.googlecode.instinct.test.checker;

import java.lang.reflect.Constructor;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.mock.instance.UberInstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.TestingException;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassPropertiesSuperClass;

@SuppressWarnings({"ExceptionClassNameDoesntEndWithException"})
public final class ExceptionChecker {
    private static final EdgeClass edgeClass = new DefaultEdgeClass();
    private static final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();
    private static final InstanceProvider instanceProvider = new UberInstanceProvider();

    private ExceptionChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T extends RuntimeException> void checkException(final Class<T> exceptionClass) {
        checkClassPropertiesSuperClass(RuntimeException.class, exceptionClass);
        checkSuperClassMethods(exceptionClass);
    }

    @Suggest("Bit smelly.")
    private static <T extends RuntimeException> void checkSuperClassMethods(final Class<T> cls) {
        try {
            checkMessage(cls);
        } catch (EdgeException ignored) {
        }
        try {
            checkCause(cls);
        } catch (EdgeException ignored) {
        }
        try {
            checkMessageCause(cls);
        } catch (EdgeException ignored) {
        }
    }

    private static <T extends RuntimeException> void checkMessage(final Class<T> cls) {
        final Constructor<T> constructor = getConstructor(cls, String.class);
        final Object message = instanceProvider.newInstance(String.class);
        final Exception instance = (Exception) edgeConstructor.newInstance(constructor, new Object[]{message});
        if (!message.equals(instance.getMessage())) {
            throw new TestingException("Constructor " + cls.getSimpleName() + "(String) must call super(String)");
        }
    }

    private static <T extends RuntimeException> void checkCause(final Class<T> cls) {
        final Constructor<T> constructor = getConstructor(cls, Throwable.class);
        final Object cause = instanceProvider.newInstance(Throwable.class);
        final Exception instance = (Exception) edgeConstructor.newInstance(constructor, new Object[]{cause});
        if (!cause.equals(instance.getCause())) {
            throw new TestingException("Constructor " + cls.getSimpleName() + "(Throwable) must call super(Throwable)");
        }
    }

    private static <T extends RuntimeException> void checkMessageCause(final Class<T> cls) {
        final Constructor<T> constructor = getConstructor(cls, String.class, Throwable.class);
        final Object message = instanceProvider.newInstance(String.class);
        final Object cause = instanceProvider.newInstance(Throwable.class);
        final Exception instance = (Exception) edgeConstructor.newInstance(constructor, new Object[]{message, cause});
        if (!message.equals(instance.getMessage())) {
            throw new TestingException("Constructor " + cls.getSimpleName() + "(String,Throwable) must call super(String,Throwable)");
        }
        if (!cause.equals(instance.getCause())) {
            throw new TestingException("Constructor " + cls.getSimpleName() + "(String,Throwable) must call super(String,Throwable)");
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?>... types) {
        return edgeClass.getConstructor(cls, types);
    }
}
