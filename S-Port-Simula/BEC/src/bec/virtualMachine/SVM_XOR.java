package bec.virtualMachine;

import bec.util.Type;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS xor TOS
 */
public class SVM_XOR extends SVM_Instruction {
	Type type;

	public SVM_XOR(Type type) {
//		this.opcode = SVM_Instruction.iXOR;
	}
	
	@Override	
	public String toString() {
		return "XOR      ";
	}

}
