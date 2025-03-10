Module arr("RTS");
 begin sysinsert rt,sysr,knwn,util,strg; 

       ----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                 ---
       ---  Simula a.s.                                       ---
       ---  Oslo, Norway                                      ---
       ---                                                    ---
       ---           P O R T A B L E     S I M U L A          ---
       ---            R U N T I M E     S Y S T E M           ---
       ---                                                    ---
       ---                    A r r a y s                     ---
       ---                                                    ---
       ----------------------------------------------------------

 ---   NOTE: 1) dummy arrays get lb:=0, ub:=-1 
 ---         2) more than maxint-1 elements: converted to dummy

 Visible routine arGnew;
 import range(0:MAX_TYPE) type; range(1:MAX_DIM) ndim;
 integer bound(MAX_BND);
 export ref(arbody) bod;
 begin integer base,nelt;
       range(0:MAX_BND) i,j; range(0:MAX_SORT) sort;
       infix(arrbnd) bnd; ref(arhead) head; size lng;
       ---  Create a single array head.
       lng:=size(arhead:ndim);
       ALLOC2(S_ARHEAD,head,lng); head.lng:=lng;
       head.ndim:=ndim; head.misc:=type;
       --  Fill in lower and upper bounds,
       --  the dope vector and the negated base index.
       i:=ndim - 1; j:=2*i; nelt:=1; base:=0;
       repeat bnd.lb:=bound(j); bnd.ub:=bound(j+1);
              if bnd.lb<=0 then if bnd.ub>=(maxint+bnd.lb)
              then ERROR(ENO_ARR_1) endif endif;
              bnd.dope:=bnd.ub-bnd.lb+1;
              if bnd.dope<0 then bnd.dope:=0 endif;
              head.bound(i):=bnd;
              nelt:=nelt * bnd.dope; -- 0 for dummies
              base:=base * bnd.dope + bnd.lb;
       while i <> 0 do i:=i-1; j:=j-2 endrepeat;
       head.nelt:=nelt; head.bound(ndim-1).dope:= -base;

       ---   Create a single array body entity.
       sort:=S_ARBODY;
       ---  Compute the size of the array body entity.
       case 0:MAX_TYPE (type)
       when T_BOO: lng:=size(booArr:nelt);
       when T_CHA: lng:=size(chaArr:nelt);
       when T_SIN: lng:=size(sinArr:nelt);
       when T_INT: lng:=size(intArr:nelt);
       when T_REA: lng:=size(reaArr:nelt);
       when T_LRL: lng:=size(lrlArr:nelt);
       when T_REF: lng:=size(refArr:nelt); sort:=S_ARBREF;
       when T_PTR: lng:=size(ptrArr:nelt);
       when T_TXT: lng:=size(txtArr:nelt); sort:=S_ARBTXT;
       otherwise   IERR_R("arGnew")  endcase;
       ---  Allocate storage.
       ALLOC(sort,bod,lng,head); bod.lng:=lng;
       bod.head:=head; bod.misc:=type;
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=bod; observ endif
 end;


 Visible routine ar2new;
 import range(0:MAX_TYPE) type; integer lb1,ub1,lb2,ub2;
 export ref(arent2) arr;
 begin integer dope,nelt; range(0:MAX_SORT) sort; size lng;
       if lb1<=0
       then if ub1>=(maxint+lb1) then ERROR(ENO_ARR_1) endif; endif;
       if lb2<=0
       then if ub2>=(maxint+lb2) then ERROR(ENO_ARR_1) endif; endif;
       if (ub1<lb1) or (ub2<lb2) then nelt:=dope:=0;
       else dope:=ub1-lb1+1; nelt:=dope * (ub2-lb2+1) endif;
       sort:=S_ARENT2;
       ---  Compute the size of the array entity.
       case 0:MAX_TYPE (type)
       when T_BOO: lng:=size(booAr2:nelt);
       when T_CHA: lng:=size(chaAr2:nelt);
       when T_SIN: lng:=size(sinAr2:nelt);
       when T_INT: lng:=size(intAr2:nelt);
       when T_REA: lng:=size(reaAr2:nelt);
       when T_LRL: lng:=size(lrlAr2:nelt);
       when T_REF: lng:=size(refAr2:nelt); sort:=S_ARREF2;
       when T_PTR: lng:=size(ptrAr2:nelt);
       when T_TXT: lng:=size(txtAr2:nelt); sort:=S_ARTXT2;
       otherwise  IERR_R("ar2new")   endcase;
       ---  Allocate storage.
       ALLOC2(sort,arr,lng); arr.lng:=lng; arr.misc:=type;
       arr.lb_1:=lb1; arr.ub_1:=ub1;
       arr.dope:=dope; arr.lb_2:=lb2; arr.ub_2:=ub2;
       arr.negbas:=- (lb2 * dope + lb1);
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=arr; observ endif
 end;


 Visible routine arAcop;
 import field(ref(entity)) src;  --  Offset of source.
        field(ref(entity)) dst;  --  Offset of destination.
 ---    range(0:MAX_TYPE) typ;   --  Element type. 
 begin ref(entity) new,old; size lng;
       lng:=var(curins + src).lng;
       ALLOC2(S_GAP,new,lng);    --  sort code changed below
       old:=var(curins + src);
       ---   Find the size of the common properties part, and move it.
       case  0:MAX_SORT (old.sort)
       when  S_ARBODY,S_ARBREF,S_ARBTXT:  lng:=size(arbody);
       when  S_ARENT2,S_ARREF2,S_ARTXT2:  lng:=size(arent2);
       when  S_ARENT1,S_ARREF1,S_ARTXT1:  lng:=size(arent1);
       otherwise  IERR_R("arAcop")  endcase;
       MOVEIN(old,new,lng); var(curins + dst):=new;
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=new; observ endif
 end;


 Visible routine arrVal;
 import ref(inst) parins;    --  Parameter instance.
        field(ref(entity)) fld;  --  Offset of parameter.
 ---    range(0:MAX_TYPE) typ;   --  Element type. 
 export ref(inst) updated;   --  Parameter instance.
 begin ref(entity) new,old; size lng;
       lng:=var(parins + fld).lng;
       ALLOC(S_GAP,new,lng,parins); -- sort code changed below
       old:=var(parins + fld); MOVEIN(old,new,lng);
       var(parins + fld):=new; updated:=parins;
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=new; observ endif
 end;

 Visible routine arTcop; --- Copy the array referenced by TMP.ARR
 --- import range(0:MAX_TYPE) typ;     --  Element type. 
 begin ref(entity) new,old; size lng;
       old:=tmp.arr; lng:=old.lng;
       ALLOC(S_GAP,new,lng,old);  -- sort code changed below
       MOVEIN(old,new,lng);
       tmp.arr:=new;
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=new; observ endif
 end;

 Visible routine LOWBND; 
 import ref(entity) arr; integer i; export integer res;
 begin ref(arhead) head;
       case  0:MAX_SORT (arr.sort)
       when  S_ARBODY,S_ARBREF,S_ARBTXT: 
             head:=arr qua arbody.head;
             if (i>0) and (i<=head.ndim) then res:=head.bound(i-1).lb
             else ERROR(ENO_ARR_1);
             endif;
       when  S_ARENT2,S_ARREF2,S_ARTXT2:
             if    i=1 then res:=arr qua arent2.lb_1
             elsif i=2 then res:=arr qua arent2.lb_2;
             else ERROR(ENO_ARR_1);
             endif;
       when  S_ARENT1,S_ARREF1,S_ARTXT1:
             if    i=1 then res:=arr qua arent1.lb;
             else ERROR(ENO_ARR_1);
             endif;
       otherwise  IERR_R("LOWBND")  endcase;

 end;

 Visible routine UPPBND; 
 import ref(entity) arr; integer i; export integer res;
 begin ref(arhead) head;
       case  0:MAX_SORT (arr.sort)
       when  S_ARBODY,S_ARBREF,S_ARBTXT: 
             head:=arr qua arbody.head;
             if (i>0) and (i<=head.ndim) then res:=head.bound(i-1).ub;
             else ERROR(ENO_ARR_1);
             endif;
       when  S_ARENT2,S_ARREF2,S_ARTXT2:
             if    i=1 then res:=arr qua arent2.ub_1
             elsif i=2 then res:=arr qua arent2.ub_2
             else ERROR(ENO_ARR_1);
             endif;
       when  S_ARENT1,S_ARREF1,S_ARTXT1:
             if   i=1 then res:=arr qua arent1.ub;
             else ERROR(ENO_ARR_1);
             endif;
       otherwise  IERR_R("UPPBND")  endcase;
 end;

 Visible routine arrDim;  -- non-standard function (pje)
 import ref(entity) arr; integer i; export integer res;
 begin case  0:MAX_SORT (arr.sort)
       when  S_ARBODY,S_ARBREF,S_ARBTXT: 
             res:=arr qua arbody.head.ndim;
       when  S_ARENT2,S_ARREF2,S_ARTXT2:
             res:=2;
       when  S_ARENT1,S_ARREF1,S_ARTXT1:
             res:=1;
       otherwise  IERR_R("arrDim")  endcase;
 end;

end;
