package PREV.syntaxClass.instruction;

import PREV.syntaxClass.programElement.ProgramElement;
import bec.segment.ProgramSegment;
import bec.util.Scode;

public class LINE extends PREV_Instruction {
	int kind;
	int lineNo;
	
	public LINE(int kind) {
		this.kind = kind;
		parse();
	}

	/**
	 * 	info_setting
	 * 		::= decl line:number
	 * 		::= line line:number
	 * 		::= stmt line:number
	 */
	public void parse() {
		Scode.curline = lineNo = Scode.inNumber();
	}
	
	@Override
	public void doCode() {
		// TODO: HVA HER
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		String id = null;
		switch(kind) {
			case 0: id = "LINE ";
			case 1: id = "DECL ";
			case 2: id = "STMT ";
		}
		return id + lineNo;
	}
	

}
