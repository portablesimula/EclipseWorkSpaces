package removed.java;

import bec.util.Scode;
import bec.util.Util;

public class Parse {
//	 Module PARSE("iAPX");
//	 begin insert SCOMN,SKNWN,SBASE,COASM,MASSAGE,CODER,MINUT;
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
//	       ---             P a r s i n g     R o u t i n e s             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     c - Reverse parameter list for C-routine              ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//
//	Infix(WORD) CSEGNAM,DSEGNAM;
//	Range(0:MaxWord) IfDepth;
//	static int IfDepth;
//	%-S Boolean warnDEBUG3;  -- TEMP FIX
//	-- boolean doJUMPrel;
//
//	%title ***     M  O  N  I  T  O  R     ***
//	Visible routine MONITOR;
//	public static void MONITOR() {
//		System.out.println("Parse.MONITOR");
//	begin infix(WORD) tag,entx; infix(MemAddr) Ltab;
//	      range(0:MaxByte) n; infix(MemAddr) opr;
//	%-S   warnDEBUG3:= DEBMOD > 2;  -- TEMP FIX
//		IfDepth=0;
//		Scode.inputInstr();
//		if(Scode.curinstr == Scode.S_PROGRAM) {
//	  		System.out.println("Parse.MONITOR: S_PROGRAM");
//	  		Scomn.progIdent = Scode.inString();
//			Scode.inputInstr();
//	%+S        if CurInstr=S_GLOBAL then InterfaceModule; InputInstr endif;
//	           repeat while CurInstr=S_MODULE
//	           do ModuleDefinition; InputInstr endrepeat;
//			while(Scode.curinstr == Scode.S_MODULE) {
//				moduleDefinition();
//				Scode.inputInstr(); 
//			}
//			if(Scode.curinstr == Scode.S_MAIN) {
//				//  M a i n   P r o g r a m  ---
//	                if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//	                BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//	                EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//	                MainEntry:=NewFixAdr(CSEGID,entx);
//	                DefLABEL(qBPROC,MainEntry.fix.val,entx.val);
//
//	                repeat while NextByte = S_LOCAL
//	                do inputInstr; inGlobal; endrepeat;
//				
//				while(Scode.nextByte() == Scode.S_LOCAL) {
//					Scode.inputInstr(); 
//					Minut.inGlobal();
//				}
//	                if LtabEntry.kind <> 0
//	                then Ltab.kind:=segadr; Ltab.rela.val:=0;
//	                     Ltab.segmid:=LSEGID;
//	                     opr.kind:=extadr; opr.rela.val:=0;
//	                     opr.smbx:=DefExtr("G@PRGINF",DGROUP);
//	%-E                  Ltab.sbireg:=0;       opr.sbireg:=oSS;
//	%+E                  Ltab.sibreg:=NoIBREG; opr.sibreg:=NoIBREG;
//	%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_OFFSET,Ltab);
//	%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//	%-E                  opr.rela.val:=opr.rela.val+2;
//	%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_BASE,Ltab);
//	%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//	%+E                  Qf2b(qLOADC,0,qEAX,cOBJ,0,Ltab);
//	%+E                  Qf3(qSTORE,0,qEAX,cOBJ,opr);
//	                endif;
//
//	                programElements();
//
//	                Qf2(qRET,0,0,0,0);
//	                DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//	                peepExhaust(true); ENDASM;
//			}
//	           if CurInstr <> S_ENDPROGRAM
//	           then IERR("Illegal termination of program") endif;
//		} else Util.IERR("Illegal S-Program");
//	%+D   --- Release Display ---
//	%+D   tag:=GetLastTag; n:=tag.HI;
//	%+D   repeat DELETE(DISPL(n)); DISPL(n):=none
//	%+D   while n<>0 do n:=n-1 endrepeat;
//	end;
//	}
//	%title ***   I n t e r f a c e    M o d u l e   ***
//
//	%+S Routine InterfaceModule;
//	%+S begin range(0:MaxWord) nXtag; infix(WORD) itag,xtag,wrd;
//	%+S       range(0:MaxByte) b1,b2;
//	%+S    InputInstr;
//	%+S    if CurInstr <> S_MODULE then IERR("Missing - MODULE") endif;
//	%+S    modident:=inMsymb; modcheck:=inSymb;
//	%+S    if PROGID.val = 0
//	%+S    then edsymb(sysedit,modident);
//	%+S         PROGID:=DefSymb(pickup(sysedit));
//	%+S    endif;
//	%+S    BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
//	%+S    repeat InputInstr; case 0:S_max (CurInstr)
//	%+S           when S_GLOBAL:    inGlobal
//	%+S           when S_CONST:     inConstant(true)
//	%+S           when S_RECORD:    InRecord
//	%+S           when S_PROFILE:   InProfile(P_VISIBLE)
//	%+S           when S_ROUTINE:   InRoutine
//	%+S           when S_INFO:      Ed(errmsg,InString);
//	%+S                             WARNING("Unknown info: ");
//	%+S           when S_LINE:      SetLine(0)
//	%+S           when S_DECL:
//	%+SC                            CheckStackEmpty;
//	%+S                             SetLine(qDCL)
//	%+S           when S_STMT:
//	%+SC                            CheckStackEmpty;
//	%+S                             SetLine(qSTM)
//	%+S           when S_SETSWITCH: SetSwitch
//	%+S           when S_INSERT,
//	%+S                S_SYSINSERT: Combine; TERMINATE
//	%+S           otherwise goto E;
//	%+S           endcase;
//	%+S     while true do
//	%+S     endrepeat;
//
//	%+S E:  repeat while CurInstr=S_TAG
//	%+S     do InTag(%itag%); xtag:=InputNumber;
//	%+SD       if xtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	%+S        if   TAGTAB(xtag.HI)=none
//	%+S        then TAGTAB(xtag.HI):=
//	%+S             NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	%+S        TAGTAB(xtag.HI).elt(xtag.LO):=itag;
//	%+S        InputInstr;
//	%+S        if xtag.val>nXtag then nXtag:=xtag.val endif;
//	%+S     endrepeat;
//	%+S     OutputModule(nXtag);
//
//	%+S     if CurInstr <> S_BODY then
//	%+S     IERR("Illegal termination of module head") endif;
//
//	%+SC    repeat InputInstr while CurInstr=S_INIT
//	%+SC    do IERR("InterfaceModule: Init values is not supported");
//	%+SC       InTag(%wrd%); intype; SkipRepValue;
//	%+SC    endrepeat;
//
//	%+S     if CurInstr <> S_ENDMODULE then
//	%+S     IERR("Improper termination of module") endif;
//
//	---     peepExhaust(); --- nothing to work on
//	%+S     ENDASM;
//	%+S end;
//	%title ***   M o d u l e   D e f i n i t i o n   ***
//
//	public static void moduleDefinition() {
//		Util.IERR("Parse.moduleDefinition: NOT IMPLEMENTED");
//	       range(0:MaxWord) nXtag; ref(ModElt) m;
//	       infix(WORD) itag,xtag; infix(Fixup) Fx;
//	       modident:=inMsymb; modcheck:=inSymb;
//	       if PROGID.val = 0
//	       then edsymb(sysedit,modident);
//	            PROGID:=DefSymb(pickup(sysedit));
//	       endif;
//	       BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
//	       InputInstr;
//	       m:=DICREF(modident);
//	%+S    if SYSGEN=0
//	%+S    then
//	            m.RelElt:=RELID;
//	%+S    endif;
//	       if LtabEntry.kind=0 then -- No LineNumberTable
//	       else
//	%+D         if LtabEntry.kind<>fixadr then IERR("PARSE:Md-1") endif
//	            Fx:=FIXTAB(LtabEntry.fix.HI).elt(LtabEntry.fix.LO);
//	%+D         if Fx.smbx.val=0 then IERR("PARSE:Md-2") endif;
//	            m.LinTab:=Fx.smbx;
//	       endif;
//
//	       repeat while Viisible do InputInstr endrepeat;
//	       repeat while CurInstr=S_TAG
//	       do InTag(%itag%);
//	%+D       xtag:=InputNumber;
//	%-D       InNumber(%xtag%);
//	%+D       if xtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	          if   TAGTAB(xtag.HI)=none
//	          then TAGTAB(xtag.HI):=
//	               NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	          TAGTAB(xtag.HI).elt(xtag.LO):=itag;
//	          InputInstr;
//	          if xtag.val>nXtag then nXtag:=xtag.val endif;
//	       endrepeat;
//	       OutputModule(nXtag);
//
//	       if CurInstr <> S_BODY then
//	       IERR("Illegal termination of module head") endif;
//
//	       repeat while NextByte = S_LOCAL
//	       do inputInstr; inGlobal endrepeat;
//
//	       programElements;
//
//	       if CurInstr <> S_ENDMODULE then
//	       IERR("Improper termination of module") endif;
//	       peepExhaust(true);
//	       ENDASM;
//	}
//	
//	%title ***   Visible  /  Program  Element   ***
//	public static void specLab() {
//		Scode.inTag();
//	}
//
//	Routine DefLab;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin infix(WORD) tag,smbx; ref(IntDescr) v; InTag(%tag%);
//	%+D   RST(R_DefLab);
//	      v:=if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
//	      if v = none
//	      then v:=NEWOBJ(K_IntLabel,size(IntDescr)); smbx.val:=0;
//	           v.adr:=NewFixAdr(CSEGID,smbx); IntoDisplay(v,tag);
//	%+C   else v:=DISPL(tag.HI).elt(tag.LO);
//	%+C        if v.adr.kind <> fixadr then IERR("Parse.DefLAB-1") endif;
//	      endif;
//	      DefLABEL(0,v.adr.fix.val,0);
//	%+C   CheckStackEmpty;
//	end;
//
//	Routine Viisible; export Boolean val;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin infix(WORD) wrd; range(0:MaxByte) b1,b2;
//	      val:=true;
//	%+D   RST(R_Viisible);
//	      case 0:S_max (CurInstr)
//	      when S_CONSTSPEC:    inConstant(false)
//	      when S_LABELSPEC:    SpecLab
//	      when S_RECORD:       InRecord
//	      when S_PROFILE:      InProfile(P_VISIBLE)
//	%+S   when S_ROUTINESPEC:  SpecRut(true)
//	      when S_INSERT:       InputModule(false)
//	      when S_SYSINSERT:    InputModule(true)
//	      when S_LINE:         SetLine(0)
//	      when S_DECL:
//	%+C                        CheckStackEmpty;
//	                           SetLine(qDCL)
//	      when S_STMT: 
//	%+C                        CheckStackEmpty;
//	                           SetLine(qSTM)
//	      when S_SETSWITCH:    SetSwitch
//	      when S_INFO:         Ed(errmsg,InString); WARNING("Unknown info: ");
//	      otherwise val:=false endcase;
//	end;
//
//
//	public static void programElements() {
//	begin -- val:=true;
//	%+D   RST(R_ProgramElement); LOOP:
//	    L0:
//	%-D L1:L2:L3:L4:L5:L6:L7:L8:L9:
//		LOOP: while(true) {
//			Scode.inputInstr();
//			switch(Scode.curinstr) {
//				case Scode.S_LABELSPEC:  specLab(); break;
//				case Scode.S_LABEL:      DefLab(); break;
//				case Scode.S_PROFILE:    InProfile(Scode.P_ROUTINE); break;
//				case Scode.S_ROUTINE:    InRoutine(); break; // L4  -- %+S removed for "call-back" - pj
//				case Scode.S_IF:         IfConstruction(true); break;
//				case Scode.S_SKIPIF:     SkipifConstruction(true);
//				case Scode.S_SAVE:       ProtectConstruction(true);;
//				case Scode.S_INSERT:     Minut.inputModule(false); break;
//				case Scode.S_SYSINSERT:  Minut.inputModule(true); break;
//			default:
//				
//			      otherwise if Instruction then goto L0 endif; goto E endcase;
//				if(! instruction() ) break LOOP;
//			}
//	E:end;
//		}
//	}
	
	
//	%title ***   I n s t r u c t i o n   ***

//	public static boolean instruction() {
//		boolean result = false; // Export
//		Util.IERR("Parse.instruction: NOT IMPLEMENTED");
//	begin range(0:MaxWord) n,s,ofst; ref(Temp) tmp;
//	      infix(WORD) tag,i,sx,wrd,ndest; Boolean OldTSTOFL;
//	      range(0:MaxByte) cond,b,b1,b2; short integer repdist;
//	      ref(Descriptor) v; infix(ValueItem) itm;
//	      infix(MemAddr) d,a; ref(Qpkt) LL;
//	      range(0:MaxType) type; ref(Temp) x,y,z;
//	      ref(RecordDescr) fixrec; ref(LocDescr) attr;
//	      ref(Address) adr; ref(SwitchDescr) sw;
//
//		System.out.println("Parse.instruction: "+Scode.edInstr(Scode.curinstr));
//		result = true;
//		switch(Scode.curinstr) {
//		case Scode.S_CONSTSPEC: Minut.inConstant(false); break;
//		case Scode.S_CONST:     Minut.inConstant(true); break;
//		case Scode.S_RECORD:    Minut.inRecord(); break;
//	%+S   when S_ROUTINESPEC: SpecRut(false)
//	%+D   when S_SETOBJ:
//	%+D        CheckTosInt; CheckSosValue; CheckSosType(T_OADDR);
//	%+D        IERR("SSTMT.SETOBJ is not implemented");
//	%+D   when S_GETOBJ:
//	%+D        CheckTosInt;
//	%+D        IERR("SSTMT.GETOBJ is not implemented");
//	%+D   when S_ACCESS,S_ACCESSV:
//	%+D        IERR("SSTMT.ACCESS is not implemented");
//	      when S_PUSH,S_PUSHV:
//	           InTag(%tag%); v:=DISPL(tag.HI).elt(tag.LO);
//	           case 0:K_Max (v.kind)
//	           when K_GlobalVar: d:=v qua IntDescr.adr
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	           when K_ExternVar:
//	                    d.kind:=extadr;
//	%-E                 d.sbireg:=0;
//	%+E                 d.sibreg:=NoIBREG;
//	                    d.rela.val:=v qua ExtDescr.adr.rela;
//	                    d.smbx:=v qua ExtDescr.adr.smbx;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	           when K_LocalVar:
//	                d.kind:=locadr; d.rela.val:=0;
//	                d.loca:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           when K_Import:
//	                d.kind:=reladr; d.segmid.val:=0;
//	                d.rela.val:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           when K_Export:
//	                d.kind:=reladr; d.segmid.val:=0;
//	                d.rela.val:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           otherwise a:=noadr;
//	%+D                  edit(errmsg,v);
//	                     IERR("Illegal push of: ")
//	           endcase;
//	           Push(NewAddress(v.type,0,d));
//	           if v.kind=K_Import
//	           then TOS.repdist:= - wAllign(%TOS.repdist%) endif;
//	           if CurInstr=S_PUSHV then GQfetch endif;
//
//		case Scode.S_PUSHC: Coder.pushConst(); break;
//	      when S_INDEX,S_INDEXV:
//	%+C        CheckTosInt; CheckSosRef;
//	           adr:=TOS.suc; repdist:=adr.repdist;
//	           if repdist=0 then IERR("PARSE.INDEX: Not info type") endif;
//	           if TOS.kind=K_Coonst
//	           then itm:=TOS qua Coonst.itm;
//	                adr.Offset:=adr.Offset+(repdist*itm.wrd);
//	                GQpop;
//	                if adr.AtrState=FromConst
//	                then adr.AtrState:=NotStacked;
//	                     qPOPKill(AllignFac);
//	                endif;
//	           else
//	%-E             if TOS.type <> T_WRD2 then GQconvert(T_WRD2) endif;
//	%-E             GetTosAdjustedIn86(qAX); Pop; AssertObjStacked;
//	%-E             GQiMultc(repdist); -- AX:=AX*repdist
//	%-E             if    adr.AtrState=FromConst then qPOPKill(2)
//	%-E             elsif adr.AtrState=Calculated
//	%-E             then Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX) endif;
//	%-E             Qf1(qPUSHR,qAX,cVAL); adr.AtrState:=Calculated;
//	%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
//	%+E             GetTosAdjustedIn86(qEAX); Pop; AssertObjStacked;
//	%+E             GQeMultc(repdist); -- EAX:=EAX*repdist
//	%+E             if    adr.AtrState=FromConst then qPOPKill(4)
//	%+E             elsif adr.AtrState=Calculated
//	%+E             then Qf1(qPOPR,qEBX,cVAL);
//	%+E                  Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	%+E             endif;
//	%+E             Qf1(qPUSHR,qEAX,cVAL); adr.AtrState:=Calculated;
//	           endif;
//	           if CurInstr=S_INDEXV then GQfetch endif;
//	      when S_SELECT,S_SELECTV:
//	%+C        CheckTosRef;
//	           InTag(%tag%); attr:=DISPL(tag.HI).elt(tag.LO); adr:=TOS;
//	           adr.Offset:=adr.Offset+attr.rela; adr.type:=attr.type;
//	           adr.repdist:=TTAB(adr.type).nbyte;
//	           if adr.AtrState=FromConst
//	           then adr.AtrState:=NotStacked;
//	                qPOPKill(AllignFac);
//	           endif;
//	           if CurInstr=S_SELECTV then GQfetch endif;
//	      when S_REMOTE,S_REMOTEV:
//	%+C        CheckTosType(T_OADDR);
//	           GQfetch; InTag(%tag%);
//	           attr:=DISPL(tag.HI).elt(tag.LO); Pop;
//	           a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//	%-E        a.sbireg:=0;
//	%+E        a.sibreg:=NoIBREG;
//	           adr:=NewAddress(attr.type,attr.rela,a);
//	           adr.ObjState:=Calculated; Push(adr);
//	           if CurInstr=S_REMOTEV then GQfetch endif;
//	      when S_FETCH: GQfetch;
//	      when S_DEREF:
//	%+C        CheckTosRef;
//	           adr:=TOS;
//	%+S        if SYSGEN <> 0
//	%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//	%+S             then WARNING("DEREF on parameter") endif;
//	%+S        endif;
//	           AssertAtrStacked; Pop; pushTempVAL(T_GADDR);
//	      when S_REFER:
//	           type:=intype;
//	%+C        CheckTosType(T_GADDR);
//	           a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//	%-E        a.sbireg:=0;
//	%+E        a.sibreg:=NoIBREG;
//	           adr:=NewAddress(type,0,a);
//	           GQfetch; adr.ObjState:=adr.AtrState:=Calculated;
//	           Pop; Push(adr);
//	      when S_DSIZE:
//	           InTag(%tag%); fixrec:=DISPL(tag.HI).elt(tag.LO);
//	           if fixrec.nbrep <> 0
//	           then n:=fixrec.nbrep;
//	%+C             CheckTosInt;
//	                if TOS.kind=K_Coonst
//	                then itm:=TOS qua Coonst.itm; GQpop;
//	                     n:=wAllign(%(n*(itm.wrd))+fixrec.nbyte%);
//	%-E                  Qf2(qPUSHC,0,qAX,cVAL,n); itm.int:=n;
//	%+E                  Qf2(qPUSHC,0,qEAX,cVAL,n); itm.int:=n;
//	                     pushCoonst(T_SIZE,itm);
//	                else
//	%-E                  if TOS.Type = T_WRD4
//	%-E                  then GQfetch; Qf1(qPOPR,qAX,cVAL);
//	%-E                       Qf1(qPOPR,qDX,cVAL);
//	%-E                       NotMindMask:=uDX;
//	%-E                       Qf2(qDYADR,qORM,qDX,cVAL,qDX);
//	%-E                       PreReadMask:=uAX;
//	%-E                       PreMindMask:=wOR(PreMindMask,uAX);
//	%-E                       LL:=ForwJMP(q_WEQ);
//	%-E                       Qf2(qDYADR,qXOR,qAX,cVAL,qAX);
//	%-E                       Qf2(qMONADR,qDEC,qAX,cVAL,0);
//	%-E                       PreReadMask:=uAX;
//	%-E                       PreMindMask:=wOR(PreMindMask,uAX);
//	%-E                       DefFDEST(LL); Qf1(qPUSHR,qAX,cVAL);
//	%-E                       Pop; pushTempVAL(T_BYT2);
//	%-E                  elsif TOS.type <> T_BYT2
//	%-E                  then GQconvert(T_BYT2) endif;
//	%-E                  GetTosAdjustedIn86(qAX); Pop;
//	%-E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//	%-E                  if n > 1
//	%-E                  then GQwMultc(n); -- AX:=AX*n
//	%-E                       Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte);
//	%-E                  else Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte+1);
//	%-E                       Qf2(qDYADC,qAND,qAX,cVAL,65534);
//	%-E                  endif;
//	%-E                  TSTOFL:=OldTSTOFL;
//	%-E                  Qf1(qPUSHR,qAX,cVAL);
//
//	%+E                  GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//	%+E                  if n > 3
//	%+E                  then GQeMultc(n); -- EAX:=EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte);
//	%+E                  else if n>1 then GQeMultc(n) endif; -- EAX:=EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte+3);
//	%+E                       Qf2(qDYADC,qAND,qEAX,cVAL,-4);
//	%+E                  endif;
//	%+E                  TSTOFL:=OldTSTOFL;
//	%+E                  Qf1(qPUSHR,qEAX,cVAL);
//	                     pushTempVAL(T_SIZE);
//	                endif;
//	           else
//	%+D             edit(errmsg,fixrec);
//	                IERR("Illegal DSIZE on: ");
//	                GQpop; itm.int:=0; pushCoonst(T_SIZE,itm);
//	           endif;
//	      when S_DUP: GQdup
//	      when S_POP: if TOS <> none then GQpop
//	                  else IERR("POP -- Stack is empty") endif;
//	      when S_POPALL:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	           repeat while TOS <> none
//	           do
//	%+C           if TOS.kind = K_ProfileItem
//	%+C           then b:=(b-1)+TOS qua ProfileItem.nasspar
//	%+C           elsif TOS.kind <> K_Result then b:=b-1 endif;
//	%+D           if TraceMode <> 0
//	%+D           then outstring("*** Pop: "); edit(sysout,TOS);
//	%+D                outstring(", n:"); outword(b); printout(sysout);
//	%+D           endif;
//	              --- do not generate superfluous POPRs
//	              if nextbyte<>S_ENDSKIP then GQpop else pop endif;
//	           endrepeat;
//	%+C        if b <> 0 then IERR("POPALL n  --  wrong value of n") endif;
//	%+C        CheckStackEmpty;
//	      when S_ASSIGN:     GQassign
//	      when S_UPDATE:     GQupdate
//	      when S_RUPDATE:    if NextByte = S_POP
//	                         then inputInstr; GQrassign -- can't use skip here!
//	                         else GQrupdate endif;
//	      when S_BSEG:       BSEGInstruction
//	      when S_IF:         IfConstruction(false)
//	      when S_SKIPIF:     SkipifConstruction(false)
//		case Scode.S_PRECALL: callInstruction(0); break;
//		case Scode.S_ASSCALL: callInstruction(1); break;
//		case Scode.S_REPCALL: int b = Scode.inByte(); callInstruction(b); break;
//	      when S_GOTO:
//	%-E        if TOS.type = T_NPADR
//	%-E        then
//	%-E             if TOS.kind=K_Coonst
//	%-E             then qPOPKill(2);
//	%-E                  a:=TakeTOS qua Coonst.itm.base;
//	%-E                  if InsideRoutine
//	%-E                  then PreReadMask:=uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask:=uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                  endif;
//	%-E                  Qf5(qJMP,0,0,0,a);
//	%-E             else if InsideRoutine
//	%-E                  then GetTosValueIn86(qAX); Pop;
//	%-E                       PreReadMask:=uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask:=uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                       Qf1(qPUSHR,qCS,cANY); Qf1(qPUSHR,qAX,cANY);
//	%-E                  else Qf1(qPUSHR,qCS,cANY); GQfetch; Pop endif;
//	%-E                  Qf2(qRET,0,0,0,0);
//	%-E             endif;
//	%-E        else
//	%+C             CheckTosType(T_PADDR);
//	                if TOS.kind=K_Coonst
//	                then qPOPKill(AllignFac);
//	%-E                  qPOPKill(2);
//	                     a:=TakeTOS qua Coonst.itm.base;
//	                     if InsideRoutine
//	                     then
//	%-E                     PreReadMask:=uSP;  Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%+E                     PreReadMask:=uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%-E                     PreReadMask:=uBP;  Qf2(qLOADC,0,qBP,cSTP,0);
//	%+E                     PreReadMask:=uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+D                        showWARN("SP set to INITSP");
//	                     endif;
//	                     Qf5(qJMP,0,0,0,a)
//	%-E             elsif (BNKLNK=0) and (not InsideRoutine)
//	%-E             then GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	                else
//	%-E %+D              showWARN("E-GOTO called");
//	%-E                  GetTosValueIn86R3(qAX,qBX,0); Pop; a:=X_GOTO;
//	%-E                  PreReadMask:=wOR(uAX,uBX); Qf5(qJMP,0,0,0,a)
//	-- ????? %+E                  GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	%+E                  if InsideRoutine
//	%+E                  then GetTosAdjustedIn86(qEAX); Pop;
//	%+E                     PreReadMask:=uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%+E %+D                    showWARN("SP set to INITSP");
//	%+E                     PreReadMask:=uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+E                     Qf1(qPUSHR,qEAX,cANY);
//	%+E                  else GQfetch; Pop endif;
//	%+E                  Qf2(qRET,0,0,0,0);
//	                endif;
//	%-E        endif;
//	%+C        CheckStackEmpty;
//	      when S_PUSHLEN: itm.int:=Pushlen;
//	                      if not SkipProtect
//	                      then pushCoonst(T_SIZE,itm);
//	%-E                        Qf2(qPUSHC,0,qAX,cVAL,itm.wrd);
//	%+E                        Qf2(qPUSHC,0,qEAX,cVAL,itm.wrd);
//	                      endif;
//	      when S_SAVE:    ProtectConstruction(false)
//	%+S   when S_T_INITO:
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_INITO);
//	%+S   when S_T_GETO:
//	%+S %-E                  Qf2(qDYADC,qSUB,qSP,cSTP,4);
//	%+SE                     Qf2(qDYADC,qSUB,qESP,cSTP,4);
//	%+S                      Qf5(qCALL,0,0,4,X_GETO);
//	%+S                      Qf2(qADJST,0,0,0,4);
//	%+S                      pushTempVAL(T_OADDR);
//	%+S   when S_T_SETO:
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_SETO);
//		case Scode.S_DECL:       setLine(0); break;
//		case Scode.S_STMT:       setLine(0); break;
//		case Scode.S_LINE:       setLine(0); break;
//	      when S_EMPTY:
//	%+C                      CheckStackEmpty
//	      when S_SETSWITCH:  SetSwitch
//	      when S_INFO:       Ed(errmsg,InString); WARNING("Unknown info: ");
//	      when S_DELETE:
//	           i:=GetLastTag; InTag(%tag%);
//	%+C        if (tag.val<MinTag) or (tag.val>i.val)
//	%+C        then IERR("Argument to DELETE is out of range")
//	%+C        else
//	                repeat if DISPL(i.HI) = none then -- Nothing
//	                       elsif DISPL(i.HI).elt(i.LO) <> none
//	                       then DELETE(DISPL(i.HI).elt(i.LO));
//	                            DISPL(i.HI).elt(i.LO):=none;
//	                       endif;
//	                while i.val>tag.val do i.val:=i.val-1 endrepeat;
//	%+C        endif;
//	      when S_ZEROAREA:
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qCX,cVAL); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qDI,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        PreMindMask:=wOR(PreMindMask,uES); Qf1(qPUSHR,qES,cOBJ);
//	%-E        PreMindMask:=wOR(PreMindMask,uDI); Qf1(qPUSHR,qDI,cOBJ);
//	%-E        Qf2(qLOADC,0,qAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
//	%+E        GQfetch; Qf1(qPOPR,qECX,cVAL); Pop; GQfetch; Qf1(qPOPR,qEDI,cOBJ);
//	%+E        PreMindMask:=wOR(PreMindMask,uEDI); Qf1(qPUSHR,qEDI,cOBJ);
//	%+E        Qf2(qLOADC,0,qEAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
//	      when S_INITAREA:    intype;
//	%+C                       CheckTosType(T_OADDR);
//	      when S_EVAL:        --  Qf1(qEVAL,0) -- REMOVED FOR AD'HOC TEST ???????
//	      when S_FJUMPIF:
//	%-E        cond:=GQrelation(true); -- doJUMPrel);
//	%+E        cond:=GQrelation;
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%-E        if xFJUMP<>none
//	%-E        then if bAND(cond,7)=q_WNE
//	%-E             then FWRTAB(b):=xFJUMP; xFJUMP:=none endif endif
//	%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMPIF") endif;
//	           if TOS=SAV then DESTAB(b):=ForwJMP(cond)
//	           else LL:=ForwJMP(NotQcond(cond));
//	%-E             if xFJUMP<>none
//	%-E             then if bAND(cond,7)=q_WNE 
//	%-E                  then defFDEST(xFJUMP); xFJUMP:=none endif endif
//	                ClearSTK;
//	                DESTAB(b):=ForwJMP(0); DefFDEST(LL);
//	           endif;
//	%-E        if xFJUMP<>none
//	%-E        then if bAND(cond,7)=q_WEQ then defFDEST(xFJUMP)
//	%-E             else FWRTAB(b):=xFJUMP endif endif
//	      when S_FJUMP:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMP") endif;
//	           DESTAB(b):=ForwJMP(0);
//	%+C        CheckStackEmpty;
//	      when S_FDEST:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.FDEST") endif;
//	           DefFDEST(DESTAB(b)); DESTAB(b):=none;
//	%-E        if FWRTAB(b)<>none then DefFDEST(FWRTAB(b)); FWRTAB(b):=none; endif
//	%+C        CheckStackEmpty;
//	      when S_BDEST:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE.BDEST") endif;
//	           DESTAB(b):=DefBDEST;
//	%+C        CheckStackEmpty;
//	      when S_BJUMP:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMP") endif;
//	           BackJMP(0,DESTAB(b)); DESTAB(b):=none;
//	%+C        CheckStackEmpty;
//	      when S_BJUMPIF:
//	%-E        cond:=GQrelation(false);
//	%+E        cond:=GQrelation;
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMPIF") endif;
//	           if TOS=SAV then BackJMP(cond,DESTAB(b))
//	           else LL:=ForwJMP(NotQcond(cond)); ClearSTK;
//	                BackJMP(0,DESTAB(b)); DefFDEST(LL);
//	           endif;
//	           DESTAB(b):=none;
//	      when S_SWITCH:
//	           InTag(%tag%);
//	%+D        ndest:=InputNumber;
//	%-D        InNumber(%ndest%);
//	           sw:=NEWOBJ(K_SwitchDescr,size(SwitchDescr));
//	           sw.ndest:=ndest.val; sw.nleft:=ndest.val;
//	           if ndest.HI >= MxpSdest
//	           then ERROR("Too large Case-Statement") endif;
//	           i.val:=0; sw.swtab:=NewFixAdr(DSEGID,i); IntoDisplay(sw,tag);
//	%+C        CheckTosInt;
//	           if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//	           a:=sw.swtab;
//	%-E        if DSEGID=DGROUP then a.sbireg:=bOR(oSS,rmBX);
//	%-E        else Qf2b(qLOADSC,qDS,qBX,cOBJ,0,sw.swtab);
//	%-E             a.sbireg:=bOR(oDS,rmBX);
//	%-E        endif;
//	%-E        GetTosAdjustedIn86(qBX); Pop;
//	%+E        GetTosAdjustedIn86(qEBX); Pop;
//
//	%+D        if IDXCHK <> 0 then --- pje 22.10.90
//	%+D           PreMindMask:=wOR(PreMindMask,uBX);
//	%+D %-E       Qf2(qDYADC,qCMP,qBX,cVAL,ndest.val);
//	%+DE          Qf2(qDYADC,qCMP,qEBX,cVAL,ndest.val);
//	%+D %-E       if DSEGID=DGROUP then PreReadMask:=uBX
//	%+D %-E       else PreReadMask:=wOR(uDS,uBX) endif;
//	%+DE          PreReadMask:=uBX;
//	%+D           LL:=ForwJMP(q_WLT);
//	%+D           Qf5(qCALL,0,0,0,X_ECASE); -- OutOfRange ==> ERROR
//	%+D %-E       Qf2(qLOADC,0,qBX,cVAL,0);
//	%+DE          Qf2(qLOADC,0,qEBX,cVAL,0);
//	%+D           PreReadMask:=uBX;
//	%+D %-E       if DSEGID=DGROUP then PreMindMask:=wOR(PreMindMask,uBX)
//	%+D %-E       else PreMindMask:=wOR(PreMindMask,wOR(uDS,uBX)) endif;
//	%+DE          PreMindMask:=wOR(PreMindMask,uBX);
//	%+D           DefFDEST(LL);
//	%+D        endif; --- pje 22.10.90
//
//	%-E        Qf2(qDYADR,qADD,qBX,cVAL,qBX);
//	%+E        a.sibreg:=bOR(bOR(128,bEBX),iEBX); -- swtab+[4*EBX] 
//	           Qf3(qJMPM,0,0,0,a);
//	      when S_SDEST:
//	           InTag(%tag%); sw:=DISPL(tag.HI).elt(tag.LO);
//	%+C        if sw.kind <> K_SwitchDescr
//	%+C        then IERR("Display-entry is not defined as a switch") endif;
//	%+D        sx:=InputNumber;
//	%-D        InNumber(%sx%);
//	%+C        if sx.val >= sw.ndest then IERR("Illegal switch index")
//	%+C        else
//	                i.val:=0; a:=NewFixAdr(CSEGID,i);
//	                if sw.DESTAB(sx.HI)=none
//	                then sw.DESTAB(sx.HI):=
//	                        NEWOBJ(K_AddrBlock,size(AddrBlock)) endif;
//	                sw.DESTAB(sx.HI).elt(sx.LO):=a;
//	                DefLABEL(0,a.fix.val,0);
//	%+C        endif;
//	           sw.nleft:=sw.nleft-1;
//	           if sw.nleft=0
//	           then EmitSwitch(sw); DELETE(sw);
//	                DISPL(tag.HI).elt(tag.LO):=none;
//	           endif;
//	      when S_CONVERT:  type:=intype;
//	           if type < T_REAL
//	           then if TOS.type=T_CHAR then type:=T_BYT1 endif endif;
//	           GQconvert(type)
//	      when S_NEG:  GQneg
//	      when S_ADD:  GQadd
//	      when S_SUB:  GQsub
//	      when S_MULT: GQmult
//	      when S_DIV:  GQdiv
//	      when S_REM:  GQrem
//	      when S_NOT:  GQnot
//	      when S_AND:  GQandxor(qAND)
//	      when S_OR:   GQandxor(qOR )
//	      when S_XOR:  GQandxor(qXOR)
//	      when S_EQV:  GQeqv
//	      when S_IMP:  GQimp
//	%+S   when S_LSHIFTL: GQshift(qSHL); -- Extension to S-Code: Left shift logical
//	%+S   when S_LSHIFTA: GQshift(qSHL); -- Extension to S-Code: Left shift arithm.
//	%+S   when S_RSHIFTL: GQshift(qSHR); -- Extension to S-Code: Right shift logical
//	%+S   when S_RSHIFTA: GQshift(qSAR); -- Extension to S-Code: Right shift arithm.
//	      when S_LOCATE:
//	%+C        CheckTosType(T_AADDR); CheckSosValue;
//	%+C        CheckSosType2(T_OADDR,T_GADDR);
//	%-E        GetTosValueIn86(qAX); Pop; GQfetch;
//	%+E        GetTosValueIn86(qEAX); Pop; GQfetch;
//	           if TOS.type=T_GADDR
//	           then
//	%-E             Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX);
//	%+E             Qf1(qPOPR,qEBX,cVAL); Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	           endif;
//	%-E        Qf1(qPUSHR,qAX,cVAL);
//	%+E        Qf1(qPUSHR,qEAX,cVAL);
//	           Pop; pushTempVAL(T_GADDR);
//	      when S_INCO: GQinco_deco(true)
//	      when S_DECO: GQinco_deco(false)
//	      when S_DIST:
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qDX,cOBJ); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qAX,cOBJ); qPOPKill(2); Pop;
//	%-E        Qf2(qDYADR,qSUBF,qAX,cVAL,qDX); Qf1(qPUSHR,qAX,cVAL);
//	%+E        GQfetch; Qf1(qPOPR,qEDX,cOBJ); Pop;
//	%+E        GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//	%+E        Qf2(qDYADR,qSUBF,qEAX,cVAL,qEDX); Qf1(qPUSHR,qEAX,cVAL);
//	           pushTempVAL(T_SIZE);
//	      when S_COMPARE:
//	%-E        cond:=GQrelation(false);
//	%+E        cond:=GQrelation;
//	           Qf2(qLOADC,0,qAL,cVAL,0); Qf2(qCONDEC,cond,qAL,cVAL,0);
//	           Qf1(qPUSHR,qAL,cVAL);     pushTempVAL(T_BOOL);
//	      otherwise result:=false;
//		default: result = false;
//		}
//	      endcase;
//
//	%+D   if TraceMode <> 0 then DumpStack endif;
//		return result;
//	}
	
	
//	%title ***   R o u t i n e    B o d y   ***
//
//	--- %+S removed to be able to handle "call-back" routines -- pje
//
//	    Routine InRoutine;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	    begin ref(ProfileDescr) p; ref(IntDescr) r;
//	      range(0:MaxWord) nlocbyte; ref(LocDescr) locvar;
//	      infix(WORD) smbx,tag,prftag; range(0:MaxWord) xrela;
//	      range(0:MaxType) xt,type; infix(MemAddr) a; range(0:1) visflag;
//
//	--- TEMP FIX: when a SIMULA procedure is called back from C/Pascal,
//	---           the basic stack invariant (RUN TIME stack empty between
//	---           statements) is violated, since (at the very least) the
//	---           return address from C to Simula is on the stack. Therefore
//	---           the statement break/stepping assumption (that top of stack
//	---           is the control word for these facilities) does not hold.
//	---           Since a correction of this is a major effort, we give a
//	---           warning (once) if this routine is entered.
//	%-S   if WarnDEBUG3
//	%-S   then showWarn("DEBUG 3 is dangerous when using call-back");
//	%-S        warnDEBUG3:=false endif;
//	--- FIX END
//
//	      InTag(%tag%); InTag(%prftag%);
//	      r:=if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
//	      if r=none
//	      then r:=NEWOBJ(K_IntRoutine,size(IntDescr)); smbx.val:=0;
//	           r.adr:=NewFixAdr(CSEGID,smbx); IntoDisplay(r,tag);
//	      else r:=DISPL(tag.HI).elt(tag.LO);
//	           smbx:=FIXTAB(r.adr.fix.HI).elt(r.adr.fix.LO).smbx;
//	      endif;
//	      p:=DISPL(prftag.HI).elt(prftag.LO);
//	      if p.kind=P_ROUTINE then visflag:=1 else visflag:=0 endif
//	      r.type:=p.type; nlocbyte:=0; InsideRoutine:=true;
//	%+S   repeat InputInstr while CurInstr=S_LOCAL
//	%+S   do nlocbyte:=InLocal(nlocbyte) endrepeat;
//	%+S   DefLABEL(qBPROC,r.adr.fix.val,smbx.val); Qf2(qENTER,visflag,0,0,nlocbyte)
//	%+S   repeat while Instruction do InputInstr endrepeat;
//	---   currently no use for local variables in prod. system routines -- pje
//	%-S   visflag:=0; -- always FAR from C
//	%-S   DefLABEL(qBPROC,r.adr.fix.val,smbx.val); Qf2(qENTER,visflag,0,0,nlocbyte)
//	%-S   repeat InputInstr while Instruction do endrepeat;
//	      if CurInstr <> S_ENDROUTINE
//	      then IERR("Missing - endroutine") endif;
//	%+D   CheckStackEmpty;
//	%+E   Qf2(qLEAVE,0,0,0,nlocbyte); Qf2(qRET,0,0,0,p.nparbyte)
//	%-E   if p.WithExit=0
//	%-E   then Qf2(qLEAVE,0,0,0,nlocbyte); Qf2(qRET,visflag,0,0,p.nparbyte)
//	%-E   else if BNKLNK=0
//	%-E        then Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ) endif
//	%-E        Qf2(qLEAVE,0,0,0,nlocbyte);
//	%-E        if BNKLNK=0  then Qf2(qRET,visflag,0,0,p.nparbyte)
//	%-E        else Qf1(qPOPR,qAX,cVAL); Qf1(qPOPR,qBX,cVAL); a:=X_GOTO;
//	%-E %+D         showWARN("E-GOTO called");
//	%-E             PreReadMask:=wOR(uAX,uBX); Qf5(qJMP,0,0,0,a)
//	%-E        endif;
//	%-E   endif;
//	      DefLABEL(qEPROC,r.adr.fix.val,smbx.val);
//	      peepExhaust(true); InsideRoutine:=false;
//	    end;
//
//	%title ***   Call  Instruction   ***
//	public static void callInstruction(int n) {
//		Scode.inTag();
//		callDefault();
//	}
//	
//	%page
//	Routine wWordsOnStack;
//	import ref(StackItem) x; export range(0:MaxByte) res;
//	begin
//	%+D   RST(R_WordsOnStack);
//	      case 0:K_Max (x.kind)
//	      when K_Temp,K_Result,K_Coonst:
//	           res:=(TTAB(x.type).nbyte+(AllignFac-1))/AllignFac;
//	      when K_Address:
//	           res:=if x qua Address.AtrState=NotStacked then 0 else 1;
//	%-E        if x qua Address.ObjState<>NotStacked then res:=res+2 endif;
//	%+E        if x qua Address.ObjState<>NotStacked then res:=res+1 endif;
//	%+C   otherwise IERR("PARSE.wWordsOnStack")
//	      endcase;
//	end;
//
//	%page
//	private static void callDefault() {
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	import ref(ProfileDescr) spec; range(0:MaxWord) nstckval;
//	begin ref(ProfileItem) pitem; range(0:255) npop; infix(MemAddr) entr;
//	      ref(StackItem) z; ref(Temp) result; ref(Descriptor) rut;
//	      range(0:MaxByte) b; infix(WORD) rtag;
//	      range(0:MaxType) xt;
//	      range(0:MaxByte) nwm,nbi; -- no.word/bytes to be moved/inserted on stack
//	      range(0:MaxByte) xlng;    -- size of export on 86-stack (in bytes)
//
//	%+D   RST(R_CallDefault);
//	      pitem:=NewProfileItem(T_VOID,spec);
//	      pitem.nasspar:=0; rut:=none; npop:=0; xt:=0; xlng:=0;
//	      if nstckval=0
//	      then if pitem.spc.type <> 0
//	           then xt:=pitem.spc.type; pushTempVAL(xt); result:=takeTOS;
//	                xlng:=TTAB(xt).nbyte;
//	%-E             Qf2(qDYADC,qSUB,qSP,cSTP,wAllign(%xlng%));
//	%+E             Qf2(qDYADC,qSUB,qESP,cSTP,wAllign(%xlng%));
//	                Push(result); result.kind:=K_Result;
//	           endif;
//	           Push(pitem);
//	      else z:=TOS; nwm:=0; nbi:=0;
//	           if nstckval > 1 then IERR("PARSE.REPCALL") endif;
//	           if pitem.spc.type = 0 then Precede(pitem,z);
//	           else xt:=pitem.spc.type; pushTempVAL(xt); result:=takeTOS;
//	                xlng:=TTAB(xt).nbyte; nbi:=wAllign(%xlng%);
//	                Precede(result,z); Precede(pitem,z); result.kind:=K_Result;
//	           endif;
//	           if nbi <> 0
//	           then nwm:=wWordsOnStack(TOS);
//	                if nwm <> 0 then SpaceOnStack(nwm,nbi)
//	                else
//	%-E                  Qf2(qDYADC,qSUB,qSP,cSTP,wAllign(%xlng%));
//	%+E                  Qf2(qDYADC,qSUB,qESP,cSTP,wAllign(%xlng%));
//	                endif;
//	           endif;
//	           npop:=npop+PutPar(pitem,nstckval);
//	      endif;
		
