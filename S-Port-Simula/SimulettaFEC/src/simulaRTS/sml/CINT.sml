Module cint("RTS");
 begin sysinsert rt,sysr,knwn,util,strg,cent; 

       ----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                 ---
       ---  Simula a.s.                                       ---
       ---  Oslo, Norway                                      ---
       ---                                                    ---
       ---           P O R T A B L E     S I M U L A          ---
       ---            R U N T I M E     S Y S T E M           ---
       ---                                                    ---
       ---                C - i n t e r f a c e               ---
       ---                                                    ---
       ----------------------------------------------------------

--external("c") malloc;
routine malloc;
import size sz; export ref() res;
begin
end;

record dumcha; begin character     elt(0) end;
record dumref; begin ref()         elt(0) end;
record dumtxt; begin infix(txtqnt) elt(0) end;

Visible routine txt2c;
import infix(txtqnt) txt; export ref() res;
--- copy a text to C-space with NUL termination
begin short integer nch,pos; ref(dumcha) str;
      pos:=txt.lp; nch:=txt.lp-txt.sp;
      str:=res:=malloc(size(dumcha:nch+1));
      if res <> none
      then str.elt(nch):='!0!';
           repeat while nch <> 0
           do nch:=nch-1; pos:=pos-1; str.elt(nch):=txt.ent.cha(pos); endrepeat;
      endif;
end;


Visible routine obj2c;
import ref(entity) obj; export ref() res;
--- copy any object to C-space, according to C conventions
--- text array: allocate 'array' in C-space, insert references to
---             copies of the contents of the text array elements
--- ref array:  allocate 'array' in C-space, insert references to
---             first attribute of the referred object (or none)
--- all others: compute allocation size as size(object)-size(head)
---             and copy the contents of that part of the object
begin size lng; ref() first,refo; ref(dumref) dref;
      name(infix(txtqnt)) dtxt;
      integer nelt;
      case 0:MAX_SORT (obj.sort)
      when S_ATT,S_DET,S_RES,S_TRM:
           lng:=none + obj.pp.lng - size(inst) - none;
           first:=obj+size(inst);
      when S_ARBODY:
           lng:=none + obj.lng-size(arbody) - none;
           first:=obj+size(arbody);
      when S_ARBREF:
           lng:=none + obj.lng-size(arbody) - none;
           dref:=obj + size(arbody);
           nelt:=obj qua ref(arbody).head.nelt;
    R1:R2: if (lng=nosize) or (nelt<=0) then goto DRA endif;
           res:=malloc(lng);
           if res = none then goto ER1 endif;
           repeat while nelt>0
           do nelt:=nelt-1; refo:=dref.elt(nelt);
              if refo<>none
              then res qua ref(dumref).elt(nelt):=refo+size(inst)
              else res qua ref(dumref).elt(nelt):=none endif
           endrepeat;
           goto E1;
      when S_ARREF2:
           lng:=none + obj.lng-size(arent2) - none;
           dref:=obj + size(arent2);
           nelt:=(obj qua ref(arent2).ub_2-obj qua ref(arent2).lb_2) *
                 (obj qua ref(arent2).ub_1-obj qua ref(arent2).lb_1)
           goto R2;
      when S_ARREF1:
           lng:=none + obj.lng-size(arent1) - none;
           dref:=obj +size(arent1);
           nelt:= obj qua ref(arent1).ub-obj qua ref(arent1).lb;
           goto R1;
      when S_ARBTXT:
           dtxt:=name(obj qua ref(txtarr).elt);
           nelt:=obj qua ref(arbody).head.nelt;
    T1:T2: if nelt<=0 then goto DTA endif;
           lng:=size(dumref:nelt);
           res:=malloc(lng);
           if res = none then goto ER2 endif;
           repeat while nelt>0 do nelt:=nelt-1;
                  res qua ref(dumref).elt(nelt):=txt2c(var(dtxt));
                  dtxt:=name(var(dtxt)(1));
           endrepeat;
           goto E2;
      when S_ARTXT2:
           dtxt:=name(obj qua ref(txtar2).elt);
           nelt:=(obj qua ref(arent2).ub_2-obj qua ref(arent2).lb_2) *
                 (obj qua ref(arent2).ub_1-obj qua ref(arent2).lb_1)
           goto T2;
      when S_ARTXT1:
           dtxt:=name(obj qua ref(txtar1).elt);
           nelt:= obj qua ref(arent1).ub-obj qua ref(arent1).lb;
           goto T1;
      when S_ARENT2:
           lng:=none + obj.lng-size(arent2) - none;
           first:=obj+size(arent2);
      when S_ARENT1:
           lng:=none + obj.lng-size(arent1) - none;
           first:=obj+size(arent1);
      endcase;
      if lng <> nosize
      then res:=malloc(lng);
           if res <> none then MOVEIN(first,res,lng) endif;
      else DRA:DTA: res:=none endif;
E1:E2:ER1:ER2:
end;


Visible routine c2tmpt;
import ref() cstr; export infix(txtqnt) res;
begin integer ncha;
      ncha:=0;
      repeat while cstr qua ref(dumcha).elt(ncha)<>'!0!'
      do ncha:=ncha+1 endrepeat;
      if ncha > MAX_TXT then ncha:=MAX_TXT endif;
      res.lp:=ncha+1; res.sp:=res.cp:=0;
      res.ent:=cstr-size(entity);
end;


