package com.simula.actions.build;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.simula.util.Util;

import javax.swing.*;

public class SimulaCompiler {

    public static int call(AnActionEvent event) {
        VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        System.out.println("Virtual file: " + virtualFile);
        askRunSimula(virtualFile.getPath());
        return 0;
    }


    // ***************************************************************
    // *** askRunSimula
    // ***************************************************************
    private static void askRunSimula(String fileName) {
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

}
