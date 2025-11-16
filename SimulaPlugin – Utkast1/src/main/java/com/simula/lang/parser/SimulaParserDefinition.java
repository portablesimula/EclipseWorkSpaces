package com.simula.lang.parser;

import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.impl.source.tree.PsiPlainTextImpl;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.simula.lang.SimulaLanguage;
import com.simula.lang.lexer.SimulaLexerAdapter;
import com.simula.lang.psi.SimulaFile;
import com.simula.lang.psi.SimulaTokenSets;
import com.simula.lang.psi.test.impl.SimulaPlainTextImpl;
import org.intellij.markdown.ast.ASTNodeImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.TRUE_CONDITION;
import static com.intellij.lang.parser.GeneratedParserUtilBase.exit_section_;
import static com.simula.lang.psi.SimulaTypes.*;

final class SimulaParserDefinition implements ParserDefinition, PsiParser {

    public static final IFileElementType FILE = new IFileElementType(SimulaLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        System.out.println("SimulaParserDefinition.createJexer");
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
        System.out.println("SimulaParserDefinition.createParser");
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
        System.out.println("SimulaParserDefinition.createFile");
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
        System.out.println("SimulaParserDefinition.parse: builder="+builder.getClass().getSimpleName()+"  "+builder);
        Project project = builder.getProject();
        System.out.println("SimulaParserDefinition.parse: project="+project);
        System.out.println("SimulaParserDefinition.parse: projectName="+project.getName());
        System.out.println("SimulaParserDefinition.parse: getBasePath="+project.getBasePath());
        System.out.println("SimulaParserDefinition.parse: getProjectFile="+project.getProjectFile());
        System.out.println("SimulaParserDefinition.parse: getProjectFilePath="+project.getProjectFilePath());
        System.out.println("SimulaParserDefinition.parse: getPresentableUrl="+project.getPresentableUrl());
        System.out.println("SimulaParserDefinition.parse: getWorkspaceFile="+project.getWorkspaceFile());
        System.out.println("SimulaParserDefinition.parse: projectName="+project.getName());
        System.out.println("SimulaParserDefinition.parse: projectName="+project.getName());

        System.out.println("SimulaParserDefinition.parse: getOriginalText:\n"+builder.getOriginalText());

        parseLight(root, builder);
        ASTNode tree = builder.getTreeBuilt();

        ASTNode test = testTree();
        dumpAST(test);
        dumpAST(tree);
        return test;

//        return tree;
    }

    public ASTNode testTree() {
        CharSequence fchars = "ABC";
        IFileElementType type = new IFileElementType(Language.findLanguageByID("Simula"));
        ASTNode tree = new FileElement(type, fchars);
        CharSequence chars = "ABC";
        ASTNode text = new SimulaPlainTextImpl(chars);
        return tree;
    }

    public void dumpAST(ASTNode tree) {
        System.out.println("SimulaParserDefinition.dumpAST:"+tree.getClass().getSimpleName()+"  "+tree);
        ASTNode node = tree.getFirstChildNode();
        System.out.println("SimulaParserDefinition.dumpAST:first="+node);
        FileElement elt= (FileElement) tree;
        System.out.println("SimulaParserDefinition.dumpAST:text=\""+elt.getText()+'"');
        System.out.println("SimulaParserDefinition.dumpAST:getElementType="+elt.getElementType().getClass().getSimpleName());

        PsiCommentImpl xxx1;
        PsiPlainTextImpl xxx2;
        PsiWhiteSpaceImpl xxx3;
    }

    public void parseLight(IElementType root, PsiBuilder builder) {
        boolean r;
        builder = adapt_builder_(root, builder, this, null);
        PsiBuilder.Marker m = enter_section_(builder, 0, _COLLAPSE_, null);
        r = parse_root_(root, builder);
        exit_section_(builder, 0, m, root, r, true, TRUE_CONDITION);
    }

    protected boolean parse_root_(IElementType t, PsiBuilder builder) {
        return parse_root_(t, builder, 0);
    }

    static boolean parse_root_(IElementType t, PsiBuilder builder, int l) {
        return simpleFile(builder, l + 1);
    }

    /* ********************************************************** */
    // item_*
    static boolean simpleFile(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "simpleFile")){
           System.out.println("SimulaParserDefinition.simpleFile: l="+l+" return false");
           return false;
        }
        while (true) {
            int c = current_position_(builder);
            System.out.println("SimulaParserDefinition.simpleFile: l="+l+", c="+c);
            if (!item_(builder, l + 1)) break;
            if (!empty_element_parsed_guard_(builder, "simpleFile", c)) break;
        }
        System.out.println("SimulaParserDefinition.simpleFile: l="+l+" return true");
        return true;
    }

    /* ********************************************************** */
    // property|COMMENT|CRLF
    static boolean item_(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "item_")){
            System.out.println("SimulaParserDefinition.item_: l="+l+" return false");
            return false;
        }
        boolean r;
        PsiBuilder.Marker marker = enter_section_(builder);
        System.out.println("SimulaParserDefinition.item_: marker="+marker.getClass().getSimpleName());
        r = property(builder, l + 1);
        if (!r) r = consumeToken(builder, COMMENT);
        if (!r) r = consumeToken(builder, CRLF);
        exit_section_(builder, marker, null, r);
        return r;
    }


    /* ********************************************************** */
    // (KEY? SEPARATOR VALUE?) | KEY
    public static boolean property(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "property")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(builder, l, _NONE_, PROPERTY, "<property>");
        r = property_0(builder, l + 1);
        if (!r) r = consumeToken(builder, KEY);
        exit_section_(builder, l, m, r, false, SimpleParser::recover_property);
        return r;
    }

    // KEY? SEPARATOR VALUE?
    private static boolean property_0(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "property_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(builder);
        r = property_0_0(builder, l + 1);
        r = r && consumeToken(builder, SEPARATOR);
        r = r && property_0_2(builder, l + 1);
        exit_section_(builder, m, null, r);
        return r;
    }

    // KEY?
    private static boolean property_0_0(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "property_0_0")) return false;
        consumeToken(builder, KEY);
        return true;
    }

    // VALUE?
    private static boolean property_0_2(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "property_0_2")) return false;
        consumeToken(builder, VALUE);
        return true;
    }

    /* ********************************************************** */
    // !(KEY|SEPARATOR|COMMENT)
    static boolean recover_property(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "recover_property")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(builder, l, _NOT_);
        r = !recover_property_0(builder, l + 1);
        exit_section_(builder, l, m, r, false, null);
        return r;
    }

    // KEY|SEPARATOR|COMMENT
    private static boolean recover_property_0(PsiBuilder builder, int l) {
        if (!recursion_guard_(builder, l, "recover_property_0")) return false;
        boolean r;
        r = consumeToken(builder, KEY);
        if (!r) r = consumeToken(builder, SEPARATOR);
        if (!r) r = consumeToken(builder, COMMENT);
        return r;
    }

}