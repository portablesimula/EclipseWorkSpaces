package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS xor TOS
 * 
 *    XOR
 * 	a  \  b  true   false
 *  true     false  true
 *  false    true   false
 */
public class SVM_XOR extends SVM_Instruction {

	public SVM_XOR() {
		this.opcode = SVM_Instruction.iXOR;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? sos : tos.xor(sos);
		RTStack.push(res, "SVM_XOR: " + tos + " ^ " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "XOR      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_XOR read(AttributeInputStream inpt) throws IOException {
		SVM_XOR instr = new SVM_XOR();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
