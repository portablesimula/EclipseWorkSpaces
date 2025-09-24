package iAPX.instruction;

import iAPX.ctStack.CTStack;
import iAPX.qInstr.Q_DefFDEST;
import iAPX.qInstr.Q_ForwJMP;
import iAPX.qPkt.Qpkt;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Relation;
import iAPX.util.Scode;
import iAPX.util.Util;

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
		if(Option.GENERATE_Q_CODE) {
			Scode cond = Relation.GQrelation();
			int b = Scode.inByte();
			if(Global.DESTAB[b] != null) Util.IERR("PARSE:FJUMPIF");
			if(CTStack.TOS == CTStack.SAV) Global.DESTAB[b] = new Q_ForwJMP(cond);
			else {
				Qpkt LL = new Q_ForwJMP(Relation.not(cond));
			    CTStack.clearSTK();
			    Global.DESTAB[b]= new Q_ForwJMP(null);
			    new Q_DefFDEST(LL);
			}
//			Util.IERR("");
		} else {
//			Relation relation = Relation.ofScode();
//			int destination = Scode.inByte();
//			if(Global.DESTAB[destination] != null) Util.IERR("Destination is already defined");
//			
////			int cond = Util.GQrelation();
//			// Check Relation
//			CTStackItem tos = CTStack.pop();
//			CTStack.pop();
//			
//			Global.DESTAB[destination] = Global.PSEG.nextAddress();
//			Global.PSEG.emit(new SVM_JUMPIF(relation, tos.type.size(), null), "FJUMPIF: "+relation+" "+destination);
////			Global.PSEG.dump();
		}
//		CTStack.dumpStack();
//		Util.IERR(""+this);
	}

}
