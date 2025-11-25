package com.simula.extensions.runConfigurationExtension;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class SimulaConfigurationFactory extends ConfigurationFactory {


    protected SimulaConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(Project project) {
//        throw new RuntimeException("SimulaConfigurationFactory.createTemplateConfiguration: ");
        return new SimulaRunConfiguration(project, (SimulaRunConfigurationType) this.getType());
    }

    @Override
    public String getId() {
//        throw new RuntimeException("SimulaConfigurationFactory.getId: ");
        return "Simula";
    }

    @Override
    public String getName() {
//        throw new RuntimeException("SimulaConfigurationFactory.getName: ");
        return "Simula Configuration Factory";
    }
}