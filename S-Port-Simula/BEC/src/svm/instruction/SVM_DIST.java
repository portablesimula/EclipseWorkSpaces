/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.instruction;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.scode.Type;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import svm.RTStack;
import svm.value.IntegerValue;
import svm.value.ObjectAddress;

/// SVM-INSTRUCTION: DIST
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos - tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the type oaddr.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_DIST.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_DIST extends SVM_Instruction {

	public SVM_DIST() {
		this.opcode = SVM_Instruction.iDIST;
	}

	@Override
	public void execute() {
		ObjectAddress tos = RTStack.popOADDR();
		ObjectAddress sos = RTStack.popOADDR();
		int tval = (tos == null)? 0 : tos.ofst;
		int sval = (sos == null)? 0 : sos.ofst;
		IntegerValue res = IntegerValue.of(Type.T_SIZE, sval - tval);
		RTStack.push(res);
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "DIST     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
	}

	public static SVM_DIST read(AttributeInputStream inpt) throws IOException {
		SVM_DIST instr = new SVM_DIST();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
