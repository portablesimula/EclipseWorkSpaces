package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class RealValue extends PREV_Value {
	float value;
	
	public RealValue(float value) {
		this.type = Scode.TAG_REAL;
		this.value = value;
	}

	/**
	 * real_value ::= c-real real_literal:string
	 */
	public RealValue() {
		value = Float.valueOf(Scode.inString());
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-REAL " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RealValue(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_REAL;
		value = inpt.readFloat();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_REAL);
		oupt.writeFloat(value);
	}

	public static RealValue read(AttributeInputStream inpt) throws IOException {
		return new RealValue(inpt);
	}


}
