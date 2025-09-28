package iAPX.instruction;

import iAPX.ctStack.CTStack;
import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.qInstr.Q_LOADA;
import iAPX.qInstr.Q_POPM;
import iAPX.qInstr.Q_STORE;
import iAPX.util.Option;
import iAPX.util.Reg;
import iAPX.util.Scode;
import iAPX.util.Type;
import iAPX.util.Util;

public abstract class RUPDATE extends Instruction {
	
	private static final boolean DEBUG = false;
	
	/**
	 * assign_instruction : =  assign | update | rupdate
	 * 
	 * rupdate (dyadic)
	 * 
	 * check TOS ref;
	 * force SOS value; check types identical;
	 * pop;
	 * 
	 * This instruction (“reverse update”) works almost like update with the sole exception that the
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.
	 */
	public static void ofScode() {
		if(Option.GENERATE_Q_CODE) {
			if(Scode.nextByte() == Scode.S_POP) {
				Scode.inputInstr();	GQrassign(); // -- can't use skip here!
			} GQrupdate();
		} else {
//			if(DEBUG) CTStack.dumpStack("RUPDATE.ofScode: ");
//			CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
//			Address adr = (Address) CTStack.pop();
//			CTStack.forceTosValue();			
//			if(DEBUG) {
//				IO.println("RUPDATE.ofScode: adr="+adr.getClass().getSimpleName()+"  "+adr);
//				IO.println("RUPDATE.ofScode: sos="+CTStack.TOS);
//	//			Util.IERR("");
//	//			CTStack.dumpStack("RUPDATE-2: ");
//			}
//			Global.PSEG.emit(new SVM_STORE(adr.objadr.addOffset(adr.offset), adr.xReg, adr.size), "RUPDATE: "); // Store into adr
		}
	}

	public static void GQrupdate() { //(MemAddr opr) {
//	      range(0:MaxWord) nbyte; range(0:MaxType) st,dt;
//	%-E   range(0:nregs) segreg;
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		Type st = CTStack.TOS.suc.type; Type dt = CTStack.TOS.type; int nbyte = dt.size;
//	      if dt<=T_MAX then cTYP = cTYPE(dt) else cTYP = cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQrupdate-1") endif;
		MemAddr opr = MemAddr.getTosDstAdr(); CTStack.pop();
//	      if TOS.kind=K_Address
//	      then
//	%-E        Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E        opr.sbireg = bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E        Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg = bEDI+iEDI;
//	           opr.kind = reladr; opr.rela.val = 0; opr.segmid.val = 0;
//	      endif;
//	%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL);
//	              PreMindMask = wOR(PreMindMask,uAL); Qf1(qPUSHR,qAL,cTYP);
//	              Qf3(qSTORE,0,qAL,cTYP,opr)
//	      when 2: GetTosAsBYT2(qAX);
//	              PreMindMask = wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	              Qf3(qSTORE,0,qAX,cTYP,opr)
//	---   when 4: GetTosAsBYT4(qCX,qAX);
//	---           PreMindMask = wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---           PreMindMask = wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	---           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	---           opr.rela.val = opr.rela.val+AllignFac;
//	---           Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E   when 4: GetTosAsBYT4(qAX,qCX);
//	%-E           PreMindMask = wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E   when 6: GetTosValueIn86R3(qAX,qCX,qDX);
//	%-E           PreMindMask = wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E   when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI);
//	%-E           PreMindMask = wOR(PreMindMask,uSI); Qf1(qPUSHR,qSI,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask = wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E           opr.rela.val = opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qSI,cTYP,opr);
//	%+E   when 4: GetTosAsBYT4(qEAX);
//	%+E           PreMindMask = wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           Qf3(qSTORE,0,qEAX,cTYP,opr)
//	%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	%+E           PreMindMask = wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask = wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val = opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//	%+E           PreMindMask = wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP);
//	%+E           PreMindMask = wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask = wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val = opr.rela.val+AllignFac;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E           opr.rela.val = opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	           if TOS.kind <> K_Address
//	           then
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E             opr.sbireg = bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg = bEDI+iEDI;
//	                opr.kind = reladr; opr.rela.val = 0; opr.segmid.val = 0;
//	           endif;
//	%-E        if bAND(opr.sbireg,oSREG) <> oES
//	%-E        then segreg = GetSreg(opr);
//	%-E             PreMindMask = wOR(PreMindMask,uMask(segreg)); 
//	%-E             Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        endif;
//	           GQfetch;
//	%-E        Qf2(qMOV,0,qSI,cSTP,qSP); Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%+E        Qf2(qMOV,0,qESI,cSTP,qESP);
//	%-E        Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E        Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	           Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	      endcase;
		Util.IERR("");
	}

	public static void GQrassign() {
//	begin infix(MemAddr) opr; range(0:MaxWord) nbyte;
//	      range(0:MaxType) st,dt; range(0:MaxByte) cTYP;
		IO.println("RUPDATE.GQrassign: TOS=" + CTStack.TOS + ", SOS=" + CTStack.TOS.suc);
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		Type st = CTStack.TOS.suc.type; Type dt = CTStack.TOS.type; int size = dt.size;
//	      if dt<=T_MAX then cTYP = cTYPE(dt) else cTYP = cANY endif;
		if(size == 0) Util.IERR("CODER.GQrassign-1");
		MemAddr opr = MemAddr.getTosDstAdr(); CTStack.pop();;
		if(CTStack.TOS.kind == Kind.K_Address) {
//			Qf3(qLOADA,0,qEDI,cADR,opr);
			new Q_LOADA(Reg.qEDI, opr);
//			opr.sibreg = bEDI+iEDI;
//	        opr.kind = reladr; opr.rela.val = 0; opr.segmid.val = 0;
			opr = MemAddr.ofRelAddr(0); opr.setIreg(Reg.qEDI);
		}
		if(size == 1) {
			getTosAsBYT4(Reg.qEAX);
//			Qf3(qSTORE,0,qEAX,cTYP,opr)
			new Q_STORE(Reg.qEAX, opr);
		} else {
			if(CTStack.TOS.kind != Kind.K_Address) {
//				Qf3(qLOADA,0,qEDI,cADR,opr);
				new Q_LOADA(Reg.qEDI, opr);
//				opr.sibreg = bEDI+iEDI;
//				opr.kind = reladr; opr.rela.val = 0; opr.segmid.val = 0;
				opr = MemAddr.ofRelAddr(0); opr.setIreg(Reg.qEDI);
			}
			FETCH.GQfetch();
//			Qf4(qPOPM,0,qAL,cTYP,nbyte,opr);
			new Q_POPM(Reg.qAL, size, opr);
		}
		
		CTStack.pop();
		Util.IERR("");
	}

	public static void getTosAsBYT4(int reg) {
		if(CTStack.TOS == null) Util.IERR("CODER.GetTosAsBYT4-1");
		if(CTStack.TOS.type.size == 0) Util.IERR("CODER.GetTosAsBYT4-2");
//	      if TTAB(TOS.tysizebyte <> 4 then GQconvert(T_WRD4) endif;
		Reg.getTosAdjustedIn86(reg);
	}

}
