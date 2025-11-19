package com.simula.extension.ConfigurationType;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.simula.extension.RunConfigurationExtension.SimulaRunConfiguration;
import org.jetbrains.annotations.NotNull;

public class SimulaConfigurationFactory_draft1 extends ConfigurationFactory {
    protected SimulaConfigurationFactory_draft1(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(Project project) {
        return new SimulaRunConfiguration(project, this, "Simula Run Configuration");
    }

    @Override
    public String getName() {
        return "Simula Configuration Factory";
    }
}