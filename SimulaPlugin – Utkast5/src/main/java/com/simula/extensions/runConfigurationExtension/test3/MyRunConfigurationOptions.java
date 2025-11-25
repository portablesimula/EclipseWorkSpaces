package com.simula.extensions.runConfigurationExtension.test3;

import com.intellij.execution.configurations.RunConfigurationOptions;

public class MyRunConfigurationOptions extends RunConfigurationOptions {

    public MyRunConfigurationOptions() {
        if(true) throw new RuntimeException("NEW MyRunConfigurationOptions: ");

    }
}
