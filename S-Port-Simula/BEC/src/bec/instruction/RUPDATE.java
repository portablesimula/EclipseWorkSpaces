package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.virtualMachine.SVM_STORE;

public abstract class RUPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * rupdate (dyadic)
	 * 
	 * check TOS ref;
	 * force SOS value; check types identical;
	 * pop;
	 * 
	 * This instruction (“reverse update”) works almost like update with the sole exception that the
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.
	 */
	public static void ofScode() {
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		AddressItem adr = (AddressItem) CTStack.pop();
		CTStack.forceTosValue();			
		Global.PSEG.emit(new SVM_STORE(adr.objadr.addOffset(adr.offset), adr.size), "RUPDATE: "); // Store into adr			
	}

}
