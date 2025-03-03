package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_JUMP;
import bec.virtualMachine.SVM_NOOP;

public class FDEST extends PREV_Instruction {
	int destination;
	
	public FDEST() {
		parse();
	}

	/**
	 * forward_destination ::= fdest destination:index
	 * 
	 * check stack empty;
	 * 
	 * The destination must have been defined by a fjump or fjumpif instruction, otherwise: error.
	 * The current program point becomes the destination of the jump-instruction and the destination becomes
	 * undefined.
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	@Override
	public void doCode() {
		CTStack.checkStackEmpty();

//		CTStack.dumpStack();
		MemAddr addr = Global.DESTAB[destination];
		Global.DESTAB[destination] = null;
		SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(addr.ofst);
		instr.destination = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "FDEST " + destination);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}
	
	public String toString() {
		return "FDEST " + destination;
	}
	

}
