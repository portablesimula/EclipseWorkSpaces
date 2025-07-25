 Module form("RTS");
 begin sysinsert rt,sysr,knwn,util,strg,cent,arr;

    -----------------------------------------------------------------
    ---  COPYRIGHT 1989 by                                        ---
    ---  Simula a.s.                                              ---
    ---  Oslo, Norway                                             ---
    ---                                                           ---
    ---              P O R T A B L E     S I M U L A              ---
    ---               R U N T I M E     S Y S T E M               ---
    ---                                                           ---
    ---  F o r m a l    N a m e    P a r a m e t e r s    a n d   ---
    ---     P a r a m e t e r    T r a n s m i s s i o n   to     ---
    ---  F o r m a l    or    V i r t u a l    P r o c e d u r e  ---
    ---                                                           ---
    -----------------------------------------------------------------


 const infix(ptp) VAL_THK_PTP = record:ptp
%-X    (refVec=none,xpp=ref(THK_XPP),lng=size(thunk));
%+X    (refVec=none,xpp=none        ,lng=size(thunk));

 const infix(ptp) ADR_THK_PTP = record:ptp
%-X    (refVec=ref(ADR_THK_VEC),xpp=ref(THK_XPP),lng=size(thunk));
%+X    (refVec=ref(ADR_THK_VEC),xpp=none        ,lng=size(thunk));

 const infix(pntvec:1) ADR_THK_VEC =
 record:pntvec(npnt=1,pnt=(field(thunk.val.ins)));

 const infix(ptp) ADR_TXT_PTP = record:ptp
%-X    (refVec=ref(ADR_TXT_VEC),xpp=ref(THK_XPP),lng=size(thunk));
%+X    (refVec=ref(ADR_TXT_VEC),xpp=none        ,lng=size(thunk));

 const infix(pntvec:1) ADR_TXT_VEC =
 record:pntvec(npnt=1,pnt=(field(thunk.val.txt.ent)));

%-X const infix(ptpExt) THK_XPP = record:ptpExt
---    (idt=ref(THK_IDT),attrV=none,blkTyp=BLK_THK);
%-X    (idt=none        ,attrV=none,blkTyp=BLK_THK);

--- const infix(idfier:5) THK_IDT = record:idfier
---    (ncha=5,cha=('T','H','U','N','K'));

%page     
 Routine AB_THK;
 import ref(inst)  sl;   --  static link
        ref(ptp) pp;         --  the thunk prototype
        label pad;           --  start of thunk code
        boolean simple;   --  is formal of simple kind?
 export label psc;           --  start thunk execution here
 begin ref(thunk) thk;       --  to storage area being allocated.
       ---  Check that save entity allocation invariant still holds!!
       if bio.nxtAdr > bio.lstAdr
       then GARB(bio.nxtAdr,nosize,name(sl)) endif;
       if bio.thunks <> none
       then --  Remove the first one from the free list.
            thk:=bio.thunks; bio.thunks:=bio.thunks.dl;
            --  If it is a thunk for a left hand side, or a thunk
            --  for a text with attribute access, make sure that GC
            --  won't follow any old and invalid reference values.
            if pp=ref(ADR_THK_PTP) then thk.val.ins:=none;
            elsif pp=ref(ADR_TXT_PTP) then thk.val.txt.ent:=none endif;
       else ALLOC(S_THK,thk,%size(thunk)%,sl); endif;
       repeat while sl.sort=S_THK do sl:=sl.sl endrepeat;
       thk.sl:=sl; thk.pp:=pp; thk.dl:=curins;
       thk.simple:=simple; curins:=thk; psc:=pad;
----   %-X    if bio.trc then curins.lsc:=pad; bio.obsEvt:=EVT_BEG;
----   %-X       call PSIMOB(smb) endif;
 end;


 Visible routine E_THK; exit label psc;
 begin ref(thunk) thk;
----   %-X    if bio.trc
----   %-X    then curins.lsc:=psc; bio.GCval:=curins.sort;
----   %-X         trEND(curins) endif
       thk:=curins; curins:=curins.dl;
       if thk.pp = ref(ADR_THK_PTP)
       then -- The thunk inserted in the dynamic chain of its caller.
            -- There may be a save entity allocated for the call on
            -- this thunk. That save entity is found immediately below
            -- the caller. No other save entity can occur just below the
            -- caller. Since the save entity will be used in a restore
            -- immediately after this thunk finishes, the thunk must be
            -- inserted below it.
            if curins.dl.sort = S_SAV
            then thk.dl:=curins.dl.dl; curins.dl.dl:=thk;
            else thk.dl:=curins.dl; curins.dl:=thk endif;
            thk.lsc:=nowhere;     --  Because may enter operating chain.
            thk.sl:=none;         --  Because garbage collection may occur.
       else -- The thunk value is accessed immediately after the thunk
            -- finishes its execution. Thus the thunk can be inserted in
            -- the free list. Note that even if it is inserted in
            -- the free list, it is not free to be used by others until
            -- its result has been accessed, but we know that the
            -- "next operation" will be to access its result (and
            -- then the order of the operations does not matter).
            thk.dl:=bio.thunks; bio.thunks:=thk;
       endif;
       psc:=curins.lsc;
 end;


 Visible routine mkVlab;
 import ref(inst) sl; range(0:MAX_VIR) vir_ind;
 export infix(labqnt) lab;
 begin lab.pad:=sl.pp qua ref(claptp).virts.vir(vir_ind).pad;
       if lab.pad = nowhere then ERROR(ENO_VIR_1) endif;   --  No match?
       lab.sl:=sl;       --  The label must be local to the class.
       lab.clv:=0;       --  The label must be on connection level zero.
 end;

 Visible routine mkVpro;
 import ref(inst) sl; range(0:MAX_VIR) vir_ind;
 export infix(proqnt) pro;
 begin infix(virdes) vir;   --  Local copy here for efficiency reasons.
       vir:=sl.pp qua ref(claptp).virts.vir(vir_ind);
       if vir.ppp = none then ERROR(ENO_VIR_1) endif;      --  No match?
       pro.ppp:=vir.ppp; pro.qal:=vir.qal; pro.sl:=sl;
 end;

 Visible routine mkVswt;
 import ref(inst) sl; range(0:MAX_VIR) vir_ind;
 export infix(swtqnt) swt;
 begin swt.des:=sl.pp qua ref(claptp).virts.vir(vir_ind).des;
       if swt.des = none then ERROR(ENO_VIR_1) endif;      --  No match?
       swt.sl:=sl;         --  The switch must be local to the class.
 end;

 Visible routine B_s2la;
 import integer ind;               --  The index;
        infix(swtqnt) swt;         --  The switch.
 exit label psc;
 begin infix(swtelt) elt;
       if (ind<=0)  or (ind>swt.des.nelt) then ERROR(ENO_SWT_1) endif;
       elt:=swt.des.elt(ind);
       if elt.thk
       then curins.lsc:=psc;
            ---  Allocate a thunk instance and prepare its initiation.
            psc:=AB_THK(swt.sl,ref(VAL_THK_PTP),elt.pad,false);
       else ---  Check that the save invariant still holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       endif;
 end;


 Visible routine E_s2la;
 import integer ind;               --  The index;
        infix(swtqnt) swt;         --  The switch.
 export infix(labqnt) lab;
 begin infix(swtelt) elt; range(0:MAX_RBL) rbl; ref(inst) sl;
       elt:=swt.des.elt(ind);
       if elt.thk
       then ---  Pick up the label evaluated by the thunk instance.
            lab:=tmp.pnt qua ref(thunk).val.lab;
       elsif elt.pad <> nowhere
       then ---  It is a directly visible local or global label.
            sl :=swt.sl; rbl:=elt.rbl;
            repeat while rbl > 0 do sl:=sl.sl; rbl:=rbl-1 endrepeat;
            lab.sl:=sl; lab.pad:=elt.pad; lab.clv:=elt.clv;
       else ---  It is a directly visible parameter called by reference.
            sl :=swt.sl; rbl:=elt.rbl;
            repeat while rbl > 0 do sl:=sl.sl; rbl:=rbl-1 endrepeat;
            lab :=var((sl + elt.fld) qua name(infix(labqnt)));
       endif;
 end;
