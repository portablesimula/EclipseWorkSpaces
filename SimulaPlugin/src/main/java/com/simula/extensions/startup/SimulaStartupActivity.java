package com.simula.extensions.startup;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.startup.StartupActivity;
import com.simula.util.Util;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//public class SimulaStartupActivity implements ProjectActivity {
//    @Override
//    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
//        return null;
//    }
//}

public class SimulaStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        // This code is executed after the project has been opened.
        // Place your startup logic here (e.g., showing a notification, running a check)

//        Util.logActionList("B");
//        Util.logActionList("G");
//        Util.logActionList("N");

//        Util.logActionGroupList();

//        IdeActions
        ActionManager actionManager = ActionManager.getInstance();
//        actionManager.unregisterAction("CompileProject");
        actionManager.unregisterAction("ViewMenu");
//        actionManager.unregisterAction("NavigateMenu");
        actionManager.unregisterAction("CodeMenu");
//        actionManager.unregisterAction("RefactorMenu");
        actionManager.unregisterAction("BuildMenu");
        actionManager.unregisterAction("ToolsMenu");
//        actionManager.unregisterAction("GitMenu");
    }
}
