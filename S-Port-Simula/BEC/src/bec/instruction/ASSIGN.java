package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_ADDREG;
import bec.virtualMachine.SVM_ASSIGN;
import bec.virtualMachine.SVM_PUSHC;

public abstract class ASSIGN extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * assign (dyadic)
	 * 
	 * - force TOS value;
	 * - check SOS ref; check types identical;
	 * - pop; pop;
	 * 
	 * Code is generated to transfer the value described by TOS to the location designated by SOS.
	 * This implies that the stack elements must be evaluated, and that any code generation involving
	 * TOS or SOS, that has been deferred for optimisation purposes, must take place before the
	 * assignment code is generated. SOS and TOS are popped from the stack.
	 */
	public static void ofScode() {
//		CTStack.dumpStack("ASSIGN.ofScode");
		CTStack.checkSosRef();
		CTStack.checkTypesEqual();
		CTStackItem tos = CTStack.pop();
		AddressItem sos = (AddressItem) CTStack.pop();
		
		// GetTosDstAdr
		int offset = 0;
		if(sos.withRemoteBase) {
//			System.out.println("ASSIGN.ofScode: sos="+sos);
			offset = sos.offset;
		}
		
		
		Global.PSEG.emit(new SVM_ASSIGN(tos.type.size(), offset), "ASSIGN: "); // Store into adr
//		Util.IERR("");
	}

//	Visible Routine GQassign;
//	begin infix(MemAddr) opr; infix(MemAddr) adr;
//	      range(0:MaxType) st,dt; range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	%+C   CheckSosRef; CheckTypesEqual;
//	      st:=TOS.type; dt:=TOS.suc.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQassign-1") endif;
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL); Pop; opr:=GetTosDstAdr;
//	              Qf3(qSTORE,0,qAL,cTYP,opr);
//	      when 2: GetTosAsBYT2(qAX); Pop; opr:=GetTosDstAdr;
//	              Qf3(qSTORE,0,qAX,cTYP,opr);
//	%+E   when 4: GetTosAsBYT4(qEAX); Pop; opr:=GetTosDstAdr;
//	%+E           Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0); Pop;
//	%+E           opr:=GetTosDstAdr;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX); Pop;
//	%+E           opr:=GetTosDstAdr;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	           if (TOS.kind=K_Address)
//	           then opr:=GetTosSrcAdr;
//	%+E             Qf3(qLOADA,0,qESI,cADR,opr);
//	                Pop; opr:=GetTosDstAdr;
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr); Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	           else -- tos.mode=m_val and tos.kind<>K_Content --
//	%+E             opr:=GetSosAddr(qEBX,qEDI);
//	                Qf4(qPOPM,0,qAL,cTYP,nbyte,opr); Pop;
//	                repeat while SosAdrNwk > 0
//	                do qPOPKill(AllignFac);
//	                   SosAdrNwk:=SosAdrNwk-1;
//	                endrepeat;
//	           endif;
//	      endcase;
//	      Pop;
//	end;

}
