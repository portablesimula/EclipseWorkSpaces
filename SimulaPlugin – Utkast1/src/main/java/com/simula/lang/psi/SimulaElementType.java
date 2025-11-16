package com.simula.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.simula.lang.SimulaLanguage;

public class SimulaElementType extends IElementType {

    public SimulaElementType(@NotNull @NonNls String debugName) {
        super(debugName, SimulaLanguage.INSTANCE);
    }

}