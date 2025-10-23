package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class ZEROAREA extends Instruction {
	
	/**
	 * area_initialisation ::= zeroarea
	 * 
	 * zeroarea (dyadic)
	 * force TOS value; check TOS type(OADDR);
	 * force SOS value; check SOS type(OADDR);
	 * pop;
	 * 
	 * TOS and SOS must be OADDR, otherwise error.
	 * The area between SOS and TOS (SOS included, TOS not) is to be zero-filled, and TOS is popped.
	 */
	public static void ofScode() {
		CTStack.forceTosValue(); CTStack.checkTosType(Type.T_OADDR);
		CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_CALL_SYS(SVM_CALL_SYS.P_ZEROAREA));
		CTStack.pop();
	}

}
