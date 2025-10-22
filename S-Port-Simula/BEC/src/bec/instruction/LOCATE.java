package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_ADD;

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
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_AADDR); CTStack.checkSosValue();
		CTStack.checkSosType2(Type.T_OADDR,Type.T_GADDR);
		CTStack.pop();
		
		if(CTStack.TOS().type == Type.T_GADDR)
			Global.PSEG.emit(new SVM_ADD(), "LOCATE: ");
		
		CTStack.pop(); CTStack.pushTempVAL(Type.T_GADDR, 1);
	}

}
