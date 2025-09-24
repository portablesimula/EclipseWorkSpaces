package bec.value.dataAddress;

import java.io.IOException;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

// 
public class SegmentAddress extends DataAddress {
	public String segID;
	
//	public static DataAddress ofSegAddr(String segID, int ofst) {
//		return new DataAddress(SEGMNT_ADDR, segID, 0, ofst);
//	}
//	
//	public static DataAddress ofSegAddr(DataSegment seg, int ofst) {
//		String segID = (seg == null)? null : seg.ident;
//		return new DataAddress(SEGMNT_ADDR, segID, 0, ofst);
//	}
	
	public SegmentAddress(String segID, int ofst) {
		this.kind = DataAddress.SEGMNT_ADDR;
		this.type = Type.T_OADDR;
		this.segID = segID;
		this.ofst = ofst;
	}
	
//	public RTSegmentAddress(DataSegment seg, int ofst) {
////		String segID = (seg == null)? null : seg.ident;
//		this((seg == null)? null : seg.ident, ofst);
//	}
	
	/**
	 * object_address		::= c-oaddr global_or_const:tag
	 */
	public static DataAddress ofScode() {
		Tag tag = Tag.ofScode();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		IO.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(descr instanceof Variable var) return var.address;
		if(descr instanceof ConstDescr cns) return cns.getAddress();
		Util.IERR("MISSING: " + descr);
		return null;
	}

	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}

	public int getOfst() { return ofst; }
	public void incrOffset() { ofst++; }
	public void decrOffset() { ofst--; }

	@Override
	public SegmentAddress addOffset(int ofst) {
		return new SegmentAddress(segID, this.ofst + ofst);
	}

	public Value copy() {
		return new SegmentAddress(segID, ofst);
	}

	@Override
	public DataAddress toOADDR() {
		if(! Global.duringEXEC()) Util.IERR("");
		return this;
	}

	@Override
	public void store(int idx, Value value, String comment) {
		DataSegment dseg = segment();
		int reladdr = ofst + idx;
		dseg.store(reladdr, value);
	}
	
	@Override
	public Value load(int idx) {
		DataSegment dseg = segment();
		int reladdr = ofst + idx;				
		return dseg.load(reladdr);
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = (other == null)? null : ((SegmentAddress)other).segID;
		int rhs = (other == null)? 0 : ((SegmentAddress)other).ofst;
		return Segment.compare(segID, ofst, relation, RHSegID, rhs);
	}

	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}

	@Override
	public String toString() {
		String s = segID;
		if(ofst != 0) s = s + '[' + ofst + ']';
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SegmentAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.SEGMNT_ADDR;
		this.type = Type.T_OADDR;
		segID = inpt.readString();
		ofst = inpt.readShort();
//		IO.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("RTSegmentAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeString(segID);
		oupt.writeShort(ofst);
//		IO.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeString(segID);
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new SegmentAddress(inpt);
	}

}
