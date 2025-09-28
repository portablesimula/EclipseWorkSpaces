package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.Value;

/**
 * Operation SUB
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos - tos) onto the Runtime Stack.
 * The operation depends on the types ....
 * 
 * INT   - INT   --> INT
 * OADDR - OADDR --> SIZE
 * REAL  - REAL  --> REAL
 * LREAL - LREAL --> LREAL 
 * 
 * Other combinations are illegal.
 */
public class SVM_SUB extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_SUB() {
		this.opcode = SVM_Instruction.iSUB;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (sos == null)? ( (tos == null)? null : tos.neg()) : sos.sub(tos);
		if(DEBUG) IO.println("SVM_SUB: " + sos + " - " + tos + " ==> " + res);
		RTStack.push(res, "SVM_SUB: " + sos + " - " + tos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "SUB      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_SUB instr = new SVM_SUB();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
