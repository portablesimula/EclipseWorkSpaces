package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.value.ProgramAddress;

/**
 * Remove top item on the Runtime-Stack and set PSC to that value
 */
public class SVM_GOTO extends SVM_Instruction {

	public SVM_GOTO() {
		this.opcode = SVM_Instruction.iGOTO;
	}

	@Override
	public void execute() {
		ProgramAddress target = (ProgramAddress) RTStack.pop();
		BecGlobal.PSC = target.copy();
		RTStack.checkStackEmpty();
	}
	
	@Override	
	public String toString() {
		return "GOTO     ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	protected SVM_GOTO(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iGOTO;
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTO(inpt);
	}

}
