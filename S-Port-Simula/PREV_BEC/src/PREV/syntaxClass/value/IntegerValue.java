package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class IntegerValue extends PREV_Value {
	public int value;
	
	public IntegerValue(int value) {
		this.type = Scode.TAG_INT;
		this.value = value;
	}

	/**
	 * integer_value   ::= c-int integer_literal:string
	 */
	public IntegerValue() {
		this.type = Scode.TAG_INT;
		this.value = Integer.valueOf(Scode.inString());
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-INT " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private IntegerValue(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_INT;
		value = inpt.readInt();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_INT);
		oupt.writeInt(value);
	}

	public static IntegerValue read(AttributeInputStream inpt) throws IOException {
		return new IntegerValue(inpt);
	}


}
