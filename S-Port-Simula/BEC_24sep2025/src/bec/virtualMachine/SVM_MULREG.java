package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;

/**
 * Operation MULREG xReg1 size'int
 *
 * xReg1 := xReg1 * size'int
 * 
 */
public class SVM_MULREG extends SVM_Instruction {
	int xReg;
	int size;

	private final boolean DEBUG = false;

	public SVM_MULREG(int xReg, int size) {
		this.opcode = SVM_Instruction.iMULREG;
		this.xReg = xReg;
		this.size = size;
		Reg.reads("SVM_MULREG: ", xReg); Reg.writes("SVM_MULREG: ", xReg);
	}

	@Override
	public void execute() {
		int rval = Reg.getIntValue(xReg);
		
		if(DEBUG) IO.println("SVM_ADD: " + rval + " * " + size);
		
		int res = tos + rval;
		if(DEBUG) IO.println("SVM_ADD: " + rval + " * " + size + " ==> " + res);
		RTRegister.putIntValue(xReg, res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "MULREG   " + Reg.edReg(xReg) + " * " + size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_MULREG() {
		this.opcode = SVM_Instruction.iMULREG;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
		oupt.writeReg(size);
	}

	public static SVM_MULREG read(AttributeInputStream inpt) throws IOException {
		SVM_MULREG instr = new SVM_MULREG();
		instr.xReg = inpt.readReg();
		instr.size = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
