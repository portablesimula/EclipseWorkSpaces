package PREV.syntaxClass.instruction;

public class INCO extends PREV_Instruction {
	
	/**
	 * addressing_instruction ::= inco
	 */
	public INCO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "INCO";
	}
	

}
