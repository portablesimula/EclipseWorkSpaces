package removed.java;

import bec.util.Scode;
import bec.util.Util;

public class Coder {
//	Module CODER("iAPX");
//	 begin insert SCOMN,SKNWN,SBASE,COASM,MASSAGE;
//	       -----------------------------------------------------------------
//	       ---  COPYRIGHT 1988 by                                        ---
//	       ---  Simula a.s.                                              ---
//	       ---  Oslo, Norway                                             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---              P O R T A B L E     S I M U L A              ---
//	       ---                                                           ---
//	       ---                   F O R    I B M    P C                   ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---           S   -   C   O   M   P   I   L   E   R           ---
//	       ---                                                           ---
//	       ---             S e g m e n t   a n d   S t a c k             ---
//	       ---                      H a n d l i n g                      ---
//	       ---              C o d e    G e n e r a t i o n               ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     R - Includes full Real Capabilities                   ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//
//	%-D Visible macro GQconvert(1);
//	%-D begin GQfetch; if TOS.type <> %1 then DOconvert(%1) endif; endmacro;
//
//	%-D Visible macro GQfetch(0);
//	%-D begin if TOS.kind=K_Address then GQfetchxx endif endmacro
//
//	%page
//	%+D Routine CheckPush; import ref(StackItem) s;
//	%+D begin
//	%+D   RST(R_Push);
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,14); outstring("***PUSH:  "); print(s) endif;
//	%+D   if (s.suc <> none) or (s.pred <> none)
//	%+D   then IERR("CODER.CheckPush") endif;
//	%+D end;
//
//	macro DOpush(1);
//	begin
//	%+D   CheckPush(%1);
//	      if TOS = none then TOS:=BOS:=%1 qua stackitem; %1 qua stackitem.suc:=none;
//	      else %1 qua stackitem.suc:=TOS;
//	           TOS:=%1 qua stackitem.suc.pred:=%1 qua stackitem endif;
//	%+D   if TraceMode > 1 then DumpStack endif;
//	endmacro;
//
//	Visible Routine Push; import ref(StackItem) s;
//	begin
//	      DOpush(s);
//	end;
//
//	Visible Routine Precede; import ref(StackItem) new,x;
//	begin
//	%+D   RST(R_Precede);
//	%+C   if (x=none) or (x=SAV) then IERR("CODER.Precede") endif;
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,14); outstring("***PRECEDE:  "); print(x);
//	%+D        setpos(sysout,14); outstring("        BY:  "); print(new);
//	%+D   endif;
//	      new.suc:=x.suc; new.pred:=x; x.suc:=new;
//	      if new.suc <> none then new.suc.pred:=new
//	      elsif BOS=x  then BOS:=new endif;
//	%+D   if TraceMode > 1 then DumpStack endif;
//	end;
//
//	%+D Routine CheckPop;
//	%+D begin RST(R_Pop);
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,14); outstring("*** POP:  "); print(TOS) endif;
//	%+D   if TOS = none then IERR("CODER.CheckPop");
//	%+D   elsif TOS.kind >= K_Max
//	%+D   then IERR("POP -- TOS is already deleted"); print(TOS) endif;
//	%+D end;
//
//	Visible Routine Pop;
//	begin ref(Object) x; range(0:MaxByte) kind;
//	%+D   CheckPop;
//	      x:=TOS; kind:=x.kind; TOS:=x qua StackItem.suc;
//	      if TOS=none then BOS:=none else TOS.pred:=none endif;
//	%+D   x qua StackItem.suc:=x qua StackItem.pred:=none;
//	      x qua FreeObject.next:=FreeObj(kind); FreeObj(kind):=x;
//	%+D   x.kind := kind+128;
//	end;
//
//	Visible Routine TakeTOS; export ref(StackItem) x;
//	begin
//	%+D   RST(R_TakeTOS);
//	%+C   if TOS = none then IERR("CODER.TakeTOS"); x:=none
//	%+C   else
//	           x:=TOS; TOS:=x.suc;
//	%+C        x.suc:=x.pred:=none;
//	           if TOS = none
//	           then BOS:=none else TOS.pred:=none endif;
//	%+C   endif;
//	end;
//
//	--- Routine TakeRef; export ref(Address) x;
//	--- begin
//	--- %+D   RST(R_TakeRef);
//	--- %+C   if TOS = none then IERR("CODER.TakeRef"); x:=none;
//	--- %+C   else
//	--- %+C        CheckRef(TOS);
//	---            x:=TakeTOS;
//	--- %+C   endif;
//	--- end;
//
//	%+C Routine CheckRef; import ref(StackItem) s;
//	%+C begin if s.kind <> K_Address
//	%+C       then FATAL_ERROR("Check-ref fails") endif;
//	%+C end;
//
//	%+C Visible Routine CheckTosRef;
//	%+C begin CheckRef(TOS) end;
//
//	%+C Visible Routine CheckSosRef;
//	%+C begin CheckRef(TOS.suc) end;
//
//	%+C Visible Routine CheckSosValue;
//	%+C begin if TOS.suc.kind = K_Address
//	%+C       then IERR("CheckSosValue fails") endif;
//	%+C end;
//
//	%+C Visible Routine CheckTosType; import range(0:MaxType) t;
//	%+C begin if TOS.type <> t
//	%+C       then IERR("Illegal type of TOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckSosType; import range(0:MaxType) t;
//	%+C begin if TOS.suc.type <> t
//	%+C       then IERR("Illegal type of SOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckTosInt;
//	%+C begin if TOS.type > T_BYT1
//	%+C       then IERR("Illegal type of TOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckTosArith;
//	%+C begin if TOS.type > T_TREAL
//	%+C       then IERR("Illegal type of TOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckSosInt;
//	%+C begin if TOS.suc.type > T_BYT1
//	%+C       then IERR("Illegal type of SOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckSosArith;
//	%+C begin if TOS.suc.type > T_TREAL
//	%+C       then IERR("Illegal type of SOS") endif;
//	%+C end;
//
//	%+C Visible Routine CheckSosType2; import range(0:MaxType) t1,t2;
//	%+C begin    if TOS.suc.type = t1 then -- OK
//	%+C       elsif TOS.suc.type = t2 then -- OK
//	%+C       else IERR("Illegal type of SOS") endif;
//	%+C end;
//
//	%+C Routine CheckTypesEqual;
//	%+C begin range(0:MaxType) t1,t2;
//	%+C       t1:=TOS.type; t2:=TOS.suc.type;
//	%+C       if t1=t2 then goto E1 endif;
//	%+C       if t1 > T_max then IERR("CODER.CheckTypesEqual-1") endif;
//	%+C       if t2 > T_max then IERR("CODER.CheckTypesEqual-2") endif;
//	%+C       t1:=ArithType(t1,t1); t2:=ArithType(t2,t2);
//	%+C       if t1=t2 then goto E2 endif;
//	%+C       if (t1>T_BYT1) or (t2>T_BYT1)
//	%+C       then IERR("Different types of TOS and SOS") endif;
//	%+C E1:E2:end;
//
//	%+C Visible Routine CheckStackEmpty;
//	%+C begin if TOS <> none
//	%+C       then IERR("Stack should be empty"); TOS:=BOS:=none endif;
//	%+C       if StackDepth87 <> 0
//	%+C       then IERR("StackDepth87 <> 0"); StackDepth87:=0 endif;
//	--- %-E %+C   if CHKSTK
//	--- %-E %+C   then if not InsideRoutine
//	--- %-E %+C        then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	--- %-E %+C   endif;
//	%+C end;
//
//	%+D Visible Routine DumpStack;
//	%+D begin ref(StackItem) s;
//	%+D   if InputTrace <> 0 then printout(inptrace) endif;
//	%+D   setpos(sysout,14); outstring("Current Stack  -  ");
//	%+D   if TOS = none then outstring("**Empty**"); printout(sysout);
//	%+D   else outstring("TOS:"); s:=TOS;
//	%+D        repeat putpos(sysout,36); outstring("* ");
//	%+D               if s.type<>0
//	%+D               then edtype(sysout,s.type); outchar('(');
//	%+D                    outint(s.repdist); outstring(")-");
//	%+D               endif;
//	%+D               case 0:K_Max (s.kind)
//	%+D               when K_ProfileItem: outstring("PROFILE:  ")
//	%+D               when K_Address: outstring("REF:  ")
//	%+D               otherwise outstring("VAL:  ") endcase; edit(sysout,s)
//	%+D               printout(sysout); s:=s.suc
//	%+D        while s <> none do endrepeat;
//	%+D   endif;
//	%+D   if StackDepth87 <> 0
//	%+D   then setpos(sysout,14); outstring("StackDepth87: ");
//	%+D        outword(StackDepth87); printout(sysout);
//	%+D   endif;
//	%+D end;
//	%title
//	Visible Routine AssertObjStacked;
//	begin infix(MemAddr) adr;
//	%-E   range(0:nregs) segreg;
//	%+D   RST(R_AssertObjStacked);
//	%+C   if TOS.kind <> K_Address
//	%+C   then IERR("CODER.AssertObjStacked-1") endif;
//	      if TOS qua Address.ObjState=NotStacked
//	      then TOS qua Address.ObjState:=FromConst;
//	           adr:=TOS qua Address.Objadr;
//	           case 0:adrMax (adr.kind)
//	           when reladr,locadr: 
//	%-E             segreg:=GetSreg(adr);
//	%-E             PreMindMask:=wOR(PreMindMask,uMask(segreg));
//	%-E             Qf1(qPUSHR,segreg,cOBJ);
//	%-E             Qf3(qPUSHA,0,qBX,cOBJ,adr);
//	%+E             Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//	           when segadr,fixadr,extadr:
//	%-E             Qf2b(qPUSHC,0,qBX,cOBJ,F_BASE,adr);
//	%-E             Qf2b(qPUSHC,0,qBX,cOBJ,F_OFFSET,adr);
//	%+E             Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//	%+C        otherwise IERR("CODER.AssertObjStacked-2")
//	           endcase;
//	      endif;
//	end;
//
//	Visible Routine AssertAtrStacked;
//	begin
//	%+D   RST(R_AssertAtrStacked);
//	      AssertObjStacked;
//	      if TOS qua Address.AtrState=NotStacked
//	      then TOS qua Address.AtrState:=FromConst;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
//	      elsif TOS qua Address.AtrState=Calculated
//	      then if TOS qua Address.Offset <> 0
//	           then
//	%-E             Qf2(qLOADC,0,qAX,cVAL,TOS qua Address.Offset);
//	%-E             Qf1(qPOPR,qBX,cVAL);
//	%-E             Qf2(qDYADR,qADD,qAX,cVAL,qBX); Qf1(qPUSHR,qAX,cVAL);
//	%+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
//	                TOS qua Address.Offset:=0;
//	           endif;
//	      endif;
//	end;
//	%page
//
//	Visible Routine PresaveOprRegs; import infix(MemAddr) opr;
//	begin range(0:MaxWord) rMask;
//	%-E   range(0:MaxByte) segreg,bireg;
//	%+E   range(0:nregs) r;
//	%+D   RST(R_PresaveOprRegs);
//	%-E   segreg:=bAND(opr.sbireg,oSREG);
//	%-E   if    segreg=oES then rMask:=uES;
//	%-E   elsif segreg=oDS then rMask:=uDS;
//	%-E   else  rMask:=0 endif;
//	%-E   bireg:=bAND(opr.sbireg,rmBIREG);
//	%-E   if bireg <> 0
//	%-E   then case 0:7 (bAND(bireg,7))
//	%-E        when biBXSI: rMask:=wOR(rMask,wOR(uBX,uSI))
//	%-E        when biBXDI: rMask:=wOR(rMask,wOR(uBX,uDI))
//	%-E        when biBPSI: rMask:=wOR(rMask,wOR(uBP,uSI))
//	%-E        when biBPDI: rMask:=wOR(rMask,wOR(uBP,uDI))
//	%-E        when biSI:   rMask:=wOR(rMask,uSI)
//	%-E        when biDI:   rMask:=wOR(rMask,uDI)
//	%-E        when biBP:   rMask:=wOR(rMask,uBP)
//	%-E        when biBX:   rMask:=wOR(rMask,uBX)
//	%-E        endcase;
//	%-E   endif;
//	%+E   r:=GetBreg(opr); if r=0 then rMask:=0 else rMask:=uMask(r) endif;
//	%+E   r:=GetIreg(opr); if r<>0 then rMask:=wOR(rMask,uMask(r)) endif;
//	      PreMindMask:=wOR(PreMindMask,rMask);
//	end;
//	%page
//
//	Routine GetTosSrcAdr; export infix(MemAddr) a;
//	begin a:=TOS qua Address.Objadr;
//	      a.rela.val:=a.rela.val+TOS qua Address.Offset;
//	      case 0:2 (TOS qua Address.AtrState)
//	      when NotStacked: -- Nothing
//	      when FromConst:  qPOPKill(AllignFac);
//	      when Calculated:
//	%+D        if GetIreg(a)<>0 then IERR("GetTosSrcAdr-0") endif;
//	%-E        Qf1(qPOPR,qSI,cVAL); a.sbireg:=SetSBIreg(a.sbireg,qSI)
//	%+E        Qf1(qPOPR,qESI,cVAL);
//	%+E        if a.sibreg=NoIBREG then a.sibreg:=bESI+iESI;
//	%+E        else a.sibreg:=wOR(wAND(a.sibreg,BaseREG),iESI) endif;
//	      endcase;
//	%-E   if bAND(a.sbireg,oSREG)=oSS
//	%-E   then case 0:2 (TOS qua Address.ObjState)
//	%-E        when NotStacked: -- Nothing
//	%-E        when FromConst:  qPOPKill(2); qPOPKill(2);
//	%-E %+C    otherwise IERR("CODER.GetTosSrcAdr")
//	%-E        endcase;
//	%-E   else a.sbireg:=SetSBIreg(a.sbireg,qDS);
//	%-E        case 0:2 (TOS qua Address.ObjState)
//	%-E        when NotStacked: Qf2b(qLOADSC,qDS,qSI,cOBJ,0,a);
//	%-E        when FromConst:  qPOPKill(2); Qf1(qPOPR,qDS,cOBJ);
//	%-E        when Calculated: a.sbireg:=SetSBIreg(a.sbireg,qBX);
//	%-E                         Qf1(qPOPR,qBX,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E        endcase;
//	%-E   endif;
//	%+E   case 0:2 (TOS qua Address.ObjState)
//	%+E   when NotStacked: -- Nothing
//	%+E   when FromConst:  qPOPKill(4);
//	%+E   when Calculated: Qf1(qPOPR,qEBX,cOBJ);
//	%+E        a.sibreg:=bOR(bAND(a.sibreg,IndxREG),bEBX);
//	%+E   endcase;
//	end;
//
//	Routine GetTosDstAdr; export infix(MemAddr) a;
//	begin a:=TOS qua Address.Objadr;
//	      a.rela.val:=a.rela.val+TOS qua Address.Offset;
//	      case 0:2 (TOS qua Address.AtrState)
//	      when NotStacked: -- Nothing
//	      when FromConst:  qPOPKill(AllignFac);
//	      when Calculated:
//	%+D        if GetIreg(a)<>0 then IERR("GetTosDstAdr-0") endif;
//	%-E        Qf1(qPOPR,qDI,cVAL); a.sbireg:=SetSBIreg(a.sbireg,qDI)
//	%+E        Qf1(qPOPR,qEDI,cVAL);
//	%+E        if a.sibreg=NoIBREG then a.sibreg:=bEDI+iEDI;
//	%+E        else a.sibreg:=wOR(wAND(a.sibreg,BaseREG),iEDI) endif;
//	      endcase;
//	%-E   if bAND(a.sbireg,oSREG)=oSS
//	%-E   then case 0:2 (TOS qua Address.ObjState)
//	%-E        when NotStacked: -- Nothing
//	%-E        when FromConst:  qPOPKill(2); qPOPKill(2);
//	%-E %+C    otherwise IERR("CODER.GetTosDstAdr")
//	%-E        endcase;
//	%-E   else a.sbireg:=SetSBIreg(a.sbireg,qES);
//	%-E        case 0:2 (TOS qua Address.ObjState)
//	%-E        when NotStacked: Qf2b(qLOADSC,qES,qDI,cOBJ,0,a);
//	%-E        when FromConst:  qPOPKill(2); Qf1(qPOPR,qES,cOBJ);
//	%-E        when Calculated: a.sbireg:=SetSBIreg(a.sbireg,qBX);
//	%-E                         Qf1(qPOPR,qBX,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        endcase;
//	%-E   endif;
//	%+E   case 0:2 (TOS qua Address.ObjState)
//	%+E   when NotStacked: -- Nothing
//	%+E   when FromConst:  qPOPKill(4);
//	%+E   when Calculated: Qf1(qPOPR,qEBX,cOBJ);
//	%+E        a.sibreg:=bOR(bAND(a.sibreg,IndxREG),bEBX);
//	%+E   endcase;
//	end;
//	%page
//
//	range(0:255) SosAdrNwk;
//
//	Routine GetSosAddr; ---- TOS is val and SOS is ref ----
//	%-E import range(0:nregs) srp,brp,irp;
//	%+E import range(0:nregs) brp,irp;
//	export infix(MemAddr) a;
//	begin range(0:MaxWord) depth;
//	      infix(MemAddr) opr; ref(Address) sos;
//	      range(0:6) nbu; -- No. of bytes to be used
//	      range(0:3) nwk; -- No. of words/dwords to be killed (later)
//
//	%+D   RST(R_GetSosAddr);
//	      sos:=TOS.suc; a:=sos.Objadr;
//	      a.rela.val:=a.rela.val+sos.Offset;
//	      case 0:2 (sos.AtrState)
//	      when NotStacked: nbu:=0; nwk:=0;
//	      when FromConst:  nbu:=0; nwk:=1;
//	      when Calculated: nbu:=2; nwk:=1            endcase;
//
//	%-E   if bAND(a.sbireg,oSREG)=oSS
//	%-E   then case 0:2 (sos.ObjState)
//	%-E        when NotStacked:
//	%-E        when FromConst:  nwk:=nwk+2;
//	%-E %+C    otherwise IERR("CODER.GetSosAddr")
//	%-E        endcase;
//	%-E   else a.sbireg:=SetSBIreg(a.sbireg,srp);
//	           case 0:2 (sos.ObjState)
//	           when NotStacked:
//	%-E             Qf2b(qLOADSC,srp,irp,cOBJ,0,sos.Objadr);
//	           when FromConst: 
//	%-E             Qf2b(qLOADSC,srp,irp,cOBJ,0,sos.Objadr);
//	%-E             nwk:=nwk+2;
//	%+E             nwk:=nwk+1;
//	           when Calculated:
//	%-E             nwk:=nwk+2;
//	%+E             nwk:=nwk+1;
//	                nbu:=nbu+4;
//	           endcase;
//	%-E   endif;
//
//	      if nbu > 0
//	      then depth:=wAllign(%TTAB(TOS.type).nbyte%);
//	%+C        if depth=0 then IERR("CODER.GetSosAddr-1") endif;
//	%-E        Qf2(qMOV,0,brp,cSTP,qSP);
//	%+E        Qf2(qMOV,0,brp,cSTP,qESP);
//	           opr.kind:=reladr; opr.rela.val:=depth; opr.segmid.val:=0;
//	%-E        opr.sbireg:=SetSBIreg(oSS,brp);
//	%+E        opr.sibreg:=iReg(%brp%);
//	           if nbu>2 then PreMindMask:=wOR(PreMindMask,uMask(brp)) endif;
//	           Qf4c(qLOAD,0,irp,cVAL,0,opr,0);
//	%-E        a.sbireg:=SetSBIreg(a.sbireg,irp);
//	%+E        a.sibreg:=iReg(%irp%);
//	           if nbu=6
//	           then opr.rela.val:=opr.rela.val+AllignFac;
//	                PreMindMask:=wOR(PreMindMask,uMask(brp));
//	                Qf3(qDYADM,qADD,irp,cVAL,opr);
//	           endif;
//	%-E        if nbu > 2
//	%-E        then opr.rela.val:=opr.rela.val+AllignFac;
//	%-E             Qf4c(qLOAD,0,srp,cOBJ,0,opr,0);
//	%-E        endif;
//	      endif;
//	      SosAdrNwk:=nwk;
//	end;
//
//
//	Visible Routine GetTosValueIn86R3;
//	import range(0:nregs) reg1,reg2,reg3;
//	--     /* M} ikke bruke qDI p.g.a. RUPDATE. */
//	begin infix(MemAddr) opr; ref(Temp) val;
//	      range(0:6) lng; range(0:MaxType) type; range(0:MaxByte) cTYP;
//	%+D   RST(R_GetTosValueIn86R3);
//	      type:=TOS.type; lng:=TTAB(type).nbyte/AllignFac;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      case 0:K_Max (TOS.kind)
//	      when K_Temp,K_Result,K_Coonst:
//	           Qf1(qPOPR,reg1,cTYP)
//	           if lng > 1
//	           then Qf1(qPOPR,reg2,cTYP);
//	                if lng > 2 then Qf1(qPOPR,reg3,cTYP) endif;
//	           endif;
//	      when K_Address:
//	           opr:=GetTosSrcAdr;
//	           case 0:3 (lng)
//	           when 1: Qf4c(qLOAD,0,reg1,cTYP,0,opr,0)
//	           when 2: PresaveOprRegs(opr); Qf4c(qLOAD,0,reg1,cTYP,0,opr,0)
//	                   opr.rela.val:=opr.rela.val+AllignFac;
//	                   Qf4c(qLOAD,0,reg2,cTYP,0,opr,0);
//	           when 3: PresaveOprRegs(opr); Qf4c(qLOAD,0,reg1,cTYP,0,opr,0)
//	                   opr.rela.val:=opr.rela.val+AllignFac;
//	                   PresaveOprRegs(opr); Qf4c(qLOAD,0,reg2,cTYP,0,opr,0)
//	                   opr.rela.val:=opr.rela.val+AllignFac;
//	                   Qf4c(qLOAD,0,reg3,cTYP,0,opr,0);
//	           endcase;
//	           Pop; pushTempVAL(type);
//	      endcase;
//	end;
//
//	%-E Visible Routine GetTosValueIn86R4;
//	%-E import range(0:nregs) reg1,reg2,reg3,reg4;
//	%-E --     /* M} ikke bruke qDI p.g.a. RUPDATE. */
//	%-E begin infix(MemAddr) opr; range(0:MaxType) type; range(0:MaxByte) cTYP;
//	%-E       type:=TOS.type;
//	%-E       if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	%-E       case 0:K_Max (TOS.kind)
//	%-E       when K_Temp,K_Result,K_Coonst:
//	%-E            Qf1(qPOPR,reg1,cTYP); Qf1(qPOPR,reg2,cTYP);
//	%-E            Qf1(qPOPR,reg3,cTYP); Qf1(qPOPR,reg4,cTYP);
//	%-E       when K_Address:
//	%-E            opr:=GetTosSrcAdr;
//	%-E            PresaveOprRegs(opr); Qf4c(qLOAD,0,reg1,cTYP,0,opr,0)
//	%-E            opr.rela.val:=opr.rela.val+AllignFac;
//	%-E            PresaveOprRegs(opr); Qf4c(qLOAD,0,reg2,cTYP,0,opr,0)
//	%-E            opr.rela.val:=opr.rela.val+AllignFac;
//	%-E            PresaveOprRegs(opr); Qf4c(qLOAD,0,reg3,cTYP,0,opr,0)
//	%-E            opr.rela.val:=opr.rela.val+AllignFac;
//	%-E            Qf4c(qLOAD,0,reg4,cTYP,0,opr,0);
//	%-E            Pop; pushTempVAL(type);
//	%-E       endcase;
//	%-E end;
//
//
//	Visible Routine GetTosValueIn86; import range(0:255) reg;
//	--     /* M} ikke bruke qDI p.g.a. RUPDATE. */
//	begin infix(MemAddr) opr; range(0:MaxType) type; range(0:MaxByte) cTYP;
//	%+D   RST(R_GetTosValueIn86);
//	      type:=TOS.type;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      case 0:K_Max (TOS.kind)
//	      when K_Temp,K_Result,K_Coonst: Qf1(qPOPR,reg,cTYP)
//	      when K_Address:
//	           opr:=GetTosSrcAdr;
//	           Qf4c(qLOAD,0,reg,cTYP,0,opr,0);
//	           Pop; pushTempVAL(type);
//	      endcase;
//	end;
//
//
//	Visible Routine GetTosAdjustedIn86; import range(0:nregs) reg;
//	begin range(0:255) nbyte; infix(ValueItem) itm; range(0:MaxByte) type,cTYP;
//	%+D   RST(R_GetTosAdjustedIn86);
//	%+C   if TOS=none then IERR("CODER.GetTosAdjusted-1") endif;
//	      type:=TOS.type; nbyte:=TTAB(type).nbyte;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GetTosAdjustedIn86-1") endif;
//	%+C   if nbyte > AllignFac
//	%+C   then WARNING("CODER.GetTosAdjusted-2");
//	%+C        repeat while nbyte > AllignFac
//	%+C        do qPOPKill(AllignFac); nbyte:=nbyte-AllignFac endrepeat
//	%+C   endif;
//	      if TOS.kind=K_Coonst
//	      then qPOPKill(nbyte); itm:=TOS qua Coonst.itm;
//	%-E        if type=T_NPADR
//	%-E        then case 0:adrMax (itm.base.kind)
//	%-E             when 0: Qf2(qLOADC,0,reg,cTYP,0) -- NOWHERE/NOBODY
//	%-E             when reladr,locadr: Qf3(qLOADA,0,reg,cTYP,itm.base);
//	%-E             when segadr,fixadr,extadr:
//	%-E                           Qf2b(qLOADC,0,reg,cTYP,F_OFFSET,itm.base);
//	%-E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//	%-E             endcase;
//	%-E        else Qf2(qLOADC,0,reg,cTYP,itm.wrd) endif;
//	%+E        case 0:T_Max (type)
//	%+E        when T_OADDR,T_PADDR,T_RADDR:
//	%+E             case 0:adrMax (itm.base.kind)
//	%+E             when 0: Qf2(qLOADC,0,reg,cTYP,0) -- NONE/NOWHERE/NOBODY
//	%+E             when reladr,locadr: Qf3(qLOADA,0,reg,cTYP,itm.base);
//	%+E             when segadr,fixadr,extadr: Qf2b(qLOADC,0,reg,cTYP,0,itm.base)
//	%+E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//	%+E             endcase;
//	%+E        when T_BOOL,T_CHAR,T_BYT1,T_BYT2,T_WRD2,T_WRD4,
//	%+E             T_REAL,T_SIZE,T_AADDR:
//	%+E             Qf2(qLOADC,0,reg,cTYP,itm.int);
//	%+E %+C    otherwise IERR("CODER:GetTosAdjusted-6");Qf2(qLOADC,0,reg,cTYP,0);
//	%+E        endcase;
//	      else case 0:AllignFac (nbyte)
//	           when 1: GetTosValueIn86(LowPart(%reg%));
//	                   if RegSize(reg) > 1
//	                   then
//	%-E                     Qf2(qLOADC,0,HighPart(%reg%),cTYP,0);
//	%+E                     Qf2(qMOV,qZEXT,LowPart(%reg%),cTYP,LowPart(%reg%));
//	                   endif;
//	           when 2: GetTosValueIn86(WordReg(reg));
//	%+E                if RegSize(reg) > 2
//	%+E                then Qf2(qMOV,qZEXT,WordReg(reg),cTYP,WordReg(reg)) endif;
//	%+E        when 4: GetTosValueIn86(WholeReg(reg));
//	%+C        otherwise IERR("CODER.GetTosAdjusted-5")
//	           endcase;
//	      endif;
//	end;
//
//	visible macro pushFromNPX(2);
//	begin
//	%-E   pushFromNxx(   %2);
//	%+E   pushFromNxx(%1,%2);
//	endmacro
//
//	Visible Routine PushFromNxx;
//	%-E import range(0:MaxType)          totype;
//	%+E import range(0:MaxType) fromtype,totype;
//	begin
//	%-E   Qf1b(qFPUSH,0,Fmf87(totype),cVAL);
//	%+E   Qf1b(qFPUSH,Fwf87(fromtype),Fmf87(totype),cVAL);
//	end;
//
//	Routine PopTosToNPX;  -- M} ikke bruke qDI p.g.a. RUPDATE --
//	begin GQfetch;
//	%-E   Qf1b(qFPOP,0,Fmf87(TOS.type),cVAL);
//	%+E   Qf1b(qFPOP,Fwf87(TOS.type),Fmf87(TOS.type),cVAL);
//	      Pop;
//	end;
//
//
//	Routine GetTosAsBYT1; import range(0:nregs) reg;
//	begin
//	%+D   RST(R_GetTosAsBYT1);
//	%+C   if TOS=none  then IERR("CODER.GetTosAsBYT1-1") endif;
//	%+C   if RegSize(reg) > 1 then IERR("CODER.GetTosAsBYT1-2") endif;
//	%+C   if TTAB(TOS.type).nbyte=0 then IERR("CODER.GetTosAsBYT1-3") endif;
//	      if TTAB(TOS.type).nbyte > 1 then GQconvert(T_BYT1) endif;
//	      if TOS.kind=K_Coonst
//	      then qPOPKill(1); Qf2(qLOADC,0,reg,cVAL,TOS qua Coonst.itm.wrd);
//	      else GetTosValueIn86(reg) endif;
//	end;
//
//	Routine GetTosAsBYT2; import range(0:nregs) reg;
//	begin infix(ValueItem) itm; range(0:MaxByte) type,cTYP;
//	%+D   RST(R_GetTosAsBYT2);
//	%+C   if TOS=none then IERR("CODER.GetTosAsBYT2-1") endif;
//	      type:=TOS.type;
//	%+C   if RegSize(reg) <> 2 then IERR("CODER.GetTosAsBYT2-2") endif;
//	%+C   if TTAB(type).nbyte=0 then IERR("CODER.GetTosAsBYT2-3") endif;
//	      if TTAB(type).nbyte <> 2 then GQconvert(T_BYT2) endif;
//	     
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      if TOS.kind=K_Coonst
//	      then qPOPKill(2); itm:=TOS qua Coonst.itm;
//	%-E        if type=T_NPADR
//	%-E        then case 0:adrMax (itm.base.kind)
//	%-E             when 0: Qf2(qLOADC,0,reg,cTYP,0) --- NOWHERE/NOBODY ---
//	%-E             when reladr,locadr: Qf3(qLOADA,0,reg,cTYP,itm.base);
//	%-E             when segadr,fixadr,extadr:
//	%-E                           Qf2b(qLOADC,0,reg,cTYP,F_OFFSET,itm.base);
//	%-E %+C         otherwise IERR("CODER.GetTosAsBYT2-4")
//	%-E             endcase;
//	%-E        else Qf2(qLOADC,0,reg,cTYP,itm.wrd) endif;
//	%+E        Qf2(qLOADC,0,reg,cTYP,itm.wrd);
//	      else GetTosValueIn86(reg) endif;
//	end;
//
//	Routine GetTosAsBYT4;
//	%-E import range(0:nregs) reg1,reg2;
//	%+E import range(0:nregs) reg;
//	begin
//	%+D   RST(R_GetTosAsBYT4);
//	%+C   if TOS=none then IERR("CODER.GetTosAsBYT4-1") endif;
//	%+C   if TTAB(TOS.type).nbyte=0 then IERR("CODER.GetTosAsBYT4-2") endif;
//	      if TTAB(TOS.type).nbyte <> 4 then GQconvert(T_WRD4) endif;
//	%-E   GetTosValueIn86R3(reg1,reg2,0);
//	%+E   GetTosAdjustedIn86(reg);
//	end;
//
//	%title ***   S t a c k   I t e m s   ***
//
//	Visible Routine NewProfileItem;
//	import range(0:MaxType) type; ref(ProfileDescr) spc;
//	export ref(Object) prf;
//	begin prf:=FreeObj(K_ProfileItem);
//	      if prf <> none
//	      then FreeObj(K_ProfileItem):=prf qua FreeObject.next;
//	      else L: prf:=PoolNxt; PoolNxt:=PoolNxt+size(ProfileItem);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(ProfileItem),prf); goto L endif;
//	%+D        ObjCount(K_ProfileItem):=ObjCount(K_ProfileItem)+1;
//	      endif;
//	      prf.kind:=K_ProfileItem; prf.type:=type;
//	      prf qua StackItem.repdist:=TTAB(type).nbyte;
//	      prf qua StackItem.suc:=none; prf qua StackItem.pred:=none;
//	      prf qua ProfileItem.spc:=spc;
//	      prf qua ProfileItem.nasspar:=0;
//	%+C   if type=T_NOINF then IERR("No info TYPE-1") endif;
//	end;
//
//	Visible Routine NewAddress;
//	import range(0:MaxType) type; range(0:MaxWord) Offset;
//	       infix(MemAddr) Objadr;
//	export ref(Object) adr;
//	begin adr:=FreeObj(K_Address);
//	      if adr <> none
//	      then FreeObj(K_Address):=adr qua FreeObject.next;
//	      else L: adr:=PoolNxt; PoolNxt:=PoolNxt+size(Address);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Address),adr); goto L endif;
//	%+D        ObjCount(K_Address):=ObjCount(K_Address)+1;
//	      endif;
//	      adr.kind:=K_Address; adr.type:=type;
//	      adr qua StackItem.repdist:=TTAB(type).nbyte;
//	      adr qua StackItem.suc:=none; adr qua StackItem.pred:=none;
//	      adr qua Address.Objadr:=Objadr; adr qua Address.Offset:=Offset;
//	      adr qua Address.ObjState:=0; adr qua Address.AtrState:=0;
//	end;
//
//	Visible Routine pushTempVAL;
//	import range(0:MaxType) type;
//	begin ref(Object) tmp;
//	      tmp:=FreeObj(K_Temp);
//	      if tmp <> none
//	      then FreeObj(K_Temp):=tmp qua FreeObject.next;
//	      else L: tmp:=PoolNxt; PoolNxt:=PoolNxt+size(Temp);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Temp),tmp); goto L endif;
//	%+D        ObjCount(K_Temp):=ObjCount(K_Temp)+1;
//	      endif;
//	      tmp.kind:=K_Temp; tmp.type:=type;
//	      tmp qua StackItem.repdist:=TTAB(type).nbyte;
//	      tmp qua StackItem.suc:=none; tmp qua StackItem.pred:=none;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-3") endif;
//	      DOpush(tmp);
//	end;
//
//	Visible Routine pushCoonst;
//	import range(0:MaxType) type; infix(ValueItem) itm;
//	begin ref(Object) cns;
//	      cns:=FreeObj(K_Coonst);
//	      if cns <> none
//	      then FreeObj(K_Coonst):=cns qua FreeObject.next;
//	      else L: cns:=PoolNxt; PoolNxt:=PoolNxt+size(Coonst);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Coonst),cns); goto L endif;
//	%+D        ObjCount(K_Coonst):=ObjCount(K_Coonst)+1;
//	      endif;
//	      cns.kind:=K_Coonst; cns.type:=type;
//	      cns qua StackItem.repdist:=TTAB(type).nbyte;
//	      cns qua StackItem.suc:=none; cns qua StackItem.pred:=none;
//	      cns qua Coonst.itm:=itm;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-4") endif;
//	      DOpush(cns);
//	end;
//	%title ***  STACK INSTRUCTIONS  ***
//
//	Routine Fmf87;
//	import range(0:MaxType) t; export range(0:255) res;
//	begin case 0:T_Max (t)
//	      when T_TREAL: res:=FMF_TEMP
//	      when T_LREAL: res:=FMF_LREAL
//	      when T_REAL:  res:=FMF_REAL
//	      when T_WRD4:  res:=FMF_INT
//	      when T_WRD2:  res:=FMF_SINT
//	%+C   otherwise IERR("CODER.Fmf87"); res:=FMF_SINT
//	      endcase;
//	end;
//
//	%+E Visible Routine Fwf87;
//	%+E import range(0:MaxType) t; export range(0:255) res;
//	%+E begin case 0:T_Max (t)
//	%+E       when T_TREAL: res:=bSHL(FMF_TEMP,5)
//	%+E       when T_LREAL: res:=bSHL(FMF_LREAL,5)
//	%+E       when T_REAL:  res:=bSHL(FMF_REAL,5)
//	%+E       when T_WRD4:  res:=bSHL(FMF_INT,5)
//	%+E       when T_WRD2:  res:=bSHL(FMF_SINT,5)
//	%+EC      otherwise IERR("CODER.Fwf87"); res:=bSHL(FMF_SINT,5)
//	%+E       endcase;
//	%+E end;
//
//	%+D Visible Routine GQfetch  ; --  M} ikke bruke qDI(se rupdate) --
//	%-D Visible Routine GQfetchxx; --  M} ikke bruke qDI(se rupdate) --
//	begin infix(MemAddr) opr; range(0:MaxType) type;
//	      range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	%+D   if TOS.kind=K_Address
//	%+D   then
//	           opr:=GetTosSrcAdr;
//	           type:=TOS.type; nbyte:=TTAB(type).nbyte;
//	           if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	%+D        RST(R_GQfetch);
//	%+D        if TraceMode > 1
//	%+D        then outstring("***GQfetch "); edtype(sysout,type);
//	%+D             outstring("  nbyte="); outword(nbyte);
//	%+D             printout(sysout); DumpStack;
//	%+D        endif;
//	%+C        if nbyte=0 then IERR("CODER.GQfetch-1") endif;
//	           if nbyte <= AllignFac then Qf4(qPUSHM,0,0,cTYP,nbyte,opr)
//	           elsif nbyte <= (3*AllignFac)
//	           then opr.rela.val:=opr.rela.val+nbyte-AllignFac;
//	                repeat if nbyte>AllignFac then PresaveOprRegs(opr) endif;
//	                   Qf4(qPUSHM,0,0,cTYP,AllignFac,opr); nbyte:=nbyte-AllignFac;
//	                while nbyte <> 0
//	                do opr.rela.val:=opr.rela.val-AllignFac endrepeat;
//	           else Qf4(qPUSHM,0,0,cTYP,nbyte,opr) endif;
//	           Pop; pushTempVAL(type);
//	%+D   endif;
//	end;
//	%page
//
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
//	%-E             reg:=GetSreg(opr);
//	%-E             PreMindMask:=wOR(PreMindMask,uMask(reg));
//	%-E             Qf1(qPUSHR,reg,cOBJ);
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
//	%+C        if nbyte=0 then IERR("CODER.GQdup-1") endif;
//	           if nbyte = 1
//	           then Qf1(qPOPR,qAL,cTYP); PreMindMask:=wOR(PreMindMask,uAL);
//	                Qf1(qPUSHR,qAL,cTYP); Qf1(qPUSHR,qAL,cTYP);
//	%-E        elsif nbyte=2
//	%-E        then Qf1(qPOPR,qDX,cTYP); PreMindMask:=wOR(PreMindMask,uDX);
//	%-E             Qf1(qPUSHR,qDX,cTYP); Qf1(qPUSHR,qDX,cTYP);
//	---        then Qf1(qPOPR,qAX,cTYP); PreMindMask:=wOR(PreMindMask,uAX);
//	---             Qf1(qPUSHR,qAX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	%-E        elsif nbyte=4
//	%-E        then Qf1(qPOPR,qDX,cTYP); Qf1(qPOPR,qAX,cTYP);
//	%-E             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E             Qf1(qPUSHR,qAX,cTYP); Qf1(qPUSHR,qDX,cTYP);
//	---        then Qf1(qPOPR,qAX,cTYP); Qf1(qPOPR,qCX,cTYP);
//	---             PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---             Qf1(qPUSHR,qCX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	%-E        elsif nbyte=6
//	%-E        then Qf1(qPOPR,qDX,cTYP); Qf1(qPOPR,qAX,cTYP); Qf1(qPOPR,qCX,cTYP);
//	%-E             PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E             Qf1(qPUSHR,qCX,cTYP);Qf1(qPUSHR,qAX,cTYP);Qf1(qPUSHR,qDX,cTYP);
//	---        then Qf1(qPOPR,qAX,cTYP); Qf1(qPOPR,qCX,cTYP); Qf1(qPOPR,qDX,cTYP);
//	---             PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---             Qf1(qPUSHR,qDX,cTYP);Qf1(qPUSHR,qCX,cTYP);Qf1(qPUSHR,qAX,cTYP);
//	%-E        elsif nbyte=8
//	%-E        then Qf1(qPOPR,qDX,cTYP); Qf1(qPOPR,qAX,cTYP);
//	%-E             Qf1(qPOPR,qCX,cTYP); Qf1(qPOPR,qSI,cTYP);
//	%-E             PreMindMask:=wOR(PreMindMask,uSI); Qf1(qPUSHR,qSI,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP); 
//	%-E             PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E             Qf1(qPUSHR,qSI,cTYP); Qf1(qPUSHR,qCX,cTYP);
//	%-E             Qf1(qPUSHR,qAX,cTYP); Qf1(qPUSHR,qDX,cTYP);
//	---        elsif nbyte=8
//	---        then Qf1(qPOPR,qAX,cTYP); Qf1(qPOPR,qCX,cTYP);
//	---             Qf1(qPOPR,qDX,cTYP); Qf1(qPOPR,qSI,cTYP);
//	---             PreMindMask:=wOR(PreMindMask,uSI); Qf1(qPUSHR,qSI,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP); 
//	---             PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---             Qf1(qPUSHR,qSI,cTYP); Qf1(qPUSHR,qDX,cTYP);
//	---             Qf1(qPUSHR,qCX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	%+E        elsif nbyte=2
//	%+E        then Qf1(qPOPR,qAX,cTYP); PreMindMask:=wOR(PreMindMask,uEAX);
//	%+E             Qf1(qPUSHR,qAX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	%+E        elsif nbyte=4
//	%+E        then Qf1(qPOPR,qECX,cTYP); PreMindMask:=wOR(PreMindMask,uECX);
//	%+E             Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qECX,cTYP);
//	%+E        elsif nbyte=8
//	%+E        then Qf1(qPOPR,qEAX,cTYP); Qf1(qPOPR,qECX,cTYP);
//	%+E             PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP); 
//	%+E             PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E             Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qEAX,cTYP);
//	%+E        elsif nbyte=12
//	%+E        then Qf1(qPOPR,qEAX,cTYP);Qf1(qPOPR,qECX,cTYP);Qf1(qPOPR,qEDX,cTYP);
//	%+E             PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP); 
//	%+E             PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP); 
//	%+E             PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E             Qf1(qPUSHR,qEDX,cTYP); Qf1(qPUSHR,qECX,cTYP);
//	%+E             Qf1(qPUSHR,qEAX,cTYP);
//	           else opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	%-E             Qf2(qMOV,0,qSI,cSTP,qSP); opr.sbireg:=bOR(oSS,rmSI);
//	%+E             Qf2(qMOV,0,qESI,cSTP,qESP); opr.sibreg:=bESI+iESI;
//	                Qf4(qPUSHM,0,0,cTYP,nbyte,opr);
//	           endif;
//	      otherwise IERR("Illegal use of dup(StackItem)");
//	      endcase;
//	end;
//	%page
	
	
	public static void pushConst() {
//	begin ref(Descriptor) d,lab,rut; infix(WORD) tag,atag,smbx;
//	      range(0:MaxWord) nbyte,ofst,i; infix(ValueItem) itm;
//	      ref(LocDescr) atr; range(0:MaxType) type,atype;
//	      infix(MemAddr) adr; range(0:nregs) a;
		Scode.inputInstr();
		switch(Scode.curinstr) {
//	    case Scode.S_C_INT: itm.int:=inint; a:=FreePartReg;
//	           if nextbyte=S_NEG then inputInstr; itm.int:= -itm.int endif
//	           if itm.int >= 0
//	           then if    itm.int <= 255 
//	                then Qf2(qPUSHC,0,LowPart(%a%),cVAL,itm.byt); type:=T_BYT1;
//	                elsif itm.int <= 32767 
//	                then Qf2(qPUSHC,0,WordReg(a),cVAL,itm.wrd); type:=T_WRD2
//	                elsif itm.int <= 65535 then if RNGCHK=0
//	                   then Qf2(qPUSHC,0,WordReg(a),cVAL,itm.wrd); type:=T_BYT2
//	                   else goto L2 endif
//	                else  goto L1  endif; 
//	           elsif itm.int >= -32768
//	           then Qf2(qPUSHC,0,WordReg(a),cVAL,itm.wrd); type:=T_WRD2
//	           else L1:L2:    type:=T_WRD4; GQpushVAL4(itm) endif;
//	           pushCoonst(type,itm);
//	    case Scode.S_C_REAL: itm.rev:=inreal;
//	           GQpushVAL4(itm); pushCoonst(T_REAL,itm);
//	    case Scode.S_C_LREAL: itm.lrv:=inlreal;
//	           GQpushVAL8(itm); pushCoonst(T_LREAL,itm);
//	    case Scode.S_C_CHAR:  itm.int:=0;
//	%+D        itm.byt:=InputByte;
//	%-D        InByte(%itm.byt%);
//	           Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//	           pushCoonst(T_CHAR,itm);
//	    case Scode.S_NOSIZE:  itm.int:=0;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,0); pushCoonst(T_SIZE,itm);
//	    case Scode.S_C_SIZE:  TypeLength:=0; type:=intype;
//	           if type < T_Max then TypeLength:=TTAB(type).nbyte endif;
//	           itm.int:=TypeLength;
//	%-E        Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd); pushCoonst(T_SIZE,itm);
//	%+E        Qf2(qPUSHC,0,qEAX,cVAL,itm.int); pushCoonst(T_SIZE,itm);
//	    case Scode.S_TRUE:    itm.int:=255;
//	           Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,255);
//	           pushCoonst(T_BOOL,itm);
//	    case Scode.S_FALSE:   itm.int:=0;
//	           Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,0);
//	           pushCoonst(T_BOOL,itm);
//	    case Scode.S_ANONE:   itm.int:=0;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,0); pushCoonst(T_AADDR,itm);
//	    case Scode.S_C_AADDR: InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	           itm.int:=atr.rela;
//	%-E        Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd); pushCoonst(T_AADDR,itm);
//	%+E        Qf2(qPUSHC,0,qEAX,cVAL,itm.int); pushCoonst(T_AADDR,itm);
//	    case Scode.S_C_PADDR: InTag(%tag%); lab:=DISPL(tag.HI).elt(tag.LO);
//	           if lab.kind=K_IntLabel then itm.base:=lab qua IntDescr.adr
//	           else itm.base.kind:=extadr;
//	%-E             itm.base.sbireg:=0;
//	%+E             itm.base.sibreg:=NoIBREG;
//	                itm.base.rela.val:=lab qua ExtDescr.adr.rela;
//	                itm.base.smbx:=lab qua ExtDescr.adr.smbx;
//	           endif;
//	           PushAddr(T_PADDR,itm);
//	    case Scode.S_C_RADDR: InTag(%tag%); rut:=DISPL(tag.HI).elt(tag.LO);
//	           if rut.kind=K_IntRoutine then itm.base:=rut qua IntDescr.adr
//	           else itm.base.kind:=extadr; 
//	%-E             itm.base.sbireg:=0;
//	%+E             itm.base.sibreg:=NoIBREG;
//	                itm.base.rela.val:=rut qua ExtDescr.adr.rela;
//	                itm.base.smbx:=rut qua ExtDescr.adr.smbx;
//	           endif;
//	%-E        itm.base.rela.val:=itm.base.rela.val+3;
//	           PushAddr(T_RADDR,itm);
//	    case Scode.S_ONONE:   itm.base.kind:=0; PushAddr(T_OADDR,itm);
//	    case Scode.S_GNONE:   itm.base.kind:=0; PushAddr(T_GADDR,itm);
//	    case Scode.S_NOWHERE: itm.base.kind:=0; PushAddr(T_PADDR,itm);
		case Scode.S_NOBODY:  break; //itm.base.kind:=0; PushAddr(T_RADDR,itm);
	    case Scode.S_C_OADDR: 
	    	Scode.inTag(); //InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	           if d.kind=K_GlobalVar
//	           then itm.base:=d qua IntDescr.adr
//	                if itm.base.kind=0  -- No address attached (Const-spec)
//	                then smbx.val:=0; itm.base:=NewFixAdr(DSEGID,smbx);
//	                     d qua IntDescr.adr:=itm.base;
//	                endif;
//	           elsif d.kind=K_ExternVar
//	           then itm.base.kind:=extadr;
//	%-E             itm.base.sbireg:=0;
//	%+E             itm.base.sibreg:=NoIBREG;
//	                itm.base.rela.val:=d qua ExtDescr.adr.rela;
//	                itm.base.smbx:=d qua ExtDescr.adr.smbx;
//	           else IERR("MINUT: Illegal tag for C-OADDR") endif;
//	           PushAddr(T_OADDR,itm);
	    	break;
//	    case Scode.S_C_GADDR: InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	           if d.kind=K_GlobalVar
//	           then itm.base:=d qua IntDescr.adr
//	                if itm.base.kind=0  -- No address attached (Const-spec)
//	                then smbx.val:=0; itm.base:=NewFixAdr(DSEGID,smbx);
//	                     d qua IntDescr.adr:=itm.base;
//	                endif;
//	           elsif d.kind=K_ExternVar
//	           then itm.base.kind:=extadr;
//	%-E             itm.base.sbireg:=0;
//	%+E             itm.base.sibreg:=NoIBREG;
//	                itm.base.rela.val:=d qua ExtDescr.adr.rela;
//	                itm.base.smbx:=d qua ExtDescr.adr.smbx;
//	           else IERR("MINUT: Illegal tag for C-GADDR") endif;
//	           itm.ofst:=0; PushAddr(T_GADDR,itm);
//	    case Scode.S_C_DOT: ofst:=0;
//	           repeat InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	                  ofst:=ofst+atr.rela; InputInstr
//	           while CurInstr=S_C_DOT do endrepeat;
//	           if CurInstr=S_C_AADDR
//	           then InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	                itm.int:=ofst+atr.rela;
//	%-E             Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd); 
//	%+E             Qf2(qPUSHC,0,FreePartReg,cVAL,itm.int); 
//	                pushCoonst(T_AADDR,itm);
//	           elsif CurInstr=S_C_GADDR
//	           then InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	                if d.kind=K_GlobalVar
//	                then itm.base:=d qua IntDescr.adr
//	                     if itm.base.kind=0  -- No adr attached (Const-spec)
//	                     then smbx.val:=0; itm.base:=NewFixAdr(DSEGID,smbx);
//	                          d qua IntDescr.adr:=itm.base;
//	                     endif;
//	                elsif d.kind=K_ExternVar
//	                then itm.base.kind:=extadr;
//	%-E                  itm.base.sbireg:=0;
//	%+E                  itm.base.sibreg:=NoIBREG;
//	                     itm.base.rela.val:=d qua ExtDescr.adr.rela;
//	                     itm.base.smbx:=d qua ExtDescr.adr.smbx;
//	                else IERR("MINUT: Illegal tag for C-GADDR") endif;
//	                itm.ofst:=ofst; PushAddr(T_GADDR,itm);
//	           else IERR("Illegal termination of C-DOT value") endif;
	    case Scode.S_C_RECORD:
	    	// record_value ::= c-record structured_type
	    	//					<attribute_value>+ endrecord
	    	//		attribute_value ::= attr attribute:tag type repetition_value
	    Scode.inTag(); //InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	           type:=DefStructType(d) nbyte:=TTAB(type).nbyte;
//	           adr:=EmitStructConst(type,nbyte);
//	%-E        if DSEGID=DGROUP then adr.sbireg:=oSS
//	%-E        else Qf2b(qLOADSC,qDS,qAX,cOBJ,0,adr); adr.sbireg:=oDS endif;
//	           Qf4(qPUSHM,0,0,cANY,nbyte,adr); pushTempVAL(type);
    	System.out.println("Coder.pushConst'S_C_RECORD: ");
	    while(Scode.accept(Scode.S_ATTR)) {
	    	Scode.inTag();
	    	Scode.inType();
	    	System.out.println("Coder.pushConst'S_ATTR: ");
//		    Coasm.emitValue();
	    	Util.IERR("HVA GJØR VI HER");
	    }
//	      otherwise goto E endcase;
		}
//	E:end;
	}
	

