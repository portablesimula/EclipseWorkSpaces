package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Relation;
import bec.value.BooleanValue;
import bec.value.Value;

/**
 * Operation COMPARE relation
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result onto the Runtime Stack.
 */
public class SVM_COMPARE extends SVM_Instruction {
	Relation relation;
	
	public SVM_COMPARE(Relation relation) {
		this.opcode = SVM_Instruction.iCOMPARE;
		this.relation = relation;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		boolean res = relation.compare(sos, tos);
		RTStack.push(BooleanValue.of(res), "SVM_COMPARE: " + tos + " " + relation + " " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}
	
	public String toString() {
		return "COMPARE  " + relation;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_COMPARE instr = new SVM_COMPARE(Relation.read(inpt));
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}
}
