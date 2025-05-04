package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

public class SVM_RESTORE extends SVM_Instruction {
	
	public SVM_RESTORE() {
		this.opcode = SVM_Instruction.iRESTORE;
	}
	
	@Override
	public void execute() {
		RTStack.restoreStack();
//		Util.IERR("");
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		return "RESTORE  ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RESTORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRESTORE;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RESTORE(inpt);
	}

}
