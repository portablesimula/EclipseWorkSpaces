package bec.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.RTStack;

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
 *     (bReg) ---- > RTSegmentAddress -------->.=====================.
 *                                             |-----.               |
 *                                             |     |  iReg +       |
 *                                             |     | attribute     |
 *                                             |     |  OFFSET       |
 *                                             |     V               |
 *     RTRemoteAddress(bReg, iReg+ofst) -------|----->.======.       |
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

	public RemoteAddress(int attrOfst) {
		this.kind = DataAddress.REMOTE_ADDR;
		this.type = Type.T_OADDR;
		this.ofst = attrOfst;
		IO.println("NEW RemoteAddress: " + this);
	}

	@Override 
	public RemoteAddress addOffset(int ofst) {
		return new RemoteAddress(this.ofst + ofst);
	}

	@Override
	/// Evaluate offset = offset + values in Ireg and Breg
	/// 
	/// objadr := Breg + Ireg + ofst
	public DataAddress toRTAddress() {
		IO.println("RemoteAddress.toRTAddress: bReg=" + Reg.edReg(getBreg()));
		IO.println("RemoteAddress.toRTAddress: iReg=" + Reg.edReg(getIreg()));
		IO.println("RemoteAddress.toRTAddress: " + edIBReg() + "  ofst=" + ofst);
		if(! Global.duringEXEC()) Util.IERR("");
		DataAddress oaddr = null;
//		GeneralAddress gaddr = (GeneralAddress) RTRegister.getAddrValue(xReg);
//		DataAddress oaddr = gaddr.base.addOffset(gaddr.ofst + ofst);
//		IO.println("ReferAddress.toRTAddress: oaddr="+oaddr);
		Util.IERR("");
		return oaddr;
	}

	@Override
	public DataAddress toOADDR() {
		if(! Global.duringEXEC()) Util.IERR("");
		DataAddress oaddr = (DataAddress) RTStack.pop();
		oaddr = oaddr.addOffset(ofst);
		IO.println("RemoteAddress.toOADDR: oaddr=" + oaddr);
		return oaddr;
	}

	@Override
	public void store(int idx, Value value, String comment) {
		IO.println("RemoteAddress.store: "+value+"  "+this+", idx="+idx+", attrOfst="+ofst+", IBReg:"+edIBReg());
		DataAddress oaddr = (DataAddress) RTStack.pop();
		Util.IERR("");
//		DataAddress oaddr = RTRegister.getAddrValue(xReg);
		oaddr.store(ofst + idx, value, comment);
	}
	
	
	@Override
	public Value load(int idx) {
//		IO.println("RTRemoteAddress.load: "+this+", idx="+idx);
		DataAddress oaddr = RTRegister.getAddrValue(xReg);
		return oaddr.load(ofst + idx);
	}
	
	public SegmentAddress toSegAddr() {
		if(! Global.duringEXEC()) Util.IERR("");
		SegmentAddress segadr = (SegmentAddress) RTRegister.getAddrValue(this.xReg);
		return segadr.addOffset(ofst);
	}
	
	@Override
	public String toString() {
		String s = "REMOTE_ADDR[" + edIBReg();
		if(ofst != 0) s = s + "+" + ofst;
		return s + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RemoteAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.REMOTE_ADDR;
		this.type = Type.T_OADDR;
		xReg = inpt.readReg();
		ofst = inpt.readShort();
//		IO.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("RTRemoteAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeReg(xReg);
		oupt.writeShort(ofst);
//		IO.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeReg(xReg);
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new RemoteAddress(inpt);
	}

}
