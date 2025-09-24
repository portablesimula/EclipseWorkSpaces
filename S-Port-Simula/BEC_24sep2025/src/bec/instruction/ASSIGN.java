package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.RelAddress;
import bec.value.dataAddress.RemoteAddress;
import bec.value.dataAddress.SegmentAddress;
import bec.virtualMachine.RTRegister;
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
		
//		IO.println("ASSIGN.ofScode: tos="+tos);
//		IO.println("ASSIGN.ofScode: sos="+sos);
		
//		SegmentAddress sadr = (SegmentAddress) sos.objadr;
		DataAddress adr = sos.objadr;

		Global.PSEG.emit(new SVM_ASSIGN(false, adr.addOffset(sos.offset), sos.getIndexReg(), sos.size), "ASSIGN: "); // Store into adr

		RTRegister.clearReg(sos.getIndexReg());
		if(sos.objadr instanceof RemoteAddress rem) RTRegister.clearReg(rem.xReg);
		else if(sos.objadr instanceof RelAddress rel) RTRegister.clearReg(rel.xReg);
	}

}
