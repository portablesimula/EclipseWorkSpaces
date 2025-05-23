package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The size values on the top of the operand stack is stored at addr...
public class SVM_STORE extends SVM_Instruction {
	RTAddress addr;
	int size;
	
	private final boolean DEBUG = false;
	
//	public SVM_STORE(AddressItem itm, int size) {
//		this.opcode = SVM_Instruction.iSTORE;
//		this.addr = new RTAddress(itm);
//		this.size = size;
//	}
	
	public SVM_STORE(RTAddress addr, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.addr = addr;
		this.size = size;
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			Global.PSC.segment().dump("STORE.execute: ");
			RTStack.dumpRTStack("STORE.execute: ");
		}
			
		RTAddress addr = this.addr;
		if(this.addr.withRemoteBase) {
			// this.addr is Stack Relative Address
			ObjectAddress oaddr = RTStack.popOADDR();
			addr = new RTAddress(oaddr, addr.offset);
			addr.xReg = this.addr.xReg;
		}
		int n = RTStack.size()-1;
		int idx = size - 1;
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			if(DEBUG) System.out.println("STORE: "+item+" ==> "+addr + "["+idx+"]");
			addr.store(idx--, item, "");
//			RTStack.printCallTrace("STORE.execute: ");
		}
		
		if(DEBUG) {
//			addr.segment().dump("STORE.execute: ", addr.offset, addr.offset+size);
//			System.out.println("STORE: size="+size);
			addr.dumpArea("STORE.execute: ", size);
		}
		
		Global.PSC.ofst++;		
	}
	
	public String toString() {
		String cnt = "";
		if(size > 1) cnt = ", size=" + size;
//		return "PEEK2MEM  " + addr + cnt;
		return "STORE    " + addr + cnt;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_STORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		boolean present = inpt.readBoolean();
		if(present) {
			this.addr = RTAddress.read(inpt);
		}
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		if(addr != null) {
			oupt.writeBoolean(true);
			addr.write(oupt);
		} else oupt.writeBoolean(false);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
