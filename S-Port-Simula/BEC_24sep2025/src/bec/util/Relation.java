package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.value.BooleanValue;
import bec.value.IntegerValue;
import bec.value.Value;

public class Relation {
	public int relation;
	
	private static final boolean DEBUG = false;

	public Relation(int relation) {
		this.relation = relation;
	}

	/**
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public static Relation ofScode() {
		Scode.inputInstr();
		Relation rel = new Relation(Scode.curinstr);
		if(DEBUG) {
			IO.println("Relation.ofScode: CurInstr="+Scode.curinstr);
			IO.println("Relation.ofScode: relation="+Scode.edInstr(rel.relation));
		}
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
	
	public Relation rev() {
		switch(this.relation) {
		case Scode.S_LT: return new Relation(Scode.S_GT); // lhs <  rhs   ==   rhs >  lhs
		case Scode.S_LE: return new Relation(Scode.S_GE); // lhs <= rhs   ==   rhs >= lhs
		case Scode.S_EQ: return new Relation(Scode.S_EQ); // lhs == rhs   ==   rhs == lhs
		case Scode.S_GE: return new Relation(Scode.S_LE); // lhs >= rhs   ==   rhs <= lhs
		case Scode.S_GT: return new Relation(Scode.S_LT); // lhs >  rhs   ==   rhs <  lhs
		case Scode.S_NE: return new Relation(Scode.S_NE); // lhs != rhs   ==   rhs != lhs
		}
		Util.IERR("Illegal Relation: " + this);
		return this;
	}
	
	public boolean compare(Value lhs, Value rhs) {
		boolean res = false;
		if(lhs != null) {
			res = lhs.compare(relation, rhs);
		} else if(rhs != null) {
			res = rhs.compare(rev().relation, lhs);
		} else {
			switch(relation) {
				case Scode.S_LT: res = /* 0 < 0  */ false; break;
				case Scode.S_LE: res = /* 0 <= 0 */ true; break;
				case Scode.S_EQ: res = /* 0 == 0 */ true; break;
				case Scode.S_GE: res = /* 0 >= 0 */ true; break;
				case Scode.S_GT: res = /* 0 > 0  */ false; break;
				case Scode.S_NE: res = /* 0 != 0 */ false; break;
			}
		}
//		IO.println("Relation.compare: " + lhs + " " + this + " " + rhs + " ==> " + res);
		return res;
	}
	
	public static boolean compare(int LHS, int relation, int RHS) {
		boolean res = false;
		switch(relation) {
			case Scode.S_LT: res = LHS <  RHS; break;
			case Scode.S_LE: res = LHS <= RHS; break;
			case Scode.S_EQ: res = LHS == RHS; break;
			case Scode.S_GE: res = LHS >= RHS; break;
			case Scode.S_GT: res = LHS >  RHS; break;
			case Scode.S_NE: res = LHS != RHS; break;
		}
//		IO.println("Segment.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
//		Util.IERR("");
		return res;		
	}
	
	public String toString() {
		return Scode.edInstr(relation);
	}
	
	// ***********************************************************************************************
	// *** TESTING
	// ***********************************************************************************************
	
	public static void main(String[] args) {
		int nErr = 0;
		Value vTrue = BooleanValue.of(true);
		Value v44 = IntegerValue.of(Type.T_INT,44);
		Value v66 = IntegerValue.of(Type.T_INT,66);
		
//		TEST_Boolean(Scode.S_EQ);
		nErr += TEST(null,  Scode.S_EQ, null, true);
		nErr += TEST(vTrue, Scode.S_EQ, null, false);
		nErr += TEST(null,  Scode.S_EQ, vTrue, false);
		nErr += TEST(vTrue, Scode.S_EQ, vTrue, true);		

//		TEST_Boolean(Scode.S_NE);
		nErr += TEST(null,  Scode.S_NE, null, false);
		nErr += TEST(vTrue, Scode.S_NE, null, true);
		nErr += TEST(null,  Scode.S_NE, vTrue, true);
		nErr += TEST(vTrue, Scode.S_NE, vTrue, false);		

//		TEST_Integer(Scode.S_LT);
		nErr += TEST(null, Scode.S_LT, null, false);
		nErr += TEST(v44,  Scode.S_LT, null, false);
		nErr += TEST(null, Scode.S_LT, v44, true);
		nErr += TEST(v66,  Scode.S_LT, v44, false);
		nErr += TEST(v66,  Scode.S_LT, v66, false);

//		TEST_Integer(Scode.S_LE);
		nErr += TEST(null, Scode.S_LE, null, true);
		nErr += TEST(v44,  Scode.S_LE, null, false);
		nErr += TEST(null, Scode.S_LE, v44, true);
		nErr += TEST(v66,  Scode.S_LE, v44, false);
		nErr += TEST(v66,  Scode.S_LE, v66, true);
		
//		TEST_Integer(Scode.S_EQ);
		nErr += TEST(null, Scode.S_EQ, null, true);
		nErr += TEST(v44,  Scode.S_EQ, null, false);
		nErr += TEST(null, Scode.S_EQ, v44, false);
		nErr += TEST(v66,  Scode.S_EQ, v44, false);
		nErr += TEST(v66,  Scode.S_EQ, v66, true);
		
//		TEST_Integer(Scode.S_GE);
		nErr += TEST(null, Scode.S_GE, null, true);
		nErr += TEST(v44,  Scode.S_GE, null, true);
		nErr += TEST(null, Scode.S_GE, v44, false);
		nErr += TEST(v66,  Scode.S_GE, v44, true);
		nErr += TEST(v66,  Scode.S_GE, v66, true);
		
//		TEST_Integer(Scode.S_GT);
		nErr += TEST(null, Scode.S_GT, null, false);
		nErr += TEST(v44,  Scode.S_GT, null, true);
		nErr += TEST(null, Scode.S_GT, v44, false);
		nErr += TEST(v66,  Scode.S_GT, v44, true);
		nErr += TEST(v66,  Scode.S_GT, v66, false);
		
//		TEST_Integer(Scode.S_NE);
		nErr += TEST(null, Scode.S_NE, null, false);
		nErr += TEST(v44,  Scode.S_NE, null, true);
		nErr += TEST(null, Scode.S_NE, v44, true);
		nErr += TEST(v66,  Scode.S_NE, v44, true);
		nErr += TEST(v66,  Scode.S_NE, v66, false);
		

		IO.println("Number of errors: " + nErr);
	}
	
	private static int TEST(Value lhs, int code, Value rhs, boolean expected) {
		Relation rel = new Relation(code);
		boolean b = rel.compare(lhs, rhs);
		if(b != expected) {
			IO.println("TEST: " + lhs + " " + rel + " " + rhs + " ==> " + b);
			return 1;
		}
		return 0;
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
