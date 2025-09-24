package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;

//The value is pushed onto the operand stack.
public class SVM_PUSHLEN extends SVM_Instruction {
	
	public SVM_PUSHLEN() {
		this.opcode = SVM_Instruction.iPUSHLEN;
	}
	
	/**
	 * stack_instruction ::= pushlen
	 * 
	 * pushlen
	 * 
	 * push( VAL, SIZE, "temporary area.LENGTH" );
	 *
	 *
	 *	An implicit eval is performed.
	 *
	 *	The SIZE needed for the following save, that is the sum of the current value of ALLOCATED
	 *	and the number of object units, which is needed for SAVE-MARKS and possibly other
	 *	implementation-dependant information, is computed and the value is pushed onto the stack.
	 *
	 *	For optimisation purposes, it is set to nosize in case ALLOCATED = nosize (i.e. if the temporary
	 *	area is empty). In this case the accompaning save and corresponding restore will receive onone
	 *	as parameter.
	 *
	 *	An S-compiler may choose to skip code generation for the complete sequence pushlen, asscall,
	 *	call, and save in the case ALLOCATED = nosize. In that case the processing of restore is
	 *	changed, see below.	public static void ofScode() {
	 */
	@Override
	public void execute() {
		IntegerValue size = IntegerValue.of(Type.T_SIZE, RTStack.size());
		RTStack.push(size, "SVM_PUSHLEN");
//		System.out.println("SVM_PUSHLEN.execute:  SAVE-RESTORE " + size);
//		Util.IERR("");
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		return "PUSHLEN  ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSHLEN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHLEN;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHLEN(inpt);
	}

}
