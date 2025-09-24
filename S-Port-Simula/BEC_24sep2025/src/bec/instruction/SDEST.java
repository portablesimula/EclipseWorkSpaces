package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.descriptor.SwitchDescr;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.virtualMachine.SVM_NOOP;

public abstract class SDEST extends Instruction {
	
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
	private SDEST() {}
	public static void ofScode() {
//		CTStack.dumpStack();
		Tag tag = Tag.ofScode();
		int which = Scode.inNumber();
		CTStack.checkStackEmpty();
		SwitchDescr swt = (SwitchDescr) tag.getMeaning();
		if(swt.DESTAB[which] != null) Util.IERR("SWITCH dest["+which+"]. dest != null");

		swt.DESTAB[which] = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "SDEST " + which);
//		Global.PSEG.dump("SDEST: ");
//		Util.IERR(""+this);
	}

}
