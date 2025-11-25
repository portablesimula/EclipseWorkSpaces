package com.simula.extensions.runConfigurationExtension;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.util.Ref;
import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiElement;

public class MyFileRunConfigurationProducer extends LazyRunConfigurationProducer<MyRunConfiguration> {

    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
//        if(true) throw new RuntimeException("MyFileRunConfigurationProducer.getConfigurationFactory: ");
        System.out.println("MyFileRunConfigurationProducer.getConfigurationFactory: ");
        // Return the factory defined in your ConfigurationType implementation
//        return MyRunConfigurationType.getInstance().getFactories()[0];
        return SimulaRunConfigurationType.getFactory();
    }

    /**
     * Attempts to create a new run configuration from the current context.
     */
    @Override
    protected boolean setupConfigurationFromContext(
            @NotNull MyRunConfiguration configuration,
            @NotNull ConfigurationContext context,
            @NotNull Ref<PsiElement> sourceElement) {

        System.out.println("MyFileRunConfigurationProducer.setupConfigurationFromContext: ");
//        if(true) throw new RuntimeException("MyFileRunConfigurationProducer.setupConfigurationFromContext: ");

        PsiElement location = context.getPsiLocation();

        // Example: Check if the context points to a specific file (e.g., "myscript.xyz")
        if (location != null && location.getContainingFile() != null &&
                location.getContainingFile().getName().equals("myscript.xyz")) {

            // Set configuration parameters based on the context
            configuration.setName("Simula");
            // configuration.getOptions().setScriptPath(location.getContainingFile().getVirtualFile().getPath());

            // Return true if the configuration was successfully set up
            return true;
        }

        // Return false if this producer cannot handle the current context
        return false;
    }

    public String getId() {
        if(true) throw new RuntimeException("MyFileRunConfigurationProducer.getId: ");
        return "";
    }


    public String getName() {
       if(true) throw new RuntimeException("MyFileRunConfigurationProducer.getName: ");
       return "";
    }

    /**
     * Checks if an existing configuration matches the current context.
     */
    @Override
    public boolean isConfigurationFromContext(
            @NotNull MyRunConfiguration configuration,
            @NotNull ConfigurationContext context) {

        if(true) throw new RuntimeException("MyFileRunConfigurationProducer.isConfigurationFromContext: ");

        PsiElement location = context.getPsiLocation();

        if (location != null && location.getContainingFile() != null &&
                location.getContainingFile().getName().equals("myscript.xyz")) {

            // Compare the existing configuration's settings with the context's expected settings
            // String configPath = configuration.getOptions().getScriptPath();
            // String contextPath = location.getContainingFile().getVirtualFile().getPath();

            // return configPath.equals(contextPath);
            return true; // Simple check for example
        }
        return false;
    }
}
