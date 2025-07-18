Module fil("RTS");
 begin sysinsert rt,sysr,knwn,util,strg,cent

       -----------------------------------------------------------------
       ---  COPYRIGHT 1989 by                                        ---
       ---  Simula a.s.                                              ---
       ---  Oslo, Norway                                             ---
       ---                                                           ---
       ---                 P O R T A B L E     S I M U L A           ---
       ---                  R U N T I M E     S Y S T E M            ---
       ---                                                           ---
       ---                             F i l e s                     ---
       ---                                                           ---
       -----------------------------------------------------------------



ref(claptp) subpp;

 Visible macro DEF_TXTENT(3)
 begin const infix(txtent: %2 ) %1 = record:txtent
       (sl=none, sort=S_TXTENT, misc=1, ncha = %2 , cha = %3 )
 endmacro;

 Visible macro REC_TXTQNT(2)
 begin record:txtqnt ( ent = ref( %1 ), cp=0, sp=0, lp = %2 )
 endmacro;

 DEF_TXTENT(%defient%,%10%,
       %('!0!','!1!','!1!','!0!','!1!','!2!','!0!','!0!','!0!','!0!')%);

 DEF_TXTENT(%defoent%,%10%,
       %('!1!','!1!','!2!','!1!','!1!','!2!','!0!','!0!','!0!','!0!')%);
 DEF_TXTENT(%defdent%,%10%,
       %('!1!','!1!','!1!','!2!','!1!','!5!','!0!','!0!','!0!','!0!')%);

 const infix(txtqnt) acmin = REC_TXTQNT(%defient%,%10%);
 const infix(txtqnt) acmout= REC_TXTQNT(%defoent%,%10%);
 const infix(txtqnt) acmdir= REC_TXTQNT(%defdent%,%10%);


--------   M O D U L E    I N F O   ---------

const infix(modinf) FILMOD=record:modinf
%-X    (modIdt=ref(RTSIDT),obsLvl=2);
%+X    (modIdt=none       ,obsLvl=0);

--- DEFINE_IDENT(%RTSIDT%,%7%,%('R','T','S','.','F','I','L')%);
%-X DEFINE_IDENT(%RTSIDT%,%10%,%('C','L','A','S','S',' ','F','I','L','E')%);

---------   C l a s s    F I L E   ---------

%tag (filrec,filPtp)

Visible record filRec:filent; begin end;

Visible const infix(claptp:2)  FILPTP=record: claptp
       (plv=0,lng=size(filent),refVec=ref(FILPNT),
%-X     xpp=ref(FILXPP),
        dcl=FILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),none));

const infix(pntvec:2) FILPNT=record:pntvec
       (npnt=2,pnt=(field(filent.nam.ent),field(filent.action.ent)));

%-X const infix(ptpExt) FILXPP=record:ptpExt
%-X    (idt=ref(FILIDT),modulI=ref(FILMOD),
%-X     attrV=ref(FILATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:1) FILATR=record:atrvec
%-X    (natr=1,atr=ref(FA_NAM));

%-X SIMPLE_ATTR(%FA_NAM%,%ID_NAM%,%filent.nam%,%T_TXT%);

%-X DEFINE_IDENT(%FILIDT%,%4%,%('F','I','L','E')%);
%-X DEFINE_IDENT(%ID_NAM%,%8%,%('F','I','L','E','N','A','M','E')%);
%page

---------   C l a s s    I M A G E F I L E   ---------

%tag (imfRec,imfPtp)

Visible record imfRec:filent; begin end;

Visible const infix(claptp:3)  IMFPTP=record: claptp
       (plv=1,lng=size(filent),refVec=ref(IMFPNT),
%-X     xpp=ref(IMFXPP),
        dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(IMFPTP),none));

const infix(pntvec:1) IMFPNT=record:pntvec
       (npnt=1,pnt=field(filent.img.ent));

%-X const infix(ptpExt) IMFXPP=record:ptpExt
%-X    (idt=ref(IMFIDT),modulI=ref(FILMOD),attrV=ref(IMFATR),
%-X     blkTyp=BLK_CLA);

%-X const infix(atrvec:1) IMFATR=record:atrvec
%-X    (natr=1,atr=ref(FA_IMG));

%-X SIMPLE_ATTR(%FA_IMG%,%ID_IMG%,%filent.img%,%T_TXT%);

%-X DEFINE_IDENT(%IMFIDT%,%9%,%('I','M','A','G','E','F','I','L','E')%);
%-X DEFINE_IDENT(%ID_IMG%,%5%,%('I','M','A','G','E')%);


---------   C l a s s    B Y T E F I L E   ---------

%tag (btfRec,btfPtp)

Visible record btfRec:filent; begin end;

Visible const infix(claptp:3)  BTFPTP=record: claptp
       (plv=1,lng=size(filent),
%-X     xpp=ref(BTFXPP),
        dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
         prefix=(ref(FILPTP),ref(BTFPTP),none));

%-X const infix(ptpExt) BTFXPP=record:ptpExt
%-X    (idt=ref(BTFIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%BTFIDT%,%8%,%('B','Y','T','E','F','I','L','E')%);


---------   C l a s s    I n b y t e f i l e   ---------

%tag (ibfRec,ibfPtp)

 Visible record ibfRec:filent; begin end;

 Visible const infix(claptp:4) ibfPtp=record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(IBFXPP),
        dcl=IBFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(BTFPTP),ref(ibfPtp),none));

%-X const infix(ptpExt) IBFXPP=record:ptpExt
%-X    (idt=ref(IBFIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%IBFIDT%,%10%,%('I','N','B','Y','T','E','F','I','L','E')%);


---------   C l a s s    O u t b y t e f i l e   ---------

%tag (obfRec,obfPtp)

 Visible record obfRec:filent; begin end;

 Visible const infix(claptp:4) obfPtp=record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(OBFXPP),
        dcl=OBFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(BTFPTP),ref(obfPtp),none));

%-X const infix(ptpExt) OBFXPP=record:ptpExt
%-X    (idt=ref(OBFIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%OBFIDT%,%11%,%('O','U','T','B','Y','T','E','F','I','L','E')%);


---------   C l a s s    D i r e c t b y t e f i l e   ---------

 Visible record dbfRec:filent; begin end;

 Visible const infix(claptp:4) dbfPtp=record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(DBFXPP),
        dcl=DBFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(BTFPTP),ref(dbfPtp),none));

