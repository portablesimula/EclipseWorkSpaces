package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;

public abstract class BJUMP extends Instruction {

	/**
	 * backward_jump ::= bjump destination:index
	 * 
	 * check stack empty;
	 * 
	 * The destination must have been defined in a bdest instruction, otherwise: error.
	 * A jump to the referenced program point is generated, and the destination becomes undefined.
	 */
	public static void ofScode() {
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();

//		CTStack.dumpStack();
		
		ProgramAddress addr = BecGlobal.DESTAB[destination];
		if(addr == null) Util.IERR("BJUMP dest. dest == null");
		BecGlobal.PSEG.emit(new SVM_JUMP(addr), "BJUMP: ");
		BecGlobal.DESTAB[destination] = null;
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

}
