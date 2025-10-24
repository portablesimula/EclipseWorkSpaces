/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ProfileItem;
import bec.util.Global;
import bec.util.Util;
import bec.virtualMachine.SVM_POPK;

public abstract class POP extends Instruction {
	
	/// S-INSTRUCTION: POP
	///
	/// stack_instruction ::= pop
	/// 
	/// Pop off TOS;
	/// This instruction is illegal if TOS is a profile description.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/POP.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		if(CTStack.TOS() instanceof ProfileItem) Util.IERR("Illegal pop of profileItem ");
		int size = CTStack.TOS().type.size();
		CTStack.pop();
		Global.PSEG.emit(new SVM_POPK(size));
	}

}
