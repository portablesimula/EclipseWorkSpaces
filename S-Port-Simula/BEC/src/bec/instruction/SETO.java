package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class SETO extends Instruction {
	
	/**
	 * temp_control ::= t-seto
	 * 
	 * t-seto
	 * force TOS value; check TOS type(OADDR);
	 * pop;
	 * 
	 * Code is generated that inserts the value described by TOS into the pointer variable refered by
	 * SAVE-OBJECT and SAVE-INDEX. Note that t-seto does not update SAVE-INDEX.
	 * TOS is popped.
	 */
	public static void ofScode() {
		CTStack.checkTosType(Type.T_OADDR);
		Util.GQfetch("SETO: "); CTStack.pop();
//		%+S                      Qf5(qCALL,0,0,4,X_SETO);
		Global.PSEG.emit(new SVM_NOT_IMPL(), "SETO: ");
//		Util.IERR("NOT IMPL");
	}

}
