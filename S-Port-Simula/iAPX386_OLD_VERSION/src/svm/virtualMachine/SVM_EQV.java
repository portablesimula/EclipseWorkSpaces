package svm.virtualMachine;

import bec.util.Global;
import svm.value.Value;

/**
 * Operation EQV
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two BOOL values from the Runtime Stack and
 * push the result (sos EQV tos) onto the Runtime Stack.
 *
 * The 'a EQV b' function is defined by:
 *  
 * 	a EQV b  true   false
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
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "EQV      ";
	}

}
