package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_POPtoMEM;

public class RUPDATE extends PREV_Instruction {
	
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
	public RUPDATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		MemAddr adr = Util.getTosDstAdr();
		CTStack.pop();
		Global.PSEG.emit(new SVM_POPtoMEM(adr, 1), ""+this); // Store into adr
//		Global.PSEG.dump();
	}
	
	public String toString() {
		return "RUPDATE";
	}
	

}
