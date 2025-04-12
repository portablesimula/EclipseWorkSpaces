package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Type;

public class RealValue extends Value {
	public float value;
	
	private RealValue(float value) {
		this.type = Type.T_REAL;
		this.value = value;
	}
	
	public static RealValue of(float value) {
		if(value != 0) return new RealValue(value);
		return null;
	}

	/**
	 * real_value ::= c-real real_literal:string
	 */
	public static RealValue ofScode() {
		return RealValue.of(Float.valueOf(Scode.inString()));
	}
//	public RealValue() {
//		value = Float.valueOf(Scode.inString());
//	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}

	@Override
	public Value neg() {
		return RealValue.of(- value);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		RealValue val2 = (RealValue) other;
		float res = value + val2.value;
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value sub(Value other) {
		float res = 0;
		if(other != null) {
			RealValue val2 = (RealValue) other;
			res = this.value - val2.value;
		} else res = this.value;
//		System.out.println("IntegerValue.sub: " + this.value + " - " + other + " = " + res);
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
		RealValue val2 = (RealValue) other;
		float res = value * val2.value;
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value div(Value other) {
//		System.out.println("RealValue.div: " + other + " / " + this.value);
		float res = 0;
		if(other != null) {
			RealValue val2 = (RealValue) other;
			res = val2.value / this.value;
		} else res = 0;
//		System.out.println("RealValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public boolean compare(int relation, Value other) {
		float LHS = this.value;
		float RHS = (other == null)? 0 : ((RealValue)other).value;
		boolean res = false;
		switch(relation) {
			case Scode.S_LT: res = LHS <  RHS; break;
			case Scode.S_LE: res = LHS <= RHS; break;
			case Scode.S_EQ: res = LHS == RHS; break;
			case Scode.S_GE: res = LHS >= RHS; break;
			case Scode.S_GT: res = LHS >  RHS; break;
			case Scode.S_NE: res = LHS != RHS; break;
		}
//		Util.IERR("");
		return res;
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
