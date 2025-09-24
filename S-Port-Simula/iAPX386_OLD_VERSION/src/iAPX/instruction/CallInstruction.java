package iAPX.instruction;

import static iAPX.util.Global.*;

import iAPX.ctStack.CTStack;
import iAPX.ctStack.ProfileItem;
import iAPX.ctStack.StackItem;
import iAPX.dataAddress.MemAddr;
import iAPX.descriptor.ProfileDescr;
import iAPX.descriptor.RoutineDescr;
import iAPX.enums.Kind;
import iAPX.enums.Opcode;
import iAPX.enums.PKind;
import iAPX.parser.Parser;
import iAPX.qInstr.Q_CALL;
import iAPX.qPkt.Qfrm2;
import iAPX.qPkt.Qfunc;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Reg;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;

public class CallInstruction {
	
	public CallInstruction() {
	}
	
	public static void ofScode(int n) {
//	begin ref(ProfileDescr) prf; infix(WORD) tag;
//	      InTag(%tag%); prf = DISPL(tag.HI).elt(tag.LO);
		Tag tag = Tag.inTag();
		ProfileDescr prf = (ProfileDescr) Display.lookup(tag);
	switch(prf.pKind) {
    case P_SYSTEM:       callSYS(prf,n,prf.pKind); break;
//	      case P_OS2:       CallSYS(prf,n,qOS2)
//	      case P_XNX:       CallSYS(prf,n,qXNX)
//	      case P_KNL:       CallSYS(prf,n,qKNL)
//	      case P_C:         CallSYS(prf,n,qC)
//	      case P_FTN:       CallFORTRAN(prf,n)
//	----- case P_PASCAL:    CallSYS(prf,n,qC)
//	      case P_PASCAL:    CallSYS(prf,n,0)
//
//	      case P_GTOUTM:    CallDefault(prf,n)  -- CallGTOUTM(prf,n)
//	%+S   case P_MOVEIN:    CallInline(prf,n)
//	      case P_RSQROO,P_SQROOT:
//	                        CallDefault(prf,n)  --- TEMP ????????????
//	-- ???  %-E                     CallDefault(prf,n)
//	-- ???  %+E                     CallInline(prf,n)
//	      case P_RLOGAR:    CallDefault(prf,n)  -- CallRLOGAR(prf,n)
//	      case P_LOGARI:    CallDefault(prf,n)  -- CallLOGARI(prf,n)
//	      case P_REXPON:    CallDefault(prf,n)  -- CallREXPON(prf,n)
//	      case P_EXPONE:    CallDefault(prf,n)  -- CallEXPONE(prf,n)
//	      case P_RSINUS:    CallDefault(prf,n)  -- CallRSINUS(prf,n)
//	      case P_SINUSR:    CallDefault(prf,n)  -- CallSINUSR(prf,n)
//	      case P_RARTAN:    CallDefault(prf,n)  -- CallRARTAN(prf,n)
//	      case P_ARCTAN:    CallDefault(prf,n)  -- CallARCTAN(prf,n)
//
//	      case P_RLOG10:    CallDefault(prf,n)  -- CallRLOG10(prf,n)
//	      case P_DLOG10:    CallDefault(prf,n)  -- CallDLOG10(prf,n)
//	      case P_RCOSIN:    CallDefault(prf,n)  -- CallRCOSIN(prf,n)
//	      case P_COSINU:    CallDefault(prf,n)  -- CallCOSINU(prf,n)
//	      case P_RTANGN:    CallDefault(prf,n)  -- CallRTANGN(prf,n)
//	      case P_TANGEN:    CallDefault(prf,n)  -- CallTANGEN(prf,n)
//	      case P_RARCOS:    CallDefault(prf,n)  -- CallRARCOS(prf,n)
//	      case P_ARCCOS:    CallDefault(prf,n)  -- CallARCCOS(prf,n)
//	      case P_RARSIN:    CallDefault(prf,n)  -- CallRARSIN(prf,n)
//	      case P_ARCSIN:    CallDefault(prf,n)  -- CallARCSIN(prf,n)
//	      case P_ERRNON:    CallDefault(prf,n)  -- CallERRNON(prf,n)
//	      case P_ERRQUA:    CallDefault(prf,n)  -- CallERRQUA(prf,n)
//	      case P_ERRSWT:    CallDefault(prf,n)  -- CallERRSWT(prf,n)
//	      case P_ERROR:     CallDefault(prf,n)  -- CallERROR(prf,n)
//	      case P_CBLNK:     CallDefault(prf,n)  -- CallCBLNK(prf,n)
//	      case P_CMOVE:     CallDefault(prf,n)  -- CallCMOVE(prf,n)
//	      case P_STRIP:     CallDefault(prf,n)  -- CallSTRIP(prf,n)
//	      case P_TXTREL:    CallDefault(prf,n)  -- CallTXTREL(prf,n)
//	      case P_TRFREL:    CallDefault(prf,n)  -- CallTRFREL(prf,n)
//	      case P_AR1IND,P_AR2IND:
//	%-E                     CallDefault(prf,n)
//	%+E                     CallInline(prf,n)
//	      case P_ARGIND:    CallDefault(prf,n)  -- CallARGIND(prf,n)
//	      case P_IABS:      CallDefault(prf,n)  -- CallIABS(prf,n)
//	      case P_RABS:      CallDefault(prf,n)  -- CallRABS(prf,n)
//	      case P_DABS:      CallDefault(prf,n)  -- CallDABS(prf,n)
//	      case P_RSIGN:     CallDefault(prf,n)  -- CallRSIGN(prf,n)
//	      case P_DSIGN:     CallDefault(prf,n)  -- CallDSIGN(prf,n)
//	      case P_MODULO:    CallDefault(prf,n)  -- CallMODULO(prf,n)
//	      case P_RENTI:     CallDefault(prf,n)  -- CallRENTI(prf,n)
//	      case P_DENTI:     CallDefault(prf,n)  -- CallDENTI(prf,n)
//	      case P_DIGIT:     CallDefault(prf,n)  -- CallDIGIT(prf,n)
//	      case P_LETTER:    CallDefault(prf,n)  -- CallLETTER(prf,n)
//	      case P_RIPOWR:    CallDefault(prf,n)  -- CallRIPOWR(prf,n)
//	      case P_RRPOWR:    CallDefault(prf,n)  -- CallRRPOWR(prf,n)
//	      case P_RDPOWR:    CallDefault(prf,n)  -- CallRDPOWR(prf,n)
//	      case P_DIPOWR:    CallDefault(prf,n)  -- CallDIPOWR(prf,n)
//	      case P_DRPOWR:    CallDefault(prf,n)  -- CallDRPOWR(prf,n)
//	      case P_DDPOWR:    CallDefault(prf,n)  -- CallDDPOWR(prf,n)
//	%+S   case P_DOS_CREF, P_DOS_OPEN, P_DOS_CLOSE, P_DOS_READ, P_DOS_WRITE,
//	%+S        P_DOS_DELF, P_DOS_FPTR, P_DOS_CDIR,  P_DOS_ALOC, P_DOS_TERM,
//	%+S        P_DOS_TIME, P_DOS_DATE, P_DOS_VERS,  P_DOS_EXEC, P_DOS_IOCTL,
//	%+S        P_DOS_LOCK, P_DOS_GDRV, P_DOS_GDIR:
//	%+S        CallInline(prf,n)
//	%+S   case P_APX_SCMPEQ,P_APX_SMOVEI,P_APX_SMOVED,P_APX_SFILL,
//	%+S        P_APX_SSKIP,P_APX_STRIP,P_APX_SFINDI,P_APX_SFINDD:
//	%+S        CallInline(prf,n)
//	%+S   case P_APX_BOBY,P_APX_SZ2W,P_APX_RF2N,
//	%+S        P_APX_BYBO,P_APX_W2SZ,P_APX_N2RF,
//	%+S        P_APX_BSHL,P_APX_WSHL,P_APX_BSHR,P_APX_WSHR:
//	%+S        CallInline(prf,n)
//	%+S   case P_APX_BNOT,P_APX_BAND,P_APX_BOR,P_APX_BXOR,
//	%+S        P_APX_WNOT,P_APX_WAND,P_APX_WOR,P_APX_WXOR:
//	%+S        CallInline(prf,n)
//	%+S   case P_DOS_SDMODE,P_DOS_UPDPOS,P_DOS_CURSOR,P_DOS_SDPAGE,
//	%+S        P_DOS_SROLUP,P_DOS_SROLDW,P_DOS_GETCEL,P_DOS_PUTCHR,
//	%+S        P_DOS_GDMODE,P_DOS_SETPAL,P_DOS_RDCHK,P_DOS_KEYIN:
//	%+S        CallInline(prf,n)
	default: Util.IERR(""+prf.pKind);  // CallDefault(prf,n);
	} //endcase;
//	end;
//	%page
//	Routine wWordsOnStack;
//	import ref(StackItem) x; export range(0:MaxByte) res;
//	begin
//	%+D   RST(R_WordsOnStack);
//	      case 0:K_Max (x.kind)
//	      case K_Temp,K_Result,K_Coonst:
//	           res = (TTAB(x.type).nbyte+(AllignFac-1))/AllignFac;
//	      case K_Address:
//	           res = if x qua Address.AtrState=NotStacked then 0 else 1;
//	%-E        if x qua Address.ObjState<>NotStacked then res = res+2 endif;
//	%+E        if x qua Address.ObjState<>NotStacked then res = res+1 endif;
//	%+C   otherwise IERR("PARSE.wWordsOnStack")
//	      endcase;
	}

