package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT;

public abstract class NOT extends Instruction {
	
	/**
	 * arithmetic_instruction ::= not
	 * 
	 * force TOS value; check TOS type(BOOL);
	 * 
	 * value(TOS) := not value(TOS);
	 * TOS is replaced by a description of the negated TOS value.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStackItem tos = CTStack.TOS();
	    
	    Type at = tos.type;
	    if(at != Type.T_BOOL) {
		    at = CTStack.arithType(at, Type.T_INT);
		    CTStack.checkTosArith();
		    if( at == Type.T_REAL || at == Type.T_LREAL) Util.IERR("CODER.GQnot-1");
	    }
		BecGlobal.PSEG.emit(new SVM_NOT(), "NOT: ");
		CTStack.pop();
	    CTStack.pushTempVAL(at, 1, "NOT: ");
	    
//		CTStack.dumpStack("NOT: ");
//		Global.PSEG.dump("NOT: ");
//		Util.IERR(""+this);
	}

}
