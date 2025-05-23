package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
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
			if(tos != null)	System.out.println("SVM_ADD: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	System.out.println("SVM_ADD: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			System.out.println("SVM_ADD: " + sos + " + " + tos);
//			RTUtil.dumpCurins();
		}
		Value res = (sos == null)? tos : sos.add(tos);
		if(DEBUG) System.out.println("SVM_ADD: " + sos + " + " + tos + " ==> " + res);
		RTStack.push(res, "SVM_ADD: " + tos + " + " + sos + " = " + res);
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "ADD      ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_ADD read(AttributeInputStream inpt) throws IOException {
		SVM_ADD instr = new SVM_ADD();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
