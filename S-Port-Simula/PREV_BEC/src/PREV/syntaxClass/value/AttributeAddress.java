package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class AttributeAddress extends PREV_Value {
	boolean isANONE;
	int tag;
	
	public AttributeAddress(boolean isANONE) {
		this.type = Scode.TAG_AADDR;
		this.isANONE = isANONE;
		parse();
	}

	/**
	 * 	attribute_address
	 * 		::= c-aaddr attribute:tag
	 * 		::= ANONE
	 */
	private void parse() {
		if(! isANONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isANONE) return "ANONE";
		return("C-AADDR " + Scode.edTag(tag));
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AttributeAddress(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_AADDR;
		tag = inpt.readTag();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_AADDR);
		oupt.writeTag(tag);
	}

	public static AttributeAddress read(AttributeInputStream inpt) throws IOException {
		return new AttributeAddress(inpt);
	}


}
