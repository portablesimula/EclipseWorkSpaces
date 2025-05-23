package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.SVM_DUP;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_LOAD;

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
		RTAddress rtAddr = null;
		if(CTStack.TOS() instanceof AddressItem addr) {
			CTStack.pop(); CTStack.pushTempVAL(addr.type, 1, "DUP: ");
			rtAddr = new RTAddress(addr);
		}
		
//		CTStack.dumpStack("DUP.ofScode: ");
//		Global.PSEG.emit(new SVM_DUP(address), "DUP: ");
		Global.PSEG.emit(new SVM_DUP(rtAddr), "DUP: ");
//		Util.IERR("NOT IMPL");
	}

//	Visible Routine GQdup;
//	begin infix(MemAddr) opr; range(0:nregs) reg; range(0:MaxType) type;
//	      range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	      type:=TOS.type; nbyte:=TTAB(type).nbyte;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      case 0:K_Max (TOS.kind)
//	      when K_ProfileItem:
//	           Push(NewProfileItem(type,TOS qua ProfileItem.spc));
//	           TOS qua ProfileItem.nasspar:=TOS.suc qua ProfileItem.nasspar;
//	      when K_Address:
//	           opr:=GetTosSrcAdr;
//	           if TOS qua Address.ObjState <> NotStacked
//	           then
//	                reg:=GetBreg(opr);
//	                PreMindMask:=wOR(PreMindMask,uMask(reg));
//	                Qf1(qPUSHR,reg,cOBJ);
//	                if TOS qua Address.AtrState <> NotStacked
//	                then reg:=GetIreg(opr);
//	                     PreMindMask:=wOR(PreMindMask,uMask(reg));
//	                     Qf1(qPUSHR,reg,cVAL);
//	                endif;
//	           endif;
//	           if nbyte <= AllignFac then Qf4(qPUSHM,0,0,cTYP,nbyte,opr)
//	           elsif nbyte <= (3*AllignFac)
//	           then opr.rela.val:=opr.rela.val+nbyte-AllignFac;
//	                repeat if nbyte>AllignFac then PresaveOprRegs(opr) endif;
//	                   Qf4(qPUSHM,0,0,cTYP,AllignFac,opr); nbyte:=nbyte-AllignFac;
//	                while nbyte <> 0
//	                do opr.rela.val:=opr.rela.val-AllignFac endrepeat;
//	           else Qf4(qPUSHM,0,0,cTYP,nbyte,opr) endif;
//	           pushTempVAL(type);
//	      when K_Temp,K_Result: pushTempVAL(type); goto L;
//	      when K_Coonst: pushCoonst(type,TOS qua Coonst.itm); L:
//	           if nbyte=0 then IERR("CODER.GQdup-1") endif;
//	           if nbyte = 1
//	           then Qf1(qPOPR,qAL,cTYP); PreMindMask:=wOR(PreMindMask,uAL);
//	                Qf1(qPUSHR,qAL,cTYP); Qf1(qPUSHR,qAL,cTYP);
//	           elsif nbyte=2
//	           then Qf1(qPOPR,qAX,cTYP); PreMindMask:=wOR(PreMindMask,uEAX);
//	                Qf1(qPUSHR,qAX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	           elsif nbyte=4
//	           then Qf1(qPOPR,qECX,cTYP); PreMindMask:=wOR(PreMindMask,uECX);
//	                Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qECX,cTYP);
//	           elsif nbyte=8
//	           then Qf1(qPOPR,qEAX,cTYP); Qf1(qPOPR,qECX,cTYP);
//	                PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP); 
//	                PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	                Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qEAX,cTYP);
//	           elsif nbyte=12
//	           then Qf1(qPOPR,qEAX,cTYP);Qf1(qPOPR,qECX,cTYP);Qf1(qPOPR,qEDX,cTYP);
//	                PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP); 
//	                PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP); 
//	                PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	                Qf1(qPUSHR,qEDX,cTYP); Qf1(qPUSHR,qECX,cTYP);
//	                Qf1(qPUSHR,qEAX,cTYP);
//	           else opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	                Qf2(qMOV,0,qESI,cSTP,qESP); opr.sibreg:=bESI+iESI;
//	                Qf4(qPUSHM,0,0,cTYP,nbyte,opr);
//	           endif;
//	      otherwise IERR("Illegal use of dup(StackItem)");
//	      endcase;
//	end;

}
