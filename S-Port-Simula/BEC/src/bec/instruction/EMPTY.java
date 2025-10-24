/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;

public abstract class EMPTY extends Instruction {
	
	/// S-INSTRUCTION: EMPTY
	///
	/// stack_instruction ::= empty
	///
	/// * check stack empty;
	///
	/// This instruction is intended as a debugging aid,
	/// it is recommended that the condition is checked always.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/EMPTY.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		CTStack.checkStackEmpty();
	}

}
