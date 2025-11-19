package com.simula.extension.ConfigurationType;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

//public class SimulaRunConfigurationType implements ConfigurationType {
public class SimulaRunConfigurationType_using_base_NOTUSED extends ConfigurationTypeBase {
    public SimulaRunConfigurationType_using_base_NOTUSED(@NotNull String id,
                                                         @Nls @NotNull String displayName,
                                                         @Nls @Nullable String description,
                                                         @Nullable Icon icon) {
        super(id, displayName, description, icon);
    }
//    protected SimulaRunConfigurationType2(@NotNull String id,
//                                          @Nls @NotNull String displayName,
//                                          @Nls @Nullable String description,
//                                          @Nullable NotNullLazyValue<Icon> icon) {
//        super(id, displayName, description, icon);
//    }
}
