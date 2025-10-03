package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ProfileItem;
import bec.util.Global;
import bec.util.Util;
import bec.virtualMachine.DELETED_RTRegister;
import bec.virtualMachine.SVM_POPK;

public abstract class POP extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 * 
	 * Pop off TOS;
	 * This instruction is illegal if TOS is a profile description.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		if(CTStack.TOS() instanceof ProfileItem) Util.IERR("Illegal pop of profileItem ");
		
//		IO.println("POP.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());
//		IO.println("POP.ofScode: TOS.type="+CTStack.TOS().type);
		int size = CTStack.TOS().type.size();
//		IO.println("POP.ofScode: TOS.type.size="+size);
		CTStack.pop();
//		CTStack.dumpStack("POP: ");
		Global.PSEG.emit(new SVM_POPK(size), "POPK: " + size);
//		if(CTStack.size() == 0) RTRegister.clearFreeRegs();
	}

}
