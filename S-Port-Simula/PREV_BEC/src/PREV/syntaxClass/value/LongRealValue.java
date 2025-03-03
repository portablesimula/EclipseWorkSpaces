package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class LongRealValue extends PREV_Value {
	double value;
	
	public LongRealValue(double value) {
		this.type = Scode.TAG_LREAL;
		this.value = value;
	}

	/**
	 * longreal_value ::= c-lreal real_literal:string
	 */
	public LongRealValue() {
		this.type = Scode.TAG_LREAL;
		value = Double.valueOf(Scode.inString());
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-LREAL " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private LongRealValue(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_LREAL;
		value = inpt.readDouble();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_LREAL);
		oupt.writeDouble(value);
	}

	public static LongRealValue read(AttributeInputStream inpt) throws IOException {
		return new LongRealValue(inpt);
	}


}
