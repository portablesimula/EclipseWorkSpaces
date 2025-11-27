package com.simula.extensions.runConfigurationExtension;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.simula.util.Util;
import org.jetbrains.annotations.NotNull;

public class SimulaRunConfigurationType extends ConfigurationTypeBase {
    private static MyConfigurationFactory myConfigurationFactory;
    private static SimulaRunConfigurationType INSTANCE;

//    public MyConfigurationType() {
//        super("MY_RUN_CONFIGURATION_TYPE_ID", "My Configuration", null, () -> MyIcons.MyIcon);
//        addFactory(new MyConfigurationFactory(this));
//    }

    public SimulaRunConfigurationType() {
        // ID, Name, Description, Icon
        super("Simula",
                "Simula",
                "A custom run configuration type for Simula",
//                AllIcons.General.Information); // Use a relevant icon
                Util.getSimulaIcon()); // Use a relevant icon
//        addFactory(new MyConfigurationFactory(this));
        myConfigurationFactory = new MyConfigurationFactory(this);
        addFactory(myConfigurationFactory);
    }

    public static @NotNull SimulaRunConfigurationType getInstance() {
        if(SimulaRunConfigurationType.INSTANCE == null)
            SimulaRunConfigurationType.INSTANCE = new SimulaRunConfigurationType();
        return SimulaRunConfigurationType.INSTANCE;
    }

    public static ConfigurationFactory myFactory() {
        return SimulaConfigurationFactory.getInstance();
    }

//    public static FactoryBuilderSupport getInstance() {
//        if(true) throw new RuntimeException("SimulaRunConfigurationType.getInstance: FactoryBuilderSupport");
//        return null;
//    }

    public static @NotNull ConfigurationFactory getFactory() {
//        if (true) throw new RuntimeException("MyRunConfigurationType_saved.getFactory: FactoryBuilderSupport");
        System.out.println("SimulaRunConfigurationType.getFactory: ");
//        ConfigurationFactory[] factories = INSTANCE.getFactories();
//        return factories[0];
        return myFactory();
    }

//    public static @NotNull ConfigurationFactory getFactory() {
//        if(true) throw new RuntimeException("SimulaRunConfigurationType.getFactory: FactoryBuilderSupport");
//        System.out.println("SimulaRunConfigurationType.getFactory: FactoryBuilderSupport");
//
////        Retrieves an enumeration of factory classes/object specified by a property. The property is gotten from the environment and the provider resource file associated with the given context and concatenated. See getProperty(). The resulting property value is a list of class names.
////        This method then loads each class using the current thread's context class loader and keeps them in a list. Any class that cannot be loaded is ignored. The resulting list is then cached in a two-level hash table, keyed first by the context class loader and then by the property's value. The next time threads of the same context class loader call this method, they can use the cached list.
////                After obtaining the list either from the cache or by creating one from the property value, this method then creates and returns a FactoryEnumeration using the list. As the FactoryEnumeration is traversed, the cached Class object in the list is instantiated and replaced by an instance of the factory object itself. Both class objects and factories are wrapped in weak references so as not to prevent GC of the class loader.
////        Note that multiple threads can be accessing the same cached list via FactoryEnumeration, which locks the list during each next(). The size of the list will not change, but a cached Class object might be replaced by an instantiated factory object.
////        Params:
////        propName – The non-null property name
////        env – The possibly null environment properties
////        ctx – The possibly null context
////        Returns:
////        An enumeration of factory classes/objects; null if none.
////                Throws:
////        NamingException – If encounter problem while reading the provider property file.
////        Object xxx = getFactories();
//
//        return myConfigurationFactory;
//    }

    // Inner class for the factory
    private static class MyConfigurationFactory extends ConfigurationFactory {
        protected MyConfigurationFactory(ConfigurationType type) {
            super(type);
//            if(true) throw new RuntimeException("NEW SimulaRunConfigurationType: ");
            System.out.println("NEW SimulaRunConfigurationType: ");
        }

        @Override
        public String getId(){
//            if(true) throw new RuntimeException("SimulaRunConfigurationType.getId: ");
            System.out.println("SimulaRunConfigurationType.getId: -> Simula");
            Thread.dumpStack();
            return("Simula");
        }

        @NotNull
        @Override
        public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            System.out.println("SimulaRunConfigurationType.createTemplateConfiguration: ");
//            if(true) throw new RuntimeException("SimulaRunConfigurationType.createTemplateConfiguration: ");
            return new SimulaRunConfiguration(project, this, "My Run Config");
        }
    }
}
