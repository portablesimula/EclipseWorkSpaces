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
        Util.askRunSimula(virtualFile.getPath());
        return 0;
    }


}
