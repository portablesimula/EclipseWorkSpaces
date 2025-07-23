package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.BecGlobal;
import bec.virtualMachine.SVM_ASSIGN;

public abstract class ASSIGN extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * assign (dyadic)
	 * 
	 * - force TOS value;
	 * - check SOS ref; check types identical;
	 * - pop; pop;
	 * 
	 * Code is generated to transfer the value described by TOS to the location designated by SOS.
	 * This implies that the stack elements must be evaluated, and that any code generation involving
	 * TOS or SOS, that has been deferred for optimisation purposes, must take place before the
	 * assignment code is generated. SOS and TOS are popped from the stack.
	 */
	public static void ofScode() {
		CTStack.checkSosRef();
		CTStack.checkTypesEqual();
		CTStack.forceTosValue();
		@SuppressWarnings("unused")
		CTStackItem tos = CTStack.pop();
		AddressItem sos = (AddressItem) CTStack.pop();
		BecGlobal.PSEG.emit(new SVM_ASSIGN(false, sos.objadr.addOffset(sos.offset), sos.xReg, sos.size), "ASSIGN: "); // Store into adr
	}

}
