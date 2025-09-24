package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;

/**
 * Operation PUSH rtAddr xReg size
 * 
 * Runtime Stack
 *    ... →
 *    ..., value1, value2, ... , value'size
 *
 * Push 'size' values from a data segment starting by pushing the value at 'rtAddr+xreg'.
 * After the operation the Runtime Stack looks like:
 * 
 * 		value1  =  rtAddre+xReg[0] 
 * 		value2  =  rtAddre+xReg[1]
 * 		...
 * 		value'size  =  rtAddre+xReg[size-1]
 */
public class SVM_PUSH extends SVM_Instruction {
	DataAddress rtAddr;
//	int xReg;
	int size;

//	private final boolean DEBUG = false;

//	public SVM_PUSH(DataAddress rtAddress, int xReg, int size) {
	public SVM_PUSH(DataAddress rtAddress, int size) {
		this.opcode = SVM_Instruction.iPUSH;
		this.rtAddr = rtAddress;
//		this.xReg = xReg;
		this.size = size;
		Reg.reads("SVM_PUSH: ", rtAddress);
	}
	
	public SVM_PUSH(Variable var) {
		this.opcode = SVM_Instruction.iPUSH;
		this.rtAddr = var.address;
		this.size = var.repCount;
	}
	
	@Override
	public void execute() {
		this.rtAddr.doLoad(size);
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		String s = "PUSH     " + rtAddr;
//		if(xReg != 0) s = s + "+R" + xReg + "(" + RTRegister.getValue(xReg) + ')';
		if(size > 1) s += ", " + size;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSH;
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
		return new SVM_PUSH(inpt);
	}

}
