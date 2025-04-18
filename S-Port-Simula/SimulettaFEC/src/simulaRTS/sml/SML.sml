 Module sml("RTS");
 begin sysinsert rt,sysr,knwn,util,cent,smst;

       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---           P O R T A B L E     S I M U L A           ---
       ---            R U N T I M E     S Y S T E M            ---
       ---                                                     ---
       ---           C l a s s    S I M U L A T I O N          ---
       ---                                                     ---
       -----------------------------------------------------------

Macro NAME_ATTR(4);
begin const infix(atrdes) %1 = record:atrdes
        (ident=ref( %2 ),fld=field( %3 ),
         mode=M_NAME,kind=K_SMP,type= %4 );
endmacro;

%-X long real smltim; -- temp 'old' simulation time
--------   M O D U L E    I N F O   ---------

const infix(modinf) SMLMOD=record:modinf
%-X   (modIdt=ref(SMLIDT),obsLvl=2);
%+X   (modIdt=none       ,obsLvl=0);

%-X DEFINE_IDENT(%SMLIDT%,%10%,%('S','I','M','U','L','A','T','I','O','N')%);

---------   C l a s s    S I M U L A T I O N   ---------

%tag (simltn,smlPtp)

 Visible record simltn1:linkag;
 begin ref(rankin) sqs; ref(proces) cur,main end;

 Visible record simltn:simltn1; begin end;

 Visible const infix(claptp:3) SMLPTP=record:claptp
       ( plv=1,lng=size(simltn),refVec=ref(SML_refVec),
%-X      xpp=ref(SMLXPP),
         dcl=nowhere,stm=SMLSTM,cntInr=E_OBJ,
         prefix =(ref(smsPtp),ref(SMLPTP),none));

 const infix(pntvec:3) SML_refVec=record:pntvec
       ( npnt=3,pnt=(field(simltn.sqs),
         field(simltn.cur),field(simltn.main)));

