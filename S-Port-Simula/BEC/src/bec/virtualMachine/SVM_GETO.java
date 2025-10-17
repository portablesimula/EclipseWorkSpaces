package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;

/// Operation GETO
/// 
/// 	Runtime Stack
/// 	   ... →
/// 	   ..., next
///
/// Push 'next' pointer of an earlier registered save-object.
/// In case the 'next' being onone, no more pointers and the scan 
/// of the save-object should be terminated.
///
/// If more pointers the SAVE-INDEX is updated.
/// In case the "next" pointer is onone, the pointer is skipped.
///
/// See: SVM_INITO and SVM_SETO.
public class SVM_GETO extends SVM_Instruction {

	public SVM_GETO() {
		this.opcode = SVM_Instruction.iGETO;
	}

	@Override
	public void execute() {
		ObjectAddress next = SVM_INITO.get();
		RTStack.push(next, "GETO: ");
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "GETO     ";
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
		SVM_GETO instr = new SVM_GETO();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
