package PREV.syntaxClass.programElement;

import bec.util.Scode;

public class LABEL extends ProgramElement {
	int tag;
	
	public LABEL() {
		parse();
	}

	/**
	 * label_definition ::= label label:spectag
	 */
	public void parse() {
		tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "LABEL " + Scode.edTag(tag);
	}
	

}
