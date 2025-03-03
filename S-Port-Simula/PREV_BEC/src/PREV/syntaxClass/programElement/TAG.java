package PREV.syntaxClass.programElement;

import bec.util.Scode;

public class TAG extends ProgramElement {
	int iTag;
	int xTag;
	
	public TAG() {
		parse();
	}

	public void parse() {
		iTag = Scode.inTag();
		xTag = Scode.inNumber();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	public String toString() {
		return "TAG" + Scode.edTag(iTag) + " " + xTag;
	}
	

}
