package removed.java;

import bec.util.Scode;
import bec.util.Util;

public class Minut {
//	 Module MINUT("iAPX");
//	 begin insert SCOMN,SKNWN,SBASE,COASM,MASSAGE;
//	       -----------------------------------------------------------------
//	       ---  COPYRIGHT 1988 by                                        ---
//	       ---  Simula a.s.                                              ---
//	       ---  Oslo, Norway                                             ---
//	       ---                                                           ---
//	       ---              P O R T A B L E     S I M U L A              ---
//	       ---                                                           ---
//	       ---                   F O R    I B M    P C                   ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---           S   -   C   O   M   P   I   L   E   R           ---
//	       ---                                                           ---
//	       ---                I n p u t    R o u t i n e s               ---
//	       ---          M o d u l e    I / O    R o u t i n e s          ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//
//	infix(WORD) ModCSEG;       -- Current input module's code Segment index
//	infix(ModuleHeader) modlab;
//
//
//	Routine Name2Buf; import name() nm; size s; export infix(string) res;
//	begin res.chradr:=nm; res.nchr:=Size2Word(s) end;
//
//	Routine Ref2Buf; import ref() rf; size s; export infix(string) res;
//	begin res.chradr:=Ref2Name(rf); res.nchr:=Size2Word(s) end;
//	%title ***    D i s p l a y    U t i l i t i e s    ***
//
//	Visible Routine IntoDisplay;
//	import ref(Descriptor) d; infix(WORD) tag;
//	begin ref(Object) obj;
//	%+D   RST(R_IntoDisplay);
//	      if tag.val <> 0
//	      then
//	%+C        if    DISPL(tag.HI)=none             then -- Nothing
//	%+C        elsif DISPL(tag.HI).elt(tag.LO)=none then -- Nothing
//	%+C        elsif DISPL(tag.HI).elt(tag.LO) <> d
//	%+C        then
//	%+CD            edtag(errmsg,tag);
//	%+C             IERR("Display-entry is already defined:");
//	%+C        endif;
//	           if DISPL(tag.HI)=none
//	           then obj:=FreeObj(K_RefBlock);
//	                if obj <> none
//	                then FreeObj(K_RefBlock):=obj qua FreeObject.next;
//	                else L: obj:=PoolNxt; PoolNxt:=PoolNxt+size(RefBlock);
//	                     if PoolNxt >= PoolBot
//	                     then PALLOC(size(RefBlock),obj); goto L endif;
//	%+D                  ObjCount(K_RefBlock):=ObjCount(K_RefBlock)+1;
//	                endif;
//	                ZeroArea(obj,obj+size(RefBlock));
//	                obj.kind:=K_RefBlock; DISPL(tag.HI):=obj;
//	           endif;
//	           DISPL(tag.HI).elt(tag.LO):=d;
//	%+D        if TraceMode <> 0
//	%+D        then setpos(sysout,14); outstring("Display(");
//	%+D             outword(tag.val); outstring(") = "); print(d);
//	%+D        endif;
//	      endif;
//	end;
	
//	%title ***   I n p u t   R e c o r d   ***
	
	/**
	 *	record_descriptor
     *		::= record record_tag:newtag <record_info>?
     *			<prefix_part>? common_part
     *			<alternate_part>*
     *			endrecord 
     *
	 *	record_info	::= info "TYPE" | info "DYNAMIC"
	 *	prefix_part	::= prefix resolved_structure
	 *	common_part	::= <attribute_definition>*
	 *	alternate_part ::= alt <attribute_definition>*
	 *		attribute_definition ::= attr attr:newtag quantity_descriptor
	 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *			structured_type ::= record_tag:tag
	 *
	 *		quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 */
	public static void inRecord() {
		Scode.inTag();
		if(Scode.accept(Scode.S_INFO)) Scode.inString();
		if(Scode.accept(Scode.S_PREFIX)) Scode.inTag();
		do {
			while(Scode.accept(Scode.S_ATTR)) {
				Scode.inTag(); Scode.inType();
				if(Scode.accept(Scode.S_REP)) Scode.inNumber();
			}
		} while(Scode.accept(Scode.S_ALT));
		Scode.expect(Scode.S_ENDRECORD);
	}
	
//	%title ***   I n G l o b a l   ***

