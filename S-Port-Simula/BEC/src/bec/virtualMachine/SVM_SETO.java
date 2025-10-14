package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;

/**
 * Code is generated that inserts the value described by TOS into the pointer variable refered by
 * SAVE-OBJECT and SAVE-INDEX. Note that t-seto does not update SAVE-INDEX.
 * TOS is popped.
 */
/// Operation GETO
/// 
/// 	Runtime Stack
/// 	   ..., oaddr →
/// 	   ...
///
/// An oaddr is popped of the Runtime stack and inserted
/// into the pointer variable refered by SAVE-OBJECT and SAVE-INDEX.
/// Note that t-seto does not update SAVE-INDEX.
///
/// See: SVM_INITO and SVM_GETO.
///
public class SVM_SETO extends SVM_Instruction {

	public SVM_SETO() {
		this.opcode = SVM_Instruction.iSETO;
	}

	@Override
	public void execute() {
		ObjectAddress oaddr = RTStack.popOADDR();
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
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_SETO instr = new SVM_SETO();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
