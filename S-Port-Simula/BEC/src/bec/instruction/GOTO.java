/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_GOTO;

/// S-INSTRUCTION: GOTO
///
/// goto_instruction ::= goto
/// 
/// goto_statement ::= goto
/// 
/// force TOS value; check TOS type(PADDR);
/// pop; check stack empty;
/// 
/// TOS is popped and instructions generated to perform the control transfer.
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/GOTO.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class GOTO extends Instruction {
	
	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_GOTO instruction.
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_PADDR);
		CTStack.pop();
		CTStack.checkStackEmpty();
		
		Global.PSEG.emit(new SVM_GOTO());
	}

}
