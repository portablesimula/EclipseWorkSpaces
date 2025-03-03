package PREV.syntaxClass.instruction;

public class DUP extends PREV_Instruction {
	
	/**
	 * stack_instruction ::= dup
	 */
	public DUP() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DUP";
	}
	

}
