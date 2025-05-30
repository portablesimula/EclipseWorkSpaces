package removed.java;

import java.util.HashMap;

import bec.util.Global;
import bec.util.Scode;

public class Sbase {
//	Module SBASE("iAPX");
//	begin insert SCOMN,SKNWN;
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
//	       ---                 B a s i c    L i b r a r y                ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//
//	define Hex_F8=248 -- 370oct=11 111 000B
//	define Hex_FC=252 -- 374oct=11 111 100B
//
//	Visible define CapStrg=0 --- Storage Overflow
//	Visible define CapSymb=1 --- Symbol Table Overflow
//	Visible define CapSegm=2 --- Too many Modules/Segments
//	Visible define CapPubl=3 --- Too many Public Symbols
//	Visible define CapExtr=4 --- Too many External References
//	Visible define CapTags=5 --- Too many tags (identifiers)
//	Visible define CapFixs=6 --- Too many FIXUPs
//	Visible define CapDisk=7 --- Disc full/File too large
//	Visible define MaxCap=7
//
//	Visible const infix(MemAddr) noadr = record:MemAddr(kind=0);
//	Visible const infix(String) nostring = record:String(nchr=0);
//
//	%title ***  B a s i c    U t i l i e s  ***
//
//	Visible Routine DefStructType;
//	import ref(RecordDescr) r; export range(0:MaxType) t;
//	begin infix(DataType) x;
//	%+D   RST(R_DefType);
//	      x.kind:=tUnsigned; t:=ntype;
//	      if r.kind = K_TypeRecord
//	      then x.pntmap:=r qua TypeRecord.pntmap; x.nbyte:=r.nbyte;
//	           if r.nbyte > MaxByte then IERR("Too large type") endif;
//	      else t:=T_NOINF; goto E0 endif;
//	%+D   if (x.nbyte>2) and UnAlligned(%x.nbyte%)
//	%+D   then IERR("SBASE:DefStructtype -- UnAlligned") endif;
//	      if x.nbyte <= (3*AllignFac)
//	      then if x.nbyte=1 then t:=T_BYT1; goto E1;
//	        elsif x.nbyte=2 then t:=T_BYT2; goto E2;
//	        elsif x.nbyte=4
//	           then if x.pntmap.val=0 then t:=T_WRD4; goto E3 endif endif;
//	      endif;
//	      repeat while t <> 0
//	      do if x=TTAB(t) then goto E4 endif; t:=t-1 endrepeat;
//	      ntype:=ntype+1;
//	      if ntype >= MaxType
//	      then IERR("Too many types"); ntype:=MaxType/2 endif;
//	      TTAB(ntype):=x; t:=ntype;
//	E0:E1:E2:E3:E4:end;
//
//	Visible Routine STEQ; --- Test String Equal:  val := T = V;
//	import infix(String) T,V; export Boolean val;
//	begin if T.nchr <> V.nchr then val:=false else
//	      val:=APX_SCMPEQ(T.nchr,T.chradr,V.chradr) endif;
//	end;
//
//	Visible Routine Open;
//	import infix(String) fspec;
//	       range(0:7) ftype;
//	       infix(String) action;
//	       range(0:MaxWord) imlng;
//	export range(0:MaxKey) filekey;
//	begin
//	%+D   if TraceMode <> 0
//	%+D   then outstring("*** OPEN("); outstring(fspec); outchar(',')
//	%+D        outword(ftype); outchar(','); outstring(action);
//	%+D        outchar(','); outword(imlng); outchar(')');
//	%+D        outimage;
//	%+D   endif;
//	      filekey:=EnvOpen(fspec,ftype,action,imlng);
//	      if status <> 0
//	      then ed(errmsg,fspec);
//	           FATAL_ERROR("Can't Open File - "); filekey:=0;
//	      endif;
//	%+D   if TraceMode <> 0
//	%+D   then outstring("*** OPEN: Filekey = ");
//	%+D        outword(filekey); outimage;
//	%+D   endif;
//	end;
//
//	%+D Visible Routine RST; import range(0:MaxByte) k;
//	%+D begin CalCount(k):=CalCount(k)+1 end;
//
//	Visible Routine ReadByte;
//	import range(1:MaxKey) filekey; export range(0:MaxByte) byte;
//	begin byte:=EnvInByte(filekey);
//	      if status<>0 then FILERR(filekey,"SBASE.Read-Byte") endif;
//	%+D   if InputTrace > 3
//	%+D   then outstring("*** Read_byte: ");
//	%+D        outword(byte); printout(sysout);
//	%+D   endif;
//	end;
//
//	%+S Visible Routine inimage; import ref(File) F;
//	%+S begin range(0:MaxWord) filled; infix(String) image;
//	%+SC   if F=none then IERR("inimage-1");
//	%+SC   elsif F.key=0 then IERR("inimage-2")
//	%+SC   else
//	%+S         image.chradr:=@F.chr; image.nchr:=F.nchr-1;
//	%+S         filled:=EnvInImage(F.key,image);
//	%+S         if status <> 0
//	%+S         then if (status=13) or (status=16)
//	%+S              then --- End-of-File ---
//	%+S                   F.chr:=(25 qua character); -- ISO EM-character
//	%+S                   filled:=1; status:=0;
//	%+S              else FILERR(F.key,"inimage-2") endif;
//	%+S         endif;
//	%+S 
//	%+S         --- Blank fill rest of image if necessary ---
//	%+S         repeat while filled < F.nchr
//	%+S         do F.chr(filled):=' '; filled:=filled+1 endrepeat;
//	%+S 
//	%+SD        if TraceMode <> 0
//	%+SD        then outstring("*** INIMAGE: "); outstring(image);
//	%+SD             outimage;
//	%+SD        endif;
//	%+S         F.pos:=0;
//	%+SC   endif;
//	%+S end;
//
//	Visible Routine pickup; import ref(File) F; export infix(String) s;
//	begin if F.pos=0 then s:=nostring
//	      else s.chradr:=@F.chr; s.nchr:=F.pos; F.pos:=0 endif
//	end;
//
//	Visible Routine printout; import ref(File) F;
//	begin infix(String) s; Boolean flg; range(0:MaxKey) key;
//	      if F.pos<>0
//	      then s:=pickup(F); flg:=false;
//	           if s.nchr>F.nchr then s.nchr:=F.nchr-1; flg:=true; endif;
//	           if F.key<>0 then key:=F.key else key:=sysout.key endif;
//	           EnvOutImage(key,s);
//	           if status<>0 then FILERR(key,"SBASE.Printout") endif;
//	%+C        if flg then IERR("SBASE.Printout-TRUNK") endif;
//	      endif;
//	end;
//
//	Visible Routine putpos; import ref(File) F; range(0:MaxWord) p;
//	begin if p<1 then p:=0 elsif p>=F.nchr then p:=F.nchr-1 endif;
//	      repeat while p>F.pos do edchar(F,' ') endrepeat; F.pos:=p;
//	end;
//
//	Visible Routine setpos; import ref(File) F; range(0:MaxWord) p;
//	begin if p<1 then p:=0 elsif p>=F.nchr then p:=F.nchr-1 endif;
//	      repeat edchar(F,' ') while p>F.pos do endrepeat;
//	end;

//	public static void ITRC(String id, String msg) {
//		if(Scode.inputTrace > 1) {
//	%+D       then outstring("Line "); outword(curline);
//	%+D            setpos(sysout,14); outstring(m); outchar(':');
//	%+D            setpos(sysout,25); outstring(pickup(inptrace)); outimage;
//			Scode.traceBuff = new StringBuilder("Line " + Scode.curline + "  " + id + ": " + msg);
//	%+D       else if inptrace.pos > 60 then printout(inptrace)
//	%+D            elsif inptrace.pos<14 then setpos(inptrace,14)
//	%+D            else  setpos(inptrace,inptrace.pos+1) endif;
//		}
//	}

//	%title ***  E r r o r   R e p o r t i n g   ***
//
//	define eFatal=0, eError=1, eWarng=2, eNote=3, eMax=3;
//
//	Routine eReport; import range(0:eMax) code; infix(string) msg;
//	begin range(0:MaxByte) stat; stat:=status; status:=0;
//	      outimage; outstring("Line:"); edWrd(sysout,curline);
//	      case 0:eMax (code)
//	      when eFatal: outstring("  *** FATAL ERROR ***   ");
//	      when eError: outstring("  *** ERROR ***   ");
//	      when eWarng: outstring("  WARNING: ");
//	      when eNote:  outstring("      NOTE:       ");
//	      endcase;
//	      outstring(msg);
//	      if errmsg.pos<>0 then outstring(pickup(errmsg)) endif;
//	      outimage;
//	      if stat <> 0
//	      then outstring("*** STATUS "); edWrd(sysout,stat);
//	           outimage;
//	      endif;
//	end;
//
//	Visible Routine FATAL_ERROR; import infix(String) msg;
//	begin eReport(eFatal,msg); nerr:=nerr+1;
//	      if scode   <> 0 then EnvClose(scode  ,nostring); status:=0 endif;
//	      if modinpt <> 0 then EnvClose(modinpt,nostring); status:=0 endif;
//	      if modoupt <> 0 then EnvClose(modoupt,nostring); status:=0 endif;
//	      if scrfile <> 0 then EnvClose(scrfile,nostring); status:=0 endif;
//	      if objfile <> 0 then EnvClose(objfile,nostring); status:=0 endif;
//	      scode:=0; modinpt:=0; modoupt:=0; scrfile:=0; objfile:=0;
//	%+S   if envpar
//	%+S   then
//	           EnvGiveIntInfo(4,nerr);
//	           EnvGiveIntInfo(1,2);
//	           EnvTerm(0,nostring);
//	%+S   else EnvTerm(2,"FATAL-ERROR") endif;
//	end;

//	public static void ERROR(String msg) {
//	begin eReport(eError,msg); nerr:=nerr+1;
//	      if nerr > MXXERR then FATAL_ERROR("Too many errors") endif;
//		if(Scode.inputTrace != 0) {
//			System.out.println(Scode.traceBuff);
//		}
//		System.out.println(""+msg);
//	}
//
//	Visible Routine CAPERR; import range(0:MaxCap) code;
//	begin infix(String) msg; range(0:MaxByte) stat;
//	      stat:=status; status:=0;
//	%+D   CAPDUMP;
//	      case 0:MaxCap (code)
//	      when CapStrg: msg:="Storage Overflow"
//	      when CapSymb: msg:="Symbol Table Overflow"
//	      when CapSegm: msg:="Too many Modules/Segments"
//	      when CapPubl: msg:="Too many Public Symbols"
//	      when CapExtr: msg:="Too many External References"
//	      when CapTags: msg:="Too many tags (identifiers)"
//	      when CapFixs: msg:="Too many FIXUPs"
//	      when CapDisk: msg:="Disc full/File too large"
//	      endcase;
//	      ed(errmsg,msg); status:=stat;
//	      FATAL_ERROR("Capacity limit exceeded: ");
//	end;

//	public static void IERR(String msg) {
//		ERROR("Internal error: " + msg);
//		Thread.dumpStack();
//		System.exit(0);
//	}
//
//	Visible Routine FILERR; import range(0:MaxKey) key; infix(String) idnt;
//	begin range(0:MaxByte) stat; stat:=status; status:=0;
//	      if key<>0 then edfilename(errmsg,key) endif;
//	      if stat=15 --- File full on output
//	      then ed(errmsg,", "); CAPERR(CapDisk) endif;
//	      ed(errmsg,idnt); status:=stat; FATAL_ERROR("File error: ");
//	end;
//
//	Visible Routine WARNING; import infix(String) msg;
//	begin if status<>0 then goto E1 endif;                   -- pje
//	      if EnvGetIntInfo(5) <> 0
//	      then E1: eReport(eNote,msg); endif;
//	end;
//
//	Visible Routine showWARN; import infix(String) msg;
//	begin eReport(eWarng,msg); end;
//
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	%title *********    D i c t i o n a r y    *********
//
//	Visible Routine DICREF;
//	import infix(WORD) n; export ref(SmbElt) s;
//	begin 
//	%+D   RST(R_DICREF);
//	%+C   if DIC.Symb(n.HI) = none
//	%+C   then IERR("SBASE.DICREF: part = none") endif;
//	      s:=DIC.Symb(n.HI).elt(n.LO);
//	end;
//
//	Visible Routine DICSMB;
//	import infix(WORD) n; export infix(String) s; 
//	begin ref(SmbElt) x;
//	%+D   RST(R_DICSMB);
//	      if DIC.Symb(n.HI) = none then s:="*Undefined"
//	      else x:=DIC.Symb(n.HI).elt(n.LO);
//	           if x=none then s:=nostring;
//	           elsif x.nchr=0 then s:=nostring;
//	           else s.nchr:=x.nchr;
//	                case 0:sMax (x.clas)
//	                when sSYMB: s.chradr:=@x qua Symbol.chr
//	                when sEXTR: s.chradr:=@x qua Extern.chr
//	                when sPUBL: s.chradr:=@x qua Public.chr
//	                when sSEGM: s.chradr:=@x qua Segment.chr
//	                when sMODL: s.chradr:=@x qua ModElt.chr
//	%+C             otherwise IERR("SBASE.DICSMB"); s:=nostring;
//	                endcase;
//	           endif;
//	      endif;
//	end;
	public static String DICSMB(int n) {
		String s = dicMap.get(n);
		return s;
	}
//
//	Routine HASH; import infix(string) s; export range(0:255) k;
//	begin range(0:MaxWord) i; k:=0; i:=s.nchr;
//	      repeat while i <> 0
//	      do i:=i-1; k:=k+var(s.chradr)(i) qua integer endrepeat;
//	      k:=bAND(k,255); -- NOTE: -- MaxHash Supposed = 256
//	end;
//
//	Visible Routine DefSymb;
//	import infix(String) s; export infix(WORD) n;
//	begin range(0:MaxHash) k; ref(SmbElt) x,w;
//	%+D   RST(R_DefSymb);
//	      if s.nchr=0 then n.val:=0; goto E1 endif;
//	      k:=bAND(HASH(s)+sSYMB,255); n:=DIC.HashKey(k); w:=none;
//	      repeat while n.val <> 0
//	      do w:=DIC.Symb(n.HI).elt(n.LO);
//	         if    w.nchr <> s.nchr then -- Nothing
//	         elsif w.clas <> sSYMB  then -- Nothing
//	         elsif APX_SCMPEQ(s.nchr,s.chradr,@w qua Symbol.chr)
//	         then goto E2 endif;
//	         n:=w.link;
//	      endrepeat;
//	      n.val:=DIC.nSymb+1;
//	      if n.HI >= MxpSymb then CAPERR(CapSymb)
//	      elsif DIC.Symb(n.HI)=none
//	      then  DIC.Symb(n.HI):=NEWOBX(size(RefBlock)) endif;
//	      x:=NEWOBX(size(Symbol:s.nchr)); x.link.val:=0; x.clas:=sSYMB;
//	      APX_SMOVEI(s.nchr,@x qua Symbol.chr,s.chradr); x.nchr:=s.nchr;
//	      DIC.Symb(n.HI).elt(n.LO):=x; DIC.nSymb:=n.val;
//	      if w=none then DIC.HashKey(k):=n else w.link:=n endif;
//	E1:E2:end;
	public static HashMap<Integer,String> dicMap = new HashMap<Integer,String>();
	public static int nSymb;
	public static int DefSymb(String symb) {
		int key = nSymb++;
		dicMap.put(key, symb);
		return key;
	}
//
//	Visible Routine DefSegm;
//	import infix(String) s; range(0:2) segtype; export infix(WORD) n;
//	begin range(0:MaxHash) k; ref(SmbElt) x,w;
//	%+D   RST(R_DefSegm);
//	      if s.nchr=0 then n.val:=0; goto E1 endif;
//	      k:=bAND(HASH(s)+sSEGM,255); n:=DIC.HashKey(k); w:=none;
//	      repeat while n.val <> 0
//	      do w:=DIC.Symb(n.HI).elt(n.LO);
//	         if    w.nchr <> s.nchr then -- Nothing
//	         elsif w.clas <> sSEGM  then -- Nothing
//	         elsif APX_SCMPEQ(s.nchr,s.chradr,@w qua Segment.chr)
//	         then goto E2 endif;
//	         n:=w.link;
//	      endrepeat;
//	      n.val:=DIC.nSymb+1;
//	      if n.HI >= MxpSymb then CAPERR(CapSymb)
//	      elsif DIC.Symb(n.HI)=none
//	      then  DIC.Symb(n.HI):=NEWOBX(size(RefBlock)) endif;
//	      x:=NEWOBZ(size(Segment:s.nchr)); x.link.val:=0; x.clas:=sSEGM;
//	      x qua Segment.type:=segtype;
//	      APX_SMOVEI(s.nchr,@x qua Segment.chr,s.chradr); x.nchr:=s.nchr;
//	      DIC.Symb(n.HI).elt(n.LO):=x; DIC.nSymb:=n.val;
//	      if w=none then DIC.HashKey(k):=n else w.link:=n endif;
//	E1:E2:end;
//
//	Visible Routine PutSegx;
//	import infix(WORD) segid; export infix(WORD) segx;
//	begin ref(Segment) x;
//	%+D   RST(R_PutSegx);
//	      if segid.val=0 then segx.val:=0; goto E0 endif;
//	      x:=DIC.Symb(segid.HI).elt(segid.LO);
//	%+C   if x.clas <> sSEGM
//	%+C   then EdSymb(errmsg,segid); Ed(errmsg,", "); EdWrd(errmsg,x.clas);
//	%+C        IERR(" SBASE.PutSegx: Wrong Symbol Class");
//	%+C   endif;
//	      if x.segx.val <> 0 then segx:=x.segx; goto E1 endif;
//	      segx.val:=DIC.nSegm+1;
//	      if segx.HI >= MxpSegm then CAPERR(CapSegm)
//	      elsif DIC.Segm(segx.HI)=none
//	      then  DIC.Segm(segx.HI):=NEWOBX(size(WordBlock)) endif;
//	      DIC.Segm(segx.HI).elt(segx.LO):=segid;
//	      DIC.nSegm:=segx.val; x.segx:=segx;
//	E0:E1:end;
//
//	Visible Routine GetSegx;
//	import infix(WORD) segid; export infix(WORD) segx;
//	begin ref(Segment) x;
//	%+D   RST(R_GetSegx);
//	      if segid.val=0 then segx.val:=0; goto E0 endif;
//	      x:=DIC.Symb(segid.HI).elt(segid.LO);
//	%+C   if x.clas <> sSEGM
//	%+C   then EdSymb(errmsg,segid); Ed(errmsg,", "); EdWrd(errmsg,x.clas);
//	%+C        IERR(" SBASE.GetSegx: Wrong Symbol Class");
//	%+C   endif;
//	      segx:=x.segx;
//	E0:end;
//
//	Visible Routine DefExtr;
//	import infix(String) s; infix(WORD) segid; export infix(WORD) n;
//	begin range(0:MaxHash) k; ref(SmbElt) x,w;
//	%+D   RST(R_DefExtr);
//	      if s.nchr=0 then n.val:=0; goto E1 endif;
//	      k:=bAND(HASH(s)+sEXTR,255); n:=DIC.HashKey(k); w:=none;
//	      repeat while n.val <> 0
//	      do w:=DIC.Symb(n.HI).elt(n.LO);
//	         if    w.nchr <> s.nchr then -- Nothing
//	         elsif w.clas <> sEXTR  then -- Nothing
//	         elsif APX_SCMPEQ(s.nchr,s.chradr,@w qua Extern.chr)
//	         then goto E2 endif;
//	         n:=w.link;
//	      endrepeat;
//	      n.val:=DIC.nSymb+1;
//	      if n.HI >= MxpSymb then CAPERR(CapSymb)
//	      elsif DIC.Symb(n.HI)=none
//	      then  DIC.Symb(n.HI):=NEWOBX(size(RefBlock)) endif;
//	      x:=NEWOBX(size(Extern:s.nchr)); x.link.val:=0; x.clas:=sEXTR;
//	      x qua Extern.segid:=segid; x qua Extern.extx.val:=0;
//	      APX_SMOVEI(s.nchr,@x qua Extern.chr,s.chradr); x.nchr:=s.nchr;
//	      DIC.Symb(n.HI).elt(n.LO):=x; DIC.nSymb:=n.val;
//	      if w=none then DIC.HashKey(k):=n else w.link:=n endif;
//	E1:E2:end;
//
//	Visible Routine PutExtern;
//	import infix(WORD) extid; export infix(WORD) extx;
//	begin ref(Extern) x;
//	%+D   RST(R_PutExtern);
//	      x:=DIC.Symb(extid.HI).elt(extid.LO); PutSegx(x.segid);
//	%+C   if x.clas <> sEXTR
//	%+C   then EdSymb(errmsg,extid); Ed(errmsg,", "); EdWrd(errmsg,x.clas);
//	%+C        IERR(" SBASE.PutExtern: Wrong Symbol Class");
//	%+C   endif;
//	      if x.extx.val <> 0 then extx:=x.extx; goto E1 endif;
//	      extx.val:=DIC.nExtr+1;
//	      if extx.HI >= MxpExtr then CAPERR(CapExtr)
//	      elsif DIC.Extr(extx.HI)=none
//	      then  DIC.Extr(extx.HI):=NEWOBX(size(WordBlock)) endif;
//	      DIC.Extr(extx.HI).elt(extx.LO):=extid;
//	      DIC.nExtr:=extx.val; x.extx:=extx;
//	E1:end;
//
//
//	Visible Routine DefPubl;
//	import infix(String) s; export infix(WORD) n;
//	begin range(0:MaxHash) k; ref(SmbElt) x,w;
//	%+D   RST(R_DefPubl);
//	      if s.nchr=0 then n.val:=0; goto E1 endif;
//	      k:=bAND(HASH(s)+sPUBL,255); n:=DIC.HashKey(k); w:=none;
//	      repeat while n.val <> 0
//	      do w:=DIC.Symb(n.HI).elt(n.LO);
//	         if    w.nchr <> s.nchr then -- Nothing
//	         elsif w.clas <> sPUBL  then -- Nothing
//	         elsif APX_SCMPEQ(s.nchr,s.chradr,@w qua Public.chr)
//	         then goto E2 endif;
//	         n:=w.link;
//	      endrepeat;
//	      n.val:=DIC.nSymb+1;
//	      if n.HI >= MxpSymb then CAPERR(CapSymb)
//	      elsif DIC.Symb(n.HI)=none
//	      then  DIC.Symb(n.HI):=NEWOBX(size(RefBlock)) endif;
//	      x:=NEWOBZ(size(Public:s.nchr)); x.link.val:=0; x.clas:=sPUBL;
//	      APX_SMOVEI(s.nchr,@x qua Public.chr,s.chradr); x.nchr:=s.nchr;
//	      DIC.Symb(n.HI).elt(n.LO):=x; DIC.nSymb:=n.val;
//	      if w=none then DIC.HashKey(k):=n else w.link:=n endif;
//	E1:E2:end;
//
//	Visible Routine PutPublic;
//	import infix(WORD) pubid,segid; infix(wWORD) rela;
//	begin infix(WORD) pubx; ref(Public) x;
//	%+D   RST(R_PutPublic);
//	      x:=DIC.Symb(pubid.HI).elt(pubid.LO);
//	%+C   if x.clas <> sPUBL
//	%+C   then EdSymb(errmsg,pubid); Ed(errmsg,", "); EdWrd(errmsg,x.clas);
//	%+C        IERR(" SBASE.PutPublic: Wrong Symbol Class"); goto E1;
//	%+C   endif;
//	%+C   if x.pubx.val <> 0
//	%+C   then EdSymb(errmsg,pubid);
//	%+C        IERR("SBASE.PutPublic: Already Defined "); goto E2;
//	%+C   endif;
//	      pubx.val:=DIC.nPubl+1;
//	      if pubx.HI >= MxpPubl then CAPERR(CapPubl)
//	      elsif DIC.Publ(pubx.HI)=none
//	      then  DIC.Publ(pubx.HI):=NEWOBX(size(WordBlock)) endif;
//	      DIC.Publ(pubx.HI).elt(pubx.LO):=pubid; DIC.nPubl:=pubx.val;
//	      ------ Fill in Public's attributes ------
//	      x.segx:=PutSegx(segid); x.pubx:=pubx; x.rela:=rela;
//	%+C E1:E2:
//	end;
//
//	Visible Routine NewPubl; export infix(WORD) s;
//	begin --- Create new unique Public symbol ---
//	%+D   RST(R_NewPubl);
//	      EdSymb(sysedit,PRFXID); -- edchar(sysedit,'@');
//	      EdSymb(sysedit,PROGID)
//	      if SymbSequ > 0
//	      then edchar(sysedit,'@'); EdWrd(sysedit,SymbSequ) endif;
//	      SymbSequ:=SymbSequ+1; s:=DefPubl(pickup(sysedit));
//	end;
//
//	Visible Routine DefModl;
//	import infix(String) s; export infix(WORD) n;
//	begin range(0:MaxHash) k; ref(SmbElt) x,w;
//	%+D   RST(R_DefModl);
//	      if s.nchr=0 then n.val:=0; goto E1 endif;
//	      k:=bAND(HASH(s)+sMODL,255); n:=DIC.HashKey(k); w:=none;
//	      repeat while n.val <> 0
//	      do w:=DIC.Symb(n.HI).elt(n.LO);
//	         if    w.nchr <> s.nchr then -- Nothing
//	         elsif w.clas <> sMODL  then -- Nothing
//	         elsif APX_SCMPEQ(s.nchr,s.chradr,@w qua ModElt.chr)
//	         then goto E2 endif;
//	         n:=w.link;
//	      endrepeat;
//	      n.val:=DIC.nSymb+1;
//	      if n.HI >= MxpSymb then CAPERR(CapSymb)
//	      elsif DIC.Symb(n.HI)=none
//	      then  DIC.Symb(n.HI):=NEWOBX(size(RefBlock)) endif;
//	      x:=NEWOBZ(size(ModElt:s.nchr)); x.link.val:=0; x.clas:=sMODL;
//	      APX_SMOVEI(s.nchr,@x qua ModElt.chr,s.chradr); x.nchr:=s.nchr;
//	      DIC.Symb(n.HI).elt(n.LO):=x; DIC.nSymb:=n.val;
//	      if w=none then DIC.HashKey(k):=n else w.link:=n endif;
//	E1:E2:end;
//
//	Visible Routine PutModule;
//	import infix(WORD) relid; export infix(WORD) modx;
//	begin ref(ModElt) x;
//	%+D   RST(R_PutModule);
//	      x:=DIC.Symb(relid.HI).elt(relid.LO);
//	%+C   if x.clas <> sMODL
//	%+C   then EdSymb(errmsg,relid); Ed(errmsg,", "); EdWrd(errmsg,x.clas);
//	%+C        IERR(" SBASE.PutModule: Wrong Symbol Class");
//	%+C   endif;
//	      if x.modx.val <> 0 then modx:=x.modx; goto E1 endif;
//	      modx.val:=DIC.nModl+1;
//	      if modx.HI >= MxpModl then CAPERR(CapSegm)
//	      elsif DIC.Modl(modx.HI)=none
//	      then  DIC.Modl(modx.HI):=NEWOBX(size(WordBlock)) endif;
//	      DIC.Modl(modx.HI).elt(modx.LO):=relid;
//	      DIC.nModl:=modx.val; x.modx:=modx;
//	E1:end;
//	%title ******   S  B  I     R e g i s t e r    U s a g e   ******
//
//	%-E %-D Visible const range(0:7) oprDefaultSreg(8) = 
//	%-E %-D     ( qDS,   qDS,   qSS,   qSS,  qDS, qDS, qSS, qDS );
//
//	%-E %-D ---  biBXSI biBXDI biBPSI biBPDI biSI biDI biBP biBX (biBIREG=biBX)
//
//	%-E %-D Visible macro GetDefaultSreg(1);
//	%-E %-D begin oprDefaultSreg(bAND( %1 .sbireg,7)) endmacro
//
//	%-E %+D Visible Routine GetDefaultSreg;
//	%-E %+D import infix(MemAddr) opr; export range(0:nregs) sr;
//	%-E %+D begin case 0:7 (bAND(opr.sbireg,7))
//	%-E %+D       when biBPSI,biBPDI,biBP: sr:=qSS otherwise sr:=qDS endcase;
//	%-E %+D %+D   RST(R_GetDefaultSreg);
//	%-E %+D end;
//
//	%-E Visible Routine OverrideSreg;
//	%-E import range(0:MaxByte) sbireg; export Boolean res;
//	%-E begin case 0:7 (bAND(sbireg,7))
//	%-E       when biBPSI,biBPDI,biBP: res:=bAND(sbireg,oSREG) <> oSS
//	%-E       otherwise             res:=bAND(sbireg,oSREG) <> oDS endcase;
//	%-E %+D   RST(R_OverrideSreg);
//	%-E end;
//
//	%-E %-D Visible macro GetSreg(1);
//	%-E %-D begin (bAND(bSHR(%1 .sbireg,4),3)+qES) endmacro;
//
//	%-E %+D Visible Routine GetSreg;
//	%-E %+D import infix(MemAddr) opr; export range(0:nregs) r;
//	%-E %+D begin r:=bAND(bSHR(opr.sbireg,4),3)+qES end;
//
//	%-E Visible const range(0:7) oprBasereg(16) = 
//	%-E     (  0 ,   qBX,   qBP,   qBP,   0 ,  0 , qBP, qBX ,
//	%-E       qBX,   qBX,   qBP,   qBP,   0 ,  0 , qBP, qBX );
//	%-E ---  biBXSI biBXDI biBPSI biBPDI biSI biDI biBP biBX (biBIREG=biBX)
//
//	%-E visible macro getBreg(1);
//	%-E begin oprBasereg(bAND( %1 .sbireg,rmBIREG)) endmacro
//
//	%+E Visible Routine GetBreg;
//	%+E import infix(MemAddr) opr; export range(0:nregs) r;
//	%+E begin
//	%+E       range(0:nregs) ir;
//	--- %-E   r:=bAND(opr.sbireg,rmBIREG);
//	--- %-E   if r <> 0 then case 0:7 (bAND(r,7))
//	--- %-E   when biBXSI,biBXDI,biBX: r:=qBX
//	--- %-E   when biBPSI,biBPDI,biBP: r:=qBP
//	--- %-E   otherwise r:=0 endcase endif;
//	%+E       if opr.sibreg=NoIBREG then r:=0
//	%+E       else r:=bOR(qEAX,bAND(opr.sibreg,BaseREG));
//	%+E            ir:=bOR(qEAX,bSHR(bAND(opr.sibreg,IndxREG),3));
//	%+E            if ir=qESP then  elsif r=ir then r:=0 endif;
//	%+E       endif;
//	%+E end;
//
//	%-E Visible const range(0:7) oprIndexreg(16) = 
//	%-E     (  0 ,   qDI,   qSI,   qDI,  qSI, qDI,  0 ,  0  ,
//	%-E       qSI,   qDI,   qSI,   qDI,  qSI, qDI,  0 ,  0  );
//	%-E ---  biBXSI biBXDI biBPSI biBPDI biSI biDI biBP biBX (biBIREG=biBX)
//
//	%-E visible macro getIreg(1);
//	%-E begin oprIndexreg(bAND( %1 .sbireg,rmBIREG)) endmacro
//
//	%+E Visible Routine GetIreg;
//	%+E import infix(MemAddr) opr; export range(0:nregs) r;
//	%+E begin
//	--- %-E   r:=bAND(opr.sbireg,rmBIREG);
//	--- %-E   if r <> 0 then case 0:7 (bAND(r,7))
//	--- %-E   when biBXSI,biBPSI,biSI: r:=qSI
//	--- %-E   when biBXDI,biBPDI,biDI: r:=qDI
//	--- %-E   otherwise r:=0 endcase endif;
//	%+E       r:=bOR(qEAX,bSHR(bAND(opr.sibreg,IndxREG),3));
//	%+E       if r=qESP then r:=0 endif;
//	%+E end;
//
//	%+E Visible Routine SetBreg;
//	%+E import range(0:MaxByte) sibreg; range(0:nregs) br;
//	%+E export range(0:MaxByte) sibres;
//	%+E begin range(0:nregs) ir;
//	%+E       if br=0 then br:=bOR(qEAX,bSHR(bAND(sibreg,IndxREG),3)) endif;
//	%+E       sibres:=bAND(sibreg,ssMASK+IndxREG)+bAND(br,BaseREG);
//	%+E end;
//
//	%+E Visible Routine SetIreg;
//	%+E import range(0:MaxByte) sibreg; range(0:nregs) r;
//	%+E export range(0:MaxByte) sibres;
//	%+E begin range(0:nregs) br,ir;
//	%+E       if sibreg=NoIBREG then br:=0
//	%+E       else br:=bOR(qEAX,bAND(sibreg,BaseREG));
//	%+E            ir:=bOR(qEAX,bSHR(bAND(sibreg,IndxREG),3));
//	%+E            if ir=qESP then  elsif br=ir then br:=0 endif;
//	%+E       endif;
//	%+E       if br=0 then br:=r endif; if r=0 then r:=qESP endif;
//	%+E       sibres:=bAND(sibreg,ssMASK)+bAND(br,BaseREG)+bAND(bSHL(r,3),IndxREG)
//	%+E end;
//
//	%-E Visible Routine AdrSegid;
//	%-E import infix(MemAddr) adr; export range(0:MaxWord) segid;
//	%-E begin case 0:adrMax (adr.kind)
//	%-E     when reladr: if bAND(adr.sbireg,oSREG)=oSS
//	%-E                  then segid:=DGROUP.val else segid:=0 endif;
//	%-E     when locadr: segid:=DGROUP.val
//	%-E     when segadr: segid:=adr.segmid.val
//	%-E     when extadr: segid:=DIC.Symb(adr.smbx.HI).elt(adr.smbx.LO)
//	%-E                         qua Extern.segid.val
//	%-E     when fixadr: segid:=FIXTAB(adr.fix.HI).elt(adr.fix.LO).segid.val
//	%-E     otherwise segid:=0 endcase;
//	%-E end;
//
//	%-E Visible Routine SetSBIreg;
//	%-E import range(0:MaxByte) sbireg,r; export range(0:MaxByte) res;
//	%-E begin range(0:MaxByte) bireg;
//	%-E       case 0:nregs (r)
//	%-E       when qES: res:=bOR(bAND(sbireg,rmBIREG),oES)
//	%-E       when qCS: res:=bOR(bAND(sbireg,rmBIREG),oCS)
//	%-E       when qSS: res:=bOR(bAND(sbireg,rmBIREG),oSS)
//	%-E       when qDS: res:=bOR(bAND(sbireg,rmBIREG),oDS)
//	%-E       when qBX: if bAND(sbireg,rmBIREG)=0 then bireg:=rmBX
//	%-E                 else case 0:7 (bAND(sbireg,7))
//	%-E                 when biBXSI,biBPSI,biSI: bireg:=rmBXSI
//	%-E                 when biBXDI,biBPDI,biDI: bireg:=rmBXDI
//	%-E                 otherwise   bireg:=rmBX    endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qBP: if bAND(sbireg,rmBIREG)=0 then bireg:=rmBP
//	%-E                 else case 0:7 (bAND(sbireg,7))
//	%-E                 when biBXSI,biBPSI,biSI: bireg:=rmBPSI
//	%-E                 when biBXDI,biBPDI,biDI: bireg:=rmBPDI
//	%-E                 otherwise   bireg:=rmBP    endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qSI: if bAND(sbireg,rmBIREG)=0 then bireg:=rmSI
//	%-E                 else case 0:7 (bAND(sbireg,7))
//	%-E                 when biBPSI,biBPDI,biBP: bireg:=rmBPSI
//	%-E                 when biBXSI,biBXDI,biBX: bireg:=rmBXSI
//	%-E                 otherwise   bireg:=rmSI    endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qDI: if bAND(sbireg,rmBIREG)=0 then bireg:=rmDI
//	%-E                 else case 0:7 (bAND(sbireg,7))
//	%-E                 when biBPSI,biBPDI,biBP: bireg:=rmBPDI
//	%-E                 when biBXSI,biBXDI,biBX: bireg:=rmBXDI
//	%-E                 otherwise   bireg:=rmDI    endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E %+D  otherwise IERR("SBASE:SetSBIreg")
//	%-E      endcase;
//	%-E end;
//
//	%-E Visible Routine RemSBIreg;
//	%-E import range(0:MaxByte) sbireg,r; export range(0:MaxByte) res;
//	%-E begin range(0:MaxByte) bireg;
//	%-E       case 0:nregs (r)
//	%-E       when qES,qCS,qSS,qDS: res:=bAND(sbireg,rmBIREG)
//	%-E       when qBX: bireg:=bAND(sbireg,rmBIREG);
//	%-E                 if bireg <> 0 then case 0:7 (bAND(bireg,7))
//	%-E                 when biBXSI:bireg:=rmSI when biBXDI:bireg:=rmDI
//	%-E                 when biBX: bireg:=0 endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qBP: bireg:=bAND(sbireg,rmBIREG);
//	%-E                 if bireg <> 0 then case 0:7 (bAND(bireg,7))
//	%-E                 when biBPSI:bireg:=rmSI when biBPDI:bireg:=rmDI
//	%-E                 when biBP: bireg:=0 endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qSI: bireg:=bAND(sbireg,rmBIREG);
//	%-E                 if bireg <> 0 then case 0:7 (bAND(bireg,7))
//	%-E                 when biBXSI:bireg:=rmBX when biBPSI:bireg:=rmBP
//	%-E                 when biSI: bireg:=0 endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E       when qDI: bireg:=bAND(sbireg,rmBIREG);
//	%-E                 if bireg <> 0 then case 0:7 (bAND(bireg,7))
//	%-E                 when biBXDI:bireg:=rmBX when biBPDI:bireg:=rmBP
//	%-E                 when biDI: bireg:=0 endcase endif;
//	%-E                 res:=bOR(bAND(sbireg,oSREG),bireg);
//	%-E %+D   otherwise IERR("SBASE:RemSBIreg-1")
//	%-E       endcase;
//	%-E %+D   if res=sbireg then IERR("SBASE:RemSBIreg-2") endif;
//	%-E end;
//
//	%title ******   R e g i s t e r    U s a g e   ******
//
//	Visible Routine SamePart;
//	import range(0:nregs) reg1,reg2; export Boolean res;
//	begin
//	%+D   RST(R_SamePart);
//	      if    bAND(reg1,Hex_FC) = bAND(reg2,Hex_FC) then res:=true
//	      elsif bAND(reg1,Hex_F8)=8  then res:= bAND(reg2,Hex_F8)=8
//	%+E   elsif bAND(reg1,Hex_F8)=16 then res:= bAND(reg2,Hex_F8)=16
//	      else res:=false endif;
//	end;
//
//	Visible Routine CheckRegFree;
//	import ref(Qpkt) qi; range(0:MaxWord) rMask; export Boolean res;
//	-- Search forward, starting with qi, and check that all registers
//	-- in rMask are free to be used.
//	begin res:=wAND(rMask,wNOT(RegMinded(qi,rMask)))=rMask;
//	%+D   if listq1 > 2
//	%+D   then EdRegMask(sysout,"CheckRegFree",rMask);
//	%+D        if res then outstring(" = True") else outstring(" = False") endif;
//	%+D        if qi=none then EdRegMask(sysout,"  Mind",MindMask) endif;
//	%+D        outimage;
//	%+D   endif;
//	end;
//
//	Visible Routine GetFreeReg;
//	import ref(Qpkt) qi; range(0:MaxWord) rMask; export range(0:nregs) r;
//	-- Search forward, starting with qi, looking for a whole register 'r'
//	-- which is free to be used. 
//	begin range(0:MaxWord) m;
//	      m:=wAND(wNOT(RegMinded(qi,rMask)),rMask);
//	%-E   if    wAND(m,uAX)=uAX then r:=qAX  elsif wAND(m,uCX)=uCX then r:=qCX
//	%+E   if    wAND(m,uAX)=uAX then r:=qEAX elsif wAND(m,uCX)=uCX then r:=qECX
//	%-E   elsif wAND(m,uDX)=uDX then r:=qDX  elsif wAND(m,uBX)=uBX then r:=qBX
//	%+E   elsif wAND(m,uDX)=uDX then r:=qEDX elsif wAND(m,uBX)=uBX then r:=qEBX
//	%-E   elsif wAND(m,uSI)=uSI then r:=qSI  elsif wAND(m,uDI)=uDI then r:=qDI
//	%+E   elsif wAND(m,uSI)=uSI then r:=qESI elsif wAND(m,uDI)=uDI then r:=qEDI
//	      else r:=0 endif;
//	%+D   if listq1 > 2
//	%+D   then outstring("GetFreeReg: ");
//	%+D        if r=0 then outstring("none") else outreg(r) endif;
//	%+D        outimage;
//	%+D   endif;
//	end;
//
//
//	Visible Routine RegMinded;
//	import ref(Qpkt) qi; range(0:MaxWord) rMask; export range(0:MaxWord) mind;
//	-- Search forward, starting with qi, accumulating
//	-- information about register usage.
//	-- a) A register is MINDED     if it is read before it is written
//	-- b) A register is NOT MINDED if it is written before it is read
//	-- If the end of the instruction queue is reached without any decition,
//	-- we have to inspect 'MindMask' to reach a decition.
//	begin
//	%+D   range(0:MaxWord) rM; rM:=rMask;
//	      mind:=0; repeat while qi <> none
//	      do case 0:qMXX (qi.fnc)
//	         when qFDEST,qBDEST,qLABEL:          -- Skip instruction
//	         when qJMP: if qi.subc<>0 then goto M1 else goto M2 endif
//	%+E      when qCALL,     qJMPM:              -- Treated as all registers read
//	%-E      when qCALL,qDOS2,     qJMPM,qJMPFM: -- Treated as all registers read
//	         M1:  mind:=wOR(mind,rMask); rMask:=0;
//	         otherwise
//	         M2:  mind:=wOR(mind,wAND(qi.read,rMask));
//	              rMask:=wAND(wNOT(wOR(mind,qi.write)),rMask);
//	         endcase;
//	         if rMask=0 then goto E1 endif; qi:=qi.next;
//	      endrepeat;
//	      mind:=wOR(mind,wAND(MindMask,rMask));
//	E1:
//	%+D   if listq1 > 2
//	%+D   then EdRegMask(sysout,"RegMinded(",rM);
//	%+D        EdRegMask(sysout,") = ",mind); outimage;
//	%+D   endif;
//	end;
//
//
//	%-D Visible macro RegDies(2);
//	%-D begin CheckRegFree( %1 qua Qpkt.next, %2 ) endmacro;
//
//	%+D Visible Routine RegDies;
//	%+D import ref(Qpkt) qi; range(0:MaxWord) rMask; export Boolean res;
//	%+D begin res:=CheckRegFree(qi.next,rMask) end;
//
//	%-D Visible macro RegAvailable(1);
//	%-D begin GetFreeReg(%1,uAX+uCX+uDX+uBX+uSI+uDI) endmacro;
//
//	%+D Visible Routine RegAvailable;
//	%+D import ref(Qpkt) qi; export range(0:nregs) res;
//	%+D begin res:=GetFreeReg(qi,uAX+uCX+uDX+uBX+uSI+uDI) end;
//
//	%-D Visible macro RegxAvailable(1);
//	%-D begin
//	%-D %-E   GetFreeReg(%1,uBX+uSI+uDI);
//	%-D %+E   GetFreeReg(%1,uAX+uCX+uDX+uBX+uSI+uDI);
//	%-D endmacro;
//
//	%+D Visible Routine RegxAvailable;
//	%+D import ref(Qpkt) qi; export range(0:nregs) res;
//	%+D begin
//	%+D %-E   res:=GetFreeReg(qi,uBX+uSI+uDI);
//	%+D %+E   res:=GetFreeReg(qi,uAX+uCX+uDX+uBX+uSI+uDI);
//	%+D end;
//	%page
//
//	Visible Routine MakeRegmap; import ref(Qpkt) qi;
//	begin range(0:nregs) qR; range(0:MaxByte) s,subc;
//	%-E   range(0:MaxByte) segreg,bireg;
//	%+E   range(0:MaxByte) sibreg; range(0:nregs) ir,br;
//	      range(0:MaxWord) read,write,mind,uR,uRx,unused,m;
//	%+D   RST(R_MakeRegmap);
//
//	--    NOTE: It is not necessary to 'mind' SP,BP,M since this
//	--          is always done automatically.
//
//	      case 0:qMXX (qi.fnc);
//	      when qPUSHR: ------ PUSHR   reg ----------------------- Format 1
//	           read:=wOR(uMask(qi.subc),uSP); write:=uSP; mind:=0;
//	      when qPOPR: ------- POPR    reg ----------------------- Format 1
//	           subc:=qi.subc; mind:=uMask(subc);
//	           write:=wOR(mind,uSP); read:=uSP;
//	           if subc <= qBL
//	           then write:=wOR(write,uMask(subc+4)) endif;
//	      when qPUSHC: ------ PUSHC   reg const ----------------- Format 2
//	                   ------ PUSHC   reg fld addr -------------- Format 2b
//	           read:=uSP; write:=uSP; mind:=0;
//	%-E        if CPUID < iAPX186
//	%-E        then write:=wOR(write,uMask(qi.reg)) endif;
//	      when qPUSHA: ------ PUSHA   reg opr ------------------- Format 3
//	           read:=uSP; mind:=0; write:=wOR(uMask(qi.reg),uSP);
//	           goto O3xL1;
//	      when qPUSHM: ------ PUSHM   const opr ----------------- Format 4
//	           read:=wOR(uSP,uM); write:=uSP; mind:=0; goto O4L1;
//	      when qPOPK: ------- POPK    const --------------------- Format 2
//	           read:=uSP; write:=wOR(uSP,uF); mind:=0;
//	      when qPOPM: ------- POPM    reg const opr ------------- Format 4
//	           read:=uSP; write:=wOR(uSP,uM); mind:=0;
//	           if UnAlligned(%qi qua Qfrm4.aux.LO%)
//	           then write:=wOR(write,uMask(WholeReg(qi.reg))) endif;
//	           goto O4L2
//	      when qLOADC: ------ LOADC   reg const ----------------- Format 2
//	                   ------ LOADC   reg fld addr -------------- Format 2b
//	           write:=mind:=uMask(qi.reg); read:=0;
//	%-E   when qLOADSC: ----- LOADSC  sreg reg fld addr --------- Format 2b
//	%-E        mind:=uMask(qi.subc);
//	%-E        write:=wOR(mind,uMask(qi.reg)); read:=0;
//	      when qLOADA: ------ LOADA   reg opr ------------------- Format 3
//	           write:=mind:=uMask(qi.reg); read:=0;
//	           goto O3xL2;
//	%-E   when qLDS: -------- LDS     reg ofst opr nrep --------- Format 4c
//	%-E        read:=uM; write:=mind:=wOR(uMask(qi.reg),uDS);
//	%-E        goto O3L2;
//	%-E   when qLES: -------- LES     reg ofst opr nrep --------- Format 4c
//	%-E        read:=uM; write:=mind:=wOR(uMask(qi.reg),uES);
//	%-E        goto O3L3;
//	%+E   when qBOUND: ------ BOUND   reg opr ------------------- Format 3
//	%+E        mind:=uMask(qi.reg);
//	%+E        read:=wOR(mind,uM); write:=0; goto O3L4x;
//	      when qLOAD: ------- LOAD    subc reg ofst opr nrep ---- Format 4c
//	           read:=uM;
//	%+E        if qi.subc <> 0 then write:=mind:=uMask(WholeReg(qi.reg))
//	%+E        else
//	                write:=mind:=uMask(qi.reg);
//	%+E        endif;
//	           goto O3L4;
//	      when qSTORE: ------ STORE   reg opr ------------------- Format 3
//	           read:=uMask(qi.reg); write:=uM; mind:=0;
//	           goto O3L5;
//	      when qMOV: -------- MOV     reg reg2 ------------------ Format 2
//	           read:=uMask(qi qua Qfrm2.aux.val);
//	%+E        if qi.subc <> 0 then write:=mind:=uMask(WholeReg(qi.reg))
//	%+E        else
//	                write:=mind:=uMask(qi.reg);
//	%+E        endif;
//	      when qMOVMC: ------ MOVMC   width const opr ----------- Format 4
//	                   ------ MOVMC   width fld opr addr -------- Format 4b
//	           read:=0; write:=uM; mind:=0; goto O4L3;
//	      when qXCHG: ------- XCHG    reg reg2 ------------------ Format 2
//	           read:=write:=mind:=wOR(uMask(qi.reg),uMask(qi qua Qfrm2.aux.val));
//	      when qXCHGM: ------ XCHGM   reg opr ------------------- Format 3
//	           mind:=uMask(qi.reg); read:=write:=wOR(mind,uM);
//	           goto O3L6;
//	      when qMONADR: ----- MONADR  subc reg ------------------ Format 2
//	           subc:=qi.subc; read:=write:=mind:=uMask(qi.reg);
//	           if subc<>qNOT
//	           then write:=wOR(write,uF);
//	                if subc=qNEGM then mind:=wOR(mind,uF) endif endif;
//	      when qMONADM: ----- MONADM  subc width opr ------------ Format 3
//	           read:=uM; write:=uM; mind:=0;
//	           if qi.subc<>qNOT then write:=wOR(write,uF) endif;
//	           goto O3L7;
//	      when qDYADC: ------ DYADC   subc reg const ------------ Format 2
//	                   ------ DYADC   subc reg fld addr --------- Format 2b
//	           read:=0; goto DR1;
//	      when qDYADM: ------ DYADM   subc reg opr -------------- Format 3
//	           read:=uM; goto DR2;
//	      when qDYADR: ------ DYADR   subc reg reg2 ------------- Format 2
//	           read:=uMask(qi qua Qfrm2.aux.val);
//	  DR1:DR2: uR:=uMask(qi.reg); read:=wOR(read,uR);
//	           case 0:19 (qi.subc)
//	           when qCMP: write:=mind:=uF;
//	           when qADDM,qSUBM,qANDM,qORM,qXORM:
//	                      write:=mind:=wOR(uR,uF);
//	           when qADC,qADCF,qSBB,qSBBF:
//	                      read:=wOR(read,uF);
//	                      write:=wOR(uR,uF); mind:=uR;
//	           otherwise  mind:=uR; write:=wOR(mind,uF) endcase;
//	           if qi.fnc=qDYADM then goto O3L8 endif;
//	      when qSHIFT: ------ SHIFT   subc reg CL --------------- Format 2
//	           mind:=uMask(qi.reg);
//	           read:=wOR(mind,uCL); write:=wOR(mind,uF);
//	      when qDYADMC: ----- DYADMC  subc width const opr ------ Format 4
//	                    ----- DYADMC  subc width fld opr addr --- Format 4b
//	           read:=0; goto DM1;
//	      when qDYADMR: ----- DYADMR  subc reg opr -------------- Format 3
//	           read:=uMask(qi.reg);
//	      DM1: mind:=0; read:=wOR(read,uM);
//	           case 0:19 (qi.subc)
//	           when qCMP: write:=mind:=uF; goto O3L9;
//	           when qADDM,qSUBM,qANDM,qORM,qXORM: mind:=uF;
//	           when qADC,qADCF,qSBB,qSBBF: read:=wOR(read,uF);
//	           endcase;
//	           write:=wOR(uF,uM);
//	           goto O4L4;
//	      when qTRIADR: ----- TRIADR  subc reg ------------------ Format 2
//	           qR:=qi.reg; s:=RegSize(qr);
//	           uR:=uMask(accreg(s)); read:=wOR(uR,uMask(qr));
//	           uRx:=uMask(extreg(s)); write:=wOR(uRx,wOR(uR,uF));
//	           case 0:15 (qi.subc)
//	           when qWMUL,qIMUL,qWMULF,qIMULF: mind:=wOR(uR,uRx);
//	           when qWDIV,qIDIV,qWDIVF,qIDIVF:
//	                  read:=wOR(read,uRx); mind:=uR;
//	           when qWMOD,qIMOD,qWMODF,qIMODF:
//	                  read:=wOR(read,uRx); mind:=uRx;
//	           endcase;
//	      when qTRIADM: ----- TRIADM  subc width opr ------------ Format 3
//	           if qi.reg<qw_W then uR:=uAL; uRx:=uAH else uR:=uAX; uRx:=uDX endif;
//	           read:=wOR(uR,uM); write:=wOR(uR,wOR(uF,uRx));
//	           case 0:15 (qi.subc)
//	           when qWMUL,qIMUL,qWMULF,qIMULF: mind:=wOR(uR,uRx);
//	           when qWDIV,qIDIV,qWDIVF,qIDIVF:
//	                  read:=wOR(read,uRx); mind:=uR
//	           when qWMOD,qIMOD,qWMODF,qIMODF:
//	                  read:=wOR(read,uRx); mind:=uRx
//	           endcase;
//	           goto O3L10;
//	      when qCWD: -------- CWD   width ----------------------- Format 1
//	%-E        read:=uAX; write:=uDX; mind:=wOR(uAX,uDX);
//	%+E        if qi.subc=qAL then read:=uAL; write:=uAH; mind:=uAX;
//	%+E        else read:=uAX; write:=uDX; mind:=wOR(uAX,uDX) endif;
//	      when qCONDEC: ----- CONDEC  subc reg ------------------ Format 2
//	           write:=uMask(WholeReg(qi.reg));
//	           mind:=uMask(qi.reg); read:=wOR(mind,uF);
//	      when qRSTRB, ------ RSTRB   subc dir rep -------------- Format 2
//	           qRSTRW: ------ RSTRB   subc dir rep -------------- Format 2
//	%-E   ---  read:=wOR(wOR(uCX,uDI),uES); write:=wOR(uCX,uDI);
//	%+E        read:=wOR(uECX,uEDI); write:=wOR(uECX,uEDI);
//	           case 0:6 (qi.subc)
//	           when qRMOV:
//	%-E             read:=wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
//	%-E             write:=wOR(wOR(uCX,uDI),wOR(uM,uSI)); mind:=0;
//	%+E             read:=wOR(read,wOR(uM,uESI));
//	%+E             write:=wOR(write,wOR(uM,uESI)); mind:=0;
//	           when qRCMP:
//	%-E             read:=wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
//	%-E             write:=wOR(wOR(uCX,uDI),wOR(uF,uSI)); mind:=uF;
//	%+E             read:=wOR(read,wOR(uM,uESI));
//	%+E             write:=wOR(write,wOR(uF,uESI)); mind:=uF;
//	           when qZERO:
//	%-E             if qi.fnc=qRSTRB then read:=wOR(wOR(wOR(uCX,uDI),uES),uAL)
//	%-E             else read:=wOR(wOR(wOR(uCX,uDI),uES),uAX) endif;
//	%-E             write:=wOR(wOR(uCX,uDI),wOR(uF,uM)); mind:=0;
//	%+E             if qi.fnc=qRSTRB then read:=wOR(read,uAL)
//	%+E             else read:=wOR(read,uEAX) endif;
//	%+E             write:=wOR(write,wOR(uF,uM)); mind:=0;
//	           when qRCMPS:
//	%-E             read:=wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
//	%-E             write:=wOR(wOR(uCX,uDI),wOR(wOR(uF,uAX),uSI)); mind:=uAL;
//	%+E             read:=wOR(read,wOR(uM,uESI));
//	%+E             write:=wOR(write,wOR(wOR(uF,uEAX),uESI)); mind:=uAL;
//	           when qRSCAS:
//	%-E             read:=wOR(wOR(wOR(uCX,uDI),uES),wOR(uM,uAL));
//	%-E             write:=wOR(wOR(uCX,uDI),uF); mind:=uCX;
//	%+E             read:=wOR(read,wOR(uM,uAL));
//	%+E             write:=wOR(write,uF); mind:=uECX;
//	           when qRSTOS:
//	%-E             read:=wOR(wOR(wOR(uCX,uDI),uES),uAL);
//	%-E             write:=wOR(wOR(uCX,uDI),uM); mind:=0;
//	%+E             read:=wOR(read,uAL);
//	%+E             write:=wOR(write,uM); mind:=0;
//	           endcase;
//	      when qLAHF: ------- LAHF ------------------------------ Format 1
//	           read:=uF; write:=uAH; mind:=uAH;
//	      when qSAHF: ------- SAHF ------------------------------ Format 1
//	           read:=uAH; write:=uF; mind:=uF;
//	      when qFDEST, ------ FDEST   subc fix rela qi ---------- Format 6
//	           qBDEST, ------ BDEST   subc labno rela ----------- Format 6
//	           qLABEL: ------ LABEL   subc fix ------------------ Format 6
//	           read:=uSPBPM; write:=uALL; mind:=0;
//	      when qJMP: -------- JMP     subc addr ----------------- Format 5
//	           read:=uSPBPM; write:=uALL; mind:=0;
//	           if qi.subc<>0 then read:=wOR(read,uF) endif;
//	      when qJMPM: ------- JMPM    opr ----------------------- Format 3
//	           read:=uSPBPM; write:=uALL; mind:=0; goto O3L11;
//	%-E   when qJMPFM: ------ JMPFM   opr ----------------------- Format 3
//	%-E        read:=uSPBPM; write:=uALL; mind:=0; goto O3L12;
//	      when qCALL: ------- CALL    subc pxlng addr ----------- Format 5
//	           read:=wOR(uSP,uM); write:=uALLbutBP; mind:=0;
//	%+E        if qi qua Qfrm5.addr.kind=reladr -- Special Case: CALL [reg]
//	%+E        then read:=wOR(read,uMask(GetBreg(qi qua Qfrm5.addr))) endif;
//	      when qADJST: ------ ADJST   const --------------------- Format 2
//	           if qi qua Qfrm2.aux.val=0
//	           then read:=write:=0 else read:=write:=uSP endif;
//	           mind:=0;
//	%-E   when qDOS2: ------- DOS2          --------------------- Format 1
//	%-E        read:=wOR(uSPBPM,uAH); write:=wOR(wOR(uAX,uM),uF); mind:=0;
//	%-E   when qINT: -------- INT     const --------------------- Format 2
//	%-E        if qi qua Qfrm2.aux.val=33  -- DOS-CALL
//	%-E        then read:=wOR(uSPBPM,uAH); write:=wOR(wOR(uAX,uM),uF);
//	%-E        else read:=uSPBPM; write:=uALL endif; mind:=0;
//	      when qENTER, ------ ENTER   const --------------------- Format 2
//	           qLEAVE, ------ LEAVE   const --------------------- Format 2
//	           qRET, -------- RET     const --------------------- Format 2
//	%+E        qINT, -------- INT     const --------------------- Format 2
//	           qIRET: ------- IRET ------------------------------ Format 1
//	           read:=uSPBPM; write:=uALL; mind:=0;
//	      when qFLD: -------- FLD     fmf opr ------------------- Format 3
//	%+E        if NUMID=WTLx167 then write:=uAX;
//	%+E        else
//	                write:=0;
//	%+E        endif;
//	           read:=uM; mind:=0; goto O3L13;
//	      when qFLDC: ------- FLDC    sreg fmf lrv/rev/val ------ Format 3+
//	%-E        read:=uMask(qi.subc); write:=0; mind:=0;
//	%+E        if NUMID=WTLx167 then read:=0; write:=uAX; mind:=0;
//	%+E        else read:=0; write:=0; mind:=0 endif;
//	      when qFST, -------- FST     fmf opr ------------------- Format 3
//	           qFSTP: ------- FSTP    fmf opr ------------------- Format 3
//	%+E        if NUMID=WTLx167 then write:=wOR(uM,uAX);
//	%+E        else
//	                write:=uM;
//	%+E        endif;
//	           read:=0; mind:=0; goto O3L14;
//	      when qFPUSH: ------ FPUSH   fSD fmf ------------------- Format 1
//	%-E        read:=uSP; write:=wOR(uSP,uDI); mind:=0;
//	%+E        read:=uSP; write:=uSP; mind:=0;
//	      when qFPOP: ------- FPOP    fSD fmf ------------------- Format 1
//	%-E        read:=uSP; write:=wOR(uSP,uDI); mind:=0;
//	%+E        read:=uSP; write:=uSP; mind:=0;
//	      when qFLDCK, ------ FLDCK   subc ---------------------- Format 1
//	           qFDUP, ------- FDUP ------------------------------ Format 1
//	           qFMONAD, ----- FMONAD  subc ---------------------- Format 1
//	           qWAIT, ------- WAIT ------------------------------ Format 1
//	           qTSTOFL, ----- INTO ------------------------------ Format 1
//	           qEVAL: ------- EVAL ------------------------------ Format 1
//	           read:=write:=mind:=0;
//	      when qFDYAD: ------ FDYAD   subc ---------------------- Format 1
//	           read:=write:=mind:=0;
//	           if qi.subc = qFCOM
//	           then write:=wOR(uAX,uF); mind:=uF endif;
//	      when qFDYADM: ----- FDYADM  subc fmf opr -------------- Format 3
//	%+E        if NUMID=WTLx167 then write:=uAX;
//	%+E        else
//	                write:=0;
//	%+E        endif;
//	           read:=uM; mind:=0;
//	           if qi.subc = qFCOM
//	           then write:=wOR(uAX,uF); mind:=uF endif;
//	           goto O3L16;
//	      when qLINE: ------- LINE    subc const ---------------- Format 2
//	           read:=write:=mind:=0;
//	%+D        if qi.subc=qSTM then
//	%+S %-D    if qi.subc=qSTM then
//	      -- ???? if DEBMOD > 2 then read:=uSP; write:=wOR(uSP,uF) endif;
//	              if DEBMOD > 2 then            write:=        uF  endif;
//	%+D        endif;
//	%+S %-D    endif;
//	      endcase;
//
//	      goto FF1;
//
//	O4L1:O4L2:O4L3:O4L4:
//	%-E O3L2:O3L3:O3L12:
//	%+E O3L4x:
//	O3L4:O3L5:O3L6:O3L7:O3L8:O3L9:
//	O3L10:O3L11:O3L13:O3L14:      O3L16:
//	%-E   segreg:=bAND(qi qua Qfrm3.opr.sbireg,oSREG);
//	%+D %-E if segreg=0 then IERR("No Segment Register Specified") endif;
//	%-E   if    segreg=oES then read:=wOR(read,uES)
//	%-E   elsif segreg=oDS then read:=wOR(read,uDS) endif;
//	O3xL1:O3xL2:
//	%-E   bireg:=bAND(qi qua Qfrm3.opr.sbireg,rmBIREG);
//	%-E   if bireg <> 0
//	%-E   then case 0:7 (bAND(bireg,7))
//	%-E        when biBXSI: read:=wOR(read,wOR(uBX,uSI))
//	%-E        when biBXDI: read:=wOR(read,wOR(uBX,uDI))
//	%-E        when biBPSI: read:=wOR(read,wOR(uBP,uSI))
//	%-E        when biBPDI: read:=wOR(read,wOR(uBP,uDI))
//	%-E        when biSI: read:=wOR(read,uSI) when biDI: read:=wOR(read,uDI)
//	%-E        when biBP: read:=wOR(read,uBP) when biBX: read:=wOR(read,uBX)
//	%-E        endcase;
//	%-E   endif;
//	%+E   sibreg:=qi qua Qfrm3.opr.sibreg;
//	%+E   if sibreg <> NoIBREG
//	%+E   then br:=bOR(qEAX,bAND(sibreg,BaseREG));
//	%+E        ir:=bOR(qEAX,bSHR(bAND(sibreg,IndxREG),3));
//	%+E        if (br<>ir) or (br=qESP) then read:=wOR(read,uMask(br)) endif
//	%+E        if ir<>qESP then read:=wOR(read,uMask(ir)) endif;
//	%+E   endif;
//	FF1:  if not InMassage
//	      then
//	%+D        if listq1 > 1
//	%+D        then if (PreReadMask<>0)
//	%+D             or (NotMindMask<>0) or (PreMindMask<>uSPBPM)
//	%+D             then EdRegMask(sysout,"          PreRead",PreReadMask);
//	%+D                  EdRegMask(sysout,"  PreMind",PreMindMask);
//	%+D                  EdRegMask(sysout,"  NotMind",NotMindMask);
//	%+D                  PrintOut(sysout);
//	%+D             endif;
//	%+D        endif;
//	           read:=wOR(read,PreReadMask); PreReadMask:=0;
//	%+S        write:=wOR(write,PreWriteMask); PreWriteMask:=0;
//	%+D        unused:=wNOT(wOR(read,write));
//	%+D        m:=wOR(PreMindMask,NotMindMask);
//	%+D        m:=wAND(wAND(wNOT(uSPBPM),unused),m);
//	%+D        if m <> 0
//	%+D        then EdRegMask(errmsg,"Can't Presave",m); IERR(" ") endif;
//	%+D        m:=wAND(wNOT(MindMask),read);
//	%+D        if m <> 0
//	%+D        then EdRegMask(errmsg,"Can't Read",m); IERR(" ") endif;
//	%+D        m:=wAND(wAND(wNOT(wOR(read,uM)),write),MindMask);
//	%+D        if m <> 0
//	%+D        then EdRegMask(errmsg,"Can't Write",m); IERR(" ") endif;
//	---        m:=wOR(PreMindMask,wOR(mind,wAND(MindMask,wNOT(wOR(read,write)))));
//	---        MindMask:=wAND(wNOT(NotMindMask),m);
//	           MindMask:=wAND(wOR(PreMindMask,
//	                              wOR(mind,wAND(MindMask,wNOT(wOR(read,write))))),
//	                          wNOT(NotMindMask));
//	           PreMindMask:=uSPBPM; NotMindMask:=0;
//	      endif;
//	      qi.read:=read; qi.write:=write;
//	end;
//	%title ***   O b j e c t    G e n e r a t i o n   ***
//
//	Visible Routine PALLOC; import size lng; ref() obj;
//	begin size plng; ref(FreeArea) x,w;
//	      --- Put rest of pool onto free list ---
//	      plng:=PoolBot-obj;
//	      if plng > size(FreeArea)
//	      then obj qua FreeArea.PoolSize:=plng; w:=FreePool;
//	           if w=none then FreePool:=obj
//	           elsif plng > w.PoolSize then FreePool:=obj
//	           else repeat x:=w; w:=x.next while w <> none
//	                do if plng>w.PoolSize then goto L endif endrepeat;
//	           L:   x.next:=obj;
//	           endif;
//	           obj qua FreeArea.next:=w;
//	      endif;
//	      npool:=npool+1; plng:=EnvGetSizeInfo(1,npool);
//	      if status <> 0 then IERR("SBASE.PALLOC-1") endif;
//	      PoolTop:=EnvArea(plng,npool);
//	      if status <> 0
//	      then
//	           CAPERR(CapStrg)  --- TEMP  TEMP  TEMP  ?????????
//	           if FreePool=none then CAPERR(CapStrg)
//	           elsif lng>FreePool.PoolSize then CAPERR(CapStrg)
//	           else Status:=0; npool:=npool-1; PoolTop:=FreePool;
//	                plng:=FreePool.PoolSize; FreePool:=FreePool.next;
//	           endif;
//	%+D   elsif npool <= MaxPool
//	%+D   then PoolTab(npool-1).PoolTop:=PoolTop;
//	%+D        PoolTab(npool-1).PoolSize:=plng;
//	      endif;
//	      PoolBot:=PoolTop+plng; PoolNxt:=PoolTop;
//	end;
//
//	Visible Routine NEWOBZ; import size lng; export ref() obj;
//	begin L: obj:=PoolNxt; PoolNxt:=PoolNxt+lng;
//	      if PoolNxt >= PoolBot then PALLOC(lng,obj); goto L endif;
//	      zeroarea(obj,obj+lng);
//	end;
//
//	Visible Routine NEWOBX; import size lng; export ref() obj;
//	begin L: obj:=PoolNxt; PoolNxt:=PoolNxt+lng;
//	      if PoolNxt >= PoolBot then PALLOC(lng,obj); goto L endif;
//	end;
//
//	Visible Routine NEWOBJ;
//	import range(0:K_Max) kind; size lng; export ref(Object) obj;
//	begin obj:=FreeObj(kind);
//	%+D   RST(R_NEWOBJ);
//	      if obj <> none
//	      then FreeObj(kind):=obj qua FreeObject.next;
//	      else L: obj:=PoolNxt; PoolNxt:=PoolNxt+lng;
//	           if PoolNxt >= PoolBot then PALLOC(lng,obj); goto L endif;
//	%+D        ObjCount(kind):=ObjCount(kind)+1;
//	      endif;
//	      zeroarea(obj,obj+lng); obj.kind:=kind;
//	end;
//
//	Visible Routine DELETE; import ref(Object) x;
//	begin range(0:MaxWord) i; ref(Object) y,z; range(0:MaxByte) kind;
//	%+D   RST(R_DELETE);
//	      if x <> none
//	      then kind:=x.kind;
//	           if kind < K_Max
//	           then
//	%+D             if TraceMode > 1
//	%+D             then setpos(sysout,14); outstring("*** DELETE:  ");
//	%+D                  print(x)
//	%+D             endif;
//	                x qua FreeObject.next:=FreeObj(kind);
//	                FreeObj(kind):=x; x.kind := kind+128;
//	           else IERR("DELETE(x)  --  x is already deleted");
//	%+D             --- print(x); EnvDmpobj(sysout.key,x,objsize(x));
//	           endif;
//	      endif;
//	end;
//
//	%title ***  E d i t i n g    R o u t i n e s
//
//	--- EDIT TO SYSOUT:
//
//	visible routine outstring; import infix(string) t;
//	--- exp. ed(sysout ,t): no check on overflow in sysout buffer ---
//	begin range(0:maxByte) p,n; n:=t.nchr;
//	      if n <> 0
//	      then p:=sysout.pos; sysout.pos:=p+n;
//	           APX_SMOVEI(n,@sysout.chr(p),t.chradr);
//	      endif;
//	end;
//
//	%+D visible routine outsymb; import infix(word) s;
//	%+D --- exp. edSymb(sysout ,s): no check on overflow in sysout buffer ---
//	%+D begin infix(string) t; range(0:maxByte) p,n;
//	%+D       t:=DICSMB(s); n:=t.nchr;
//	%+D       if n <> 0
//	%+D       then p:=sysout.pos; sysout.pos:=p+n;
//	%+D            APX_SMOVEI(n,@sysout.chr(p),t.chradr);
//	%+D       endif;
//	%+D end;
//
//	visible routine outchar; import character ch;
//	--- exp. edChar(sysout ,ch): no check on overflow in sysout buffer ---
//	begin range(0:maxbyte) p; p:=sysout.pos; sysout.pos:=p+1; sysout.chr(p):=ch end;
//
//	visible routine outint; import integer i;
//	begin edInt(sysout ,i) end;
//
//	%+D visible routine outword; import range(0:maxword) w;
//	%+D begin integer i; i:=w; if i<0 then i:=i+65536 endif; outint(i) end;
//
//	%+D Visible Routine outadr; import infix(MemAddr) adr;
//	%+D begin edadr(sysout ,adr) end;
//
//	%+D Visible Routine outopr; import infix(MemAddr) opr;
//	%+D begin EdOpr(sysout ,opr) end;
//
//	%+D Visible Routine outreg; import range(0:nregs) r;
//	%+D begin EdReg(sysout ,r) end;
//
//	Visible Routine outimage;
//	begin
//	%+D   printout(inptrace); printout(ouptrace);
//	      printout(sysout);
//	end;
//
//	Visible Routine ed; import ref(File) F; infix(String) t;
//	begin range(0:Maxword) p,n; p:=F.pos; n:=t.nchr;
//	      if n <> 0
//	      then if (p+n) >= F.nchr
//	           then printout(F); p:=0;
//	                if n>F.nchr then n:=F.nchr endif;
//	           endif;
//	           APX_SMOVEI(n,@F.chr(p),t.chradr); F.pos:=p+n;
//	      endif;
//	end;
//
//	%+D Visible Routine edfile; import ref(File) F,T;
//	%+D begin infix(String) s;
//	%+D       if T=none then ed(F,"no-file")
//	%+D       else ed(F,"key="); EdWrd(F,T.key);
//	%+D            ed(F,",  pos="); EdWrd(F,T.pos);
//	%+D            ed(F,",  nchr="); EdWrd(F,T.nchr); ed(F,",  chr=");
//	%+D            s.chradr:=@T.chr; s.nchr:=T.nchr; ed(F,s);
//	%+D       endif;
//	%+D end;
//
//	%+D Visible Routine EdPmap; import ref(File) F; infix(WORD) p;
//	%+D begin range(0:MaxByte) i,j; infix(string) pm;
//	%+D       if p.val=0 then ed(F,"(None)")
//	%+D       else pm:=DICSMB(p); i:=0; Ed(F,"(Pnt:");
//	%+D            repeat while i < pm.nchr
//	%+D            do j:=var(pm.chradr)(i) qua integer;
//	%+D               if i<>0 then EdChar(F,',') endif; EdWrd(F,j); i:=i+1;
//	%+D            endrepeat;
//	%+D            EdChar(F,')');
//	%+D       endif;
//	%+D end;
//
//	Visible Routine EdSymb; import ref(File) F; infix(WORD) i;
//	begin ed(F,DICSMB(i)) end;
	public static String edSymb(int i) {
		return DICSMB(i);
	}
//
//	%+D Visible Routine edtag; import ref(File) F; infix(WORD) tag;
//	%+D begin edchar(F,'T'); EdWrd(F,tag.val); edchar(F,':');
//	%+D       if TIDTAB(tag.HI) <> none
//	%+D       then EdSymb(F,TIDTAB(tag.HI).elt(tag.LO)) endif;
//	%+D end;
//
//	%+D Routine GetTag; import ref(Descriptor) d; export infix(WORD) t;
//	%+D begin if d=none then t.val:=0; goto E1 endif; t:=GetLastTag;
//	%+D       repeat while t.val <> 0
//	%+D       do if DISPL(t.HI)=none then -- Nothing
//	%+D          elsif DISPL(t.HI).elt(t.LO)=d then goto E2 endif;
//	%+D          t.val:=t.val-1
//	%+D       endrepeat;
//	%+D E1:E2:end;
//
//	%+D Visible Routine eddate; import ref(File) F;
//	%+D begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	%+D       if (p+12) >= F.nchr then printout(F); p:=0; endif;
//	%+D       s.chradr:=@F.chr(p); s.nchr:=F.nchr-p;
//	%+D       F.pos:=p+EnvTimDat(s);
//	%+D       if status<>0 then IERR("SBASE.Eddate") endif;
//	%+D end;
//
//	%+D Visible Routine EdFixup; import ref(File) F; infix(Fixup) Fx;
//	%+D begin infix(WORD) s; s:=GetSegx(Fx.segid);
//	%+D       Ed(F,"SEG"); EdWrd(F,s.val); EdChar(F,':');
//	%+D       if Fx.Matched then EdWrd(F,Fx.rela.val)
//	%+D       else if Fx.smbx.val <> 0
//	%+D            then EdSymb(F,Fx.smbx);EdChar(F,':') endif;
//	%+D            Ed(F,"Unmatched");
//	%+D       endif;
//	%+D end;
//
//
//	%+D Visible Routine EdAdr; import ref(File) F; infix(MemAddr) adr;
//	%+D begin infix(WORD) s; integer rela; infix(Fixup) Fx;
//	%+D    rela:=adr.rela.val;
//	%+D    case 0:adrMax (adr.kind)
//	%+D    when reladr: -- Nothing --
//	%+D    when locadr: rela:=rela-adr.loca;
//	%+D    when segadr: s:=GetSegx(adr.segmid); Ed(F,"SEG"); EdWrd(F,s.val);
//	%+D    when knladr: Ed(F,"KERNEL#"); EdHex(F,adr.knlx.val,4);
//	%+D    when extadr: EdSymb(F,adr.smbx);
//	%+D    when fixadr: Fx:=FIXTAB(adr.fix.HI).elt(adr.fix.LO);
//	%+D         if Fx.Matched
//	%+D         then s:=GetSegx(Fx.segid); Ed(F,"SEG"); EdWrd(F,s.val);
//	%+D         else Ed(F,"?FIX"); EdWrd(F,Fx.fixno); rela:=0 endif;
//	%+D    otherwise ed(F,"noadr") endcase;
//	%+D    if    rela < 0 then edchar(F,'-'); EdHex(F,-rela,4); -- EdWrd(F,-rela)
//	%+D    elsif rela > 0 then edchar(F,'+'); EdHex(F, rela,4); -- EdWrd(F,rela)
//	%+D    endif;
//	%+D end;
//
//	%+D Visible Routine EdOpr; import ref(File) F; infix(MemAddr) opr;
//	%+D begin infix(MemAddr) opx; integer rela;
//	%+D %-E   range(0:MaxByte) bireg;
//	%+DE      range(0:MaxByte) br,ir,ss;
//	%+D %-E   bireg:=bAND(opr.sbireg,15);
//	%+D %-E   if OverrideSreg(opr.sbireg)
//	%+D %-E   then edreg(F,GetSreg(opr)); edchar(F,':') endif;
//	%+D       opx:=opr; rela:=opr.rela.val; opx.rela.val:=0;
//	%+D       if opr.kind=0 then rela:=0
//	%+D       elsif opr.kind=locadr
//	%+D       then rela:=rela-opr.loca; opx.loca:=0 endif;
//	%+D       EdAdr(F,opx);
//	%+D %-E   if bireg <> 0
//	%+D %-E   then case 0:7 (bAND(bireg,7))
//	%+D %-E        when 0: Ed(F,"[BX+SI]")   when 4: Ed(F,"[SI]")
//	%+D %-E        when 1: Ed(F,"[BX+DI]")   when 5: Ed(F,"[DI]")
//	%+D %-E        when 2: Ed(F,"[BP+SI]")   when 6: Ed(F,"[BP]")
//	%+D %-E        when 3: Ed(F,"[BP+DI]")   when 7: Ed(F,"[BX]")
//	%+D %-E        endcase;
//	%+D %-E   endif;
//	%+DE      if opr.sibreg <> NoIBREG
//	%+DE      then EdChar(F,'['); br:=GetBreg(opr); ir:=GetIreg(opr);
//	%+DE           if br=0 then EdReg(F,ir);
//	%+DE           else EdReg(F,br);
//	%+DE                if ir<>0 then EdChar(F,'+'); EdReg(F,ir) endif;
//	%+DE           endif;
//	%+DE           case 0:3 (bSHR(opr.sibreg,6));
//	%+DE           when 0: EdChar(F,']'); when 1: Ed(F,"*2]");
//	%+DE           when 2: Ed(F,"*4]");   when 3: Ed(F,"*8]");
//	%+DE           endcase;
//	%+DE      endif;
//	%+D       if    rela < 0 then edchar(F,'-'); EdWrd(F,-rela)
//	%+D       elsif rela > 0 then edchar(F,'+'); EdWrd(F,rela) endif;
//	%+D end;
//
//
//	%+D Routine edatatype; import ref(File) F; infix(DataType) d;
//	%+D begin EdWrd(F,d.nbyte); Ed(F,"BYTE");
//	%+D       if d.pntmap.val <> 0 then EdPmap(F,d.pntmap) endif;
//	%+D end;
//
//	%+D Visible Routine edtype; import ref(File) F; range(0:MaxType) t;
//	%+D begin if t <= T_max
//	%+D       then case 0:T_max (t)
//	%+D            when T_VOID:  ed(F,"VOID")   when T_WRD4:  ed(F,"WRD4")
//	%+D            when T_WRD2:  ed(F,"WRD2")   when T_BYT2:  ed(F,"BYT2")
//	%+D            when T_BYT1:  ed(F,"BYT1")   when T_TREAL: ed(F,"TREAL")
//	%+D            when T_REAL:  ed(F,"REAL")   when T_LREAL:  ed(F,"LREAL")
//	%+D            when T_BOOL:  ed(F,"BOOL")   when T_CHAR:  ed(F,"CHAR")
//	%+D            when T_SIZE:  ed(F,"SIZE")   when T_OADDR: ed(F,"OADDR")
//	%+D            when T_AADDR: ed(F,"AADDR")  when T_GADDR: ed(F,"GADDR")
//	%+D            when T_PADDR: ed(F,"PADDR")  when T_RADDR: ed(F,"RADDR")
//	%+D            when T_NPADR: ed(F,"NPADR")  when T_NOINF: ed(F,"NOINF")
//	%+D            endcase;
//	%+D       else EdChar(F,'T'); EdWrd(F,t); EdChar(F,':');
//	%+D            edatatype(F,TTAB(t))
//	%+D       endif;
//	%+D end;
//
//
//	Visible Routine edsysfile; import ref(File) f; range(1:3) code;
//	begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	      if (p+12) >= F.nchr then printout(F); p:=0 endif
//	      s.chradr:=@F.chr(p); s.nchr:=F.nchr-p;
//	      F.pos:=p+EnvDsetSpec(code,s);
//	      if status <> 0 then IERR("SBASE.Edsysfile") endif;
//	end;
//
//	Visible Routine edfilename; import ref(File) f; range(1:MaxKey) key;
//	begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	      if (p+24) >= F.nchr then printout(F); p:=0 endif
//	      s.chradr:=@F.chr(p); s.nchr:=F.nchr-p;
//	      F.pos:=p+EnvDsetName(key,s);
//	      if status <> 0 then FILERR(key,"SBASE.Edfilename") endif;
//	end;
//
//	Visible Routine edtextinfo; import ref(File) f; range(0:23) code;
//	begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	      if (p+24) >= F.nchr then printout(F); p:=0 endif
//	      s.chradr:=@F.chr(p); s.nchr:=F.nchr-p;
//	      F.pos:=p+EnvGetTextInfo(code,s);
//	      if status <> 0 then EdWrd(errmsg,code); IERR("-SBASE.Edtextinfo") endif;
//	end;
//
//	%+D Visible Routine edreg; import ref(File) F; range(0:nregs) r;
//	%+D begin case 0:nregs (r)
//	%+DE      when qEAX:ed(F,"EAX")  when qECX:ed(F,"ECX")
//	%+DE      when qEBX:ed(F,"EBX")  when qEDX:ed(F,"EDX")
//	%+DE      when qESP:ed(F,"ESP")  when qEBP:ed(F,"EBP")
//	%+DE      when qESI:ed(F,"ESI")  when qEDI:ed(F,"EDI")
//	%+D       when qAL:ed(F,"AL")  when qCL:ed(F,"CL")  when qDL:ed(F,"DL")
//	%+D       when qBL:ed(F,"BL")  when qAH:ed(F,"AH")  when qCH:ed(F,"CH")
//	%+D       when qDH:ed(F,"DH")  when qBH:ed(F,"BH")  when qAX:ed(F,"AX")
//	%+D       when qCX:ed(F,"CX")  when qDX:ed(F,"DX")  when qBX:ed(F,"BX")
//	%+D %-E   when qSP:ed(F,"SP")  when qBP:ed(F,"BP")
//	%+D %-E   when qSI:ed(F,"SI")  when qDI:ed(F,"DI")
//	%+D %-E   when qCS:ed(F,"CS")  when qSS:ed(F,"SS") 
//	%+D %-E   when qDS:ed(F,"DS")  when qES:ed(F,"ES")
//	%+D       when qF:ed(F,"F")    when qM:ed(F,"M")
//	%+D       otherwise ed(F,"R"); EdWrd(F,r) endcase;
//	%+D end;
//
//	%+D Visible Routine EdRegMask;
//	%+D import ref(File) F; infix(String) s; range(0:MaxWord) m;
//	%+D begin range(0:MaxWord) x;
//	%+D       Ed(F,s); if m=0 then Ed(F,":None"); goto E1
//	%+D       elsif m=uALL then Ed(F,":ALL"); goto E2
//	%+D       elsif m=uALLbutBP then Ed(F,":ALL but BP"); goto E3 endif;
//	%+D       x:=wAND(m,uAX);  if x=0 then -- Nothing
//	%+DE      elsif x=uEAX then Ed(F,":EAX")
//	%+D %-E   elsif x=uAX  then Ed(F,":AX")
//	%+D       elsif x=uAL  then Ed(F,":AL") else Ed(F,":AH") endif;
//	%+D       x:=wAND(m,uCX);  if x=0 then -- Nothing
//	%+DE      elsif x=uECX then Ed(F,":ECX")
//	%+D %-E   elsif x=uCX  then Ed(F,":CX")
//	%+D       elsif x=uCL  then Ed(F,":CL") else Ed(F,":CH") endif;
//	%+D       x:=wAND(m,uDX);  if x=0 then -- Nothing
//	%+DE      elsif x=uEDX then Ed(F,":EDX")
//	%+D %-E   elsif x=uDX  then Ed(F,":DX")
//	%+D       elsif x=uDL  then Ed(F,":DL") else Ed(F,":DH") endif;
//	%+D       x:=wAND(m,uBX);  if x=0 then -- Nothing
//	%+DE      elsif x=uEBX then Ed(F,":EBX")
//	%+D %-E   elsif x=uBX  then Ed(F,":BX")
//	%+D       elsif x=uBL  then Ed(F,":BL") else Ed(F,":BH") endif;
//	%+D %-E   if wAND(m,uSP)<>0  then Ed(F,":SP")  endif;
//	%+DE      if wAND(m,uESP)<>0 then Ed(F,":ESP") endif;
//	%+D %-E   if wAND(m,uBP)<>0  then Ed(F,":BP")  endif;
//	%+DE      if wAND(m,uEBP)<>0 then Ed(F,":EBP") endif;
//	%+D %-E   if wAND(m,uSI)<>0  then Ed(F,":SI")  endif;
//	%+DE      if wAND(m,uESI)<>0 then Ed(F,":ESI") endif;
//	%+D %-E   if wAND(m,uDI)<>0  then Ed(F,":DI")  endif;
//	%+DE      if wAND(m,uEDI)<>0 then Ed(F,":EDI") endif;
//	%+D %-E   if wAND(m,uES)<>0  then Ed(F,":ES")  endif;
//	%+D %-E   if wAND(m,uDS)<>0  then Ed(F,":DS")  endif;
//	%+D       if wAND(m,uF )<>0  then Ed(F,":F")   endif;
//	%+D       if wAND(m,uM )<>0  then Ed(F,":M")   endif;
//	%+D E1:E2:E3:end;
//
//	%+D Visible Routine edhex; import ref(File) F; range(0:MaxWord) val,n;
//	%+D begin range(0:MaxWord) p,q,x,h; infix(String) s; p:=F.pos;
//	%+D       if (p+n) >= F.nchr then printout(F); p:=0 endif;
//	%+D       q:=p+n; F.pos:=q;
//	%+D       repeat x:=val while q>p
//	%+D       do q:=q-1; val:=x/16; h:=x-(val*16)+48;
//	%+D          if h > 57 then h:=h+7 endif;
//	%+D          F.chr(q):=h qua character;
//	%+D       endrepeat;
//	%+D end;
//
//	%+D Visible Routine edbin; import ref(File) F; range(0:MaxByte) val;
//	%+D begin range(0:1) b1,b2,b3,b4,b5,b6,b7,b8;
//	%+D    b8:=bAND(val,1); val:=val/2; b7:=bAND(val,1); val:=val/2;
//	%+D    b6:=bAND(val,1); val:=val/2; b5:=bAND(val,1); val:=val/2;
//	%+D    b4:=bAND(val,1); val:=val/2; b3:=bAND(val,1); val:=val/2;
//	%+D    b2:=bAND(val,1); val:=val/2; b1:=bAND(val,1);
//	%+D    EdWrd(F,b1); EdWrd(F,b2); EdWrd(F,b3); EdWrd(F,b4);
//	%+D    EdWrd(F,b5); EdWrd(F,b6); EdWrd(F,b7); EdWrd(F,b8);
//	%+D end;
//
//	Visible Routine EdWrd;
//	import ref(File) F;
//	%-E    range(0:MaxWord) w;
//	%+E    integer          w;
//	begin range(0:5) d; range(0:MaxWord) p;
//	      infix(String) s; integer i;
//	%+E   EdInt(F,w);
//	%-E   i:=w; if i<0 then i:=i+65536 endif;
//	%-E   if i<10         then d:=1 elsif i<100         then d:=2
//	%-E   elsif i<1000    then d:=3 elsif i<10000       then d:=4
//	%-E   else d:=5 endif;
//	%-E
//	%-E   p:=F.pos;
//	%-E   if (p+d) >= F.nchr then printout(F); p:=0 endif;
//	%-E   F.pos:=p+d; s.chradr:=@F.chr(p);
//	%-E   s.nchr:=d; EnvPutInt(s,i);
//	%-E   if status<>0 then IERR("SBASE.EdWrd") endif;
//	end;
//
//	Visible Routine EdInt; import ref(File) F; integer i;
//	begin range(0:11) d; range(0:MaxWord) p; infix(String) s;
//	      if i<0 then edchar(F,'-'); i:=-i endif;
//	      if i<10         then d:=1 elsif i<100         then d:=2
//	   elsif i<1000       then d:=3 elsif i<10000       then d:=4
//	   elsif i<100000     then d:=5 elsif i<1000000     then d:=6
//	   elsif i<10000000   then d:=7 elsif i<100000000   then d:=8
//	   elsif i<1000000000 then d:=9                     else d:=11;
//	   endif;
//
//	      p:=F.pos;
//	      if (p+d) >= F.nchr then printout(F); p:=0 endif;
//	      F.pos:=p+d; s.chradr:=@F.chr(p);
//	      s.nchr:=d; EnvPutInt(s,i);
//	      if status<>0 then IERR("SBASE.EdInt") endif;
//	end;
//
//	%+D Visible Routine EdValue;
//	%+D import ref(File) F; range(0:MaxType) type; infix(ValueItem) itm;
//	%+D begin case 0:T_Max (type)
//	%+D       when T_BYT1,T_CHAR,T_BOOL:            EdWrd(F,itm.byt)
//	%+D       when T_BYT2,T_WRD2,T_SIZE,T_AADDR:    EdWrd(F,itm.wrd)
//	%+D       when T_WRD4:                          EdInt(F,itm.int)
//	%+D       when T_REAL:                          EdReal(F,itm.rev)
//	%+D       when T_LREAL:                         EdLreal(F,itm.lrv)
//	%+D       when T_NPADR,T_PADDR,T_RADDR,T_OADDR: EdAdr(F,itm.base)
//	%+D       when T_GADDR: EdAdr(F,itm.base); EdChar(F,'+');
//	%+D                     EdWrd(F,itm.ofst);
//	%+D       endcase;
//	%+D end;
//
//	%+D Visible Routine edsize; import ref(File) F; size i;
//	%+D begin infix(String) s; range(0:Maxword) p; p:=F.pos;
//	%+D       if (p+13) >= F.nchr then printout(F); p:=0 endif;
//	%+D       F.pos:=p+13; s.chradr:=@F.chr(p);
//	%+D       s.nchr:=13; EnvPutSize(s,i);
//	%+D       if status<>0 then IERR("SBASE.Edsize") endif;
//	%+D end;
//
//	%+D Visible Routine edref; import ref(File) F; ref() i;
//	%+D begin infix(String) s; range(0:Maxword) p; p:=F.pos;
//	%+D       if (p+13) >= F.nchr then printout(F); p:=0 endif;
//	%+D       F.pos:=p+13; s.chradr:=@F.chr(p);
//	%+D       s.nchr:=13; EnvPutOaddr(s,i);
//	%+D       if status<>0 then IERR("SBASE.Edref") endif;
//	%+D end;
//
//	%+D Visible Routine edfix;
//	%+D import ref(File) F; real r; range(0:MaxWord) n;
//	%+D begin infix(String) s; integer i; range(0:7) d; range(0:MaxWord) p;
//	%+D       p:=F.pos; i:=r qua integer; if i < 0 then i:= -i endif;
//	%+D       if i<10         then d:=2 elsif i<100         then d:=3
//	%+D    elsif i<1000       then d:=4 elsif i<10000       then d:=5
//	%+D    elsif i<100000     then d:=6 else  d:=7  endif;
//	%+D       if r < 0.0 then d:=d+1 endif; d:=d+n;
//	%+D       if (p+d) >= F.nchr then printout(F); p:=0 endif;
//	%+D       F.pos:=p+d; s.chradr:=@F.chr(p);
//	%+D       s.nchr:=d; EnvPutFix(s,r,n);
//	%+D       if status<>0 then status:=0; edreal(F,r) endif;
//	%+D end;
//
//	%+D Visible Routine edreal; import ref(File) F; real r;
//	%+D begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	%+D       if (p+15) >= F.nchr then printout(F); p:=0 endif;
//	%+D       F.pos:=p+15; s.chradr:=@F.chr(p);
//	%+D       s.nchr:=15; EnvPutReal(s,r,8);
//	%+D       if status<>0 then IERR("SBASE.Edreal") endif;
//	%+D end;
//
//	%+D Visible Routine edlreal; import ref(File) F; long real r;
//	%+D begin infix(String) s; range(0:MaxWord) p; p:=F.pos;
//	%+D       if (p+20) >= F.nchr then printout(F); p:=0 endif;
//	%+D       F.pos:=p+20; s.chradr:=@F.chr(p);
//	%+D       s.nchr:=20; EnvPutLreal(s,r,12);
//	%+D       if status<>0 then IERR("SBASE.Edlreal") endif;
//	%+D end;
//
//	Visible Routine edchar; import ref(File) F; character c;
//	begin range(0:Maxword) p; p:=F.pos;
//	      if (p+1) >= F.nchr then printout(F); p:=0 endif;
//	      F.pos:=p+1; F.chr(p):=c;
//	end;
//
//	%+D Visible Routine edbool; import ref(File) F; Boolean b;
//	%+D begin if b then ed(F,"true") else ed(F,"false") endif end;
//	%title ***   P r i n t / E d i t   O b j e c t   ***
//
//	%+D Visible Routine print; import ref(Object) x;
//	%+D begin edit(sysout,x); printout(sysout) end;
//
//
//	%+D Visible Routine edit; import ref(File) F; ref(Object) x;
//	%+D begin range(0:Maxword) pos,i,ofst,lng; ref(Parameter) par;
//	%+D    range(0:MaxByte) kind;
//
//	%+D    if x = none
//	%+D    then ed(F,"No Object (none)"); goto E endif;
//	%+D    kind:=x.kind;
//	%+D    if kind > K_Max then kind:=kind-128; ed(F,"*DELETED:") endif;
//
//	%+D    case 0:K_Max (kind)
//
//	%+D    when K_BSEG:
//	%+D           ed(F,"Segment "); EdSymb(F,x qua BSEG.segid);
//	%+D           ed(F,", TOS=");  edref(F,x qua BSEG.TOS);
//	%+D           ed(F,", BOS=");  edref(F,x qua BSEG.BOS);
//	%+D           ed(F,", SAV=");  edref(F,x qua BSEG.SAV);
//
//	%+D    when K_Qfrm1:  --- ListQinstr(x);
//	%+D    when K_Qfrm2:  --- ListQinstr(x);
//	%+D    when K_Qfrm2b: --- ListQinstr(x);
//	%+D    when K_Qfrm3:  --- ListQinstr(x);
//	%+D    when K_Qfrm4:  --- ListQinstr(x);
//	%+D    when K_Qfrm4b: --- ListQinstr(x);
//	%+D    when K_Qfrm4c: --- ListQinstr(x);
//	%+D    when K_Qfrm5:  --- ListQinstr(x);
//	%+D    when K_Qfrm6:  --- ListQinstr(x);
//
//	%+D    ---------    E d i t     D e s c r i p t o r   ---------
//
//	%+D    when K_GlobalVar:
//	%+D         ed(F,"Global "); edtype(F,x.type);
//	%+D         ed(F," Address = "); EdAdr(F,x qua IntDescr.adr);
//	%+D    when K_ExternVar:
//	%+D         ed(F,"Extern "); edtype(F,x.type); ed(F," Address = ");
//	%+D         edadr(F,Ext2MemAddr(x qua ExtDescr.adr));
//	%+D    when K_Import,K_Export:
//	%+D         edtype(F,x.type); ed(F,"  rela = ");
//	%+D         EdWrd(F,x qua LocDescr.rela);
//	%+D    when K_LocalVar:
//	%+D         ed(F,"Local "); edtype(F,x.type); 
//	%+D         ed(F,"  rela = "); EdWrd(F,x qua LocDescr.rela);
//	%+D    when K_Attribute:
//	%+D         ed(F,"Attribute "); edtype(F,x.type);
//	%+D         ed(F,"  Offset = "); EdWrd(F,x qua LocDescr.rela);
//	%+D    when K_RecordDescr:
//	%+D         ed(F,"RECORD "); EdTag(F,GetTag(x)); ed(F,"  ");
//	%+D         EdWrd(F,x qua RecordDescr.nbyte); ed(F," bytes   ");
//	%+D    when K_TypeRecord:
//	%+D         ed(F,"RECORD "); EdTag(F,GetTag(x)); ed(F,"  ");
//	%+D         EdWrd(F,x qua TypeRecord.nbyte); ed(F," bytes   ");
//	%+D         Ed(F," pntmap="); edpmap(F,x qua TypeRecord.pntmap);
//	%+D    when K_ProfileDescr:
//	%+D         ed(F,"Profile "); pos:=F.pos; EdTag(F,GetTag(x)); i:=0;
//	%+D         if x qua ProfileDescr.WithExit <> 0 then Ed(F," WithExit ")
//	%+D         else Ed(F," Export: "); EdType(F,x.type) endif;
//	%+D         PrintOut(F);
//	%+D         repeat while i < x qua ProfileDescr.npar
//	%+D         do setpos(F,pos); ed(F,"Import: ");
//	%+D            EdType(F,x qua ProfileDescr.par(i).type);
//	%+D            if x qua ProfileDescr.par(i).count <> 1
//	%+D            then Ed(F,", Count=");
//	%+D                 EdWrd(F,x qua ProfileDescr.par(i).count);
//	%+D            endif;
//	%+D            PrintOut(F); i:=i+1;
//	%+D         endrepeat;
//	%+D    when K_IntRoutine: ed(F,"Routine ");
//	%+D         if x qua IntDescr.adr.kind <> 0
//	%+D         then ed(F,"  Entry "); EdAdr(F,x qua IntDescr.adr) endif;
//	%+D    when K_ExtRoutine: ed(F,"Routine ");
//	%+D         ed(F," Entry "); EdAdr(F,Ext2MemAddr(x qua ExtDescr.adr))
//	%+D    when K_IntLabel: ed(F,"Label ");
//	%+D         if x qua IntDescr.adr.kind <> 0
//	%+D         then ed(F,"  Entry "); EdAdr(F,x qua IntDescr.adr) endif;
//	%+D    when K_ExtLabel: ed(F,"Label ");
//	%+D         ed(F," Entry ");EdAdr(F,Ext2MemAddr(x qua ExtDescr.adr));
//	%+D    when K_SwitchDescr:
//	%+D         i:=0; pos:=F.pos+7;
//	%+D         ed(F,"Switch "); 
//	%+D    when K_AddrBlock:
//	%+D         EnvDmpobj(F.key,x,objsize(x));
//
//	%+D    ---------     E d i t   S t a c k - i t e m     ---------
//
//	%+D    when K_ProfileItem:
//	%+D         if x qua ProfileItem.nasspar <> 0
//	%+D         then ed(F,"  nasspar=");
//	%+D              EdWrd(F,x qua ProfileItem.nasspar);
//	%+D         endif;
//	%+D    when K_Address:
//	%+D         ed(F,"Obj:");
//	%+D         case 0:2 (x qua Address.ObjState)
//	%+D         when FromConst:  ed(F,"Stacked=")
//	%+D         when Calculated: ed(F,"Stacked")  endcase;
//	%+D         edopr(F,x qua Address.Objadr); ed(F," ++ Atr:");
//	%+D         case 0:2 (x qua Address.AtrState)
//	%+D         when FromConst:  ed(F,"Stacked=")
//	%+D         when Calculated: ed(F,"Stacked+")  endcase;
//	%+D         EdWrd(F,x qua Address.Offset);
//	%+D    when K_Coonst:
//	%+D         ed(F,"Stacked = Coonst(");
//	%+D         EdValue(F,x.type,x qua Coonst.itm); edchar(F,')');
//	%+D    when K_Temp:   ed(F,"Stacked");
//	%+D    when K_Result: ed(F,"Result");
//
//	%+D    otherwise EnvDmpobj(F.key,x,objsize(x));
//	%+D              if status<>0 then IERR("SBASE.edit-3") endif;
//	%+D    endcase;
//	%+D E:end;
//
//	%title ***  O b j e c t    S i z e  ***
//
//	%+D Routine objsize; import ref(Object) x; export size s;
//	%+D begin range(0:MaxByte) kind;
//	%+D    if x=none then kind:=0; IERR("SBASE.objsize-1")
//	%+D    else kind:=x.kind; if kind > 127 then kind:=kind-128 endif;
//	%+D         if kind>K_Max then kind:=0; IERR("SBASE.objsize-2") endif;
//	%+D    endif;
//	%+D    case 0:K_Max (kind)
//	%+D    when K_BSEG:          s:=size(BSEG);
//	%+D    when K_Qfrm1:         s:=size(Qfrm1);
//	%+D    when K_Qfrm2:         s:=size(Qfrm2);
//	%+D    when K_Qfrm2b:        s:=size(Qfrm2b);
//	%+D    when K_Qfrm3:         s:=size(Qfrm3);
//	%+D    when K_Qfrm4:         s:=size(Qfrm4);
//	%+D    when K_Qfrm4b:        s:=size(Qfrm4b);
//	%+D    when K_Qfrm4c:        s:=size(Qfrm4c);
//	%+D    when K_Qfrm5:         s:=size(Qfrm5);
//	%+D    when K_Qfrm6:         s:=size(Qfrm6);
//	%+D    when K_GlobalVar,
//	%+D         K_IntLabel,
//	%+D         K_IntRoutine:    s:=size(IntDescr);
//	%+D    when K_ExternVar,
//	%+D         K_ExtLabel,
//	%+D         K_ExtRoutine:    s:=size(ExtDescr);
//	%+D    when K_Attribute,
//	%+D         K_Import,
//	%+D         K_Export,
//	%+D         K_LocalVar:      s:=size(LocDescr);
//	%+D    when K_TypeRecord:    s:=size(TypeRecord);
//	%+D    when K_RecordDescr:   s:=size(RecordDescr);
//	%+D    when K_ProfileDescr:  s:=size(ProfileDescr :
//	%+D                                         x qua ProfileDescr.npar);
//	%+D    when K_SwitchDescr:   s:=size(SwitchDescr);
//	%+D    when K_AddrBlock:     s:=size(AddrBlock);
//	%+D    when K_ProfileItem:   s:=size(ProfileItem);
//	%+D    when K_Address:       s:=size(Address);
//	%+D    when K_Coonst:        s:=size(Coonst);
//	%+D    when K_Temp,K_Result: s:=size(Temp);
//
//	%+D    when K_WordBlock:     s:=size(WordBlock);
//	%+D    when K_RefBlock:      s:=size(RefBlock);
//	%+D    otherwise EdWrd(errmsg,x.kind); s:=size(Object);
//	%+D              IERR("**Unknown Object Kind: **");
//	%+D    endcase;
//	%+D end;
//
//	%title ***  O b j e c t    K i n d  ***
//
//	%+D Routine EdKind; import ref(File) F; range(0:K_Max) k;
//	%+D begin infix(String) s;
//	%+D    case 0:K_Max (k)
//	%+D    when K_BSEG:          s:="BSEG";
//	%+D    when K_Qfrm1:         s:="Qfrm1";
//	%+D    when K_Qfrm2:         s:="Qfrm2";
//	%+D    when K_Qfrm2b:        s:="Qfrm2b";
//	%+D    when K_Qfrm3:         s:="Qfrm3";
//	%+D    when K_Qfrm4:         s:="Qfrm4";
//	%+D    when K_Qfrm4b:        s:="Qfrm4b";
//	%+D    when K_Qfrm4c:        s:="Qfrm4c";
//	%+D    when K_Qfrm5:         s:="Qfrm5";
//	%+D    when K_Qfrm6:         s:="Qfrm6";
//	%+D    when K_GlobalVar:     s:="GlobalVar";
//	%+D    when K_IntLabel:      s:="IntLabel";
//	%+D    when K_IntRoutine:    s:="IntRoutine";
//	%+D    when K_ExternVar:     s:="ExternVar";
//	%+D    when K_ExtLabel:      s:="ExtLabel";
//	%+D    when K_ExtRoutine:    s:="ExtRoutine";
//	%+D    when K_Attribute:     s:="Attribute";
//	%+D    when K_Import:     s:="Parameter";
//	%+D    when K_Export:        s:="Export";
//	%+D    when K_LocalVar:      s:="LocDescr";
//	%+D    when K_TypeRecord:    s:="TypeRecord";
//	%+D    when K_RecordDescr:   s:="RecordDescr";
//	%+D    when K_ProfileDescr:  s:="ProfileDescr";
//	%+D    when K_SwitchDescr:   s:="SwitchDescr";
//	%+D    when K_AddrBlock:     s:="AddrBlock";
//	%+D    when K_ProfileItem:   s:="ProfileItem";
//	%+D    when K_Address:       s:="Address";
//	%+D    when K_Coonst:        s:="Coonst";
//	%+D    when K_Temp:          s:="Temp";
//	%+D    when K_Result:        s:="Result";
//	%+D    when K_WordBlock:     s:="WordBlock";
//	%+D    when K_RefBlock:      s:="RefBlock";
//	%+D    otherwise s:="Object" endcase;
//	%+D    EdWrd(F,k); EdChar(F,':'); Ed(F,s);
//	%+D end;
//	%title ***  B I G    D U M P  ***
//	%+D Visible Routine BIGDMP; import infix(String) s;
//	%+D begin short integer i; ref(Object) x;
//	%+D    x:=PoolTop; outstring(s); outimage;
//	%+D    outstring("   PoolTop: "); edref(sysout,PoolTop);
//	%+D    outstring("   PoolNxt: "); edref(sysout,PoolNxt);
//	%+D    outstring("   PoolBot: "); edref(sysout,PoolBot); outimage;
//
//	%+D    outstring("scode       ");    outword(scode); outimage;
//	%+D    outstring("modoupt     ");    outword(modoupt); outimage;
//	%+D    outstring("modinpt     ");    outword(modinpt); outimage;
//	%+D    outstring("scrfile     ");    outword(scrfile); outimage;
//	%+D    outstring("objfile     ");    outword(objfile); outimage;
//	%+D    outstring("sysout      ");    edref(sysout,sysout); outimage;
//	%+D    outstring("inptrace    ");    edref(sysout,inptrace); outimage;
//	%+D    outstring("ouptrace    ");    edref(sysout,ouptrace); outimage;
//	%+D    outstring("sysedit     ");    edref(sysout,sysedit); outimage;
//	%+D    outstring("errmsg      ");    edref(sysout,errmsg); outimage;
//
//	%+D    outstring("ProgIdent  ");     outsymb(ProgIdent); outimage;
//	%+D    outstring("modident    ");    outsymb(modident); outimage;
//	%+D    outstring("modcheck    ");    outsymb(modcheck); outimage;
//	%+D    outstring("DsegEntry  ");     outsymb(DsegEntry); outimage
//	%+D    outstring("MainEntry  ");     outadr(MainEntry); outimage;
//	%+DS   outstring("X_INITO     ");    outadr(X_INITO); outimage;
//	%+DS   outstring("X_GETO      ");    outadr(X_GETO); outimage;
//	%+DS   outstring("X_SETO      ");    outadr(X_SETO); outimage;
//	%+D    outstring("ntype       ");    outword(ntype); outimage;
//	%+D    outstring("TTAB        ");    i:=-1;
//	%+D    repeat i:=i+1 while i < MaxType
//	%+D    do edatatype(sysout,TTAB(i)); outchar(' ') endrepeat;
//	%+D    outimage;
//	%+D    outstring("nerr        ");    outword(nerr); outimage;
//	%+D    outstring("curline     ");    outint(curline); outimage;
//	%+D    outstring("curinstr    ");    outword(curinstr); outimage;
//	%+D    outstring("InputTrace ");     outword(InputTrace); outimage;
//	%+D    outstring("listsw      ");    outword(listsw); outimage;
//	%+D    outstring("TraceMode   ");    outword(TraceMode); outimage;
//	%+D    outstring("ModuleTrace");     outword(ModuleTrace);
//	%+D    outimage;
//	%+D    outstring("InitTime   ");     edreal(sysout,InitTime); outimage;
//	%+D    outstring("errormode   ");    edbool(sysout,errormode); outimage;
//	%+D    outstring("InsideRoutine");   edbool(sysout,InsideRoutine);
//	%+D    outimage;
//	%+D    outstring("nSubSeg    ");     outword(nSubSeg); outimage;
//	%+D    outstring("curseg      ");    edref(sysout,curseg); outimage;
//	%+D    outstring("FreeSeg    ");     edref(sysout,FreeSeg); outimage;
//	%+D    outstring("PoolTop    ");     edref(sysout,PoolTop); outimage;
//	%+D    outstring("PoolNxt    ");     edref(sysout,PoolNxt); outimage;
//	%+D    outstring("PoolBot    ");     edref(sysout,PoolBot); outimage;
//	%+D    outstring("FreeObj    ");     i:=-1; repeat i:=i+1 while i<K_Max
//	%+D    do edref(sysout,FreeObj(i));  outchar(' ') endrepeat;
//	%+D    outimage;
//
//	%+D    outstring("TLIST       ");    outword(TLIST); outimage;
//	%+D    outstring("MXXERR      ");    outword(MXXERR); outimage;
//	%+DS   outstring("SYSGEN      ");    outword(SYSGEN); outimage;
//
//	%+D    outstring("PROGID      ");    outsymb(PROGID); outimage;
//	%+D    outstring("SCODID      ");    outsymb(SCODID); outimage;
//	%+D    outstring("ATTRID      ");    outsymb(ATTRID); outimage;
//	%+D    outstring("RELID       ");    outsymb(RELID); outimage;
//	%+D    outstring("SCRID       ");    outsymb(SCRID); outimage;
//	%+DA   outstring("ASMID       ");    outsymb(ASMID); outimage;
//
//	%+D    outstring("SymbSequ   ");     outword(SymbSequ); outimage;
//
//	%+D    outstring("SK1LIN      ");    outint(SK1LIN); outimage;
//	%+D    outstring("SK1TRC      ");    outint(SK1TRC); outimage;
//	%+D    outstring("SK2LIN      ");    outint(SK2LIN); outimage;
//	%+D    outstring("SK2TRC      ");    outint(SK2TRC); outimage;
//
//	%+D    outstring("TAGTAB      ");    edref(sysout,TAGTAB); outimage;
//
//	%+D    repeat while x.kind <> 0
//	%+D    do if x.kind < K_Max
//	%+D       then edref(sysout,x); outstring("  ******   Object kind=");
//	%+D            outword(x.kind); outimage; print(x);
//	%+D       endif;
//	%+D       x:=x+objsize(x);
//	%+D    endrepeat;
//	%+D end;
//	%title ***   C A P D U M P   ***
//	%+D Visible routine CAPDUMP;
//	%+D begin range(0:K_Max) k; size plng,pused; infix(WORD) tx,sx,segid;
//	%+D   range(0:MaxWord) n; ref(FreeObject) x;
//	%+D   plng:=PoolBot-PoolTop; pused:=PoolNxt-PoolTop; outimage;
//	%+D   outstring("****  S T O R A G E   S T A T I S T I C S ****");
//	%+D   outimage; outstring("LINE/"); outint(curline);
//	%+D   if ModIdent.val <> 0
//	%+D   then outstring(" MODULE/"); outsymb(ModIdent) endif;
//	%+D   if MainEntry.kind <> 0
//	%+D   then outstring(" ENT/"); outadr(MainEntry) endif;
//	%+D   outimage;
//	%+D   sx.val:=0; repeat while sx.val < DIC.nSegm
//	%+D   do sx.val:=sx.val+1; segid:=DIC.Segm(sx.HI).elt(sx.LO);
//	%+D      if DICREF(segid) qua Segment.rela.val <> 0
//	%+D      then outsymb(segid); outchar('/');
//	%+D           outword(DICREF(segid) qua Segment.rela.val);
//	%+D           outchar(' ');
//	%+D      endif;
//	%+D   endrepeat;
//	%+D   outimage; n:=0;
//	%+D   repeat while n < npool
//	%+D   do outstring("Storage Pool: "); EdRef(sysout,PoolTab(n).PoolTop);
//	%+D      outstring("   Size="); EdSize(sysout,PoolTab(n).PoolSize);
//	%+D      outimage; n:=n+1;
//	%+D   endrepeat;
//	%+D   outstring("TAG/"); tx:=GetLastTag; outword(tx.val);
//	%+D   outstring(" TYP/");  outword(nType);
//	%+D   outstring(" FIX/");  outword(nFix);
//	%+D   outstring(" SYMB/"); outword(DIC.nSymb);
//	%+D   outstring(" SEG/");  outword(DIC.nSegm);
//	%+D   outstring(" PUBL/"); outword(DIC.nPubl);
//	%+D   outstring(" EXTR/"); outword(DIC.nExtr);
//	%+D   outstring(" MODL/"); outword(DIC.nModl);
//	%+D   outimage; outimage;
//	%+D   k:=0; repeat while k < K_Max
//	%+D   do n:=0; if FreeObj(k) <> none
//	%+D      then x:=FreeObj(k);
//	%+D           repeat while x <> none do n:=n+1; x:=x.next endrepeat;
//	%+D      endif;
//	%+D      if sysout.pos > 51 then printout(sysout)
//	%+D      elsif sysout.pos > 26 then setpos(sysout,50)
//	%+D      elsif sysout.pos > 2  then setpos(sysout,25) endif;
//	%+D      EdKind(sysout,k); outchar('/'); outword(n);
//	%+D      if ObjCount(k) <> n
//	%+D      then outchar(':'); outword(ObjCount(k)) endif;
//	%+D      k:=k+1;
//	%+D   endrepeat;
//	%+D   outimage; outimage;
//	%+D end;
//
//	%title ***   E D R U T N A M   ***
//	%+D Routine EdRutNam; import ref(File) F; range(0:R_Max) k;
//	%+D begin case 0:R_Max (k)
//	%+D       ------  SBASE  ------
//	-- %+D       when R_DefType: Ed(F,"DefType")
//	%+D       when R_DICREF: Ed(F,"DICREF")
//	-- %+D       when R_DICSMB: Ed(F,"DICSMB")
//	-- %+D       when R_DefSymb: Ed(F,"DefSymb")
//	-- %+D       when R_DefSegm: Ed(F,"DefSegm")
//	-- %+D       when R_PutSegx: Ed(F,"PutSegx")
//	-- %+D       when R_GetSegx: Ed(F,"GetSegx")
//	-- %+D       when R_DefExtr: Ed(F,"DefExtr")
//	-- %+D       when R_PutExtern: Ed(F,"PutExtern")
//	-- %+D       when R_DefPubl: Ed(F,"DefPubl")
//	-- %+D       when R_PutPublic: Ed(F,"PutPublic")
//	-- %+D       when R_NewPubl: Ed(F,"NewPubl")
//	-- %+D       when R_DefModl: Ed(F,"DefModl")
//	-- %+D       when R_PutModule: Ed(F,"PutModule")
//	%+D       when R_GetDefaultSreg: Ed(F,"GetDefaultSreg")
//	%+D       when R_OverrideSreg: Ed(F,"OverrideSreg")
//	%+D       when R_MakeRegmap: Ed(F,"MakeRegmap")
//	-- %+D       when R_SamePart: Ed(F,"SamePart")
//	%+D       when R_RegDies: Ed(F,"RegDies")
//	%+D       when R_RegxAvailable: Ed(F,"RegxAvailable")
//	-- %+D       when R_IntoDisplay: Ed(F,"IntoDisplay")
//	-- %+D       when R_GetRec: Ed(F,"GetRec")
//	-- %+D       when R_GetAtr: Ed(F,"GetAtr")
//	-- %+D       when R_GetPrf: Ed(F,"GetPrf")
//	-- %+D       when R_GetRut: Ed(F,"GetRut")
//	-- %+D       when R_GetLab: Ed(F,"GetLab")
//	%+D       when R_NEWOBX: Ed(F,"NEWOBX")
//	%+D       when R_NEWOBJ: Ed(F,"NEWOBJ")
//	%+D       when R_DELETE: Ed(F,"DELETE")
//	%+D       ------  COASM  ------ ------  COASM  ------
//	%+D       when R_iCodeSize: Ed(F,"iCodeSize")
//	-- %+D       when R_ShrtJMP: Ed(F,"ShrtJMP")
//	%+D       when R_EmitSOP: Ed(F,"EmitSOP")
//	-- %+D       when R_EmitCall: Ed(F,"EmitCall")
//	-- %+D       when R_EmitAddr: Ed(F,"EmitAddr")
//	-- %+D       when R_EncodeEA: Ed(F,"EncodeEA")
//	-- %+D       when R_ModifySP: Ed(F,"ModifySP")
//	-- %+D       when R_LoadCnst: Ed(F,"LoadCnst")
//	-- %+D       when R_EmitJMP: Ed(F,"EmitJMP")
//	%+D       when R_QtoI: Ed(F,"QtoI")
//	%+D       ------  MASSAGE  ------ ------  MASSAGE  ------
//	%+D       when R_Massage: Ed(F,"Massage")
//	%+D       when R_PrevQinstr: Ed(F,"PrevQinstr")
//	%+D       when R_AppendQinstr: Ed(F,"AppendQinstr")
//	%+D       when R_InsertQinstr: Ed(F,"InsertQinstr")
//	%+D       when R_DeleteQinstr: Ed(F,"DeleteQinstr")
//	%+D       when R_DeleteQPosibJ: Ed(F,"DeleteQPosibJ")
//	-- %+D       when R_MoveFdest: Ed(F,"MoveFdest")
//	-- %+D       when R_QinstrEqual: Ed(F,"QinstrEqual")
//	-- %+D       when R_OprEqual: Ed(F,"OprEqual")
//	-- %+D       when R_ForwJMP: Ed(F,"ForwJMP")
//	-- %+D       when R_DefFDEST: Ed(F,"DefFDEST")
//	-- %+D       when R_DefBDEST: Ed(F,"DefBDEST")
//	%+D       when R_RegsWrittenDies: Ed(F,"RegsWrittenDies")
//	%+D       when R_RegLastused: Ed(F,"RegLastused")
//	%+D       when R_RegLastWrite: Ed(F,"RegLastWrite")
//	%+D       when R_RegOneshot: Ed(F,"RegOneshot")
//	%+D       when R_StackModification: Ed(F,"StackModification")
//	-- %+D       when R_FindPush2: Ed(F,"FindPush2")
//	%+D       when R_FindPush: Ed(F,"FindPush")
//	%+D       when R_StackEqual: Ed(F,"StackEqual")
//	%+D       when R_RegsReadUnmodified: Ed(F,"RegsReadUnmodified")
//	%+D       when R_RegUnused: Ed(F,"RegUnused")
//	%+D       when R_OperandregsModified: Ed(F,"OperandregsModified")
//	-- %+D       when R_MemoryUse: Ed(F,"MemoryUse")
//	-- %+D       when R_MemoryUnused: Ed(F,"MemoryUnused")
//	-- %+D       when R_MemoryLastused: Ed(F,"MemoryLastused")
//	-- %+D       when R_mOPR: Ed(F,"mOPR")
//	-- %+D       when R_mLOADSC_2: Ed(F,"mLOADSC_2")
//	-- %+D       when R_mPUSHR: Ed(F,"mPUSHR")
//	-- %+D       when R_mPUSHM: Ed(F,"mPUSHM")
//	-- %+D       when R_mPOPK: Ed(F,"mPOPK")
//	-- %+D       when R_mPOPR: Ed(F,"mPOPR")
//	-- %+D       when R_mPOPR_2: Ed(F,"mPOPR_2")
//	-- %+D       when R_mPOPM: Ed(F,"mPOPM")
//	-- %+D       when R_mLOADC: Ed(F,"mLOADC")
//	-- %+D       when R_mLOADSC: Ed(F,"mLOADSC")
//	-- %+D       when R_mLOAD: Ed(F,"mLOAD")
//	-- %+D       when R_mLDS: Ed(F,"mLDS")
//	-- %+D       when R_mLES: Ed(F,"mLES")
//	-- %+D       when R_mLOADA: Ed(F,"mLOADA")
//	-- %+D       when R_mMOV: Ed(F,"mMOV")
//	-- %+D       when R_mSTORE: Ed(F,"mSTORE")
//	-- %+D       when R_mMONADR: Ed(F,"mMONADR")
//	-- %+D       when R_QinstrBefore: Ed(F,"QinstrBefore")
//	-- %+D       when R_TryReverse: Ed(F,"TryReverse")
//	-- %+D       when R_mDYADR: Ed(F,"mDYADR")
//	-- %+D       when R_mDYADC: Ed(F,"mDYADC")
//	-- %+D       when R_mTRIADR: Ed(F,"mTRIADR")
//	-- %+D       when R_mTRIADM: Ed(F,"mTRIADM")
//	-- %+D       when R_mFDYAD: Ed(F,"mFDYAD")
//	-- %+D       when R_mFDYADrev: Ed(F,"mFDYADrev")
//	-- %+D       when R_mCondition: Ed(F,"mCondition")
//	-- %+D       when R_mJMP: Ed(F,"mJMP")
//	-- %+D       when R_mFDEST: Ed(F,"mFDEST")
//	%+D       ------  CODER  ------ ------  CODER  ------
//	%+D       when R_Push: Ed(F,"Push")
//	-- %+D       when R_Precede: Ed(F,"Precede")
//	%+D       when R_Pop: Ed(F,"Pop")
//	%+D       when R_TakeTOS: Ed(F,"TakeTOS")
//	%+D       when R_TakeRef: Ed(F,"TakeRef")
//	-- %+D       when R_CopyBSEG: Ed(F,"CopyBSEG")
//	%+D       when R_AssertObjStacked: Ed(F,"AssertObjStacked")
//	%+D       when R_AssertAtrStacked: Ed(F,"AssertAtrStacked")
//	%+D       when R_PresaveOprRegs: Ed(F,"PresaveOprRegs")
//	%+D       when R_GetTosAddr: Ed(F,"GetTosAddr")
//	%+D       when R_GetSosAddr: Ed(F,"GetSosAddr")
//	%+D       when R_GetTosValueIn86R3: Ed(F,"GetTosValueIn86R3")
//	%+D       when R_GetTosValueIn86: Ed(F,"GetTosValueIn86")
//	%+D       when R_GetTosAdjustedIn86: Ed(F,"GetTosAdjustedIn86")
//	-- %+D       when R_GetTosAsBYT1: Ed(F,"GetTosAsBYT1")
//	-- %+D       when R_GetTosAsBYT2: Ed(F,"GetTosAsBYT2")
//	-- %+D       when R_GetTosAsBYT4: Ed(F,"GetTosAsBYT4")
//	%+D       when R_GQfetch: Ed(F,"GQfetch")
//	%+D       when R_GQdup: Ed(F,"GQdup")
//	-- %+D       when R_DupIn86: Ed(F,"DupIn86")
//	%+D       when R_GQpop: Ed(F,"GQpop")
//	%+D       when R_GetOprAddr: Ed(F,"GetOprAddr")
//	-- %+D       when R_ArithType: Ed(F,"ArithType")
//	%+D       ------  PARSE  ------ ------  PARSE  ------
//	-- %+D       when R_SpecLab: Ed(F,"SpecLab")
//	-- %+D       when R_DefLab: Ed(F,"DefLab")
//	-- %+D       when R_Viisible: Ed(F,"Viisible")
//	-- %+D       when R_ProgramElement: Ed(F,"ProgramElement")
//	-- %+D       when R_Instruction: Ed(F,"Instruction")
//	-- %+D       when R_CallInstruction: Ed(F,"CallInstruction")
//	-- %+D       when R_WordsOnStack: Ed(F,"WordsOnStack")
//	-- %+D       when R_MoveOnStack: Ed(F,"MoveOnStack")
//	-- %+D       when R_CallDefault: Ed(F,"CallDefault")
//	-- %+D       when R_PopExport: Ed(F,"PopExport")
//	-- %+D       when R_PushExport: Ed(F,"PushExport")
//	%+D       when R_PutPar: Ed(F,"PutPar")
//	-- %+D       when R_ParType: Ed(F,"ParType")
//	-- %+D       when R_ConvRepWRD2: Ed(F,"ConvRepWRD2")
//	-- %+D       when R_ConvRepWRD4: Ed(F,"ConvRepWRD4")
//	-- %+D       when R_IfConstruction: Ed(F,"IfConstruction")
//	-- %+D       when R_SkipifConstruction: Ed(F,"SkipifConstruction")
//	-- %+D       when R_ProtectConstruction: Ed(F,"ProtectConstruction")
//	%+D       endcase;
//	%+D end;
//	%title ***   R U T D U M P   ***
//	%+D Visible routine RUTDUMP;
//	%+D begin range(0:R_Max) k; integer cnt;
//	%+D   k:=0; cnt:=0; repeat k:=k+1 while k < R_Max
//	%+D   do if CalCount(k) > cnt then cnt:=CalCount(k) endif endrepeat;
//	%+D   outstring("****  C A L L   S T A T I S T I C S ****");
//	%+D   outimage; k:=0; cnt:=cnt/20;
//	%+D   repeat k:=k+1 while k < R_Max
//	%+D   do if CalCount(k) > cnt 
//	%+D      then if sysout.pos>61 then outimage
//	%+D           elsif sysout.pos>2 then outstring("  ") endif;
//	%+D           outword(k); outchar(':');
//	%+D           EdRutNam(sysout,k); outchar('/');
//	%+D           outint(CalCount(k));
//	%+D      endif;
//	%+D   endrepeat;
//	%+D   outimage; outimage;
//	%+D end;
//
//	end;

}
