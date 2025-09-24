package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.virtualMachine.SVM_EVAL;

public abstract class EVAL extends Instruction {

	private static final boolean DEBUG = false;
	private static final boolean EVAL_IS_DUMMY = true;

	/**
	 * eval_instruction ::= eval
	 * 
	 * NOTE: when S_EVAL: --  Qf1(qEVAL,0) -- REMOVED FOR AD'HOC TEST  - IN OLD BEC for PC-Simula

	 */
	public static void ofScode() {
//		Util.IERR("NOT IMPL");
//		IO.println("EVAL.ofScode: SJEKK DETTE SEINERE EN GANG");
		CTStackItem tos = CTStack.TOS();

		if(DEBUG) {
			if(tos != null)	IO.println("EVAL: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			IO.println("EVAL: " + tos + ", mode="+tos.edMode());
//			Util.IERR("");
		}
		
		if(EVAL_IS_DUMMY) {
			
		} else {
//			tos.mode = CTStackItem.Mode.VAL;
//			CTStack.forceTosValue();
			if(tos instanceof AddressItem addr) {
				if(addr.getIndexReg() > 0) {
					Global.PSEG.emit(new SVM_EVAL(addr.getIndexReg()), "EVAL: ");
					addr.useIndexReg(0);
				}
			}
		}
	}

}
