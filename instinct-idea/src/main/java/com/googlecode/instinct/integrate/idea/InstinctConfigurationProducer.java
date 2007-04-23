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

import com.intellij.execution.ExecutionUtil;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public final class InstinctConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
    private PsiClass contextClass;
    private InstinctConfigurationType configurationType;

    public InstinctConfigurationProducer(final PsiClass contextClass, final InstinctConfigurationType configurationType) {
        super(configurationType);
        this.contextClass = contextClass;
        this.configurationType = configurationType;
    }

    protected RunnerAndConfigurationSettingsImpl createConfigurationByElement(Location location, final ConfigurationContext configurationContext) {
        location = ExecutionUtil.stepIntoSingleClass(location);
        final PsiClass aClass = configurationType.getContextClass(location.getPsiElement());
        if (aClass == null) return null;
        final PsiMethod currentMethod = configurationType.getBehaviourMethodElement(location.getPsiElement());
        final RunnerAndConfigurationSettingsImpl settings = cloneTemplateConfiguration(location.getProject(), configurationContext);
        final InstinctRunConfiguration configuration = (InstinctRunConfiguration) settings.getConfiguration();
        configuration.setContextClass(ClassUtil.fullName(aClass));
        if (currentMethod != null) {
            configuration.setBehaviorMethod(currentMethod.getName());
        }
        configuration.setModule(ExecutionUtil.findModule(aClass));
        configuration.setName(createName(aClass, currentMethod));
        return settings;
    }

    @Override
    public PsiElement getSourceElement() {
        return contextClass;
    }

    public int compareTo(final Object o) {
        return -1;
    }

    private String createName(final PsiClass aClass, final PsiMethod currentMethod) {
        return currentMethod == null ? ClassUtil.shortName(aClass) : currentMethod.getName();
    }
}
