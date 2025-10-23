package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_GOTO;

public abstract class GOTO extends Instruction {
	
	/**
	 * goto_instruction ::= goto
	 * 
	 * goto_statement ::= goto
	 * 
	 * goto
	 * force TOS value; check TOS type(PADDR);
	 * pop; check stack empty;
	 * 
	 * TOS is popped and instructions generated to perform the control transfer.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_PADDR);
		Global.PSEG.emit(new SVM_GOTO());
		CTStack.pop();
		CTStack.checkStackEmpty();
	}

}
