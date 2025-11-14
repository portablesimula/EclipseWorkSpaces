package com.simula.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import java.io.IOException;

public class SimulaLexer implements FlexLexer {
    public SimulaLexer(Object o) {
        System.out.println("NEW SimulaLexer");
    }

    @Override
    public void yybegin(int i) {

    }

    @Override
    public int yystate() {
        return 0;
    }

    @Override
    public int getTokenStart() {
        return 0;
    }

    @Override
    public int getTokenEnd() {
        return 0;
    }

    @Override
    public IElementType advance() throws IOException {
        return null;
    }

    @Override
    public void reset(CharSequence charSequence, int i, int i1, int i2) {

    }
}
