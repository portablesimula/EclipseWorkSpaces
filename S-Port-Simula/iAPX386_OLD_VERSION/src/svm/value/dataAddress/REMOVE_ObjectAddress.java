package svm.value.dataAddress;

import bec.value.Value;

//import java.io.IOException;
//
//import bec.AttributeInputStream;
//import bec.AttributeOutputStream;
//import bec.descriptor.ConstDescr;
//import bec.descriptor.Descriptor;
//import bec.descriptor.Variable;
//import bec.segment.DataSegment;
//import bec.segment.Segment;
//import bec.util.Global;
//import bec.util.Scode;
//import bec.util.Tag;
//import bec.util.Type;
//import bec.util.Util;
//import bec.value.GeneralAddress;
//import bec.value.IntegerValue;
//import bec.value.Value;
//import bec.virtualMachine.CallStackFrame;
//import bec.virtualMachine.RTRegister;
//import bec.virtualMachine.RTStack;

public class REMOVE_ObjectAddress extends Value {
////	public DataSegment seg;
//	public int kind;
//	public String segID;
//	public int xReg; // BARE HVIS: TESTING_REMOTE
//	protected int ofst;
//	
//	protected REMOVE_ObjectAddress(int kind, String segID, int xReg, int ofst) {
//		this.type = Type.T_OADDR;
//		this.kind = kind;
//		this.segID = segID;
//		this.xReg = xReg;
//		this.ofst = ofst;
//	}
//	
//	/**
//	 * object_address		::= c-oaddr global_or_const:tag
//	 */
//	public static REMOVE_ObjectAddress ofScode() {
//		Tag tag = Tag.ofScode();
//		Descriptor descr = tag.getMeaning();
//		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
////		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
//		if(descr instanceof Variable var) return var.address;
//		if(descr instanceof ConstDescr cns) return cns.getAddress();
//		Util.IERR("MISSING: " + descr);
//		return null;
//	}
//	
//	public static REMOVE_ObjectAddress ofSegAddr(String segID, int ofst) {
//		return new REMOVE_ObjectAddress(SEGMNT_ADDR, segID, 0, ofst);
//	}
//	
//	public static REMOVE_ObjectAddress ofSegAddr(DataSegment seg, int ofst) {
//		String segID = (seg == null)? null : seg.ident;
//		return new REMOVE_ObjectAddress(SEGMNT_ADDR, segID, 0, ofst);
//	}
//	
////	public static DataAddress ofRelAddr(DataSegment seg) {
////		String segID = (seg == null)? null : seg.ident;
////		return new DataAddress(segID, 0);
////	}
//
//	public static REMOVE_ObjectAddress ofRemoteAddr(int xReg, int ofst) {  // BARE HVIS: TESTING_REMOTE
//		if(! Global.TESTING_REMOTE) Util.IERR("SKAL IKKE BRUKES");
//		REMOVE_ObjectAddress addr = new REMOVE_ObjectAddress(REMOTE_ADDR, null, xReg, ofst);
////		addr.xReg = xReg;
//		System.out.println("DataAddress.ofRemoteAddr: "+addr);
//		if(xReg == 0) Util.IERR("");
////		System.exit(-1);
//		return addr;
//	}
//
//	public static REMOVE_ObjectAddress ofRemoteAddr() { // BARE HVIS IKKE: TESTING_REMOTE
//		if(Global.TESTING_REMOTE) Util.IERR("SKAL IKKE BRUKES");
//		return new REMOVE_ObjectAddress(REMOTE_ADDR, null, 0, 0);
//	}
//
//	public static REMOVE_ObjectAddress ofReferAddr() {
//		return new REMOVE_ObjectAddress(REFER_ADDR, null, 0, 0);
//	}
//
//	public static REMOVE_ObjectAddress ofRelFrameAddr(int ofst) {
//		return new REMOVE_ObjectAddress(REL_ADDR, null, 0, ofst);
//	}
//	
//	public RTAddress toRTAddress() {
//		switch(kind) {
//			case SEGMNT_ADDR: return new RTSegmentAddress(segID, ofst);
//			case REMOTE_ADDR: return new RTRemoteAddress(xReg, ofst);
//			case REFER_ADDR:  return new RTReferAddress(ofst);
//			case REL_ADDR:    return new RTRelFrameAddress(ofst);
//			case STACK_ADDR:  return new RTStackAddress(ofst);
//		}
//		Util.IERR("");
//		return null;
//	}
//	
//	public int size() {
//		return type.size();
//	}
//	
//	public DataSegment segment() {
//		if(segID == null) return null;
//		return (DataSegment) Segment.lookup(segID);
//	}
//		
//	public Value copy() {
//		return new REMOVE_ObjectAddress(kind, segID, xReg, ofst);
//	}
//	
//	public int getOfst() { return ofst; }
//	public void incrOffset() { ofst++; }
//	public void decrOffset() { ofst--; }
//	
//	public REMOVE_ObjectAddress addOffset(int ofst) {
//		return new REMOVE_ObjectAddress(this.kind, segID, xReg, this.ofst + ofst);
//	}
//	
//	public REMOVE_ObjectAddress toStackAddress() {
//		REMOVE_ObjectAddress oaddr = this;
//		if(this.kind == REMOVE_ObjectAddress.REL_ADDR) {
////			System.out.println("DataAddress.toStackAddress: OADDR: "+this);
////			RTStack.dumpRTStack("DataAddress.toStackAddress: NOTE: ");
////			System.out.println("DataAddress.toStackAddress: VALUE: "+this.load());
//			
//			CallStackFrame callStackTop = RTStack.callStack_TOP();
//			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
//			oaddr = new REMOVE_ObjectAddress(STACK_ADDR, segID, xReg, bias + ofst);
//			
////			System.out.println("DataAddress.toStackAddress: VALUE: "+oaddr.load());
//		}
////		Util.IERR("");
//		return oaddr;
//	}
//	
//	@Override
//	public void emit(DataSegment dseg, String comment) {
//		dseg.emit(this, comment);
//	}
//	
//	public void store(int idx, Value value, String comment) {
//		switch(kind) {
//			case SEGMNT_ADDR:
//				DataSegment dseg = segment();
//				int reladdr = ofst + idx;
//				dseg.store(reladdr, value);
//				break;
//			case REL_ADDR:
//				int frmx = RTStack.frameIndex();
//				RTStack.store(frmx + ofst + idx, value, comment);
//
////				Util.IERR("");
//				break;
//			case REMOTE_ADDR:
//				//return "REMOTE_ADDR[RTStackTop+" + ofst + ']';
//				System.out.println("DataAddress.store: "+value+"  "+this+", idx="+idx);
//				REMOVE_ObjectAddress regAddr = (REMOVE_ObjectAddress) RTRegister.getValue(xReg);
//				dseg = regAddr.segment();
//				int regOfst = regAddr.ofst;
//				if(regAddr.xReg != 0) Util.IERR("NOT IMPL");
//				
//				System.out.println("DataAddress.store: dseg="+dseg);
//				System.out.println("DataAddress.store: regOfst="+regOfst);
//				System.out.println("DataAddress.store: ofst="+ofst);
//
//				reladdr = regOfst + ofst + idx;
//				dseg.store(reladdr, value);
//
////				Util.IERR("NOT IMPL");
//				break;
////			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
//			case STACK_ADDR: //  return "STACK_ADR[RTStack(" + ofst + ")]";
////				System.out.println("DataAddress.store: "+value+"  "+this);
////				RTStack.dumpRTStack("DataAddress.store: "+this);
//				RTStack.store(ofst + idx, value, comment);
////				RTStack.dumpRTStack("DataAddress.store: "+this);
////				Util.IERR("");
//				break;
//			default: Util.IERR(""+kind);
//		}
//	}
//	
//	public Value load(int idx) {
//		switch(kind) {
//			case SEGMNT_ADDR:
//				DataSegment dseg = segment();
//				int reladdr = ofst + idx;				
//				return dseg.load(reladdr);
//			case REL_ADDR:
//				// load rel-addr  callStackTop + ofst
//				CallStackFrame callStackTop = RTStack.callStack_TOP();
//				int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
//				Value value = RTStack.load(bias + ofst + idx);
////				System.out.println("DataAddress.load: value="+value);
////				Util.IERR("");
//				return value;
//			case REMOTE_ADDR:
//				// return "REMOTE_ADDR[RTStackTop+" + ofst + ']';
//				System.out.println("DataAddress.load: "+this+", idx="+idx);
//				Value regVal = RTRegister.getValue(xReg);
//				System.out.println("DataAddress.load: regVal="+regVal);
//				
////				DataAddress regAddr = (DataAddress) RTRegister.getValue(xReg);
//				REMOVE_ObjectAddress regAddr = null;
//				int gOfst = 0;
//				if(regVal instanceof GeneralAddress gaddr) {
//					regAddr = gaddr.base;
//					gOfst = gaddr.ofst;
//				} else regAddr = (REMOVE_ObjectAddress)regVal;
//				
//				dseg = regAddr.segment();
//				int regOfst = regAddr.ofst;
//				if(regAddr.xReg != 0) Util.IERR("NOT IMPL");
//				
//				System.out.println("DataAddress.load: dseg="+dseg);
//				System.out.println("DataAddress.load: regOfst="+regOfst);
//				System.out.println("DataAddress.load: ofst="+ofst);
//
//				reladdr = gOfst + regOfst + ofst + idx;
////				Util.IERR("NOT IMPL"+reladdr);
//				return dseg.load(reladdr);
////			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
//			case STACK_ADDR:
//				value = RTStack.load(ofst + idx);
////				System.out.println("DataAddress.load: value="+value);
////				Util.IERR("");
//				return value;
//			default: Util.IERR(""+kind); return null;
//		}
//	}
//	
//	public Value load() {
//		return load(0);
//	}
//
//
//	@Override
//	public Value add(Value other) {
//		if(other == null) return this;
//		if(other instanceof IntegerValue ival) {
//			return new REMOVE_ObjectAddress(this.kind, this.segID, this.xReg, this.ofst + ival.value);
//		} else {
//			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
//			return null;
//		}
//	}
//
//	@Override
//	public Value sub(Value other) {
//		if(other == null) return this;
//		if(other instanceof IntegerValue ival) {
//			return new REMOVE_ObjectAddress(this.kind, this.segID, this.xReg, this.ofst - ival.value);
//		} else if(other instanceof REMOVE_ObjectAddress oaddr) {
////			System.out.println("DataAddress.sub: this="+this);
////			System.out.println("DataAddress.sub: other="+other);
//			if(!oaddr.segID.equals(segID)) {
//				RTStack.dumpRTStack("DataAddress.sub: ");
////				Segment.lookup("CSEG_ADHOC02").dump("ProgramAddress.execute: FINAL ", 0, 20);
//				Segment.lookup("CSEG_ADHOC02").dump("ProgramAddress.execute: FINAL ");
//				Util.IERR("Illegal DataAddress'sub operation: "+oaddr.segID+" != "+segID);
//			}
//			return IntegerValue.of(Type.T_SIZE, this.ofst - oaddr.ofst);
//		} else {
//			Util.IERR(""+other.getClass().getSimpleName()+"  "+other);
//			return null;
//		}
//	}
//
//	@Override
//	public boolean compare(int relation, Value other) {
//		String RHSegID = (other == null)? null : ((REMOVE_ObjectAddress)other).segID;
//		int rhs = (other == null)? 0 : ((REMOVE_ObjectAddress)other).ofst;
//		return Segment.compare(segID, ofst, relation, RHSegID, rhs);
//	}
//
////	@Override
////	public boolean compare(int relation, Value other) {
////		int LHS = this.getOfst();
////		int RHS = (other == null)? 0 : ((DataAddress)other).getOfst();
////		boolean res = false;
////		switch(relation) {
////			case Scode.S_LT: res = LHS <  RHS; break;
////			case Scode.S_LE: res = LHS <= RHS; break;
////			case Scode.S_EQ: res = LHS == RHS; break;
////			case Scode.S_GE: res = LHS >= RHS; break;
////			case Scode.S_GT: res = LHS >  RHS; break;
////			case Scode.S_NE: res = LHS != RHS; break;
////		}
//////		System.out.println("IntegerValue.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
//////		Util.IERR("");
////		return res;
////	}
//	
//	public void dumpArea(String title, int lng) {
//		if(this.segID != null) {
//			segment().dump("\nRTAddress.dumpArea:", ofst, ofst+lng);
//		} else {
//			System.out.println("\nRTAddress.dumpArea: BEGIN " + title + " +++++++++++++++++++++++++++++++++++++");
//			for(int i=0;i<lng;i++) {
////				RTAddress rtadr = new RTAddress(this, i);
//				REMOVE_ObjectAddress rtadr = this.addOffset(i);
//				System.out.println(""+rtadr+": " + load(ofst+i));
//			}
//			System.out.println("RTAddress.dumpArea: ENDOF " + title + " +++++++++++++++++++++++++++++++++++++");
//		}
////		Util.IERR("");
//	}
//	
//	@Override
//	public String toString() {
////		if(segID == null) {
////			return "RELADR[callStackTop+" + ofst + ']';
////		} else {
////			return segID + '[' + ofst + ']';
////		}
//		switch(kind) {
//			case SEGMNT_ADDR: return segID + '[' + ofst + ']';
//			case REMOTE_ADDR:
//				if(Global.TESTING_REMOTE) {
//					return "REMOTE_ADDR[" + RTRegister.edRegVal(xReg) + ']';
//				} else {
//					return "REMOTE_ADDR[RTStackTop+" + ofst + RTRegister.edReg(xReg) + ']';
//				}
//			case REFER_ADDR:  return "REFER_ADDR[" + ofst + ']';
//			case REL_ADDR:    return "REL_ADR[callStackTop+" + ofst + ']';
//			case STACK_ADDR:  return "STACK_ADR[RTStack(" + ofst + ")]";
//			default: return "UNKNOWN";
//		}
//	}
//
//	// ***********************************************************************************************
//	// *** Attribute File I/O
//	// ***********************************************************************************************
//	private REMOVE_ObjectAddress(AttributeInputStream inpt) throws IOException {
//		this.type = Type.T_OADDR;
////		readLocals(this, inpt);
//		kind = inpt.readKind();
//		segID = inpt.readString();
//		xReg = inpt.readReg(); // BARE HVIS: TESTING_REMOTE
//		ofst = inpt.readShort();
////		System.out.println("DataAddress.read: " + this);
//	}
//
//	public void write(AttributeOutputStream oupt) throws IOException {
//		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
//		oupt.writeKind(Scode.S_C_OADDR);
//		writeBody(oupt);
////		System.out.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
//	}
//
//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeKind(kind);
//		oupt.writeString(segID);
//		oupt.writeReg(xReg); // BARE HVIS: TESTING_REMOTE
//		oupt.writeShort(ofst);
//	}
//	
//	public static REMOVE_ObjectAddress read(AttributeInputStream inpt) throws IOException {
//		return new REMOVE_ObjectAddress(inpt);
//	}

}
