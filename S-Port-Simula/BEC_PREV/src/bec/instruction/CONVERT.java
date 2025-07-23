package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.virtualMachine.SVM_CONVERT;

public abstract class CONVERT extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public static void ofScode() {
//		CTStack.dumpStack("BEGIN CONVERT.ofScode: ");
		Type toType = Type.ofScode();
//		System.out.println("CONVERT: "+tos.getClass().getSimpleName());

		if(CTStack.TOS().type != toType) doConvert(toType);
		
//		CTStack.dumpStack("END CONVERT.ofScode: ");
//		Global.PSEG.dump("END CONVERT.ofScode: ");
//		Util.IERR("");
	}
	
	public static void GQconvert(Type totype) {
		CTStackItem TOS = CTStack.TOS();
		Type fromtype = TOS.type;
		if(totype != fromtype) {
			if(DEBUG) System.out.println("CONVERT.doConvert: " + TOS + " ==> " + totype);
			BecGlobal.PSEG.emit(new SVM_CONVERT(fromtype.tag, totype.tag), "");
			CTStack.pop(); CTStack.pushTempVAL(totype, 1, "CONVERT: ");
			TOS.type = totype;
			if(DEBUG) System.out.println("CONVERT.doConvert: " + totype + " ==> " + TOS);
		}
	}

	private static void doConvert(Type totype) {
		FETCH.doFetch("CONVERT: ");
		CTStackItem TOS = CTStack.TOS();
		Type fromtype = TOS.type;
		if(DEBUG) System.out.println("CONVERT.doConvert: " + TOS + " ==> " + totype);
		BecGlobal.PSEG.emit(new SVM_CONVERT(fromtype.tag, totype.tag), "");
		CTStack.pop(); CTStack.pushTempVAL(totype, 1, "CONVERT: ");
	}

}
