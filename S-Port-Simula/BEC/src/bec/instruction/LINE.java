package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.virtualMachine.SVM_LINE;

public abstract class LINE extends Instruction {

	/**
	 * 	info_setting
	 * 		::= decl line:number
	 * 		::= line line:number
	 * 		::= stmt line:number
	 */
	public static void ofScode(int kind) {
		Scode.curline = Scode.inNumber();	
		if(kind==2)
			CTStack.checkStackEmpty();
		Global.PSEG.emit(new SVM_LINE(0, Scode.curline), "LINE: ");
	}

}
