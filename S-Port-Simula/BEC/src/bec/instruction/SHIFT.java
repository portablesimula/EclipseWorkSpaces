package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_SHIFT;

public abstract class SHIFT extends Instruction {
	int instr;
	
	private static final boolean DEBUG = false;

	/**
	 * Bitwise Shift
	 * 
	 * arithmetic_instruction ::= add
	 * 
	 * add, sub, mult, div (dyadic)
	 * 
	 * force TOS value; check TOS type(INT,REAL,LREAL);
	 * force SOS value; check SOS type(INT,REAL,LREAL);
	 * check types equal;
	 * 
	 * pop; pop;
	 * push( VAL, type, "value(SOS) op value(TOS)" );
	 * 
	 * SOS and TOS are replaced by a description of the value of the application of the operator. The
	 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
	 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic. 
	 */
	public static void ofScode(int instr) {
		CTStack.forceTosValue();			
		CTStackItem tos = CTStack.TOS();
		CTStackItem sos = CTStack.SOS();
//	    Type at = CTStack.arithType(tos.type, sos.type);

		if(DEBUG) {
			if(tos != null)	IO.println("SHIFT: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	IO.println("SHIFT: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			IO.println("SHIFT: " + sos + " " + Scode.edInstr(instr) + " " + tos);
//			Util.IERR("");
		}

	    Global.PSEG.emit(new SVM_SHIFT(instr), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTempVAL(tos.type, 1, "SHIFT: " + Scode.edInstr(instr));

//		Util.IERR(""+Scode.edInstr(instr));
	}

}
