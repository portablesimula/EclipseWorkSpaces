package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.value.IntegerValue;


/// SVM-INSTRUCTION: PUSHLEN
/// 
///	  Runtime Stack
///		... →
///		..., size
///
///	The size of the Runtime stack is pushed onto the Runtime stack.
///
///	For optimisation purposes, if it is set to nosize the accompaning save
/// and corresponding restore will receive onone as parameter.
///
/// See: SVM_SAVE and SVM_RERSTORE
/// See also S-Port - Definition of S-code - sect. 7. INTERMEDIATE RESULTS.
///	
public class SVM_PUSHLEN extends SVM_Instruction {
	
	public SVM_PUSHLEN() {
		this.opcode = SVM_Instruction.iPUSHLEN;
	}
	
	@Override
	public void execute() {
		IntegerValue size = IntegerValue.of(Type.T_SIZE, RTStack.size());
		RTStack.push(size);
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
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHLEN(inpt);
	}

}
