package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.Value;

/// Operation MULT
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos * tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the same arithmetic type, i.e. int, float or double.
///
public class SVM_MULT extends SVM_Instruction {

	public SVM_MULT() {
		this.opcode = SVM_Instruction.iMULT;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.mult(sos);
		RTStack.push(res);
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
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeKind(SVM_Instruction.iMULT);
	}

	public static SVM_MULT read(AttributeInputStream inpt) throws IOException {
		SVM_MULT instr = new SVM_MULT();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
