package com.simula.util;

import javax.swing.*;
import java.net.URL;

public class Util {
    static Util INSTANCE = new Util();

    public static Icon getSimulaIcon() {
        // Assuming your image is in a folder named 'images' within your resources root
        String iconName = "images/favico.png";
///        String iconName = "images/sim.png";
//        String iconName = "images/sim2.png";
//        String iconName = "images/simula.png;
       URL imageUrl = INSTANCE.getClass().getClassLoader().getResource(iconName);
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
}
