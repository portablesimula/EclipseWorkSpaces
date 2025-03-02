package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMPIF;

public abstract class BJUMPIF extends Instruction {

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
	public static void ofScode() {
//		CTStack.dumpStack();
		CTStack.checkTypesEqual();
		CTStack.checkSosValue();
		Relation relation = Relation.ofScode();
		int destination = Scode.inByte();
		
//		int cond = Util.GQrelation();
		// Check Relation
		CTStack.pop();
		CTStack.pop();
		
		ProgramAddress addr = Global.DESTAB[destination];
		if(addr == null) Util.IERR("");
		Global.PSEG.emit(new SVM_JUMPIF(relation, addr), "BJUMPIF: "+destination+ " " + destination);
		Global.DESTAB[destination] = null;
		
//		Global.PSEG.dump("BJUMPIF: ");
//		CTStack.dumpStack("BJUMPIF: ");
//		Util.IERR(""+this);
	}

}
