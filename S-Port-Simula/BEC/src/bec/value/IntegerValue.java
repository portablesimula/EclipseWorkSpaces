package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Attribute;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

public class IntegerValue extends Value {
	public int value;
	
	private IntegerValue(Type type, int value) {
//		this.type = Type.T_INT;
		this.type = type;
		this.value = value;
	}
	
	public static IntegerValue of(Type type, int value) {
		if(value != 0) return new IntegerValue(type, value);
		return null;
	}

	/**
	 * integer_value   ::= c-int integer_literal:string
	 * 
	 * character_value ::= c-char byte
	 * 
	 * size_value
	 * 		::= c-size type
	 * 		::= NOSIZE
	 * 
	 * attribute_address	::= c-aaddr attribute:tag
	 */
	public static IntegerValue ofScode_INT() {
		return IntegerValue.of(Type.T_INT, Integer.valueOf(Scode.inString()));
	}
	public static IntegerValue ofScode_CHAR() {
		return IntegerValue.of(Type.T_CHAR, Scode.inByte());
	}
	
	public static IntegerValue ofScode_SIZE() {
		Type type = Type.ofScode();
		return IntegerValue.of(Type.T_SIZE, type.size());
	}

	public static IntegerValue ofScode_AADDR() {
		Tag tag = Tag.ofScode();
//		Variable var = (Variable) tag.getMeaning();
		Attribute var = (Attribute) tag.getMeaning();
		if(var == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
//		Util.IERR("NOT IMPL");
//		return IntegerValue.of(Type.T_AADDR, var.address.ofst);
		return IntegerValue.of(Type.T_AADDR, var.rela);
	}
	
	public static int intValue(IntegerValue val) {
		if(val == null) return 0;
		return val.value;
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
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
		return IntegerValue.of(this.type,- value);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		if(other instanceof GeneralAddress gaddr) {
			return gaddr.add(this);
		} else {
			IntegerValue val2 = (IntegerValue) other;
			int res = value + val2.value;
			if(res == 0) return null;
			return IntegerValue.of(this.type, res);
		}
	}

	@Override
	public Value sub(Value other) {
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = this.value - val2.value;
		} else res = this.value;
//		System.out.println("IntegerValue.sub: " + this.value + " - " + other + " = " + res);
		if(res == 0) return null;
		return IntegerValue.of(this.type, res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
		IntegerValue val2 = (IntegerValue) other;
		int res = value * val2.value;
		if(res == 0) return null;
		return IntegerValue.of(this.type, res);
	}

	@Override
	public Value div(Value other) {
//		System.out.println("IntegerValue.div: " + other + " / " + this.value);
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = val2.value / this.value;
		} else res = 0;
//		System.out.println("IntegerValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return IntegerValue.of(this.type, res);
	}

	@Override
	public Value rem(Value other) {
//		System.out.println("IntegerValue.div: " + other + " / " + this.value);
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = val2.value % this.value;
		} else res = 0;
//		System.out.println("IntegerValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return IntegerValue.of(this.type, res);
	}

	@Override
	public Value and(Value other) {
		int val2 = (other == null)? 0 : ((IntegerValue) other).value;
		return IntegerValue.of(this.type, value & val2);
	}

	@Override
	public Value or(Value other) {
		int val2 = (other == null)? 0 : ((IntegerValue) other).value;
		return IntegerValue.of(this.type, value | val2);
	}

	@Override
	public Value xor(Value other) {
		int val2 = (other == null)? 0 : ((IntegerValue) other).value;
		return IntegerValue.of(this.type, value ^ val2);
	}

	@Override
	public boolean compare(int relation, Value other) {
		int LHS = this.value;
		int RHS = (other == null)? 0 : ((IntegerValue)other).value;
		return Relation.compare(LHS, relation, RHS);
//		boolean res = false;
//		switch(relation) {
//			case Scode.S_LT: res = LHS <  RHS; break;
//			case Scode.S_LE: res = LHS <= RHS; break;
//			case Scode.S_EQ: res = LHS == RHS; break;
//			case Scode.S_GE: res = LHS >= RHS; break;
//			case Scode.S_GT: res = LHS >  RHS; break;
//			case Scode.S_NE: res = LHS != RHS; break;
//		}
////		System.out.println("IntegerValue.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
////		Util.IERR("");
//		return res;
	}


