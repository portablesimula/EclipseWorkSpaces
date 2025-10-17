package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.Value;

/// Operation NEG
/// 
/// 	Runtime Stack
/// 	   ..., tos →
/// 	   ..., result
///
/// The 'tos' is popped off the Runtime stack.
/// The 'result' is calculated as result = - tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' must be of the arithmetic type, i.e. int, float or double.
///
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
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_NEG instr = new SVM_NEG();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
