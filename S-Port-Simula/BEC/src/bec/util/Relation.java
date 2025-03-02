package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.value.IntegerValue;
import bec.value.Value;

public class Relation {
	int relation;
	
	private Relation(int relation) {
		this.relation = relation;
	}

	/**
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public static Relation ofScode() {
		Scode.inputInstr();
		Relation rel = new Relation(Scode.curinstr);
		System.out.println("Relation.ofScode: CurInstr="+Scode.edInstr(Scode.curinstr));
		System.out.println("Relation.ofScode: relation="+Scode.edInstr(rel.relation));
		switch(rel.relation) {
			case Scode.S_LT, Scode.S_LE, Scode.S_EQ,
			     Scode.S_GE, Scode.S_GT, Scode.S_NE: break; // OK
			default: Util.IERR("Illegal Relation: " + rel.relation);
		}
		return rel;
	}
	
	public Relation not() {
		switch(this.relation) {
		case Scode.S_LT: return new Relation(Scode.S_GE);
		case Scode.S_LE: return new Relation(Scode.S_GT);
		case Scode.S_EQ: return new Relation(Scode.S_NE);
		case Scode.S_GE: return new Relation(Scode.S_LT);
		case Scode.S_GT: return new Relation(Scode.S_LE);
		case Scode.S_NE: return new Relation(Scode.S_EQ);
		}
		Util.IERR("Illegal Relation: " + this);
		return this;
	}
	
	public boolean eval(Value v1, Value v2) {
		if(v1.type == Type.T_INT) {
			int val1 = (v1 == null)? 0 : ((IntegerValue)v1).value;
			int val2 = (v2 == null)? 0 : ((IntegerValue)v2).value;
			switch(relation) {
			case Scode.S_LT: return val1 < val2;
			case Scode.S_LE: return val1 <= val2;
			case Scode.S_EQ: return val1 == val2;
			case Scode.S_GE: return val1 >= val2;
			case Scode.S_GT: return val1 > val2;
			case Scode.S_NE: return val1 != val2;
			default: Util.IERR("Illegal Relation: " + relation);
		}
			Util.IERR("");
		}
		Util.IERR(""+v1.type+"  "+v1.type.tag);
		return false;
	}
	
	public String toString() {
		return Scode.edInstr(relation);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private Relation(AttributeInputStream inpt) throws IOException {
		relation = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(relation);
	}

	public static Relation read(AttributeInputStream inpt) throws IOException {
		return new Relation(inpt);
	}

}
