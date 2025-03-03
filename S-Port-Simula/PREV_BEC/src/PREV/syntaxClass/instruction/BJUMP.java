package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_JUMP;

public class BJUMP extends PREV_Instruction {
	int destination;
	
	public BJUMP() {
		parse();
	}

	/**
	 * backward_jump ::= bjump destination:index
	 * 
	 * check stack empty;
	 * 
	 * The destination must have been defined in a bdest instruction, otherwise: error.
	 * A jump to the referenced program point is generated, and the destination becomes undefined.
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void doCode() {
		CTStack.checkStackEmpty();

//		CTStack.dumpStack();
		
		MemAddr addr = Global.DESTAB[destination];
		if(addr == null) Util.IERR("BJUMP dest. dest == null");
		Global.PSEG.emit(new SVM_JUMP(addr), ""+this);
		Global.DESTAB[destination] = null;
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BJUMP " + destination;
	}
	

}
