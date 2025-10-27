/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Type;
import bec.virtualMachine.SVM_COMPARE;

/// S-INSTRUCTION: COMPARE
///
/// arithmetic_instruction ::= compare relation
/// 
/// force TOS value; force SOS value;
/// check relation;
/// pop; pop;
/// push( VAL, BOOL, "value(SOS) rel value(TOS)" );
/// 
/// TOS and SOS replaced by a description of the boolean result of evaluating the relation. SOS is always
/// the left operand, i.e. SOS rel TOS.
/// 
/// relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
///
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/COMPARE.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class COMPARE extends Instruction {

	/// Scans the remaining S-Code (if any) belonging to this instruction.
	/// Perform the specified stack operations (which may result in code generation).
	/// Finally: Emit an SVM_COMPARE instruction.
	public static void ofScode() {
		Relation relation = Relation.ofScode();
		FETCH.doFetch();
		CTStack.checkTypesEqual(); CTStack.checkSosValue();	
		CTStack.pop(); CTStack.pop();
		CTStack.pushTempItem(Type.T_BOOL);
		
		Global.PSEG.emit(new SVM_COMPARE(relation));
	}	

}
