package bec.util;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.descriptor.RecordDescr;

public class Type {
	public  int tag;
	private int size;  // Size of type in basic cells
	public  BitSet pntmap; // NULL:no pointers, else: Reladdr of pointers
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
//			Scode.inNumber();
////			Util.IERR("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
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
	
	private Type(int tag, int size, int rela) {
		this.tag = tag;
		this.size = size;
		
	}

	public static Type lookupType(RecordDescr rec) {
		Type type = TMAP.get(rec.tag.val);
		if(type == null) Util.IERR("Type.recType: UNKNOWN: " + rec);
		
		rec.print("Type.lookupType: ");
		System.out.println("Type.lookupType: type=" + type);
		
		return type;
	}

	public static void newRecType(RecordDescr rec) {
//		Type x = new Type(rec.size, null, rec.tag.toString());
		Type type = new Type(rec.tag.val, rec.size, 0);
		type.pntmap = rec.pntmap;
		type.comment = "From " + rec;
		if(TMAP.get(rec.tag.val) != null) {
			if(rec.tag.val != Scode.TAG_STRING)	Util.IERR("Already defined: " + type);
		} else {
			TMAP.put(rec.tag.val, type);
			RECTYPES.add(type);
		}
//		System.out.println("DataType.newRecType: " + Scode.edTag(tag) + ", size="+size);
	}

	public boolean isSimple() {
		return tag <= Scode.TAG_SIZE;
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

		//    -----------        type                      tag         size    pnt )
		T_VOID   = newBasType(Scode.TAG_VOID,   0         );
		T_TEXT   = newBasType(Scode.TAG_TEXT,   3         );
		T_STRING = newBasType(Scode.TAG_STRING, 3         );
		T_BOOL   = newBasType(Scode.TAG_BOOL,   1         );
		T_CHAR   = newBasType(Scode.TAG_CHAR,   1         );
		T_INT    = newBasType(Scode.TAG_INT,    1         );
		T_SINT   = newBasType(Scode.TAG_SINT,   1         );
		T_REAL   = newBasType(Scode.TAG_REAL,   1         );
		T_LREAL  = newBasType(Scode.TAG_LREAL,  1         );
		T_SIZE   = newBasType(Scode.TAG_SIZE,   1         );
		T_OADDR  = newPntType(Scode.TAG_OADDR,  1,     0  );
		T_AADDR  = newBasType(Scode.TAG_AADDR,  1         );
		T_GADDR  = newPntType(Scode.TAG_GADDR,  2,     1  );
		T_PADDR  = newBasType(Scode.TAG_PADDR,  1         );
		T_RADDR  = newBasType(Scode.TAG_RADDR,  1         );
	}

	private static Type newBasType(int tag, int size) {
		Type type = new Type(tag, size, 0);
		TMAP.put(tag, type);
		return type;
	}

	private static Type newPntType(int tag, int size, int rela) {
		Type type = new Type(tag, size, 0);
		type.pntmap = new BitSet(32);
		type.pntmap.set(rela);
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
		return Scode.edTag(tag) + " size=" + size + " pntmap=" + pntmap + " " + comment;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public static void writeRECTYPES(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("writeRECTYPES: ");
		oupt.writeKind(Kind.K_RECTYPES);
		oupt.writeShort(RECTYPES.size());
		for(Type type:RECTYPES) {
//			System.out.println("Type.writeRECTYPES: " + type.tag );
			oupt.writeTagID(type.tag);
			oupt.writeShort(type.size);
			if(type.pntmap != null) {
				oupt.writeBoolean(true);
				byte[] pnts = type.pntmap.toByteArray();
				int n = pnts.length;
				oupt.writeShort(n);
				for(int i=0;i<n;i++) oupt.writeShort(pnts[i]);
			} else oupt.writeBoolean(false);
			type.comment = "From " + Global.currentModule;
		}
//		Util.IERR("");
	}

	public static void readRECTYPES(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			int tag = inpt.readTagID();
			int size = inpt.readShort();
			Type type = new Type(tag, size, 0);
			boolean present = inpt.readBoolean();
			if(present) {
				int npt = inpt.readShort();
				byte[] pnts = new byte[npt];
				for(int j=0;j<npt;j++) pnts[j] = (byte) inpt.readShort();
				type.pntmap = BitSet.valueOf(pnts);
			}
//			if(tag == Scode.TAG_STRING) ; // OK Predefinert
//			else if(TMAP.get(tag) !=null) {
//				Type.dumpTypes("Type.readRECTYPES: ");
//				Util.IERR("Alredy defined: " + TMAP.get(tag) + " NEW in " + Global.currentModule);
//			}
//			TMAP.put(tag, type);
//			RECTYPES.add(type);
			
			if(tag == Scode.TAG_STRING) ; // OK Predefinert
			else if(TMAP.get(tag) ==null) {
				TMAP.put(tag, type);
				RECTYPES.add(type);
//				Type.dumpTypes("Type.readRECTYPES: ");
			}
			
			
//			System.out.println("Type.readRECTYPES: NEW Type: " + type);
//			Util.IERR("");
		}
	}
	
//	protected Type(AttributeInputStream inpt) throws IOException {
//		int tag = inpt.readTag();
//		System.out.println("NEW Type(inpt): " + Scode.edInstr(tag));
//		Type type = TMAP.get(tag);
//		Util.IERR("SJEKK DETTE");
//	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
	}

	public static Type read(AttributeInputStream inpt) throws IOException {
		int tag = inpt.readTag();
//		System.out.println("NEW Type(inpt): " + Scode.edInstr(tag));
		Type type = TMAP.get(tag);
//		Util.IERR("SJEKK DETTE");
		return type;
	}


}
