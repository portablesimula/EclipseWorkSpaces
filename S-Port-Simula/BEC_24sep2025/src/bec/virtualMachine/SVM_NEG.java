package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Operation NEG
 *
 * Runtime Stack
 *    ..., tos →
 *    ..., result
 *
 * Pop one value from the Runtime Stack and
 * push the result (- tos) onto the Runtime Stack.
 */
public class SVM_NEG extends SVM_Instruction {

	public SVM_NEG() {
		this.opcode = SVM_Instruction.iNEG;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value res = (tos == null)? null : tos.neg();
		RTStack.push(res, "SVM_NEG:  -" + tos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "NEG      ";
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
		SVM_NEG instr = new SVM_NEG();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
