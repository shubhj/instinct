package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Field;
import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.junit.Assert;

public final class AnnotatedFieldLocatorImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(AnnotatedFieldLocatorImpl.class, AnnotatedFieldLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsReturnsNoFields() {
        Assert.assertEquals(new Field[]{}, new AnnotatedFieldLocatorImpl().locate(WithoutRuntimeAnnotations.class, Dummy.class));
    }
}
