package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT;

public class NOT extends PREV_Instruction {
	
	/**
	 * arithmetic_instruction ::= not
	 * 
	 * force TOS value; check TOS type(BOOL);
	 * 
	 * value(TOS) := not value(TOS);
	 * TOS is replaced by a description of the negated TOS value.
	 */
	public NOT() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		StackItem tos = CTStack.TOS;
	    
	    int at = tos.type;
	    if(at != Scode.TAG_BOOL) {
		    at = CTStack.arithType(at, Scode.TAG_INT);
		    CTStack.checkTosArith();
		    if( at == Scode.TAG_REAL || at == Scode.TAG_LREAL) Util.IERR("CODER.GQnot-1");
	    }
		Global.PSEG.emit(new SVM_NOT(at), "");
		CTStack.pop();
	    CTStack.pushTemp(at);
	    
//		CTStack.dumpStack("NOT: ");
//		Global.PSEG.dump("NOT: ");
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "NOT";
	}
	

}
