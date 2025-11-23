package com.simula.actions.build;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

// In your custom action class
public class SimulaBuildAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        System.out.println("SimulaBuildAction.actionPerformed: Project=" + project);
        if (project == null) {
            if(true)throw new RuntimeException("SimulaBuildAction.actionPerformed: ");
            return;
        }

//        // Example: Trigger a full project build using CompilerManager
//        CompilerManager.getInstance(project).make(null);

        // You might want to add error handling and more specific build logic here
        // based on your plugin's requirements.

        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        System.out.println("Virtual file: " + virtualFile);
        VirtualFile[] virtualFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        System.out.println("Virtual file: " + virtualFiles.length+"  "+virtualFiles);
        for(VirtualFile vf:virtualFiles) {
            System.out.println("Virtual file: " + vf);

        }
        SimulaCompiler.call(e);

        // Assuming you have a 'project' instance
        VirtualFile[] contentSourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
        System.out.println("Content source roots: " + contentSourceRoots.length+"  "+contentSourceRoots);
        for (VirtualFile root : contentSourceRoots) {
            System.out.println("Project source root: " + root.getPath());
            System.out.println("Project source root: " + root.getPath());
            System.out.println("Project source root: " + root.getPath());
            System.out.println("Project source root: " + root.getPath());
            System.out.println("Project source root: " + root.getPath());
        }
        throw new RuntimeException("SimulaBuildAction.actionPerformed: ");
    }
}