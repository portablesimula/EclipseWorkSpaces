package iAPX.descriptor;

import java.io.IOException;
import java.util.BitSet;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Option;
import iAPX.util.Tag;    

public class TypeRecord extends RecordDescr {
	public BitSet pntmap;
	
	private static final boolean DEBUG = false;

	public TypeRecord(Tag tag, int size, int nbrep, BitSet pntmap) {
		super(tag, size, nbrep);
		this.kind = Kind.K_TypeRecord;
		this.pntmap = pntmap;
	}
	
	@Override
	public void print(final String indent) {
		String head = "\nRECORD " + tag + " Size=" + size;
		if(withInfo) head += " INFO TYPE pntmap=" + pntmap;
		if(prefixTag != null) head += " PREFIX " + prefixTag;
		IO.println(indent + head);
		if(attributes != null) for(AttrDescr attr:attributes) {
			IO.println(indent + "   " + attr.toString());
		}
		IO.println("ENDRECORD");
	}

	@Override
	public String toString() {
		String s = "TypeRecord: ";
		if(Option.GENERATE_DEBUG_CODE) s += tag + ", ";		
		s += "type=" + type + ", size=" + size + ", nbrep=" + nbrep;
		if(pntmap != null) s = s + ", pntmap=" + pntmap;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		if(pntmap != null) {
			oupt.writeBoolean(true);
			oupt.writeBitSet(pntmap);
		} else oupt.writeBoolean(false);

	}

	private TypeRecord() {
		this.kind = Kind.K_TypeRecord;
	}

	public static TypeRecord read(AttributeInputStream inpt) throws IOException {
		TypeRecord rec = new TypeRecord();
		TypeRecord.read(rec, inpt);
		if(Option.ATTR_INPUT_DUMP) rec.print("");
//		Util.IERR("");
		return rec;
	}
	
	protected static void read(TypeRecord rec, AttributeInputStream inpt) throws IOException {
		RecordDescr.read(rec, inpt);
		boolean present = inpt.readBoolean(); // pntmap
		if(present) {
			rec.pntmap = inpt.readBitSet();
			if(DEBUG) IO.println("TypeRecord.read: pntmap="+rec.pntmap);
		}
	}



}
