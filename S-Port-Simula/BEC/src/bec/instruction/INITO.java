package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class INITO extends Instruction {
	
	/**
	 * temp_control ::= t-inito
	 * 
	 * t-inito
	 * force TOS value; check TOS type(OADDR);
	 * pop;
	 * 
	 * Code is generated to initialise a scan of the save-object described by TOS,
	 * i.e. SAVE-OBJECT is set to refer to the object, and SAVE-INDEX is initialized.
	 * TOS is popped.
	 */
	public static void ofScode() {
		CTStack.checkTosType(Type.T_OADDR);
		Util.GQfetch("INITO: ");
		CTStack.pop();
//		%+S                      Qf5(qCALL,0,0,4,X_INITO);
		Global.PSEG.emit(new SVM_NOT_IMPL(), "INITO: ");
//		Util.IERR("NOT IMPL");
	}

}
