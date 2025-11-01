/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.instruction;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import svm.RTStack;
import svm.value.ProgramAddress;

/// SVM-INSTRUCTION: GOTO
/// 
/// 	Runtime Stack
/// 	   paddr →
/// 	   - empty
///
/// The paddr is popped of the Runtim stack.
/// Then Program Sequence Control PCS := paddr
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_GOTO.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
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
		oupt.writeByte(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTO(inpt);
	}

}
