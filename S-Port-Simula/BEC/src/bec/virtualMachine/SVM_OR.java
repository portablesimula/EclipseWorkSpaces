package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * SOS and TOS are replaced by the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items from the Runtime-Stack and push the value SOS or TOS
 */
public class SVM_OR extends SVM_Instruction {

	public SVM_OR() {
		this.opcode = SVM_Instruction.iOR;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		Value res = (tos == null)? sos : tos.or(sos);
//		System.out.println("SVM_ADD: " + tos + " + " + sos + " = " + res);
		RTStack.push(res, "SVM_ADD: " + tos + " + " + sos + " = " + res);
		Global.PSC.ofst++;
//		Util.IERR(""+res);
	}
	
	@Override	
	public String toString() {
		return "OR       ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
	}

	public static SVM_OR read(AttributeInputStream inpt) throws IOException {
		SVM_OR instr = new SVM_OR();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
