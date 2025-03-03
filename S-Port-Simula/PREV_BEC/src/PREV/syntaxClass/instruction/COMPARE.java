package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_COMPARE;
import bec.virtualMachine.SVM_CONVERT;

public class COMPARE extends PREV_Instruction {
	Relation relation;
	
	public COMPARE() {
		parse();
	}

	/**
	 * arithmetic_instruction ::= compare relation
	 * 
	 * force TOS value; force SOS value;
	 * check relation;
	 * pop; pop;
	 * push( VAL, BOOL, "value(SOS) rel value(TOS)" );
	 * 
	 * TOS and SOS replaced by a description of the boolean result of evaluating the relation. SOS is always
	 * the left operand, i.e. SOS rel TOS.
	 * 
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public void parse() {
		relation = new Relation();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		
		CTStack.checkTypesEqual(); CTStack.checkSosValue();	
		CTStack.pop(); CTStack.pop();
		Global.PSEG.emit(new SVM_COMPARE(relation), "");
		CTStack.pushTemp(Scode.TAG_BOOL);

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "COMPARE " + relation;
	}
	

}
