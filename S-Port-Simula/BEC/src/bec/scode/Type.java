/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.scode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import bec.descriptor.Kind;
import bec.descriptor.RecordDescr;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Option;
import bec.util.Util;

/// Type.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/scode/Type.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class Type {
	public  int tag;
	private int size;  // Size of type in basic cells
	private int rep0size;  // Size of rep 0 attribute in basic cells

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
	public static Type ofScode() {
		int tag = Scode.inTag();
		if(tag == Tag.TAG_INT) {
			if(Scode.accept(Sinstr.S_RANGE)) {
				Scode.inNumber(); // low
				Scode.inNumber(); // high
			}
		}
		Type type = TMAP.get(tag);
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
		return type;
	}

	public static void newRecType(RecordDescr rec) {
		Type type = new Type(rec.tag.val, rec.size);
		type.rep0size = rec.rep0size;
		TMAP.put(rec.tag.val, type);
		RECTYPES.add(type);
	}
	
	public static void removeFromTMAP(int tag) {
		TMAP.remove(tag);
	}

	public boolean isSimple() {
		return tag <= Tag.TAG_SIZE;
	}

	public boolean isRecordType() {
		return tag > Tag.T_max;
	}
	
	public boolean isArithmetic() {
		switch(tag) {
		case Tag.TAG_INT, Tag.TAG_REAL, Tag.TAG_LREAL: return true;
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
		T_VOID   = newBasType(Tag.TAG_VOID,   0   );
		T_TEXT   = newBasType(Tag.TAG_TEXT,   3   );
		T_STRING = newBasType(Tag.TAG_STRING, 3   );
		T_BOOL   = newBasType(Tag.TAG_BOOL,   1   );
		T_CHAR   = newBasType(Tag.TAG_CHAR,   1   );
		T_INT    = newBasType(Tag.TAG_INT,    1   );
		T_SINT   = newBasType(Tag.TAG_SINT,   1   );
		T_REAL   = newBasType(Tag.TAG_REAL,   1   );
		T_LREAL  = newBasType(Tag.TAG_LREAL,  1   );
		T_SIZE   = newBasType(Tag.TAG_SIZE,   1   );
		T_OADDR  = newBasType(Tag.TAG_OADDR,  1   );
		T_AADDR  = newBasType(Tag.TAG_AADDR,  1   );
		T_GADDR  = newBasType(Tag.TAG_GADDR,  2   );
		T_PADDR  = newBasType(Tag.TAG_PADDR,  1   );
		T_RADDR  = newBasType(Tag.TAG_RADDR,  1   );
	}

	private static Type newBasType(int tag, int size) {
		Type type = new Type(tag, size);
//		if(tag == 2497) Util.IERR("");
		TMAP.put(tag, type);
		return type;
	}

	public static void dumpTypes(String title) {
		IO.println("============ "+title+" BEGIN Dump Types ================");
		for(Integer type:TMAP.keySet()) {
			IO.println("TTAB["+type+"] = " + TMAP.get(type));
		}
		for(Type type:RECTYPES) {
			IO.println("Record TYPE = " + type);
			
		}
		IO.println("============ "+title+" ENDOF Dump Types ================");
	}
	
	public String toString() {
		return Scode.edTag(tag) + " size=" + size + ", rep0size=" + rep0size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public static void writeRECTYPES(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("writeRECTYPES: ");
		oupt.writeByte(Kind.K_RECTYPES);
		oupt.writeShort(RECTYPES.size());
		for(Type type:RECTYPES) {
//			oupt.writeTagID(type.tag);
			oupt.writeString(Scode.TAGIDENT.get(type.tag));
			oupt.writeShort(type.tag);
			oupt.writeShort(type.size);
		}
	}

	public static void readRECTYPES(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
//			int tag = inpt.readTagID();
	    	String ident = inpt.readString();
	    	int tag = inpt.readShort();
	    	Scode.TAGIDENT.set(tag, ident);
			int size = inpt.readShort();
			Type type = new Type(tag, size);
			
			if(tag == Tag.TAG_STRING) ; // OK Predefinert
			else if(TMAP.get(tag) ==null) {
				TMAP.put(tag, type);
				RECTYPES.add(type);
			}
		}
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(tag);
	}

	public static Type read(AttributeInputStream inpt) throws IOException {
		int tag = inpt.readShort();
//		IO.println("NEW Type(inpt): " + Sinstr.edInstr(tag));
		Type type = TMAP.get(tag);
		if(type == null) Util.IERR("SJEKK DETTE");
		return type;
	}


}
