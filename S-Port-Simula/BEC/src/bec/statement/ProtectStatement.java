package bec.statement;

import bec.util.Util;

public class ProtectStatement { // extends ProgramElement {
	
	public ProtectStatement() {
		parse();
	}

	public void parse() {
		Util.IERR("NOT IMPLEMENTED");
		// ProtectConstruction(true)
	}
	
	public String toString() {
		return "SAVE "; // + lab;
	}
	

}
