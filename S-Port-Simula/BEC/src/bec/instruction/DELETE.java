/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Type;

public abstract class DELETE extends Instruction {

	/// S-INSTRUCTION: DELETE
	///
	/// delete_statement ::= delete from:tag
	/// 
    /// check stacks empty;
    /// 
    /// All tags defined with values greater than or equal to from:tag are made undefined, i.e. the
    /// corresponding descriptors may be released. The tags become available for reuse. The stack and all
    /// saved stacks must be empty, otherwise: error.
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/DELETE.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode() {
		Tag tag = Tag.ofScode();
		CTStack.checkStackEmpty();
		int startTag = tag.val;
		for (int t = startTag; t < Global.DISPL.size(); t++) {
			Global.DISPL.set(t, null);
			Type.removeFromTMAP(t);
		}
	}

}
