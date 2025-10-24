/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_REM;

public abstract class REM extends Instruction {
	
	/// S-INSTRUCTION: REM
	///
	/// arithmetic_instruction ::= rem (dyadic)
	///
	/// Remainder, defined as "SOS - (SOS//TOS)*TOS".
	/// Syntax and semantics as for mult except that INT is the only legal type.
	/// 
	/// Note that SIMULA demands "truncation towards zero" for integer division. Thus (except for a
	/// zero remainder) the result of rem has the same sign as the result of the division.
	/// In more formal terms:
	/// 
	///	 i div j = sign(i/j) * entier(abs(i/j))
	/// 
	///	 i rem j = i - (i div j) * j
	/// 
	/// where '/' represents the exact mathematical division within the space of real numbers.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/REM.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	private REM() {}
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkTosArith(); CTStack.checkSosArith(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		CTStackItem tos = CTStack.TOS();
		CTStackItem sos = CTStack.SOS();
	    Type at = CTStack.arithType(tos.type, sos.type);
		Global.PSEG.emit(new SVM_REM());
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(at, 1);
	}

}
