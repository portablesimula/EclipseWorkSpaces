package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.Value;

/**
 * Remove one items on the Runtime-Stack and update xReg (xReg = TOS + xReg)
 */
public class DELETED_SVM_ADDREG extends SVM_Instruction {
	int xReg;

	private final boolean DEBUG = false;

	public DELETED_SVM_ADDREG(int xReg) {
		this.opcode = SVM_Instruction.iADDREG;
		this.xReg = xReg;
		DELETED_RTRegister.reads("SVM_ADDREG", xReg);
		DELETED_RTRegister.writes("SVM_ADDREG", xReg);
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value rval = DELETED_RTRegister.getValue(xReg);
		
		if(DEBUG) {
			if(tos != null)	IO.println("SVM_ADDEG: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(rval != null)	IO.println("SVM_ADDEG: SOS: " + rval.getClass().getSimpleName() + "  " + rval);
			IO.println("SVM_ADD: " + rval + " + " + tos);
		}
		Value res = (rval == null)? tos : rval.add(tos);
		if(DEBUG) IO.println("SVM_ADD: " + rval + " + " + tos + " ==> " + res);
		DELETED_RTRegister.putValue(xReg, res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "ADDREG   ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	private DELETED_SVM_ADDREG() {
		this.opcode = SVM_Instruction.iADDREG;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static DELETED_SVM_ADDREG read(AttributeInputStream inpt) throws IOException {
		DELETED_SVM_ADDREG instr = new DELETED_SVM_ADDREG();
		instr.xReg = inpt.readReg();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
