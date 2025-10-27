/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import java.io.IOException;

import bec.segment.DataSegment;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class Value {
	public Type type;

	/**
	 * Utility print method.
	 * 
	 * @param indent number of spaces leading the lines
	 */
	public void print(final String indent) {
		IO.println(this.getClass().getSimpleName() + ".print: " +  toString());
	}

	public void emit(DataSegment dseg) {
		Util.IERR("Method 'emit' need a redefinition in " + this.getClass().getSimpleName());
	}

	public float toFloat() {
		Util.IERR("Method 'toFloat' need a redefinition in " + this.getClass().getSimpleName());
		return 0;
	}

	public double toDouble() {
		Util.IERR("Method 'toDouble' need a redefinition in " + this.getClass().getSimpleName());
		return 0;
	}

	public Value copy() {
		Util.IERR("Method 'copy' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value neg() {
		Util.IERR("Method 'neg' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value add(Value other) {
		Util.IERR("Method 'add' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value sub(Value other) {
		Util.IERR("Method 'sub' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value mult(Value other) {
		Util.IERR("Method 'mult' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value div(Value other) {
		Util.IERR("Method 'div' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value rem(Value other) {
		Util.IERR("Method 'rem' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value and(Value other) {
		Util.IERR("Method 'and' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value or(Value other) {
		Util.IERR("Method 'or' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value xor(Value other) {
		Util.IERR("Method 'xor' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value imp(Value other) {
		Util.IERR("Method 'imp' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public Value eqv(Value other) {
		Util.IERR("Method 'eqv' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	public boolean compare(int relation, Value other) {
		Util.IERR("Method 'compare' need a redefinition in " + this.getClass().getSimpleName());
		return false;
	}

	public Value shift(int instr, Value other) {
		Util.IERR("Method 'shift' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static Value read(AttributeInputStream inpt) throws IOException {
		Value val = read1(inpt);
//		IO.println("Value.read: " + val);
		return val;
	}
	
	private static Value read1(AttributeInputStream inpt) throws IOException {
		int kind = inpt.readUnsignedByte();
//		IO.println("Value.read: kind="+Scode.edInstr(kind));
		switch(kind) {
			case Scode.S_NULL:		return null;
			case Scode.S_TRUE:		return BooleanValue.of(true);
			case Scode.S_FALSE:		return BooleanValue.of(false);
			case Scode.S_C_INT, Scode.S_C_CHAR, Scode.S_C_SIZE, Scode.S_C_AADDR: return IntegerValue.read(inpt);
			case Scode.S_C_REAL:	return RealValue.read(inpt);
			case Scode.S_C_LREAL:	return LongRealValue.read(inpt);
			case Scode.S_TEXT:		return TextValue.read(inpt);
			case Scode.S_STRING:	return StringValue.read(inpt);
			case Scode.S_C_RECORD:	return RecordValue.read(inpt);
			case Scode.S_C_OADDR:	return ObjectAddress.read(inpt);
			case Scode.S_C_GADDR:	return GeneralAddress.read(inpt);
			case Scode.S_C_PADDR, Scode.S_C_RADDR: return ProgramAddress.read(inpt);
//			case Scode.S_C_DOT:		return DotAddress.read(inpt);
			default: Util.IERR("MISSING: " + Scode.edInstr(kind)); return null;
		}
	}
	
	public static Value read(int kind, AttributeInputStream inpt) throws IOException {
//		IO.println("Value.read: kind="+Scode.edInstr(kind));
		switch(kind) {
			case Scode.S_NULL:		return null;
			case Scode.S_TRUE:		return BooleanValue.of(true);
			case Scode.S_FALSE:		return BooleanValue.of(false);
			case Scode.S_C_INT, Scode.S_C_CHAR, Scode.S_C_SIZE, Scode.S_C_AADDR: return IntegerValue.read(inpt);
			case Scode.S_C_REAL:	return RealValue.read(inpt);
			case Scode.S_C_LREAL:	return LongRealValue.read(inpt);
			case Scode.S_TEXT:		return TextValue.read(inpt);
			case Scode.S_STRING:	return StringValue.read(inpt);
			case Scode.S_C_RECORD:	return RecordValue.read(inpt);
			case Scode.S_C_OADDR:	return ObjectAddress.read(inpt);
			case Scode.S_C_GADDR:	return GeneralAddress.read(inpt);
			case Scode.S_C_PADDR, Scode.S_C_RADDR: return ProgramAddress.read(inpt);
//			case Scode.S_C_DOT:		return DotAddress.read(inpt);
			default: Util.IERR("MISSING: " + Scode.edInstr(kind)); return null;
		}
	}


}
