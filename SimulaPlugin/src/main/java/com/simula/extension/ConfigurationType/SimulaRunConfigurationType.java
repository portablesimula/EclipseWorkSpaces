package com.simula.extension.ConfigurationType;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;

final class SimulaRunConfigurationType extends ConfigurationTypeBase {

    static final String ID = "DemoRunConfiguration";

    SimulaRunConfigurationType() {
        super(ID, "Demo", "Demo run configuration type",
                NotNullLazyValue.createValue(() -> AllIcons.Nodes.Console));
        addFactory(new SimulaConfigurationFactory(this));
    }

}