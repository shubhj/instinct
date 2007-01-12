package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.JavaClassNameFactory;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.once;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.ReflectUtil.insertFieldValue;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    @Suggest("Make this a uniqie field, or dummy?")
    private static final String FULLY_QUALIFIED_CLASS_NAME = "FQN";
    private AnnotationChecker checker;
    private File packageRoot;
    private File classFile;
    private EdgeClass edgeClass;
    private JavaClassNameFactory classNameFactory;
    private JavaClassName className;
    private Class<?> classWithoutAnnotation;

    public void testProperties() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testIsAnnotated() {
        expects(classNameFactory, once()).method("create").with(same(packageRoot), same(classFile)).will(returnValue(className));
        expects(className, once()).method("getFullyQualifiedName").will(returnValue(FULLY_QUALIFIED_CLASS_NAME));
        expects(edgeClass, once()).method("forName").with(same(FULLY_QUALIFIED_CLASS_NAME)).will(returnValue(classWithoutAnnotation));
        checker.isAnnotated(classFile, Specification.class);
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "packageRoot");
        classFile = mock(File.class, "classFile");
        edgeClass = mock(EdgeClass.class);
        classNameFactory = mock(JavaClassNameFactory.class);
        className = mock(JavaClassName.class);
        classWithoutAnnotation = WithoutRuntimeAnnotatedMethods.class;
    }

    @Override
    public void setUpSubject() {
        checker = new AnnotationCheckerImpl(packageRoot);
        insertFieldValue(checker, "edgeClass", edgeClass);
        insertFieldValue(checker, "classNameFactory", classNameFactory);
    }
}
