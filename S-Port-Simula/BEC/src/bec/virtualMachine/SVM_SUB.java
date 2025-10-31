/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.value.Value;

/// SVM-INSTRUCTION: SUB
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos - tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the same arithmetic type, i.e. int, float or double.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_SUB.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
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
		RTStack.push(res);
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
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_SUB instr = new SVM_SUB();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
