package com.simula.extensions.runConfigurationExtension.run;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ProgramRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimulaRunProfileState_NOT_USED implements RunProfileState {
    /**
     * Starts the process.
     *
     * @param executor the executor used to start up the process.
     * @param runner   the program runner used to start up the process.
     * @return the result (normally an instance of {@link DefaultExecutionResult}), containing a process handler
     * and a console attached to it.
     * @throws ExecutionException if the execution has failed.
     */
    @Override
    public @Nullable ExecutionResult execute(Executor executor, @NotNull ProgramRunner<?> runner) throws ExecutionException {
        System.out.println("SimulaRunProfileState.execute: ");
        System.out.println("SimulaRunProfileState.execute: ");
        System.out.println("SimulaRunProfileState.execute: ");
        System.out.println("SimulaRunProfileState.execute: ");
        System.out.println("SimulaRunProfileState.execute: ");
        System.out.println("SimulaRunProfileState.execute: ");

        if(true)throw new RuntimeException("SimulaRunProfileState.execute: ");

        return null;
    }
}
