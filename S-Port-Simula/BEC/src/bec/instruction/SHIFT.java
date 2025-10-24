/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.virtualMachine.SVM_SHIFT;

/// S-INSTRUCTION: SHIFT
///  
///  arithmetic_instruction ::= lshiftl | lshifta | rshiftl | rshiftl   (dyadic)
///
///  force TOS value; check TOS type(INT);
///  force SOS value; check SOS type(INT);
///  check types equal;
///  
///  pop; pop;
///  push( VAL, type, "value(SOS) op value(TOS)" );
///  
///  SOS and TOS are replaced by a description of the value of the application of the operator. The
///  type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
///  SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic. 
///
///	 - lshiftl  Extension to S-Code: Left shift logical
///  - lshifta  Extension to S-Code: Left shift arithm.
///  - rshiftl  Extension to S-Code: Right shift logical
///  - rshifta  Extension to S-Code: Right shift arithm.

/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/SHIFT.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class SHIFT extends Instruction {

	public static void ofScode(int instr) {
		CTStack.forceTosValue();			
		CTStack.checkTosInt(); CTStack.checkSosInt();
		CTStackItem tos = CTStack.TOS();
	    Global.PSEG.emit(new SVM_SHIFT(instr));
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(tos.type, 1);
	}

}
