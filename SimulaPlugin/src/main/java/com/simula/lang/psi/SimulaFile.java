package com.simula.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.simula.lang.SimulaFileType;
import com.simula.lang.SimulaLanguage;
import org.jetbrains.annotations.NotNull;

public class SimulaFile extends PsiFileBase {

    public SimulaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, SimulaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return SimulaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Simula File";
    }

}