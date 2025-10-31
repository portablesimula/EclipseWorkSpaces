/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.Global;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.virtualMachine.SVM_DIV;

/// S-INSTRUCTION: DIV
///
/// arithmetic_instruction ::= div (dyadic)
/// 
/// force TOS value; check TOS type(INT,REAL,LREAL);
/// force SOS value; check SOS type(INT,REAL,LREAL);
/// check types equal;
/// 
/// pop; pop;
/// push( VAL, type, "value(SOS) / value(TOS)" );
/// 
/// SOS and TOS are replaced by a description of the value of the application of the operator. The
/// type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
/// SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic. 
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/DIV.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class DIV extends Instruction {

	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_DIV instruction.
	public static void ofScode() {
		FETCH.doFetch(); CTStack.checkTosArith();
		CTStack.checkSosValue(); CTStack.checkTypesEqual();
		CTStackItem tos = CTStack.pop(); CTStack.pop();
	    CTStack.pushTempItem(tos.type);
	    
		Global.PSEG.emit(new SVM_DIV());
	}

}
