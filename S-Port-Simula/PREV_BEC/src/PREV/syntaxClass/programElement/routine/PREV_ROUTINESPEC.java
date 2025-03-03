package PREV.syntaxClass.programElement.routine;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;

public class PREV_ROUTINESPEC extends ProgramElement {
	int bodyTag;
	int profileTag;
	
	boolean inHead;
	
	/**
	 * routine_specification ::= routinespec body:newtag profile:tag
	 */
	private PREV_ROUTINESPEC(boolean inHead) {
		bodyTag = Scode.inTag();
		profileTag = Scode.inTag();
		this.inHead = inHead;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ROUTINESPEC " + Scode.edTag(bodyTag) + " " + Scode.edTag(profileTag);
	}
	

}
