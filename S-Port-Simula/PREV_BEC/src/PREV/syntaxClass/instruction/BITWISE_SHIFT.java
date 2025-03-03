package PREV.syntaxClass.instruction;

import bec.util.Scode;

public class BITWISE_SHIFT extends PREV_Instruction {
	int instr;
	
	public BITWISE_SHIFT(int instr) {
		this.instr = instr;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
