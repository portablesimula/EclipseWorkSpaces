Module mntr("RTS");
 begin sysinsert rt,sysr,knwn,util,strg,cent,arr,fil,edit;
              -- ,libr,smst,sml;

    -----------------------------------------------------------------
    ---  COPYRIGHT 1989 by                                        ---
    ---  Simula a.s.                                              ---
    ---  Oslo, Norway                                             ---
    ---                                                           ---
    ---              P O R T A B L E     S I M U L A              ---
    ---               R U N T I M E     S Y S T E M               ---
    ---                                                           ---
    --- E r r o r    a n d    E x c e p t i o n    H a n d l i n g---
    ---       B e g i n    a n d    E n d    P r o g r a m        ---
    ---                                                           ---
    --- select X : generate mntx, FEC production version of mntr  ---
    ---                                                           ---
    --- The following abbreviations are used:                     ---
    ---                                                           ---
    ---  AI - Active Instance                                     ---
    ---  AP - Active program Point (Active Point)                 ---
    ---  CE - Currently identified system Element (Current Element)--
    ---  CI - Currently identified Instance (Current Instance)    ---
    ---  OP - Observer Position                                   ---
    ---                                                           ---
    --- The relation between CE and CI is as follows:             ---
    --- CE is either an instance or an attribute of an instance.  ---
    --- CI is defined relative to CE. When CE is an instance, CI=CE--
    --- When CE is an attribute of an instance, CI is the instance---
    --- of which CE is an attribute.                              ---
    --- Both CE and CI are represented by the variable 'CE'       ---
    ---                                                           ---
    -----------------------------------------------------------------
%title ***  M N E M O N I C S    a n d    R E C O R D S  ***

--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 2 INPUT=1 -- Input Trace

% INSERT sportid.def
% INSERT C:/GitHub/SimulaCompiler/Simula/src/sport/rts/sportid.def
% INSERT C:/WorkSpaces/SPort-System/S_Port/src/sport/rts/sportid.def
% INSERT C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/sportid.def
const infix(string) sportid="S-PORT SYSTEM";
const infix(string) smlid="Simuletta 1.0"


   infix(string) dest;

%-X define inlng = 160;             -- Input file's line length
%-X Define max_break = 60;          -- Max number of stmnt breaks

%-X Define
%-X    C_ALG= 1,  C_CS=  2,  C_DA=  3,  C_DL=  4,  C_DV=  5,  C_DDE=  6,
%-X    C_DEM= 7,  C_DOC= 8,  C_DOP= 9,  C_DSB=10,  C_DTE=11,  C_ID=  12,
%-X    C_LOE=13,  C_LOS=14,  C_LSF=15,  C_LOF=16,  C_MTAI=17, C_MTPI=18,
%-X    C_MTDE=19, C_MTO=20,  C_MTTE=21, C_OAC=22,  C_OCF=23,  C_OFE=24,
%-X    C_OIG=25,  C_OGC=26,  C_OSE=27,  C_OS=28,   C_OSQ=29,  C_OSN=30,
%-X    C_OTG=31,  C_RS=32,   C_TE=33,   C_EX=34,   C_STR=35,  C_SLG=36,
%-X    C_SSS=37,  C_SSM=38,  C_SLM=39,  C_DO=40,   C_CHM=41,  C_OAS =42,
%-X    C_max=42;      -- Number of commands defined;

                      -- Element Kind Codes
%-X Define
%-X    EK_NO  = 0,    -- Element is an instance
%-X    EK_BLK = 1,    -- Element is a block attribute
%-X    EK_VAL = 2,    -- Element is a value attribute (except array)
%-X    EK_CLA = 3,    -- Element is a class (local)
%-X    EK_PRO = 4,    -- Element is a procedure (local or parameter)
%-X    EK_LAB = 5,    -- Element is a label (local or parameter)
%-X    EK_SWT = 6,    -- Element is a switch (parameter)
%-X    EK_ARR = 7,    -- Element is an array (not indexed)
%-X    EK_IDX = 8,    -- Element is an array during indexing
%-X    EK_ARV = 9,    -- Element is an indexed array element
%-X    EK_REC = 10,   -- Element is a record (local)
%-X    EK_MAX = 10;   -- Element Kind Codes

--  Set Help Literals  --
%-X Define
%-X    H_CMND=1, H_VAR=2,   H_PROG=3, H_BRK=4,  H_FNAM=5,
%-X    H_SML=6,  H_GC=7,    H_FIL=8,  H_COND=9, H_SEQ=10,
%-X    H_DO=11,  H_DOCMD=12,H_SET=13, H_SSM=14,
%-X    H_max=14;

    Define EXC_UNS = 0,              -- Unspecified error condition
           EXC_ILA = 8,              -- Illegal address trap
           EXC_BRK = 10,             -- Breakpoint trap
           EXC_ITR = 11,             -- User interrupt
           EXC_TIM = 12,             -- Time limit exceeded
           EXC_IMP = 13,             -- Continuation impossible
           EXC_SST = 14,             -- Start of statement
           EXC_ARX = 15,             -- Array index out of bounds
           EXC_NON = 16;             -- None-dot

%-X Record PROGPNT;  INFO "TYPE";
%-X begin label paddr;             -- program Point,s address
%-X       integer count;           -- event-count;
%-X end;

%-X Record ELEMENT;  INFO "TYPE";
%-X begin
%-X       ref() ins;               -- CI, instance enclosing element
%-X       ref(ptp) pp;             -- CI'prototype
%-X       range(0:MAX_SORT) sort;  -- CI'sort - NOSORT used for records
%-X       name() adr;              -- The GADDR of (first) element
%-X       ref(atrdes) attrD;       -- The descriptor of the attribute
%-X       ref(entity) arr;         -- the array described by CE.attrD
%-X       range(0:EK_MAX) elt_kind;    -- Element kind
%-X       range(0:MAX_TYPE) atr_type;  -- Attribute type
%-X       range(0:MAX_PLV) plv;    -- Current attribute's prefix level
%-X       -- In use under interpretation of array indices: (+arr above)
%-X       range(0:MAX_DIM) nind;   -- number of indices applied
%-X       integer aind;            -- current abs. array element index
%-X       integer nelt;            -- number elements in repetition
%-X       integer ind(MAX_DIM);    -- the indices applied
%-X                                -- if rep, ind(MAX_DIM)=nelt
%-X end;
%title ***  G L O B A L    V A R I A B L E S   ***
%-X range(0:1) errstat;        -- 1: smb_err
%-X ref(inst) OP;              -- Observer Position
%-X ref(inst) AI;              -- User's active instance
%-X infix(ELEMENT) CE;         -- Current system Element
%-X infix(ELEMENT) UE;         -- Utility system Element
%-X infix(ELEMENT) VE;         -- Utility system Element (setvalue)

%-X ref(inst) previns;    -- The previously operating instance
%-X Boolean interactive;  -- True: if we are executing interactively
%-X boolean dialogue;
%-X Boolean SMB_TRC;      -- True: trace mode
%-X Boolean CHGMODE;      -- True: change attribute mode
%-X Boolean RT_ERR;       -- True: treating an RT-error
%-X Boolean LSTMODE;      -- True: in listing mode (i.e. list source)
%-X Range(0:255) SYSMODE; -- <> 0: in system mode (display system info)
%-X range(0:EVT_max) upevt;  -- impl. param to OBSERVE_CNT

    range(0:MAX_ENO) er_msg;      --  Primary error message number.
    range(0:MAX_ENO) er_msx;      --  Additional error message number.
    range(0:MAX_ENO) er_no;
    range(0:MAX_STS) er_sts;
    range(0:MAX_TRM) er_trm;
    ref(filent) er_fil;
    infix(labqnt) erh;         -- After ERROR return label temp
    label returnFromSimob;     -- After EXCEPTION return from SIMOB
    label retur;               -- parameter to ENDDEB

%-X Boolean MISS;         -- Missing SIMOB-info during ident-search
%-X Boolean EXPR;         -- Attempt to evaluate name param expression
%-X infix(string) inline; -- Current command line
%-X range(0:inlng) inpos; -- Current input line's pos (0..inlng-1)
%-X range(0:inlng) inlim; -- First pos not within current item
%-X range(0:inlng) inmax; -- First pos not filled in inbuff
%-X infix(string) curid;  -- The current identifier

%-X range(0:MAX_KEY) indevice;  -- Current input device for SIMOB
%-X infix(string) logid;        -- Name of logfile

%-X range(0:max_break) nbreaks        -- Number of stmnt breaks defined
%-X infix(PROGPNT) stmnt(max_break);  -- Statement break table

%-X integer DOcnt, DOmax;   -- count and max count for DO-C-L;
%-X range(0:inlng) DOstart; -- First character pos after DO-C-L;
%-X define EVT_cnt = 39; -- TEMP, should be in UTIL
%-X integer CNT(EVT_cnt);       -- Event counts
                                -- 0:not observed, 1:enter simob
                                -- >1:skip count
%-X integer CNTshadow(EVT_cnt); -- resets CNT, when count reach ONE(!)
%-X const infix(string) logAction =
%    "!1!!0!!2!!2!!1!!0!!0!!0!!0!!8!NOBUFFER!0!";
%-X  "!1!!0!!2!!2!!1!!0!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!"
%-X  -- noshared,append,anycreate,writeonly,nopurge,rewind,%NOBUFFER
%-X const infix(string) Inaction  =
%-X  "!0!!1!!1!!0!!1!!0!!0!!0!!0!!8!NOBUFFER!0!";
%    "!0!!1!!1!!0!!1!!0!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!"
%-X  -- shared,noappend,nocreate,readonly,nopurge,rewind,%NOBUFFER

%-X const infix(string) CMID(C_max) = ( -- The command names:
%-X   "ATTACH-LOGFILE filename",                  -- C_ALG   =  1
%-X   "CANCEL-STATEMENT number",                  -- C_CS    =  2
%-X   "DISPLAY-ATTRIBUTE variable",               -- C_DA    =  3
%-X   "DISPLAY-LOCALS",                           -- C_DL    =  4
%-X   "DISPLAY-VISIBLES",                         -- C_DV    =  5
%-X   "DISPLAY-DYNAMIC-ENCLOSURES",               -- C_DDE   =  6
%-X   "DISPLAY-ERROR-MESSAGE",                    -- C_DEM   =  7
%-X   "DISPLAY-OPERATING-CHAIN",                  -- C_DOC   =  8
%-X   "DISPLAY-OBSERVER-POSITION",                -- C_DOP   =  9
%-X   "DISPLAY-SIMULATION-BLOCK",                 -- C_DSB   = 10
%-X   "DISPLAY-TEXTUAL-ENCLOSURES",               -- C_DTE   = 11
%-X   "INPUT-DEVICE filename",                    -- C_ID    = 12
%-X   "LIST-OBSERVED-EVENTS",                     -- C_LOE   = 13
%-X   "LIST-OBSERVED-STATEMENTS",                 -- C_LOS   = 14
%-X   "LIST-SOURCE-FILE filename",                -- C_LSF   = 15
%-X   "LIST-OPEN-FILES",                          -- C_LOF   = 16
%-X   "MOVE-TO-ACTIVE-INSTANCE",                  -- C_MTAI  = 17
%-X   "MOVE-TO-PREVIOUS-INSTANCE",                -- C_MTPI  = 18
%-X   "MOVE-TO-DYNAMIC-ENCLOSURE",                -- C_MTDE  = 19
%-X   "MOVE-TO-OBJECT variable",                  -- C_MTO   = 20
%-X   "MOVE-TO-TEXTUAL-ENCLOSURE",                -- C_MTTE  = 21
%-X   "OBSERVE-ARRAY-CREATION condition",         -- C_OAC   = 22
%-X   "OBSERVE-CONTROL-FLOW ( ON ! OFF )",        -- C_OCF   = 23
%-X   "OBSERVE-FILE-EVENTS ( file-event )*",      -- C_OFE   = 24
%-X   "OBSERVE-INSTANCE-GENERATION condition",    -- C_OIG   = 25
%-X   "OBSERVE-GARBAGE-COLLECTION ( GC-event )*", -- C_OGC   = 26
%-X   "OBSERVE-SIMULATION-EVENTS ( Simulation-event )*", -- C_OSE = 27
%-X   "OBSERVE-STATEMENT program-point",          -- C_OS    = 28
%-X   "OBSERVE-SEQUENCING condition",             -- C_OSQ   = 29
%-X   "OBSERVE-SNAPSHOT condition",               -- C_OSN   = 30
%-X   "OBSERVE-TEXT-GENERATION condition",        -- C_OTG   = 31
%-X   "RESUME-SIMULA",                            -- C_RS    = 32
%-X   "TERMINATE-EXECUTION",                      -- C_TE    = 33
%-X   "EXIT",                                     -- C_EX    = 34
%-X   "SET-TRACE ( ON ! OFF )",                   -- C_STR   = 35
%-X   "SET-LOGFILE ( ON ! OFF )",                 -- C_SLG   = 36
%-X   "SET-SCREEN-SIZE lines_per_screen",         -- C_SSS   = 37
%-X   "SET-SYSTEM-MODE code",                     -- C_SSM   = 38
%-X   "SET-LISTING-MODE ( ON ! OFF )",            -- C_SLM   = 39
%-X   "DO number command ( ; command )*",         -- C_DO    = 40
%-X   "SET-CHANGE-MODE ( ON ! OFF )",             -- C_CHM   = 41
%-X   "OBSERVE-ALL-STATEMENTS  condition"         -- C_OAS   = 42
%-X   );

%-X const infix(string) subCMD(26) = (
      --- QPS observable events ---
%-X   " detach", " call", " resume",
      --- file system observable events ---
%-X   " open", " close", " inimage", " outimage"," locate", " endfile",
      --- SIMULATION observable events ---
%-X   " activate", " passivate", " cancel", " wait", " hold",
%-X   " act(none)", " act(term)", " act(sched)",
%-X   " reactivate", " ract(none)", " ract(term)", " ract(BA)",
%-X   " ract(curr)",
      --- GC observable events ---
%-X   " start", " finish", " file", " area"
%-X   );
%title ***  E R R O R    H A N D L I N G  ***
 Visible body(pEXERR) EXERR;
 -- import range(0:MAX_ENO) eno; ref(filent) fil;
 begin label cont; er_no:=eno;
---    ed_str("ENTER EXERR, eno:"); ed_int(eno);
---    ed_str(", fil:"); ED_OADDR(fil); ed_out;
---    ed_str(", bio.errmsg:"); ed_str(bio.errmsg);
---    ed_str("er_trm:"); ed_int(er_trm);
---    ed_str(", er_no:"); ed_int(er_no);
---    ed_str(", er_msg:"); ed_int(er_msg);
---    ed_str(", er_msx:"); ed_int(er_msx);

%+M	   ED_STR("*** MNTR.EXERR: "); ED_INT(eno); ED_STR(": ");
%+M	   ED_STR(ERR_MSG(eno)); ED_OUT;
%+M    TERMIN(1,"MNTR.EXERR:");

       if eno = ENO_SYS_1 then er_msg:=er_msx:= 0;
       else er_msg:=eno;
            if    (eno >= ENO_ITR_MIN) and (eno <= ENO_ITR_MAX)
            then  er_msx:=ENO_ITR_0
            elsif (eno >= ENO_FPT_MIN) and (eno <= ENO_FPT_MAX)
            then  er_msx:=ENO_FPT_0
            elsif (eno >= ENO_FNP_MIN) and (eno <= ENO_FNP_MAX)
            then  er_msx:=ENO_FNP_0
            else  er_msx:= 0 endif;
       endif;
       er_fil:=fil; er_sts:=status; status:=0;
%-X    outermost:=GTOUTM;
%-X    curins.lsc:=outermost;  --- T E M P ! ! !
---    GTOUTM cannot change status !!!
---    if status <> 0 then status:=0; curins.lsc:=nowhere endif;
       case 0:MAX_ACT (actLvl)
       when ACT_GC,ACT_INI,ACT_TRM:
                     er_trm:=TRM_SYS; goto  SYS_STMTS;
       when ACT_USR: er_trm:=TRM_ERR; goto  USR_STMTS;
       when ACT_SMB: er_trm:=TRM_SYS; goto  TRM_STMTS;
---    when ACT_ERR: er_trm:=TRM_SYS; goto  TRM_STMTS;
       endcase;
 end;


 Visible body(PXCHDL) EXCHDL;
 -- import range(0:MAX_EXC) code;
 --        infix(string) msg;
 --        label addr;            -- addr of offending instruction
 --                               -- NOTE: may be different from GTOUTM
 -- export label cont;
 begin
---    ed_str("ENTER EXCHDL, code:"); ed_int(code);
---    ed_str(", msg:"); ed_str(msg);
---    ed_str(", bio.errmsg:"); ed_str(bio.errmsg);
---    ed_str(", addr:"); ed_paddr(addr); ed_out;
---    ed_str("er_trm:"); ed_int(er_trm);
---    ed_str(", er_no:"); ed_int(er_no);
---    ed_str(", er_msg:"); ed_int(er_msg);
---    ed_str(", er_msx:"); ed_int(er_msx);
---    ed_str(", cont:");
---    if    cont=USR_STMTS then ed_str("USR_STMTS")
---    elsif cont=USR_STMTS then ed_str("SYS_STMTS")
---    elsif cont=SMB_STMTS then ed_str("SYS_STMTS")
---    elsif cont=TRM_STMTS then ed_str("TRM_STMTS")
---    elsif cont=addr      then ed_str("***addr***")
---    elsif cont=nowhere   then ed_str("***nowhere***")
---    else ed_paddr(cont); endif; ed_out;
%-X    outermost:=GTOUTM;
       returnFromSimob:=addr;
       if msg.nchr=0 then msg:=bio.errmsg endif;
       ---- set continuation address into current instance ---
       er_no:=er_msg:=code; er_msx:=0;
       case 0:MAX_EXC (code)
            ----- errors in user program, that lead to termination:
       when 1,  -- invalid floatimg point operation
            2,  -- floating point division by zero
            3,  -- floating point overflow
            4,  -- floating point underflow
            5,  -- inexact (floating) result
            6,  -- integer overflow
            7,  -- integer division by zero
            8,  -- illegal address trap (impl.error)
            9,  -- illegal instruction trap (implementation error)
            15, -- Index out of range IR
            16: -- None-dot IR
            if actLvl<>ACT_USR then ed_out; PRT(msg) endif;
            case 0:MAX_ACT (actLvl)
            when ACT_GC:  bio.errmsg:=
                 "*** INTERNAL ERROR IN RTS: Exception during GC";
                          er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_INI: bio.errmsg:=
                 "*** INTERNAL ERROR IN RTS: Exception during B_PROG";
                          er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_USR: er_trm:=TRM_ERR; cont:=USR_STMTS;
