package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

public class RTAddress_Kopi extends Value {
	String segID; // null: RelAddr
	public int offset;
	public int xReg; // 0: no index register
	public int kind;

	public RTAddress_Kopi(AddressItem itm) {
		if(itm.objadr != null) {
			this.kind   = itm.objadr.kind;
			this.segID  = itm.objadr.segID;
			this.offset = itm.objadr.getOfst() + itm.offset;
		}
		this.xReg = itm.xReg;
//		System.out.println("NEW RTAddress: " + itm + " ==> " + this);
		if(kind == 0) Util.IERR("");
	}

	public RTAddress_Kopi(ObjectAddress objadr, int gOfst) {
		this.kind = objadr.kind;
		this.segID =  objadr.segID;
		this.offset = objadr.getOfst() + gOfst;	
		if(kind == 0) Util.IERR("");
	}

	public RTAddress_Kopi(RTAddress_Kopi rtadr, int ofst) {
		this.segID  = rtadr.segID;
		this.offset = rtadr.offset + ofst;	
		this.xReg   = rtadr.xReg;
		if(kind == 0) Util.IERR("");
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
	
	public void dumpArea(String title, int lng) {
		if(this.segID != null) {
			segment().dump("\nRTAddress.dumpArea:", offset, offset+lng);
		} else {
			System.out.println("\nRTAddress.dumpArea: BEGIN " + title + " +++++++++++++++++++++++++++++++++++++");
			for(int i=0;i<lng;i++) {
				RTAddress_Kopi rtadr = new RTAddress_Kopi(this, i);
				System.out.println(""+rtadr+": " + load(offset+i));
			}
			System.out.println("RTAddress.dumpArea: ENDOF " + title + " +++++++++++++++++++++++++++++++++++++");
		}
//		Util.IERR("");
	}
	
	public Value load(int idx) {
		
		boolean DEBUG = false;

		if(segID == null) {
			// load rel-addr  callStackTop + ofst
			Value xRegValue = xRegValue();
			if(xRegValue instanceof GeneralAddress gaddr) {
				if(DEBUG) System.out.println("RTAddress.load: CASE 1:");
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
					res = RTStack.load(frmx + reladdr);
//					Util.IERR("");
				}
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+res);
				return res;
			} else if(xRegValue instanceof ObjectAddress oaddr) {
				if(DEBUG) {
					System.out.println("RTAddress.load: CASE 2:");
					oaddr.segment().dump("RTAddress.load: ");
					System.out.println("RTAddress.load: xRegValue="+xRegValue);
				}
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + idx;
				Value res = dseg.load(reladdr);
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+res);
				return res;
			} else {
				if(DEBUG) System.out.println("RTAddress.load: CASE 3:");
//				CallStackFrame top = RTStack.callStack_TOP();
//				int frmx = (top == null)? 0 : top.rtStackIndex;
				
				int frmx = RTStack.frameIndex();
				int iReg = xRegIntValue();
				int from = frmx + offset + iReg + idx;					
				
//				RTStack.dumpRTStack("RTAddress.load: ");
//				System.out.println("RTAddress.load: "+this+", idx="+idx);
//				System.out.println("RTAddress.load: withRemoteBase="+this.withRemoteBase);
//				System.out.println("RTAddress.load: frmx="+frmx+",offset="+offset+", iReg="+iReg+", idx="+idx);
//				System.out.println("RTAddress.load: FROM: "+from);
				
				Value val = RTStack.load(from);				
				if(DEBUG) System.out.println("RTAddress.load("+from+") ===> "+val);
				
//				if(iReg != 0) Util.IERR("");
				
				if(val == null) {
////					Global.DSEG.dump("RTAddress.load: ");
////					Segment.lookup("POOL_1").dump("RTAddress.load: ", 0, 25);
//					
//					System.out.println("RTAddress.load: "+this+", idx="+idx);
//					System.out.println("RTAddress.load: withRemoteBase="+this.withRemoteBase);
//					
////					Segment.lookup("PSEG_FIL_FOPEN:BODY").dump("RTAddress.load: ");
////					Util.IERR("SJEKK DETTE");
//					System.out.println("RTAddress.load: SJEKK DETTE !!!");
					return null;
				}
				return val;				
			}
		} else {
			if(DEBUG) System.out.println("RTAddress.load: CASE 4:");
			DataSegment dseg = segment();
			int reladdr = offset + xRegIntValue()+ idx;				
			return dseg.load(reladdr);
		}
	}
	
	public ObjectAddress reladdr2ObjAddr() {
		Value itm = RTStack.pop();
		System.out.println("RTAddress.reladdr2ObjAddr: itm="+itm);
		
		ObjectAddress objAddr = (ObjectAddress) itm;
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

		objAddr = objAddr.addOffset(xRegIntValue());
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

//		Util.IERR("");
		return objAddr;
	}
	
	public void store(int idx, Value value, String comment) {
		
		boolean DEBUG = false;

		if(DEBUG) System.out.println("RTAddress.store-0: " + value + " ==> " + this + ", idx=" + idx);
		if(segID == null) {
			Value xRegValue = xRegValue();
			if(DEBUG) {
				if(xRegValue == null)
					 System.out.println("RTAddress.store-A: xRegValue=null");
				else System.out.println("RTAddress.store-A: xRegValue=" + xRegValue.getClass().getSimpleName() + " " + xRegValue);
			}
			if(xRegValue == null) {
				int frmx = RTStack.frameIndex();
				if(DEBUG) {
//					RTStack.dumpRTStack("RTAddress.store: ");
					System.out.println("RTAddress.store-B: RTStack.callStackTop="+RTStack.callStack_TOP());
					System.out.println("RTAddress.store-B: rtStackIndex="+RTStack.callStack_TOP().rtStackIndex);
					System.out.println("RTAddress.store-B: frmx="+frmx+", offset="+offset+", idx="+idx);
				}
				RTStack.store(frmx + offset + idx, value, comment);
				
			} else if(xRegValue instanceof IntegerValue ival) {
				if(DEBUG) {
//					RTStack.dumpRTStack("RTAddress.store: ");
					System.out.println("RTAddress.store-C: RTStack.callStackTop="+RTStack.callStack_TOP());
					System.out.println("RTAddress.store-C: rtStackIndex="+RTStack.callStack_TOP().rtStackIndex);
				}
				int frmx = RTStack.frameIndex();
				RTStack.store(frmx + offset + idx + ival.value, value, comment);
				
			} else if(xRegValue instanceof ObjectAddress oaddr) {
				if(DEBUG) {
					oaddr.segment().dump("RTAddress.store-2: ");
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
					if(dseg != null) dseg.dump("RTAddress.store-3: ");
					System.out.println("RTAddress.store: xRegValue="+xRegValue+", reladdr="+reladdr);
				}
				if(dseg != null) {
					dseg.store(reladdr, value);
				} else {
					int frmx = RTStack.frameIndex();
					System.out.println("RTAddress.store: frmx="+frmx);
					RTStack.dumpRTStack("RTAddress.store: ");
					RTStack.store(frmx + reladdr, value, comment);
					Util.IERR("SJEKK DETTE");
				}
				if(DEBUG) oaddr.segment().dump("RTAddress.store: ");
//				Util.IERR("");
			} else {
				Util.IERR("");
			}
		} else {
			DataSegment dseg = segment();
			int reladdr = offset + xRegIntValue() + idx;
			dseg.store(reladdr, value);
		}
	}

	public String toString() {
		String s = "";
		
////		s = (segID == null)? "xRELADR[" : "xSEGADR["+segID+':';
//		if(segID == null) {
//			s = (withRemoteBase)? "REMOTE[" : "RELADR[";
//		} else {
//			if(segID.equals("DSEG_RT") && offset == 0) {
//				s = "CURINS=";
//			}
//			s = s + "SEGADR["+segID+':';
//		}
//		
//		s = s + offset;
//		if(xReg > 0) {
//			s = s + "+" + RTRegister.edReg(xReg);
//			Value value = RTRegister.getValue(xReg);
//				s = s + '(' + value + ')';
//		}
//		s = s + "]";		
////		if(withRemoteBase) s = s + "WithRemoteBase";
//		return s;
		
		
		if(xReg > 0) {
			s = s + "+" + RTRegister.edReg(xReg);
			Value value = RTRegister.getValue(xReg);
				s = s + '(' + value + ')';
		}
		switch(kind) {
			case ObjectAddress.SEGMNT_ADDR: return segID + '[' + offset + s + ']';
			case ObjectAddress.REMOTE_ADDR: return "REMOTE_ADDR[RTStackTop+" + offset + s + ']';
			case ObjectAddress.REFER_ADDR:  return "REFER_ADDR[" + offset + s +']';
			case ObjectAddress.REL_ADDR:    return "REL_ADR[callStackTop+" + offset + s + ']';
			case ObjectAddress.STACK_ADDR:  return "STACK_ADR[RTStack(" + offset + s + ")]";
			default: return "UNKNOWN:"+kind;
		}

	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTAddress_Kopi(AttributeInputStream inpt) throws IOException {
		segID = inpt.readString();
		offset = inpt.readShort();
		xReg = inpt.readReg();
	}

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeString(segID);
		oupt.writeShort(offset);
		oupt.writeReg(xReg);
	}

	public static RTAddress_Kopi read(AttributeInputStream inpt) throws IOException {
		return new RTAddress_Kopi(inpt);
	}


}
