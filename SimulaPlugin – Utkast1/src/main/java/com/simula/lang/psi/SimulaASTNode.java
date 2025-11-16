package com.simula.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Vector;

public class SimulaASTNode implements ASTNode {
    ASTNode parent;
    Vector<ASTNode> children;
    int current;

    IElementType iElementType;
    String text;
    int start;
    int length;

    public SimulaASTNode(IElementType iElementType, String text, int start, int length){
        this.iElementType = iElementType;
        this.text = text;
        this.start = start;
        this.length = length;
    }

    /**
     * Returns the type of this node.
     *
     * @return the element type.
     */
    @Override
    public @NotNull IElementType getElementType() {
        return iElementType;
    }

    /**
     * Returns the text of this node.
     * <p></p>
     * Note: This call requires traversing whole subtree, so it can be expensive for composite nodes, and should be avoided if possible.
     *
     * @return the node text.
     */
    @Override
    public @NotNull String getText() {
        return text;
    }

    /**
     * Returns same text getText() returns but might be more effective eliminating toString() transformation from internal CharSequence representation
     * <p></p>
     * Note: This call requires traversing whole subtree, so it can be expensive for composite nodes, and should be avoided if possible.
     *
     * @return the node text.
     * @see PsiElement#textMatches
     * @see #textContains
     */
    @Override
    public @NotNull CharSequence getChars() {
        return text;
    }

    /**
     * Checks if the specified character is present in the text of this node.
     *
     * @param c the character to search for.
     * @return true if the character is found, false otherwise.
     */
    @Override
    public boolean textContains(char c) {
        return text.contains(""+c);
    }

    /**
     * Returns the starting offset of the node text in the document.
     * <p></p>
     * Note: it works in <i>O(tree_depth)</i> time, which can be slow in deep trees, so invoking this method should be avoided if possible.
     *
     * @return the start offset.
     */
    @Override
    public int getStartOffset() {
        return start;
    }

    /**
     * Returns the length of the node text.
     *
     * @return the text length.
     */
    @Override
    public int getTextLength() {
        return length;
    }

    /**
     * Returns the text range (a combination of starting offset in the document and length) for this node.
     * <p></p>
     * Note: it works in <i>O(tree_depth)</i> time, which can be slow in deep trees, so invoking this method should be avoided if possible.
     *
     * @return the text range.
     */
    @Override
    public TextRange getTextRange() {
        System.out.println("SimulaASTNode.getTextRange: NOT IMPL");
        return null;
    }

    /**
     * Returns the parent of this node in the tree.
     *
     * @return the parent node.
     */
    @Override
    public ASTNode getTreeParent() {
        return parent;
    }

    /**
     * Returns the first child of this node in the tree.
     *
     * @return the first child node.
     */
    @Override
    public ASTNode getFirstChildNode() {
        current = 0;
        return children.firstElement();
    }

    /**
     * Returns the last child of this node in the tree.
     *
     * @return the last child node.
     */
    @Override
    public ASTNode getLastChildNode() {
        current = children.size() - 1;
        return children.lastElement();
    }

    /**
     * Returns the next sibling of this node in the tree.
     *
     * @return the next sibling node.
     */
    @Override
    public ASTNode getTreeNext() {
        current++;
        return children.get(current);
    }

    /**
     * Returns the previous sibling of this node in the tree.
     *
     * @return the previous sibling node.
     */
    @Override
    public ASTNode getTreePrev() {
        current--;
        return children.get(current);
    }

    /**
     * Returns the list of children of the specified node, optionally filtered by the
     * specified token type filter.
     *
     * @param filter the token set used to filter the returned children, or null if
     *               all children should be returned.
     * @return the children array.
     */
    @Override
    public ASTNode @NotNull [] getChildren(@Nullable TokenSet filter) {
        System.out.println("SimulaASTNode.getChildren: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.getChildren: NOT IMPL");
//        return new ASTNode[0];
    }

    /**
     * Adds the specified child node as the last child of this node.
     *
     * @param child the child node to add.
     */
    @Override
    public void addChild(@NotNull ASTNode child) {
        children.add(child);
    }

    /**
     * Adds the specified child node at the specified position in the child list.
     *
     * @param child        the child node to add.
     * @param anchorBefore the node before which the child node is inserted ({@code null} to add a child as a last node).
     */
    @Override
    public void addChild(@NotNull ASTNode child, @Nullable ASTNode anchorBefore) {
        System.out.println("SimulaASTNode.addChild: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.addChild: NOT IMPL");

    }

    /**
     * Add leaf element with specified type and text in the child list.
     *
     * @param leafType     type of leaf element to add.
     * @param leafText     text of added leaf.
     * @param anchorBefore the node before which the child node is inserted.
     */
    @Override
    public void addLeaf(@NotNull IElementType leafType, @NotNull CharSequence leafText, @Nullable ASTNode anchorBefore) {
        System.out.println("SimulaASTNode.addLeaf: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.addLeaf: NOT IMPL");

    }

    /**
     * Removes the specified node from the list of children of this node.
     *
     * @param child the child node to remove.
     */
    @Override
    public void removeChild(@NotNull ASTNode child) {
        children.remove(child);
    }