%-X const infix(ptpExt) DBFXPP=record:ptpExt
%-X    (idt=ref(DBFIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%DBFIDT%,%14%,%('D','I','R','E','C','T','B','Y','T','E','F','I','L','E')%);


---------   C l a s s    I n f i l e   ---------

%tag (iflRec,iflPtp)

 Visible record iflRec:filent; begin end;

 Visible const infix(claptp:4) iflPtp=record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(IFLXPP),
        dcl=IFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(IMFPTP),ref(iflPtp),none));

%-X const infix(ptpExt) IFLXPP=record:ptpExt
%-X    (idt=ref(IFLIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%IFLIDT%,%6%,%('I','N','F','I','L','E')%);


---------   C l a s s    O u t f i l e   ---------

%tag (oflRec,oflPtp)

 Visible record oflRec:filent; begin end;

 Visible const infix(claptp:4) oflPtp =record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(OFLXPP),
        dcl=OFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(IMFPTP),ref(oflPtp ),none));

%-X const infix(ptpExt) OFLXPP=record:ptpExt
%-X    (idt=ref(OFLIDT),modulI=ref(FILMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%OFLIDT%,%7%,%('O','U','T','F','I','L','E')%);


---------   C l a s s    D i r e c t f i l e   ---------

%tag (dflRec,dflPtp)

 Visible record dflRec:filent; begin end;

 Visible const infix(claptp:4) dflPtp =record:claptp
       (plv=2,lng=size(filent),refVec=none,
%-X     xpp=ref(DFLXPP),
        dcl=DFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(IMFPTP),ref(dflPtp ),none));

%-X const infix(ptpExt) DFLXPP=record:ptpExt
%-X    (idt=ref(DFLIDT),modulI=ref(FILMOD),
%-X     attrV=ref(DFLATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:1) DFLATR=record:atrvec
%-X    (natr=1,atr=ref(FA_NAM));

%-X SIMPLE_ATTR(%FA_LOC%,%ID_LOC%,%filent.loc%,%T_INT%);

%-X DEFINE_IDENT(%DFLIDT%,%10%,%('D','I','R','E','C','T','F','I','L','E')%);
%-X DEFINE_IDENT(%ID_LOC%,%3%,%('L','O','C')%);


---------   C l a s s    P r i n t f i l e   ---------

%tag (pflRec,pflPtp)

 Visible record pflRec:prtent; begin end;

 Visible const infix(claptp:5) pflPtp =record:claptp
       (plv=3,lng=size(prtEnt),refVec=none,
%-X     xpp=ref(PFLXPP),
        dcl=PFILDCL,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(FILPTP),ref(IMFPTP),ref(oflPtp ),ref(pflPtp ),none))

