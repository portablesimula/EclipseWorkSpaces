package iAPX.instruction;

import iAPX.ctStack.CTStack;
import iAPX.ctStack.ProfileItem;
import iAPX.qInstr.Q_POPK;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Util;
import svm.virtualMachine.SVM_POPK;

public abstract class POP extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 * 
	 * Pop off TOS;
	 * This instruction is illegal if TOS is a profile description.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		if(CTStack.TOS instanceof ProfileItem) Util.IERR("Illegal pop of profileItem ");
		
//		System.out.println("POP.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());
//		System.out.println("POP.ofScode: TOS.type="+CTStack.TOS().type);
		int size = CTStack.TOS.type.size;
//		System.out.println("POP.ofScode: TOS.type.size="+size);
		CTStack.pop();
//		CTStack.dumpStack("POP: ");
		if(Option.GENERATE_Q_CODE) {
			qPOPKill(size);
		} else {
			Global.PSEG.emit(new SVM_POPK(size), "POPK: " + size);
		}
//		if(CTStack.size() == 0) RTRegister.clearFreeRegs();
	}

	public static void qPOPKill(int aux) {
//		Qfunc.Qf2(Opcode.qPOPK,0,0,0,aux);
		new Q_POPK(aux);
	}

}
