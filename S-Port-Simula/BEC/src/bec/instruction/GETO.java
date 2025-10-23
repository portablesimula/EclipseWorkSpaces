package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_GETO;

public abstract class GETO extends Instruction {
	
	/**
	 * temp_control ::= t-geto
	 * 
	 * t-geto
	 * push( VAL, OADDR, "value of current pointer" );
	 * 
	 * Code is generated, which in case SAVE-INDEX refers to the "last" pointer of the save object
	 * refered by SAVE-OBJECT or no pointer exists in the object, the value onone is returned to
	 * signal that the scan of the object should be terminated. Otherwise SAVE-INDEX is updated to
	 * describe the "next" pointer of the save object. In case the value of the "next" pointer is onone,
	 * the pointer is skipped, i.e. iterate this description, otherwise the value of the refered pointer is
	 * returned.
	 */
	public static void ofScode() {
		CTStack.pushTempVAL(Type.T_OADDR, 1);
		Global.PSEG.emit(new SVM_GETO());
	}

}
