package com.simula.lang.parser;

import com.simula.lang.psi.ElementTypes;
import com.simula.lang.psi.SimulaFile;
import com.simula.util.Global;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/*
   GIT: https://github.com/PRQL/prql-jetbrains/tree/main/src/main
*/
public class SimulaParser_FROM_Demo implements ParserDefinition {

    TokenSet WHITE_SPACES = TokenSet.create(ElementTypes.whiteSpaces);
    TokenSet COMMENTS = TokenSet.create(ElementTypes.comment);
    TokenSet LITERALS = TokenSet.create(ElementTypes.stringLiteral); //PrqlTypes.STRING_LITERAL,  PrqlTypes.RAW_LITERAL, PrqlTypes.CHAR_LITERAL, PrqlTypes.INDENTED_STRING)
    IFileElementType FILE = new IFileElementType(Global.simulaLanguage);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        System.out.println("SimulaParserDefinition.createLexer");
        return null;
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        System.out.println("SimulaParserDefinition.createParser");
        return null;
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return LITERALS;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode astNode) {
        System.out.println("SimulaParserDefinition.createElement");
        return null;
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        System.out.println("SimulaParserDefinition.createFile");
        return new SimulaFile(viewProvider);
    }
}
