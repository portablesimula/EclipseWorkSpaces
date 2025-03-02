package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

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
//		%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//		%+E        GQfetch; Qf1(qPOPR,qECX,cVAL); Pop; GQfetch; Qf1(qPOPR,qEDI,cOBJ);
//		%+E        PreMindMask:=wOR(PreMindMask,uEDI); Qf1(qPUSHR,qEDI,cOBJ);
//		%+E        Qf2(qLOADC,0,qEAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_NOT_IMPL(), "ZEROAREA: ");
		CTStack.pop();
//		Util.IERR("NOT IMPL");
	}

}
