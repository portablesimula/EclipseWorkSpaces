package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.Value;
import svm.value.dataAddress.DataAddress;

/**
 * Operation STORE rtAddr xReg size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ..., value1, value2, ... , value'size
 *
 * Move 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr+xreg'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddre+xReg[0]     value1
 * 		rtAddre+xReg[1]     value2
 * 		...
 * 		rtAddre+xReg[size-1]  value'size
 * 
 * The Runtime Stack remains unchanged.
 * 
 */
public class SVM_STORE extends SVM_Instruction {
	DataAddress rtAddr;
	int xReg;
	int size;
	
	private final boolean DEBUG = false;
	
	public SVM_STORE(DataAddress rtAddress, int xReg, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = rtAddress;
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
		
		DataAddress addr = this.rtAddr;
//		switch(addr.kind) {
//			case DataAddress.SEGMNT_ADDR: doStore(addr, xReg); break;
//			case DataAddress.REL_ADDR:    doStore(addr, xReg); break;
//			case DataAddress.STACK_ADDR:  Util.IERR(""); doStore(addr, xReg); break;
//			case DataAddress.REMOTE_ADDR:
//				if(Global.TESTING_REMOTE) {
//					System.out.println("SVM_STORE.execute: "+addr+", xReg="+addr.xReg);
//					doStore(addr, xReg);
////					Util.IERR("NOT IMPL");
//				} else {
//					DataAddress oaddr = RTStack.popOADDR();
//					addr = oaddr.addOffset(addr.getOfst());
//					doStore(addr, xReg);
//				}
//				break;
//			case DataAddress.REFER_ADDR:
//				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
//				doStore(addr, 0); break;
//			default: Util.IERR("");
//		}
//		
//		if(DEBUG) {
////			addr.segment().dump("STORE.execute: ", addr.offset, addr.offset+size);
////			System.out.println("STORE: size="+size);
//			addr.dumpArea("STORE.execute: ", size);
//		}
		
		addr.doStore(xReg, size);
		
		Global.PSC.addOfst(1);		
	}
	
//	private void doStore(DataAddress addr, int xReg) {
//		int n = RTStack.size()-1;
//		int idx = size - 1;
//		if(xReg > 0) idx = idx + RTRegister.getIntValue(xReg);
//		for(int i=0;i<size;i++) {
//			Value item = RTStack.load(n-i);
//			if(DEBUG) System.out.println("STORE: "+item+" ==> "+addr + "["+idx+"]");
//			addr.store(idx--, item, "");
////			RTStack.printCallTrace("STORE.execute: ");
//		}
//		
//	}
	
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
		this.rtAddr = (DataAddress) Value.read(inpt);
		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		rtAddr.write(oupt);
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
