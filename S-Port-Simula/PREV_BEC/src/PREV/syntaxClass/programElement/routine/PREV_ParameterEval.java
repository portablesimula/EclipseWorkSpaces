package PREV.syntaxClass.programElement.routine;

import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.PREV_Instruction;
import PREV.syntaxClass.programElement.ProgramElement;
import bec.segment.ProgramSegment;

public class PREV_ParameterEval extends SyntaxClass {
	Vector<PREV_Instruction> instructions;
	int nRep;
	
	/**
	 * 	parameter eval
	 * 		::= <instruction>+ asspar
	 * 		::= <instruction>+ assrep n:byte
	 */
	public PREV_ParameterEval(Vector<PREV_Instruction> instructions,int nRep) {
		this.instructions = instructions;
		this.nRep = nRep;
//		System.out.println("NEW ParameterEvaluation: " + this);
//		print();
	}
	
	@Override
	public void doCode() {
		for(ProgramElement instr:instructions)
			instr.doCode();
	}

	public void print() {
		for(ProgramElement instr:instructions)
			System.out.println("   " + instr);
		System.out.println((nRep < 0) ? "   ASSPAR" : "   ASSREP " + nRep);
	}

	@Override
	public void printTree(final int indent) {
		
		for(ProgramElement instr:instructions)
			instr.printTree(indent);
//		// Modify Scode.STACK
//		int nPop = (nRep < 0) ? 1 : nRep;
//		System.out.println("ParameterEval.printTree: nPop="+nPop);
//		for(int i=0;i<nPop;i++) Scode.STACK.pop();
		
		sLIST(indent, (nRep < 0) ? "ASSPAR" : "ASSREP " + nRep);
		
	}
		
	public String toString() {
		return "Instruction ... " + ((nRep < 0) ? "ASSPAR " : "ASSREP " + nRep);
	}
	

}
