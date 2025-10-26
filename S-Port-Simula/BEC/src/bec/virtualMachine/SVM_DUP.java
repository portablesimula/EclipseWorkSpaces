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

/// SVM-INSTRUCTION: DUP n
/// 
///	  Runtime Stack
///		..., value1, value2, ... , value'n →
///		..., value1, value2, ... , value'n, value1, value2, ... , value'n
///
/// A duplicate of the 'n' top values are is pushed onto the Runtime stack.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_DUP.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_DUP extends SVM_Instruction {
	private final int n;
	
	public SVM_DUP(int n) {
		this.opcode = SVM_Instruction.iDUP;
		this.n = n;
	}
	
	@Override
	public void execute() {
		if(n == 1) {
			RTStack.push(RTStack.peek());				
		} else {
			RTStack.dup(n);
		}
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "DUP      " + n;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(n);
	}

	public static SVM_DUP read(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		SVM_DUP instr = new SVM_DUP(n);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
