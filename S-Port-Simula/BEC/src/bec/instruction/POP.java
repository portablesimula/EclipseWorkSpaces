package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ProfileItem;
import bec.util.Util;

public abstract class POP extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 * 
	 * Pop off TOS;
	 * This instruction is illegal if TOS is a profile description.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		if(CTStack.TOS instanceof ProfileItem) Util.IERR("Illegal pop of profileItem ");
		CTStack.pop();
//		CTStack.dumpStack("POP: ");
	}

}
