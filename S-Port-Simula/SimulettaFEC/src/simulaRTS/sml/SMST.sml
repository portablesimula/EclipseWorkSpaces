 Module smst("RTS");
 begin sysinsert rt,sysr,knwn,util,cent;

       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---           P O R T A B L E     S I M U L A           ---
       ---            R U N T I M E     S Y S T E M            ---
       ---                                                     ---
       ---               C l a s s    S I M S E T              ---
       ---                                                     ---
       -----------------------------------------------------------



--------   M O D U L E    I N F O   ---------

const infix(modinf) SMSMOD=record:modinf
%-X   (modIdt=ref(SMSIDT),obsLvl=2);
%+X   (modIdt=none       ,obsLvl=0);

%-X DEFINE_IDENT(%SMSIDT%,%6%,%('S','I','M','S','E','T')%);

---------   C l a s s    S I M S E T   ---------

%tag (smsRec,smsPtp)

 Visible record smsRec:inst; begin end;

 Visible const infix(claptp:2) smsPtp=record:claptp
       (plv=0,lng=size(inst),refVec=none,
%-X     xpp=ref(SMSXPP),
        dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(smsPtp),none));

%-X const infix(ptpExt) SMSXPP=record:ptpExt
%-X    (idt=ref(SMSIDT),modulI=ref(SMSMOD),attrV=none,blkTyp=BLK_CLA);

% UT const infix(blkvec:3) SMSVEC=record:blkvec
% UT   (nblk=3,blk=(record:blkdes(pp=ref(lkaPtp)),
% UT                record:blkdes(pp=ref(lnkPtp)),
% UT                record:blkdes(pp=ref(hedPtp)) ));

---------   C l a s s    L I N K A G E   ---------

%tag (linkag,lkaPtp)

Visible record linkag1:inst;
begin ref(linkag) prd,suc end;

Visible record linkag:linkag1; begin end;

Visible const infix(claptp:2) lkaPtp=record:claptp
       (plv=0,lng=size(linkag),refVec=ref(LKAPNT),
%-X     xpp=ref(LKAXPP),
        dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(lkaPtp),none));

const infix(pntvec:2) LKAPNT=record:pntvec
       ( npnt=2,pnt=( field(linkag.prd),field(linkag.suc)));

%-X const infix(ptpExt) LKAXPP=record:ptpExt
%-X    (idt=ref(LKAIDT),modulI=ref(SMSMOD),
%-X     attrV=ref(LKAATR),blkTyp=BLK_CLA);

%-X const infix(atrvec:2) LKAATR=record:atrvec
%-X    (natr=2,atr=(ref(LA_SUC),ref(LA_PRD)));

%-X SIMPLE_ATTR(%LA_SUC%,%ID_SUC%,%linkag.suc%,%T_REF%);
%-X SIMPLE_ATTR(%LA_PRD%,%ID_PRD%,%linkag.prd%,%T_REF%);

%-X DEFINE_IDENT(%LKAIDT%,%7%,%('L','I','N','K','A','G','E')%);
%-X DEFINE_IDENT(%ID_SUC%,%3%,%('S','U','C')%);
%-X DEFINE_IDENT(%ID_PRD%,%4%,%('P','R','E','D')%);

---------   C l a s s    L I N K   ---------

%tag (lnkRec,lnkPtp)

 Visible record lnkRec:linkag; begin end;

 Visible const infix(claptp:3) lnkPtp=record:claptp
       (plv=1,lng=size(linkag),refVec=none,
%-X     xpp=ref(LNKXPP),
        dcl=nowhere,stm=nowhere,cntInr=E_OBJ,
        prefix=(ref(lkaPtp),ref(lnkPtp),none));

%-X const infix(ptpExt) LNKXPP=record:ptpExt
%-X    (idt=ref(LNKIDT),modulI=ref(SMSMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%LNKIDT%,%4%,%('L','I','N','K')%);

---------   C l a s s    H E A D   ---------

%tag (hedRec,hedPtp)

 Visible record hedRec:linkag; begin end;

 Visible const infix(claptp:3) hedPtp=record:claptp
       (plv=1,lng=size(linkag),refVec=none,
%-X     xpp=ref(HEDXPP),
        dcl=nowhere,stm=HEDSTM,cntInr=E_OBJ,
        prefix=(ref(lkaPtp),ref(hedPtp),none));

%-X const infix(ptpExt) HEDXPP=record:ptpExt
%-X    (idt=ref(HEDIDT),modulI=ref(SMSMOD),attrV=none,blkTyp=BLK_CLA);

%-X DEFINE_IDENT(%HEDIDT%,%4%,%('H','E','A','D')%);
%page
 Visible routine follow; import ref(linkag) ins,prd;
 begin outSS(ins);
       if prd <> none
       then if prd.suc <> none
            then ins.prd:=prd; ins.suc:=prd.suc;
                 prd.suc:=prd.suc.prd:=ins;
            endif;
       endif;
 end;

 Visible routine intoSS; import ref(linkag) ins,head;
 begin outSS(ins);
       if head <> none
       then ins.suc:=head; ins.prd:=head.prd;
            head.prd:=head.prd.suc:=ins;
       endif;
 end;

 Visible routine outSS; import ref(linkag) ins;
 begin if ins.suc <> none
       then ins.prd.suc:=ins.suc; ins.suc.prd:=ins.prd;
            ins.suc:=ins.prd:=none;
       endif;
 end;

 Visible routine precSS; import ref(linkag) ins,suc;
 begin outSS(ins);
       if suc <> none
       then if suc.prd <> none
            then ins.suc:=suc; ins.prd:=suc.prd;
                 suc.prd:=suc.prd.suc:=ins;
            endif;
       endif;
 end;

 Visible routine predSS;
 import ref(linkag) ins; export ref(linkag) prd;
 begin prd:=ins.prd;
       if prd <> none
       then if prd.pp qua ref(claptp).prefix(1) <> ref(lnkPtp)
            then prd:=none endif;
       endif;
 end;

 Visible routine prevSS;
 import ref(linkag) ins; export ref(linkag) prev;
 begin prev:=ins.prd; end;

 Visible routine sucSS;
 import ref(linkag) ins; export ref(linkag) suc;
 begin suc:=ins.suc;
       if suc <> none
       then if suc.pp qua ref(claptp).prefix(1) <> ref(lnkPtp)
            then suc:=none endif;
       endif;
 end;

 Visible routine cardSS;
 import ref(linkag) head; export integer i;
 begin ref(linkag) ins; i:=0; ins:=head;
       repeat ins:=ins.suc while ins <> head do i:=i+1 endrepeat;
 end;

 Visible routine cleaSS; import ref(linkag) head;
 begin ref(linkag) ins,temp;
       ins:=head.suc; head.suc:=head.prd:=none;
       repeat while ins <> none
       do temp:=ins.suc; ins.suc:=ins.prd:=none; ins:=temp endrepeat;
       head.suc:=head.prd:=head;
 end;

 Visible routine emptSS;
 import ref(linkag) head; export boolean empty;
 begin empty:= head.suc=head; end;

 ------------------------------------------------
            ---   Class HEAD on prefix level one.
 HEDSTM:    ---   Before inner statement code.
            curins qua linkag.suc:=curins;
            curins qua linkag.prd:=curins;
            nxtStm(2);
 ------------------------------------------------

end;
