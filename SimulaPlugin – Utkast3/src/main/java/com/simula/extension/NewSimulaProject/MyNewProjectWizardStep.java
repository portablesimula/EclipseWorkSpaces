package com.simula.extension.NewSimulaProject;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.NewProjectWizardBaseStep;
import com.intellij.ide.wizard.NewProjectWizardStep;
import com.intellij.openapi.observable.properties.PropertyGraph;
import com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.annotations.NotNull;

public class MyNewProjectWizardStep  implements NewProjectWizardStep {
    @Override
    public @NotNull WizardContext getContext() {
        NewProjectWizardBaseStep nnn;
        return null;
    }

    @Override
    public @NotNull PropertyGraph getPropertyGraph() {
        return null;
    }

    @Override
    public @NotNull Keywords getKeywords() {
        return null;
    }

    @Override
    public @NotNull UserDataHolder getData() {
        return null;
    }
}
