package svm.value.dataAddress;

import java.io.IOException;

import iAPX.descriptor.AttrDescr;
import iAPX.descriptor.ConstDescr;
import iAPX.descriptor.Descriptor;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Display;
import iAPX.util.Global;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;
import svm.segment.Segment;
import svm.value.IntegerValue;
import svm.value.Value;


public class GeneralAddress extends DataAddress {
	public DataAddress base;
	public int ofst;
	
	public GeneralAddress(DataAddress address, int ofst) {
		this.type = Type.T_GADDR;
		this.base = address;
		this.ofst = ofst;
	}
	
//	public GADDR_Value(Segment seg,	int ofst) {
//		if(seg != null)	this.segID = seg.ident;
//		this.ofst = ofst;
//		if(ofst > 9000 || ofst < 0) Util.IERR("");
//	}
	
	/**
	 * attribute_address	::= c-aaddr attribute:tag
	 * object_address		::= c-oaddr global_or_const:tag
	 * general_address		::= c-gaddr global_or_const:tag
	 * routine_address		::= c-raddr body:tag
	 * program_address		::= c-paddr label:tag
	 */
	public static GeneralAddress ofScode() {
		Tag tag = Tag.inTag();
//		Variable var = (Variable) tag.getMeaning();
//		Descriptor descr = tag.getMeaning();
		Descriptor descr = Display.lookup(tag);
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
		
		// DETTE MÅ RETTES OPP
//		if(descr instanceof AttrDescr var) {
//			return new GeneralAddress(var.address, 0);
//		} else if(descr instanceof ConstDescr cns) {
//			return new GeneralAddress(cns.getAddress(), 0);
//		}
		Util.IERR("NOT IMPL: " + descr.getClass().getSimpleName());
		return null;
	}

	@Override
	public Value load(int idx) {
		DataAddress addr = this.base.addOffset(this.ofst);
		Value val = addr.load(idx);
		return val;
	}

	@Override
	public void store(int idx, Value value, String comment) {
		DataAddress addr = this.base.addOffset(this.ofst);
		addr.store(idx, value, comment);
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = null; //(other == null)? null : ((GeneralAddress)other).segID;
		int rhs = 0;           //(other == null)? 0 : ((GeneralAddress)other).ofst;
		if(other != null) {
			GeneralAddress gaddr = (GeneralAddress) other;
			SegmentAddress base = (SegmentAddress) gaddr.base;
			RHSegID = base.segID;
			rhs = base.getOfst() + gaddr.ofst;
		}
//		return Segment.compare(this.base.segID, base.getOfst() + ofst, relation, RHSegID, rhs);
		SegmentAddress LHSbase = (SegmentAddress) this.base;
		return Segment.compare(LHSbase.segID, LHSbase.getOfst() + ofst, relation, RHSegID, rhs);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new GeneralAddress(this.base, this.ofst + ival.value);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}
	
	public String toString() {
		String s = "";
		if(ofst != 0) s = "[" + ofst + ']';
		return (base == null)? "GNONE" : base.toString() + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private GeneralAddress(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_GADDR;
		base = (DataAddress) Value.read(inpt);
		ofst = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_GADDR);
		base.write(oupt);
		oupt.writeShort(ofst);
	}

	public static GeneralAddress read(AttributeInputStream inpt) throws IOException {
		return new GeneralAddress(inpt);
	}

}
