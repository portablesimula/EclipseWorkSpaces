package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.compileTimeStack.CTStackItem.Mode;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
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
			int xReg = 0;
			if(tos instanceof AddressItem addr) {
				CTStack.pop(); CTStack.pushTempVAL(addr.type, 1, "DUP: ");
				rtAddr = addr.objadr.addOffset(addr.offset);
//				xReg = addr.xReg;
			}
			Global.PSEG.emit(new SVM_DUP(rtAddr, xReg, tos.type.size()), "DUP: ");
//			if(Option.TESTING_xREG)
				Util.IERR("NOT IMPL");
		}
	}

}
