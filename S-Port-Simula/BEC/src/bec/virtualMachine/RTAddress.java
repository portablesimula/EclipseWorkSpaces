package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

public class RTAddress extends Value {
//	public ObjectAddress objadr;
	String segID;
	public int offset;
//	public boolean isRemoteBase; // objaddr is base address before dot
//	public boolean isRefered;
//	private AddressItem.ObjState objState;
	private int xReg; // 0: no index register
	
	private static final boolean DEBUG = true;

	public RTAddress(AddressItem itm) {
		if(itm.objadr != null) {
			this.segID =  itm.objadr.segID;
//			this.objadr = itm.objadr;
			this.offset = itm.objadr.getOfst() + itm.offset;			
		} else {
			this.segID = "Frame";			
		}
//		this.isRemoteBase  = itm.isRemoteBase;
//		this.isRefered = itm.isRefered;
//		this.objState  = itm.objState;
//		this.objState  = itm.atrState;
		this.xReg = itm.xReg;
	}

	public RTAddress(ObjectAddress objadr, int gOfst) {
		this.segID =  objadr.segID;
		this.offset = objadr.getOfst() + gOfst;			
//		this.xReg = itm.xReg;
	}

//	public RTAddress(ObjectAddress objadr) {
//		this.objadr = objadr;
//	}

//	public ObjectAddress toObjectAddress() {
//		Thread.dumpStack();
//		if(DEBUG) System.out.println("RTAddress.toObjectAddress: "+objadr+", offset="+offset+", xReg="+xReg);
//		ObjectAddress res =  this.objadr.addOffset(offset);
//		if(xReg > 0) {
//			if(DEBUG) System.out.println("RTAddress.toObjectAddress: xReg="+RTRegister.edReg(xReg)+"  "+RTRegister.getIndex(xReg));
//			res = res.addOffset(RTRegister.getIndex(xReg));
//		}
//		if(DEBUG) System.out.println("RTAddress.toObjectAddress: "+res);
//		return res;
//	}

//	public ObjectAddress OLD_toObjectAddress() {
////		if(objState == AddressItem.ObjState.Calculated) {
////		if(objState == AddressItem.ObjState.Remote) {
//		if(isRemoteBase) {
////			ObjectAddress temp = RTStack.popGADDR();
//			ObjectAddress temp = RTStack.popOADDR();
//			ObjectAddress res = temp.addOffset(offset);
//			if(DEBUG) System.out.println("RTAddress.toObjectAddress: isRemoteBase: "+res);
//			return res;
//		} else if(isRefered) {
////			ObjectAddress temp = RTStack.popGADDR();
//			ObjectAddress temp = RTStack.popOADDR();
//			ObjectAddress res = temp.addOffset(offset);
//			if(DEBUG) System.out.println("RTAddress.toObjectAddress: isRefered: "+res);
//			return res;
//		} else {
//			ObjectAddress res =  this.objadr.addOffset(offset);
//			if(DEBUG) System.out.println("RTAddress.toObjectAddress: "+res);
//			if(xReg > 0) {
//				if(DEBUG) System.out.println("RTAddress.toObjectAddress: xReg="+RTRegister.edReg(xReg)+"  "+RTRegister.getIndex(xReg));
//				res = res.addOffset(RTRegister.getIndex(xReg));
//			}
//			return res;
//		}
//	}
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
	
	private int xRegValue() {
		if(xReg == 0) return 0;
		return RTRegister.getIndex(xReg);
	}
	
	private static final boolean TESTING = true;
	public Value load(int idx) {
//		return toObjectAddress().load();
		if(segID == null) {
			// load rel-addr  curFrame + ofst
			ObjectAddress objAddr = null;
			if(TESTING) {
				objAddr = reladdr2ObjAddr();
			} else {
				int frmx = (RTStack.curFrame == null)? 0 : RTStack.curFrame.rtStackIndex;
				System.out.println("RTAddress.load: curFrame="+frmx);
				
				int reladdr = offset + idx;
	
				RTStackItem itm = RTStack.load(frmx + reladdr);
				System.out.println("RTAddress.load: itm="+itm);
				
				objAddr = (ObjectAddress) itm.value();
				System.out.println("RTAddress.load: objAddr="+objAddr);
	
				objAddr = objAddr.addOffset(xRegValue());
				System.out.println("RTAddress.load: objAddr="+objAddr);
			}
			
			Value val = objAddr.load();
			System.out.println("RTAddress.load: val="+val);
				
//			Util.IERR("");
//			return RTStack.load(frmx + reladdr).value();
			return val;
		} else {
			DataSegment dseg = segment();
//			dseg.dump("RTAddress.load: ");
			int reladdr = offset + xRegValue() + idx;
			return dseg.load(reladdr);
//			Util.IERR("");
		}
	}
	
	public ObjectAddress reladdr2ObjAddr() {
		RTStackItem itm = RTStack.pop();
		System.out.println("RTAddress.reladdr2ObjAddr: itm="+itm);
		
		ObjectAddress objAddr = (ObjectAddress) itm.value();
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

		objAddr = objAddr.addOffset(xRegValue());
		System.out.println("RTAddress.reladdr2ObjAddr: objAddr="+objAddr);

//		Util.IERR("");
		return objAddr;
	}
	
	public void store(int idx, Value value, String comment) {
//		toObjectAddress().store(val, comment);
		if(segID == null) {
			Util.IERR("SKAL IKKE FOREKOMME !!!");
		} else {
			DataSegment dseg = segment();
			int reladdr = offset + xRegValue() + idx;
			dseg.store(reladdr, value);
//			dseg.dump("RTAddress.store: ");
//			Util.IERR("");
		}
	}

	public String toString() {
		String s = (segID == null)? "RELADR[" : "SEGADR["+segID+':';
		s = s + offset;
		if(xReg > 0) {
			s += "+" + RTRegister.edReg(xReg) + '(' + RTRegister.getIndex(xReg) + ')';
		}
		return s + "]";			
	}

//	public String toString() {
//		if(objadr != null) {
//			String s = "SEGADR["+objadr.segID+':' + objadr.getOfst() + "+" + offset;
//			if(xReg > 0) {
//				s += "+" + RTRegister.edReg(xReg) + '(' + RTRegister.getIndex(xReg) + ')';
//			}
//			return s + "]";			
//		}
//		int ofst = (objadr==null)? 0 : objadr.getOfst();
//		String s = "RELADR[Frame:" + ofst + "+" + offset;
//		return s + "]";
//	}


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
