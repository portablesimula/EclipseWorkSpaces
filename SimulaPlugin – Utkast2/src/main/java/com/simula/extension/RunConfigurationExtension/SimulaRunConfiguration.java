package com.simula.extension.RunConfigurationExtension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configuration.RunConfigurationExtensionBase;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimulaRunConfiguration extends RunConfigurationExtensionBase {

//    @Override
    protected void updateEditor(@NotNull RunConfiguration configuration, @NotNull SettingsEditor editor) {
        // Add custom UI components to the editor
        // editor.addSettingsEditor(new MyCustomSettingsEditor());
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
    protected void patchCommandLine(@NotNull RunConfigurationBase configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId) throws ExecutionException {

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
        return false;
    }

    /**
     *
     * @param applicableConfiguration Applicable run configuration
     * @param runnerSettings
     * @return True if extension is turned on in configuration extension settings.
     * E.g. RCov is turned on for given run configuration.
     */
    @Override
    public boolean isEnabledFor(@NotNull RunConfigurationBase applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return false;
    }


    // Implement other necessary methods based on your extension's requirements
}