package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;
import svm.value.dataAddress.DataAddress;
import svm.value.dataAddress.SegmentAddress;

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
	
	public SVM_DUP(DataAddress rtAddr2, int xReg, int size) {
		this.opcode = SVM_Instruction.iDUP;
		this.rtAddr = rtAddr2;
		this.xReg = xReg;
		this.size = size;
	}

	@Override
	public void execute() {
		if(rtAddr == null) {
			// DUP VALUE
			if(size == 1) {
//				Value tos = RTStack.pop();
//				RTStack.push(tos, "SVM_DUP: ");
//				RTStack.push(tos, "SVM_DUP: ");				
				RTStack.push(RTStack.peek(), "SVM_DUP: ");				
			} else {
				RTStack.dup(size);
			}
			if(xReg != 0) Util.IERR("NOT IMPL: xReg="+xReg);
		} else {
			Value tos = RTStack.pop();
//			if(tos != null)	IO.println("SVM_DUP: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			RTStack.push(tos, "SVM_DUP: ");
			if(rtAddr.kind == DataAddress.REMOTE_ADDR) {
				
//				if(Global.TESTING_REMOTE)
					Util.IERR("NOT IMPL");
				
				// this.addr is Stack Relative Address
				SegmentAddress oaddr = (SegmentAddress) RTStack.popOADDR();
//				IO.println("SVM_DUP.execute: oaddr="+oaddr+", rtAddr="+rtAddr);
				RTStack.push(oaddr, "SVM_DUP: ");
				
//				oaddr = oaddr.addOffset(rtAddr.getOfst()); // TODO: CHECK DETTE
				Util.IERR("CHECK DETTE");
				
				if(size != 1) Util.IERR("NOT IMPL: size="+size);
				tos = oaddr.load(0);
				RTStack.push(tos, "SVM_DUP: ");				
			} else {
				if(size != 1) Util.IERR("NOT IMPL: size="+size);
				tos = rtAddr.load(0);
				RTStack.push(tos, "SVM_DUP: ");
			}
		}
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String s = "";
		if(rtAddr != null) {
			s = rtAddr.toString();
			s = s + ( (xReg == 0)? "" : "+R" + xReg + "(" + RTRegister.getValue(xReg) + ')' );
		}
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
