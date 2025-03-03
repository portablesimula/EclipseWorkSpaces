package PREV;

import bec.compileTimeStack.CTStack;
import bec.instruction.Instruction;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_SWITCH;

public abstract class SWITCH extends Instruction {
	
	/**
	 * forward_jump ::= switch switch:newtag size:number
	 */
	private SWITCH() {}
	private static void ofScode() {
		System.out.println("SWITCH.ofScode: ");
		Tag tag = Tag.ofScode();
		int size = Scode.inNumber();
//		if(size >= MxpSdest) Util.ERROR("Too large Case-Statement");
		MemAddr[] DESTAB = new MemAddr[size];
		CTStack.checkTosInt();
//        if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//        a:=sw.swtab;
		int qEBX = 1; // MÅ RETTES
		CTStack.getTosAdjustedIn86(qEBX);
		CTStack.pop();
      	Global.PSEG.emit(new SVM_SWITCH(DESTAB), "");
//     	Global.PSEG.dump("SWITCH: ");
	}

}
