package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.util.Global;
import svm.value.Value;
import svm.value.dataAddress.DataAddress;

/**
 * Operation LOAD rtAddr xReg size
 * 
 * Runtime Stack
 *    ... →
 *    ..., value1, value2, ... , value'size
 *
 * Push 'size' values from a data segment starting by pushing the value at 'rtAddr+xreg'.
 * After the operation the Runtime Stack looks like:
 * 
 * 		value1  =  rtAddre+xReg[0] 
 * 		value2  =  rtAddre+xReg[1]
 * 		...
 * 		value'size  =  rtAddre+xReg[size-1]
 */
public class SVM_LOAD extends SVM_Instruction {
	DataAddress rtAddr;
	int xReg;
	int size;

//	private final boolean DEBUG = false;

	public SVM_LOAD(DataAddress rtAddress, int xReg, int size) {
		this.opcode = SVM_Instruction.iLOAD;
		this.rtAddr = rtAddress;
		this.xReg = xReg;
		this.size = size;
	}
	
	public SVM_LOAD(Variable var) {
		this.opcode = SVM_Instruction.iLOAD;
//		this.addr = new RTAddress(var.address, 0);
//		this.rtAddr = var.address.toRTAddress();
		this.rtAddr = var.address;
		this.size = var.repCount;
	}
	
	@Override
	public void execute() {
		DataAddress addr = this.rtAddr;
//		switch(addr.kind) {
//			case DataAddress.SEGMNT_ADDR: doLoad(addr, xReg); break;
//			case DataAddress.REL_ADDR:    doLoad(addr, xReg); break;
//			case DataAddress.STACK_ADDR:  Util.IERR(""); doLoad(addr, xReg); break;
//			case DataAddress.REMOTE_ADDR:
//				if(Global.TESTING_REMOTE) {
//					System.out.println("SVM_LOAD.execute: "+addr+", xReg="+addr.xReg);
//					doLoad(addr, xReg);
////					Util.IERR("NOT IMPL");
//				} else {
//					DataAddress oaddr = RTStack.popOADDR();
//					addr = oaddr.addOffset(addr.getOfst());
//					doLoad(addr, xReg);
//				}
//				break;
//				
//			case DataAddress.REFER_ADDR:
//				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
//				System.out.println("SVM_LOAD.execute: REFER_ADDR: rtAddr="+rtAddr);
//				System.out.println("SVM_LOAD.execute: REFER_ADDR: gaddr="+gaddr);
//				System.out.println("SVM_LOAD.execute: REFER_ADDR: base="+gaddr.base.getClass().getSimpleName()+"  "+gaddr.base);
//				System.out.println("SVM_LOAD.execute: REFER_ADDR: addr="+addr);
//				doLoad(addr, 0); break;
//	
//			default: Util.IERR("");
//		}
//
//		if(DEBUG) {
////			System.out.println("LOAD.execute: "+addr);
////			addr.segment().dump("LOAD.execute: ", addr.offset, addr.offset+size);
//			addr.dumpArea("LOAD.execute: ", size);
//		}
		
		
//		System.out.println("LOAD.execute: "+addr.getClass().getSimpleName()+"  "+addr);
		
		addr.doLoad(xReg, size);
		
		Global.PSC.addOfst(1);
	}
	
//	private void doLoad(DataAddress addr, int xReg) {
//		int idx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
//		for(int i=0;i<size;i++) {
//			Value value = addr.load(idx + i);
//			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
//		}		
//	}
	
	@Override
	public String toString() {
		String s = "LOAD     " + rtAddr;
//		if(xReg != 0) s = s + "+R" + xReg + "(" + RTRegister.getValue(xReg) + ')';
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOAD(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOAD;
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
		return new SVM_LOAD(inpt);
	}

}
