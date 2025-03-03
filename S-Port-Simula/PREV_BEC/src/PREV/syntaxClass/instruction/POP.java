package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ProfileItem;
import bec.util.Util;

public class POP extends PREV_Instruction {
	
	/**
	 * stack_instruction ::= pop
	 * 
	 * Pop off TOS;
	 * This instruction is illegal if TOS is a profile description.
	 */
	public POP() {
		if(CTStack.TOS instanceof ProfileItem) Util.IERR("Illegal pop of profileItem ");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.pop();
	}
	
	public String toString() {
		return "POP";
	}
	

}