%title ***  Evaluate name parameter --> LHS destination  ***

 routine FNP_CHK_QAL;
 import ref (claptp) super;  -- qualification of left hand side.
        ref (claptp) sub;    -- qualification of right hand side.
        range (0:MAX_ENO) eno;    -- error number
 begin if super.plv > sub.plv then  ERROR(eno)  endif;
       if super <> sub.prefix(super.plv) then  ERROR(eno)  endif;
 end;

 Visible routine BnpAss;
 import name(infix(parqnt)) fnp;         --  Parameter's address
 exit label psc;
 begin curins.lsc:= psc;
       psc:= AB_THK(var(fnp).ent qua ref(inst),ref(ADR_THK_PTP),
                     if var(fnp).ap.type = T_REF
                     then var(fnp).ap qua ref(refThk).pad
                     else var(fnp).ap qua ref(thkPar).pad,true);
 end;


 Routine npAssA;
 import name(infix(parqnt)) fnp;  --  parameter quantity address
        range(0:MAX_ENO) eno;     --  error number
 export name() adr;               --  actual parameter's address
 begin ref(thunk) thk;            --  To the executed thunk instance.
       ---  Check that the actual parameter denotes a variable.
       case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_ASS: adr:= var(fnp).ent + var(fnp).fld;
       when PAR_THK_ASS:
            ---  Unchain thunk instance and insert it in the free list.
            thk:= curins.dl; curins.dl:= thk.dl;
            thk.dl:= bio.thunks; bio.thunks:= thk;
            adr:= thk.val.ins + thk.val.fld;
       otherwise ERROR(eno) endcase;
 end;


 Visible routine EnpBoo;
 import boolean f_rhs;               --  right hand side value
        name(infix(parqnt)) fnp;     --  parameter quantity address
 begin var(npAssA(fnp,ENO_FNP_1) qua name(boolean)):= f_rhs; end;

 Visible routine EnpCha;
 import character f_rhs;             --  right hand side value
        name(infix(parqnt)) fnp;     --  parameter quantity address
 begin var(npAssA(fnp,ENO_FNP_1) qua name(character)):= f_rhs; end;

 Visible routine EnpPtr;
 import ref()     f_rhs;             --  right hand side value
        name(infix(parqnt)) fnp;     --  parameter quantity address
 begin var(npAssA(fnp,ENO_FNP_1) qua name(ref()    )):= f_rhs; end;


 Macro E_FNP_ASS_CNV(1);    --  name of routine for IERR
 begin name() adr;          --  actual parameter's address
       adr:= npAssA(fnp,ENO_FNP_1);
       ---  We know that the actual parameter is a simple variable.
       ---  Convert from formal type to actual type during assignment.
       case T_SIN:T_LRL (var(fnp).ap.type)
       when T_SIN: var(adr qua name(infix(quant))).sin:=
                                                f_rhs qua short integer;
       when T_INT: var(adr qua name(integer))    := f_rhs qua integer;
       when T_REA: var(adr qua name(real))       := f_rhs qua real;
       when T_LRL: var(adr qua name(long real))  := f_rhs qua long real;
       otherwise IERR_R(%1) endcase;
 endmacro;


 Visible routine EnpSin;
 import short integer f_rhs; -- right hand side, convert to formal type
        name(infix(parqnt)) fnp; --  parameter quantity address
 begin E_FNP_ASS_CNV(%"EnpSin"%); end;


 Visible routine EnpInt;
 import integer f_rhs;    --  right hand side , converted to formal type
        name(infix(parqnt)) fnp; --  parameter quantity address
 begin E_FNP_ASS_CNV(%"EnpInt"%); end;


 Visible routine EnpRea;
 import real f_rhs;    --  right hand side , converted to formal type
        name(infix(parqnt)) fnp; --  parameter quantity address
 begin E_FNP_ASS_CNV(%"EnpRea"%); end;


 Visible routine EnpLrl;
 import long real f_rhs;    --  right hand side, convert to formal type
        name(infix(parqnt)) fnp; --  parameter quantity address
 begin E_FNP_ASS_CNV(%"EnpLrl"%); end;


 Visible routine EnpRef;
 import ref(inst) f_rhs; --right hand side, qualified as formal type
        name(infix(parqnt)) fnp; --  parameter quantity address
 begin ---  Check that the right hand side object is subordinate to
       ---  the actual parameter, which denotes a ref-variable.
       if f_rhs <> none
       then FNP_CHK_QAL(var(fnp).ap qua ref(refPar).qal,
                       f_rhs.pp qua ref(claptp),ENO_FNP_5);
       endif;
       var(npAssA(fnp,ENO_FNP_1) qua name(ref(inst))):= f_rhs;
 end;


 Visible routine EnpTxt;
 import infix(txtqnt) f_rhs;       -- right hand side value
        name(infix(parqnt)) fnp;   --  parameter quantity address
 begin
