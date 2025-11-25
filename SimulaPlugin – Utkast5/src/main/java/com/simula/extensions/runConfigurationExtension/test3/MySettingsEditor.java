package com.simula.extensions.runConfigurationExtension.test3;

import com.intellij.openapi.options.SettingsEditor;
import javax.swing.*;

import com.simula.util.RTOption;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySettingsEditor extends SettingsEditor<MyRunConfiguration> {
    private final JPanel myPanel;
//    private final JTextField scriptPathField;

    public MySettingsEditor(MyRunConfiguration settings) {
//        myPanel = new JPanel();
//        scriptPathField = new JTextField();
//        myPanel.add(new JLabel("Script Path:"));
//        myPanel.add(scriptPathField);

        // ======================================================================

        myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Edit: com.simula.extensions.test1.SimulaRunConfigurationEditor"));
//        myPanel.add(paramPanel());
        JButton button = new JButton("Select more options");
        // Add an ActionListener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
                // You can add more complex logic here, like updating a label, opening a new window, etc.
                RTOption.selectRuntimeOptions(settings);
            }
        });
        myPanel.add(button);
        //        resetEditorFrom(settings);

    }

    @Override
    protected void resetEditorFrom(@NotNull MyRunConfiguration settings) {
        // Update UI from configuration settings
        // scriptPathField.setText(s.getOptions().getScriptPath());
    }

    @Override
    protected void applyEditorTo(@NotNull MyRunConfiguration settings) {
        // Update configuration settings from UI
        // s.getOptions().setScriptPath(scriptPathField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        System.out.println("MySettingsEditor.createEditor: ");
//        if(true) throw new RuntimeException("MySettingsEditor.createEditor: ");
        return myPanel;
    }
}
