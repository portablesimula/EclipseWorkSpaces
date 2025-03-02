package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.virtualMachine.SVM_JUMPIF;

public abstract class FJUMPIF extends Instruction {

	/**
	 * forward_jump ::= fjumpif relation destination:newindex
	 * 
	 * force TOS value; force SOS value;
	 * check relation;
	 * pop; pop;
	 * 
	 * The destination must be undefined, and TOS and SOS must be of the same permissible resolved type
	 * with regard to the relation given, otherwise: error.
	 * A conditional forward jump sequence will be generated, branching only if the relation (see chapter 9)
	 * evaluates true. The destination will refer to an undefined program point to be located later (by fdest).
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
		
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
		Global.PSEG.emit(new SVM_JUMPIF(relation, null), "FJUMPIF: "+relation);
//		Global.PSEG.dump();
//		CTStack.dumpStack();
//		Util.IERR(""+this);
	}

}
