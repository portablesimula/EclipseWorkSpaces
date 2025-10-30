/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.scode.Type;

/// S-INSTRUCTION: INITAREA
///
/// initarea resolved_type
/// force TOS value; check TOS type(OADDR);
/// 
/// TOS.TYPE must be OADDR, otherwise: error.
/// The argument type is imposed upon the area, and the area is initialised according to the table below.
/// Only the common part of an instance of a structure will be initialised, ignoring both the prefix and any
/// alternate part(s). The structure is initialised component by component according to the table below.
/// 
/// NOTE: In this implementation  INITAREA == NOOP
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/INITAREA.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class INITAREA extends Instruction {
	
	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// NOTE: In this implementation  INITAREA == NOOP
	public static void ofScode() {
		@SuppressWarnings("unused")
		Type type = Type.ofScode();

		FETCH.doFetch();			
		CTStack.checkTosType(Type.T_OADDR);
		
//		Global.PSEG.emit(new SVM_NOOP());
	}

}
