package com.simula.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.simula.lang.SimulaLanguage;
import com.simula.lang.lexer.SimulaLexerAdapter;
import com.simula.lang.psi.SimulaFile;
import com.simula.lang.psi.SimulaTokenSets;
import org.jetbrains.annotations.NotNull;

final class SimulaParserDefinition implements ParserDefinition, PsiParser {

    public static final IFileElementType FILE = new IFileElementType(SimulaLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new SimulaLexerAdapter();
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return SimulaTokenSets.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiParser createParser(final Project project) {
//      return new SimpleParser();
        return new SimulaParserDefinition();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new SimulaFile(viewProvider);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
//      return SimpleTypes.Factory.createElement(node);
        System.out.println("SimulaParserDefinition.createElement: BEGIN");
//      return SimpleTypes.Factory.createElement(node);
        return null;
    }

    /**
     * ADDED BY MYH FROM INTERFACE: PsiParser
     *
     * Parses the contents of the specified PSI builder and returns an AST tree with the
     * specified type of root element. The PSI builder contents is the entire file
     * or (if chameleon tokens are used) the text of a chameleon token which needs to
     * be reparsed.
     *
     * @param root    the type of the root element in the AST tree.
     * @param builder the builder which is used to retrieve the original file tokens and build the AST tree.
     * @return the root of the resulting AST tree.
     */
    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        System.out.println("SimulaParserDefinition.parse: BEGIN");
        return null;
    }
}