package PREV.syntaxClass.instruction;

import bec.util.Scode;

public class DELETE extends PREV_Instruction {
	int tag;
	
	public DELETE() {
		parse();
	}

	/**
	 * delete_statement ::= delete from:tag
	 * 
     * check stacks empty;
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DELETE " + Scode.edTag(tag);
	}
	

}
