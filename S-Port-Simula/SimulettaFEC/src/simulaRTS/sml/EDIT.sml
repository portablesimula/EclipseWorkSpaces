Module edit("RTS");
 begin sysinsert rt,sysr,knwn,util,strg,cent,fil

       -----------------------------------------------------------------
       ---  COPYRIGHT 1989 by                                        ---
       ---  Simula a.s.                                              ---
       ---  Oslo, Norway                                             ---
       ---                                                           ---
       ---                 P O R T A B L E     S I M U L A           ---
       ---                  R U N T I M E     S Y S T E M            ---
       ---                                                           ---
       ---                    E d i t  -  D e e d i t                ---
       ---                                                           ---
       -----------------------------------------------------------------

 Visible routine A_PRE;
 import ref(claptp) cpp;
 export ref(inst) ins;     -- The one we will allocate now.
 begin ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC2(S_PRE,ins,cpp.lng);
       ---  Fill in prefixed block attributes.
       ins.sl:=curins; ins.pp:=cpp;
 end;

 Visible routine I_PRE;
 import ref(inst) ins;      -- The prefixed block instance.
 exit label psc;
 begin range(0:255) plev; ref(claptp) cpp; -- used by FIND_macros
       ---  Set the program point where current instance will continue.
       cpp:=ins.pp qua ref(claptp); curins.lsc:=cpp.cntInr;
       ---  Link to dynamic enclosure.
       ins.dl:=curins;
       ---  Enter prefixed block instance's code, i.e. start execution
       ---  of the declaration code for the class on prefix level zero.
       curins:=ins;
       plev:=0; FIND_DCL();    --  find dcl-code
       plev:=0; FIND_STM();    --   - or statements before/after inner
 DFOUND: SFOUND:
%-X    if bio.trc then curins.lsc:=psc; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
 end;

 Visible routine B_VIRT;
 import ref(inst) sl;
        range(0:MAX_VIR) vir_ind;        -- Index in virtual vector.
 exit label psc;
 begin ref(inst) ins;  -- The one we will allocate now.
       ref(proptp) ppp;   -- The prototype for the virtual procedure.
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       ---  Check that the virtual procedure has a match.
       ppp:=sl.pp qua ref(claptp).virts.vir(vir_ind).ppp;
       if ppp = none then  ERROR(ENO_VIR_1)  endif;
       ---  Check that the number of parameters are zero.
       if ppp.parVec <> none then  ERROR(ENO_PRO_1)  endif;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_PRO,ins,ppp.lng,sl);
       ---  Fill in procedure attributes.
       ins.sl:=sl; ins.pp:=ppp; ins.dl:=curins;
       ---  Enter procedure instance's code.
       curins:=ins; psc:=ppp.start;
%-X    if bio.trc then curins.lsc:=psc; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
 end;

 Visible routine A_VIRT;
 import ref(inst) sl;
        range(0:MAX_VIR) vir_ind;   -- Index in virtual vector.
        range(0:MAX_ATR) npar;      -- Number of parameters.
 export ref(inst)ins;           -- The one we will allocate now.
 begin ref(proptp) ppp;   -- The prototype for the virtual procedure.
       ---  Check that the virtual procedure has a match.
       ppp:=sl.pp qua ref(claptp).virts.vir(vir_ind).ppp;
       assert ppp <> none skip ERROR(ENO_VIR_1) endskip;
       ---  Check that the number of parameters are correct.
       if ppp.parVec = none then ERROR(ENO_PRO_1);
       elsif npar <> ppp.parVec.natr then ERROR(ENO_PRO_1) endif;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_PRO,ins,ppp.lng,sl);
       ---  Fill in procedure attributes.
       ins.sl:=sl; ins.pp:=ppp;
%      ED_STR("EDIT.A_VIRT: ins="); ED_OADDR(ins); ED_OUT;
% 	   DMPENT(ins);
% 	   DMPOOL(1);
 end;


 Visible routine MAIN;
 import infix(txtqnt) txt; export infix(txtqnt) res;
 begin res.ent:=txt.ent; res.sp:=0; res.cp:=0;
       if txt.ent=none then res.lp:=0   --  notext
       else res.lp:=txt.ent.ncha endif;
 end;


 Visible routine TXTCNS;
 import infix(txtqnt) txt; export boolean cns;
 begin
 	 cns:=if txt.ent=none then true else (txt.ent.misc<>0)
 end;


 Visible routine TXTMIN;
 import infix(txtqnt) left, right; export infix(txtqnt) res;
 begin if TXTREL(left,right,3) then res:=left else res:=right endif end;

 Visible routine TXTMAX;
 import infix(txtqnt) left, right; export infix(txtqnt) res;
 begin if TXTREL(left,right,6) then res:=left else res:=right endif end;

 Visible routine txtAss;
 import name(infix(txtqnt)) dst; infix(txtqnt) src;
 begin infix(string) dst_str,src_str;
       src_str.nchr:=src.lp - src.sp;
       dst_str.nchr:=var(dst).lp - var(dst).sp;
