package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_PUSHLEN;

public abstract class PUSHLEN extends Instruction {
	
	/**
	 * stack_instruction ::= pushlen
	 * 
	 * pushlen
	 * 
	 * push( VAL, SIZE, "temporary area.LENGTH" );
	 *
	 *
	 *	An implicit eval is performed.
	 *
	 *	The SIZE needed for the following save, that is the sum of the current value of ALLOCATED
	 *	and the number of object units, which is needed for SAVE-MARKS and possibly other
	 *	implementation-dependant information, is computed and the value is pushed onto the stack.
	 *
	 *	For optimisation purposes, it is set to nosize in case ALLOCATED = nosize (i.e. if the temporary
	 *	area is empty). In this case the accompaning save and corresponding restore will receive onone
	 *	as parameter.
	 *
	 *	An S-compiler may choose to skip code generation for the complete sequence pushlen, asscall,
	 *	call, and save in the case ALLOCATED = nosize. In that case the processing of restore is
	 *	changed, see below.	public static void ofScode() {
	 */
	public static void ofScode() {
		CTStack.pushTempVAL(Type.T_SIZE, 1, "PUSHLEN: ");
		BecGlobal.PSEG.emit(new SVM_PUSHLEN(), "");
//		Util.IERR("NOT IMPL");
	}

//    when S_PUSHLEN: itm.int:=Pushlen;
//    if not SkipProtect
//    then pushCoonst(T_SIZE,itm);
//         Qf2(qPUSHC,0,qEAX,cVAL,itm.wrd);
//    endif;

}
