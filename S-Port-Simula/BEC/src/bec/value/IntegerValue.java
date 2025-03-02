package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Attribute;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

public class IntegerValue extends Value {
	public int value;
	
	public IntegerValue(Type type, int value) {
		this.type = Type.T_INT;
		this.value = value;
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
		return new IntegerValue(Type.T_INT, Integer.valueOf(Scode.inString()));
	}
	public static IntegerValue ofScode_CHAR() {
		return new IntegerValue(Type.T_CHAR, Scode.inByte());
	}
	
	public static IntegerValue ofScode_SIZE() {
		Type type = Type.ofScode();
		return new IntegerValue(Type.T_SIZE, type.size());
	}

	public static IntegerValue ofScode_AADDR() {
		Tag tag = Tag.ofScode();
//		Variable var = (Variable) tag.getMeaning();
		Attribute var = (Attribute) tag.getMeaning();
		if(var == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
//		Util.IERR("NOT IMPL");
//		return new IntegerValue(Type.T_AADDR, var.address.ofst);
		return new IntegerValue(Type.T_AADDR, var.rela);
	}
	
	public static int intValue(IntegerValue val) {
		if(val == null) return 0;
		return val.value;
	}

	@Override
	public Value neg() {
		return new IntegerValue(this.type,- value);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		IntegerValue val2 = (IntegerValue) other;
		int res = value + val2.value;
		if(res == 0) return null;
		return new IntegerValue(this.type, res);
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
		return new IntegerValue(this.type, res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
		IntegerValue val2 = (IntegerValue) other;
		int res = value * val2.value;
		if(res == 0) return null;
		return new IntegerValue(this.type, res);
	}

	@Override
	public Value div(Value other) {
		System.out.println("IntegerValue.div: " + other + " / " + this.value);
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = val2.value / this.value;
		} else res = 0;
		System.out.println("IntegerValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return new IntegerValue(this.type, res);
	}

	public String toString() {
		return "C-INT " + value; // + "   " + (char)value;
	}
	

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
