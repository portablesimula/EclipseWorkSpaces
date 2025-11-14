package com.simula.lang.psi;

// This is a generated file. Not intended for manual editing.

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.simula.lang.psi.impl.SimplePropertyImpl;

public interface SimulaTypes {

    IElementType PROPERTY = new SimulaElementType("PROPERTY");

    IElementType COMMENT = new SimulaTokenType("COMMENT");
    IElementType CRLF = new SimulaTokenType("CRLF");
    IElementType KEY = new SimulaTokenType("KEY");
    IElementType SEPARATOR = new SimulaTokenType("SEPARATOR");
    IElementType VALUE = new SimulaTokenType("VALUE");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == PROPERTY) {
                return new SimplePropertyImpl(node);
            }
            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
