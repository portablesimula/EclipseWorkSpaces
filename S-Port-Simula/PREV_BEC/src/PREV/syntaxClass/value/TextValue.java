package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class TextValue extends PREV_Value {
	String value;
	
	public TextValue() {
		this.type = Scode.TAG_CHAR;
		parse();
	}

	/**
	 * text_value ::= text long_string
	 */
	public void parse() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
		value = Scode.inLongString();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "TEXT \"" + value + '"';
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_CHAR;
		value = inpt.readString();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_TEXT);
		oupt.writeString(value);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
