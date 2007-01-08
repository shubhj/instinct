package au.id.adams.instinct.internal.aggregate;

import java.lang.reflect.Method;
import au.id.adams.instinct.internal.aggregate.locate.AnnotatedMethodLocator;
import au.id.adams.instinct.internal.aggregate.locate.AnnotatedMethodLocatorImpl;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.test.InstinctTestCase;

public final class AnnotatedMethodLocatorSlowTest extends InstinctTestCase {
    private AnnotatedMethodLocator locator;

    public void testFindsCorrectNumberOfSpecifications() {
        final Method[] methods = locator.locate(TestContext1.class, Specification.class);
        assertEquals(4, methods.length);
    }

    @Override
    public void setUpSubjects() {
        locator = new AnnotatedMethodLocatorImpl();
    }
}
