package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Operation ADD
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos + tos) onto the Runtime Stack.
 * The addition operation depends on the types ....
 * 
 * INT   + INT   --> INT
 * GADDR + INT   --> GADDR
 * OADDR + INT   --> OADDR
 * REAL  + REAL  --> REAL
 * LREAL + LREAL --> LREAL 
 * 
 * Other combinations are illegal.
 */
public class SVM_ADD extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_ADD() {
		this.opcode = SVM_Instruction.iADD;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(DEBUG) {
			if(tos != null)	IO.println("SVM_ADD: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	IO.println("SVM_ADD: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			IO.println("SVM_ADD: " + sos + " + " + tos);
		}
		Value res = (sos == null)? tos : sos.add(tos);
		if(DEBUG) IO.println("SVM_ADD: " + sos + " + " + tos + " ==> " + res);
		RTStack.push(res, "SVM_ADD: " + tos + " + " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "ADD      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_ADD read(AttributeInputStream inpt) throws IOException {
		SVM_ADD instr = new SVM_ADD();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
