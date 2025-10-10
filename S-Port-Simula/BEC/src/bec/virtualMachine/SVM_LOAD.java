package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;
import bec.value.Value;

// The size values at addr... is loaded onto the operand stack.
public class SVM_LOAD extends SVM_Instruction {
	private final ObjectAddress rtAddr;
	private final int size;
	private final boolean indexed;

	public SVM_LOAD(ObjectAddress rtAddr, boolean indexed, int size) {
		this.opcode = SVM_Instruction.iLOAD;
		this.rtAddr = rtAddr;
		this.size = size;
		this.indexed = indexed;
	}
	
	@Override
	public void execute() {
		int idx =(! indexed)? 0 :RTStack.popInt();
		ObjectAddress addr = rtAddr.toRTMemAddr();
		for(int i=0;i<size;i++) {
			Value value = addr.load(idx + i);
			RTStack.push(value, "SVM_LOAD: "+addr+":"+size);
		}		

		Global.PSC.addOfst(1);
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
