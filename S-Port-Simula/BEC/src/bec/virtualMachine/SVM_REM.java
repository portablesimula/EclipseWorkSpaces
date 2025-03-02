package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS / TOS)
 */
public class SVM_REM extends SVM_Instruction {

	public SVM_REM() {
		this.opcode = SVM_Instruction.iREM;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		Value res = (tos == null)? null : tos.mult(sos);
		System.out.println("SVM_MULT: " + tos + " + " + sos + " = " + res);
		RTStack.push(res, "SVM_MULT: " + tos + " + " + sos + " = " + res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "MULT     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(SVM_Instruction.iREM);
	}

	public static SVM_REM read(AttributeInputStream inpt) throws IOException {
		SVM_REM instr = new SVM_REM();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
