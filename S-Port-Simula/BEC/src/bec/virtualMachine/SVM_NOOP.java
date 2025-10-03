package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;

public class SVM_NOOP extends SVM_Instruction {

	// No Operation
	public SVM_NOOP() {
		this.opcode = SVM_Instruction.iNOOP;
	}

	@Override
	public void execute() {
		Global.PSC.addOfst(1);
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
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOOP(inpt);
	}

}
