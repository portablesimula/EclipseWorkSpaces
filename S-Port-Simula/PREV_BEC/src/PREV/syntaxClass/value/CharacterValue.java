package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class CharacterValue extends PREV_Value {
	int value;
	
	public CharacterValue(int value) {
		this.value = value;
	}

	/**
	 * character_value ::= c-char byte
	 */
	public CharacterValue() {
		this.type = Scode.TAG_CHAR;
		this.value = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-CHAR " + value;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_CHAR);
		oupt.writeChar(value);
	}

	public static CharacterValue read(AttributeInputStream inpt) throws IOException {
		return new CharacterValue(inpt.readChar());
	}


}
