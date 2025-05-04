package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

//The value is pushed onto the operand stack.
public class SVM_SAVE extends SVM_Instruction {
	
	public SVM_SAVE() {
		this.opcode = SVM_Instruction.iSAVE;
	}
	
	/// save
	/// * force TOS value; check TOS type(OADDR);
	/// * pop;
	/// * remember stack;
	/// * purge stack;
	///
	/// TOS describes the address of a save-object. The size of this object is as determined by the
	/// preceding pushlen. The complete state of the stack is remembered (together with the values of
	/// ALLOCATED and MARKS) and the compilation continues with an empty stack.
	///
	/// Code is generated, which - if TOS.VALUE <> onone (see note below) - at run time will save the
	/// used part of the temporary area, and set the SAVE-MARKS attribute.
	///
	/// TOS is popped.
	@Override
	public void execute() {
		RTStack.saveStack();
//		Util.IERR("");
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		return "SAVE     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_SAVE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSAVE;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_SAVE(inpt);
	}

}
