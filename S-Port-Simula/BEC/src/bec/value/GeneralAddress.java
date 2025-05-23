package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Attribute;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

public class GeneralAddress extends Value {
	public ObjectAddress base;
	public int ofst;
	
	public GeneralAddress(ObjectAddress base, int ofst) {
		this.type = Type.T_GADDR;
		this.base = base;
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
		Tag tag = Tag.ofScode();
//		Variable var = (Variable) tag.getMeaning();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
		if(descr instanceof Variable var) {
//			System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
			return new GeneralAddress(var.address, 0);
		} else if(descr instanceof ConstDescr cns) {
			return new GeneralAddress(cns.getAddress(), 0);
		}
		Util.IERR("NOT IMPL: " + descr.getClass().getSimpleName());
		return null;
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = null; //(other == null)? null : ((GeneralAddress)other).segID;
		int rhs = 0;           //(other == null)? 0 : ((GeneralAddress)other).ofst;
		if(other != null) {
			GeneralAddress gaddr = (GeneralAddress) other;
			ObjectAddress base = gaddr.base;
			RHSegID = base.segID;
			rhs = base.getOfst() + gaddr.ofst;
		}
		return Segment.compare(this.base.segID, base.getOfst() + ofst, relation, RHSegID, rhs);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new GeneralAddress(this.base, this.ofst + ival.value);
//		} else if(other instanceof ObjectAddress oaddr) {
//			System.out.println("ObjectAddress.add: this="+this);
//			System.out.println("ObjectAddress.add: other="+other);
//			if(!oaddr.segID.equals(segID))
//				Util.IERR("Illegal ObjectAddress'add operation: "+oaddr.segID+" != "+segID);
//			return new ObjectAddress(this.segID, this.ofst + oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}
	
	public String toString() {
		return (base == null)? "GNONE" : base.toString() + '[' + ofst + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private GeneralAddress(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_GADDR;
		base = (ObjectAddress) Value.read(inpt);
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
