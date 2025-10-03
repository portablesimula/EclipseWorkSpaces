package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.virtualMachine.SVM_ASSIGN;

public abstract class UPDATE extends Instruction {
	
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
	 * 
	 * update (dyadic)
	 * force TOS value;
	 * check SOS ref; check types identical;
	 * force SOS value;
	 * pop;
	 *
	 * Code is generated to transfer the value described by TOS to the location designated by SOS.
	 * TOS must be evaluated and any deferred code generation involving TOS must take place before
	 * the update code is generated. Note that only TOS is popped and the new TOS is modified to
	 * describe the value assigned.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkSosRef();
		CTStack.checkTypesEqual();
//		FETCH.doFetch("ASSIGN: "); // Force TOS value
		CTStackItem tos = CTStack.pop();
		AddressItem sos = (AddressItem) CTStack.pop();
		CTStack.pushTempVAL(tos.type, 1, "UPDATE: ");
		
//		if(Option.TESTING_xREG)
			Util.IERR("NOT IMPL");

//		Global.PSEG.emit(new SVM_ASSIGN(true, sos.objadr.addOffset(sos.offset), sos.xReg, sos.size), "UPDATE: "); // Store into adr
	}

}
