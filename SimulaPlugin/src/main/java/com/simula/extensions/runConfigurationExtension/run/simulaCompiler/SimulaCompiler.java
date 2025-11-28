package com.simula.extensions.runConfigurationExtension.run.simulaCompiler;

import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.simula.extensions.runConfigurationExtension.SimulaRunConfiguration;
import com.simula.util.CTOption;
import com.simula.util.RTOption;
import com.simula.util.Util;

import java.util.List;

public class SimulaCompiler {

    public static int call(Project project, CTOption ctOption) {
        System.out.println("SimulaCompiler.call: Project=" + project);
        Util.printProject("SimulaCompiler.call: ", project);

//        VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        VirtualFile virtualFile = project.getProjectFile();
        System.out.println("Virtual file: " + virtualFile);
        Util.askRunSimula(virtualFile.getPath());

        RunManager runManager = RunManager.getInstance(project);
        List<RunnerAndConfigurationSettings> allConfigurations = runManager.getAllSettings();
        // Alternatively, you can use getConfigurationsList()
        // List<RunConfiguration> configurationsList = runManager.getConfigurationsList();

        for (RunnerAndConfigurationSettings settings : allConfigurations) {
            RunConfiguration config = settings.getConfiguration();
            String configName = config.getName();
            String configType = config.getType().getDisplayName();

            System.out.println("SimulaCompiler.call: "+config.getClass().getSimpleName()+" "+configName);
            // Perform actions based on configuration type or name
            if (config instanceof SimulaRunConfiguration conf) {
                RTOption rtOption = conf.rtOption;
                // ...
                Util.log("ZZZZZZZZZZZZZZZZZZZZ");
                rtOption.print("SimulaCompiler.call: Config: " + configName);
            } else if (config instanceof ApplicationConfiguration) {
                // Access specific methods for Application run configurations
                ApplicationConfiguration appConfig = (ApplicationConfiguration) config;
                String mainClass = appConfig.getMainClassName();
                // ...
            }
        }

//        if(true) throw new RuntimeException("SimulaCompiler.call: END");

        return 0;
    }


}
