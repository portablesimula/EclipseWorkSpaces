package bec.instruction;

import bec.S_Module;
import bec.util.Scode;
import bec.util.Util;

public abstract class SAVE extends Instruction {
	
	/**
	 * protect_statement ::= save <program_element>* restore
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public static void ofScode() {
//		programElements = new Vector<ProgramElement>();
//		while(Scode.nextByte() != Scode.S_RESTORE) {
//			ProgramElement elt = ProgramElement.inProgramElement();
//			if(elt == null) Util.IERR("Inconsistent instruction in SAVE-RESTORE");
//			programElements.add(elt);
//		}
		S_Module.programElements();
		Scode.inputInstr();
//		printTree(0);
		Util.IERR("NOT IMPLEMENTED");
	}

}

