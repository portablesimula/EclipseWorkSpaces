/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_SETO;

public abstract class SETO extends Instruction {
	
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
	public static void ofScode() {
		CTStack.checkTosType(Type.T_OADDR);
		CTStack.forceTosValue(); CTStack.pop();
		Global.PSEG.emit(new SVM_SETO());
	}

}
