package com.simula.lang.psi;

// This is a generated file. Not intended for manual editing.

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class SimulaVisitor extends PsiElementVisitor {

    public void visitProperty(@NotNull SimulaProperty o) {
        visitNamedElement(o);
    }

    public void visitNamedElement(@NotNull SimulaNamedElement o) {
        visitPsiElement(o);
    }

    public void visitPsiElement(@NotNull PsiElement o) {
        visitElement(o);
    }

}