package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.Value;

/// SVM-INSTRUCTION: REM
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos rem tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of type int.
/// 
/// Remainder, defined as "sos - (sos // tos) * tos".
///
/// Note that SIMULA demands "truncation towards zero" for integer division.
/// Thus (except for a zero remainder) the result of rem has the same sign
/// as the result of the division. In more formal terms:
///
///		i div j = sign(i/j) * entier(abs(i/j))
///		i rem j = i - (i div j) * j
///
/// where '/' represents the exact mathematical division within the space of real numbers.
///
public class SVM_REM extends SVM_Instruction {

	public SVM_REM() {
		this.opcode = SVM_Instruction.iREM;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.rem(sos);
		RTStack.push(res);
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
