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
 * Remove to items on the Runtime-Stack and push the value SOS + TOS
 * 
 * 
 * 	a  \  b  true   false
 *  true     true   false
 *  false    false  false
 */
public class SVM_AND extends SVM_Instruction {

	public SVM_AND() {
		this.opcode = SVM_Instruction.iAND;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.and(sos);
		RTStack.push(res, "SVM_AND: " + tos + " and " + sos + " = " + res);
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "AND      ";
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
		SVM_AND instr = new SVM_AND();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}


}
