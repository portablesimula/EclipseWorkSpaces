package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Type;

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
		
		boolean TESTING = true;

		if(TESTING) {
			CTStack.checkTosRef();
//	           adr:=TOS;
//	%+S        if SYSGEN <> 0
//	%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//	%+S             then WARNING("DEREF on parameter") endif;
//	%+S        endif;
			AddressItem.assertAtrStacked();
			CTStack.pop();
			CTStack.pushTempVAL(Type.T_GADDR, 2, "DEREF: ");

		} else {		
//			CTStack.checkTosRef();
//			AddressItem tos = (AddressItem) CTStack.TOS();
//			Global.PSEG.emit(new SVM_PUSHA(tos.objadr, tos.offset, tos.getIndexReg()), "DEREF'objadr: ");
//			CTStack.pop();
//			CTStack.pushTempVAL(Type.T_GADDR, 2, "DEREF: ");
//	
//			if(tos.getIndexReg() != 0) RTRegister.clearReg(tos.getIndexReg());
//			if(tos.objadr instanceof RemoteAddress rem) RTRegister.clearReg(rem.xReg);
//			else if(tos.objadr instanceof ReferAddress rel) RTRegister.clearReg(rel.xReg);
//	
//			CTStack.pop();
//			CTStack.pushTempVAL(Type.T_GADDR, 2, "DEREF: ");
		}
	}

}
