package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;
import bec.util.Util;

public class SAVE extends PREV_Instruction {
	Vector<ProgramElement> programElements;
	
	/**
	 * protect_statement ::= save <program_element>* restore
	 */
	public SAVE() {
		parse();
	}

	/**
	 * segment_instruction ::= bseg <program_element>* eseg
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public void parse() {
		programElements = new Vector<ProgramElement>();
		while(Scode.nextByte() != Scode.S_RESTORE) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) Util.IERR("Inconsistent instruction in SAVE-RESTORE");
			programElements.add(elt);
		}
		Scode.inputInstr();
//		printTree(0);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, "SAVE");
		for(ProgramElement elt:programElements) {
			elt.printTree(indent + 1);
		}
		sLIST(indent, "RESTORE");
	}
	
	public String toString() {
		return "SAVE .... RESTORE";
	}

}

