/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.virtualMachine.SVM_LINE;

public abstract class LINE extends Instruction {

	/// S-INSTRUCTION: LINE
	///
	/// 	info_setting
	/// 		::= decl line:number
	/// 		::= line line:number
	/// 		::= stmt line:number
	/// 
	/// 
	/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/LINE.java"><b>Source File</b></a>.
	/// 
	/// @author S-Port: Definition of S-code
	/// @author Øystein Myhre Andersen
	public static void ofScode(int kind) {
		Scode.curline = Scode.inNumber();	
		if(kind==2)
			CTStack.checkStackEmpty();
		Global.PSEG.emit(new SVM_LINE(0, Scode.curline));
	}

}
