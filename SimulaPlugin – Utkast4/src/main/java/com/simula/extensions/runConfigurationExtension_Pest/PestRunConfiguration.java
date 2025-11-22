package com.simula.extensions.runConfigurationExtension_Pest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PestRunConfiguration implements RunConfiguration {
    public PestRunConfiguration(@NotNull Project project, PestRunConfigurationType pestRunConfigurationType) {
    }

    /**
     * Returns the factory that has created the run configuration.
     */
    @Override
    public @Nullable ConfigurationFactory getFactory() {
        return null;
    }

    /**
     * Sets the name of the configuration.
     *
     * @param name
     */
    @Override
    public void setName(@NlsSafe String name) {

    }

    /**
     * Returns the UI control for editing the run configuration settings.
     * <p>
     * If additional control over validation is required, the object
     * returned from this method may also implement {@link CheckableRunConfigurationEditor}.
     * <p>
     * If the settings it provides need to be displayed in multiple tabs,
     * returned editor should extend {@link SettingsEditorGroup}.
     *
     * @return the settings editor component.
     */
    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return null;
    }

    /**
     * Returns the project in which the run configuration exists.
     */
    @Override
    public Project getProject() {
        return null;
    }

    /**
     * Clones the run configuration.
     *
     * @return a clone of this run configuration.
     */
    @Override
    public RunConfiguration clone() {
        return null;
    }

    /**
     * Prepares for executing a specific instance of the run configuration.
     *
     * @param executor    the execution mode selected by the user (run, debug, profile etc.)
     * @param environment the environment object containing additional settings for executing the configuration.
     * @return the {@link RunProfileState} describing the process which is about to be started, or {@code null}
     * if it's impossible to start the process.
     */
    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return null;
    }

    /**
     * @return the name of the run configuration.
     */
    @Override
    public @NlsSafe @NotNull String getName() {
        return "";
    }

    /**
     * Returns the icon for the run configuration. This icon is displayed in the tab showing the results of executing the run profile,
     * and for persistent run configuration is also used in the run configuration management UI.
     *
     * @return the icon for the run configuration, or null if the default executor icon should be used.
     */
    @Override
    public @Nullable Icon getIcon() {
        return null;
    }
}
