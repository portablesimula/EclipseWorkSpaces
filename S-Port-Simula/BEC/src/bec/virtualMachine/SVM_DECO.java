/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;

/// SVM-INSTRUCTION: DECO
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The size 'tos' and the oaddr 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = new ObjectAddress(sos.segID, sos.offset - tos)
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_DECO.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_DECO extends SVM_Instruction {

	public SVM_DECO() {
		this.opcode = SVM_Instruction.iDECO;
	}

	@Override
	public void execute() {
		int tos = RTStack.popInt();
		ObjectAddress sos = RTStack.popOADDR();
		ObjectAddress res = (sos == null)? ObjectAddress.ofRelFrameAddr( -tos) : sos.addOffset(-tos);
		RTStack.push(res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "DECO     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DECO read(AttributeInputStream inpt) throws IOException {
		SVM_DECO instr = new SVM_DECO();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
