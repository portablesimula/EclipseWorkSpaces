package iAPX.descriptor;

import static iAPX.util.Global.*;

import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;

//Record RecordDescr:Descriptor;   // K_RecordDescr                 SIZE = 8 bytes
//begin int nbyte;    // Record size information
//      int nbrep;    // Size of rep(0) attribute
//      infix(WORD) pntmap;        // Only used by TypeRecord
//end;
public class RecordDescr extends Descriptor {
//	int tag;  // Inherited
//	int type; // Inherited
	public int size;    // Record size information
	public int nbrep;   // Size of rep(0) attribute
	
	boolean withInfo;             // GENERATE_DEBUG_CODE
	Tag prefixTag;                // GENERATE_DEBUG_CODE
	Vector<AttrDescr> attributes; // GENERATE_DEBUG_CODE

	private static final boolean DEBUG = false;
	
	public RecordDescr(Tag tag, int size, int nbrep) {
		this.kind = Kind.K_RecordDescr;
		this.tag  = tag;
		this.size = size;
		this.nbrep = nbrep;
		if(Option.GENERATE_DEBUG_CODE) attributes = new Vector<AttrDescr>();
	}
	
	//%title ***   I n p u t   R e c o r d   ***
	public static RecordDescr inRecord() { // export ref(RecordDescr) r;
		Vector<AttrDescr> attributes = new Vector<AttrDescr>(); // GENERATE_DEBUG_CODE
		Tag prefixTag = null; int fixnbt = 0; int size = 0;
		boolean withInfo = false; boolean withALT = false;
		Tag tag = Tag.inTag(); 
		if(DEBUG) IO.println("\nRecordDescr.inRecord: BEGIN PARSING: " + tag);
		
		if(Scode.accept(Scode.S_INFO)) {
			String info = Scode.inString();
			if(DEBUG) IO.println("RecordDescr.inRecord: info=" + info);
			if(info.equals("TYPE")) withInfo = true;
		}
		
		int Scomn = 0;
		if(Scode.accept(Scode.S_PREFIX)) {
			prefixTag = Tag.inTag();
			RecordDescr prefix = (RecordDescr) Display.lookup(prefixTag);
			Scomn = prefix.size;
			if(DEBUG) IO.println("RecordDescr.inRecord: PREFIX=" + prefix);
			if(prefix.nbrep != 0) Util.IERR("Prefix contains a 'REP 0'");
			
			if(prefix instanceof TypeRecord trec) {
				withInfo = true;
				IO.println("RecordDescr.inRecord: COPY POINTER-MAP MÅ IMPLEMENTERES: " + tag);
//	%+S             if prefix qua TypeRecord.pntmap.val=0 then pm = nostring
//	%+S             else pm = DICSMB(prefix qua TypeRecord.pntmap) endif;
//	%+S             npnt = pm.nchr; i = npnt;
//	%+S             repeat while i <> 0
//	%+S             do i = i-1;
//	%+S                rela(i) = var(pm.chradr)(i) qua integer endrepeat;
				Util.IERR("NOT IMPL");
			} else if(withInfo) Util.IERR("MINUT: No INFO TYPE on prefix");
		}
		
		boolean common_part = true;	
		BitSet pntmap = null;

		do {
			int ofst = Scomn;
			while(Scode.accept(Scode.S_ATTR)) {
				if(DEBUG) IO.println("\nRecordDescr.inRecord: AFTER ACCEPT ATTR: curinstr="+curinstr+"  "+Scode.curinstr);
	            if(fixnbt != 0) Util.IERR("Illegal 'REP 0'");
				Tag atag = Tag.inTag(); int count = 1;
				if(DEBUG) IO.println("RecordDescr.inRecord: ATTR: " + atag);
				
				Type attrType = Scode.inType(); int attrSize = attrType.getSize();
				//if(attrType < T_max) attrSize = TTAB[attrType].size;
				if(DEBUG) IO.println("RecordDescr.inRecord: New Type: " + attrType + ", attrSize="+attrSize);
				
				if(Scode.accept(Scode.S_REP)) {
					count = Scode.inNumber();
					if(count == 0) {
						fixnbt = attrSize;
//						if(withInfo) Util.IERR("'REP 0' in TYPE");
					}
				}
				if(attrSize == 0) Util.IERR("Illegal attrType " + attrType + " on attribute: " + atag);
				
				AttrDescr attr = new AttrDescr(atag, attrType, ofst);
				if(Option.GENERATE_DEBUG_CODE) {
					attributes.add(attr);
				}
				
				if(withInfo & !withALT) {
					BitSet attrPntmap = attrType.getPntmap();
					while((count--) != 0) {
						if(attrPntmap != null) {
							if(DEBUG) IO.println("RecordDescr.inRecordDesrc: attrType=" + attrType + ", attrPntmap = " + attrPntmap);
							if(pntmap == null) pntmap = new BitSet();
							int[] ofsts = attrPntmap.stream().toArray();
							for(int i=0;i<ofsts.length;i++) {
								if(DEBUG) IO.println("RecordDescr.inRecordDesrc: ofst="+ofst+", rela="+ofsts[i]);
								pntmap.set(ofst+ofsts[i]);
							}
						}
						ofst += attrSize;
					}
					if(DEBUG) IO.println("RecordDescr.inRecordDesrc: ACCUM pntmap="+pntmap);
				} else {
					ofst = ofst+(count * attrSize);
				}
				Display.add(attr);
			}
				
			if(common_part) Scomn = ofst; common_part = false;
			if(ofst > size) size = ofst;
		
			withALT = true;
		} while(Scode.accept(Scode.S_ALT));
		
		if(! Scode.accept(Scode.S_ENDRECORD)) Util.IERR("Missing ENDRECORD - GOT " + Scode.curinstr);
		
		RecordDescr rec = null;
		if(withInfo) {
			if(DEBUG) IO.println("RecordDescr.inRecordDesrc: FINAL pntmap="+pntmap);
			rec = new TypeRecord(tag, size, fixnbt, pntmap);
		} else {
			rec = new RecordDescr(tag, size, fixnbt);
		}
		if(Option.GENERATE_DEBUG_CODE) {
			rec.prefixTag = prefixTag;
			rec.withInfo = withInfo;
			rec.attributes = attributes;
		}
		Display.add(rec);
		if(Option.SCODE_INPUT_DUMP) rec.print("");
		return rec;
	}
	
