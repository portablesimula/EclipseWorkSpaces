package com.simula.lang.parser;

import com.simula.lang.SimulaLanguage;
import com.simula.lang.lexer.SimulaLexerAdapter;
import com.simula.lang.psi.SimulaFile;
import com.simula.lang.psi.SimulaTypes;
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

    TokenSet WHITE_SPACES = TokenSet.create(SimulaTypes.WHITE_SPACES);
    TokenSet COMMENTS = TokenSet.create(SimulaTypes.COMMENT);
    TokenSet LITERALS = TokenSet.create(SimulaTypes.STRING_LITERAL); //PrqlTypes.STRING_LITERAL,  PrqlTypes.RAW_LITERAL, PrqlTypes.CHAR_LITERAL, PrqlTypes.INDENTED_STRING)
   IFileElementType FILE = new IFileElementType(SimulaLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        System.out.println("SimulaParserDefinition.createLexer");
        return new SimulaLexerAdapter();
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
