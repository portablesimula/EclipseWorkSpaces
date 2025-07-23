package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.Value;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS / TOS)
 */
public class SVM_DIV extends SVM_Instruction {

	public SVM_DIV() {
		this.opcode = SVM_Instruction.iDIV;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(tos == null) Util.IERR("DIV by zero: " + sos + " / 0");
		Value res = (tos == null)? null : tos.div(sos);
		RTStack.push(res, "SVM_DIV: " + tos + " / " + sos + " = " + res);
		BecGlobal.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "DIV      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DIV read(AttributeInputStream inpt) throws IOException {
		SVM_DIV instr = new SVM_DIV();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
