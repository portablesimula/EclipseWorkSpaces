package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Scode;

public abstract class POPALL extends Instruction {
	
	/**
	 * popall N:byte
	 *  - perform pop n times;
	 *  - check stack empty;
	 *  
	 * Pop N items off the stack. The stack should then be empty, otherwise: error.
	 * This instruction gives a short way of emptying the stack, together with the control of the
	 * number of elements that was on the stack. Profiles cannot be deleted from the stack by pop,
	 * only by deleting the complete stack through popall.
	 */
	public static void ofScode() {
		int n = Scode.inByte();
		for(int i=0;i<n;i++) CTStack.pop();
		CTStack.checkStackEmpty();
	}

}