% 	var(npAssA(fnp,ENO_FNP_1) qua name(infix(txtqnt))):= f_rhs;
 	
 	name() adr;
 	name(infix(txtqnt)) adr2;
 	adr:=npAssA(fnp,ENO_FNP_1);
 	adr2:=adr qua name(infix(txtqnt));
 	var(adr2):=f_rhs;
 end;

%title ***  Evaluate name parameter --> value  ***
 
 Visible routine E_FNP_ADR; --- DENNE MANGLET I 'RTSINT1.def'B_FNP_ASS
 import name(infix(parqnt)) fnp;      --  Parameter's address
 begin
 	TERMIN(3,"ERROR: E_FNP_ADR --- DENNE MANGLET I 'RTSINT1.def' ");
 end;
 
 Visible routine BnpAcc;
 import name(infix(parqnt)) fnp;      --  Parameter's address
 exit label psc;
 begin ref(pardes) ap;                --  Actual parameter's descriptor
       label pad;                     --  start of thunk code
       boolean simple;                --  is formal of simple kind
       ap:= var(fnp).ap;              --  For fast execution.
       if ((ap.code = PAR_THK_NAS) or (ap.code = PAR_THK_ASS))
       then curins.lsc:= psc;
            if ap.type = T_REF then pad:=ap qua ref(refThk).pad
            else pad:=ap qua ref(thkPar).pad endif;
            if var(fnp).fp = none then simple:=true    ---   T E M P
            else                                          ---   T E M P
                 simple:=var(fnp).fp.kind = K_SMP;
            endif;                                        ---   T E M P

            psc:= AB_THK(var(fnp).ent,ref(VAL_THK_PTP),pad,simple);
       else ---  Check that the save invariant still holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       endif;
 end;


 Visible routine npAccA;
 import name(infix(parqnt)) fnp;  --  parameter quantity address
 export name() adr;               --  actual parameter's address
 begin ref(pardes)   ap;          --  Local copy here for efficiency.
 
% +M    ed_str("FORM.npAccA: fnp="); ed_GADDR(fnp); ed_out;
       ap:= var(fnp).ap;
       case 0:MAX_PAR (ap.code)
       when PAR_QNT_LIT:
            case 0:MAX_TYPE (ap.type)
            when T_BOO:  adr:= name(ap qua ref(litPar).l_boo);
            when T_CHA:  adr:= name(ap qua ref(litPar).l_cha);
            when T_SIN:  adr:= name(ap qua ref(litPar).l_sin);
            when T_INT:  adr:= name(ap qua ref(litPar).l_int);
            when T_REA:  adr:= name(ap qua ref(litPar).l_rea);
            when T_LRL:  adr:= name(ap qua ref(litPar).l_lrl);
            when T_REF:  adr:= name(ap qua ref(litPar).l_ref);
            when T_PTR:  adr:= name(ap qua ref(litPar).l_ptr);
            when T_TXT:  adr:=  ap qua ref(litPar).l_txt + nofield
                                   qua name(infix(txtqnt));
            otherwise IERR_R("npAccA-1") endcase;
       when PAR_QNT_NAS:  IERR_R("npAccA-2");
       when PAR_QNT_ASS: adr:= var(fnp).ent + var(fnp).fld;
       when PAR_THK_NAS:
            if var(fnp).fp <> none                        ---   T E M P
            then                                          ---   T E M P
                 if var(fnp).fp.kind <> K_SMP then IERR_R("npAccA-3")
                 endif;
            endif;                                        ---   T E M P
            case 0:MAX_TYPE (ap.type)
            when T_BOO:  adr:= name(bio.thunks.val.boo);
            when T_CHA:  adr:= name(bio.thunks.val.cha);
            when T_SIN:  adr:= name(bio.thunks.val.sin);
            when T_INT:  adr:= name(bio.thunks.val.int);
            when T_REA:  adr:= name(bio.thunks.val.rea);
            when T_LRL:  adr:= name(bio.thunks.val.lrl);
            when T_REF:  adr:= name(bio.thunks.val.pnt);
            when T_PTR:  adr:= name(bio.thunks.val.ptr);
            when T_TXT:  adr:= name(bio.thunks.val.txt);
            otherwise IERR_R("npAccA-4") endcase;
       when PAR_THK_ASS:
            adr:= bio.thunks.val.ins + bio.thunks.val.fld;
       otherwise IERR_R("npAccA-5") endcase;
 end;


 Visible routine EncBoo;
 import name(infix(parqnt)) fnp; export boolean val;
 begin val:= var(npAccA(fnp) qua name(boolean)); end;


 Visible routine EncCha;
 import name(infix(parqnt)) fnp; export character val;
 begin val:= var(npAccA(fnp) qua name(character)); end;


 Visible routine EncPtr;
 import name(infix(parqnt)) fnp; export ref() val;
 begin val:= var(npAccA(fnp) qua name(ref())); end;


 Macro E_FNP_ACC_CNV(2);  --  1. parameter is the type of 'val'.
                          --  2. parameter is routine name for IERR
 begin name() adr;
       adr:= npAccA(fnp);
       case 0:T_LRL (var(fnp).ap.type)
       when T_SIN:  val:= var(adr qua name(infix(quant))).sin qua %1;
       when T_INT:  val:= var(adr qua name(integer)) qua %1;
       when T_REA:  val:= var(adr qua name(real)) qua %1;
       when T_LRL:  val:= var(adr qua name(long real)) qua %1;
       otherwise IERR_R(%2) endcase;
 endmacro;


 Visible routine EncSin;
 import name(infix(parqnt)) fnp; export short integer val;
 begin E_FNP_ACC_CNV(%short integer%,%"EncSin"%); end;

 Visible routine EncInt;
 import name(infix(parqnt)) fnp; export integer val;
 begin E_FNP_ACC_CNV(%integer%,%"EncInt"%); end;

 Visible routine EncRea;
 import name(infix(parqnt)) fnp; export real val;
 begin E_FNP_ACC_CNV(%real%,%"EncRea"%); end;

 Visible routine EncLrl;
 import name(infix(parqnt)) fnp; export long real val;
 begin E_FNP_ACC_CNV(%long real%,%"EncLrl"%); end;


 Visible routine EncRef;
 import name(infix(parqnt)) fnp; export ref(inst) val;
 begin val:= var(npAccA(fnp) qua name(ref(inst)));
