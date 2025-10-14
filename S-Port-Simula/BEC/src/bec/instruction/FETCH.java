package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_LOAD;

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
		doFetch("FETCH");
	}

	public static void doFetch(String comment) {
		if(CTStack.TOS() instanceof AddressItem addr) {
			Type type = addr.type;
			Global.PSEG.emit(new SVM_LOAD(addr.objadr.addOffset(addr.offset), type.size()), comment + " " +type);				
			CTStack.pop(); CTStack.pushTempVAL(type, 1, "GQFetch: ");
		}
	}

}
