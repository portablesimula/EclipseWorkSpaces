package PREV.syntaxClass.instruction;

public class EVAL extends PREV_Instruction {
	
	/**
	 * eval_instruction ::= eval
	 */
	public EVAL() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "EVAL";
	}
	

}
