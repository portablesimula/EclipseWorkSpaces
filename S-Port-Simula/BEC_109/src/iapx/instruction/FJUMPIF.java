package iapx.instruction;

import iapx.CTStack.CTStack;
import iapx.qInstr.Q_DefFDEST;
import iapx.qInstr.Q_ForwJMP;
import iapx.qPkt.Qpkt;
import iapx.util.Global;
import iapx.util.Relation;
import iapx.util.Scode;
import iapx.util.Util;

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
		Scode cond = Relation.GQrelation();
		int b = Scode.inByte();
		IO.println("FJUMPIF.ofScode: Global.DESTAB: " + Global.DESTAB);
		if(Global.DESTAB[b] != null) Util.IERR("FJUMPIF.ofScode: ");
		if(CTStack.TOS == CTStack.SAV) Global.DESTAB[b] = new Q_ForwJMP(cond);
		else {
			Qpkt LL = new Q_ForwJMP(Relation.not(cond));
		    CTStack.clearSTK();
		    Global.DESTAB[b]= new Q_ForwJMP(null);
		    new Q_DefFDEST(LL);
		}
//		Util.IERR("");
//		CTStack.dumpStack();
//		Util.IERR(""+this);
	}

}
