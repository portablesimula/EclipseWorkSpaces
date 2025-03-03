package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_AND;
import bec.virtualMachine.SVM_XOR;

public class XOR extends PREV_Instruction {
	
	/**
	 * arithmetic_instruction ::= xor
	 * 
	 * and, or, xor, imp, eqv (dyadic)
	 * 
	 * force TOS value; check TOS type(BOOL);
	 * force SOS value; check SOS type(BOOL);
	 * pop; pop;
	 * push( VAL, BOOL, "value(SOS) op value(TOS)" );
	 * 
	 * TOS and SOS are replaced by a description of the result of applying the operator.
	 * Note that SOS is the left operand.
	 */
	public XOR() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		StackItem tos = CTStack.TOS;
	    
	    int at = tos.type;
	    if(at != Scode.TAG_BOOL) {
		    at = CTStack.arithType(at, tos.suc.type);
		    CTStack.checkTosArith(); CTStack.checkSosArith();
		    CTStack.checkSosValue(); CTStack.checkTypesEqual();
		    if( at == Scode.TAG_REAL || at == Scode.TAG_LREAL) Util.IERR("CODER.GQandxor-1");
	    } else {
	    	CTStack.checkSosValue(); CTStack.checkSosType(Scode.TAG_BOOL);
	    }
	    
		Global.PSEG.emit(new SVM_XOR(at), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(at);
//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "OR";
	}
	

}
