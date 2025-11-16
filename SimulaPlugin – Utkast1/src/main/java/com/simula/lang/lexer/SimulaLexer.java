package com.simula.lang.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SimulaLexer extends Lexer {

    CharSequence buffer; // character data for lexing.
    int startOffset;     // offset to start lexing from
    int endOffset;       // offset to stop lexing at
    int state;           // the initial state of the lexer.

    public SimulaLexer() {
        System.out.println("NEW SimulaLexer: ");
//        throw new RuntimeException("NEW SimulaLexer: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
    }

    /**
     * Prepare for lexing character data from {@code buffer} passed. Internal lexer state is supposed to be {@code initialState}. It is guaranteed
     * that the value of initialState is the same as returned by {@link #getState()} method of this lexer at condition {@code startOffset=getTokenStart()}.
     * This method is used to incrementally re-lex changed characters using lexing data acquired from this particular lexer sometime in the past.
     *
     * @param buffer       character data for lexing.
     * @param startOffset  offset to start lexing from
     * @param endOffset    offset to stop lexing at
     * @param initialState the initial state of the lexer.
     */
    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        System.out.println("SimulaLexer.start: start="+startOffset+", end="+endOffset+", state="+initialState+", chars="+buffer);
        this.buffer=buffer;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.state = initialState;
//        throw new RuntimeException("SimulaLexer.start: start="+startOffset+", end="+endOffset+", state="+initialState+", chars=\""+buffer+'"');
    }

    /**
     * Returns the current state of the lexer.
     *
     * @return the lexer state.
     */
    @Override
    public int getState() {
        System.out.println("SimulaLexer.getState: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//        throw new RuntimeException("SimulaLexer.getState: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
        return state;
    }

    /**
     * Returns the token at the current position of the lexer or {@code null} if lexing is finished.
     *
     * @return the current token.
     */
    @Override
    public @Nullable IElementType getTokenType() {
        throw new RuntimeException("SimulaLexer.getTokenType: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//       return null;
    }

    @Override
    public int getTokenStart() {
        System.out.println("SimulaLexer.getTokenStart: ");
        throw new RuntimeException("SimulaLexer.getTokenStart: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//        return startOffset;
    }

    @Override
    public int getTokenEnd() {
        System.out.println("SimulaLexer.getTokenEnd: ");
        throw new RuntimeException("SimulaLexer.getTokenEnd: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//        return endOffset;
    }

    /**
     * Advances the lexer to the next token.
     */
    @Override
    public void advance() {
        throw new RuntimeException("SimulaLexer.ZZ_advance: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');

    }

    /**
     * Returns the current position and state of the lexer.
     *
     * @return the lexer position and state.
     */
    @Override
    public @NotNull LexerPosition getCurrentPosition() {
        throw new RuntimeException("SimulaLexer.getCurrentPosition: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//        return null;
    }

    /**
     * Restores the lexer to the specified state and position.
     *
     * @param position the state and position to restore to.
     */
    @Override
    public void restore(@NotNull LexerPosition position) {
        throw new RuntimeException("SimulaLexer.restore: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');

    }

    /**
     * Returns the buffer sequence over which the lexer is running. This method should return the
     * same buffer instance which was passed to the {@code start()} method.
     *
     * @return the lexer buffer.
     */
    @Override
    public @NotNull CharSequence getBufferSequence() {
        throw new RuntimeException("SimulaLexer.getBufferSequence: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//       return buffer;
    }

    /**
     * Returns the offset at which the lexer will stop lexing. This method should return
     * the length of the buffer or the value passed in the {@code endOffset} parameter
     * to the {@code start()} method.
     *
     * @return the lexing end offset
     */
    @Override
    public int getBufferEnd() {
        throw new RuntimeException("SimulaLexer.getBufferEnd: start="+startOffset+", end="+endOffset+", state="+state+", chars=\""+buffer+'"');
//        return endOffset;
    }

}
