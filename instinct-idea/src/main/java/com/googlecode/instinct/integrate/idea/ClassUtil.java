package com.googlecode.instinct.integrate.idea;

import com.googlecode.instinct.internal.util.Suggest;
import com.intellij.psi.PsiClass;

@Suggest("Does this have to be a static class?")
public final class ClassUtil {
    public ClassUtil() {
        throw new UnsupportedOperationException();
    }

    public static String fullName(final PsiClass psiClass) {
        return psiClass.getQualifiedName();
    }

    public static String shortName(final PsiClass psiClass) {
        return shortName(fullName(psiClass));
    }

    public static String shortName(final String fullName) {
        final int index = fullName.lastIndexOf(".");
        return index == -1 ? fullName : fullName.substring(index + 1, fullName.length());
    }
}
