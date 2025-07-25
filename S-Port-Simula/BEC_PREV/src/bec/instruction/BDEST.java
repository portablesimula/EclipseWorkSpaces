package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;

public abstract class BDEST extends Instruction {

	/**
	 * backward_destination ::= bdest destination:newindex
	 * 
	 * check stack empty;
	 * 
	 * The destination must be undefined, otherwise: error.
	 * The destination is defined to refer to the current program point.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();

		if(BecGlobal.DESTAB[destination] != null) Util.IERR("BJUMP dest. dest == null");

		BecGlobal.DESTAB[destination] = BecGlobal.PSEG.nextAddress();
      	BecGlobal.PSEG.emit(new SVM_NOOP(), "BDEST " + destination);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

}
