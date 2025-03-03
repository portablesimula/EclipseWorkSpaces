package PREV.syntaxClass.instruction;

public class GOTO extends PREV_Instruction {
	
	/**
	 * goto_instruction ::= goto
	 */
	public GOTO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "GOTO";
	}
	

}
