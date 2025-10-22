package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Relation;
import bec.value.BooleanValue;
import bec.value.Value;

/// Operation COMPARE relation
/// 
/// 		relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ..., result
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos relation tos.
/// Then the boolean 'result' is pushed onto the Runtime Stack.
/// 
/// 'tos' and 'sos' must be of the same arithmetic type, i.e. int, float or double,
/// or boolean, in which case: relation ::= ?eq | ?ne
///
public class SVM_COMPARE extends SVM_Instruction {
	private final Relation relation;
	
	public SVM_COMPARE(Relation relation) {
		this.opcode = SVM_Instruction.iCOMPARE;
		this.relation = relation;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		boolean res = relation.compare(sos, tos);
		RTStack.push(BooleanValue.of(res));
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
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_COMPARE instr = new SVM_COMPARE(Relation.read(inpt));
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}
}
