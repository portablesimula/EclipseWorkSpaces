package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_DEREF;

public abstract class DEREF extends Instruction {
	
	private static final boolean DEBUG = false;

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
		if(DEBUG) CTStack.dumpStack("DEREF: ");
		CTStack.checkTosRef();
		
		AddressItem tos = (AddressItem) CTStack.TOS();
		Global.PSEG.emit(new SVM_DEREF(tos.objadr, tos.offset, tos.xReg), "DEREF'objadr: ");
					
		CTStack.pop();
		CTStack.pushTempVAL(Type.T_GADDR, 2, "DEREF: ");
		return;
	}

}
