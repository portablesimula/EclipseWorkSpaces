package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
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
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.rem(sos);
		RTStack.push(res, "SVM_REM: " + tos + " + " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "REM      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeKind(SVM_Instruction.iREM);
	}

	public static SVM_REM read(AttributeInputStream inpt) throws IOException {
		SVM_REM instr = new SVM_REM();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
