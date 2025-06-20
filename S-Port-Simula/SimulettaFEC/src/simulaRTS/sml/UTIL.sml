--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
--%PASS 2 OUTPUT=1 -- S-Code Output Trace  --- SKAL INN IGJEN
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace

Module util("RTS");
 begin sysinsert rt,sysr,knwn;

       ----------------------------------------------------------------
       ---  COPYRIGHT 1989 by                                       ---
       ---  Simula a.s.                                             ---
       ---  Oslo, Norway                                            ---
       ---                                                          ---
       ---                 P O R T A B L E     S I M U L A          ---
       ---                  R U N T I M E     S Y S T E M           ---
       ---                                                          ---
       ---        I n t e r n a l    R T S    U t i l i t i e s     ---
       ---                                                          ---
       ----------------------------------------------------------------

Visible macro DEFINE_IDENT(3)
begin const infix(idfier: %2 ) %1 =record:idfier
      ( ncha= %2 , cha= %3 );
endmacro;

Visible macro SIMPLE_ATTR(4);
begin const infix(atrdes) %1 = record:atrdes
        (ident=ref( %2 ),fld=field( %3 ),
         mode=M_LOCAL,kind=K_SMP,type= %4 );
endmacro;

--  Observation Event Literal Values --  NOTE sequence is significant

            -- these will always cause SIMOB to be called
            -- They have no direct influence on 'bio.trc'
Visible define EVT_INI  =  0, -- System generation
               EVT_ERR  =  1, -- Runtime ERROR
               EVT_BRK  =  2, -- Break point trap
               EVT_SST  =  3, -- Statement start interupt
               EVT_ITR  =  4, -- Attention interupt
               EVT_BPRG =  5, -- Begin user program
               EVT_EPRG =  6, -- End user program

               OCF_first=  7, OCF_last = 9, -- interval for obs-c-flo
               EVT_BEG  =  7, -- Begin block/procedure/class
               EVT_END  =  8, -- End   block/procedure/class
               EVT_GOTO =  9, -- Goto non-local label

               EVT_ARR  = 10, -- Array allocation
               EVT_TXT  = 11, -- Text entity generation
               EVT_SNAP = 12, -- User SNAPSHOT

           --  Sequencing events --
               QPS_first= 13, QPS_last = 15, -- interval for ALL
               EVT_deta = 13,
               EVT_call = 14,
               EVT_resu = 15,

           --  Events during file handling  --
               FTR_first= 16, FTR_last = 21, -- interval for ALL
               EVT_open = 16,  -- open
               EVT_clos = 17,  -- close
               EVT_inim = 18,  -- inimage
               EVT_ouim = 19,  -- outimage
               EVT_loca = 20,  -- locate
               EVT_endf = 21,  -- endfile

           --  SIMULATION events  --
               SML_first= 22, SML_last = 34, -- interval for ALL
               EVT_acti = 22,  -- activate
               EVT_pass = 23,  -- passivate
               EVT_canc = 24,  -- cancel
               EVT_wait = 25,  -- wait
               EVT_hold = 26,  -- hold
               EVT_ACT1 = 27,  -- activate none
               EVT_ACT2 = 28,  -- activate terminated
               EVT_ACT3 = 29,  -- activate scheduled
               EVT_RACT = 30,  -- reactivate
               EVT_RAC1 = 31,  -- reactivate none
               EVT_RAC2 = 32,  -- reactivate terminated
               EVT_RAC3 = 33,  -- reactivate before/after itself
               EVT_RAC4 = 34,  -- reactivate current

           --  Events during Garbage Collection  --
               GC_first = 35, GC_last = 38,
               EVT_gcBEG= 35,  -- Begin Garbage Collection
               EVT_gcEND= 36,  -- End Garbage Collection
               EVT_gcFIL= 37,  -- Unclosed file found as garbage
               EVT_gcNP = 38,  -- Allocate new pool
 ----          EVT_gcP1 = 39,  -- End of pass 1
 ----          EVT_gcP2 = 40,  -- End of pass 2
 ----          EVT_gcP3 = 41,  -- End of pass 3
 ----          EVT_gcP4 = 42,  -- End of pass 4
 ----          EVT_gcPE1= 43,  -- Process entity in pass 1
 ----          EVT_gcPE3= 44,  -- Process entity in pass 3
 ----          EVT_gcUPD= 45,  -- Updated entity in pass 3

               EVT_max  = 38;

