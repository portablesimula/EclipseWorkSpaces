package bec.virtualMachine;

import bec.util.Type;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS imp TOS
 */
public class SVM_IMP extends SVM_Instruction {
	Type type;

	public SVM_IMP(Type type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "IMP      " + type;
	}

}
