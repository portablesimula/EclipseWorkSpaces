package PREV.syntaxClass.instruction;

public class REM extends PREV_Instruction {
	
	/**
	 * arithmetic_instruction ::= rem
	 */
	public REM() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "REM";
	}
	

}
