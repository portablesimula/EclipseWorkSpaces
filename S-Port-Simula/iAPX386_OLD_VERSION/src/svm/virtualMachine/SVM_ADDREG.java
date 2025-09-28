package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.Value;

/**
 * Operation ADDREG xReg

 * Runtime Stack
 *    ..., tos →
 *    ...
 *
 * Pop one value from the Runtime Stack and update xReg (xReg = tos + xReg)
 * The addition operation depends on the types ....
 * 
 * INT   + INT   --> INT
 * GADDR + INT   --> GADDR
 * OADDR + INT   --> OADDR
 * REAL  + REAL  --> REAL
 * LREAL + LREAL --> LREAL 
 * 
 * Other combinations are illegal.
 */
public class SVM_ADDREG extends SVM_Instruction {
	int xReg;

	private final boolean DEBUG = false;

	public SVM_ADDREG(int xReg) {
		this.opcode = SVM_Instruction.iADDREG;
		this.xReg = xReg;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value rval = RTRegister.getValue(xReg);
		
		if(DEBUG) {
			if(tos != null)	IO.println("SVM_ADDEG: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(rval != null)	IO.println("SVM_ADDEG: SOS: " + rval.getClass().getSimpleName() + "  " + rval);
			IO.println("SVM_ADD: " + rval + " + " + tos);
		}
		Value res = (rval == null)? tos : rval.add(tos);
		if(DEBUG) IO.println("SVM_ADD: " + rval + " + " + tos + " ==> " + res);
		RTRegister.putValue(xReg, res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "ADDREG   " + RTRegister.edReg(xReg);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_ADDREG read(AttributeInputStream inpt) throws IOException {
		SVM_ADDREG instr = new SVM_ADDREG(inpt.readReg());
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
