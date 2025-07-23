package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;

// POP RT-Stack'TOS 
// The aux values on the top of the operand stack is popped off and forgotten.
public class SVM_POPK extends SVM_Instruction {
	int aux;
	
	public SVM_POPK(int aux) {
		this.opcode = SVM_Instruction.iPOPK;
		this.aux = aux;
	}

	@Override
	public void execute() {
		for(int i=0;i<aux;i++) {
			if(RTStack.curSize() <= 0) {
//				Util.IERR("RTStack underflow");
			} else {
				RTStack.pop();
			}
		}
		BecGlobal.PSC.addOfst(1);
	}

	public String toString() {
		return "POPK     " + aux;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POPK(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOPK;
		this.aux = inpt.readShort();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(aux);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POPK(inpt);
	}

}
