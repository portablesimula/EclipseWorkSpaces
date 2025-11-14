package com.simula.lang.psi;

// This is a generated file. Not intended for manual editing.

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface SimpleProperty extends SimpleNamedElement {

    String getKey();

    String getValue();

    String getName();

    PsiElement setName(@NotNull String newName);

    PsiElement getNameIdentifier();

    ItemPresentation getPresentation();

}