	@Override
	public void print(final String indent) {
		String head = "\nRECORD " + tag + " Size=" + size;
		if(withInfo) head += " INFO TYPE";
		if(prefixTag != null) head += " PREFIX " + prefixTag;
		IO.println(indent + head);
		if(attributes != null) for(AttrDescr attr:attributes) {
			IO.println(indent + "   " + attr.toString());
		}
//		if(alternateParts != null) {
//			for(AlternatePart alt:alternateParts) {
//				alt.print(indent + "   ");
//			}
//		}
		IO.println("ENDRECORD");
	}
	
	@Override
	public String toString() {
		String s = "RecordDescr: ";
		if(Option.GENERATE_DEBUG_CODE) s += tag + ", ";		
		s += "type=" + type + ", size=" + size + ", nbrep=" + nbrep;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

//  when K_RecordDescr:
//%+D            if ModuleTrace <> 0 then
//%+D            outstring("RecordDescr: "); Print(d) endif;
//       rd.kind:=K_RecordDescr; rd.type:=ChgType(d.type);
//       rd.nbyte:=d qua RecordDescr.nbyte;
//       rd.nbrep:=d qua RecordDescr.nbrep;
//       buf.nchr:=Size2Word(size(RecordDescr))
//%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-3") endif;
//       buf.chradr:=@rd; EnvOutBytes(modoupt,buf);
//       if status<>0 then FILERR(modoupt,"Wdescr-3") endif;
	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		oupt.writeShort(size);
		oupt.writeShort(nbrep);
		if(Option.GENERATE_DEBUG_CODE) {
			Tag.writeTag(prefixTag,oupt);
			oupt.writeBoolean(withInfo);
			int n = attributes.size();
			oupt.writeShort(n);
			for(AttrDescr attr:attributes) {
				attr.write(oupt);
			}
		}
	}

	protected RecordDescr() {
		this.kind = Kind.K_RecordDescr;
		if(Option.GENERATE_DEBUG_CODE) attributes = new Vector<AttrDescr>();
	}
	
	public static RecordDescr read(AttributeInputStream inpt) throws IOException {
		RecordDescr rec = new RecordDescr();
		RecordDescr.read(rec, inpt);
		if(Option.ATTR_INPUT_DUMP) rec.print("");
//		Util.IERR("");
		return rec;
	}
	
	protected static void read(RecordDescr rec, AttributeInputStream inpt) throws IOException {
		Descriptor.read(rec, inpt);
		rec.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: size="+rec.size);
		rec.nbrep = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: nbrep="+rec.nbrep);
		if(Option.GENERATE_DEBUG_CODE) {
			rec.prefixTag = Tag.readTag(inpt);
			if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: prefixTag="+rec.prefixTag);
			rec.withInfo = inpt.readBoolean();
			if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: withInfo="+rec.withInfo);
			int nAttr = inpt.readShort();
			if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: nAttr="+nAttr);
			for(int i=0;i<nAttr;i++) {
				Kind kind = Kind.read(inpt);
				if(Option.ATTR_INPUT_TRACE) IO.println("RecordDescr.read: kind="+kind);
				if(kind != Kind.K_Attribute) Util.IERR("");
				rec.attributes.add(AttrDescr.read(inpt));
			}
		}
	}


}
