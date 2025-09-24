package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;

/**
 * Operation ADDREG xReg1 xReg2
 *
 * xReg1 := xReg1 + xReg2
 * 
 */
public class SVM_ADDREG extends SVM_Instruction {
	int xReg1;
	int xReg2;

	private final boolean DEBUG = false;

	public SVM_ADDREG(int xReg1, int xReg2) {
		this.opcode = SVM_Instruction.iADDREG;
		this.xReg1 = xReg1;
		this.xReg2 = xReg2;
		Reg.reads("SVM_ADDREG: ", xReg1, xReg2); Reg.writes("SVM_ADDREG: ", xReg1);
	}

	@Override
	public void execute() {
		int rval1 = Reg.getIntValue(xReg1);
		int rval2 = Reg.getIntValue(xReg2);
		
		if(DEBUG) IO.println("SVM_ADD: " + rval + " + " + tos);
		
		int res = tos + rval;
		if(DEBUG) IO.println("SVM_ADD: " + rval + " + " + tos + " ==> " + res);
		RTRegister.putIntValue(xReg, res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "ADDREG   " + Reg.edReg(xReg1) + " + " + Reg.edReg(xReg1);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_ADDREG() {
		this.opcode = SVM_Instruction.iADDREG;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg1);
		oupt.writeReg(xReg2);
	}

	public static SVM_ADDREG read(AttributeInputStream inpt) throws IOException {
		SVM_ADDREG instr = new SVM_ADDREG();
		instr.xReg1 = inpt.readReg();
		instr.xReg2 = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
