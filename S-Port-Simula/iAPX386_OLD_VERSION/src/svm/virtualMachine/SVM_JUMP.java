package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.ProgramAddress;
import svm.value.Value;

public class SVM_JUMP extends SVM_Instruction {
	public ProgramAddress destination;

	public SVM_JUMP(ProgramAddress destination) {
		this.opcode = SVM_Instruction.iJUMP;
		this.destination = destination;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_JUMPIF: ");
//		RTStack.checkStackEmpty();
		Global.PSC = destination.copy();
	}
	
	@Override	
	public String toString() {
		return "JUMP     " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	protected SVM_JUMP(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iJUMP;
		this.destination = (ProgramAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		destination.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_JUMP(inpt);
	}

}
