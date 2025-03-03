package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_CONVERT;

public class CONVERT extends PREV_Instruction {
	int toType;
	
	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public CONVERT() {
		toType = Scode.inTag();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		StackItem tos = CTStack.takeTOS();
//		if(tos instanceof Coonst) {
//			ConvConst(totype);
//		} else {
			Global.PSEG.emit(new SVM_CONVERT(toType), "");
//		}

		CTStack.pushTemp(toType);

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "CONVERT " + Scode.edTag(toType);
	}
	

}
