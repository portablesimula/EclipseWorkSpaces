package com.simula.extensions.runConfigurationExtension_Pest;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.SimpleConfigurationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import com.simula.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PestRunConfigurationType extends SimpleConfigurationType {
    public PestRunConfigurationType() {
       super("Simula", "Simula", "Simula description" ,
               (NotNullLazyValue<Icon>) Util.getSimulaIcon());
    }

    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     */
    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        throw new RuntimeException("PestRunConfigurationProducer.isConfigurationFromContext: ");
//        return new PestRunConfiguration(project, this);
    }
}

//class PestRunConfigurationType private constructor() :
//SimpleConfigurationType(
//        "PestRunConfigurationType",
//        PestBundle.message("FRAMEWORK_NAME"),
//        PestBundle.message("FRAMEWORK_NAME"),
//        NotNullLazyValue.createValue { PestIcons.Config }
//    ),
//DumbAware {
//    override fun createTemplateConfiguration(project: Project): RunConfiguration {
//        return PestRunConfiguration(project, this)
//    }
//
//    companion object {
//        @JvmStatic
//        val instance: PestRunConfigurationType
//        get() = findConfigurationType(PestRunConfigurationType::class.java)
//    }
