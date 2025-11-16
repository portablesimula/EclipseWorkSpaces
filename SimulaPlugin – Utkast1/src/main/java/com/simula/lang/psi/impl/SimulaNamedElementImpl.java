package com.simula.lang.psi.impl;

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.simula.lang.psi.SimulaNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class SimulaNamedElementImpl extends ASTWrapperPsiElement implements SimulaNamedElement {

    public SimulaNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

}