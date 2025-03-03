package PREV.syntaxClass.instruction;

public class ZEROAREA extends PREV_Instruction {
	
	/**
	 * area_initialisation ::= zeroarea
	 */
	public ZEROAREA() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ZEROAREA";
	}
	

}
