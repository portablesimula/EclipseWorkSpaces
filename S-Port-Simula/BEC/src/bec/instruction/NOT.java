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
import bec.util.Util;
import bec.virtualMachine.SVM_NOT;

public abstract class NOT extends Instruction {
	
	/// S-INSTRUCTION: NOT
	///
	/// arithmetic_instruction ::= not
	/// 
	/// force TOS value; check TOS type(BOOL,INT);
	/// 
	/// value(TOS) := not value(TOS);
	/// TOS is replaced by a description of the negated TOS value.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/NOT.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStackItem tos = CTStack.TOS();
	    
	    Type at = tos.type;
	    if(at != Type.T_BOOL) {
		    at = CTStack.arithType(at, Type.T_INT);
		    CTStack.checkTosArith();
		    if( at == Type.T_REAL || at == Type.T_LREAL) Util.IERR("");
	    }
		Global.PSEG.emit(new SVM_NOT());
		CTStack.pop();
	    CTStack.pushTempVAL(at, 1);
	}

}
