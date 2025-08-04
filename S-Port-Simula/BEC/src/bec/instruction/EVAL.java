package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.virtualMachine.SVM_EVAL;

public abstract class EVAL extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * eval_instruction ::= eval
	 * 
	 * NOTE: when S_EVAL: --  Qf1(qEVAL,0) -- REMOVED FOR AD'HOC TEST  - IN OLD BEC for PC-Simula

	 */
	public static void ofScode() {
//		Util.IERR("NOT IMPL");
//		System.out.println("EVAL.ofScode: SJEKK DETTE SEINERE EN GANG");
		CTStackItem tos = CTStack.TOS();

		if(DEBUG) {
			if(tos != null)	System.out.println("EVAL: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			System.out.println("EVAL: " + tos + ", mode="+tos.edMode());
//			Util.IERR("");
		}
		
//		tos.mode = CTStackItem.Mode.VAL;
//		CTStack.forceTosValue();
		if(tos instanceof AddressItem addr) {
			if(addr.xReg > 0) {
				Global.PSEG.emit(new SVM_EVAL(addr.xReg), "EVAL: ");
				addr.xReg = 0;
			}
		}
		
	}

}
