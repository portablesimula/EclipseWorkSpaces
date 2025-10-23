package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_CONVERT;

public abstract class CONVERT extends Instruction {

	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public static void ofScode() {
		Type toType = Type.ofScode();
		CTStack.forceTosValue();
		doConvert(toType);
	}
	
	public static void doConvert(Type totype) {
		CTStackItem TOS = CTStack.TOS();
		Type fromtype = TOS.type;
		if(totype != fromtype) {
			Global.PSEG.emit(new SVM_CONVERT(fromtype.tag, totype.tag));
			CTStack.pop(); CTStack.pushTempVAL(totype, 1);
			TOS.type = totype;
		}
	}

}
