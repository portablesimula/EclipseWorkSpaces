package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The size values on the top of the operand stack is stored at addr...
public class SVM_STORE extends SVM_Instruction {
	private final ObjectAddress rtAddr;
	private final int size;
	private final boolean indexed;
	
	public SVM_STORE(ObjectAddress rtAddr, boolean indexed, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.rtAddr = rtAddr;
		this.size = size;
		this.indexed = indexed;
	}
	
	@Override
	public void execute() {
		int idx = size - 1;
		if(indexed)	idx += RTStack.popInt();
		ObjectAddress addr = rtAddr.toRTMemAddr();
		int n = RTStack.size()-1;
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			addr.store(idx--, item, "");
		}
		
		Global.PSC.addOfst(1);		
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
