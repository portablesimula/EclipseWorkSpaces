package com.simula.extensions.runConfigurationExtension.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.simula.extensions.runConfigurationExtension.SimulaRunConfiguration;
import com.simula.util.Util;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public class MyCommandLineState extends CommandLineState {

    public MyCommandLineState(@NotNull ExecutionEnvironment environment) {
        super(environment);
        System.out.println("NEW MyCommandLineState: ");
        System.out.println("NEW MyCommandLineState: ModulePath: "+environment.getModulePath());
        System.out.println("NEW MyCommandLineState: ModulePath: "+environment.getUserDataString());
        System.out.println("NEW MyCommandLineState: ModulePath: "+environment.isRunningCurrentFile());
        System.out.println("NEW MyCommandLineState: ModulePath: "+environment.isHeadless());
        Project project = environment.getProject();
        Util.printProject("NEW MyCommandLineState: ", project);

        Thread.dumpStack();
//        if(true) throw new RuntimeException("NEW MyCommandLineState: ");
    }

    @Override
    public String toString() {
        ExecutionEnvironment environment = this.getEnvironment();
        return environment.toString();
    }

    @Override
    protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        // 1. Create a GeneralCommandLine object
        System.out.println("MyCommandLineState.startProcess: 1. Create a GeneralCommandLine object");
//        if (true) throw new RuntimeException("MyCommandLineState.startProcess: 1. Create a GeneralCommandLine object");
        String workDirectory = getEnvironment().getProject().getBasePath();
        GeneralCommandLine commandLine = new GeneralCommandLine()
            .withExePath("/path/to/your/executable") // Set the path to your executable
            .withParameters("--my-argument1,", "--my-argument2,") // Add arguments
            .withWorkDirectory(workDirectory) // Set working directory
            .withCharset(Charset.forName("UTF-8")) // Set character set
        ;

        // 2. Wrap it in a ProcessHandler
        // OSProcessHandler is commonly used for standard external processes
        ProcessHandler processHandler = new OSProcessHandler(commandLine);

        // 3. Optional: attach a console view (though CommandLineState usually handles this automatically)
        // The console view will display stdout/stderr
        // consoleView.attachToProcess(processHandler);

        // 4. Return the handler
        return processHandler;
    }

//    @NotNull
//    @Override
    protected ProcessHandler ZZ_startProcess() throws ExecutionException {
        SimulaRunConfiguration configuration = (SimulaRunConfiguration) getEnvironment().getRunProfile();
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
