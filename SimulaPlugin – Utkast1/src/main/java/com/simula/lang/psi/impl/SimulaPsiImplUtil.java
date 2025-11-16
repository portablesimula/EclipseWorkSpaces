package com.simula.lang.psi.impl;

// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.simula.lang.psi.SimulaElementFactory;
import com.simula.lang.psi.SimulaProperty;
import com.simula.lang.psi.SimulaTypes;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SimulaPsiImplUtil {

    public static String getKey(SimulaProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(SimulaTypes.KEY);
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getValue(SimulaProperty element) {
        ASTNode valueNode = element.getNode().findChildByType(SimulaTypes.VALUE);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(SimulaProperty element) {
        return getKey(element);
    }

    public static PsiElement setName(SimulaProperty element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(SimulaTypes.KEY);
        if (keyNode != null) {
            SimulaProperty property = SimulaElementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(SimulaProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(SimulaTypes.KEY);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static ItemPresentation getPresentation(final SimulaProperty element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getKey();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

}