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
import bec.virtualMachine.SVM_JUMPIF;

public abstract class FJUMPIF extends Instruction {

	/// S-INSTRUCTION: FJUMPIF
	///
	/// forward_jump ::= fjumpif relation destination:newindex
	/// 
	/// relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	/// 
	/// force TOS value; force SOS value;
	/// check relation;
	/// pop; pop;
	/// 
	/// The destination must be undefined, and TOS and SOS must be of the same permissible resolved type
	/// with regard to the relation given, otherwise: error.
	/// A conditional forward jump sequence will be generated, branching only if the relation (see chapter 9)
	/// evaluates true. The destination will refer to an undefined program point to be located later (by fdest).
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/FJUMPIF.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		CTStack.checkTypesEqual();
		CTStack.checkSosValue();
		Relation relation = Relation.ofScode();
		int destination = Scode.inByte();
		if(Global.DESTAB[destination] != null) Util.IERR("Destination is already defined");
		CTStackItem tos = CTStack.pop();
		CTStack.pop();
		
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
		Global.PSEG.emit(new SVM_JUMPIF(relation, tos.type.size(), null));
	}

}