%-X const infix(ptpExt) PFLXPP=record:ptpExt
%-X    (idt=ref(PFLIDT),modulI=ref(FILMOD),
%-X     attrV=ref(PFLATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:4) PFLATR=record:atrvec
%-X    (natr=4,atr=(ref(FA_SPC),ref(FA_LPP),ref(FA_LIN),ref(FA_PAG)));

%-X SIMPLE_ATTR(%FA_SPC%,%ID_SPC%,%prtEnt.spc%,%T_INT%);
%-X SIMPLE_ATTR(%FA_LPP%,%ID_LPP%,%prtEnt.lpp%,%T_INT%);
%-X SIMPLE_ATTR(%FA_LIN%,%ID_LIN%,%prtEnt.lin%,%T_INT%);
%-X SIMPLE_ATTR(%FA_PAG%,%ID_PAG%,%prtEnt.pag%,%T_INT%);

%-X DEFINE_IDENT(%PFLIDT%,%9%,%('P','R','I','N','T','F','I','L','E')%);
%-X DEFINE_IDENT(%ID_SPC%,%7%,%('S','P','A','C','I','N','G')%);
%-X DEFINE_IDENT(%ID_LPP%,%14%,%('L','I','N','E','S','_','P','E','R','_','P','A','G','E')%);
%-X DEFINE_IDENT(%ID_LIN%,%4%,%('L','I','N','E')%);
%-X DEFINE_IDENT(%ID_PAG%,%4%,%('P','A','G','E')%);
%title   ********   Error handling routines   ********

 -- routine FILerr must be called only after relevant status testing
 -- has been performed elsewhere - this routine will terminate exec.

 -- Message number 18: Internal RTS error
 --                19: Internal error in ENVIR
 --                20: Internal error in FEC (?)

--%DEFINE ENO_FST_1 =113, -- Invalid filekey
DEFINE ENO_FST_1 =113, -- Invalid filekey
        ENO_FST_2 =134, -- File not defined
        ENO_FST_3 =135, -- File does not exist
        ENO_FST_4 =136, -- File already exists
        ENO_FST_5 =18 , -- File not open
        ENO_FST_6 =18 , -- File already open
        ENO_FST_7 =18 , -- File closed (by someone else)
        ENO_FST_8 =18 , -- Illegal use of file (e.g. read from outfile)
        ENO_FST_9 =18 , -- Illegal record format for dir.file
        ENO_FST_10=18 , -- Illegal file name (syntax error)
        ENO_FST_11=18 , -- Output image too long
        ENO_FST_12=18 , -- Input image too short
        ENO_FST_13=18 , -- EOF on input
        ENO_FST_14=19 , -- Not enough work space, NOT A FILE ERROR
        ENO_FST_15=18 , -- File full on output (EOF)
        ENO_FST_16=18 , -- Location out of range, or never written
        ENO_FST_17=108, -- Hardware I/O error
        ENO_FST_18=137, -- Access method has not been implemented
        ENO_FST_19=138, -- Impossible request
        ENO_FST_20=109, -- File is write-protected
        ENO_FST_21=18 , -- Nonnumeric item (de-editing)
        ENO_FST_22=18 , -- Value out of range (de-editing)
        ENO_FST_23=18 , -- Incomplete real (de-editing)
        ENO_FST_24=19 , -- Text is too short (editing)
        ENO_FST_25=18 , -- Fraction part has negative length (editing)
        ENO_FST_26=19 , -- --- not used
        ENO_FST_27=19 , -- Argument out of range, NOT A FILE ERROR
        ENO_FST_28=19 , -- File already has a key
        ENO_FST_29=110, -- Too many simultaneously open files
        ENO_FST_30=19 , -- This give/get routine not impl.
        ENO_FST_31=103, -- Syntax error in dsetspec
        ENO_FST_32=111, -- File is read-protected
        ENO_FST_33=19 , -- Illegal action (?)
        ENO_FST_34=105, -- Partial record read (inimage)
        ENO_FST_35=18 , -- Undefined record (directfile)
        ENO_FST_36=19 ; -- Max number of breaks set, NOT A FILE ERROR

 const short integer filstaterr(37) =
       (        0 ,ENO_FST_1 ,ENO_FST_2 ,ENO_FST_3 ,ENO_FST_4 , -- 0..
        ENO_FIL_3 ,ENO_FIL_1 ,ENO_FIL_21,ENO_FIL_22,ENO_FIL_23, -- 5..
        ENO_FIL_24,ENO_FIL_25,ENO_FIL_26,ENO_FIL_8 ,ENO_FST_14, -- 10..
        ENO_FIL_27,ENO_FIL_28,ENO_FIL_29,ENO_FST_18,ENO_FST_19, -- 15..
        ENO_FIL_30,ENO_FIL_9 ,ENO_FIL_10,ENO_FIL_11,ENO_FST_24, -- 20..
        ENO_FIL_17,ENO_FST_26,ENO_FST_27,ENO_FST_28,ENO_FIL_31, -- 25..
        ENO_FST_30,ENO_FIL_24,ENO_FIL_32,ENO_FST_33,ENO_FST_34, -- 30..
        ENO_FIL_28,ENO_FST_36 );                                -- 35..

 Visible routine FILerr;
 import ref(filent) fil;
        boolean eof;  --  Is fil.eof significant?
        boolean open; --  Is fil.key = 0 significant?
 begin range(0:MAX_ENO)   eno;

       if open and (fil.key=0) then eno:=ENO_FIL_3;
       elsif eof and fil.eof   then eno:=ENO_FIL_33;
       else eno:=filstaterr(status)
   --  else case  0:MAX_STS (status)
   --       when  7: eno:=ENO_FIL_21;
   --       when  8: eno:=ENO_FIL_22;
   --       when  9: eno:=ENO_FIL_23;
   --       when 10: eno:=ENO_FIL_24;
   --       when 11: eno:=ENO_FIL_25;
   --       when 12: eno:=ENO_FIL_26;
   --       when 13: eno:=ENO_FIL_8 ;
   --       when 15: eno:=ENO_FIL_27;
   --       when 16: eno:=ENO_FIL_28;
   --       when 17: eno:=ENO_FIL_29;
   --       when 20: eno:=ENO_FIL_30;
   --       when 21: eno:=ENO_FIL_9 ;
   --       when 22: eno:=ENO_FIL_10;
   --       when 23: eno:=ENO_FIL_11;
   --       when 25: eno:=ENO_FIL_17;
   --       when 29: eno:=ENO_FIL_31;
   --       when 31: eno:=ENO_FIL_24;
   --       when 32: eno:=ENO_FIL_32;
   --       when 35: eno:=ENO_FIL_28;
   --       otherwise eno:=ENO_FIL_34 endcase;
       endif;
       ERROR1(eno,fil);
 end;

%title ******   O P E N   ******

 routine fOPEN;
 import ref(filent) fil; infix(txtqnt) img;
 export boolean success;
 begin range(0:MAX_KEY) key; -- ref(filent) fil; -- MYH
       infix(string) nam,action; integer img_lng;
       
%      fil:=var(filnam); -- MYH
% +M    ED_STR("FIL.fOPEN: fil="); ED_OADDR(fil); ED_OUT;   
       if fil.key <> 0 then goto FEX endif;  --- file already open

       nam:=TX2STR(fil.nam); action:=TX2STR(fil.action);
       img_lng:=img.lp - img.sp;
% +M    ED_STR("FIL.fOPEN: nam="); ED_STR(nam); ED_OUT;   

       --- save references (possible GC)
       bio.opfil:=fil; bio.opimg:=img.ent;
           key:=OPFILE(nam,fil.type,action,img_lng);
       --- restore reference (maybe changed by GC)
%       var(filnam):=fil:=bio.opfil; img.ent:=bio.opimg; -- MYH
       fil:=bio.opfil; img.ent:=bio.opimg;               -- MYH
%-X    if fil=none then IERR_R("fOPEN-1") endif
       bio.smbP1:=none; bio.smbP2:=none;

       if status <> 0
       then FEX: status:=0; success:=false;
       else if bio.files <> none then  bio.files.prd:=fil  endif;
            fil.suc:=bio.files; bio.files:=fil; fil.img:=img;
            fil.key:=key; fil.loc:=1; fil.eof:= false; success:=true;
       endif;
 end;

 Visible routine BOPN;
 import ref(filent) fil; export boolean success;
 begin
% 	   success:=fOPEN(name(fil),notext); -- MYH
 	   success:=fOPEN(fil,notext);       -- MYH
 	   
%-X    if bio.trc then if success
%-X    then bio.obsEvt:=EVT_open; bio.smbP1:=fil; observ endif endif;
 end;
--- begin infix(string) nam,action;  success:=false;
---       if fil.key <> 0 then goto EX endif;
---       nam:=TX2STR(fil.nam); action:=TX2STR(fil.action);
---       fil.key:=OPFILE(nam,fil.type,action,0);
---       if status <> 0
---       then status:=0; fil.key:=0;
---       else if bio.files <> none then  bio.files.prd:=fil  endif;
---            fil.suc:=bio.files; bio.files:=fil; fil.loc:=1;
---            fil.eof:= false; success:=true; fil.bsize := 8;
---%-X            if bio.trc then bio.obsEvt:=EVT_open; bio.smbP1:=fil;
---%-X               observ endif;
---       endif;
--- EX:end;

 Visible routine OPEN; -- corrected jan 87 pje
 import ref(filent) fil; infix(txtqnt) img; export boolean success;
 begin
% +M    ED_STR("FIL.OPEN: fil="); ED_OADDR(fil); ED_OUT;   
% +M    ED_STR("FIL.OPEN: nam="); ED_TXT(fil.nam); ED_OUT;  
 
% 	   success:=fOPEN(name(fil),img); -- MYH
 	   success:=fOPEN(fil,img);       -- MYH
 	   
       if success then
       		img:=fil.img; --- may have been changed
            case 0:MAX_FIL (fil.type)
            when FIL_IN: txtAsT(img,notext); fil.img.cp:=img.lp;
            when FIL_OUT: fil.img.cp:=img.sp;
            when FIL_DIR: fil.img.cp:=img.sp; LOCATE(fil,1);
            when FIL_PRT:
                 fil.img.cp:=img.sp; fil qua ref(prtEnt).lin:=1;
                 fil qua ref(prtEnt).pag:=1;    -- pje june 89
            endcase;
%-X         if bio.trc then bio.obsEvt:=EVT_open; bio.smbP1:=fil;
%-X            observ endif;
       endif;
 end;
--- begin infix(string) nam,action; integer img_lng;
---       success:=false;
---       if fil.key <> 0 then goto EX endif;
---       nam:=TX2STR(fil.nam); action:=TX2STR(fil.action);
---       img_lng:=img.lp - img.sp;

---       fil.key:=OPFILE(nam,fil.type,action,img_lng);
---       if status <> 0 then status:=0; fil.key:=0;
---       else if bio.files <> none then  bio.files.prd:=fil  endif;
---            fil.suc:=bio.files; bio.files:=fil; fil.loc:=1;
---            case 0:MAX_FIL (fil.type)
---            when FIL_IN: txtAsT(img,notext); img.cp:=img.lp;
---            when FIL_OUT: img.cp:=img.sp;
---            when FIL_DIR: img.cp:=img.sp; LOCATE(fil,1);
---            when FIL_PRT:
---                 img.cp:=img.sp; fil qua ref(prtEnt).lin:=1;
---                 -- fil qua ref(prtEnt).pag:=0; -- pje june 89, rem.
---                 -- EJECT(fil,1);               -- rem. apr 88
---                 fil qua ref(prtEnt).pag:=1;    -- pje june 89
---            endcase;
---            fil.eof:= false;
---            fil.img:=img; success:=true;
---%-X            if bio.trc then bio.obsEvt:=EVT_open; bio.smbP1:=fil;
---%-X               observ endif;
---       endif;
--- EX:end;
%title ******   C L O S E   ******

 Visible routine CLOSE; import ref(filent) fil;
 export boolean success; --  pje --
 begin infix(string) action;
       success:=false; action:=TX2STR(fil.action);
       if fil.key <> 0
       then --  Some file classes have associated actions at close time.
%-X         if bio.trc then bio.obsEvt:=EVT_clos; bio.smbP1:=fil;
%-X            observ endif;
            case 0:MAX_FIL (fil.type)
            when FIL_OUT:
                 if fil.img.cp <> fil.img.sp then OUTIM(fil) endif;
            when FIL_PRT:
                 if fil.img.cp <> fil.img.sp then OUTIM(fil) endif;
                 fil qua ref(prtEnt).spc:=1;
  ---            EJECT(fil,fil qua ref(prtEnt).lpp);   ---  REMOVED
                 LPP(fil,0); fil qua ref(prtEnt).lin:=0;
             endcase;

             CLFILE(fil.key,action);
             if status=0
             then fil.loc:=0; fil.key:=0; fil.eof:=true; success:=true;
                  fil.img:=notext;

             ---  Remove the file entity from the list of open files.
                  if fil.suc <> none then  fil.suc.prd:=fil.prd  endif;
                  if fil.prd = none then bio.files:=fil.suc
                  else fil.prd.suc:=fil.suc endif;
                  fil.suc:=fil.prd:=none;
             else status:=0; -- file can't be closed, return false
             endif;
       endif;
 end;


 Visible routine FILNAM;
 import ref(filent) fil; export infix(txtqnt) res;
 begin res:=COPY(fil.nam) end;

 Visible routine FILSET;
 import ref(filent) fil; export infix(txtqnt) res;
 begin infix(string) to,from;
       if fil.key=0 then goto E endif;
       to:=STRBUF(0); to.nchr:=GDSNAM(fil.key,to);
       if status<>0
       then -- copy FILENAME instead
    E:      status:=0; res:=FILNAM(fil);
       else -- move result from STRBUF to generated text
            res:=BLANKS(to.nchr); C_MOVE(STRBUF(to.nchr),TX2STR(res));
       endif;
 end;
%title ***   S E T A C C E S S   ***

 define MAX_ACCM = 18; -- not including BYTESIZE and MOVE

  DEF_TXTENT(zeroent,1,%('!0!')%);

 const infix(txtqnt) zeroq = REC_TXTQNT(zeroent,1);

 const infix(string) accstring(MAX_ACCM) =
      ("SHARED",  "NOSHARED",
       "APPEND",  "NOAPPEND",
       "PURGE",   "NOPURGE",
       "CREATE",  "NOCREATE", "ANYCREATE",
       "READONLY","WRITEONLY","READWRITE",
       "REWIND",  "NOREWIND", "NEXTFILE","PREVIOUS","REPEAT","RELEASE");

 const infix(string) moveacc = "MOVE:";
 const infix(string) bsizacc = "BYTESIZE:";

 const range(0:9) accesspos(MAX_ACCM) = (
       0,0,1,1,4,4,
       2,2,2,
       3,3,3,
       5,5,5,5,5,5 );
 const range(0:5) accesscode(MAX_ACCM) = (
       0,1,0,1,0,1,
       0,1,2,
       0,1,2,
       0,1,2,3,4,5 );
%page

 Visible routine SETACC;
 import ref(filent) fil; infix(txtqnt) acc; export boolean res;
 begin infix(string) accstr,compacc,gistr;
       range(0:255) ch,pos; integer lng; integer i;
       infix(txtqnt) filact;
       res:=false; acc:=STRIP(acc);
       if acc.ent=none then goto F1 endif; -- notext AFTER strip
%      ED_STR("FIL.SETACC: fil="); ED_TXT(fil.nam); ED_STR(", acc="); ED_TXT(acc); ED_OUT;
       lng:=acc.lp-acc.sp; if lng>255 then goto F2 endif; -- too long
       filact:=fil.action;
       acc:=COPY(acc); UPTX(acc); -- TEMP until STRequal incl.
       if acc.ent.cha(acc.sp) = '%'
       then --- impl.dependent mode - set length and append to action
            lng:=lng-1; -- expanded acc:=subTmp(acc,2,lng);
            acc.cp:=acc.sp:=acc.sp+1; 
            --- change terminating zero to length of new mode
            filact.ent.cha(filact.lp-1):= lng qua character;
            fil.action:=CONCAT(3,(filact,acc,zeroq));
            res:=true;
       else --- search mode in set of predefined modes
            accstr.nchr:=acc.lp-acc.sp
            accstr.chradr:=name(acc.ent.cha(acc.sp));
            pos:=0; -- use byte as loop control
            repeat while pos < MAX_ACCM do
-------            if STRequal(accstr,accstring(pos))
---                if TXTREP(accstr,accstring(pos),2)
				   if STRequal(accstr,accstring(pos))
                   then ch:=accesscode(pos); pos:=accesspos(pos);
%						ED_STR("FIL.SETACC: fil="); ED_TXT(fil.nam); ED_STR(", accstr="); ED_STR(accstr); ED_OUT;
%						ED_STR("FIL.SETACC: pos="); ED_INT(pos); ED_STR(", ch="); ED_INT(ch); ED_OUT;
                        filact.ent.cha(pos):=ch qua character;
                        res:=true; goto T1
                   endif;
                   pos:=pos+1
            endrepeat;
            if accstr.nchr<6 then goto F3 endif; -- too short
            compacc:=accstr;
            compacc.nchr:=5; -- start is MOVE: ?
-----       if TXTREP(compacc,moveacc,2)
            if STRequal(compacc,moveacc)
            then gistr.chradr:=name(var(accstr.chradr)(5));
                 gistr.nchr:=accstr.nchr-5; i:=GETINT(gistr);
                 if (status<>0) or (i<-32767) or (i>32767)
                 then status:=0; goto F4 endif;
                 i:=i + 32768; -- bias
                 filact.ent.cha(7):=(i/256) qua character;
                 filact.ent.cha(8):=(i rem 256) qua character;
                 res:=true; goto T2
            endif;
            if accstr.nchr<10 then goto F5 endif; -- too short
            compacc.nchr:=9; -- start is BYTESIZE: ?
-----       if TXTREP(compacc,bsizacc,2)
            if STRequal(compacc,bsizacc)
            then gistr.chradr:=name(var(accstr.chradr)(9));
                 gistr.nchr:=accstr.nchr-9; i:=GETINT(gistr);
                 if (status<>0) or (i<0) or (i>255)
                 then status:=0; goto F6 endif;
                 filact.ent.cha(6):=i qua character;
                 res:=true
            endif;
       endif;
F1:F2:F3:F4:F5:F6:
T1:T2:
 end;
%title  ******   B Y T E F I L E S   ******

 Routine ERR_INB; --- corrected pje jan 87
 import ref(filent) fil;
 begin if (status<>13) or fil.eof
       then FILerr(fil,true,true) endif;
       ---  else: End-of-file was encountered for the first time
       fil.eof:=true; status:=0;
%-X    if bio.trc then bio.obsEvt:=EVT_endf; bio.smbP1:=fil;
%-X       observ endif;
 end;


 Visible routine INBYTE;
 import ref(filent) fil; export range(0:MAX_BYT) val;
 begin val:=fINBYT(fil.key);
       if status <> 0 then ERR_INB(fil); val:=0 endif;
 end;

 Visible routine in2byt;
 import ref(filent) fil; export range(0:MAX_2BT) val;
 begin val:=fIN2BY(fil.key);
       if status <> 0 then ERR_INB(fil); val:=0 endif;
 end;

 Visible routine utbyte ;
 import ref(filent) fil; range(0:MAX_BYT) val;
 begin fUTBYT(fil.key,val);
       if status <> 0 then  FILerr(fil,true,true)  endif;
 end;

 Visible routine ut2byt;
 import ref(filent) fil; range(0:MAX_2BT) val;
 begin fUT2BY(fil.key,val);
       if status <> 0 then  FILerr(fil,true,true)  endif;
 end;

 Routine ERR_DIRB;
 import ref(filent) fil;
 begin range(0:MAX_BYT) val;
       val:=status; status:=0;
       if DBENDF(fil)
       then
%-X         if bio.trc then bio.obsEvt:=EVT_endf; bio.smbP1:=fil;
%-X            observ endif;
       else status:=val; FILerr(fil,true,true) endif;
 end;

 Visible routine dinbyt ;
 import ref(filent) fil; export range(0:MAX_BYT) val;
 begin val:=fINBYT(fil.key);
       if status <> 0
       then if fil.loc > LASTLC(fil)
            then status:=0; fLOCAT(fil.key,fil.loc); status:=0; val:=0;
            else FILerr(fil,true,true) endif;
       else fil.loc:=fil.loc+1 endif;
 end;

 Visible routine din2by;
 import ref(filent) fil; export range(0:MAX_2BT) val;
 begin val:=fIN2BY(fil.key);
       if status <> 0
       then val:=status; status:=0; fLOCAT(fil.key,fil.loc); status:=0;
            if    fil.loc > LASTLC(fil) then val:=0
            elsif fil.loc = LASTLC(fil) then val:=dinbyt(fil)*256
            else status:=val; FILerr(fil,true,true) endif;
       else fil.loc:=fil.loc+2 endif;
 end;

 Visible routine dutbyt;
 import ref(filent) fil; range(0:MAX_BYT) val;
 begin fUTBYT(fil.key,val);
       if status <> 0 then  FILerr(fil,true,true)  endif;
       fil.loc:=fil.loc+1;
 end;

 Visible routine dut2by;
 import ref(filent) fil; range(0:MAX_2BT) val;
 begin fUT2BY(fil.key,val);
       if status <> 0 then  FILerr(fil,true,true)  endif;
       fil.loc:=fil.loc+2;
 end;
%title ***   Inimage - inrecord   ***

 Visible routine INIMAG;
 import ref(filent)   fil;
 begin range(0:MAX_TXT) filled; infix(txtqnt) img; infix(string) str;
       img:=fil.img;   -- now test image<>notext and not constant:
       assert img.ent <> none skip  ERROR1(ENO_FIL_5,fil)  endskip;
       assert img.ent.misc=0  skip  ERROR1(ENO_FIL_6,fil)  endskip;
       str.nchr:=img.lp - img.sp;                 -- Equivalent to call
       str.chradr:=name(img.ent.cha(img.sp));     -- on TXT_TO_STR.
%       ED_STR("FIL.INIMAG: filled="); ED_INT(filled); ED_STR(", status="); ED_INT(status); ED_OUT;
       filled:=fINIMA(fil.key,str);
%       ED_STR("FIL.INIMAG: filled="); ED_INT(filled); ED_STR(", status="); ED_INT(status); ED_OUT;
       if status <> 0
       then if (status = 13) and (not fil.eof)    --- end-of-file
            then var(str.chradr):='!25!';         --- ISO EM-character
                 filled:=1; fil.eof:=true; status:=0;
%-X              if bio.trc then bio.obsEvt:=EVT_endf; bio.smbP1:=fil;
%-X                 observ endif;
            elsif (status=35) and (fil.type=FIL_DIR) -- never-written im
            then fil.img.cp:=img.lp; status:=0;
                 repeat while str.nchr <> 0 -- zero fill image
                 do str.nchr:=str.nchr-1;
                    var(str.chradr)(str.nchr):='!0!';
                 endrepeat;
                 goto EX;
            else FILerr(fil,true,true) endif;
       endif;

       --- blank fill rest of image if necessary ---
       if filled < str.nchr
       then str.nchr:=str.nchr - filled;
            str.chradr:=name(img.ent.cha(img.sp+filled));
            C_BLNK(str);
       endif;
       fil.img.cp:=img.sp;
    EX:fil.loc:=fil.loc + 1; --  Non-standard feature.
%-X    if bio.trc then bio.obsEvt:=EVT_inim; bio.smbP1:=fil;
%-X       observ endif;
 end;


 Visible routine INREC;   -- corr. feb 87 pje: result inverted
 import ref(filent) fil; export boolean res;
 begin range(0:MAX_TXT) filled; infix(txtqnt) img; infix(string) str;
       img:=fil.img;
       assert img.ent <> none skip  ERROR1(ENO_FIL_5,fil)  endskip;
       assert img.ent.misc=0  skip  ERROR1(ENO_FIL_6,fil)  endskip;
       res:=false;
       str.nchr:=img.lp - img.sp;                 -- Equivalent to call
       str.chradr:=name(img.ent.cha(img.sp));     -- on TXT_TO_STR.
       filled:=fINIMA(fil.key,str);
       if status <> 0 then
       		if (status = 13) and (not fil.eof)    --- end-of-file
            then var(str.chradr):='!25!';         --- ISO EM-character
                 filled:=1; fil.eof:=true; status:=0;
%-X              if bio.trc then bio.obsEvt:=EVT_endf; bio.smbP1:=fil;
%-X                 observ endif;
            elsif status = 34 then res:=true; status:=0;
            else FILerr(fil,true,true) endif;
       endif;
       fil.img.cp:=img.sp+filled;
%-X    if bio.trc then bio.obsEvt:=EVT_inim; bio.smbP1:=fil;
%-X       observ endif;
 end;
%title ***   Inchar - BIntext   ***

 Visible routine INCHAR;
 import ref(filent) fil; export character res;
 begin infix(txtqnt) img; img:=fil.img;
       if img.cp >= img.lp then INIMAG(fil); img:=fil.img endif;
       res:=img.ent.cha(img.cp); fil.img.cp:=img.cp + 1;
 end;


 Visible routine BINTEX; -- corrected pje jan. 87, june 88
 import ref(filent) fil; infix(txtqnt) txt; export infix(txtqnt) res;
 begin infix(string) str;   --  TXT_TO_STR(txt).
       range(0:MAX_TXT) filled,pos;
       filled:=0;
       if txt.ent <> none -- notext parameter?
       then str.nchr:=txt.lp-txt.sp;
            str.chradr:=name(txt.ent.cha(txt.sp));
            filled:=BINTXT(fil.key,str);
            if fil.type=FIL_DIRBYTE
            then fil.loc:=fil.loc+filled;
                 if status <> 0
                 then ERR_DIRB(fil); -- error terminate if not endfile
                 endif;
            elsif status <> 0
            then ERR_INB(fil) endif  -- INBYTE: set fil.eof or terminate
            if filled=0 then goto NOTX endif;
            res:=txt; res.cp:=res.sp; res.lp:=res.sp+filled;
       else NOTX: res:=notext endif;
 end;
%title ***   Outimage   ***

 Visible routine OUTIM; import ref(filent) fil;
 begin infix(txtqnt) img;   --  Local copy here for efficiency.
       infix(string) str;   --  TXT_TO_STR(img).
       ref(prtEnt) pfil;

%-X    if bio.trc then bio.obsEvt:=EVT_ouim; bio.smbP1:=fil;
%-X       observ endif;
       img:=fil.img;
       if img.ent = none then ERROR1(ENO_FIL_13,fil) endif;
       str.nchr:=img.lp-img.sp; str.chradr:=name(img.ent.cha(img.sp));

       if fil.type = FIL_PRT
       then pfil:=fil qua ref(prtEnt);
            if pfil.lin > pfil.lpp then EJECT(pfil,1) endif;
            PRINTO(pfil.key,str,pfil.spc);
            if status <> 0
            then if (status<>19) or (pfil.spc>0)
                 then FILerr(pfil,true,true) endif;
                 status:=0;
                 if pfil.spc=0 then pfil.lin:=pfil.lin+1 endif;
            else pfil.lin:=pfil.lin + pfil.spc endif;
       else fUTIMA(fil.key,str);
            if status <> 0 then FILerr(fil,true,true) endif;
            fil.loc:=fil.loc + 1;  --  Non-standard feature.
       endif;

       C_BLNK(str); fil.img.cp:=img.sp;
 end;

%title ***   outchar - outint - outtext   ***

 Visible routine OUTCHA;
 import ref(filent) fil; character val;
 begin infix(txtqnt) img;
       img:=fil.img;
       if img.cp >= img.lp then OUTIM(fil); img:=fil.img endif;
       ---  At this point we know that 'file.img <> notext'.
       if img.ent.misc <> 0
       then if actLvl <> ACT_GC then ERROR1(ENO_FIL_16,fil) endif endif;
       img.ent.cha(img.cp):=val; fil.img.cp:=img.cp + 1;
 end;

 Visible Macro  OUT_ITEM(2);
 --  First parameter is the name of the function (e.g. PUTINT).
 --  Second parameter contains the extra size arguments,if any.
 begin infix(string) item; infix(txtqnt) img;
       integer ww,p; -- infix(string) dest;
       ww:=w;
       if    w < 0 then w:=-w  -- edit as normal, move afterwards
       elsif w = 0             -- edit into ebuf, move afterwards
       then  item.nchr:=400; item.chradr:=name(bio.ebuf);
             --- blank-filling done by envir ---
             %1 (item,val %2); item:=EdCheck(fil,item); w:=item.nchr;
       endif;
       img:=MakeRoom(fil,w);
       if ww <> 0
       then item.nchr:=w; item.chradr:=name(img.ent.cha(img.cp));
            %1 (item,val %2); if status<>0 then ERR_OUT(fil,item) endif;
       endif;
       fil.img.cp:=img.cp
                   + (if ww>0 then w else LeftAdj(img,item,w, ww < 0 ));
 endmacro;

 Visible Routine EdCheck;
 import ref(filent) fil; infix(string) item; export infix(string) res;
 begin integer p;
       if status <> 0
       then ERR_OUT(fil,item); p:=395 --- edit 5 stars if overflow
       else p:=0; repeat while bio.ebuf(p)=' ' do p:=p+1 endrepeat
       endif;
       res.nchr:=400-p; res.chradr:=name(bio.ebuf(p));
 end;

 Visible Routine MakeRoom;
 import ref(filent) fil; integer w; export infix(txtqnt) img;
 begin img:=fil.img;
       if img.cp + w > img.lp
       then OUTIM(fil); img:=fil.img;
            if img.cp + w > img.lp then ERROR1(ENO_FIL_15,fil) endif;
       endif;
       ---  At this point we know that 'file.img <> notext'.
       assert img.ent.misc=0 skip ERROR1(ENO_FIL_16,fil) endskip;
 end;

 Visible Routine LeftAdj;
 import infix(txtqnt) img; infix(string) item;
        integer w; boolean spfill;
 export integer res;
 begin integer p; infix(string) dest;
       if spfill
       then p:=0;
            repeat while var(item.chradr)(p)=' ' do p:=p+1 endrepeat
            item.nchr:=w-p; item.chradr:=name(var(item.chradr)(p));
       endif;
       res:=dest.nchr:=w; dest.chradr:=name(img.ent.cha(img.cp));
       C_MOVE(item,dest);
 end;


 Visible Routine ERR_OUT;
 import ref(filent) fil; infix(string) item;
 begin integer n;
       if status <> 24 then FILerr(fil,true,true) endif;
       ---  Overflow,not enough space for all the digits. Starfill.
       status:=0; n:=item.nchr;
       repeat n:=n-1 while n>=0 do var(item.chradr)(n) :='*' endrepeat;
       bio.edOflo:=bio.edOflo + 1;
 end;

 Visible routine OUTINT;
 import ref(filent) fil; integer val,w;
 begin OUT_ITEM(%PUTINT%,%%) end;

--  Visible routine OUTTXC; -- outtext( text conc )
--  import ref(filent) fil; range(0:MAX_DIM) cnt;
--         infix(txtqnt) txt(MAX_DIM);
--  begin range(0:MAX_DIM) i,nch;
--        i:=0;
--        case 0:FIL_MAX (fil.type)
--        when FIL_OUTBYTE,FIL_DIRBYTE:
--             repeat while i<=cnt
--             do BOUTXT(fil,txt(i)); i:=i+1 endrepeat;
--        when FIL_OUT,FIL_PRT,FIL_DIR:
--             nch:=0; repeat while i<=cnt
--             do nch:=nch+txt(i).lp-txt(i).sp; i:=i+1 endrepeat;
--             if fil.img.cp + nch > fil.img.lp then OUTIM(fil) endif;
--             i:=0; repeat while i<=cnt
--             do OUTTXT(fil,txt(i)); i:=i+1 endrepeat;
--        endcase;
--  end;

 Visible routine OUTTXT;
 import ref(filent) fil; infix(txtqnt) txt;
 begin infix(txtqnt) img;           --  Local copy here for efficiency.
       infix(string) src;           --  Copy from this string.
       infix(string) dst;           --  Copy to this string.
       integer imlength,tpos,tlen;  --  Used for long strings
       
%	   ed_str("*** OUTTXT: "); ed_oaddr(txt.ent);
%	   ed_str(", CP: "); ed_int(txt.cp);
%	   ed_str(", SP: "); ed_int(txt.sp);
%	   ed_str(", LP: "); ed_int(txt.lp); ed_out;
       
       img:=fil.img; tpos:=txt.sp; tlen:=txt.lp-tpos;
       if img.cp + tlen > img.lp then OUTIM(fil); img:=fil.img endif;
       ---  At this point we know that 'file.img <> notext'.
       imlength:=img.lp-img.sp;
       ---  Make sure that we also have 'txt' <> notext'.
       if tlen > 0
       then assert img.ent.misc=0 skip ERROR1(ENO_FIL_16,fil) endskip;
            ---  Transfer all the characters in the source.
            dst.chradr:=name(img.ent.cha(img.cp));
    LOOP: 
    		src.chradr:=name(txt.ent.cha(tpos));
            if tlen > imlength -- in this case, img.cp=img.sp
            then src.nchr:=dst.nchr:=imlength;  C_MOVE(src,dst);
                 OUTIM(fil); img:=fil.img;
                 tpos:=tpos+imlength; tlen:=tlen-imlength; goto LOOP;
            else src.nchr:=dst.nchr:=tlen; C_MOVE(src,dst) endif;
            fil.img.cp:=img.cp+tlen;
       endif;
 end;

 Visible routine BOUTEX;
 import ref(filent) fil; infix(txtqnt) txt;
 begin infix(string) str;   --  TXT_TO_STR(txt).
--  ed_str("*** ENT:"); PTOADR(WFIELD(12),txt.ent);
--  ed_str(", SP: "); ed_int(txt.sp);
--  ed_str(", LP: "); ed_int(txt.lp); ed_out;
       if txt.ent <> none  --- notext parameter?
       then str.nchr:=txt.lp-txt.sp;
            str.chradr:=name(txt.ent.cha(txt.sp));
            BOUTXT(fil.key,str);
            if status <> 0 then  FILerr(fil,true,true)  endif;
            if fil.type=FIL_DIRBYTE then fil.loc:=fil.loc+str.nchr endif
       endif;
 end;

 Visible routine BOUTXC; -- outtext( text conc )
 import ref(filent) fil; range(0:MAX_DIM) cnt;
        infix(txtqnt) txt(MAX_DIM);
 begin range(0:MAX_DIM) i;
       i:=0; repeat while cnt<>i
       do BOUTEX(fil,txt(i)); i:=i+1 endrepeat;
 end;

 Visible routine LOCATE;
 import ref(filent) fil; integer loc;
 begin fLOCAT(fil.key,loc);
       if status <> 0 then FILerr(fil,true,true) endif;
       fil.loc:=loc; fil.eof:=false;
%-X    if bio.trc then bio.obsEvt:=EVT_loca; bio.smbP1:=fil;
%-X       observ endif;
 end;

 Visible routine LASTLC; -- pje jan 87
 import ref(filent) fil; export integer lloc;
 begin lloc:=LSTLOC(fil.key);
       if status <> 0 then FILerr(fil,true,true) endif;
 end;


 Visible routine DBENDF; -- pje apr 88
 import ref(filent) fil; export boolean endfil;
 begin endfil:=true;
       if fil.key<>0 then endfil:=fil.loc>LASTLC(fil) endif;
 end;


 Visible routine EJECT;
 import ref(prtEnt) pfil; integer lin;
 begin if lin < 1 then ERROR1(ENO_FIL_20,pfil) endif;
       if lin > pfil.lpp then lin:=1 endif;
       if lin > pfil.lin
       then PRINTO(pfil.key,nostring,lin - pfil.lin);
            if status <> 0 then FILerr(pfil,true,true) endif;
  --- pje   pfil.loc:=pfil.loc + (lin-pfil.lin);       --  Non-standard.
       else NEWPAG(pfil.key);
            if status <> 0 then FILerr(pfil,true,true) endif;
  --- pje   pfil.loc:=pfil.loc + (pfil.lpp-pfil.lin+1); --  Non-standard
            if lin > 1
            then PRINTO(pfil.key,nostring,lin-1);
                 if status <> 0 then FILerr(pfil,true,true) endif;
  --- pje        pfil.loc:=pfil.loc + (lin-1);          --  Non-standard
            endif;
            pfil.pag:=pfil.pag+1;
       endif;
       pfil.lin:=lin;
 end;

 Visible routine  LPP;
 import ref(prtEnt) pfil; integer lpp;
 export integer result;
 begin result:=pfil.lpp;
       if lpp > 0 then pfil.lpp:=lpp;
       elsif lpp = 0
       then pfil.lpp:=GETLPP(pfil.key);
            if status <> 0
        --  then if (pfil.key = 0) and (status = 1)
        --       then status:=0; pfil.lpp:=42;          ---  TEMPORARY
        --       else FILerr(pfil,false,false) endif;
            then status:=0; pfil.lpp:=42;          ---  pje june 89
            endif;
       else pfil.lpp:=maxint-1;  -- lpp<0: "infinite" value
       endif;
 end;

 Visible routine sysNam;
 import range(0:MAX_KEY) key; export infix(txtqnt) res;
 begin infix(string) from,to;
       to:=STRBUF(0); to.nchr:=GDSNAM(key,to);
       if status<>0
       then -- if key <= 3 then ERROR0;
            if key <= 3 then from:="SYSIN/SYSOUT/SYSTRC"
            elsif (status = 2) or (status = 3)
            then from:= "Unknown file";
            else from:="Cannot get full name";
            endif;
            status:=0; to.nchr:=from.nchr; C_MOVE(from,to);
       endif;
       --- We shall return a text quantity referring
       --- the first characters of the editing buffer.
       res:=BLANKS(to.nchr); C_MOVE(STRBUF(to.nchr),TX2STR(res));
 end;

%page
--   %Visible
FILDCL:   ---   Declaration code, all file classes (pref.level 0)
          curins qua ref(filent).type :=FIL_FILE;
          if actLvl = ACT_USR
          then if curins qua ref(filent).nam = notext
               then ERROR(ENO_FIL_2) endif;
          endif;
          curins qua ref(filent).eof:=true;
          nxtDcl(1);


IBFILDCL: curins qua ref(filent).type:=FIL_INBYTE;     goto INACT;
IFILDCL:  curins qua ref(filent).type:=FIL_IN;
--INACT:  curins qua ref(filent).action:=COPY(acmin);  goto FILDCL3;
  INACT:  txttmp:=acmin;   goto FILDCL3;

OBFILDCL: curins qua ref(filent).type:=FIL_OUTBYTE;    goto OUTACT;
OFILDCL:  curins qua ref(filent).type:=FIL_OUT;
--UTACT:  curins qua ref(filent).action:=COPY(acmout); goto FILDCL3;
 OUTACT:  txttmp:=acmout;  goto FILDCL3;

DBFILDCL: curins qua ref(filent).type:=FIL_DIRBYTE;    goto DIRACT;
DFILDCL:  curins qua ref(filent).type:=FIL_DIR;
--IRACT:  curins qua ref(filent).action:=COPY(acmdir); -- goto FILDCL3;
 DIRACT:  txttmp:=acmdir;  -- goto FILDCL3;
FILDCL3:  curins qua ref(filent).action:=COPY(txttmp);
          nxtDcl(3);

          ---   The class PRINTFILE on prefix level three.
PFILDCL:  curins qua ref(filent).type :=FIL_PRT;
          curins qua ref(prtEnt).spc:=1;
          LPP(curins qua ref(prtEnt),0);
          nxtDcl(4);

%hidden
end;
