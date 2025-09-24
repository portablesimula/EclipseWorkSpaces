package bec.value.dataAddress;

import java.io.IOException;
import bec.AttributeInputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.CallStackFrame;
import bec.virtualMachine.RTStack;

public abstract class DataAddress extends Value {
	public int kind;
	public int ofst;

	public static final int SEGMNT_ADDR = 1; // Segment Address
	public static final int REMOTE_ADDR = 2; // Remote Address
	public static final int FRAME_ADDR = 3; // Frame relative Addtess
	public static final int STACK_ADDR = 4; // Stack relative Address
	public static final int REL_ADDR = 5; // Refer Address
	
//	int sibreg; // <ss>2<ireg>3<breg>3
//	public int scale;
	private int iReg;
	private int bReg;
	public boolean noIBREG() {
		if(iReg != 0) return false;
		if(bReg != 0) return false;
		return true;
	}
	public boolean hasIBREG() {
		if(iReg != 0) return true;
		if(bReg != 0) return true;
		return false;
	}

	public int getIreg() { return iReg; }
	public int getBreg() { return bReg; }
	public void setIreg(int r) { iReg = r; }
	public void setBreg(int r) { bReg = r; }
	
	public String edIBReg() {
		String s = "";
		if(bReg != 0) {
			s += Reg.edReg(bReg);
			if(iReg != 0) s += '|' + Reg.edReg(iReg);
		} else if(iReg != 0) s += Reg.edReg(iReg);
		return s;
	}

	public DataAddress addOffset(int ofst) {
		Util.IERR("MISSING addOffset: "+this.getClass().getSimpleName());
		return null;
	}

	/// Evaluate offset = offset + values in Ireg and Breg
	public DataAddress toRTAddress() {
		if(! Global.duringEXEC()) Util.IERR("");
		Util.IERR("MISSING toRTAddress: "+this.getClass().getSimpleName());
		return null;
	}

	public DataAddress toOADDR() {
		if(! Global.duringEXEC()) Util.IERR("");
		Util.IERR("MISSING toOADDR: "+this.getClass().getSimpleName());
		return null;
	}

	public Value load(int idx) {
		Util.IERR("MISSING load: "+this.getClass().getSimpleName());
		return null;
	}

	public Value load() {
		return load(0);
	}

	public void store(int idx, Value value, String comment) {
		Util.IERR("MISSING store: "+this.getClass().getSimpleName());
	}

	public DataAddress doLoad(int size) {
		if(bReg > 0) {
			Util.IERR("");
		}
		int idx = (iReg == 0)? 0 : Reg.getIntValue(iReg);
		for(int i=0;i<size;i++) {
			Value value = this.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+this+":"+size);
		}		
		return null;
	}

	public void doStore(int size) {
		int n = RTStack.size()-1;
		int idx = size - 1;
		if(bReg > 0) {
			Util.IERR("");
		}
		if(iReg > 0) {
//			IO.println("DataAddress.doStore: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
			idx += Reg.getIntValue(iReg);
//			IO.println("DataAddress.doStore: idx="+idx+", xReg="+RTRegister.edRegVal(xReg));
		}
		
		DataAddress oaddr = this.toOADDR();
		IO.println("DataAddress.doStore: idx="+idx+", oaddr="+oaddr);
		
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
//			IO.println("DataAddress.doStore: "+item+" ==> "+this + "["+idx+"]");
			
//			this.store(idx--, item, "");
			oaddr.store(idx--, item, "");
			
//			RTStack.printCallTrace("STORE.execute: ");
		}
	}

	public DataAddress toStackAddress() {
		DataAddress res = this;
		if(this instanceof FrameAddress relAddr) {
			CallStackFrame callStackTop = RTStack.callStack_TOP();
			int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			res = new StackAddress(bias + relAddr.ofst);
		}
		return res;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public static DataAddress read(AttributeInputStream inpt) throws IOException {
		int kind = inpt.readKind();
//		IO.println("DataAddress.read: kind="+kind);
		switch(kind) {
			case SEGMNT_ADDR: return SegmentAddress.read(inpt);
			case REMOTE_ADDR: return RemoteAddress.read(inpt);
			case REL_ADDR:  return RelAddress.read(inpt);
			case FRAME_ADDR:  return FrameAddress.read(inpt);
			case STACK_ADDR:  return StackAddress.read(inpt);
		}
		Util.IERR("");
		return null;
	}

}
