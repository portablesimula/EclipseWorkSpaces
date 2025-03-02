package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_SUB;

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
//		%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//		%+E        GQfetch; Qf1(qPOPR,qEDX,cOBJ); Pop;
//		%+E        GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//		%+E        Qf2(qDYADR,qSUBF,qEAX,cVAL,qEDX); Qf1(qPUSHR,qEAX,cVAL);
//		           pushTemp(T_SIZE);
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_SUB(), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(Type.T_SIZE, RTRegister.qEAX, 1, "DIST: ");
//		Util.IERR("NOT IMPL");
	}

}
