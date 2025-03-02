package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_POP2MEM;

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
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.	 */
	public static void ofScode() {
		CTStack.dumpStack("RUPDATE-1: ");
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
//		ObjectAddress adr = Util.getTosDstAdr();
		AddressItem adr = (AddressItem) CTStack.TOS;
		System.out.println("RUPDATE.ofScode: adr="+adr.objReg);
//		Util.IERR("");
		CTStack.dumpStack("RUPDATE-2: ");
		StackItem tos = CTStack.pop();
//		CTStack.dumpStack("RUPDATE: ");
		Global.PSEG.emit(new SVM_POP2MEM(adr, tos.size), "RUPDATE: "); // Store into adr
		Global.PSEG.dump("RUPDATE: ");
//		Util.IERR("");
	}

}
