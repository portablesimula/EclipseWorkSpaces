package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;

public class BooleanValue extends Value {
	boolean value;
	
	/**
	 * boolean_value ::= true | false
	 */
	public BooleanValue(boolean value) {
		this.type = Type.T_BOOL;
		this.value = value;
	}

	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "" + value;
	}
	
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind((value)?Scode.S_TRUE:Scode.S_FALSE);
	}

	public static BooleanValue read(AttributeInputStream inpt) throws IOException {
		inpt.readInstr();
		return new BooleanValue(inpt.curinstr==Scode.S_TRUE);
	}


}
