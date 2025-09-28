package iapx.instruction;

import iapx.CTStack.CTStack;
import iapx.CTStack.ProfileItem;
import iapx.qInstr.Q_POPK;
import iapx.util.Util;

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
		
//		IO.println("POP.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());
//		IO.println("POP.ofScode: TOS.type="+CTStack.TOS().type);
		int size = CTStack.TOS.type.size;
//		IO.println("POP.ofScode: TOS.type.size="+size);
		CTStack.pop();
//		CTStack.dumpStack("POP: ");
		qPOPKill(size);
//		if(CTStack.size() == 0) RTRegister.clearFreeRegs();
	}

	public static void qPOPKill(int aux) {
//		Qfunc.Qf2(Opcode.qPOPK,0,0,0,aux);
		new Q_POPK(aux);
	}

}
