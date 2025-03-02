package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;

public class RealValue extends Value {
	public float value;
	
	public RealValue(float value) {
		this.type = Type.T_REAL;
		this.value = value;
	}

	/**
	 * real_value ::= c-real real_literal:string
	 */
	public RealValue() {
		value = Float.valueOf(Scode.inString());
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "C-REAL " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RealValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_REAL;
		value = inpt.readFloat();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_REAL);
		oupt.writeFloat(value);
	}

	public static RealValue read(AttributeInputStream inpt) throws IOException {
		return new RealValue(inpt);
	}


}
