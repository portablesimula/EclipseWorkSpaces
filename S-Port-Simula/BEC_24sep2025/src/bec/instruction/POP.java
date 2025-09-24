package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.TempItem;
import bec.util.Global;
import bec.util.Util;
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
	}

	public static void qPOPKill(int aux) {
		Global.PSEG.emit(new SVM_POPK(aux), "POPK: " + aux);
	}

	public static void GQpop() {
//	begin range(0:MaxWord) nbyte;
//	%+D   RST(R_GQpop);
//	      case 0:K_Max (TOS.kind)
//	      when K_Coonst,K_Temp,K_Result:
//	           nbyte:=TTAB(TOS.type).nbyte;
//	           if nbyte <= AllignFac then qPOPKill(nbyte)
//	           elsif nbyte <= (3*AllignFac)
//	           then repeat qPOPKill(AllignFac); nbyte:=nbyte-AllignFac;
//	                while nbyte<>0 do endrepeat;
//	           else qPOPKill(nbyte) endif;
//	      when K_Address:
//	           if TOS qua Address.AtrState <> NotStacked
//	           then qPOPKill(AllignFac) endif;
//	           if TOS qua Address.ObjState <> NotStacked
//	           then qPOPKill(AllignFac);
//	%-E             qPOPKill(2);
//	           endif;
//	      endcase;
		if(CTStack.TOS() instanceof AddressItem adr) {
			if(adr.atrState != AddressItem.NotStacked) qPOPKill(1);
			if(adr.objState != AddressItem.NotStacked) qPOPKill(1);
		} else if(CTStack.TOS() instanceof TempItem tmp) {
			int size = tmp.type.size;
			qPOPKill(size);
		} else Util.IERR("");
		CTStack.pop();
	}

}
