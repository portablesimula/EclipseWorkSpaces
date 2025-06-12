package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The size values on the top of the operand stack is stored at addr...
public class SVM_STORE extends SVM_Instruction {
	ObjectAddress rtAddr;
	int xReg;
	int size;
	
	private final boolean DEBUG = false;
	
//	public SVM_STORE(AddressItem itm, int size) {
//		this.opcode = SVM_Instruction.iSTORE;
//		this.addr = new RTAddress(itm);
//		this.size = size;
//	}
	
	public SVM_STORE(ObjectAddress rtAddr, int xReg, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = rtAddr;
		this.xReg = xReg;
		this.size = size;
//		System.out.println("NEW SVM_STORE: " + this);
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			Global.PSC.segment().dump("STORE.execute: ");
			RTStack.dumpRTStack("STORE.execute: ");
		}
		
		ObjectAddress addr = this.rtAddr;
		switch(addr.kind) {
			case ObjectAddress.SEGMNT_ADDR: doStore(addr, xReg); break;
			case ObjectAddress.REL_ADDR:    doStore(addr, xReg); break;
			case ObjectAddress.STACK_ADDR:  Util.IERR(""); doStore(addr, xReg); break;
			case ObjectAddress.REMOTE_ADDR:
				ObjectAddress oaddr = RTStack.popOADDR();
				addr = oaddr.addOffset(addr.getOfst());
				doStore(addr, xReg); break;
			case ObjectAddress.REFER_ADDR:
				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
				doStore(addr, 0); break;
			default: Util.IERR("");
		}
		
		if(DEBUG) {
//			addr.segment().dump("STORE.execute: ", addr.offset, addr.offset+size);
//			System.out.println("STORE: size="+size);
			addr.dumpArea("STORE.execute: ", size);
		}
		
		Global.PSC.addOfst(1);		
	}
	
	private void doStore(ObjectAddress addr, int xReg) {
		int n = RTStack.size()-1;
		int idx = size - 1;
		if(xReg > 0) idx = idx + RTRegister.getIntValue(xReg);
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			if(DEBUG) System.out.println("STORE: "+item+" ==> "+addr + "["+idx+"]");
			addr.store(idx--, item, "");
//			RTStack.printCallTrace("STORE.execute: ");
		}
		
	}
	
	public String toString() {
		String s = "";
		if(xReg != 0) s = s + "+R" + xReg + "(" + RTRegister.getValue(xReg) + ')';
		if(size > 1) s = ", size=" + size;
		return "STORE    " + rtAddr + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_STORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = ObjectAddress.read(inpt);
		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		rtAddr.writeBody(oupt);
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
