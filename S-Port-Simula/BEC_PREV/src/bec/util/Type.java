package bec.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.descriptor.RecordDescr;

public class Type {
	public  int tag;
	private int size;  // Size of type in basic cells
	private int rep0size;  // Size of rep 0 attribute in basic cells
	String comment;

	private static HashMap<Integer, Type> TMAP;
	private static Vector<Type> RECTYPES;
	public static Type T_VOID,  T_TEXT,  T_STRING, T_BOOL,  T_CHAR;
	public static Type T_INT,   T_SINT,  T_REAL,   T_LREAL, T_SIZE;
	public static Type T_OADDR, T_AADDR, T_GADDR,  T_PADDR, T_RADDR;
	

	/**
	 *	 type ::= structured_type | simple_type | range_type
	 * 
	 *	 	simple_type ::= BOOL | CHAR | INT | REAL | LREAL | SIZE | OADDR | AADDR | GADDR | PADDR | RADDR
	 * 
	 *	 	structured_type ::= record_tag:tag
	 *
	 *		 range_type
	 *			::= INT range lower:number upper:number  -- NOTE: DETTE ER NYTT
	 *			::= SINT                                 -- NOTE: DETTE ER NYTT
	 *
	 */
//	public Type() {
	public static Type ofScode() {
		int tag = Scode.ofScode();
		if(tag == Scode.TAG_INT) {
			if(Scode.accept(Scode.S_RANGE)) {
				//range = new Range();
				Scode.inNumber(); // low
				Scode.inNumber(); // high
			}
		}
//		if(Scode.accept(Scode.S_FIXREP)) {
//			int fixrep = Scode.inNumber();
//			System.out.println("Type.ofScode: "+Scode.edTag(tag)+" FIXREP "+fixrep);
////			Descriptor descr = tag.getMeaning();
//			dumpTypes("Type.ofScode: ");
//			Type type = TMAP.get(tag);
//			System.out.println("Type.ofScode: type="+type);
//
//			Util.IERR("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
//			System.out.println("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
//		}
//		System.out.println("NEW Type.ofScode: " + Scode.edTag(tag));
		Type type = TMAP.get(tag);
//		Util.IERR("SJEKK DETTE: " + type);
		if(type == null) {
			Util.IERR("Illegal type: " + Scode.edTag(tag));
		}
		return type;
	}
	
	private Type(int tag, int size) {
		this.tag = tag;
		this.size = size;
	}

	public static Type lookupType(RecordDescr rec) {
		Type type = TMAP.get(rec.tag.val);
		if(type == null) Util.IERR("Type.recType: UNKNOWN: " + rec);
		
//		rec.print("Type.lookupType: ");
//		System.out.println("Type.lookupType: type=" + type);
		
		return type;
	}

	public static void newRecType(RecordDescr rec) {
//		System.out.println("DataType.newRecType: " + Scode.edTag(rec.tag.val) + ", size="+rec.size);
		Type type = new Type(rec.tag.val, rec.size);
		type.rep0size = rec.nbrep;
		type.comment = "From " + rec;
		Type old = TMAP.get(rec.tag.val);
		if(old != null) {
//			System.out.println("DataType.newRecType: old=" + old);
			if(rec.tag.val != Scode.TAG_STRING)	Util.IERR("Already defined: " + type);
		} else {
//			if(rec.tag.val == 2483) Util.IERR("");
			TMAP.put(rec.tag.val, type);
			RECTYPES.add(type);
		}
	}
	
	public static void removeFromTMAP(int tag) {
		TMAP.remove(tag);
	}

	public boolean isSimple() {
		return tag <= Scode.TAG_SIZE;
	}

	public boolean isRecordType() {
		return tag > Scode.T_max;
	}
	
	public boolean isArithmetic() {
		switch(tag) {
		case Scode.TAG_INT, Scode.TAG_REAL, Scode.TAG_LREAL: return true;
		}
		return false;
	}
	
	
	public int size() {
		return size;
	}
	
	
	public static void init() {
		TMAP = new HashMap<Integer, Type>();
		RECTYPES = new Vector<Type>();		

		// type                    tag         size )
		T_VOID   = newBasType(Scode.TAG_VOID,   0   );
		T_TEXT   = newBasType(Scode.TAG_TEXT,   3   );
		T_STRING = newBasType(Scode.TAG_STRING, 3   );
		T_BOOL   = newBasType(Scode.TAG_BOOL,   1   );
		T_CHAR   = newBasType(Scode.TAG_CHAR,   1   );
		T_INT    = newBasType(Scode.TAG_INT,    1   );
		T_SINT   = newBasType(Scode.TAG_SINT,   1   );
		T_REAL   = newBasType(Scode.TAG_REAL,   1   );
		T_LREAL  = newBasType(Scode.TAG_LREAL,  1   );
		T_SIZE   = newBasType(Scode.TAG_SIZE,   1   );
		T_OADDR  = newBasType(Scode.TAG_OADDR,  1   );
		T_AADDR  = newBasType(Scode.TAG_AADDR,  1   );
		T_GADDR  = newBasType(Scode.TAG_GADDR,  2   );
		T_PADDR  = newBasType(Scode.TAG_PADDR,  1   );
		T_RADDR  = newBasType(Scode.TAG_RADDR,  1   );
	}

	private static Type newBasType(int tag, int size) {
		Type type = new Type(tag, size);
		if(tag == 2483) Util.IERR("");
		TMAP.put(tag, type);
		return type;
	}

	public static void dumpTypes(String title) {
		System.out.println("============ "+title+" BEGIN Dump Types ================");
		for(Integer type:TMAP.keySet()) {
			System.out.println("TTAB["+type+"] = " + TMAP.get(type));
		}
		for(Type type:RECTYPES) {
			System.out.println("Record TYPE = " + type);
			
		}
		System.out.println("============ "+title+" ENDOF Dump Types ================");
	}
	
	public String toString() {
		return Scode.edTag(tag) + " size=" + size + ", rep0size=" + rep0size + " " + comment;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public static void writeRECTYPES(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("writeRECTYPES: ");
		oupt.writeKind(Kind.K_RECTYPES);
		oupt.writeShort(RECTYPES.size());
		for(Type type:RECTYPES) {
//			System.out.println("Type.writeRECTYPES: " + type.tag );
			oupt.writeTagID(type.tag);
			oupt.writeShort(type.size);
			type.comment = "From " + BecGlobal.currentModule;
		}
//		Util.IERR("");
	}

	public static void readRECTYPES(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			int tag = inpt.readTagID();
			int size = inpt.readShort();
			Type type = new Type(tag, size);
			
			if(tag == Scode.TAG_STRING) ; // OK Predefinert
			else if(TMAP.get(tag) ==null) {
//				if(tag == 2483) {
//					System.out.println("Type.readRECTYPES: NEW Type: " + type + ", tag=" + tag);
////					Util.IERR("");
//				}
				TMAP.put(tag, type);
				RECTYPES.add(type);
//				Type.dumpTypes("Type.readRECTYPES: ");
			}
//			System.out.println("Type.readRECTYPES: NEW Type: " + type);
//			Util.IERR("");
		}
//		Type.dumpTypes("Type.readRECTYPES: ");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
	}

	public static Type read(AttributeInputStream inpt) throws IOException {
		int tag = inpt.readTag();
//		System.out.println("NEW Type(inpt): " + Scode.edInstr(tag));
		Type type = TMAP.get(tag);
		if(type == null) Util.IERR("SJEKK DETTE");
		return type;
	}


}
