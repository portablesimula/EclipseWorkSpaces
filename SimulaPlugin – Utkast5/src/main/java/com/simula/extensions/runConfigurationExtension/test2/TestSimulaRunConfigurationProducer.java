package com.simula.extensions.runConfigurationExtension.test2;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

// MyCustomRunConfigurationProducer.java
//public class MyCustomRunConfigurationProducer extends LazyRunConfigurationProducer<MyCustomRunConfiguration> {
public class TestSimulaRunConfigurationProducer extends LazyRunConfigurationProducer<TestSimulaRunConfiguration> {

    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
        return TestSimulaConfigurationType.getInstance().getConfigurationFactories()[0];
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull TestSimulaRunConfiguration configuration,
                                                    @NotNull ConfigurationContext context,
                                                    @NotNull Ref<PsiElement> sourceElement) {
        // Example: If context is a Java class, set its fully qualified name as the main class
        System.out.println("========= TestSimulaRunConfigurationProducer.setupConfigurationFromContext'CONFIGURATION ======================================");
        System.out.println("configuration: " + configuration);
        configuration.setName("Simula-Default");
        configuration.dump();

        System.out.println("========= TestSimulaRunConfigurationProducer.setupConfigurationFromContext'SOURCE ======================================");
        System.out.println("sourceElement: " + sourceElement);
        PsiElement elt = sourceElement.get();
        PsiElement cntxt = elt.getContext();
        System.out.println("sourceElement.context: " + cntxt);

        Project project = context.getProject();
        System.out.println("========= TestSimulaRunConfigurationProducer.setupConfigurationFromContext'PROJECT ======================================");
        System.out.println("PresentableUrl: " + project.getPresentableUrl());
        System.out.println("getProjectFile: " + project.getProjectFile());
        System.out.println("getProjectFilePath: " + project.getProjectFilePath());
        System.out.println("getName: " + project.getName());
        System.out.println("getBasePath: " + project.getBasePath());
        System.out.println("getWorkspaceFile: " + project.getWorkspaceFile());
        System.out.println("getLocationHash: " + project.getLocationHash());
        System.out.println("isDefault: " + project.isDefault());
        System.out.println("isInitialized: " + project.isInitialized());
        System.out.println("isOpen: " + project.isOpen());
        System.out.println("getActualComponentManager: " + project.getActualComponentManager());
        System.out.println("Project: " + project);


//        if(true) throw new RuntimeException("TestSimulaRunConfigurationProducer.setupConfigurationFromContext: NOTIMPL");

        //        PsiElement location = context.getPsiLocation();
//        if (location instanceof PsiClass) {
//            PsiClass psiClass = (PsiClass) location;
//            configuration.setMainClassName(psiClass.getQualifiedName());
//            sourceElement.set(psiClass);
//            return true;
//        }
//        return false;
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
    public boolean isConfigurationFromContext(@NotNull TestSimulaRunConfiguration configuration, @NotNull ConfigurationContext context) {
        if(true) throw new RuntimeException("NOT IMPL");
        return false;
    }

//    @Override
////    public boolean isConfigurationFromContext(@NotNull MyCustomRunConfiguration configuration,
//    public boolean isConfigurationFromContext(@NotNull SimulaRunConfiguration configuration,
//                                              @NotNull ConfigurationContext context) {
//        // Example: Check if the configuration's main class matches the current context's class
//        PsiElement location = context.getPsiLocation();
//        if (location instanceof PsiClass) {
//            PsiClass psiClass = (PsiClass) location;
//            return Objects.equals(configuration.getMainClassName(), psiClass.getQualifiedName());
//        }
//        return false;
//    }
}