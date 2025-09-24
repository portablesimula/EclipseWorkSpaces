package iAPX.util;

import java.io.IOException;
import java.util.BitSet;

import iAPX.descriptor.RecordDescr;
import iAPX.descriptor.TypeRecord;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;

public class Type {
	public int tag;
	public int size;
	public BitSet pntmap;
	public boolean isBasic;
	//	int fixrep;
	public String ident; // GENERATE_DEBUG_CODE

	public static final Type T_INT =   new Type(new Tag(Tag.TAG_INT));
	public static final Type T_REAL =  new Type(new Tag(Tag.TAG_REAL));
	public static final Type T_LREAL = new Type(new Tag(Tag.TAG_LREAL));
	public static final Type T_BOOL =  new Type(new Tag(Tag.TAG_BOOL));
	public static final Type T_CHAR =  new Type(new Tag(Tag.TAG_CHAR));
	public static final Type T_SIZE =  new Type(new Tag(Tag.TAG_SIZE));
	public static final Type T_OADDR = new Type(new Tag(Tag.TAG_OADDR));
	public static final Type T_AADDR = new Type(new Tag(Tag.TAG_AADDR));
	public static final Type T_GADDR = new Type(new Tag(Tag.TAG_GADDR));
	public static final Type T_PADDR = new Type(new Tag(Tag.TAG_PADDR));
	public static final Type T_RADDR = new Type(new Tag(Tag.TAG_RADDR));
//	public static final Type T_NPADR = new Type(new Tag(Tag.TAG_NPADR));
//	public static final Type T_NOINF = new Type(new Tag(Tag.TAG_NOINF));
	public static final Type T_VOID = null;

	public Type(Tag tag) {
		this.tag = tag.val;
		this.size = evalSize(tag);
		this.pntmap = evalPntmap(tag);
		this.isBasic = tag.isBasic();
		if(Option.GENERATE_DEBUG_CODE) this.ident = tag.toString();
		
		if(tag.val == Tag.TAG_SIZE & this.pntmap != null) Util.IERR("GOT TRAP");
	}
	
//	public Type(Tag tag, int fixrep) {
//		this.tag = tag;
//		this.fixrep = fixrep;
//	}
	
	public boolean isT_INT() {
		return tag == Tag.TAG_INT;
	}
	
	public boolean equals(Type other) {
			if(this.size != other.size) return false;
			if(this.pntmap == null)	return other.pntmap == null;
			if(other.pntmap == null) return false;
			return this.pntmap.equals(other.pntmap);
	}

//	public void write(AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(tag.val);
//	}
//
//	public static Type read(AttributeInputStream inpt) throws IOException {
//		return new Type(new Tag(inpt.readShort()));
//	}
	
	public int getSize() {
		return size;
	}
	
	private int evalSize(Tag tag) {
		int lng = 0;
		if(tag.isBasic()) {
			lng = (tag.val == Tag.TAG_GADDR)? 2 : 1;
		} else {
			boolean TESTING = false;
			if(TESTING) {
//				Display.dumpDisplay("Type.size: ");
				Display.printTags("Type.size: ");
				IO.println("Type.size: " + tag + ", display=" + Display.lookup(tag));
			}
			RecordDescr rec = (RecordDescr)Display.lookup(tag);
//			IO.println("Scode.inType: rec="+rec+"  tag=" + tag);
//			if(fixrep > 1) {
//				lng = rec.size+(rec.nbrep*fixrep);
//			} else {
				lng = rec.size;
				//	IO.println("Scode.inType: lng="+lng+"  tag="+Scode.edTag(tag));
				if(rec.nbrep != 0) Util.IERR("Missing FIXREP");
//			}
		}
		return lng;
	}

	public BitSet getPntmap() {
		return pntmap;
	}

	private BitSet evalPntmap(Tag tag) {
		BitSet pmap = null;
		if(tag.isBasic()) {
//			lng = (tag.val == Tag.TAG_GADDR)? 2 : 1;
			switch(tag.val) {
			case Tag.TAG_OADDR, Tag.TAG_GADDR:
				pmap = new BitSet(); pmap.set(0);
				break;
			}
		} else {
//			TypeRecord rec = (TypeRecord)Display.lookup(tag);
			if(Display.lookup(tag) instanceof TypeRecord rec) {
//				IO.println("Scode.inType: rec="+rec+"  tag=" + tag);
//				if(fixrep > 1) {
//					lng = rec.size+(rec.nbrep*fixrep);
//				} else {
					pmap = rec.pntmap;
					//	IO.println("Scode.inType: lng="+lng+"  tag="+Scode.edTag(tag));
					if(rec.nbrep != 0) Util.IERR("Missing FIXREP");
//				}
			}
		}
		return pmap;
	}
	
	public static Type arithType(Type t1, Type t2) { // export ct;
		switch(t1.tag) {
			case Tag.TAG_LREAL: return T_LREAL;
			case Tag.TAG_REAL:
				switch(t2.tag) {
					case Tag.TAG_LREAL: return T_LREAL;
					default: return T_REAL;
				}
			case Tag.TAG_INT:
				switch(t2.tag) {
					case Tag.TAG_LREAL: return T_LREAL;
					case Tag.TAG_REAL:  return T_REAL;
					default: return T_INT;
				}
			default: return t1;
		}
	}

	public String toString() {
		String s = "Type";
//		if(Option.GENERATE_DEBUG_CODE) s += ":" +ident;
		if(Option.GENERATE_DEBUG_CODE) s += ":" +tag;
		s += "[size=" + size;
		if(pntmap != null) s += ", pntmap=" +pntmap;		
		return s + "]";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	private Type(int tag, int size, BitSet pntmap, boolean isBasic) {
		this.tag = tag;
		this.size = size;
		this.pntmap = pntmap;
	}

	public static void write(Type type, AttributeOutputStream oupt) throws IOException {
//    public void writeType(Type type) throws IOException {
		if(AttributeOutputStream.TRACE) IO.println("Type.write: " + type);
//		int tx = Minut.chgType(type);
//		oupt.writeByte(tx);
		if(type != null) {
			oupt.writeBoolean(true);
			oupt.writeShort(type.tag);
			oupt.writeShort(type.size);
			if(type.pntmap != null) {
				oupt.writeBoolean(true);
				oupt.writeBitSet(type.pntmap);
			} else {
				oupt.writeBoolean(false);					
			}
			oupt.writeBoolean(type.isBasic);
			if(Option.GENERATE_DEBUG_CODE) oupt.writeString(type.ident);;
		} else oupt.writeBoolean(false);
	}


    public static Type readType(AttributeInputStream inpt) throws IOException {
//    	Util.IERR("NOT IMPL - Se AttributeOutputStream.writeType: ");
    	boolean present = inpt.readBoolean();
//    	Type type = (present)? new Type(readTag()) : null;
    	Type type = null;
    	if(present) {
			int tag = inpt.readShort();
			int size = inpt.readShort();
			BitSet pntmap = null;
			present = inpt.readBoolean();
			if(present) {
				pntmap = inpt.readBitSet();
			}
			boolean isBasic = inpt.readBoolean();
			type = new Type(tag, size, pntmap, isBasic);
			if(Option.GENERATE_DEBUG_CODE) {
				type.ident = inpt.readString();
			}
			IO.println("AttributeInputStream.readType: " + type);
    	}
		if(AttributeInputStream.TRACE) IO.println("AttributeInputStream.readType: " + type);
//		Util.IERR("");
    	return type;
    }

}
