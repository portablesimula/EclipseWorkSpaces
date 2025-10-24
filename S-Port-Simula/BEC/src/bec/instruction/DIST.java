/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_DIST;

public abstract class DIST extends Instruction {
	
	/// S-INSTRUCTION: DIST
	///
	/// addressing_instruction ::= dist
	/// 
	/// dist (dyadic)
	///
	/// force TOS value; check TOS type(OADDR);
	/// force SOS value; check SOS type(OADDR);
	/// pop; pop;
	/// push( VAL, SIZE, "value(SOS) - value(TOS)" );
	/// 
	/// TOS and SOS are replaced by a description of the signed distance from TOS to SOS.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/DIST.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		CTStack.forceTosValue();
		CTStack.checkTosType(Type.T_OADDR); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_DIST());
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(Type.T_SIZE, 1);
	}

}
