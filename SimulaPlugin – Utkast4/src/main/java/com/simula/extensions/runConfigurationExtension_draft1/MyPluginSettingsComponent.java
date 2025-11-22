package com.simula.extensions.runConfigurationExtension_draft1;

import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyPluginSettingsComponent {
    public @Nullable JComponent getPanel() {

        JPanel panel;
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.add(checkBox("CaseSensitive","Source file is case sensitive."));
        panel.add(checkBox("Verbose","Output messages about what the compiler is doing"));
        panel.add(checkBox("Warnings","Generate warning messages"));
        panel.add(checkBox("Extensions","Disable all language extensions. In other words, follow the Simula Standard literally"));
        panel.add(checkBox("noExecution","Don't execute generated .jar file"));
//        if(Option.internal.DEBUGGING) {
//            panel.add(checkBox("TRACING","Debug option"));
//            panel.add(checkBox("TRACE_SCAN","Debug option"));
//            panel.add(checkBox("TRACE_COMMENTS","Debug option"));
//            panel.add(checkBox("TRACE_PARSE","Debug option"));
//            panel.add(checkBox("TRACE_ATTRIBUTE_OUTPUT","Debug option"));
//            panel.add(checkBox("TRACE_ATTRIBUTE_INPUT","Debug option"));
//            panel.add(checkBox("TRACE_CHECKER","Debug option"));
//            panel.add(checkBox("TRACE_CHECKER_OUTPUT","Debug option"));
//            panel.add(checkBox("TRACE_CODING","Debug option"));
//            panel.add(checkBox("TRACE_BYTECODE_OUTPUT","Debug option"));
//        }
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        Util.optionDialog(panel,"Select Compiler Options",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,"Ok");
//        Global.storeWorkspaceProperties();

        return panel;
    }


    /// Editor Utility: Create a checkBox with tooltips.
    /// @param id option id
    /// @param tooltip option's tooltip or null
    /// @return the resulting check box
    private static JCheckBox checkBox(String id,String tooltip) {
        return checkBox(id, tooltip, false);
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

}
