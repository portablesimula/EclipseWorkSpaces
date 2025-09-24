package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;

/**
 * Operation ASSIGN rtAddr xReg size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ...
 *
 * Pop 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr+xReg'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddr[0]     value1
 * 		rtAddr[1]     value2
 * 		...
 * 		rtAddr[size-1]  value'size
 * 
 * ----------------------------------------------------------------------------------------------------
 * 
 * Operation UPDATE rtAddr xReg size
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'size →
 *    ..., value1, value2, ... , value'size
 *
 * Pop 'size' values from the Runtime Stack and store them starting by storing value1 at 'rtAddr+xReg'.
 * After the operation the data segment looks like:
 * 
 * 		rtAddr[0]     value1
 * 		rtAddr[1]     value2
 * 		...
 * 		rtAddr[size-1]  value'size
 * 
 * Finally; push the values back onto the Runtime Stack.
 * 
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private boolean update; // false: ASSIGN, true: UPDATE
	private DataAddress rtAddr;
//	private int xReg;
	private int size;

	private final boolean DEBUG = false;

//	public SVM_ASSIGN(boolean update, DataAddress rtAddress, int xReg, int size) {
	public SVM_ASSIGN(boolean update, DataAddress rtAddress, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.rtAddr = rtAddress;
//		this.xReg = xReg;
		this.size = size;
		Reg.reads(rtAddr);
	}

	@Override
	public void execute() {
		if(DEBUG) {
			IO.println("\nSVM_ASSIGN: BEGIN DEBUG INFO ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			RTUtil.printCurins();
			RTStack.dumpRTStack("SVM_ASSIGN: ");
		}
		Vector<Value> values = RTStack.pop(size);
		if(DEBUG) for(int i=0;i<size;i++) IO.println("SVM_ASSIGN: BEFORE: sos.store: " + i + " " + values.get(i));
		int idx = size;
		int rx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
		for(int i=0;i<size;i++) {
			this.rtAddr.store(rx + i, values.get((--idx)), "");
		}
		if(DEBUG) for(int i=0;i<size;i++) IO.println("SVM_ASSIGN: AFTER: sos.store: " + i + " " + values.get(i));
		
		if(update) {
			RTStack.pushx(values, "SVM_ASSIGN");
			if(DEBUG) {
				RTUtil.printCurins();
				RTStack.dumpRTStack("SVM_ASSIGN: ");
				IO.println("SVM_ASSIGN: END DEBUG INFO\n");
			}
			
			if(size > 3) Util.IERR("HVA NÅ ???"); //System.exit(-1);
		}	
		
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((update)? "UPDATE   " : "ASSIGN   ").append(rtAddr);
//		if(xReg > 0) sb.append(" + ").append(RTRegister.edReg(xReg));
		sb.append(", size=").append(size);
//		if(xReg > 0) IO.println("SVM_ASSIGN: " + sb);
		return sb.toString();
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_ASSIGN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = inpt.readBoolean();
//		this.rtAddr = RTAddress.read(inpt);
		this.rtAddr = (DataAddress) Value.read(inpt);
//		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(update);
		rtAddr.write(oupt);
//		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}
	
	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		return new SVM_ASSIGN(inpt);
	}

}
