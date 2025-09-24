package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Operation MULT
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos * tos) onto the Runtime Stack.
 * The operation depends on the types ....
 * 
 * INT   * INT   --> INT
 * REAL  * REAL  --> REAL
 * LREAL * LREAL --> LREAL 
 * 
 * Other combinations are illegal.
 */
public class SVM_MULT extends SVM_Instruction {

	public SVM_MULT() {
		this.opcode = SVM_Instruction.iMULT;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.mult(sos);
		RTStack.push(res, "SVM_MULT: " + tos + " + " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "MULT     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeKind(SVM_Instruction.iMULT);
	}

	public static SVM_MULT read(AttributeInputStream inpt) throws IOException {
		SVM_MULT instr = new SVM_MULT();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
