/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Scode;
import bec.value.Value;

/// SVM-INSTRUCTION: SHIFT opr
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos opr tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of type int.
/// 
/// The operation depends on the 'opr' parameter:
///	LSHIFTA: Signed Left Shift     << The left shift operator moves all bits by a given number of bits to the left.
/// LSHIFTL: Unsigned Left Shift   << The left shift operator moves all bits by a given number of bits to the left.
/// RSHIFTA: Signed Right Shift    >> The right shift operator moves all bits by a given number of bits to the right.
/// RSHIFTL: Unsigned Right Shift  >>> It is the same as the signed right shift, but the vacant leftmost position is filled with 0 instead of the sign bit.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_SHIFT.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_SHIFT extends SVM_Instruction {
	int instr;
	
	private final boolean DEBUG = false;

	public SVM_SHIFT(int instr) {
		this.opcode = SVM_Instruction.iSHIFT;
		this.instr = instr;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(DEBUG) {
			if(tos != null)	IO.println("SVM_SHIFT: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	IO.println("SVM_SHIFT: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			IO.println("SVM_SHIFT: " + sos + " " + Scode.edInstr(instr) + " " + tos);
		}
		Value res = (sos == null)? null : sos.shift(instr, tos);
		if(DEBUG)
			IO.println("SVM_SHIFT: " + sos + " " + Scode.edInstr(instr)+ " " + tos + " ==> " + res);
		RTStack.push(res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		switch(instr) {
			case Scode.S_LSHIFTA: return("LSHIFTA");
			case Scode.S_LSHIFTL: return("LSHIFTL");
			case Scode.S_RSHIFTA: return("RSHIFTA");
			case Scode.S_RSHIFTL: return("RSHIFTL");
		}
		return "SHIFT    ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		oupt.writeByte(instr);
	}

	public static SVM_SHIFT read(AttributeInputStream inpt) throws IOException {
		SVM_SHIFT instr = new SVM_SHIFT(inpt.readUnsignedByte());
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
