package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
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
		doFetch("FETCH");
	}

	
	public static void doFetch(String comment) {
		if(CTStack.TOS() instanceof AddressItem addr) {
			if(DEBUG) IO.println("FETCH.doFetch: addr="+addr);
			Type type = addr.type;
			Global.PSEG.emit(new SVM_LOAD(addr.objadr.addOffset(addr.offset), addr.xReg, type.size()), comment + " " +type);
			CTStack.pop(); CTStack.pushTempVAL(type, 1, "GQFetch: ");
			if(DEBUG) {
				CTStack.dumpStack("GQfetch: "+comment);
				Global.PSEG.dump("GQfetch: "+comment);
			}
//		} else {
//			Global.PSEG.emit(new SVM_NOOP(), "GQfetch: "+comment);
		}
	}

}
