package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.Value;

public class SVM_COMPARE extends SVM_Instruction {
	Relation relation;
	
	public SVM_COMPARE(Relation relation) {
		this.opcode = SVM_Instruction.iCOMPARE;
		this.relation = relation;
	}

	@Override
	public void execute() {
//		RTStack.callStackTop.dump("SVM_COMPARE-1: ");
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
//		System.out.println("SVM_COMPARE: " + sos + " " + relation + " " + tos);
		
//		Util.IERR("USE relation.compare(tos, relation, sos)");
//		Value res = (tos == null)? sos : tos.and(sos);
//		boolean res = relation.compare(tos, sos);
		boolean res = relation.compare(sos, tos);
		
//		boolean res = false;
//		if(tos != null) {
//			tos.compare(relation.relation, sos);
//		} else if(sos != null) {
//			sos.compare(relation.not().relation, tos);
//		} else {
//			switch(relation.relation) {
//				case Scode.S_LT: res = /* 0 < 0  */ false; break;
//				case Scode.S_LE: res = /* 0 <= 0 */ true; break;
//				case Scode.S_EQ: res = /* 0 == 0 */ true; break;
//				case Scode.S_GE: res = /* 0 >= 0 */ true; break;
//				case Scode.S_GT: res = /* 0 > 0  */ false; break;
//				case Scode.S_NE: res = /* 0 != 0 */ false; break;
//			}
//		}
		
//		System.out.println("SVM_COMPARE: " + sos + " " + relation + " " + tos + " ==> " + res);
		RTStack.push(BooleanValue.of(res), "SVM_COMPARE: " + tos + " " + relation + " " + sos + " = " + res);
		Global.PSC.ofst++;
//		RTStack.callStackTop.dump("SVM_AND-2: ");
//		Util.IERR(""+res);
	}
	
	public String toString() {
		return "COMPARE  " + relation;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_COMPARE instr = new SVM_COMPARE(Relation.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}
}