%-X                       curins.lsc:=outermost;  ---  T E M P ! ! !
            when ACT_SMB: bio.errmsg:=
                 "*** INTERNAL ERROR: Exception during SIMOB";
                          er_trm:=TRM_SYS; cont:=TRM_STMTS;
---         when ACT_ERR: bio.errmsg:="Exception during error reporting"
---                       er_trm:=TRM_SYS; cont:=TRM_STMTS;
            when ACT_TRM:                  cont:=nowhere;
            endcase;
       when 0:  -- unspecified error condition
            bio.errmsg:=msg;
            case 0:MAX_ACT (actLvl)
            when ACT_GC,ACT_INI: er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_USR:        er_trm:=TRM_ERR; cont:=USR_STMTS;
%-X                       curins.lsc:=outermost;  ---  T E M P ! ! !
            when ACT_SMB:        er_trm:=TRM_SYS; cont:=TRM_STMTS;
---         when ACT_ERR:        er_trm:=TRM_SYS; cont:=TRM_STMTS;
            when ACT_TRM:                         cont:=nowhere;
            endcase;
       when 13: -- Continuation Impossible (system error)
            cont:=nowhere;
       when 12: -- Time Limit Exceeded
            er_trm:=TRM_ERR;
            case 0:MAX_ACT (actLvl)
            when ACT_GC,ACT_INI,ACT_USR,ACT_SMB:  cont:=SYS_STMTS;
                          bio.errmsg:="Time limit exceeded";
            when ACT_TRM: cont:=addr;  -- If termination, continue.
            endcase;
       when 14: -- Statement start IR
            bio.obsEvt:=EVT_SST; goto L4;
       when 10: -- statement break
            bio.obsEvt:=EVT_BRK;
       L4:
%-X         outermost:=addr;
---    L4:  case 0:MAX_ACT (actLvl)
            case 0:MAX_ACT (actLvl)
            when ACT_GC,ACT_INI: --- ,ACT_ERR:
                 bio.errmsg:="*** BREAKPOINT IN RTS ??? ***"
                 er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_USR: er_msg:= 0; cont:=SMB_STMTS;
%+X              curins.lsc:=addr;      -- ????????????????
%-X              curins.lsc:=outermost; -- ????????????????
            when ACT_SMB:
                 bio.errmsg:="*** BREAKPOINT IN SIMOB ??? ***"
                 er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_TRM: cont:=nowhere;                ---  Give in.
            endcase;
       when 11: -- User Interrupt (NOT REALLY IMPLEMENTED)
            case 0:MAX_ACT (actLvl)
            when ACT_GC,ACT_INI: --- ,ACT_ERR:
                 bio.errmsg:="*** EXECUTION ABORTED ***"
                 er_trm:=TRM_SYS; cont:=SYS_STMTS;
            when ACT_USR,ACT_SMB: er_msg:= 0;
                 bio.obsEvt:=EVT_ITR; cont:=SMB_STMTS;
%-X                       curins.lsc:=outermost;  ---  T E M P ! ! !
            when ACT_TRM: cont:=nowhere;                ---  Give in.
            endcase;
    endcase;
---    ed_str("EXIT EXCHDL, er_trm:"); ed_int(er_trm);
---    ed_str(", er_msg:"); ed_int(er_msg);
---    ed_str(", er_msx:"); ed_int(er_msx);
---    ed_str(", cont:");
---    if    cont=USR_STMTS then ed_str("USR_STMTS")
---    elsif cont=USR_STMTS then ed_str("SYS_STMTS")
---    elsif cont=SMB_STMTS then ed_str("SYS_STMTS")
---    elsif cont=TRM_STMTS then ed_str("TRM_STMTS")
---    elsif cont=addr      then ed_str("***addr***")
---    elsif cont=nowhere   then ed_str("***nowhere***")
---    else ed_paddr(cont); endif
---    ed_out;
 end;
%title ***  E V E N T    H A N D L E R  ***

Visible body(PSIMOB) SIMOB;
-- parameters in bio.obsEvt, bio.smbP1,
--               bio.smbP2 (detach), bio.GCval (call,detach,resume),
--               curins.lsc (TO BE CHANGED!!!)
begin
%-X   Boolean run_time_error_occurred;
%-X   range(0:max_break) index;   -- current break index
%-X   integer x;       -- current command index ;
%-X   range(0:EVT_max) event; range(0:MAX_SORT) sort;
      short integer trapCode;

      trapCode:=BEGTRP;
      if trapCode >= 0
      then --- trap occurred during SIMOB session, OP and AI already set
%+X        TERMIN(er_trm,"Trap or interrupt during exception handling");
%-X        case 0:16 (trapCode) --- not implemented yet
%-X        when  0,         --- unspecified error condition
%-X             13:         --- continuation impossible
%-X                 TERMIN(er_trm,"Unrecoverable error in SIMOB");
%-X        when  1,2,3,4,5, --- floating exceptions
%-X              6,7:       --- integer exceptions
%-X                 PRT(" -- floating or integer exception"); goto T0;
%-X        when  8,         --- illegal address
%-X              9:         --- illegal instruction
%-X                 TERMIN(er_trm,"Illegal addr or instr in SIMOB");
%-X        when 10,         --- statement break
%-X             14:         --- statement start trap
%-X                 PRT(" ??? Statement trap in SIMOB ???"); goto T1;
%-X        when 11:         --- user interrupt
%-X T0:T1:          inlim:=inmax:=inline.nchr:=0;
%-X        when 12:         --- time limit
%-X                 TERMIN(er_trm,"Time limit exceeded");
%-X        when 15:         --- index out of range trap
%-X                 PRT(" -- index out of range");
%-X        when 16:         --- none-dot trap
%-X                 PRT(" -- access through NONE");
%-X        endcase;
%-X        dialogue:=interactive; goto CMDloop;
      endif;

---   ed_str("ENTER SIMOB, obsEvt:"); ed_int(bio.obsEvt);
---   ed_str(", smbP1:"); ED_OADDR(bio.smbP1);
---   ed_str(", smbP2:"); ED_OADDR(bio.smbP2);
---   ed_str(", GCval:"); ed_int(bio.GCval);
      
%+X   if bio.obsEvt = EVT_gcFIL then CLOSE(bio.smbP1)
%+X        bio.actLvl:=ACT_USR; bio.obsEvt:=0; status:=0;
%+X   else if bio.obsEvt = EVT_ERR
%+X        then
%+X             ED_ERR(er_no,er_fil,er_msg,er_msx,er_sts);
---             GCutil(3); ??????
%+X        endif;
%+X        E_PROG(TRM_REQ,"Program error terminated");
%+X   endif;

%-X   event:=bio.obsEvt; bio.obsEvt:=0;
%-X   if CNT(event) <> 0
%-X   then if CNT(event)<>1 then CNT(event):=CNT(event)-1
%-X        else -- CNT(event):=CNTshadow(event);

      ----- CNT <> 0 for all events that must lead to activation
      ----- of SIMOB (EVT_ITR/BRK/ERR). In addition, EVT_BPRG/EPRG
      ----- are set to 1 if bio.trc is set. Lastly, the event under
      ----- observation will have its CNT set to skip-count.

      ----- the remainder of the routine is not indented -----
      ----- the two if-statements closes at routine end  -----

%-X   BEGDEB; --- Start SIMOB session
%-X   run_time_error_occurred:=false; dialogue:=interactive;
%-X   -- Set previously operating instance
%-X   previns:=DYN(curins);
%-X   if previns=none then previns:=curins.sl endif;
%-X   AI:=curins; -- Reactivation point of user program --
%     repeat while AI.lsc=nowhere do AI:=AI.dl endrepeat;
%-X   repeat while (AI.sort=S_SAV) or (AI.sort=S_THK)
%-X   do AI:=AI.dl endrepeat;
%-X   OP:=AI; RT_ERR:=false; bio.pgleft:=bio.pgsize;
--- %-X         PRT(" --- before case on event");
%-X   case 0:EVT_max (event)
%-X   when
---   EVT_SST:  ED_AT_IN("*** Statement Break ",OP);
%-X   EVT_SST:  LIST_OBSERVER_POSITION;
%-X   when
%-X   EVT_ITR:  ED_AT_IN("*** ATTENTION INTERRUPT ",OP);
%-X             --- clear DO-command if any
%-X   when
%-X   EVT_BRK:  -- index := <this break's index>;
%-X             index := 0;
%-X             repeat while index <> max_break do
%-X                if stmnt(index).paddr=curins.lsc then goto FBRK endif
%-X                index:=index+1 endrepeat;
%-X             run_time_error_occurred:=true;
%-X             PRT("*** UNRECOGNIZED STATEMENT BREAK:"); goto EBRK;
%-X       FBRK: if stmnt(index).count > 0 then
%-X                dialogue:=false;
%-X                stmnt(index).count:=stmnt(index).count-1; goto USER1
%-X             endif;
%-X       EBRK: LIST_OBSERVER_POSITION;
---             PRT("*** STATEMENT BREAK:");
---       EBRK: if interactive then ED_AT_IN("*** ",OP)
---             else DISPLAY_OPERATING_CHAIN endif;
%-X   when
%-X   EVT_ERR:  RT_ERR:=true;
--- %-X         PRT(" --- at EVT_ERR");
%-X             if bio.erh.sl <> none
%-X             then if GINTIN(19) > 0
%-X                  then ED_ERR(er_no,er_fil,er_msg,er_msx,er_sts);
%-X                       if interactive then ED_AT_IN("*** ",OP)
%-X                       else bio.pgsize:=0; DISPLAY_OPERATING_CHAIN endif;
%-X                  else dialogue:=false endif;
%-X                  if status<>0 then status:=0; dialogue:=false endif;
%-X             else ED_ERR(er_no,er_fil,er_msg,er_msx,er_sts);
%-X                  if interactive then ED_AT_IN("*** ",OP)
%-X                  else bio.pgsize:=0; DISPLAY_OPERATING_CHAIN endif;
%-X                  run_time_error_occurred:=true;
%-X             endif;
%-X             if SYSMODE<>0 then GCutil(3) endif;

%-X   when
%-X   EVT_BPRG: -- A 'one-shot' event
%-X             bio.trc:=false; CNT(EVT_BPRG):=0; CNT(EVT_EPRG):=1;
%-X             ed_str("Begin SIMULA Execution ("); ed_str(sportid);
%-X             PRT(")");

%-X   when
%-X   EVT_EPRG: bio.trc:=false; ed_out;
%-X             ed_str("End SIMULA Execution, CPU Time used: ");
---             if bio.realAr then
%-X                ED_LFIX(CPUTIM-bio.initim)
---             else ed_str("(no timing)") endif;
%-X             ed_out;

%-X   when
%-X   EVT_BEG:  ED_LSC(curins);
%-X             if curins.sort=S_ATT
%-X             then ed_str("new ") else ed_str("begin ") endif;
%-X             ED_eID(curins); ed_out;

%-X   when
---   EVT_END: --- Par 1=the instance being terminated
---             ED_LSC(curins); ed_str("end "); previns:=bio.smbP1;
%-X   EVT_END: --- tmp=the instance being terminated
%-X             ED_LSC(curins); ed_str("end "); previns:=tmp.pnt;
---             goto OUTobj;
%-X             ED_eID(tmp.pnt); ed_out;

%-X   when
%-X   EVT_GOTO: --- Par 1=the instance executing goto
%-X             ed_str("goto "); ED_LSC(curins); ED_eID(curins);
%-X             ed_str("from "); ED_LSC(bio.smbP1); ED_eID(bio.smbP1);
%-X             previns:=bio.smbP1; ed_out;

%-X   when
%-X   EVT_deta, EVT_call, EVT_resu:  --- Par 1=component
%-X             ED_LSC(curins); previns:=bio.smbP1;
%-X             if event = EVT_deta
%-X             then bio.smbP1:=bio.smbP2; bio.smbP2:=none;
%-X                  ed_str("detach ");
%-X             else bio.smbP1:=curins;
%-X                  ed_str(if event=EVT_call then "call "
%-X                                           else "resume ");
%-X             endif;
---     OUTobj:
%-X             sort:=bio.smbP1.sort; bio.smbP1.sort:=bio.GCval;
%-X             ED_eID(bio.smbP1);    bio.smbP1.sort:=sort;
%-X             bio.GCval:=0; ed_out;

%-X   when
%-X   EVT_TXT: --- GCval = ncha
%-X             ED_LSC(curins); ed_str("new text, length ");
%-X             ED_INT(bio.GCval); bio.GCval:=0; ed_out;

%-X   when
%-X   EVT_ARR: --- Par 1=arrent
%-X             ED_LSC(curins); ed_str("new "); ED_ARR(bio.smbP1);
%-X             ed_out;

%-X   when
%-X   EVT_SNAP:
%-X             ED_LSC(curins); PRT(" SNAPSHOT ");

%-X   when
%-X   EVT_open, EVT_clos, EVT_inim, EVT_ouim, EVT_loca, EVT_endf:
%-X             FIL_EVENT(subCMD(event-QPS_first),bio.smbP1);

%-X   when
%-X   EVT_ACT1, EVT_ACT2, EVT_ACT3,
%-X   EVT_RACT, EVT_RAC1, EVT_RAC2, EVT_RAC3, EVT_RAC4,
%-X   EVT_acti, EVT_pass, EVT_canc, EVT_wait, EVT_hold:
%-X             if obSML <> nobody
%-X             then previns:=bio.smbP1;
%-X                  call PobSML(obSML)(event,bio.smbP1)
%-X             else PRT(" no SIMULATION block ") endif;

%-X   when
%-X   EVT_gcBEG: GC_EVENT("Begin Garbage Collection",none)

%-X   when
%-X   EVT_gcEND: GC_EVENT("End of Garbage Collection",none)

%-X   when
%-X   EVT_gcFIL: previns:=bio.smbP1; GC_EVENT(nostring,bio.smbP1);

%-X   when
%-X   EVT_gcNP:  GC_EVENT("Allocate Pool",none)

%-X   endcase; bio.pgleft:=bio.pgsize;

%-X   CMDloop:
%-X   repeat while dialogue
%-X   do bio.pgleft:=bio.pgsize; setCI(OP);

%-X      inlim:=inpos:=inlng; errstat:=0; MISS:=false;
%-X      x:=command_no(prompt("CMND: ",H_CMND));

%-X      case 0:C_max (x)
%-X      when C_CS:   CANCEL_STATEMENT
%-X      when C_DA:   DISPLAY_ATTRIBUTE
%-X      when C_DL:   DISPLAY_LOCALS
%-X      when C_DV:   DISPLAY_VISIBLES
%-X      when C_DOC:  DISPLAY_OPERATING_CHAIN
%-X      when C_DDE:  DISPLAY_DYNAMIC_ENCLOSURES
%-X      when C_DEM:  DISPLAY_ERROR_MESSAGE
%-X      when C_DOP:  ED_OP;
%-X                   if LSTMODE then LIST_OBSERVER_POSITION endif;
%-X      when C_DSB:  DISPLAY_SIMBLK
%-X      when C_DTE:  DISPLAY_TEXTUAL_ENCLOSURES
%-X      when C_ID:   INPUT_DEVICE
%-X      when C_LOE:  LIST_OBSERVED_EVENTS
%-X      when C_LOS:  LIST_OBSERVED_STATEMENTS
%-X      when C_LSF:  LIST_SOURCE
%-X      when C_LOF:  LIST_OPEN_FILES
%-X      when C_MTAI: MOVE_TO_ACTIVE_INSTANCE
%-X      when C_MTPI: MOVE_TO_PREVIOUS_INSTANCE
%-X      when C_MTDE: MOVE_TO_DYNAMIC_ENCLOSURE
%-X      when C_MTO:  MOVE_TO_OBJECT
%-X      when C_MTTE: MOVE_TO_TEXTUAL_ENCLOSURE
%-X      when C_OCF:  upevt:=OCF_last; OBSERVE_CNT(OCF_first,incond)
%-X      when C_OAC:                   OBSERVE_CNT(EVT_ARR,incond)
%-X      when C_OFE:                   OBSERVE_FIL
%-X      when C_OSE:                   OBSERVE_SML
%-X      when C_OGC:                   OBSERVE_GC
%-X      when C_OIG:                   OBSERVE_CNT(EVT_BEG,incond)
%-X      when C_OSQ:  upevt:=QPS_last; OBSERVE_CNT(QPS_first,incond)
%-X      when C_OSN:                   OBSERVE_CNT(EVT_SNAP,incond)
%-X      when C_OTG:                   OBSERVE_CNT(EVT_TXT,incond)
%-X      when C_OS:                    OBSERVE_STATEMENT
%-X      when C_RS:   goto USER2
%-X      when C_TE,C_EX:   SMB_TERM("Program Terminated from SIMOB");
%-X      when C_ALG:  ATTACH_LOGFILE
%-X %+D  when C_STR:  SMB_TRC:=inonoff
%-X      when C_SLG:  SET_LOGFILE(inonoff)
%-X      when C_SSS:  repeat prompt("Number of lines (0..255): ",0);
%-X                          x:=readByte;
%-X                   while errstat<>0 do endrepeat;   bio.pgsize:=x;
%-X      when C_SSM:  repeat prompt("Code: ",H_SSM);
%-X                          bio.GCval:=SYSMODE:=readByte;
%-X                   while errstat<>0 do endrepeat;
%-X                   GCutil(16);
%-X      when C_SLM:  LSTMODE:=inonoff
%-X      when C_DO:   DO_CMD_LINE
%-X      when C_CHM:  if DOmax=0 then CHGMODE:=inonoff
%-X                   else PRT("SET-CHANGE ignored in DO-line") endif
%-X      when C_OAS:  OBSERVE_CNT(EVT_SST,incond);
%-X                   STMNOT(CNT(EVT_SST)<>0);
%-X      otherwise if errstat=0
%-X                then FAILED("Unknown command (? lists the commands)")
%-X                endif;
%-X      endcase;
%-X      bio.pgleft:=bio.pgsize;
%-X   endrepeat;
%-X USER1:USER2:
%-X   if run_time_error_occurred then SMB_TERM("Error terminated") endif
%-X   bio.actLvl:=ACT_USR;

