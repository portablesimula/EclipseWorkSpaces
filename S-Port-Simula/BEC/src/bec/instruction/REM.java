package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_REM;

public abstract class REM extends Instruction {
	
	/**
	 * arithmetic_instruction ::= rem
	 * 
	 * rem (dyadic)
	 * Remainder, defined as "SOS - (SOS//TOS)*TOS".
	 * Syntax and semantics as for mult except that INT is the only legal type.
	 * 
	 * Note that SIMULA demands "truncation towards zero" for integer division. Thus (except for a
	 * zero remainder) the result of rem has the same sign as the result of the division.
	 * In more formal terms:
	 * 
	 *	 i div j = sign(i/j) * entier(abs(i/j))
	 * 
	 *	 i rem j = i - (i div j) * j
	 * 
	 * where '/' represents the exact mathematical division within the space of real numbers.
	 */
	private REM() {}
	public static void ofScode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		CTStack.checkTosArith(); CTStack.checkSosArith(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		StackItem tos = CTStack.TOS;
	    Type at = CTStack.arithType(tos.type, tos.suc.type);
		Global.PSEG.emit(new SVM_REM(), "REM:  ");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(at, RTRegister.qEAX, 1, "REM:  ");
//		CTStack.dumpStack("MULT.doCode: ");
//		Global.PSEG.dump("MULT.doCode: ");
//		Util.IERR(""+this);
	}

}
