package com.simula.extensions.runConfigurationExtension_Pest;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.util.Ref;
import org.jetbrains.annotations.NotNull;

public class PestRunConfigurationProducer extends RunConfigurationProducer {

    protected PestRunConfigurationProducer() {
//        super(configurationFactory);
        super(true);
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
        throw new RuntimeException("PestRunConfigurationProducer.isConfigurationFromContext: ");
//        return false;
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
        throw new RuntimeException("PestRunConfigurationProducer.isConfigurationFromContext: ");
//       return false;
    }
}
