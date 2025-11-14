package com.simula.lang.psi;

import com.simula.util.Global;
import com.intellij.psi.tree.IFileElementType;

public class ElementTypes {
    public static IFileElementType whiteSpaces   = new IFileElementType("WHITE_SPACES", Global.simulaLanguage);
    public static IFileElementType comment       = new IFileElementType("COMMENT",Global.simulaLanguage);
    public static IFileElementType stringLiteral = new IFileElementType("STRING_LITERAL",Global.simulaLanguage);

    public static IFileElementType COMMENT       = new IFileElementType("COMMENT",Global.simulaLanguage);
    public static IFileElementType KEY           = new IFileElementType("KEY",Global.simulaLanguage);

}