%-X   endif endif; ----- end of CNT(event) <> 0
%-X   outermost:=nowhere;

      bio.errmsg:=nostring; bio.smbP1:=bio.smbP2:=none;
      ENDTRP; --- terminate exception handling

      if returnFromSimob=nowhere
      then ENDDEB(nowhere) endif; --- End SIMOB session
      ---- else a trap was treated, and SIMOB was called from SMB_STMTS
---   PRT("EXIT SIMOB");
end;


%-X Routine SMB_TERM; import infix(string) mess;
--  PURPOSE: perform an user requested termination of the user system.
%-X begin bio.actLvl:=ACT_USR; E_PROG(TRM_REQ,mess) end;


%-X Routine FIL_EVENT; import infix(string) evmess; ref(filent) fil;
%-X begin 
%-X       ed_str(evmess); ed_str(" on file: ");
%-X       if    fil=bio.sysin  then PRT("sysin")
%-X       elsif fil=bio.sysout then PRT("sysout")
%-X       else  previns:=fil; PRT(TX2STR(fil.nam)) endif;
%-X end;

%-X Routine GC_EVENT; import infix(string) evmess; ref(entity) ent;
%-X begin if ent = none then PRT(evmess)
%-X       else --- EVT_gcFIL: Unclosed file found as Garbage ---
%-X            ed_str("The non-referencable open File ");
%-X            ed_str(TX2STR(ent qua filent.nam));
%-X            ed_str(" was found during Garbage Collection");
%-X            PRT(" -- Closed by Simula Runtime System");
%-X            previns:=ent; CLOSE(ent);
%-X       endif;
%-X --    when GC_PRC1,GC_PRC3,GC_UPD3: -- Follow update etc.
%-X --         ed_str(str);
%-X --         if ent = none then ed_str(" none"); ed_out;
%-X --         else ED_eID(ent);
%-X --              if trc_gc >= 3 then DMP_ENT(ent) endif;
%-X --              ed_out;
%-X --         endif;
%-X end;
%title ***  I n v a r i a b l e  ***
%-X Routine invariable; export Boolean all_attr;
-- Interprets an identification, either remote, indexed or simple. **;
-- The identification is interpreted as a sequence of simple       **;
-- identifications, where the simple identifications are separated **;
-- by dots. When this Routine returns, the attribute which the     **;
-- identification identified is known to the run-time system.      **;
--                                                                 **;
-- If an object or a text is identified together with all their    **;
-- attributes, then 'true' is returned.                            **;
--                                                                 **;
-- The identification is done relative to the observer position.   **;
%-X begin character c; infix(string) id;
%-X       Boolean found,scope_lim; ref(inst) ins;

%-X       scope_lim:=false; all_attr:=false;
%-X       setCI(OP); -- set current to observer position;
%-X       prompt("Variable: ",H_VAR);
%-X I1:I2:id:=inidentifier; if errstat <> 0 then goto E1 endif;
%-X       if scope_lim
%-X       then found:=if CE.sort=S_NOSORT then FIND_RATTR(id)
%-X                   else FIND_ATTR(CE.ins,id);
%-X       else found:=FIND_IDENT(id) endif;
%-X       if not found then SMB_ERR(id," is not found"); goto E2 endif;
%-X       --  Interprete any indices;
%-X       if nextbyte='('
%-X       then getbyte; INDX_INIT; if errstat<>0 then goto E9 endif;
%-X            if EXPR then EXPR:=false; FAILED("Array by name");
%-X               goto E3 endif;
%-X            repeat if not moreinfo then goto L1 endif;
%-X                   if number then INDX_NUMBER(innumber)
%-X                   else INDX_IDENT(inidentifier) endif;
%-X                   if errstat <> 0 then goto E4 endif;
%-X            L1:    c:=getbyte;
%-X                   if c='@'
%-X                   then prompt("Index: ",0);
%-X                        if nextbyte = ')' then c:=')'
%-X                        else c:=',' endif;
%-X                   endif;
%-X            while c=',' do endrepeat;
%-X            INDX_TERM; if errstat <> 0 then goto E5 endif;
%-X            if c <> ')' then FAILED("Missing  )") endif;
%-X       endif;

%-X       if nextbyte = '.'
%-X       then getbyte; scope_lim:=true;
%-X            if CE.atr_type=T_REF
%-X            then ins:=EVAL_REF;
%-X                 if ins=none
%-X                 then SMB_ERR(curid," == none"); goto E6 endif;
%-X                 setCI(ins);
%-X                 if nextbyte='*' then c:=getbyte else goto I1 endif;
%-X            elsif CE.atr_type=T_TXT
%-X            then c:=getbyte;
%-X                 if c <> '*' then SMB_ERR(curid," is a text");;
%-X                 goto E7 endif;
%-X            elsif CE.atr_type=T_PTR
%-X            then ins:=EVAL_PTR;
%-X                 if ins=none
%-X                 then SMB_ERR(curid," == none"); goto E10 endif;
%-X                 ---  special 'setCI' for record  ---
%-X                 CE.sort:=S_NOSORT; -- *** NB NB ***
%-X                 CE.ins:=ins; CE.elt_kind:=EK_NO;
%-X                 CE.pp:=CE.attrD qua refdes.qal;
%-X                 if nextbyte='*' then c:=getbyte else goto I2 endif;
%-X            else SMB_ERR(curid," is neither text nor reference");;
%-X                 goto E8 endif;
%-X       endif;
%-X       all_attr:= c='*';
%-X       if moreinfo then FAILED("Syntax error") endif;
%-X E1:E2:E3:E4:E5:E6:E7:E8:E9:E10: if MISS then
%-X       PRT("** Warning **  Insufficient info for identifier search") endif;
%-X end;
%title ***   A r r a y    I n d e x i n g   ***
%-X Routine INDX_INIT; -- Initiate for indexing of array in CE
%-X                    -- Very little to be done for repetitions
%-X begin name() adr; EXPR:=false;
%-X       if CE.attrD.kind = K_rep then goto S1 endif;
%-X       if CE.elt_kind <> EK_ARR
%-X       then SMB_ERR(curid," is not an array"); goto E1 endif;
---       adr:=CE.ins+CE.attrD.fld; -- set already??
%-X       adr:=CE.adr;
%-X       if CE.attrD.mode = M_NAME
%-X       then -- adr is address of an infix(PARQNT)
%-X            if var(adr qua name(infix(PARQNT))).sem
%-X            then adr:=var(adr qua name(infix(PARQNT))).ent
%-X                    + var(adr qua name(infix(PARQNT))).fld;
%-X            else EXPR:=true; goto E2 endif;
%-X       endif;
%-X       CE.arr:=var(adr qua name(ref(entity)));
%-X S1:   CE.elt_kind:=EK_IDX;
%-X E1:E2:end;


%-X Routine INDX_TERM; -- Terminate the interpretation of array indices.
%-X begin if CE.elt_kind = EK_IDX then FAILED("Too few array indices")
%-X       elsif CE.elt_kind <> EK_ARV then IERR_R("IT1") endif;
%-X end;

%-X routine OutOfBounds;
%-X begin FAILED("index out of bounds") end;

%-X routine TooManyIndex;
%-X begin FAILED("too many indices") end;

%-X Routine INDX_NUMBER;
%-X import integer ind;         -- array index
-- Interpretation of an array index which is an integer literal.
-- Check that correct number of indices is not exceeded.  Check
-- index out of bounds. Update actual index 'aind'
%-X begin ref(arent1) A1; ref(arent2) A2; integer aind,dim,nbase;
%-X       range(0:MAX_DIM) cind;
%-X       ref(arhead) head; infix(arrbnd) bnd;
%-X       if CE.elt_kind <> EK_IDX
%-X       then FAILED("variable is not an array"); goto E1 endif;
%-X       aind:=CE.aind; cind:=CE.nind;
%-X       CE.ind(cind):=ind; cind:=cind+1; CE.nind:=cind;
%-X       if CE.attrD.kind = K_REP
%-X       then if (ind<0) -- or (ind>CE.nelt)
%-X            then OutOfBounds;
%-X            elsif cind<>1 then TooManyIndex
%-X            else CE.aind:=ind; CE.elt_kind:=EK_ARV endif;
%-X       else case  0:MAX_SORT (CE.arr.sort)
%-X       when S_ARENT1,S_ARREF1,S_ARTXT1:  -- Array is one-dimensional.
%-X            A1:=CE.arr;
%-X            if cind = 1
%-X            then if (ind < A1.lb) or (ind > A1.ub) then OutOfBounds
%-X                 else CE.aind:=ind-A1.lb; CE.elt_kind:=EK_ARV endif;
%-X            else TooManyIndex endif;
%-X       when S_ARENT2,S_ARREF2,S_ARTXT2:  -- Array is two-dimensional.
%-X            A2:=CE.arr;
%-X            if cind = 1
%-X            then if (ind<A2.lb_1) or (ind>A2.ub_1) then OutOfBounds
%-X                 else CE.aind:=ind+A2.negbas endif;
%-X            elsif cind = 2
%-X            then if (ind<A2.lb_2) or (ind>A2.ub_2) then OutOfBounds
%-X                 else CE.aind:=ind*A2.dope+aind; CE.elt_kind:=EK_ARV;
%-X                 endif
%-X            else TooManyIndex endif;
%-X       when S_ARBODY:                 --  Array is multi-dimensional.
%-X            head:=CE.arr qua arbody.head;
%-X            if cind > head.ndim then TooManyIndex
%-X            else bnd:=head.bound(cind-1);
%-X                 if (ind < bnd.lb) or (ind > bnd.ub) then OutOfBounds
%-X                 elsif cind = head.ndim
%-X                 then CE.elt_kind:=EK_ARV;
%-X                      dim:=head.ndim-1; bnd:=head.bound(dim);
%-X                      nbase:=bnd.negbas; aind:=CE.ind(dim);
%-X                      repeat while dim > 0
%-X                      do dim:=dim - 1; bnd:=head.bound(dim);
%-X                         aind:=aind * bnd.dope + CE.ind(dim);
%-X                      endrepeat;
%-X                      CE.aind:=aind + nbase;
%-X                 endif
%-X            endif;
%-X       otherwise FAILED("SIMOB internal error 'IN2'") endcase;
%-X       endif;
%-X E1:end;


%-X Routine INDX_IDENT;
%-X import infix(string) ident;             -- identifier
-- Interpretation of an array index which is a single identifier.
-- Check that correct number of indices is not exceeded.  Search for
-- an integer attribute in the scope of the observer position which
-- is associated with 'ident'.  Check index out of bounds.  Store its
-- value as the next index in an indexed array expression.
%-X begin integer x; Boolean found;
%-X       UE:=CE; setCI(OP); -- set current to observer position;
%-X       found:=FIND_IDENT(ident)
%-X       if MISS then
%-X        PRT("** Warning **  Insufficient info for identifier search")
%-X       endif;
%-X       if found
%-X       then if    CE.atr_type=T_INT then x:=EVAL_INT
%-X            elsif CE.atr_type=T_SIN then x:=EVAL_SINT
%-X            elsif CE.atr_type=T_CHA then x:=EVAL_CHAR qua integer
%-X            else SMB_ERR(ident," is not of type integer");
%-X                 CE:=UE; goto E1 endif;
%-X            CE:=UE;
%-X            if EXPR
%-X            then FAILED("Can't evaluate name parameter"); EXPR:=false
%-X            else INDX_NUMBER(x) endif;
%-X       else FAILED("Can't find identifier"); CE:=UE;
%-X       endif;
%-X E1:end;
%title ***  D i s p l a y    C u r r e n t    E l e m e n t  ***
%-X Routine DISPL_CE; import Boolean star,upd;
%-X begin integer i; real r; long real lr; character c;
%-X       Boolean b; range(0:MAX_DIM) ix; ref() x; infix(txtqnt) t;

%-X       if CE.elt_kind=EK_BLK then goto E1 endif;
%-X       ED_IDT(CE.attrD.ident);
%-X       case 0:EK_MAX (CE.elt_kind)
%-X  --   when EK_BLK: --- ED_BLKID(CE.blk_vec.blk(CE.idx).pp);
%-X  --                upd:=false;                          goto E1;
%-X       when EK_VAL:
%-X       when EK_CLA: upd:=false;
%-X                    ed_str(": Class");  ---     T  E  M  P     ---
%-X                    goto E2;
%-X       when EK_REC: upd:=false;
%-X                    ed_str(": Record");  ---     T  E  M  P     ---
%-X                    goto E3;
%-X       when EK_PRO: upd:=false;
%-X                    ed_str(": Procedure");  --- T  E  M  P     ---
%-X                    goto E4;
%-X       when EK_LAB: upd:=false;
%-X                    ed_str(": Label");  ---     T  E  M  P     ---
%-X                    goto E5;
%-X       when EK_SWT: upd:=false;
%-X                    ed_str(": Switch");  ---    T  E  M  P     ---
%-X                    goto E6;
%-X       when EK_ARR: ed_str(": ");
%-X                    INDX_INIT; if errstat<>0 then goto X1 endif;
%-X                    CE.elt_kind:=EK_ARR; upd:=false;
%-X                    if EXPR then EXPR:=false; ed_str("array by name")
%-X                    elsif CE.attrD.kind=K_REP then ED_REP
%-X                    elsif CE.arr <> none then ED_ARR(CE.arr)
%-X                    else ed_str("uninitialized array") endif;
%-X                    goto E7;
%-X       when EK_ARV: ED_CHA('('); ix:=0;
%-X                    repeat ed_int(CE.ind(ix)); ix:=ix+1;
%-X                    while ix < CE.nind do ED_CHA(',') endrepeat;
%-X                    ED_CHA(')');
%-X       endcase;

%-X       ed_str(": ");
%-X       case 0:MAX_TYPE (CE.atr_type)
%-X       when T_INT: i:=EVAL_INT;    if not EXPR then ed_int(i) endif;
%-X       when T_SIN: i:=EVAL_SINT;   if not EXPR then ed_int(i) endif;
%-X       when T_REA: r:=EVAL_REAL;   if not EXPR then ED_REA(r,2) endif;
%-X       when T_LRL: lr:=EVAL_LREAL; if not EXPR then ED_LRL(lr,2) endif;
%-X       when T_CHA: c:=EVAL_CHAR;
%-X                   if not EXPR then ed_str("'"); ED_CHA(c);
%-X                      ed_str("' - "); ed_int(c qua integer) endif;
%-X       when T_BOO: b:=EVAL_BOOL;
%-X                   if not EXPR
%-X                   then ed_str(if b then "True" else "False") endif;
%-X       when T_REF: x:=EVAL_REF;  if not EXPR then ED_eID(x) endif;
%-X                   upd:=false; --- TEMP: NOT IMPLEMENTED
%-X       when T_PTR: x:=EVAL_PTR; if not EXPR then ED_OADDR(x) endif;
%-X                   upd:=false; --- TEMP: NOT IMPLEMENTED
%-X       when T_TXT: t:=EVAL_TEXT;
%-X                   if not EXPR then ED_TEXT(t,not star) endif;
%-X                   upd:=false; --- TEMP: NOT IMPLEMENTED
%-X       otherwise   ed_str("???"); upd:=false  endcase;
%-X       if EXPR then upd:=EXPR:=false; ed_str(" expression") endif;
%-X       if upd then ed_str(", new value: "); SETVALUE;
%-X       else   E1:E2:E3:E4:E5:E6:E7:  ed_out endif;
%-X X1:end;
%title ******   EVAL - get attribute GADDR and value   ******
%-X Macro EVAL(6); --  1:Routine-name, 2:Quant-variant, 3:Result-type,
                   --  4-6:Array-entity-types