//	      repeat InputInstr while CurInstr <> S_CALL
//	      do repeat while Instruction do InputInstr endrepeat;
//	         if    CurInstr=S_ASSPAR    then npop:=npop+PutPar(pitem,1)
//	         elsif CurInstr=S_ASSREP    then
//	%+D                                      b:=InputByte;
//	%-D                                      InByte(%b%);
//	                                         npop:=npop+PutPar(pitem,b)
//	         elsif CurInstr=S_CALL_TOS  then goto F
//	         else  IERR("Syntax error in call Instruction") endif;
//	%+D      if TraceMode <> 0 then DumpStack endif;
//	      endrepeat;
//		
//		Scode.inputInstr();
//		LOOP:while(Scode.curinstr != Scode.S_CALL) {
//			while(instruction()) Scode.inputInstr();
//				
//			if(Scode.curinstr == Scode.S_ASSPAR) ; // OK
//			else if(Scode.curinstr == Scode.S_ASSREP) Scode.inByte();
//			else if(Scode.curinstr == Scode.S_CALL_TOS) break LOOP;
//			else Util.IERR("Syntax error in call Instruction");
//		
//			
//			Scode.inputInstr();
//		}
//	      ---------  Call Routine  ---------
//	      InTag(%rtag%); rut:=DISPL(rtag.HI).elt(rtag.LO);
//		Scode.inTag();
//		
//	      if rut.kind=K_IntRoutine then entr:=rut qua IntDescr.adr
//	      else entr.kind:=extadr;
//	%-E        entr.sbireg:=0;
//	%+E        entr.sibreg:=NoIBREG;
//	           entr.rela.val:=rut qua ExtDescr.adr.rela;
//	           entr.smbx:=rut qua ExtDescr.adr.smbx;
//	      endif;
//	%+C   if entr=noadr then IERR("Undefined routine entry") endif;
//	F:    ---------  Final Actions  ---------
//	%+C   if pitem.nasspar <> pitem.spc.npar
//	%+C   then IERR("Wrong number of Parameters") endif;
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,54); outstring(".   CALL "); print(TOS) endif;
//	      if rut=none
//	      then
//	%+C        CheckTosType(T_RADDR);
//	%-E        GetTosValueIn86R3(qAX,qBX,0); Pop;
//	%-E        entr:=X_CALL; PreReadMask:=wOR(uAX,uBX);
//	%+E        GetTosValueIn86(qEAX); Pop;
//	%+E        entr.kind:=reladr; entr.rela.val:=0; entr.sibreg:=bEAX+iESP;
//	      endif;
//	      Qf5(qCALL,spec.WithExit,0,xlng+pitem.spc.nparbyte,entr);
//	      repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
//	%+C   if TOS <> pitem then IERR("SSTMT.Call") endif;
//	      Pop;
//	      if xlng <> 0 then Qf2(qADJST,0,0,0,xlng) endif;
//	      if xt <> 0 then result.kind:=K_Temp endif;
//	%-E   if CHKSTK
//	%-E   then if spec.WithExit <> 0
//	%-E        then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	%-E   endif;
//	}
//	%page
//
//	Routine PutPar;
//	import ref(ProfileItem) pItm; range(0:MaxWord) nrep;
//	export range(0:255) npop;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin range(0:MaxWord) n; range(0:255) i,c; ref(StackItem) s;
//	      range(0:MaxType) st,pt; infix(MemAddr) opr;
//
//	%+D   RST(R_PutPar);
//	      pt:=pItm.spc.par(pItm.nasspar).type;
//	      c :=pItm.spc.par(pItm.nasspar).count;
//	      n:=TTAB(pt).nbyte; i:=nrep;
//	%+C   if nrep>c then IERR("Too many values in repeated param") endif;
//	      pItm.nasspar:=pItm.nasspar+1;
//	      s:=TOS; st:=s.type;
//	      --- First: Treat TOS ---
//	      if st <> pt then GQconvert(pt)
//	      elsif s.kind = K_Address then s.type:=st
//	      else GQconvert(pt) endif;
//	      if TOS.kind=K_Address then GQfetch endif; s:=TOS;
//	---   GQparam;  ---- JUST FOR TEST !!!!!!!!!!!!!!
//	--      ---- **** CONVERT GADDR PARAMETER IF C (pascal?)         -- pje
//	--      ---- **** code copied from 'name2ref' *****              -- pje
//	--      if pItm.spc.Pkind = P_C                                  -- pje
//	--      then if pt = T_GADDR                                     -- pje
//	--%-E        then GetTosValueIn86R3(qAX,qDX,qCX);                -- pje
//	--%-E             Qf2(qDYADR,qADD,qAX,cADR,qDX);                 -- pje
//	--%-E             Qf1(qPUSHR,qCX,cADR); Qf1(qPUSHR,qAX,cADR);    -- pje
//	--%+E        then GetTosValueIn86R3(qEAX,qEDX,0);                -- pje
//	--%+E             Qf2(qDYADR,qADD,qEAX,cADR,qEDX);               -- pje
//	--%+E             Qf1(qPUSHR,qEAX,cADR);                         -- pje
//	--      endif endif;                                             -- pje
//
//	      --- Then: Treat rest of rep-par ---
//	      repeat i:=i-1 while i <> 0
//	      do s:=s.suc;
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
//	      endrepeat;
//	%-E  L1:
//	   L2:if nrep < c
//	      then
//	%-E        Qf2(qDYADC,qSUB,qSP,cSTP,(c-nrep)*n);
//	%+E        Qf2(qDYADC,qSUB,qESP,cSTP,(c-nrep)*n);
//	      endif;
//	      npop:=nrep;
//	end;
//
//	%-E Routine ConvRepWRD2; import range(0:255) nrep;
//	%-E begin range(0:255) i; infix(MemAddr) tmp;
//	%-E %+D   RST(R_ConvRepWRD2);
//	%-E       i:=nrep; tmp:=TMPAREA;
//	%-E       repeat while i <> 0
//	%-E       do case 0:T_max (TOS.type)
//	%-E          when T_WRD4:       -- 4-byte signed integer in 86
//	%-E               -- CHECK_RANGE(-32768:32767)
//	%-E               Qf4(qPOPM,0,qAL,cVAL,2,tmp); qPOPKill(2);
//	%-E          when T_WRD2:       -- 2-byte signed integer in 86
//	%-E               Qf4(qPOPM,0,qAL,cVAL,2,tmp);
//	%-E          when T_BYT2:       -- 2-byte unsigned integer in 86
//	%-E               -- CHECK_RANGE(-32768:32767)
//	%-E               Qf4(qPOPM,0,qAL,cVAL,2,tmp);
//	%-E          when T_BYT1,       -- 1-byte unsigned integer in 86
//	%-E               T_CHAR:       -- Character
//	%-E               GetTosAdjustedIn86(qAX); Qf3(qSTORE,0,qAX,cVAL,tmp);
//	%-E          endcase;
//	%-E          i:=i-1; Pop; tmp.rela.val:=tmp.rela.val+2;
//	%-E       endrepeat;
//	%-E       Qf4(qPUSHM,0,0,cVAL,2*nrep,TMPAREA);
//	%-E       repeat while nrep<>0
//	%-E       do pushTempVAL(T_WRD2); nrep:=nrep-1 endrepeat;
//	%-E end;
//
//	Routine ConvRepWRD4; import range(0:255) nrep;
//	begin range(0:255) i; range(0:MaxType) FromType; infix(MemAddr) tmp;
//	%-E   infix(MemAddr) opr;
//	%+D   RST(R_ConvRepWRD4);
//	      i:=nrep; tmp:=TMPAREA;
//	      repeat while i <> 0
//	      do FromType:=TOS.type
//	         case 0:T_max (FromType)
//	         when T_WRD4:       -- 4-byte signed integer in 86
//	%-E           opr:=tmp; opr.rela.val:=opr.rela.val+2;
//	%-E           Qf4(qPOPM,0,qAL,cVAL,2,tmp); Qf4(qPOPM,0,qAL,cVAL,2,opr);
//	%+E           Qf4(qPOPM,0,qAL,cVAL,4,tmp);
//	         when T_WRD2:       -- 2-byte signed integer in 86
//	%-E           opr:=tmp; opr.rela.val:=opr.rela.val+2;
//	%-E           Qf1(qPOPR,qAX,cVAL); Qf1(qCWD,qAX,cVAL);
//	%-E           Qf3(qSTORE,0,qAX,cVAL,tmp); Qf3(qSTORE,0,qDX,cVAL,opr);
//	%+E           Qf1(qPOPR,qAX,cVAL); Qf2(qMOV,qSEXT,qAX,cVAL,qAX)
//	%+E           Qf3(qSTORE,0,qEAX,cVAL,tmp);
//	         when T_BYT2:       -- 2-byte unsigned integer in 86
//	%-E           opr:=tmp; opr.rela.val:=opr.rela.val+2;
//	%-E           Qf1(qPOPR,qAX,cVAL); Qf2(qLOADC,0,qDX,cVAL,0);
//	%-E           Qf3(qSTORE,0,qAX,cVAL,tmp); Qf3(qSTORE,0,qDX,cVAL,opr);
//	%+E           GetTosAdjustedIn86(qEAX); Qf3(qSTORE,0,qEAX,cVAL,tmp);
//	         when T_BYT1,       -- 1-byte unsigned integer in 86
//	              T_CHAR:       -- Character
//	%-E           opr:=tmp; opr.rela.val:=opr.rela.val+2;
//	%-E           GetTosAdjustedIn86(qAX); Qf2(qLOADC,0,qDX,cVAL,0);
//	%-E           Qf3(qSTORE,0,qAX,cVAL,tmp); Qf3(qSTORE,0,qDX,cVAL,opr);
//	%+E           GetTosAdjustedIn86(qEAX); Qf3(qSTORE,0,qEAX,cVAL,tmp);
//	         endcase;
//	         i:=i-1; Pop; tmp.rela.val:=tmp.rela.val+4;
//	      endrepeat;
//	      Qf4(qPUSHM,0,0,cVAL,4*nrep,TMPAREA);
//	      repeat while nrep<>0 do pushTempVAL(T_WRD4); nrep:=nrep-1 endrepeat;
//	end;
//
//	%+S %-E Routine GetTosPntr; import range(0:nregs) sr,br,xr;
//	%+S %-E begin Qf1(qPOPR,xr,cVAL); Qf1(qPOPR,br,cOBJ);
//	%+S %-E       Qf2(qDYADR,qADD,br,cADR,xr); Qf1(qPOPR,sr,cOBJ);
//	%+S %-E end;
//
//	%+SE Routine GetTosPntr; import range(0:nregs) br,xr;
//	%+SE begin Qf1(qPOPR,xr,cVAL); Qf1(qPOPR,br,cOBJ);
//	%+SE       Qf2(qDYADR,qADD,br,cADR,xr);
//	%+SE end;
//
//	Routine CallFORTRAN;
//	import ref(ProfileDescr) spec; range(0:MaxWord) nstckval;
//	begin
//
//	------  Kopi av CallDefault n}r den er ferdig ------
//
//	end;
//	%page
//	Routine CallSYS;
//	import ref(ProfileDescr) spec; range(0:MaxWord) nstckval,Pkind;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin ref(ProfileItem) pitem; range(0:255) npop; infix(MemAddr) entr,opr;
//	      ref(Descriptor) rut; range(0:MaxType) xt; infix(WORD) rtag;
//	      range(0:MaxByte) dx,xlng; ref(Temp) result;
//	%-E   Boolean TurboC; TurboC:=false;
//	%-E   if CBIND=cMS then -- OK
//	%-E   elsif CBIND=cTURBO then TurboC:=true
//	%-E   else showWARN("No Mixed-Language Binding Defined. MicroSoft rules assumed")
//	%-E        CBIND:=cMS;
//	%-E   endif;
//	      if spec.type = 0 then xt:=0; xlng:=0
//	      else xt:=spec.type; xlng:=TTAB(xt).nbyte endif;
//	      pitem:=NewProfileItem(T_VOID,spec);
//	      pitem.nasspar:=0; npop:=0;
//	%+C   if StackDepth87 > 0     then IERR("PARSE.CallSYS-1") endif;
//	      if nstckval=0 then Push(pitem)
//	      else
//	%+C        if nstckval > 1 then IERR("PARSE.CallSYS-2") endif;
//	           Precede(pitem,TOS); npop:=npop+PutPar(pitem,1)
//	      endif;
//	      repeat InputInstr while CurInstr <> S_CALL
//	      do repeat while Instruction do InputInstr endrepeat;
//	         if    CurInstr=S_ASSPAR
//	         then npop:=npop+PutPar(pitem,1)
//	         else  IERR("Syntax error in call Instruction") endif;
//	         ---- **** CONVERT GADDR PARAMETER IF C/Pascal            -- pje
//	         ---- **** code copied from 'name2ref' *****              -- pje
//	         if (spec.Pkind = P_C) or (spec.Pkind = P_PASCAL)         -- pje
//	         then if TOS.type = T_GADDR                               -- pje
//	%-E           then GetTosValueIn86R3(qAX,qDX,qCX);                -- pje
//	%-E                Qf2(qDYADR,qADD,qAX,cADR,qDX);                 -- pje
//	%-E                Qf1(qPUSHR,qCX,cADR); Qf1(qPUSHR,qAX,cADR);    -- pje
//	%+E           then GetTosValueIn86R3(qEAX,qEDX,0);                -- pje
//	%+E                Qf2(qDYADR,qADD,qEAX,cADR,qEDX);               -- pje
//	%+E                Qf1(qPUSHR,qEAX,cADR);                         -- pje
//	         endif endif;                                             -- pje
//	%+D      if TraceMode <> 0 then DumpStack endif;
//	      endrepeat;
//	      ---------  Call Routine  ---------
//	      InTag(%rtag%); rut:=DISPL(rtag.HI).elt(rtag.LO);
//	      if rut.kind=K_IntRoutine then entr:=rut qua IntDescr.adr
//	      else entr.kind:=extadr;
//	%-E        entr.sbireg:=0;
//	%+E        entr.sibreg:=NoIBREG;
//	           entr.rela.val:=rut qua ExtDescr.adr.rela;
//	           entr.smbx:=rut qua ExtDescr.adr.smbx;
//	      endif;
//	%+C   if entr=noadr then IERR("Undefined routine entry") endif;
//	      ---------  Final Actions  ---------
//	%+C   if pitem.nasspar <> pitem.spc.npar
//	%+C   then IERR("Wrong number of Parameters") endif;
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,54); outstring(".   CALL ");
//	%+D        print(TOS) endif;
//
//	%+c   if pitem.spc.Pkind = P_C
//	%+c   then
//	%+c        if pitem.spc.npar > 2 then RevPar(pitem,1) endif;
//	%+c   endif;
//
//	      Qf5(qCALL,Pkind,0,spec.nparbyte,entr);
//	      repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
//	%+C   if TOS <> pitem then IERR("PARSE.CallSYS-3") endif;
//	      Pop;
//	      if xlng <> 0 -- Push Result onto Stack --
//	      then if xt=T_REAL
//	           then
//	%-E             if TurboC then dx:=8 else dx:=xlng endif;
//	%+E             dx:=8
//	           else dx:=xlng endif;
//	           if dx > 4
//	%-E        then if TurboC
//	%-E             then PushFromNPX(T_LREAL,xt); StackDepth87:=StackDepth87+1;
//	%-E             else MindMask:=wOR(MindMask,uDX); Qf2(qMOV,0,qDS,cANY,qDX);
//	%-E                  MindMask:=wOR(MindMask,uAX); Qf2(qMOV,0,qDI,cANY,qAX);
//	%-E                  opr.kind:=reladr; opr.rela.val:=0;
//	%-E                  opr.sbireg:=bOR(oDS,rmDI); opr.segmid.val:=0;
//	%-E                  Qf4(qPUSHM,0,0,
//	%-E                      if xt<=T_max then cTYPE(xt) else cANY,xlng,opr);
//	%-E             endif;
//	%-E        elsif xlng > 2
//	%-E        then MindMask:=wOR(MindMask,uDX); Qf1(qPUSHR,qDX,cANY);
//	%-E             MindMask:=wOR(MindMask,uAX); Qf1(qPUSHR,qAX,cANY);
//	%-E        else MindMask:=wOR(MindMask,uAX); Qf1(qPUSHR,qAX,cANY) endif;
//
//	%+E        then PushFromNPX(T_LREAL,xt); StackDepth87:=StackDepth87+1;
//	%+E        else MindMask:=wOR(MindMask,uEAX);
//	%+E             if xlng > 2 then Qf1(qPUSHR,qEAX,cANY)
//	%+E             else Qf1(qPUSHR,qAX,cANY) endif;
//	%+E        endif;
//	           pushTempVAL(xt);
//	      endif;
//	end;
//	%page
//
//	%+c Routine RevPar;  --- Reverse Parameter Stack (C-Interface)
//	%+c import ref(ProfileItem) pItm;
//	%+c ---   range(0:MaxWord) nReg; -- Number of Registers for compact code
//	%+c       range(0:MaxWord) sPar; -- Skip First 'sPar' parameters
//	%+c begin range(0:MaxWord) n,p,c,nPar;
//	%+c       range(0:MaxType) pt; infix(MemAddr) opr;
//	%+c 
//	%+c       opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	%+c       p:=0; nPar:=pItm.spc.nPar;
//	%+c 
//	%+c ---   PUSH SS; POP DS; MOV DI,SP
//	%+c %-E   Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%+c %-E   Qf2(qMOV,0,qDI,cSTP,qSP); opr.sbireg:=bOR(oSS,rmDI);
//	%+c %+E   Qf2(qMOV,0,qEDI,cSTP,qESP); opr.sibreg:=bEDI+NoIREG;
//	%+c 
//	%+c       repeat while nPar > sPar
//	%+c       do nPar:=nPar-1;
//	%+c          pt:=pItm.spc.par(nPar).type;
//	%+c          c :=pItm.spc.par(nPar).count;
//	%+c          n:=wAllign(%TTAB(pt).nbyte%)*c;
//	%+c          opr.rela.val:=p; p:=p+n;
//	%+c %-E      PreMindMask:=wOR(PreMindMask,uDI);
//	%+c %+E      PreMindMask:=wOR(PreMindMask,uEDI);
//	%+c          Qf4(qPUSHM,0,0,
//	%+c              if pt<=T_max then cTYPE(pt) else cANY,n,opr);
//	%+c       endrepeat;
//	%+c 
//	%+c %-E   Qf2(qMOV,0,qSI,cSTP,qSP);
//	%+c %-E   Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%+c %-E   Qf2(qLOADC,0,qCX,cVAL,p/2);
//	%+c %+E   Qf2(qMOV,0,qESI,cSTP,qESP);
//	%+c %+E   Qf2(qLOADC,0,qECX,cVAL,p/4);
//	%+c       Qf2(qRSTRW,qRMOV,qCLD,cANY,qREP);
//	%+c 
//	%+c %-E   Qf2(qDYADC,qADD,qSP,cSTP,p);
//	%+c %+E   Qf2(qDYADC,qADD,qESP,cSTP,p);
//	%+c end;
//	%page
//	%+S %-E Routine DosRegUse; import range(0:MaxWord) read,write,mind;
//	%+S %-E begin PreReadMask:=wOR(PreReadMask,read);
//	%+S %-E       PreWriteMask:=wOR(PreWriteMask,write);
//	%+S %-E       PreMindMask:=wOR(PreMindMask,mind);
//	%+S %-E end;
//
//	Routine CallInline;
//	import ref(ProfileDescr) spec; range(0:MaxWord) nstckval;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin ref(ProfileItem) pitem; ref(Descriptor) rut; ref(StackItem) z;
//	      range(0:MaxByte) b; infix(WORD) rtag; infix(MemAddr) opr,tmp;
//	      pitem:=NewProfileItem(T_VOID,spec); pitem.nasspar:=0;
//	      if nstckval=0 then Push(pitem);
//	      elsif nstckval > 1 then IERR("PARSE.REPCALL-2")
//	      else z:=TOS; PutPar(pitem,1) Precede(pitem,z) endif;
//	      repeat InputInstr while CurInstr <> S_CALL
//	      do repeat while Instruction do InputInstr endrepeat;
//	         if    CurInstr=S_ASSPAR    then PutPar(pitem,1)
//	         elsif CurInstr=S_ASSREP    then
//	%+D                                      b:=InputByte;
//	%-D                                      InByte(%b%);
//	                                         PutPar(pitem,b)
//	         else  IERR("Syntax error in Inline Call") endif;
//	%+D      if TraceMode <> 0 then DumpStack endif;
//	      endrepeat;
//	      InTag(%rtag%); rut:=DISPL(rtag.HI).elt(rtag.LO);
//	      ---------  Final Actions  ---------
//	%+C   if pitem.nasspar <> pitem.spc.npar
//	%+C   then IERR("Wrong number of Parameters") endif;
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,54); outstring(".   CALL "); print(TOS) endif;
//	      case 0:P_max (spec.Pkind)
//	      when P_RSQROO,P_SQROOT: GQsqrt;
//	%+E   when P_AR1IND:
//	%+E        tmp:=NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	%+E        opr.kind:=reladr; opr.sibreg:=iESP+bEBX;
//	%+E        GetTosAdjustedIn86(qEAX); Pop; -- Index
//	%+E        GetTosAdjustedIn86(qEBX); Pop; -- Array object reference
//	%+E %+C    if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+E        Pop; -- Profile Item
//	%+E        opr.rela.val:=12; -- [EBX]+12:lb  [EBX]+16:ub
//	%+E  ---   if INDEX-Checking
//	%+E  ---   then
//	%+E             PresaveOprRegs(opr); Qf3(qBOUND,0,qEAX,cVAL,opr);
//	%+E  ---   endif;
//	%+E        Qf3(qDYADM,qSUBF,qEAX,cVAL,opr); Qf3(qSTORE,0,qEAX,cVAL,tmp);
//	%+E   when P_AR2IND:
//	%+E        tmp:=NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	%+E        opr.kind:=reladr; opr.sibreg:=iESP+bEBX;
//	%+E        GetTosAdjustedIn86(qEAX); Pop; -- Index 2
//	%+E        GetTosAdjustedIn86(qESI); Pop; -- Index 1
//	%+E        GetTosAdjustedIn86(qEBX); Pop; -- Array object reference
//	%+E %+C    if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+E        Pop; -- Profile Item
//	%+E  ---   if INDEX-Checking
//	%+E  ---   then
//	%+E             opr.rela.val:=12; -- [EBX]+12:lb1  [EBX]+16:ub1
//	%+E             PresaveOprRegs(opr); Qf3(qBOUND,0,qESI,cVAL,opr);
//	%+E             opr.rela.val:=24; -- [EBX]+24:lb2  [EBX]+28:ub2
//	%+E             PresaveOprRegs(opr); Qf3(qBOUND,0,qEAX,cVAL,opr);
//	%+E  ---   endif;
//	%+E        opr.rela.val:=20; -- [EBX]+20:dope
//	%+E        PresaveOprRegs(opr);
//	%+E        NotMindMask:=uEDX; Qf3(qTRIADM,qIMULF,qw_D,cVAL,opr);
//	%+E        opr.rela.val:=32; -- [EBX]+32:neg_base
//	%+E        Qf3(qDYADM,qADDF,qEAX,cVAL,opr);
//	%+E        Qf2(qDYADR,qADDF,qESI,cVAL,qEAX); Qf3(qSTORE,0,qESI,cVAL,tmp);
//
//	%+S   when P_MOVEIN:
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+S %-E    Qf2(qMONADR,qSHR1,qCX,cVAL,0);       -- CX:=CX/2
//	%+S %-E    Qf1(qPOPR,qDI,cOBJ); Qf1(qPOPR,qES,cOBJ); Pop;
//	%+S %-E    Qf1(qPOPR,qSI,cOBJ); Qf1(qPOPR,qDS,cOBJ); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+SE       Qf2(qMONADR,qSHR1,qECX,cVAL,0);      -- ECX:=ECX/2
//	%+SE       Qf2(qMONADR,qSHR1,qECX,cVAL,0);      -- ECX:=ECX/2
//	%+SE       Qf1(qPOPR,qEDI,cOBJ); Pop; Qf1(qPOPR,qESI,cOBJ); Pop;
//	%+S        Qf2(qRSTRW,qRMOV,qCLD,cANY,qREP);
//	%+SW     outstring("- MOVEIN RSTRW RMOV CLD ANY REP l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E  when P_DOS_CREF:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qCX); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,60);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDX),uCX),uAX,uAX);
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_WRD2);
//	%+S %-E  when P_DOS_OPEN:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qAL); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,61);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDX),uAL),uAX,uAX);
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_WRD2);
//	%+S %-E  when P_DOS_CLOSE:
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop; Qf2(qLOADC,0,qAH,cVAL,62);
//	%+S %-E       DosRegUse(uBX,0,0); Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_READ:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qCX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,63);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDX),wOR(uBX,uCX)),uAX,uAX);
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_WRD2);
//	%+S %-E  when P_DOS_WRITE:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qCX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,64);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDX),wOR(uBX,uCX)),uAX,uAX);
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_WRD2);
//	%+S %-E  when P_DOS_DELF:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop; Qf2(qLOADC,0,qAH,cVAL,65);
//	%+S %-E       DosRegUse(wOR(uDS,uDX),0,0); Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_FPTR:
//	%+S %-E       GetTosValueIn86R3(qDX,qCX,0); Pop;
//	%+S %-E       GetTosAdjustedIn86(qAL); Pop;
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,66);
//	%+S %-E       DosRegUse(wOR(wOR(uAL,uBX),wOR(uCX,uDX)),
//	%+S %-E                                  wOR(uDX,uAX),wOR(uDX,uAX));
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_CDIR:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop; Qf2(qLOADC,0,qAH,cVAL,59);
//	%+S %-E       DosRegUse(wOR(uDS,uDX),0,0); Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_ALOC:
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop; Qf2(qLOADC,0,qAH,cVAL,72);
//	%+S %-E       DosRegUse(uBX,wOR(uAX,uBX),wOR(uAX,uBX));
//	%+S %-E       Qf1(qDOS2,0,cANY); Qf1(qPUSHR,qBX,cANY); Qf1(qPUSHR,qAX,cANY);
//	%+S %-E       Qf2(qLOADC,0,qAX,cVAL,0); Qf1(qPUSHR,qAX,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_TERM:
//	%+S %-E       GetTosAdjustedIn86(qAL); Pop; Qf2(qLOADC,0,qAH,cVAL,76);
//	%+S %-E       DosRegUse(uAL,0,0); Qf2(qINT,0,0,0,33);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_TIME:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,44);
//	%+S %-E       DosRegUse(0,wOR(uCX,uDX),wOR(uCX,uDX));
//	%+S %-E       Qf2(qINT,0,0,0,33); Qf1(qPUSHR,qCX,cVAL); Qf1(qPUSHR,qDX,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_DATE:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,42);
//	%+S %-E       DosRegUse(0,wOR(wOR(uCX,uDX),uAL),wOR(wOR(uCX,uDX),uAL));
//	%+S %-E       Qf2(qINT,0,0,0,33); Qf1(qPUSHR,qCX,cVAL); Qf1(qPUSHR,qDX,cVAL);
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,0); Qf1(qPUSHR,qAX,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_VERS:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,48);
//	%+S %-E       DosRegUse(0,wOR(wOR(uAX,uBX),uCX),wOR(wOR(uAX,uBX),uCX));
//	%+S %-E       Qf2(qINT,0,0,0,33);
//	%+S %-E       Qf2(qLOADC,0,qDH,cVAL,0); Qf2(qMOV,0,qDL,cVAL,qBH);
//	%+S %-E       Qf2(qLOADC,0,qBH,cVAL,0);
//	%+S %-E       Qf1(qPUSHR,qBX,cVAL); Qf1(qPUSHR,qCX,cVAL);
//	%+S %-E       Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_EXEC:
//	%+S %-E       GetTosPntr(qES,qBX,qAX); Pop;
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAL,cVAL,0); Qf2(qLOADC,0,qAH,cVAL,75);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAL,uES),uDS),wOR(uBX,uDX)),uALL,0);
//	%+S %-E       Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_IOCTL:
//	%+S %-E       GetTosPntr(qDS,qDX,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qCX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qAL); Pop;
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop;
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,68);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAX,uBX),uCX),wOR(uDX,uDS)),
//	%+S %-E                                 wOR(uAX,uDX),wOR(uAX,uDX));
//	%+S %-E       Qf1(qDOS2,0,cANY);
//	%+S %-E       Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_LOCK:
//	%+S %-E       GetTosAdjustedIn86(qDI); Pop;
//	%+S %-E       GetTosAdjustedIn86(qSI); Pop;
//	%+S %-E       GetTosAdjustedIn86(qDX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qCX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qAL); Pop;
//	%+S %-E       GetTosAdjustedIn86(qBX); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,92);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAX,uBX),wOR(uCX,uDX)),
//	%+S %-E                                       wOR(uDX,uDS)),0,0);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDX),uCX),uAX,uAX);
//	%+S %-E       Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_GDRV:
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,25);
//	%+S %-E       DosRegUse(0,uAX,uAL);
//	%+S %-E       Qf1(qDOS2,0,cANY);
//	%+S %-E       Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %-E  when P_DOS_GDIR:
//	%+S %-E       GetTosPntr(qDS,qSI,qAX); Pop;
//	%+S %-E       GetTosAdjustedIn86(qDL); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,71);
//	%+S %-E       DosRegUse(wOR(wOR(uDS,uDL),uSI),0,0); Qf1(qDOS2,0,cANY);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S   when P_APX_SCMPEQ:
//	%+S %-E    GetTosPntr(qDS,qSI,qAX); Pop;
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qESI,qEAX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRCMPS,qCLD,cVAL,qREPEQ);
//	%+SW     outstring("- APX_SCMPEQ RSTRW RMOV CLD ANY REP l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S        Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BOOL);
//	%+S   when P_APX_SMOVEI:
//	%+S %-E    GetTosPntr(qDS,qSI,qAX); Pop;
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qESI,qEAX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRMOV,qCLD,cVAL,qREP);
//	%+SW     outstring("- APX_SMOVEI RSTRB RMOV CLD VAL REP l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S   when P_APX_SMOVED:
//	%+S %-E    GetTosPntr(qDS,qSI,qAX); Pop;
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qESI,qEAX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRMOV,qSTD,cVAL,qREP);
//	%+SW     outstring("- APX_SMOVED RSTRB RMOV STD VAL REP l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S   when P_APX_SSKIP:
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+S        GetTosAdjustedIn86(qAL); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRSCAS,qCLD,cVAL,qREPEQ);
//	%+SW     outstring("- APX_SSKIP RSTRB RSCAS CLD VAL REPEQ l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S        Qf1(qPUSHR,qCX,cVAL); pushTempVAL(T_WRD2);
//	%+S   when P_APX_STRIP:
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+S        GetTosAdjustedIn86(qAL); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRSCAS,qSTD,cVAL,qREPEQ);
//	%+SW     outstring("- APX_STRIP RSTRB RSCAS STD VAL REPEQ l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S        Qf1(qPUSHR,qCX,cVAL); pushTempVAL(T_WRD2);
//	%+S   when P_APX_SFINDI:
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+S        GetTosAdjustedIn86(qAL); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRSCAS,qCLD,cVAL,qREPNE);
//	%+SW     outstring("- APX_SFINDI RSTRB RSCAS CLD VAL REPNE l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S        Qf1(qPUSHR,qCX,cVAL); pushTempVAL(T_WRD2);
//	%+S   when P_APX_SFINDD:
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+S        GetTosAdjustedIn86(qAL); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRSCAS,qSTD,cVAL,qREPNE);
//	%+SW     outstring("- APX_SFINDD RSTRB RSCAS STD VAL REPNE l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S        Qf1(qPUSHR,qCX,cVAL); pushTempVAL(T_WRD2);
//	%+S   when P_APX_SFILL:
//	%+S %-E    GetTosPntr(qES,qDI,qAX); Pop;
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop;
//	%+SE       GetTosPntr(qEDI,qEAX); Pop;
//	%+SE       GetTosAdjustedIn86(qECX); Pop;
//	%+S        GetTosAdjustedIn86(qAL); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S        Qf2(qRSTRB,qRSTOS,qCLD,cVAL,qREP);
//	%+SW     outstring("- APX_SFILL RSTRB RSTOS CLD VAL REP l. "); -- trcpje
//	%+SW     outword(curline); outimage; -- trcpje
//	%+S   when P_APX_BOBY: Pop; Pop; pushTempVAL(T_BYT1);
//	%+S   when P_APX_BYBO: Pop; Pop; pushTempVAL(T_BOOL);
//	%+S   when P_APX_SZ2W: Pop; Pop; pushTempVAL(T_WRD2);
//	%+S   when P_APX_W2SZ: Pop; Pop; pushTempVAL(T_SIZE);
//	%+S   when P_APX_RF2N:
//	%+S %-E    Qf2(qPUSHC,0,qAX,cVAL,0);
//	%+SE       Qf2(qPUSHC,0,qEAX,cVAL,0);
//	%+S        Pop; Pop; pushTempVAL(T_GADDR);
//	%+S   when P_APX_N2RF:
//	%+S %-E    GetTosValueIn86R3(qAX,qDX,qCX);
//	%+S %-E    Qf2(qDYADR,qADD,qAX,cADR,qDX);
//	%+S %-E    Qf1(qPUSHR,qCX,cADR); Qf1(qPUSHR,qAX,cADR);
//	%+SE       GetTosValueIn86R3(qEAX,qEDX,0);
//	%+SE       Qf2(qDYADR,qADD,qEAX,cADR,qEDX); Qf1(qPUSHR,qEAX,cADR);
//	%+S        Pop; Pop; pushTempVAL(T_OADDR);
//	%+S   when P_APX_BNOT:
//	%+S %-E    GetTosAdjustedIn86(qAL); Pop; Qf2(qMONADR,qNOT,qAL,cVAL,0);
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop; Qf2(qMONADR,qNOT,qEAX,cVAL,0);
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WNOT:
//	%+S %-E    GetTosAdjustedIn86(qAX); Pop; Qf2(qMONADR,qNOT,qAX,cVAL,0);
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop; Qf2(qMONADR,qNOT,qEAX,cVAL,0);
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_BAND:
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAL); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qAND,qAL,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf2(qDYADR,qAND,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WAND:
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop; GetTosAdjustedIn86(qAX); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qAND,qAX,cVAL,qCX);
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf2(qDYADR,qAND,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_BOR:
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAL); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qOR,qAL,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf2(qDYADR,qOR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WOR:
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop; GetTosAdjustedIn86(qAX); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qOR,qAX,cVAL,qCX);
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf2(qDYADR,qOR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_BXOR:
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAL); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qXOR,qAL,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf2(qDYADR,qXOR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WXOR:
//	%+S %-E    GetTosAdjustedIn86(qCX); Pop; GetTosAdjustedIn86(qAX); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qDYADR,qXOR,qAX,cVAL,qCX);
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf2(qDYADR,qXOR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_BSHL:
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAL); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; MindMask:=wAND(MindMask,wNOT(uCH));
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qSHIFT,qSHL,qAL,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf2(qSHIFT,qSHL,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WSHL:
//	%+S %-E    GQconvert(T_BYT1); --- pje ??? else gettos..(qCL) => POP CX
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAX); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; MindMask:=wAND(MindMask,wNOT(uCH));
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qSHIFT,qSHL,qAX,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf2(qSHIFT,qSHL,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_BSHR:
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAL); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; MindMask:=wAND(MindMask,wNOT(uCH));
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qSHIFT,qSHR,qAL,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAL,cVAL); pushTempVAL(T_BYT1);
//	%+S %+E    Qf2(qSHIFT,qSHR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S   when P_APX_WSHR:
//	%+S %-E    GQconvert(T_BYT1); --- pje ??? else gettos..(qCL) => POP CX
//	%+S %-E    GetTosAdjustedIn86(qCL); Pop; GetTosAdjustedIn86(qAX); Pop;
//	%+S %+E    GetTosAdjustedIn86(qECX); Pop; MindMask:=wAND(MindMask,wNOT(uCH));
//	%+S %+E    GetTosAdjustedIn86(qEAX); Pop;
//	%+SC       if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S        Pop; -- Profile Item
//	%+S %-E    Qf2(qSHIFT,qSHR,qAX,cVAL,qCL);
//	%+S %-E    Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %+E    Qf2(qSHIFT,qSHR,qEAX,cVAL,qECX);
//	%+S %+E    Qf1(qPUSHR,qEAX,cVAL); pushTempVAL(T_WRD4);
//	%+S %-E  when P_DOS_SDMODE:
//	%+S %-E       GetTosValueIn86(qAL); Pop; Qf2(qLOADC,0,qAH,cVAL,0);
//	%+S %-E       DosRegUse(uAX,0,0); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_UPDPOS:
//	%+S %-E       GetTosValueIn86(qDX); Pop;
//	%+S %-E       GetTosValueIn86(qBH); Pop; Qf2(qLOADC,0,qAH,cVAL,2);
//	%+S %-E       DosRegUse(wOR(wOR(uAH,uBH),uDX),0,0); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_CURSOR:
//	%+S %-E       GetTosValueIn86(qBH); Pop; Qf2(qLOADC,0,qAH,cVAL,3);
//	%+S %-E       DosRegUse(wOR(uAH,uBH),uDX,uDX); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf1(qPUSHR,qDX,cVAL); pushTempVAL(T_BYT2);
//	%+S %-E  when P_DOS_SDPAGE:
//	%+S %-E       GetTosValueIn86(qAL); Pop; Qf2(qLOADC,0,qAH,cVAL,5);
//	%+S %-E       DosRegUse(uAX,0,0); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_SROLUP:
//	%+S %-E       GetTosValueIn86(qDX); Pop; GetTosValueIn86(qCX); Pop;
//	%+S %-E       GetTosValueIn86(qBH); Pop; GetTosValueIn86(qAL); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,6);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAX,uBH),uCX),uDX),0,0);
//	%+S %-E       Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_SROLDW:
//	%+S %-E       GetTosValueIn86(qDX); Pop; GetTosValueIn86(qCX); Pop;
//	%+S %-E       GetTosValueIn86(qBH); Pop; GetTosValueIn86(qAL); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,7);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAX,uBH),uCX),uDX),0,0);
//	%+S %-E       Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_GETCEL:
//	%+S %-E       GetTosValueIn86(qBH); Pop; Qf2(qLOADC,0,qAH,cVAL,8);
//	%+S %-E       DosRegUse(wOR(uAH,uBH),uAX,uAX); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf1(qPUSHR,qAX,cVAL); pushTempVAL(T_BYT2);
//	%+S %-E  when P_DOS_PUTCHR:
//	%+S %-E       GetTosValueIn86(qCX); Pop; GetTosValueIn86(qBL); Pop;
//	%+S %-E       GetTosValueIn86(qAL); Pop; GetTosValueIn86(qBH); Pop;
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,9);
//	%+S %-E       DosRegUse(wOR(wOR(uAX,uBX),uCX),0,0); Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_GDMODE:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,15);
//	%+S %-E       DosRegUse(uAH,wOR(uAX,uBX),wOR(uAX,uBX));
//	%+S %-E       Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       Qf1(qPUSHR,qBX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+S %-E       pushTempVAL(spec.type);
//	%+S %-E  when P_DOS_SETPAL:
//	%+S %-E       GetTosPntr(qES,qDX,qAX); Pop; GetTosValueIn86(qBX); Pop;
//	%+S %-E       GetTosValueIn86(qAL); Pop; Qf2(qLOADC,0,qAH,cVAL,16);
//	%+S %-E       DosRegUse(wOR(wOR(wOR(uAX,uBX),uES),uDX),0,0);
//	%+S %-E       Qf2(qINT,0,0,0,16);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E  when P_DOS_RDCHK:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,11); DosRegUse(0,uAL,uAL);
//	%+S %-E       Qf2(qINT,0,0,0,33); Qf1(qPUSHR,qAL,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(T_BOOL);
//	%+S %-E  when P_DOS_KEYIN:
//	%+S %-E       Qf2(qLOADC,0,qAH,cVAL,7); DosRegUse(0,uAL,uAL);
//	%+S %-E       Qf2(qINT,0,0,0,33); Qf1(qPUSHR,qAL,cVAL);
//	%+SC %-E      if TOS <> pitem then IERR("SSTMT.Call") endif;
//	%+S %-E       Pop; -- Profile Item
//	%+S %-E       pushTempVAL(T_CHAR);
//	      otherwise IERR("PARSE.CallInline-1") endcase;
//	end;
//	%title ***  IF/ELSE/ENDIF and SKIPIF/ENDSKIP  Construction  ***
//
//	Routine IfConstruction; import Boolean stm;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin ref(Qpkt) L1,L2,L3,Lx; ref(BSEG) thnseg,elsseg;
//	      range(0:MaxByte) cond;
//	      range(0:MaxType) thntype,elstype,thnNb,elsNb; Boolean NoElse;
//
//	%+D   RST(R_IfConstruction);
//	      IfDepth:=IfDepth+1;
//	      if IfDepth = 6
//	      then WARNING("Deeply nested IF-Expression -- MAY CAUSE TROUBLE") endif;
//	--??  Qf1(qEVAL,0);
//
//	%-E   cond:=GQrelation(true); -- doJUMPrel);
//	%+E   cond:=GQrelation;
//	      L1:=L2:=ForwJMP(NotQcond(cond));
//	%-E   L3:=xFJUMP;
//	%-E   if L3<>none
//	%-E   then if bAND(cond,7)=q_WNE then defFDEST(L3); L3:=none endif endif
//	      --------- Then Brance ---------
//	      elsseg:=curseg; CopyBSEG; thnseg:=curseg;
//	      if stm then programElements;
//	             else repeat InputInstr while Instruction    do endrepeat;
//	      endif;
//	%+C   if TOS <> none then
//	           GQfetch; thntype:=TOS.type;
//	           Pop; pushTempVAL(thntype);
//	           thnNb:=if thntype >= T_REAL then 0
//	           else TTAB(thntype).nbyte;
//	%+C   else IERR("IF-Stack empty at end-of-then-branch") endif;
//
//	      NewCurSeg(elsseg);
//	      if CurInstr=S_ELSE
//	      then L2:=ForwJMP(0); DefFDEST(L1); NoElse:=false;
//	%-E        if L3<>none then defFDEST(L3); L3:=none endif;
//	           --------- Else Brance ---------
//	           if stm then programElements
//	                  else repeat InputInstr while Instruction    do endrepeat;
//	           endif;
//	      else NoElse:=true endif;
//	%+C   if TOS <> none then
//	           GQfetch; elstype:=TOS.type;
//	           Pop; pushTempVAL(elstype);
//	           elsNb:=if elstype >= T_REAL then 0
//	           else TTAB(elstype).nbyte;
//	%+C   else IERR("IF-Stack empty at ENDIF") endif;
//
//	      if thnNb<elsNb
//	      then if NoElse then --- Nothing
//	           else Lx:=ForwJMP(0); DefFDEST(L2); L2:=Lx endif;
//	           NewCurSeg(thnseg); GQconvert(elstype)
//	      elsif thnNb>elsNb
//	      then if NoElse then L2:=ForwJMP(0); DefFDEST(L1) endif;
//	           NewCurSeg(elsseg); GQconvert(thntype);
//	      endif;
//
//	      NewCurSeg(thnseg);
//	%+D   if TraceMode <> 0
//	%+D   then outstring("**** Delete"); DumpStack endif;
//	      repeat while TOS <> none do Pop endrepeat;
//	      DELETE(curseg); curseg:=none; NewCurSeg(elsseg);      
//	%+C   if CurInstr <> S_ENDIF then IERR("Illegal if-construction") endif;
//	      DefFDEST(L2);
//	%-E   if L3<>none then defFDEST(L3) endif;
//	      IfDepth:=IfDepth-1;
//	end;
//
//	Routine SkipifConstruction; import Boolean stm;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin ref(Qpkt) LL,L3; ref(BSEG) OldBSEG; range(0:maxbyte) cond;
//	%+D   RST(R_SkipifConstruction);
//	%-E   cond:=GQrelation(true); -- doJUMPrel);
//	%+E   cond:=GQrelation;
//	      LL:=ForwJMP(cond);
//	%-E   L3:=xFJUMP;
//	%-E   if L3<>none
//	%-E   then if bAND(cond,7)=q_WEQ then defFDEST(L3); L3:=none endif endif
//
//	      OldBSEG:=curseg; CopyBSEG;
//	      if stm then programElements
//	             else repeat InputInstr while Instruction    do endrepeat;
//	      endif;
//	%+C   if CurInstr <> S_ENDSKIP
//	%+C   then IERR("Improper termination of skipif-construction") endif;
//	%+C   CheckStackEmpty;
//	      DELETE(curseg); curseg:=none; NewCurSeg(OldBSEG);
//	---   Qf2(qINT,0,0,0,2); -- Impossible to reach this at runtime
//	      DefFDEST(LL);
//	%-E   if L3<>none then defFDEST(L3) endif;
//	end;
//	%title ***   BSEG/ESEG  Construction   ***
//
//	Routine BSEGInstruction;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin ref(BSEG) OldBSEG,NewBSEG; Boolean DeadFlag, deleteFlag; ref(Segment) seg;
//	%+C   if InsideRoutine then
//	%+C   IERR("BSEG Instruction is illegal within a routine body") endif;
//	      ---------   Create new sub-BSEG   ---------
//	      OldBSEG:=curseg; NewBSEG:=FreeSeg;
//	      if NewBSEG <> none then FreeSeg:=FreeSeg.next
//	      else NewBSEG:=NEWOBJ(K_BSEG,size(BSEG)); nSubSeg:=nSubSeg+1;
//	           if CSEGNAM.val = 0 then EdSymb(sysedit,PROGID)
//	           else EdSymb(sysedit,CSEGNAM) endif;
//	           EdWrd(sysedit,nSubSeg); ed(sysedit,"_TEXT");
//	           NewBSEG.segid:=DefSegm(pickup(sysedit),aCODE);
//	      endif;
//	      ------ Save Current Segment's Attributes ------
//	      seg:=DICREF(CSEGID); seg.qcount:=qcount;
//	      seg.qfirst:=qfirst; seg.qlast:=qlast; seg.MindMask:=MindMask;
//	      ------ Initiate New Segment's Attributes ------
//	      NewCurSeg(NewBSEG); MindMask:=uSPBPM;
//	      qfirst:=none; qlast:=none; qcount:=0;
//
//	      ---------   Process sub-BSEG   ---------
//	      DeadFlag:=DeadCode; DeadCode:=false;
//	      deleteflag:=deleteOK; deleteOK:= MASSLV>61 and (DEBMOD<3);
//	      Qf2(qLINE,0,0,0,curline);
//	      programElements;
//	%+C   CheckStackEmpty;
//	%+C   if CurInstr <> S_ESEG then
//	%+C   IERR("Improper termination of BSEG-construction") endif;
//	      Qf2(qINT,0,0,0,1); -- Impossible to reach this at runtime
//	      peepExhaust(true); DeadCode:=DeadFlag; deleteOK:=deleteflag;
//
//	      ---------   Delete sub-BSEG   ---------
//	      if DICREF(CSEGID) qua Segment.rela.val < SEGLIM
//	      then curseg.next:=FreeSeg; FreeSeg:=curseg endif;
//	      NewCurSeg(OldBSEG);
//	      ------ Reset Current Segment's Attributes ------
//	      seg:=DICREF(CSEGID); qcount:=seg.qcount;
//	      qfirst:=seg.qfirst; qlast:=seg.qlast; MindMask:=seg.MindMask;
//	end;
//
//	%page
//	Routine NewCurSeg; import ref(BSEG) newseg;
//	begin if curseg <> none
//	      then curseg.segid:=CSEGID; curseg.StackDepth87:=StackDepth87;
//	           curseg.SAV:=SAV; curseg.BOS:=BOS; curseg.TOS:=TOS;
//	      endif;
//	      CSEGID:=newseg.segid; StackDepth87:=newseg.StackDepth87;
//	      SAV:=newseg.SAV; BOS:=newseg.BOS; TOS:=newseg.TOS; curseg:=newseg;
//	end;
//
//	Routine CopyBSEG;
//	begin ref(Object) newseg; ref(StackItem) s;
//	%+D   RST(R_CopyBSEG);
//	      curseg.segid:=CSEGID; curseg.StackDepth87:=StackDepth87;
//	      curseg.SAV:=SAV; curseg.BOS:=BOS; curseg.TOS:=TOS;
//	      newseg:=FreeObj(K_BSEG);
//	      if newseg <> none
//	      then FreeObj(K_BSEG):=newseg qua FreeObject.next;
//	      else newseg:=NEWOBX(size(BSEG));
//	%+D        ObjCount(K_BSEG):=ObjCount(K_BSEG)+1;
//	      endif;
//	      newseg.kind:=K_BSEG; newseg qua BSEG.segid:=CSEGID;
//	      newseg qua BSEG.next:=none; newseg qua BSEG.TOS:=none;
//	      newseg qua BSEG.BOS:=none;  newseg qua BSEG.SAV:=none;
//	      newseg qua BSEG.StackDepth87:=StackDepth87;
//	      if SAV=none then s:=BOS else s:=SAV.pred endif;
//	      curseg:=newseg; SAV:=none; BOS:=none; TOS:=none;
//	      repeat while s <> none
//	      do case 0:K_Max (s.kind)
//	         when K_ProfileItem:
//	              Push(NewProfileItem(s.type,s qua ProfileItem.spc));
//	              TOS.repdist:=s.repdist;
//	              TOS qua ProfileItem.nasspar:=s qua ProfileItem.nasspar;
//	         when K_Address:
//	              Push(NewAddress(s.type,s qua Address.Offset,
//	                                     s qua Address.Objadr));
//	              TOS.repdist:=s.repdist;
//	              TOS qua Address.ObjState:=s qua Address.ObjState;
//	              TOS qua Address.AtrState:=s qua Address.AtrState;
//	         when K_Coonst:
//	              pushCoonst(s.type,s qua Coonst.itm);
//	              TOS.repdist:=s.repdist;
//	         when K_Temp,K_Result:
//	              pushTempVAL(s.type);
//	              TOS.kind:=s.kind; TOS.repdist:=s.repdist;
//	%+C      otherwise IERR("Illegal use of Copy(StackItem)");
//	         endcase;
//	         s:=s.pred;
//	      endrepeat;
//	%+D   if TraceMode <> 0
//	%+D   then outstring("**** Copied"); DumpStack endif;
//	end;
//
//	%title  ***   SAVE/RESTORE  Construction   ***
//	Record SavePntMap;
//	begin Range(0:MaxWord) n;            -- Created by Pushlen: pointer map
//	      Range(0:MaxWord) rela(MaxPnt); -- Created by Pushlen: pointer map
//	end;
//
//	Range(0:255) SavLng;      -- Save object length (bytes)
//	Boolean SkipProtect;      -- Created by Pushlen: true: Pushlen=0
//	Infix(SavePntMap) sMap;
//
//	Routine ProtectConstruction; import Boolean stm;
//	begin Boolean skipped; range(0:255) lng,i; infix(MemAddr) opr;
//	      ref(StackItem) old_SAV; infix(MemAddr) SDpnt;
//	      infix(WORD) dum;
//	%+D   RST(R_ProtectConstruction);
//	      skipped:=SkipProtect; lng:=SavLng;
//	      if skipped then Save86(0,noadr)
//	      else -- lng > 0 --
//	           SDpnt:=EmitLiteral(@sMap.n,(sMap.n+1)*2); sMap.n:=0;
//	%+C        CheckTosType(T_OADDR);
//	%-E        GetTosValueIn86R3(qBX,qES,0); Pop;
//	%+E        GetTosValueIn86(qEBX); Pop;
//	           opr.kind:=reladr; opr.rela.val:=4; opr.segmid.val:=0;
//	%-E        opr.sbireg:=bOR(oES,rmBX);
//	%+E        opr.sibreg:=bEBX+NoIREG;
//	           Save86(lng,opr); opr.rela.val:=0;
//	%-E        PresaveOprRegs(opr);
//	%-E        Qf4b(qMOVMC,0,qw_W,cOBJ,F_OFFSET,opr,SDpnt);
//	%-E        opr.rela.val:=2; Qf4b(qMOVMC,0,qw_W,cOBJ,F_BASE,opr,SDpnt);
//	%+E        Qf4b(qMOVMC,0,qw_D,cOBJ,0,opr,SDpnt);
//	%-E %+C    if CHKSTK then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	      endif;
//	      old_SAV:=SAV; SAV:=TOS;
//	      if stm then programElements
//	             else repeat inputInstr while Instruction do endrepeat;
//	      endif
//	%+C   if CurInstr <> S_RESTORE
//	%+C   then IERR("Improper termination of protect-construction") endif;
//	%+C   CheckTosRef; CheckTosType(T_OADDR);
//	      SAV:=old_SAV;
//	      if skipped
//	      then -- Remove Effect of: PRECALL restore / CALL restore / PUSH rstr
//	           GQpop;
//	           if qlast.fnc <> qCALL then IERR("PARSE.SAVE-1")
//	           else DeleteLastQ endif;
//	%-E %+C    if CHKSTK then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	           InputInstr;
//	%+D        if CurInstr<>S_PRECALL then IERR("PARSE.SAVE-2") endif;
//	           InTag(%dum%);  -- Skip: PRECALL PRESTO
//	           InputInstr;
//	%+D        if CurInstr<>S_CALL   then IERR("PARSE.SAVE-3") endif;
//	           InTag(%dum%);  -- Skip: CALL    PRESTO
//	           Rstr86(0,noadr);
//	      else -- lng>0 --
//	%-E        GQfetch; GetTosValueIn86R3(qBX,qES,0); Pop;
//	%+E        GQfetch; GetTosValueIn86(qEBX); Pop;
//	%-E %+C    if CHKSTK then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	           opr.rela.val:=4; Rstr86(lng,opr);
//	%-E        MindMask:=wAND(wNOT(wOR(uES,uBX)),MindMask);
//	%+E        MindMask:=wAND(wNOT(uEBX),MindMask);
//	      endif;
//	end;
//	%page
//
//	 Routine Pushlen; export range(0:MaxWord) savelng;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	 begin range(0:MaxWord) npt,nbt; ref(StackItem) s,sx;
//	       ref(ProfileDescr) spc; ref(LocDescr) par; ref(Temp) tmp;
//	       infix(DataType) tp; infix(string) pm; range(0:MaxByte) rela;
//	       infix(WORD) dum;
//
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** PUSHLEN "); DumpStack endif;
//
//	%+C    if sMap.n <> 0 then
//	%+C    IERR("PUSHLEN - SAVE - RESTORE -- sequence error") endif;
//	       sMap.n:=0; savelng:=0; SavLng:=0;
//
//	       if SAV=none then sx:=BOS else sx:=SAV.pred endif;
//
//	       s:=TOS; if s = none then goto L endif;
//	       repeat case 0:K_Max (s.kind)
//	         when K_ProfileItem: -- (params are stacked)
//	         when K_Address:
//	%+D           if TraceMode <> 0
//	%+D           then nbt:=0;
//	%+D                if s qua Address.AtrState<>NotStacked
//	%+D                then nbt:=nbt+AllignFac endif;
//	%+D                if s qua Address.ObjState<>NotStacked
//	%+D                then nbt:=nbt+4 endif;
//	%+D                outstring("*** Prep "); edit(sysout,s);
//	%+D                outstring("  nbt="); outword(nbt);
//	%+D                printout(sysout);
//	%+D           endif;
//	              if s qua Address.AtrState <> NotStacked
//	              then SavLng:=SavLng+AllignFac endif;
//	              if s qua Address.ObjState <> NotStacked
//	              then
//	                   if sMap.n > (MaxPnt-2)
//	                   then ERROR("Too many pointers in SAVE Object");
//	                        sMap.n:=0;
//	                   endif;
//	                   sMap.rela(sMap.n):=SavLng+4; sMap.n:=sMap.n+1;
//	                   SavLng:=SavLng+4;
//	              endif;
//	         when K_Temp,K_Coonst:
//	              tp:=TTAB(s.type);
//	              nbt:=tp.nbyte;
//	              if tp.pntmap.val=0 then pm:=nostring
//	              else pm:=DICSMB(tp.pntmap) endif;
//	              npt:=pm.nchr;
//	%+D           if TraceMode <> 0
//	%+D           then outstring("*** Prep "); edit(sysout,s);
//	%+D                outstring("  nbt="); outword(nbt);
//	%+D                outstring("  npt="); outword(npt);
//	%+D                printout(sysout);
//	%+D           endif;
//	              if nbt <> 0
//	              then repeat while npt <> 0
//	                   do npt:=npt-1;
//	                      rela:=var(pm.chradr)(npt) qua integer;
//	                      if sMap.n > (MaxPnt-2)
//	                      then ERROR("Too many pointers in SAVE Object");
//	                           sMap.n:=0;
//	                      endif;
//	                      sMap.rela(sMap.n):=rela+SavLng+4;
//	                      sMap.n:=sMap.n+1;
//	                   endrepeat;
//	                   SavLng:=SavLng+wAllign(%nbt%);
//	              endif;
//	         when K_Result: -- Do not save result area
//	%+C      otherwise IERR("PARSE.Pushlen");
//	         endcase;
//	       while s <> sx do s:=s.suc endrepeat;
//	       if SavLng <> 0
//	       then SkipProtect:=false; savelng:=4+SavLng;
//	       else L: SkipProtect:=true; savelng:=0; sMap.n:=0;
//	            InputInstr;
//	%+D         if CurInstr<>S_ASSCALL then IERR("PARSE.SkipSAVE-1") endif;
//	            InTag(%dum%);  -- Skip: ASSCALL PRE_SAV
//	            InputInstr;
//	%+D         if CurInstr<>S_CALL   then IERR("PARSE.SkipSAVE-2") endif;
//	            InTag(%dum%);  -- Skip: CALL    PRE_SAV
//	            InputInstr;
//	%+D         if CurInstr<>S_SAVE   then IERR("PARSE.SkipSAVE-3") endif;
//	            ProtectConstruction(true); SkipProtect:=true;
//	       endif;
//
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** PUSHLEN  savelng="); outword(savelng);
//	%+D         outstring("  sMap.n="); outword(sMap.n);
//	%+D         printout(sysout);
//	%+D    endif;
//	 end;
//	%page
//
//	 Routine Save86; import range(0:MaxWord) ChkLng; infix(MemAddr) opr;
//	 begin range(0:MaxWord) nbt,lng; ref(StackItem) s,sx;
//	       ref(ProfileDescr) spc; ref(LocDescr) par; ref(Temp) tmp;
//
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** SAVE86 "); DumpStack endif;
//	%+C    Lng:=opr.rela.val;
//	       if SAV=none then sx:=BOS else sx:=SAV.pred endif;
//	       s:=TOS; if s = none then goto L endif;
//	       repeat case 0:K_Max (s.kind)
//	              when K_ProfileItem: -- (params are stacked)
//	                   --- Pop off Possible Export ---
//	                   if s.suc <> none
//	                   then if s.suc.kind = K_Result
//	                        then nbt:=TTAB(s.suc.type).nbyte;
//	%+D                          if TraceMode <> 0
//	%+D                          then outstring("*** Save "); edit(sysout,s.suc);
//	%+D                               outstring("  nbt="); outword(nbt);
//	%+D                               printout(sysout);
//	%+D                          endif;
//	%-E                          Qf2(qDYADC,qADD,qSP,cSTP,wAllign(%nbt%))
//	%+E                          Qf2(qDYADC,qADD,qESP,cSTP,wAllign(%nbt%))
//	                        endif;
//	                   endif;
//	              when K_Address:
//	%+D                if TraceMode <> 0
//	%+D                then nbt:=0;
//	%+D                     if s qua Address.AtrState <> NotStacked
//	%+D                     then nbt:=nbt+AllignFac endif;
//	%+D                     if s qua Address.ObjState <> NotStacked
//	%+D                     then nbt:=nbt+4 endif;
//	%+D                     outstring("*** Save "); edit(sysout,s);
//	%+D                     outstring("  nbt="); outword(nbt);
//	%+D                     printout(sysout);
//	%+D                endif;
//	                   if s qua Address.AtrState <> NotStacked
//	                   then PresaveOprRegs(opr);
//	                        Qf4(qPOPM,0,qAL,cVAL,AllignFac,opr);
//	                        opr.rela.val:=opr.rela.val+AllignFac;
//	                   endif;
//	                   if s qua Address.ObjState <> NotStacked
//	                   then PresaveOprRegs(opr);
//	                        Qf4(qPOPM,0,qAL,cANY,AllignFac,opr);
//	                        opr.rela.val:=opr.rela.val+AllignFac;
//	%-E                     PresaveOprRegs(opr); Qf4(qPOPM,0,qAL,cANY,2,opr);
//	%-E                     opr.rela.val:=opr.rela.val+2;
//	                   endif;
//	              when K_Temp,K_Coonst:
//	                   nbt:=TTAB(s.type).nbyte;
//	%+D                if TraceMode <> 0
//	%+D                then outstring("*** Save "); edit(sysout,s);
//	%+D                     outstring("  nbt="); outword(nbt);
//	%+D                     printout(sysout);
//	%+D                endif;
//	                   if nbt <> 0
//	                   then nbt:=wAllign(%nbt%)
//	                        PresaveOprRegs(opr); Qf4(qPOPM,0,qAL,cANY,nbt,opr);
//	                        opr.rela.val:=opr.rela.val+nbt;
//	                   endif;
//	              when K_Result: -- Treated with the Profile
//	%+C           otherwise IERR("PARSE.SAVE86-1");
//	              endcase;
//	       while s <> sx do s:=s.suc endrepeat;
//	    L:
//	%+C    Lng:=opr.rela.val-Lng;
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** SAVE86  Lng="); outword(Lng);
//	%+D         outstring(",  ChkLng="); outword(ChkLng); printout(sysout);
//	%+D    endif;
//	%+C    if Lng <> ChkLng then IERR("PARSE.SAVE86-2") endif;
//	 end;
//	%page
//
//	 Routine Rstr86; import range(0:MaxWord) ChkLng; infix(MemAddr) opr;
//	 begin range(0:MaxWord) nbt,lng; ref(StackItem) s,sx;
//	       ref(ProfileDescr) spc; ref(LocDescr) par; ref(Temp) tmp;
//
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** RSTR86 "); DumpStack endif;
//	       opr.rela.val:=opr.rela.val+ChkLng;
//	%+C    Lng:=opr.rela.val;
//	       if SAV=none then sx:=BOS else sx:=SAV.pred endif;
//	       repeat while sx <> none
//	       do s:=sx;
//	          case 0:K_Max (s.kind)
//	          when K_ProfileItem: -- (params are stacked)
//	               --- Make room for Possible Export ---
//	               if s.suc <> none
//	               then if s.suc.kind = K_Result
//	                    then nbt:=TTAB(s.suc.type).nbyte;
//	%+D                      if TraceMode <> 0
//	%+D                      then outstring("*** Rstr "); edit(sysout,s.suc);
//	%+D                           outstring("  nbt="); outword(nbt);
//	%+D                           printout(sysout);
//	%+D                      endif;
//	%-E                      Qf2(qDYADC,qSUB,qSP,cSTP,wAllign(%nbt%))
//	%+E                      Qf2(qDYADC,qSUB,qESP,cSTP,wAllign(%nbt%))
//	                    endif;
//	               endif;
//	          when K_Address:
//	%+D            if TraceMode <> 0
//	%+D            then nbt:=0;
//	%+D                if s qua Address.AtrState<>NotStacked
//	%+D                then nbt:=nbt+AllignFac endif;
//	%+D                if s qua Address.ObjState<>NotStacked
//	%+D                then nbt:=nbt+4 endif;
//	%+D                outstring("*** Rstr "); edit(sysout,s);
//	%+D                outstring("  nbt="); outword(nbt);
//	%+D                printout(sysout);
//	%+D            endif;
//	               if s qua Address.ObjState <> NotStacked
//	               then opr.rela.val:=opr.rela.val-AllignFac;
//	                    PresaveOprRegs(opr); Qf4(qPUSHM,0,0,cANY,AllignFac,opr);
//	%-E                 opr.rela.val:=opr.rela.val-2;
//	%-E                 PresaveOprRegs(opr); Qf4(qPUSHM,0,0,cANY,2,opr);
//	               endif;
//	               if s qua Address.AtrState <> NotStacked
//	               then opr.rela.val:=opr.rela.val-AllignFac;
//	                    PresaveOprRegs(opr); Qf4(qPUSHM,0,0,cANY,AllignFac,opr);
//	               endif;
//	          when K_Temp,K_Coonst:
//	               nbt:=TTAB(s.type).nbyte;
//	%+D            if TraceMode <> 0
//	%+D            then outstring("*** Rstr "); edit(sysout,s);
//	%+D                 outstring("  nbt="); outword(nbt);
//	%+D                 printout(sysout);
//	%+D            endif;
//	               if nbt <> 0
//	               then nbt:=wAllign(%nbt%)
//	                    opr.rela.val:=opr.rela.val-nbt;
//	                    PresaveOprRegs(opr); Qf4(qPUSHM,0,0,cANY,nbt,opr);
//	               endif;
//	          when K_Result: -- Treated with the Profile
//	%+C       otherwise IERR("PARSE.RSTR86-1");
//	          endcase;
//	          sx:=sx.pred;
//	       endrepeat;
//
//	%+C    Lng:=Lng-opr.rela.val;
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** RSTR86  Lng="); outword(Lng);
//	%+D         outstring(",  ChkLng="); outword(ChkLng); printout(sysout);
//	%+D    endif;
//	%+C    if Lng <> ChkLng then IERR("PARSE.RSTR86-2") endif;
//	 end;
//	%page
//
//	 Routine ClearSTK;
//	 begin range(0:MaxWord) nbt; ref(StackItem) s,sx;
//
//	%+D    if TraceMode <> 0
//	%+D    then outstring("*** ClearSTK "); DumpStack endif;
//	       if SAV=none then sx:=BOS else sx:=SAV.pred endif;
//	       s:=TOS;
//	       repeat case 0:K_Max (s.kind)
//	              when K_ProfileItem: -- Nothing to save (params stacked)
//	              when K_Address:
//	                   if s qua Address.AtrState <> NotStacked
//	                   then qPOPKill(AllignFac) endif;
//	                   if s qua Address.ObjState <> NotStacked
//	                   then qPOPKill(AllignFac);
//	%-E                     qPOPKill(2);
//	                   endif;
//	              when K_Temp,K_Coonst,K_Result:
//	                   nbt:=TTAB(s.type).nbyte;
//	                   if nbt <> 0
//	                   then if s.kind=K_Result
//	%-E                     then Qf2(qDYADC,qADD,qSP,cSTP,wAllign(%nbt%))
//	%+E                     then Qf2(qDYADC,qADD,qESP,cSTP,wAllign(%nbt%))
//	                        else qPOPKill(nbt) endif;
//	                   endif;
//	              otherwise IERR("PARSE.ClearSTK") endcase;
//	       while s <> sx do s:=s.suc endrepeat;
//	 end;
//	%title ***  E r r o r   M e s s a g e s  ***
//
//	%+D  const infix(string) INTMSG(17) = (
//	%+D  "Unspecified error condition",                --   0
//	%+D  "Invalid floating point operation",           --   1
//	%+D  "Floating point division by zero",            --   2
//	%+D  "Floating point overflow",                    --   3
//	%+D  "Floating point underflow",                   --   4
//	%+D  "Inexact result (floating point operation)",  --   5
//	%+D  "Integer overflow",                           --   6
//	%+D  "Integer division by zero",                   --   7
//	%+D  "Illegal address trap",                       --   8
//	%+D  "Illegal Instruction trap",                   --   9
//	%+D  "Breakpoint trap",                            --   10
//	%+D  "User interrupt - NOT YET IMPLEMENTED",       --   11
//	%+D  "Cpu time limit overflow",                    --   12
//	%+D  "Continuation impossible",                    --   13
//	%+D  "Code 14 trap",                               --   14
//	%+D  "Code 15 trap",                               --   15
//	%+D  "Void pointer"                                --   16
//	%+D  );
//
//
//	%+D  const infix(string) STATMSG(29) = (
//	%+D  "*** not used ***",                           --   0
//	%+D  "Invalide filekey",                           --   1
//	%+D  "File not defined",                           --   2
//	%+D  "File does not exist",                        --   3
//	%+D  "File already exist",                         --   4
//	%+D  "File not open",                              --   5
//	%+D  "File already open",                          --   6
//	%+D  "File already closed",                        --   7
//	%+D  "Illegal use of file",                        --   8
//	%+D  "Illegal record format for directfile",       --   9
//	%+D  "Illegal filename",                           --  10
//	%+D  "Output image too long",                      --  11
//	%+D  "Input image too long",                       --  12
//	%+D  "End of file on input",                       --  13
//	%+D  "Not enough space available",                 --  14
//	%+D  "File full on output",                        --  15
//	%+D  "Location out of range",                      --  16
//	%+D  "I/O error, e.g. hardware fault",             --  17
//	%+D  "Specified action cannot be performed",       --  18
//	%+D  "Impossible",                                 --  19
//	%+D  "No write access to this file",               --  20
//	%+D  "Non-numeric item as first character",        --  21
//	%+D  "Value out of range",                         --  22
//	%+D  "Incomplete syntax",                          --  23
//	%+D  "Text string too short",                      --  24
//	%+D  "Fraction part less then zero",               --  25
//	%+D  "Illegal specification of interval",          --  26
//	%+D  "Argument out of range for system routine",   --  27
//	%+D  "Key previously defined"                      --  28
//	%+D  );
//
//	%title ***   I N I T I A T E   ***
//
//	 Routine NewFile; import range(0:132) nchr; export ref(File) val;
//	 begin val:=NEWOBZ(size(File:nchr)); val.nchr:=nchr end;
//
	public static void INITIATE() {
		System.out.println("Sbase.INITIATE: ");
//	 Visible routine INITIATE;
//	 begin infix(string) action; size poolsize; short integer i;
//	       errormode:=true; npool:=1;
//	       EnvInit(entry(Exhandl));
//	       if status <> 0 then EnvTerm(3,"Error return from init") endif;
//	       erroutine:=entry(Erhandl);
//	%+D    InitTime:=EnvGetCpuTime qua real;
//	%+D    status:=0; -- if status > 0 then IERR("INITIATE-1") endif;
//	       poolsize:=EnvGetSizeInfo(1,npool);
//	       if (status <> 0) or (poolsize=nosize) then IERR("INITIATE-2") endif;
//	       PoolTop:=EnvArea(poolsize,npool);
//	       if (status <> 0) or (PoolTop=none) then IERR("INITIATE-3") endif;
//	%+D    PoolTab(0).PoolTop:=PoolTop;
//	%+D    PoolTab(0).PoolSize:=PoolSize;
//
//	       PoolNxt:=PoolTop; PoolBot:=PoolTop+poolsize;
//	       zeroarea(PoolTop,PoolBot);
//
//	       ---  Create editing objects ---
//	       sysedit:=NewFile(80); errmsg:=NewFile(80);
//	%+D    inptrace:=NewFile(132); ouptrace:=NewFile(132);
//
//	       ---  Create and open sysout (printfile=3) ---
//	       sysout:=NewFile(132); edsysfile(sysedit,2);
//	       action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!8!NOBUFFER!0!";  -- outfile
//	       sysout.key:=EnvOpen(pickup(sysedit),3,action,0);
//	       if status <> 0 then IERR("INITIATE-5") endif;
//
//	       ---  Create and open sysin (infile=1) ---
//	       sysin:=NewFile(80); edsysfile(sysedit,1);
//	       action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!";  -- infile
//	       sysin.key:=EnvOpen(pickup(sysedit),1,nostring,0);
//	       if status <> 0 then IERR("INITIATE-6") endif;
//
//	       errormode:=false;
//	%-E    i:=256; repeat while i<>0 do i:=i-1; FWRTAB(i):=none endrepeat
//	 end;
	}
	
	
