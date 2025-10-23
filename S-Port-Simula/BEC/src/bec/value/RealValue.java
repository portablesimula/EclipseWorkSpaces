package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

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
		String r = Scode.inString();
		if(r.startsWith("-")) {
			r = r.substring(1).trim();
			r = (r.startsWith("&"))? "-1" + r : "-" + r;
		}
//		if(r.startsWith("&")) r = "0" + r;
		if(r.startsWith("&")) r = "1" + r;
		else if(r.startsWith("-&")) r = "-0" + r.substring(1);
		return RealValue.of(Float.valueOf(r.replace('&', 'E')));
	}
//	public RealValue() {
//		value = Float.valueOf(Scode.inString());
//	}
	
	@Override
	public void emit(DataSegment dseg) {
		dseg.emit(this);
	}

	@Override
	public Value neg() {
		return RealValue.of(- value);
	}

	@Override
	public float toFloat() {
		return value;
	}

	@Override
	public double toDouble() {
		return (double) value;
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
//		RealValue val2 = (RealValue) other;
		float val2 = other.toFloat();
		float res = value + val2;
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value sub(Value other) {
		float res = 0;
		if(other != null) {
//			RealValue val2 = (RealValue) other;
			float val2 = other.toFloat();
			res = this.value - val2;
		} else res = this.value;
//		IO.println("IntegerValue.sub: " + this.value + " - " + other + " = " + res);
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
//		RealValue val2 = (RealValue) other;
		float val2 = other.toFloat();
		float res = value * val2;
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public Value div(Value other) {
//		IO.println("RealValue.div: " + other + " / " + this.value);
		float res = 0;
		if(other != null) {
//			RealValue val2 = (RealValue) other;
			float val2 = other.toFloat();
			res = val2 / this.value;
		} else res = 0;
//		IO.println("RealValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return RealValue.of(res);
	}

	@Override
	public boolean compare(int relation, Value other) {
//		float LHS = this.value;
//		float RHS = (other == null)? 0 : ((RealValue)other).value;
		double LHS = this.value;
		double RHS = 0;
		if(other == null); // Nothing
		else if(other instanceof RealValue rval) RHS = rval.value;
		else if(other instanceof LongRealValue lrval) RHS = lrval.value;
		else Util.IERR("");
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
//		IO.println(indent + toString());
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
//		IO.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_REAL);
		oupt.writeFloat(value);
	}

	public static RealValue read(AttributeInputStream inpt) throws IOException {
		return new RealValue(inpt);
	}


}
