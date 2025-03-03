package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class RoutineAddress extends PREV_Value {
	boolean isNOBODY;
	int tag;
	
	public RoutineAddress(boolean isNOBODY) {
		this.type = Scode.TAG_RADDR;
		this.isNOBODY = isNOBODY;
		parse();
	}

	/**
	 * 	routine_address
	 * 		::= c-raddr body:tag
	 * 		::= nobody
	 */
	private void parse() {
		if(! isNOBODY) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isNOBODY) return "NOBODY";
		return("C-RADDR " + Scode.edTag(tag));
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RoutineAddress(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_RADDR;
		tag = inpt.readTag();
		isNOBODY = inpt.readBoolean();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_RADDR);
		oupt.writeBoolean(isNOBODY);
		oupt.writeTag(tag);
	}

	public static RoutineAddress read(AttributeInputStream inpt) throws IOException {
		return new RoutineAddress(inpt);
	}


}
