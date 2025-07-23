package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.ObjectAddress;
import bec.value.Value;

// The size values at addr... is loaded onto the operand stack.
public class SVM_LOAD extends SVM_Instruction {
	ObjectAddress rtAddr;
	int xReg;
	int size;

	private final boolean DEBUG = false;

	public SVM_LOAD(ObjectAddress rtAddr, int xReg, int size) {
		this.opcode = SVM_Instruction.iLOAD;
		this.rtAddr = rtAddr;
		this.xReg = xReg;
		this.size = size;
	}
	
	public SVM_LOAD(Variable var) {
		this.opcode = SVM_Instruction.iLOAD;
//		this.addr = new RTAddress(var.address, 0);
		this.rtAddr = var.address;
		this.size = var.repCount;
	}
	
	@Override
	public void execute() {
		ObjectAddress addr = this.rtAddr;
		switch(addr.kind) {
			case ObjectAddress.SEGMNT_ADDR: doLoad(addr, xReg); break;
			case ObjectAddress.REL_ADDR:    doLoad(addr, xReg); break;
			case ObjectAddress.STACK_ADDR:  Util.IERR(""); doLoad(addr, xReg); break;
			case ObjectAddress.REMOTE_ADDR:
				ObjectAddress oaddr = RTStack.popOADDR();
				addr = oaddr.addOffset(addr.getOfst());
				doLoad(addr, xReg); break;
				
			case ObjectAddress.REFER_ADDR:
				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
//				System.out.println("LOAD.execute: REFER_ADDR: "+rtAddr);
//				System.out.println("LOAD.execute: REFER_ADDR: "+addr);
				doLoad(addr, 0); break;
	
			default: Util.IERR("");
		}

		if(DEBUG) {
//			System.out.println("LOAD.execute: "+addr);
//			addr.segment().dump("LOAD.execute: ", addr.offset, addr.offset+size);
			addr.dumpArea("LOAD.execute: ", size);
		}
		BecGlobal.PSC.addOfst(1);
	}
	
	private void doLoad(ObjectAddress addr, int xReg) {
		int idx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
		for(int i=0;i<size;i++) {
			Value value = addr.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
		}		
	}
	
	@Override
	public String toString() {
		String s = "LOAD     " + rtAddr;
		if(xReg != 0) s = s + "+R" + xReg + "(" + RTRegister.getValue(xReg) + ')';
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOAD(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOAD;
		this.rtAddr = ObjectAddress.read(inpt);
		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		rtAddr.writeBody(oupt);
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOAD(inpt);
	}

}
