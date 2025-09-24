package svm.virtualMachine;

import bec.util.Global;
import svm.value.BooleanValue;
import svm.value.Value;

/**
 * Operation IMP
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two BOOL values from the Runtime Stack and
 * push the result (sos IMP tos) onto the Runtime Stack.
 *
 * The 'a IMP b' function is defined by:
 *  
 * 	a IMP b  true   false
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
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
//		System.out.println("SVM_IMP.execute: " + sos + " imp " + tos);
		Value res = (sos == null)? BooleanValue.of(true) : sos.imp(tos);
//		System.out.println("SVM_IMP.execute: " + sos + " imp " + tos + " ==> " + res);
		RTStack.push(res, "SVM_IMP: " + sos + " imp " + tos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "IMP      ";
	}

}
