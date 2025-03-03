package PREV.syntaxClass.instruction;

public class SETO extends PREV_Instruction {
	
	/**
	 * temp_control ::= t-seto
	 */
	public SETO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SETO";
	}
	

}
