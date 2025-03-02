package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.Value;

//The value in register REG is pushed onto the operand stack.
public class SVM_PUSHR extends SVM_Instruction {
	Type type;
	int reg;
	
	public SVM_PUSHR(Type type, int reg) {
		this.opcode = SVM_Instruction.iPUSHR;
		this.type = type;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		RTStack.pushr(type, reg, "" + RTRegister.edReg(reg));
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		return "PUSHR    " + type + " " + RTRegister.edReg(reg);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSHR(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHR;
		this.type = Type.read(inpt);
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		type.write(oupt);
		oupt.writeShort(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHR(inpt);
	}

}
