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

// relAddr = iReg + bReg + ofst
public class RelAddress extends DataAddress {
	
	public RelAddress(int ofst) {
		this.kind = DataAddress.REL_ADDR;
		this.type = Type.T_OADDR;
		this.ofst = ofst;
	}


//	@Override
//	public Value copy() {
//		return new RelAddress(ofst);
//	}

	@Override
	public RelAddress addOffset(int ofst) {
		return new RelAddress(this.ofst + ofst);
	}
	
	@Override
	public void store(int idx, Value value, String comment) {
//		IO.println("ReferAddress.store: idx="+idx+", value="+value+", xReg="+RTRegister.edRegVal(xReg));
		toRTAddress().store(idx, value, comment);
//		Segment.lookup("DSEG_ADHOC02").dump("ReferAddress.store: ");
//		Util.IERR("");
	}
	
	@Override
	public DataAddress toRTAddress() {
		if(! Global.duringEXEC()) Util.IERR("");
		if(hasIBREG()) Util.IERR(""+edIBReg());
		return this;
	}
	
	@Override
	public Value load(int idx) {
//		IO.println("ReferAddress.load: idx="+idx+", xReg="+xReg);
//		IO.println("ReferAddress.load: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
		Value val = toRTAddress().load(idx);
//		IO.println("ReferAddress.load: idx="+idx+", xReg="+RTRegister.edRegVal(xReg)+" ===> "+val);
		return val;
	}

	@Override
	public String toString() {
		String s = "REL_ADDR[" + edIBReg();
		if(ofst != 0) s = s + "+" + ofst;
		return s + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RelAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.REL_ADDR;
		this.type = Type.T_OADDR;
		ofst = inpt.readShort();
	    setIreg(inpt.readReg());
		setBreg(inpt.readReg());
//		IO.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("RTReferAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		this.type = Type.T_OADDR;
		oupt.writeShort(ofst);
		oupt.writeReg(getIreg());
		oupt.writeReg(getBreg());
//		IO.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new RelAddress(inpt);
	}

}
