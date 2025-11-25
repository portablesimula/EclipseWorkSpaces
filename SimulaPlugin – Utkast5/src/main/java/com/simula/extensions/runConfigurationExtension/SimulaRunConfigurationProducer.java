package com.simula.extensions.runConfigurationExtension;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.util.Ref;
import org.jetbrains.annotations.NotNull;

public class SimulaRunConfigurationProducer extends RunConfigurationProducer {

    ConfigurationFactory configurationFactory;

    RunConfiguration configuration;
    ConfigurationContext context;
    Ref sourceElement;

    /**
     * param configurationFactory
     * deprecated Use {@link LazyRunConfigurationProducer}.
     */
    protected SimulaRunConfigurationProducer() {
//        super(new SimulaConfigurationFactory(new SimulaRunConfigurationType()));
        super(new SimulaRunConfigurationType());
    }

    /**
     * Sets up a configuration based on the specified context.
     *
     * @param configuration a clone of the template run configuration of the specified type
     * @param context       contains the information about a location in the source code.
     * @param sourceElement a reference to the source element for the run configuration (by default contains the element at caret,
     *                      can be updated by the producer to point to a higher-level element in the tree).
     * @return true if the context is applicable to this run configuration producer, false if the context is not applicable and the
     * configuration should be discarded.
     */
    @Override
    protected boolean setupConfigurationFromContext(@NotNull RunConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref sourceElement) {
//        throw new RuntimeException("SimulaRunConfigurationProducer.setupConfigurationFromContext: ");
//       return false;
        this.configuration = configuration;
        this.context = context;
        this.sourceElement = sourceElement;
        System.out.println("SimulaRunConfigurationProducer.setupConfigurationFromContext: "+configuration.getClass().getSimpleName());
        return true;
    }

    /**
     * Checks if the specified configuration was created from the specified context.
     *
     * @param configuration a configuration instance.
     * @param context       contains the information about a location in the source code.
     * @return true if this configuration was created from the specified context, false otherwise.
     */
    @Override
    public boolean isConfigurationFromContext(@NotNull RunConfiguration configuration, @NotNull ConfigurationContext context) {
        throw new RuntimeException("SimulaRunConfigurationProducer.isConfigurationFromContext: ");
//       return false;
    }
}
