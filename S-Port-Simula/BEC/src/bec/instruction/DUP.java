package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.compileTimeStack.CTStackItem.Mode;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_DUP;

public abstract class DUP extends Instruction {
	
	/**
	 * stack_instruction ::= dup
	 * 
	 * push( TOS );
	 * force TOS value;
	 * 
	 * A duplicate of TOS is pushed onto the stack and forced into value mode.
	 */
	public static void ofScode() {
		CTStack.dup();
		CTStackItem tos = CTStack.TOS();
		tos.mode = Mode.VAL;
		
		if(tos instanceof AddressItem addr) {
			ObjectAddress oaddr = addr.objadr.addOffset(addr.offset);
			Global.PSEG.emit(new SVM_DUP(sizeOnStack(oaddr)), "DUP: ");
			FETCH.doFetch("DUP: ");
		} else {
			Global.PSEG.emit(new SVM_DUP(tos.type.size()), "DUP: ");
		}
	}


	private static int sizeOnStack(ObjectAddress oaddr) {
//		IO.println("SVM_DUP.dupsizeOnStack: "+oaddr);
		int size =(oaddr.indexed)? 1 : 0;
		switch(oaddr.kind) {
			case ObjectAddress.SEGMNT_ADDR: // Stack: nothing + index(?)
			case ObjectAddress.REL_ADDR:    // Stack: nothing + index(?) 
			case ObjectAddress.STACK_ADDR:  // Stack: nothing + index(?)
				break;
			case ObjectAddress.REMOTE_ADDR: // Stack: oaddr + index(?)
				size++;
				break;
			case ObjectAddress.REFER_ADDR:  // Stack: ofst oaddr + index(?)
				size = size + 2;
				break;
			default: Util.IERR("");
		}
//		IO.println("SVM_DUP.dupsizeOnStack: "+oaddr+", sizeOnStack="+size);
		return size;
	}

}
