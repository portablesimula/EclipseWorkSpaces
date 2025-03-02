package bec.instruction;

import bec.util.Util;

public abstract class FETCH extends Instruction {
	
	/**
	 * addressing_instruction ::= fetch
	 * 
	 * force TOS value;
	 * 
	 * TOS.MODE should be REF, otherwise fetch has no effect.
	 * TOS is modified to describe the contents of the area previously described.
	 * 
	 *      (TOS) -------------------,
	 *                               |
	 *                               V
	 *      The resulting            .============.
	 *          TOS -----------------|---> VALUE  |
	 *      after fetch              '============'
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		Util.GQfetch("FETCH");
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

}
