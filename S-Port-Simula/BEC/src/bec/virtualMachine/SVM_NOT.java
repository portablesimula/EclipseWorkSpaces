package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.BooleanValue;
import bec.value.Value;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
 */
public class SVM_NOT extends SVM_Instruction {
	Type type;

	public SVM_NOT(Type type) {
		this.opcode = SVM_Instruction.iNOT;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop().value();
//		Value res = (tos == null)? null : tos.neg();
		Value res = (tos == null)? new BooleanValue(true) : null;
//		System.out.println("SVM_NOT:  -" + tos + " = " + res);
		RTStack.push(res, "SVM_NOT:  -" + tos + " = " + res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "NOT      " + type;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOT(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOT;
		Type.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		type.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOT(inpt);
	}

}
