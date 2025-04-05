package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.GeneralAddress;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
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
	Type type;
	int xReg;

	public SVM_REFER(Type type, int xReg) {
		this.opcode = SVM_Instruction.iREFER;
		this.type = type;
		this.xReg=xReg;
	}

	@Override
	public void execute() {
		int gOfst = RTStack.popInt();
		ObjectAddress objadr = (ObjectAddress) RTStack.pop().value();
		RTRegister.putValue(xReg, new GeneralAddress(objadr, gOfst));

		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "REFER    " + Scode.edTag(type.tag) + " R" + xReg;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_REFER(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iREFER;
		this.type = Type.read(inpt);
		this.xReg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		type.write(oupt);
		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_REFER(inpt);
	}

}
