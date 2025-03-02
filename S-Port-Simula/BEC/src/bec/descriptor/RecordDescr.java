package bec.descriptor;

import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InterfaceModule;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;

public class RecordDescr extends Descriptor {
	public int size;      // Record size information
	public int nbrep;     // Size of rep(0) attribute
	public BitSet pntmap; // Only used by TypeRecord
	boolean infoType;

	// NOT SAVED
	String xinfo;
	int prefixTag;
	Vector<Attribute> attributes;
	Vector<AlternatePart> alternateParts;
	
	private RecordDescr(int kind, Tag tag) {
		super(kind, tag);
	}
	
//	%title ***   I n p u t   R e c o r d   ***
/**
 *	record_descriptor
 *		::= record record_tag:newtag <record_info>?
 *			<prefix_part>? common_part
 *			<alternate_part>*
 *			endrecord 
 *
 *	record_info	::= info "TYPE" | info "DYNAMIC"
 *	prefix_part	::= prefix resolved_structure
 *	common_part	::= <attribute_definition>*
 *	alternate_part ::= alt <attribute_definition>*
 *		attribute_definition ::= attr attr:newtag quantity_descriptor
 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
 *			structured_type ::= record_tag:tag
 *
 *		quantity_descriptor ::= resolved_type < Rep count:number >?
 * 
 */
	public static RecordDescr of() {
		RecordDescr rec = new RecordDescr(Kind.K_RecordDescr,Tag.ofScode());
		int comnSize = 0;
			
		if(Scode.accept(Scode.S_INFO)) {
			String info = Scode.inString();
//			if("TYPE".equalsIgnoreCase(info)) rec.infoType = true;
			rec.infoType = true;
		}
		if(Scode.accept(Scode.S_PREFIX)) {
			rec.prefixTag = Scode.ofScode();
			RecordDescr prefix = rec.getPrefix(rec.prefixTag);
			comnSize = prefix.size;
		}
		rec.attributes = new Vector<Attribute>();
		while(Scode.accept(Scode.S_ATTR)) {
			Attribute attr = new Attribute(comnSize);
			comnSize = comnSize + attr.size;
			if(attr.repCount == 0) rec.nbrep = attr.size;
			rec.attributes.add(attr);
		}
		rec.size = comnSize;
		while(Scode.accept(Scode.S_ALT)) {
			if(rec.alternateParts == null) rec.alternateParts = new Vector<AlternatePart>();
			AlternatePart alt = rec.new AlternatePart();
			rec.alternateParts.add(alt);
			int altSize = comnSize;
			while(Scode.accept(Scode.S_ATTR)) {
				Attribute attr = new Attribute(altSize);
				altSize = altSize + attr.size;
				alt.attributes.add(attr);
			}
			rec.size = Math.max(rec.size, altSize);
		}
		Scode.expect(Scode.S_ENDRECORD);
			
//		if(Global.currentModule instanceof InterfaceModule) {
//		if(rec.infoType) {
			rec.buildPointerMap();
			Type.newRecType(rec);
//		}
//		if(Scode.inputTrace > 3)
			rec.print("   ");
		return rec;
	}
		
	private void buildPointerMap() {
		System.out.println("RecordDescr.buildPointerMap: DENNE METODEN MÅ SKRIVES");
	}
	
	private RecordDescr getPrefix(int prefixTag) {
		RecordDescr prefix = (RecordDescr) Global.DISPL.get(prefixTag);
		return prefix;
	}

	public void emitDefaultValues(DataSegment dseg, int repCount, String comment) {
//		System.out.println("RECORD.emitDefaultValue: " + dseg + "  " + this);
//		print(2);
			
		int rep = (repCount > 0) ? repCount : 1;
		for(int i=0;i<rep;i++) {
			for(int j=0;j<size;j++)
				dseg.emit(null, comment);
		}
//		Global.DSEG.dump();
//		Util.IERR("");
	}
		
	@Override
	public void print(final String indent) {
		String head = "RECORD " + tag + " Size=" + size;
		if(infoType)  head = head + " INFO TYPE";
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
//		System.out.println(indent + head);
		System.out.println(indent + head);
//		for(AttributeDefinition attr:attributes) {
		if(attributes != null) for(Attribute attr:attributes) {
//			sLIST(indent + 1, attr.toString());
			System.out.println(indent + "   " + attr.toString());
		}
		if(alternateParts != null) {
			for(AlternatePart alt:alternateParts) {
				alt.print(indent + "   ");
			}
		}
//		System.out.println(indent + "ENDRECORD");
		System.out.println("   " + "ENDRECORD");
	}
		
	public String toString() {
		String head = "RECORD " + tag + " Size=" + size;
		if(infoType)  head = head + " INFO TYPE";
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		return head + " ...";
	}


	
	/**
	 *	alternate_part ::= alt <attribute_definition>*
	 *		attribute_definition ::= attr attr:newtag quantity_descriptor
	 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *			structured_type ::= record_tag:tag
	 *
	 *		quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 */
	class AlternatePart {
		Vector<Attribute> attributes;
		
		public AlternatePart() {
			attributes = new Vector<Attribute>();
		}
	
		public int size() {
			int n = 0;
			for(Attribute attr:attributes) n = n + attr.size;
			return n;
		}
		
		public void print(final String indent) {
			boolean first = true;
			for(Attribute attr:attributes) {
				if(first) {
					System.out.println(indent + "ALT " + attr);
					first = false;
				}
				else System.out.println(indent + "    " + attr);

			}
		}
	}	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("RecordDescr.Write: " + this);
		oupt.writeKind(kind);
		tag.write(oupt);
		oupt.writeShort(size);
		oupt.writeShort(nbrep);
		oupt.writeBoolean(infoType);
	}

	public static RecordDescr read(AttributeInputStream inpt) throws IOException {
		Tag tag = Tag.read(inpt);
		RecordDescr rec = new RecordDescr(Kind.K_RecordDescr, tag);
		rec.size = inpt.readShort();
		rec.nbrep = inpt.readShort();
		rec.infoType = inpt.readBoolean();
		if(Global.ATTR_INPUT_TRACE) System.out.println("RecordDescr.Read: " + rec);
//		if(rec.infoType) {
//			rec.buildPointerMap();
//			Type.newRecType(rec);
//		}
		return rec;
	}


}
