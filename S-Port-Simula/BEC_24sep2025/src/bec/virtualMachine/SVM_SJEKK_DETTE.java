package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

public class SVM_SJEKK_DETTE extends SVM_Instruction {

	public SVM_SJEKK_DETTE() {
		this.opcode = SVM_Instruction.iSJEKK_DETTE;
	}

	@Override
	public void execute() {
		Util.IERR("SJEKK DETTE");
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "SJEKK_DETTE ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_SJEKK_DETTE read(AttributeInputStream inpt) throws IOException {
		SVM_SJEKK_DETTE instr = new SVM_SJEKK_DETTE();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
