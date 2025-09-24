package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.Value;

/**
 * Operation AND
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos AND tos) onto the Runtime Stack.
 * This operation depends on the types ....
 *
 * BOOL AND BOOL  --> BOOL
 * INT  AND INT   --> INT
 *
 * Other combinations are illegal.
 * In the case INT the bitwise AND is calculated.
 * 
 * The 'a AND b' function is defined by:
 *  
 * 	a  AND  b  true   false
 *  true       true   false
 *  false      false  false
 */
public class SVM_AND extends SVM_Instruction {

	public SVM_AND() {
		this.opcode = SVM_Instruction.iAND;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.and(sos);
		RTStack.push(res, "SVM_AND: " + tos + " and " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "AND      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_AND instr = new SVM_AND();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}


}
