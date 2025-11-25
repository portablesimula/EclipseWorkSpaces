package com.simula.extensions.runConfigurationExtension.test2;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.simula.util.Util;
import org.jetbrains.annotations.NotNull;

// MyCustomConfigurationType.java
public class TestSimulaConfigurationType extends ConfigurationTypeBase {

    private static TestSimulaRunConfiguration INSTANCE;
//    public static TestSimulaConfigurationType INSTANCE = new TestSimulaConfigurationType();
    TestSimulaConfigurationFactory simulaConfigurationFactory;

    public TestSimulaConfigurationType() {
        super("MY_CUSTOM_RUN_CONFIGURATION_ID", "Simula", "Description", Util.getSimulaIcon());
        simulaConfigurationFactory = new TestSimulaConfigurationFactory(this) {
            @NotNull
            @Override
            public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
//                return new MyCustomRunConfiguration(project, this, "My Custom");
//                TestSimulaConfigurationType.INSTACE = new TestSimulaRunConfiguration(project, this);
                return simulaRunConfiguration(project);
            }
        };
        addFactory(simulaConfigurationFactory);
    }

    private RunConfiguration simulaRunConfiguration(@NotNull Project project) {
        TestSimulaRunConfiguration INSTANCE = new TestSimulaRunConfiguration(project, this);
        TestSimulaConfigurationType.INSTANCE = INSTANCE;
        return INSTANCE;
    }
//    SimulaRunConfigurationType() {
//        super(ID, "Simula", "Simula run configuration type",
//                NotNullLazyValue.createValue(() -> AllIcons.Nodes.Console));
//        simulaConfigurationFactory = new SimulaConfigurationFactory(this);
//        addFactory(simulaConfigurationFactory);
//    }
//
//    private static void setINSTANCE() {
//        if(INSTANCE == null) {
//            INSTANCE = new TestSimulaRunConfiguration(project, this);
//        }
//    }

    public static ConfigurationType getInstance() {
//       throw new RuntimeException("NOT IMPL");
//        return (ConfigurationType)TestSimulaConfigurationType.INSTANCE;
        return new TestSimulaConfigurationType();
    }
}

