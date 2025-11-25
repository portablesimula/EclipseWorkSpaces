package com.simula.extensions.runConfigurationExtension.saved;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.simula.extensions.runConfigurationExtension.test3.MyConfigurationFactory;
import com.simula.util.Util;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyRunConfigurationType_saved implements ConfigurationType {
//    private ConfigurationFactory myFactory;
    private static MyRunConfigurationType_saved INSTANCE;

    public MyRunConfigurationType_saved() {
//        if(myFactory != null) throw new RuntimeException("NEW MyRunConfigurationType_saved: Illegal use");
////        this.myFactory = new MyConfigurationFactory();
//        this.myFactory = MyConfigurationFactory.getInstance();
    }

    public static @NotNull MyRunConfigurationType_saved getInstance() {
        if(MyRunConfigurationType_saved.INSTANCE == null) MyRunConfigurationType_saved.INSTANCE = new MyRunConfigurationType_saved();
        return MyRunConfigurationType_saved.INSTANCE;
    }

    public static ConfigurationFactory myFactory() {
        return MyConfigurationFactory.getInstance();
    }

    // ... implement other required methods (getDisplayName, getIcon, getId, etc.) ...

//    @Override
    public ConfigurationFactory[] getFactories() {
        return new ConfigurationFactory[]{myFactory()};
    }

    public static @NotNull ConfigurationFactory getFactory() {
//        if (true) throw new RuntimeException("MyRunConfigurationType_saved.getFactory: FactoryBuilderSupport");
        System.out.println("MyRunConfigurationType_saved.getFactory: FactoryBuilderSupport");
//        ConfigurationFactory[] factories = INSTANCE.getFactories();
//        return factories[0];
        return myFactory();
    }

        /**
         * Returns the display name of the configuration type. This is used, for example, to represent the configuration type in the run
         * configurations tree, and also as the name of the action used to create the configuration.
         *
         * @return the display name of the configuration type.
         */
    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Simula";
    }

    /**
     * Returns the description of the configuration type. You may return the same text as the display name of the configuration type.
     *
     * @return the description of the configuration type.
     */
    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getConfigurationTypeDescription() {
        return "Simula run configuration";
    }

    /**
     * Returns the 16x16 icon used to represent the configuration type.
     *
     * @return the icon
     */
    @Override
    public Icon getIcon() {
        return Util.getSimulaIcon();
    }

    /**
     * The ID of the configuration type. Should be camel-cased without dashes, underscores, spaces and quotation marks.
     * The ID is used to store run configuration settings in a project or workspace file and
     * must not change between plugin versions.
     */
    @Override
    public @NotNull @NonNls String getId() {
        return "Simula";
    }

    /**
     * Returns the configuration factories used by this configuration type. Normally, each configuration type provides just a single factory.
     * You can return multiple factories if your configurations can be created in multiple variants (for example, local and remote for an
     * application server).
     *
     * @return the run configuration factories.
     */
    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
//        return new ConfigurationFactory[0];
        return new ConfigurationFactory[]{myFactory()};
    }
//    @Override
//    public ConfigurationFactory[] getFactories() {
//        return new ConfigurationFactory[]{myFactory};
//    }

}