//	 * Signed Left Shift	<<	The left shift operator moves all bits by a given number of bits to the left.
//	 * Signed Right Shift	>>	The right shift operator moves all bits by a given number of bits to the right.
//	 * Unsigned Right Shift	>>>	It is the same as the signed right shift, but the vacant leftmost position is filled with 0 instead of the sign bit.
	public Value shift(int instr, Value other) {
		int LHS = this.value;
		int RHS = (other == null)? 0 : ((IntegerValue)other).value;
//		System.out.println("IntegerValue.shift: " + LHS + " " + Scode.edInstr(instr) + " " + RHS);
		int res = 0;
		switch(instr) {
		case Scode.S_LSHIFTA,
			 Scode.S_LSHIFTL: res = LHS << RHS; break;
		case Scode.S_RSHIFTA: res = LHS >> RHS; break;
		case Scode.S_RSHIFTL: res = LHS >>> RHS; break;
		default: Util.IERR("");
		}
//		System.out.println("IntegerValue.shift: " + LHS + " " + Scode.edInstr(instr) + " " + RHS + " ==> " + res);
//		Util.IERR("");
		return IntegerValue.of(type.T_INT, res);
	}

	public String toString() {
		if(type == null) return ""+value;
		switch(type.tag) {
			case Scode.TAG_INT:   return "INT:"   + value;
			case Scode.TAG_CHAR:  return "CHAR:"  + (char)value;
			case Scode.TAG_SIZE:  return "SIZE:"  + value;
			case Scode.TAG_AADDR: return "FIELD:" + value;
			default: return "C-" +type + " " + value; 
		}
	}
	
	
	// ***********************************************************************************************
	// *** TESTING
	// ***********************************************************************************************
//	public static void main(String[] args) {
//		int nErr = 0;
//		IntegerValue v44 = IntegerValue.of(Type.T_INT, 44);
//		nErr += TEST(v44, Scode.S_LT, null, false);
//		nErr += TEST(v44, Scode.S_LT, v44, false);	
//		
//		nErr += TEST(v44, Scode.S_LE, null, false);
//		nErr += TEST(v44, Scode.S_LE, v44, true);
//		
//		nErr += TEST(v44, Scode.S_EQ, null, false);
//		nErr += TEST(v44, Scode.S_EQ, v44, true);
//		
//		nErr += TEST(v44, Scode.S_GE, null, true);
//		nErr += TEST(v44, Scode.S_GE, v44, true);	
//		
//		nErr += TEST(v44, Scode.S_GT, null, true);
//		nErr += TEST(v44, Scode.S_GT, v44, false);	
//		
//		nErr += TEST(v44, Scode.S_NE, null, true);
//		nErr += TEST(v44, Scode.S_NE, v44, false);
//		
//		System.out.println("Number of errors: " + nErr);
//	}
//	
//	private static int TEST(IntegerValue lhs, int relation, IntegerValue rhs, boolean expected) {
//		boolean b = lhs.compare(relation, rhs);
//		if(b != expected) {
//			System.out.println("ERROR: " + lhs + " " + Scode.edInstr(relation) + " " + rhs + " ==> " + b);
//			return 1;
//		}
//		return 0;
//	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private IntegerValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.read(inpt);
		value = inpt.readInt();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_INT);
		type.write(oupt);
		oupt.writeInt(value);
	}

	public static IntegerValue read(AttributeInputStream inpt) throws IOException {
		return new IntegerValue(inpt);
	}


}
