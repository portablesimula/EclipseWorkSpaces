package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.IntegerValue;
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
//		Value res = (tos == null)? BooleanValue.of(true) : null;
		Value res = null;
		if(tos == null) res = BooleanValue.of(true);
		else {
			if(tos instanceof BooleanValue bval) {
				res = BooleanValue.of(! bval.value);
			}
			else if(tos instanceof IntegerValue ival) {
				res = IntegerValue.of(Type.T_INT, ~ ival.value);
			} else Util.IERR("");
//			boolean bval = ((BooleanValue)tos).value;
//			if(! bval) res = BooleanValue.of(true);
		}
//		System.out.println("SVM_NOT:  not " + tos + " ==> " + res);
		RTStack.push(res, "SVM_NOT:  not " + tos + " ==> " + res);
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