Visible define TRM_NRM = 0; -- Normal termination
Visible define TRM_REQ = 1; -- User requested termination
Visible define TRM_ERR = 2; -- Error in user program or observation tool
Visible define TRM_SYS = 3; -- System error
Visible define MAX_TRM = 3;

Visible const infix(string) ERR_MSG(MAX_ENO) = (

 "Unspecified error condition.",                              --   0
 "Invalid floating point operation.",                         --   1
 "Floating point division by zero.",                          --   2
 "Floating point overflow.",                                  --   3
 "Floating point underflow.",                                 --   4
 "Inexact result (floating point operation).",                --   5
 "Integer overflow.",                                         --   6
 "Integer division by zero.",                                 --   7
 "Illegal address trap.",                                     --   8
 "Illegal instruction trap.",                                 --   9
 "Breakpoint trap.",                                          --  10
 "User interrupt - NOT YET IMPLEMENTED.",                     --  11
 "Cpu time limit overflow.",                                  --  12
 "Continuation is impossible.",                               --  13
 "Start of statement exception - NOT YET IMPLEMENTED.",       --  14
 "Array index out of range",                                  --  15
 "Attempted attribute access through none",                   --  16
 "Trap caused by interrupt or exception:",                    --  17
 "Internal error in Simula System (RTS): ",                   --  18
 "Internal error in Simula System (ENV): ",                   --  19
 "Internal error in Simula System (SIMOB): ",                 --  20
 "Breakpoint trap, but no observation tool is available.",    --  21
 "Not enough primary storage for predefined instances.",      --  22
 "Qualification check fails.",                                --  23
 "Attempt to access attribute == NONE ('attr.y' or 'attr QUA ...').",
 "Unexpected non-zero status on return from environment routine call.",
 "Exponentiation: Result is undefined.",                      --  26
 "Impossible to satisfy the request, maybe because it is illegal.",
 "Actual parameter value is out of range.",                   --  28
 "The service function is not implemented.",                  --  29
 "Illegal action.",                                           --  30
 "Storage request cannot be met, not enough primary storage.",--  31
 "Requested termination: Not enough available storage after compaction",
 "Illegal goto destination.",                                 --  33
 "x.Detach: x is not on the operating chain.",                --  34
 "Resume(x): x is none.",                                     --  35
 "Resume(x): x is not local to sub-block or prefixed block instance",
 "Resume(x): x is not in detached state.",                    --  37
 "Process is not local to prefixed block, illegal implicit Resume.",
 "Implicit Resume(x): Process object x is not in detached state.",
 "Call(x): x is none.",                                       --  40
 "Call(x): x is not in detached state.",                      --  41
 "Wrong number of parameters in call on formal or virtual procedure.",
 "Virtual attribute has no match.",                           --  43
 "Lower/upperbound(a,i): illegal value of i.",                --  44
 "Incorrect number of array indices.",                        --  45
 "Array index value is out of bounds.",                       --  46
 "Array index value is out of bounds.",                       --  47
 "Blanks(n):  n is negative or too large.",                   --  48
 "Text value assignment x := y: x.Length < y.Length, maybe x == notext",
 "Text value assignment x := y: x.Constant = True.",          --  50
 "Sub(i,n):  i is less than 1.",                              --  51
 "Sub(i,n):  n is negative.",                                 --  52
 "t.Sub(i,n): i + n > t.Length + 1, maybe t == notext.",      --  53
 "t.Get...:  t == notext.",                                   --  54
 "t.Get...:  Non-numeric item.",                              --  55
 "t.Get...:  Numeric item is out of range.",                  --  56
 "t.Get...:  Numeric item is not complete.",                  --  57
 "t.Getchar:  t.More = False, maybe t == notext.",            --  58
 "t.Put...:  t == notext.",                                   --  59
 "t.Put...:  t.Constant = True.",                             --  60
 "t.Put...(r,n):  Fraction size specification n is negative.",--  61
 "t.Putchar:  t.More = False, maybe t == notext.",            --  62
 "Parameter called by name:",                                 --  63
 "Assignment to formal: Actual is no variable, cannot assign.",
 "Parameter transmission: Actual is no variable.",            --  65
 "The types of the actual and the formal parameter are different.",
 "Different qualifications of the actual and the formal parameter.",
 "Assignment to formal:  object is not subordinate to actual.",
 "Occurrence of formal: actual object is not subordinate to formal.",
 "Occurrence of formal: actual procedure is not subordinate to formal.",
 "Parameter transmission to formal or virtual procedure:",    --  71
 "Actual object is not subordinate to formal parameter.",     --  72
 "Actual procedure parameter is not subordinate to formal parameter.",
 "Actual and formal parameter are of different kinds.",       --  74
 "Actual and formal parameter are of different types.",       --  75
 "Actual and formal parameter are of incompatible types.",    --  76
 "Transplantation: actual and formal qualification are dynamically different.",
 "Qualification of actual and formal reference array do not coincide.",
 "Types of actual and formal procedure are neither coincident nor subordinate.",
 "file.Open:  The file is open already.",                     --  80
 "new ...file: FILENAME == notext.",                          --  81
 "file.OPEN = false.",                                        --  82
 "file.ENDFILE == true.",                                     --  83
 "file.In...:  file.image == notext  or  file not open.",     --  84
 "file.Inimage:  file.image.Constant = true.",                --  85
 "Directfile.Inimage:  End of file was encountered.",         --  86
 "file.In...:  Attempt to read through end of file.",         --  87
 "file.In...:  Non-numeric item.",                            --  88
 "file.In...:  Numeric item is out of range.",                --  89
 "file.In...:  Numeric item is not complete.",                --  90
 "file.Intext(n):  n is negative or too large.",              --  91
 "file.Out...:  file.image == notext  or  file not open.",    --  92
 "file.Out...(...,w):  w < 0.",                               --  93
 "file.Out...(...,w):  w > file.image.Length",                --  94
 "file.Out...:  file.image.Constant = true.",                 --  95
 "file.Out...(...,n,...):  Fraction size specification n is negative.",
 "file.Outtext(t):  t.Length > file.image.Length",            --  97
 "Printfile.Spacing(n):  n < 0  or  n > Linesperpage.",       --  98
 "Printfile.Eject(n):  n <= 0.",                              --  99
 "File.Close:  The file is closed already.",                  -- 100
 "Illegal file operation, not compatible with this file.",    -- 101
 "The external record format is not compatible with this directfile.",
 "File.Open:  Illegal file name.",                            -- 103
 "file.Out...:  Output image too long.",                      -- 104
 "file.In...:  Input image too long.",                        -- 105
 "file.Out...:  The file is full.",                           -- 106
 "Directfile:  Location out of range.",                       -- 107
 "I/O error, e.g. hardware fault.",                           -- 108
 "No write access to the file.",                              -- 109
 "File.Open:  Too many files open simultaneously.",           -- 110
 "No read access to the file.",                               -- 111
 "End of file has been encountered already.",                 -- 112
 "The file is closed",                                        -- 113
 "Simulation:  (Re)Activate empties SQS.",                    -- 114
 "Simulation:  Cancel,Passivate or Wait empties SQS",         -- 115
 "Process.Evtime:  The process is idle.",                     -- 116
 "Random drawing:  Actual array parameter is not one-dimensional.",
 "Histd(a,u):  An element of the array a is negative.",       -- 118
 "Linear(a,b,u):  The number of elements in a and b are different.",
 "Linear(a,b,u):  The array a does not satisfy the stated assumptions.",
 "Negexp(a,u) :  a <= 0.",                                    -- 121
 "Randint(a,b,U) or Uniform(a,b,U) :   b < a.",               -- 122
 "Erlang(a,b,u):  a <= 0  or  b <= 0.",                       -- 123
 "Normal(a,b,u):  b <= 0.",                                   -- 124
 "Erlang/Negexp/Normal/Poisson: parameter U <= 0",            -- 125
 "Histo(a,b,c,d):  Array parameter is not one-dimensional.",  -- 126
 "Histo(a,b,c,d):  number of elements in a <= number of elements in b.",
 "Standard function call:  Parameter value is out of range.", -- 128
 "Switch designator:  Index value is out of range.",          -- 129
 "Call on external non-SIMULA procedure:  actual label is not local.",
 "Lowten(c): c is illegal",                                   -- 131
 "Decimalmark(c): ci is neither '.' or ','",                  -- 132
 "RTS_Utility: first parameter is out of range",              -- 133
 "No such data set",                                          -- 134
 "Filename does not describe a data set",                     -- 135
 "File cannot be CREATEd, it exists already",                 -- 136
 "Open/Close: file access code is not implemented",           -- 137
 "A file operation cannot be performed"                       -- 138

% +X "0","1","2","3","4","5","6","7","8","9",
% +X "10","11","12","13","14","15","16","17","18","19",
% +X "20","21","22","23","24","25","26","27","28","29",
% +X "30","31","32","33","34","35","36","37","38","39",
% +X "40","41","42","43","44","45","46","47","48","49",
% +X "50","51","52","53","54","55","56","57","58","59",
% +X "60","61","62","63","64","65","66","67","68","69",
% +X "70","71","72","73","74","75","76","77","78","79",
% +X "80","81","82","83","84","85","86","87","88","89",
% +X "90","91","92","93","94","95","96","97","98","99",
% +X "110","101","102","103","104","105","106","107","108","109",
% +X "110","111","112","113","114","115","116","117","118","119",
% +X "120","121","122","123","124","125","126","127","128","129",
% +X "130","131","132","133","134","135","136","137","138"
 );
