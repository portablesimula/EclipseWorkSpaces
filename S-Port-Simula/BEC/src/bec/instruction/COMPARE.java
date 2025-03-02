package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Type;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_COMPARE;

public abstract class COMPARE extends Instruction {

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
	public static void ofScode() {
//		CTStack.dumpStack();
		Relation relation = Relation.ofScode();
		
		CTStack.checkTypesEqual(); CTStack.checkSosValue();	
		CTStack.pop(); CTStack.pop();
		Global.PSEG.emit(new SVM_COMPARE(relation), "");
		CTStack.pushTemp(Type.T_BOOL, RTRegister.qEAX, 1, "COMPARE: ");

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}	

}
