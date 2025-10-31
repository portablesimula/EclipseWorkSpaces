/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import bec.Global;
import bec.value.Value;

/// SVM-INSTRUCTION: EQV
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos and tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the same type, boolean or int.
/// 
/// The 'eqv' operation is defined by the following matrix:
/// 
///       EQV
/// 	a  \  b  true   false
///     true     true   false
///     false    false  true
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_EQV.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_EQV extends SVM_Instruction {

	public SVM_EQV() {
		this.opcode = SVM_Instruction.iEQV;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.eqv(sos);
		RTStack.push(res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "EQV      ";
	}

}
