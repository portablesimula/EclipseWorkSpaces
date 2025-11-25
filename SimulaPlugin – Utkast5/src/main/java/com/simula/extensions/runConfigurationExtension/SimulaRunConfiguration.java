package com.simula.extensions.runConfigurationExtension;

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
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.util.NlsSafe;
import com.simula.extensions.runConfigurationExtension.editor.SimulaRunConfigurationEditor;
import com.simula.extensions.runConfigurationExtension.run.MyRunProfileState;
import com.simula.extensions.runConfigurationExtension.run.SimulaRunProfileState;
import com.simula.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SimulaRunConfiguration implements RunConfiguration {
    public Project project;
    SimulaRunConfigurationType simulaRunConfigurationType;
    String name;

    public SimulaRunConfigurationSettings settings;

    @Override
    public String toString() {
        return "SimulaRunConfiguration["+name+", project:"+project.getName()+", "+simulaRunConfigurationType+"]";
    }

    public SimulaRunConfiguration(@NotNull Project project, SimulaRunConfigurationType simulaRunConfigurationType) {
        this.project = project;
        this.simulaRunConfigurationType = simulaRunConfigurationType;
    }

    /**
     * Returns the factory that has created the run configuration.
     */
    @Override
    public @Nullable ConfigurationFactory getFactory() {
//        throw new RuntimeException("SimulaRunConfiguration.getFactory: ");
//        return null;
        return simulaRunConfigurationType.simulaConfigurationFactory;
    }

    /**
     * Sets the name of the configuration.
     *
     * @param name
     */
    @Override
    public void setName(@NlsSafe String name) {
//        throw new RuntimeException("SimulaRunConfiguration.setName: ");
        this.name = name;
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

//        val names = EnumMap<PhpTestRunnerSettings.Scope, String>(PhpTestRunnerSettings.Scope::class.java)
//        val editor = this.getConfigurationEditor(names)
//
//        editor.setRunnerOptionsDocumentation("https://pestphp.com/docs/installation")
//        return PestTestRunConfigurationEditor(editor, this)

//        throw new RuntimeException("SimulaRunConfiguration.getConfigurationEditor: ");
//        return null;
        return new SimulaRunConfigurationEditor(this);
    }

    /**
     * Returns the project in which the run configuration exists.
     */
    @Override
    public Project getProject() {
//       throw new RuntimeException("SimulaRunConfiguration.getProject: ");
//        return null;
        return project;
    }

    /**
     * Clones the run configuration.
     *
     * @return a clone of this run configuration.
     */
    @Override
    public RunConfiguration clone() {
//        throw new RuntimeException("SimulaRunConfiguration.clone: ");
//        return null;
        return new SimulaRunConfiguration(project, simulaRunConfigurationType);
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
//        executor.
//        environment.
//          public @NotNull Project getProject()
//          public @NotNull ExecutionTarget getExecutionTarget()
//          public @NotNull RunProfile getRunProfile()


        // Iterate through all content files in the project
        System.out.println("SimulaRunConfiguration.getState: Iterate through all content files in the project");
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(virtualFile -> {
            // Process the virtualFile
            System.out.println(virtualFile.getPath());
            return true; // Continue iteration
        });

//        Util.askRunSimula(virtualFile.getPath());

//        throw new RuntimeException("SimulaRunConfiguration.getState: ");
//        return new SimulaRunProfileState();
        return new MyRunProfileState(environment, this);
    }

    /**
     * @return the name of the run configuration.
     */
    @Override
    public @NlsSafe @NotNull String getName() {
//       throw new RuntimeException("SimulaRunConfiguration.getName: ");
        return this.name;
    }

    /**
     * Returns the icon for the run configuration. This icon is displayed in the tab showing the results of executing the run profile,
     * and for persistent run configuration is also used in the run configuration management UI.
     *
     * @return the icon for the run configuration, or null if the default executor icon should be used.
     */
    @Override
    public @Nullable Icon getIcon() {
//        throw new RuntimeException("SimulaRunConfiguration.getIcon: ");
//        return null;
        return Util.getSimulaIcon();
    }
}
