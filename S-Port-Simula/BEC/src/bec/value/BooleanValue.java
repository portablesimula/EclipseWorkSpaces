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
import bec.util.Option;
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
	public void emit(DataSegment dseg) {
		dseg.emit(this);
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
			case Scode.S_EQ: res = LHS == RHS; break;
			case Scode.S_NE: res = LHS != RHS; break;
			default: Util.IERR("Undefined relation");
		}
		return res;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind((value)?Scode.S_TRUE:Scode.S_FALSE);
	}

	public static BooleanValue read(AttributeInputStream inpt) throws IOException {
		return new BooleanValue(inpt.readKind() == Scode.S_TRUE);
	}


}
