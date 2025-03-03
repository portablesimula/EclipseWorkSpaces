package PREV.syntaxClass.instruction;

public class POPALL extends PREV_Instruction {
	
	/**
	 * stack_instruction ::= pop
	 */
	public POPALL() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "POPALL";
	}
	

}
