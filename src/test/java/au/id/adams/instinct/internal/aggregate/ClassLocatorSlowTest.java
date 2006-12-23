package au.id.adams.instinct.internal.aggregate;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import au.id.adams.instinct.internal.aggregate.locate.AnnotationFileFilter;
import au.id.adams.instinct.internal.aggregate.locate.ClassLocator;
import au.id.adams.instinct.internal.aggregate.locate.ClassLocatorImpl;
import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.test.InstinctTestCase;
import au.id.adams.instinct.internal.util.ClassName;

public final class ClassLocatorSlowTest extends InstinctTestCase {
    private static final int EXPECTED_CONTEXTS = 17;
    private ClassLocator locator;

    public void testFindsCorrectNumberOfContexts() {
        final FileFilter filter = new AnnotationFileFilter(getSpecPackageRoot(), BehaviourContext.class);
        final ClassName[] names = locator.locate(getSpecPackageRoot(), filter);
        assertEquals(EXPECTED_CONTEXTS, names.length);
    }

    private File getSpecPackageRoot() {
        final URL resource = ClassLocatorSlowTest.class.getResource("/");
        final String specRoot = resource.getFile();
        return new File(specRoot);
    }

    @Override
    public void setUpSubjects() {
        locator = new ClassLocatorImpl();
    }
}
