package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_LOADA;

public abstract class DEREF extends Instruction {

	/**
	 * addressing_instruction ::= deref
	 * 
	 * 
	 * check TOS ref;
	 * TOS.MODE := VAL; TOS.TYPE := GADDR;
	 * TOS is modified to describe the address of the area.
	 * 
	 *     (TOS) ------------------------------------------,
     *                              REF                     |
     *                                                      |
     *                                                      |
     *  The resulting           .==================.        V
     *       TOS ---------------|--> GADDR VALUE --|------->.========,
     *  after deref             '=================='        |        |
     *                                                      |        |
     *                                                      '========'
	 */
	public static void ofScode() {
		CTStack.checkTosRef();
		AddressItem tos = (AddressItem) CTStack.TOS();
		Global.PSEG.emit(new SVM_LOADA(tos.objadr, tos.offset), "DEREF'objadr: ");			
		CTStack.pop();
		CTStack.pushTempVAL(Type.T_GADDR, 2);
		return;
	}

}
