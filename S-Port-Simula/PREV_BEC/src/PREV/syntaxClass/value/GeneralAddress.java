package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class GeneralAddress extends PREV_Value {
	boolean isGNONE;
	int tag;
	
	public GeneralAddress(boolean isGNONE) {
		this.type = Scode.TAG_GADDR;
		this.isGNONE = isGNONE;
		parse();
	}

	/**
	 * 	general_address
	 * 		::= c-gaddr global_or_const:tag
	 * 		::= gnone
	 */
	private void parse() {
		if(! isGNONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isGNONE) return "GNONE";
		return("C-GADDR " + Scode.edTag(tag));
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private GeneralAddress(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_GADDR;
		tag = inpt.readTag();
		isGNONE = inpt.readBoolean();
//		System.out.println("NEW GeneralAddress: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_GADDR);
		oupt.writeBoolean(isGNONE);
		oupt.writeTag(tag);
	}

	public static GeneralAddress read(AttributeInputStream inpt) throws IOException {
		return new GeneralAddress(inpt);
	}


}
