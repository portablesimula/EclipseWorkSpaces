package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;

public abstract class EVAL extends Instruction {

	private static final boolean DEBUG = false;

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
		
//		tos.mode = CTStackItem.Mode.VAL;
//		CTStack.forceTosValue();
	}

}
