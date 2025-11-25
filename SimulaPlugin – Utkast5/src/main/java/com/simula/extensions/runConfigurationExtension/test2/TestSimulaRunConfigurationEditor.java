package com.simula.extensions.runConfigurationExtension.test2;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.components.JBCheckBox;
import com.simula.util.RTOption;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class TestSimulaRunConfigurationEditor extends SettingsEditor<TestSimulaRunConfiguration> {

    private JPanel myMainPanel;
    private JPanel coveragePanel;
    private JPanel parallelPanel;
//    private val coverageEngineComboBox = ComboBox(arrayOf(CoverageEngine.XDEBUG, CoverageEngine.PCOV))
    private JBCheckBox enabledParallelTestingCheckBox;

//    public PestTestRunConfigurationEditor(PhpTestRunConfigurationEditor parentEditor, TestSimulaRunConfiguration settings){
    public TestSimulaRunConfigurationEditor(TestSimulaRunConfiguration settings){
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

        RTOption.selectRuntimeOptions(settings);

//        myMainPanel = new JPanel(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));
        myMainPanel = new JPanel();
        myMainPanel.setLayout(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));
//        myMainPanel = new JPanel(new BorderLayout());
//        myMainPanel.add(parentEditor.component);
//        myMainPanel.add(coveragePanel);
//        myMainPanel.add(parallelPanel);
        myMainPanel.add(new JLabel("HURRA !"));
//        resetEditorFrom(settings);
    }

    private void doReset(TestSimulaRunConfiguration configuration) {
        System.out.println("PestTestRunConfigurationEditor.doReset: ");
//    val settings = configuration.settings as PestRunConfigurationSettings
//    val runnerSettings = settings.pestRunnerSettings
//
//    coverageEngineComboBox.selectedItem = runnerSettings.coverageEngine
//    enabledParallelTestingCheckBox.isSelected = runnerSettings.parallelTestingEnabled
    }

    @Override
    protected void resetEditorFrom(@NotNull TestSimulaRunConfiguration settings) {
        System.out.println("PestTestRunConfigurationEditor.resetEditorFrom: " + settings);
        doReset(settings);
//        parentEditor.javaClass.declaredMethods.find { it.name == "resetEditorFrom" }!!.let {
//            it.isAccessible = true
//            it.invoke(parentEditor, settings)
//        }
    }

    @Override
    protected void applyEditorTo(@NotNull TestSimulaRunConfiguration settings) throws ConfigurationException {
        if(true) throw new RuntimeException("PestTestRunConfigurationEditor.applyEditorTo: " + settings);
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

    private void doApply(TestSimulaRunConfiguration configuration) {
        if(true) throw new RuntimeException("PestTestRunConfigurationEditor.doApply: " + configuration);
//       SimulaRunConfigurationSettings settings = configuration.settings;

//    val runnerSettings = settings.pestRunnerSettings
//
//    runnerSettings.coverageEngine = coverageEngineComboBox.selectedItem as CoverageEngine
//    runnerSettings.parallelTestingEnabled = enabledParallelTestingCheckBox.isSelected
}


//class PestTestRunConfigurationEditor(
//        private val parentEditor: PhpTestRunConfigurationEditor,
//        settings: PestRunConfiguration
//) : SettingsEditor<PestRunConfiguration>() {
//private val myMainPanel = JPanel()
//private var coveragePanel = JPanel()
//private var parallelPanel = JPanel()
//private val coverageEngineComboBox = ComboBox(arrayOf(CoverageEngine.XDEBUG, CoverageEngine.PCOV))
//private val enabledParallelTestingCheckBox = JBCheckBox()
//
//init {
//    coveragePanel = UI.PanelFactory.grid().add(
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
//}
//
//override fun createEditor(): JComponent {
//    return myMainPanel
//}
//
//private fun doApply(configuration: PestRunConfiguration) {
//    val settings = configuration.settings as PestRunConfigurationSettings
//    val runnerSettings = settings.pestRunnerSettings
//
//    runnerSettings.coverageEngine = coverageEngineComboBox.selectedItem as CoverageEngine
//    runnerSettings.parallelTestingEnabled = enabledParallelTestingCheckBox.isSelected
//}
//
//private fun doReset(configuration: PestRunConfiguration) {
//    val settings = configuration.settings as PestRunConfigurationSettings
//    val runnerSettings = settings.pestRunnerSettings
//
//    coverageEngineComboBox.selectedItem = runnerSettings.coverageEngine
//    enabledParallelTestingCheckBox.isSelected = runnerSettings.parallelTestingEnabled
//}
//
//override fun resetEditorFrom(settings: PestRunConfiguration) {
//    doReset(settings)
//    parentEditor.javaClass.declaredMethods.find { it.name == "resetEditorFrom" }!!.let {
//        it.isAccessible = true
//        it.invoke(parentEditor, settings)
//    }
//}
//
//override fun applyEditorTo(settings: PestRunConfiguration) {
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
//    doApply(settings)
//}
//
//override fun getSnapshot(): PestRunConfiguration {
//    val result = parentEditor.snapshot as PestRunConfiguration
//    doApply(result)
//    return result
//}
}