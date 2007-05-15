package com.googlecode.instinct.integrate.idea;

import java.lang.annotation.Annotation;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Specification;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import static com.intellij.psi.PsiModifier.ABSTRACT;
import static com.intellij.psi.PsiModifier.PUBLIC;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;

@Suggest({"Does this have to be a static class?", "Break up this class."})
public final class ClassUtil {
    private static final PsiMethod METHOD_IS_NOT_A_SPECIFICATION = null;

    private ClassUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isContextClass(final PsiClass psiClass) {
        return canBeRun(psiClass) && containsSpecificationMethod(psiClass);
    }

    public static boolean containsSpecificationMethod(final PsiClass psiClass) {
        final PsiMethod[] methods = psiClass.getAllMethods();
        for (final PsiMethod method : methods) {
            if (isSpecificationMethod(method)) {
                return true;
            }
        }
        return false;
    }

    public static String fullName(final PsiClass psiClass) {
        return psiClass.getQualifiedName();
    }

    public static String shortName(final PsiClass psiClass) {
        return shortName(fullName(psiClass));
    }

    public static String shortName(final String fullName) {
        final int lastDot = fullName.lastIndexOf(".");
        return lastDot == -1 ? fullName : fullName.substring(lastDot + 1, fullName.length());
    }

    @Suggest({"Do we want to allow non-public classes to be run? See LifeCycleMethodValidatorImpl.",
            "Check for no-args constructor"})
    public static boolean canBeRun(final PsiClass psiClass) {
        return isPublic(psiClass) && !psiClass.isInterface() && !isAbstract(psiClass);
    }

    public static boolean isSpecificationMethod(final PsiMethod method) {
        return hasAnnotation(method, Specification.class) && hasNoParams(method);
    }

    public static boolean isAbstract(final PsiModifierListOwner definition) {
        final PsiModifierList modifiers = definition.getModifierList();
        return modifiers != null && modifiers.hasModifierProperty(ABSTRACT);
    }

    public static <A extends Annotation> boolean hasAnnotation(final PsiModifierListOwner definition, final Class<A> annotation) {
        final PsiModifierList modifierList = definition.getModifierList();
        return modifierList != null && modifierList.findAnnotation(annotation.getName()) != null;
    }

    public static boolean isPublic(final PsiModifierListOwner definition) {
        final PsiModifierList modifierList = definition.getModifierList();
        return modifierList != null && modifierList.hasModifierProperty(PUBLIC);
    }

    public static boolean hasNoParams(final PsiMethod method) {
        return method.getParameterList().getParametersCount() == 0;
    }

    public static boolean isVoid(final PsiMethod method) {
        return PsiType.VOID.equals(method.getReturnType());
    }

    public static PsiMethod getSpecificationMethod(final PsiElement psiElement) {
        if (psiElement instanceof PsiIdentifier && psiElement.getParent() instanceof PsiMethod) {
            final PsiMethod method = (PsiMethod) psiElement.getParent();
            if (isSpecificationMethod(method)) {
                return method;
            }
        }
        return METHOD_IS_NOT_A_SPECIFICATION;
    }

    public static PsiClass getContextClass(final PsiElement element) {
        for (PsiElement current = element; current != null; current = current.getParent()) {
            if (current instanceof PsiClass) {
                final PsiClass candidate = (PsiClass) current;
                if (isContextClass(candidate)) {
                    return candidate;
                }
            } else if (current instanceof PsiFile) {
                final PsiClass candidate = getMainClass((PsiFile) current);
                if (candidate != null && isContextClass(candidate)) {
                    return candidate;
                }
            }
        }
        assert element != null;
        final PsiFile file = element.getContainingFile();
        if (file instanceof PsiJavaFile) {
            final PsiClass[] definedClasses = ((PsiJavaFile) file).getClasses();
            for (final PsiClass definedClass : definedClasses) {
                if (isContextClass(definedClass)) {
                    return definedClass;
                }
            }
            for (final PsiClass definedClass : definedClasses) {
                final PsiClass candidate = findCandidateContextForProductionClass(definedClass);
                if (candidate != null && isContextClass(candidate)) {
                    return candidate;
                }
            }
        }
        return null;
    }

    private static PsiClass findCandidateContextForProductionClass(final PsiClass prodClass) {
        final String candidateContextClassName = prodClass.getQualifiedName() + "Context";
        final Project project = prodClass.getProject();
        return PsiManager.getInstance(project).findClass(candidateContextClassName, GlobalSearchScope.allScope(project));
    }

    private static PsiClass getMainClass(final PsiFile psiFile) {
        if (psiFile instanceof PsiJavaFile) {
            final PsiJavaFile javaFile = (PsiJavaFile) psiFile;
            final PsiClass[] definedClasses = javaFile.getClasses();
            for (final PsiClass definedClass : definedClasses) {
                if (isPublic(definedClass)) {
                    return definedClass;
                }
            }
        }
        return null;
    }
}
