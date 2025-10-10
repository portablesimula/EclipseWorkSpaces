package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.Value;

/**
 * Operation ADD
 * 
 * 	Runtime Stack
 * 	   ..., sos, tos →
 * 	   ..., result
 *
 * The 'tos' and 'sos' are popped off the Runtime stack.
 * The 'result' is calculated as result = sos + tos.
 * Then the 'result' is pushed onto the Runtime Stack.
 * 
 * 'tos' and 'sos' must be of the same arithmetic type, i.e. int, float or double.
 */
public class SVM_ADD extends SVM_Instruction {

	public SVM_ADD() {
		this.opcode = SVM_Instruction.iADD;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (sos == null)? tos : sos.add(tos);
		RTStack.push(res, "SVM_ADD: " + tos + " + " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "ADD      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_ADD read(AttributeInputStream inpt) throws IOException {
		SVM_ADD instr = new SVM_ADD();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
