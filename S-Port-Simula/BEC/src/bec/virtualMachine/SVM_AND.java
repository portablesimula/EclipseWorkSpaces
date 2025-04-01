package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.BooleanValue;
import bec.value.Value;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS + TOS
 * 
 * 
 * 	a  \  b  true   false
 *  true     true   false
 *  false    false  false
 */
public class SVM_AND extends SVM_Instruction {
	Type type;

	public SVM_AND(Type type) {
		this.opcode = SVM_Instruction.iAND;
		this.type = type;
	}

	@Override
	public void execute() {
//		RTStack.callStackTop.dump("SVM_AND-1: ");
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
//		Value res = null;
//		if(tos instanceof BooleanValue tbval) {
//			boolean tval = (tos == null)? false : tbval.value;
//			boolean sval = (sos == null)? false : ((BooleanValue)sos).value;
//		}
		Value res = (tos == null)? null : tos.and(sos);
//		System.out.println("SVM_AND: " + sos + " and " + tos + " ==> " + res);
		RTStack.push(res, "SVM_AND: " + tos + " and " + sos + " = " + res);
		Global.PSC.ofst++;
//		RTStack.callStackTop.dump("SVM_AND-2: ");
//		Util.IERR(""+res);
	}
	
	@Override	
	public String toString() {
		return "AND      " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		type.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_AND instr = new SVM_AND(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}


}
