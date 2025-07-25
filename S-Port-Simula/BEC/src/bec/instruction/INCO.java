package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_INCO;

public abstract class INCO extends Instruction {
	
	/**
	 * addressing_instruction ::= inco
	 * 
	 * inco, deco (dyadic)
	 * force TOS value; check TOS type(SIZE);
	 * force SOS value; check SOS type(OADDR);
	 * pop; pop;
	 * push( VAL, OADDR, "value(SOS) +/- value(TOS)" );
	 * 
	 * The two top elements are replaced by a descriptor of the object address RESULT defined
	 * through the equation
	 * dist(RESULT,value(SOS)) = +/- value(TOS)
	 * where + corresponds to inco and - to deco.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_SIZE); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
		Global.PSEG.emit(new SVM_INCO(), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(Type.T_OADDR, 1, "INCO: ");
	}

}
