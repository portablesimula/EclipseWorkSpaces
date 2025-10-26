/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class LongRealValue extends Value {
	public double value;
	
	private LongRealValue(double value) {
		this.type = Type.T_LREAL;
		this.value = value;
	}
	
	public static LongRealValue of(double value) {
		if(value != 0) return new LongRealValue(value);
		return null;
	}

	/**
	 * longreal_value ::= c-lreal real_literal:string
	 */
	public static LongRealValue ofScode() {
		String r = Scode.inString();
		if(r.startsWith("-")) {
			r = r.substring(1).trim();
			r = (r.startsWith("&"))? "-1" + r : "-" + r;
		}
//		if(r.startsWith("&")) r = "0" + r;
		if(r.startsWith("&")) r = "1" + r;
		else if(r.startsWith("-&")) r = "-0" + r.substring(1);
		return LongRealValue.of(Double.valueOf(r.replace('&', 'E')));
//		return LongRealValue.of(Double.valueOf(Scode.inString()));
	}
//	public LongRealValue() {
//		this.type = Type.T_LREAL;
//		value = Double.valueOf(Scode.inString());
//	}
	
	@Override
	public void emit(DataSegment dseg) {
		dseg.emit(this);
	}

	@Override
	public float toFloat() {
		return (float) value;
	}

	@Override
	public double toDouble() {
		return (double) value;
	}

	@Override
	public Value neg() {
		return LongRealValue.of(- value);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
///		LongRealValue val2 = (LongRealValue) other;
		double val2 = other.toDouble();
		double res = value + val2;
		if(res == 0) return null;
		return LongRealValue.of(res);
	}

	@Override
	public Value sub(Value other) {
		double res = 0;
		if(other != null) {
//			LongRealValue val2 = (LongRealValue) other;
			double val2 = other.toDouble();
			res = this.value - val2;
		} else res = this.value;
//		IO.println("IntegerValue.sub: " + this.value + " - " + other + " = " + res);
		if(res == 0) return null;
		return LongRealValue.of(res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
//		LongRealValue val2 = (LongRealValue) other;
		double val2 = other.toDouble();
		double res = value * val2;
		if(res == 0) return null;
		return LongRealValue.of(res);
	}

	@Override
	public Value div(Value other) {
//		IO.println("RealValue.div: " + other + " / " + this.value);
		double res = 0;
		if(other != null) {
//			LongRealValue val2 = (LongRealValue) other;
			double val2 = other.toDouble();
			res = val2 / this.value;
		} else res = 0;
//		IO.println("RealValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return LongRealValue.of(res);
	}

	@Override
	public boolean compare(int relation, Value other) {
		double LHS = this.value;
//		double RHS = (other == null)? 0 : ((LongRealValue)other).value;
		double RHS = (other == null)? 0 : other.toDouble();
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
		return "C-LREAL " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private LongRealValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_LREAL;
		value = inpt.readDouble();
//		IO.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeByte(Scode.S_C_LREAL);
		oupt.writeDouble(value);
	}

	public static LongRealValue read(AttributeInputStream inpt) throws IOException {
		return new LongRealValue(inpt);
	}


}
