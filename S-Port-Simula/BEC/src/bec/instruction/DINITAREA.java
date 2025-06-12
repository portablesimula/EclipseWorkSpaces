package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;
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
//		Line 116: sCode.Output: LABEL T1186:L2 
//		Line 117: sCode.Output: PUSHC C_INT "4" 
//		Line 116: sCode.Output: PUSHV T1184:r3 
//		Line 117: sCode.Output: DINITAREA T1171:REC2 FIXREP 4 
//		Line 117: sCode.Output: POP 

		CTStack.forceTosValue();
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_INT);
		Type type = Type.ofScode();
		Scode.expect(Scode.S_FIXREP);
		int fixrep = Scode.inNumber();
//		System.out.println("DINITAREA.ofScode: type=" + type + ", instr=" + Scode.edInstr(Scode.curinstr) + ", fixrep="+fixrep);
		CTStack.pop();
//		Global.PSEG.emit(new SVM_NOOP(), "DINITAREA: ");
		Global.PSEG.emit(new SVM_POPK(1), "DINITAREA: ");
//		Util.IERR("NOT IMPL: "+type);
	}

}
