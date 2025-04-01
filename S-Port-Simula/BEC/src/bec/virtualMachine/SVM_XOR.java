package bec.virtualMachine;

import bec.util.Global;
import bec.util.Type;
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
	Type type;

	public SVM_XOR(Type type) {
//		this.opcode = SVM_Instruction.iXOR;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		Value res = (tos == null)? sos : tos.xor(sos);
//		System.out.println("SVM_XOR: " + tos + " ^ " + sos + " = " + res);
		RTStack.push(res, "SVM_XOR: " + tos + " ^ " + sos + " = " + res);
		Global.PSC.ofst++;
//		Util.IERR(""+res);
	}
	
	@Override	
	public String toString() {
		return "XOR      ";
	}

}
