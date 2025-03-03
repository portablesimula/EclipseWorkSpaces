package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.virtualMachine.SVM_NEG;

public class NEG extends PREV_Instruction {
	
	/**
	 * arithmetic_instruction ::= neg
	 * 
	 * force TOS value; check TOS type(INT,REAL,LREAL);
	 * value(TOS) := - value(TOS);
	 * 
	 * TOS is replaced by a description of the TOS value with its sign inverted.
	 */
	public NEG() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		CTStack.checkTosArith();
		int at = CTStack.TOS.type;
		Global.PSEG.emit(new SVM_NEG(at), "");
		CTStack.pop();
	    CTStack.pushTemp(at);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "NEG";
	}
	

}
