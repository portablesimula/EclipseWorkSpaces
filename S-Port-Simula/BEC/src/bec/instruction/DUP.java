package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.SVM_DUP;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_PUSH;

public abstract class DUP extends Instruction {
	
	/**
	 * stack_instruction ::= dup
	 * 
	 * push( TOS );
	 * force TOS value;
	 * 
	 * A duplicate of TOS is pushed onto the stack and forced into value mode.
	 */
	public static void ofScode() {
//		FETCH.doFetch("DUP");
//		CTStack.dumpStack("DUP.ofScode: ");
		CTStack.dup();
		
//		FETCH.doFetch("DUP");
		boolean address = false;
		RTAddress rtAddr = null;
		if(CTStack.TOS() instanceof AddressItem addr) {
			CTStack.pop(); CTStack.pushTempVAL(addr.type, 1, "DUP: ");
			address = true;
			rtAddr = new RTAddress(addr);
		}
		
//		CTStack.dumpStack("DUP.ofScode: ");
//		Global.PSEG.emit(new SVM_DUP(address), "DUP: ");
		Global.PSEG.emit(new SVM_DUP(rtAddr), "DUP: ");
//		Util.IERR("NOT IMPL");
	}

//	Visible Routine GQpop;
//	begin range(0:MaxWord) nbyte;
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
//	      Pop;
//	end;

}
