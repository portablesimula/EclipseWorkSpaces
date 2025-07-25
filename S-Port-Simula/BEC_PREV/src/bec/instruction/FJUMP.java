package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_JUMP;

public abstract class FJUMP extends Instruction {

	/**
	 * forward_jump ::= fjump destination:newindex
	 * 
	 * check stack empty;
	 * 
	 * The destination must be undefined,otherwise: error.
	 * A jump to the (as yet unknown) program point is generated, and the destination becomes defined.
	 */
	public static void ofScode() {
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();
		if(BecGlobal.DESTAB[destination] != null) Util.IERR("Destination is already defined");
		
//		CTStack.dumpStack();
		BecGlobal.DESTAB[destination] = BecGlobal.PSEG.nextAddress();
		BecGlobal.PSEG.emit(new SVM_JUMP(null), "FJUMP: "+destination);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

}
