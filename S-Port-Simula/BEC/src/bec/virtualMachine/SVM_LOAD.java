package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// The size values at addr... is loaded onto the operand stack.
public class SVM_LOAD extends SVM_Instruction {
	RTAddress addr;
	int size;

	private final boolean DEBUG = false;

	public SVM_LOAD(RTAddress addr, int size) {
		this.opcode = SVM_Instruction.iLOAD;
		this.addr = addr;
		this.size = size;
	}
	
	public SVM_LOAD(Variable var) {
		this.opcode = SVM_Instruction.iLOAD;
		this.addr = new RTAddress(var.address, 0);
		this.size = var.repCount;
	}
	
	@Override
	public void execute() {
		RTAddress addr = this.addr;
//		System.out.println("SVM_LOAD.execute: addr="+addr);
		if(this.addr.withRemoteBase) {
			// this.addr is Stack Relative Address
			ObjectAddress oaddr = RTStack.popOADDR();
			addr = new RTAddress(oaddr, addr.offset);
			addr.xReg = this.addr.xReg;
//			System.out.println("SVM_LOAD.execute: UPDDATED addr="+addr);
		}
		for(int i=0;i<size;i++) {
			Value value = addr.load(i);
			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
		}
		
		if(DEBUG) {
//			System.out.println("LOAD.execute: "+addr);
//			addr.segment().dump("LOAD.execute: ", addr.offset, addr.offset+size);
			addr.dumpArea("LOAD.execute: ", size);
		}
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		String s = "LOAD     " + addr;
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOAD(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOAD;
		this.addr = RTAddress.read(inpt);
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		addr.write(oupt);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOAD(inpt);
	}

}
