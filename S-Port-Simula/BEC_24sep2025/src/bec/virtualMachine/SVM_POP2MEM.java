package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;

/**
 * Operation STORE rtAddr size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ..., value1, value2, ... , value'size
 *
 * Move 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddr[0]     value1
 * 		rtAddr[1]     value2
 * 		...
 * 		rtAddr[size-1]  value'size
 * 
 * The Runtime Stack remains unchanged.
 * 
 */
public class SVM_POP2MEM extends SVM_Instruction {
	DataAddress rtAddr;
	int size;
	
	private final boolean DEBUG = false;
	
	public SVM_POP2MEM(DataAddress rtAddress, int size) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.rtAddr = rtAddress;
//		this.xReg = xReg;
		this.size = size;
//		IO.println("NEW SVM_STORE: " + this);
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			Global.PSC.segment().dump("STORE.execute: ");
			RTStack.dumpRTStack("STORE.execute: ");
		}
		this.rtAddr.doStore(size);
		Global.PSC.addOfst(1);		
	}
	
	public String toString() {
		String s = "";
//		if(xReg != 0) s = s + '+' + RTRegister.edRegVal(xReg);
		if(size > 1) s = ", size=" + size;
		return "POP2MEM  " + rtAddr + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2MEM(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.rtAddr = (DataAddress) Value.read(inpt);
//		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		rtAddr.write(oupt);
//		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2MEM(inpt);
	}

}
