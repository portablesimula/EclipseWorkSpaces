/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;

/// Record descriptor.
///
/// S-CODE:
///
///	record_descriptor
///		::= record record_tag:newtag <record_info>?
///			<prefix_part>? common_part
///			<alternate_part>*
///			endrecord 
///
///			record_info	::= info "TYPE" | info "DYNAMIC"
///			prefix_part	::= prefix resolved_structure
///			common_part	::= <attribute_definition>*
///			alternate_part ::= alt <attribute_definition>*
///				attribute_definition ::= attr attr:newtag quantity_descriptor
///				resolved_structure ::= structured_type < fixrep count:ordinal >?
///					structured_type ::= record_tag:tag
///
///				quantity_descriptor ::= resolved_type < Rep count:number >?
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/RecordDescr.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class RecordDescr extends Descriptor {
	public int size;      // Record size information
	public int rep0size;     // Size of rep(0) attribute
	boolean infoType;

	// NOT SAVED
	String xinfo;
	int prefixTag;
	Vector<Attribute> attributes;
	Vector<AlternatePart> alternateParts;
	
	/// Create a new RecordDescr with the given 'tag'
	/// @param tag used to lookup descriptors
	private RecordDescr(final Tag tag) {
		super(Kind.K_RecordDescr, tag);
	}
	
	/// Scans the remaining S-Code (if any) belonging to this descriptor.
	/// Then construct a new RecordDescr instance.
	/// @return an Attribute instance.
	public static RecordDescr ofScode() {
		RecordDescr rec = new RecordDescr(Tag.ofScode());
		int comnSize = 0;
			
		if(Scode.accept(Scode.S_INFO)) {
			@SuppressWarnings("unused")
			String info = Scode.inString();
			rec.infoType = true;
		}
		if(Scode.accept(Scode.S_PREFIX)) {
			rec.prefixTag = Scode.ofScode();
			RecordDescr prefix = rec.getPrefix(rec.prefixTag);
			comnSize = prefix.size;
		}
		rec.attributes = new Vector<Attribute>();
		while(Scode.accept(Scode.S_ATTR)) {
			Attribute attr = Attribute.ofScode(comnSize);
			comnSize = comnSize + attr.allocSize();
			if(attr.repCount == 0) rec.rep0size = attr.size();
			rec.attributes.add(attr);
		}
		rec.size = comnSize;
		while(Scode.accept(Scode.S_ALT)) {
			if(rec.alternateParts == null) rec.alternateParts = new Vector<AlternatePart>();
			AlternatePart alt = rec.new AlternatePart();
			rec.alternateParts.add(alt);
			int altSize = comnSize;
			while(Scode.accept(Scode.S_ATTR)) {
				Attribute attr = Attribute.ofScode(altSize);
				altSize = altSize + attr.allocSize();
				alt.attributes.add(attr);
			}
			rec.size = Math.max(rec.size, altSize);
		}
		Scode.expect(Scode.S_ENDRECORD);
		Type.newRecType(rec);
		return rec;
	}
	
	private RecordDescr getPrefix(final int prefixTag) {
		RecordDescr prefix = (RecordDescr) Display.get(prefixTag);
		return prefix;
	}
		
	@Override
	public void print(final String indent) {
		String head = "RECORD " + tag + " Size=" + size;
		if(infoType)  head = head + " INFO TYPE";
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		IO.println(indent + head);
		if(attributes != null) for(Attribute attr:attributes) {
			IO.println(indent + "   " + attr.toString());
		}
		if(alternateParts != null) {
			for(AlternatePart alt:alternateParts) {
				alt.print(indent + "   ");
			}
		}
		IO.println("   " + "ENDRECORD");
	}
		
	@Override
	public String toString() {
		String head = "RECORD " + tag + " Size=" + size;
		if(infoType)  head = head + " INFO TYPE";
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		return head + " ...";
	}


	/// AlternatePart.
	///
	///	alternate_part ::= alt <attribute_definition>*
	///		attribute_definition ::= attr attr:newtag quantity_descriptor
	///		resolved_structure ::= structured_type < fixrep count:ordinal >?
	///			structured_type ::= record_tag:tag
	///
	///		quantity_descriptor ::= resolved_type < Rep count:number >?
	/// 
	///
	class AlternatePart {
		Vector<Attribute> attributes;
		
		public AlternatePart() {
			attributes = new Vector<Attribute>();
		}
	
		public int size() {
			int n = 0;
			for(Attribute attr:attributes) n = n + attr.size();
			return n;
		}
		
		public void print(final String indent) {
			boolean first = true;
			for(Attribute attr:attributes) {
				if(first) {
					IO.println(indent + "ALT " + attr);
					first = false;
				}
				else IO.println(indent + "    " + attr);
			}
		}
	}	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(final AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("RecordDescr.Write: " + this);
		oupt.writeByte(kind);
		tag.write(oupt);
		oupt.writeShort(size);
		oupt.writeShort(rep0size);
		oupt.writeBoolean(infoType);
	}

	/// Reads a RecordDescr from the given input.
	/// @param inpt the input stream
	public static RecordDescr read(final AttributeInputStream inpt) throws IOException {
		RecordDescr rec = new RecordDescr(Tag.read(inpt));
		rec.size = inpt.readShort();
		rec.rep0size = inpt.readShort();
		rec.infoType = inpt.readBoolean();
		if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.Read: " + rec);
		return rec;
	}

}
