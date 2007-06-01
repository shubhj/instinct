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
import static com.googlecode.instinct.integrate.idea.ClassUtil.fullName;
import static com.googlecode.instinct.integrate.idea.ClassUtil.getSpecificationMethod;
import com.googlecode.instinct.internal.util.Suggest;
import com.intellij.execution.LocatableConfigurationType;
import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

@Suggest("Add a check to make sure that Instinct is on the module's classpath, and that it's a version we can use.")
public final class InstinctConfigurationType implements LocatableConfigurationType {
    public static final String PLUGIN_ID = '#' + InstinctConfigurationType.class.getName();
    public static final RunnerAndConfigurationSettingsImpl LOCATION_IS_NOT_IN_A_CONTEXT_CLASS = null;

    public String getDisplayName() {
        return "Instinct";
    }

    public String getConfigurationTypeDescription() {
        return "Instinct Configuration";
    }

    @SuppressWarnings({"HardcodedFileSeparator"})
    public Icon getIcon() {
        return IconLoader.getIcon("/instinct.png");
    }

    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new InstinctConfigurationFactory(this)};
    }

    @NotNull
    public String getComponentName() {
        return PLUGIN_ID;
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @SuppressWarnings({"RawUseOfParameterizedType"})
    public RunnerAndConfigurationSettings createConfigurationByLocation(final Location location) {
        final PsiClass contextClass = ClassUtil.getContextClass(location.getPsiElement());
        if (contextClass == null) {
            return LOCATION_IS_NOT_IN_A_CONTEXT_CLASS;
        } else {
            final RuntimeConfigurationProducer configurationProducer = new InstinctConfigurationProducer(contextClass, this);
            return configurationProducer.createProducer(location, null).getConfiguration();
        }
    }

    public boolean isConfigurationByElement(final RunConfiguration configuration, final Project project, final PsiElement element) {
        return elementIsAContextOrSpecification(element, configuration);
    }

    @Suggest("Is this limiting the spec to be run to only that defined in the run configuration?")
    private boolean elementIsAContextOrSpecification(final PsiElement element, final RunConfiguration configuration) {
        final PsiClass contextClass = ClassUtil.getContextClass(element);
        if (contextClass == null) {
            return false;
        }
        final InstinctRunConfiguration runConfiguration = ((InstinctRunConfiguration) configuration);
        return Comparing.equal(fullName(contextClass), runConfiguration.getContextClassName())
                && Comparing.equal(getSpecificationMethodName(element), runConfiguration.getSpecificationMethodName());
    }

    private String getSpecificationMethodName(final PsiElement element) {
        final PsiMethod method = getSpecificationMethod(element);
        return method == null ? null : method.getName();
    }

    @Suggest("Why is this needed?")
    public static LocatableConfigurationType getInstance() {
        return ApplicationManager.getApplication().getComponent(InstinctConfigurationType.class);
    }
}
