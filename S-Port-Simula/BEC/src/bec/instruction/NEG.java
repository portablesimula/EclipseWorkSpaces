package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_NEG;

public abstract class NEG extends Instruction {
	
	/**
	 * arithmetic_instruction ::= neg
	 * 
	 * force TOS value; check TOS type(INT,REAL,LREAL);
	 * value(TOS) := - value(TOS);
	 * 
	 * TOS is replaced by a description of the TOS value with its sign inverted.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		CTStack.checkTosArith();
		Type at = CTStack.TOS.type;
		Global.PSEG.emit(new SVM_NEG(), "");
		CTStack.pop();
	    CTStack.pushTemp(at, RTRegister.qEAX, 1, "NEG: ");
	}

}
