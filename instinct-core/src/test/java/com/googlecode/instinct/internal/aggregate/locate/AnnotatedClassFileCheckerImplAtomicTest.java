package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactory;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.eq;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

@Suggest("Come back and drop in an annotation checker once written")
public final class AnnotatedClassFileCheckerImplAtomicTest extends InstinctTestCase {
    private AnnotatedClassFileChecker checker;
    private File packageRoot;
    private File classFile;
    private AnnotationChecker annotationChecker;
    private ClassInstantiator instantiator;
    private ClassInstantiatorFactory instantiatorFactory;

    public void testProperties() {
        checkClass(AnnotatedClassFileCheckerImpl.class, AnnotatedClassFileChecker.class);
    }

    public void testIsAnnotated() {
        expects(instantiatorFactory).method("create").with(same(packageRoot)).will(returnValue(instantiator));
        expects(instantiator).method("instantiateClass").with(same(classFile)).will(returnValue(Class.class));
        expects(annotationChecker).method("isAnnotated").with(eq(Class.class), eq(BehaviourContext.class)).will(returnValue(true));
        assertTrue(checker.isAnnotated(classFile, BehaviourContext.class));
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "mockPackageRoot");
        classFile = mock(File.class, "mockClassFile");
        annotationChecker = mock(AnnotationChecker.class);
        instantiator = mock(ClassInstantiator.class);
        instantiatorFactory = mock(ClassInstantiatorFactory.class);
    }

    @Override
    public void setUpSubject() {
        checker = new AnnotatedClassFileCheckerImpl(packageRoot);
        insertFieldValue(checker, "annotationChecker", annotationChecker);
        insertFieldValue(checker, "instantiatorFactory", instantiatorFactory);
    }
}
