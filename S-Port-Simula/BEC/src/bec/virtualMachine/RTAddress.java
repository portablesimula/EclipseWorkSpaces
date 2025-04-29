package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

public class RTAddress extends Value {
	String segID; // null: RelAddr
	public int offset;
	public int xReg; // 0: no index register
	public boolean withRemoteBase;
	
	private static final boolean DEBUG = false;

	public RTAddress(AddressItem itm) {
		if(itm.objadr != null) {
			this.segID =  itm.objadr.segID;
			this.offset = itm.objadr.getOfst() + itm.offset;
			this.withRemoteBase = itm.withRemoteBase;
		}
		this.xReg = itm.xReg;
	}

	public RTAddress(ObjectAddress objadr, int gOfst) {
		this.segID =  objadr.segID;
		this.offset = objadr.getOfst() + gOfst;			
	}
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
	
	private Value xRegValue() {
//		if(xReg == 0) return 0;
		if(xReg == 0) return null;
		return RTRegister.getValue(xReg);
	}
	
	private int xRegIntValue() {
		if(xReg == 0) return 0;
		IntegerValue ival = (IntegerValue) RTRegister.getValue(xReg);
		return (ival == null)? 0 : ival.value;
	}
	
	public Value load(int idx,int incr) {
		if(segID == null) {
			// load rel-addr  callStackTop + ofst
			Value xRegValue = xRegValue();
			if(xRegValue instanceof GeneralAddress gaddr) {
				ObjectAddress oaddr = gaddr.base;
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + gaddr.ofst + idx;
				if(DEBUG) {
					if(dseg != null) dseg.dump("RTAddress.load: ");
					System.out.println("RTAddress.load: xRegValue="+xRegValue+", reladdr="+reladdr);
				}
				Value res = null;
				if(dseg != null) {
					res = dseg.load(reladdr);
				} else {
//					CallStackFrame top = RTStack.callStack_TOP();
//					int frmx = (top == null)? 0 : top.rtStackIndex;
					int frmx = RTStack.frameIndex();
					if(DEBUG) {
						System.out.println("RTAddress.load: frmx="+frmx);
						RTStack.dumpRTStack("RTAddress.load: ");
					}
					res = RTStack.load(frmx + reladdr).value();
//					Util.IERR("");
				}
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+res);
				return res;
			} else if(xRegValue instanceof ObjectAddress oaddr) {
				if(DEBUG) {
					oaddr.segment().dump("RTAddress.load: ");
					System.out.println("RTAddress.load: xRegValue="+xRegValue);
				}
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + idx;
				Value res = dseg.load(reladdr);
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+res);
				return res;
			} else {
//				CallStackFrame top = RTStack.callStack_TOP();
//				int frmx = (top == null)? 0 : top.rtStackIndex;
				int frmx = RTStack.frameIndex();
				int iReg = xRegIntValue();
				RTStackItem val = RTStack.load(frmx + offset + (iReg * incr) + idx);				
				if(DEBUG)
					System.out.println("RTAddress.load("+idx+") ===> "+val);
				if(val == null) {
//					Global.DSEG.dump("RTAddress.load: ");
					Segment.lookup("POOL_1").dump("RTAddress.load: ", 0, 25);
					System.out.println("RTAddress.load: "+this+", idx="+idx);
					System.out.println("RTAddress.load: withRemoteBase="+this.withRemoteBase);
//					Segment.lookup("PSEG_FIL_FOPEN:BODY").dump("RTAddress.load: ");
//					Util.IERR("SJEKK DETTE");
					System.out.println("RTAddress.load: SJEKK DETTE !!!");
					return null;
				}
				return val.value();				
			}
		} else {
			DataSegment dseg = segment();
			int reladdr = offset + (xRegIntValue() * incr) + idx;
			return dseg.load(reladdr);
		}
	}
	
	public ObjectAddress reladdr2ObjAddr() {
		RTStackItem itm = RTStack.pop();
		System.out.println("RTAddress.reladdr2ObjAddr: itm="+itm);
		
		ObjectAddress objAddr = (ObjectAddress) itm.value();
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

		objAddr = objAddr.addOffset(xRegIntValue());
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

//		Util.IERR("");
		return objAddr;
	}
	
	public void store(int idx, Value value, String comment) {
		if(DEBUG) System.out.println("RTAddress.store: " + value + " ==> " + this + ", idx=" + idx);
		if(segID == null) {
			Value xRegValue = xRegValue();
			if(DEBUG) {
				if(xRegValue == null)
					 System.out.println("RTAddress.store: xRegValue=null");
				else System.out.println("RTAddress.store: xRegValue=" + xRegValue.getClass().getSimpleName() + " " + xRegValue);
			}
			if(xRegValue instanceof ObjectAddress oaddr) {
				if(DEBUG) {
					oaddr.segment().dump("RTAddress.store: ");
					System.out.println("RTAddress.store: xRegValue="+xRegValue);
				}
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + idx;
				dseg.store(reladdr, value);
				if(DEBUG) oaddr.segment().dump("RTAddress.store: ");
//				Util.IERR("");
			} else if(xRegValue instanceof GeneralAddress gaddr) {
				ObjectAddress oaddr = gaddr.base;
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + gaddr.ofst + idx;
				if(DEBUG) {
					if(dseg != null) dseg.dump("RTAddress.store: ");
					System.out.println("RTAddress.store: xRegValue="+xRegValue+", reladdr="+reladdr);
				}
				if(dseg != null) {
					dseg.store(reladdr, value);
				} else {
//					CallStackFrame top = RTStack.callStack_TOP();
//					int frmx = (top == null)? 0 : top.rtStackIndex;
					int frmx = RTStack.frameIndex();
					System.out.println("RTAddress.store: frmx="+frmx);
					RTStack.dumpRTStack("RTAddress.store: ");
//					res = RTStack.load(frmx + reladdr).value();
					RTStack.store(frmx + reladdr, value, comment);
					Util.IERR("SJEKK DETTE");
				}
				if(DEBUG) oaddr.segment().dump("RTAddress.store: ");
//				Util.IERR("");
			} else {
//				CallStackFrame callStackTop = RTStack.callStack_TOP();
				if(DEBUG) {
					RTStack.dumpRTStack("RTAddress.store: ");
					System.out.println("RTAddress.store: RTStack.callStackTop="+RTStack.callStack_TOP());
					System.out.println("RTAddress.store: rtStackIndex="+RTStack.callStack_TOP().rtStackIndex);
				}
//				int frmx = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
				int frmx = RTStack.frameIndex();
				RTStack.store(frmx + offset + idx, value, comment);
			}
		} else {
			DataSegment dseg = segment();
			int reladdr = offset + xRegIntValue() + idx;
			dseg.store(reladdr, value);
		}
	}

	public String toString() {
		String s = "";
		
//		s = (segID == null)? "xRELADR[" : "xSEGADR["+segID+':';
		if(segID == null) {
			s = (withRemoteBase)? "REMOTE[" : "RELADR[";
		} else {
			if(segID.equals("DSEG_RT") && offset == 0) {
				s = "CURINS=";
			}
			s = s + "SEGADR["+segID+':';
		}
		
		s = s + offset;
		if(xReg > 0) {
			s = s + "+" + RTRegister.edReg(xReg);
			Value value = RTRegister.getValue(xReg);
				s = s + '(' + value + ')';
		}
		s = s + "]";		
//		if(withRemoteBase) s = s + "WithRemoteBase";
		return s;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTAddress(AttributeInputStream inpt) throws IOException {
		segID = inpt.readString();
		offset = inpt.readShort();
		xReg = inpt.readReg();
		withRemoteBase = inpt.readBoolean();
	}

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeString(segID);
		oupt.writeShort(offset);
		oupt.writeReg(xReg);
		oupt.writeBoolean(withRemoteBase);
	}

	public static RTAddress read(AttributeInputStream inpt) throws IOException {
		return new RTAddress(inpt);
	}


}
