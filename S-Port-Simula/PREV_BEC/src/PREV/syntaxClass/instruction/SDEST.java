package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;

public class SDEST extends PREV_Instruction {
	int tag;
	int which;
	
	/**
	 * forward_destination ::= sdest switch:tag which:number
	 * 
	 * check stack empty;
	 * 
	 * The tag must have been defined in a switch instruction, and the number must be within the range
	 * defined by the corresponding switch instruction, otherwise: error.
	 * The destination "D(which)" of the switch instruction defining the tag is located at the current program
	 * point.
	 */
	public SDEST() {
		tag = Scode.inTag();
		which = Scode.inNumber();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkStackEmpty();
		SWITCH swt = (SWITCH) Global.getMeaning(tag);
		if(swt.DESTAB[which] != null) Util.IERR("SWITCH dest["+which+"]. dest != null");

		swt.DESTAB[which] = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "SDEST " + which);
		Global.PSEG.dump("SDEST: ");
//		Util.IERR(""+this);
	}
	
	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SDEST " + Scode.edTag(tag) + " " + which;
	}
	

}
