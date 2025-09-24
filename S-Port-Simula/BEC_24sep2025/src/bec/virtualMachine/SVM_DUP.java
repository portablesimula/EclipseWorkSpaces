package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.SegmentAddress;

/**
	 * push( TOS );
	 * force TOS value;
	 * 
	 * A duplicate of TOS is pushed onto the stack and forced into value mode.
 */
public class SVM_DUP extends SVM_Instruction {
	DataAddress rtAddr;
	int xReg;
	int size;
	
	public SVM_DUP(DataAddress rtAddr, int xReg, int size) {
		this.opcode = SVM_Instruction.iDUP;
		this.rtAddr = rtAddr;
		this.xReg = xReg;
		this.size = size;
		Util.IERR("FJERNE xReg ???");
	}

	@Override
	public void execute() {
		if(rtAddr == null) {
			// DUP VALUE
			if(size == 1) {
				RTStack.push(RTStack.peek(), "SVM_DUP: ");				
			} else {
				RTStack.dup(size);
			}
			if(xReg != 0) Util.IERR("NOT IMPL: xReg="+xReg);
		} else {
//			IO.println("SVM_DUP: " + this);
			if(size != 1) Util.IERR("NOT IMPL: size="+size);
			Value tos = rtAddr.load(0);
			RTStack.push(tos, "SVM_DUP: ");
			if(xReg != 0) Util.IERR("NOT IMPL: xReg="+xReg);
		}
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String s = "";
		if(rtAddr != null) s = rtAddr.toString() + ( (xReg == 0)? "" : RTRegister.edRegVal(xReg) );
		return "DUP      " + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		if(rtAddr == null) {
			oupt.writeBoolean(false);
		} else {
			oupt.writeBoolean(true);
			rtAddr.write(oupt);
		}
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_DUP read(AttributeInputStream inpt) throws IOException {
		DataAddress rtAddr = null;
		boolean present = inpt.readBoolean();
		if(present) rtAddr = (DataAddress) Value.read(inpt);
		int xReg = inpt.readReg();
		int size = inpt.readShort();
		SVM_DUP instr = new SVM_DUP(rtAddr, xReg, size);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
