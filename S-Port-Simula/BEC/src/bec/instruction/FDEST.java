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
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;

/// S-INSTRUCTION: FDEST
///
/// forward_destination ::= fdest destination:index
/// 
/// check stack empty;
/// 
/// The destination must have been defined by a fjump or fjumpif instruction, otherwise: error.
/// The current program point becomes the destination of the jump-instruction and the destination becomes
/// undefined.
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/FDEST.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class FDEST extends Instruction {

	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Fixup the corresponding SVM_JUMP instruction.
	/// Finally: Update the destination table.
	public static void ofScode() {
		CTStack.checkStackEmpty();
		int destination = Scode.inByte();
		ProgramAddress addr = Global.DESTAB[destination];
		if(addr == null) Util.IERR("Destination is undefined");
		Global.DESTAB[destination] = null;
		SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(addr.getOfst());
		instr.setDestination(Global.PSEG.nextAddress());
//     	Global.PSEG.emit(new SVM_NOOP());
	}

}
