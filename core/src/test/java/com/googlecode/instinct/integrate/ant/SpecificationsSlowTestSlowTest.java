package com.googlecode.instinct.integrate.ant;

import java.io.File;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;
import org.apache.tools.ant.Project;

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class SpecificationsSlowTestSlowTest extends InstinctTestCase {
    private Specifications specifications;

    @Override
    public void setUpSubject() {
        specifications = new Specifications(new Project());
        specifications.setDir(new File(".", "core/build/main-classes").getAbsolutePath());
    }

    // Note. If this test fails, you may need to run the Ant build to place the spec classes in the correct location.
    public void testFindsContextsMarkedWithAnnotationsAndMarkedMethods() {
        final JavaClassName[] contextClassNames = specifications.getContextClasses();
        expect.that(contextClassNames).hasSize(2);
    }
}
