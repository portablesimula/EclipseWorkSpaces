/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;

public abstract class BDEST extends Instruction {

	/// S-INSTRUCTION: BDEST
	///
	/// backward_destination ::= bdest destination:newindex
	/// 
	/// check stack empty;
	/// 
	/// The destination must be undefined, otherwise: error.
	/// The destination is defined to refer to the current program point.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/BDEST.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();
		if(Global.DESTAB[destination] != null) Util.IERR("BJUMP dest. dest == null");
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP());
	}

}
