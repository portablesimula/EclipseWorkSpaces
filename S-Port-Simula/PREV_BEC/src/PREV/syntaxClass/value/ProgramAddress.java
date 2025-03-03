package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class ProgramAddress extends PREV_Value {
	boolean isNOWHERE;
	int tag;
	
	public ProgramAddress(boolean isNOWHERE) {
		this.type = Scode.TAG_PADDR;
		this.isNOWHERE = isNOWHERE;
		parse();
	}

	/**
	 * program_address
	 * 		::= c-paddr label:tag
	 * 		::= NOWHERE
	 */
	public void parse() {
		if(! isNOWHERE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isNOWHERE) return "NOWHERE";
		return("C-PADDR " + Scode.edTag(tag));
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ProgramAddress(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_PADDR;
		tag = inpt.readTag();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_PADDR);
		oupt.writeTag(tag);
	}

	public static ProgramAddress read(AttributeInputStream inpt) throws IOException {
		return new ProgramAddress(inpt);
	}


}
