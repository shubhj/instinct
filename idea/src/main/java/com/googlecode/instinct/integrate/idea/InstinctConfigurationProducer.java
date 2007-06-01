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

import static com.googlecode.instinct.integrate.idea.ClassUtil.fullName;
import static com.googlecode.instinct.integrate.idea.ClassUtil.getSpecificationMethod;
import static com.googlecode.instinct.integrate.idea.ClassUtil.shortName;
import static com.googlecode.instinct.integrate.idea.InstinctConfigurationType.LOCATION_IS_NOT_IN_A_CONTEXT_CLASS;
import com.googlecode.instinct.internal.util.Suggest;
import static com.intellij.execution.ExecutionUtil.findModule;
import static com.intellij.execution.ExecutionUtil.stepIntoSingleClass;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

@SuppressWarnings(
        {"InstanceVariableOfConcreteClass", "MethodParameterOfConcreteClass", "LocalVariableOfConcreteClass", "CastToConcreteClass", "RawUseOfParameterizedType"})
public final class InstinctConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
    private PsiClass contextClass;
    private InstinctConfigurationType configurationType;

    public InstinctConfigurationProducer(final PsiClass contextClass, final InstinctConfigurationType configurationType) {
        super(configurationType);
        this.contextClass = contextClass;
        this.configurationType = configurationType;
    }

    @Override
    @SuppressWarnings({"RawUseOfParameterizedType"})
    @Suggest("Dupe with InstinctConfigurationType.createConfigurationByLocation().")
    protected RunnerAndConfigurationSettingsImpl createConfigurationByElement(final Location location,
            final ConfigurationContext configurationContext) {
        final Location newlocation = stepIntoSingleClass(location);
        final PsiClass contextClass = ClassUtil.getContextClass(newlocation.getPsiElement());
        if (contextClass == null) {
            return LOCATION_IS_NOT_IN_A_CONTEXT_CLASS;
        } else {
            return createConfigurationByElement(newlocation, configurationContext, contextClass);
        }
    }

    @Override
    public PsiElement getSourceElement() {
        return contextClass;
    }

    public int compareTo(final Object o) {
        return -1;
    }

    private RunnerAndConfigurationSettingsImpl createConfigurationByElement(final Location location, final ConfigurationContext configurationContext,
            final PsiClass contextClass) {
        final RunnerAndConfigurationSettingsImpl settings = cloneTemplateConfiguration(location.getProject(), configurationContext);
        final InstinctRunConfiguration configuration = (InstinctRunConfiguration) settings.getConfiguration();
        configuration.setContextClassName(fullName(contextClass));
        final PsiMethod currentMethod = getSpecificationMethod(location.getPsiElement());
        if (currentMethod != null) {
            configuration.setSpecificationMethodName(currentMethod.getName());
        }
        configuration.setModule(findModule(contextClass));
        configuration.setName(createName(contextClass, currentMethod));
        return settings;
    }

    private String createName(final PsiClass currentClass, final PsiMethod currentMethod) {
        return currentMethod == null ? shortName(currentClass) : currentMethod.getName();
    }
}