	public static void inGlobal() {
		Util.IERR("Minut.inGlobal: NOT IMPLEMENTED");
//	begin infix(WORD) tag,count; range(0:MaxWord) nbyte;
//	      range(0:AllignFac) n; range(0:MaxType) type;
//	      ref(Descriptor) v; infix(MemAddr) adr;
//	%+E   infix(wWORD) ofst32;
//	      InTag(%tag%); TypeLength:=0; type:=intype; nbyte:=TypeLength;
//	%+E   if    type=T_BYT1 then type:=T_WRD4 --- NY: FOR TEST ?????
//	%+E   elsif type=T_BYT2 then type:=T_WRD4
//	%+E   elsif type=T_WRD2 then type:=T_WRD4 endif;
//	      if NextByte = S_REP
//	      then inputInstr;
//	%+D        count:=InputNumber;
//	%-D        InNumber(%count%);
//	%+C        if count.val=0
//	%+C        then IERR("Illegal 'REP 0'"); count.val:=1 endif;
//	      else count.val:=1 endif;
//	      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
//	%+S   if NextByte = S_SYSTEM
//	%+S   then inputInstr; v:=NEWOBJ(K_ExternVar,size(ExtDescr));
//	%+S        v.type:=type; v qua ExtDescr.adr.rela:=0;
//	%+S        v qua ExtDescr.adr.smbx:=InExtr('G',DGROUP);
//	%+S   else
//	           if nbyte > 1
//	           then
//	%-E             n:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E             ofst32.HighWord.val:=DBUF.ofstHI;
//	%+E             ofst32.LowWord.val:=DBUF.ofstLO;
//	%+E             n:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	%+E             if (nbyte=2) and (n>=2) then n:=n-2 endif;
//	                if n<>0 then EmitZero(n) endif;
//	%+C        elsif nbyte=0 then IERR("Illegal type on GlobalVar")
//	           endif;
//	           adr.kind:=segadr; adr.segmid:=DSEGID;
//	%-E        adr.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E        adr.rela.HighWord.val:=DBUF.ofstHI;
//	%+E        adr.rela.LowWord.val:=DBUF.ofstLO;
//	%+E        adr.rela.val:=adr.rela.val+DBUF.nxt;
//	%-E        adr.sbireg:=0;
//	%+E        adr.sibreg:=NoIBREG;
//	           v:=NEWOBJ(K_GlobalVar,size(IntDescr)); v.type:=type;
//	           v qua IntDescr.adr:=adr; EmitZero(count.val*nbyte);
//	%+S   endif;
//	      IntoDisplay(v,tag);
	}
//
//
//	%+S Visible Routine InLocal;
//	%+S import range(0:MaxWord) nlocbyte; export range(0:MaxWord) res;
//	%+S begin ref(LocDescr) locvar; infix(WORD) tag,count;
//	%+S       range(0:MaxWord) nbyte; range(0:MaxType) type;
//	%+S      --- Input Local Variable in Routine Body ---
//	%+S      InTag(%tag%); TypeLength:=0; type:=intype; nbyte:=TypeLength;
//	%+SE     if    type=T_BYT1 then type:=T_WRD4 --- NY: FOR TEST ?????
//	%+SE     elsif type=T_BYT2 then type:=T_WRD4
//	%+SE     elsif type=T_WRD2 then type:=T_WRD4 endif;
//	%+S      if NextByte = S_REP
//	%+S      then inputInstr; count:=InputNumber;
//	%+SC          if count.val=0
//	%+SC          then IERR("Illegal 'REP 0'"); count.val:=1 endif;
//	%+S      else count.val:=1 endif;
//	%+S      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
//	%+S      locvar:=NEWOBJ(K_LocalVar,size(LocDescr));
//	%+S      locvar.type:=type; IntoDisplay(locvar,tag);
//	%+S      nlocbyte:=nlocbyte+(nbyte*count.val);
//	%+S      nlocbyte:=wAllign(%nlocbyte%);
//	%+S      locvar.rela:=nlocbyte; res:=nlocbyte;
//	%+S end;
	
//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 */
	public static void inConstant(boolean constDef) {
//	begin ref(RepValue) cnst; ref(IntDescr) v; infix(WORD) tag,count;
//	      range(0:MaxWord) nbyte; range(0:MaxType) type;
//	      InTag(%tag%); TypeLength:=0; type:=intype; nbyte:=TypeLength;
		Scode.inTag();
		Scode.inType();
		if(Scode.accept(Scode.S_REP)) {
			Scode.inNumber();
//	      if NextByte = S_REP
//	      then inputInstr;
//	%+D        count:=InputNumber;
//	%-D        InNumber(%count%);
//	%+C        if count.val=0
//	%+C        then IERR("Illegal 'REP 0'"); count.val:=1 endif;
//	      else count.val:=1 endif;
		}
//	      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
//	%+C   if nbyte=0 then IERR("Illegal Type on Constant") endif;
//	      v:=if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
//	      if v=none
//	      then v:=NEWOBJ(K_GlobalVar,size(IntDescr));
//	           v.type:=type; v.adr:=noadr; IntoDisplay(v,tag);
//	%+C   else if v.kind <> K_GlobalVar
//	%+C        then
//	%+CD            edtag(errmsg,tag);
//	%+C             IERR("Display-entry is not defined as a constant:");
//	%+C        endif;
//	%+C        if v.type <> type
//	%+C        then IERR("Type not same as given by CONSTSPEC") endif;
//	      endif;
	      if(constDef) {
	    	  System.out.println("Minut.inConstant'constDef");
//	      then
//	    	  EmitRepValue(v,nbyte);
	    	  Coasm.emitValue();
//	%+S        if NextByte = S_SYSTEM
//	%+S        then inputInstr;
//	%+S             v.adr.kind:=extadr; v.adr.rela.val:=0;
//	%+S             v.adr.smbx:=InExtr('G',DGROUP);
//	%+S %-E         v.adr.sbireg:=0;
//	%+SE            v.adr.sibreg:=NoIBREG;
//	%+S        endif;
	    	  Scode.accept(Scode.S_SYSTEM);
	      }
    	  System.out.println("Minut.inConstant FINISH");
	}

	
//	%title ***   R o u t i n e   S p e c   ***
//
//	%+S Visible Routine SpecRut; import Boolean inHead;
//	%+S begin infix(WORD) tag,ptag; infix(WORD) smb;
//	%+S       infix(string) s; ref(IntDescr) v;
//	%+S       InTag(%tag%); InXtag(%ptag%);
//	%+S       if inHead
//	%+S       then if TagIdent.val <> 0
//	%+S            then EdSymb(sysedit,PRFXID); -- edchar(sysedit,'@');
//	%+S                 edsymb(sysedit,TagIdent); s:=pickup(sysedit);
//	%+S                 if s.nchr>16 then smb:=NewPubl
//	%+S                 else smb:=DefPubl(s) endif;
//	%+S            else smb:=NewPubl endif;
//	%+S       else smb.val:=0 endif;
//	%+S       v:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	%+S       v.adr:=NewFixAdr(CSEGID,smb); IntoDisplay(v,tag);
//	%+S end;
//
//	%title ***   I n p u t   P r o f i l e   ***
//	Visible Routine InProfile;
//	import range(0:P_max) defkind; export ref(ProfileDescr) pr;
//	begin infix(WORD) tag,rtag,ptag,ExpTag,segid,count;
//	      range(0:MaxType) type,ExpType; infix(string) s,xs;
//	      range(0:MaxWord) i,npar,kind,nbyte,lng,nlocbyte;
//	      ref(LocDescr) v,w,fpar; ref(IntDescr) rut;
//	      infix(WORD) eid,xid,nid; range(0:1) wxt;
//	      range(0:MaxByte) nCnt,Pno(4),Cnt(4);
//
//	      InTag(%tag%); kind:=defkind; ExpType:=T_VOID;
//	      nbyte:=0; rut:=none; nCnt:=0; segid.val:=0;
//	      if    NextByte = S_EXTERNAL
//	      then  inputInstr;
//	            kind:=P_EXTERNAL;
//	            InXtag(%rtag%);     -- Body'Tag
//	            nid:=inSymb;        -- Nature'String
//	            xs:=InString;       -- xid'String
//	            Ed(sysedit,xs); -- Deault EntryPoint
//	            rut:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	            rut.adr:=noadr;
//	            --- Search for NATURE index ---
//	            s:=DICSMB(nid);
//	               if STEQ(s,"simuletta")  then kind:=P_SIMULETTA
//	--- pje                                     segid:=EnvCSEG;
//	--- pje     elsif STEQ(s,"library")    then kind:=P_SIMULETTA
//	--- pje                                     segid:=EnvCSEG;
//	            elsif STEQ(s,"assembly")   then kind:=P_ASM
//	            elsif STEQ(s,"assembler")  then kind:=P_ASM
//	            elsif STEQ(s,"cc")         then kind:=P_C
//	            elsif STEQ(s,"c")          then kind:=P_C
//	                  if OSID<>oUNIX386 then if OSID<>oUNIX386W
//	                  then pickup(sysedit); -- Remove Deault EntryPoint
//	                       edchar(sysedit,'_'); Ed(sysedit,xs);
//	                  endif endif;
//	            elsif STEQ(s,"fortran")    then kind:=P_FTN
//	            elsif STEQ(s,"pascal")     then kind:=P_PASCAL
//	            endif;
//	            --- set segid (and kind?) for interface to envir  --- pje
//	            if sysedit.nchr>2                                 --- pje
//	            then s.chradr:=@sysedit.chr; s.nchr:=2;           --- pje
//	                 if STEQ(s,"E@")                              --- pje
//	                 then segid:=EnvCSEG; -- kind:=P_SIMULETTA ?  --- pje
//	            endif endif                                       --- pje
//	            
//	            eid:=DefExtr(pickup(sysedit),segid);
//	%+D         if InputTrace <> 0
//	%+D         then ed(inptrace,"ENT="); edsymb(inptrace,eid); ITRC("*BEC") endif;
//	            rut.adr.kind:=extadr; rut.adr.rela.val:=0; rut.adr.smbx:=eid
//	%-E         rut.adr.sbireg:=0;
//	%+E         rut.adr.sibreg:=NoIBREG;
//	%+S   elsif NextByte = S_INTERFACE
//	%+S   then  inputInstr; InString; kind:=P_INTERFACE;
//	%+S   elsif NextByte = S_KNOWN
//	%+S   then  inputInstr; kind:=P_KNOWN; InTag(%rtag%); xid:=inKsymb;
//	%+S         rut:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	%+S         rut.adr:=NewFixAdr(CSEGID,xid);
//	%+S         --- Search for inline index ---
//	%+S         s:=DICSMB(xid); s.nchr:=s.nchr-2;
//	%+S         s.chradr:=name(var(s.chradr)(2));
//	%+S            if STEQ(s,"RLOG10")    then kind:=P_RLOG10
//	%+S         elsif STEQ(s,"DLOG10")    then kind:=P_DLOG10
//	%+S         elsif STEQ(s,"RCOSIN")    then kind:=P_RCOSIN
//	%+S         elsif STEQ(s,"COSINU")    then kind:=P_COSINU
//	%+S         elsif STEQ(s,"RTANGN")    then kind:=P_RTANGN
//	%+S         elsif STEQ(s,"TANGEN")    then kind:=P_TANGEN
//	%+S         elsif STEQ(s,"RARCOS")    then kind:=P_RARCOS
//	%+S         elsif STEQ(s,"ARCCOS")    then kind:=P_ARCCOS
//	%+S         elsif STEQ(s,"RARSIN")    then kind:=P_RARSIN
//	%+S         elsif STEQ(s,"ARCSIN")    then kind:=P_ARCSIN
//	%+S         elsif STEQ(s,"ERRNON")    then kind:=P_ERRNON
//	%+S         elsif STEQ(s,"ERRQUA")    then kind:=P_ERRQUA
//	%+S         elsif STEQ(s,"ERRSWT")    then kind:=P_ERRSWT
//	%+S         elsif STEQ(s,"ERROR")     then kind:=P_ERROR
//	%+S         elsif STEQ(s,"CBLNK")     then kind:=P_CBLNK
//	%+S         elsif STEQ(s,"CMOVE")     then kind:=P_CMOVE
//	%+S         elsif STEQ(s,"STRIP")     then kind:=P_STRIP
//	%+S         elsif STEQ(s,"TXTREL")    then kind:=P_TXTREL
//	%+S         elsif STEQ(s,"TRFREL")    then kind:=P_TRFREL
//	%+S         elsif STEQ(s,"AR1IND")    then kind:=P_AR1IND
//	%+S         elsif STEQ(s,"AR2IND")    then kind:=P_AR2IND
//	%+S         elsif STEQ(s,"ARGIND")    then kind:=P_ARGIND
//	%+S         elsif STEQ(s,"IABS")      then kind:=P_IABS
//	%+S         elsif STEQ(s,"RABS")      then kind:=P_RABS
//	%+S         elsif STEQ(s,"DABS")      then kind:=P_DABS
//	%+S         elsif STEQ(s,"RSIGN")     then kind:=P_RSIGN
//	%+S         elsif STEQ(s,"DSIGN")     then kind:=P_DSIGN
//	%+S         elsif STEQ(s,"MODULO")    then kind:=P_MODULO
//	%+S         elsif STEQ(s,"RENTI")     then kind:=P_RENTI
//	%+S         elsif STEQ(s,"DENTI")     then kind:=P_DENTI
//	%+S         elsif STEQ(s,"DIGIT")     then kind:=P_DIGIT
//	%+S         elsif STEQ(s,"LETTER")    then kind:=P_LETTER
//	%+S         elsif STEQ(s,"RIPOWR")    then kind:=P_RIPOWR
//	%+S         elsif STEQ(s,"RRPOWR")    then kind:=P_RRPOWR
//	%+S         elsif STEQ(s,"RDPOWR")    then kind:=P_RDPOWR
//	%+S         elsif STEQ(s,"DIPOWR")    then kind:=P_DIPOWR
//	%+S         elsif STEQ(s,"DRPOWR")    then kind:=P_DRPOWR
//	%+S         elsif STEQ(s,"DDPOWR")    then kind:=P_DDPOWR
//	%+S         endif;
//	%+S   elsif NextByte = S_SYSTEM
//	%+S   then  inputInstr; kind:=P_SYSTEM; InTag(%rtag%);
//	%+S         xid:=InExtr('E',EnvCSEG);
//	%+S         rut:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	%+S         if PsysKind=P_KNL
//	%+S         then rut.adr.kind:=knladr; rut.adr.knlx:=xid
//	%+S         else rut.adr.kind:=extadr; rut.adr.smbx:=xid endif;
//	%+S         rut.adr.rela.val:=0;
//	%+S %-E     rut.adr.sbireg:=0;
//	%+SE        rut.adr.sibreg:=NoIBREG;
//	%+S         if PsysKind <> 0 then kind:=PsysKind
//	%+S -- ????????  rut.adr.segid.val:=0  ?????????????????
//	%+S         else --- Search for inline index ---
//	%+S              s:=DICSMB(xid); s.nchr:=s.nchr-2;
//	%+S              s.chradr:=name(var(s.chradr)(2));
//	%+S                 if STEQ(s,"GTOUTM")    then kind:=P_GTOUTM
//	%+S              elsif STEQ(s,"MOVEIN")    then kind:=P_MOVEIN
//	%+S              elsif STEQ(s,"RSQROO")    then kind:=P_RSQROO
//	%+S              elsif STEQ(s,"SQROOT")    then kind:=P_SQROOT
//	%+S              elsif STEQ(s,"RLOGAR")    then kind:=P_RLOGAR
//	%+S              elsif STEQ(s,"LOGARI")    then kind:=P_LOGARI
//	%+S              elsif STEQ(s,"REXPON")    then kind:=P_REXPON
//	%+S              elsif STEQ(s,"EXPONE")    then kind:=P_EXPONE
//	%+S              elsif STEQ(s,"RSINUS")    then kind:=P_RSINUS
//	%+S              elsif STEQ(s,"SINUSR")    then kind:=P_SINUSR
//	%+S              elsif STEQ(s,"RARTAN")    then kind:=P_RARTAN
//	%+S              elsif STEQ(s,"ARCTAN")    then kind:=P_ARCTAN
//	%+S              elsif STEQ(s,"M?CREF")    then kind:=P_DOS_CREF
//	%+S              elsif STEQ(s,"M?OPEN")    then kind:=P_DOS_OPEN
//	%+S              elsif STEQ(s,"M?CLOSE")   then kind:=P_DOS_CLOSE
//	%+S              elsif STEQ(s,"M?READ")    then kind:=P_DOS_READ
//	%+S              elsif STEQ(s,"M?WRITE")   then kind:=P_DOS_WRITE
//	%+S              elsif STEQ(s,"M?DELF")    then kind:=P_DOS_DELF
//	%+S              elsif STEQ(s,"M?FPTR")    then kind:=P_DOS_FPTR
//	%+S              elsif STEQ(s,"M?CDIR")    then kind:=P_DOS_CDIR
//	%+S              elsif STEQ(s,"M?ALOC")    then kind:=P_DOS_ALOC
//	%+S              elsif STEQ(s,"M?TERM")    then kind:=P_DOS_TERM
//	%+S              elsif STEQ(s,"M?TIME")    then kind:=P_DOS_TIME
//	%+S              elsif STEQ(s,"M?DATE")    then kind:=P_DOS_DATE
//	%+S              elsif STEQ(s,"M?VERS")    then kind:=P_DOS_VERS
//	%+S              elsif STEQ(s,"M?EXEC")    then kind:=P_DOS_EXEC
//	%+S              elsif STEQ(s,"M?IOCTL")   then kind:=P_DOS_IOCTL
//	%+S              elsif STEQ(s,"M?LOCK")    then kind:=P_DOS_LOCK
//	%+S              elsif STEQ(s,"M?GDRV")    then kind:=P_DOS_GDRV
//	%+S              elsif STEQ(s,"M?GDIR")    then kind:=P_DOS_GDIR
//	%+S              elsif STEQ(s,"S?SCMPEQ")  then kind:=P_APX_SCMPEQ
//	%+S              elsif STEQ(s,"S?SMOVEI")  then kind:=P_APX_SMOVEI
//	%+S              elsif STEQ(s,"S?SMOVED")  then kind:=P_APX_SMOVED
//	%+S              elsif STEQ(s,"S?SSKIP")   then kind:=P_APX_SSKIP
//	%+S              elsif STEQ(s,"S?STRIP")   then kind:=P_APX_STRIP
//	%+S              elsif STEQ(s,"S?SFINDI")  then kind:=P_APX_SFINDI
//	%+S              elsif STEQ(s,"S?SFINDD")  then kind:=P_APX_SFINDD
//	%+S              elsif STEQ(s,"S?SFILL")   then kind:=P_APX_SFILL
//	%+S              elsif STEQ(s,"S?BOBY")    then kind:=P_APX_BOBY
//	%+S              elsif STEQ(s,"S?BYBO")    then kind:=P_APX_BYBO
//	%+S              elsif STEQ(s,"S?SZ2W")    then kind:=P_APX_SZ2W
//	%+S              elsif STEQ(s,"S?W2SZ")    then kind:=P_APX_W2SZ
//	%+S              elsif STEQ(s,"S?RF2N")    then kind:=P_APX_RF2N
//	%+S              elsif STEQ(s,"S?N2RF")    then kind:=P_APX_N2RF
//	%+S              elsif STEQ(s,"S?BNOT")    then kind:=P_APX_BNOT
//	%+S              elsif STEQ(s,"S?BAND")    then kind:=P_APX_BAND
//	%+S              elsif STEQ(s,"S?BOR")     then kind:=P_APX_BOR
//	%+S              elsif STEQ(s,"S?BXOR")    then kind:=P_APX_BXOR
//	%+S              elsif STEQ(s,"S?WNOT")    then kind:=P_APX_WNOT
//	%+S              elsif STEQ(s,"S?WAND")    then kind:=P_APX_WAND
//	%+S              elsif STEQ(s,"S?WOR")     then kind:=P_APX_WOR
//	%+S              elsif STEQ(s,"S?WXOR")    then kind:=P_APX_WXOR
//	%+S              elsif STEQ(s,"S?BSHL")    then kind:=P_APX_BSHL
//	%+S              elsif STEQ(s,"S?WSHL")    then kind:=P_APX_WSHL
//	%+S              elsif STEQ(s,"S?BSHR")    then kind:=P_APX_BSHR
//	%+S              elsif STEQ(s,"S?WSHR")    then kind:=P_APX_WSHR
//	%+S              elsif STEQ(s,"M?SVDM")    then kind:=P_DOS_SDMODE
//	%+S              elsif STEQ(s,"M?UPOS")    then kind:=P_DOS_UPDPOS
//	%+S              elsif STEQ(s,"M?CURS")    then kind:=P_DOS_CURSOR
//	%+S              elsif STEQ(s,"M?SDPG")    then kind:=P_DOS_SDPAGE
//	%+S              elsif STEQ(s,"M?SRUP")    then kind:=P_DOS_SROLUP
//	%+S              elsif STEQ(s,"M?SRDW")    then kind:=P_DOS_SROLDW
//	%+S              elsif STEQ(s,"M?GETC")    then kind:=P_DOS_GETCEL
//	%+S              elsif STEQ(s,"M?PUTC")    then kind:=P_DOS_PUTCHR
//	%+S              elsif STEQ(s,"M?GVDM")    then kind:=P_DOS_GDMODE
//	%+S              elsif STEQ(s,"M?SPAL")    then kind:=P_DOS_SETPAL
//	%+S              elsif STEQ(s,"M?RCHK")    then kind:=P_DOS_RDCHK
//	%+S              elsif STEQ(s,"M?KEYI")    then kind:=P_DOS_KEYIN
//	%+S              endif;
//	%+S         endif;
//	      endif;
//	      npar:=0; fpar:=none;
//	      repeat InputInstr while CurInstr=S_IMPORT
//	      do InTag(%ptag%); type:=intype;
//	%+E      if    type=T_BYT1 then type:=T_WRD4 --- NY: FOR TEST ?????
//	%+E      elsif type=T_BYT2 then type:=T_WRD4
//	%+E      elsif type=T_WRD2 then type:=T_WRD4 endif;
//	         if NextByte = S_REP
//	         then inputInstr;
//	%+D           count:=InputNumber;
//	%-D           InNumber(%count%);
//	%+C           if count.val=0
//	%+C           then IERR("Illegal 'REP 0'"); count.val:=1 endif;
//	         else count.val:=1 endif;
//	         v:=NEWOBJ(K_Import,size(LocDescr)); v.type:=type;
//	         if npar=0 then fpar:=v else w.nextag:=ptag endif;
//	         ----  PREPARE GADDR PARAM TO C-routine  ----           -- pje
//	         if (kind=P_C) or (kind=P_PASCAL) then if type=T_GADDR  -- pje
//	         then type:=T_OADDR endif endif;                        -- pje
//	         lng:=wAllign(%TTAB(  type).nbyte%);                    -- pje
//	---      lng:=wAllign(%TTAB(v.type).nbyte%);
//	%+C      if lng=0 then IERR("Illegal Type on Parameter") endif;
//	         nbyte:=nbyte+lng; v.rela:=nbyte;
//	         if count.val > 1
//	         then
//	              nbyte:=nbyte+(count.val*lng)-lng;
//	%+C           if nCnt >= 4
//	%+C           then IERR("MINUT: Too many rep-params"); nCnt:=3 endif;
//	%+C           if count.val>255 then IERR("MINUT: too large count") endif;
//	              Pno(nCnt):=npar; Cnt(nCnt):=count.val; nCnt:=nCnt+1;
//	         endif;
//	         npar:=npar+1; w:=v; IntoDisplay(v,ptag);
//	      endrepeat;
//
//	      wxt:=0;
//	      if CurInstr=S_EXIT
//	      then v:=NEWOBJ(K_Import,size(LocDescr)); v.type:=T_PADDR;
//	           v.rela:=AllignFac; -- Offset of return address in stack head
//	           InTag(%ptag%); wxt:=qEXIT; IntoDisplay(v,ptag);
//	           InputInstr;
//	      elsif CurInstr=S_EXPORT
//	      then InTag(%ExpTag%); ExpType:=intype;
//	%+E        if    ExpType=T_BYT1 then ExpType:=T_WRD4 --- NY: FOR TEST ?????
//	%+E        elsif ExpType=T_BYT2 then ExpType:=T_WRD4
//	%+E        elsif ExpType=T_WRD2 then ExpType:=T_WRD4 endif;
//	           v:=NEWOBJ(K_Export,size(LocDescr)); v.type:=ExpType;
//	           v.rela:=(4+AllignFac)+nbyte; IntoDisplay(v,ExpTag);
//	           InputInstr;
//	      endif;
//
//	      if CurInstr <> S_ENDPROFILE then IERR("Missing ENDPROFILE") endif;
//
//	      pr:=NEWOBJ(K_ProfileDescr,size(ProfileDescr:npar));
//	      pr.npar:=npar; pr.Pkind:=kind; pr.type:=ExpType;
//	      pr.nparbyte:=nbyte; pr.WithExit:=wxt;
//	      --- Increment relative addresses by size of import block ---
//	      --- and fill Profile's Parameter - Specifications        ---
//	      v:=fpar; i:=0;
//	      repeat while i < npar
//	      do v.rela:=((4+AllignFac)+nbyte)-v.rela; pr.par(i).type:=v.type;
//	         pr.par(i).count:=1;
//	         v:=DISPL(v.nextag.HI).elt(v.nextag.LO); i:=i+1;
//	      endrepeat;
//	      repeat while nCnt <> 0
//	      do nCnt:=nCnt-1; pr.par(Pno(nCnt)).count:=Cnt(nCnt) endrepeat;
//	      IntoDisplay(pr,tag);
//	      if rut<>none then rut.type:=ExpType; IntoDisplay(rut,rtag) endif;
//	end;
//	%title ***   I n p u t    M o d u l e   ***

