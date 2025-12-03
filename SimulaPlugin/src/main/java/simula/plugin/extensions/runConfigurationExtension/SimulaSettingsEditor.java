package simula.plugin.extensions.runConfigurationExtension;

import com.intellij.openapi.options.SettingsEditor;
import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import simula.plugin.util.RTOption;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimulaSettingsEditor extends SettingsEditor<SimulaRunConfiguration> {
    private final JPanel myPanel;
//    private final JTextField scriptPathField;

    public SimulaSettingsEditor(SimulaRunConfiguration settings) {
        myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
//       myPanel.add(new JLabel("Edit: simula/plugin/extensions/runConfigurationExtension/SimulaSettingsEditor.java"));

//        JButton modeButton = new JButton("Set Compiler Mode");
//        modeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("SimulaSettingsEditor'Button clicked!");
//               settings.simOptions.selectModeOption();
//            }
//        });
//        myPanel.add(modeButton);
//
//        JButton ctButton = new JButton("Set Compiler Options");
//        ctButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("SimulaSettingsEditor'Button clicked!");
//                settings.simOptions.selectCTOptions();
//            }
//        });
//        myPanel.add(ctButton);

        myPanel.add(settings.simOptions.selectOptionsPanel());
//        myPanel.add(new JLabel("Compiler Options:"));
//        myPanel.add(checkBox("CaseSensitive","Source file is case sensitive."));
//        myPanel.add(checkBox("Verbose","Output messages about what the compiler is doing"));
//        myPanel.add(checkBox("Warnings","Generate warning messages"));
//        myPanel.add(checkBox("Extensions","Disable all language extensions. In other words, follow the Simula Standard literally"));
//        myPanel.add(checkBox("noExecution","Don't execute generated .jar file"));
//
//        myPanel.add(new JLabel("Runtime Options:"));
////       myPanel.add(settings.simOptions.selectRTOptions());
//        myPanel.add(checkBox("Verbose", "Output messages about what happens at runtime"));
//        myPanel.add(checkBox("BLOCK_TRACING", "Trace begin end of all blocks"));
//        myPanel.add(checkBox("GOTO_TRACING", "Trace goto label"));
//        myPanel.add(checkBox("QPS_TRACING", "Trace detach, call and resume"));
//        myPanel.add(checkBox("SML_TRACING", "Trace Simulation actions"));

//        myPanel.add(paramPanel());
        //        resetEditorFrom(settings);

    }

    /// Editor Utility: Create a checkBox with tooltips.
    /// @param id option id
    /// @param tooltip option's tooltip or null
    /// @return the resulting check box
    private static JCheckBox checkBox(String id,String tooltip) {
//        return checkBox(id, tooltip,Option.getOption(id));
        return checkBox(id, tooltip, getOption(id));
    }

    /// Editor Utility: Create a checkBox with tooltips.
    /// @param id option id.
    /// @param tooltip option's tooltip or null.
    /// @param selected true: this checkBox is selected.
    /// @return the resulting check box.
    private static JCheckBox checkBox(String id, String tooltip, boolean selected) {
        JCheckBox item = new JCheckBox(id);
        item.setBackground(Color.white);
        item.setSelected(selected);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                if(id.equals("viaJavaSource") || id.equals("directClassFiles") || id.equals("simulaClassLoader")) {
//                    if(Option.verbose) Util.println("Compiler Mode: "+id);
//                    Option.setCompilerMode(id);
//                } else {
//                    Option.setOption(id,item.isSelected());
//                }
            }});
        if(tooltip != null) item.setToolTipText(tooltip);
        item.addMouseListener(new MouseAdapter() {
            Color color = item.getBackground();
            @Override
            public void mouseEntered(MouseEvent me) {
                color = item.getBackground();
                item.setBackground(Color.lightGray); // change the color to lightGray when mouse over a button
            }
            @Override
            public void mouseExited(MouseEvent me) {
                item.setBackground(color);
            }
        });
        return(item);
    }

    private static boolean getOption(String id) {
        return false;
    }

    private void setOption(String id, boolean on) {

    }

    @Override
    protected void resetEditorFrom(@NotNull SimulaRunConfiguration settings) {
        // Update UI from configuration settings
        // scriptPathField.setText(s.getOptions().getScriptPath());
    }

    @Override
    protected void applyEditorTo(@NotNull SimulaRunConfiguration settings) {
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