%title ***   E r r o r    H a n d l i n g   ***

 Visible routine IERR_E; -- wrong status from ENVIR
 begin range(0:MAX_ENO) eno;
       case 0:MAX_STS (status)
       when 19:  eno:=ENO_SYS_3 ;
       when 27:  eno:=ENO_SYS_4;
       when 30:  eno:=ENO_SYS_5;
       when 33:  eno:=ENO_SYS_6 ;
       otherwise eno:=ENO_ITN_2 endcase;
       ERROR1(eno,none);
 end;

 Visible routine ERROR1;
 import range(0:MAX_ENO) eno; ref(filent) fil;
 begin call PEXERR(errorX)(eno,fil) end;

 Visible routine IERR_R; import infix(string) idn;
 begin
---    ED_OUT; PRT("*** Internal Error in Run Time System ***");
       bio.errmsg:=idn; call PEXERR(errorX)(ENO_ITN_1,none);
 end;

VIsible routine ED_ERR;
import range(0:MAX_ENO) eno; ref(filent) fil;
       range(0:MAX_ENO) msg,msx; range(0:MAX_STS) sts;
begin ED_OUT;
      if eno>=0 then
         ED_STR("*** Simula Runtime Error no. "); ED_INT(eno) endif;
      if sts <> 0 then  ---  Treat the case of a non-zero status.
         if (msg<>0) or (msx<>0) or (bio.errmsg.nchr<>0)
         then --- do not give status if any other message
         else ED_STR(",  STATUS =  "); ED_INT(sts); PRT("  ***") endif;
      endif;
      ED_OUT;
      ---  Output the error message(s) from the RTS.
