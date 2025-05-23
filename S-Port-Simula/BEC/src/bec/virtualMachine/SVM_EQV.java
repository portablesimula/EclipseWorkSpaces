package bec.virtualMachine;

import bec.util.Global;
import bec.value.Value;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS eqv TOS
 * 
 *    EQV
 * 	a  \  b  true   false
 *  true     true   false
 *  false    false  true
 *  
 */
public class SVM_EQV extends SVM_Instruction {

	public SVM_EQV() {
		this.opcode = SVM_Instruction.iEQV;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.eqv(sos);
		RTStack.push(res, "SVM_AND: " + tos + " eqv " + sos + " = " + res);
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "EQV      ";
	}

}
