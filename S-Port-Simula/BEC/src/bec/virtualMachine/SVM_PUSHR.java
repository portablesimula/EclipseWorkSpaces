package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

//The value in register REG is pushed onto the operand stack.
public class SVM_PUSHR extends SVM_Instruction {
	int reg;
	
	public SVM_PUSHR(int reg) {
		this.opcode = SVM_Instruction.iPUSHR;
		this.reg = reg;
		RTRegister.reads("SVM_PUSHR", reg);
	}
	
	@Override
	public void execute() {
		RTStack.pushr(reg, "" + RTRegister.edReg(reg));
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		return "PUSHR    " + RTRegister.edReg(reg);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSHR(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHR;
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHR(inpt);
	}

}
