/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMPIF;

public abstract class BJUMPIF extends Instruction {

	/// S-INSTRUCTION: BJUMPIF
	///
	/// backward_jump ::= bjumpif relation destination:index (dyadic)
	/// 
	/// relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	///
	/// force TOS value; force SOS value;
	/// check relation;
	/// pop; pop;
	/// 
	/// The destination must be defined by a bdest instruction, and TOS and SOS must be of the same
	/// permissible resolved types with regard to relation, otherwise: error.
	/// A conditional jump sequence will be generated, branching only if the relation evaluates true. The
	/// destination becomes undefined.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/BJUMPIF.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
//		CTStack.dumpStack();
		CTStack.forceTosValue();
		CTStack.checkTypesEqual();
		CTStack.checkSosValue();
		Relation relation = Relation.ofScode();
		int destination = Scode.inByte();
		
//		int cond = Util.GQrelation();
		// Check Relation
		CTStackItem tos = CTStack.pop();
		CTStack.pop();
		
		ProgramAddress addr = Global.DESTAB[destination];
		if(addr == null) Util.IERR("Destination is undefined");
		Global.PSEG.emit(new SVM_JUMPIF(relation, tos.type.size(), addr));
		Global.DESTAB[destination] = null;
	}

}
