package com.simula.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Util {
    static Util INSTANCE = new Util();

    public static Icon getSimulaIcon() {
        String iconName = "favico.png";
///        String iconName = "images/sim.png";
//        String iconName = "images/sim2.png";
//        String iconName = "images/simula.png;
        return getSimulaIcon(iconName);
    }
    public static Icon getSimulaIcon(String iconName) {
        // Assuming your image is in a folder named 'images' within your resources root
       URL imageUrl = INSTANCE.getClass().getClassLoader().getResource("images/" + iconName);
        if (imageUrl != null) {
            System.out.println("Util.getSimulaIcon: URL="+imageUrl);
            ImageIcon sIcon = new ImageIcon(imageUrl);
            // Now you can use 'myIcon' with a JLabel, JButton, or other Swing components
            // For example: JLabel label = new JLabel(myIcon);
            return sIcon;
        } else {
            throw new RuntimeException("Util.getSimulaIcon: Image not found: " + iconName);
        }
    }


    // ***************************************************************
    // *** askRunSimula
    // ***************************************************************
    public static void askRunSimula(String fileName) {
        String title = "TITLE";
        String msg = "Source File: " + fileName;
        msg +="\n\nDo you want to start Simula Compiling now ?\n\n";
        int answer = Util.optionDialog(msg,title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, "Start Simula", "Exit");

//        if(DEBUG)
//            IO.println("SimulaExtractor.extract: answer="+answer); // TODO: MYH
        if(answer==0) {
            System.out.println("SimulaCompiler.askRunSimula: DO RUN SIMULA");
//            new Thread() {
//                public void run() {	startJar(simulaJarFileName); }
//            }.start();
        }
    }

    public static void warning(String s) {
    }

    public static void TRACE(String s) {
    }

    public static void error(String s) {
    }

    public static void ASSERT(boolean digit, String s) {
    }

    public static boolean equals(String name, String end) {
        return false;
    }

    public static void IERR() {
    }


    /// Brings up an option dialog.
    /// @param msg the message to display
    /// @param title the title string for the dialog
    /// @param optionType an integer designating the options available on the dialog
    /// @param messageType an integer designating the kind of message this is
    /// @param option an array of objects indicating the possible choices the user can make
    /// @return an integer indicating the option chosen by the user, or CLOSED_OPTION if the user closed the dialog
    public static int optionDialog(final Object msg, final String title, final int optionType, final int messageType, final String... option) {
        Object OptionPaneBackground = UIManager.get("OptionPane.background");
        Object PanelBackground = UIManager.get("Panel.background");
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        int answer = JOptionPane.showOptionDialog(null, msg, title, optionType, messageType,
                Util.getSimulaIcon("sim.png"), option, option[0]);
        // IO.println("doClose.saveDialog: answer="+answer);
        UIManager.put("OptionPane.background", OptionPaneBackground);
        UIManager.put("Panel.background", PanelBackground);
        return (answer);
    }

}
