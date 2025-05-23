package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_STORE;

public abstract class RUPDATE extends Instruction {
	private static final boolean DEBUG = false;
	
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
//		if(DEBUG) CTStack.dumpStack("RUPDATE-1: ");
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
//		ObjectAddress adr = Util.getTosDstAdr();
		AddressItem adr = (AddressItem) CTStack.pop();
		if(DEBUG) {
			System.out.println("RUPDATE.ofScode: adr="+adr);
			System.out.println("RUPDATE.ofScode: sos="+CTStack.TOS());
//			Util.IERR("");
//			CTStack.dumpStack("RUPDATE-2: ");
		}
//		CTStack.dumpStack("RUPDATE: ");
		Global.PSEG.emit(new SVM_STORE(new RTAddress(adr), adr.size), "RUPDATE: "); // Store into adr
//		if(DEBUG) Global.PSEG.dump("RUPDATE: ");
	}

}