	public static void inputModule(boolean sysmod) {
//		Util.IERR("Parse.inputModule: NOT IMPLEMENTED");
//	 begin infix(WORD) modid,check,bias,limit,LinTab,AtrElt,AtrNam,RelElt;
//	       infix(WORD) dx,dxlim,tg,i,smbx,segid,extid;
//	       range(0:MaxByte) n,clas,segtype; ref(ModElt) me;
//	       infix(DataType) dt; range(0:MaxType) tx;
//	       ref(ProfileDescr) prf; range(0:MaxByte) npar;
//	       ref(Descriptor) d; Boolean TagMapping;
//	       infix(String) action,xid,s,buf; range(0:MaxWord) dlng,Magic;
//
	       String modid = Scode.inString();
	       String check = Scode.inString();
	       String extid = Scode.inString();
//	       InTag(%bias%); InTag(%limit%);
	       int bias = Scode.inTag();
	       int limit = Scode.inTag();
//
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("******************   Begin  -  Input-module  ");
//	%+D         outsymb(modid); outstring("  "); outsymb(check);
//	%+D         outstring("   ******************"); outimage;
//	%+D    endif;
//	       if sysmod and (CombAtr > 0) then goto E1 endif;
//
//	       ------ Create and open modinpt (inbytefile=5) ------
//	%+S    if envpar
//	%+S    then
//	            edsymb(sysedit,modid);
//	            EnvGiveTextInfo(2,pickup(sysedit));
//	            if status<>0 then IERR("MINUT.InputModule-1") endif;
//	            EdSymb(sysedit,extid); xid:=pickup(sysedit)
//	            if (xid.nchr=1)
//	            then if (var(xid.chradr)='?') then xid:=nostring endif endif;
//	            EnvGiveTextInfo(3,xid);
//	            if status<>0 then IERR("MINUT.InputModule-1b") endif;
//	            if sysmod then edtextinfo(sysedit,14)
//	            else edtextinfo(sysedit,12); endif;
//
//	%+S    else if ATTRID.val <> 0 then EdSymb(sysedit,ATTRID) endif;
//	%+S         edsymb(sysedit,modid); ed(sysedit,".at2");
//	%+S    endif;
//	       s:=pickup(sysedit); AtrElt:=DefSymb(s);
//	       action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!";  -- in(byte)file
//	       modinpt:=Open(s,5,action,0);
//	       SetPos(sysedit,80); s:=pickup(sysedit);
//	       s.nchr:=EnvDsetName(modinpt,s); AtrNam:=DefSymb(s);
//	%+D    if TraceMode <> 0
//	%+D    then outstring("ATTRIBUTE INPUT Elt: "); outsymb(AtrElt); outimage;
//	%+D         outstring("                Nam: "); outsymb(AtrNam); outimage;
//	%+D    endif;
//	       ------ Read Module Header ------
//	       EnvLocate(modinpt,1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Module Header *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       buf:=Name2Buf(@modlab,size(ModuleHeader));
//	       EnvInBytes(modinpt,buf);
//	       if status<>0 then FILERR(modoupt,"MINUT.Rsmb-3") endif;
//	%+E    if Size2Word(size(ProfileDescr:2))=12 then Magic:=3327
//	%+E    else Magic:=3227; -- HYBRID Attribute file ---
//	%+ED        if Size2Word(size(ProfileDescr:2))<>10
//	%+ED        then EdWrd(errmsg,Size2Word(size(ProfileDescr:2)));
//	%+ED             IERR(" MINUT:Magic-1");
//	%+ED        endif;
//	%+E    endif;
//	%+E    if modlab.Magic=3227
//	%+E    then WARNING("HYBRID Attribute input file") endif;
//	%-E    if (modlab.Magic <> 3127)  or (modlab.Layout <> 2)
//	%+E    if (modlab.Magic <> Magic) or (modlab.Layout <> 2)
//	       then ed(errmsg,"Illegal Attribute File: ");
//	            edsymb(errmsg,modid); ed(errmsg,", Magic:");
//	            edwrd(errmsg,modlab.magic); WARNING(" ");
//	       endif;
//	       if sysmod then CombAtr:=modlab.Comb endif;
//
//	       ------ Read Symbol Table ------
//	       EnvLocate(modinpt,modlab.SymbLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Symbol Table *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       s.chradr:=@sysedit.chr; i.val:=0;
//	       repeat while i.val < modlab.nSymb
//	       do s.nchr:=EnvInByte(modinpt);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-1") endif;
//	          clas:=EnvInByte(modinpt);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-2") endif;
//	          EnvInBytes(modinpt,s);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-3") endif;
//	          case 0:sMAX (clas)
//	          when sSYMB: smbx:=DefSymb(s)
//	          when sMODL: LinTab.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      RelElt.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      smbx:=DefModl(s); me:=DICREF(smbx);
//	                      me.LinTab:=ChgInSmb(LinTab);
//	                      me.RelElt:=ChgInSmb(RelElt);
//	                      me.AtrElt:=AtrElt;
//	                      me.AtrNam:=AtrNam;
//	%+S                   if SYSGEN=0 then PutModule(smbx) endif;
//	%-S                   PutModule(smbx);
//	          when sSEGM: segtype:=EnvInByte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-4") endif;
//	                      smbx:=DefSegm(s,segtype)
//	          when sEXTR: segid.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      smbx:=DefExtr(s,ChgInSmb(segid))
//	%+C       otherwise IERR("MINUT.Rsmb-6")
//	          endcase;
//	          if SMBMAP(i.HI)=none
//	          then SMBMAP(i.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	          SMBMAP(i.HI).elt(i.LO):=smbx; i.val:=i.val+1;
//	%+D       if ModuleTrace <> 0
//	%+D       then outstring("DEFSYMB "); outword(i.val);
//	%+D            outchar(' '); outword(smbx.val);
//	%+D            outchar(' '); outsymb(smbx); OutImage;
//	%+D       endif;
//	       endrepeat;
//
//	       ------ Update Module Header and Test ------
//	       modlab.modid:=ChgInSmb(modlab.modid);
//	       modlab.check:=ChgInSmb(modlab.check);
//	       if sysmod and (CombAtr>0)
//	       then limit.val:=bias.val+modlab.nXtag
//	       else if modlab.modid <> modid
//	            then ERROR("Wrong identification in module") endif;
//	            if modlab.check <> check
//	            then ERROR("Wrong check code in module") endif;
//	            if limit.val-bias.val <> modlab.nXtag
//	            then ERROR("Wrong no.of visible items in module") endif;
//	       endif;
//	       ------ Read Type Table ------
//	       EnvLocate(modinpt,modlab.TypeLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Type Table *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < modlab.nType
//	       do EnvInbytes(modinpt,Name2Buf(@dt,size(DataType)));
//	          if status<>0 then FILERR(modoupt,"MINUT.Rtyp-1") endif;
//	          dt.pntmap:=ChgInSmb(dt.pntmap); tx:=ntype;
//	          repeat while tx > T_max
//	          do if dt=TTAB(tx) then goto T1 endif; tx:=tx-1 endrepeat;
//	          ntype:=ntype+1;
//	%+C       if ntype >= MaxType
//	%+C       then IERR("Too many types"); ntype:=MaxType/2 endif;
//	          TTAB(ntype):=dt; tx:=ntype;
//	     T1:  if TYPMAP(i.HI)=none
//	          then TYPMAP(i.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	%+C       if i.hi >= MxpXtyp then IERR("DEFTYPE: TYPMAP overflow"); endif;
//	          TYPMAP(i.HI).elt(i.LO).val:=tx; i.val:=i.val+1;
//	%+D       if ModuleTrace <> 0
//	%+D       then outstring("DEFTYPE "); outword(T_max+i.val);
//	%+D            outchar(' '); outword(tx);
//	%+D            outchar(' '); EdType(sysout,tx); OutImage;
//	%+D       endif;
//	       endrepeat;
//
//	       ------ Read Tag-Mapping Table ------
//	       if modlab.nTmap=0 then TagMapping:=false
//	       else TagMapping:=true;
//	            EnvLocate(modinpt,modlab.TmapLoc+1); i.val:=0;
//	%+D         if ModuleTrace <> 0
//	%+D         then outstring("*** Read Tag-Mapping Table *** LOC = ");
//	%+D              outint(EnvLocation(modinpt));
//	%+D              outstring(", LNG = ");
//	%+D              outword(modlab.nTmap); OutImage;
//	%+D         endif;
//	            repeat while i.val < modlab.nTmap
//	            do tg.val:=EnvIn2Byte(modinpt);
//	               if status<>0 then FILERR(modinpt,"MINUT.Rmap-1") endif;
//	               if TAGTAB(i.HI)=none
//	               then TAGTAB(i.HI):=NEWOBZ(size(WordBlock)) endif;
//	               TAGTAB(i.HI).elt(i.LO):=tg;
//	               i.val:=i.val+1;
//	            endrepeat;
//	       endif;
//
//	       ------ Read Tag-Ident Table ------
//	%+SD   if (BECDEB <> 0) and (modlab.TgidLoc > 0)
//	%+SD   then EnvLocate(modinpt,modlab.TgidLoc+1); i:=bias;
//	%+SD        if ModuleTrace <> 0
//	%+SD        then outstring("*** Read Tag-Ident Table *** LOC = ");
//	%+SD             outint(EnvLocation(modinpt)); OutImage;
//	%+SD        endif;
//	%+SD        repeat while i.val < limit.val
//	%+SD        do smbx.val:=EnvIn2Byte(modinpt);
//	%+SD           if status<>0 then FILERR(modinpt,"MINUT.Rtid-1") endif;
//	%+SD           if TIDTAB(i.HI)=none
//	%+SD           then TIDTAB(i.HI):=NEWOBZ(size(WordBlock)) endif;
//	%+SD           TIDTAB(i.HI).elt(i.LO):=ChgInSmb(smbx);
//	%+SD           i.val:=i.val+1;
//	%+SD        endrepeat;
//	%+SD   endif;
//
//	       ------ Read Descriptors ------
//	       EnvLocate(modinpt,modlab.DescLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Descriptors *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); outstring(", LNG = ");
//	%+D         outword(modlab.sDesc); OutImage;
//	%+D    endif;
//	       d:=NEWOBX(Word2Size(modlab.sDesc));
//	       buf.chradr:=Ref2Name(d); buf.nchr:=modlab.sDesc;
//	       EnvInBytes(modinpt,buf);
//	       if status<>0 then FILERR(modinpt,"MINUT.Rdsc-1") endif;
//
//	       --- Perform Relocations ---
//	       if TagMapping then dx.val:=0; dxlim.val:=modlab.nTmap-1
//	       else dx:=bias; dxlim:=limit endif;
//	       repeat while dx.val <= dxlim.val
//	       do d.type:=ChgInType(d.type);
//	          case 0:K_Max (d.kind)
//	          when K_ExternVar,K_ExtLabel,K_ExtRoutine:
//	               d qua ExtDescr.adr.smbx:=
//	                     ChgInSmb(d qua ExtDescr.adr.smbx);
//	               dlng:=Size2Word(size(ExtDescr))
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_ExternVar  then S:="ExternVar: "
//	%+D              elsif d.kind=K_ExtRoutine then S:="ExtRoutine: "
//	%+D              else S:="ExtLabel: " endif; outstring(S); Print(d);
//	%+D            endif;
//	          when K_RecordDescr:
//	               dlng:=Size2Word(size(RecordDescr))
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("RecordDescr: "); Print(d) endif;
//	          when K_TypeRecord:
//	               d qua TypeRecord.pntmap:=
//	                     ChgInSmb(d qua TypeRecord.pntmap);
//	               dlng:=Size2Word(size(TypeRecord))
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("TypeRecord: "); Print(d) endif;
//	          when K_ProfileDescr:
//	               prf:=d; npar:=prf.npar; dlng:=Size2Word(size(ProfileDescr:npar))
//	               dlng:=((dlng+3)/4)*4; -- To force 4-byte allignment
//	               repeat while npar <> 0
//	               do npar:=npar-1;
//	                  prf.par(npar).type:=ChgInType(prf.par(npar).type);
//	               endrepeat;
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("ProfileDescr: "); Print(d) endif;
//	          when K_Attribute,K_Import,K_Export:
//	               dlng:=Size2Word(size(LocDescr))
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_Attribute then S:="Attribute: "
//	%+D              elsif d.kind=K_Import then S:="Parameter: "
//	%+D              else S:="Export: " endif; outstring(S); Print(d);
//	%+D            endif;
//	          otherwise ERROR("Unknown Descriptor in AttrFile");
//	%+D                 outword(d.kind); Print(d);
//	          endcase;
//	          if TagMapping
//	          then tg.val:=TAGTAB(dx.HI).elt(dx.LO).val+bias.val;
//	               IntoDisplay(d,tg);
//	          else IntoDisplay(d,dx) endif;
//	          d:=d+Word2Size(dlng); dx.val:=dx.val+1;
//	       endrepeat;
//
//	       EnvClose(modinpt,nostring);
//	       if status<>0 then IERR("MINUT.InputModule-3") endif; modinpt:=0;
//
//	       ------ Release TAGTAB, SMBMAP & TYPMAP ------
//	       if TagMapping
//	       then i.val:=modlab.nTmap; n:=i.HI;
//	            repeat DELETE(TAGTAB(n)); TAGTAB(n):=none
//	            while n<>0 do n:=n-1 endrepeat;
//	       endif;
//	       i.val:=modlab.nSymb; n:=i.HI;
//	       repeat DELETE(SMBMAP(n)); SMBMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nType; n:=i.HI;
//	       repeat DELETE(TYPMAP(n)); TYPMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	E1:
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("******************   End of  -  Input-module  ");
//	%+D         outsymb(modid); outstring("  "); outsymb(check);
//	%+D         outstring("   ******************"); outimage;
//	%+D    endif;
	}
//	%page
//
//	Routine ChgInSmb; import infix(WORD) smbx; export infix(WORD) chgx;
//	begin infix(WORD) n;
//	      if smbx.val=0 then chgx.val:=0
//	      else n.val:=smbx.val-1;
//	           if SMBMAP(n.HI)=none
//	           then IERR("ChgInSmb-1"); chgx.val:=0;
//	           else chgx:=SMBMAP(n.HI).elt(n.LO) endif;
//	      endif;
//	%+D   if ModuleTrace <> 0
//	%+D   then outstring("ChgInSmb "); outword(smbx.val);
//	%+D        outchar(' '); outsymb(chgx); OutImage;
//	%+D   endif;
//	end;
//
//	Routine ChgInType;
//	import range(0:MaxType) tx; export range(0:MaxType) t;
//	begin infix(WORD) n;
//	      if tx <= T_max then t:=tx
//	      else n.val:=tx-(T_max+1);
//	           if TYPMAP(n.HI)=none then IERR("ChgInType-1"); t:=0;
//	           else t:=TYPMAP(n.HI).elt(n.LO).val endif;
//	      endif;
//	%+D   if ModuleTrace <> 0
//	%+D   then outstring("ChgInType "); outword(tx);
//	%+D        outchar(' '); EdType(sysout,t); OutImage;
//	%+D   endif;
//	end;
//
//	%title ***   O u t p u t    M o d u l e   ***
//
//	 Visible Routine OutputModule; import range(0:MaxWord) nXtag;
//	 begin infix(WORD) i,sx,tx,px;
//	       ref(Descriptor) d; infix(DataType) dt;
//	       ref(SmbElt) smb; infix(string) buf;
//	       infix(ExtDescr) xd; infix(LocDescr) ld; infix(TypeRecord) rd;
//	       ref(ProfileDescr) prf; infix(ParamSpec) par;
//	       range(0:MaxType) type; range(0:MaxByte) n,npar;
//	       infix(String) action,S;
//
//	%+D    if (ModuleTrace <> 0) or (TLIST > 2)
//	%+D    then outstring("**************   Begin  -  Output-module  ");
//	%+D         outsymb(modident); outstring("  ");
//	%+D         outsymb(modcheck);
//	%+D         outstring("   **************"); outimage;
//	%+D    endif;
//
//	       ------ Create and open modoupt (outbytefile=6) ------
//	%+S    if envpar
//	%+S    then
//	            edsymb(sysedit,modident);
//	            EnvGiveTextInfo(1,pickup(sysedit));
//	            if status<>0 then IERR("MINUT.OutputModule-1") endif;
//	            edtextinfo(sysedit,11);
//	%+S    else if ATTRID.val <> 0 then EdSymb(sysedit,ATTRID) endif;
//	%+S         edsymb(sysedit,modident); ed(sysedit,".AT2");
//	%+S    endif;
//	       S:=pickup(sysedit);
//	%+D    if TLIST>1
//	%+D    then outstring("ATTRIBUTE FILE OUTPUT: ");
//	%+D         outstring(S); outimage;
//	%+D    endif;
//	       action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!";  -- out(byte)file
//	       modoupt:=Open(S,6,action,0);
//
//	       ------ Initiate Module Label ------
//	%-E    modlab.Magic:=3127;
//	%+E    if Size2Word(size(ProfileDescr:2))=12 then modlab.Magic:=3327
//	%+E    else modlab.Magic:=3227; WARNING("HYBRID Attribute output file");
//	%+ED        if Size2Word(size(ProfileDescr:2))<>10
//	%+ED        then EdWrd(errmsg,Size2Word(size(ProfileDescr:2)));
//	%+ED             IERR(" MINUT:Magic-2");
//	%+ED        endif;
//	%+E    endif;
//	       modlab.Layout:=2; modlab.Comb:=CombAtr;
//	       modlab.modid:=ChgSmb(modident);
//	       modlab.check:=ChgSmb(modcheck);
//	       modlab.nXtag:=nXtag; nXtag:=nXtag+1;
//	       modlab.sFeca:=0; modlab.FecaLoc:=0;
//	       modlab.nTmap:=0; modlab.TmapLoc:=0;
//
//	       ------ Prepare Dependent Module info ------
//	       i.val:=0;
//	       repeat while i.val < DIC.nModl
//	       do i.val:=i.val+1;
//	          ChgSmb(DIC.Modl(i.HI).elt(i.LO));
//	       endrepeat;
//
//	       ------ Modify and Write Descriptors ------
//	       EnvLocate(modoupt,Size2Word(size(ModuleHeader))+1);
//	       modlab.DescLoc:=EnvLocation(modoupt)-1;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Modify and Write Descriptors *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < nXtag
//	       do tx:=TAGTAB(i.HI).elt(i.LO); i.val:=i.val+1;
//	          d:=DISPL(tx.HI).elt(tx.LO);
//	          if d = none
//	          then Ed(errmsg,"External tag "); EdWrd(errmsg,i.val-1);
//	               Ed(errmsg," = Tag "); EdWrd(errmsg,tx.val);
//	               IERR(" is not defined (OutModule)")
//	               d:=NEWOBJ(K_ExternVar,size(ExtDescr));
//	          endif;
//	          case 0:K_Max (d.kind)
//	          when K_ExternVar,K_ExtLabel,K_ExtRoutine:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_ExternVar  then S:="ExternVar: "
//	%+D              elsif d.kind=K_ExtRoutine then S:="ExtRoutine:"
//	%+D              else S:="ExtLabel: " endif; outstring(S); Print(d)
//	%+D            endif;
//	               -- Modify: d qua ExtDescr.type
//	               xd.kind:=d.kind;
//	               xd.type:=ChgType(d.type);
//	               xd.adr:=d qua ExtDescr.adr;
//	               xd.adr.smbx:=ChgSmb(xd.adr.smbx);
//	               buf.nchr:=Size2Word(size(ExtDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-1") endif;
//	               buf.chradr:=@xd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-1") endif;
//	          when K_GlobalVar,K_IntLabel,K_IntRoutine:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_GlobalVar  then S:="GlobalVar: "
//	%+D              elsif d.kind=K_IntRoutine then S:="IntRoutine:"
//	%+D              else S:="IntLabel: " endif; outstring(S); Print(d)
//	%+D            endif;
//	               if d qua IntDescr.adr.kind=0  -- No address attached
//	               then sx.val:=0;
//	                    d qua IntDescr.adr:=NewFixAdr(DSEGID,sx);
//	               endif;
//	               -- Modify: d qua IntDescr.type
//	               -- Modify: d qua IntDescr's address
//	               if    d.kind=K_GlobalVar  then xd.kind:=K_ExternVar
//	               elsif d.kind=K_IntRoutine then xd.kind:=K_ExtRoutine
//	               else xd.kind:=K_ExtLabel endif;
//	               xd.type:=ChgType(d.type);
//	               xd.adr:=ChgAdr(d qua IntDescr.adr);
//	               buf.nchr:=Size2Word(size(ExtDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-2") endif;
//	               buf.chradr:=@xd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-2") endif;
//	          when K_RecordDescr:
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("RecordDescr: "); Print(d) endif;
//	               rd.kind:=K_RecordDescr; rd.type:=ChgType(d.type);
//	               rd.nbyte:=d qua RecordDescr.nbyte;
//	               rd.nbrep:=d qua RecordDescr.nbrep;
//	               buf.nchr:=Size2Word(size(RecordDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-3") endif;
//	               buf.chradr:=@rd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-3") endif;
//	          when K_TypeRecord:
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("TypeRecord: "); Print(d) endif;
//	               rd.kind:=K_TypeRecord; rd.type:=ChgType(d.type);
//	               rd.nbyte:=d qua RecordDescr.nbyte;
//	               rd.nbrep:=d qua RecordDescr.nbrep;
//	               rd.pntmap:=ChgSmb(d qua TypeRecord.pntmap);
//	               buf.nchr:=Size2Word(size(TypeRecord))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.chradr:=@rd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-4") endif;
//	          when K_ProfileDescr:
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("ProfileDescr: "); Print(d) endif;
//	               prf:=d; type:=d.type; d.type:=ChgType(type);
//	%+D            buf.nchr:=Size2Word(size(ProfileDescr:0))
//	%+D            if buf.nchr=6 then -- OK
//	%+D            elsif buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.nchr:=6; --- TO FORCE RIGTH FORMAT OF PROFILEDESCR
//	               buf.chradr:=ref2name(prf); EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-5") endif;
//	               prf.type:=type; npar:=prf.npar px.val:=0;
//	               repeat while px.val < npar
//	               do par:=prf.par(px.val); px.val:=px.val+1;
//	                  EnvOutByte(modoupt,ChgType(par.type));
//	                  if status<>0 then FILERR(modoupt,"Wdescr-6") endif
//	                  EnvOutByte(modoupt,par.count);
//	                  if status<>0 then FILERR(modoupt,"Wdescr-7") endif
//	               endrepeat;
//	               if bAND(npar,1)=0  -- FORCE 4-BYTE ALLIGNMENT OF PROFILES
//	               then EnvOutByte(modoupt,0);
//	                    if status<>0 then FILERR(modoupt,"Wdescr-6x") endif
//	                    EnvOutByte(modoupt,0);
//	                    if status<>0 then FILERR(modoupt,"Wdescr-7x") endif
//	               endif;
//	          when K_Attribute,K_Import,K_Export:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_Attribute then S:="Attribute: "
//	%+D              elsif d.kind=K_Import then S:="Parameter: "
//	%+D              else S:="Export: " endif; outstring(S); Print(d);
//	%+D            endif;
//	               -- Modify: d qua IntDescr.type
//	               ld.kind:=d.kind; ld.type:=ChgType(d.type);
//	               ld.rela:=d qua LocDescr.rela; ld.nextag.val:=0;
//	               buf.nchr:=Size2Word(size(LocDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.chradr:=@ld; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-8") endif;
//	          otherwise ERROR("Unknown Descriptor in display");
//	%+D                 outword(d.kind); Print(d);
//	          endcase;
//	       endrepeat;
//	       modlab.sDesc:=(EnvLocation(modoupt)-1)-modlab.DescLoc;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** End Descriptors *** LNG = ");
//	%+D         outword(modlab.sDesc); OutImage;
//	%+D    endif;
//
//	       ------ Write Type Table ------
//	       modlab.nType:=nXtyp;
//	       modlab.Typeloc:=EnvLocation(modoupt)-1;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Type Table *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < nXtyp
//	       do tx:=TYPMAP(i.HI).elt(i.LO);
//	          dt:=TTAB(tx.val); i.val:=i.val+1;
//	          if dt.pntmap.val <> 0 then dt.pntmap:=ChgSmb(dt.pntmap) endif;
//	          EnvOutbytes(modoupt,Name2Buf(@dt,size(DataType)));
//	          if status<>0 then FILERR(modoupt,"Wtyp-1") endif;
//	       endrepeat;
//
//	       ------ Write Tag-ident Table ------
//	       modlab.TgidLoc:=0;
//	%+SD   if BECDEB > 1
//	%+SD   then i.val:=0; modlab.TgidLoc:=EnvLocation(modoupt)-1;
//	%+SD        if ModuleTrace <> 0
//	%+SD        then outstring("*** Write Tag-ident Table *** LOC = ");
//	%+SD             outint(EnvLocation(modoupt)); OutImage;
//	%+SD        endif;
//	%+SD        repeat while i.val < nXtag
//	%+SD        do tx:=TAGTAB(i.HI).elt(i.LO); i.val:=i.val+1;
//	%+SD           d:=DISPL(tx.HI).elt(tx.LO);
//	%+SD           if d=none then IERR("MINUT.OutputModule - 1") endif;
//	%+SD           sx:=TIDTAB(tx.HI).elt(tx.LO); sx:=ChgSmb(sx);
//	%+SD           EnvOut2byte(modoupt,sx.val);
//	%+SD           if status<>0 then FILERR(modoupt,"MINUT.OutWord") endif;
//	%+SD        endrepeat;
//	%+SD   endif;
//
//	       ------ Write Symbol Table ------
//	       modlab.SymbLoc:=EnvLocation(modoupt)-1; modlab.nSymb:=nXsmb;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Symbol Table *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < nXsmb
//	       do smb:=DICREF(SMBMAP(i.HI).elt(i.LO)); i.val:=i.val+1;
//	          EnvOutByte(modoupt,smb.nchr); buf.nchr:=smb.nchr;
//	          if status<>0 then FILERR(modoupt,"MINUT.Wsmb-1") endif;
//	          case 0:sMax (smb.clas)
//	          when sSYMB: EnvOutByte(modoupt,sSYMB);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-2") endif;
//	               buf.chradr:=name(smb qua Symbol.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-3") endif;
//	          when sMODL: EnvOutByte(modoupt,sMODL);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-4") endif;
//	               buf.chradr:=name(smb qua ModElt.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-5") endif;
//	               sx:=ChgSmb(smb qua ModElt.LinTab);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-6") endif;
//	               sx:=ChgSmb(smb qua ModElt.RelElt);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-7") endif;
//	          when sSEGM: EnvOutByte(modoupt,sSEGM);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-8") endif;
//	               buf.chradr:=name(smb qua Segment.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-9") endif;
//	               EnvOutByte(modoupt,smb qua Segment.type);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-10") endif;
//	          when sPUBL: EnvOutByte(modoupt,sEXTR);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-11") endif;
//	               buf.chradr:=name(smb qua Public.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-12") endif;
//	               sx:=smb qua Public.segx;
//	               sx:=ChgSmb(DIC.Segm(sx.HI).elt(sx.LO));
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-13") endif;
//	          when sEXTR: EnvOutByte(modoupt,sEXTR);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-14") endif;
//	               buf.chradr:=name(smb qua Extern.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-15") endif;
//	               sx:=ChgSmb(smb qua Extern.segid);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-16") endif;
//	%+C       Otherwise IERR("MINUT.Wsmb-3");
//	          endcase;
//	       endrepeat;
//
//	       ------ Write Module Header and Close file ------
//	       EnvLocate(modoupt,1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Module Header *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       buf:=Name2Buf(@modlab,size(ModuleHeader));
//	       EnvOutBytes(modoupt,buf);
//	       if status<>0 then FILERR(modoupt,"MINUT.Wsmb-3") endif;
//	       EnvClose(modoupt,nostring);
//	       if status<>0 then IERR("MINUT.OutputModule-4") endif; modoupt:=0;
//
//	       ------ Release TAGTAB, SMBMAP and TYPMAP ------
//	       i.val:=nXtag; n:=i.HI;
//	       repeat DELETE(TAGTAB(n)); TAGTAB(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nSymb; n:=i.HI;
//	       repeat DELETE(SMBMAP(n)); SMBMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nType; n:=i.HI;
//	       repeat DELETE(TYPMAP(n)); TYPMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("**************   End of  -  Output-module  ");
//	%+D         outsymb(modident); outstring("  ");
//	%+D         outsymb(modcheck);
//	%+D         outstring("   **************"); outimage;
//	%+D    endif;
//	 end;
//	%page
//	Routine ChgAdr; import infix(MemAddr) a; export infix(ExtRef) x;
//	begin infix(Fixup) Fx; x.rela:=a.rela.val;
//	      case 0:adrMax (a.kind)
//	      when segadr: if a.segmid <> DSEGID then IERR("ChgAdr-1") endif;
//	                   x.smbx:=ChgSmb(DsegEntry);
//	      when extadr: x.smbx:=ChgSmb(a.smbx)
//	      when fixadr: Fx:=FIXTAB(a.fix.HI).elt(a.fix.LO);
//	                   if Fx.smbx.val=0
//	                   then Fx.smbx:=NewPubl;
//	                        FIXTAB(a.fix.HI).elt(a.fix.LO).smbx:=Fx.smbx;
//	                   endif;
//	                   DICREF(Fx.smbx) qua Public.segx:=PutSegx(Fx.segid);
//	                   x.smbx:=ChgSmb(Fx.smbx);
//	%+D   otherwise IERR("ChgAdr-2");
//	      endcase;
//	end;
//
//	Routine ChgSmb; import infix(WORD) smbx; export infix(WORD) chgx;
//	begin infix(WORD) n,sx; ref(SmbElt) s;
//	      if smbx.val=0 then chgx.val:=0
//	      else n.val:=nXsmb;
//	           repeat while n.val <> 0
//	           do n.val:=n.val-1;
//	              if SMBMAP(n.HI).elt(n.LO)=smbx then goto E endif;
//	           endrepeat;
//	           --- Ensure that local symbols are defined before this ---
//	           s:=DICREF(smbx);
//	           case 0:sMAX (s.clas)
//	           when sSYMB: --- Nothing
//	           when sMODL: ChgSmb(s qua ModElt.LinTab)
//	                       ChgSmb(s qua ModElt.RelElt)
//	           when sSEGM: --- Nothing
//	           when sPUBL: sx:=s qua Public.segx;
//	                       ChgSmb(DIC.Segm(sx.HI).elt(sx.LO));
//	           when sEXTR: ChgSmb(s qua Extern.segid)
//	           endcase;
//	           n.val:=nXsmb; nXsmb:=n.val+1;
//	           if SMBMAP(n.HI)=none
//	           then SMBMAP(n.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	           SMBMAP(n.HI).elt(n.LO):=smbx;
//	      E:   chgx.val:=n.val+1;
//	      endif;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("CHGSMB "); outword(chgx.val);
//	%+D         outchar(' '); outsymb(smbx); OutImage;
//	%+D    endif;
//	end;
//
//	Routine ChgType; import range(0:MaxType) t; export range(0:MaxType) tx;
//	begin infix(WORD) n;
//	      if t <= T_max then tx:=t
//	      else n.val:=nXtyp;
//	           repeat while n.val <> 0
//	           do n.val:=n.val-1;
//	              if TYPMAP(n.HI).elt(n.LO).val=t then goto E endif;
//	           endrepeat;
//	           n.val:=nXtyp; nXtyp:=n.val+1;
//	%+C        if n.hi >= MxpXtyp then IERR("ChgTyp: TYPMAP overflow"); endif;
//	           if TYPMAP(n.HI)=none
//	           then TYPMAP(n.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	           TYPMAP(n.HI).elt(n.LO).val:=t;
//	      E:   tx:=n.val+T_max+1;
//	      endif;
//	%+D   if ModuleTrace <> 0
//	%+D   then outstring("CHGTYP "); outword(tx);
//	%+D        outchar(' '); EdType(sysout,t); OutImage;
//	%+D   endif;
//	end;
//
//	%title ***   C o m b i n e    A t t r i b u t e    F i l e s   ***
//
//	%+S Visible Routine Combine; 
//	%+S begin infix(WORD) itag,nXtag,LastTag;
//	%+S       outstring("*** Combine Attribute-Files ***"); outimage;
//	%+S       CombAtr:=1;
//	%+S       repeat while (CurInstr=S_INSERT) or (CurInstr=S_SYSINSERT)
//	%+S       do InputModule(false); InputInstr endrepeat;
//	%+S       --- Keep all descriptors ---
//	%+S       itag.val:=MinTag; nXtag.val:=0; LastTag:=GetLastTag;
//	%+S       repeat while itag.val <= LastTag.val
//	%+S       do
//	%+SD         if nXtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	%+S          if   TAGTAB(nXtag.HI)=none
//	%+S          then TAGTAB(nXtag.HI):=
//	%+S               NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	%+S          TAGTAB(nXtag.HI).elt(nXtag.LO):=itag;
//	%+S          itag.val:=itag.val+1; nXtag.val:=nXtag.val+1;
//	%+S       endrepeat;
//	%+S       OutputModule(nXtag.val-1);
//	%+S end;
//
//	end;

}
