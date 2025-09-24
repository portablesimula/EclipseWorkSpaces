package svm.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.CallStackFrame;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.RTStack;

public abstract class DataAddress extends DataAddress {
	public int kind;
	public int ofst;

	public static final int SEGMNT_ADDR = 1; // Segment Address
	public static final int REMOTE_ADDR = 2; // Remote Address
	public static final int REFER_ADDR = 3; // Refer Address
	public static final int REL_ADDR = 4; // Frame relative Addtess
	public static final int STACK_ADDR = 5; // Stack relative Address

	public DataAddress addOffset(int ofst) {
		Util.IERR("MISSING addOffset: "+this.getClass().getSimpleName());
		return null;
	}

//	public DataAddress doLoad(int xReg, int size) {
//		Util.IERR("MISSING doLoad: "+this.getClass().getSimpleName());
//		return null;
//	}
//
//	public void doStore(int xReg, int size) {
//		Util.IERR("MISSING doStore: "+this.getClass().getSimpleName());
//	}

	public void doAssign(boolean update, int xReg, int size) {
		Util.IERR("MISSING doAssign: "+this.getClass().getSimpleName());
	}

//	public Value load(int idx) {
//		Util.IERR("MISSING load: "+this.getClass().getSimpleName());
//		return null;
//	}
//
//	public Value load() {
//		return load(0);
//	}
//
//	public void store(int idx, Value value, String comment) {
//		Util.IERR("MISSING store: "+this.getClass().getSimpleName());
//	}

//	@Override
	public DataAddress doLoad(int xReg, int size) {
		int idx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
		for(int i=0;i<size;i++) {
			Value value = this.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+this+":"+size);
		}		
		return null;
	}

//	@Override
	public void doStore(int xReg, int size) {
		int n = RTStack.size()-1;
		int idx = size - 1;
		if(xReg > 0) {
//			System.out.println("DataAddress.doStore: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
			idx = idx + RTRegister.getIntValue(xReg);
//			System.out.println("DataAddress.doStore: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
		}
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
//			System.out.println("DataAddress.doStore: "+item+" ==> "+this + "["+idx+"]");
			this.store(idx--, item, "");
//			RTStack.printCallTrace("STORE.execute: ");
		}
	}

	public DataAddress toStackAddress() {
		DataAddress res = this;
//		if(this.kind == DataAddress.REL_ADDR) {
		if(this instanceof FrameAddress relAddr) {
//			System.out.println("DataAddress.toStackAddress: OADDR: "+this);
//			RTStack.dumpRTStack("DataAddress.toStackAddress: NOTE: ");
//			System.out.println("DataAddress.toStackAddress: VALUE: "+this.load());
			
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
//			oaddr = new DataAddress(STACK_ADDR, segID, xReg, bias + ofst);
			res = new StackAddress(bias + relAddr.ofst);
			
//			System.out.println("DataAddress.toStackAddress: VALUE: "+oaddr.load());
		}
//		Util.IERR("");
		return res;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		int kind = inpt.readKind();
//		System.out.println("RTAddress.read: kind="+kind);
		switch(kind) {
			case SEGMNT_ADDR: return SegmentAddress.read(inpt);
			case REMOTE_ADDR: return RemoteAddress.read(inpt);
			case REFER_ADDR:  return RelAddress.read(inpt);
			case REL_ADDR:    return FrameAddress.read(inpt);
			case STACK_ADDR:  return StackAddress.read(inpt);
		}
		Util.IERR("");
		return null;
	}

}
