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
		Value tos = RTStack.pop().value();
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
		Global.PSC.ofst++;
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
//		oupt.writeBoolean(address);
		rtAddr.write(oupt);
	}

	public static SVM_DUP read(AttributeInputStream inpt) throws IOException {
//		SVM_DUP instr = new SVM_DUP(inpt.readBoolean());
		SVM_DUP instr = new SVM_DUP(RTAddress.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
