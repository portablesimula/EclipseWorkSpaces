package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.compileTimeStack.CTStackItem.Mode;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_DUP;

public abstract class DUP extends Instruction {
	
	private static final boolean TESTING_DUP = false;
	
	/**
	 * stack_instruction ::= dup
	 * 
	 * push( TOS );
	 * force TOS value;
	 * 
	 * A duplicate of TOS is pushed onto the stack and forced into value mode.
	 */
	public static void ofScode() {
		if(TESTING_DUP) {
			CTStack.dup();
			CTStack.forceTosValue();			
		} else {
			CTStack.dup();
			CTStackItem tos = CTStack.TOS();
			tos.mode = Mode.VAL;
			
			ObjectAddress rtAddr = null;
			if(tos instanceof AddressItem addr) {
				CTStack.pop(); CTStack.pushTempVAL(addr.type, 1, "DUP: ");
				rtAddr = addr.objadr.addOffset(addr.offset);
			}
			Global.PSEG.emit(new SVM_DUP(rtAddr, tos.type.size()), "DUP: ");
		}
	}

}
