package bec.virtualMachine;

import bec.util.Global;
import bec.value.BooleanValue;
import bec.value.Value;

/// SVM-INSTRUCTION: IMP
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos and tos.
/// Then the 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the same type, boolean or int.
/// 
/// The 'imp' operation is defined by the following matrix:
/// 
///       IMP
/// 	a  \  b  true   false
///     true     true   false
///     false    true   true
///
public class SVM_IMP extends SVM_Instruction {

	public SVM_IMP() {
		this.opcode = SVM_Instruction.iIMP;
	}
	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
//		IO.println("SVM_IMP.execute: " + sos + " imp " + tos);
		Value res = (sos == null)? BooleanValue.of(true) : sos.imp(tos);
//		IO.println("SVM_IMP.execute: " + sos + " imp " + tos + " ==> " + res);
		RTStack.push(res);
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "IMP      ";
	}

}
