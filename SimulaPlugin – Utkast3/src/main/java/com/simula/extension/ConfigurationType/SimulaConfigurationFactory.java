package com.simula.extension.ConfigurationType;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import com.simula.extension.RunConfigurationExtension.SimulaRunConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimulaConfigurationFactory extends ConfigurationFactory {

    protected SimulaConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull String getId() {
        return SimulaRunConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(
            @NotNull Project project) {
        return new SimulaRunConfiguration(project, this, "Demo");
    }

    @Nullable
    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return SimulaRunConfigurationOptions.class;
    }

}