//	%title ***   P a r a m e t e r    I n p u t    ***
//
	public static void PARAM() {
		System.out.println("Parse.PARAM: ");
//	Visible routine PARAM;
//	begin infix(string) idn; range(0:MaxWord) n;
//	%+S   envpar:=EnvGetIntInfo(1) = 0;  -- ????????   Returns Status <> 0 !!!!!!!!!
//	%+S   if status <> 0 then status:=0; envpar:=true endif;
//
//	%+D   TLIST:=0;     --  D - Major Event Trace of S-Compiler
//	%+D   BECDEB:=0;    --  Debuging level (0: skip all debug info)
//	%+S   SYSGEN:=0;    --  System generation
//	                    --      1: Generation of Runtime System
//	                    --      2: Generation of S-Compiler
//	                    --      3: Generation of Environment
//	                    --      4: Generation of Library module
//	%+S   SEGLIM:=20000 --  Max seg-size befor segment-split
//	%+S   QBFLIM:=0;    --  No.of Q-instr in buff before Exhaust Half
//	%+S   RNGCHK:=0;    --  >0: produce integer --> char range check
//	%+S   IDXCHK:=1;    --  >0: produce array index check
//	%+S   LINTAB:=1;    --  >0: generate line info to LIN_CODE Segment
//	%+S   DEBMOD:=2;    --  >2: generate line break instructions
//	%+S   MXXERR:=200;  --  Max number of errors
//	%+D   SK1LIN:=0;    --  S-Compiler-Trace - Pass 1 starting line
//	%+D   SK1TRC:=0;    --  Pass 1 Trace level=SEOMTI (one digit each)
//	%+D   SK2LIN:=0;    --  S-Compiler-Trace - Pass 2 starting line
//	%+D   SK2TRC:=0;    --  Pass 2 Trace level=SEOMTI (one digit each)
//	                    --      I = 0..9 Input trace level
//	                    --      T = 0..9 Trace-mode level
//	                    --      M = 0..9 Module input/output trace level
//	                    --      O = 0..9 Output trace level  listsw
//	                    --      E = 0..9 Output trace level  listq1
//	                    --      S = 0..9 Output trace level  listq2
//	%+S   MASSLV:=255;  --  Massage level
//	      MASSDP:=25;   --  Massage recursion depth
//	%-E %+S BNKLNK:=0;    --  >0: Prepare Produced code for BANKING
//	      CombAtr:=0;
//
//	      PRFXID.val:=0;  PROGID.val:=0;  SCODID.val:=0;
//	      CSEGNAM.val:=0; DSEGNAM.val:=0;
//	%+A   ASMID.val:=0;
//	      ATTRID.val:=0;  RELID.val:=0;   SCRID.val:=0;   sMap.n:=0;
//
//	      MainEntry.kind:=0;  -- Main program's entry-point
//	      DsegEntry.val :=0;  -- Data Segment start address
//	      nerr:=0;            -- Number of error messages until now
//	      curline:=0;         -- Current source line number
//	      LabelSequ:=0;       -- No.of assembly labels created by 'NewLabno'
//	      SymbSequ:=0;        -- No.of symbols created by 'NewPubl'
//	%+S   CPUID:=iAPX86;  NUMID:=iAPX87; OSID:=0; TSTOFL:=false;
//	-- %-E   doJUMPrel:=false;
//	%+D   DefSinstr;
//
//	%+S   if envpar
//	%+S   then --- Parameters are taken from environment
//	           MASSLV:=EnvGetIntInfo(6);
//	           if MASSLV > 63 then MASSDP:=MASSLV-38 endif
//	-- %-E        doJUMPrel:= (MASSLV>=63);
//	     
//	%+D        TLIST:=EnvGetIntInfo(3)
//	%-E        BNKLNK:=EnvGetIntInfo(7)
//	%-E        if Status <> 0 then Status:=0; BNKLNK:=0 endif;   --- TEMP ?????
//	%+S        SYSGEN:=EnvGetIntInfo(8)
//
//	           QBFLIM:=EnvGetIntInfo(9)     ---  I.e.  STKLNG   Temp !??
//	           if QBFLIM=0 then QBFLIM:=32000 endif -- imposs. large number
//
//	           MXXERR:=EnvGetIntInfo(10)
//	%+D        SK1LIN:=EnvGetIntInfo(11)    SK1TRC:=EnvGetIntInfo(12)
//	%+D        SK2LIN:=EnvGetIntInfo(13)    SK2TRC:=EnvGetIntInfo(14)
//	%+D        BECDEB:=EnvGetIntInfo(22)
//	           LINTAB:=EnvGetIntInfo(23)    RNGCHK:=EnvGetIntInfo(24)
//	           DEBMOD:=EnvGetIntInfo(30)    IDXCHK:=EnvGetIntInfo(25)
//	           TSTOFL:=EnvGetIntInfo(26)<>0
//	           CPUID:=EnvGetIntInfo(28)
//	           NUMID:=EnvGetIntInfo(29)
//	           OSID:=EnvGetIntInfo(31)
//	           CBIND:=EnvGetIntInfo(27)
//	           if Status <> 0 then CBIND:=0; Status:=0 endif;
//	           SEGLIM:=EnvGetIntInfo(2)
//	%-E        CHKSTK:=EnvGetIntInfo(32)<>0;
//	-- ?????   edtextinfo(sysedit,14); idn:=pickup(sysedit);
//	-- ?????   if idn.nchr > 0 then PROGID:=DefSymb(idn) endif;
//	           edtextinfo(sysedit,5); idn:=pickup(sysedit);
//	           if idn.nchr <> 0 then RELID:=DefSymb(idn) endif;
//	%          edtextinfo(sysedit,8); idn:=pickup(sysedit);
//	%          if idn.nchr <> 0 then PRFXID:=DefSymb(idn) endif;
//	           edtextinfo(sysedit,8); edchar(sysedit,'@');
//	           idn:=pickup(sysedit);
//	           if idn.nchr > 1 then PRFXID:=DefSymb(idn) endif;
//	           edtextinfo(sysedit,9); idn:=pickup(sysedit);
//	           if idn.nchr <> 0 then CSEGNAM:=DefSymb(idn) endif;
//	           edtextinfo(sysedit,10); idn:=pickup(sysedit);
//	           if idn.nchr <> 0 then DSEGNAM:=DefSymb(idn) endif;
//	           status:=0;
//
//	%+D        if TLIST <> 0
//	%+D        then outstring("S-Compiler iAPX/109 ");
//	%+D             eddate(sysout); outimage;
//	%+D        endif;
//
//	%+A        edtextinfo(sysedit,6);
//	%+A        if sysedit.pos <> 0
//	%+A        then idn:=pickup(sysedit);
//	%+A             n:=idn.nchr; repeat while n<>0
//	%+A             do n:=n-1;
//	%+A                if var(idn.chradr)(n)='.' then idn.nchr:=n; n:=0 endif
//	%+A             endrepeat;
//	%+A             ed(sysedit,idn); ed(sysedit,".asm"); idn:=pickup(sysedit);
//	%+A             ASMID:=DefSymb(idn);
//	%+AD            if TLIST <> 0
//	%+AD            then outstring("ASSEMBLY SOURCE OUTPUT: ");
//	%+AD                 outstring(idn); outimage;
//	%+AD            endif;
//	%+A        endif;
//
//	%+S   else --- Parameters are taken from sysin
//	%+SD       outstring("S-Compiler iAPX/109 "); eddate(sysout); outimage;
//	%+S        outstring("Enter Parameters:"); outimage; inimage(sysin);
//	%+S        repeat idn:=InItem(sysin); while not STEQ(idn,"END")
//	%+S        do    if STEQ(idn,"ENVPAR:") then envpar:=ParValue(sysin) = 0
//	%+SD          elsif STEQ(idn,"TLIST:")  then TLIST:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"SYSGEN:") then SYSGEN:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"SEGLIM:") then SEGLIM:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"QBFLIM:") then QBFLIM:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"CPUID:")  then CPUID:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"NUMID:")  then NUMID:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"OSID:")   then OSID:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"TSTOFL:") then TSTOFL:=ParValue(sysin)>0
//	%+S           elsif STEQ(idn,"LINTAB:") then LINTAB:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"DEBMOD:") then DEBMOD:=ParValue(sysin)
//	%-E %+S       elsif STEQ(idn,"CHKSTK:") then CHKSTK:=ParValue(sysin)>0
//	%+S           elsif STEQ(idn,"MAXERR:") then MXXERR:=ParValue(sysin)
//	%+SD          elsif STEQ(idn,"SK1LIN:") then SK1LIN:=ParValue(sysin)
//	%+SD          elsif STEQ(idn,"SK1TRC:") then SK1TRC:=ParValue(sysin)
//	%+SD          elsif STEQ(idn,"SK2LIN:") then SK2LIN:=ParValue(sysin)
//	%+SD          elsif STEQ(idn,"SK2TRC:") then SK2TRC:=ParValue(sysin)
//	%+SD          elsif STEQ(idn,"BECDEB:") then BECDEB:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"RNGCHK:") then RNGCHK:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"IDXCHK:") then IDXCHK:=ParValue(sysin)
//	%+S           elsif STEQ(idn,"MASSLV:") then MASSLV:=ParValue(sysin)
//
//	%+S           elsif STEQ(idn,"PRFXID:") then PRFXID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"PROGID:") then PROGID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"CSEG:")   then CSEGNAM:=ParText(sysin)
//	%+S           elsif STEQ(idn,"DSEG:")   then DSEGNAM:=ParText(sysin)
//	%+S           elsif STEQ(idn,"SCODE:")  then SCODID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"ATTR:")   then ATTRID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"OUTPUT:") then RELID:=ParText(sysin)
//	%+SA          elsif STEQ(idn,"ASMOUT:") then ASMID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"SCR:")    then SCRID:=ParText(sysin)
//	%+S           elsif STEQ(idn,"END")    then goto E1;
//	%+S           else ed(errmsg,idn); ERROR("Illegal Parameter: ") endif;
//	%+S        endrepeat;
//	%+S   endif;
//	%+S E1:
//	end;
	}