Visible routine c2valt;
--- GC may occur (COPY cannot be used here)
import ref() cstr; export infix(txtqnt) res;
begin range(0:MAX_TXT) ncha; size lng; infix(string) to,from;
      ncha:=0;
      repeat while cstr qua ref(dumcha).elt(ncha)<>'!0!'
      do ncha:=ncha+1 endrepeat;
      if ncha=0 then res:=notext
      else if ncha > MAX_TXT then ncha:=MAX_TXT endif;
           res:=BLANKS(ncha);
           to.nchr:=from.nchr:=ncha;
           to.chradr:=name(res.ent.cha); from.chradr:=cstr+nofield;
           C_MOVE(from,to);
      endif
end;


-- Visible routine txtN2c;
-- import name(infix(txtqnt)) txt; export name() res;
-- begin if var(txt).ent = none then res:=noname  -- notext
--       else res:=name(var(txt).ent.cha(var(txt).sp)) endif;
-- end;


Visible routine objN2c;
import name(ref(entity)) obj; export name() res;
begin size lng;
      res:=noname;
      if var(obj) <> none
      then case 0:MAX_SORT (var(obj).sort)
           when S_ATT,S_DET,S_RES,S_TRM:
                lng:=var(obj).pp.lng;
                if lng <> size(inst)
                then res:=name(var(obj) qua ref(nonObj).cha) endif
           when S_ARBODY,S_ARBTXT,S_ARBREF:
                case 0:MAX_TYPE (var(obj).misc)
                when T_BOO: res:=name(var(obj) qua ref(booArr).elt)
                when T_CHA: res:=name(var(obj) qua ref(chaArr).elt)
                when T_SIN: res:=name(var(obj) qua ref(sinArr).elt)
                when T_INT: res:=name(var(obj) qua ref(intArr).elt)
                when T_REA: res:=name(var(obj) qua ref(reaArr).elt)
                when T_LRL: res:=name(var(obj) qua ref(lrlArr).elt)
                when T_REF: res:=name(var(obj) qua ref(refArr).elt)
                when T_TXT: res:=name(var(obj) qua ref(txtArr).elt)
                when T_PTR: res:=name(var(obj) qua ref(ptrArr).elt)
                endcase;
           when S_ARENT1,S_ARTXT1,S_ARREF1:
                case 0:MAX_TYPE (var(obj).misc)
                when T_BOO: res:=name(var(obj) qua ref(booAr1).elt)
                when T_CHA: res:=name(var(obj) qua ref(chaAr1).elt)
                when T_SIN: res:=name(var(obj) qua ref(sinAr1).elt)
                when T_INT: res:=name(var(obj) qua ref(intAr1).elt)
                when T_REA: res:=name(var(obj) qua ref(reaAr1).elt)
                when T_LRL: res:=name(var(obj) qua ref(lrlAr1).elt)
                when T_REF: res:=name(var(obj) qua ref(refAr1).elt)
                when T_TXT: res:=name(var(obj) qua ref(txtAr1).elt)
                when T_PTR: res:=name(var(obj) qua ref(ptrAr1).elt)
                endcase;
           when S_ARENT2,S_ARTXT2,S_ARREF2:
                case 0:MAX_TYPE (var(obj).misc)
                when T_BOO: res:=name(var(obj) qua ref(booAr2).elt)
                when T_CHA: res:=name(var(obj) qua ref(chaAr2).elt)
                when T_SIN: res:=name(var(obj) qua ref(sinAr2).elt)
                when T_INT: res:=name(var(obj) qua ref(intAr2).elt)
                when T_REA: res:=name(var(obj) qua ref(reaAr2).elt)
                when T_LRL: res:=name(var(obj) qua ref(lrlAr2).elt)
                when T_REF: res:=name(var(obj) qua ref(refAr2).elt)
                when T_TXT: res:=name(var(obj) qua ref(txtAr2).elt)
                when T_PTR: res:=name(var(obj) qua ref(ptrAr2).elt)
                endcase;
           when S_TXTENT:
                res:=name(var(obj) qua ref(txtent).cha)
           when S_ALLOC :
                res:=name(var(obj) qua ref(nonObj).cha)
           endcase;
      endif
end;


 Visible routine A_CPRO;
 --- allocate "call-back" procedure object
 import ref(inst) sl; ref(proptp) ppp; export label start;
 begin  ref(inst) ins;     -- The one we will allocate now.
        ---  Allocate storage. If necessary, call the garbage collector.
        ALLOC(S_PRO,ins,ppp.lng,sl);
        ---  Fill in procedure attributes.
        ins.sl:=sl; ins.pp:=ppp; ins.dl:=curins;
        curins:=ins; start:=ppp.start;
%-X     if bio.trc then curins.lsc:=start; bio.obsEvt:=EVT_BEG;
%-X        call PSIMOB(smb) endif;
 end;


 Visible routine A_CVIR;
 import ref(inst) sl;
        range(0:MAX_VIR) vir_ind;   -- Index in virtual vector.
 export label start;
 begin ref(inst)ins;           -- The one we will allocate now.
       ref(proptp) ppp;   -- The prototype for the virtual procedure.
       ---  Check that the virtual procedure has a match.
       ppp:=sl.pp qua ref(claptp).virts.vir(vir_ind).ppp;
       assert ppp <> none skip ERROR(ENO_VIR_1) endskip;
       ---  Check that it has no parameters  ---
       assert ppp.parVec = none skip ERROR(ENO_PRO_1) endskip;
       start:=A_CPRO(sl,ppp);
 end;


end; -- cint