	private static void callSYS(ProfileDescr spec, int nstckval, PKind pkind) {
//	begin ref(ProfileItem) pitem; range(0:255) npop; infix(MemAddr) entr,opr;
//	      ref(Descriptor) rut; range(0:MaxType) xt; infix(WORD) rtag;
//	      range(0:MaxByte) dx,xlng; ref(Temp) result;
		IO.println("\nCallInstruction.callSys: " + pkind + ", nstckval=" + nstckval + ", " + spec + ", curinstr=" + Scode.curinstr);
		Type xt = null; int xlng = 0;
		if(spec.type != null) {
			xt = spec.type; xlng = xt.size;
		}
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		int npop = 0;
		if(CTStack.StackDepth87 > 0) Util.IERR("PARSE.CallSYS-1");
		if(nstckval == 0) CTStack.Push(pitem);
		else {
			if(nstckval > 1) Util.IERR("PARSE.CallSYS-2");
			CTStack.precede(pitem, CTStack.TOS); npop = npop + putPar(pitem,1);
			Util.IERR("SJEKK DETTE");
		}
//	      repeat InputInstr while CurInstr <> S_CALL
		IO.println("CallInstruction.callSys: BEFORE test S_CALL: curinstr=" + Scode.curinstr);
		IO.println("CallInstruction.callSys: BEFORE test S_CALL: nxtinstr=" + Scode.nextByte());
		Scode.inputInstr();
		while(Scode.curinstr != Scode.S_CALL) {
//	      do repeat
			while(Parser.instruction()) { Scode.inputInstr(); }
			if(Scode.curinstr != Scode.S_ASSPAR) Util.IERR("Syntax error in call Instruction");
			
			IO.println("CallInstruction.callSys: BEFORE putPar");
			npop = npop + putPar(pitem,1);
//			Util.IERR("SJEKK DETTE");
		
			if(Option.traceMode != 0) CTStack.dumpStack();
			Scode.inputInstr();
		}
		// ---------  Call Routine  ---------
//	      InTag(%rtag%); rut = DISPL(rtag.HI).elt(rtag.LO);
		Tag rtag = Tag.inTag(); RoutineDescr rut = (RoutineDescr) Display.lookup(rtag);
		IO.println("CallInstruction.callSys: Call " + rut);
//	      if rut.kind=K_IntRoutine then entr = rut qua IntDescr.adr
//	      else entr.kind = extadr;
//	%+E        entr.sibreg = NoIBREG;
//	           entr.rela.val = rut qua ExtDescr.adr.rela;
//	           entr.smbx = rut qua ExtDescr.adr.smbx;
//	      endif;
		MemAddr entr = rut.adr;
		if(entr == null) Util.IERR("Undefined routine entry");
		// ---------  Final Actions  ---------
		if(pitem.nasspar != pitem.spc.npar()) Util.IERR("Wrong number of Parameters");
		if(Option.traceMode > 1) IO.println(".   CALL " + CTStack.TOS);

//		Qfunc.Qf5(Opcode.qCALL,pkind.ordinal(),0,spec.nparWords,entr);
		new Q_CALL(pkind, spec.nparWords, entr);

		//	      repeat while npop<>0 do Pop; npop = npop-1 endrepeat;
		while((npop--) > 0) CTStack.pop();
		if(CTStack.TOS != pitem) Util.IERR("PARSE.CallSYS-3");
		CTStack.pop();
		if(xlng != 0) { // -- Push Result onto Stack --
//			if(xt == Type.T_REAL) {
//				dx = 8;
//			} else dx = xlng;
//			if(dx > 4) {
//				PushFromNPX(T_LREAL,xt); StackDepth87 = StackDepth87+1;
//			} else MindMask = wOR(MindMask,uEAX);
			Qfunc.Qf1(Opcode.qPUSHR,Reg.qEAX,cANY);
			CTStack.pushTemp(xt);
		}
//		Util.IERR("");
	}

