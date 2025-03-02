package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
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
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		if(tos == null) Util.IERR("DIV by zero: " + sos + " / 0");
		Value res = (tos == null)? null : tos.div(sos);
		System.out.println("SVM_DIV: " + tos + " / " + sos + " = " + res);
		RTStack.push(res, "SVM_DIV: " + tos + " / " + sos + " = " + res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "DIV      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
	}

	public static SVM_DIV read(AttributeInputStream inpt) throws IOException {
		SVM_DIV instr = new SVM_DIV();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
