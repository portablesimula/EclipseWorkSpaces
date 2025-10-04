package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// The size values at addr... is loaded onto the operand stack.
public class SVM_LOAD extends SVM_Instruction {
	private final ObjectAddress rtAddr;
	private final int size;
	private final boolean indexed;

	private final boolean DEBUG = false;

	public SVM_LOAD(ObjectAddress rtAddr, boolean indexed, int size) {
		this.opcode = SVM_Instruction.iLOAD;
		this.rtAddr = rtAddr;
		this.size = size;
		this.indexed = indexed;
	}
	
//	public SVM_LOAD(Variable var, boolean indexed) {
//		this.opcode = SVM_Instruction.iLOAD;
////		this.addr = new RTAddress(var.address, 0);
//		this.rtAddr = var.address;
//		this.size = var.repCount;
//		this.indexed = indexed;
//	}
	
	@Override
	public void execute() {
		ObjectAddress addr = this.rtAddr;
		switch(addr.kind) {
			case ObjectAddress.SEGMNT_ADDR: doLoad(addr, indexed); break;
			case ObjectAddress.REL_ADDR:    doLoad(addr, indexed); break;
			case ObjectAddress.STACK_ADDR:  Util.IERR(""); doLoad(addr, indexed); break;
			case ObjectAddress.REMOTE_ADDR:
//				Segment.lookup("PSEG_ADHOC03").dump("SVM_LOAD.REMOTE_ADDR: "+indexed);
				RTStack.dumpRTStack("SVM_LOAD.REMOTE_ADDR: "+indexed);
				int idx = 0;
//				int idx = size - 1;
				if(indexed)	idx += RTStack.popInt();
				ObjectAddress oaddr = RTStack.popOADDR();
				addr = oaddr.addOffset(addr.getOfst());
				doLoad2(addr, idx); break;
				
			case ObjectAddress.REFER_ADDR:
//				RTStack.dumpRTStack("SVM_STORE.execute: ");
				idx = size - 1;
				if(indexed)	idx += RTStack.popInt();
				int ofst = RTStack.popInt();
				addr = RTStack.popOADDR().addOffset(rtAddr.getOfst() + ofst);
				doLoad2(addr, idx); break;
	
			default: Util.IERR("");
		}			

		if(DEBUG) {
//			IO.println("LOAD.execute: "+addr);
//			addr.segment().dump("LOAD.execute: ", addr.offset, addr.offset+size);
			addr.dumpArea("LOAD.execute: ", size);
		}
		Global.PSC.addOfst(1);
	}
		
	private void doLoad(ObjectAddress addr, boolean indexed) {
		int idx = 0;
		if(indexed)	idx += RTStack.popInt();
		for(int i=0;i<size;i++) {
			Value value = addr.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
		}		
	}
	
	private void doLoad2(ObjectAddress addr, int idx) {
//		int idx = 0;
//		if(indexed)	idx += RTStack.popInt();
		for(int i=0;i<size;i++) {
			Value value = addr.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
		}		
	}
	
	@Override
	public String toString() {
		String s = "LOAD     " + rtAddr;
		if(indexed) s += "+IDX";
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOAD(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOAD;
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
		return new SVM_LOAD(inpt);
	}

}
