package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
 */
public class SVM_REFER extends SVM_Instruction {
	Type type;

	public SVM_REFER(Type type) {
		this.opcode = SVM_Instruction.iREFER;
		this.type = type;
	}

	@Override
	public void execute() {
//		Value tos = RTStack.pop().value();
////		Value res = (tos == null)? null : tos.neg();
//		Value res = (tos == null)? BooleanValue.of(true) : null;
////		System.out.println("SVM_NOT:  -" + tos + " = " + res);
//		RTStack.push(res, "SVM_NOT:  -" + tos + " = " + res);
		
		int gOfst = RTStack.popInt();
		ObjectAddress objadr = (ObjectAddress) RTStack.pop().value();
		RTAddress rtaddr = new RTAddress(objadr,gOfst);
		RTStack.push(rtaddr, "REFER: ");

		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "REFER    " + type;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_REFER(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iREFER;
		Type.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		type.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_REFER(inpt);
	}

}
