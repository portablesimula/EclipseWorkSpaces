package bec.segment;

import java.io.IOException;
import java.util.HashMap;

import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ProgramAddress;

public class Segment { // extends Descriptor {
	public String ident;
	protected int segmentKind; // K_SEG_DATA, K_SEG_CONST, K_SEG_CODE
	private int segmentIndex;
	private static int SEQU = 1;

	public Segment(String ident, int segmentKind) {
		if(Global.SEGMAP.get(ident) != null) Util.IERR("Segment allready defined: " + ident);
		this.ident = ident.toUpperCase();
		Global.SEGMAP.put(this.ident, this);
		this.segmentKind = segmentKind;
		this.segmentIndex = SEQU++;
	}
	
	public int segIndex() {
		return this.segmentIndex;
	}

	public static Segment find(String ident) {
		return Global.SEGMAP.get(ident);
	}

	public static Segment lookup(String ident) {
		Segment seg = Global.SEGMAP.get(ident);
		if(seg == null) {
//			find("PSEG_FIL_FOPEN:BODY").dump("Segment.lookup: ");
//			Global.PSEG.dump("Segment.lookup: ");
//			Segment.listAll();
			Util.IERR("Can't find Segment \"" + ident + '"');
		}
		return seg;
	}

	public static int getIndex(String ident) {
		Segment seg = Global.SEGMAP.get(ident);
		if(seg == null) {
			return 0;
		}
		return seg.segmentIndex;
	}

	
	public static boolean compare(String LHSegID, int lhs, int relation, String RHSegID, int rhs) {
		if(equals(LHSegID, RHSegID)) {
			return Relation.compare(lhs, relation, rhs);
		} else {
			int LHS = lhs + Segment.getIndex(LHSegID) << 16;
			int RHS = rhs + Segment.getIndex(RHSegID) << 16;
			boolean res = false;
			switch(relation) {
				case Scode.S_LT: res = LHS <  RHS; break;
				case Scode.S_LE: res = LHS <= RHS; break;
				case Scode.S_EQ: res = LHS == RHS; break;
				case Scode.S_GE: res = LHS >= RHS; break;
				case Scode.S_GT: res = LHS >  RHS; break;
				case Scode.S_NE: res = LHS != RHS; break;
				//default: Util.IERR("");
			}
//			System.out.println("Segment.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
//			Util.IERR("");
			return res;		
		}
	}
	
	private static boolean equals(String s1, String s2) {
		if(s1 == null) return s2 == null;
		return s1.equals(s2);
	}

//	public static void writeSegments(AttributeOutputStream oupt) {
//		listAll();
//		try {
//			for(Segment seg:SEGMAP.values()) {
//				if(!seg.inserted)
//					seg.write(oupt);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			Util.IERR("");
//		}
////		Util.IERR("");
//	}
	
	public static void listAll() {
		for(Segment seg:Global.SEGMAP.values()) {
			System.out.println("   " + seg);
		}
	}

	public void dump(String title) {
	}
	
	public void dump(String title,int from,int to) {
	}
	
	public static void dumpAll(String title) {
		for(Segment seg:Global.SEGMAP.values()) {
			seg.dump(title);
		}
	}
	
	public String toString() {
		return Kind.edKind(segmentKind) + ':' + segmentKind + " \"" + ident + '"';
	}

	
	// ***********************************************************************************************
	// *** TESTING
	// ***********************************************************************************************

	public static void main(String[] args) {
		System.out.println("BEGIN TEST Segment.compare: ");
		Global.SEGMAP = new HashMap<String, Segment>();
		Global.CSEG = new DataSegment("CSEG", Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG", Kind.K_SEG_DATA);
		
		ProgramAddress seg1Low  = new ProgramAddress(Type.T_PADDR, "DSEG", 12);
		ProgramAddress seg1High = new ProgramAddress(Type.T_PADDR, "DSEG", 99);
		ProgramAddress seg2Low  = new ProgramAddress(Type.T_PADDR, "CSEG", 12);
		ProgramAddress seg2High = new ProgramAddress(Type.T_PADDR, "CSEG", 99);
		int nErr = 0;

		nErr += TEST(seg1Low, Scode.S_LT, seg1High, true);
		nErr += TEST(seg1Low, Scode.S_LE, seg1High, true);
		nErr += TEST(seg1Low, Scode.S_EQ, seg1High, false);
		nErr += TEST(seg1Low, Scode.S_GE, seg1High, false);
		nErr += TEST(seg1Low, Scode.S_GT, seg1High, false);
		nErr += TEST(seg1Low, Scode.S_NE, seg1High, true);

		nErr += TEST(null, Scode.S_EQ, seg1High, false);
		nErr += TEST(null, Scode.S_NE, seg1High, true);

		nErr += TEST(seg1Low, Scode.S_EQ, null, false);
		nErr += TEST(seg1Low, Scode.S_NE, null, true);
		
		nErr += TEST(null, Scode.S_LT, null, false);
		nErr += TEST(null, Scode.S_LE, null, true);
		nErr += TEST(null, Scode.S_EQ, null, true);
		nErr += TEST(null, Scode.S_GE, null, true);
		nErr += TEST(null, Scode.S_GT, null, false);
		nErr += TEST(null, Scode.S_NE, null, false);

		nErr += TEST(seg2Low, Scode.S_EQ, seg1High, false);
		nErr += TEST(seg2Low, Scode.S_NE, seg1High, true);
		
		System.out.println("ENDOF TEST Segment.compare: nErr = " + nErr);		
	}
	
	private static int TEST(ProgramAddress LHS, int relation, ProgramAddress RHS, boolean expected) {
		String LHSegID = (LHS == null)? null : ((ProgramAddress)LHS).segID;
		int lhs = (LHS == null)? 0 : ((ProgramAddress)LHS).getOfst();
		String RHSegID = (RHS == null)? null : ((ProgramAddress)RHS).segID;
		int rhs = (RHS == null)? 0 : ((ProgramAddress)RHS).getOfst();
		if(Segment.compare(LHSegID, lhs, relation, RHSegID, rhs) != expected) {
			System.out.println("ERROR: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + (! expected));
			return 1;
		}
		return 0;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

//	public static Descriptor read(AttributeInputStream inpt, int kind) throws IOException {
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
//		return(null);
//	}


}
