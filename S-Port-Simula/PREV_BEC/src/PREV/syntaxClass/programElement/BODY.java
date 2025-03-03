package PREV.syntaxClass.programElement;

import bec.segment.ProgramSegment;

public class BODY extends ProgramElement {
	
	public BODY() {
	}
	
	@Override
	public void doCode() {
		// NOTHING
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BODY "; // + lab;
	}
	

}
