package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.value.Value;

// The size values at addr... is pushed onto the operand stack.
public class SVM_PUSH extends SVM_Instruction {
	RTAddress addr;
	int size;
	
	public SVM_PUSH(RTAddress addr, int size) {
		this.opcode = SVM_Instruction.iPUSH;
		this.addr = addr;
		this.size = size;
	}
	
	public SVM_PUSH(Variable var) {
		this.opcode = SVM_Instruction.iPUSH;
		this.addr = new RTAddress(var.address, 0);
		this.size = var.repCount;
	}
	
	@Override
	public void execute() {
			
		RTAddress addr = this.addr;
		if(this.addr.withRemoteBase) {
			// this.addr is Stack Relative Address
			ObjectAddress oaddr = RTStack.popOADDR();
			addr = new RTAddress(oaddr, addr.offset);
		}
			
//		System.out.println("SVM_PUSH: addr=" + addr+", size="+size);
		for(int i=0;i<size;i++) {
			Value value = addr.load(i, size);
//			System.out.println("SVM_PUSH: " + value);
			RTStack.push(value, "SVM_PUSH");
		}
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		String s = "PUSH     " + addr;
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSH;
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
		return new SVM_PUSH(inpt);
	}

}
