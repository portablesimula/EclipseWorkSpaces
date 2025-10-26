/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.value.Value;

/// SVM-INSTRUCTION: JUMP paddr
/// 
/// Unconditional Jump to paddr.
/// The Program Sequence Control PCS := paddr
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_JUMP.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_JUMP extends SVM_Instruction {
	protected ProgramAddress destination;

	public SVM_JUMP(ProgramAddress destination) {
		this.opcode = SVM_Instruction.iJUMP;
		this.destination = destination;
	}
	
	public void setDestination(ProgramAddress destination) {
		if(this.destination != null) Util.IERR("");
		this.destination = destination;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_JUMPIF: ");
//		RTStack.checkStackEmpty();
		Global.PSC = destination.copy();
	}
	
	@Override	
	public String toString() {
		return "JUMP     " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	protected SVM_JUMP(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iJUMP;
		this.destination = (ProgramAddress) Value.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		destination.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_JUMP(inpt);
	}

}
