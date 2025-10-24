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
import bec.virtualMachine.SVM_LOAD;

public abstract class FETCH extends Instruction {

	/// S-INSTRUCTION: FETCH
	///
	/// addressing_instruction ::= fetch
	/// 
	/// force TOS value;
	/// 
	/// TOS.MODE should be REF, otherwise fetch has no effect.
	/// TOS is modified to describe the contents of the area previously described.
	/// 
	///      (TOS) -------------------,
	///                               |
	///                               V
	///      The resulting            .============.
	///          TOS -----------------|---> VALUE  |
	///      after fetch              '============'
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/FETCH.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		doFetch();
	}

	public static void doFetch() {
		if(CTStack.TOS() instanceof AddressItem addr) {
			Type type = addr.type;
			Global.PSEG.emit(new SVM_LOAD(addr.objadr.addOffset(addr.offset), type.size()));				
			CTStack.pop(); CTStack.pushTempVAL(type, 1);
		}
	}

}
