/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.integrate.idea;

import javax.swing.Icon;
import com.googlecode.instinct.internal.util.Suggest;
import com.intellij.execution.LocatableConfigurationType;
import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public final class InstinctConfigurationType implements LocatableConfigurationType {
    private static final String BEHAVIOURS_INTERFACE = "org.jbehave.core.behaviour.Behaviours";
    private final ConfigurationFactory factory;

    @Suggest("Can we do better than pass this?")
    public InstinctConfigurationType() {
        factory = new InstinctConfigurationFactory(this);
    }

    public String getDisplayName() {
        return "Instinct";
    }

    public String getConfigurationTypeDescription() {
        return "Instinct Configuration";
    }

    public Icon getIcon() {
        return IconLoader.getIcon("/instinct.png");
    }

    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{factory};
    }

    @NotNull
    @NonNls
    public String getComponentName() {
        return "#com.googlecode.instinct.integrate.idea.InstinctConfigurationType";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public RunnerAndConfigurationSettings createConfigurationByLocation(final Location location) {
        final PsiClass contextClass = getContextClass(location.getPsiElement());
        if (contextClass == null) {
            return null;
        }
        final RuntimeConfigurationProducer configurationProducer = new InstinctConfigurationProducer(contextClass, this);
        return configurationProducer.createProducer(location, null).getConfiguration();
    }

    public boolean isConfigurationByElement(final RunConfiguration configuration, final Project project, final PsiElement element) {
        final PsiClass behaviorClass = getContextClass(element);
        if (behaviorClass == null) {
            return false;
        }
        final InstinctRunConfiguration runConfiguration = ((InstinctRunConfiguration) configuration);
        return Comparing.equal(ClassUtil.fullName(behaviorClass), runConfiguration.getContextClassName())
                && Comparing.equal(getBehaviourMethodName(element), runConfiguration.getSpecificationMethodName());
    }

    public PsiClass getContextClass(final PsiElement element) {
        for (PsiElement current = element; current != null; current = current.getParent()) {
            if (current instanceof PsiClass) {
                final PsiClass currentClass = (PsiClass) current;
                if (isContextClass(currentClass)) {
                    return currentClass;
                }
            } else if (current instanceof PsiFile) {
                final PsiClass psiClass = getMainClass((PsiFile) current);
                if (psiClass != null && isContextClass(psiClass)) {
                    return psiClass;
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
                final PsiClass contextClassCandidate = checkIfThereIsOneWithBehaviorAtEnd(definedClass);
                if (contextClassCandidate != null && isContextClass(contextClassCandidate)) {
                    return contextClassCandidate;
                }
            }
        }
        return null;
    }

    public PsiMethod getBehaviourMethodElement(PsiElement psiElement) {
        if (psiElement instanceof PsiIdentifier && psiElement.getParent() instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) psiElement.getParent();
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
        return null;
    }

    private String getBehaviourMethodName(PsiElement element) {
        PsiMethod method = getBehaviourMethodElement(element);
        return method == null ? null : method.getName();
    }

    private PsiClass checkIfThereIsOneWithBehaviorAtEnd(PsiClass psiClass) {
        String nameToCheck = psiClass.getQualifiedName() + "Behavior";
        Project project = psiClass.getProject();
        return PsiManager.getInstance(project).findClass(nameToCheck, GlobalSearchScope.allScope(project));
    }

    private PsiClass getMainClass(PsiFile psiFile) {
        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile javaFile = (PsiJavaFile) psiFile;
            PsiClass[] definedClasses = javaFile.getClasses();
            for (int i = 0; i < definedClasses.length; i++) {
                PsiClass definedClass = definedClasses[i];
                if (isPublic(definedClass)) {
                    return definedClass;
                }
            }
        }
        return null;
    }

    @Suggest("Dupe.")
    private boolean isContextClass(final PsiClass psiClass) {
        return isRunnableClass(psiClass) && (implementsBehaviorsInterface(psiClass) || containsShouldMethod(psiClass));
    }

    @Suggest({"Replace with annotation lookup.", "Fix the dupe with "})
    private boolean implementsBehaviorsInterface(final PsiClass psiClass) {
        final PsiClassType[] interfaces = psiClass.getImplementsListTypes();
        for (final PsiClassType iface : interfaces) {
            if (BEHAVIOURS_INTERFACE.equals(iface.resolve().getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsShouldMethod(final PsiClass psiClass) {
        final PsiMethod[] methods = psiClass.getAllMethods();
        for (final PsiMethod method : methods) {
            if (isSpecificationMethod(method)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRunnableClass(PsiClass psiClass) {
        return isPublic(psiClass) && !psiClass.isInterface() && !isAbstract(psiClass);
    }

    private boolean isAbstract(final PsiModifierListOwner definition) {
        final PsiModifierList modifiers = definition.getModifierList();
        return modifiers != null && modifiers.hasModifierProperty("abstract");
    }

    @Suggest("This needs to use the @Specification annotation.")
    private boolean isSpecificationMethod(final PsiMethod method) {
        return method.getName().startsWith("should") && isPublic(method) && isVoid(method);
    }

    private boolean isPublic(final PsiModifierListOwner definition) {
        final PsiModifierList modifierList = definition.getModifierList();
        return modifierList != null && modifierList.hasModifierProperty("public");
    }

    private boolean isVoid(final PsiMethod method) {
        return PsiType.VOID.equals(method.getReturnType());
    }

    public static InstinctConfigurationType getInstance() {
        return ApplicationManager.getApplication().getComponent(InstinctConfigurationType.class);
    }
}