% +M	   ED_STR("EDIT.TXTASS: dst_str.nchr="); ED_INT(dst_str.nchr); ED_OUT;
% +M	   ED_STR("EDIT.TXTASS: src_str.nchr="); ED_INT(src_str.nchr); ED_OUT;
       assert dst_str.nchr >= src_str.nchr skip ERROR(ENO_TXT_2) endskip
       ---   var(dst) = notext  ===>  src = notext
       if var(dst).ent<>none -- notext?
       then assert var(dst).ent.misc = 0 skip ERROR(ENO_TXT_3) endskip;
            dst_str.chradr:=name(var(dst).ent.cha(var(dst).sp));
            src_str.chradr:=if src_str.nchr > 0
            then name(src.ent.cha(src.sp)) else noname;
            ---  Transfer all the characters in the source.
            ---  Blankfill any remaining characters of the destination.
            C_MOVE(src_str,dst_str);
       endif;
 end;


 Visible routine setPoT; --- THIS IS SUPERFLUOUS, inline code POP!!
 import infix(txtqnt) adr; integer pos;
 begin   end;


 Visible routine subTmp;
 import infix(txtqnt) txt; integer m,n;
 export infix(txtqnt) res;
 begin if m < 1 then ERROR(ENO_TXT_4);
       elsif n < 0 then ERROR(ENO_TXT_5);
       elsif n = 0 then res:=notext;
       else res.ent:=txt.ent; res.sp:=txt.sp + m - 1;
            res.lp:=res.sp + n; res.cp:=res.sp;
            if res.lp > txt.lp then ERROR(ENO_TXT_6) endif;
       endif;
 end;

%page

 Visible routine INTEXT;
 import ref(filent) fil; integer ncha; export infix(txtqnt) res;
 begin infix(txtqnt) img; size lng;
       if ncha > 0
       then if ncha > MAX_TXT then  ERROR1(ENO_FIL_12,fil)  endif;
            NEW_TXT(fil);  --- Create Text Object
            img:=fil.img;    --- Fill in Text Object Value ---
            repeat while res.cp < res.lp
            do if img.cp >= img.lp then INIMAG(fil); img:=fil.img endif;
               res.ent.cha(res.cp):=img.ent.cha(img.cp);
               res.cp:=res.cp + 1; img.cp:=img.cp + 1;
            endrepeat;
            res.cp:=0; fil.img.cp:=img.cp;
       elsif ncha = 0
       then res:=notext;
            ---  Check that the save entity invariant still holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       else ERROR1(ENO_FIL_12,fil) endif;
%-X    if bio.trc then bio.obsEvt:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;
 end;

 Macro IN_ITEM(1);
 --  First parameter is the name of the function (e.g. GETINT).
 begin infix(string) item; infix(txtqnt) img;
       if LAST(fil) then  ERROR1(ENO_FIL_8,fil)  endif;
       img:=fil.img; item.nchr:=img.lp - img.cp;
       item.chradr:=name(img.ent.cha(img.cp));
       res:=%1 (item); if status <> 0 then FILerr(fil,true,true) endif;
       fil.img.cp:=img.cp + itsize;
 endmacro;

 Visible routine ININT;
 import ref(filent) fil; export integer res;
 begin IN_ITEM(GETINT) end;

 Visible routine INREAL;
 import ref(filent) fil; export long real res;
 begin IN_ITEM(GTREAL) end;

 Visible routine INFRAC;
 import ref(filent) fil; export integer res;
 begin IN_ITEM(GTFRAC) end;

 Visible routine OUTFIX;
 import ref(filent) fil; real val; integer n,w;
 begin OUT_ITEM(PUTFIX,%,n%) end;

 Visible routine OUTLFX;
 import ref(filent) fil; long real val; integer n,w;
 begin OUT_ITEM(PTLFIX,%,n%) end;

 Visible routine  OUTREA;
 import ref(filent) fil; real val; integer n,w;
 begin OUT_ITEM(PTREAL,%,n%) end;

 Visible routine OUTLRL;
 import ref(filent) fil; long real val; integer n,w;
 begin OUT_ITEM(PLREAL,%,n%) end;

 Visible routine OUTFRC;
 import ref(filent) fil; integer val,n,w;
 begin OUT_ITEM(PTFRAC,%,n%) end;
