package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.virtualMachine.SVM_DIST;

public abstract class DIST extends Instruction {
	
	/**
	 * addressing_instruction ::= dist
	 * 
	 * dist (dyadic)
	 * force TOS value; check TOS type(OADDR);
	 * force SOS value; check SOS type(OADDR);
	 * pop; pop;
	 * push( VAL, SIZE, "value(SOS) - value(TOS)" );
	 * 
	 * TOS and SOS are replaced by a description of the signed distance from TOS to SOS.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		BecGlobal.PSEG.emit(new SVM_DIST(), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(Type.T_SIZE, 1, "DIST: ");
	}

}
