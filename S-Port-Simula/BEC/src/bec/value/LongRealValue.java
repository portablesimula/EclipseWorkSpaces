package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;

public class LongRealValue extends Value {
	double value;
	
	public LongRealValue(double value) {
		this.type = Type.T_LREAL;
		this.value = value;
	}

	/**
	 * longreal_value ::= c-lreal real_literal:string
	 */
	public LongRealValue() {
		this.type = Type.T_LREAL;
		value = Double.valueOf(Scode.inString());
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "C-LREAL " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private LongRealValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_LREAL;
		value = inpt.readDouble();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_LREAL);
		oupt.writeDouble(value);
	}

	public static LongRealValue read(AttributeInputStream inpt) throws IOException {
		return new LongRealValue(inpt);
	}


}
