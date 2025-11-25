package com.simula.extensions.runConfigurationExtension.test3;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import groovy.util.FactoryBuilderSupport;
import org.jetbrains.annotations.NotNull;

public class MyRunConfigurationType_draft1 extends ConfigurationTypeBase {
    private static MyConfigurationFactory myConfigurationFactory;
    private static MyRunConfigurationType_draft1 INSTANCE;

    public MyRunConfigurationType_draft1() {
        // ID, Name, Description, Icon
        super("Simula",
                "Simula",
                "A custom run configuration type for Simula",
                AllIcons.General.Information); // Use a relevant icon
//        addFactory(new MyConfigurationFactory(this));
        myConfigurationFactory = new MyConfigurationFactory(this);
        addFactory(myConfigurationFactory);
    }

    public static @NotNull MyRunConfigurationType_draft1 getInstance() {
        if(MyRunConfigurationType_draft1.INSTANCE == null)
            MyRunConfigurationType_draft1.INSTANCE = new MyRunConfigurationType_draft1();
        return MyRunConfigurationType_draft1.INSTANCE;
    }

    public static ConfigurationFactory myFactory() {
        return com.simula.extensions.runConfigurationExtension.test3.MyConfigurationFactory.getInstance();
    }

//    public static FactoryBuilderSupport getInstance() {
//        if(true) throw new RuntimeException("MyRunConfigurationType.getInstance: FactoryBuilderSupport");
//        return null;
//    }

    public static @NotNull ConfigurationFactory getFactory() {
//        if (true) throw new RuntimeException("MyRunConfigurationType_saved.getFactory: FactoryBuilderSupport");
        System.out.println("MyRunConfigurationType.getFactory: ");
//        ConfigurationFactory[] factories = INSTANCE.getFactories();
//        return factories[0];
        return myFactory();
    }

//    public static @NotNull ConfigurationFactory getFactory() {
//        if(true) throw new RuntimeException("MyRunConfigurationType.getFactory: FactoryBuilderSupport");
//        System.out.println("MyRunConfigurationType.getFactory: FactoryBuilderSupport");
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
//            if(true) throw new RuntimeException("NEW MyRunConfigurationType: ");
            System.out.println("NEW MyRunConfigurationType: ");
        }

        @Override
        public String getId(){
//            if(true) throw new RuntimeException("MyRunConfigurationType.getId: ");
            System.out.println("MyRunConfigurationType.getId: ");
            return("Simula");
        }

        @NotNull
        @Override
        public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            System.out.println("MyRunConfigurationType.createTemplateConfiguration: ");
//            if(true) throw new RuntimeException("MyRunConfigurationType.createTemplateConfiguration: ");
            return new MyRunConfiguration(project, this, "My Run Config");
        }
    }
}
