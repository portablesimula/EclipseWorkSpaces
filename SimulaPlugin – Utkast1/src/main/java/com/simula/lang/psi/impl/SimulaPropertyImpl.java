package com.simula.lang.psi.impl;

// This is a generated file. Not intended for manual editing.

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.simula.lang.psi.SimulaProperty;
import com.simula.lang.psi.SimulaVisitor;
import org.jetbrains.annotations.NotNull;

public class SimulaPropertyImpl extends SimulaNamedElementImpl implements SimulaProperty {

    public SimulaPropertyImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SimulaVisitor visitor) {
        visitor.visitProperty(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SimulaVisitor) accept((SimulaVisitor)visitor);
        else super.accept(visitor);
    }

    @Override
    public String getKey() {
        return SimulaPsiImplUtil.getKey(this);
    }

    @Override
    public String getValue() {
        return SimulaPsiImplUtil.getValue(this);
    }

    @Override
    public String getName() {
        return SimulaPsiImplUtil.getName(this);
    }

    @Override
    public PsiElement setName(@NotNull String newName) {
        return SimulaPsiImplUtil.setName(this, newName);
    }

    @Override
    public PsiElement getNameIdentifier() {
        return SimulaPsiImplUtil.getNameIdentifier(this);
    }

    @Override
    public ItemPresentation getPresentation() {
        return SimulaPsiImplUtil.getPresentation(this);
    }

}