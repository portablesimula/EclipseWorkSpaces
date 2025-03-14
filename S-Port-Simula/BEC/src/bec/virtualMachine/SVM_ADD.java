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
//		RTStack.curFrame.dump("SVM_ADD-1: ");
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		Value res = (tos == null)? sos : tos.add(sos);
//		System.out.println("SVM_ADD: " + tos + " + " + sos + " = " + res);
		if(DEBUG) System.out.println("SVM_ADD: " + sos + " + " + tos + " ==> " + res);
		RTStack.push(res, "SVM_ADD: " + tos + " + " + sos + " = " + res);
		Global.PSC.ofst++;
//		RTStack.curFrame.dump("SVM_ADD-2: ");
//		Util.IERR(""+res);
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
