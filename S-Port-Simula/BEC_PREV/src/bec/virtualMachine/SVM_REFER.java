package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.value.GeneralAddress;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * 
 * refer resolved_type
 * force TOS value; check TOS type(GADDR);
 * TOS.MODE := REF; TOS.TYPE := type;
 * 
 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
 * 
 * RTStak: TOS: gaddr'offset
 *         SOS: gaddr'objaddr
 *         
 * TOS and SOS is removed to form a GADDR value and store it in xReg.
 * 
 *                           =================
 *       (TOS) ==============|==> GADDR VALUE –|----------.
 *                           =================            |
 *                                                        |
 *                                                        |
 *  The resulting                                         V
 *       TOS -------------------------------------------->.==========.
 *   after refer                  REF                     |  object  |
 *                                                        |    of    |
 *                                                        |  "type"  |
 *                                                        '=========='
 */
public class SVM_REFER extends SVM_Instruction {
	int xReg;

	public SVM_REFER(int xReg) {
		this.opcode = SVM_Instruction.iREFER;
		this.xReg=xReg;
	}

	@Override
	public void execute() {
		int gOfst = RTStack.popInt();
		ObjectAddress objadr = (ObjectAddress) RTStack.pop();
		
		RTRegister.putValue(xReg, new GeneralAddress(objadr, gOfst));

		BecGlobal.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "REFER    " + "R" + xReg;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_REFER(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iREFER;
		this.xReg = inpt.readReg();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_REFER(inpt);
	}

}
