package PREV.syntaxClass.instruction;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.programElement.AttributeDefinition;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.DataType;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class RECORD extends PREV_Instruction {
	public int tag;
	String info;
	int prefixTag;
	Vector<AttributeDefinition> attributes;
	Vector<AlternatePart> alternateParts;
	
	Vector<Integer> pntmap; // Only used by TypeRecord

	
	public RECORD() {
		parse();
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
	private void parse() {
//		Vector<AttributeDefinition> curAttrs;
//		Vector<AlternatePart> alternateParts = null;

		tag = Scode.inTag();	
//		Global.Display.set(tag, this);
		int comnSize = 0;
		
		if(Scode.accept(Scode.S_INFO))   info = Scode.inString();
		if(Scode.accept(Scode.S_PREFIX)) {
			prefixTag = Scode.inTag();
			RECORD prefix = (RECORD) Global.getMeaning(prefixTag);
			comnSize = prefix.size();
		}
		
		attributes = new Vector<AttributeDefinition>();
		while(Scode.accept(Scode.S_ATTR)) {
			AttributeDefinition attr = new AttributeDefinition(comnSize);
			comnSize = comnSize + attr.size();
			attributes.add(attr);
		}
		int maxAlt = comnSize;
		while(Scode.accept(Scode.S_ALT)) {
			if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
			AlternatePart alt = new AlternatePart();
			alternateParts.add(alt);
			int altSize = comnSize;
			while(Scode.accept(Scode.S_ATTR)) {
				AttributeDefinition attr = new AttributeDefinition(altSize);
				altSize = altSize + attr.size();
				alt.attributes.add(attr);
			}
			maxAlt = Math.max(maxAlt, altSize);
		}
		printTree(2);
		if(maxAlt != size()) Util.IERR("maxAlt="+maxAlt+", size="+size());
		
//		curAttrs = attributes;
//		do {
//			if(attributes == null) {
//				curAttrs = attributes = new Vector<AttributeDefinition>();
//			} else {
//				if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
//				AlternatePart alt = new AlternatePart();
//				alternateParts.add(alt);
//				curAttrs = alt.attributes;
//			}
//			while(Scode.accept(Scode.S_ATTR)) {
////				Scode.inTag(); Scode.inType();
////				if(Scode.accept(Scode.S_REP)) Scode.inNumber();
//				AttributeDefinition attr = new AttributeDefinition(size);
//				size = size + attr.size();
//				curAttrs.add(attr);
//			}
//		} while(Scode.accept(Scode.S_ALT));

//		if(Scode.inputTrace > 3) printTree(2);
		Scode.expect(Scode.S_ENDRECORD);
		
		if("TYPE".equalsIgnoreCase(info)) {
			buildPointerMap();
			printTree(2);
//			DataType.newRecType(this);
//			Util.IERR("");
		}
	}
	
	private void buildPointerMap() {
		
	}
	
	public int size() {
		int np = 0;
		if(prefixTag != 0) {
			RECORD prefix = (RECORD) Global.Display.get(prefixTag);
			np = prefix.size();
		}
		int n = 0;
		for(AttributeDefinition attr:attributes) {
			n = n + attr.size();
		}
		int maxAlt = 0;
		if(alternateParts != null) {
			for(AlternatePart alt:alternateParts) {
				int altSize = alt.size();
				maxAlt = Math.max(altSize, maxAlt);
			}
		}
		int tot = np + n + maxAlt;
//		System.out.println("RECORD.size: "+Scode.edTag(tag)+" "+tot+"    prefix="+np+", n="+n+", maxAlt="+maxAlt);
//		if(tot > 50) Util.IERR("");
		return tot;
	}

	public void emitDefaultValues(DataSegment dseg, int repCount, String comment) {
//		System.out.println("RECORD.emitDefaultValue: " + dseg + "  " + this);
//		printTree(2);
		
		int rep = (repCount > 0) ? repCount : 1;
		for(int i=0;i<rep;i++) {
			for(int j=0;j<size();j++)
				dseg.emit(null, comment);
		}
//		Global.DSEG.dump();
//		Util.IERR("");
	}
	
	@Override
	public void printTree(final int indent) {
		String head = "RECORD " + Scode.edTag(tag);
		if(info != null)  head = head + " INFO " + info;
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		sLIST(indent, head);
		for(AttributeDefinition attr:attributes) {
			sLIST(indent + 1, attr.toString());
		}
		if(alternateParts != null) {
			for(AlternatePart alt:alternateParts) {
				alt.printTree(indent + 1);
			}
		}
		sLIST(indent, "ENDRECORD");
	}
	
	public String toString() {
		String head = "RECORD " + Scode.edTag(tag);
		if(info != null)  head = head + " INFO " + info;
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		return head + " ...";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	private RECORD(AttributeInputStream inpt) throws IOException {
		this.tag = inpt.readTag();
		inpt.readInstr();
		if(inpt.curinstr == Scode.S_INFO) { info = inpt.readString(); inpt.readInstr(); }
		if(inpt.curinstr == Scode.S_PREFIX) { prefixTag = inpt.readTag(); inpt.readInstr(); }
		attributes = new Vector<AttributeDefinition>();
		while(inpt.curinstr == Scode.S_ATTR) {
			attributes.add(AttributeDefinition.read(inpt));
			inpt.readInstr();
		}
//		System.out.println("RECORD.readAttributes: inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		while(inpt.curinstr == Scode.S_ALT) {
			if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
			alternateParts.add(AlternatePart.read(inpt));
		}
		if(inpt.curinstr != Scode.S_ENDRECORD) Util.IERR("IMPOSSIBLE: " + Scode.edInstr(inpt.curinstr));
		
		if(Global.ATTR_INPUT_TRACE) printTree(2);
		if("TYPE".equalsIgnoreCase(info)) {
//			DataType.newRecType(this);
		}
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_DUMP) {
			Util.TRACE_OUTPUT("BEGIN Write RECORD: " + Scode.edTag(tag));
			this.printTree(2);
		}		
		
		oupt.writeKind(Scode.S_RECORD); // Mark: This is a ClassDeclaration
		oupt.writeTag(tag);
		if(info != null) { oupt.writeInstr(Scode.S_INFO); oupt.writeString(info); }
		if(prefixTag > 0) { oupt.writeInstr(Scode.S_PREFIX); oupt.writeTag(prefixTag); }
		for(AttributeDefinition attr:attributes) {
			attr.write(oupt);
		}
		if(alternateParts != null) {
			for(AlternatePart alt:alternateParts) {
				alt.write(oupt);
			}
//			Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
		}
		oupt.writeInstr(Scode.S_ENDRECORD);
//		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
//		if(tag == 32) {
//			this.printTree(2);
//			System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
//			Util.IERR("STOP");
//		}
	}

	public static SyntaxClass readObject(AttributeInputStream inpt) throws IOException {
		return(new RECORD(inpt));
	}

}
