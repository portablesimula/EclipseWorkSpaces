package main;

import lang.lexer.Lexer;
import lang.lexer.SimpleManualLexer;

public class Main {
	static CharSequence buffer;
	static int startOffset;
	static int endOffset;
	static int initialState;

	public static void main(String[] argv) {
		Lexer lexer = new SimpleManualLexer();
		buffer = "abra ca dab";
		endOffset = buffer.length();
		lexer.start(buffer, startOffset, endOffset, initialState);
		
		while(true) {
			
		}
	}
}