% 	   ED_STR("FORM.EncRef: val="); ED_OADDR(val); ED_OUT;
% 	   DMPENT(val);
       ---  Check that the qualification of the accessed object is
       ---  subordinate the the formal qualification.
       if val <> none
       then FNP_CHK_QAL(var(fnp).fp qua ref(refdes).qal,
                        val.pp qua ref(claptp),ENO_FNP_6);
       endif;
 end;


 Visible routine EncTxt;
 import name(infix(parqnt)) fnp; export infix(txtqnt) val;
 begin val:= var(npAccA(fnp) qua name(infix(txtqnt))); end;


 Visible routine EncArr;
 import name(infix(parqnt)) fnp; export ref(entity) val;
 begin case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_NAS: -- Parameter quantity contains array pointer.
            val:= var(fnp).ent;
       when PAR_THK_NAS: -- The actual quantity is in thunk instance.
            val:= bio.thunks.val.arr;
       otherwise IERR_R("EncArr") endcase;
 end;


 Visible routine EncPro;
 import name(infix(parqnt)) fnp; export infix(proqnt) val;
 begin case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_NAS: -- Parameter quantity contains actual quantity.
            val.sl:= var(fnp).ent qua ref(inst);
            val.ppp:= var(fnp).ppp; val.qal:= var(fnp).qal;
       when PAR_THK_NAS: -- The actual quantity is in thunk instance.
            val:= bio.thunks.val.pro;
       otherwise IERR_R("EncPro") endcase;
       ---  If it is a ref-procedure, check qualification.
       if var(fnp).fp.type = T_REF
       then FNP_CHK_QAL(var(fnp).fp qua ref(refdes).qal,
                        val.qal,ENO_FNP_7); endif;
 end;


 Visible routine EncLab;
 import name(infix(parqnt)) fnp; export infix(labqnt) val;
 begin case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_NAS: -- Parameter quantity contains actual quantity.
            val.sl:= var(fnp).ent qua ref(inst);
            val.pad:= var(fnp).pad; val.clv:= var(fnp).clv;
       when PAR_THK_NAS: -- The actual quantity is in thunk instance.
            val:= bio.thunks.val.lab;
       otherwise IERR_R("EncLab") endcase;
 end;


 Visible routine EncSwt;
 import name(infix(parqnt)) fnp; export infix(swtqnt) val;
 begin case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_NAS: -- Parameter quantity contains actual quantity.
            val.sl:= var(fnp).ent qua ref(inst);
            val.des:= var(fnp).des;
       when PAR_THK_NAS: -- The actual quantity is in thunk instance.
            val:= bio.thunks.val.swt;
       otherwise IERR_R("EncSwt") endcase;
 end;

%title ***  Pick up value returned by name procedure  ***

 Macro FNP_PRO_CNV(2);   --  The parameter is the type of 'val'.
                         --  2. parameter is routine name for IERR
 begin case T_SIN:T_LRL(type)            -- Actual type.
       when T_SIN:    val:= tmp.pnt qua ref(sinPro).val qua %1;
       when T_INT:    val:= tmp.pnt qua ref(intPro).val qua %1;
       when T_REA:    val:= tmp.pnt qua ref(reaPro).val qua %1;
       when T_LRL:    val:= tmp.pnt qua ref(lrlPro).val qua %1;
       otherwise IERR_R(%2) endcase;
 endmacro;


 Visible routine npPsin;
 import range(0:MAX_TYPE) type; export short integer val;
 begin FNP_PRO_CNV(%short integer%,%"npPsin"%); end;

 Visible routine npPint;
 import range(0:MAX_TYPE) type; export integer val;
 begin FNP_PRO_CNV(%integer%,%"npPint"%); end;

 Visible routine npPrea;
 import range(0:MAX_TYPE) type; export real val;
 begin FNP_PRO_CNV(%real%,%"npPrea"%); end;

 Visible routine npPlrl;
 import range(0:MAX_TYPE) type; export long real val;
 begin FNP_PRO_CNV(%long real%,%"npPlrl"%); end;

