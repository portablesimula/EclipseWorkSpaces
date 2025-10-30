/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.scode.Type;
import bec.util.Global;
import bec.virtualMachine.SVM_NEG;

/// S-INSTRUCTION: NEG
///
/// arithmetic_instruction ::= neg
/// 
/// force TOS value; check TOS type(INT,REAL,LREAL);
/// value(TOS) := - value(TOS);
/// 
/// TOS is replaced by a description of the TOS value with its sign inverted.
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/NEG.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class NEG extends Instruction {
	
	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_NEG instruction.
	public static void ofScode() {
		FETCH.doFetch();			
		CTStack.checkTosArith();
		Type at = CTStack.TOS().type;
		CTStack.pop();
	    CTStack.pushTempItem(at);
	    
		Global.PSEG.emit(new SVM_NEG());
	}

}
