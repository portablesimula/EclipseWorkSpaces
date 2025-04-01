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
	private int xReg; // 0: no index register
	
	private static final boolean DEBUG = false;

	public RTAddress(AddressItem itm) {
		if(itm.objadr != null) {
			this.segID =  itm.objadr.segID;
			this.offset = itm.objadr.getOfst() + itm.offset;			
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
			if(xRegValue instanceof ObjectAddress oaddr) {
				if(DEBUG) {
					oaddr.segment().dump("RTAddress.load: ");
					System.out.println("RTAddress.load: xRegValue="+xRegValue);
				}
				DataSegment dseg = oaddr.segment();
				int reladdr = offset + oaddr.getOfst() + idx;
				Value res = dseg.load(reladdr);
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+res);
//				Util.IERR("");
				return res;
			} else {
				int frmx = RTStack.callStack_TOP().rtStackIndex;
				int iReg = xRegIntValue();
				RTStackItem val = RTStack.load(frmx + offset + (iReg * incr) + idx);				
				if(DEBUG) System.out.println("RTAddress.load("+idx+") ===> "+val);
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
		if(segID == null) {
			Value xRegValue = xRegValue();
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
				Util.IERR("");
			} else {
				CallStackFrame callStackTop = RTStack.callStack_TOP();
				if(DEBUG) {
					RTStack.dumpRTStack("RTAddress.store: ");
					System.out.println("RTAddress.store: RTStack.callStackTop="+callStackTop);
					System.out.println("RTAddress.store: rtStackIndex="+callStackTop.rtStackIndex);
				}
				int frmx = callStackTop.rtStackIndex;
				RTStack.store(frmx + offset + idx, value, comment);
			}
		} else {
			DataSegment dseg = segment();
			int reladdr = offset + xRegIntValue() + idx;
			dseg.store(reladdr, value);
		}
	}

	public String toString() {
		String s = (segID == null)? "RELADR[" : "SEGADR["+segID+':';
		s = s + offset;
		if(xReg > 0) {
			s = s + "+" + RTRegister.edReg(xReg);
			Value value = RTRegister.getValue(xReg);
//			System.out.println("RTAddress.toString: xReg="+xReg+", value="+value);
//			if(value >= 0) {
				s = s + '(' + value + ')';
//			}
		}
		return s + "]";			
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTAddress(AttributeInputStream inpt) throws IOException {
//		Util.IERR("ER DETTE MULIG ?");
		
		segID = inpt.readString();
		offset = inpt.readShort();
		xReg = inpt.readReg();
		
//		boolean present = inpt.readBoolean();
//		if(present) this.objadr = (ObjectAddress) Value.read(inpt);
//		this.offset = inpt.readShort();
//		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

//	String segID;
//	public int offset;
//	private int xReg; // 0: no index register

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
//		Util.IERR("ER DETTE MULIG ?");
		
		oupt.writeString(segID);
		oupt.writeShort(offset);
		oupt.writeReg(xReg);
		
////		objadr.write(oupt);
//		if(objadr != null) {
//			oupt.writeBoolean(true);
//			objadr.write(oupt);
//		} else oupt.writeBoolean(false);
//		oupt.writeShort(offset);
	}

	public static RTAddress read(AttributeInputStream inpt) throws IOException {
		return new RTAddress(inpt);
	}


}
