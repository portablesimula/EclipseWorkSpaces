package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;

//The value in register 'reg' is pushed onto the operand stack.
public class SVM_PUSHR extends SVM_Instruction {
	private int reg;

	private final boolean DEBUG = false;

	public SVM_PUSHR(int reg) {
		this.opcode = SVM_Instruction.iPUSHR;
		this.reg = reg;
		Reg.reads("SVM_PUSHR: ", reg);
	}
	
	@Override
	public void execute() {
//		Value value = RTRegister.getAddrValue(reg);
		Value value = Reg.getValue(reg);
		RTStack.push(value, null);
		
		Util.IERR("");
		
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		return "PUSHR    " + Reg.edReg(reg);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSHR(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHR;
		this.reg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHR(inpt);
	}

}
