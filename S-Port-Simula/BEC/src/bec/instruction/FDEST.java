package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;
import bec.virtualMachine.SVM_NOOP;

public abstract class FDEST extends Instruction {
	int destination;

	/**
	 * forward_destination ::= fdest destination:index
	 * 
	 * check stack empty;
	 * 
	 * The destination must have been defined by a fjump or fjumpif instruction, otherwise: error.
	 * The current program point becomes the destination of the jump-instruction and the destination becomes
	 * undefined.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();

//		CTStack.dumpStack();
		ProgramAddress addr = Global.DESTAB[destination];
		Global.DESTAB[destination] = null;
		SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(addr.ofst);
		instr.destination = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "FDEST " + destination);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

}
