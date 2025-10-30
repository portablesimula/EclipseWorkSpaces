/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.scode.Type;
import bec.util.Global;
import bec.virtualMachine.SVM_SETO;

/// S-INSTRUCTION: ADD
///
///  temp_control ::= t-seto
///  
///  force TOS value; check TOS type(OADDR);
///  pop;
///  
///  Code is generated that inserts the value described by TOS into the pointer variable refered by
///  SAVE-OBJECT and SAVE-INDEX. Note that t-seto does not update SAVE-INDEX.
///  TOS is popped.
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/ADD.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class SETO extends Instruction {
	
	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_SETO instruction.
	public static void ofScode() {
		CTStack.checkTosType(Type.T_OADDR);
		FETCH.doFetch(); CTStack.pop();
		
		Global.PSEG.emit(new SVM_SETO());
	}

}
