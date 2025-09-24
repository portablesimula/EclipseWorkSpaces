package svm.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.RTStack;

public class StackAddress extends DataAddress {
	
	public StackAddress(int ofst) {
		this.kind = DataAddress.STACK_ADDR;
		this.type = Type.T_OADDR;
		this.ofst = ofst;
	}

	@Override
	public StackAddress addOffset(int ofst) {
		return new StackAddress(this.ofst + ofst);
	}
	
	@Override
	public void store(int idx, Value value, String comment) {
//		System.out.println("DataAddress.store: "+value+"  "+this);
//		RTStack.dumpRTStack("DataAddress.store: "+this);
		RTStack.store(ofst + idx, value, comment);
//		RTStack.dumpRTStack("DataAddress.store: "+this);
		Util.IERR("");
	}
	
	@Override
	public Value load(int idx) {
		Value value = RTStack.load(ofst + idx);
//		System.out.println("DataAddress.load: value="+value);
		Util.IERR("");
		return value;
	}

	@Override
	public String toString() {
		return "STACK_ADR[RTStack(" + ofst + ")]";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private StackAddress(AttributeInputStream inpt) throws IOException {
		this.kind = DataAddress.STACK_ADDR;
		this.type = Type.T_OADDR;
		ofst = inpt.readShort();
//		System.out.println("DataAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("RTStackAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeShort(ofst);
//		System.out.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(ofst);
//	}
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		return new StackAddress(inpt);
	}

}
