package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;

public abstract class INITAREA extends Instruction {
	
	/**
	 * initarea resolved_type
	 * force TOS value; check TOS type(OADDR);
	 * 
	 * TOS.TYPE must be OADDR, otherwise: error.
	 * The argument type is imposed upon the area, and the area is initialised according to the table below.
	 * Only the common part of an instance of a structure will be initialised, ignoring both the prefix and any
	 * alternate part(s). The structure is initialised component by component according to the table below.
	 * 
	 * NOTE: In this implementation  INITAREA == NOOP
	 */
	public static void ofScode() {
		CTStack.checkTosType(Type.T_OADDR);
		Type type = Type.ofScode();
//		CTStack.pop();
		Global.PSEG.emit(new SVM_NOOP(), "INITAREA: ");
//		Util.IERR("NOT IMPL: "+type);
	}

}
