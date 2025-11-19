package com.simula.extension.RunConfigurationExtension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configuration.RunConfigurationExtensionBase;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import com.simula.extension.ConfigurationType.SimulaConfigurationFactory_draft1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SimulaRunConfiguration_draft1 extends RunConfigurationExtensionBase implements RunConfiguration {

    public SimulaRunConfiguration_draft1(Project project,
                                  SimulaConfigurationFactory_draft1 simulaConfigurationFactory,
                                  String SimulaRunConfiguration) {
    }

    //    @Override
    protected void updateEditor(@NotNull RunConfiguration configuration, @NotNull SettingsEditor editor) {
        // Add custom UI components to the editor
        // editor.addSettingsEditor(new MyCustomSettingsEditor());
        throw new RuntimeException("SimulaRunConfiguration.updateEditor: "+editor);
    }

    /**
     * Patches the command line of the process about to be started by the underlying run configuration.
     *
     * @param configuration  the underlying run configuration.
     * @param runnerSettings the runner-specific settings.
     * @param cmdLine        the command line of the process about to be started.
     * @param runnerId       the ID of the {@link ProgramRunner} used to start the process.
     * @throws ExecutionException if there was an error configuring the command line and the execution should be canceled.
     */
    @Override
    protected void patchCommandLine(@NotNull RunConfigurationBase configuration,
                                    @Nullable RunnerSettings runnerSettings,
                                    @NotNull GeneralCommandLine cmdLine,
                                    @NotNull String runnerId) throws ExecutionException {
        throw new RuntimeException("SimulaRunConfiguration.patchCommandLine: "+cmdLine);

    }
//    @Override
//    protected void patchCommandLine(@NotNull RunConfiguration configuration, @NotNull SimulaParameters simulaParameters,
//                                    ExecutionEnvironment env) {
//        // Modify command line arguments, add environment variables, etc.
//        // javaParameters.addVmParameter("-Dmy.custom.property=value");
//    }

    /**
     * @param configuration Run configuration
     * @return True if extension in general applicable to given run configuration - just to attach settings tab, etc. But extension may be
     * turned off in its settings. E.g. RCov in general available for given run configuration, but may be turned off.
     */
    @Override
    public boolean isApplicableFor(@NotNull RunConfigurationBase configuration) {
       throw new RuntimeException("SimulaRunConfiguration.isApplicableFor: "+configuration);
//      return false;
    }

    /**
     *
     * @param applicableConfiguration Applicable run configuration
     * @param runnerSettings
     * @return True if extension is turned on in configuration extension settings.
     * E.g. RCov is turned on for given run configuration.
     */
    @Override
    public boolean isEnabledFor(@NotNull RunConfigurationBase applicableConfiguration,
                                @Nullable RunnerSettings runnerSettings) {
        throw new RuntimeException("SimulaRunConfiguration.isEnabledFor: "+runnerSettings);
//      return false;
    }

    /**
     * Returns the factory that has created the run configuration.
     */
    @Override
    public @Nullable ConfigurationFactory getFactory() {
        throw new RuntimeException("SimulaRunConfiguration.getFactory: ");
//       return null;
    }

    /**
     * Sets the name of the configuration.
     *
     * @param name
     */
    @Override
    public void setName(@NlsSafe String name) {
        throw new RuntimeException("SimulaRunConfiguration.setName: "+name);

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
        throw new RuntimeException("SimulaRunConfiguration.SettingsEditor: ");
//        return null;
    }

    /**
     * Returns the project in which the run configuration exists.
     */
    @Override
    public Project getProject() {
        throw new RuntimeException("SimulaRunConfiguration.getProject: ");
//        return null;
    }

    /**
     * Clones the run configuration.
     *
     * @return a clone of this run configuration.
     */
    @Override
    public RunConfiguration clone() {
        throw new RuntimeException("SimulaRunConfiguration.clone: ");
//        return null;
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
    public @Nullable RunProfileState getState(@NotNull Executor executor,
                                              @NotNull ExecutionEnvironment environment) throws ExecutionException {
        throw new RuntimeException("SimulaRunConfiguration.getState: ");
//        return null;
    }

    /**
     * @return the name of the run configuration.
     */
    @Override
    public @NlsSafe @NotNull String getName() {
        throw new RuntimeException("SimulaRunConfiguration.getName: ");
//        return "";
    }

    /**
     * Returns the icon for the run configuration. This icon is displayed in the tab showing the results of executing the run profile,
     * and for persistent run configuration is also used in the run configuration management UI.
     *
     * @return the icon for the run configuration, or null if the default executor icon should be used.
     */
    @Override
    public @Nullable Icon getIcon() {
        throw new RuntimeException("SimulaRunConfiguration.getIcon: ");
//        return null;
    }


    // Implement other necessary methods based on your extension's requirements
}