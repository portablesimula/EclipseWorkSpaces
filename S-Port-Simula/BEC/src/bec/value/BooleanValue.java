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

public class BooleanValue extends Value {
	public boolean value;
	
	/**
	 * boolean_value ::= true | false
	 */
	private BooleanValue(boolean value) {
		this.type = Type.T_BOOL;
		this.value = value;
	}
	
	public static BooleanValue of(boolean value) {
		if(value) return new BooleanValue(true);
		return null;
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}
	
	@Override
	public Value and(Value other) {
		if(other == null) return BooleanValue.of(value == false);
//		if(other == null) return this;
		BooleanValue val2 = (BooleanValue) other;
		boolean res = value & val2.value;
		if(!res) return null;
		return BooleanValue.of(res);
	}

	@Override
	public BooleanValue or(Value other) {
		if(other == null) return this;
		BooleanValue val2 = (BooleanValue) other;
		boolean res = value | val2.value;
		if(!res) return null;
		return BooleanValue.of(res);
	}

	@Override
	public BooleanValue xor(Value other) {
		if(other == null) return this;
		BooleanValue val2 = (BooleanValue) other;
		boolean res = value ^ val2.value;
		if(!res) return null;
		return BooleanValue.of(res);
	}

	@Override
	public BooleanValue imp(Value other) {
		// true imp false  ==> false  otherwise  true
		boolean res = true;
		if(this.value) {
			if(other == null || !((BooleanValue)other).value) res = false;
		}
//		IO.println("BooleanValue.imp: " + this.value + " imp " + other + " ==> " + res);
		if(!res) return null;
		return BooleanValue.of(res);
	}

	@Override
	public BooleanValue eqv(Value other) {
		if(other == null) return this;
		BooleanValue val2 = (BooleanValue) other;
		boolean res = value == val2.value;
		if(!res) return null;
		return BooleanValue.of(res);
	}

	@Override
	public boolean compare(int relation, Value other) {
		boolean LHS = this.value;
		boolean RHS = (other == null)? false : ((BooleanValue)other).value;
		boolean res = false;
		switch(relation) {
//			case Scode.S_LT: res = LHS <  RHS; break;
//			case Scode.S_LE: res = LHS <= RHS; break;
			case Scode.S_EQ: res = LHS == RHS; break;
//			case Scode.S_GE: res = LHS >= RHS; break;
//			case Scode.S_GT: res = LHS >  RHS; break;
			case Scode.S_NE: res = LHS != RHS; break;
			default: Util.IERR("Undefined relation");
		}
//		IO.println("BooleanValue.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
//		Util.IERR("");
		return res;
	}

	@Override
//	public void print(final String indent) {
//		IO.println(indent + toString());
//	}
	
	public String toString() {
		return "" + value;
	}
	
	
	// ***********************************************************************************************
	// *** TESTING
	// ***********************************************************************************************
//	public static void main(String[] args) {
//		int nErr = 0;
//		BooleanValue vTrue = BooleanValue.of(true);
//
//		nErr += TEST(vTrue, Scode.S_EQ, null, false);
//		nErr += TEST(vTrue, Scode.S_EQ, vTrue, true);		
//
//		nErr += TEST(vTrue, Scode.S_NE, null, true);
//		nErr += TEST(vTrue, Scode.S_NE, vTrue, false);		
//		
//		IO.println("Number of errors: " + nErr);
//	}
//	
//	private static int TEST(BooleanValue lhs, int relation, BooleanValue rhs, boolean expected) {
//		boolean b = lhs.compare(relation, rhs);
//		if(b != expected) {
//			IO.println("TEST: " + lhs + " " + Scode.edInstr(relation) + " " + rhs + " ==> " + b);
//			return 1;
//		}
//		return 0;
//	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind((value)?Scode.S_TRUE:Scode.S_FALSE);
	}

	public static BooleanValue read(AttributeInputStream inpt) throws IOException {
		inpt.readInstr();
		return new BooleanValue(inpt.curinstr==Scode.S_TRUE);
	}


}
