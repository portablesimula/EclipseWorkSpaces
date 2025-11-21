package com.simula.extensions.simulaConfigurationType_draft1;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.SimpleConfigurationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SimulaSimpleConfigurationType extends SimpleConfigurationType {
    protected SimulaSimpleConfigurationType(@NotNull String id, @Nls @NotNull String name, @Nls @Nullable String description, @NotNull NotNullLazyValue<Icon> icon) {
        super(id, name, description, icon);
    }

    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     */
    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return null;
    }
}