	private static int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
//	begin range(0:MaxWord) n; range(0:255) i,c; ref(StackItem) s;
//	      range(0:MaxType) st,pt; infix(MemAddr) opr;

		IO.println("\nCallInstruction.putPar: " + pItm + ", nrep=" + nrep + ", curinstr=" + Scode.curinstr);
		Type pt = pItm.spc.par.get(pItm.nasspar).type;
		int count  = pItm.spc.par.get(pItm.nasspar).count;
		int size = pt.size; int i = nrep;
		if(nrep > count) Util.IERR("Too many values in repeated param");
		pItm.nasspar++;
		StackItem s = CTStack.TOS; Type st = s.type;
		//--- First: Treat TOS ---
		if(st != pt) CONVERT.GQconvert(pt);
		else if(s.kind == Kind.K_Address) s.type = st;
		else CONVERT.GQconvert(pt);
		if(CTStack.TOS.kind == Kind.K_Address) FETCH.GQfetch(); 

		// --- Then: Treat rest of rep-par ---
//		StackItem s = CTStack.TOS;
//	      repeat i = i-1 while i <> 0 do
		while((--i) > 0) {
			s = s.suc;
			Util.IERR("");
//	--??     Husk at integer-type skal legges p} stacken m.h.t spesifikasjon!!!
//	--??     CheckTypesEqual(s.type,p.type);
//	%+C      if s.kind=K_Address then IERR("MODE mismatch below TOS") endif;
//	         if s.type <> pt
//	         then
//	%+S           if SYSGEN <> 0 then
//	%+S           WARNING("PARSE: TYPE mismatch below TOS -- ASSREP") endif;
//	              if    pt=T_WRD4 then ConvRepWRD4(nrep); goto L2;
//	%-E           elsif pt=T_WRD2 then ConvRepWRD2(nrep); goto L1;
//	%+C           else IERR("PARSE: TYPE mismatch below TOS -- ASSREP");
//	              endif;
//	         endif;
		}
//	%-E  L1:
//	   L2:
		if(nrep < count) {
			Qfunc.Qf2(Opcode.qDYADC,Qfrm2.qSUB, Reg.qESP,cSTP,(count-nrep)*size);
		}
		IO.println("CallInstruction.putPar: END: " + Scode.curinstr);
		IO.println("CallInstruction.putPar: END: " + Scode.nextByte());
		int npop = nrep;
//		Util.IERR("");
		return npop;
	}
	
}
