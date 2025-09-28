package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;

/**
 * Operation DIV
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos / tos) onto the Runtime Stack.
 * The operation depends on the types ....
 * 
 * INT   / INT   --> INT
 * REAL  / REAL  --> REAL
 * LREAL / LREAL --> LREAL 
 * 
 * Other combinations are illegal.
 */
public class SVM_DIV extends SVM_Instruction {

	public SVM_DIV() {
		this.opcode = SVM_Instruction.iDIV;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(tos == null) Util.IERR("DIV by zero: " + sos + " / 0");
		Value res = (tos == null)? null : tos.div(sos);
		RTStack.push(res, "SVM_DIV: " + tos + " / " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "DIV      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DIV read(AttributeInputStream inpt) throws IOException {
		SVM_DIV instr = new SVM_DIV();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
