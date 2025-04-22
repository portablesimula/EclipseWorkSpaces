package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Remove one items on the Runtime-Stack and update xReg (xReg = TOS + xReg)
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
		Value tos = RTStack.pop().value();
		Value rval = RTRegister.getValue(xReg);
		
		if(DEBUG) {
			if(tos != null)	System.out.println("SVM_ADDEG: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(rval != null)	System.out.println("SVM_ADDEG: SOS: " + rval.getClass().getSimpleName() + "  " + rval);
			System.out.println("SVM_ADD: " + rval + " + " + tos);
		}
		Value res = (rval == null)? tos : rval.add(tos);
		if(DEBUG) System.out.println("SVM_ADD: " + rval + " + " + tos + " ==> " + res);
		RTRegister.putValue(xReg, res);
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "ADDREG   ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_ADDREG read(AttributeInputStream inpt) throws IOException {
		SVM_ADDREG instr = new SVM_ADDREG(inpt.readReg());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