//	Routine PushAddr;
//	import range(0:MaxType) type; infix(ValueItem) itm;
//	begin case 0:adrMax (itm.base.kind)
//	      when 0: --- ONONE/GNONE/NOWHERE/NOBODY ---
//	%-E           Qf2(qLOADC,0,qAX,cVAL,0)
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); 
//	%-E           Qf1(qPUSHR,qAX,cOBJ); Qf1(qPUSHR,qAX,cOBJ);
//	%+E           Qf2(qPUSHC,0,qEAX,cVAL,0);
//	              itm.Ofst:=0;
//	      when reladr,locadr:
//	%-E           Qf1(qPUSHR,qSS,cOBJ); Qf3(qPUSHA,0,qBX,cOBJ,itm.base);
//	%+E           Qf3(qPUSHA,0,qEBX,cOBJ,itm.base);
//	      when segadr,fixadr,extadr:
//	%-E           Qf2b(qPUSHC,0,FreePartReg,cOBJ,F_BASE,itm.base);
//	%-E           Qf2b(qPUSHC,0,FreePartReg,cOBJ,F_OFFSET,itm.base);
//	%+E           Qf2b(qPUSHC,0,qEAX,cOBJ,0,itm.base);
//	%+C   otherwise IERR("CODER.PushAddr")
//	      endcase;
//	      if type=T_GADDR then Qf2(qPUSHC,0,FreePartReg,cVAL,itm.Ofst) endif;
//	      pushCoonst(type,itm);
//	end;
//	%page
//
//	Visible Routine GQpop;
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
//	      Pop;
//	end;
//
//
//	Visible Routine GQinco_deco; import Boolean inco;
//	begin range(0:255) y;
//	%+C   CheckTosType(T_SIZE); CheckSosValue; CheckSosType(T_OADDR);
//	%-E   GetTosValueIn86(qDX); Pop;
//	%+E   GetTosValueIn86(qEDX); Pop;
//	%-E   GQfetch; Qf1(qPOPR,qAX,cOBJ); Pop;
//	%+E   GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//	      if inco then y:=qINCO else y:=qDECO endif;
//	%-E   Qf2(qDYADR,y,qAX,cOBJ,qDX); Qf1(qPUSHR,qAX,cOBJ);
//	%+E   Qf2(qDYADR,y,qEAX,cOBJ,qEDX); Qf1(qPUSHR,qEAX,cOBJ);
//	      pushTempVAL(T_OADDR);
//	end;
//
//	%title ***  FLOATING-POINT EMULATOR INTERFACE  ***
//	%-E Routine EM4MONAD; import infix(MemAddr) entr;
//	%-E begin GetTosValueIn86R3(qAX,qDX,0); Pop; PreReadMask:=wOR(uAX,uDX);
//	%-E       PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E       Qf5(qCALL,0,0,0,entr); Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM8MONAD; import infix(MemAddr) entr;
//	%-E begin GQfetch; Pop;
//	%-E       PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uDX),wOR(uCX,uBX)));
//	%-E       Qf5(qCALL,0,0,8,entr); Qf1(qPUSHR,qBX,cVAL); Qf1(qPUSHR,qCX,cVAL);
//	%-E       Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM4CNV8; import infix(MemAddr) entr;
//	%-E begin GetTosValueIn86R3(qAX,qDX,0); Pop; PreReadMask:=wOR(uAX,uDX);
//	%-E       PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uDX),wOR(uCX,uBX)));
//	%-E       Qf5(qCALL,0,0,0,entr); Qf1(qPUSHR,qBX,cVAL); Qf1(qPUSHR,qCX,cVAL);
//	%-E       Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM8CNV4; import infix(MemAddr) entr;
//	%-E begin GQfetch; Pop; PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E       Qf5(qCALL,0,0,8,entr); Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM4DYAD; import infix(MemAddr) entr;
//	%-E begin GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E       GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E       PreReadMask:=wOR(wOR(wOR(uAX,uCX),uDX),uBX);
//	%-E       PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E       Qf5(qCALL,0,0,0,entr); Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM8DYAD; import infix(MemAddr) entr;
//	%-E begin GQfetch; Pop; Pop;
//	%-E       PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uDX),wOR(uCX,uBX)));
//	%-E       Qf5(qCALL,0,0,16,entr); Qf1(qPUSHR,qBX,cVAL); Qf1(qPUSHR,qCX,cVAL);
//	%-E       Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E end;
//
//	%-E Routine EM4CMP;
//	%-E begin GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E       GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E       PreReadMask:=wOR(wOR(wOR(uAX,uCX),uDX),uBX);
//	%-E       PreMindMask:=wOR(PreMindMask,uAH);
//	%-E       Qf5(qCALL,0,0,0,X_RECMP); Qf1(qSAHF,0,cVAL);
//	%-E end;
//
//	%-E Routine EM8CMP;
//	%-E begin GQfetch; Pop; Pop; PreMindMask:=wOR(PreMindMask,uAH);
//	%-E       Qf5(qCALL,0,0,16,X_LRCMP); Qf1(qSAHF,0,cVAL);
//	%-E end;
//	%title ***  ARITHMETIC INSTRUCTIONS  ***
//	Visible Routine GQadd;
//	begin range(0:MaxType) at,tt,ts,tr;
//	      range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	%+C   CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	      tt:=TOS.type; ts:=TOS.suc.type; at:=tr:=ArithType(tt,ts);
//	      if TTAB(at).kind=tFloat
//	      then
//	%-E        if NUMID=NoNPX
//	%-E        then if    at=T_REAL then EM4DYAD(X_READD)
//	%-E             elsif at=T_LREAL then EM8DYAD(X_LRADD)
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%-E             Qf1(qFDYAD,qFADD,cVAL);
//	%+E             Qf1b(qFDYAD,qFADD,Fwf87(at),cVAL);
//	-- ?????        tr:=T_TREAL;
//	                PushFromNPX(tr,tr);
//	%-E        endif;
//	      else --- nbyte:=TTAB(at).nbyte;
//	%-E        if RNGCHK <> 0
//	%-E        then if at=T_BYT1 then at:=T_BYT2; tr:=T_WRD2;
//	%-E             elsif at<>T_BYT2
//	%-E             then at:=tr:=T_WRD4; endif
//	%-E        else if at=T_BYT1 then at:=tr:=T_BYT2; endif;
//	%-E        endif;
//	           nbyte:=TTAB(at).nbyte;
//	%+E        if at=T_BYT1  then at:=tr:=T_WRD4; nbyte:=4;
//	%+E        elsif nbyte=2 then at:=tr:=T_WRD4; nbyte:=4; endif;
//
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQadd-1") endif;
//	%-E             Qf2(qDYADR,qADDM,qAX,cVAL,qCX); Qf2(qDYADR,qADCF,qDX,cVAL,qBX)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte); c:=countreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qDYADR,qADDF,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	      endif; pushTempVAL(tr);
//	end;
//	%page
//
//	Visible Routine GQsub;
//	begin range(0:MaxType) at,tt,ts,tr;
//	      range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	%+C   CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	      tt:=TOS.type; ts:=TOS.suc.type; at:=tr:=ArithType(tt,ts);
//	      if TTAB(at).kind=tFloat
//	      then
//	%-E        if NUMID=NoNPX
//	%-E        then if    at=T_REAL then EM4DYAD(X_RESUB)
//	%-E             elsif at=T_LREAL then EM8DYAD(X_LRSUB)
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%-E             Qf1(qFDYAD,qFSUB,cVAL);
//	%+E             Qf1b(qFDYAD,qFSUB,Fwf87(at),cVAL);
//	-- ?????        tr:=T_TREAL;
//	                PushFromNPX(tr,tr);
//	%-E        endif;
//	      else --- nbyte:=TTAB(at).nbyte;
//	%-E        if RNGCHK <> 0
//	%-E        then if at=T_BYT1 then at:=T_BYT2; tr:=T_WRD2;
//	%-E             elsif at=T_BYT2 then tr:=T_WRD2;
//	%-E             else at:=tr:=T_WRD4; endif
//	%-E        else if at=T_BYT1 then at:=T_BYT2; tr:=T_WRD2;
//	%-E             elsif at=T_BYT2 then tr:=T_WRD2 endif;
//	%-E        endif;
//	           nbyte:=TTAB(at).nbyte;
//	%+E        if at=T_BYT1 then at:=tr:=T_WRD4; nbyte:=4
//	%+E        elsif nbyte=2 then at:=tr:=T_WRD4; nbyte:=4 endif;
//
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQsub-1") endif;
//	%-E             Qf2(qDYADR,qSUBM,qAX,cVAL,qCX); Qf2(qDYADR,qSBBF,qDX,cVAL,qBX)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte); c:=countreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qDYADR,qSUBF,a,cVAL,c) Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	      endif; pushTempVAL(tr);
//	end;
//	%page
//
//	Visible Routine GQmult;
//	begin range(0:MaxType) at,tt,ts,tr; range(0:MaxWord) nbyte;
//	%-E   range(0:MaxWord) cnst;
//	%+E   integer          cnst;
//	%+C   CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	      tt:=TOS.type; ts:=TOS.suc.type; at:=tr:=ArithType(tt,ts);
//	      if TTAB(at).kind=tFloat
//	      then
//	%-E        if NUMID=NoNPX
//	%-E        then if    at=T_REAL then EM4DYAD(X_REMUL)
//	%-E             elsif at=T_LREAL then EM8DYAD(X_LRMUL)
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%-E             Qf1(qFDYAD,qFMUL,cVAL);
//	%+E             Qf1b(qFDYAD,qFMUL,Fwf87(at),cVAL);
//	-- ?????        tr:=T_TREAL;
//	                PushFromNPX(tr,tr);
//	%-E        endif;
//	      else --- nbyte:=TTAB(at).nbyte;
//	           if RNGCHK <> 0
//	           then if at=T_BYT2 then goto B2; endif;
//	%-E             if at=T_BYT1 -- special for const < 128
//	%-E             then if TOS.kind=K_Coonst
//	%-E                  then cnst:=TOS qua Coonst.itm.wrd;
//	%-E                  elsif TOS.suc.kind=K_Coonst
//	%-E                  then cnst:=TOS.suc qua Coonst.itm.wrd;
//	%-E                  else cnst:=1000 endif;
//	%-E                  if cnst<128 then if cnst>-128
//	%-E                  then at:=tr:=T_WRD2; goto W2 endif endif;
//	%-E                  at:=T_WRD2;
//	%-E             endif;
//	%-E             if at=T_WRD2
//	%-E             then tr:=T_WRD4; 
//	%-E                  GQconvert(at); GetTosAdjustedIn86(qDX); Pop;
//	%-E                  GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	%-E                  Qf2(qTRIADR,qIMULF,qDX,cVAL,0);
//	%-E                  goto Wp4;
//	%-E             endif;
//	                at:=tr:=T_WRD4;
//	           else if at=T_BYT1 then tr:=T_BYT2; endif;
//	           endif;
//	%+E   B2:
//	           nbyte:=TTAB(at).nbyte;
//	%+E        if nbyte=2 then at:=tr:=T_WRD4; nbyte:=4 endif; --- TEMP?????
//	           if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQmult-1") endif;
//	%-E             PreReadMask:=wOR(wOR(wOR(uAX,uCX),uDX),uBX);
//	%-E             PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E %+S         WARNING("Multiplication of 32bit integers");
//	%-E             Qf5(qCALL,0,0,0,X_IMUL);
//	%-E             if TSTOFL then Qf1(qTSTOFL,0,cVAL) endif
//	%-E   Wp4:      Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E        then if TOS.kind=K_Coonst
//	%+E             then cnst:=TOS qua Coonst.itm.int; GQpop;
//	%+E                  GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  GQeMultc(cnst);
//	%+E             elsif TOS.suc.kind=K_Coonst
//	%+E             then GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  cnst:=TOS qua Coonst.itm.int; GQpop;
//	%+E                  GQeMultc(cnst);
//	%+E             else GQconvert(at); GetTosAdjustedIn86(qEDX); Pop;
//	%+E                  GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  NotMindMask:=uEDX;
//	%+E                  Qf2(qTRIADR,qIMULF,qEDX,cVAL,0);
//	%+E             endif;
//	%+E             Qf1(qPUSHR,qEAX,cVAL);
//	%-E        elsif nbyte=2
//	%-E        then
//	%-E   B2:W2:    if TOS.kind=K_Coonst
//	%-E             then cnst:=TOS qua Coonst.itm.wrd; GQpop;
//	%-E                  if cnst=256
//	%-E                  then if RNGCHK<>0 then goto C2 endif;
//	%-E                       GQconvert(T_BYT1); GetTosAdjustedIn86(qAL); Pop;
//	%-E                       Qf2(qLOADC,0,qAH,cVAL,0);
//	%-E                       Qf2(qXCHG,0,qAL,cVAL,qAH);
//	%-E                  else 
//	%-E                  C2:  GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	%-E                       if at=T_WRD2 then GQiMultc(cnst)
//	%-E                       else              GQwMultc(cnst) endif;
//	%-E                  endif
//	%-E             elsif TOS.suc.kind=K_Coonst
//	%-E             then GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	%-E                  cnst:=TOS qua Coonst.itm.wrd; GQpop;
//	%-E                  if at=T_WRD2 then GQiMultc(cnst)
//	%-E                               else GQwMultc(cnst) endif;
//	%-E             else GQconvert(at); GetTosAdjustedIn86(qDX); Pop;
//	%-E                  GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	%-E                  NotMindMask:=uDX;
//	%-E                  if at=T_WRD2 then Qf2(qTRIADR,qIMULF,qDX,cVAL,0)
//	%-E                               else Qf2(qTRIADR,qWMULF,qDX,cVAL,0) endif;
//	%-E             endif;
//	%-E             Qf1(qPUSHR,qAX,cVAL);
//	           else GQconvert(at); GetTosAdjustedIn86(qCL); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(qAL); Pop;
//	                Qf2(qTRIADR,qIMULF,qCL,cVAL,0); Qf1(qPUSHR,qAX,cVAL);
//	           endif;
//	      endif; pushTempVAL(tr);
//	end;
//	%page
//
//	%+E Visible Routine GQeMultc; import integer cnst;
//	%+E begin integer n; n:=if cnst>0 then cnst else -cnst;
//	%+E   if n > 10 then goto LX endif;
//	%+E   case 0:10 (n)
//	%+E   when 10: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf2(qMOV,0,qEDX,cVAL,qEAX);
//	%+E           Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L6;
//	%+E   when 6: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf2(qMOV,0,qEDX,cVAL,qEAX);
//	%+E       L6: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           Qf2(qDYADR,qADDF,qEAX,cVAL,qEDX);
//	%+E           if cnst<0 then Qf2(qMONADR,qNEGF,qEAX,cVAL,0) endif;
//	%+E   when 8:     Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L4;
//	%+E   when 4: L4: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L2;
//	%+E   when 2: L2: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L1;
//	%+E   when 1: L1: if cnst<0 then Qf2(qMONADR,qNEGF,qEAX,cVAL,0) endif;
//	%+E   otherwise LX: Qf2(qLOADC,0,qEDX,cVAL,cnst);
//	%+E                 NotMindMask:=uEDX; Qf2(qTRIADR,qIMULF,qEDX,cVAL,0);
//	%+E   endcase;
//	%+E end;
//
//	Visible Routine GQiMultc; import short integer cnst;
//	begin range(0:MaxWord) n; if cnst>=0 then n:=cnst else n:=-cnst endif;
//	      if n > 10 then goto LX1 elsif TSTOFL then goto LX2 endif;
//	      case 0:10 (n)
//	      when 10: Qf2(qDYADR,qADD,qAX,cVAL,qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf2(qMOV,0,qDX,cVAL,qAX);
//	              Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L6;
//	      when 6: Qf2(qDYADR,qADD,qAX,cVAL,qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf2(qMOV,0,qDX,cVAL,qAX);
//	          L6: Qf2(qDYADR,qADD,qAX,cVAL,qAX); Qf2(qDYADR,qADD,qAX,cVAL,qDX);
//	              if cnst<0 then Qf2(qMONADR,qNEG,qAX,cVAL,0) endif;
//	      when 8:     Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L4;
//	      when 4: L4: Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L2;
//	      when 2: L2: Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L1;
//	      when 1: L1: if cnst<0 then Qf2(qMONADR,qNEG,qAX,cVAL,0) endif;
//	      otherwise LX1:LX2: Qf2(qLOADC,0,qDX,cVAL,cnst);
//	                    NotMindMask:=uDX; Qf2(qTRIADR,qIMULF,qDX,cVAL,0);
//	      endcase;
//	end;
//
//	Visible Routine GQwMultc; import range(0:MaxWord) cnst;
//	begin if cnst>10 then goto LX1 elsif TSTOFL then goto LX2 endif;
//	      case 0:10 (cnst)
//	      when 10: Qf2(qDYADR,qADD,qAX,cVAL,qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf2(qMOV,0,qDX,cVAL,qAX);
//	              Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L6;
//	      when 6: Qf2(qDYADR,qADD,qAX,cVAL,qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf2(qMOV,0,qDX,cVAL,qAX);
//	          L6: Qf2(qDYADR,qADD,qAX,cVAL,qAX); Qf2(qDYADR,qADD,qAX,cVAL,qDX);
//	      when 8:     Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L4;
//	      when 4: L4: Qf2(qDYADR,qADD,qAX,cVAL,qAX); goto L2;
//	      when 2: L2: Qf2(qDYADR,qADD,qAX,cVAL,qAX);
//	      when 1: -- Nothing
//	      otherwise LX1:LX2: Qf2(qLOADC,0,qDX,cVAL,cnst);
//	                    NotMindMask:=uDX; Qf2(qTRIADR,qWMULF,qDX,cVAL,0);
//	      endcase;
//	end;
//	%page
//
//	Visible Routine GQdiv;
//	begin range(0:MaxType) at,tt,ts,tr; range(0:MaxWord) nbyte;
//	%-E   range(0:MaxWord) cnst;
//	%+E   integer          cnst;
//	%+C   CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	      tt:=TOS.type; ts:=TOS.suc.type; at:=tr:=ArithType(tt,ts);
//	      if TTAB(at).kind=tFloat
//	      then
//	%-E        if NUMID=NoNPX
//	%-E        then if    at=T_REAL then EM4DYAD(X_REDIV)
//	%-E             elsif at=T_LREAL then EM8DYAD(X_LRDIV)
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%-E             Qf1(qFDYAD,qFDIV,cVAL);
//	%+E             Qf1b(qFDYAD,qFDIV,Fwf87(at),cVAL);
//	-- ?????        tr:=T_TREAL;
//	                PushFromNPX(tr,tr);
//	%-E        endif;
//	      else nbyte:=TTAB(at).nbyte;
//	%+E        if nbyte=2 then at:=tr:=T_WRD4; nbyte:=4 endif; --- TEMP?????
//	           if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQdiv-1") endif;
//	%-E             PreReadMask:=wOR(wOR(wOR(uAX,uCX),uDX),uBX);
//	%-E             PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E %+S         WARNING("Division between 32bit integer");
//	%-E             Qf5(qCALL,0,0,0,X_IDIV);
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E        then if TOS.kind=K_Coonst
//	%+E             then cnst:=TOS qua Coonst.itm.int; GQpop;
//	%+E                  GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  GQeDivc(cnst);
//	%+E             else GQconvert(at); GetTosAdjustedIn86(qECX); Pop;
//	%+E                  GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  Qf1(qCWD,qEAX,cVAL); Qf2(qTRIADR,qIDIV,qECX,cVAL,0);
//	%+E             endif;
//	%+E             Qf1(qPUSHR,qEAX,cVAL);
//	           elsif nbyte = 2
//	           then if TOS.kind=K_Coonst
//	                then cnst:=TOS qua Coonst.itm.wrd; GQpop;
//	                     GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	                     if cnst=256
//	                     then if RNGCHK<>0 then goto C2 endif;
//	                          tr:=T_BYT1;
//	                          Qf2(qXCHG,0,qAL,cVAL,qAH);
//	                     else
//	                     C2:  if at=T_WRD2 then GQiDivc(cnst)
//	                          else              GQwDivc(cnst) endif;
//	                     endif
//	                else GQconvert(at); GetTosAdjustedIn86(qCX); Pop;
//	                     GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	                     if at=T_WRD2
//	                     then Qf1(qCWD,qAX,cVAL); Qf2(qTRIADR,qIDIV,qCX,cVAL,0)
//	                     else Qf2(qLOADC,0,qDX,cVAL,0);
//	                          Qf2(qTRIADR,qWDIV,qCX,cVAL,0);
//	                     endif;
//	                endif;
//	                Qf1(qPUSHR,qAX,cVAL);
//	           else GQconvert(at); GetTosAdjustedIn86(qCL); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(qAL); Pop;
//	                Qf2(qLOADC,0,qAH,cVAL,0); Qf2(qTRIADR,qWDIV,qCL,cVAL,0);
//	                Qf1(qPUSHR,qAL,cVAL);
//	           endif;
//	      endif; pushTempVAL(tr);
//	end;
//	%page
//
//	%+E Routine GQeDivc; import integer cnst;
//	%+E begin integer n; n:=if cnst>0 then cnst else -cnst;
//	%+E       if n > 8 then goto LX endif;
//	%+E       case 0:8 (n)
//	%+E       when 8:     Qf2(qMONADR,qSAR1,qEAX,cVAL,0); goto L4;
//	%+E       when 4: L4: Qf2(qMONADR,qSAR1,qEAX,cVAL,0); goto L2;
//	%+E       when 2: L2: Qf2(qMONADR,qSAR1,qEAX,cVAL,0); goto L1;
//	%+E       when 1: L1: if cnst<0 then Qf2(qMONADR,qNEGF,qEAX,cVAL,0) endif;
//	%+E       otherwise LX: Qf2(qLOADC,0,qECX,cVAL,cnst);
//	%+E                     Qf1(qCWD,qEAX,cVAL); Qf2(qTRIADR,qIDIV,qECX,cVAL,0)
//	%+E       endcase;
//	%+E end;
//
//	Routine GQiDivc; import short integer cnst;
//	begin range(0:MaxWord) n; if cnst>=0 then n:=cnst else n:=-cnst endif;
//	      if n > 8 then goto LX endif;
//	      case 0:8 (n)
//	      when 8:     Qf2(qMONADR,qSAR1,qAX,cVAL,0); goto L4;
//	      when 4: L4: Qf2(qMONADR,qSAR1,qAX,cVAL,0); goto L2;
//	      when 2: L2: Qf2(qMONADR,qSAR1,qAX,cVAL,0); goto L1;
//	      when 1: L1: if cnst<0 then Qf2(qMONADR,qNEGF,qAX,cVAL,0) endif;
//	      otherwise LX: Qf2(qLOADC,0,qCX,cVAL,cnst);
//	                    Qf1(qCWD,qAX,cVAL); Qf2(qTRIADR,qIDIV,qCX,cVAL,0)
//	      endcase;
//	end;
//
//	Routine GQwDivc; import range(0:MaxWord) cnst;
//	begin if cnst > 8 then goto LX endif;
//	      case 0:8 (cnst)
//	      when 8:     Qf2(qMONADR,qSHR1,qAX,cVAL,0); goto L4;
//	      when 4: L4: Qf2(qMONADR,qSHR1,qAX,cVAL,0); goto L2;
//	      when 2: L2: Qf2(qMONADR,qSHR1,qAX,cVAL,0);
//	      when 1: -- Nothing
//	      otherwise LX: Qf2(qLOADC,0,qCX,cVAL,cnst); Qf2(qLOADC,0,qDX,cVAL,0);
//	                    Qf2(qTRIADR,qWDIV,qCX,cVAL,0);
//	      endcase;
//	end;
//	%page
//
//	Visible Routine GQrem;
//	begin range(0:MaxType) at,tt,ts,tr; range(0:MaxWord) nbyte,cnst;
//	%+C   CheckTosInt; CheckSosInt; CheckSosValue;
//	      tt:=TOS.type; ts:=TOS.suc.type; at:=tr:=ArithType(tt,ts);
//	%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQrem-2")
//	%+C   else
//	           nbyte:=TTAB(at).nbyte;
//	%+E        if nbyte=2 then at:=tr:=T_WRD4; nbyte:=4 endif; --- TEMP?????
//	           if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQrem-1") endif;
//	%-E             PreReadMask:=wOR(wOR(wOR(uAX,uCX),uDX),uBX);
//	%-E             PreMindMask:=wOR(PreMindMask,wOR(uAX,uDX));
//	%-E %+S         WARNING("Remainder of 32bit integer");
//	%-E             Qf5(qCALL,0,0,0,X_IREM);
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E        then GQconvert(at); GetTosAdjustedIn86(qECX); Pop;
//	%+E             GQconvert(at); GetTosAdjustedIn86(qEAX); Pop;
//	%+E             Qf1(qCWD,qEAX,cVAL); Qf2(qTRIADR,qIMOD,qECX,cVAL,0)
//	%+E             Qf1(qPUSHR,qEDX,cVAL);
//	           elsif nbyte = 2
//	           then if TOS.kind=K_Coonst
//	                then cnst:=TOS qua Coonst.itm.wrd; GQpop;
//	                     if cnst=256
//	                     then if RNGCHK<>0 then goto C2 endif;
//	                          tr:=T_BYT1;
//	                          GQconvert(T_BYT1); GetTosAdjustedIn86(qAL); Pop;
//	                          Qf1(qPUSHR,qAL,cVAL);
//	                     else
//	                     C2:  GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	                          if at=T_WRD2
//	                          then Qf1(qCWD,qAX,cVAL); Qf2(qLOADC,0,qCX,cVAL,cnst);
//	                               Qf2(qTRIADR,qIMOD,qCX,cVAL,0);
//	                               Qf1(qPUSHR,qDX,cVAL);
//	                          else GQwRemc(cnst) endif;
//	                     endif
//	                else GQconvert(at); GetTosAdjustedIn86(qCX); Pop;
//	                     GQconvert(at); GetTosAdjustedIn86(qAX); Pop;
//	                     if at=T_WRD2
//	                     then Qf1(qCWD,qAX,cVAL); Qf2(qTRIADR,qIMOD,qCX,cVAL,0)
//	                     else Qf2(qLOADC,0,qDX,cVAL,0);
//	                          Qf2(qTRIADR,qWMOD,qCX,cVAL,0);
//	                     endif;
//	                     Qf1(qPUSHR,qDX,cVAL);
//	                endif;
//	           else GQconvert(at); GetTosAdjustedIn86(qCL); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(qAL); Pop;
//	                Qf2(qLOADC,0,qAH,cVAL,0); Qf2(qTRIADR,qWMOD,qCL,cVAL,0);
//	                Qf2(qMOV,0,qAL,cVAL,qAH); Qf1(qPUSHR,qAL,cVAL);
//	           endif;
//	%+C   endif;
//	      pushTempVAL(tr);
//	end;
//	%page
//
//	Routine GQwRemc; import range(0:MaxWord) cnst;
//	begin if cnst > 8
//	      then if    cnst=256 then Qf2(qDYADC,qAND,qAX,cVAL,255);
//	           elsif cnst=128 then Qf2(qDYADC,qAND,qAX,cVAL,127);
//	           elsif cnst=64  then Qf2(qDYADC,qAND,qAX,cVAL,63);
//	           elsif cnst=32  then Qf2(qDYADC,qAND,qAX,cVAL,31);
//	           elsif cnst=16  then Qf2(qDYADC,qAND,qAX,cVAL,15);
//	           else goto LX endif;
//	           Qf1(qPUSHR,qAX,cVAL); goto E;
//	      endif;
//	      case 0:8 (cnst)
//	      when 8: Qf2(qDYADC,qAND,qAX,cVAL,7); Qf1(qPUSHR,qAX,cVAL);
//	      when 4: Qf2(qDYADC,qAND,qAX,cVAL,3); Qf1(qPUSHR,qAX,cVAL);
//	      when 2: Qf2(qDYADC,qAND,qAX,cVAL,1); Qf1(qPUSHR,qAX,cVAL);
//	      when 1: Qf1(qPUSHR,qAX,cVAL);
//	      otherwise LX: Qf2(qLOADC,0,qCX,cVAL,cnst); Qf2(qLOADC,0,qDX,cVAL,0);
//	                    Qf2(qTRIADR,qWMOD,qCX,cVAL,0); Qf1(qPUSHR,qDX,cVAL);
//	      endcase;
//	E:end;
//	%page
//	Visible Routine GQneg;
//	begin range(0:MaxType) at;
//	%+C   CheckTosArith;
//	--- ??? %-E   at:=TOS.type;
//	--- ??? %-E   if at=T_BYT2 then at:=arithType(at,T_WRD4)  ----- TEMP ???
//	--- ??? %-E   else at:=ArithType(at,T_WRD2) endif;  ----  TEMP ????????
//	%-E   at:=ArithType(TOS.type,T_WRD2);         ----  TEMP ????????
//	%+E   at:=ArithType(TOS.type,T_WRD4);         ----  TEMP ????????
//	      if TTAB(at).kind=tFloat
//	      then
//	%-E        if NUMID=NoNPX
//	%-E        then if    at=T_REAL then EM4MONAD(X_RENEG)
//	%-E             elsif at=T_LREAL then EM8MONAD(X_LRNEG)
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	%-E             Qf1(qFMONAD,qFNEG,cVAL);
//	%+E             Qf1b(qFMONAD,qFNEG,Fwf87(at),cVAL);
//	-- ?????        at:=T_TREAL;
//	                PushFromNPX(at,at);
//	%-E        endif;
//	      else GQconvert(at);
//	           if at = T_WRD4
//	%-E        then GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E             Qf2(qMONADR,qNEGM,qAX,cVAL,0);
//	%-E             Qf2(qDYADC,qADCF,qDX,cVAL,0)
//	%-E             Qf2(qMONADR,qNEGF,qDX,cVAL,0);
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E        then GetTosAdjustedIn86(qEAX); Pop;
//	%+E             Qf2(qMONADR,qNEGF,qEAX,cVAL,0); Qf1(qPUSHR,qEAX,cVAL);
//	           else GetTosAdjustedIn86(qAX); Pop;
//	                Qf2(qMONADR,qNEGF,qAX,cVAL,0); Qf1(qPUSHR,qAX,cVAL);
//	           endif;
//	      endif; pushTempVAL(at);
//	end;
//
//
//	Visible Routine GQsqrt;
//	begin range(0:MaxType) at;
//	%+C   CheckTosArith;
//	      at:=ArithType(TOS.type,T_REAL);         ----  TEMP ????????
//	-- ??? %-E   if NUMID=NoNPX
//	-- ??? %-E   then if    at=T_REAL then EM4MONAD(X_RSQRT);
//	-- ??? %-E        elsif at=T_LREAL then EM8MONAD(X_LSQRT);
//	-- ??? %-E        endif;
//	-- ??? %-E   else
//	                  GQconvert(at); PopTosToNPX;
//	-- ??? %-E        Qf1(qFMONAD,qFSQRT,cVAL);
//	%+E               Qf1b(qFMONAD,qFSQRT,Fwf87(at),cVAL);
//	-- ?????          at:=T_TREAL;
//	                  PushFromNPX(at,at);
//	-- ??? %-E   endif;
//	      Pop; --- Profile Item
//	      pushTempVAL(at);
//	end;
//	%title ***  LOGICAL INSTRUCTIONS  ***
//	Visible Routine GQnot;
//	begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a;
//	      at:=TOS.type;
//	      if at <> T_BOOL
//	      then at:=ArithType(at,T_BYT1);
//	%+C        CheckTosArith;
//	      endif;
//	%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQnot-1")
//	%+C   else
//	           nbyte:=TTAB(at).nbyte;
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQnot-2") endif;
//	%-E             Qf2(qMONADR,qNOT,qAX,cVAL,0); Qf2(qMONADR,qNOT,qDX,cVAL,0)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qMONADR,qNOT,a,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	%+C   endif;
//	      pushTempVAL(at);
//	end;
//
//	Visible Routine GQandxor; import range(0:maxbyte) bop;
//	begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	      at:=TOS.type;
//	      if at <> T_BOOL
//	      then at:=ArithType(at,TOS.suc.type);
//	%+C        CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	%+C   else CheckSosValue; CheckSosType(T_BOOL);
//	      endif;
//	%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQandxor-1")
//	%+C   else
//	           nbyte:=TTAB(at).nbyte;
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQand-2") endif;
//	%-E             Qf2(qDYADR,bop,qAX,cVAL,qCX); Qf2(qDYADR,bop,qDX,cVAL,qBX)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte); c:=countreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qDYADR,bop,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	%+C   endif;
//	      pushTempVAL(at);
//	end;
//
//	--Visible Routine GQor;
//	--begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	--      at:=TOS.type;
//	--      if at <> T_BOOL
//	--      then at:=ArithType(at,TOS.suc.type);
//	--%+C        CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	--%+C   else CheckSosValue; CheckSosType(T_BOOL);
//	--      endif;
//	--%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQor-1")
//	--%+C   else
//	--           nbyte:=TTAB(at).nbyte;
//	--%-E        if nbyte > 2
//	--%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	--%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	--%-E %+C         if at <> T_WRD4 then IERR("CODER.GQor-2") endif;
//	--%-E             Qf2(qDYADR,qOR,qAX,cVAL,qCX); Qf2(qDYADR,qOR,qDX,cVAL,qBX)
//	--%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	--%-E        else
//	--                a:=accreg(nbyte); c:=countreg(nbyte);
//	--                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	--                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	--                Qf2(qDYADR,qOR,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	--%-E        endif;
//	--%+C   endif;
//	--      pushTempVAL(at);
//	--end;
//
//	--Visible Routine GQxor;
//	--begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	--      at:=TOS.type;
//	--      if at <> T_BOOL
//	--      then at:=ArithType(at,TOS.suc.type);
//	--%+C        CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	--%+C   else CheckSosValue; CheckSosType(T_BOOL);
//	--      endif;
//	--%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQxor-1")
//	--%+C   else
//	--           nbyte:=TTAB(at).nbyte;
//	--%-E        if nbyte > 2
//	--%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	--%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	--%-E %+C         if at <> T_WRD4 then IERR("CODER.GQxor-2") endif;
//	--%-E             Qf2(qDYADR,qXOR,qAX,cVAL,qCX); Qf2(qDYADR,qXOR,qDX,cVAL,qBX)
//	--%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	--%-E        else
//	--                a:=accreg(nbyte); c:=countreg(nbyte);
//	--                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	--                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	--                Qf2(qDYADR,qXOR,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	--%-E        endif;
//	--%+C   endif;
//	--      pushTempVAL(at);
//	--end;
//
//	Visible Routine GQeqv;
//	begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	      at:=TOS.type;
//	      if at <> T_BOOL
//	      then at:=ArithType(at,TOS.suc.type);
//	%+C        CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	%+C   else CheckSosValue; CheckSosType(T_BOOL);
//	      endif;
//	%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQeqv-1")
//	%+C   else
//	           nbyte:=TTAB(at).nbyte;
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQeqv-2") endif;
//	%-E             Qf2(qMONADR,qNOT,qAX,cVAL,0); Qf2(qDYADR,qXOR,qAX,cVAL,qCX);
//	%-E             Qf2(qMONADR,qNOT,qDX,cVAL,0); Qf2(qDYADR,qXOR,qDX,cVAL,qBX)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte); c:=countreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qMONADR,qNOT,a,cVAL,0);
//	                Qf2(qDYADR,qXOR,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	%+C   endif;
//	      pushTempVAL(at);
//	end;
//
//	Visible Routine GQimp;
//	begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a,c;
//	      at:=TOS.type;
//	      if at <> T_BOOL
//	      then at:=ArithType(at,TOS.suc.type);
//	%+C        CheckTosArith; CheckSosArith; CheckSosValue; CheckTypesEqual;
//	%+C   else CheckSosValue; CheckSosType(T_BOOL);
//	      endif;
//	%+C   if TTAB(at).kind=tFloat then IERR("CODER.GQimp-1")
//	%+C   else
//	           nbyte:=TTAB(at).nbyte;
//	%-E        if nbyte > 2
//	%-E        then GQconvert(at); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E             GQconvert(at); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E %+C         if at <> T_WRD4 then IERR("CODER.GQimp-2") endif;
//	%-E             Qf2(qMONADR,qNOT,qAX,cVAL,0); Qf2(qDYADR,qOR,qAX,cVAL,qCX);
//	%-E             Qf2(qMONADR,qNOT,qDX,cVAL,0); Qf2(qDYADR,qOR,qDX,cVAL,qBX)
//	%-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%-E        else
//	                a:=accreg(nbyte); c:=countreg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(c); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                Qf2(qMONADR,qNOT,a,cVAL,0);
//	                Qf2(qDYADR,qOR,a,cVAL,c); Qf1(qPUSHR,a,cVAL);
//	%-E        endif;
//	%+C   endif;
//	      pushTempVAL(at);
//	end;
//
//	%+S Visible Routine GQshift; import range(0:maxbyte) code;
//	%+S begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a;
//	%+S %+C   CheckTosInt; CheckSosArith; CheckSosValue;
//	%+S       GetTosAdjustedIn86(qCX); Pop; at:=TOS.type;
//	%+S %+C   if TTAB(at).kind=tFloat then IERR("CODER.GQshl-1")
//	%+S %+C   else
//	%+S            nbyte:=TTAB(at).nbyte;
//	%+S %-E        if nbyte > 2
//	%+S %-E        then GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%+S %-E %+C         if at <> T_WRD4 then IERR("CODER.GQshl-2") endif;
//	%+S %-E             Qf2(qSHIFT,code,qAX,cVAL,qCX); Qf2(qSHIFT,code,qDX,cVAL,qCX)
//	%+S %-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+S %-E        else
//	%+S                 a:=accreg(nbyte); GetTosAdjustedIn86(a); Pop;
//	%+S                 Qf2(qSHIFT,code,a,cVAL,qCX); Qf1(qPUSHR,a,cVAL);
//	%+S %-E        endif;
//	%+S %+C   endif;
//	%+S       pushTempVAL(at);
//	%+S end;
//
//	-- Visible Routine GQshr;
//	-- begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a;
//	-- %+C   CheckTosInt; CheckSosArith; CheckSosValue;
//	--       GetTosAdjustedIn86(qCX); Pop; at:=TOS.type;
//	-- %+C   if TTAB(at).kind=tFloat then IERR("CODER.GQshr-1")
//	-- %+C   else
//	--            nbyte:=TTAB(at).nbyte;
//	-- %-E        if nbyte > 2
//	-- %-E        then GetTosValueIn86R3(qAX,qDX,0); Pop;
//	-- %-E %+C         if at <> T_WRD4 then IERR("CODER.GQshr-2") endif;
//	-- %-E             Qf2(qSHIFT,qSHR,qAX,cVAL,qCX); Qf2(qSHIFT,qSHR,qDX,cVAL,qCX)
//	-- %-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	-- %-E        else
//	--                 a:=accreg(nbyte); GetTosAdjustedIn86(a); Pop;
//	--                 Qf2(qSHIFT,qSHR,a,cVAL,qCX); Qf1(qPUSHR,a,cVAL);
//	-- %-E        endif;
//	-- %+C   endif;
//	--       pushTempVAL(at);
//	-- end;
//
//	-- Visible Routine GQsar;
//	-- begin range(0:MaxType) at; range(0:MaxWord) nbyte; range(0:nregs) a;
//	-- %+C   CheckTosInt; CheckSosArith; CheckSosValue;
//	--       GetTosAdjustedIn86(qCX); Pop; at:=TOS.type;
//	-- %+C   if TTAB(at).kind=tFloat then IERR("CODER.GQsar-1")
//	-- %+C   else
//	--            nbyte:=TTAB(at).nbyte;
//	-- %-E        if nbyte > 2
//	-- %-E        then GetTosValueIn86R3(qAX,qDX,0); Pop;
//	-- %-E %+C         if at <> T_WRD4 then IERR("CODER.GQsar-2") endif;
//	-- %-E             Qf2(qSHIFT,qSAR,qAX,cVAL,qCX); Qf2(qSHIFT,qSAR,qDX,cVAL,qCX)
//	-- %-E             Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	-- %-E        else
//	--                 a:=accreg(nbyte); GetTosAdjustedIn86(a); Pop;
//	--                 Qf2(qSHIFT,qSAR,a,cVAL,qCX); Qf1(qPUSHR,a,cVAL);
//	-- %-E        endif;
//	-- %+C   endif;
//	--       pushTempVAL(at);
//	-- end;
//
//	%title ***  A s s i g n m e n t  ***
//	Visible Routine GQassign;
//	begin infix(MemAddr) opr; infix(MemAddr) adr;
//	      range(0:MaxType) st,dt; range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	%-E   range(0:nregs) segreg;
//	%+C   CheckSosRef; CheckTypesEqual;
//	      st:=TOS.type; dt:=TOS.suc.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQassign-1") endif;
//	%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL); Pop; opr:=GetTosDstAdr;
//	              Qf3(qSTORE,0,qAL,cTYP,opr);
//	      when 2: GetTosAsBYT2(qAX); Pop; opr:=GetTosDstAdr;
//	              Qf3(qSTORE,0,qAX,cTYP,opr);
//	---   when 4: GetTosAsBYT4(qCX,qAX); Pop; opr:=GetTosDstAdr;
//	---           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	---           opr.rela.val:=opr.rela.val+AllignFac;
//	---           Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E   when 4: GetTosAsBYT4(qAX,qCX); Pop; opr:=GetTosDstAdr;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E   when 6: GetTosValueIn86R3(qAX,qCX,qDX); Pop;
//	%-E           opr:=GetTosDstAdr;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E   when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI); Pop;
//	%-E           opr:=GetTosDstAdr;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qSI,cTYP,opr);
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
//	%-E             if bAND(opr.sbireg,oSREG) <> oDS
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E             endif;
//	%-E             Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E             Qf3(qLOADA,0,qESI,cADR,opr);
//	                Pop; opr:=GetTosDstAdr;
//	%-E             if bAND(opr.sbireg,oSREG) <> oES
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E             endif;
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);  Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr); Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	           else -- tos.mode=m_val and tos.kind<>K_Content --
//	%-E             opr:=GetSosAddr(qES,qBX,qDI);
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
//	%page
//
//	Visible Routine GQupdate;
//	begin infix(MemAddr) opr; infix(MemAddr) loc; ref(StackItem) x;
//	      range(0:MaxType) st,dt; range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	%-E   range(0:nregs) segreg;
//	%+C   CheckSosRef; CheckTypesEqual;
//	      st:=TOS.type; dt:=TOS.suc.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQupdate-1") endif;
//	%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL);
//	              x:=TakeTOS; opr:=GetTosDstAdr;
//	              PreMindMask:=wOR(PreMindMask,uAL);
//	              Qf1(qPUSHR,qAL,cTYP); Qf3(qSTORE,0,qAL,cTYP,opr);
//	      when 2: GetTosAsBYT2(qAX);
//	              x:=TakeTOS; opr:=GetTosDstAdr;
//	              PreMindMask:=wOR(PreMindMask,uAX);
//	              Qf1(qPUSHR,qAX,cTYP); Qf3(qSTORE,0,qAX,cTYP,opr);
//	---   when 4: GetTosAsBYT4(qCX,qAX);
//	---           x:=TakeTOS; opr:=GetTosDstAdr;
//	---           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	---           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	---           opr.rela.val:=opr.rela.val+AllignFac;
//	---           Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E   when 4: GetTosAsBYT4(qAX,qCX);
//	%-E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E   when 6: GetTosValueIn86R3(qAX,qCX,qDX);
//	%-E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%-E           PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E   when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI);
//	%-E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%-E           PreMindMask:=wOR(PreMindMask,uSI); Qf1(qPUSHR,qSI,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qSI,cTYP,opr);
//	%+E   when 4: GetTosAsBYT4(qEAX);
//	%+E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX);
//	%+E           Qf1(qPUSHR,qEAX,cTYP); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	%+E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//	%+E           x:=TakeTOS; opr:=GetTosDstAdr;
//	%+E           PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           PresaveOprRegs(opr);
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	           if TOS.kind=K_Address
//	           then opr:=GetTosSrcAdr;
//	%-E             if bAND(opr.sbireg,oSREG) <> oDS
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E             endif;
//	%-E             Pop; Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E             Pop; Qf3(qLOADA,0,qESI,cADR,opr);
//	                opr:=GetTosDstAdr;
//	%-E             if bAND(opr.sbireg,oSREG) <> oES
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E             endif;
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E             PreMindMask:=wOR(PreMindMask,uDI); Qf1(qPUSHR,qDI,cADR);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr);
//	%+E             PreMindMask:=wOR(PreMindMask,uEDI); Qf1(qPUSHR,qEDI,cADR);
//	                pushTempVAL(st); x:=takeTOS;
//	%-E             Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	%-E             Qf1(qPOPR,qDI,cADR);
//	%-E             opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E             Qf1(qPOPR,qEDI,cADR); opr.sibreg:=bEDI+iEDI;
//	                opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	                Qf4(qPUSHM,0,0,cTYP,nbyte,opr);
//	           else -- tos.mode=m_val: Record value on stack --
//	                if TOS.suc qua Address.ObjState = NotStacked
//	                then -- No biregs below record value --
//	%-E                  opr:=GetSosAddr(qES,qBX,qDI);
//	%+E                  opr:=GetSosAddr(qEBX,qEDI);
//	%-E                  if bAND(opr.sbireg,oSREG) <> oES
//	%-E                  then segreg:=GetSreg(opr);
//	%-E                       PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                       Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E                  endif;
//	                     Pop;
//	%-E                  Qf3(qLOADA,0,qDI,cADR,opr); Qf2(qMOV,0,qSI,cSTP,qSP);
//	%+E                  Qf3(qLOADA,0,qEDI,cADR,opr); Qf2(qMOV,0,qESI,cSTP,qESP);
//	%-E                  Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E                  Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E                  Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                     Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	                     pop; pushTempVAL(st);
//	                else -- Biregs below record value --
//	%-E                  opr:=GetSosAddr(qES,qBX,qDI);
//	%+E                  opr:=GetSosAddr(qEBX,qEDI);
//	                     Qf4(qPOPM,0,qAL,cTYP,nbyte,opr);
//	                     Pop; GQfetch;
//	                endif;
//	                goto E;
//	           endif;
//	      endcase;
//	      Pop; Push(x);
//	E:end;
//	%page
//
//	Visible Routine GQrassign;
//	begin infix(MemAddr) opr; range(0:MaxWord) nbyte;
//	      range(0:MaxType) st,dt; range(0:MaxByte) cTYP;
//	%+C   CheckTosRef; CheckSosValue; CheckTypesEqual;
//	      st:=TOS.suc.type; dt:=TOS.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQrassign-1") endif;
//	      opr:=GetTosDstAdr; Pop;
//	      if TOS.kind=K_Address
//	      then
//	%-E        Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E        opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E        Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	           opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	      endif;
//	%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL); Qf3(qSTORE,0,qAL,cTYP,opr)
//	      when 2: GetTosAsBYT2(qAX); Qf3(qSTORE,0,qAX,cTYP,opr)
//	---   when 4: GetTosAsBYT4(qCX,qAX);
//	---           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	---           opr.rela.val:=opr.rela.val+AllignFac;
//	---           Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E   when 4: GetTosAsBYT4(qAX,qCX);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E   when 6: GetTosValueIn86R3(qAX,qCX,qDX);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E   when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qSI,cTYP,opr);
//	%+E   when 4: GetTosAsBYT4(qEAX); Qf3(qSTORE,0,qEAX,cTYP,opr)
//	%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	              if TOS.kind <> K_Address
//	              then
//	%-E                Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E                opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E                Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	                   opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	              endif;
//	              GQfetch; Qf4(qPOPM,0,qAL,cTYP,nbyte,opr);
//	      endcase;
//	      Pop;
//	end;
//	%page
//
//	Visible Routine GQrupdate;
//	begin infix(MemAddr) opr; range(0:MaxByte) cTYP;
//	      range(0:MaxWord) nbyte; range(0:MaxType) st,dt;
//	%-E   range(0:nregs) segreg;
//	%+C   CheckTosRef; CheckSosValue; CheckTypesEqual;
//	      st:=TOS.suc.type; dt:=TOS.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GQrupdate-1") endif;
//	      opr:=GetTosDstAdr; Pop;
//	      if TOS.kind=K_Address
//	      then
//	%-E        Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E        opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E        Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	           opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	      endif;
//	%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//	%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL);
//	              PreMindMask:=wOR(PreMindMask,uAL); Qf1(qPUSHR,qAL,cTYP);
//	              Qf3(qSTORE,0,qAL,cTYP,opr)
//	      when 2: GetTosAsBYT2(qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	              Qf3(qSTORE,0,qAX,cTYP,opr)
//	---   when 4: GetTosAsBYT4(qCX,qAX);
//	---           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	---           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	---           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	---           opr.rela.val:=opr.rela.val+AllignFac;
//	---           Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E   when 4: GetTosAsBYT4(qAX,qCX);
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E   when 6: GetTosValueIn86R3(qAX,qCX,qDX);
//	%-E           PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E   when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI);
//	%-E           PreMindMask:=wOR(PreMindMask,uSI); Qf1(qPUSHR,qSI,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uDX); Qf1(qPUSHR,qDX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uCX); Qf1(qPUSHR,qCX,cTYP);
//	%-E           PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qAX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qCX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           PresaveOprRegs(opr); Qf3(qSTORE,0,qDX,cTYP,opr);
//	%-E           opr.rela.val:=opr.rela.val+AllignFac;
//	%-E           Qf3(qSTORE,0,qSI,cTYP,opr);
//	%+E   when 4: GetTosAsBYT4(qEAX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           Qf3(qSTORE,0,qEAX,cTYP,opr)
//	%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//	%+E           opr.rela.val:=opr.rela.val+AllignFac;
//	%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	           if TOS.kind <> K_Address
//	           then
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//	%-E             opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	                opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	           endif;
//	%-E        if bAND(opr.sbireg,oSREG) <> oES
//	%-E        then segreg:=GetSreg(opr);
//	%-E             PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E             Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        endif;
//	           GQfetch;
//	%-E        Qf2(qMOV,0,qSI,cSTP,qSP); Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%+E        Qf2(qMOV,0,qESI,cSTP,qESP);
//	%-E        Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E        Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	           Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	      endcase;
//	end;
//
//	%title ***   R  e  l  a  t  i  o  n   ***
//
//	Visible Routine GQrelation;
//	%-E   import boolean jmprel;
//	      export range(0:255) res;
//	begin range(0:MaxType) at; range(0:255) a,d,qCond,qType; range(0:255) srel;
//	      infix(MemAddr) opr; range(0:MaxWord) s,nbyte; range(0:MaxByte) cTYP;
//	%-E   range(0:nregs) segreg;
//	      xFJUMP:=none;
//	      inputInstr; srel:=curInstr;
//	%+C   CheckTypesEqual; CheckSosValue;
//	%+D   if (srel<S_LT) or (srel>S_NE) then IERR("Illegal relation") endif;
//	      at:=TOS.type; reversed:=false;
//	      if at <= T_max then at:=ArithType(at,TOS.suc.type); cTYP:=cTYPE(at)
//	      else cTYP:=cANY endif;
//	      nbyte:=TTAB(at).nbyte;
//	%+C   if nbyte=0 then IERR("CODER.GQrel-0") endif;
//	      if TTAB(at).kind=tFloat
//	      then reversed:=false;
//	%-E        if NUMID=NoNPX
//	%-E        then
//	%-E             if    at=T_REAL  then EM4CMP; goto E1
//	%-E             elsif at=T_LREAL then EM8CMP; goto E3;
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%+S             WARNING("Floating point Relation");
//	%-E             Qf1(qFDYAD,qFCOM,cTYP);
//	%+E             Qf1b(qFDYAD,qFCOM,Fwf87(at),cTYP);
//	%-E        endif;
//	      else
//	           if nbyte <= AllignFac
//	           then a:=accreg(nbyte); d:=datareg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(d); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                reversed:=false; Qf2(qDYADR,qCMP,a,cTYP,d);
//	%-E        elsif at=T_WRD4 then res:=GQrel32(srel,jmprel); goto E2
//	%+E        elsif nbyte=8
//	%+E        then GetTosValueIn86R3(qEAX,qEDX,0); Pop;
//	%+E             GetTosValueIn86R3(qECX,qEBX,0); Pop;
//	%+E             Qf2(qDYADR,qCMP,qECX,cTYP,qEAX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qEBX,cTYP,qEDX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%+E             Qf1(qSAHF,0,cVAL);
//	%+E        elsif nbyte=12
//	%+E        then GetTosValueIn86R3(qEAX,qECX,qEDX); Pop;
//	%+E             GQfetch; -- To prevent SI from being destroyed
//	%+E             GetTosValueIn86R3(qEDI,qESI,qEBX); Pop;
//	%+E             Qf2(qDYADR,qCMP,qEDI,cTYP,qEAX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qESI,cTYP,qECX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qEBX,cTYP,qEDX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%+E             Qf1(qSAHF,0,cVAL);
//	%-E        elsif nbyte <= (3*AllignFac)
//	%-E        then if srel=S_EQ then goto EN1 elsif srel=S_NE
//	%-E             then EN1:
//	%-E                  if nbyte=6 -- Gaddr etc.
//	%-E                  then GetTosValueIn86R3(qAX,qCX,qDX); Pop;
//	%-E                       GQfetch; -- To prevent SI from being destroyed
//	%-E                       GetTosValueIn86R3(qDI,qSI,qBX); Pop;
//	%-E                       Qf2(qDYADR,qCMP,qAX,cTYP,qDI);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%-E                       Qf2(qDYADR,qCMP,qCX,cTYP,qSI);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAL,cVAL,qAH);
//	%-E                       Qf2(qDYADR,qCMP,qDX,cTYP,qBX);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%-E                       Qf1(qSAHF,0,cVAL);
//	%-E                  else if IsONONE(TOS)
//	%-E                       then GQpop; GQfetch;
//	%-E                            qPOPKill(2); Qf1(qPOPR,qAX,cOBJ);
//	%-E                            NotMindMask:=uAX;
//	%-E                            Pop; Qf2(qDYADR,qORM,qAX,cOBJ,qAX);
//	%-E                       elsif IsONONE(TOS.suc)
//	%-E                       then GQfetch; qPOPKill(2);
//	%-E                            Qf1(qPOPR,qAX,cOBJ); Pop; GQpop;
//	%-E                            NotMindMask:=uAX;
//	%-E                            Qf2(qDYADR,qORM,qAX,cOBJ,qAX);
//	%-E                       else GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E                            GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E                            Qf2(qDYADR,qCMP,qAX,cOBJ,qCX);
//	%-E                            if jmprel
//	%-E                            then mindMask:=wOR(uSPBPM,uF);
//	%-E                                 xFJUMP:=forwJMP(q_WNE);
//	%-E                                 MindMask:=wOR(uDX,uBX);
//	%-E                                 Qf2(qDYADR,qCMP,qDX,cOBJ,qBX);
//	%-E                                 mindMask:=wOR(uSPBPM,uF);
//	%-E                            else Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%-E                                 Qf2(qDYADR,qCMP,qDX,cOBJ,qBX);
//	%-E                                 Qf1(qLAHF,0,cVAL);
//	%-E                                 Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%-E                                 Qf1(qSAHF,0,cVAL);
//	%-E                            endif
//	%-E                       endif
//	%-E                  endif;
//	%-E                  reversed:=false;
//	%-E             else -- oaddr < <= > >=
//	%-E %+C              if at <> T_OADDR then IERR("CODER.GQrel-1") endif;
//	%-E                  GQfetch; Qf1(qPOPR,qDX,cOBJ); qPOPKill(2); Pop;
//	%-E                  GQfetch; Qf1(qPOPR,qAX,cOBJ); qPOPKill(2); Pop;
//	%-E                  reversed:=false;
//	%-E                  Qf2(qDYADR,qCMP,qAX,cOBJ,qDX);
//	%-E             endif;
//	           elsif (TOS.kind<>K_Address)
//	           then
//	%-E             Qf2(qMOV,0,qDI,cSTP,qSP);
//	%-E             Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%+E             Qf2(qMOV,0,qEDI,cSTP,qESP);
//	                if TOS.suc.kind=K_Address
//	                then
//	%-E                  opr:=GetSosAddr(qDS,qBX,qSI);
//	%+E                  opr:=GetSosAddr(qEBX,qESI);
//	%-E                  if bAND(opr.sbireg,oSREG) <> oDS
//	%-E                  then segreg:=GetSreg(opr);
//	%-E                       PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                       Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E                  endif;
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                else
//	%-E                  Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	                     opr.kind:=reladr; opr.segmid.val:=0;
//	                     opr.rela.val:=wAllign(%nbyte%);
//	%-E                  opr.sbireg:=rmDI;PreMindMask:=wOR(PreMindMask,uDI)
//	%+E                  opr.sibreg:=bEDI+iEDI;
//	%+E                  PreMindMask:=wOR(PreMindMask,uEDI)
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%-E             Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                Qf1(qLAHF,0,cVAL); qPOPKill(nbyte);
//	                if TOS.suc.kind=K_Address
//	                then repeat while SosAdrNwk>0
//	                     do qPOPKill(AllignFac);
//	                        SosAdrNwk:=SosAdrNwk-1;
//	                     endrepeat;
//	                else qPOPKill(nbyte) endif;
//	                Qf1(qSAHF,0,cVAL); Pop; Pop;
//	           else -- TOS.kind=K_Address or TOS.kind=K_Content */
//	                opr:=GetTosDstAdr;
//	%-E             if bAND(opr.sbireg,oSREG) <> oES
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E             endif;
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr);
//	                Pop;
//	                if  (TOS.kind<>K_Address)
//	                then s:=nbyte;
//	%-E                  Qf2(qMOV,0,qSI,cSTP,qSP);
//	%-E                  Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%+E                  Qf2(qMOV,0,qESI,cSTP,qESP);
//	                else s:=0;
//	                     opr:=GetTosSrcAdr;
//	%-E                  if bAND(opr.sbireg,oSREG) <> oDS
//	%-E                  then segreg:=GetSreg(opr);
//	%-E                       PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                       Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E                  endif;
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%-E             Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                if s<>0
//	                then Qf1(qLAHF,0,cVAL); qPOPKill(s);
//	                     Qf1(qSAHF,0,cVAL);
//	                endif;
//	                Pop;
//	           endif;
//	      endif;
//	%-E E1:E3:
//	      if    at=T_INT  then qtype:=q_ILT
//	      elsif at=T_WRD2 then qType:=q_ILT else qType:=q_WLT endif;
//	      qCond:=(srel-S_LT)+qType;
//	      if reversed then qCond:=RevQcond(qCond) endif;
//	      res:= qCond;
//	%-E E2:
//	end;
//
//	%-E Routine IsONONE; import ref(Coonst) x; export Boolean res;
//	%-E begin if x.kind <> K_Coonst then res:=false
//	%-E       elsif x.type <> T_OADDR then res:=false
//	%-E       else res:=x.itm.base.kind=0 endif;
//	%-E end;
//
//	%-E Routine GQrel32; import range(0:255) srel; boolean jmprel;
//	%-E export range(0:255) res;
//	%-E begin ref(Qpkt) TT,LL,FF,EE;
//	%-E       GQconvert(T_WRD4); GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E       GQconvert(T_WRD4); GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E       if srel=S_EQ then goto EN1 elsif srel=S_NE
//	%-E       then EN1: Qf2(qDYADR,qCMP,qAX,cVAL,qCX);
//	%-E            if jmprel
//	%-E            then mindMask:=wOR(uSPBPM,uF);
//	%-E                 xFJUMP:=forwJMP(q_WNE);
//	%-E                 MindMask:=wOR(uDX,uBX);
//	%-E                 Qf2(qDYADR,qCMP,qDX,cVAL,qBX);
//	%-E                 mindMask:=wOR(uSPBPM,uF);
//	%-E            else Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%-E                 Qf2(qDYADR,qCMP,qDX,cVAL,qBX);
//	%-E                 Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%-E                 Qf1(qSAHF,0,cVAL);
//	%-E            endif
//	%-E            res:=(srel-S_LT)+q_ILT;
//	%-E       else
//	%-E %+S        WARNING("Relation between 32bit integers");
//	%-E            Qf2(qDYADR,qSUBM,qDX,cVAL,qBX);
//	%-E            PreReadMask:=wOR(wOR(uAX,uCX),uDX);
//	%-E            PreMindMask:=
//	%-E                   wOR(PreMindMask,wOR(wOR(uAX,uCX),WOR(uDX,uF)));
//	%-E            LL:=ForwJMP(q_WEQ);
//	%-E            PreReadMask:=wOR(wOR(uAX,uCX),uDX);
//	%-E            PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uCX),uDX));
//	%-E            FF:=ForwJMP(if srel<S_EQ then q_ILT else q_IGT);
//	%-E            PreReadMask:=wOR(wOR(uAX,uCX),uDX);
//	%-E            PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uCX),uDX));
//	%-E            TT:=DefBDEST;
//	%-E            NotMindMask:=uF;
//	%-E            PreMindMask:=wOR(PreMindMask,uDX);
//	%-E            Qf2(qDYADR,qXORM,qDX,cVAL,qDX);
//	%-E            PreReadMask:=wOR(wOR(uAX,uCX),uDX);
//	%-E            PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uCX),uDX));
//	%-E            EE:=ForwJMP(0);
//	%-E            PreReadMask:=wOR(wOR(uAX,uCX),uDX);
//	%-E            PreMindMask:=wOR(PreMindMask,wOR(wOR(uAX,uCX),uDX));
//	%-E            DefFDEST(LL);
//	%-E            Qf2(qDYADR,qCMP,qAX,cVAL,qCX);
//	%-E            PreReadMask:=uDX;
//	%-E            PreMindMask:=wOR(PreMindMask,uDX);
//	%-E            BackJMP(NotQcond((srel-S_LT)+q_WLT),TT);
//	%-E            Qf2(qMONADR,qDEC,qDX,cVAL,0);
//	%-E            PreReadMask:=uDX;
//	%-E            PreMindMask:=wOR(PreMindMask,uDX);
//	%-E            DefFDEST(FF);
//	%-E            NotMindMask:=uDX;
//	%-E            Qf2(qDYADR,qORM,qDX,cVAL,qDX);
//	%-E            PreReadMask:=uF;
//	%-E            PreMindMask:=wOR(PreMindMask,uF);
//	%-E            DefFDEST(EE); res:=q_WNE;
//	%-E       endif;
//	%-E end;
//	%title ***   T y p e    C o n v e r s i o n   ***
//
//	Routine FreePartReg; export range(0:nregs) res;
//	begin
//	%-E   if    wAND(MindMask,uAX)=0 then res:=qAX
//	%-E   elsif wAND(MindMask,uDX)=0 then res:=qDX
//	%-E   elsif wAND(MindMask,uCX)=0 then res:=qCX
//	%-E   elsif wAND(MindMask,uBX)=0 then res:=qBX
//	%-E   else  IERR("FreePartReg -- Not available"); res:=qAX  endif;
//	%+E   if    wAND(MindMask,uEAX)=0 then res:=qEAX
//	%+E   elsif wAND(MindMask,uEDX)=0 then res:=qEDX
//	%+E   elsif wAND(MindMask,uECX)=0 then res:=qECX
//	%+E   elsif wAND(MindMask,uEBX)=0 then res:=qEBX
//	%+E   else  IERR("FreePartReg -- Not available"); res:=qEAX  endif;
//	end;
//
//	--- Visible Routine GQparam;
//	--- begin range(0:MaxType) type; range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	---       GQfetch; type:=TOS.type;
//	---       if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	---       if TOS.kind <> K_Coonst
//	---       then nbyte:=TTAB(type).nbyte;
//	--- %-E        if nbyte <= 8  then case 0:8  (nbyte)
//	--- %+E        if nbyte <= 12 then case 0:12 (nbyte)
//	---            when 1: GetTosAsBYT1(qAL); Qf1(qPUSHR,qAL,cTYP);
//	---            when 2: GetTosAsBYT2(qAX); Qf1(qPUSHR,qAX,cTYP);
//	--- %-E        when 4: GetTosAsBYT4(qAX,qCX);
//	--- %-E                Qf1(qPUSHR,qCX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	--- %-E        when 6: GetTosValueIn86R3(qAX,qCX,qDX); Qf1(qPUSHR,qDX,cTYP);
//	--- %-E                Qf1(qPUSHR,qCX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	--- %-E        when 8: GetTosValueIn86R4(qAX,qCX,qDX,qSI);
//	--- %-E                Qf1(qPUSHR,qSI,cTYP); Qf1(qPUSHR,qDX,cTYP);
//	--- %-E                Qf1(qPUSHR,qCX,cTYP); Qf1(qPUSHR,qAX,cTYP);
//	--- %+E        when 4: GetTosAsBYT4(qEAX); Qf1(qPUSHR,qEAX,cTYP);
//	--- %+E        when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	--- %+E                Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qEAX,cTYP);
//	--- %+E        when 12: GetTosValueIn86R3(qEAX,qECX,qEDX); Qf1(qPUSHR,qEDX,cTYP)
//	--- %+E                 Qf1(qPUSHR,qECX,cTYP); Qf1(qPUSHR,qEAX,cTYP);
//	---            endcase endif;
//	---       endif;
//	--- end;
//	%page
//
//	%+D Visible Routine GQconvert; import range(0:MaxType) totype;
//	%-D Visible Routine DOconvert; import range(0:MaxType) totype;
//	begin range(0:MaxType) fromtype; Boolean ILL;
//	      infix(ValueItem) itm; infix(MemAddr) opr; range(0:nregs) a,d;
//	%+S   GQfetch;
//	      fromtype:=TOS.type;
//	%+D   if fromtype=totype then -- Nothing
//	%+D   elsif fromtype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-1")
//	%+D   elsif totype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-2")
//	%+D   else
//	      if TOS.kind = K_Coonst then ConvConst(totype)
//	      else ILL:=false;
//	           case 0:T_max (fromtype)
//	           when T_TREAL:       -- temp real
//	                case 0:T_max (totype)
//	                when T_LREAL, -- temp real --> long real
//	                     T_REAL,  -- temp real --> real
//	                     T_WRD4:  -- temp real --> 32bit integer
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTempVAL(totype);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	                     PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	                     pushTempVAL(T_WRD4); goto LL1;
//	                otherwise ILL:=true endcase;
//	           when T_LREAL:       -- long real
//	                case 0:T_max (totype)
//	                when T_TREAL: -- long real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTempVAL(totype);
//	                when T_REAL: -- long real --> real
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2RE)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_REAL);
//	                when T_WRD4: -- long real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_WRD4);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTempVAL(T_WRD4); goto LL2;
//	                otherwise ILL:=true endcase;
//	           when T_REAL:       -- real
//	                case 0:T_max (totype)
//	                when T_TREAL: -- real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTempVAL(T_TREAL);
//	                when T_LREAL: -- real --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_RE2LR)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_LREAL);
//	                when T_WRD4: -- real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_WRD4);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTempVAL(T_WRD4); goto LL3;
//	                otherwise ILL:=true endcase;
//	           when T_WRD4:       -- 4-byte signed integer in 86
//	LL1:LL2:LL3:LL4:LL5:LL6:
//	                case 0:T_max (totype)
//	                when T_TREAL: -- 32bit integer --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTempVAL(T_TREAL);
//	                when T_LREAL: -- 32bit integer --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_IN2LR);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_LREAL);
//	                when T_REAL: -- 32bit integer --> real
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_IN2RE);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTempVAL(T_REAL);
//	                when T_WRD2: -- 32bit integer --> 16bit signed integer
//	                     -- CHECK_RANGE(-32768:32767)
//	                     a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                when T_BYT2: -- 32bit integer  --> 16bit unsigned integer
//	                     -- CHECK_RANGE(0:65535)
//	                     a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                when T_BYT1, -- 32bit integer --> 1-byte unsigned integer
//	                     T_CHAR: -- 32bit integer --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_WRD2:       -- 2-byte signed integer in 86
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                     Pop; pushTempVAL(T_WRD4); goto LL4;
//	                when T_WRD4:   --> 4-byte signed integer in 86
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                when T_BYT2:   --> 2-byte unsigned integer in 86
//	                     -- CHECK_POSITIVE
//	                when T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_BYT2:       -- 2-byte unsigned integer in 86
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	%-E                  a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a:=FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTempVAL(T_WRD4); goto LL5;
//	                when T_WRD4:   --> 4-byte signed integer in 86
//	%-E                  a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a:=FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                when T_WRD2:   --> 2-byte signed integer in 86
//	                when T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_BYT1,       -- 1-byte unsigned integer in 86
//	                T_CHAR:       -- Character
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     a:=FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTempVAL(T_WRD4); goto LL6;
//	                when T_WRD4:  --> 4-byte signed integer in 86
//	                     a:=FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                when T_WRD2,  --> 2-byte signed integer in 86
//	                     T_BYT2:  --> 2-byte unsigned integer in 86
//	                     a:=WordReg(FreePartReg);
//	                     GetTosAdjustedIn86(a); Qf1(qPUSHR,a,cVAL);
//	                when T_BYT1,  --> 1-byte unsigned integer in 86
//	                     T_CHAR:  --> Character
//	                otherwise ILL:=true endcase;
//	           when T_OADDR:
//	                if totype = T_GADDR
//	                then Qf2(qPUSHC,0,FreePartReg,cVAL,0) else ILL:=true endif;
//	           when T_GADDR:
//	                if totype = T_AADDR
//	                then a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     qPOPKill(AllignFac); Qf1(qPUSHR,a,cVAL);
//	                elsif totype = T_OADDR
//	                then qPOPKill(AllignFac) else ILL:=true endif;
//	           otherwise ILL:=true endcase;
//	           if ILL then ERROR("Type conversion is undefined") endif;
//	           Pop; pushTempVAL(totype);
//	      endif;
//	%+D   endif;
//	end;
//	%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
//	Routine ConvConst; import range(0:MaxType) totype;
//	begin integer v; infix(ValueItem) itm; Boolean ILL;
//	%+C   if TOS.type > T_max then IERR("CODER.ConvConst-1") endif;
//	%+C   if totype > T_max then IERR("CODER.ConvConst-2") endif;
//	      itm:=TOS qua Coonst.itm; ILL:=false;
//	      case 0:T_max (TOS.type)
//	      when T_BYT1,T_CHAR: qPOPKill(1); goto I1;
//	      when T_WRD2,T_BYT2: qPOPKill(2); goto I2;
//	      when T_WRD4:
//	%-E                qPOPKill(2);
//	                   qPOPKill(AllignFac);
//	I1:I2:     case 0:T_max (totype)
//	           when T_BYT1,T_CHAR:
//	                if itm.int > 255 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//	           when T_BYT2:
//	                if (TOS.type=T_WRD2) and (RNGCHK=0) then -- OK
//	                elsif itm.int > 65535 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD2:
//	                if (TOS.type=T_BYT2) and (RNGCHK=0) then -- OK
//	                elsif itm.int > 32767 then ILL:=true
//	                elsif itm.int < -32768 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD4: GQpushVAL4(itm)
//	           when T_REAL:
//	                itm.rev:=itm.int qua real; GQpushVAL4(itm)
//	           when T_LREAL:
//	                itm.lrv:=itm.int qua long real; GQpushVAL8(itm)
//	           otherwise ILL:=true endcase;
//	      when T_REAL:
//	%-E        qPOPKill(2); qPOPKill(2);
//	%+E        qPOPKill(4);
//	           case 0:T_max (totype)
//	           when T_BYT1,T_CHAR:
//	                itm.int:=itm.rev qua integer;
//	                if itm.int > 255 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//	           when T_BYT2:
//	                itm.int:=itm.rev qua integer;
//	                if itm.int > 65535 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD2:
//	                itm.int:=itm.rev qua integer;
//	                if itm.int > 32767 then ILL:=true
//	                elsif itm.int < -32768 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD4:
//	                itm.int:=itm.rev qua integer; GQpushVAL4(itm)
//	           when T_REAL:
//	                itm.rev:=itm.rev qua real; GQpushVAL4(itm)
//	           when T_LREAL:
//	                itm.lrv:=itm.rev qua long real; GQpushVAL8(itm)
//	           otherwise ILL:=true endcase;
//	      when T_LREAL:
//	%-E        qPOPKill(2); qPOPKill(2);
//	%-E        qPOPKill(2); qPOPKill(2);
//	%+E        qPOPKill(4); qPOPKill(4);
//	           case 0:T_max (totype)
//	           when T_BYT1,T_CHAR:
//	                itm.int:=itm.lrv qua integer;
//	                if itm.int > 255 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//	           when T_BYT2:
//	                itm.int:=itm.lrv qua integer;
//	                if itm.int > 65535 then ILL:=true
//	                elsif itm.int < 0 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD2:
//	                itm.int:=itm.lrv qua integer;
//	                if itm.int > 32767 then ILL:=true
//	                elsif itm.int < -32768 then ILL:=true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//	           when T_WRD4:
//	                itm.int:=itm.lrv qua integer; GQpushVAL4(itm)
//	           when T_REAL:
//	                itm.rev:=itm.lrv qua real; GQpushVAL4(itm)
//	           when T_LREAL:
//	                itm.lrv:=itm.lrv qua long real; GQpushVAL8(itm)
//	           otherwise ILL:=true endcase;
//	      when T_OADDR:
//	           if totype = T_GADDR
//	           then itm.ofst:=0; Qf2(qPUSHC,0,FreePartReg,cVAL,0);
//	           else ILL:=true endif;
//	      when T_GADDR:
//	           if totype = T_AADDR
//	           then qPOPKill(AllignFac); qPOPKill(AllignFac);
//	%-E             qPOPKill(2);
//	                itm.int:=itm.Ofst; Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd);
//	           elsif totype = T_OADDR then qPOPKill(AllignFac);
//	           else ILL:=true endif;
//	      otherwise ILL:=true endcase;
//	      if ILL then ERROR("Constant conversion is undefined") endif;
//	      TOS qua Coonst.itm:=itm; TOS.type:=totype;
//	end;
//	%page
//
//	Routine GQpushVAL4; import infix(ValueItem) itm;
//	begin range(0:nregs) a; a:=FreePartReg;
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(1));
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(0));
//	%+E   Qf2(qPUSHC,0,a,cVAL,itm.int);
//	end;
//
//	Routine GQpushVAL8; import infix(ValueItem) itm;
//	begin range(0:nregs) a; a:=FreePartReg;
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(3));
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(2));
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(1));
//	%-E   Qf2(qPUSHC,0,a,cVAL,itm.wrd(0));
//	%+E   Qf2(qPUSHC,0,a,cVAL,itm.int(1));
//	%+E   Qf2(qPUSHC,0,a,cVAL,itm.int(0));
//	end;
//
//	%title ***  A r i t h T y p e  ***
//	Routine ArithType;
//	import range(0:MaxType) t1,t2; export range(0:MaxType) ct;
//	begin
//	%+D   RST(R_ArithType);
//	      case 0:T_max (t1)
//	      when T_TREAL: ct:=T_TREAL
//	      when T_LREAL: case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           otherwise ct:=T_LREAL endcase;
//	      when T_REAL: case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           when T_LREAL: ct:=T_LREAL
//	           otherwise ct:=T_REAL endcase;
//	      when T_WRD4: case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           when T_LREAL: ct:=T_LREAL
//	           when T_REAL:  ct:=T_REAL
//	           otherwise ct:=T_WRD4 endcase;
//	      when T_WRD2:       -- 2-byte signed integer in 86
//	           case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           when T_LREAL: ct:=T_LREAL
//	           when T_REAL:  ct:=T_REAL
//	           when T_WRD4:  ct:=T_WRD4
//	--- ???    when T_BYT2:  if RNGCHK=0 then ct:=T_WRD2 else ct:=T_WRD4 endif
//	           otherwise ct:=T_WRD2 endcase;
//	      when T_BYT2:       -- 2-byte unsigned integer in 86
//	           case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           when T_LREAL: ct:=T_LREAL
//	           when T_REAL:  ct:=T_REAL
//	           when T_WRD4:  ct:=T_WRD4
//	--- ???    when T_WRD2:  if RNGCHK=0 then ct:=T_WRD2 else ct:=T_WRD4 endif
//	           when T_WRD2:  ct:=T_WRD2
//	           otherwise ct:=T_BYT2 endcase;
//	      when T_BYT1:       -- 1-byte unsigned integer in 86
//	           case 0:T_max (t2)
//	           when T_TREAL: ct:=T_TREAL
//	           when T_LREAL: ct:=T_LREAL
//	           when T_REAL:  ct:=T_REAL
//	           when T_WRD4:  ct:=T_WRD4
//	           when T_WRD2:  ct:=T_WRD2
//	           when T_BYT2:  ct:=T_BYT2
//	           otherwise ct:=T_BYT1 endcase;
//	      otherwise ct:=t1 endcase;
//	end;
//
//	%title ***  I n s t r u c t i o n s    V a l u e    T y p e  ***
//
//	-- Visible Routine cTYPE;
//	-- import range(0:MaxType) type; export range(0:MaxType) cTYP;
//	-- begin if type > T_max then cTYP:=cANY
//	--       else case 0:T_max (type)
//	--            when T_WRD4,T_WRD2,T_BYT2,T_BYT1,T_REAL,
//	--                 T_LREAL,T_TREAL,T_BOOL,T_CHAR,T_SIZE,
//	--                 T_AADDR,T_PADDR,T_RADDR,T_NPADR:        cTYP:=cVAL
//	--            when T_OADDR:                                cTYP:=cOBJ
//	--            when T_GADDR:                                cTYP:=cADR
//	--            otherwise cTYP:=cANY endcase;
//	--       endif;
//	-- end;
//
//	end;

}
