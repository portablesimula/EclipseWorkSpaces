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

public class ObjectAddress_Kopi extends Value {
//	public DataSegment seg;
	public int kind;
	public String segID;
	protected int ofst;
	
	public static final int SEGMNT_ADDR = 1; // Segment Address
	public static final int REMOTE_ADDR = 2; // Remote Address
	public static final int REFER_ADDR = 3; // Refer Address
	public static final int REL_ADDR = 4; // Frame relative Addtess
	public static final int STACK_ADDR = 5; // Stack relative Address
	
	protected ObjectAddress_Kopi(int kind, String segID,	int ofst) {
		this.type = Type.T_OADDR;
		this.kind = kind;
		this.segID = segID;
		this.ofst = ofst;
	}
	
	/**
	 * object_address		::= c-oaddr global_or_const:tag
	 */
	public static ObjectAddress ofScode() {
		Tag tag = Tag.ofScode();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		IO.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(descr instanceof Variable var) return var.address;
		if(descr instanceof ConstDescr cns) return cns.getAddress();
		Util.IERR("MISSING: " + descr);
		return null;
	}
	
	public static ObjectAddress_Kopi ofSegAddr(String segID, int ofst) {
		return new ObjectAddress_Kopi(SEGMNT_ADDR, segID, ofst);
	}
	
	public static ObjectAddress_Kopi ofSegAddr(DataSegment seg, int ofst) {
		String segID = (seg == null)? null : seg.ident;
		return new ObjectAddress_Kopi(SEGMNT_ADDR, segID, ofst);
	}
	
//	public static ObjectAddress ofRelAddr(DataSegment seg) {
//		String segID = (seg == null)? null : seg.ident;
//		return new ObjectAddress(segID, 0);
//	}

	public static ObjectAddress_Kopi ofRemoteAddr() {
		return new ObjectAddress_Kopi(REMOTE_ADDR, null, 0);
	}

	public static ObjectAddress_Kopi ofReferAddr() {
		return new ObjectAddress_Kopi(REFER_ADDR, null, 0);
	}

	public static ObjectAddress_Kopi ofRelFrameAddr(int ofst) {
		return new ObjectAddress_Kopi(REL_ADDR, null, ofst);
	}
	
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
	
	public Value copy() {
		return new ObjectAddress_Kopi(kind, segID,	ofst);
	}
	
	public int getOfst() { return ofst; }
	public void incrOffset() { ofst++; }
	public void decrOffset() { ofst--; }
	
	public ObjectAddress_Kopi addOffset(int ofst) {
		return new ObjectAddress_Kopi(this.kind, segID, this.ofst + ofst);
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}
	
	public void store(int idx, Value value, String comment) {
		if(segID == null) {
			// store rel-addr  callStackTop + ofst
//			IO.println("ObjectAddress.store: callStackTop="+RTStack.callStackTop.rtStackIndex);
			RTStack.store(RTStack.callStack_TOP().rtStackIndex + ofst + idx, value, comment);
//			Util.IERR("");
			
		} else {
		DataSegment dseg = segment();
		dseg.store(ofst + idx, value);
//		dseg.dump("MemAddr.store: ");
//		Util.IERR("");
		}
	}
	
	public Value load(int idx) {
		if(segID == null) {
			// load from rel-addr  callStackTop + ofst
//			IO.println("ObjectAddress.load: callStackTop="+RTStack.callStackTop.rtStackIndex);
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			Value item = RTStack.load(bias + ofst + idx);
//			IO.println("ObjectAddress.load: value="+value);
//			Util.IERR("");
			return item;
		} else {
		DataSegment dseg = segment();
		Value value =  dseg.load(ofst + idx);
//		dseg.dump("MemAddr.load: ");
//		Util.IERR("");	
		return value;
		}
	}
	
	public Value load() {
		if(segID == null) {
			// load from rel-addr  callStackTop + ofst
//			IO.println("ObjectAddress.load: callStackTop="+RTStack.callStackTop.rtStackIndex);
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			Value item = RTStack.load(bias + ofst);
//			IO.println("ObjectAddress.load: value="+value);
//			Util.IERR("");
			return item;
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
			return new ObjectAddress_Kopi(this.kind, this.segID, this.ofst + ival.value);
		} else if(other instanceof ObjectAddress_Kopi oaddr) {
			IO.println("ObjectAddress.add: this="+this);
			IO.println("ObjectAddress.add: other="+other);
			if(!oaddr.segID.equals(segID))
				Util.IERR("Illegal ObjectAddress'add operation: "+oaddr.segID+" != "+segID);
			return new ObjectAddress_Kopi(this.kind, this.segID, this.ofst + oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}

	@Override
	public Value sub(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new ObjectAddress_Kopi(this.kind, this.segID, this.ofst - ival.value);
		} else if(other instanceof ObjectAddress_Kopi oaddr) {
//			IO.println("ObjectAddress.sub: this="+this);
//			IO.println("ObjectAddress.sub: other="+other);
			if(!oaddr.segID.equals(segID)) {
				RTStack.dumpRTStack("ObjectAddress.sub: ");
//				Segment.lookup("CSEG_ADHOC02").dump("ProgramAddress.execute: FINAL ", 0, 20);
				Segment.lookup("CSEG_ADHOC02").dump("ProgramAddress.execute: FINAL ");
				Util.IERR("Illegal ObjectAddress'sub operation: "+oaddr.segID+" != "+segID);
			}
			return IntegerValue.of(Type.T_SIZE, this.ofst - oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = (other == null)? null : ((ObjectAddress_Kopi)other).segID;
		int rhs = (other == null)? 0 : ((ObjectAddress_Kopi)other).ofst;
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
////		IO.println("IntegerValue.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
////		Util.IERR("");
//		return res;
//	}
	
	@Override
	public String toString() {
//		if(segID == null) {
//			return "RELADR[callStackTop+" + ofst + ']';
//		} else {
//			return segID + '[' + ofst + ']';
//		}
		switch(kind) {
			case SEGMNT_ADDR: return segID + '[' + ofst + ']';
			case REMOTE_ADDR: return "REMOTE_ADDR[RTStackTop+" + ofst + ']';
			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
			case REL_ADDR:    return "REL_ADR[callStackTop+" + ofst + ']';
			case STACK_ADDR:  return "STACK_ADR[RTStack(" + ofst + ")]";
			default: return "UNKNOWN";
		}
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ObjectAddress_Kopi(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_OADDR;
		kind = inpt.readKind();
		segID = inpt.readString();
		ofst = inpt.readShort();
//		IO.println("ObjectAddress.read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeString(segID);
		oupt.writeShort(ofst);
//		IO.println("ObjectAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

	public static ObjectAddress_Kopi read(AttributeInputStream inpt) throws IOException {
		return new ObjectAddress_Kopi(inpt);
	}

}
