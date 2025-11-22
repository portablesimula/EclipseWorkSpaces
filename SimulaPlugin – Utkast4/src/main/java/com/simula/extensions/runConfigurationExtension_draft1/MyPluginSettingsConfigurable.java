package com.simula.extensions.runConfigurationExtension_draft1;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyPluginSettingsConfigurable implements Configurable {

    private MyPluginSettingsComponent mySettingsComponent; // Your custom UI component

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "My Plugin Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new MyPluginSettingsComponent(); // Instantiate your UI
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        // Compare current UI values with stored settings to determine if modified
        // For example: return !mySettingsComponent.getSettingValue().equals(MyPluginSettingsState.getInstance().getSettingValue());
        return false; // Placeholder
    }

    @Override
    public void apply() throws ConfigurationException {
        // Save the settings from the UI to your plugin's state
        // For example: MyPluginSettingsState.getInstance().setSettingValue(mySettingsComponent.getSettingValue());
    }

    @Override
    public void reset() {
        // Load the stored settings and update the UI
        // For example: mySettingsComponent.setSettingValue(MyPluginSettingsState.getInstance().getSettingValue());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}