package com.simula.extensions.runConfigurationExtension.editor;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.components.JBCheckBox;
import com.simula.extensions.runConfigurationExtension.SimulaRunConfiguration;
import com.simula.extensions.runConfigurationExtension.SimulaRunConfigurationSettings;
import com.simula.util.RTOption;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulaRunConfigurationEditor extends SettingsEditor<SimulaRunConfiguration> {

    private JPanel myMainPanel;
    private JPanel coveragePanel;
    private JPanel parallelPanel;
//    private val coverageEngineComboBox = ComboBox(arrayOf(CoverageEngine.XDEBUG, CoverageEngine.PCOV))
    private JBCheckBox enabledParallelTestingCheckBox;

//    public PestTestRunConfigurationEditor(PhpTestRunConfigurationEditor parentEditor, SimulaRunConfiguration settings){
    public SimulaRunConfigurationEditor(SimulaRunConfiguration settings){
//   coveragePanel = UI.PanelFactory.grid().add(
//            UI.PanelFactory.panel(coverageEngineComboBox).withLabel(PestBundle.message("COVERAGE_ENGINE_LABEL_TEXT"))
//    ).createPanel()
//    parallelPanel = UI.PanelFactory.grid().add(
//            UI.PanelFactory.panel(enabledParallelTestingCheckBox).withLabel(PestBundle.message("ENABLE_PARALLEL_TESTING_LABEL_TEXT"))
//    ).createPanel()
//
//    myMainPanel.layout = BoxLayout(myMainPanel, BoxLayout.Y_AXIS)
//    myMainPanel.add(parentEditor.component)
//    myMainPanel.add(coveragePanel)
//    myMainPanel.add(parallelPanel)
//    resetEditorFrom(settings)

        System.out.println("NEW PestTestRunConfigurationEditor: ");
//       if(true) throw new RuntimeException("NEW PestTestRunConfigurationEditor: ");

//        RTOption.selectRuntimeOptions(settings);

//        myMainPanel = new JPanel(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));
        myMainPanel = new JPanel();
        myMainPanel.setLayout(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));
//        myMainPanel = new JPanel(new BorderLayout());

//        myMainPanel.add(parentEditor.component);

//        myMainPanel.add(coveragePanel);
//        myMainPanel.add(parallelPanel);
        myMainPanel.add(new JLabel("Edit: com.simula.extensions.test1.SimulaRunConfigurationEditor"));
        myMainPanel.add(paramPanel());
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
        myMainPanel.add(button);
        //        resetEditorFrom(settings);
    }

    private JPanel paramPanel() {
        JPanel panel = new JPanel();
//        panel.setLayout(new GridBagLayout());
        panel.setLayout(new GridLayout(2, 2));
//        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Working Directory:"), BorderLayout.EAST);
        panel.add(new JTextField("C:\\GitHub\\EclipseWorkSpaces\\JavaHelloWorld"), BorderLayout.CENTER);
        panel.add(RTOption.checkBox("Verbose"));
//        panel.setSize(30, 200);
        return panel;
    }

    private void doReset(SimulaRunConfiguration configuration) {
        System.out.println("PestTestRunConfigurationEditor.doReset: ");
//    val settings = configuration.settings as PestRunConfigurationSettings
//    val runnerSettings = settings.pestRunnerSettings
//
//    coverageEngineComboBox.selectedItem = runnerSettings.coverageEngine
//    enabledParallelTestingCheckBox.isSelected = runnerSettings.parallelTestingEnabled
    }

    @Override
    protected void resetEditorFrom(@NotNull SimulaRunConfiguration settings) {
        System.out.println("PestTestRunConfigurationEditor.resetEditorFrom: " + settings);
        doReset(settings);
//        parentEditor.javaClass.declaredMethods.find { it.name == "resetEditorFrom" }!!.let {
//            it.isAccessible = true
//            it.invoke(parentEditor, settings)
//        }
    }

    @Override
    protected void applyEditorTo(@NotNull SimulaRunConfiguration settings) throws ConfigurationException {
//        if(true) throw new RuntimeException("PestTestRunConfigurationEditor.applyEditorTo: " + settings);

//    parentEditor.javaClass.declaredMethods.find { it.name == "applyEditorTo" }!!.let {
//        it.isAccessible = true
//        try {
//            it.invoke(parentEditor, settings)
//        } catch (exception: InvocationTargetException) {
//            // In case the method throws a read only error (happens in code with me) we ignore it.
//            if (exception.cause is ReadOnlyModificationException) {
//                return@let
//            }
//
//            throw exception
//        }
//    }
        doApply(settings);
    }

    @Override
    protected @NotNull JComponent createEditor() {
//        if(true) throw new RuntimeException("PestTestRunConfigurationEditor.createEditor: ");
        return myMainPanel;
    }

    private void doApply(SimulaRunConfiguration configuration) {
        if(true) throw new RuntimeException("SimulaRunConfigurationEditor.doApply: " + configuration);
       SimulaRunConfigurationSettings settings = configuration.settings;
//    val runnerSettings = settings.pestRunnerSettings
//
//    runnerSettings.coverageEngine = coverageEngineComboBox.selectedItem as CoverageEngine
//    runnerSettings.parallelTestingEnabled = enabledParallelTestingCheckBox.isSelected
    }

}