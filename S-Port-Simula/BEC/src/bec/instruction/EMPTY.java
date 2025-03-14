package bec.instruction;

import bec.compileTimeStack.CTStack;

public abstract class EMPTY extends Instruction {
	
	/**
	 * stack_instruction ::= empty
	 */
	public static void ofScode() {
		CTStack.checkStackEmpty();
	}

}
