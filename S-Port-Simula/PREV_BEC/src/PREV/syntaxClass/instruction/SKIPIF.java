package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;

public class SKIPIF extends PREV_Instruction {
	Relation relation;
	Vector<ProgramElement> programElements;
	
	public SKIPIF() {
		parse();
	}

	/**
	 * skip_statement ::= skipif relation <program_element>* endskip
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		relation = new Relation();
		programElements = new Vector<ProgramElement>();
		while(Scode.nextByte() != Scode.S_ENDSKIP) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) Util.IERR("Inconsistent instruction in SKIPIF");
			programElements.add(elt);
		}
		Scode.inputInstr();
	
//		if(Scode.inputTrace > 3) print();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, "SKIPIF");
		for(ProgramElement elt:programElements) {
			elt.printTree(indent + 1);
		}
		sLIST(indent, "ENDSKIP");
	}

	public void print() {
		System.out.println("SKIPIF " + relation);
		for(ProgramElement elt:programElements) {
			System.out.println("   " + elt);
		}
		System.out.println("ENDSKIP");
	}

	public String toString() {
		return "SKIPIF ... ENDSKIP";
	}

}
