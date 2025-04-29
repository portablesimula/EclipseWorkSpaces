package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_EQV;

public abstract class EQV extends Instruction {
	
	/**
	 * arithmetic_instruction ::= eqv
	 * 
	 * and, or, xor, imp, eqv (dyadic)
	 * 
	 * force TOS value; check TOS type(BOOL);
	 * force SOS value; check SOS type(BOOL);
	 * pop; pop;
	 * push( VAL, BOOL, "value(SOS) op value(TOS)" );
	 * 
	 * TOS and SOS are replaced by a description of the result of applying the operator.
	 * Note that SOS is the left operand.
	 */
	public static void ofScode() {
		CTStackItem tos = CTStack.TOS();
	    
	    Type at = tos.type;
	    if(at != Type.T_BOOL) {
			CTStackItem sos = CTStack.SOS();
		    at = CTStack.arithType(at, sos.type);
		    CTStack.checkTosArith(); CTStack.checkSosArith();
		    CTStack.checkSosValue(); CTStack.checkTypesEqual();
		    if( at == Type.T_REAL || at == Type.T_LREAL) Util.IERR("");
	    } else {
	    	CTStack.checkSosValue(); CTStack.checkSosType(Type.T_BOOL);
	    }
	    
		Global.PSEG.emit(new SVM_EQV(), "EQV: ");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(at, 1, "EQV: ");
	}	

}
