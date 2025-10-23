package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.virtualMachine.SVM_POPK;

public abstract class DINITAREA extends Instruction {
	
	/**
	 * dinitarea structured_type (dyadic)
	 * force TOS value; check TOS type(INT);
	 * force SOS value; check SOS type(OADDR);
	 * pop;
	 * 
	 * TOS.TYPE must be INT, SOS.TYPE must be OADDR, and the structured type must contain an
	 * indefinite repetition, otherwise: error.
	 * The value of TOS is used to resolve the type, i.e fixing the number of elements in the indefinite
	 * repetition, following that the evaluation proceeds exactly as for initarea.
	 * 
	 * NOTE: In this implementation  DINITAREA == NOOP
	 */
	public static void ofScode() {
		CTStack.forceTosValue();
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_INT);
		Type type = Type.ofScode();
		Scode.expect(Scode.S_FIXREP);
		int fixrep = Scode.inNumber();
		CTStack.pop();
		Global.PSEG.emit(new SVM_POPK(1));
	}

}
