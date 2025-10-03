package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
	 * push( TOS );
	 * force TOS value;
	 * 
	 * A duplicate of TOS is pushed onto the stack and forced into value mode.
 */
public class SVM_DUP extends SVM_Instruction {
	private final ObjectAddress rtAddr;
	int xReg;
	private final int size;
	
	public SVM_DUP(ObjectAddress rtAddr, int xReg, int size) {
		this.opcode = SVM_Instruction.iDUP;
		this.rtAddr = rtAddr;
		this.xReg = xReg;
		this.size = size;
//		RTRegister.writes("SVM_DUP", xReg);
		Util.IERR("NOT IMPL");
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
			if(rtAddr.kind == ObjectAddress.REMOTE_ADDR) {
				// this.addr is Stack Relative Address
				ObjectAddress oaddr = RTStack.popOADDR();
//				IO.println("SVM_DUP.execute: oaddr="+oaddr+", rtAddr="+rtAddr);
				RTStack.push(oaddr, "SVM_DUP: ");
				oaddr = oaddr.addOffset(rtAddr.getOfst());
				
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

	public void OLD_execute() {
		Value tos = RTStack.pop();
//		if(tos != null)	IO.println("SVM_DUP: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
		RTStack.push(tos, "SVM_DUP: ");
		if(rtAddr == null) {
			// DUP VALUE
			if(size != 1) Util.IERR("NOT IMPL: size="+size);
			if(xReg != 0) Util.IERR("NOT IMPL: xReg="+xReg);
			RTStack.push(tos, "SVM_DUP: ");
//			Util.IERR("SJEKK DETTE");
		} else {
			if(rtAddr.kind == ObjectAddress.REMOTE_ADDR) {
				// this.addr is Stack Relative Address
				ObjectAddress oaddr = RTStack.popOADDR();
//				IO.println("SVM_DUP.execute: oaddr="+oaddr+", rtAddr="+rtAddr);
				RTStack.push(oaddr, "SVM_DUP: ");
				oaddr = oaddr.addOffset(rtAddr.getOfst());
				
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
			s = s + ( (xReg == 0)? "" : "+R" + xReg + "(" + DELETED_RTRegister.getValue(xReg) + ')' );
		}
		return "DUP      " + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		if(rtAddr == null) {
			oupt.writeBoolean(false);
		} else {
			oupt.writeBoolean(true);
			rtAddr.writeBody(oupt);
		}
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}

	public static SVM_DUP read(AttributeInputStream inpt) throws IOException {
//		SVM_DUP instr = new SVM_DUP(inpt.readBoolean());
		ObjectAddress rtAddr = null;
		boolean present = inpt.readBoolean();
		if(present) rtAddr = ObjectAddress.read(inpt);
		int xReg = inpt.readReg();
		int size = inpt.readShort();
		SVM_DUP instr = new SVM_DUP(rtAddr, xReg, size);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
