package PREV.syntaxClass.instruction;

import bec.util.Scode;

public class DSIZE extends PREV_Instruction {
	int tag;
	
	/**
	 * addressing_instruction ::= dsize structured_type
	 * 
	 *		structured_type ::= record_tag:tag
	 */
	public DSIZE() {
		tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DSIZE " + Scode.edTag(tag);
	}
	

}
