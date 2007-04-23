package com.googlecode.instinct.integrate.idea;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

public final class InstinctConfigurationFactory extends ConfigurationFactory {
    public InstinctConfigurationFactory(final ConfigurationType configurationType) {
        super(configurationType);
    }

    @Override
    public RunConfiguration createTemplateConfiguration(final Project project) {
        return new InstinctRunConfiguration(project, this, "");
    }
}