%-X   if msx <> 0 then PRT(ERR_MSG(msx)) endif;
%-X   if msg <> 0 then PRT(ERR_MSG(msg)) endif;
      if bio.errmsg.nchr<>0
      then PRT(bio.errmsg); bio.errmsg.nchr:=0; endif;
      ---  If its is a file error, supply additional information.
      if fil <> none
      then ED_STR(" Filename: ");
           if fil = bio.sysin then PRT("sysin")
           elsif fil = bio.sysout then PRT("sysout")
           else PRT(TX2STR(fil.nam)) endif;
      endif;
end;

%title ***  U t i l i t y    R o u t i n e s  ***


Visible routine STRBUF;
import range(0:MAX_BYT) n; export infix(string) s;
begin s.chradr:=@bio.utbuff; if bio.utpos <> 0 then ED_OUT endif;
      if n <> 0 then s.nchr:=n else s.nchr:=utlng endif;
end;

 Visible routine STRequal;
 import infix(string) str1,str2; export boolean res;
 begin character ch1,ch2; range(0:32000) pos;
       str1.nchr:=STRIPP(str1);  str2.nchr:=STRIPP(str2); 
       pos:=0;
       if str1.nchr = str2.nchr
       then repeat while pos < str1.nchr do
               ch1:=var(str1.chradr)(pos); ch2:=var(str2.chradr)(pos);
               if ch1 <> ch2
               then if ('a'<=ch1) and (ch1<='z')
                    then ch1:=(ch1 qua integer - 32) qua character endif
                    if ('a'<=ch2) and (ch2<='z')
                    then ch2:=(ch2 qua integer - 32) qua character endif
                    if ch1<>ch2 then goto F1 endif;
               endif;
               pos:=pos+1;
            endrepeat;
            res:=true; -- also for both nostring (nchr = 0)
       else   F1: res:=false;
       endif;
 end;
%title ***   T  r  a  c  i  n  g   ***

