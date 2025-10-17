package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ProgramAddress;

/// Operation GOTO
/// 
/// 	Runtime Stack
/// 	   paddr →
/// 	   - empty
///
/// The paddr is popped of the Runtim stack.
/// Then Program Sequence Control PCS := paddr
///
public class SVM_GOTO extends SVM_Instruction {

	public SVM_GOTO() {
		this.opcode = SVM_Instruction.iGOTO;
	}

	@Override
	public void execute() {
		ProgramAddress target = (ProgramAddress) RTStack.pop();
		Global.PSC = target.copy();
		RTStack.checkStackEmpty();
	}
	
	@Override	
	public String toString() {
		return "GOTO     ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	protected SVM_GOTO(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iGOTO;
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTO(inpt);
	}

}