//
//	%+S Routine InItem; import ref(File) F; export infix(string) itm;
//	%+S begin character c; range(0:MaxWord) p;
//	%+S       repeat while F.chr(F.pos)=' '
//	%+S       do F.pos:=F.pos+1;
//	%+S          if F.pos>=F.nchr then inimage(F) endif;
//	%+S       endrepeat;
//	%+S       p:=F.pos;
//	%+S       repeat c:=F.chr(F.pos) while c <> ' '
//	%+S       do F.pos:=F.pos+1 endrepeat;
//	%+S       itm.chradr:=@F.chr(p); itm.nchr:=F.pos-p;
//	%+SD      if TraceMode <> 0
//	%+SD      then outstring("INITEM: "); outstring(itm); outimage endif;
//
//	%+S end;
//
//	%+S Routine ParValue; import ref(File) F; export integer val;
//	%+S begin infix(string) s; s:=InItem(F); val:=EnvGetInt(s);
//	%+S       if status <> 0
//	%+S       then ed(errmsg,s); ERROR("Non integer item: ");
//	%+S            status:=0; val:=0;
//	%+S       endif;
//	%+S end;
//
//	%+S Routine ParText; import ref(File) F; export infix(WORD) val;
//	%+S begin infix(string) s; s:=InItem(F); val:=DefSymb(s) end;
//
//
//	%+D Routine DefSinstr;
//	%+D begin infix(WORD) sx;
//	%+D       DefSymb("RECORD");           ---    1.
//	%+D       DefSymb("LSHIFTL");          ---    2. Extension to S-Code
//	%+D       DefSymb("PREFIX");           ---    3.
//	%+D       DefSymb("ATTR");             ---    4.
//	%+D       DefSymb("LSHIFTA");          ---    5. Extension to S-Code
//	%+D       DefSymb("REP");              ---    6.
//	%+D       DefSymb("ALT");              ---    7.
//	%+D       DefSymb("FIXREP");           ---    8.
//	%+D       DefSymb("ENDRECORD");        ---    9.
//	%+D       DefSymb("C-RECORD");         ---   10.
//	%+D       DefSymb("TEXT");             ---   11.
//	%+D       DefSymb("C-CHAR");           ---   12.
//	%+D       DefSymb("C-INT");            ---   13.
//	%+D       DefSymb("C-SIZE");           ---   14.
//	%+D       DefSymb("C-REAL");           ---   15.
//	%+D       DefSymb("C-LREAL");          ---   16.
//	%+D       DefSymb("C-AADDR");          ---   17.
//	%+D       DefSymb("C-OADDR");          ---   18.
//	%+D       DefSymb("C-GADDR");          ---   19.
//	%+D       DefSymb("C-PADDR");          ---   20.
//	%+D       DefSymb("C-DOT");            ---   21.
//	%+D       DefSymb("C-RADDR");          ---   22.
//	%+D       DefSymb("NOBODY");           ---   23.
//	%+D       DefSymb("ANONE");            ---   24.
//	%+D       DefSymb("ONONE");            ---   25.
//	%+D       DefSymb("GNONE");            ---   26.
//	%+D       DefSymb("NOWHERE");          ---   27.
//	%+D       DefSymb("TRUE");             ---   28.
//	%+D       DefSymb("FALSE");            ---   29.
//	%+D       DefSymb("PROFILE");          ---   30.
//	%+D       DefSymb("KNOWN");            ---   31.
//	%+D       DefSymb("SYSTEM");           ---   32.
//	%+D       DefSymb("EXTERNAL");         ---   33.
//	%+D       DefSymb("IMPORT");           ---   34.
//	%+D       DefSymb("EXPORT");           ---   35.
//	%+D       DefSymb("EXIT");             ---   36.
//	%+D       DefSymb("ENDPROFILE");       ---   37.
//	%+D       DefSymb("ROUTINESPEC");      ---   38.
//	%+D       DefSymb("ROUTINE");          ---   39.
//	%+D       DefSymb("LOCAL");            ---   40.
//	%+D       DefSymb("ENDROUTINE");       ---   41.
//	%+D       DefSymb("MODULE");           ---   42.
//	%+D       DefSymb("EXISTING");         ---   43.
//	%+D       DefSymb("TAG");              ---   44.
//	%+D       DefSymb("BODY");             ---   45.
//	%+D       DefSymb("ENDMODULE");        ---   46.
//	%+D       DefSymb("LABELSPEC");        ---   47.
//	%+D       DefSymb("LABEL");            ---   48.
//	%+D       DefSymb("RANGE");            ---   49.
//	%+D       DefSymb("GLOBAL");           ---   50.
//	%+D       DefSymb("INIT");             ---   51.
//	%+D       DefSymb("CONSTSPEC");        ---   52.
//	%+D       DefSymb("CONST");            ---   53.
//	%+D       DefSymb("DELETE");           ---   54.
//	%+D       DefSymb("FDEST");            ---   55.
//	%+D       DefSymb("BDEST");            ---   56.
//	%+D       DefSymb("SAVE");             ---   57.
//	%+D       DefSymb("RESTORE");          ---   58.
//	%+D       DefSymb("BSEG");             ---   59.
//	%+D       DefSymb("ESEG");             ---   60.
//	%+D       DefSymb("SKIPIF");           ---   61.
//	%+D       DefSymb("ENDSKIP");          ---   62.
//	%+D       DefSymb("IF");               ---   63.
//	%+D       DefSymb("ELSE");             ---   64.
//	%+D       DefSymb("ENDIF");            ---   65.
//	%+D       DefSymb("RSHIFTL");          ---   66. Extension to S-Code
//	%+D       DefSymb("PRECALL");          ---   67.
//	%+D       DefSymb("ASSPAR");           ---   68.
//	%+D       DefSymb("ASSREP");           ---   69.
//	%+D       DefSymb("CALL");             ---   70.
//	%+D       DefSymb("FETCH");            ---   71.
//	%+D       DefSymb("REFER");            ---   72.
//	%+D       DefSymb("DEREF");            ---   73.
//	%+D       DefSymb("SELECT");           ---   74.
//	%+D       DefSymb("REMOTE");           ---   75.
//	%+D       DefSymb("LOCATE");           ---   76.
//	%+D       DefSymb("INDEX");            ---   77.
//	%+D       DefSymb("INCO");             ---   78.
//	%+D       DefSymb("DECO");             ---   79.
//	%+D       DefSymb("PUSH");             ---   80.
//	%+D       DefSymb("PUSHC");            ---   81.
//	%+D       DefSymb("PUSHLEN");          ---   82.
//	%+D       DefSymb("DUP");              ---   83.
//	%+D       DefSymb("POP");              ---   84.
//	%+D       DefSymb("EMPTY");            ---   85.
//	%+D       DefSymb("SETOBJ");           ---   86.
//	%+D       DefSymb("GETOBJ");           ---   87.
//	%+D       DefSymb("ACCESS");           ---   88.
//	%+D       DefSymb("FJUMP");            ---   89.
//	%+D       DefSymb("BJUMP");            ---   90.
//	%+D       DefSymb("FJUMPIF");          ---   91.
//	%+D       DefSymb("BJUMPIF");          ---   92.
//	%+D       DefSymb("SWITCH");           ---   93.
//	%+D       DefSymb("GOTO");             ---   94.
//	%+D       DefSymb("T-INITO");          ---   95.
//	%+D       DefSymb("T-GETO");           ---   96.
//	%+D       DefSymb("T-SETO");           ---   97.
//	%+D       DefSymb("ADD");              ---   98.
//	%+D       DefSymb("SUB");              ---   99.
//	%+D       DefSymb("MULT");             ---  100.
//	%+D       DefSymb("DIV");              ---  101.
//	%+D       DefSymb("REM");              ---  102.
//	%+D       DefSymb("NEG");              ---  103.
//	%+D       DefSymb("AND");              ---  104.
//	%+D       DefSymb("OR");               ---  105.
//	%+D       DefSymb("XOR");              ---  106.
//	%+D       DefSymb("IMP");              ---  107.
//	%+D       DefSymb("EQV");              ---  108.
//	%+D       DefSymb("NOT");              ---  109.
//	%+D       DefSymb("DIST");             ---  110.
//	%+D       DefSymb("ASSIGN");           ---  111.
//	%+D       DefSymb("UPDATE");           ---  112.
//	%+D       DefSymb("CONVERT");          ---  113.
//	%+D       DefSymb("SYSINSERT");        ---  114.
//	%+D       DefSymb("INSERT");           ---  115.
//	%+D       DefSymb("ZEROAREA");         ---  116.
//	%+D       DefSymb("INITAREA");         ---  117.
//	%+D       DefSymb("COMPARE");          ---  118.
//	%+D       DefSymb("?LT");              ---  119.
//	%+D       DefSymb("?LE");              ---  120.
//	%+D       DefSymb("?EQ");              ---  121.
//	%+D       DefSymb("?GE");              ---  122.
//	%+D       DefSymb("?GT");              ---  123.
//	%+D       DefSymb("?NE");              ---  124.
//	%+D       DefSymb("EVAL");             ---  125.
//	%+D       DefSymb("INFO");             ---  126.
//	%+D       DefSymb("LINE");             ---  127.
//	%+D       DefSymb("SETSWITCH");        ---  128.
//	%+D       DefSymb("RSHIFTA");          ---  129. Extension to S-Code
//	%+D       DefSymb("PROGRAM");          ---  130.
//	%+D       DefSymb("MAIN");             ---  131.
//	%+D       DefSymb("ENDPROGRAM");       ---  132.
//	%+D       DefSymb("DSIZE");            ---  133.
//	%+D       DefSymb("SDEST");            ---  134.
//	%+D       DefSymb("RUPDATE");          ---  135.
//	%+D       DefSymb("ASSCALL");          ---  136.
//	%+D       DefSymb("CALL-TOS");         ---  137.
//	%+D       DefSymb("DINITAREA");        ---  138.
//	%+D       DefSymb("NOSIZE");           ---  139.
//	%+D       DefSymb("POPALL");           ---  140.
//	%+D       DefSymb("REPCALL");          ---  141.
//	%+D       DefSymb("INTERFACE");        ---  142.
//	%+D       DefSymb("MACRO");            ---  143.
//	%+D       DefSymb("MARK");             ---  144.
//	%+D       DefSymb("MPAR");             ---  145.
//	%+D       DefSymb("ENDMACRO");         ---  146.
//	%+D       DefSymb("MCALL");            ---  147.
//	%+D       DefSymb("PUSHV");            ---  148.
//	%+D       DefSymb("SELECTV");          ---  149.
//	%+D       DefSymb("REMOTEV");          ---  150.
//	%+D       DefSymb("INDEXV");           ---  151.
//	%+D       DefSymb("ACCESSV");          ---  152.
//	%+D       DefSymb("DECL");             ---  153.
//	%+D       sx:=DefSymb("STMT");         ---  154.
//	%+D       if sx.val <> 154 then IERR("PARSE.DefSinstr") endif;
//	%+D end;
//	%title ***   B E G P R O G   ***
//
//	Routine NewExtAdr;
//	import infix(WORD) smbx; export infix(MemAddr) x;
//	begin x.kind:=extadr; x.rela.val:=0; x.smbx:=smbx;
//	%-E   x.sbireg:=0;
//	%+E   x.sibreg:=NoIBREG;
//	end;
//
//	Routine NewRelxAdr;
//	import infix(WORD) xref; export infix(MemAddr) x;
//	begin x.kind:=extadr; x.rela.val:=0; x.smbx:=xref;
//	%-E   x.sbireg:=oSS;
//	%+E   x.sibreg:=NoIBREG;
//	end;
//
//	Routine NewBasType; import range(0:MaxWord) type,nbyte,kind;
//	begin infix(DataType) x; x.pntmap.val:=0;
//	      x.nbyte:=nbyte; x.kind:=kind; TTAB(type):=x;
//	end;
//
//	Routine NewPntType; import range(0:MaxWord) type,nbyte,kind,rela;
//	begin infix(DataType) x; edchar(sysedit,rela qua character);
//	      x.pntmap:=DefSymb(pickup(sysedit));
//	      x.nbyte:=nbyte; x.kind:=kind; TTAB(type):=x;
//	end;


	public static void BEGPROG() {
		System.out.println("Parse.BEGPROG: ");
//	Visible routine BEGPROG;
//	begin range(0:MaxWord) i; infix(string) action;
//	%+D   integer trc,txx; 
//
//	      ---  Create and open scode (inbytefile=5) ---
//	%+S   if envpar then
//	           edtextinfo(sysedit,4);
//	%+S   else if SCODID.val = 0
//	%+S        then ed(sysedit,"INPUT.SCD")
//	%+S        else EdSymb(sysedit,SCODID) endif;
//	%+S   endif;
//	----  action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!8!NOBUFFER!0!";  -- in(byte)file
//	      action:=
//	      "!0!!1!!1!!0!!1!!2!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!";
//	      scode:=Open(pickup(sysedit),5,action,0);
//		Scode.initScode();
//
//	      --- Create and Open Relocatable/Assembly Scratch File
//	%+S   if envpar then
//	           edtextinfo(sysedit,7);
//	%+S   else if SCRID.val = 0
//	%+S        then ed(sysedit,"ICODE.TMP")
//	%+S        else EdSymb(sysedit,SCRID) endif;
//	%+S   endif;
//	      action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!";  -- out(byte)file
//	%+A   if ASMID.val = 0
//	%+A   then asmgen:=false;
//	           scrfile:=Open(pickup(sysedit),6,action,0);
//	%+A   else asmgen:=true; scrfile:=Open(pickup(sysedit),2,action,0) endif;
//
//	      CurInstr:=0; modinpt:=0; modoupt:=0; objfile:=0;
//
//	      ---  Clear all free object lists ---
//	      FreePool:=none;
//	      i:=0; repeat while i<K_Max
//	      do FreeObj(i):=none;
//	%+D      ObjCount(i):=0;
//	         i:=i+1
//	      endrepeat
//
//	%+S   if    SYSGEN=1
//	%+S   then outimage; outstring("** RTS Generating-mode **");
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("R@") endif;
//	%+S   elsif SYSGEN=2
//	%+S   then outimage; outstring("** S-Compiler Generating-mode **");
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("A@") endif;
//	%+S   elsif SYSGEN=3
//	%+S   then outimage; outstring("** Environment Generating-mode **")
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("E@") endif;
//	%+S   elsif SYSGEN=4
//	%+S   then outimage; outstring("** Library Generating-mode **")
//	%+S        outimage;
//	%+S   elsif PRFXID.val=0 then PRFXID:=DefSymb("S@") endif;
//	%-S      if PRFXID.val=0 then PRFXID:=DefSymb("S@") endif;
//	      -----------   type    nbyte    kind     pnt  )
//	      NewBasType(  T_VOID    ,0      ,0            ) 
//	      NewBasType(  T_WRD4    ,4     ,tSigned       )
//	      NewBasType(  T_WRD2    ,2     ,tSigned       )
//	      NewBasType(  T_BYT2    ,2     ,tUnsigned     )
//	      NewBasType(  T_BYT1    ,1     ,tUnsigned     )
//	      NewBasType(  T_REAL    ,4     ,tFloat        )
//	      NewBasType(  T_LREAL   ,8     ,tFloat        )
//	%-E   NewBasType(  T_TREAL   ,10    ,tFloat        )
//	%+E   NewBasType(  T_TREAL   ,12    ,tFloat        )
//	      NewBasType(  T_BOOL    ,1     ,tUnsigned     )
//	      NewBasType(  T_CHAR    ,1     ,tUnsigned     )
//	      NewBasType(  T_SIZE ,AllignFac,tUnsigned     )
//	      NewPntType(  T_OADDR   ,4     ,tUnsigned ,0  )
//	      NewBasType(  T_AADDR,AllignFac,tUnsigned     )
//	%-E   NewPntType(  T_GADDR   ,6     ,tUnsigned ,2  )
//	%+E   NewPntType(  T_GADDR   ,8     ,tUnsigned ,4  )
//	      NewBasType(  T_PADDR   ,4     ,tUnsigned     )
//	      NewBasType(  T_RADDR   ,4     ,tUnsigned     )
//	%-E   NewBasType(  T_NPADR   ,2     ,tUnsigned     )
//	      NewBasType(  T_NOINF   ,0    ,tUnsigned      )
//	      ntype:=T_max; T_INT:=T_WRD4; T_SINT:=T_WRD2;
//
//	      -- Create System Segments
//	      DumSEG := DefSegm("NOTHING",aCODE);
//	      DGROUP := DefSegm("_DATA",aDGRP);
//	      EnvCSEG:= DefSegm("S@ENV_TEXT",aCODE);
//	      PutSegx(DGROUP);
//
//	%+F   if (OSID=oUNIX386) or (OSID=oUNIX386W)
//	%+F   then X_OSSTAT := NewExtAdr(DefExtr("G@OSSTAT",DGROUP));
//	%+F        X_KNLAUX := NewExtAdr(DefExtr("G@KNLAUX",DGROUP));
//	%+F        X_STATUS := NewExtAdr(DefExtr("G@STATUS",DGROUP));
//	%+F        X_STMFLG := NewExtAdr(DefExtr("G@STMFLG",DGROUP));
//	%+F %-E    X_IMUL   := NewExtAdr(DefExtr("E@IMUL",EnvCSEG));
//	%+F %-E    X_IDIV   := NewExtAdr(DefExtr("E@IDIV",EnvCSEG));
//	%+F %-E    X_IREM   := NewExtAdr(DefExtr("E@IREM",EnvCSEG));
//	%+F %-E    X_GOTO   := NewExtAdr(DefExtr("E@GOTO",EnvCSEG));
//	%+F %-E    X_CALL   := NewExtAdr(DefExtr("E@CALL",EnvCSEG));
//	%+F %+D    X_ECASE  := NewExtAdr(DefExtr("E@ECASE",EnvCSEG));
//	%+F %+S    X_INITO  := NewExtAdr(DefExtr("E@INITO",EnvCSEG));
//	%+F %+S    X_GETO   := NewExtAdr(DefExtr("E@GETO",EnvCSEG));
//	%+F %+S    X_SETO   := NewExtAdr(DefExtr("E@SETO",EnvCSEG));
//	%+F %-E    X_CHKSTK := NewExtAdr(DefExtr("E@CHKSTK",EnvCSEG));
//	%+FSC %-E  X_STKBEG := NewExtAdr(DefExtr("G@STKBEG",DGROUP));
//	%+FSC %-E  X_STKOFL := NewExtAdr(DefExtr("E@STKOFL",EnvCSEG));
//	%+F        X_SSTAT  := NewExtAdr(DefExtr("E@SSTAT",EnvCSEG));
//	%+F        TMPAREA  := NewRelxAdr(DefExtr("G@TMP8687",DGROUP));
//	%+F        TMPQNT   := NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	%+F        X_INITSP := NewRelxAdr(DefExtr("G@INITSP",DGROUP));
//	%+F   else
//	           X_OSSTAT := NewExtAdr(DefExtr("G@OSSTAT",DGROUP));
//	           X_KNLAUX := NewExtAdr(DefExtr("G@KNLAUX",DGROUP));
//	           X_STATUS := NewExtAdr(DefExtr("G@STATUS",DGROUP));
//	           X_STMFLG := NewExtAdr(DefExtr("G@STMFLG",DGROUP));
//	%-E        X_IMUL   := NewExtAdr(DefExtr("E@IMUL",EnvCSEG));
//	%-E        X_IDIV   := NewExtAdr(DefExtr("E@IDIV",EnvCSEG));
//	%-E        X_IREM   := NewExtAdr(DefExtr("E@IREM",EnvCSEG));
//	%-E        X_RENEG  := NewExtAdr(DefExtr("E@RENEG",EnvCSEG));
//	%-E        X_READD  := NewExtAdr(DefExtr("E@READD",EnvCSEG));
//	%-E        X_RESUB  := NewExtAdr(DefExtr("E@RESUB",EnvCSEG));
//	%-E        X_REMUL  := NewExtAdr(DefExtr("E@REMUL",EnvCSEG));
//	%-E        X_REDIV  := NewExtAdr(DefExtr("E@REDIV",EnvCSEG));
//	%-E        X_RECMP  := NewExtAdr(DefExtr("E@RECMP",EnvCSEG));
//	%-E        X_LRNEG  := NewExtAdr(DefExtr("E@LRNEG",EnvCSEG));
//	%-E        X_LRADD  := NewExtAdr(DefExtr("E@LRADD",EnvCSEG));
//	%-E        X_LRSUB  := NewExtAdr(DefExtr("E@LRSUB",EnvCSEG));
//	%-E        X_LRMUL  := NewExtAdr(DefExtr("E@LRMUL",EnvCSEG));
//	%-E        X_LRDIV  := NewExtAdr(DefExtr("E@LRDIV",EnvCSEG));
//	%-E        X_LRCMP  := NewExtAdr(DefExtr("E@LRCMP",EnvCSEG));
//	%-E        X_IN2RE  := NewExtAdr(DefExtr("E@IN2RE",EnvCSEG));
//	%-E        X_IN2LR  := NewExtAdr(DefExtr("E@IN2LR",EnvCSEG));
//	%-E        X_RE2IN  := NewExtAdr(DefExtr("E@RE2IN",EnvCSEG));
//	%-E        X_RE2LR  := NewExtAdr(DefExtr("E@RE2LR",EnvCSEG));
//	%-E        X_LR2IN  := NewExtAdr(DefExtr("E@LR2IN",EnvCSEG));
//	%-E        X_LR2RE  := NewExtAdr(DefExtr("E@LR2RE",EnvCSEG));
//	%-E        X_GOTO   := NewExtAdr(DefExtr("E@GOTO",EnvCSEG));
//	%-E        X_CALL   := NewExtAdr(DefExtr("E@CALL",EnvCSEG));
//	%+D        X_ECASE  := NewExtAdr(DefExtr("E@ECASE",EnvCSEG));
//	%+S        X_INITO  := NewExtAdr(DefExtr("E@INITO",EnvCSEG));
//	%+S        X_GETO   := NewExtAdr(DefExtr("E@GETO",EnvCSEG));
//	%+S        X_SETO   := NewExtAdr(DefExtr("E@SETO",EnvCSEG));
//	%-E        X_CHKSTK := NewExtAdr(DefExtr("E@CHKSTK",EnvCSEG));
//	%-E %+SC   X_STKBEG := NewExtAdr(DefExtr("G@STKBEG",DGROUP));
//	%-E %+SC   X_STKOFL := NewExtAdr(DefExtr("E@STKOFL",EnvCSEG));
//	           X_SSTAT  := NewExtAdr(DefExtr("E@SSTAT",EnvCSEG));
//	           TMPAREA  := NewRelxAdr(DefExtr("G@TMP8687",DGROUP));
//	           TMPQNT   := NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	           X_INITSP := NewRelxAdr(DefExtr("G@INITSP",DGROUP));
//	%+F   endif;
//
//	%+D   ---   Set initial list and trace switches  ---
//	%+D   TraceMode:=0; ModuleTrace:=0;
//	%+D   if SK1LIN = 1
//	%+D   then trc:=SK1TRC; txx:=trc/10; InputTrace:=  trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    TraceMode:=   trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    ModuleTrace:= trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listsw:=      trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listq1:=      trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listq2:=      trc-(10*txx);
//	%+D        SK1LIN:=0; SK1TRC:=0;
//	%+D   endif;
//
//	      curseg:=none; NewCurSeg(NEWOBJ(K_BSEG,size(BSEG)));
//	      nSubSeg:=0; FreeSeg:=none; InsideRoutine:=false;
//	      reversed:=false; InMassage:=false; DeadCode:=false;
//	      deleteOK:= MASSLV>61 and (DEBMOD<3);
//	      MindMask:=uSPBPM; PreReadMask:=NotMindMask:=0; PreMindMask:=uSPBPM;
//	%+S   PreWriteMask:=0;
//	end;
	}
