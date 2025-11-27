package com.simula.actions.build;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

// In your custom action class
public class SimulaBuildAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        System.out.println("SimulaBuildAction.actionPerformed: Project=" + project);
        if (project == null) {
            if(true)throw new RuntimeException("SimulaBuildAction.actionPerformed: ");
            return;
        }

//        // Example: Trigger a full project build using CompilerManager
//        CompilerManager.getInstance(project).make(null);

        // You might want to add error handling and more specific build logic here
        // based on your plugin's requirements.

//        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
//        System.out.println("Virtual file: " + virtualFile);
//        VirtualFile[] virtualFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
//        System.out.println("Virtual file: " + virtualFiles.length+"  "+virtualFiles);
//        for(VirtualFile vf:virtualFiles) {
//            System.out.println("Virtual file: " + vf);
//        }

        SimulaCompiler.call(event);

        throw new RuntimeException("SimulaBuildAction.actionPerformed: ");
    }

    public void update(AnActionEvent event) {
        // Implement logic to enable/disable or change the presentation of the action
        // This method is called to update the action's state before it's displayed

        // For example, to enable/disable based on context:
        // event.getPresentation().setEnabledAndVisible(someCondition);

        // Example: Change the action's text based on a condition
        // if (someCondition) {
        //     event.getPresentation().setText("Custom Action Text");
        // } else {
        //     event.getPresentation().setText("Default Action Text");
        // }

        if (ActionPlaces.MAIN_MENU.equals(event.getPlace())) {
            event.getPresentation().setText("My Menu item name xxx");
        }
        else if (ActionPlaces.MAIN_TOOLBAR.equals(event.getPlace())) {
            event.getPresentation().setText("My Toolbar item name zzz");
        }
        // Example: Disable the action if no project is open
        boolean projectOpen = event.getProject() != null;
        event.getPresentation().setEnabledAndVisible(projectOpen);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        // For plugins targeting IntelliJ Platform 2022.3 or later,
        // this method must be implemented.
        // Return BGT for background thread updates, or EDT for Event Dispatch Thread.
        return ActionUpdateThread.BGT;
    }
}