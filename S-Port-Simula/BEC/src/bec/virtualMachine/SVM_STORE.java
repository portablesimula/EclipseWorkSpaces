package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The size values on the top of the operand stack is stored at addr...
public class SVM_STORE extends SVM_Instruction {
	private final ObjectAddress rtAddr;
	private final int size;
	private final boolean indexed;
	
	private final boolean DEBUG = false;
	
	public SVM_STORE(ObjectAddress rtAddr, boolean indexed, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = rtAddr;
		this.size = size;
		this.indexed = indexed;
//		IO.println("NEW SVM_STORE: " + this);
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			Global.PSC.segment().dump("STORE.execute: ");
			RTStack.dumpRTStack("STORE.execute: ");
		}
		
		ObjectAddress addr = this.rtAddr;
		switch(addr.kind) {
			case ObjectAddress.SEGMNT_ADDR: doStore(addr, indexed); break;
			case ObjectAddress.REL_ADDR:    doStore(addr, indexed); break;
			case ObjectAddress.STACK_ADDR:  Util.IERR(""); doStore(addr, indexed); break;
			case ObjectAddress.REMOTE_ADDR:
//				int idx = 0;
				int idx = size - 1;
				if(indexed)	idx += RTStack.popInt();
				ObjectAddress oaddr = RTStack.popOADDR();
				addr = oaddr.addOffset(addr.getOfst());
				doStore2(addr, idx); break;
			case ObjectAddress.REFER_ADDR:
//				RTStack.dumpRTStack("SVM_STORE.execute: ");
				idx = size - 1;
				if(indexed)	idx += RTStack.popInt();
				int ofst = RTStack.popInt();
				addr = RTStack.popOADDR().addOffset(rtAddr.getOfst() + ofst);
				doStore2(addr, idx);
				break;
				default: Util.IERR("");
			}			
		
		if(DEBUG) {
//			addr.segment().dump("STORE.execute: ", addr.offset, addr.offset+size);
//			IO.println("STORE: size="+size);
			addr.dumpArea("STORE.execute: ", size);
		}
		
		Global.PSC.addOfst(1);		
	}
	
	private void doStore(ObjectAddress addr, boolean indexed) {
		int idx = size - 1;
		if(indexed)	idx += RTStack.popInt();
		int n = RTStack.size()-1;
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			if(DEBUG) IO.println("SVM_STORE: "+item+" ==> "+addr + "["+idx+"]");
			addr.store(idx--, item, "");
//			RTStack.printCallTrace("STORE.execute: ");
		}
	}
	
	private void doStore2(ObjectAddress addr, int idx) {
		int n = RTStack.size()-1;
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			if(DEBUG) IO.println("SVM_STORE: "+item+" ==> "+addr + "["+idx+"]");
			addr.store(idx--, item, "");
//			RTStack.printCallTrace("STORE.execute: ");
		}
	}
	
	public String toString() {
		String s = "";
		if(indexed) s += "IDX";
		if(size > 1) s = ", size=" + size;
		return "STORE    " + rtAddr + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_STORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = ObjectAddress.read(inpt);
		this.indexed = inpt.readBoolean();
		this.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		rtAddr.writeBody(oupt);
		oupt.writeBoolean(indexed);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