%title ***  Evaluate name (text) before dot (e.g. t.setpos)  ***

 Visible routine B_npTA;
 import name(infix(parqnt)) fnp;  --  Parameter's address
 exit label psc;
 begin ref(pardes) ap;            --  Actual parameter's descriptor
       ref(thunk) thk;            --  To storage area being allocated.
       ap:= var(fnp).ap;              --  For fast execution.
       case 0:MAX_PAR (ap.code)
       when PAR_QNT_LIT: ---  Allocate a thunk instance and store a copy
                         ---  of the text literal in it.
            ALLOC2(S_THK,thk,%size(thunk)%);
            thk.pp:= ref(ADR_TXT_PTP);
            thk.val.txt  := var(ap qua ref(litPar).l_txt + nofield
                                 qua name(infix(txtqnt)));
            tmp.pnt:= thk;
       when PAR_THK_NAS:    ---  Allocate and initiate a thunk.
            curins.lsc:= psc;
            psc:= AB_THK(var(fnp).ent qua ref(inst),
                         ref(ADR_TXT_PTP),
                         ap qua ref(thkPar).pad,true);
       when PAR_THK_ASS:    ---  Allocate and initiate a thunk.
            ---  Since the address which the thunk will evaluate shall
            ---  be used at once, the thunk gets the "access" prototype.
            ---  See the routine E_FNP_TXT_ADR.
            curins.lsc:= psc;
            psc:= AB_THK(var(fnp).ent qua ref(inst),ref(VAL_THK_PTP)
                             ,ap qua ref(thkPar).pad,true);
       otherwise IERR_R("B_FNP_TXT_ADR") endcase;
 end;


 Visible routine E_npTA;
 import name(infix(parqnt)) fnp;  --  parameter quantity address
 export name() adr;               --  actual parameter's address
 begin case 0:MAX_PAR (var(fnp).ap.code)
       when PAR_QNT_LIT:
            adr:= name(tmp.pnt qua ref(thunk).val.txt);
       when PAR_THK_NAS:
            adr:= name(bio.thunks.val.txt);
            ---  Remove the thunk instance from the free list.
            ---  It will never be put in the free list again.
            bio.thunks:= bio.thunks.dl;
       when PAR_THK_ASS:
            adr:= bio.thunks.val.ins + bio.thunks.val.fld;
       otherwise IERR_R("E_FNP_TXT_ADR") endcase;
 end;


 Visible routine E_npA;
 import name(infix(parqnt)) fnp;  --  parameter quantity address
 export name() adr;               --  actual parameter's address
 begin ---  Check that the actual parameter denotes a variable, which
       ---  implies that its kind is simple, and get its address.
       adr:= npAssA(fnp,ENO_FNP_2);
       if var(fnp).ap.type <> var(fnp).fp.type
       then ERROR(ENO_FNP_3) endif;
       if var(fnp).ap.type = T_REF
       then if var(fnp).ap qua ref(refPar).qal <>
               var(fnp).fp qua ref(refdes).qal
            then ERROR(ENO_FNP_4) endif;
       endif;
 end;

%title ***  F o r m a l   a n d    V i r t u a l    P r o c e d u r e 


%    Visible record proqnt;  info "TYPE";
%    begin ref(inst)      sl;
%          ref(proptp)   ppp;
%          ref(claptp)   qal;
%    end;

 Visible routine B_FORM;  --  formal procedure
 import infix(proqnt) pro; exit label psc;
 begin ref(inst) ins;     -- The one we will allocate now.
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;

% +M	   ED_STR("FORM.B_FORM: pro.sl="); ED_OADDR(pro.sl); ED_OUT;
% +M	   ED_STR("FORM.B_FORM: pro.ppp="); ED_OADDR(pro.ppp); ED_OUT;
% +M	   ED_STR("FORM.B_FORM: pro.qal="); ED_OADDR(pro.qal); ED_OUT;
% +M	   ED_STR("FORM.B_FORM: tmp.pro.sl="); ED_OADDR(tmp.pro.sl); ED_OUT;
% +M	   ED_STR("FORM.B_FORM: tmp.pro.ppp="); ED_OADDR(tmp.pro.ppp); ED_OUT;
% +M	   ED_STR("FORM.B_FORM: tmp.pro.qal="); ED_OADDR(tmp.pro.qal); ED_OUT;
	   if pro.ppp = none then         -- AD'HOC  MYH 30/3-2023
	   		pro.sl := tmp.pro.sl;     -- AD'HOC  MYH 30/3-2023
	   		pro.ppp := tmp.pro.ppp;   -- AD'HOC  MYH 30/3-2023
	   		pro.qal := tmp.pro.qal;   -- AD'HOC  MYH 30/3-2023
	   endif;

       ---  Check that the number of parameters are zero.
       if pro.ppp.parVec <> none then  ERROR(ENO_PRO_1)  endif;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_PRO,ins,pro.ppp.lng,pro.sl);
       ---  Fill in procedure attributes.
       ins.sl:=pro.sl; ins.pp:=pro.ppp; ins.dl:=curins;
       ---  Enter procedure instance's code.
       curins:=ins;