%-X begin
%-X Routine %1; export %3 val;
%-X begin EXPR:=false;
%-X       if CE.attrD.kind = K_ARR
%-X       then -- CE describes an array: get value of particular element
%-X            -- The array reference has been put into arr
%-X            -- and indexing is done giving absolute element
%-X            -- index in aind (by INDX_INIT, ... , INDX_TERM).
%-X            if    CE.nind=1
%-X            then CE.adr:=name(CE.arr qua %4 .elt(CE.aind))
%-X            elsif CE.nind=2
%-X            then CE.adr:=name(CE.arr qua %5 .elt(CE.aind))
%-X            else CE.adr:=name(CE.arr qua %6 .elt(CE.aind))
%-X            endif;
%-X       else -- CE describes a simple quantity (maybe repeated)
%-X            -- The address of the (first) element is in adr
%-X            -- Check for repetition or name param (can't be both!!!)
%-X            if    CE.attrD.kind = K_REP
%-X            then -- CE describes a repetition:
%-X                 -- the (absolute) element index is in aind.
%-X                 CE.adr:=name(var(CE.adr qua name( %3 ))(CE.aind))
%-X            elsif CE.attrD.mode = M_NAME
%-X            then -- adr is address of an infix(PARQNT)
%-X                 if var(CE.adr qua name(infix(PARQNT))).sem
%-X                 then CE.adr:=var(CE.adr qua name(infix(PARQNT))).ent
%-X                            + var(CE.adr qua name(infix(PARQNT))).fld
%-X                 else EXPR:=true; goto E endif;
%-X            endif;
%-X       endif;
%-X       val:=var(CE.adr qua name(infix(quant))). %2 ;
%-X       --- NOTE: this should work for literals too
%-X E:end;
%-X endmacro;       Routine dummy; begin end;  --- T E M P ---

%-X EVAL(%EVAL_BOOL%,%boo%,%Boolean%,%booAr1%,%booAr2%,%booArr%);
%-X EVAL(%EVAL_CHAR%,%cha%,%Character%,%chaAr1%,%chaAr2%,%chaArr%);
%-X EVAL(%EVAL_REAL%,%rea%,%real%,%reaAr1%,%reaAr2%,%reaArr%);
%-X EVAL(%EVAL_LREAL%,%lrl%,%long real%,%lrlAr1%,%lrlAr2%,%lrlArr%);
%-X EVAL(%EVAL_INT%,%int%,%integer%,%intAr1%,%intAr2%,%intArr%);
%-X EVAL(%EVAL_SINT%,%sin%,%short integer%,%sinAr1%,%sinAr2%,%sinArr%);
%-X EVAL(%EVAL_TEXT%,%txt%,%infix(txtqnt)%,%txtAr1%,%txtAr2%,%txtArr%);
%-X EVAL(%EVAL_PTR%,%ptr%,%ref()%,%ptrAr1%,%ptrAr2%,%ptrArr%);


%-X Routine EVAL_REF; export ref(entity) val;
%-X begin EXPR:=false;
%-X       if CE.attrD.kind = K_ARR
%-X       then -- CE describes an array: get value of particular element
%-X            -- The array reference has been put into arr
%-X            -- and indexing is done giving absolute element
%-X            -- index in aind (by INDX_INIT, ... , INDX_TERM).
%-X            if    CE.nind=1
%-X            then CE.adr:=name(CE.arr qua refAr1.elt(CE.aind))
%-X            elsif CE.nind=2
%-X            then CE.adr:=name(CE.arr qua refAr2.elt(CE.aind))
%-X            else CE.adr:=name(CE.arr qua refArr.elt(CE.aind)) endif;
%-X       else -- CE describes a simple quantity
%-X            -- The address of the (first) element is in adr
%-X            -- Check for repetition or name param (can't be both!!!)
%-X            if    CE.attrD.kind = K_REP
%-X            then -- CE describes a repetition:
%-X                 -- the (absolute) element index is in aind.
%-X                 CE.adr:=name(var(CE.adr qua name(ref()))(CE.aind))
%-X            elsif CE.attrD.mode = M_NAME
%-X            then if CE.sort <> S_PRO
%-X                 then -- Special case:  PROCESS.NEXTEV
%-X                      CE.adr:=noname;
%-X                      if obSML = nobody then EXPR:=true -- NBNB!!!!!
%-X                      else val:=call PobSML(obSML)(-3,CE.ins) endif;
%-X                      goto E1;
%-X                 endif;
%-X                 -- adr is address of an infix(PARQNT)
%-X                 if var(CE.adr qua name(infix(PARQNT))).sem
%-X                 then CE.adr:=var(CE.adr qua name(infix(PARQNT))).ent
%-X                            + var(CE.adr qua name(infix(PARQNT))).fld
%-X                 else EXPR:=true; goto E2 endif;
%-X            endif;
%-X       endif;
%-X       val:=var(CE.adr qua name(infix(quant))).ent;
%-X E1:E2:end;
%title ******   SETVALUE - set value of attribute   ******

%-X Routine SETVALUE;
%-X begin integer ival; real rval; long real lval; infix(string) tval;
%-X       ref() xval; boolean bval; character cval;
%-X       ref(claPtp) xptp,VEptp,tmpptp; infix(string) msg;
%-X       msg.nchr:=bio.utpos; msg.chradr:= @bio.utbuff;
%-X       ival:=SIMOBprompt(msg); bio.utpos:=0;
%-X       if ival=0 then goto X0 endif;
%-X       VE:=CE; inlim:=inline.nchr;
%-X R0:   if not moreinfo then goto X1 endif;
%-X       if nextbyte='=' -- input value of variable
---       then getbyte; inlim:=inpos; -- skip '='
%-X       then          inlim:=0    ; -- skip '='
%-X            invariable;
%-X            if errstat<>0 then goto R2 endif;
%-X            case 0:MAX_TYPE (CE.atr_type)
%-X            when T_SIN: ival:=EVAL_SINT; goto I1;
%-X            when T_INT: ival:=EVAL_INT;
%-X            I1:  if EXPR then
%-X            E3:E4:E5:E6:  -- E7:E8:
%-X                      FAILED("Can't evaluate name param or expr")
%-X                      EXPR:=false; goto R1 endif;
%-X         I2:I3:  case 0:MAX_TYPE (VE.atr_type)
%-X                 when T_INT: goto L1;
%-X                 when T_SIN: goto L2;
%-X                 when T_REA: lval:=ival qua long real; goto L31;
%-X                 when T_LRL: lval:=ival qua long real; goto L41;
%-X                 when T_CHA:
%-X                      if (ival<0) or (ival>maxrnk) then
%-X           RNG2:RNG3:RNG4:  rangeError;
%-X           R1:R2:R3:        prompt("new value: ",H_SET); errstat:=0;
%-X                            goto R0 endif;
%-X                      cval:=ival qua character; goto L51;
%-X                 otherwise
%-X           F3:F4:F5:F6: -- F7:F8:
%-X           ILL1:     FAILED("of incompatible type"); goto R3;
%-X                 endcase;
%-X            when T_REA: rval:=EVAL_REAL;   if EXPR then goto E3 endif
%-X            RR:  case 0:MAX_TYPE (VE.atr_type)
%-X                 when T_INT,T_SIN:
%-X                      if (rval<minint qua real) or
%-X                         (rval>maxint qua real)
%-X                      then goto RNG3 endif;
%-X                      ival:=rval qua integer; goto I2;
%-X                 when T_REA: goto L3;
%-X                 when T_LRL: lval:=rval qua long real; goto L43;
%-X                 otherwise goto F3 endcase;
%-X            when T_LRL: lval:=EVAL_LREAL; if EXPR then goto E4 endif
%-X                 case 0:MAX_TYPE (VE.atr_type)
%-X                 when T_INT,T_SIN,T_REA:
%-X                      if (lval<minrea qua long real) or
%-X                         (lval>maxrea qua long real)
%-X                      then goto RNG4 endif;
%-X                      rval:=lval qua real; goto RR;
%-X                 when T_LRL: goto L4;
%-X                 otherwise goto F4 endcase;
%-X            when T_CHA: cval:=EVAL_CHAR;   if EXPR then goto E5 endif
%-X                 case 0:MAX_TYPE (VE.atr_type)
%-X                 when T_INT,T_SIN: ival:=cval qua integer; goto I3;
%-X                 when T_CHA: goto L5;
%-X                 otherwise goto F5 endcase;
%-X            when T_BOO: bval:=EVAL_BOOL;   if EXPR then goto E6 endif
%-X                 if CE.atr_type <> VE.atr_type then goto F6 endif;
%-X                 goto L6;
%-X   ---      when T_REF: xval:=EVAL_REF;    if EXPR then goto E7 endif
%-X   ---           if CE.atr_type <> T_REF then goto F7 endif;
%-X   ---           --- check that CE points to sub of VE ---
%-X   ---           if xval<>none
%-X   ---           then VEptp:=VE.refval qua ref(entity).pp qua ref(claPtp);
%-X   ---                xptp:=xval qua ref(entity).pp qua ref(claPtp);
%-X   ---                plv:=xptp.plv;
%-X   ---                repeat while plv<>0
%-X   ---                do plv:=plv-1; tmpPtp:=xptp.prefix(plv);
%-X   ---                   if tmpPtp=VEptp then goto L7 endif endrepeat
%-X   ---           endif;
%-X   ---           goto L7;
%-X   ---      when T_TXT: tval:=EVAL_TEXT;   if EXPR then goto E8 endif
%-X   ---           if CE.atr_type <> VE.atr_type then goto F8 endif;
%-X   ---           goto L8;
%-X            otherwise goto ILL1 endcase;
%-X       endif
%-X       errstat:=1; -- prepare to read nothing
%-X       case 0:MAX_TYPE (VE.atr_type)
%-X       when T_INT: ival:=innumber;
%-X       L1:  if errstat=0
%-X            then var(VE.adr qua name(infix(quant))).int := ival endif
%-X       when T_SIN: ival:=innumber;
%-X       L2:  if (errstat=0) -- and (minsin<=ival) and (ival<=maxsin)
%-X            then var(VE.adr qua name(infix(quant))).sin :=
%-X                    ival qua short integer endif
%-X       when T_REA: lval:=readLong;
%-X       L3:L31:  if errstat=0
%-X            then if (lval<minrea qua long real) or
%-X                    (lval>maxrea qua long real)
%-X                 then goto RNG2 endif;
%-X                 var(VE.adr qua name(infix(quant))).rea :=
%-X                     lval qua real endif;
%-X       when T_LRL: lval:=readLong;
%-X       L4:L41:L43:  if errstat=0
%-X            then var(VE.adr qua name(infix(quant))).lrl := lval endif
%-X       when T_CHA: cval:=readChar;
%-X       L5:L51:  if errstat=0
%-X            then var(VE.adr qua name(infix(quant))).cha := cval endif
%-X       when T_BOO: bval:=readBool;
%-X       L6:  if errstat=0
%-X            then var(VE.adr qua name(infix(quant))).boo := bval endif
%-X  ---  when T_REF: errstat:=0; VE.adr:=noname; -- TEMP xval:=inref;
%-X  ---  L7:  if errstat=0 then if VE.adr<>noname
%-X  ---       then var(VE.adr qua name(infix(quant))).ref := xval
---  ---       else FAILED("SEQ.SET members cannot be changed"); endif
%-X  ---       else FAILED("this pointer cannot be changed"); endif
%-X  ---       endif
%-X  ---  when T_TXT: -- tval:=instring; -- ????
%-X  ---  L8:  -- if errstat=0
%-X  ---       ---- var(VE.adr qua name(infix(quant))).int := ival endif
%-X  ---       FAILED("text not implemented YET!");
%-X       otherwise FAILED("of unchangeable type")
%-X       endcase;
%-X X1:   CE:=VE;
%-X X0: end;
%title ***    C  O  M  M  A  N  D  S    ***

%-X Routine DISPLAY_ATTRIBUTE;
%-X begin Boolean star; star:=invariable;
%-X       if errstat = 0 then DISPL_CE(star,CHGMODE) endif;
%-X end;

%-X Routine DISPLAY_VISIBLES;
%-X begin range(0:MAX_CLV) ncon; ref(inst) ins,conins;
%-X       ref(pntvec) refVec; -- Boolean blk;
%-X       ins:=OP; -- blk:=true;
%-X       repeat refVec:=ins.pp.refVec;
%-X              if refVec <> none
%-X          --  then if blk then ncon:=refVec.ncon else ncon:=0 endif;
%-X              then ncon:=refVec.ncon;
%-X                   repeat while ncon <> 0
%-X                   do ncon:=ncon-1;
%-X                      conins:=var((ins+refVec.pnt(ncon))
%-X                              qua name(ref()));
%-X                      if conins <> none
%-X                      then ed_str("*** Inspected ");
%-X                           ED_BLKID(conins.pp); PRT(" ***");
%-X                           setCI(conins);
%-X                           DISPLAY_LOCALS;
%-X                      endif;
%-X                   endrepeat;
%-X              endif;
%-X              ed_str("*** "); ED_BLKID(ins.pp); PRT(" ***");
%-X              setCI(ins);
%-X              DISPLAY_LOCALS;
%-X         --   if    ins.sort=S_SUB then blk:=true
%-X         --   elsif ins.sort=S_PRE then blk:=true
%-X         --   elsif ins.sort=S_THK then blk:=true else blk:=false endif;
%-X       while ins <> ref(bio) do ins:=ins.sl endrepeat;
%-X end;

%-X Routine DISPLAY_DYNAMIC_ENCLOSURES;
%-X begin ref(inst) ins; PRT("Dynamic Enclosures:"); ins:=OP; ED_OP;
%-X       repeat ins:=DYN(ins) while ins <> none
%-X       do ED_AT_IN("    ",ins) endrepeat;
%-X end;

%-X Routine DISPLAY_OPERATING_CHAIN;
%-X begin ref(inst) ins,x;
%-X       if OP <> ref(bio)
%-X       then PRT("Operating Chain:"); ins:=OP; ED_OP;
%-X            repeat x:=DYN(ins) while ins <> ref(bio)
%-X            do if x <> none then ins:=x
%-X               else ed_str("in the QPS-System"); ins:=ins.sl endif;
%-X               ED_AT_IN(" ",ins);
%-X            endrepeat;
%-X       endif;
%-X end;

%-X Routine DISPLAY_TEXTUAL_ENCLOSURES;
%-X begin ref(inst) ins; PRT("Textual Enclosures:"); ins:=OP; ED_OP;
%-X       repeat while ins <> ref(bio)
%-X       do ins:=ins.sl; ED_AT_IN("    ",ins) endrepeat;
%-X end;

%-X Routine ATTACH_LOGFILE;
%-X begin infix(string)id;
%-X L1:   id:=prompt("File_name: ",H_FNAM);
%-X       if (id.nchr = 0) or str_eq(id,"sysout") then goto L1 endif;
%-X       bio.logging:=true;
%-X       if bio.logfile <> 0
%-X       then ed_str("The current logfile ("); ed_str(logid);
%-X            ed_str(") is replaced by "); PRT(id); CLOSE_LOG endif;
%-X       logid:=id; OPEN_LOG;
%-X end;

%-X Routine SET_LOGFILE; import Boolean sw;
%-X begin if sw then OPEN_LOG else CLOSE_LOG endif; end;

%-X Routine LIST_SOURCE;
%-X begin range(0:MAX_KEY) key; integer lin,cnt;
%-X       infix(string) id;
%-X L1:   id:=prompt("File_name: ",H_FNAM);
%-X       if id.nchr > 0
%-X       then key:=OPFILE(id,FIL_IN,inaction,inlng);
%-X            if status <> 0
%-X            then status:=0; ed_out; ed_str("The File "); ed_str(id);
%-X                 PRT(" cannot be opened"); goto L1;
%-X            endif;
%-X            repeat prompt("Start-line (>0): ",0); lin:=innumber;
%-X                   prompt("Line-count (>0): ",0); cnt:=innumber;
%-X             while (lin<=0) or (cnt<=0) do endrepeat;
%-X            LISTFIL(key,lin,cnt,0,0);
%-X            CLFILE(key,inaction); status:=0; bio.utpos:=0;
%-X       endif;
%-X end;

%-X Routine LIST_OBSERVER_POSITION;
%-X begin integer lin,low; range(0:MAX_KEY) key;
%-X       infix(string) filid,modid;
%-X       ref(ptpExt) xpp; ref(idfier) filidt,modident;
%-X       ref(inst) ins;
%-X       ins:=OP; -- never =none, has always ptp
%-X L1:   xpp:=extppp(ins); if xpp=none then goto E1 endif;
%-X       if xpp.blkTyp=BLK_THK then ins:=ins.sl; goto L1 endif;
%-X       filidt:=xpp.modulI.source;
%-X       if filidt=none then goto E2 endif;
%-X       if filidt.ncha=0 then goto E3 endif;
%-X       filid.nchr:=filidt.ncha; filid.chradr:= name(filidt.cha);
%-X       if str_eq(filid,"sysin") then goto E4 endif;
%-X       key:=OPFILE(filid,FIL_IN,inaction,inlng);
%-X       if status <> 0
%-X       then ed_out; ed_str("The source file ("); ed_str(filid);
%-X            PRT(") cannot be opened"); goto E5;
%-X       endif;
%-X       modident:=xpp.modulI.modIdt;
%-X       if modident=none then modid:=nostring
%-X       elsif modident.ncha=0 then modid:=nostring
%-X       else modid.nchr:=modident.ncha;
%-X            modid.chradr:= name(modident.cha) endif;
%-X       lin:=GTLNO(if OP.lsc=nowhere then getoutm else OP.lsc);
%-X       if (status<>0) or (lin<=0) then goto E6 endif;
%-X       if LSTMODE
%-X       then low:=LOWLIN(modid,lin); LISTFIL(key,lin-5,10,low,lin);
%-X       else LISTFIL(key,lin,1,0,0) endif
%-X E6:   CLFILE(key,inaction); bio.utpos:=0;
%-X E1:E2:E3:E4:E5:   status:=0;
%-X end;

%-X Routine LOWLIN;
%-X import infix(string) modid; integer lno; export integer res;
%-X begin label prg,adr; integer lim;
%-X       lim:=lno-10; if lim < 0 then lim:=0 endif;
%-X       prg:=GTPADR(modid,lno); status:=0;
%-X       repeat lno:=lno-1 while lno > lim
%-X       do adr:=GTPADR(modid,lno);
%-X          if status <> 0 then status:=0; goto E1 endif;
%-X          if adr <> prg  then goto E2 endif;
%-X       endrepeat;
%-X E1:E2:res:=lno+1;
%-X end;

%-X Routine LISTFIL;
%-X import range(0:MAX_KEY) key; integer lin,cnt,fst,lst;
%-X begin integer i; boolean cont;
%-X       infix(string) linefield,markfield,imagefield;
%-X       linefield.nchr :=5; linefield.chradr :=@bio.utbuff;
%-X       markfield.nchr :=4; markfield.chradr :=name(bio.utbuff(5));
%-X       imagefield.nchr:=utlng-10;
%-X       imagefield.chradr:=name(bio.utbuff(9));
%-X       if lin < 1 then lin:=1 endif; cont:=false; ed_out;
%-X       i:=0; repeat i:=i+1 while i < lin
%-X       do fINIMA(key,imagefield);
%-X          if status <> 0 then
%-X          if status=34 then i:=i-1; status:=0;
%-X          else goto C1 endif endif; -- EOF or error
%-X       endrepeat;
%-X       i:=0; repeat i:=i+1 while i <= cnt
%-X       do if cont then ed_str("         ")
%-X          else PUTINT(linefield,lin);
%-X               C_MOVE(if (lin>=fst) and (lin<=lst)
%-X                      then ":=> " else ":   ",markfield);
%-X               lin:=lin+1;
%-X          endif;
%-X          bio.utpos:=fINIMA(key,imagefield) + 9;
%-X          if status = 0 then cont:=false
%-X          else if status <> 34 then goto C2  --- end-of-file or error
%-X               else cont:=true; status:=0; i:=i-1 endif;
%-X          endif;
%-X          ed_out;
%-X       endrepeat;
%-X C1:C2:if status <> 0
%-X       then bio.utpos:=0;
%-X            if status=13 then PRT("End-of-file")
%-X            else PRT("Listing terminated due to file error") endif;
%-X            status:=0;
%-X       endif;
%-X end;

%-X Routine LIST_OPEN_FILES;
%-X begin ref(filent) F,N;
%-X       N:=bio.files;
%-X       repeat F:=N while F <> none
%-X       do N:=F.suc;
%-X          if    F=bio.sysin  then PRT("sysin")
%-X          elsif F=bio.sysout then PRT("sysout")
%-X          else  PRT(TX2STR(F.nam)) endif;
%-X       endrepeat;
%-X end;

%-X Routine INPUT_DEVICE;
%-X begin range(0:MAX_KEY) key; infix(string) id;
%-X       if indevice<>0 then FAILED("Cannot change input device");
%-X          goto E1 endif;
%-X L1:   id:=prompt("File_name: ",H_FNAM);
%-X       if id.nchr > 0
%-X       then key:=OPFILE(id,FIL_IN,inaction,inlng);
%-X            if status <> 0
%-X            then status:=0; ed_out; ed_str("Input Device (");
%-X                 ed_str(id); PRT(") cannot be opened"); goto L1;
%-X            endif;
%-X            indevice:=key;
%-X            FAILED("NOT IMPLEMENTED YET"); --- TEMP
%-X            CLOSE_INPUT;                   --- TEMP
%-X       endif;
%-X E1: end;

%-X Routine DISPLAY_ERROR_MESSAGE;
%-X begin if RT_ERR then ED_ERR(er_no,er_fil,er_msg,er_msx,er_sts)
%-X       else PRT("No error message given") endif;
%-X end;

%-X Routine DISPLAY_SIMBLK;
%-X begin ref(inst) ins,old;
%-X       if obSML <> nobody then
%-X          ins:=call PobSML(obSML)(-1,OP);
%-X          if ins <> none
%-X          then old:=OP; OP:=ins; ED_OP;
%-X               DISPLAY_LOCALS; call PobSML(obSML)(-2,ins);
%-X               OP:=old; goto E1; endif;
%-X       endif;
%-X       FAILED("No SIMULATION-Block found");
%-X E1:end;

%-X Routine DO_CMD_LINE;
%-X begin integer DOtmp;
%-X       if DOmax<>0
%-X       then FAILED("DO must be first on line"); DOmax:=0;
%-X       else inidentifier; -- scan off DO
%-X            prompt("DO count: ",H_DO); errstat:=1; DOtmp:=innumber;
%-X            if errstat<>0 then FAILED("no DO count"); goto E1 endif;
%-X            prompt("command list: ",H_DOCMD);
%-X            if moreinfo or (nextbyte=';')
---            then inbuff(inpos):=';'; DOstart:=inpos; DOcnt:=0;
%-X            then                     DOstart:=inpos; DOcnt:=0;
%-X                 DOmax:=DOtmp;
%-X                 if CHGMODE
%-X                 then PRT("CHANGEMODE turned off");
%-X                      CHGMODE:=false endif;
%-X            else FAILED("No command list follows DO") endif;
%-X       endif;
%-X E1: end;
%title ***   N a v i g a t i o n    C o m m a n d s  ***

%-X Routine MOVE_TO_ACTIVE_INSTANCE;
%-X begin previns:=OP; OP:=AI; ED_OP end;

%-X Routine MOVE_TO_PREVIOUS_INSTANCE;
%-X begin ref(inst) tmp;
%-X       if (previns=none) -- or (OP <> AI)
%-X       then previns:=OP; MOVE_TO_DYNAMIC_ENCLOSURE
%-X       else tmp:=previns; previns:=OP; OP:=tmp; ED_OP endif;
%-X end;


%-X Routine MOVE_TO_DYNAMIC_ENCLOSURE;
%-X begin ref(inst) ins; ins:=DYN(OP);
%-X       if ins = none then FAILED("No dynamic enclosure exists")
%-X       else previns:=OP; OP:=ins; ED_OP endif;
%-X end;


%-X Routine MOVE_TO_OBJECT;
%-X begin ref(entity) obj;
%-X       invariable;
%-X       if CE.atr_type = T_REF
%-X       then if errstat = 0
%-X            then if CE.ELT_KIND = EK_VAL then goto F1 endif;
%-X                 if CE.ELT_KIND <> EK_ARV then goto E1 endif;
%-X            F1:  obj:=EVAL_REF;
%-X                 if errstat = 0
%-X                 then if obj=none then SMB_ERR(curid," == none");
%-X                      else previns:=OP; OP:=obj; ED_OP; endif;
%-X                 endif;
%-X            endif;
%-X       else E1: SMB_ERR(curid," is not a reference") endif;
%-X end;


%-X Routine MOVE_TO_TEXTUAL_ENCLOSURE;
%-X begin if OP = ref(bio) then FAILED("No textual enclosure exists")
%-X       else previns:=OP; OP:=OP.sl; ED_OP endif;
%-X end;


%-X Routine MOVE_TO_TOP; -- Set CE to head of QPS component
%-X begin ref(inst) ins;
%-X       if DYN(OP) <> none then previns:=OP endif;
%-X       repeat ins:=DYN(OP) while ins <> none do OP:=ins endrepeat;
%-X       ED_OP;
%-X end;
%title ***  B r e a k    P o i n t    H a n d l i n g  ***
%-X Routine OBSERVE_STATEMENT;
%-X begin Boolean found; range(0:max_break) free; integer lno;
%-X       infix(PROGPNT) BRK; infix(string) modid;
%-X       found:=false; prompt("Program-point: ",H_PROG);
%-X       if LETTER(nextbyte)
%-X       then inidentifier; if errstat<>0 then goto E1 endif;
%-X            modid:=curid;
%-X            if getbyte<>':'
%-X            then FAILED("Missing colon"); goto E2 endif;
%-X       else modid:=nostring endif;
%-X       lno:=innumber; BRK.count:=0;
%-X       if moreinfo
%-X       then prompt("Skip-count (>=0): ",0); BRK.count:=innumber endif
%-X       if BRK.count < 0 then BRK.count:=0 endif;

%-X       -- Let Environment set break point, and make sure no troubles
%-X       BRK.paddr:=GTPADR(modid,lno); if status <> 0
%-X       then FAILED("Can't find program point"); goto E4 endif;
%-X       lno:=GTLNO(BRK.paddr); if status <> 0
%-X       then FAILED("Can't find program point"); goto E5 endif;

%-X       free:=1;
%-X       repeat if stmnt(free).paddr=BRK.paddr
%-X              then if stmnt(free).count<>BRK.count
%-X                   then ed_str("Count reset for "); goto F2;
%-X                   else FAILED("This break is already set"); goto E7;
%-X              endif endif;
%-X              free:=free+1 while free<>max_break do endrepeat

%-X       -- Look for a free entry --
%-X       free:=1;
%-X       repeat if stmnt(free).paddr=nowhere then goto F1 endif;
%-X              free:=free+1 while free <> max_break do endrepeat;
%-X       FAILED("Statement break table is full"); goto E6;

%-X       if nbreaks=max_break
%-X       then FAILED("Too many events defined"); goto E3 endif;
%-X  F1:  BRKPNT(BRK.paddr,true); -- environment sets breakpoint
%-X       if status <> 0
%-X       then if status=36 then FAILED("Breakpoint table is full")
%-X            elsif status=37
%-X            then FAILED("Breakpoint set had no effect")
%-X            else FAILED("Unable to set breakpoint") endif;
%-X            goto E8;
%-X       endif;
%-X       nbreaks:=nbreaks+1;
%-X  F2:  stmnt(free):=BRK; -- Insert the new breakpoint in the table.;
%-X       ED_BREAK(free);
%-X       OBSERVE_CNT(EVT_BRK,1);
%-X E1:E2:E3:E4:E5:E6:E7:E8:end;


%-X Routine CANCEL_STATEMENT;
%-X begin range(0:max_break) i; i:=0;
%-X       prompt("Break_number: ",H_BRK);
%-X       if LETTER(nextbyte)
%-X       then inidentifier;
%-X            if match(curid,"ALL")
%-X            then repeat i:=i+1 while nbreaks <> 0
%-X                 do if stmnt(i).paddr <> nowhere then kancel(i) endif
%-X                 endrepeat;
%-X            else SMB_ERR("Illegal break_number: ",curid) endif;
%-X       else kancel(innumber) endif; -- remove single break point;
%-X end;

%-X Routine kancel; import range(0:max_break) i;
%-X begin ref(idfier) ident; infix(string) modid; label paddr;
---       if i < 0 then goto F1 elsif i >= max_break then goto F2 endif;
%-X       if i >= max_break then goto F2 endif;
%-X       if stmnt(i).paddr = nowhere then goto F3 endif;
%-X       -- Call upon RTS to actually remove the break point.;
%-X       BRKPNT(stmnt(i).paddr,false);     -- removes breakpoint
%-X       if status <> 0 then status:=0; goto F4 endif;
%-X       -- Remove the entry from the statement table.;
%-X       stmnt(i).paddr:=nowhere; stmnt(i).count:=0;
%-X       nbreaks:=nbreaks-1;
%-X       if nbreaks=0 then OBSERVE_CNT(EVT_BRK,0) endif;
%-X       goto E;
%-X    F2:F3:F4:FAILED("No statement attached to this break number");
%-X E:end;

%-X Routine LIST_OBSERVED_STATEMENTS;
%-X begin range(0:max_break) i,   -- statement table index;
%-X                     listed;   -- nr. of processed entries;
%-X       if nbreaks = 0 then PRT("NO STATEMENTS ARE BEING OBSERVED.")
%-X       else PRT("STATEMENTS BEING OBSERVED:"); i:=0; listed:=0;
%-X            repeat i:=i+1 while listed < nbreaks
%-X            do if stmnt(i).paddr <> nowhere
%-X               then ED_BREAK(i); ed_out; listed:=listed + 1; endif;
%-X            endrepeat;
%-X       endif;
%-X end;

%-X Routine listScanEvt;
%-X import range(0:EVT_max) low,up; infix(string) mess;
%-X begin boolean first; first:=true;
%-X       repeat if CNT(low) <> 0
%-X              then if first then ed_str(mess); first:=false endif;
%-X                   ed_str(subCMD(low-QPS_first)) endif;
%-X        while low <> up do low:=low+1 endrepeat;
%-X       if not first then ed_out endif;
%-X end;

%-X Routine LIST_OBSERVED_EVENTS;
%-X begin boolean first; range(0:EVT_max) evt;
%-X       if CNT(EVT_BEG) <> 0
%-X       then PRT("OBSERVE-INSTANCE-GENERATION") endif;
%-X       if CNT(EVT_GOTO) <> 0
%-X       then PRT("OBSERVE-CONTROL-FLOW") endif;
%-X       listScanEvt(QPS_first,QPS_last,"OBSERVE-SEQUENCING");
%-X       if CNT(EVT_ARR) <> 0
%-X       then PRT("OBSERVE-ARRAY-CREATION") endif;
%-X       if CNT(EVT_TXT) <> 0
%-X       then PRT("OBSERVE-TEXT-GENERATION") endif;
%-X       if CNT(EVT_SNAP) <> 0
%-X       then PRT("OBSERVE-SNAPSHOT") endif;
%-X       listScanEvt(FTR_first,FTR_last,"OBSERVE-FILE-EVENTS");
%-X       listScanEvt(SML_first,SML_last,"OBSERVE-SIMULATION-EVENTS");
%-X       listScanEvt(GC_first,GC_last,"OBSERVE-GARBAGE-COLLECTOR");
%-X end;

%-X Routine OBSERVE_CNT; import range(0:EVT_max) lowevt; integer i;
          --- upevt is impl. param, i=0:OFF, i=1:ON, i>1:skipcount+1
%-X begin if upevt = 0 then upevt:=lowevt endif;
%-X       repeat CNT(lowevt):=i;; CNTshadow(lowevt):=i;
%-X        while lowevt<>upevt do lowevt:=lowevt+1 endrepeat;
%-X       upevt:=0;
%-X       bio.trc:=false; lowevt:=EVT_BEG;
%-X       repeat if CNT(lowevt) <> 0 then bio.trc:=true endif;
%-X        while lowevt<>EVT_MAX do lowevt:=lowevt+1 endrepeat;
%-X end;

%-X Routine OBSERVE_SML;
%-X begin range(0:EVT_max) lowevt;
%-X L1:L2:prompt("Event: ",H_SML); inidentifier;
%-X       if str_eq(curid,"all")
%-X       then lowevt:=SML_first; upevt:=SML_last;
%-X       else if    match(curid,"ACTIVATE")  then lowevt:=EVT_ACTI
%-X            elsif match(curid,"PASSIVATE") then lowevt:=EVT_PASS
%-X            elsif match(curid,"CANCEL")    then lowevt:=EVT_CANC
%-X            elsif match(curid,"WAIT")      then lowevt:=EVT_WAIT
%-X            elsif match(curid,"HOLD")      then lowevt:=EVT_HOLD
%-X            else FAILED("Illegal event specification"); goto L1 endif
%-X       endif;
%-X       OBSERVE_CNT(lowevt,incond);
%-X       if moreinfo then goto L2 endif;
%-X end;

%-X Routine OBSERVE_FIL;
%-X begin range(0:EVT_max) lowevt;
%-X L1:L2:prompt("Event: ",H_FIL); inidentifier;
%-X       if str_eq(curid,"ALL") then lowevt:=FTR_first; upevt:=FTR_last
%-X       else if    match(curid,"OPEN")     then lowevt:=EVT_open
%-X            elsif match(curid,"CLOSE")    then lowevt:=EVT_clos
%-X            elsif match(curid,"INIMAGE")  then lowevt:=EVT_inim
%-X            elsif match(curid,"OUTIMAGE") then lowevt:=EVT_ouim
%-X            elsif match(curid,"LOCATE")   then lowevt:=EVT_loca
%-X            elsif match(curid,"ENDFILE")  then lowevt:=EVT_endf
%-X            else FAILED("Illegal event specification"); goto L1 endif
%-X       endif;
%-X       OBSERVE_CNT(lowevt,incond);
%-X       if moreinfo then goto L2 endif;
%-X end;

%-X Routine OBSERVE_SEQ;
%-X begin range(0:EVT_max) lowevt;
%-X L1:L2:prompt("Event: ",H_SEQ); inidentifier;
%-X       if str_eq(curid,"ALL") then lowevt:=QPS_first; upevt:=QPS_last
%-X       else if    match(curid,"DETACH")   then lowevt:=EVT_deta
%-X            elsif match(curid,"CALL")     then lowevt:=EVT_call
%-X            elsif match(curid,"RESUME")   then lowevt:=EVT_resu
%-X            else FAILED("Illegal event specification"); goto L1 endif
%-X       endif;
%-X       OBSERVE_CNT(lowevt,incond);
%-X       if moreinfo then goto L2 endif;
%-X end;

%-X Routine OBSERVE_GC;
%-X begin range(0:EVT_max) lowevt;
%-X L1:L2:prompt("Event: ",H_GC); inidentifier;
%-X       if str_eq(curid,"all") then lowevt:=GC_first; upevt:=GC_last
%-X       else if    match(curid,"START")  then lowevt:=EVT_gcBEG
%-X            elsif match(curid,"FINISH") then lowevt:=EVT_gcEND
%-X            elsif match(curid,"AREA")   then lowevt:=EVT_gcNP
%-X --         elsif match(curid,"PASS1")  then lowevt:=EVT_gcEP1
%-X --         elsif match(curid,"PASS2")  then lowevt:=EVT_gcEP2
%-X --         elsif match(curid,"PASS3")  then lowevt:=EVT_gcPRC3
%-X --         elsif match(curid,"FOLLOW") then lowevt:=EVT_gcPRC1
%-X --         elsif match(curid,"UPDATE") then lowevt:=EVT_gcUPD3
%-X            else FAILED("Illegal event spesification"); goto L1 endif
%-X       endif;
%-X       OBSERVE_CNT(lowevt,incond);
%-X       if moreinfo then goto L2 endif;
%-X end;
%title ***   A t t r i b u t e    S e a r c h   ***
%-X Routine FIND_IDENT;
%-X import infix(string) ident; export Boolean found;
-- Search for an attribute named 'ident' starting with CE.ins, following
-- static link.
-- Whenever an instance is searched, the deepest nested connected
-- instance (if any) must be searched first, the instance itself last.
%-X begin ref(inst) conins,ins; ref(pntvec) refVec;
%-X       range(0:max_CLV) ncon; -- Boolean blk;
%-X       if CE.sort=S_NOSORT -- record
%-X       then found:=FIND_RATTR(ident); goto E3 endif;
%-X       found:=true; -- blk:=true; -- NOE GALT m/bruk av blk
%-X       ins:=CE.ins;
%-X       repeat refVec:=ins.pp.refVec;
%-X              if refVec <> none
%-X         --   then if blk then ncon:=refVec.ncon else ncon:=0 endif;
%-X              then ncon:=refVec.ncon;
%-X                   repeat while ncon <> 0 do ncon:=ncon-1;
%-X                      conins:=var((ins+refVec.pnt(ncon))
%-X                              qua name(ref()));
%-X                      if conins <> none
%-X                      then if FIND_ATTR(conins,ident)
%-X                           then goto E1 endif;
%-X                      endif;
%-X                   endrepeat;
%-X              endif;
%-X              if FIND_ATTR(ins,ident) then goto E2 endif;
%-X       --     if    ins.sort=S_SUB then blk:=true -- HVA m/classes??
%-X       --     elsif ins.sort=S_PRE then blk:=true
%-X       --     elsif ins.sort=S_THK then blk:=true else blk:=false endif;
%-X       while ins <> ref(bio) do ins:=ins.sl endrepeat;
%-X       -- now check the implicit inspections of sysin and sysout:
%-X       if    FIND_ATTR(bio.sysout,ident) then
%-X       elsif FIND_ATTR(bio.sysin ,ident) then
%-X       else  found:=false endif;
%-X E1:E2:E3:end;

%-X routine setCI;
%-X import ref(inst) ins;
%-X begin CE.ins:=ins; CE.elt_kind:=EK_NO;
%-X       CE.sort:=ins.sort; CE.pp:=ins.pp;
%-X end;

%-X routine setCE;
%-X import ref(atrVec) avec; ref(rptvec) repvec; range(0:MAX_ATR) x;
%-X begin  name(infix(txtqnt)) tadr; field() fld; range(0:MAX_REP) i;
%-X       CE.attrD:=avec.atr(x); CE.atr_type:=CE.attrD.type;
%-X       CE.adr:=CE.ins + CE.attrD.fld;
%-X       case 0:MAX_KIND (CE.attrD.kind)
%-X       when K_CLA: CE.elt_kind:=EK_CLA
%-X       when K_REC: CE.elt_kind:=EK_REC
%-X       when K_PRO: CE.elt_kind:=EK_PRO
%-X       when K_LAB: CE.elt_kind:=EK_LAB
%-X       when K_SWT: CE.elt_kind:=EK_SWT
%-X       when K_ARR: CE.nind:=0; CE.elt_kind:=EK_ARR;
%-X       when K_REP: CE.nelt:=0; fld:=CE.attrD.fld;
%-X            if repvec<>none then i:=repvec.nrep;
%-X            repeat while i<>0 do i:=i-1;
%-X               if repvec.rep(i).type=CE.atr_type
%-X               then if CE.atr_type<>T_TXT
%-X                    then if fld=repvec.rep(i).fld
%-X                         then goto F1 endif;
%-X                    else tadr:=CE.ins+repvec.rep(i).fld;
--%-X                         if conv_field(name(var(tadr).ent))=fld
%-X                         if name(var(tadr).ent) qua field()=fld
%-X                         then goto F2 endif;
%-X               endif endif;
%-X           endrepeat; F1:F2: CE.nelt:=repvec.rep(i).nelt endif
%-X           CE.nind:=0; CE.elt_kind:=EK_ARR;
%-X       otherwise   CE.elt_kind:=EK_VAL   endcase;
%-X end;
%title   ******   find named attribute   ******

%-X Routine FIND_RATTR; -- Find attribute 'ident' in record CI
%-X import infix(string) ident; export Boolean found;
%-X begin short integer x; range(0:MAX_PLV) plv;
%-X       ref(ptpExt) xpp; ref(atrvec) avec; ref(rptvec) repvec;
%-X       plv:=CE.pp qua claptp.plv;
%-X       repeat xpp:=CE.pp qua claptp.prefix(plv).xpp;
%-X              if xpp=none then MISS:=true;
%-X              else if xpp.modulI.obsLvl < 2 then MISS:=true endif;
%-X                   avec:=xpp.attrV; x:=SEARCH_ATRVEC(avec,ident);
%-X                   if x>=0
%-X                   then setCE(avec,CE.pp.repvec,x); found:=true;
%-X                        goto E endif;
%-X              endif;
%-X       while plv <> 0 do plv:=plv - 1  endrepeat;
%-X       CE.elt_kind:=EK_NO; found:=false;  --- attribute not found
%-X E:end;

%-X Routine FIND_ATTR; -- Find attribute 'ident' in instance 'ins'
%-X import ref(inst) ins; infix(string) ident; export Boolean found;
%-X begin short integer x; range(0:MAX_PLV) plv;
%-X       ref(ptpExt) xpp; ref(atrvec) avec; ref(rptvec) repvec;
%-X       case 0:MAX_SORT (ins.sort)
%-X       when S_ATT,S_DET,S_RES,S_TRM,S_PRE:
%-X            plv:=ins.pp qua claptp.plv;
%-X            repeat xpp:=ins.pp qua claptp.prefix(plv).xpp;
%-X                   if xpp=none then MISS:=true; goto L1 endif;
%-X                   if xpp.modulI.obsLvl < 2 then MISS:=true endif;
%-X                   avec:=xpp.attrV; x:=SEARCH_ATRVEC(avec,ident);
%-X                   if x>=0 then repvec:=ins.pp.repvec; goto F1 endif;
%-X       L1:  while plv <> 0 do plv:=plv - 1  endrepeat;
%-X       when S_SUB,S_PRO:
%-X            xpp:=ins.pp.xpp;
%-X            if xpp=none then MISS:=true; goto N1 endif;
%-X            if xpp.modulI.obsLvl < 2 then MISS:=true endif;
%-X            if xpp.blkTyp <> BLK_SUB
%-X            then avec:=ins.pp qua proptp.parVec;
%-X                 x:=SEARCH_ATRVEC(avec,ident);
%-X                 if x >= 0 then plv:=1; repvec:=none; goto F2 endif;
%-X            endif;
%-X            avec:=xpp.attrV; x:=SEARCH_ATRVEC(avec,ident);
%-X            if x>=0 then plv:=0; repvec:=ins.pp.repvec; goto F3 endif
%-X       endcase;
%-X N1:   CE.elt_kind:=EK_NO; found:=false; goto E;  --- No attribute
%-X F1:F2:F3: setCI(ins); setCE(avec,repvec,x); found:=true;
%-X E:end;
%page

%-X Routine DISPLAY_LOCALS;
         -- display all attributes of CE.ins
%-X begin range(0:MAX_ATR) x; range(0:MAX_PLV) plv;
%-X       ref(ptpExt) xpp; ref(atrvec) avec; ref(inst) ins;
%-X       ref(rptvec) repvec;
%-X       case 0:MAX_SORT (CE.sort)
%-X       when S_NOSORT: goto L1;
%-X       when S_ATT,S_DET,S_RES,S_TRM,S_PRE:
%-X       L1:  plv:=CE.pp qua claptp.plv;
%-X            repeat xpp:=CE.pp qua claptp.prefix(plv).xpp;
%-X                   if xpp=none then MISS:=true; goto L2 endif;
%-X                   if xpp.modulI.obsLvl < 2 then MISS:=true endif;
%-X            ALL1:  avec:=xpp.attrV; repvec:=CE.pp.repvec;
%-X                   if avec <> none
%-X                   then x:=0;
%-X                        repeat while x < avec.natr do
%-X                             setCE(avec,repvec,x); x:=x+1;
%-X                             DISPL_CE(false,false);
%-X                        endrepeat;
%-X                   endif;
%-X       L2:  while plv > 0 do plv:=plv - 1  endrepeat;
%-X       when S_SUB: plv:=0; goto P1;
%-X       when S_PRO: plv:=1;
%-X       P1:  xpp:=CE.pp.xpp;
%-X            if xpp=none then MISS:=true;
%-X            elsif xpp.modulI.obsLvl < 1 then MISS:=true endif;
%-X            if plv = 1
%-X            then avec:=CE.pp qua proptp.parVec; repvec:=none; plv:=0;
%-X                 if avec <> none
%-X                 then x:=0;
%-X                      repeat while x < avec.natr do
%-X                           setCE(avec,repvec,x); x:=x+1;
%-X                           DISPL_CE(false,false);
%-X                      endrepeat;
%-X                 endif;
%-X            endif;
%-X            if xpp <> none then goto ALL1 endif;
%-X       endcase;
%-X       CE.elt_kind:=EK_NO;
%-X   end;
%page

%-X Routine SEARCH_ATRVEC;
%-X import ref(atrvec) attrV; infix(string)  aid;
%-X export short integer index;
-- Search in attrV for an attribute associated with aid.
-- If found, attrV.atr(index) will be corresponding
-- atrdes. If not found, index is set equal to -1.
-- Case-independent comparison
%-X begin range(0:MAX_BYT) lng,pos; range(0:MAX_ATR) natr;
%-X       ref(idfier) idt;
%-X       range(0:MAX_BYT) c1,c2,ISO_a;
%-X       ISO_a:='a' qua integer;
%-X       if attrV = none then goto E1 endif;
%-X       natr:=attrV.natr; index:=0;
%-X       repeat idt:=attrV.atr(index).ident; lng:=aid.nchr;
%-X              if idt=none then MISS:=true; goto E2 endif;
%-X              if lng = idt.ncha
%-X              then pos:=0;
%-X                   repeat c1:=idt.cha(pos) qua integer;
%-X                          c2:=var(aid.chradr)(pos) qua integer;
%-X                          if c1>=ISO_a then c1:=c1-32 endif;
%-X                          if c2>=ISO_a then c2:=c2-32 endif;
%-X                          if c1 <> c2 then goto NEXT endif;-- unequal
%-X                          pos:=pos + 1;
%-X                   while pos <> lng do endrepeat;
%-X                   goto E3; -- identifiers are equal !
%-X              endif;
%-X       NEXT:  index:=index + 1;
%-X       while  index < natr do endrepeat;
%-X E1:E2:index:= -1; -- indicates identifier not found
%-X E3:end;
%title ***   U t i l i t y    R o u t i n e s   ***
%-X Routine separator; import character ch; export boolean res;
%-X begin res:= (ch='-') or (ch='/') or (ch='_') end;

%-X Routine match; import infix(string) t1,t2; export Boolean res;
%-X --- parameter t1 compared with t2 (t2 always upper case const)
%-X begin character c1,c2; range(0:255) i,j; -- ,ISO_a,ISO_minus;
%-X       -- Case-independent comparison
%-X       c1:=c2:='?'; i:=j:=0; res:=false;
---       ISO_a:='a' qua integer; ISO_minus:='-' qua integer;
%-X       repeat while c1 = c2
%-X       do if i < t1.nchr
%-X          then c1:=var(t1.chradr)(i); i:=i+1;
%-X               if c1 >= 'a'
%-X               then c1:=(c1 qua integer-32) qua character endif;
%-X          else res:=true; goto E1 endif;
%-X          if j < t2.nchr
%-X          then c2:=var(t2.chradr)(j); j:=j+1;
%-X               if c2 >= 'a'
%-X               then c2:=(c2 qua integer-32) qua character endif;
%-X          else goto E2 endif;
---          if (c1=ISO_minus) or (c2=ISO_minus)
---          then repeat while c1 <> ISO_minus
%-X          if separator(c1)  or (c2='-')
%-X          then repeat while not separator(c1)
%-X               do if i < t1.nchr
%-X                  then c1:=var(t1.chradr)(i); i:=i+1;
%-X                  else res:=true; goto E3 endif endrepeat;
%-X               repeat while c2 <> '-'
%-X               do if j < t2.nchr
%-X                  then c2:=var(t2.chradr)(j); j:=j+1;
%-X                  else goto E4 endif endrepeat;
%-X          endif;
%-X       endrepeat;
%-X E1:E2:E3:E4:end;

%-X Routine str_eq; import infix(string) t1,t2; export Boolean res;
%-X begin integer i; character c1,c2;
%-X       -- Case-independent comparison
%-X       i:=t1.nchr; res:=false;
%-X       if i = t2.nchr
%-X       then repeat while i <> 0 do i:=i-1;
%-X                   c1:=var(t1.chradr)(i);
%-X                   if c1>='a'
%-X                   then c1:=((c1 qua integer)-32) qua character endif
%-X                   c2:=var(t2.chradr)(i);
%-X                   if c2>='a'
%-X                   then c2:=((c2 qua integer)-32) qua character endif
%-X                   if c1 <> c2 then goto E1 endif;
%-X            endrepeat;
%-X            res:=true;
%-X       endif;
%-X E1:end;

%-X Routine DYN; import ref(inst) ins; export ref(inst) res;
%-X begin if ins <> none
%-X       then case 0:MAX_SORT (ins.sort)
%-X            when S_TRM,S_DET,S_RES,S_PRE:
%-X                ins:=none -- no dynamic encl.
%-X            otherwise
%-X                repeat ins:=ins.dl while ins.lsc=nowhere do endrepeat
%-X            endcase;
%-X       endif; res:=ins;
%-X end;

%-X Routine extppp; --- get correct extptp vis a vis ins.lsc
%-X import ref(inst) ins; export ref(ptpExt) xpp;
%-X begin label paddr; ref(ptp) inspp,pp; ref(idfier) modidt;
%-X       integer lno; short integer plev;
%-X       infix(string) curpoint,modpoint,modstr;
%-X       curpoint.nchr:=40; curpoint.chradr:=name(bio.ebuf);
%-X       modpoint.chradr:=name(bio.ebuf(100));
%-X       paddr:=ins.lsc; if paddr=nowhere then paddr:=getoutm endif;
%-X       pp:=inspp:=ins.pp; xpp:=none;
%-X       curpoint.nchr:=GTLNID(paddr,curpoint);
%-X       if status=0 then lno:=GTLNO(paddr) endif;
%-X       if status<>0 then status:=0; goto X1 endif;
%-X       plev:=0;
%-X L1:   xpp:=pp.xpp; if xpp<>none then if xpp.modulI<>none
%-X       then modidt:=xpp.modulI.modIdt;
%-X            if modidt<>none
%-X            then modstr.nchr  :=modidt.ncha;
%-X                 modstr.chradr:=name(modidt.cha);
%-X            else modstr:=nostring endif;
%-X            paddr        :=GTPADR(modstr,lno);
%-X            if status<>0 then status:=0;
%-X               paddr:=GTPADR(nostring,lno);
%-X               if status<>0 then status:=0; goto L2 endif;
%-X            endif;
%-X            modpoint.nchr:=40; modpoint.nchr:=GTLNID(paddr,modpoint);
%-X            if status<>0 then status:=0; goto L3 endif;
% -X           if TXTREP(curpoint,modpoint,2) then goto X2 endif;
%-X            if STRequal(curpoint,modpoint) then goto X2 endif;
%-X       endif endif;
%-X L2:L3:if (ins.sort>=S_ATT) and (ins.sort<=S_PRE)
%-X       then if plev<=inspp qua ref(claptp).plv
%-X            then pp:=inspp qua ref(claptp).prefix(plev); plev:=plev+1
%-X                 goto L1 endif endif;
%-X X1:   xpp:=none;
%-X X2:
%-X end;

%title ***   O u t p u t   U t i l i t i e s   ***

%-X label outermost;

%-X routine getoutm; export label outm;
%-X begin outm:=if outermost<>nowhere then outermost else GTOUTM end;

%-X Routine ED_AT_IN; import infix(string) op; ref(inst) ins;
-- Display a system point and possibly the state of its instance;
%-X begin ed_str(op); ed_str("at "); ED_LSC(ins);
%-X       ed_str("in "); ED_eID(ins); ed_out;
%-X end;

%-X Routine ED_OP; begin ED_AT_IN("OP ",OP) end;

%-X Routine ED_PREFIX; import ref(claptp) pp;
%-X begin ref(ptpExt) xpp;  if pp.plv <> 0
%-X       then xpp:=pp.prefix(pp.plv-1).xpp;
%-X            if xpp=none then ed_str("Prefix") else ED_IDT(xpp.idt)
%-X            endif;
%-X       endif;
%-X end;

%-X Routine ED_BLKID; import ref(ptp) pp;
%-X begin ref(ptpExt) xpp;
%-X       if pp=none then xpp:=none else xpp:=pp.xpp endif;
%-X       if xpp <> none
%-X       then case 0:MAX_BLK (xpp.blkTyp)
%-X            when BLK_PRE: ED_PREFIX(pp); ed_str("-Block");
%-X            when BLK_CLA: if pp qua ref(claptp).plv <> 0
%-X                          then ED_PREFIX(pp); ED_CHA(' ') endif;
%-X                          ed_str("Class "); ED_IDT(xpp.idt);
%-X            when BLK_FNC,BLK_PRO:   --   ED_TYP(xpp.typ,xpp.qal);
%-X                          ed_str("Procedure "); ED_IDT(xpp.idt);
%-X            when BLK_SUB: ed_str("Sub-Block "); ---  ED_IDT(xpp.idt);
%-X            endcase;
%-X       else ED_IDT(none) endif;
%-X end;

%   routine ED_SKIP; import integer n;
%   begin if (bio.utpos+n) >= utlng then ed_out
%         else repeat n:=n-1 while n>=0 do ED_CHA(' ') endrepeat endif;
%   end;

%-X routine ED_LFIX; import long real r;
%-X begin PTLFIX(WFIELD(10),r,2);
%-X       status:=0; -- if status <> 0 then status:=0 endif;
%-X end;

%   routine ED_AADDR; import field() val;
%   begin PTAADR(WFIELD(12),val); status:=0;
%      -- if status <> 0 then status:=0; IERR_R("EDAADDR") endif;
%   end;

% -X routine ED_OADDR; import ref() val;
% -X begin PTOADR(WFIELD(12),val); status:=0;
% -X    -- if status <> 0 then status:=0; IERR_R("EDOADDR") endif;
% -X end;

% -X routine ED_PADDR; import label val;
% -X begin PTPADR(WFIELD(12),val); status:=0;
% -X    -- if status <> 0 then status:=0; IERR_R("EDPADDR") endif;
% -X end;

%   routine ED_RADDR; import entry() val;
%   begin PTRADR(WFIELD(12),val);
%         if status <> 0 then status:=0; IERR_R("EDRADDR") endif;
%   end;

%   routine ED_SIZE; import size val;
%   begin PTSIZE(WFIELD(12),val);
%         if status <> 0 then status:=0; IERR_R("EDSIZE") endif;
%   end;


%-X routine ED_TEXT;
%-X import infix(txtqnt) txt;
%-X        boolean val;      --  Output the text value?
%-X begin infix(txtqnt) txt1;
%-X       if txt.ent=none then ed_str("notext");
%-X       else if txt.ent.misc<>0 then ed_str("CONSTANT ") endif;
%-X            --  Show whether it is a substring or not.
%-X            if (txt.lp-txt.sp) < txt.ent.ncha then ed_str("Sub");
%-X            else ed_str("Main")  endif;

%-X            --  Show start position and length.
%-X            ED_CHA('('); ed_int(txt.sp + 1);
%-X            ED_CHA(','); ed_int(txt.lp-txt.sp);
%-X            ed_str(") pos="); ed_int(txt.cp-txt.sp+1);

%-X            --  Maybe show the value,i.e. the characters.
%-X            if val
%-X            then ED_CHA(' '); ED_CHA('"');
%-X                 txt1:=STRIP(txt);
%-X                 repeat while txt1.cp <> txt1.lp
%-X                 do ED_CHA(txt1.ent.cha(txt1.cp));
%-X                    txt1.cp:=txt1.cp+1;
%-X                 endrepeat;
%-X                 ED_CHA('"');
%-X                 -- show trailing blanks as '+':
%-X                 if txt1.lp<>txt.lp then ED_CHA('+') endif;
%-X            endif;
%-X       endif;
%-X       ed_out;
%-X end;

%-X routine ED_LSC; import ref(inst) ins;
%-X begin integer p,lng,lno; ref(ptpExt) xpp; label outm;
%-X       infix(string) fld; ref(idfier) id; boolean modOK;
%-X       ed_str("line "); modOK:=false;
%-X       if    ins=none then outm:=getoutm
%-X       elsif ins.lsc <> nowhere then outm:=ins.lsc
%-X       else outm:=getoutm endif;
%-X       ed_modline(outm);
%-X       if status <> 0 then status:=0; modOK:=true endif;
%-X       if ins<>none then if ins.pp<>none
%-X       then xpp:=ins.pp.xpp;
%-X            if xpp<>none
%-X            then if modOK then if xpp.modulI<>none
%-X                 then id:=xpp.modulI.modIdt
%-X                 ---  fld.nchr:=4; fld.chradr:=name(id.cha);
%-X                 ---  if fld="RTS." then id:=none endif;
%-X                      if id<>none
%-X                      then ED_CHA('('); ED_IDT(id); ED_CHA(')');
%-X                      else ed_str("(unknown module)") endif;
%-X                 endif endif;
%-X            endif
%-X       endif endif;
%-X       ED_CHA(' ');
%-X    end;

%-X routine ED_MODLINE; import label paddr; -- export in status
%-X begin range(0:utlng) p,lng; integer lno; infix(string) fld;
%-X       if paddr=nowhere then goto L1 endif;
%-X       fld:=WFIELD(25); p:=bio.utpos-25; lng:=GTLNID(paddr,fld);
%-X       if status = 0 then bio.utpos:=p+lng
%-X       else bio.utpos:=p; status:=0; lno:=GTLNO(paddr);
%-X            if status=0 then ed_int(lno)
%-X            else L1: ed_str("???") endif; status:=27;
%-X       endif;
%-X end;


%-X routine ED_TYP;
%-X import range(0:MAX_TYPE) typ; ref(claptp) qal;
%-X begin case 0:MAX_TYPE (typ)
%-X       when T_NOTYPE:                               --  Do nothing.
%-X       when T_BOO: ed_str("boolean ");
%-X       when T_CHA: ed_str("character ");
%-X       when T_SIN: ed_str("short integer ");
%-X       when T_INT: ed_str("integer ");
%-X       when T_REA: ed_str("real ");
%-X       when T_LRL: ed_str("long real ");
%-X       when T_REF,T_PTR: ed_str("ref(");
%-X                   if qal     = none then ed_str("??");
%-X                elsif qal.xpp = none then ed_str("??");
%-X                   else ED_IDT(qal.xpp.idt) endif; ed_str(") ");
%-X       when T_TXT: ed_str("text ");
%-X       endcase;
%-X end;

%-X routine ED_REP;
%-X begin
%-X       ED_TYP(CE.attrD.type,none); ed_str(" infix array(0:");
%-X       ed_int(CE.nelt); ed_cha(')'); ed_out;
%-X end;

%-X routine ED_ARR; import ref(entity) ent;
%-X begin range(0:MAX_DIM) i; integer nelt;
%-X       ref(arbody) bod; ref(arhead) head;
%-X       ref(arent1) a1; ref(arent2) a2;

%-X       ED_TYP(ent.misc,none); ed_str("Array(");
%-X       case 0:MAX_SORT (ent.sort)
%-X       when S_ARBODY,S_ARBREF,S_ARBTXT:
%-X            bod:=ent qua ref(arbody); head:=bod.head; i:=0;
%-X            repeat ed_int(head.bound(i).lb); ED_CHA(':');
%-X                   ed_int(head.bound(i).ub); i:=i+1;
%-X            while i < head.ndim do ED_CHA(',') endrepeat;
%-X            nelt:=head.nelt;
%-X       when S_ARENT2,S_ARREF2,S_ARTXT2:
%-X            a2:=ent qua ref(arent2);
%-X            nelt:=a2.dope * (a2.ub_2 - a2.lb_2 + 1);
%-X            ed_int(a2.lb_1);ED_CHA(':');ed_int(a2.ub_1); ED_CHA(',');
%-X            ed_int(a2.lb_2);ED_CHA(':');ed_int(a2.ub_2);
%-X       when S_ARENT1,S_ARREF1,S_ARTXT1:
%-X            a1:=ent qua ref(arent1); nelt:=a1.ub - a1.lb + 1;
%-X            ed_int(a1.lb); ED_CHA(':'); ed_int(a1.ub);
%-X       endcase;
%-X       if nelt=0 then PRT("), dummy array, no elements")
%-X       else ed_str("), no. of elements = "); ed_int(nelt);
%-X            ed_out endif
%-X end;

%-X Routine ED_BREAK; import range(0:max_break) free;
--- Output break point and ass. skip count ---
%-X begin infix(PROGPNT) BRK; BRK:=stmnt(free);
%-X       ed_str("Break number "); ed_int(free);
%-X       ed_str(", attached to line "); ED_MODLINE(BRK.paddr);status:=0
%-X       if BRK.count <> 0
%-X       then ed_str(" with count "); ed_int(BRK.count) endif;
%-X       if SYSMODE<>0
%-X       then ed_str(", Break address: "); ed_paddr(BRK.paddr) endif;
%-X       ed_out;
%-X end;

%-X Routine SMB_ERR; import infix(string) ms1,ms2;
%-X begin infix(string) logmes;
%-X       status:=0;
%-X       ed_out; ed_str("*** "); ed_str(ms1); ed_str(ms2);
%-X       ed_out; errstat:=1;
%-X end;

%-X Routine FAILED; import infix(string) ms;
%-X begin SMB_ERR(nostring,ms) end;


%-X Routine help; import infix(string) id; range(0:H_max) n;
%-X begin range(0:C_max) i; i:=0;
%-X       case 0:H_max (n)
%-X  when 0: -- nothing
%-X  when H_CMND: repeat if match(id,CMID(i)) then PRT(CMID(i)) endif;
%-X                      i:=i+1 while i <> C_max do endrepeat;
%-X  when H_VAR:--PRT("The syntax of a variable is:");
%-X               PRT("   variable = simple < . simple >* < . * >?");
%-X               PRT("   simple   = identifier < ( index < , index >* ) >?");
%-X               PRT("   index    = identifier  !  integer");
%-X  when H_PROG:-- PRT("The syntax of a program_point is:");
%-X               ed_str("   program-point = <module-ident : >?");
%-X               PRT("  line-number  <count_number>?");
%-X               PRT("   module_ident   = identifier");
%-X               PRT("   line-number   = integer");
%-X  when H_BRK:--PRT("The syntax of a break_number is:");
%-X               PRT("   break-number = integer  !  ALL");
%-X  when H_FNAM: PRT("The syntax of a file name is dependent upon");
%-X               PRT("the Operating System you are executing under.");
%-X               PRT("  ---  Please check it in the manual  ---");
%-X  when H_FIL:--PRT("The syntax of a FILE event is:");
%-X               PRT("   event =  ALL");
%-X            ed_str("         !  OPEN ! CLOSE ! INIMAGE !");
%-X                       PRT(" OUTIMAGE ! LOCATE ! ENDFILE");
%-X  when H_SML:--PRT("The syntax of a SIMULATION event is:");
%-X               PRT("   SIMULATION-event = ALL");
%-X            ed_str("                    !  ACTIVATE !");
%-X                          PRT(" PASSIVATE ! HOLD ! WAIT ! CANCEL");
%-X  when H_GC: --PRT("The syntax of a GARBAGE COLLECTOR event is:");
%-X               PRT("   GC-event = ALL ! START ! FINISH ! AREA");
%-X  when H_SEQ:--PRT("The syntax of a sequencing event is:");
%-X               PRT("   sequencing-event = RESUME ! CALL ! DETACH");
%-X  when H_COND:-- PRT("The syntax of an event-CONDITION is:");
%-X               PRT("   condition = ON ! OFF ! SKIP count'number");
%-X  when H_DO:   PRT("DO repeats the execution of other SIMOB commands:");
%-X               PRT("   do-command = DO  number  cmd-list"); goto DO1;
%-X  when H_DOCMD:  DO1:
%-X               PRT("   cmd-list   = command  <; command>*");
%-X               PRT("   NOTE: DO cannot occur in the command list!");
%-X  when H_SET:  PRT("Type a value (or hit CR if no change):");
%-X               PRT("   value = SIMULA-constant ! = variable");
%-X when H_SSM:   PRT("System mode controls storage checking:");
%-X               PRT("(WARNING! may produce a LARGE amount of output)")
%-X      PRT(" 1: full check, 2: print, 4: addr 8: pre-GC, 16: post-GC")
---     otherwise PRT("Sorry, no help information available") endcase;
%-X                                                           endcase;
%-X end;
%title ***  O U T P U T     H a n d l i n g  ***

% -X routine ED_CHA; import character c;
% -X begin
% -X       if PRTCHR(c)
% -X       then if bio.utpos >= utlng then ED_OUT endif;
% -X            bio.utbuff(bio.utpos):=c; bio.utpos:=bio.utpos+1;
% -X       else if bio.utpos+5 >= utlng then ED_OUT endif;
% -X            bio.utbuff(bio.utpos):='!'; bio.utpos:=bio.utpos+1;
% -X            ED_INT(c qua integer);
% -X            bio.utbuff(bio.utpos):='!'; bio.utpos:=bio.utpos+1;
% -X       endif;
% -X end;

%title ***  I N P U T     H a n d l i n g  ***

%-X character inbuff(inlng);    -- The input buffer SENERE: ALLOC-obj
%-X character simobBuf(256);    -- Command Buffer (compiled commands)

%-X Routine GetNextCMD;
%-X begin range(0:inlng) pos1; character ch;
--- L1:L2:if inline.nchr<inmax
%-X L1:   if inline.nchr<inmax
%-X       then if inline.nchr=0 then pos1:=0
%-X            elsif inbuff(inline.nchr)=';'
%-X            then pos1:=inline.nchr+1 else goto E1 endif;
%-X L2:        inline.nchr:=inmax; inpos:=pos1;
%-X            repeat ch:=inbuff(inpos) while inpos<inline.nchr
%-X            do if ch=';' then inline.nchr:=inpos
%-X               else inpos:=inpos+1 endif;
%-X            endrepeat;
%-X            inpos:=pos1;
%-X            --- skip consecutive semic's
%-X            if pos1=inline.nchr then goto L1 endif;
%-X       elsif DOcnt<DOmax
---       then DOcnt:=DOcnt+1; inline.nchr:=DOstart; goto L2 endif
%-X       then DOcnt:=DOcnt+1; pos1:=       DOstart; goto L2 endif
%-X E1: end;

%-X Routine SIMOBprompt; import infix(string) ms;
%-X export range(0:inlng) i;
%-X begin range(0:inlng) oldnchr; oldnchr:=inline.nchr;
%-X       inlim:=inline.nchr:=inlng; inpos:=0;
%-X  L1:  i:=if indevice=0 then SYSPRO(ms,inline)
%-X          else fINIMA(indevice,inline);
%-X       if status <> 0
%-X       then if status=13
%-X            then bio.trc:=dialogue:=interactive:=false;
%-X                 FAILED("EOF on SIMOB input device");
%-X                 CLOSE_INPUT; i:=inmax:=0; goto E1;
%-X            elsif (status=12) or (status=34)
%-X            then ed_str("Input line exceeds "); ed_int(inlng);
%-X                 PRT(" characters"); 
%-X            else PRT("Garbled input - try again!!!") endif
%-X            status:=0; goto L1;
%-X       endif;
%-X       if i <> 0
%-X       then inline.nchr:=inmax:=i else inline.nchr:=oldnchr; endif
%-X       if bio.logging 
%-X       then fUTIMA(bio.logfile,inline);
%-X            if status <> 0
%-X            then status:=0; CLOSE_LOG; logid:=nostring;
%-X               PRT("Logfile problem - logging stopped");
%-X       endif endif;
%-X E1: end;

%-X Routine prompt; import infix(string) ms; range(0:H_max) hn;
%-X export infix(string) R;
%-X begin integer p,i,n,lng; character ch; Boolean question;
%-X       inpos:=inlim+1;
%-X L1:   R:=nostring; lng:=0; question:=false;
%-X L2:   if inpos >= inline.nchr then GetNextCMD endif;
%-X L3:   if inpos < inline.nchr
%-X       then p:=inpos; ch:=inbuff(p); inpos:=p+1;
%-X            if ch=' ' then goto L3 endif;
%-X            if ch='?' then question:=true; goto L5 endif;
%-X            if ch=';' then goto L6 endif;
%-X            lng:=1;
%-X      L4:   if inpos < inline.nchr
%-X            then ch:=inbuff(inpos); inpos:=inpos+1;
%-X                 if ch='?' then question:=true;
%-X                 elsif (ch <> ' ') and (ch <> ',') and (ch <> ';')
%-X                   and (ch <> '=')
%-X                 then lng:=lng+1; goto L4 endif;
%-X            endif;
%-X      L6:   R.chradr:=@inbuff(p); R.nchr:=lng;
%-X       endif;
%-X      L5:
%-X %+D   if SMB_TRC
%-X %+D   then ed_str("NEXTITEM: ^"); ed_str(R); ed_str("^ ");
%-X %+D        if question then ED_CHA('?') endif; ed_out;
%-X %+D   endif;

%-X       if question then question:=false; help(R,hn); goto L1 endif;
%-X       if lng > 0 then inpos:=p; inlim:=p+lng;
%-X       else DOcnt:=DOmax:=0;
%-X            i:=SIMOBprompt(ms); -- will set inmax if anything input
%-X            if inmax <> 0
%-X            then p:=i; -- make all chars <=127
%-X                 repeat p:=p-1 while p >= 0
%-X                 do ch:=inbuff(p);
%-X                    if ch > '!127!'
%-X                    then ch:=(ch qua short integer)-128 qua character
%-X                         inbuff(p):=ch endif;
%-X                 endrepeat;
%-X                 inline.nchr:=0; goto L2;
%-X            endif;
%-X       endif;
%-X end;


%-X Routine command_no;
%-X import infix(string) id; export range(0:C_max) res;
%-X begin range(0:C_max) k,i,n; n:=i:=0; res:=0;
%-X       repeat if match(id,CMID(i))
%-X              then k:=i; n:=n+1;
%-X                   if str_eq(id,CMID(i)) then goto E endif endif;
%-X              i:=i+1 while i <> C_max do endrepeat;
%-X       if n=1 then E: res:=k+1 endif;
%-X end;
%page

%-X Routine moreinfo; export Boolean res;
%-X begin range(0:inlng) p; p:=inpos; res:=false;
%-X       repeat while p < inline.nchr
%-X       do if inbuff(p) <> ' ' then res:=true; goto E endif;
%-X          p:=p+1 endrepeat;
%-X E:end;

%-X Routine nextbyte; export character c;
%-X begin if inpos < inlim
%-X       then c:=inbuff(inpos) else c:='@' endif;
%-X end;

%-X Routine getbyte; export character c;
%-X begin if inpos < inlim
%-X       then c:=inbuff(inpos); inpos:=inpos+1 else c:='@' endif;
%-X end;

%-X Routine number; export Boolean res;
%-X begin character x; x:=nextbyte;
%-X       res:= (x='+') or (x='-') or DIGIT(x);
%-X end;

%-X Routine innumber; export integer i;
%-X begin infix(string) s;
---       s.chradr:=@inbuff(inpos); s.nchr:=inlim-inpos;
%-X       s.chradr:=@inbuff(inpos); s.nchr:=inline.nchr-inpos;
%-X       i:=GETINT(s);
%-X       if status <> 0
%-X       then status:=0;
%-X            if errstat=0 then FAILED("Illegal number") endif
%-X       else inpos:=inpos+itsize; errstat:=0;
%-X %+D        if SMB_TRC
%-X %+D        then ed_str("INNUMBER:"); ed_int(i); ed_out endif;
%-X       endif;
%-X end;

%-X Routine RANGEerror;
%-X begin FAILED("Value is out of range"); end;


%-X Routine readLong; export long real lr;
%-X begin infix(string) s;
%-X       s.chradr:=@inbuff(inpos); s.nchr:=inlim-inpos;
%-X       lr:=GTREAL(s);
%-X       if status <> 0
%-X       then status:=0;
%-X            if errstat=0 then FAILED("Illegal real") endif
%-X       else inpos:=inpos+itsize; errstat:=0;
%-X %+D        if SMB_TRC
%-X %+D        then ed_str("INNUMBER:"); ED_LRL(lr,2); ed_out endif;
%-X       endif;
%-X end;

%-X Routine readBool; export boolean bv;
%-X begin character ch;
%-X       if inpos < inlim
%-X       then ch:=getbyte; errstat:=0;
%-X            bv:= (ch='t') or (ch='T');
%-X       endif;
%-X end;

%-X routine readByte; export range(0:255) byte;
%-X begin if number
%-X       then byte:=innumber;
%-X            if (0<=byte) and (byte<=maxrnk) and (errstat=0)
%-X            then goto E endif;
%-X       endif;
%-X       if errstat=0 then FAILED("illegal value") endif
%-X E: end;

%-X Routine readChar; export character ch;
%-X begin integer i;
%-X       if inpos < inlim
%-X       then ch:=getbyte; errstat:=0;
%-X            if ch='!' then ch:=readbyte qua character
%-X            elsif (ch='!39!') and (inpos<inlim)
%-X            then ch:=nextbyte endif;
%-X       endif;
%-X end;

%-X Routine inidentifier; export infix(string) res;
%-X begin range(0:inlng) p,lng; character c;
%-X       p:=inpos; lng:=0;
%-X       if LETTER(nextbyte)
%-X       then repeat c:=nextbyte while LETTER(c) or DIGIT(c) or (c='_')
%-X            do getbyte; lng:=lng+1 endrepeat;
%-X            curid.chradr:=@inbuff(p); curid.nchr:=lng; res:=curid;
%-X %+D        if SMB_TRC then ed_str("INIDENTIFIER: "); PRT(curid)
%-X %+D        endif;
%-X       else FAILED("Missing identifier") endif;
%-X end;

%-X Routine inonoff; export Boolean res;
%-X begin res:=true;
%-X   L1: prompt("ON or OFF: ",0); inidentifier;
%-X       if str_eq(curid,"ON") then
%-X       elsif str_eq(curid,"OFF") then res:=false
%-X       elsif errstat=0 then goto L1 endif;
%-X end;

%-X Routine incond; export integer res;
%-X begin res:=1;
%-X L1:L2:prompt("Condition: ",H_COND); inidentifier;
%-X       if    match(curid,"OFF")  then res := 0
%-X       elsif str_eq(curid,"ON")  then
%-X       elsif match(curid,"SKIP")
%-X       then prompt("Number (>=0): ",0); res:=innumber+1;
%-X            if res < 1 then goto L1 endif;
%-X       elsif errstat=0 then goto L2 endif;
%-X end;
%title ***  B e g i n / E n d    U s e r    P r o g r a m  ***

--------   M O D U L E    I N F O   ---------

const infix(modinf) BASMOD=record:modinf
%-X   (modIdt=ref(RTSIDT),obsLvl=2);
%+X   (modIdt=none       ,obsLvl=0);

%-X DEFINE_IDENT(%RTSIDT%,%8%,%('R','T','S','.','M','N','T','R')%);

---------   C l a s s    S Y S T E M   ---------

 const infix(claptp:2) SYSTEMPTP=record:claptp
       (plv=0,lng = size(inst),refVec=none,xpp=none,
         dcl=nowhere,stm=nowhere,cntInr=nowhere,
         prefix =(ref(SYSTEMPTP),none));


---------   C l a s s    B A S I C I O   ---------

--- NOTE: the prototype of bioIns MUST BE COMPLETE with respect to
---       possible pointers, since bio is marked and updated as any
---       other class during GARB

 const infix(claptp:2) bioPtp=record:claptp
       (plv=0,lng = size(bioIns),refVec=ref(BIOPNT),
%-X     xpp=ref(BIOXPP),
        dcl=nowhere,stm=nowhere,cntInr=BIOINR,
        prefix =(ref(bioPtp),none));

 const infix(pntvec:13) BIOPNT=record:pntvec
       (npnt=13,pnt=( field(bioIns.sysin),
                      field(bioIns.sysout),
                      field(bioIns.files),
                      field(bioIns.opfil),
                      field(bioIns.opimg),
                      field(bioIns.thunks),
                      field(bioIns.conc),
                      field(bioIns.smbP1),
                      field(bioIns.smbP2),
                      field(bioIns.simid.ent),
                      field(bioIns.erh.sl),
                      field(bioIns.ern.ins),
                      field(bioIns.globalI)     ));

%-X const infix(ptpExt) BIOXPP=record:ptpExt
%-X    (idt=ref(BIOIDT),modulI=ref(BASMOD),
%-X     attrV=ref(BIOATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:3) BIOATR=record:atrvec
%-X    (natr=3,atr=(ref(BA_SYSIN),ref(BA_SYSOUT),
%-X                 ref(BA_SIMID)               ) );

%-X SIMPLE_ATTR(%BA_SYSIN%,%ID_SYSIN%,%bioIns.sysin%,%T_REF%);
%-X SIMPLE_ATTR(%BA_SYSOUT%,%ID_SYSOUT%,%bioIns.sysout%,%T_REF%);
%-X SIMPLE_ATTR(%BA_SIMID%,%ID_SIMID%,%bioIns.simid%,%T_TXT%);
--- SIMPLE_ATTR(%BA_GLOBAL%,%ID_GLOBAL%,%bioIns.globalI%,%T_REF%);

%-X DEFINE_IDENT(%BIOIDT%,%7%,%('B','A','S','I','C','I','O')%);
%-X DEFINE_IDENT(%ID_SYSIN%,%5%,%('S','Y','S','I','N')%);
%-X DEFINE_IDENT(%ID_SYSOUT%,%6%,%('S','Y','S','O','U','T')%);
%-X DEFINE_IDENT(%ID_SIMID%,%5%,%('S','I','M','I','D')%);
--- DEFINE_IDENT(%ID_GLOBAL%,%6%,%('G','L','O','B','A','L')%);

%page
Visible routine B_PROG;
-- import ref(claptp) bioPtp;        --  Prototype of BASICIO.
exit label psc;
begin ref(inst) sys; integer i;
      actLvl:=ACT_INI; bioref:=curins:=none; status:=0; -- bio.utpos:=0;
      rstr_x:=none; txttmp:=notext;
%-X   SYSMODE:=0; outermost:=nowhere;
      errorX:=entry(EXERR); smb:=entry(SIMOB); -- For diagnostics.
      obSML:=nobody; returnFromSimob:=nowhere;
      allocO:=entry(xalloc); freeO:=entry(xfree);
      INITIA(entry(exchdl));                 -- Exception handler.
      maxlen:= none + maxlen + size(savent) - none;
      ---  Compute how much storage we must allocate for the predefined
      ---  enclosing instances, create initial storage pool and initlize
      ---  the pool structure. Also zero bio and set bio.realAr
      B_STRG(none + SYSTEMPTP.lng       -- system head
                  + pflPtp.lng          -- SYSOUT
                  + size(txtent:outlth) -- sysout.image
      --          + pflPtp.lng          -- SYSTRACE (not impl)
                  + FILPTP.lng          -- SYSIN
                  + size(txtent:inplth) -- sysin.image
                  + size(txtent:10) + size(txtent:10) -- size(txtent:10)
                  ---  action strings for SYSOUT, SYSIN -- and SYSTRACE
           - none );
      sys:=bio.nxtAdr;
      bio.nxtAdr:=bio.nxtAdr + SYSTEMPTP.lng;
      sys.sort:=S_PRE; sys.pp:=ref(SYSTEMPTP);
      sys.lsc:=psc;    --  So that we know where the user program is!
      bio.sl:=bio.dl:=sys; curins:=ref(bio);
      bio.sort:=S_PRE; bio.pp:=ref(bioptp);
      bioref:=ref(bio);
---   if bio.realAr then
         bio.initim:=CPUTIM; status:=0;
---   endif;
---   bio.lsc:=BIODCL;
      ---  Create the outermost prefixed block instance.
      ---  This instance will enclose BASICIO.
%-X   upevt:=EVT_ITR; OBSERVE_CNT(0,1); -- never changed
%-X   upevt:=EVT_max; OBSERVE_CNT(EVT_BEG,0);
---   bio.maxint:=maxint;    bio.minint:=minint;
---   bio.maxrea:=maxrea;    bio.minrea:=minrea;
---   bio.maxlrl:=maxlrl;    bio.minlrl:=minlrl;
---   bio.maxrnk:=maxrnk;

      psc:=BIODCL; -- continue at label below
end;


Routine SYS_FIL_SPC;
import range(1:3) code; export infix(txtqnt) txt;
begin integer n;
      n:=GDSPEC(code,STRBUF(0));
      -- if status <> 0 then ERROR0 endif; 
      if status=0
      then txt:=BLANKS(n); C_MOVE(STRBUF(n),TX2STR(txt)) endif;
end;


Visible routine  E_PROG;
import range(0:MAX_TRM) trm; infix(string) mess;
begin ref(filent) F,N; boolean noneOpen;
%-X   bio.trc:=false; noneOpen:=true;
      if bio.sysout.img.cp > bio.sysout.img.sp
      then OUTIM(bio.sysout) endif;
%-X   if trm=TRM_NRM then
         if GINTIN(19)<>0 then -- cannot use bio.trc here!!!
%-X           bio.obsEvt:=EVT_EPRG; call PSIMOB(smb);
              E_STRG; endif;
%-X      status:=0;
%-X   endif;
      if bio.edOflo <> 0
      then ed_out; ed_str("No. of text edit overflows: ");
           ed_int(bio.edOflo); ed_out;
      endif;
      actLvl:=ACT_TRM; N:=bio.files;
      repeat F:=N while F <> none
      do N:=F.suc; if F=bio.sysin then elsif F=bio.sysout then
         else
%-X           if noneOpen
%-X           then ed_str("The file(s) "); noneOpen:=false;
%-X           else ed_str(", ") endif;
%-X           ed_str(TX2STR(F.nam));
              CLOSE(F);
         endif;
      endrepeat;
%-X   if noneOpen then -- do nothing
%-X   else ed_out;
%-X    PRT(" -- closed at program termination by SIMULA Runtime System")
%-X   endif;
%-X   CLOSE_LOG; CLOSE_INPUT;
      CLOSE(bio.sysin); CLOSE(bio.sysout); TERMIN(trm,mess);
end;

%-X Routine OPEN_LOG;
%-X begin bio.logging:=true;
%-X       if logid.nchr > 0
%-X       then bio.logfile:=OPFILE(logid,FIL_OUT,logAction,utlng);
%-X                -- noshared,append,anycreate,writeonly,nopurge,rewind
%-X            if status <> 0 then if status <> 6
%-X            then bio.logging:=false; bio.logfile:=0; logid.nchr:=0;
%-X                 SMB_ERR(logid," (SIMOB log file) cannot be opened");
%-X            endif endif; status:=0;
%-X       else bio.logging:=false; FAILED("No SIMOB log file defined")
%-X       endif;
%-X end;

%-X Routine CLOSE_LOG;
%-X begin if bio.logfile<>0 then CLFILE(bio.logfile,logAction) endif;
%-X       status:=0; -- ignore problems
%-X       bio.logfile:=0; bio.logging:=false;
%-X end;

%-X Routine CLOSE_INPUT;
%-X begin if indevice<>0 then CLFILE(indevice,inAction) endif;
%-X       indevice:=0; status:=0; -- ignore problems
%-X end;

Visible routine ERRX; import infix(txtqnt) mss;
begin bio.errmsg.nchr:=mss.lp-mss.sp;
      bio.errmsg.chradr:=name(mss.ent.cha(mss.sp));
      ERROR(ENO_SYS_1);
end;

Visible routine TRMP;
begin E_PROG(TRM_REQ,nostring) end;
%page
 BIODCL:           ---  We get here only once, when exiting from B_PROG.

    ---  Create and open sysout.
    B_CLA(bioref,ref(pflPtp)); bio.sysout:=tmp.pnt;
    bio.sysout.nam:=SYS_FIL_SPC(2);
    if status <> 0 then TERMIN(3,"Cannot open SYSOUT") endif
% +M ED_STR("MNTR.BIODCL: sysout="); ED_TXT(bio.sysout.nam); ED_OUT;   
    OPEN(bio.sysout,BLANKS(outlth));

    ---  Create and open sysin.
    B_CLA(bioref,ref(iflPtp)); bio.sysin:=tmp.pnt;
    bio.sysin.nam:=SYS_FIL_SPC(1);
    if status <> 0 then TERMIN(3,"Cannot open SYSIN") endif
% +M ED_STR("MNTR.BIODCL: sysin="); ED_TXT(bio.sysin.nam); ED_OUT;   
    OPEN(bio.sysin,BLANKS(inplth));

    ---  Now when the system files are open,we can output error messages

    -- bio.obsEvt:=0;
    actLvl:=ACT_USR;
--    LTEN('&'); DECMRK('.'); status:=0; bio.lwten:='&'; bio.dcmrk:='.'; -- MYH
    -- bio.lwten:='&'; LTEN('&');
    -- bio.dcmrk:='.'; DECMRK('.'); if status <> 0 then ERROR0 endif;
%-X    if GINTIN(19)>0
%-X    then bio.trc:= true;
%-X         bio.pgleft:=bio.pgsize:=24;
%-X    endif;
%-X    status:=0;
%-X    curid:=nostring; inpos:=inlim:=inmax:=0;
%-X    inline.chradr:=@inbuff; inline.nchr:=0;
%-X    errstat:=0; indevice:=0; DOmax:=DOcnt:=0;
%-X    nbreaks:=max_break;
%-X    repeat nbreaks:=nbreaks-1; stmnt(nbreaks).paddr:=nowhere
%-X    while nbreaks > 0 do endrepeat;
%-X    interactive:=GINTIN(33) > 0;
%-X    if status <> 0 then status:=0; interactive:=false endif;

    ---  Create text concatenation work area.
    bio.conc:=ar1new(T_TXT,0,MAX_DIM-1) qua txtAr1;
    ---  Set simid
    bio.simid:=BLANKS(sportid.nchr);
    dest.chradr:=name(bio.simid.ent.cha); dest.nchr:=sportid.nchr;
    C_MOVE(sportid,dest);

%-X    if bio.trc then CNT(EVT_BPRG):=1; -- trace begin program
%-X       bio.obsEvt:=EVT_BPRG; call PSIMOB(smb) endif;

    goto   bio.sl.lsc;  ---  Now we can go back to the user program.

-- %visible
BIOINR:  IERR_R("BIOINR");            ---  Pray you will never get here.
-- %hidden


 USR_STMTS:   
---           ed_str("USR_STMTS: bio.errmsg: "); PRT(bio.errmsg);
              ---  observation tool. Issue the appropriate diagnostics.
              ---  If the error occurred in the user program and the
              ---  observation tool is present, give control to it.
              bio.obsEvt:=EVT_ERR;
 SMB_STMTS:   -- Call SIMOB --
---           ed_str("SMB_STMTS: bio.errmsg: "); PRT(bio.errmsg);
              retur:=returnFromSimob; returnFromSimob:=nowhere;
%-X           actLvl:=ACT_SMB; call PSIMOB(smb);
              actLvl:=ACT_USR;
              if (bio.erh.sl <> none)
%-X              and RT_ERR
              then
%-X                ENDDEB(nowhere); -- SIMOB called only in -X case
%-X                --- trap return is ignored in this case
                   var((bio.ern.ins+bio.ern.fld)
                        qua name(integer)) := er_no;
                   erh:=bio.erh; bio.erh.sl:=none; bio.ern.ins:=none;
%+X                bio.obsEvt:=0;
                   E_GOTO(erh);
              else
%+X                actLvl:=ACT_SMB; call PSIMOB(smb);
                   --- SIMOB was called, in both +X and -X cases
                   ENDDEB(retur);    --- resume after EXCEPTION
%-X                goto curins.lsc;  --- Resume after ERROR
---   HVOR VAR curins.lsc SATT i tilfelle ERROR ?????
              endif;

 TRM_STMTS:   -- Control should come here if controlled
              -- termination is wanted after an error.
---           ed_str("TRM_STMTS: bio.errmsg: "); PRT(bio.errmsg);
              actLvl:=ACT_TRM; E_PROG(er_trm,bio.errmsg);

 SYS_STMTS:   -- Control should come here if immediate
              -- termination is wanted after an error.
---           ed_str("SYS_STMTS: bio.errmsg: "); PRT(bio.errmsg);
              actLvl:=ACT_TRM; TERMIN(er_trm,bio.errmsg);
end;
