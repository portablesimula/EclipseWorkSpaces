package PREV.syntaxClass.instruction;

public class DIST extends PREV_Instruction {
	
	/**
	 * addressing_instruction ::= dist
	 */
	public DIST() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DIST";
	}
	

}
