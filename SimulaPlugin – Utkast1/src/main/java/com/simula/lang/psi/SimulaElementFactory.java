package com.simula.lang.psi;

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.simula.lang.SimulaFileType;

public class SimulaElementFactory {

    public static SimulaProperty createProperty(Project project, String name) {
        final SimulaFile file = createFile(project, name);
        return (SimulaProperty) file.getFirstChild();
    }

    public static SimulaFile createFile(Project project, String text) {
        String name = "dummy.simple";
        return (SimulaFile) PsiFileFactory.getInstance(project).createFileFromText(name, SimulaFileType.INSTANCE, text);
    }

    public static SimulaProperty createProperty(Project project, String name, String value) {
        final SimulaFile file = createFile(project, name + " = " + value);
        return (SimulaProperty) file.getFirstChild();
    }

    public static PsiElement createCRLF(Project project) {
        final SimulaFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

}