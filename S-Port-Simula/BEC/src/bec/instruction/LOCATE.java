package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class LOCATE extends Instruction {
	
	/**
	 * addressing_instruction ::= locate
	 * 
	 * locate (dyadic)
	 * force TOS value; check TOS type(AADDR);
	 * force SOS value; check SOS type(OADDR,GADDR);
	 * pop; pop;
	 * push( VAL, GADDR, "value(SOS).BASE, value(SOS).OFFSET++value(TOS)" );
	 * 
	 * SOS and TOS are replaced by a description of the general address value
	 * formed by "addition" of the two original addresses.
	 * 
	 *                               .===========================.
	 *                               |                           |
	 *                               |                           |
	 *                               |                           |
	 *                               |                           |
	 *      (SOS) -------------------|-------->.=============.   |
	 *                               |         |   |         |   |
	 *                               |         |   | (TOS)   |   |
	 *                               |         |   V         |   |
	 *    The resulting              |         |   .=====,   |   |
	 *         TOS ------------------|---------|-->| : : |   |   |
	 *    after locate               |         |   '====='   |   |
	 *                               |         |             |   |
	 *                               |         |             |   |
	 *                               |         '============='   |
	 *                               |                           |
	 *                               |                           |
	 *                               |                           |
	 *                               '==========================='
	 */
	public static void ofScode() {
		CTStack.checkTosType(Type.T_AADDR); CTStack.checkSosValue();
		CTStack.checkSosType2(Type.T_OADDR,Type.T_GADDR);
//		%+E        GetTosValueIn86(qEAX); Pop; GQfetch;
		CTStack.pop();
//		           if TOS.type=T_GADDR
//		           then
//		%+E             Qf1(qPOPR,qEBX,cVAL); Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//		           endif;
//		%+E        Qf1(qPUSHR,qEAX,cVAL);
		Global.PSEG.emit(new SVM_NOT_IMPL(), "LOCATE: ");
		CTStack.pop(); CTStack.pushTemp(Type.T_GADDR, RTRegister.qEAX, 1, "LOCATE: ");
//		Util.IERR("NOT IMPL");
	}

}
