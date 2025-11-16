package com.simula.lang.psi.test.impl;

import com.intellij.psi.PlainTextTokenTypes;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiPlainText;
import com.intellij.psi.impl.source.tree.OwnBufferLeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.simula.lang.psi.test.SimulaPlainText;
import org.jetbrains.annotations.NotNull;

public class SimulaPlainTextImpl  extends OwnBufferLeafPsiElement implements SimulaPlainText {
    public SimulaPlainTextImpl(@NotNull CharSequence text) {
        super(PlainTextTokenTypes.PLAIN_TEXT, text);
    }

    public SimulaPlainTextImpl(@NotNull IElementType type, @NotNull CharSequence text) {
        super(type, text);
    }

//    @Override
//    public void accept(@NotNull PsiElementVisitor visitor){
//        visitor.visitPlainText(this);
//    }

    @Override
    public String toString(){
        return "SimulaPlainText";
    }
}