Visible routine observ;
begin label lsc;
%-X   lsc:=curins.lsc;
%-X   curins.lsc:=GTOUTM; call PSIMOB(smb); curins.lsc:=lsc;
end;

%title ***  M o r e   B a s i c   O u t p u t    H a n d l i n g  ***

-- Editing system output messages
-- All other editing routines are used only in MNTR

character xbuff(utlng);
Visible routine GET_ED; export infix(string) res;
begin integer i;
      res.chradr:=@xbuff; res.nchr:=bio.utpos;
      repeat while i<bio.utpos;
             do xbuff(i):=bio.utbuff(i); i:=i+1;
      endrepeat;
      bio.utpos:=0;
end;



Visible routine WFIELD; import integer n; export infix(string) s;
begin if n > (utlng - bio.utpos) then ED_OUT endif
      if n > utlng then n:=utlng endif;
      s.chradr:=@bio.utbuff(bio.utpos); s.nchr:=n;
      bio.utpos:=bio.utpos+n;
end;


Visible routine ED_IDT; import ref(idfier) idt;
begin infix(string) str;
%-X   if idt = none then str:="not observable";
%-X   else str.nchr:=idt.ncha; str.chradr:=name(idt.cha) endif;
%-X   ED_STR(str);
end;

%-X routine ED_iID; import ref(inst) ins;
%-X begin ref(ptpExt) xpp; label act;
%-X   act:=nowhere;
---   if ins.pp=none then xpp:=none else xpp:=ins.pp.xpp endif;
%-X   case 0:MAX_SORT (ins.sort)
%-X   when S_ATT: ED_STR("attached class ");
%-X               act:=ins.pp qua ref(claptp).stm;
%-X   when S_DET: ED_STR("detached class ");
%-X               act:=ins.pp qua ref(claptp).stm;
%-X   when S_RES: ED_STR("resumed class ");
%-X               act:=ins.pp qua ref(claptp).stm;
%-X   when S_TRM: ED_STR("terminated class ");
%-X               act:=ins.pp qua ref(claptp).stm;
%-X   when S_PRE: ED_STR("prefixed block ")
%-X               act:=ins.pp qua ref(claptp).stm;
%-X   when S_PRO: --  if xpp <> none then ED_TYP(xpp.typ,xpp.qal) endif;
%-X               ED_STR("procedure ");
%-X               act:=ins.pp qua ref(proptp).start;
%-X   when S_SUB: ED_STR("sub-block ");
%-X   when S_THK: ED_STR("parameter evaluation ");
%-X   endcase;
%-X   if ins.pp<>none
%-X   then xpp:=ins.pp.xpp; if xpp=none then goto L1 endif;
%-X        if xpp.idt <> none then ED_IDT(xpp.idt) endif;
%-X ---        if xpp.blk_inf.sno <> nofield
%-X ---        then ED_CHA('('); ED_INT(var(ins+xpp.blk_inf.sno));
%-X ---             ED_CHA(')');
%-X ---        endif;
%-X   else L1: if act <> nowhere
% -X        then ED_STR(" with first action at line "); ED_INT(GTLNO(act)) endif;
%-X        then ED_STR(" with first action at "); ED_PADDR(act) endif;
%-X   endif;
%-X   status:=0; ED_STR(" ");
%-X end;

Visible routine ED_eID; import ref(entity) ent;
begin
%-X   if ent = none then ED_STR("none ");
%-X   else case  0:MAX_SORT (ent.sort)
%-X        when  S_SUB,S_ATT,S_DET,S_PRE,S_PRO,S_RES,S_THK,S_TRM:
%-X              ED_iID(ent);
%-X        when  S_ARBODY,S_ARBREF,S_ARBTXT:
%-X              ED_STR("body of multi-dimensional array ");
%-X        when  S_ARHEAD:
%-X              ED_STR("head of multi-dimensional array ");
%-X        when  S_ARENT2,S_ARREF2,S_ARTXT2:
%-X              ED_STR("two-dimensional array ");
%-X        when  S_ARENT1,S_ARREF1,S_ARTXT1:
%-X              ED_STR("one-dimensional array ");
%-X        when  S_SAV:  ED_STR("save object entity ");
%-X        when  S_TXTENT:  ED_STR("text object entity ");
%-X        when  S_GAP:  ED_STR("storage gap ")
%-X        otherwise ED_STR(" ***** unknown entity ***** ")
%-X        endcase;
%-X   endif;
end;
end;
