package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.Value;

/**
 * Operation OR
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two BOOL values from the Runtime Stack and
 * push the result (sos OR tos) onto the Runtime Stack.
 *
 * The 'a OR b' function is defined by:
 *  
 *  a OR b  true   false
 *  true    true   true
 *  false   true   false
 */
public class SVM_OR extends SVM_Instruction {

	public SVM_OR() {
		this.opcode = SVM_Instruction.iOR;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? sos : tos.or(sos);
		RTStack.push(res, "SVM_OR: " + tos + " | " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "OR       ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_OR read(AttributeInputStream inpt) throws IOException {
		SVM_OR instr = new SVM_OR();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
