package PREV.syntaxClass.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class BooleanValue extends PREV_Value {
	boolean value;
	
	/**
	 * boolean_value ::= true | false
	 */
	public BooleanValue(boolean value) {
		this.type = Scode.TAG_BOOL;
		this.value = value;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "" + value;
	}
	
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr((value)?Scode.S_TRUE:Scode.S_FALSE);
	}

	public static BooleanValue read(AttributeInputStream inpt) throws IOException {
		inpt.readInstr();
		return new BooleanValue(inpt.curinstr==Scode.S_TRUE);
	}


}
