package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;

public class IF extends PREV_Instruction {
	Relation relation;
	Vector<ProgramElement> thenElements;
	Vector<ProgramElement> elseElements;
	
	public IF() {
		parse();
	}

	/**
	 * 	if_statement ::= if relation <program_element>* else_part
	 * 
	 * 		else_part
	 * 			::= else <program_element>* endif
	 * 			::= endif
	 */
	public void parse() {
		relation = new Relation();
		thenElements = new Vector<ProgramElement>();
		LOOP: while(true) {
			switch(Scode.nextByte()) {
				case Scode.S_ELSE -> {
//					System.out.println("IF: BEGIN ELSE");
					Scode.inputInstr();
					elseElements = new Vector<ProgramElement>();
					while(Scode.nextByte() != Scode.S_ENDIF) {
						readElement(elseElements);
					}
					break LOOP;
				}
				case Scode.S_ENDIF -> { break LOOP; }
				default -> { readElement(thenElements); }
			}
		}
//		while(Scode.nextByte() != Scode.S_ELSE) {
//			ProgramElement elt = ProgramElement.inProgramElement();
//			if(elt == null) Util.IERR("Inconsistent instruction in SKIPIF");
//			thenElements.add(elt);
//		}
		Scode.inputInstr();
	
//		if(Scode.inputTrace > 3) printTree(0);
//		Util.IERR("STOP");;
	}
	
	private void readElement(Vector<ProgramElement> programElements) {
		ProgramElement elt = ProgramElement.inProgramElement();
		if(elt == null) Util.IERR("Inconsistent instruction in IF");
		programElements.add(elt);
		
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, "IF " + relation);
		for(ProgramElement elt:thenElements) {
			elt.printTree(indent + 1);
		}
		if(elseElements != null) {
			sLIST(indent, "ELSE");
			for(ProgramElement elt:elseElements) {
				elt.printTree(indent + 1);
			}			
		}
		sLIST(indent, "ENDIF");
	}

	public String toString() {
		return "SKIPIF ... ENDSKIP";
	}

}
