package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_JUMP;

public class FJUMP extends PREV_Instruction {
	int destination;
	
	public FJUMP() {
		parse();
	}

	/**
	 * forward_jump ::= fjump destination:newindex
	 * 
	 * check stack empty;
	 * 
	 * The destination must be undefined,otherwise: error.
	 * A jump to the (as yet unknown) program point is generated, and the destination becomes defined.
	 */
	public void parse() {
		destination = Scode.inByte();
//		printTree(2);
	}

	@Override
	public void doCode() {
		CTStack.checkStackEmpty();
		if(Global.DESTAB[destination] != null) Util.IERR("Destination is already defined");
		
//		CTStack.dumpStack();
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
		Global.PSEG.emit(new SVM_JUMP(null), ""+this);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FJUMP " + destination;
	}
	

}