    /**
     * Removes a range of nodes from the list of children, starting with {@code firstNodeToRemove},
     * up to and not including {@code firstNodeToKeep}.
     *
     * @param firstNodeToRemove the first child node to remove from the tree.
     * @param firstNodeToKeep   the first child node to keep in the tree.
     */
    @Override
    public void removeRange(@NotNull ASTNode firstNodeToRemove, @Nullable ASTNode firstNodeToKeep) {
        System.out.println("SimulaASTNode.removeRange: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.removeRange: NOT IMPL");

    }

    /**
     * Replaces the specified child node with another node.
     *
     * @param oldChild the child node to replace.
     * @param newChild the node to replace with.
     */
    @Override
    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
        System.out.println("SimulaASTNode.replaceChild: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.replaceChild: NOT IMPL");

    }

    /**
     * Replaces all child nodes with the children of the specified node.
     *
     * @param anotherParent the parent node whose children are used for replacement.
     */
    @Override
    public void replaceAllChildrenToChildrenOf(@NotNull ASTNode anotherParent) {
        System.out.println("SimulaASTNode.replaceAllChildrenToChildrenOf: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.replaceAllChildrenToChildrenOf: NOT IMPL");

    }

    /**
     * Adds a range of nodes belonging to the same parent to the list of children of this node,
     * starting with {@code firstChild}, up to and not including {@code firstChildToNotAdd}.
     *
     * @param firstChild         the first node to add.
     * @param firstChildToNotAdd the first child node following firstChild which will not be added to the tree.
     * @param anchorBefore       the node before which the child nodes are inserted.
     */
    @Override
    public void addChildren(@NotNull ASTNode firstChild, @Nullable ASTNode firstChildToNotAdd, @Nullable ASTNode anchorBefore) {
        System.out.println("SimulaASTNode.addChildren: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.addChildren: NOT IMPL");

    }

    /**
     * Creates and returns a deep copy of the AST tree part starting at this node.
     *
     * @return the top node of the copied tree (as an ASTNode object)
     */
    @Override
    public @NotNull Object clone() {
        System.out.println("SimulaASTNode.clone: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.clone: NOT IMPL");
//        return null;
    }

    /**
     * Creates a copy of the entire AST tree containing this node and returns a counterpart
     * of this node in the resulting tree.
     *
     * @return the counterpart of this node in the copied tree.
     */
    @Override
    public ASTNode copyElement() {
        System.out.println("SimulaASTNode.copyElement: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.copyElement: NOT IMPL");
//        return null;
    }

    /**
     * Finds a leaf child node at the specified offset from the start of the text range of this node.
     *
     * @param offset the relative offset for which the child node is requested.
     * @return the child node, or null if none is found.
     */
    @Override
    public @Nullable ASTNode findLeafElementAt(int offset) {
        System.out.println("SimulaASTNode.findLeafElementAt: NOT IMPL");
        throw new RuntimeException("SimulaASTNode.findLeafElementAt: NOT IMPL");
//        return null;
    }

    /**
     * Returns a copyable user data object attached to this node.
     *
     * @param key the key for accessing the user data object.
     * @return the user data object, or null if no such object is found in the current node.
     * @see #putCopyableUserData(Key, Object)
     */
    @Override
    public <T> @Nullable T getCopyableUserData(@NotNull Key<T> key) {
        return null;
    }

    /**
     * Attaches a copyable user data object to this node. Copyable user data objects are copied
     * when the AST tree nodes are copied.
     *
     * @param key   the key for accessing the user data object.
     * @param value the user data object to attach.
     * @see #getCopyableUserData(Key)
     */
    @Override
    public <T> void putCopyableUserData(@NotNull Key<T> key, @Nullable T value) {

    }

    /**
     * Returns the first child of the specified node which has the specified type.
     *
     * @param type the type of the node to return.
     * @return the found node, or null if none was found.
     */
    @Override
    public @Nullable ASTNode findChildByType(@NotNull IElementType type) {
        return null;
    }

    /**
     * Returns the first child after anchor of the specified node which has the specified type.
     *
     * @param type   the type of the node to return.
     * @param anchor to start search from
     * @return the found node, or null if none was found.
     */
    @Override
    public @Nullable ASTNode findChildByType(@NotNull IElementType type, @Nullable ASTNode anchor) {
        return null;
    }

    /**
     * Returns the first child of the specified node which has type from specified set.
     *
     * @param typesSet the token set used to filter the returned children.
     * @return the found node, or null if none was found.
     */
    @Override
    public @Nullable ASTNode findChildByType(@NotNull TokenSet typesSet) {
        return null;
    }

    /**
     * Returns the first child after anchor of the specified node which has type from specified set.
     *
     * @param typesSet the token set used to filter the returned children.
     * @param anchor   to start search from
     * @return the found node, or null if none was found.
     */
    @Override
    public @Nullable ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        return null;
    }

    /**
     * Returns the PSI element for this node.
     *
     * @return the PSI element.
     */
    @Override
    public PsiElement getPsi() {
        return null;
    }

    /**
     * Checks and returns the PSI element for this node.
     *
     * @param clazz expected psi class
     * @return the PSI element.
     */
    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return null;
    }

    /**
     * @param key
     * @return a user data value associated with this object. Doesn't require read action.
     */
    @Override
    public <T> @Nullable T getUserData(@NotNull Key<T> key) {
        return null;
    }

    /**
     * Add a new user data value to this object. Doesn't require write action.
     *
     * @param key
     * @param value
     */
    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
