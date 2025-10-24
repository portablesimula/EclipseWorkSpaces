/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.descriptor.RecordDescr;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_MULT;
import bec.virtualMachine.SVM_LOADC;

public abstract class DSIZE extends Instruction {
	
	/// S-INSTRUCTION: DSIZE
	///
	/// addressing_instruction ::= dsize structured_type
	/// 
	///		structured_type ::= record_tag:tag
	///
	/// dsize structured_type
	/// 
	/// force TOS value; check TOS type(INT);
	/// pop;
	/// push( VAL, SIZE, "size(type with mod. rep.count)" );
	/// 
	/// The structured type must be prefixed with a "DYNAMIC" type (see 4.3.6),
	/// and it must contain an indefinite repetition, otherwise: error.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/DSIZE.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		Tag tag = Tag.ofScode();
		RecordDescr fixrec = (RecordDescr) tag.getMeaning();
		if(fixrec.nbrep != 0) {
			int n = fixrec.nbrep;
			CTStack.forceTosValue(); CTStack.checkTosInt();
			CTStack.pop();
			IntegerValue nbrepValue = IntegerValue.of(Type.T_INT, n);
			Global.PSEG.emit(new SVM_LOADC(Type.T_INT, nbrepValue));
			Global.PSEG.emit(new SVM_MULT());
			IntegerValue fixValue = IntegerValue.of(Type.T_INT, fixrec.size);
			Global.PSEG.emit(new SVM_LOADC(Type.T_INT, fixValue));
			Global.PSEG.emit(new SVM_ADD());
			
			CTStack.pushTempVAL(Type.T_SIZE, 1);
		} else {
			Util.IERR("Illegal DSIZE on: " + fixrec);
		}
	}

}
