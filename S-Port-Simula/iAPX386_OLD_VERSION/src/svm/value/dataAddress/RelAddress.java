package svm.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.value.Value;
import bec.virtualMachine.RTRegister;

// relAddr = xReg:gaddr + ofst
public class RelAddress extends DataAddress {
	int xReg;
	
	public RelAddress(int xReg, int ofst) {
		this.kind = DataAddress.REFER_ADDR;
		this.type = Type.T_OADDR;
		this.ofst = ofst;
		this.xReg = xReg;
	}

	@Override
	public RelAddress addOffset(int ofst) {
		return new RelAddress(xReg, this.ofst + ofst);
	}
	
	@Override
	public void store(int idx, Value value, String comment) {
//		IO.println("RelAddr.store: idx="+idx+", value="+value+", xReg="+RTRegister.edRegVal(xReg));
		getOADDR().store(idx, value, comment);
//		Segment.lookup("DSEG_ADHOC02").dump("RelAddr.store: ");
//		Util.IERR("");
	}
	
	private DataAddress getOADDR() {
		GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
		DataAddress oaddr = gaddr.base.addOffset(gaddr.ofst + ofst);
//		IO.println("RelAddr.getOADDR: oaddr="+oaddr);
		return oaddr;
	}
	
	@Override
	public Value load(int idx) {
//		IO.println("RelAddr.load: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
		Value val = getOADDR().load(idx);
//		IO.println("RelAddr.load: idx="+idx+", xReg="+RTRegister.edRegVal(xReg)+" ===> "+val);
		return val;
	}

	@Override
	public String toString() {
		Value regval = RTRegister.getValue(xReg);
		return "REL_ADDR[" + RTRegister.edReg(xReg) + '=' + ((regval == null)? "" : (regval.getClass().getSimpleName() + ':' + regval) + "[" + ofst + ']');
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RelAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.REFER_ADDR;
		this.type = Type.T_OADDR;
		ofst = inpt.readShort();
//		IO.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("RTReferAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		this.type = Type.T_OADDR;
		oupt.writeShort(ofst);
//		IO.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new RelAddress(inpt);
	}

}
