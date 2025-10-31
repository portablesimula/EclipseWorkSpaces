/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.scode.Type;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.IntegerValue;
import bec.value.Value;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
 */
/// SVM-INSTRUCTION: NOT
/// 
/// 	Runtime Stack
/// 	   ..., tos →
/// 	   ..., result
///
/// The 'tos' is popped off the Runtime stack.
/// The 'result' is calculated as result = ! tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' must be boolean or int.
/// 
/// The 'and' operation is defined by the following matrix:
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_NOT.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_NOT extends SVM_Instruction {

	public SVM_NOT() {
		this.opcode = SVM_Instruction.iNOT;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value res = null;
		if(tos == null) res = BooleanValue.of(true);
		else {
			if(tos instanceof BooleanValue bval) {
				res = BooleanValue.of(! bval.value);
			}
			else if(tos instanceof IntegerValue ival) {
				res = IntegerValue.of(Type.T_INT, ~ ival.value);
			} else Util.IERR("");
		}
		RTStack.push(res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "NOT      ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOT(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOT;
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOT(inpt);
	}

}