%-X    if bio.trc then curins.lsc:=pro.ppp.start; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
       psc:=pro.ppp.start;
 end;

 Visible routine A_FORM;
 import infix(proqnt) pro;
        range(0:MAX_ATR) npar;   --  Number of parameters.
 export ref(inst) ins;       -- The one we will allocate now.
 begin ---  Check that the number of parameters are correct.
       if pro.ppp.parVec = none then ERROR(ENO_PRO_1);
       elsif npar <> pro.ppp.parVec.natr then ERROR(ENO_PRO_1) endif;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_PRO,ins,pro.ppp.lng,pro.sl);
       ---  Fill in procedure attributes.
       ins.sl:=pro.sl; ins.pp:=pro.ppp;
 end;

 Macro GET_FP(2);     --- The call is always GET_FP(parins,n);
 begin ref(atrdes)fp; --- parameters for documentation purposes only.
       fp:= parins.pp qua ref(proptp).parVec.atr(n);
 endmacro;


 Visible routine B_FPT;
 import ref(inst) parins;  --  Transmitting to this procedure
        ref(inst) sl;      --  Static enclosure for thunk
        ref(pardes) ap;        --  Actual parameter's descriptor
        short integer n;       --  No. (position) of parameter
 exit label psc;
 begin GET_FP(parins,n);
   --  Save the parameter instance by inserting it in the dynamic chain.
       parins.dl:= curins.dl;         curins.dl:= parins;
       if  (fp.mode <> M_NAME)
       and ((ap.code = PAR_THK_NAS)  or (ap.code = PAR_THK_ASS))
       then curins.lsc:= psc;
            psc:= AB_THK(sl,ref(VAL_THK_PTP),
                         if ap.type = T_REF
                         then ap qua ref(refThk).pad
                         else ap qua ref(thkPar).pad,fp.kind=K_SMP);
       endif;
 end;


 Visible routine E_FPT;
 import short integer n;       --  No. (position) of parameter.
        infix(parqnt) par;     --  Transmitting this parameter.
        ref(inst) cla_sl;  --  Qualifying class declared here.
                               --  Different from none <0> ref type.
 export ref(inst) parins;  --  Transmitting to this procedure.
 begin name() adr;             --  The address of the target.
       ref(pardes) ap;         --  Local copy here for efficiency.
       ref(atrdes) fp;         --  Formal parameter's descriptor.
       -- Restore the parameter instance by removing it from the chain.
       parins:= curins.dl;            curins.dl:= parins.dl;
       ---  Pick up the corresponding formal parameter descriptor,
       fp:= parins.pp qua ref(proptp).parVec.atr(n);
       ap:= par.ap;              --  Use local variable for efficiency.
       adr:= parins + fp.fld;    --  Compute the address of the target.
       par.fp:= fp;  ---  Set mode, kind, type ... for formal parameter.
       if fp.mode = M_NAME
       then ---  Formal parameter is call by name. Whether the actual
            ---  parameter is call by name or not, the parameter
            ---  quantity we have imported is representing it correctly,
            ---  except for its formal properties. See if actual and
            ---  formal matches exactly and check legality.
            par.sem:= par.sem  and  FPT_TO_FNP_CHK(ap,fp);
            var(adr qua name(infix(parqnt))):= par;
       else ---  Formal is by value or by reference.
            ---  Check for legality and compatibility.
            FPT_TO_FRP_CHK(ap,fp);
            case  0:MAX_KIND (fp.kind)
            when  K_SMP: --  Formal parameter is of simple kind.
                         --  Store the parameter value into the target.
                  case 0:MAX_TYPE (fp.type)
                  when T_BOO:   var(adr qua name(boolean))
                                :=  EncBoo(name(par));
                  when T_CHA:   var(adr qua name(character))
                                :=  EncCha(name(par));
                  when T_SIN:   var(adr qua name(infix(quant))).sin
                                :=  EncSin(name(par));
                  when T_INT:   var(adr qua name(integer))
                                :=  EncInt(name(par));
                  when T_REA:   var(adr qua name(real))
                                :=  EncRea(name(par));
                  when T_LRL:   var(adr qua name(long real))
                                :=  EncLrl(name(par));
                  when T_REF:   var(adr qua name(ref(inst)))
                                :=  EncRef(name(par));
                  when T_PTR:   var(adr qua name(ref()))
                                :=  EncPtr(name(par));
                  when T_TXT:   var(adr qua name(infix(txtqnt)))
                                :=  EncTxt(name(par));
                       ---  Check if the text is transmitted by value.
                       ---  Garbage collection may occur in TXT_BY_VAL.
                       if fp.mode = M_VALUE
                       then parins:= txtVal(parins,fp.fld) endif;
                  endcase;
            when  K_ARR: --  Formal parameter is of array kind. Store
                     -- the parameter value into the parameter quantity.
                  var(adr qua name(ref(entity))):=
                                        EncArr(name(par));
                  ---  Check if the array is transmitted by value.
                  ---  Garbage collection may occur in arrVal
                  if fp.mode = M_VALUE
                  then parins:= arrVal(parins,fp.fld) endif;
            when  K_PRO:  --  Formal parameter is of procedure kind.
                  var(adr qua name(infix(proqnt))):=
                                   EncPro(name(par));
            when  K_LAB:  --  Formal parameter is of label kind.
                  var(adr qua name(infix(labqnt))):=
                                   EncLab(name(par));
            when  K_SWT:  --  Formal parameter is of switch kind.
                  var(adr qua name(infix(swtqnt))):=
                                   EncSwt(name(par));
            endcase;
       endif;   -- End formal is by value or by reference.
 end;
