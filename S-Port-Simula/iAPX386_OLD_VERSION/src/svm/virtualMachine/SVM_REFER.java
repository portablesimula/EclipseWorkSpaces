package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.Value;
import svm.value.dataAddress.GeneralAddress;
import svm.value.dataAddress.DataAddress;

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
	
	public Value load(int idx) {
		GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
		Value val = gaddr.load(idx);
		return val;
	}

	public void store(int idx, Value value, String comment) {
		GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
		gaddr.store(idx, value, comment);
	}

	@Override
	public void execute() {
		int gOfst = RTStack.popInt();
		DataAddress objadr = (DataAddress) RTStack.pop();
//		System.out.println("SVM_REFER: objadr="+objadr);
//		System.out.println("SVM_REFER: gOfst="+gOfst);
		RTRegister.putValue(xReg, new GeneralAddress(objadr, gOfst));	
		
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		Value regval = RTRegister.getValue(xReg);
		return "REFER    " + RTRegister.edReg(xReg) + '=' + ((regval == null)? "" : (regval.getClass().getSimpleName() + ':' + regval));
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_REFER(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iREFER;
		this.xReg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_REFER(inpt);
	}

}
