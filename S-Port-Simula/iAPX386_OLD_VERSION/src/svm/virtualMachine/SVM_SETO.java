package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.dataAddress.DataAddress;

/**
 * Code is generated that inserts the value described by TOS into the pointer variable refered by
 * SAVE-OBJECT and SAVE-INDEX. Note that t-seto does not update SAVE-INDEX.
 * TOS is popped.
 */
public class SVM_SETO extends SVM_Instruction {

	public SVM_SETO() {
		this.opcode = SVM_Instruction.iSETO;
	}

	@Override
	public void execute() {
		DataAddress oaddr = RTStack.popOADDR();
		SVM_INITO.set(oaddr);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "SETO      ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_SETO instr = new SVM_SETO();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
