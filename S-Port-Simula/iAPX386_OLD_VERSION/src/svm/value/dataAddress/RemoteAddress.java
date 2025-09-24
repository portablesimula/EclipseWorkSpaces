package svm.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;

/**
 * RTRemoteAddress:
 * 
 * 
 *     DataSegment --------------------------->.=====================.
 *                                             |                     |
 *                                             .                     .
 *                                             .                     .
 *                                             .                     .
 *                                             |                     |
 *     (xReg) ---- > RTSegmentAddress -------->.=====================.
 *                                             |-----.               |
 *                                             |     |               |
 *                                             |     | attribute     |
 *                                             |     |  OFFSET       |
 *                                             |     V               |
 *     RTRemoteAddress(xReg, ofst) ------------|----->.======.       |
 *                                             |      | attr |       |
 *                                             |      '======'       |
 *                                             |                     |
 *                                             '====================='
 *                                             |                     |
 *                                             .                     .
 *                                             .                     .
 *                                             .                     .
 *                                             |                     |
 *                                             '====================='
 */
public class RemoteAddress extends DataAddress {
	// IF TESTING_REMOTE:
	// remAddr = RTStack'tos + ofst
	int xReg;
	SegmentAddress oaddr; // BARE HVIS Global.TESTING_REMOTE

	public RemoteAddress(int xReg, int attrOfst) {
		this.kind = DataAddress.REMOTE_ADDR;
		this.type = Type.T_OADDR;
		this.xReg = xReg;
		this.ofst = attrOfst;
	}
	
	public void fixup(SegmentAddress oaddr) {  // BARE HVIS Global.TESTING_REMOTE
		this.oaddr = oaddr;
	}

	@Override 
	public RemoteAddress addOffset(int ofst) {
		if(Global.TESTING_REMOTE) {
			RemoteAddress res = new RemoteAddress(this.xReg, this.ofst + ofst);
			res.oaddr = this.oaddr;
			return res;
		} else {
			return new RemoteAddress(this.xReg, this.ofst + ofst);
		}
	}

	@Override
	public void store(int idx, Value value, String comment) {
		System.out.println("RTRemoteAddress.store: "+value+"  "+this+", idx="+idx+", attrOfst="+ofst+", xReg:"+RTRegister.edReg(xReg)+", oaddr="+RTRegister.getValue(xReg));
		if(Global.TESTING_REMOTE) {
//			DataAddress oaddr = (DataAddress) RTStack.pop();
			oaddr.store(ofst + idx, value, comment);			
		} else {
			DataAddress oaddr = (DataAddress) RTRegister.getValue(xReg);
			oaddr.store(ofst + idx, value, comment);
		}
	}
	
	
	@Override
	public Value load(int idx) {
//		System.out.println("RTRemoteAddress.load: "+this+", idx="+idx);
		if(Global.TESTING_REMOTE) {
			DataAddress oaddr = (DataAddress) RTStack.pop();
			return oaddr.load(ofst + idx);
		} else {
			DataAddress oaddr = (DataAddress) RTRegister.getValue(xReg);
			return oaddr.load(ofst + idx);
		}
	}
	
	@Override
	public String toString() {
//		String s = "REMOTE_ADDR[" + RTRegister.edRegVal(xReg);
		if(Global.TESTING_REMOTE) {
			String s = "REMOTE_ADDR[TOS";
			if(ofst != 0) s = s + "+" + ofst;
			return s + ']';
			
		} else {
			Value regval = RTRegister.getValue(xReg);
			String regvalClass = (regval == null)? "" : (regval.getClass().getSimpleName() + ':');
			String s = "REMOTE_ADDR[" + RTRegister.edReg(xReg) + '=' + regvalClass + regval;
			if(ofst != 0) s = s + "+" + ofst;
			return s + ']';
		}
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RemoteAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.REMOTE_ADDR;
		this.type = Type.T_OADDR;
		xReg = inpt.readReg();
		ofst = inpt.readShort();
//		System.out.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("RTRemoteAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeReg(xReg);
		oupt.writeShort(ofst);
//		System.out.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeReg(xReg);
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new RemoteAddress(inpt);
	}

}
