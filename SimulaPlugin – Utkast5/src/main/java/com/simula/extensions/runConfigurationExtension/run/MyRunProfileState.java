package com.simula.extensions.runConfigurationExtension.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.simula.extensions.runConfigurationExtension.SimulaRunConfiguration;
import org.jetbrains.annotations.NotNull;

public class MyRunProfileState extends CommandLineState {

    private final SimulaRunConfiguration myConfiguration;

    public MyRunProfileState(@NotNull ExecutionEnvironment environment, SimulaRunConfiguration configuration) {
        super(environment);
        this.myConfiguration = configuration;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        // Configure your command line based on the run configuration settings
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath("path/to/your/executable"); // Replace with your executable
        // Add program arguments, environment variables, working directory etc. based on myConfiguration
        // commandLine.addParameters(myConfiguration.getProgramArguments());
        // commandLine.setWorkDirectory(myConfiguration.getWorkingDirectory());

        System.out.println("MyRunProfileState.startProcess: ");
        if(true) throw new RuntimeException("MyRunProfileState.startProcess: ");

        return new OSProcessHandler(commandLine);
    }


    @Override
    public String toString() {
        return "MyRunProfileState["+this.myConfiguration+"]";
    }

}