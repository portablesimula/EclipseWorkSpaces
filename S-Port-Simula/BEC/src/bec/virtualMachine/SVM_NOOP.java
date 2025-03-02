package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class SVM_NOOP extends SVM_Instruction {

	// No Operation
	public SVM_NOOP() {
		this.opcode = SVM_Instruction.iNOOP;
	}

	@Override
	public void execute() {
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "NOOP ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOOP(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOOP;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOOP(inpt);
	}

}