//
//	%page
//
//	Routine SpaceOnStack;
//	import range(0:MaxByte) nwm; -- no.words/dwords to be moved on stack
//	       range(0:MaxByte) nbi; -- no.bytes to be inserted on stack
//	begin ref(Qpkt) qi; qi:=FindPushx(nwm);
//	      if qi <> none
//	      then if (qi.fnc=qDYADC) and (qi.subc=qSUB)
//	%-E        and (qi.reg=qSP)
//	%+E        and (qi.reg=qESP)
//	           then qi qua Qfrm2.aux.val:=qi qua Qfrm2.aux.val+nbi
//	                -- no need (and also error???) to call: ModifyQinstr(qi);
//	           else
//	%-E             InsertQf2(qi,qDYADC,qSUB,qSP,cSTP,nbi);
//	%+E             InsertQf2(qi,qDYADC,qSUB,qESP,cSTP,nbi);
//	           endif;
//	      elsif nwm < 6
//	      then
//	%-E        Qf1(qPOPR,qAX,cANY);
//	%-E        if nwm > 1 then Qf1(qPOPR,qCX,cANY);
//	%-E             if nwm > 2 then Qf1(qPOPR,qDX,cANY);
//	%-E                  if nwm > 3 then Qf1(qPOPR,qSI,cANY);
//	%-E                       if nwm > 4 then Qf1(qPOPR,qDI,cANY) endif;
//	%+E        Qf1(qPOPR,qEAX,cANY);
//	%+E        if nwm > 1 then Qf1(qPOPR,qECX,cANY);
//	%+E             if nwm > 2 then Qf1(qPOPR,qEDX,cANY);
//	%+E                  if nwm > 3 then Qf1(qPOPR,qESI,cANY);
//	%+E                       if nwm > 4 then Qf1(qPOPR,qEDI,cANY) endif;
//	           endif endif endif;
//	%-E        Qf2(qDYADC,qSUB,qSP,cSTP,nbi);
//	%-E        if nwm > 4 then Qf1(qPUSHR,qDI,cANY) endif;
//	%-E        if nwm > 3 then Qf1(qPUSHR,qSI,cANY) endif;
//	%-E        if nwm > 2 then Qf1(qPUSHR,qDX,cANY) endif;
//	%-E        if nwm > 1 then Qf1(qPUSHR,qCX,cANY) endif;
//	%-E        Qf1(qPUSHR,qAX,cANY);
//	%+E        Qf2(qDYADC,qSUB,qESP,cSTP,nbi);
//	%+E        if nwm > 4 then Qf1(qPUSHR,qEDI,cANY) endif;
//	%+E        if nwm > 3 then Qf1(qPUSHR,qESI,cANY) endif;
//	%+E        if nwm > 2 then Qf1(qPUSHR,qEDX,cANY) endif;
//	%+E        if nwm > 1 then Qf1(qPUSHR,qECX,cANY) endif;
//	%+E        Qf1(qPUSHR,qEAX,cANY);
//	      else
//	%-E        Qf2(qMOV,0,qSI,cSTP,qSP); Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E        Qf2(qDYADC,qSUB,qSP,cSTP,nbi);
//	%-E        Qf2(qMOV,0,qDI,cSTP,qSP); Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        Qf2(qLOADC,0,qCX,cVAL,nwm); Qf2(qRSTRW,qRMOV,qCLD,cANY,qREP);
//	%+E        Qf2(qMOV,0,qESI,cSTP,qESP);
//	%+E        Qf2(qDYADC,qSUB,qESP,cSTP,nbi);
//	%+E        Qf2(qMOV,0,qEDI,cSTP,qESP);
//	%+E        Qf2(qLOADC,0,qECX,cVAL,nwm); Qf2(qRSTRW,qRMOV,qCLD,cANY,qREP);
//	      endif;
//	end;
//
//	public static void setLine(int type) {
//		Scomn.curline = Scode.inNumber();
//	begin infix(word) wrd; integer trc,txx;
//	%-D    InNumber(%wrd%);  curline:=wrd.val;
//	%+D    wrd:=InputNumber; curline:=wrd.val;
//	%+D    if SK1LIN <> 0
//	%+D    then if curline >= SK1LIN
//	%+D         then trc:=SK1TRC; txx:=trc/10; InputTrace:=  trc-(10*txx);
//	%+D              trc:=txx; txx:=trc/10;    TraceMode:=   trc-(10*txx);
//	%+D              trc:=txx; txx:=trc/10;    ModuleTrace:= trc-(10*txx);
//	%+D              trc:=txx; txx:=trc/10;    listsw:=      trc-(10*txx);
//	%+D              trc:=txx; txx:=trc/10;    listq1:=      trc-(10*txx);
//	%+D              trc:=txx; txx:=trc/10;    listq2:=      trc-(10*txx);
//	%+D              if SK1TRC = 0 then SK1LIN:=0
//	%+D              else SK1LIN:=SK1LIN+5; SK1TRC:=0 endif;
//	%+D         endif;
//	%+D    endif;
//	%+D    Qf2(qLINE,type,0,cANY,curline);
//	%-D    --- only STM is inserted in prod. versions ---
//	%-D    if type=qSTM then Qf2(qLINE,type,0,cANY,curline) endif;
	}
