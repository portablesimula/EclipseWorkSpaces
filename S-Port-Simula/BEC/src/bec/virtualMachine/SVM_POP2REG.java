package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

// POP RT-Stack'TOS --> Register 
// The value on the top of the operand stack is popped off and stored in reg.
public class SVM_POP2REG extends SVM_Instruction {
	int reg;
	int count;
	
	private static final boolean DEBUG = false;
	
	public SVM_POP2REG(int reg, int count) {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = reg;
		this.count = count;
	}
	
	public SVM_POP2REG(int reg) {
		this(reg, 1);
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			RTStack.dumpRTStack("POP2REG: "+RTRegister.edReg(reg)+" count="+count);
			RTStack.callStack_TOP().dump("POP2REG: "+RTRegister.edReg(reg)+" count="+count+"  ");
		}
		for(int i=0;i<count;i++) {
			Value value = RTStack.pop().value();
			RTRegister.putValue(reg+i, value);
		}
		Global.PSC.ofst++;
//		if(DEBUG) {
//			target.segment().dump("PEEK2MEM.execute: ");
//			Util.IERR("");
//		}
	}
	
	public String toString() {
		String s = "";
		String sep = "";
		for(int i=0;i<count;i++) {
			s += (sep + RTRegister.edReg(reg+i));
			sep = "+";
		}
		return "POP2REG  " + s;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2REG(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = inpt.readShort();
		this.count = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2REG(inpt);
	}

}
