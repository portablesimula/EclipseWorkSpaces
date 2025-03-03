package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_JUMPIF;

public class BJUMPIF extends PREV_Instruction {
	Relation relation;
	int destination;
	
	public BJUMPIF() {
		parse();
	}

	/**
	 * backward_jump ::= bjumpif relation destination:index
	 * 
	 * bjumpif relation destination:index (dyadic)
	 * force TOS value; force SOS value;
	 * check relation;
	 * pop; pop;
	 * 
	 * The destination must be defined by a bdest instruction, and TOS and SOS must be of the same
	 * permissible resolved types with regard to relation, otherwise: error.
	 * A conditional jump sequence will be generated, branching only if the relation evaluates true. The
	 * destination becomes undefined.
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		relation = new Relation();
		destination = Scode.inByte();
//		Util.IERR(""+this);
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTypesEqual();
		CTStack.checkSosValue();
		
//		int cond = Util.GQrelation();
		// Check Relation
		CTStack.pop();
		CTStack.pop();
		
		MemAddr addr = Global.DESTAB[destination];
		if(addr == null) Util.IERR("");
		Global.PSEG.emit(new SVM_JUMPIF(relation, addr), ""+this);
		Global.DESTAB[destination] = null;
		
//		Global.PSEG.dump("BJUMPIF: ");
//		CTStack.dumpStack("BJUMPIF: ");
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BJUMPIF " + relation + " " + destination;
	}
	

}
