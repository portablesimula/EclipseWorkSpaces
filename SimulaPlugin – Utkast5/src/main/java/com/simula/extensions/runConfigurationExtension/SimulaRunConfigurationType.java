package com.simula.extensions.runConfigurationExtension;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;

public final class SimulaRunConfigurationType extends ConfigurationTypeBase {

    static final String ID = "SimulaRunConfiguration";
    SimulaConfigurationFactory simulaConfigurationFactory;

    SimulaRunConfigurationType() {
        super(ID, "Simula", "Simula run configuration type",
                NotNullLazyValue.createValue(() -> AllIcons.Nodes.Console));
        simulaConfigurationFactory = new SimulaConfigurationFactory(this);
        addFactory(simulaConfigurationFactory);
    }

    @Override
    public String toString() {
        return ID+"[Simula, Simula run configuration type]";
    }
}