//
//	 Routine SetSwitch;
//	 begin range(0:MaxByte) n,val;
//	%+D    n:=InputByte; val:=InputByte;
//	%-D    InByte(%n%);  InByte(%val%);
//	%+D    case 0:10 (n)
//	%+D    when 1: InputTrace:=val      when 2: TraceMode:=val
//	%+D    when 3: ModuleTrace:=val     when 4: listsw:=val
//	%+D    when 5: listq1:=val          when 6: listq2:=val
//	%+D    otherwise
//	                 ERROR("Trace switch index is undefined");
//	%+D    endcase;
//	 end;
//
//	%title ***   E N D P R O G   ***
//	 Visible routine ENDPROG;
//	 begin size poolsize,poolused; real xqtime; infix(WORD) sx,segid;
//	%+S    range(0:200) i;
//	%+D    Boolean emsg;
//	       poolsize:=PoolBot-PoolTop; poolused:=PoolNxt-PoolTop;
//	%+D    xqtime:=(EnvGetCpuTime qua real) - InitTime;
//	%+D    if status <> 0 then IERR("ENDPROG") endif;
//	%+D    emsg:=(TLIST<>0);
//	%+SD   if not envpar then if nerr<>0 then emsg:=true endif endif;
//	%+S %-D                   if nerr<>0 then emsg:=true endif      ;
//	%+D    if emsg
//	%+D    then outimage; outimage; outstring("End S-Compiler iAPX/109  -");
//	%+D         if curline<>0
//	%+D         then outstring("  Lines: "); outint(curline) endif;
//	%+D         outstring("  Errors: ");
//	%+D         if nerr=0 then outstring("None")
//	%+D         else outword(nerr) endif;
//	%+D         outimage;
//	%+D    endif;
//	%+D    emsg:=(TLIST>1);
//	%+SD   if not envpar then if nerr<>0 then emsg:=true endif endif;
//	%+S %-D                   if nerr<>0 then emsg:=true endif      ;
//	%+D    if emsg then outimage; outimage; CAPDUMP; RUTDUMP; endif;
//	       if scode   <> 0 then EnvClose(scode,nostring);   scode:=0  endif;
//	       if modinpt <> 0 then EnvClose(modinpt,nostring); modinpt:=0 endif;
//	       if modoupt <> 0 then EnvClose(modoupt,nostring); modoupt:=0 endif;
//	       if scrfile <> 0 then EnvClose(scrfile,nostring); scrfile:=0 endif;
//	       if objfile <> 0 then EnvClose(objfile,nostring); objfile:=0 endif;
//	       if MainEntry.kind <> 0
//	       then
//	%+S         if envpar
//	%+S         then
//	%+A              if asmgen then -- Nothing
//	%+A              else
//	                      AUTOLINK;
//	%+A              endif;
//	%+S         endif;
//	       endif;
//	 end;
//
//	Routine AUTOLINK;
//	begin infix(WORD) ix; range(0:MaxWord) p; ref(ModElt) m; infix(String) s,action;
//	%+D   if TraceMode <> 0
//	%+D   then outstring("AUTOLINK ");
//	%+D        outsymb(relid); printout(sysout);
//	%+D   endif;
//	      --- Re-Open Scratch File
//	      action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!";  -- out(byte)file
//	      edtextinfo(sysedit,7); scrfile:=Open(pickup(sysedit),6,action,0)
//	      EnvOut2byte(scrfile,4652); -- Magic number
//	      if Status<>0 then FILERR(scrfile,"PARSE.AUTOLINK-1") endif;
//	      ix.val:=0; repeat while ix.val < DIC.nModl
//	      do ix.val:=ix.val+1; m:=DICREF(DIC.Modl(ix.HI).elt(ix.LO));
//	         if m.RelElt.val > 0
//	         then edsymb(sysedit,m.AtrNam);
//	              repeat p:=sysedit.pos while p <> 0
//	              do p:=p-1; 
//	                 if sysedit.chr(p)='/' then goto L1 endif;
//	                 if sysedit.chr(p)='\' then goto L2 endif;
//	                 sysedit.pos:=p;
//	              endrepeat;
//	       L1:L2: edsymb(sysedit,m.RelElt);
//	              s:=pickup(sysedit);
//	              EnvOutByte(scrfile,s.nchr);
//	              if Status <> 0 then FILERR(scrfile,"PARSE.AUTOLINK-4") endif;
//	              EnvOutBytes(scrfile,s);
//	              if Status <> 0 then FILERR(scrfile,"PARSE.AUTOLINK-5") endif;
//	         endif;
//	      endrepeat;
//	      EnvOutByte(scrfile,0);
//	      if Status <> 0 then FILERR(scrfile,"PARSE.AUTOLINK-6") endif;
//	      EnvClose(scrfile,nostring); scrfile:=0;
//	end;
//	%title ***   T E R M I N A T E   ***
//
//	 Visible routine TERMINATE;
//	 begin
//	       ------ Close Sysin ------
//	       if sysin <> none
//	       then if sysin.key <> 0
//	            then EnvClose(sysin.key,nostring); sysin.key:=0 endif;
//	       endif;
//
//	       ------ Close Sysout ------
//	       if sysout <> none
//	       then if sysout.key <> 0
//	            then EnvClose(sysout.key,nostring); sysout.key:=0 endif;
//	       endif;
//
//	%+S    if envpar then EnvGiveIntInfo(4,nerr) endif;
//	       EnvTerm(0,nostring);
//	 end;
//
//	 Routine PrtError; import infix(string) mss; range(0:30) stat;
//	 begin status:=0; outimage; outstring("Line "); outint(curline);
//	       outstring(" *** SYSTEM ERROR ***   "); outstring(mss); outimage;
//
//	%+D    if stat <> 0
//	%+D    then outimage; outstring("*** STATUS ");
//	%+D         outword(stat); outstring(" ***   ");
//	%+D         outstring(STATMSG(stat)); outimage;
//	%+D    endif;
//	 end;
//
//	 Body(Perror) Erhandl;
//	 begin if errormode then EnvTerm(3,msg)
//	       else errormode:=true; nerr:=nerr+1;
//	            PrtError(msg,status); ENDPROG; TERMINATE;
//	       endif;
//	 end;
//
//
//
//	 Body(perhandl) Exhandl;
//	 begin range(0:maxbyte) stat; stat:=status; status:=0;
//	%+D    if code>16 then code:=0 endif
//	%+D    if errormode then EnvTerm(3,INTMSG(code))
//	%+D    else errormode:=true; nerr:=nerr+1;
//	%-D         outstring("INTERNAL SYSTEM ERROR (BackEnd), code "); outint(code);
//	%-D         outimage; outstring("Source line "); outint(curline); outimage;
//	%+D         PrtError(INTMSG(code),stat);
//	            if (code=0) or (code=11)
//	            then outstring(msg); outimage endif;
//	%+D         ENDPROG;
//	%-D         TERMINATE;
//	%+D    endif;
//	 end;
//
//end;
//}
