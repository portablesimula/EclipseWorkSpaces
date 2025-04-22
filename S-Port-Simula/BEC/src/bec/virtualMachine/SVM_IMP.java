package bec.virtualMachine;

import bec.util.Global;
import bec.value.BooleanValue;
import bec.value.Value;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS imp TOS
 * 
 *    IMP
 * 	a  \  b  true   false
 *  true     true   false
 *  false    true   true
 *  
 */
public class SVM_IMP extends SVM_Instruction {

	public SVM_IMP() {
		this.opcode = SVM_Instruction.iIMP;
	}
	@Override
	public void execute() {
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
//		System.out.println("SVM_IMP.execute: " + sos + " imp " + tos);
		Value res = (sos == null)? BooleanValue.of(true) : sos.imp(tos);
//		System.out.println("SVM_IMP.execute: " + sos + " imp " + tos + " ==> " + res);
		RTStack.push(res, "SVM_IMP: " + sos + " imp " + tos + " = " + res);
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "IMP      ";
	}

}
