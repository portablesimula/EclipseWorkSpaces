package com.simula.lang.lexer;

import com.intellij.lexer.FlexAdapter;

public class SimulaLexerAdapter extends FlexAdapter {

    public SimulaLexerAdapter() {
//        super(new SimulaLexer(null));
        super(null);
    }

}