%page

 Macro M_FPT_SMP_PAR(1);     --  First parameter is the procedure name.
 begin Visible routine %1;  --  Transmit literal or non-thunk variable.
       import ref(inst) parins; --  Transmitting to this procedure.
              short integer n;      --  No. (position) of parameter.
              ref(pardes) ap;       --  Actual parameter's descriptor.
              ref() obj;            --  Object part of address.
              field() fld;          --  Offset part of address.
 endmacro;


 Macro M_FPT_SMP_BOD(0);
 begin ref(atrdes)     fp;    --  Formal parameter descriptor.
       fp:= parins.pp qua ref(proptp).parVec.atr(n);
       if fp.mode = M_NAME ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,obj,fld);
       else                ---  Formal is call by reference or value.
            if fp.kind <> K_SMP then ERROR(ENO_FPT_3) endif;
 endmacro;


 M_FPT_SMP_PAR(FPTBOO);
 begin M_FPT_SMP_BOD();
            if fp.type <> T_BOO then ERROR(ENO_FPT_4)  endif;
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(boolean)):=
                 var((obj + fld) qua name(boolean));
       endif;   -- End formal is call by value.
 end;


 M_FPT_SMP_PAR(FPTCHA);
 begin M_FPT_SMP_BOD();
            if fp.type <> T_CHA then ERROR(ENO_FPT_4)  endif;
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(character)):=
                  var((obj + fld) qua name(character));
       endif;   -- End formal is call by value.
 end;


 M_FPT_SMP_PAR(FPTPTR);
 begin M_FPT_SMP_BOD();
            if fp.type <> T_PTR then ERROR(ENO_FPT_4)  endif;
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(ref()  )):=
                 var((obj + fld) qua name(ref()  ));
       endif;   -- End formal is call by value.
 end;


 Macro FPT_CNV(1);
 ---  The parameter is an expression describing the type of the
 ---  value to be transmitted.
 begin ---  Place the actual quantity in the parameter quantity.
       case 0:MAX_TYPE (fp.type)
       when T_SIN: var((parins + fp.fld) qua name(infix(quant))).sin :=
                     var((obj+fld) qua name(infix(quant))).
                     %1 qua short integer;
       when T_INT: var((parins + fp.fld) qua name(integer))       :=
                     var((obj+fld) qua name(infix(quant))).
                     %1 qua integer;
       when T_REA: var((parins + fp.fld) qua name(real))          :=
                     var((obj+fld) qua name(infix(quant))). %1 qua real;
       when T_LRL: var((parins + fp.fld) qua name(long real))     :=
                     var((obj+fld) qua name(infix(quant))).
                     %1 qua long real;
       otherwise   ERROR(ENO_FPT_5);
       endcase;
 endmacro;


 M_FPT_SMP_PAR(FPTSIN);
 begin M_FPT_SMP_BOD();
            FPT_CNV(sin);
       endif;   -- End formal is call by value.
 end;

 M_FPT_SMP_PAR(FPTINT);
 begin M_FPT_SMP_BOD();
            FPT_CNV(int);
       endif;   -- End formal is call by value.
 end;


 M_FPT_SMP_PAR(FPTREA);
 begin M_FPT_SMP_BOD();
            FPT_CNV(rea);
       endif;   -- End formal is call by value.
 end;


 M_FPT_SMP_PAR(FPTLRL);
 begin M_FPT_SMP_BOD();
            FPT_CNV(lrl);
       endif;   -- End formal is call by value.
 end;


 M_FPT_SMP_PAR(FPTREF);
 ref(inst) cla_sl;       --  Qualifying class declared here.
 begin ref(inst) val;    --  The value of the parameter.
       M_FPT_SMP_BOD();
            if fp.type <> T_REF then ERROR(ENO_FPT_4) endif;
            ---  Place the actual quantity in the parameter quantity.
            val:=  var((obj + fld) qua name(ref(inst)));
            ---  Check qualification.
            if val <> none
            then FNP_CHK_QAL(fp qua ref(refdes).qal,
                            val.pp qua ref(claptp),ENO_FPT_1);
            endif;
            var((parins+fp.fld) qua name(ref(inst))):= val;
       endif;   -- End formal is call by value.
 end;


 Visible routine FPTTXT;    --  Transmit literal or non-thunk variable.
 import ref(inst) parins; --  Transmitting to this procedure.
        short integer n;      --  No. (position) of parameter.
        ref(pardes) ap;       --  Actual parameter's descriptor.
        ref() obj;            --  Object part of address.
        field() fld;          --  Offset part of address.
 begin
       ref(atrdes)     fp;    --  Formal parameter descriptor.
       ref()           tp;    --  E.g. ref(infix(txtqnt)).
       fp:= parins.pp qua ref(proptp).parVec.atr(n);
       if fp.mode = M_NAME ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,obj,fld);
       else                ---  Formal is call by reference or value.
            if fp.kind <> K_SMP then ERROR(ENO_FPT_3) endif;

            if fp.type <> T_TXT then ERROR(ENO_FPT_4)  endif;
            ---  Place the actual quantity in the parameter quantity.
            if ap.code=PAR_QNT_LIT
            then tp:=ap qua ref(litPar).l_txt;
                 var((parins+fp.fld) qua name(infix(txtqnt))):=
                        tp qua ref(quant).txt;
            else var((parins+fp.fld) qua name(infix(txtqnt))):=
                        var((obj+fld) qua name(infix(txtqnt)));
            endif;
            ---  Garbage collection may occur in TXT_BY_VAL.
            if fp.mode = M_VALUE then parins:= txtVal(parins,fp.fld);
            endif;
       endif;   -- End formal is call by reference or value.
 end;


 Visible routine FPTARR;
 import ref(inst) parins;  --  Transmitting to this procedure
        short integer n;       --  No. (position) of parameter
        ref(pardes) ap;        --  Actual parameter's descriptor
        ref(inst) cla_sl;  --  Qualifying class declared here.
        ref(entity) arr;       --  The actual array.
 begin GET_FP(parins,n);
       if fp.mode = M_NAME     ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,arr,nofield);
       else ---  Check for legality and compatibility.
            FPT_TO_FRP_CHK(ap,fp);
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(ref(entity))):= arr;
            ---  Garbage collection may occur in arrVal
            if fp.mode = M_VALUE
            then parins:= arrVal(parins,fp.fld) endif;
       endif;   -- End formal is call by reference or value.
 end;


 Visible routine FPTPRO;
 import ref(inst) parins;   --  Transmitting to this procedure
        short integer n;        --  No. (position) of parameter
        ref(pardes) ap;         --  Actual parameter's descriptor
        ref(inst) cla_sl;   --  Qualifying class declared here.
        infix(proqnt) pro;      --  The actual procedure
 begin name(infix(parqnt)) fnp; --  Address of name parameter.
       GET_FP(parins,n);
       if fp.mode = M_NAME      ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,pro.sl,nofield);
       ---  Set component values of the quantity we are transferring to.
            fnp:=(parins + fp.fld) qua name(infix(parqnt));
            var(fnp).ppp:= pro.ppp; var(fnp).qal:= pro.qal;
       else ---  Check for legality and compatibility.
            FPT_TO_FRP_CHK(ap,fp);
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(infix(proqnt))):= pro;
       endif;   -- End formal is call by value.
 end;


 Visible routine FPTLAB;  --  Transmit non-thunk label.
 import ref(inst) parins;    --  Transmitting to this procedure
        short integer n;         --  No. (position) of parameter
        ref(pardes) ap;          --  Actual parameter's descriptor
        infix(labqnt) lab;       --  The actual label.
 begin name(infix(parqnt)) fnp;  --  Address of name parameter.
       GET_FP(parins,n);
       if fp.mode = M_NAME       ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,lab.sl,nofield);
       ---  Set component values of the quantity we are transferring to.
            fnp:=(parins + fp.fld) qua name(infix(parqnt));
            var(fnp).pad:= lab.pad; var(fnp).clv:= lab.clv;
       else if fp.kind <> K_LAB then ERROR(ENO_FPT_3)  endif;
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(infix(labqnt))):= lab;
       endif;   -- End formal is call by value.
 end;


 Visible routine FPTSWT;  --  Transmit non-thunk switch.
 import ref(inst) parins;   --  Transmitting to this procedure
        short integer n;        --  No. (position) of parameter
        ref(pardes) ap;         --  Actual parameter's descriptor
        infix(swtqnt) swt;      --  The actual switch.
 begin GET_FP(parins,n);
       if fp.mode = M_NAME      ---  Formal is call by name.
       then FPT_TO_FNP(parins,ap,fp,swt.sl,nofield);
        --  Set component value of the quantity we are transferring to.
            var((parins+fp.fld) qua name(infix(parqnt))).des:= swt.des;
       else if fp.kind <> K_SWT then ERROR(ENO_FPT_3)  endif;
            ---  Place the actual quantity in the parameter quantity.
            var((parins + fp.fld) qua name(infix(swtqnt))):= swt;
       endif;   -- End formal is call by value.
 end;


 Routine FPT_TO_FNP;
 import ref(inst) parins; --  Transmitting to this one
        ref(pardes) ap;       --  Actual parameter's descriptor
        ref(atrdes) fp;       --  Formal parameter's descriptor
        ref(entity) ent;      --  Entity part of address.
        field() fld;          --  Offset part of address.
 begin infix(parqnt) fnp;     --  Local quantity here for efficiency.
  ---  Set the component values of the quantity we are transferring to.
       fnp.ap:= ap; fnp.fp:= fp;
       fnp.sem:= FPT_TO_FNP_CHK(ap,fp);
       fnp.ent:= ent; fnp.fld:= fld;
       --  Transfer to the target, which has been initialised to 'null'.
       var((parins + fp.fld) qua name(infix(parqnt))):= fnp;
 end;


 Routine FPT_TO_FNP_CHK;
 import ref(pardes) ap;  --  Actual parameter's descriptor
        ref(atrdes) fp;  --  Formal parameter's descriptor
 export boolean sem;     --  Do formal and actual match exactly?
 begin ---  See if actual and formal matches exactly.
       sem:=(fp.kind = ap.kind)  and (fp.type = ap.type);
       if sem
       then if ap.type = T_REF
            then if fp qua ref(refdes).qal=ap qua ref(refPar).qal
                 then sem:=(ap.code = PAR_QNT_ASS);  --  Implies kind simple.
                 else if(fp.kind <> K_ARR) then ERROR(ENO_FPT_7)  endif;
                      sem:= false;
                 endif;
            else sem:=(ap.code = PAR_QNT_ASS);  --  Implies kind simple.
            endif;
       else --- Invariant: (fp.kind <> ap.kind)  or (fp.type <> ap.type)
            --- Check for legality and compability.
            case 0:MAX_KIND (fp.kind)
            when K_SMP:       --  Formal parameter is of simple kind.
                 if (ap.kind <> K_SMP) and (ap.kind <> K_PRO)
                 then ERROR(ENO_FPT_3) endif;
                 if not COMPAT(ap.type,fp.type) then ERROR(ENO_FPT_5)
                 endif;
                 --  The qualification shall not be checked now, if type is ref.
            when K_ARR:       --  Formal parameter is of array kind.
                 if ap.kind <> K_ARR   then ERROR(ENO_FPT_3) endif;
                 if ap.type <> fp.type then ERROR(ENO_FPT_4) endif;
                 --  The exact match test above must have chosen the then-part.
                 IERR_R("FPT_TO_FNP_CHK");
            when K_PRO:       --  Formal parameter is of procedure kind.
                 if ap.kind <> K_PRO        then ERROR(ENO_FPT_3) endif;
                 if not compat(ap.type,fp.type) then ERROR(ENO_FPT_5) endif;
                 --  If formal is a ref-procedure, then we will never get here.
                 --  The exact match test above must have chosen the then-part.
                 --  However, the qualification shall not be checked now.
            otherwise          --  Formal parameter is of label or switch kind.
                  ERROR(ENO_FPT_3);
            endcase;
       endif;
 end;


 Routine FPT_TO_FRP_CHK;
 import ref(pardes) ap;    --  Actual parameter's descriptor
        ref(atrdes) fp;    --  Formal parameter's descriptor
 begin case 0:MAX_KIND (fp.kind)
       when K_SMP:       --  Formal parameter is of simple kind.
            if (ap.kind <> K_SMP)  and (ap.kind <> K_PRO)
            then ERROR(ENO_FPT_3) endif;
            if not COMPAT(ap.type,fp.type) then ERROR(ENO_FPT_5)  endif;
            --  The qualification shall not be checked now, if type is ref.
       when K_ARR:       --  Formal parameter is of array kind.
            if ap.kind <> K_ARR   then ERROR(ENO_FPT_3) endif;
            if ap.type <> fp.type then ERROR(ENO_FPT_4) endif;
            if fp.type = T_REF
            then ---  The compiler has checked that the formal is not by value.
                 if (fp qua ref(refdes).qal) <> (ap qua ref(refPar).qal)
                 then ERROR(ENO_FPT_7) endif;
            endif;
       when K_PRO:       --  Formal parameter is of procedure kind.
            if ap.kind <> K_PRO then ERROR(ENO_FPT_3)  endif;
            if (fp.type <> T_NOTYPE)  and (ap.type <> fp.type)
            then ERROR(ENO_FPT_8) endif; --  Neither subordinate nor coincident.
            if fp.type = T_REF
            then --  Must check qualification, even if checked by EncPro
                 FNP_CHK_QAL(fp qua ref(refdes).qal,
                             ap qua ref(refPar).qal,ENO_FPT_2);
            endif;
       when K_LAB:       --  Formal parameter is of label kind.
            if ap.kind <> K_LAB then ERROR(ENO_FPT_3) endif;
       when K_SWT:       --  Formal parameter is of switch kind.
            if ap.kind <> K_SWT then ERROR(ENO_FPT_3) endif;
       endcase;
 end;


 Visible routine thk2pq;
 import ref(pardes) ap;      --  Actual parameter's descriptor
 export infix(parqnt) par;   --  Representing the transmitted
 begin par.ap:= ap; par.fp:= none;
       par.sem:= false; par.ent:= curins;
       ---  None of the variant parts are in use. Thus the following statements
       ---  are not necessary, but they are included in order to 'nullify' as
       ---  much as possible of the variant storage area.
       par.ppp:= none; par.qal:= none;
 end;


 Routine COMPAT;
 import range(0:MAX_TYPE) at;         --  actual type
        range(0:MAX_TYPE) ft;         --  formal type
 export boolean cmpt;
 begin case 0:MAX_TYPE (ft)
       when T_NOTYPE: cmpt:= true;     -- formal is untyped procedure
       when T_SIN,T_INT,T_REA,T_LRL:
            case 0:MAX_TYPE (at)
            when T_INT,T_SIN,T_REA,T_LRL: cmpt:= true;
            otherwise cmpt:= false  endcase
       otherwise cmpt:= at = ft  endcase;
 end;

 end;
