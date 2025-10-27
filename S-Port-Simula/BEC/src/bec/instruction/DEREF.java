/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_LOADA;

/// S-INSTRUCTION: DEREF
///
/// addressing_instruction ::= deref
/// 
/// 
/// check TOS ref;
/// TOS.MODE := VAL; TOS.TYPE := GADDR;
/// TOS is modified to describe the address of the area.
/// 
///     (TOS) ------------------------------------------,
///                              REF                     |
///                                                      |
///                                                      |
///  The resulting           .==================.        V
///       TOS ---------------|--> GADDR VALUE --|------->.========,
///  after deref             '=================='        |        |
///                                                      |        |
///                                                      '========'
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/DEREF.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class DEREF extends Instruction {

	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_LOADA instruction.
	public static void ofScode() {
		CTStack.checkTosRef();
		AddressItem tos = (AddressItem) CTStack.pop();
		CTStack.pushTempItem(Type.T_GADDR, 2);

		Global.PSEG.emit(new SVM_LOADA(tos.objadr, tos.offset));			
	}

}
