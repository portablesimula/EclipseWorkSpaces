package PREV.syntaxClass.instruction;

import bec.util.Scode;
import bec.util.Util;

public class EMPTY extends PREV_Instruction {
	
	/**
	 * stack_instruction ::= empty
	 */
	public EMPTY() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "EMPTY";
	}
	

}
