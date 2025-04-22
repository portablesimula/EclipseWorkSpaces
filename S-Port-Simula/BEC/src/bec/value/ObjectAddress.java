package bec.value;

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
import bec.virtualMachine.CallStackFrame;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTStack.RTStackItem;

public class ObjectAddress extends Value {
//	public DataSegment seg;
	public String segID;
	protected int ofst;
	
	public ObjectAddress(String segID,	int ofst) {
		this.segID = segID;
		this.ofst = ofst;
//		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	private ObjectAddress(DataSegment seg,	int ofst) {
//		this.seg = seg;
		if(seg != null)	this.segID = seg.ident;
		this.ofst = ofst;
//		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	private ObjectAddress(int ofst) {
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	/**
	 * attribute_address	::= c-aaddr attribute:tag
	 * object_address		::= c-oaddr global_or_const:tag
	 * general_address		::= c-gaddr global_or_const:tag
	 * routine_address		::= c-raddr body:tag
	 * program_address		::= c-paddr label:tag
	 */
	public static ObjectAddress ofScode() {
		Tag tag = Tag.ofScode();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
//		Util.IERR("NOT IMPL");
//		return null;
		if(descr instanceof Variable var) return var.address;
//		if(descr instanceof ConstDescr cns) return cns.address;
		if(descr instanceof ConstDescr cns) return cns.getAddress();
		Util.IERR("MISSING: " + descr);
		return null;
	}
	
	public static ObjectAddress ofSegAddr(DataSegment seg, int ofst) {
		return new ObjectAddress(seg, ofst);
	}
	
	public static ObjectAddress ofRelAddr(DataSegment seg) {
		return new ObjectAddress(seg, 0);
	}
	
	public static ObjectAddress ofRelFrameAddr(int ofst) {
		return new ObjectAddress(ofst);
	}
	
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
	
	public Value copy() {
		return new ObjectAddress(segID,	ofst);
	}
	
	public int getOfst() { return ofst; }
	public void incrOffset() { ofst++; }
	public void decrOffset() { ofst--; }
	
	public ObjectAddress addOffset(int ofst) {
		return new ObjectAddress(segID, this.ofst + ofst);
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}
	
	public void store(int idx, Value value, String comment) {
		if(segID == null) {
			// store rel-addr  callStackTop + ofst
//			System.out.println("ObjectAddress.store: callStackTop="+RTStack.callStackTop.rtStackIndex);
			RTStack.store(RTStack.callStack_TOP().rtStackIndex + ofst + idx, value, comment);
//			Util.IERR("");
			
		} else {
		DataSegment dseg = segment();
		dseg.store(ofst + idx, value);
//		dseg.dump("MemAddr.store: ");
//		Util.IERR("");
		}
	}
	
	public Value load() {
		if(segID == null) {
			// load from rel-addr  callStackTop + ofst
//			System.out.println("ObjectAddress.load: callStackTop="+RTStack.callStackTop.rtStackIndex);
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			RTStackItem item = RTStack.load(bias + ofst);
//			System.out.println("ObjectAddress.load: value="+value);
//			Util.IERR("");
			return item.value();
		} else {
		DataSegment dseg = segment();
		Value value =  dseg.load(ofst);
//		dseg.dump("MemAddr.load: ");
//		Util.IERR("");	
		return value;
		}
	}


	@Override
	public Value add(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new ObjectAddress(this.segID, this.ofst + ival.value);
		} else if(other instanceof ObjectAddress oaddr) {
			System.out.println("ObjectAddress.add: this="+this);
			System.out.println("ObjectAddress.add: other="+other);
			if(!oaddr.segID.equals(segID))
				Util.IERR("Illegal ObjectAddress'add operation: "+oaddr.segID+" != "+segID);
			return new ObjectAddress(this.segID, this.ofst + oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}

	@Override
	public Value sub(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new ObjectAddress(this.segID, this.ofst - ival.value);
		} else if(other instanceof ObjectAddress oaddr) {
//			System.out.println("ObjectAddress.sub: this="+this);
//			System.out.println("ObjectAddress.sub: other="+other);
			if(!oaddr.segID.equals(segID))
				Util.IERR("Illegal ObjectAddress'add operation: "+oaddr.segID+" != "+segID);
			return IntegerValue.of(Type.T_SIZE, this.ofst - oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = (other == null)? null : ((ObjectAddress)other).segID;
		int rhs = (other == null)? 0 : ((ObjectAddress)other).ofst;
		return Segment.compare(segID, ofst, relation, RHSegID, rhs);
	}

//	@Override
//	public boolean compare(int relation, Value other) {
//		int LHS = this.getOfst();
//		int RHS = (other == null)? 0 : ((ObjectAddress)other).getOfst();
//		boolean res = false;
//		switch(relation) {
//			case Scode.S_LT: res = LHS <  RHS; break;
//			case Scode.S_LE: res = LHS <= RHS; break;
//			case Scode.S_EQ: res = LHS == RHS; break;
//			case Scode.S_GE: res = LHS >= RHS; break;
//			case Scode.S_GT: res = LHS >  RHS; break;
//			case Scode.S_NE: res = LHS != RHS; break;
//		}
////		System.out.println("IntegerValue.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
////		Util.IERR("");
//		return res;
//	}
	
	@Override
	public String toString() {
		if(segID == null) {
			return "RELADR[callStackTop+" + ofst + ']';
		} else {
			return segID + '[' + ofst + ']';
			
		}
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ObjectAddress(AttributeInputStream inpt) throws IOException {
//		String ident = inpt.readString();
		segID = inpt.readString();
//		ofst = inpt.readInt();
		ofst = inpt.readShort();
//		if(ident != null) seg = Segment.lookup(ident);

//		System.out.println("=============================================================================================================== " + this);
		if(ofst > 9000 || ofst < 0) Util.IERR(""+ofst);
//		Util.IERR(""+seg);
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeString(segID);
//		oupt.writeInt(ofst);
		oupt.writeShort(ofst);
		if(ofst > 9000 || ofst < 0) Util.IERR("");
//		System.out.println("=============================================================================================================== " + this);
	}

	public static ObjectAddress read(AttributeInputStream inpt) throws IOException {
		return new ObjectAddress(inpt);
	}

}
