package PREV.syntaxClass.instruction;

public class DECO extends PREV_Instruction {
	
	/**
	 * addressing_instruction ::= deco
	 */
	public DECO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DECO";
	}
	

}
