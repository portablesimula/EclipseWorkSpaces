package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_LOAD;

public abstract class FETCH extends Instruction {

	private static final boolean DEBUG = false;

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
		doFetch("FETCH");
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	
	public static void doFetch(String comment) {
		if(CTStack.TOS() instanceof AddressItem addr) {
			if(DEBUG) System.out.println("FETCH.doFetch: addr="+addr);
			Type type = addr.type;
			RTAddress rtAddr = new RTAddress(addr); 
			if(DEBUG) System.out.println("FETCH.doFetch: rtAddr="+rtAddr);
			Global.PSEG.emit(new SVM_LOAD(rtAddr, type.size()), comment + " " +type);
			CTStack.pop(); CTStack.pushTempVAL(type, 1, "GQFetch: ");
			if(DEBUG) {
				CTStack.dumpStack("GQfetch: "+comment);
				Global.PSEG.dump("GQfetch: "+comment);
//				Util.IERR("NOT IMPL");
			}
		} else {
			Global.PSEG.emit(new SVM_NOOP(), "GQfetch: "+comment);
		}
	}

}
