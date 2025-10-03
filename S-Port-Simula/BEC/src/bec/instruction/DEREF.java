package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_LOADA;

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
	
//	CheckTosRef;
//  AssertAtrStacked; Pop; pushTemp(T_GADDR);

	public static void ofScode() {
		if(DEBUG) CTStack.dumpStack("DEREF: ");
		CTStack.checkTosRef();
		
		AddressItem tos = (AddressItem) CTStack.TOS();
		if(DEBUG) IO.println("DEREF.ofScode: tos="+tos);
		Global.PSEG.emit(new SVM_LOADA(tos.objadr, tos.offset, tos.indexed), "DEREF'objadr: ");			
		CTStack.pop();
		CTStack.pushTempVAL(Type.T_GADDR, 2, "DEREF: ");
		return;
	}

}
