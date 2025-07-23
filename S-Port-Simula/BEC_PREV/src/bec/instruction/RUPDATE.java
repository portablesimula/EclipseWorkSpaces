package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
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
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.
	 */
	public static void ofScode() {
		if(DEBUG)
			CTStack.dumpStack("RUPDATE.ofScode: ");
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		AddressItem adr = (AddressItem) CTStack.pop();
		CTStack.forceTosValue();			
		if(DEBUG) {
			System.out.println("RUPDATE.ofScode: adr="+adr);
			System.out.println("RUPDATE.ofScode: sos="+CTStack.TOS());
//			Util.IERR("");
//			CTStack.dumpStack("RUPDATE-2: ");
		}
		BecGlobal.PSEG.emit(new SVM_STORE(adr.objadr.addOffset(adr.offset), adr.xReg, adr.size), "RUPDATE: "); // Store into adr
	}

}
