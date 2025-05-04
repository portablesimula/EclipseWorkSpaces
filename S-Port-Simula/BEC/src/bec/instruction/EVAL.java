package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Util;

public abstract class EVAL extends Instruction {

	private static final boolean DEBUG = true;

	/**
	 * eval_instruction ::= eval
	 */
	public static void ofScode() {
//		Util.IERR("NOT IMPL");
		System.out.println("EVAL.ofScode: SJEKK DETTE SEINERE EN GANG");
		CTStackItem tos = CTStack.TOS();

		if(DEBUG) {
			if(tos != null)	System.out.println("EVAL: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			System.out.println("EVAL: " + tos + ", mode="+tos.edMode());
//			Util.IERR("");
		}
		
		tos.mode = CTStackItem.Mode.VAL;
	}

}
