package svm.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;
import svm.value.dataAddress.DataAddress;

/**
 * Operation ASSIGN rtAddr xReg size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ...
 *
 * Pop 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr+xReg'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddre+xReg[0]     value1
 * 		rtAddre+xReg[1]     value2
 * 		...
 * 		rtAddre+xReg[size-1]  value'size
 * 
 * ----------------------------------------------------------------------------------------------------
 * 
 * Operation UPDATE rtAddr xReg size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ..., value1, value2, ... , value'size
 *
 * Pop 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr+xReg'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddre+xReg[0]     value1
 * 		rtAddre+xReg[1]     value2
 * 		...
 * 		rtAddre+xReg[size-1]  value'size
 * 
 * Finally; push the values back onto the Runtime Stack.
 * 
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private boolean update; // false: ASSIGN, true: UPDATE
	private DataAddress rtAddr;
	private int xReg;
	private int size;

	private final boolean DEBUG = false;

	public SVM_ASSIGN(boolean update, DataAddress rtAddress, int xReg, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.rtAddr = rtAddress;
		this.xReg = xReg;
		this.size = size;
	}

//	@Override
	public void TEST_execute() {
		if(DEBUG) {
			System.out.println("\nSVM_ASSIGN: BEGIN DEBUG INFO ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			RTUtil.printCurins();
			RTStack.dumpRTStack("SVM_ASSIGN: ");
		}
		
		DataAddress addr = this.rtAddr;
//		System.out.println("SVM_ASSIGN: TARGET="+addr);
//		switch(rtAddr.kind) {
//			case DataAddress.SEGMNT_ADDR: doAssign(addr, xReg); break;
//			case DataAddress.REL_ADDR:    doAssign(addr, xReg); break;
//			case DataAddress.STACK_ADDR:  Util.IERR(""); doAssign(addr, xReg); break;
//			case DataAddress.REMOTE_ADDR:
//				if(Global.TESTING_REMOTE) Util.IERR("NOT IMPL");
//				// this.addr is Stack Relative Address
//				DataAddress oaddr = RTStack.popOADDR();
//				addr = oaddr.addOffset(addr.getOfst());
////				System.out.println("SVM_ASSIGN: REMOTE_ADDR, TARGET="+addr);
//				doAssign(addr, xReg);
//				break;
//				
//			case DataAddress.REFER_ADDR:
//				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
//				doAssign(addr, 0);
//				break;
//	
//			default: Util.IERR("");
//		}

		addr.doAssign(update, xReg, size);

		Global.PSC.addOfst(1);
	}
	
//	private void doAssign(DataAddress addr, int xReg) {
//		Vector<Value> values = RTStack.pop(size);
//		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: BEFORE: sos.store: " + i + " " + values.get(i));
//		int idx = size;
//		int rx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
//		for(int i=0;i<size;i++) {
//			addr.store(rx + i, values.get((--idx)), "");
//		}
//		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: AFTER: sos.store: " + i + " " + values.get(i));
//		
////		boolean TESTING = true;
////		if(TESTING) {
////			addr.addOffset(rx).dumpArea("SVM_ASSIGN.doAssign", size);
////		}
//		
//		if(update) {
////			for(int i=0;i<size;i++) {
//				RTStack.pushx(values, "SVM_ASSIGN");
////			}
//			if(DEBUG) {
//				RTUtil.printCurins();
//				RTStack.dumpRTStack("SVM_ASSIGN: ");
//				System.out.println("SVM_ASSIGN: END DEBUG INFO\n");
//			}
//			
//			if(size > 3) System.exit(-1);
//		}		
//	}

	@Override
	public void execute() {
		if(DEBUG) {
			System.out.println("\nSVM_ASSIGN: BEGIN DEBUG INFO ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			RTUtil.printCurins();
			RTStack.dumpRTStack("SVM_ASSIGN: ");
		}
		Vector<Value> values = RTStack.pop(size);
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: values["+i+"] = " + values.get(i));
		
		DataAddress addr = this.rtAddr;
//		System.out.println("SVM_ASSIGN: TARGET="+addr);
//		switch(rtAddr.kind) {
//			case DataAddress.SEGMNT_ADDR: doAssign(addr, xReg, values); break;
//			case DataAddress.REL_ADDR:    doAssign(addr, xReg, values); break;
//			case DataAddress.STACK_ADDR:  Util.IERR(""); doAssign(addr, xReg, values); break;
//			case DataAddress.REMOTE_ADDR:
//				if(Global.TESTING_REMOTE) Util.IERR("NOT IMPL");
//				// this.addr is Stack Relative Address
//				DataAddress oaddr = RTStack.popOADDR();
//				addr = oaddr.addOffset(addr.getOfst());
////				System.out.println("SVM_ASSIGN: REMOTE_ADDR, TARGET="+addr);
//				doAssign(addr, xReg, values);
//				break;
//				
//			case DataAddress.REFER_ADDR:
//				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
//				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
//				doAssign(addr, 0, values);
//				break;
//	
//			default: Util.IERR("");
//		}

		addr.doAssign(update, xReg, size);

		Global.PSC.addOfst(1);
	}
	
//	private void doAssign(DataAddress addr, int xReg, Vector<Value> values) {
//		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: BEFORE: sos.store: " + i + " " + values.get(i));
//		int idx = size;
//		int rx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
//		for(int i=0;i<size;i++) {
//			addr.store(rx + i, values.get((--idx)), "");
//		}
//		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: AFTER: sos.store: " + i + " " + values.get(i));
//		
////		boolean TESTING = true;
////		if(TESTING) {
////			addr.addOffset(rx).dumpArea("SVM_ASSIGN.doAssign", size);
////		}
//		
//		if(update) {
////			for(int i=0;i<size;i++) {
//				RTStack.pushx(values, "SVM_ASSIGN");
////			}
//			if(DEBUG) {
//				RTUtil.printCurins();
//				RTStack.dumpRTStack("SVM_ASSIGN: ");
//				System.out.println("SVM_ASSIGN: END DEBUG INFO\n");
//			}
//			
//			if(size > 3) System.exit(-1);
//		}		
//	}

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
//	}
	
	@Override	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((update)? "UPDATE   " : "ASSIGN   ").append(rtAddr);
		if(xReg > 0) sb.append(" + ").append(RTRegister.edReg(xReg));
		sb.append(", size=").append(size);
//		if(xReg > 0) System.out.println("SVM_ASSIGN: " + sb);
		return sb.toString();
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_ASSIGN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.update = inpt.readBoolean();
//		this.rtAddr = RTAddress.read(inpt);
		this.rtAddr = (DataAddress) Value.read(inpt);
		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(update);
		rtAddr.write(oupt);
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}
	
	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		return new SVM_ASSIGN(inpt);
	}

}