%title *****   GET/PUT  -  for real/lreal/frac   *****

 Visible routine gtchaT;
 import infix(txtqnt) txt; export character c;
 begin if txt.cp = txt.lp then ERROR(ENO_TXT_11) endif;
       c:=txt.ent.cha(txt.cp);
 end;

 Visible routine ptchaT;
 import infix(txtqnt) txt; character c;
 begin assert txt.cp < txt.lp skip ERROR(ENO_TXT_15) endskip;
       assert txt.ent.misc = 0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       txt.ent.cha(txt.cp):=c;
 end;

 Visible routine gtreaA;
 import name(infix(txtqnt)) adr; export long real res;
 begin GET_ITEM_ATR(GTREAL) end;

 Visible routine gtreaT;
 import infix(txtqnt) txt; export long real res;
 begin GET_ITEM_TMP(GTREAL) end;


 Visible routine gtfraA;
 import name(infix(txtqnt)) adr; export integer res;
 begin GET_ITEM_ATR(GTFRAC) end;

 Visible routine gtfraT;
 import infix(txtqnt) txt; export integer res;
 begin GET_ITEM_TMP(GTFRAC) end;


 Visible routine ptreaA;
 import name(infix(txtqnt)) adr; real val; integer n;
 begin PUT_ITEM_ATR(PTREAL,%,n%) end;

 Visible routine ptreaT;
 import infix(txtqnt) txt; real val; integer n;
 begin PUT_ITEM_TMP(PTREAL,%,n%) end;


 Visible routine ptlrlA;
 import name(infix(txtqnt)) adr; long real val; integer n;
 begin PUT_ITEM_ATR(PLREAL,%,n%) end;

 Visible routine ptlrlT;
 import infix(txtqnt) txt; long real val; integer n;
 begin PUT_ITEM_TMP(PLREAL,%,n%) end;


 Visible routine ptfixA;
 import name(infix(txtqnt)) adr; real val; integer n;
 begin PUT_ITEM_ATR(PUTFIX,%,n%) end;

 Visible routine ptfixT;
 import infix(txtqnt) txt; real val; integer n;
 begin PUT_ITEM_TMP(PUTFIX,%,n%) end;


 Visible routine ptlfxA;
 import name(infix(txtqnt)) adr; long real val; integer n;
 begin PUT_ITEM_ATR(PTLFIX,%,n%) end;

 Visible routine ptlfxT;
 import infix(txtqnt) txt; long real val; integer n;
 begin PUT_ITEM_TMP(PTLFIX,%,n%) end;


 Visible routine ptfraA;
 import name(infix(txtqnt)) adr; integer val,n;
 begin PUT_ITEM_ATR(PTFRAC,%,n%) end;

 Visible routine ptfraT;
 import infix(txtqnt) txt; integer val,n;
 begin PUT_ITEM_TMP(PTFRAC,%,n%) end;

%title ***   CHKPNT - LOCK - UNLOCK - LAST   ***
 Visible routine CHKPNT;
 import ref(filent) fil; export boolean res;
 begin CHECKP(fil.key); res:= status=0; status:=0; end;

 Visible routine LOCK;
 import ref(filent) fil; real lim; integer loc1,loc2;
 export integer res;
 begin res := -1;
       if lim > 0.0
       then if fil.locked then UNLOCK(fil) endif;
            res:=LOCKFI(fil.key,lim,loc1,loc2);
            if status<>0 then res:=-(status+1); status:=0; -- pje jan 87
            elsif res = 0 then fil.locked := true endif;
       endif;
 end;

 Visible routine UNLOCK;
 import ref(filent) fil; export boolean res;
 begin res := CHKPNT(fil);
       if fil.locked
       then UPLOCK(fil.key); fil.locked:=false;
         -- if status <> 0 then FILerr(fil,true,true) endif;
            status:=0; -- pje june 89
       endif;
 end;

 Visible routine LAST; -- lastitem
 import ref(filent) fil; export boolean res;
 begin infix(txtqnt) img; character ch;
       repeat while not fil.eof
       do img:=fil.img;
          repeat while img.cp < img.lp
          do ch:=img.ent.cha(img.cp)
             if (ch <> ' ') and (ch qua integer <> 9) -- SP and HT
             then fil.img:=img; res:=false; goto E endif;
             img.cp:=img.cp + 1;
          endrepeat;
          INIMAG(fil);
       endrepeat;
       res:=true;
 E:end


 Visible routine DELIM;
 import ref(filent) fil; export boolean res;
 begin res:=DELETE(fil.key);
       if status <> 0 then status:=0; res:=false endif;
       if res then fil.loc:=fil.loc+1 endif;
 end;

 Visible routine SPACIN;
 import ref(prtEnt) pfil; integer spc;
 begin if -- pje june 89 (spc<0) or
          (spc>pfil.lpp) then ERROR1(ENO_FIL_19,pfil) endif;
       pfil.spc:=spc;
 end;


 Visible routine MAXLOC;  -- pje jan 87
 import ref(filent) fil; export integer mloc;
 begin mloc:=MXLOC(fil.key);
       if status <> 0 then status:=0; mloc:=maxint - 1 endif;
 end;


 Visible routine OUTREC; import ref(filent) fil;
 begin infix(txtqnt) img;   --  Local copy here for efficiency.
       infix(string) str;   --  TXT_TO_STR(img).
       ref(prtEnt) pfil;

