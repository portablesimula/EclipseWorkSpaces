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

public class ObjectAddress extends Value {
//	public DataSegment seg;
	public int kind;
	public String segID;
	protected int ofst;
	
	public static final int SEGMNT_ADDR = 1; // Segment Address
	public static final int REMOTE_ADDR = 2; // Remote Address
	public static final int REFER_ADDR = 3; // Refer Address
	public static final int REL_ADDR = 4; // Frame relative Addtess
	public static final int STACK_ADDR = 5; // Stack relative Address
	
	protected ObjectAddress(int kind, String segID,	int ofst) {
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
//		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(descr instanceof Variable var) return var.address;
		if(descr instanceof ConstDescr cns) return cns.getAddress();
		Util.IERR("MISSING: " + descr);
		return null;
	}
	
	public static ObjectAddress ofSegAddr(String segID, int ofst) {
		return new ObjectAddress(SEGMNT_ADDR, segID, ofst);
	}
	
	public static ObjectAddress ofSegAddr(DataSegment seg, int ofst) {
		String segID = (seg == null)? null : seg.ident;
		return new ObjectAddress(SEGMNT_ADDR, segID, ofst);
	}
	
//	public static ObjectAddress ofRelAddr(DataSegment seg) {
//		String segID = (seg == null)? null : seg.ident;
//		return new ObjectAddress(segID, 0);
//	}

	public static ObjectAddress ofRemoteAddr() {
		return new ObjectAddress(REMOTE_ADDR, null, 0);
	}

	public static ObjectAddress ofReferAddr() {
		return new ObjectAddress(REFER_ADDR, null, 0);
	}

	public static ObjectAddress ofRelFrameAddr(int ofst) {
		return new ObjectAddress(REL_ADDR, null, ofst);
	}
	
	public int size() {
		return type.size();
	}
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
		
	public Value copy() {
		return new ObjectAddress(kind, segID,	ofst);
	}
	
	public int getOfst() { return ofst; }
	public void incrOffset() { ofst++; }
	public void decrOffset() { ofst--; }
	
	public ObjectAddress addOffset(int ofst) {
		return new ObjectAddress(this.kind, segID, this.ofst + ofst);
	}
	
	public ObjectAddress toStackAddress() {
		ObjectAddress oaddr = this;
		if(this.kind == ObjectAddress.REL_ADDR) {
//			System.out.println("ObjectAddress.toStackAddress: OADDR: "+this);
//			RTStack.dumpRTStack("ObjectAddress.toStackAddress: NOTE: ");
//			System.out.println("ObjectAddress.toStackAddress: VALUE: "+this.load());
			
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			oaddr = new ObjectAddress(STACK_ADDR, segID, bias + ofst);
			
//			System.out.println("ObjectAddress.toStackAddress: VALUE: "+oaddr.load());
		}
//		Util.IERR("");
		return oaddr;
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}
	
	public void store(int idx, Value value, String comment) {
		switch(kind) {
			case SEGMNT_ADDR:
				DataSegment dseg = segment();
				int reladdr = ofst + idx;
				dseg.store(reladdr, value);
				break;
			case REL_ADDR:
				int frmx = RTStack.frameIndex();
				RTStack.store(frmx + ofst + idx, value, comment);

//				Util.IERR("");
				break;
//			case REMOTE_ADDR: return "REMOTE_ADDR[RTStackTop+" + ofst + ']';
//			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
			case STACK_ADDR: //  return "STACK_ADR[RTStack(" + ofst + ")]";
//				System.out.println("ObjectAddress.store: "+value+"  "+this);
//				RTStack.dumpRTStack("ObjectAddress.store: "+this);
				RTStack.store(ofst + idx, value, comment);
//				RTStack.dumpRTStack("ObjectAddress.store: "+this);
//				Util.IERR("");
				break;
			default: Util.IERR(""+kind);
		}
	}
	
	public Value load(int idx) {
		switch(kind) {
			case SEGMNT_ADDR:
				DataSegment dseg = segment();
				int reladdr = ofst + idx;				
				return dseg.load(reladdr);
			case REL_ADDR:
				// load rel-addr  callStackTop + ofst
				CallStackFrame callStackTop = RTStack.callStack_TOP();
				int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
				Value value = RTStack.load(bias + ofst + idx);
//				System.out.println("ObjectAddress.load: value="+value);
//				Util.IERR("");
				return value;
//			case REMOTE_ADDR: return "REMOTE_ADDR[RTStackTop+" + ofst + ']';
//			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
			case STACK_ADDR:
				value = RTStack.load(ofst + idx);
//				System.out.println("ObjectAddress.load: value="+value);
//				Util.IERR("");
				return value;
			default: Util.IERR(""+kind); return null;
		}
	}
	
	public Value load() {
		return load(0);
	}


	@Override
	public Value add(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new ObjectAddress(this.kind, this.segID, this.ofst + ival.value);
		} else if(other instanceof ObjectAddress oaddr) {
			System.out.println("ObjectAddress.add: this="+this);
			System.out.println("ObjectAddress.add: other="+other);
			if(!oaddr.segID.equals(segID))
				Util.IERR("Illegal ObjectAddress'add operation: "+oaddr.segID+" != "+segID);
			return new ObjectAddress(this.kind, this.segID, this.ofst + oaddr.ofst);
		} else {
			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
			return null;
		}
	}

	@Override
	public Value sub(Value other) {
		if(other == null) return this;
		if(other instanceof IntegerValue ival) {
			return new ObjectAddress(this.kind, this.segID, this.ofst - ival.value);
		} else if(other instanceof ObjectAddress oaddr) {
//			System.out.println("ObjectAddress.sub: this="+this);
//			System.out.println("ObjectAddress.sub: other="+other);
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
	
	public void dumpArea(String title, int lng) {
		if(this.segID != null) {
			segment().dump("\nRTAddress.dumpArea:", ofst, ofst+lng);
		} else {
			System.out.println("\nRTAddress.dumpArea: BEGIN " + title + " +++++++++++++++++++++++++++++++++++++");
			for(int i=0;i<lng;i++) {
//				RTAddress rtadr = new RTAddress(this, i);
				ObjectAddress rtadr = this.addOffset(i);
				System.out.println(""+rtadr+": " + load(ofst+i));
			}
			System.out.println("RTAddress.dumpArea: ENDOF " + title + " +++++++++++++++++++++++++++++++++++++");
		}
//		Util.IERR("");
	}
	
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
	private ObjectAddress(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_OADDR;
//		readLocals(this, inpt);
		kind = inpt.readKind();
		segID = inpt.readString();
		ofst = inpt.readShort();
//		System.out.println("ObjectAddress.read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		writeBody(oupt);
//		System.out.println("ObjectAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

	public void writeBody(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(kind);
		oupt.writeString(segID);
		oupt.writeShort(ofst);
	}
	
	public static ObjectAddress read(AttributeInputStream inpt) throws IOException {
		return new ObjectAddress(inpt);
	}

}
