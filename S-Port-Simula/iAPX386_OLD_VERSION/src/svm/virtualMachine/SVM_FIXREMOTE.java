package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;
import svm.value.dataAddress.GeneralAddress;
import svm.value.dataAddress.DataAddress;
import svm.value.dataAddress.RemoteAddress;
import svm.value.dataAddress.SegmentAddress;

// FIXUP RemoteAddress
public class SVM_FIXREMOTE extends SVM_Instruction {
	RemoteAddress remaddr;

	public SVM_FIXREMOTE(RemoteAddress remaddr) {
		this.opcode = SVM_Instruction.iFIXREM;
		this.remaddr=remaddr;
	}
	
//	public Value load(int idx) {
//		GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//		Value val = gaddr.load(idx);
//		return val;
//	}
//
//	public void store(int idx, Value value, String comment) {
//		GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//		gaddr.store(idx, value, comment);
//	}

	@Override
	public void execute() {
		SegmentAddress objadr = (SegmentAddress) RTStack.pop();
		System.out.println("SVM_REMOTE: objadr="+objadr);
		remaddr.fixup(objadr);
		
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "REMOTE   " + remaddr;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_FIXREMOTE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iFIXREM;
//		this.xReg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
//		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_FIXREMOTE(inpt);
	}

}
