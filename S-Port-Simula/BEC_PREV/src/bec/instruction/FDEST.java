package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;
import bec.virtualMachine.SVM_NOOP;

public abstract class FDEST extends Instruction {
	int destination;
	
	private static final boolean DEBUG = false;

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
		ProgramAddress addr = BecGlobal.DESTAB[destination];
		if(addr == null) Util.IERR("Destination is undefined");
		BecGlobal.DESTAB[destination] = null;
//		SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(addr.ofst);
		SVM_JUMP instr = (SVM_JUMP) BecGlobal.PSEG.instructions.get(addr.getOfst());
		instr.destination = BecGlobal.PSEG.nextAddress();
      	BecGlobal.PSEG.emit(new SVM_NOOP(), "FDEST " + destination);
      	if(DEBUG) {
//      		System.out.println("FDEST.ofScode: FIXUP["+addr.ofst+"]: "+instr);
      		System.out.println("FDEST.ofScode: FIXUP["+addr.getOfst()+"]: "+instr);
			BecGlobal.PSEG.dump("FDEST.ofScode: FIXUP: ");
//			Util.IERR(""+this);
      	}
	}

}
