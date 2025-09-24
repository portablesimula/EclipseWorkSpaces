package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

/**
 * Operation POPK n
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'n →
 *    ...
 *
 * The 'n' values on the top of the Runtime Stack is popped off and forgotten.
 */
public class SVM_POPK extends SVM_Instruction {
	int n;
	
	public SVM_POPK(int n) {
		this.opcode = SVM_Instruction.iPOPK;
		this.n = n;
	}

	@Override
	public void execute() {
		for(int i=0;i<n;i++) {
			if(RTStack.curSize() <= 0) Util.IERR("RTStack underflow");
			RTStack.pop();
		}
		Global.PSC.addOfst(1);
	}

	public String toString() {
		return "POPK     " + n;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POPK(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOPK;
		this.n = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(n);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POPK(inpt);
	}

}
