package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
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
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_DIST(), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(Type.T_SIZE, 1, "DIST: ");
	}

}
