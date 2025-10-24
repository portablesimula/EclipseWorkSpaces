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
import bec.virtualMachine.SVM_CONVERT;

public abstract class CONVERT extends Instruction {

	/// S-INSTRUCTION: CONVERT
	///
	/// convert_instruction ::= convert simple_type
	/// 
	/// TOS must be of simple type, otherwise: error.
	/// 
	/// The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/CONVERT.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		Type toType = Type.ofScode();
		CTStack.forceTosValue();
		doConvert(toType);
	}
	
	public static void doConvert(Type totype) {
		CTStackItem TOS = CTStack.TOS();
		Type fromtype = TOS.type;
		if(totype != fromtype) {
			Global.PSEG.emit(new SVM_CONVERT(fromtype.tag, totype.tag));
			CTStack.pop(); CTStack.pushTempVAL(totype, 1);
			TOS.type = totype;
		}
	}

}
