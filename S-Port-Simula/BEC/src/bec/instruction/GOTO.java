package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.CTStackItem;
import bec.segment.ProgramSegment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_GOTO;
import bec.virtualMachine.SVM_JUMP;

public abstract class GOTO extends Instruction {
	
	/**
	 * goto_instruction ::= goto
	 * 
	 * goto_statement ::= goto
	 * 
	 * goto
	 * force TOS value; check TOS type(PADDR);
	 * pop; check stack empty;
	 * 
	 * TOS is popped and instructions generated to perform the control transfer.
	 */
	public static void ofScode() {
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_PADDR);
		CTStackItem TOS = CTStack.TOS();
		
//		IO.println("GOTO.ofScode: TOS="+TOS.getClass().getSimpleName()+"  "+TOS);
		
		if(TOS instanceof ConstItem citm) {
			ProgramAddress padr = (ProgramAddress) citm.value;
			
//			IO.println("GOTO.ofScode: padr="+padr);
//			if(padr != null) {
//				ProgramSegment seg = (ProgramSegment) padr.segment();
//				seg.dump("GOTO.ofScode: ");
//				SVM_JUMP instr = (SVM_JUMP) seg.instructions.get(padr.ofst);
//				Util.IERR("SJEKK DETTE");
//			} else {
//				citm.value = Global.PSEG.nextAddress();
//			}
			
//			Global.PSEG.emit(new SVM_JUMP(padr), "GOTO: " + citm);
			Global.PSEG.emit(new SVM_GOTO(), "GOTO: " + citm);
			CTStack.pop();
		} else {
			FETCH.doFetch("GOTO: "); // Force TOS Value
			Global.PSEG.emit(new SVM_GOTO(), "GOTO-TOS: ");
			CTStack.pop();
//	%+E                  Qf2(qRET,0,0,0,0);
		}
		CTStack.checkStackEmpty();

//		Util.IERR("NOT IMPL");
	}

}
