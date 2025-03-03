package PREV.syntaxClass.instruction;

public class LOCATE extends PREV_Instruction {
	
	/**
	 * addressing_instruction ::= locate
	 */
	public LOCATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "LOCATE";
	}
	

}
