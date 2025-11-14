package com.simula.lang.psi;

import com.intellij.psi.tree.TokenSet;

public interface SimulaTokenSets {

    TokenSet IDENTIFIERS = TokenSet.create(SimulaTypes.KEY);

    TokenSet COMMENTS = TokenSet.create(ElementTypes.comment);

}