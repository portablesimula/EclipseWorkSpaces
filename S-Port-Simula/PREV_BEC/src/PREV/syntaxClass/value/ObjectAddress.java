package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class ObjectAddress extends PREV_Value {
	boolean isONONE;
	int tag;
	
	public ObjectAddress(boolean isONONE) {
		this.type = Scode.TAG_OADDR;
		this.isONONE = isONONE;
		parse();
	}

	/**
	 * object_address
	 * 		::= c-oaddr global_or_const:tag
	 * 		::= onone
	 */
	private void parse() {
		if(! isONONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isONONE) return "ONONE";
		return("C-OADDR " + Scode.edTag(tag));
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ObjectAddress(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_OADDR;
		tag = inpt.readTag();
		isONONE = inpt.readBoolean();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_OADDR);
		oupt.writeBoolean(isONONE);
		oupt.writeTag(tag);
	}

	public static ObjectAddress read(AttributeInputStream inpt) throws IOException {
		return new ObjectAddress(inpt);
	}


}
