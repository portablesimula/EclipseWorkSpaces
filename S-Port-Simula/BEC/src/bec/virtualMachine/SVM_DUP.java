package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
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
	RTAddress rtAddr;
	
	public SVM_DUP(RTAddress rtAddr) {
		this.opcode = SVM_Instruction.iDUP;
		this.rtAddr = rtAddr;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
//		if(tos != null)	System.out.println("SVM_DUP: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
		RTStack.push(tos, "SVM_DUP: ");
		if(rtAddr == null) {
			// DUP VALUE
			RTStack.push(tos, "SVM_DUP: ");
//			Util.IERR("SJEKK DETTE");
		} else {
			boolean TESTING = true;
			
			if(TESTING) {

				if(rtAddr.withRemoteBase) {
					// this.addr is Stack Relative Address
					ObjectAddress oaddr = RTStack.popOADDR();
					RTStack.push(oaddr, "SVM_DUP: ");
					RTAddress addr = new RTAddress(oaddr, rtAddr.offset);
					addr.xReg = rtAddr.xReg;
//					System.out.println("SVM_DUP.execute: UPDDATED addr="+addr);
					tos = addr.load(0);
				} else tos = rtAddr.load(0);
//				System.out.println("SVM_DUP: "+rtAddr+" ===> NEW tos="+tos);
//				Util.IERR("");
			}
//			ObjectAddress oaddr = (ObjectAddress) tos;
//			oaddr = oaddr.addOffset(rtAddr.offset);
//			RTStack.push(oaddr, "SVM_DUP: ");
//			tos = oaddr.load();
//			System.out.println("SVM_DUP: NEW tos="+tos);
			RTStack.push(tos, "SVM_DUP: ");
		}
//		RTStack.push(tos, "SVM_DUP: ");
		Global.PSC.addOfst(1);
//		Util.IERR("");
	}
	
//	@Override
	public void OLD_execute() {
		Value tos = RTStack.pop();
		if(tos != null)	System.out.println("SVM_DUP: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
		if(rtAddr == null) {
			// DUP VALUE
			RTStack.push(tos, "SVM_DUP: ");
//			Util.IERR("SJEKK DETTE");
		} else {
			ObjectAddress oaddr = (ObjectAddress) tos;
			oaddr = oaddr.addOffset(rtAddr.offset);
			RTStack.push(oaddr, "SVM_DUP: ");
			tos = oaddr.load();
			System.out.println("SVM_DUP: NEW tos="+tos);
		}
		RTStack.push(tos, "SVM_DUP: ");
		Global.PSC.addOfst(1);
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
//		return "DUP      " + address;
		return "DUP      " + rtAddr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		if(rtAddr == null) {
			oupt.writeBoolean(false);
		} else {
			oupt.writeBoolean(true);
			rtAddr.write(oupt);
		}
	}

	public static SVM_DUP read(AttributeInputStream inpt) throws IOException {
//		SVM_DUP instr = new SVM_DUP(inpt.readBoolean());
		RTAddress rtAddr = null;
		boolean present = inpt.readBoolean();
		if(present) rtAddr = RTAddress.read(inpt);
		SVM_DUP instr = new SVM_DUP(rtAddr);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
