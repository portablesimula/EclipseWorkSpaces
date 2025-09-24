package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.value.dataAddress.GeneralAddress;
import bec.value.dataAddress.DataAddress;

/**
 * Operation POPGADR reg
 * 
 * Runtime Stack
 *    ..., gaddr'base, gaddr'ofst →
 *    ...
 *    
 * A GeneralAddress is poped off the Runtime Stack and stored in the register 'reg'.
 */
public class SVM_POPGADR extends SVM_Instruction {
	int xReg; // To hold the GADDR

	public SVM_POPGADR(int xReg) {
		this.opcode = SVM_Instruction.iPOPGADR;
		this.xReg=xReg;
		Util.IERR("FJERNES ???");
	}
	
	public Value load(int idx) {
		GeneralAddress gaddr = (GeneralAddress) RTRegister.getAddrValue(xReg);
		Value val = gaddr.load(idx);
		return val;
	}

	public void store(int idx, Value value, String comment) {
		GeneralAddress gaddr = (GeneralAddress) RTRegister.getAddrValue(xReg);
		gaddr.store(idx, value, comment);
	}

	@Override
	public void execute() {
		int gOfst = RTStack.popInt();
		DataAddress objadr = (DataAddress) RTStack.pop();
//		IO.println("SVM_POPGADR.execute: objadr=" + objadr.getClass().getSimpleName() + " " + objadr);
		if(objadr instanceof GeneralAddress gaddr) {
			RTRegister.putAddrValue(xReg, gaddr.addOffset(gOfst));	
		} else {
			RTRegister.putAddrValue(xReg, new GeneralAddress(objadr, gOfst));	
		}
//		IO.println("SVM_POPGADR.execute: objadr=" + objadr + ", gOfst=" + gOfst + "  ==>  " + RTRegister.edRegVal(xReg));
		
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
//		Value regval = RTRegister.getValue(xReg);
//		return "REFER    " + RTRegister.edReg(xReg) + '=' + ((regval == null)? "" : (regval.getClass().getSimpleName() + ':' + regval));
		return "POPGADR  " + RTRegister.edRegVal(xReg);
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POPGADR(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOPGADR;
		this.xReg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POPGADR(inpt);
	}

}
