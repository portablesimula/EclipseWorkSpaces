package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;

// POP RT-Stack'TOS --> Register 
// The value on the top of the operand stack is popped off and stored in reg.
public class SVM_STORE2REG extends SVM_Instruction {
	int reg;
	
	private static final boolean DEBUG = false;
	
	public SVM_STORE2REG(int reg) {
		this.opcode = SVM_Instruction.iSTORE2REG;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
			System.out.println("SVM_STORE2REG: "+RTRegister.edReg(reg));
			RTStack.dumpRTStack("SVM_STORE2REG: ");
		}
		Value value = RTStack.pop();
//		if(value != null) System.out.println("SVM_STORE2REG: "+RTRegister.edReg(reg)+" value="+value.getClass().getSimpleName()+"  "+value);
		RTRegister.putValue(reg, value);
		if(DEBUG) {
			System.out.println("SVM_STORE2REG: "+RTRegister.toLine());
		}
		
		Global.PSC.addOfst(1);
	}
	
	public String toString() {
		return "STORE2REG " + RTRegister.edReg(reg);
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_STORE2REG(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE2REG;
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE2REG(inpt);
	}

}
