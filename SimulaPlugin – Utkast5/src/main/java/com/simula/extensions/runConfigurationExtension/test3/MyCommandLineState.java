package com.simula.extensions.runConfigurationExtension.test3;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

public class MyCommandLineState extends CommandLineState {

    protected MyCommandLineState(@NotNull ExecutionEnvironment environment) {
        super(environment);
        System.out.println("NEW MyCommandLineState: ");
//        if(true) throw new RuntimeException("NEW MyCommandLineState: ");
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        MyRunConfiguration configuration = (MyRunConfiguration) getEnvironment().getRunProfile();
        // Get settings, build the command line

        String cmnd = "java -version";
        System.out.println("MyCommandLineState.startProcess: " + cmnd);
        if(true) throw new RuntimeException("MyCommandLineState.startProcess: " + cmnd);

//        GeneralCommandLine commandLine = new GeneralCommandLine("path/to/your/executable");
//        // commandLine.addParameter(configuration.getOptions().getScriptPath());

        GeneralCommandLine commandLine = new GeneralCommandLine(cmnd);

        System.out.println("MyCommandLineState.startProcess: " + commandLine);

        return new OSProcessHandler(commandLine);
    }
}
