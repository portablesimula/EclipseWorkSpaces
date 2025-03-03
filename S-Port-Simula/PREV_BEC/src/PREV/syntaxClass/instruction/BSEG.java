package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;
import bec.util.Util;

public class BSEG extends PREV_Instruction {
	Vector<ProgramElement> programElements;
	
	public BSEG() {
		parse();
	}

	/**
	 * segment_instruction ::= bseg <program_element>* eseg
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public void parse() {
		// BSEGInstruction
		programElements = new Vector<ProgramElement>();
		while(Scode.nextByte() != Scode.S_ESEG) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) Util.IERR("Inconsistent instruction in BSE-ESEG");
			programElements.add(elt);
		}
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) print();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, "BSEG");
		for(ProgramElement elt:programElements) {
			elt.printTree(indent + 1);
		}
		sLIST(indent, "ESEG");
	}
	
	public void print() {
		System.out.println("BSEG");
		for(ProgramElement elt:programElements) {
			System.out.println("   " + elt);
		}
		System.out.println("ESEG");
	}
	
	public String toString() {
		return "BSEG";
	}

}
