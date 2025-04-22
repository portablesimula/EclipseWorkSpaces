package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_PUSHR;

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
		
		AddressItem tos = (AddressItem) CTStack.TOS;
	    if(! tos.withRemoteBase) {
	    	ObjectAddress objadr = tos.objadr;
	    	if(objadr.segID != null) {
				Global.PSEG.emit(new SVM_PUSHC(objadr), "DEREF'objadr: ");
	    	}
	    }

		if(tos.xReg > 0) {
			Global.PSEG.emit(new SVM_PUSHR(tos.xReg), "DEREF'objReg: ");
			if(tos.offset != 0) {
				Global.PSEG.emit(new SVM_PUSHC(IntegerValue.of(Type.T_INT,tos.offset)), "DEREF'offset: ");
				Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
			}
		} else {
			Global.PSEG.emit(new SVM_PUSHC(IntegerValue.of(Type.T_INT,tos.offset)), "DEREF'offset'1: ");			
		}
		
		CTStack.pop();
		CTStack.pushTemp(Type.T_GADDR, 2, "DEREF: ");
		return;
	}

}
