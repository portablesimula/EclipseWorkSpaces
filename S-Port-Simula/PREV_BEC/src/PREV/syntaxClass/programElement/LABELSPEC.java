package PREV.syntaxClass.programElement;

import bec.util.Scode;

public class LABELSPEC extends ProgramElement {
	int labelTag;
	
	public LABELSPEC() {
		parse();
	}

	public void parse() {
		labelTag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "LABELSPEC " + Scode.edTag(labelTag);
	}
	

}