%-X const infix(ptpExt) SMLXPP=record:ptpExt
%-X    (idt=ref(SMLIDT),modulI=ref(SMLMOD),
%-X     attrV=ref(SMLATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:2) SMLATR=record:atrvec
%-X    (natr=2,atr=(ref(SA_CUR),ref(SA_MAIN)));

%-X SIMPLE_ATTR(%SA_CUR%,%ID_CUR%,%simltn.cur%,%T_REF%);
%-X SIMPLE_ATTR(%SA_MAIN%,%ID_MAIN%,%simltn.main%,%T_REF%);

%-X DEFINE_IDENT(%ID_CUR%,%7%,%('C','U','R','R','E','N','T')%);
%-X DEFINE_IDENT(%ID_MAIN%,%4%,%('M','A','I','N')%);

---------   C l a s s    R A N K I N G   ---------

%tag (rankin,rnkPtp)

 Visible record rankin1:linkag;
 begin ref(rankin) bl,ll,rl; long real rnk end;

 Visible record rankin:rankin1; begin end;

 Visible const infix(claptp:4) rnkPtp=record:claptp
       ( plv=2,lng=size(rankin),refVec=ref(RNK_refVec),
%-X      xpp=ref(RNKXPP),
         dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
         prefix=(ref(lkaPtp),ref(lnkPtp),ref(rnkPtp),none));

 const infix(pntvec:3) RNK_refVec=record:pntvec
       ( npnt=3,
         pnt=(field(rankin.bl),field(rankin.ll),field(rankin.rl)));

%-X const infix(ptpExt) RNKXPP=record:ptpExt
%-X    (idt=ref(RNKIDT),modulI=ref(SMLMOD),
%-X     attrV=ref(RNKATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:1) RNKATR=record:atrvec
%-X    (natr=1,atr=ref(RA_RNK));

%-X SIMPLE_ATTR(%RA_RNK%,%ID_RNK%,%rankin.rnk%,%T_LRL%);

%-X DEFINE_IDENT(%RNKIDT%,%7%,%('E','L','E','M','E','N','T')%);
%-X DEFINE_IDENT(%ID_RNK%,%4%,%('R','A','N','K')%);

---------   C l a s s    P R O C E S S   ---------

%tag (proces,prcPtp,PCSINR)

 Visible record proces:rankin;     begin end;

 Visible const infix(claptp:5) prcPtp=record:claptp
       ( plv=3,lng=size(proces),refVec=none,
%-X      xpp=ref(PRCXPP),
         dcl=nowhere,stm=PCSSTM,cntInr=PCSINR,
         prefix=(ref(lkaPtp),ref(lnkPtp),ref(rnkPtp),
                                 ref(prcPtp),none));
                                 
%-X const infix(ptpExt) PRCXPP=record:ptpExt
%-X    (idt=ref(PRCIDT),modulI=ref(SMLMOD),
%-X     attrV=ref(PRCATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:2) PRCATR=record:atrvec
%-X    (natr=2,atr=(ref(PA_EVT),ref(PA_NEV)));

%-X SIMPLE_ATTR(%PA_EVT%,%ID_EVT%,%rankin.rnk%,%T_LRL%);
%-X NAME_ATTR(%PA_NEV%,%ID_NEV%,%rankin.bl%,%T_REF%);

%-X DEFINE_IDENT(%ID_EVT%,%6%,%('E','V','T','I','M','E')%);
%-X DEFINE_IDENT(%ID_NEV%,%6%,%('N','E','X','T','E','V')%);
%-X DEFINE_IDENT(%PRCIDT%,%7%,%('P','R','O','C','E','S','S')%);

---------   C l a s s    M A I N P R O G R A M   ---------

 Record mainprog:proces;            begin end;

 const infix(claptp:6) MAINPTP=record:claptp
       ( plv=4,lng=size(mainprog),refVec=none,
%-X      xpp=ref(MPRXPP),
         dcl=MPRDCL,stm=MPRSTM,cntInr=MPRINR,
         prefix=(ref(lkaPtp),ref(lnkPtp),ref(rnkPtp),
                                 ref(prcPtp),ref(MAINPTP),none));


%-X const infix(ptpExt) MPRXPP=record:ptpExt
%-X    (idt=ref(MPRIDT),modulI=ref(SMLMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%MPRIDT%,%12%,%('M','A','I','N','_','P','R','O','G','R','A','M')%);

%title ***  R a n k i n g   ***

 Routine RANK_PRED;
 import ref(rankin) ins; export ref(rankin) prd;
 begin if ins.rl = ins then prd:=none;   --  ins = head
       elsif ins.rl <> none then prd:=ins.rl;
       elsif ins.ll <> none
       then prd:=ins.ll;
            if prd.rl = prd then prd:=none endif;  --  ins = first
       elsif ins.bl = none then prd:=none;  --  Instance is not ranked.
       else ---  'ins' is a terminal node, which has a predecessor.
            prd:=ins;
            repeat while prd.bl.ll = prd do prd:=prd.bl endrepeat;
            prd:=prd.bl.ll;
       endif;
 end;


 Routine RANK_SUC;
 import ref(rankin) ins; export ref(rankin) suc;
 begin if ins.bl = none then suc:=none;  -- Instance is not ranked.
       elsif ins.bl.rl = ins.bl then suc:=none;  --  ins =  last or head
       else ---  All nodes below 'ins' is preceding 'ins'.
            suc:=ins.bl;  --  Try the father.
            if (suc.rl <> ins) and (suc.rl <> none)
            then --  Look in the right subtree of the father,
                 --  for the node with the least rank value,
                 --  which must be the leftmost terminal node.
                 suc:=suc.rl;
                 repeat while suc.ll <> none do suc:=suc.ll endrepeat;
            endif;
       endif;
 end;


 Routine RANK_CLEAR;
 import ref(rankin) head;
 begin ref(rankin) ins,temp;
       if head.rl <> head then IERR_R("RANK_CLEAR") endif;     --  TEMP
       if head.bl <> head ---  The set is non-empty.
       then ins:=head.bl; ins.ll:=none;      --  ins = first
            head.ll.bl:=none;                --  last.bl = none
            head.bl:=head.ll:=head;
            repeat while ins.bl <> none
            do ---  The instance is the one with the least rank,
               ---  that is, it is the leftmost terminal node.
               ---  ins.ll = ins.rl = none
               ---  Delete terminal nodes, as long as the father has no
               ---  right subtree. (Probably a common situation.)
               repeat temp:=ins.bl; ins.bl:=none; ins:=temp;
               while (ins.rl = none) and (ins.bl <> none)
               do ins.ll:=none endrepeat;
               ---  Make the right subtree the left subtree,
               ---  so that the tree will remain properly ordered.
               ins.ll:=ins.rl;   ins.rl:=none;
               repeat ---  Find the leftmost terminal node.   ins.rl = none
                      repeat while ins.ll <> none do  ins:=ins.ll  endrepeat;
               while ins.rl <> none
               do ---  Make the right subtree the left subtree,
                  ---  so that the tree will remain properly ordered.
                  ins.ll:=ins.rl; ins.rl:=none;
               endrepeat;
            endrepeat;
       endif;
 end;


 Routine RANK_EMPTY;
 import ref(rankin) head; export boolean empty;
 begin if head.rl <> head then IERR_R("RANK_EMPTY") endif;
       empty:=head.bl = head;
 end;


 Routine RANK_FIRST;
 import ref(rankin) head; export ref(rankin) first;
 begin if head.rl <> head then IERR_R("RANK_FIRST") endif;
       first:=head.bl; if first = head then first:=none endif;
 end;


 Routine RANK_LAST;
 import ref(rankin) head; export ref(rankin) last;
 begin if head.rl <> head then IERR_R("RANK_LAST") endif;
       last:=head.ll; if last = head then last:=none endif;
 end;


 Routine RANK_FOLLOW;
 import ref(rankin) ins;      --  Link
        ref(rankin) prd;      --  Linkage
 begin if ins.rl = ins then IERR_R("RANK_FOLLOW") endif;
       if ins.bl <> none then RANK_OUT(ins) endif;
       if (prd <> none)
       then ---  Make sure that 'prd' is ranked.
            if prd.bl <> none
            then ins.rnk:=prd.rnk;
                 ---  Insert 'ins' between 'prd' and its father.
                 if prd = prd.bl.ll then prd.bl.ll:=ins;
                 else prd.bl.rl:=ins; endif;
                 ins.ll:=prd; ins.bl:=prd.bl; prd.bl:=ins;
            endif;
       endif;
 end;


 Routine RANK_OUT;
 import ref(rankin) ins;
 begin ref(rankin) suc,bl,ll,rl;
       if ins.rl = ins then IERR_R("RANK_OUT") endif;
       ---  Make sure that the instance is ranked.
       if ins.bl <> none
       then bl:=ins.bl; ll:=ins.ll; rl:=ins.rl;
            if ll = none
            then ---  Remove a terminal node, while maintaining
                 ---  bl.ll = none   ====>   bl.rl = none
                 if bl.ll = ins then bl.ll:=bl.rl endif;
                 bl.rl:=none;
            elsif ll.rl = ll ---  ins = first  and  ll = head
            then if bl = ll
                 then ---  Remove the one and only ranked instance.
                      ll.bl:=ll.ll:=ll;
                 else ---  Remove the first, but not the last, instance.
                      bl.ll:=bl.rl; bl.rl:=none;
                      ---  ll = head, maintain  head.bl = first
                      suc:=bl;
                      repeat while suc.ll <> none do suc:=suc.ll endrepeat;
                      ll.bl:=suc; suc.ll:=ll;
                 endif;
            elsif rl = none
            then if bl.ll = ins then bl.ll:=ll else bl.rl:=ll endif;
                 ll.bl:=bl;
            else ---  ins.ll <> none  and  ins.rl <> none
                 ---  Replace the node by its right subtree.
                 if bl.ll = ins then bl.ll:=rl else bl.rl:=rl endif;
                 rl.bl:=bl;
                 ---  Must find a free link for the left subtree.
                 ---  The one to look for is 'll.suc' (ll.suc.ll = none).
                 ---  'll.suc' must be the leftmost node in the right subtree.
                 suc:=rl;
                 repeat while suc.ll <> none do suc:=suc.ll endrepeat;
                 ll.bl:=suc; suc.ll:=ll;
            endif;
            ins.bl:=ins.ll:=ins.rl:=none;
       endif;
 end;


 Routine RANK_INTO;
 import ref(rankin) ins,head; long real rnk;
 begin ref(rankin) e;
       if ins.rl = ins then IERR_R("RANK_INTO-1") endif;
       if ins.bl <> none then RANK_OUT(ins) endif;
       if head <> none
       then if head.rl <> head then IERR_R("RANK_INTO-2") endif;
            ins.rnk:=rnk;
            if rnk >= head.ll.rnk ---  Rank it last.
            then ins.bl:=head; ins.ll:=head.ll;
                 head.ll:=head.ll.bl:=ins;
            elsif rnk < head.bl.rnk ---  Rank it first.
            then ins.ll:=head; ins.bl:=head.bl;
                 head.bl:=head.bl.ll:=ins;
            else e:=head.ll;
       L1:L2:    ---  rnk < e.rnk
                 if e.ll = none then e.ll:=ins;
                 elsif rnk < e.ll.rnk then e:=e.ll; goto L1;
                 elsif e.rl = none then e.rl:=ins;
                 elsif rnk < e.rl.rnk then e:=e.rl; goto L2;
                 else ---  e.rnk  >  rnk  >=  e.rl.rnk  >=  e.ll.rnk
                      ins.ll:=e.rl; e.rl:=e.rl.bl:=ins;
                 endif;
                 ins.bl:=e;
            endif;
       endif;
 end;


 Routine RANK_PRECEDE;
 import ref(rankin) ins,suc;
 begin if ins.rl = ins then IERR_R("RANK_PRECEDE-1") endif;
       if ins.bl <> none then RANK_OUT(ins) endif;
       if (suc <> none)
       then if suc.rl = suc then IERR_R("RANK_PRECEDE-2") endif;
            ---  Make sure that 'suc' is ranked.
            if suc.bl <> none
            then ins.rnk:=suc.rnk;
                 ---  Insert 'ins' as the left subtree of 'suc',
                 ---  letting it inherit the subtrees of 'suc'.
                 ins.bl:=suc; ins.ll:=suc.ll; ins.rl:=suc.rl;
                 suc.ll:=ins; suc.rl:=none;
                 ---  Update the back links of the sons of 'suc'.
                 if ins.ll <> none
                 then ins.ll.bl:=ins;
                      if ins.rl <> none then ins.rl.bl:=ins endif;
                 endif;
            endif;
       endif;
 end;

 Routine RANK_PRIOR;
 import ref(rankin) ins,head; long real rnk;
 begin ref(rankin) e;
       if ins.rl = ins then IERR_R("RANK_PRIOR-1") endif;
       if ins.bl <> none then RANK_OUT(ins) endif;
       if head <> none
       then if head.rl <> head then IERR_R("RANK_PRIOR-2") endif;
            ins.rnk:=rnk;
            if rnk > head.ll.rnk ---  Rank it last.
            then ins.bl:=head; ins.ll:=head.ll;
                 head.ll:=head.ll.bl:=ins;
            elsif rnk <= head.bl.rnk ---  Rank it first.
            then ins.ll:=head; ins.bl:=head.bl;
                 head.bl:=head.bl.ll:=ins;
            else e:=head.ll;
       L1:L2:    ---  rnk <= e.rnk
                 if e.ll = none then e.ll:=ins;
                 elsif rnk <= e.ll.rnk then e:=e.ll; goto L1;
                 elsif e.rl = none then e.rl:=ins;
                 elsif rnk <= e.rl.rnk then e:=e.rl; goto L2;
                 else ---  e.rnk  >=  rnk  >  e.rl.rnk  >=  e.ll.rnk
                      ins.ll:=e.rl; e.rl:=e.rl.bl:=ins;
                 endif;
                 ins.bl:=e;
            endif;
       endif;
 end;

%title ***  S i m u l a t i o n   ***

 Macro SQS_FIRST(0); begin simblk.sqs.bl; endmacro;
 Macro SQS_LAST(0);  begin simblk.sqs.ll; endmacro;

 routine RESUMX;
 import ref(inst) ins;  --  Object to be resumed. (always simblk.cur)
        label psc;
 export label new_psc;
 begin ref(inst) head;  --  Component head.
       ref(inst) comp;  --  Component pointer
       ref(inst) m_sl;  --  Static enclosure of main component head.
       ref(inst) main;  --  The head of the main component and also
                            --  the head of the quasi-parallel system.

       ---  Set the program point where current instance is executing.
       curins.lsc:=psc;

       if ins.sort = S_DET
       then ---  The object to be resumed must be local to a system head
            ---  This is either the BASICIO or the SIMULATION instance.
            main:=ins.sl;
            if main.sort <> S_PRE then ERROR(ENO_RES_4) endif;

            ---  Find operating component of the quasi-parallel system
            head:=comp:=curins; m_sl:=main.sl;
            repeat while comp.dl <> m_sl do  comp := comp.dl;
                -- find component head (last non-temp inst on chain)
                   if comp.lsc<>nowhere then head:=comp endif;
            endrepeat;
            if head.sort=S_RES then head.sort:=S_DET endif;
            ins.sort:=S_RES;
            ---  Ignore any temporary instances - those of the
            ---  operating component has already been dealt with
            repeat while ins.dl.lsc=nowhere do ins:=ins.dl endrepeat;
            ---  Rotate the contents of 'ins.dl', 'comp.dl' and 'curins'
            ---  Invariant:       comp.dl=m_sl
            ---  <ins.dl,comp.dl,curins>:=<comp.dl,curins,ins.dl>
            comp.dl:=curins;   curins:=ins.dl;   ins.dl:=m_sl;
       elsif ins.sort <> S_RES then ERROR(ENO_RES_5) endif;
       -- else a nooperation
       new_psc:=curins.lsc;
 end;

 Visible routine activ1; -- after:code=true, before:code=false
 import ref(proces) x; boolean reac,code; ref(proces) y;
 exit label psc;
 begin ref(simltn) simblk; ref(proces) newcur;
       if x = none then
%-X         if bio.trc  --- (re)activate none
%-X         then bio.obsEvt:=if reac then EVT_RAC1 else EVT_ACT1;
%-X              bio.smbP1:=x; observ endif;
       elsif x.sort = S_TRM then
%-X         if bio.trc  --- (re)activate terminated process
%-X         then bio.obsEvt:=if reac then EVT_RAC2 else EVT_ACT2;
%-X              bio.smbP1:=x; observ endif;
       elsif (x.bl <> none) and (not reac) then
%-X         if bio.trc  --- activate scheduled process
%-X         then bio.obsEvt:=EVT_ACT3; bio.smbP1:=x; observ endif;
       elsif x = y then
%-X         if bio.trc  --- reactivate  x  before/after  x
%-X         then bio.obsEvt:=EVT_RAC3; bio.smbP1:=x; observ endif;
       else if code then RANK_FOLLOW(x,y) else RANK_PRECEDE(x,y) endif;
%-X         if bio.trc
%-X         then bio.obsEvt:=if reac then EVT_RACT else EVT_ACTI;
%-X              bio.smbP1:=x; observ endif;
            simblk:=x.sl qua simltn;
            newcur:=SQS_FIRST() qua proces;
            ---  If y is not scheduled in a before/after statement,
            ---  then the only effect is to remove x from the sequencing set.
            if newcur = simblk.sqs then ERROR(ENO_SML_1) endif;
            if newcur <> simblk.cur
            then simblk.cur:=newcur;
%-X              smltim:=newcur.rnk;
                 psc:=RESUMX(newcur,psc);
            endif;
       endif;
 end;


 Visible routine activ2; -- delay:code=true, at:code=false
 import ref(proces) x; boolean reac,code; long real t; boolean prior;
 exit label psc;
 begin long real time; ref(simltn) simblk; ref(proces) newcur;
       if x = none then
%-X         if bio.trc  --- (re)activate none
%-X         then bio.obsEvt:=if reac then EVT_RAC1 else EVT_ACT1;
%-X              bio.smbP1:=x; observ endif;
       elsif x.sort = S_TRM then
%-X         if bio.trc  --- (re)activate terminated process
%-X         then bio.obsEvt:=if reac then EVT_RAC2 else EVT_ACT2;
%-X              bio.smbP1:=x; observ endif;
       elsif (x.bl <> none) and (not reac) then
%-X         if bio.trc  --- activate scheduled process
%-X         then bio.obsEvt:=EVT_ACT3; bio.smbP1:=x; observ endif;
       else simblk:=x.sl qua simltn; time:=simblk.cur.rnk;
            if code then if t < 0.0&&0 then t:=0.0&&0 endif; t:=time+t;
            elsif t<time then t:=time endif;
            ---  Invariant:   t >= time
            if prior then RANK_PRIOR(x,simblk.sqs,t);
            else RANK_INTO(x,simblk.sqs,t) endif;
%-X         if bio.trc
%-X         then bio.obsEvt:=if reac then EVT_RACT else EVT_ACTI;
%-X              bio.smbP1:=x; observ endif;
            newcur:=SQS_FIRST() qua ref(proces);
            if newcur <> simblk.cur
            then simblk.cur:=newcur;
%-X              smltim:=newcur.rnk;
                 psc:=RESUMX(newcur,psc);
            endif;
       endif;
 end;


 Visible routine activ3;                  --  Activate/Reactivate x
 import ref(proces) x; boolean reac; exit label psc;
 begin ref(simltn) simblk;
       if x = none then
%-X         if bio.trc  --- (re)activate none
%-X         then bio.obsEvt:=if reac then EVT_RAC1 else EVT_ACT1;
%-X              bio.smbP1:=x; observ endif;
       elsif x.sort = S_TRM then
%-X         if bio.trc  --- (re)activate terminated process
%-X         then bio.obsEvt:=if reac then EVT_RAC2 else EVT_ACT2;
%-X              bio.smbP1:=x; observ endif;
       elsif (x.bl <> none) and (not reac) then
%-X         if bio.trc  --- activate scheduled process
%-X         then bio.obsEvt:=EVT_ACT3; bio.smbP1:=x; observ endif;
       elsif x.sl qua ref(simltn).cur = x then
%-X         if bio.trc  --- reactivate current
%-X         then bio.obsEvt:=EVT_RAC4; bio.smbP1:=x; observ endif;
       else simblk:=x.sl; RANK_PRECEDE(x,simblk.cur);
%-X         if bio.trc
%-X         then bio.obsEvt:=if reac then EVT_RACT else EVT_ACTI;
%-X              bio.smbP1:=x; observ endif;
            simblk.cur:=x;
%-X         smltim:=x.rnk;
            psc:=RESUMX(x,psc);
       endif;
 end;


 Visible routine CANCEL;
 import ref(simltn) simblk; ref(proces) ins; exit label psc;
 begin if ins = simblk.cur  --- Cancel current?
       then ---  Here follows the inline code for a call on PASSIVATE.
            ---  Remove the first element from the sequencing set.
            RANK_OUT(simblk.cur);
%-X         if bio.trc
%-X         then bio.obsEvt:=EVT_CANC; bio.smbP1:=ins; observ endif;
            simblk.cur:=SQS_FIRST() qua proces;
            ---  Check: no. of scheduled processes are greater than one.
            if simblk.cur = simblk.sqs then ERROR(ENO_SML_2) endif;
%-X         smltim:=simblk.cur.rnk;
            psc:=RESUMX(simblk.cur,psc);
            goto E;
       elsif ins <> none then RANK_OUT(ins) endif;
%-X    if bio.trc
%-X    then bio.obsEvt:=EVT_CANC; bio.smbP1:=ins; observ endif;
E:end;


 Visible routine WAIT;
 import ref(simltn) simblk; ref(linkag) set; exit label psc;
 begin ---  Insert the first process in the given two-way list.
       intoSS(simblk.cur,set);
       ---  Here follows the inline code for a call on PASSIVATE.
       ---  Remove the first element from the sequencing set.
       RANK_OUT(simblk.cur);
%-X    if bio.trc
%-X    then bio.obsEvt:=EVT_WAIT; bio.smbP1:=set; observ endif;
       simblk.cur:=SQS_FIRST() qua proces;
       ---  Check that the no. of scheduled processes are greater than one.
       if simblk.cur = simblk.sqs then ERROR(ENO_SML_2) endif;
%-X    smltim:=simblk.cur.rnk;
       psc:=RESUMX(simblk.cur,psc);
 end;


 Visible routine passiv;
 import ref(simltn) simblk; exit label psc;
 begin ref(proces) x;
       ---  Remove the first element from the sequencing set.
       x:=simblk.cur; RANK_OUT(x);
%-X    if bio.trc
%-X    then bio.obsEvt:=EVT_PASS; bio.smbP1:=x; observ endif;
       simblk.cur:=SQS_FIRST() qua proces;
       ---  Check that the no. of scheduled processes are greater than one.
       if simblk.cur = simblk.sqs then ERROR(ENO_SML_2) endif;
%-X    smltim:=simblk.cur.rnk;
       psc:=RESUMX(simblk.cur,psc);
 end;


 Visible routine HOLD;
 import ref(simltn) simblk; long real time; exit label psc;
 begin ref(rankin) suc; ref(proces) x; x:=simblk.cur;
       if time>0.0&&0
       then time:=x.rnk:=x.rnk+time else time:=x.rnk endif;
       suc:=RANK_SUC(x);
       if suc <> none
       then if suc.rnk <= time
            then RANK_INTO(x,simblk.sqs,time);
%-X              if bio.trc
%-X              then bio.obsEvt:=EVT_HOLD; bio.smbP1:=x; observ endif;
                 simblk.cur:=suc;
%-X              smltim:=suc.rnk;
                 psc:=RESUMX(simblk.cur,psc);
%-X              goto E;
            endif;
       endif;
%-X    if bio.trc then bio.obsEvt:=EVT_HOLD; bio.smbP1:=x; observ endif;
%-X    smltim:=time;  E:
 end;
%page

% Visible routine OLD_EVTIME;
% import ref(proces) prcs; export long real time;
% begin if prcs.bl = none then ERROR(ENO_PRC_1) endif;
%       time:=prcs.rnk;
% end;

 Visible routine EVTIME;
 import ref(simltn) sim; export long real time;
 begin ref(proces) prcs;
 	   prcs:=sim.cur;
 	   if prcs.bl = none then ERROR(ENO_PRC_1) endif;
% 	   DMPENT(prcs);
% 	   DMPOOL(1);
       time:=prcs.rnk;
 end;


 Visible routine IDLE;
 import ref(proces) prcs; export boolean val;
 begin val:=prcs.bl = none; end;


 Visible routine term_d;
 import ref(proces) prcs; export boolean val;
 begin val:=prcs.sort = S_TRM; end;


 Visible routine NEXTEV;
 import ref(proces) ins; export ref(proces) suc;
 begin suc:=RANK_SUC(ins); end;


 Visible routine ACCUM;
 import ref(simltn) simblk; name(long real) a,b,c; long real d;
 begin long real time; time:=simblk.cur.rnk;
       var(a):=var(a)+(var(c)*(time-var(b)));
       var(b):=time; var(c):=var(c)+d;
 end;

%title ******   SIMOB tracing   ******

body (PobSML) SML_SIMOB;
 -- import short integer code; ref(inst) ins;
 -- export ref(inst) result;
begin ref(proces) prc; ref(simltn) simblk;

%-X if code >= 0 then
%-X   ---------------  former MNTR-routine SML_EVENT  -----------------
%-X   ED_OUT; ED_STR("Time"); ED_LRL(smltim qua long real,2);
%-X   ED_STR(": ");
%-X   case 0:EVT_max (code)
%-X   when EVT_PASS: ED_STR("passivate ");
%-X   when EVT_CANC: ED_STR("cancel ");
%-X   when EVT_WAIT: ED_eID(ins qua linkag.prd);
%-X                  ED_STR("into Wait-queue: ");
%-X   when EVT_HOLD: ED_STR("hold until time");                goto R1;
%-X   when EVT_ACTI: ED_STR("activate ");                      goto T1;
%-X   when EVT_ACT1: PRT("activate none");                     goto E1;
%-X   when EVT_ACT2: ED_STR("activate terminated ");
%-X   when EVT_ACT3: ED_STR("activate scheduled ");
%-X   when EVT_RACT: ED_STR("reactivate ");                    goto T2;
%-X   when EVT_RAC1: PRT("reactivate none");                   goto E2;
%-X   when EVT_RAC2: ED_STR("reactivate terminated ");
%-X   when EVT_RAC3: PRT("reactivate before/after itself");    goto E3;
%-X   when EVT_RAC4: PRT("reactivate current=");               goto T3;
%-X   endcase;
%-X   ED_eID(ins); ED_OUT; goto E4;
%-X   T1:T2:T3: ED_eID(ins); ED_STR("at time ");
%-X   R1: ED_LRL(ins qua proces.rnk,2); ED_OUT;
%-X
%-X elsif code = -1 then
%-X ------  former MNTR-routine DISPLAY_SIMBLK (1)  -------
%-X   repeat while ins <> ref(bio)
%-X   do if ins.sort = S_PRE
%-X      then if ins.pp qua claptp.prefix(1) = ref(SMLPTP)
%-X           then result:=ins; goto F endif;
%-X      endif;
%-X      ins:=ins.sl;
%-X   endrepeat;
%-X
%-X elsif code = -2 then
%-X ------  former MNTR-routine DISPLAY_SIMBLK (2)  -------
%-X   simblk:=ins;
%-X   if simblk.sqs=none then prc:=none
%-X   else prc:=simblk.sqs.bl endif; -- I.e. 'old' current
%-X   if prc=simblk.sqs then PRT("SEQUENCING SET IS EMPTY"); goto E5;
%-X   else PRT("*** SEQUENCING SET:") endif;
%-X   repeat while prc <> none
%-X   do ED_STR("Time "); ED_LRL(prc.rnk,2);
%-X      ED_STR("  -- "); ED_eID(prc); ED_OUT;
%-X      prc:=NEXTEV(prc);
%-X   endrepeat;
%-X
%-X elsif code = -3 then
%-X ------  former MNTR-call direct                 -------
%-X   result:=NEXTEV(ins);
%-X
%-X endif;
%-X E1:E2:E3:E4: -- exits from code>=0
%-X F:           -- exits from code -1
%-X E5:          -- exits from code -2
end;

%title ******   P  R  O  C  E  S  S   ******

          ---   Class PROCESS at prefix level 3.
 PCSSTM:  ---   Statements before inner.
          DETACH(curins);
          nxtStm(4);

%visible  -------------------------------------

 PCSINR:  ---   Statements after inner.
          curins.sort:=S_TRM;
    ---   curins.dl:=none;  --- Removed 15/8-84. Used by passivate.
          passiv(curins.sl);
          IERR_R("PCSINR");

%hidden   -------------------------------------

---       ******   M  A  I  N  P  R  O  G  R  A  M   ******

          ---   Class MAINPROG at prefix level 4.
 MPRDCL:  ---   Declaration code.
 MPRSTM:  ---   Statements before inner.
          repeat  while true do  DETACH(curins)  endrepeat;
 MPRINR:  ---  Statements after inner.
          IERR_R("MPRINR");


%title ******   S  I  M  U  L  A  T  I  O  N   ******

            ---  The class SIMULATION at prefix level 1.

 SMLSTM:    ---   Before inner statement code.
            ---   Set SIMOB tracing routine entry
%-X         if GINTIN(19)>0 then obSML:=entry(SML_SIMOB) endif;
            ---   Create head of SQS.
            B_CLA(curins,ref(rnkPtp));
            curins qua ref(simltn).sqs:=tmp.pnt;
            curins qua ref(simltn).sqs.bl :=
            curins qua ref(simltn).sqs.ll :=
            curins qua ref(simltn).sqs.rl :=
            curins qua ref(simltn).sqs;

            --- Create the Main Program object.
            B_CLA(curins,ref(MAINPTP));
            curins qua ref(simltn).main:=tmp.pnt;

            ---  Schedule main program as first event in SQS.
            RANK_INTO(curins qua ref(simltn).main,
                       curins qua ref(simltn).sqs,0.0&&0);
%-X         smltim:=0.0&&0;

            curins qua ref(simltn).cur :=
            curins qua ref(simltn).main;

            nxtStm(2);


end;
