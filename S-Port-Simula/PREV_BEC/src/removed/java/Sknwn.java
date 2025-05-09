package removed.java;

public class Sknwn {
//	 Module SKNWN("iAPX");
//	 begin insert SCOMN;
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
//	       ---              S y s t e m   a n d   K n o w n              ---
//	       ---                                                           ---
//	       ---                      R o u t i n e s                      ---
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
//	%-E Visible Define AllignFac=2;
//	%+E Visible Define AllignFac=4;
//
//	Visible Macro PutLow(2);    begin wOR(%1,%2)           endmacro;
//	Visible Macro PutHigh(2);   begin wOR(wSHL(%1,8),%2)   endmacro;
//	Visible Macro GetLow(1);    begin wAND(%1,255)         endmacro;
//	Visible Macro GetHigh(1);   begin wAND(wSHR(%1,8),255) endmacro;
//	Visible Macro LowPart(1);   begin bAND(%1,3) endmacro;
//	Visible Macro HighPart(1);  begin (bAND(%1,3)+4) endmacro;
//	Visible Macro HighPartL(1); begin (%1+4) endmacro;
//	Visible Macro iReg(1);      begin bAND(%1,7) endmacro;
//	Visible Macro sReg(1);      begin bAND(%1,3) endmacro;
//
//	Visible Macro wAllign(1);
//	begin wAND(%1+(AllignFac-1),-AllignFac) endmacro;
//
//	Visible Macro Alligned(1);begin (bAND(%1,AllignFac-1)=0) endmacro;
//	Visible Macro UnAlligned(1);begin (bAND(%1,AllignFac-1)<>0) endmacro;
//
//	Visible Macro AllignDiff(1);
//	begin (bAND(AllignFac-(%1),AllignFac-1)) endmacro;
//	%title ***   T h e   E n v i r o n m e n t   I n t e r f a c e   ***
//
//	Visible Sysroutine("INITIA") EnvInit;
//	import entry(perhandl) exhandl end;
//
//	Visible Sysroutine("TERMIN") EnvTerm;
//	import range(0:3) code; infix(string) msg end;
//
//	Visible Sysroutine("GINTIN") EnvGetIntInfo;
//	import range(0:21) indx; export integer result end;
//
//	Visible Sysroutine("CPUTIM") EnvGetCpuTime;
//	export long real result end;
//
//	Visible Sysroutine("SIZEIN") EnvGetSizeInfo;
//	import range(0:127) indx; range(0:MaxByte) sequ;
//	export size result end;
//
//	Visible Sysroutine("GTEXIN") EnvGetTextInfo;
//	import range(0:127) indx; infix(string) item;
//	export integer filled end;
//
//	Visible Sysroutine("GVIINF") EnvGiveIntInfo;
//	import range(0:127) indx; integer inform end;
//
//	Visible Sysroutine("GIVINF") EnvGiveTextInfo;
//	import range(0:127) indx; infix(string) inform end;
//
//	Visible Sysroutine("DWAREA") EnvArea;
//	import size lng; range(0:MaxByte) sequ; export ref() top end;
//
//
//	---   F  i  l  e     H  a  n  d  l  i  n  g   ---
//
//	Visible Sysroutine("OPFILE") EnvOpen;
//	import infix(string) dsetspec;
//	       range(0:7) dsettype;
//	       infix(string) action;
//	       integer imlng;
//	export range(0:MaxKey) filekey;
//	end;
//
//	Visible Sysroutine("CLFILE") EnvClose;
//	import range(1:MaxKey) filekey; infix(string) action end;
//
//	Visible Sysroutine("INIMAG") EnvInImage;
//	import range(1:MaxKey) filekey; infix(string) image;
//	export integer filled end;
//
//	Visible Sysroutine("OUTIMA") EnvOutImage;
//	import range(1:MaxKey) filekey; infix(string) image end;
//
//	Visible Sysroutine("GDSNAM") EnvDsetName;
//	import range(1:MaxKey) filekey;
//	       infix(string) dsetname;
//	export integer filled end;
//
//	Visible Sysroutine("GDSPEC") EnvDsetSpec;
//	import range(1:3) code;
//	       infix(string) dsetspec;
//	export integer filled end;
//
//	Visible Sysroutine("PRINTO") EnvPrint;
//	import range(1:MaxKey) filekey;
//	       infix(string) image; integer spc end;
//
//	Visible Sysroutine("LOCATE") EnvLocate;
//	import range(1:MaxKey) filekey; integer loc end;
//
//	Visible Sysroutine("GETLOC") EnvLocation;
//	import range(1:MaxKey) filekey; export integer loc end;
//
//	Visible Sysroutine("OUTBYT") EnvOutByte;
//	import range(1:MaxKey) filekey; range(0:MaxByte) byte end;
//
//	Visible Sysroutine("INBYTE") EnvInByte;
//	import range(1:MaxKey) filekey; export range(0:MaxByte) byte end;
//
//	Visible Known("OUT2BY") EnvOut2byte;
//	import range(1:MaxKey) key; range(0:MaxWord) v;
//	begin EnvOutByte(key,GetHigh(%v%));
//	      EnvOutByte(key,GetLow(%v%));
//	end;
//
//	Visible Known("IN2BYT") EnvIn2byte;
//	import range(1:MaxKey) key; export range(0:MaxWord) v;
//	begin v:=PutHigh(%EnvInByte(key)%,%EnvInByte(key)%) end;
//
//	Visible Known("BINTXT") EnvInbytes;
//	import range(1:255) key; infix(string) dst; export short integer filled;
//	begin character c; filled:=0;
//	      repeat while filled < dst.nchr
//	      do c:=EnvInbyte(key) qua character;
//	         if status <> 0 then goto E endif;
//	         var(dst.chradr)(filled):=c; filled:=filled+1;
//	      endrepeat;
//	E:end;
//
//	Visible Known("BOUTXT") EnvOutbytes;
//	import range(1:255) key; infix(string) src;
//	begin range(0:MaxWord) i; i:=0;
//	      repeat while i < src.nchr
//	      do EnvOutByte(key,var(src.chradr)(i) qua integer);
//	         if status <> 0 then goto E endif;
//	         i:=i+1;
//	      endrepeat;
//	E:end;
//	%page
//	Visible Sysroutine("LOWTEN") EnvLTEN;
//	import character c  end;
//
//	   ---   D e - e d i t i n g   r o u t i n e s   ---
//
//	Visible Sysroutine("GETINT") EnvGetInt;
//	import infix(string) item; export integer val end;
//
//	Visible Sysroutine("GTREAL") EnvGetReal;
//	import infix(string) item; export long real val end;
//
//
//	   ---      E d i t i n g    R o u t i n e s     ---
//
//	Visible Sysroutine("PUTINT") EnvPutInt;
//	import infix(string) item; integer val end;
//
//	Visible Sysroutine("PUTFIX") EnvPutFix;
//	import infix(string) item; real val; integer frac end;
//
//	Visible Sysroutine("PTREAL") EnvPutReal;
//	import infix(string) item; real val; integer frac end;
//
//	Visible Sysroutine("PLREAL") EnvPutLreal;
//	import infix(string) item; long real val; integer frac end;
//
//	Visible Sysroutine("PTSIZE") EnvPutSize;
//	import infix(string) item; size val end;
//
//	Visible Sysroutine("PTOADR") EnvPutOaddr;
//	import infix(string) item; ref() val end;
//
//
//	   ---      U t i l i t y   R o u t i n e s      ---
//
//	Visible Sysroutine("DATTIM") EnvTimDat;
//	import infix(string) item; export integer filled end;
//
//	%+D  Visible Sysroutine("DMPOBJ") EnvDmpobj;
//	%+D  import range(1:MaxKey) filekey; ref() obj; size lng end;
//
//	%title ***  B a s i c    U t i l i e s  ***
//	Visible Sysroutine("S?BOBY") BOOL2BYTE;
//	import Boolean b; export range(0:MaxByte) v;
//	-- Inline q-Coding: No Code
//	end;
//
//	Visible Sysroutine("S?BYBO") BYTE2BOOL;
//	import range(0:MaxByte) v; export Boolean b;
//	-- Inline q-Coding: No Code
//	end;
//
//	Visible Sysroutine("S?SZ2W") Size2Word;
//	import size s; export range(0:MaxWord) w;
//	-- Inline q-Coding: No Code
//	end;
//
//	Visible Sysroutine("S?W2SZ") Word2Size;
//	import range(0:MaxWord) w; export size s;
//	-- Inline q-Coding: No Code
//	end;
//
//	Visible Sysroutine("S?RF2N") Ref2Name;
//	import ref() rf; export name() n;
//	--  Inline q-Coding: for MS-DOS,OS-2,XENIX
//	-------------------------------------
//	--   PUSHC   AX,0                  --
//	-------------------------------------
//	--  Inline q-Coding: for UNIX
//	-------------------------------------
//	--   PUSHC   EAX,0                 --
//	-------------------------------------
//	end;
//
//	Visible Sysroutine("S?N2RF") Name2Ref;
//	import name() n; export ref() rf;
//	--  Inline q-Coding: for MS-DOS,OS-2,XENIX
//	-------------------------------------
//	--   POP     AX                    --
//	--   POP     DX                    --
//	--   ADD     AX,DX                 --
//	--   PUSH    AX                    --
//	-------------------------------------
//	--  Inline q-Coding: for UNIX
//	-------------------------------------
//	--   POP     EAX                   --
//	--   POP     EDX                   --
//	--   ADD     EAX,EDX               --
//	--   PUSH    EAX                   --
//	-------------------------------------
//	end;
//	%page
//
//	Visible Sysroutine("S?BNOT") bNOT;
//	import range(0:100) i; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WNOT") wNOT;
//	import range(0:16000) i; export range(0:16000) res  end;
//
//	--  Inline q-Coding: for MS-DOS,OS-2,XENIX
//	-------------------------------------------------
//	--  POP  AL       POP  AX       POP  AX        --
//	--  NOT  AL       NOT  AX       POP  DX        --
//	--  PUSH AL       PUSH AX       NOT  AX        --
//	--                              NOT  DX        --
//	--                              PUSH DX        --
//	--                              PUSH AX        --
//	-------------------------------------------------
//
//	--  Inline q-Coding: for UNIX
//	-------------------------------------------------
//	--  POP  AL       POP  AX       POP  EAX       --
//	--  NOT  AL       NOT  AX       NOT  EAX       --
//	--  PUSH AL       PUSH AX       PUSH EAX       --
//	-------------------------------------------------
//	%page
//
//	Visible Sysroutine("S?BAND") bAND;
//	import range(0:100) i,j; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WAND") wAND;
//	import range(0:16000) i,j; export range(0:16000) res  end;
//
//	Visible Sysroutine("S?BOR") bOR;
//	import range(0:100) i,j; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WOR") wOR;
//	import range(0:16000) i,j; export range(0:16000) res  end;
//
//	Visible Sysroutine("S?BXOR") bXOR;
//	import range(0:100) i,j; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WXOR") wXOR;
//	import range(0:16000) i,j; export range(0:16000) res  end;
//
//	--  Inline q-Coding: for MS-DOS,OS-2,XENIX
//	-----------------------------------------------------------------------
//	--    POP      AL           POP      AX           POP      AX       --
//	--    POP      CL           POP      CX           POP      DX       --
//	--  AND/OR/XOR AL,CL      AND/OR/XOR AX,CX        POP      BX       --
//	--    PUSH     AL           PUSH     AX           POP      CX       --
//	--                                              AND/OR/XOR AX,BX    --
//	--                                              AND/OR/XOR DX,CX    --
//	--                                                PUSH     DX       --
//	--                                                PUSH     AX       --
//	-----------------------------------------------------------------------
//
//	--  Inline q-Coding: for UNIX
//	-----------------------------------------------------------------------
//	--    POP      AL           POP      AX           POP      EAX       --
//	--    POP      CL           POP      CX           POP      ECX       --
//	--  AND/OR/XOR AL,CL      AND/OR/XOR AX,CX      AND/OR/XOR EAX,ECX   --
//	--    PUSH     AL           PUSH     AX           PUSH     EAX       --
//	-----------------------------------------------------------------------
//	%page
//
//	Visible Sysroutine("S?BSHL") bSHL;
//	import range(0:100) i,j; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WSHL") wSHL;
//	import range(0:16000) i,j; export range(0:16000) res  end;
//
//	Visible Sysroutine("S?BSHR") bSHR;
//	import range(0:100) i,j; export range(0:100) res  end;
//
//	Visible Sysroutine("S?WSHR") wSHR;
//	import range(0:16000) i,j; export range(0:16000) res  end;
//
//	--  Inline q-Coding:
//	------------------------------------------------------------------------
//	--   POP     CL                     POP     CX                        --
//	--   POP     AL                     POP     AX                        --
//	--   SHL/SHR AL,CL                  SHL/SHR AX,CL                     --
//	--   PUSH    AL                     PUSH    AX                        --
//	------------------------------------------------------------------------
//	%title *** Byte String Compare Equal ***
//	Visible Sysroutine("S?SCMPEQ") APX_SCMPEQ;
//	import range(0:MaxWord) lng;       -- Length
//	       Name(Character) str1;       -- Pointer to String 1
//	       Name(Character) str2;       -- Pointer to String 2
//	export Boolean res;                -- Result
//	-- begin repeat while lng > 0
//	--       do lng:=lng-1;
//	--          if var(str1)(lng) <> var(str2)(lng)
//	--          then res:=false; goto E endif;
//	--       endrepeat;
//	--       res:=true;
//	-- E:
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   SI                                                          --
//	-- DYADR  ADD,SI,AX       ; SI <- str2.ofst + str2.attr               --
//	-- POPR   DS              ; DS <- str2.seg                            --
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- str1.ofst + str1.attr               --
//	-- POPR   ES              ; ES <- str1.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- RSTRB  RCMPS,CLD,REPEQ ; DS:[SI] cmp ES:[DI]; INC(SI,DI); CX times --
//	--   --   iCLD                                                        --
//	--   --   iREPE   CMPSB                                               --
//	--   --   iMOV    AL,0                                                --
//	--   --   iJNE    $+3                                                 --
//	--   --   iDEC    AX                                                  --
//	-- PUSHR  AL              ; Result                                    --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   ESI                                                         --
//	-- DYADR  ADD,ESI,EAX     ; ESI <- str2.ofst + str2.attr              --
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- str1.ofst + str1.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- RSTRB  RCMPS,CLD,REPEQ ; DS:[ESI] cmp ES:[EDI];                    --
//	--                        ; INC(ESI,EDI); ECX times                   --
//	--   --   iCLD                                                        --
//	--   --   iREPE   CMPSB                                               --
//	--   --   iMOV    AL,0                                                --
//	--   --   iJNE    $+3                                                 --
//	--   --   iDEC    AX                                                  --
//	-- PUSHR  AL              ; Result                                    --
//	------------------------------------------------------------------------
//	%title *** Byte String Move and Increment address ***
//
//	Visible Sysroutine("S?SMOVEI") APX_SMOVEI;
//	import range(0:MaxWord) lng;       -- Length
//	       Name(Character) dst;        -- Pointer to Destination
//	       Name(Character) src;        -- Pointer to Source
//	-- begin integer i; i:=0;
//	--       repeat while i < lng
//	--       do var(dst)(i):=var(src)(i); i:=i+1 endrepeat;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   SI                                                          --
//	-- DYADR  ADD,SI,AX       ; SI <- src.ofst + src.attr                 --
//	-- POPR   DS              ; DS <- src.seg                             --
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- src.ofst + src.attr                 --
//	-- POPR   ES              ; ES <- src.seg                             --
//	-- POPR   CX              ; CX <- length                              --
//	-- RSTRB  RMOV,CLD,REP    ; DS:[SI] -> ES:[DI]; INC(SI,DI); CX times  --
//	--   --   iCLD                                                        --
//	--   --   iREP    MOVSB                                               --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   ESI                                                         --
//	-- DYADR  ADD,ESI,EAX     ; ESI <- src.ofst + src.attr                --
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- src.ofst + src.attr                --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- RSTRB  RMOV,CLD,REP    ; DS:[ESI] -> ES:[EDI];                     --
//	--                        ; INC(ESI,EDI); ECX times                   --
//	--   --   iCLD                                                        --
//	--   --   iREP    MOVSB                                               --
//	------------------------------------------------------------------------
//	%title *** Byte String Move and Decrement address ***
//
//	Visible Sysroutine("S?SMOVED") APX_SMOVED;
//	import range(0:MaxWord) lng;       -- Length
//	       Name(Character) dst;        -- Pointer to Destination
//	       Name(Character) src;        -- Pointer to Source
//	-- begin integer i; i:=0;
//	--       repeat while i < lng
//	--       do i:=i+1; var(dst)(lng-i):=var(src)(lng-i) endrepeat;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   SI                                                          --
//	-- DYADR  ADD,SI,AX       ; SI <- src.ofst + src.attr                 --
//	-- POPR   DS              ; DS <- src.seg                             --
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- src.ofst + src.attr                 --
//	-- POPR   ES              ; ES <- src.seg                             --
//	-- POPR   CX              ; CX <- length                              --
//	-- RSTRB  RMOV,STD,REP    ; DS:[SI] -> ES:[DI]; DEC(SI,DI); CX times  --
//	--   --   iSTD                                                        --
//	--   --   iREP    MOVSB                                               --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   ESI                                                         --
//	-- DYADR  ADD,ESI,EAX     ; ESI <- src.ofst + src.attr                --
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- src.ofst + src.attr                --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- RSTRB  RMOV,STD,REP    ; DS:[ESI] -> ES:[EDI];                     --
//	--                        ; DEC(ESI,EDI); ECX times                   --
//	--   --   iSTD                                                        --
//	--   --   iREP    MOVSB                                               --
//	------------------------------------------------------------------------
//	%title *** Byte String Skip and Increment address ***
//
//	Visible Sysroutine("S?SSKIP") APX_SSKIP;
//	import Character c;            -- The character
//	       range(0:MaxWord) lng;   -- Length
//	       Name(Character) buff;   -- Pointer to Buffer
//	export range(0:MaxWord) rst;   -- No. of chars left after skip
//	-- begin integer i; i:=0;
//	--       repeat while lng > 0
//	--       do if var(buff)(i) <> c then goto E endif;
//	--          lng:=lng-1; i:=i+1 endrepeat;
//	-- E:    rst:=lng;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- buff.ofst + buff.attr               --
//	-- POPR   ES              ; ES <- buff.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- POPR   AL              ; AL <- the character                       --
//	-- RSTRB  RSCAS,CLD,REPEQ ; Compare AL -- ES:[DI]; INC(DI); CX times  --
//	--   --   iCLD                                                        --
//	--   --   iREPE   SCASB                                               --
//	--   --   iJE     $+3                                                 --
//	--   --   iINC    CX                                                  --
//	-- PUSHR  CX              ; CX <- length of resulting string          --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- buff.ofst + buff.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- POPR   AL              ; AL <- the character                       --
//	-- RSTRB  RSCAS,CLD,REPEQ ; AL cmp ES:[EDI]; INC(EDI); ECX times      --
//	--   --   iCLD                                                        --
//	--   --   iREPE   SCASB                                               --
//	--   --   iJE     $+3                                                 --
//	--   --   iINC    ECX                                                 --
//	-- PUSHR  ECX             ; ECX <- length of resulting string         --
//	------------------------------------------------------------------------
//	%title *** Byte String Strip and Decrement address ***
//
//	Visible Sysroutine("S?STRIP") APX_STRIP;
//	import Character c;            -- The character
//	       range(0:MaxWord) lng;   -- Length
//	       Name(Character) buff;   -- Pointer to Buffer
//	export range(0:MaxWord) rst;   -- No. of chars left after strip
//	-- begin integer i; i:=0;
//	--       repeat while lng > 0
//	--       do if var(buff)(i) <> c then goto E endif;
//	--          lng:=lng-1; i:=i-1 endrepeat;
//	-- E:    rst:=lng;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- buff.ofst + buff.attr               --
//	-- POPR   ES              ; ES <- buff.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- POPR   AL              ; AL <- the character                       --
//	-- RSTRB  RSCAS,STD,REPEQ ; Compare AL -- ES:[DI]; DEC(DI); CX times  --
//	--   --   iSTD                                                        --
//	--   --   iREPE   SCASB                                               --
//	--   --   iJE     $+3                                                 --
//	--   --   iINC    CX                                                  --
//	-- PUSHR  CX              ; CX <- length of resulting string          --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- buff.ofst + buff.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- POPR   AL              ; AL <- the character                       --
//	-- RSTRB  RSCAS,STD,REPEQ ; AL cmp ES:[EDI]; DEC(EDI); ECX times      --
//	--   --   iSTD                                                        --
//	--   --   iREPE   SCASB                                               --
//	--   --   iJE     $+3                                                 --
//	--   --   iINC    CX                                                  --
//	-- PUSHR  ECX             ; ECX <- length of resulting string         --
//	------------------------------------------------------------------------
//	%title *** Byte String Find and Increment address ***
//
//	Visible Sysroutine("S?SFINDI") APX_SFINDI;
//	import Character c;            -- The character
//	       range(0:MaxWord) lng;   -- Length
//	       Name(Character) buff;   -- Pointer to Buffer
//	export range(0:MaxWord) rst;   -- No. of chars left after find
//	-- begin integer i; i:=0;
//	--       repeat while lng > 0
//	--       do if var(buff)(i) = c then goto E endif;
//	--          lng:=lng-1; i:=i+1 endrepeat;
//	-- E:    rst:=lng;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- buff.ofst + buff.attr               --
//	-- POPR   ES              ; ES <- buff.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- POPR   AL              ; AL <- the character to find               --
//	-- RSTRB  RSCAS,CLD,REPNE ; Compare AL -- ES:[DI]; INC(DI); CX times  --
//	--   --   iCLD                                                        --
//	--   --   iREPNE  SCASB                                               --
//	--   --   iJNE    $+3                                                 --
//	--   --   iINC    CX                                                  --
//	-- PUSHR  CX              ; CX <- length of resulting string          --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- buff.ofst + buff.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- POPR   AL              ; AL <- the character to find               --
//	-- RSTRB  RSCAS,CLD,REPNE ; AL cmp ES:[EDI]; INC(EDI); ECX times      --
//	--   --   iCLD                                                        --
//	--   --   iREPNE  SCASB                                               --
//	--   --   iJNE    $+3                                                 --
//	--   --   iINC    ECX                                                 --
//	-- PUSHR  ECX             ; ECX <- length of resulting string         --
//	------------------------------------------------------------------------
//	%title *** Byte String Find and Decrement address ***
//
//	Visible Sysroutine("S?SFINDD") APX_SFINDD;
//	import Character c;            -- The character
//	       range(0:MaxWord) lng;   -- Length
//	       Name(Character) buff;   -- Pointer to Buffer
//	export range(0:MaxWord) rst;   -- No. of chars left after find
//	-- begin integer i; i:=0;
//	--       repeat while lng > 0
//	--       do if var(buff)(i) = c then goto E endif;
//	--          lng:=lng-1; i:=i-1 endrepeat;
//	-- E:    rst:=lng;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- buff.ofst + buff.attr               --
//	-- POPR   ES              ; ES <- buff.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- POPR   AL              ; AL <- the character to find               --
//	-- RSTRB  RSCAS,STD,REPNE ; Compare AL -- ES:[DI]; DEC(DI); CX times  --
//	--   --   iSTD                                                        --
//	--   --   iREPNE  SCASB                                               --
//	--   --   iJNE    $+3                                                 --
//	--   --   iINC    CX                                                  --
//	-- PUSHR  CX              ; CX <- length of resulting string          --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- buff.ofst + buff.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- POPR   AL              ; AL <- the character to find               --
//	-- RSTRB  RSCAS,STD,REPNE ; AL cmp ES:[EDI]; DEC(EDI); ECX times      --
//	--   --   iSTD                                                        --
//	--   --   iREPNE  SCASB                                               --
//	--   --   iJNE    $+3                                                 --
//	--   --   iINC    ECX                                                 --
//	-- PUSHR  ECX             ; ECX <- length of resulting string         --
//	------------------------------------------------------------------------
//	%title *** Byte String Fill and Increment address ***
//
//	Visible Sysroutine("S?SFILL") APX_SFILL;
//	import Character c;                -- The character
//	       range(0:MaxWord) lng;       -- Length
//	       Name(Character) buff;       -- Pointer to Buffer
//	-- begin repeat while lng > 0
//	--       do lng:=lng-1; var(buff)(lng):=c endrepeat;
//	end;
//
//	-- Inline q-Coding: for MS-DOS,OS-2,XENIX
//	------------------------------------------------------------------------
//	-- POPR   AX                                                          --
//	-- POPR   DI                                                          --
//	-- DYADR  ADD,DI,AX       ; DI <- buff.ofst + buff.attr               --
//	-- POPR   ES              ; ES <- buff.seg                            --
//	-- POPR   CX              ; CX <- length                              --
//	-- POPR   AL              ; AL <- the character to fill in            --
//	-- RSTRB  RSTOS,CLD,REP   ; AL -> ES:[DI]; INC(DI); CX times          --
//	--   --   iCLD                                                        --
//	--   --   iREP    STOSB                                               --
//	------------------------------------------------------------------------
//
//	-- Inline q-Coding: for UNIX
//	------------------------------------------------------------------------
//	-- POPR   EAX                                                         --
//	-- POPR   EDI                                                         --
//	-- DYADR  ADD,EDI,EAX     ; EDI <- buff.ofst + buff.attr              --
//	-- POPR   ECX             ; ECX <- length                             --
//	-- POPR   AL              ; AL <- the character to fill in            --
//	-- RSTRB  RSTOS,CLD,REP   ; AL -> ES:[EDI]; INC(EDI); ECX times       --
//	--   --   iCLD                                                        --
//	--   --   iREP    STOSB                                               --
//	------------------------------------------------------------------------
//
//
//	end;

}