%-X    if bio.trc then bio.obsEvt:=EVT_ouim; bio.smbP1:=fil;
%-X       observ endif;
       img:=fil.img;
       if img.ent = none then ERROR1(ENO_FIL_13,fil) endif;
       str.nchr:=img.cp-img.sp; str.chradr:=name(img.ent.cha(img.sp));

       if fil.type = FIL_PRT
       then pfil:=fil qua ref(prtEnt);
            if pfil.lin > pfil.lpp then EJECT(pfil,1) endif;
            PRINTO(pfil.key,str,pfil.spc);
            if status <> 0 then FILerr(pfil,true,true) endif;
            pfil.lin:=pfil.lin + pfil.spc;
       else fUTIMA(fil.key,str);
            if status <> 0 then FILerr(fil,true,true) endif;
       endif;
       fil.img.cp:=img.sp;
 end;


 Visible routine BRKOUT; import ref(filent) fil;
 begin infix(txtqnt) img;   --  Local copy here for efficiency.
       infix(string) str;   --  TXT_TO_STR(img).

%-X    if bio.trc then bio.obsEvt:=EVT_ouim; bio.smbP1:=fil;
%-X       observ endif;
       img:=fil.img;
       if img.ent = none then ERROR1(ENO_FIL_13,fil) endif;
       str.nchr:=img.cp-img.sp; str.chradr:=name(img.ent.cha(img.sp));
       BREAKO(fil.key,str);
       if status <> 0 then FILerr(fil,true,true) endif;
       str.nchr:=img.lp-img.sp; C_BLNK(str); fil.img.cp:=img.sp;
 end;


 Visible routine ISOPEN;
 import ref(filent) fil; export boolean res;
 begin
%   DMPENT(fil); 
  res:= fil.key <> 0 end;


 Visible routine LOWTEN; import character c; export character res;
 begin LTEN(c); res:=bio.lwten;
       if status <> 0 then status:=0; ERROR(ENO_LTEN);
       else bio.lwten:=c endif;
 end;


 Visible routine DCMARK; import character c; export character res;
 begin DECMRK(c); res:=bio.dcmrk;
       if status <> 0 then ERROR(ENO_DCMRK);
       else bio.dcmrk:=c endif;
 end;

 Visible routine RTutil; import integer code,val; exit label psc;
 begin ref(entity) nxt,dum;
       curins.lsc:=psc; bio.GCval:=val;
       if (code<0) or (code>4) then ERROR(ENO_RUTIL)
       else case 0:4 (code)
            when 0:
%-X                 bio.trc:=true;
            when 1:
%-X                 bio.obsEvt:=EVT_SNAP; bio.smbP1:=none; observ;
            when 2:
                    GCutil(1);                  -- freeze/unfreeze
            when 3:
%-X                 GCutil(5);                  -- reserve all but val
            when 4:
                    if    val=0 then GCutil(4)  -- garb coll
%-X                 elsif val=1 then GCutil(14) -- reverse areas
               --   elsif val=2 then
               --   elsif val=3 then
               --   elsif val=4 then
                    elsif val=5 then GCutil(2)  -- check storage
                    elsif val=6 then GCutil(15) -- print strg summary
                    elsif val=7 then GCutil(16) -- set/reset strg check
                    endif;
       endcase endif;
 end;

end;
