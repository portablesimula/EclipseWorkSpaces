package bec.instruction;

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
		Global.PSEG.emit(new SVM_LINE(0, Global.curline), "LINE: ");
	}

}
