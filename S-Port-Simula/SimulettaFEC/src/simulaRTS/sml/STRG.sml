Module strg("RTS");
 begin sysinsert rt,sysr,knwn,util;

       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---        P O R T A B L E     S I M U L A              ---
       ---         R U N T I M E     S Y S T E M               ---
       ---                                                     ---
       ---  S t o r a g e   M a n a g e m e n t   S y s t e m  ---
       ---                                                     ---
       ---  Selection Switches: M  Myhres debug dumps          ---
       ---                      C: storage checking incl.      ---
       ---                      D: debug/trace output          ---
       -----------------------------------------------------------

      ref() chain; --- PASS1 entity chain

       Visible macro ALLOC(4);
       begin %2:=bio.nxtAdr; bio.nxtAdr:= bio.nxtAdr + %3;
             if bio.nxtAdr > bio.lstAdr
             then %2:=GARB(%2,%3,name(%4)) endif;
             %2 .sort:= %1;
       endmacro;

       Visible macro ALLOC2(3);
       begin %2:=bio.nxtAdr; bio.nxtAdr:= bio.nxtAdr + %3;
             if bio.nxtAdr > bio.lstAdr
             then %2:=GARB(%2,%3,name(bioref)) endif;
             %2 .sort:= %1;
       endmacro;

       Visible macro RECLAIM(2);
       begin if %1 + %2 = bio.nxtAdr
             then ZEROAREA(%1,bio.nxtAdr); bio.nxtAdr:=%1
             endif;
       endmacro;

       Macro MARK_INSTANCE(1);
       begin
%+CD         ed_str("mark"); edSort( %1 );
%+CD         if %1 qua ref(inst).gcl<>none
%+CD         then ed_str(" already marked???") endif;
%+CD         if %1 .sort >= S_ALLOC
%+CD         then ed_str(" not object???") endif;
%+CD         ed_str(" chain"); edOaddr(chain); ed_out;
             %1 qua ref(inst).gcl:= chain; chain:=%1;
       endmacro;

       Macro MARK_obj_FOLLOW(1);
       begin z:= %1;
             if z <> none then if z qua ref(inst).gcl = none
             then MARK_INSTANCE(z) endif endif;
       endmacro;

       Macro MARK_AND_FOLLOW(1);
       -- parameter is always 'z', but macros must have param ???
       begin
             case 0:MAX_SORT (z.sort)
             when S_SUB,S_PRO,S_THK,S_ATT,S_DET,S_RES,S_TRM,S_PRE,S_SAV:
                  if z qua ref(inst).gcl=none
                  then MARK_INSTANCE(z) endif;
             when S_ARBREF,S_ARBTXT,S_ARREF1,S_ARTXT1,
                  S_ARREF2,S_ARTXT2,S_ARBODY:
                  if z.sl=none then
%+CD                 ed_str("mark"); edsort(z);
%+CD                 ed_str(" chain"); edOaddr(chain); ed_out;
                     z.sl:=chain; chain:=z endif;
             when S_TXTENT, -- no pointers (const marked in misc field)
                  S_ALLOC,S_ARHEAD,S_ARENT2,S_ARENT1: -- no pointers
                  --- NOTE the mark (z.sl=z) ensures that constant
                  ---      entities OUTSIDE DYNAMIC STORAGE, which are
                  ---      never moved, do not disturb UPDATE
                  if z.sl=none then
%+CD                 ed_str("mark"); edsort(z); ed_out;
                     z.sl:= z endif;
%+C          when S_GAP,S_NOSORT:
%+C               edSort(z); ed_out; IERR_R("STRG - illegal sort");
             endcase;
       endmacro;

       Macro UPDATE_E(1);
       begin if %1 <> none then
%+CD            ed_str("UPD"); edOaddr( %1 );
%+CD            if ( %1 .sort<S_ALLOC) or ( %1 .sort>MAX_SORT)
%+CD            then edSort( %1 ); ed_str("???") endif;
%+CD            ed_str("-->"); edOaddr( %1 . sl ); ed_out;
                                %1 := %1 . sl qua ref(entity) endif
       endmacro;

       Macro UPDATE_I(1);
       begin if %1 <> none then
%+CD            ed_str("UPD"); edOaddr( %1 );
%+CD            if ( %1 .sort=S_NOSORT) or ( %1 .sort>=S_ALLOC)
%+CD            then edSort( %1 ); ed_str("???") endif;
%+CD            ed_str("==>"); edOaddr( %1 . gcl ); ed_out;
                                %1 := %1 . gcl endif
       endmacro;

       Macro UPDATE(1);
       begin if %1 <> none
             then if %1 .sort < S_ALLOC then
%+CD                 ed_str("UPD"); edOaddr( %1 );
%+CD                 if %1 .sort=S_NOSORT
%+CD                 then edSort( %1 ); ed_str("???") endif;
%+CD                 ed_str("==>"); edOaddr( %1 qua ref(inst).gcl );
                     %1 := %1 qua ref(inst) . gcl
                  else
%+CD                 ed_str("UPD"); edOaddr( %1 );
%+CD                 if %1 .sort>MAX_SORT
%+CD                 then edSort( %1 ); ed_str("???") endif;
%+CD                 ed_str("-->"); edOaddr( %1 . sl );
                     %1 := %1 . sl endif
%+CD              ed_out;
             endif
       endmacro;

       Macro SetBioPointers(2);
       --- import ref(area)p; size request;
       begin current_pool:=%1;
             bio.nxtAdr:= %1 .nxt + %2;
             bio.lstAdr:=%1 .lim-maxlen; -- reserve space for SAV-obj
       endmacro;

       Macro insertDummy(1);
       begin %1 .nxt.sort:=S_TXTENT; %1 .nxt.ncha:=0;
             %1 .startgc:= %1 .nxt:= %1 .nxt+size(entity);
       endmacro;

       long real garb_time;         -- Time used within GC
       integer   garb_count;        -- No. of call's on GC
%+M    short integer npool;         -- Max no. of pools to be defined
       boolean   frozen;            -- True if FREEZE
       ref(area) first_pool;        -- Pointer to first storage pool
       ref(area) last_pool;         -- Pointer to last pool in chain
       ref(area) last_alloc;        -- Pointer to last allocated pool
       ref(area) current_pool;      -- Pointer to current pool
       size minsize;                -- Minimum size of a pool

       record area;                 -- Definition of storage pool
       begin ref(area) suc;         -- Used to organize the pool list
             ref(entity) nxt,lim;   -- Boundary pointers within the pool
             ref(entity) startgc;   -- "freeze-address" for the pool
             size stepsize;         -- extend/contract step
             size mingap;           -- for this pool
             short integer sequ;    -- Sequence number (1,2, ... )
       end;

       infix(area) poolzero;        -- describes bioins

%title ***   Storage   Initiation  /  Termination   ***

 Visible routine B_STRG; import size minpol;  -- Minimum pool size
 begin --  initialize the pool structure
       garb_count:=0;
%+C    postGC:=preGC:=printAll:=printOut:=atest:=false;
       first_pool:=last_alloc:=last_pool:=none; frozen:=false;
       minsize:=none + size(area) + size(entity) + maxlen - none;
       --  zero bio and set bio.realAr
--       ed_str("bioIns = "); ed_size(size(bioIns)); ed_out;
--       ed_str("ref(bio) = "); ed_oaddr(ref(bio)); ed_out;
       ZEROAREA(ref(bio),ref(bio)+size(bioIns));
---    bio.realAr:=GINTIN(43)=0;
---    if status<>0 then status:=0; bio.realAr:=true endif;
---    if bio.realAr then
          garb_time:=0&&0;
---    endif;
       --- allocate initial work area - terminate if problems ---
%+M    npool:=GINTIN(24); if status<>0 then status:=0; goto ERR endif;
       current_pool:=GetNewPool(minpol);
       if current_pool=none
       then
%+M    ERR:
            TERMIN(3,"Initial work space allocation failed") endif;
       first_pool:=current_pool;
       SetBioPointers(current_pool,nosize);
 end;

 Visible routine E_STRG;
 begin
       if garb_count > 0
       then ED_OUT; ED_STR("(Garbage Collections: "); ED_INT(garb_count)
---         if bio.realAr then
---            ED_STR(", GC-time: "); ED_FIX(garb_time qua real);
%-X            ED_STR(", GC-time: "); ED_REA(garb_time qua real,2);
---         endif;
            PRT(")");
       else PRT(" (No Garbage Collection)") endif;
 end;
%title ***   GetNewPool  /  SetBioPointers   ***
 
 Routine GetNewPool;
 import size request; export ref(area) result;
 begin ref(area) p; size poolsize; short integer sequ;
       result:=none;
       if last_alloc<>none
       then sequ:=last_alloc.sequ+1 else sequ:=1 endif;
       poolsize:=SIZEIN(1,sequ);
% +M    ED_STR("STRG.GETNEWPOOL: poolsize="); ED_SIZE(poolsize); ED_OUT;   
% +M    ED_STR("STRG.GETNEWPOOL: minsize="); ED_SIZE(minsize); ED_OUT;   
% +M    ED_STR("STRG.GETNEWPOOL: request="); ED_SIZE(request); ED_OUT;   
% +M    ED_STR("STRG.GETNEWPOOL: (none+poolsize)="); ED_OADDR(none+poolsize); ED_OUT;   
% +M    ED_STR("STRG.GETNEWPOOL: (none+minsize+request)="); ED_OADDR(none+minsize+request); ED_OUT;   
       if (status<>0) or ((none+poolsize)<=(none+minsize+request))
       then goto E1 endif;
       p:=DWAREA(poolsize,sequ);
% +M    ED_STR("STRG.GETNEWPOOL: status="); ED_INT(status); ED_OUT;   
% +M    ED_STR("STRG.GETNEWPOOL: p="); ED_OADDR(p); ED_OUT; 
--	   SETOPT(2,1);  
       if (status=0) and (p<>none)
       then ZEROAREA(p,p+poolsize);
            p.sequ:=sequ;
            p.startgc:=p.nxt:=p+size(area);
            -- all pools must have a dummy object, if frozen
            if frozen then insertDummy(p) endif;
            p.lim:=p+poolsize-size(entity); -- reserve space for GAP
            p.stepsize:=SIZEIN(2,sequ);
            if status<>0 then status:=0; p.stepsize:=nosize endif;
            p.mingap:=SIZEIN(3,sequ);
            if status<>0 then status:=0; p.mingap:=nosize endif;
            if last_pool<>none then last_pool.suc:=p;
               --- cannot trace first time because bio is not set!!!!
%-X            if bio.trc then bio.obsEvt:=EVT_gcNP; observ endif;
            endif;
            result:=last_alloc:=last_pool:=p;
% +M    	ED_STR("STRG.GETNEWPOOL: result="); ED_OADDR(result); ED_OUT;   
% +M    	ED_STR("STRG.GETNEWPOOL: p.lim=");  ED_OADDR(p.lim); ED_OUT;   
% +M  	    ED_STR("STRG.GETNEWPOOL: maxlen="); ED_SIZE(maxlen); ED_OUT;   
% +M    	ED_STR("STRG.GETNEWPOOL: p.nxt=");  ED_OADDR(p.nxt); ED_OUT;   
% +M  	    ED_STR("STRG.GETNEWPOOL: request="); ED_SIZE(request); ED_OUT;   
            if (p.lim-maxlen) < (p.nxt+request)
            then if NoExtendPool(request) then result:=none endif endif;
       endif;
    E1: status:=0; -- result reports, no status possible
 end;

-- routine SetBioPointers; import ref(area)p; size request;
-- begin current_pool:=p;
--       bio.nxtAdr:=p.nxt+request; bio.lstAdr:=p.lim-maxlen;
-- end;

 routine NoExtendPool; import size request; export boolean res;
 begin ref(area) p; ref(entity) lim; boolean notzeroed;
%+M    ED_STR("STRG.NoExtendPool: request="); ED_SIZE(request); ED_OUT;   
       res:=true; notzeroed:=false;
       lim:=last_alloc.lim+size(entity);
       if last_alloc.stepsize <> nosize
       then repeat lim:=lim+last_alloc.stepsize; -- extend
                   p:=DWAREA(lim-last_alloc,last_alloc.sequ);
                   if (p<>last_alloc) or (status<>0) then goto E1 endif;
                   last_alloc.lim:=lim - size(entity);
                   notzeroed:=true;
             while (last_alloc.lim-maxlen) < (last_alloc.nxt+request) do
            endrepeat;
            res:=false;
       endif;
 E1:   if notzeroed
       then ZEROAREA(last_alloc.nxt,last_alloc.lim+size(entity)) endif;
       status:=0; -- res reports, no status
 end;

%title ***   GC utilities   ***
 record dummyRec; info "DYNAMIC"; begin boolean dummy(0); end;

 Visible routine GCutil;
 import range(0:MAX_BYT) index;
 --- index  1: freeze (val=0) or unfreeze (val<>0) storage
 ---        2: - not used
 ---        3: check storage
 ---        4: perform garbage collection
 ---        5: allocate all except val "bytes"
 ---           FREEZE after allocate if val<0
 ---        6: - not used
 ---        7: return N, where "character array A(1:N)" is the
 ---           largest array that can be allocated
 ---        8: swap to work area 'val'
 ---        9: return current number of work areas
 ---       10: return max number of work areas
 ---       11: allocate new work area
 ---       12: free last allocated work area
 ---       13: extend last allocated work area
 ---       14: reverse work area sequence
 ---       15: print work area summary
 ---       16: set/reset storage checking
 begin ref(entity) nxt,dum; ref(area) p,p1,pool;
       size dummysize; short integer val,i;
       current_pool.nxt:=bio.nxtAdr; -- for consistency
       val:=bio.GCval; if val<0 then val:=-val endif
       if index <= 16
       then case 0:16 (index)
       when  1: -- FREEZE
             if bio.GCval = 0 then FREEZE
             elsif frozen
             then p:=first_pool; --- setp:=true;
                  repeat p.startgc:=p+size(area);
                     p:=p.suc while p<>none       do endrepeat;
                  frozen:=false;
             endif;
       when  2: -- not used
%+C    when  3: -- check storage
%+C          STRGmess:="consistency check"; chkSTRG(name(bioref));
       when  4: -- perform garbage collection
             STRG_GC(name(bioref));
       when  5: -- allocate all except val "bytes"
-- %+C          dummysize:=none+size(dummyRec:val)-none;
-- %+C          p:=first_pool;
-- %+C          repeat
-- %+C             nxt:=p.lim-maxlen-dummysize; -- must be space for SAV
-- %+C             if nxt > p.nxt
-- %+C             then p.nxt.sort:=S_TXTENT; p.nxt.lng:=nxt-p.nxt;
-- %+C                  p.nxt:=nxt; SetBioPointers(p,nosize);
-- %+C                  pool:=first_pool;
-- %+C                  repeat while pool<>none
-- %+C                  do nxt:=pool.lim-maxlen;
-- %+C                     if (pool<>p) and (nxt > pool.nxt)
-- %+C                     then pool.nxt.sort:=S_TXTENT;
-- %+C                          pool.nxt.lng:=nxt-pool.nxt;
-- %+C                          pool.nxt:=nxt;
-- %+C                     endif;
-- %+C                  endrepeat;
-- %+C                  if bio.GCval<0 then FREEZE endif;
-- %+C                  goto E1;
-- %+C             endif;
-- %+C             p:=p.suc;
-- %+C          while p<>none       do endrepeat;  E1:
       when  6: -- - not used
       when  7: -- return N, where "character array A(1:N)" is the
                -- largest array that can be allocated
    --       STRG_GC(name(bioref));
    --       dummysize:=last_pool.lim-maxlen-last_pool.nxt;
    --       i:=0; repeat while (none+rec_size(dummyrec,i) < dummyadr
    --       do i:=i+1 endrepeat; -- return i (NB overflow???)
       when  8: -- swap to work area 'val'
-- %-X          p:=first_pool;
-- %-X          repeat if p.sequ=val
-- %-X                 then SetBioPointers(p,nosize); goto E2; endif;
-- %-X                 p:=p.suc while p<>none       do endrepeat;  E2:
       when  9: -- return current number of work areas
       when 10: -- return max number of work areas
       when 11: -- allocate new work area
       when 12: -- free last allocated work area
       when 13: -- extend last allocated work area
       when 14: -- reverse work area sequence
%+C    when 15: -- print work area summary
%+C          noDump:=true; ed_out; dmpPool;
%+C    when 16: -- set/reset storage checking
%+C          postGC:=preGC:=printAll:=printOut:=atest:=false;
%+C          repeat while val>=32 do val:=val-16 endrepeat
%+C          if val >=16 then postGC:=true; val:=val-16 endif;
%+C          if val >= 8 then preGC:=true; val:=val-8 endif;
%+C          if val >= 4 then printOut:=printAll:=true; val:=val-4 endif
%+C          if val >= 2 then printOut:=true; val:=val-2 endif;
%+C          if val <> 0 then atest :=true endif;
       endcase endif;
       bio.GCval:=0;
 end;
%title ***   GC freeze   ***

 routine FREEZE;
 begin boolean setp; ref(entity)gap;  ref(area) p;
       if frozen -- remove dummies before GC (avoid multiple dummies)
       then p:=first_pool;
            repeat
               if (p.startgc-size(area)) <> p
               then p.startgc:=p.startgc-size(entity) endif;
               p:=p.suc;
            while p <> none do endrepeat;
       endif;
       STRG_GC(name(bioref));
       p:=first_pool; setp:=true;
       repeat -- insert dummy objects to avoid reuse of frozen obj.
              insertDummy(p);
              if setp then if (p.lim-maxlen) > p.nxt
                      then SetBioPointers(p,nosize); setp:=false
              endif endif;
              p:=p.suc
       while p<>none       do endrepeat;
       if setp then ERROR(ENO_DSM_1) endif; -- no more space
       frozen := true;
 end;

-- FREEZE first performs a GC (even if already frozen). Startgc pointers
-- of the areas are then moved (from unfrozen: pool + size(area))
-- to first available in the pool - and a dummy text object is inserted
-- in each pool to stop reclaim!
-- THAW undo all previous freezes i.e. startgc:= pool+size(area) for all
-- pools. The dummy text objects are reclaimed at first following GC.

-- Implications for GC passes:
-- PASS1: unchanged
-- PASS2: starts by setting the GCLs of all frozen objects to the
--        object itself, to ensure correct UPDATE in PASS3. No frozen
--        object will be reclaimed, sorts S_GAP and S_NOSORT C A N N O T
--        (and MUST not) occur in the frozen area.
--        Set up GAPs (link only the unfrozen parts)
--       NOTE: Pointers to garbage (in the unfrozen area) becomes none.
--          GAPs linking pools have GCL<>none, local GAPs have GCL=none
--          so that PASS4 can work correctly on frozen pools
--        Otherwise, PASS2 is unchanged over the unfrozen parts
-- PASS3: Unchanged
-- PASS4: starts by resetting the GCLs of the frozen objects.
--        Otherwise unchanged.
%title ***   The Garbage Collector   ***

 Visible routine GARB2;
 begin GARB(bio.nxtAdr,nosize,name(bioref)) end;

 Visible routine GARB;
 import ref() poolnx; size demand; name(ref(entity)) xpnt;
 export ref(entity) result;
 begin Boolean extendOrGC; range(0:2) attempt;
       ref(entity) lim; ref(area) p;
       --- attempt means:
       ---    0      initially, no GC attempted
       ---    1      after first GC
       ---    2      after second GC, give up if no space
       --- If no space found after first GC, another GC may be
       --- able to compact the area further, because of the pool sorting

       --- extendorGC is redundant, except it guards against errors
       --- elsewhere (in noExtendPool and in ENVIR)

       current_pool.nxt:=bio.nxtAdr:=poolnx;
       attempt:=0; extendorGC:=true;

       ---   STEP 1: check all pools for room   ---
STEP11:STEP12:STEP13:
       p:=first_pool;
       repeat if (p.lim-maxlen) > (p.nxt+demand)
              then result:=p.nxt;
                   SetBioPointers(p,demand);
                   goto EXIT1
              endif;
              p:=p.suc
       while p<>none       do endrepeat;

       ---   STEP 2: shall a true GC be performed now?   ---

       if extendorGC -- here for the first time
       then extendorGC:=false;
            if not EXTGC(demand) then goto STEP5 endif;
       endif;

       ---   STEP 3: extend current work area   ---

       if not NoExtendPool(demand) then goto STEP11 endif;

       ---   STEP 4: provide a new work area   ---

       if GetNewPool(demand) <> none
       then goto STEP12 endif; -- and do pointer setting there
---    if attempt=0 then if GetNewPool(demand) <> none
---    then goto STEP12 endif endif; -- and do pointer setting there

       ---   STEP 5: perform true garbage collection   ---

STEP5: case 0:2 (attempt)
       when 1: garb_count:=garb_count-1; -- don't count
       when 2: ERROR(ENO_DSM_1); -- FINITO! - no more space
       endcase;
       attempt:=attempt+1;
       STRG_GC(xpnt);
       goto STEP13;

EXIT1: if attempt<>0 -- mingap shall only be tested after true GC
       then
%+C         if postGC then STRGmess:="GARB.COLL. post-CHECK";
%+C            chkSTRG(xpnt) endif;
            if (last_pool.lim-maxlen-last_pool.mingap)<last_pool.nxt
            then if noExtendPool(last_pool.stepsize)
                 then if GetNewPool(nosize)=none
                      then ERROR(ENO_DSM_2) endif endif;
                      -- too little IN LAST POOL (pools sorted)
            endif;
       endif;
 end;
%page

 routine STRG_GC;
 import name(ref(entity)) xpnt;
 begin boolean swap; range(0:MAX_ACT) act;
       ref(entity) pnxt; ref(area) p,pool,q1,q2; long real gctime;

%+CD   PRT("* * * * * *    S T A R T   G C    * * * * * *");
%       ED_STR("STRG.GARB: Begin Garbage Collection - NOT TESTED"); ED_OUT;
%       ED_STR("STRG.GARB: CHECK BACK-END-COMPILER ROUTINE  SYSTEM.SIZEIN"); ED_OUT;
%       TERMIN(3,"GARBAGE COLLECTOR NOT TESTED");
%+CD   nodump:=true; dmpPool;
       current_pool.nxt:=bio.nxtAdr; -- consistency
%-X    if bio.trc then bio.obsEvt:=EVT_gcBEG; observ endif;
%+C    if preGC then STRGmess:="GARB.COLL. pre-CHECK";
%+C       chkSTRG(xpnt) endif;

       ---   inform envir about GC start   ---
       GVIINF(6,0); status:=0;

       garb_count:=garb_count+1; gctime:=CPUTIM; status:=0;
       bio.thunks:=none; act:=actLvl; actLvl:=ACT_GC;
       rstr_x:=none;  -- forget reusable save-object
--     if f_r_i <> none then RELEASE_FRI;  -- TEMP inntil friliste impl.
       PASS1(xpnt); --  if bio.trc then trGC(EVT_gcP1) endif;
       pnxt:=PASS2; --  if bio.trc then trGC(EVT_gcP2) endif;
       PASS3(xpnt); --  if bio.trc then trGC(EVT_gcP3) endif;
       PASS4;       --  if bio.trc then trGC(EVT_gcP4) endif;
       if pnxt <> current_pool.nxt then IERR_R("STRG.GC") endif;

       actLvl:=act;
       p:=current_pool; -- Last surviving pool
       repeat p:=p.suc while p <> none      
       do p.nxt:=p.startgc               endrepeat;

       ---   Clear free area of all pools and insert dummies  ---
       p:=first_pool;
       repeat ZEROAREA(p.nxt,p.lim+size(entity));
              p:=p.suc;
       while p<>none       do endrepeat;

       ---   Sort pools   ---
       swap:=first_pool.suc<>none;
       repeat while swap do  -- simple bubble sort (least code)
              q1:=first_pool; swap:=false;
              repeat q2:=q1.suc while q2 <> none       do
                     if (none+(q2.lim-q2.nxt)) < (none+(q1.lim-q1.nxt))
                     -- sort so that smallest holes are first in chain
                     then swap:=true;    -- swapPools(q1);
                          if q2=last_pool  then last_pool :=q1 endif;
                          if q1=first_pool then first_pool:=q2
                          else pool:=first_pool;
                               repeat while pool.suc<>q1
                                   do pool:=pool.suc endrepeat;
                               pool.suc:=q2;
                          endif;
                          q1.suc:=q2.suc; q2.suc:=q1;
                     else q1:=q2 endif;
              endrepeat;
       endrepeat;
       SetBioPointers(last_pool,nosize); -- consistency
       garb_time:=CPUTIM-gctime+garb_time; status:=0;

       ---   inform envir about GC end   ---
       GVIINF(6,1); status:=0;
%+CD   nodump:=true; dmpPool;
%-X    if bio.trc then bio.obsEvt:=EVT_gcEND; observ endif;
 end; -- GARB

 --- BIO allocated inline, implications for GC:
 --- PASS1: bio must be put explicitly on the chain.
 --- PASS2: bio is frozen always, gcl must be set explicitly
 --- PASS3: the pointers in bio must be updated explicitly.
 --- PASS4: bio.gcl must be explicitly cleared. (not necessary???)

%title ***   Pass 1 - Marking   ***
       ---   is done for all objects, disregarding "freezing point"

 Routine PASS1; import name(ref(entity)) xpnt;
 --- find all reachable entities, and mark them (by adding 127 to SORT)
 --- For instances, SL is moved to GCL. SL is used as link in this impl
 --- Constant entities are marked by adding additional 64 to SORT
 --- (this is necessary because their SL contains the constant mark)
 begin ref(entity) x;       -- The entity which is currently treated
       ref(entity) y;       -- Local utility
       ref(entity) z;       -- Used by the MARK... macros
       ref(ptp) pp;         -- Local utility
       integer i,j;         -- Local utility
       integer dim1,dim2;   -- Used during array treatment
       range(0:MAX_PLV) plv -- Local utility
       name() q;            -- Local utility
       name(ref(inst)) qref; name(infix(txtqnt)) qtex;
       ref(pntvec) refVec;  -- Local utility
       ref(rptvec) repVec; range(0:MAX_REP) np;
       infix(repdes) repdesc;
       ref(filent) F,N; -- Used to scan the open file list
       ref(area) p,Dest; ref(entity) gap,zero;

% +D    PRT("**********  BEGIN PASS 1  ************");
---    chain:= ref(nostring); MARK_INSTANCE(bioref);
       bio.gcl:=ref(nostring) qua ref(); chain:=ref(bio);

       --- scan frozen pools. This must be done separately, since the
       --- contents may not be reachable otherwise. All objects are
       --- assumed OK, otherwise anything may happen!

       Dest:=first_pool;
       repeat
          if frozen -- marked in sequential scan
          then x:=Dest+size(area); zero:=Dest.startgc;
               repeat while x < zero
               do if x.sort <= MAX_SORT
                  then case 0:MAX_SORT (x.sort)
                  when S_SUB,S_PRO,S_THK,
                       S_ATT,S_DET,S_RES,S_TRM,S_PRE:
                       MARK_INSTANCE(x);
                       x:= x + x.pp.lng;
                  when S_SAV:
                       MARK_INSTANCE(x);
                       x:= x + x.lng;
                  when S_ARBREF,S_ARBTXT,S_ARREF1,S_ARTXT1,
                       S_ARREF2,S_ARTXT2,S_ARBODY:
                       x.sl:=chain; chain:=x;
                       x:= x + x.lng;
                  when S_ALLOC,S_ARHEAD,S_ARENT2,S_ARENT1: -- no ptr
                       x.sl:=x;
                       x:= x + x.lng;
                  when S_TXTENT: -- no ptr
                       x.sl:=x;
--                       x:= x + rec_size(txtent,x.ncha);
                       x:= x + size(txtent:x.ncha);
                  otherwise ERR1:
%+C                    edEobj(x);
                       IERR_R("Frozen object inconsistent");
                  endcase;
                  else goto ERR1 endif;
                endrepeat;
          endif;
          Dest:=Dest.suc;
       while Dest<>none do endrepeat;

       MARK_obj_FOLLOW(%curins%);
       z:=var(xpnt);
       if z <> none then MARK_AND_FOLLOW(z) endif;

       --- chain will be <> ref(nostring) at loop entry
       repeat
          x:= chain;
%+CD      ed_str("scan"); edEobj(x);
%+C       if x.sort>MAX_SORT then
%+C            edEobj(x);
%+C            IERR_R("STRG.P1-check") endif;
          case 0:MAX_SORT (x.sort) -- note: chain objects are marked

          when S_SUB,S_PRO,S_THK:
               pp:= x.pp; plv:=0; goto MARKref;

          when S_ATT,S_DET,S_RES,S_TRM,S_PRE:
               pp:= x.pp; plv:= pp qua ref(claptp).plv;
  MARKref:     chain:= x qua ref(inst).gcl;
               MARK_obj_FOLLOW(%x.sl%);
               MARK_obj_FOLLOW(%x qua ref(inst).dl%);
               repeat refVec:=pp.refVec;
                  if refVec <> none
                  then j:=refVec.npnt;
                       repeat while j <> 0
                       do j:=j-1; q:= x + refVec.pnt(j);
                          z:=var(q qua name(ref()));
                          if z<>none then
                          case 0:MAX_SORT (z.sort) -- no check on sort
                          when S_SUB,S_PRO,S_THK,S_SAV,
                               S_ATT,S_DET,S_RES,S_TRM,S_PRE:
                               if z qua ref(inst).gcl=none
                               then MARK_INSTANCE(z) endif
                          when S_ARBREF,S_ARBTXT,S_ARREF1,
                               S_ARTXT1,S_ARREF2,S_ARTXT2,S_ARBODY:
                               if z.sl=none
                               then z.sl:=chain; chain:=z endif;
                          when S_TXTENT, -- no pointers
                               S_ALLOC,S_ARHEAD,S_ARENT2,S_ARENT1:
                               if z.sl=none then z.sl:=z endif;
                          when S_GAP,S_NOSORT:
                               -- do not terminate if check
%+C                            ed_str("*** illegal pointer: ");
%+C                            edEobj(z); ed_str("  - in "); edSort(x);
%+C                            ed_out;
%-C                            IERR_R("Object inconsistent");
                          endcase endif;
                       endrepeat; endif;
                  --- now scan and mark all ref-type repetitions
                  if pp.repVec <> none
                  then repVec:=pp.repVec;
                       j:=repVec.npnt; repeat while j <> 0
                       do j:=j-1;
                          repdesc:=repvec.rep(j); np:=repdesc.nelt
                          if repdesc.type=T_REF
                          then
                               qref:=x+repdesc.fld;
                               repeat MARK_obj_FOLLOW(%var(qref)%)
                               while np<>0 do np:=np-1;
                                  qref:=name(var(qref)(1)) endrepeat
                          else -- if repdesc.type=T_TXT
                               qtex:=x+repdesc.fld;
                               repeat z:=var(qtex).ent;
                                  if z<>none then if z.sl=none
                                  then z.sl:=z endif endif;
                               while np<>0 do np:=np-1;
                                  qtex:=name(var(qtex)(1)) endrepeat
                          endif
                       endrepeat
                  endif
               while plv<>0 do plv:=plv-1;
                             pp:= pp qua ref(claptp).prefix(plv);
               endrepeat;

          when S_ARBREF:
               chain:= x.sl;
               z:=x qua ref(arbody).head;
               if z.sl=none then z.sl:=z endif;
               i:=z qua ref(arhead).nelt;
               repeat while i <> 0 do i:=i-1;
                  MARK_obj_FOLLOW(%x qua ref(refArr).elt(i)%) endrepeat

          when S_ARBTXT:
               chain:= x.sl;
               z:=x qua ref(arbody).head;
               if z.sl=none then z.sl:=z endif;
               i:=z qua ref(arhead).nelt;
               repeat while i <> 0 do i:=i-1;
                      z:=x qua ref(txtArr).elt(i).ent;
                      if z<>none then if z.sl=none
                      then z.sl:=z endif endif;
               endrepeat;

          when S_ARBODY:
               chain:= x.sl;
               z:=x qua ref(arbody).head;
               if z.sl=none then z.sl:=z endif;

          when S_ARREF2:
               chain:= x.sl;
               dim1:=x qua ref(arent2).ub_1-x qua ref(arent2).lb_1 + 1;
               dim2:=x qua ref(arent2).ub_2-x qua ref(arent2).lb_2 + 1;
               if dim2 > 0 -- else dummy array
               then i:=dim1*dim2;
                    repeat while i > 0 do i:=i-1;
                       MARK_obj_FOLLOW(%x qua ref(refAr2).elt(i)%)
                    endrepeat;
               endif

          when S_ARTXT2:
               chain:= x.sl;
               dim1:=x qua ref(arent2).ub_1-x qua ref(arent2).lb_1 + 1;
               dim2:=x qua ref(arent2).ub_2-x qua ref(arent2).lb_2 + 1;
               if dim2 > 0 -- else dummy array
               then i:=dim1*dim2;
                    repeat while i > 0 do i:=i-1;
                      z:=x qua ref(txtAr2).elt(i).ent;
                      if z<>none then if z.sl=none
                      then z.sl:=z endif endif;
                    endrepeat
               endif;

          when S_ARREF1:
               chain:= x.sl;
               i := x qua ref(arent1).ub - x qua ref(arent1).lb + 1;
               repeat while i >  0 do i:=i-1;
                      MARK_obj_FOLLOW(%x qua ref(refAr1).elt(i)%)
               endrepeat;

          when S_ARTXT1:
               chain:= x.sl;
               i := x qua ref(arent1).ub - x qua ref(arent1).lb + 1;
               repeat while i >  0 do i:=i-1;
                      z:=x qua ref(txtAr1).elt(i).ent;
                      if z<>none then if z.sl=none
                      then z.sl:=z endif endif;
               endrepeat;

          when S_SAV:
               chain:= x qua ref(inst).gcl;
               MARK_obj_FOLLOW(%x.sl%);
               MARK_obj_FOLLOW(%x qua ref(inst).dl%);
               if x.lng > size(savent)
               then init_pointer(x+size(savent));
                    repeat z:=get_pointer while z <> none
                    do MARK_AND_FOLLOW(z) endrepeat;
               endif;

          when S_ALLOC,S_TXTENT,S_ARHEAD,S_ARENT2,S_ARENT1:
               -- no local pointer
               chain:= x.sl;

          otherwise 
%+C            edEobj(x);
               IERR_R("STRG.P1-1");
          endcase;
       while chain <> ref(nostring) do endrepeat;

       --- Scan thru list of open files and close unreachable files
       --- NOTE: this must be done via SIMOB since CLOSE is unknown here
       N:=bio.files;
       repeat F:=N while F <> none do N:=F.suc;
              if F.gcl = none then bio.smbP1:=F;
              -- Unclosed file found as Garbage
%-X              bio.obsEvt:=EVT_gcFIL; observ;
%+X              bio.obsEvt:=EVT_gcFIL; call PSIMOB(smb);
              endif;
       endrepeat;
 end;
%title ***   Pass 2 - Compute new Object Addresses   ***

%+CD routine edGap; import infix(string) mess; ref(inst) gap;
%+CD begin ed_str(mess);
%+CD       if gap = none then ed_str(" NONE (***???***)")
%+CD       else edOaddr(gap); ed_str(" to"); edOaddr(gap.pp);
%+CD            if gap.sl<>none
%+CD            then ed_str(", next pool:"); edOaddr(gap.sl) endif;
%+CD       endif;
%+CD       ed_out;
%+CD end;

 Routine PASS2; export ref(entity) pnxt;
 ---  Sequential scan of storage.
 ---  Marked entities'SL set to new address (after compaction)
 ---  Unmarked are united (if neighbours), and chained together as GAPs
 ---  with corresponding LNG (chain in SL)
 ---  GAPs also link together different storage pools (chain in GCL)
 ---  The sort codes are reset, except for constant TEXT entities, to
 ---  minimize the size of the case tables in later passes.
 begin ref(area) Source,Dest;
       ref(entity) x,xnew,destNxt,destLim,gap,zero; ref(filent) F,N;
       size lng,free; range(0:MAX_BYT) sort; boolean marked;

%+CD   PRT("**********  BEGIN PASS 2  ************");
       bio.gcl:=ref(bio); -- bio never moved

       Dest:=first_pool;
       repeat
          if frozen -- all objects above are set with new addr == old
                    -- These objects must not be moved
          then x:=Dest+size(area); zero:=Dest.startgc;
               repeat while x < zero do
%-X               if x.sort > MAX_SORT then
--%-X %+C              edEobj(x);
%-X                  IERR_R("STRG.P2-check") endif;
                  case 0:MAX_SORT (x.sort)
                  when S_NOSORT,S_GAP:
%+C                    edEobj(x);
                       IERR_R("STRG.P2-2");
                  when S_TXTENT:
                       x.sl:=x;
                       --  x:= x + rec_size(txtent,x.ncha);
                       x:= x + size(txtent:x.ncha);
                  when S_SUB, S_PRO, S_ATT, S_DET,
                       S_RES, S_TRM, S_PRE, S_THK:
                       x qua ref(inst).gcl:=x; x:= x + x.pp.lng;
                  when S_SAV:
                       x qua ref(inst).gcl:=x; x:= x + x.lng;
                  when S_ARHEAD,S_ARBODY,S_ARBREF,S_ARBTXT,S_ARENT2,
                       S_ARREF2,S_ARTXT2,S_ARENT1,S_ARREF1,S_ARTXT1,
                       S_ALLOC:
                       x.sl:=x;  x:= x + x.lng;
                  otherwise
%+CD                   edEobj(x);
                       IERR_R("STRG.P2-1");
                  endcase;
               endrepeat;
          endif;
          gap:=Dest.nxt; Dest:=Dest.suc;
       while Dest<>none
          -- Set up GAPs to link all compactable entities in all pools
          -- and to terminate sequential pool scan.
          -- Note that last pool is terminated by NOSORT
          -- Note also that GAPs are unreachable thru pointers
          -- Must be done here because startgc may be modified above;
       do gap.sort:=S_GAP; gap.pp:=Dest.startgc qua ref();
          gap.sl:=Dest+size(area);
%+CD      edGap("initial GAP from",gap);
       endrepeat;
       gap:=none; marked:=true; Source:=Dest:=first_pool;
       destLim:=Dest.lim; x:=destNxt:=Dest.startgc;
       repeat
          if x.sort > MAX_SORT then
%+C          edEobj(x);
             IERR_R("STRG.P2-3") endif;
          case 0:MAX_SORT (x.sort)

          when S_GAP: --- end of source pool, swap to next via GAP'sl
               --- and set up GAP to include both hole and former GAP
               if gap<>none then
%+CD              edGap("end pool GAP from",gap);
                  gap.sl:=x.sl endif;
               x:=x.pp qua ref(); gap:=none;
               goto E;

          when S_NOSORT: goto ENDP2;

          when S_TXTENT:
               --lng:=rec_size(txtent,x.ncha);
               lng:=size(txtent:x.ncha);
               if x.sl = none then goto UE1 endif;
    ME1:ME2:   -- marked entity
               repeat zero:=destNxt+lng;
               while zero > destLim --- Destination pool filled
               do Dest.nxt:=x; Dest:=Dest.suc;
                  destNxt:=Dest.startgc; destLim:=Dest.lim; endrepeat;
%+CD           ed_str("p2.. "); edEobj(x); ed_out;
               x.sl:= destNxt; destNxt:= zero;
               if gap <> none
               then gap.pp:= x qua ref();
%+CD                edgap("GAP from",gap);
                    gap:= none;
               endif;

          when S_ARHEAD,S_ARBODY,S_ARBREF,S_ARBTXT,S_ARENT2,
               S_ARREF2,S_ARTXT2,S_ARENT1,S_ARREF1,S_ARTXT1:
               lng:= x.lng;
               if x.sl<>none then goto ME1 endif; goto UE2;

          when S_ALLOC: lng:= x.lng; goto ME2;    -- MARK !!!

          when S_SUB, S_PRO, S_ATT, S_DET,
               S_RES, S_TRM, S_PRE, S_THK:
               lng:= x.pp.lng;
               if x qua ref(inst).gcl<>none then -- marked instance
      MI:         repeat zero:=destNxt+lng;
                  while zero > destLim --- Destination pool filled
                     do Dest.nxt:=x; Dest:=Dest.suc;
                        destNxt:=Dest.startgc; destLim:=Dest.lim;
                  endrepeat;
%+CD              ed_str("p2.. "); edEobj(x); ed_out;
                  x qua ref(inst).gcl:= destNxt; destNxt:= zero;
                  if gap <> none
                  then gap.pp:= x qua ref();
%+CD                   edgap("GAP from",gap);
                       gap:= none;
                  endif;
               else -- unmarked instance or entity
      UI:UE1:UE2:
%+CD              ed_str("FREE "); edEobj(x); ed_out;
                  if gap = none  -- Note: x.gcl==none !
                  then gap:= x; gap.sort:= S_GAP; gap.sl:=none endif;
                  gap.pp:= (x + lng) qua ref();
               endif;

          when S_SAV:
               lng:= x.lng;
               if x qua ref(inst).gcl<>none then goto MI endif;
               goto UI;

          endcase;

          x:= x + lng;
 E:    while true do endrepeat;

 ENDP2:if gap <> none then
          gap.pp:= x qua ref()
%+CD      edGap("final GAP (p2) from",gap);
       endif;
       Dest.nxt:=none; current_pool:=Dest; pnxt:=destNxt;
       ---  update of file list must be done before update of bio
       ---  therefore it is done here BEFORE pass 3 !!!
       N:=bio.files; --- Scan through files and set two-way links ---
       repeat F:=N while F <> none
       do N:=F.suc; if N <> none then F.suc:=N.gcl endif;
          if F.prd <> none then F.prd:=F.prd.gcl endif;
       endrepeat;
 end;
%title ***   Pass 3 - Update Pointers   ***

 Routine PASS3; import name(ref(entity)) xpnt;
 begin ref(entity) x,y,z; ref(ptp) pp; integer i,j,dim1,dim2;
       range(0:MAX_PLV) plv -- Local utility
       ref(area) pool;
       ref(pntvec) refVec; name(ref(entity)) q; ref(filent) F,N;
       ref(rptvec) repVec; range(0:MAX_REP) np;
       infix(repdes) repdesc;
       name(ref(inst)) qref; name(infix(txtqnt)) qtex;
       -- if bio.sl <> bio then IERR_R("STRG.P3-1") endif;
       -- NOTE: Due to GC-Tracing: curins must not be updated
       --       until the end of pass 3

%+CD   PRT("**********  BEGIN PASS 3  ************");
       x:=first_pool+size(area);  -- ** NOTE: also update in frozen area
       repeat
          SCAN1:SCAN2:SCAN3: -- jump here when NOT x:=x+x.lng
%+CD      edSort(x);
%+D       if x.sort > MAX_SORT then
%+D          IERR_R("STRG.P3-2") endif;
          case 0:MAX_SORT (x.sort)

          when S_TXTENT: -- no local pointers
%+CD           if x.sl=none then edEobj(x); PRT("P3: Ten!")
%+CD           else ed_out endif
               --x:=x+rec_size(txtent,x.ncha);
               x:=x+size(txtent:x.ncha);
               goto SCAN2;

          when S_GAP:
               if x.sl<>none
               then x:=x.sl -- change pool
               else
%+CD                if x.pp=none then edEobj(x); PRT("P3: GAP!")
%+CD                else ed_out endif
                    x:=x.pp qua ref() endif;       -- skip hole
               goto SCAN3;

          when S_NOSORT: goto ENDP3;

          when S_SUB,S_PRO,S_THK:
%+CD           if x qua ref(inst).gcl=none
%+CD           then edEobj(x); PRT("P3: block!") else ed_out endif
               pp:= x.pp; plv:= 0; goto UPDref;

          when S_ATT,S_DET,S_RES,S_TRM,S_PRE:
%+CD           if x qua ref(inst).gcl=none
%+CD           then edEobj(x); PRT("P3: class!") else ed_out endif
               pp:= x.pp; plv:= pp qua ref(claptp).plv;
  UPDref:
               UPDATE_I(%x qua ref(inst).sl%);
               UPDATE_I(%x qua ref(inst).dl%);
               repeat refVec:=pp.refVec;
                      if refVec <> none
                      then j:=refVec.npnt; repeat while j <> 0
                           do j:=j-1; q:= x + refVec.pnt(j);
                              UPDATE(%var(q)%);
                           endrepeat;
                      endif;
                      -- direct test below (this field == none normally)
                      if pp.repVec <> none
                      then repVec:=pp.repVec;
                           j:=repVec.npnt; repeat while j <> 0
                           do j:=j-1;
                              repdesc:=repvec.rep(j); np:=repdesc.nelt
                              if repdesc.type=T_REF
                              then
                                   qref:=x+repdesc.fld;
                                   repeat UPDATE_I(%var(qref)%);
                                   while np<>0 do np:=np-1;
                                      qref:=name(var(qref)(1)) endrepeat
                              else -- must be T_TXT
                                   qtex:=x+repdesc.fld;
                                   repeat UPDATE_E(%var(qtex).ent%);
                                   while np<>0 do np:=np-1;
                                      qtex:=name(var(qtex)(1)) endrepeat
                              endif
                           endrepeat
                      endif
               while plv<>0 -- always zero for non-class objects
               do plv:=plv-1; pp:=pp qua ref(claptp).prefix(plv);
               endrepeat;
               x:= x + x.pp.lng; goto SCAN1;

          when S_ARBODY:
%+CD           if x.sl=none then edEobj(x); PRT("P3: BODY!")
%+CD           else ed_out endif
               UPDATE_E(%x qua ref(arbody).head%);

          when S_ARBREF:
%+CD           if x.sl=none then edEobj(x); PRT("P3: REF!")
%+CD           else ed_out endif
               i:=x qua ref(arbody).head.nelt;
               UPDATE_E(%x qua ref(arbody).head%);
          ---  repeat i:= i - 1 while i >= 0 do
               repeat           while i >  0 do i:=i-1;
                  UPDATE_E(%x qua ref(refArr).elt(i)%) endrepeat;

          when S_ARBTXT:
%+CD           if x.sl=none then edEobj(x); PRT("P3: TXT!")
%+CD           else ed_out endif
               i:=x qua ref(arbody).head.nelt;
               UPDATE_E(%x qua ref(arbody).head%);
          ---  repeat i:= i - 1 while i >= 0 do
               repeat           while i >  0 do i:=i-1;
                  UPDATE_E(%x qua ref(txtArr).elt(i).ent%) endrepeat;

          when S_ARREF2:
%+CD           if x.sl=none then edEobj(x); PRT("P3: REF2!")
%+CD           else ed_out endif
               dim1:=x qua ref(arent2).ub_1-x qua ref(arent2).lb_1 + 1;
               dim2:=x qua ref(arent2).ub_2-x qua ref(arent2).lb_2 + 1;
               if dim2 > 0 -- else dummy array
               then i:=dim1*dim2;
                    repeat while i > 0
                    do i:=i-1; UPDATE_I(%x qua ref(refAr2).elt(i)%)
                    endrepeat;
               endif

          when S_ARTXT2:
%+CD           if x.sl=none then edEobj(x); PRT("P3: TXT2!")
%+CD           else ed_out endif
               dim1:=x qua ref(arent2).ub_1-x qua ref(arent2).lb_1 + 1;
               dim2:=x qua ref(arent2).ub_2-x qua ref(arent2).lb_2 + 1;
               if dim2 > 0 -- else dummy array
               then i:=dim1*dim2;
                    repeat while i > 0
                    do i:=i-1; UPDATE_E(%x qua ref(txtAr2).elt(i).ent%)
                    endrepeat;
               endif

          when S_ARREF1:
%+CD           if x.sl=none then edEobj(x); PRT("P3: REF1!")
%+CD           else ed_out endif
               i := x qua ref(arent1).ub - x qua ref(arent1).lb + 1;
          ---  repeat i:= i - 1 while i >= 0 do
               repeat           while i >  0 do i:=i-1;
                      UPDATE_I(%x qua ref(refAr1).elt(i)%) endrepeat;

          when S_ARTXT1:
%+CD           if x.sl=none then edEobj(x); PRT("P3: TXT1!")
%+CD           else ed_out endif
               i := x qua ref(arent1).ub - x qua ref(arent1).lb + 1;
          ---  repeat i:= i - 1 while i >= 0 do
               repeat           while i >  0 do i:=i-1;
                      UPDATE_E(%x qua ref(txtAr1).elt(i).ent%) endrepeat

          when S_SAV:
%+CD           if x qua ref(inst).gcl=none
%+CD           then edEobj(x); PRT("P3: SAVE!") else ed_out endif
               UPDATE_I(%x qua ref(inst).sl%);
               UPDATE_I(%x qua ref(inst).dl%);
               if x.lng > size(savent)
               then init_pointer(x+size(savent));
                    repeat y:= get_pointer while y <> none
                    do set_pointer(
                       if y.sort<S_ALLOC then y qua ref(inst).gcl
                       else y.sl) endrepeat
               endif;

          when S_ARHEAD,S_ALLOC,S_ARENT1,S_ARENT2:
%+CD           if x.sl=none then edEobj(x);
%+CD              if    x.sort=S_ARHEAD then PRT("P3: aHd!")
%+CD              elsif x.sort=S_ALLOC  then PRT("P3: alloc!")
%+CD              elsif x.sort=S_ARENT1 then PRT("P3: Aent1!")
%+CD              else                     PRT("P3: Aent2!") endif
%+CD           else ed_out endif

          endcase;
          x:= x + x.lng;

       while true do endrepeat;

 ENDP3:
%+CD   ed_out;
       --- ******   update of pointers in bio   ******
       --- (must be done separately, since bio is part of no pool)
       --- It is a simplified S_TRM treatment, since we know that
       --- 1) bio HAS pointers, and  2) bio has NO prefix and NO repVec
               UPDATE_I(%bio.sl%); UPDATE_I(%bio.dl%);
               j:= 0; refVec:=bio.pp.refVec;
               repeat while j < refVec.npnt
               do q:= ref(bio) + refVec.pnt(j); UPDATE(%var(q)%);
                  j:=j+1 endrepeat;
--- see (2)    if bio.pp.repVec <> none
--- see (2)    then repVec:=bio.pp.repVec;
--- see (2)         j:=repVec.npnt; repeat while j <> 0
--- see (2)         do j:=j-1; repdesc:=repvec.rep(j);
--- see (2)            np:=repdesc.nelt;
--- see (2)            q:= x + repdesc.fld;
--- see (2)            if repdesc.type=T_REF
--- see (2)            then qref:=x+repdesc.fld;
--- see (2)                 repeat UPDATE(%var(qref)(np)%)
--- see (2)                 while np <> 0 do np:=np-1; endrepeat;
--- see (2)            else -- must be T_TXT
--- see (2)                 qtex:=x+repdesc.fld;
--- see (2)                 repeat UPDATE(%var(qtex)(np).ent%)
--- see (2)                 while np <> 0 do np:=np-1; endrepeat;
--- see (2)            endif
--- see (2)         endrepeat
--- see (2)    endif
       --- ******    end update bio pointers    ******
       UPDATE(%var(xpnt)%); UPDATE_I(%curins%);
 end;
%title ***   Pass 4 - Compact all Storage Pools   ***

 Routine PASS4;
 begin ref(area) pool; range(0:MAX_SORT) sort; size lng;
       ref(entity) x,y,z,filled,gap,zero;

%+CD   PRT("**********  BEGIN PASS 4  ************");
       bio.gcl:=none;
       if frozen -- then reset sl to none above freezing point
       then pool:=first_pool;
            repeat
                x:=pool+size(area); zero:=pool.startgc;
                repeat while x < zero do
%+D               if x.sort > MAX_SORT then
%+CD                 edEobj(x);
%+D                  IERR_R("STRG.P4-1") endif;
                  case 0:MAX_SORT (x.sort)

%+CD              when S_NOSORT,S_GAP:
%+CD                   edEobj(x); -- cannot occur???
%+CD                   IERR_R("STRG.P4-2"); -- cannot occur

                  when S_TXTENT:
                       x.sl:=none;
                       --x:= x + rec_size(txtent,x.ncha);
                       x:= x + size(txtent:x.ncha);

                  when S_ARHEAD,S_ARBODY,S_ARBREF,S_ARBTXT,S_ARENT2,
                       S_ARREF2,S_ARTXT2,S_ARENT1,S_ARREF1,S_ARTXT1,
                       S_ALLOC:
                       x.sl:=none; x:= x + x.lng;

                  when S_SAV:
                       x qua ref(inst).gcl:=none; x:= x + x.lng;

                  when S_SUB,S_PRO,S_THK,S_ATT,S_DET,S_RES,S_TRM,S_PRE:
                       x qua ref(inst).gcl:=none; x:= x + x.pp.lng;

                  endcase;
                endrepeat;
                pool:= pool.suc;
            while pool<>none       do endrepeat;
       endif;

       pool:=first_pool; x:=y:=filled:=pool.startgc;
       z:=pool.nxt;
       if z <> none then sort:=z.sort; z.sort:=S_NOSORT endif;
---    repeat
       REP7:REP6:REP5:REP4:REP3:REP2:REP1:
%+D       if x.sort > MAX_SORT then
%+CD         edEobj(x);
%+D          IERR_R("STRG.P4-3") endif;
          case 0:MAX_SORT (x.sort)

          when S_SUB,S_PRO,S_THK,S_ATT,S_DET,S_RES,S_TRM,S_PRE:
               x qua ref(inst).gcl:=none; x:= x + x.pp.lng
               goto REP1;

          when S_TXTENT:
               x.sl:=none;
               --x:=x + rec_size(txtent,x.ncha);
               x:=x + size(txtent:x.ncha);
               goto REP2;

          when S_SAV:
               x qua ref(inst).gcl:=none; x:= x + x.lng
               goto REP3;

          when S_ARHEAD,S_ARBODY,S_ARBREF,S_ARBTXT,S_ARENT2,S_ARREF2,
               S_ARTXT2,S_ARENT1,S_ARREF1,S_ARTXT1:
               x.sl:=none; x:= x + x.lng
               goto REP4;

          when S_NOSORT:
               lng:=x-y;
               if y <> filled then MOVEIN(y,filled,lng) endif;
               pool.nxt:=filled+lng;
               if z = x
               then gap:=pool.nxt; gap.sort:=S_GAP;
                    y:=x; x.sort:=sort; pool:=pool.suc;
                    filled:=pool.startgc; gap.pp:=filled qua ref();
                    z:=pool.nxt;
                    if z <> none
                    then sort:=z.sort; z.sort:=S_NOSORT endif;
               else if z = none then goto ENDP4 endif;
                    IERR_R("STRG.P4-4");
               endif;
               goto REP5;

          when S_GAP:
               lng:= x - y;
               if y <> filled then MOVEIN(y,filled,lng) endif
               filled:= filled + lng; x:= x.pp qua ref(); y:=x;
               goto REP6;

          when S_ALLOC:
               if x<>x.sl
               then call Pmovit(x qua ref(inst).moveIt)
                               ( name(x qua ref(nonObj).cha) ,
                                 name(x.sl qua ref(nonObj).cha) ) endif
               x.sl:=none; x:= x + x.lng
               goto REP7;

          endcase;
---    while true do endrepeat;

ENDP4: pool.nxt.sort:=S_NOSORT;
%+CD   PRT("**********  END PASS 4  ************");
 end;
%title   ********   check storage consistency   *********
--- routine ED_LSC; import ref(inst) ins;
--- begin integer p,lng; ref(ptpExt) xpp; label outermost;
---       infix(string) fld;
---       if    ins=curins then outermost:=GTOUTM
---       elsif ins.lsc<>nowhere then outermost:=ins.lsc
---       else  goto E endif;
---       ED_STR("line ");
---       fld:=WFIELD(25); p:=bio.utpos-25; lng:=GTLNID(outermost,fld);
---       if status = 0 then bio.utpos:=p+lng endif; status:=0;
---  E:   if (ins.pp<>none)
---       then xpp:=ins.pp.xpp;
---            if xpp<>none then if xpp.idt<>none
---            then edchar(' '); ed_idt(xpp.idt) endif endif;
---       endif;
--- end;

%+C routine edOaddr; import ref() val;
%+C begin PTOADR(WFIELD(24),val); status:=0; end;

%+C routine edchar; import character c;
%+C begin if bio.utpos >= utlng then ed_out endif;
%+C       bio.utbuff(bio.utpos):=c; bio.utpos:=bio.utpos+1;
%+C end;

-- %+C routine edAaddr; import field() val;
-- %+C begin PTAADR(WFIELD(12),val); status:=0; end;

%+C Routine edType; import range(0:MAX_TYPE) t;
%+C begin if t > MAX_TYPE
%+C       then ed_str("illegal type code "); ed_int(t);
%+C       else case 0:MAX_TYPE  (t)
%+C            when T_NOTYPE:
%+C            when T_BOO   : ed_str("bool");
%+C            when T_CHA   : ed_str("char");
%+C            when T_SIN   : ed_str("sint");
%+C            when T_INT   : ed_str("int");
%+C            when T_REA   : ed_str("real");
%+C            when T_LRL   : ed_str("long");
%+C            when T_REF   : ed_str("ref");
%+C            when T_PTR   : ed_str("ptr");
%+C            when T_TXT   : ed_str("text"); endcase;
%+C       endif;
%+C end;

%+C routine edEobj; import ref(entity) x;
%+C begin printAll:=true; dmpPool; edSort(x); ed_out end;

%+C routine edSort; import ref(entity) x;
%+C begin ref(ptpExt) xpp; range(0:MAX_BYT) sort; infix(string) str;
%+C       if x=none then ed_str("PTR NONE "); goto X1 endif;
%+C       if printAll then edOaddr(x); edChar(' ') endif;
%+C       if x.sort>MAX_SORT
%+C       then ed_str(" illegal sort code ");
%+C            ed_int(x.sort); printAll:=true;
%+C       else case 0:MAX_SORT (x.sort)
%+C            when S_TRM   : ed_str("TRM");
%+C    N1:N2:N3:N4: ed_str(if x qua ref(inst).gcl=none
%+C                        then "* " else "  ");
%+C                 xpp:=x qua ref(inst).pp.xpp;
%+C                 if xpp<>none then if xpp.idt<>none
%+C                 then ed_idt(xpp.idt) endif endif;
%+C            when S_PRO   : ed_str("PRO"); goto N1;
%+C            when S_ATT   : ed_str("ATT"); goto N2;
%+C            when S_DET   : ed_str("DET"); goto N3;
%+C            when S_RES   : ed_str("RES"); goto N4;
%+C            when S_SUB   : ed_str("SUB");
%+C    G1:G2:G3:    ed_str(if x qua ref(inst).gcl=none
%+C                        then "*" else " ");
%+C            when S_SAV   : ed_str("SAV"); goto G3;
%+C            when S_PRE   : ed_str("PRE"); goto G2;
%+C            when S_THK   : ed_str("THK"); goto G1;
%+C            when S_ARENT1: ed_str("Ar1");
%+C    E1:E2:E3:    ed_str(if x.sl=none then "* " else "  ");
%+C                 edType(x.misc); 
%+C            when S_ARHEAD: ed_str("Ahd"); goto E3;
%+C            when S_ARBODY: ed_str("Abd"); goto E2;
%+C            when S_ARENT2: ed_str("Ar2"); goto E1;
%+C            when S_TXTENT: ed_str(if x.misc=0 then "TXT" else "TXc");
%+C                 ed_str(if x.sl=none then "*" else " ");
%+C                 if printall then if x.ncha<>0
%+C                 then str.nchr:=x.ncha;
%+C                      if str.nchr>20 then str.nchr:=20 endif;
%+C                      str.chradr:=name(x qua ref(txtent).cha);
%+C                      ed_str(" <"); ed_str(str); edchar('>');
%+C                 endif endif;
%+C            when S_ARREF2: ed_str("rA2");
%+C    S1:S2:S3:S4:S5: ed_str(if x.sl=none then "*" else " ");
%+C            when S_ARREF1: ed_str("rA1"); goto S5;
%+C            when S_ARBREF: ed_str("rAn"); goto S4;
%+C            when S_ARBTXT: ed_str("tAn"); goto S3;
%+C            when S_ARTXT1: ed_str("tA1"); goto S2;
%+C            when S_ARTXT2: ed_str("tA2"); goto S1;
%+C            when S_ALLOC : ed_str("BUF*");
%+C            when S_GAP   : ed_str("gap ");
%+C            when S_NOSORT: ed_str("nosort");
%+C            endcase;
%+C       endif;
%+C X1:end;

%+C Routine findEntity;
%+C import ref(entity) pt; export range(0:MAX_BYT) val;
%+C begin ref(entity) x; ref(area) p;
%+C       -- no messages here, any error will be caught in main check
---       p:=search_pool;
%+C       p:=first_pool;
%+C       repeat
%+C          x:=p+size(area);
%+C          if x<=pt then if pt<p.nxt
%+C          then repeat while x <= pt
%+C               do val:=x.sort;
%+C                  if x=pt then goto E1 endif;
%+C                  if val > MAX_SORT
%+C                  then -- ERROR - illegal object (no message here!)
%+C                       x:=p.nxt; -- terminate scan of this pool
%+C                  else case 0:MAX_SORT (val)
%+C                       when S_TXTENT:
--%+C                            x:= x + rec_size(txtent,x.ncha);
%+C                            x:= x + size(txtent:x.ncha);
%+C                       when S_NOSORT,S_GAP:
%+C                            x:= p.nxt; -- terminate scan of pool
%+C                       when S_SUB,S_PRO,S_THK,
%+C                            S_ATT,S_DET,S_RES,S_TRM,S_PRE:
%+C                            x:= x + x.pp.lng
%+C                       otherwise  x:= x + x.lng endcase;
%+C                  endif;
%+C          endrepeat endif endif;
---          p:=p.suc; if p=none then p:=first_pool endif;
---       while p<>search_pool do endrepeat;
%+C          p:=p.suc while p <> none do endrepeat
%+C       -- obj not found in pool may be a constant, or bio:
%+C       if    pt = ref(bio) then val:=S_PRE -- bio.sort predefined
%+C       else val:=pt.sort;
%+C            if (val=S_TXTENT) and (pt.misc<>0) then -- ok
%+C            else  val:=255; goto E2 endif; -- illegal pointer
%+C       endif
%+C E1:E2:end;

%+C routine attrError;
%+C import name(ref(entity)) x; infix(string) mess;
--%+C begin ptrError(if obj<>none then obj else conv_ref(x));
%+C begin ptrError(if obj<>none then obj else x qua ref());
%+C       ed_str(" - attr. at ");
--%+C       edOaddr(conv_ref(x)); ed_str(" + ");
%+C       edOaddr(x qua ref()); ed_str(" + ");
--%+C       PTAADR(WFIELD(6),conv_field(x)); status:=0;
%+C       PTAADR(WFIELD(6),x qua field()); status:=0;
%+C       ed_str(mess);
%+C end;

%+C routine ptrError; import ref(entity) obj;
%+C begin ed_out;
%+C       ed_str("*** ERROR: INVALID pointer in object starting at ");
%+C       edOaddr(obj); ed_out; dmpPool;
%+C end;

%+C Routine chk_tx; import ref(entity) x;
%+C begin if x <> none then
%+C          if findEntity(x) <> S_TXTENT
%+C          then ptrError(obj);
%+C               ed_str("  ? no text obj at "); edEobj(x); ed_out;
%+C          endif;
%+C       endif;
%+C end;

%+C Routine chk_hd;
%+C begin ref(arhead) x;
%+C       x:=obj qua ref(arbody).head;
%+C       if x <> none then
%+C          if findEntity(x) <> S_ARHEAD
%+C          then ptrError(obj);
%+C               ed_str("  ? no array head at "); edEobj(x); ed_out;
%+C          endif;
%+C       endif;
%+C end;

%+C Routine chk_pt; import name(ref(entity)) q;
%+C begin range(0:MAX_BYT) val;
%+C       if q<>noname then if var(q) <> none then
%+C          val:=findEntity(var(q));
%+C          if (val=0) or (val>MAX_SORT) or (val=S_GAP)
%+C          then attrError(q,", illegal ptr ");
%+C               edOaddr(var(q)); edchar('/'); ed_int(var(q).sort);
%+C               ed_out endif;
%+C       endif endif;
%+C end;

%+C Routine chk_rf; import ref(entity) x;
%+C begin range(0:MAX_BYT) val;
%+C       if atest then val:=findEntity(x) else val:=x.sort endif;
%+C       if val=S_TRM then goto E1; endif;
%+C       if val=S_DET then goto E2; endif;
%+C       if val=S_RES then goto E3; endif;
%+C       if val=S_ATT then goto E4; endif;
%+C       ptrError(obj);
%+C       ed_str("  ? invalid ref at "); edEobj(x); ed_out;
%+C E1:E2:E3:E4:
%+C end;

%+C Routine chk_sldl;
%+C begin range(0:MAX_BYT) val; ref(entity) link;
%+C       link:=obj.sl;
%+C       if link <> none then
%+C          val:=if atest then findEntity(link) else link.sort;
%+C          if val>MAX_SORT
%+C          then E1: ptrError(obj);
%+C               ed_str("  ? Invalid SL points to ");
%+C               edEobj(link); ed_out;
%+C          else case 0:MAX_SORT (val)
%+C               when S_SUB,S_PRO,S_PRE,S_ATT,S_DET,S_RES,S_TRM:
%+C               otherwise goto E1 endcase;
%+C          endif;
%+C       endif;
%+C       link:=obj qua ref(inst).dl;
%+C       if link <> none then
%+C          val:=if atest then findEntity(link) else link.sort;
%+C          if val>MAX_SORT
%+C          then E2: ptrError(obj);
%+C               ed_str("  ? invalid DL points to ");
%+C               edEobj(link); ed_out;
%+C          else case 0:MAX_SORT (val)
%+C               when S_SUB,S_PRO,S_PRE,S_ATT,S_DET,S_RES,S_TRM,
%+C                    S_THK,S_SAV:
%+C               otherwise goto E2 endcase;
%+C          endif;
%+C       endif;
%+C end;

%+C Routine dmpPool;
%+C begin ref(area) p;
%+C       if noDump
%+C       then noDump:=false; bio.pgsize:=0;
%+C            if bio.utpos<>0 then ed_out;
%+C                 PRT("   - ERROR in this object");
%+C            endif;
%+C            PRT("***********   STORAGE  SUMMARY   ***********");
%+C            PRT(STRGmess);
%+C            dmpBIO;
%+C            p:=first_pool;
%+C            PRT("POOL   first        next         limit");
%+C            repeat
%+C               ed_str("#"); ed_int(p.sequ);
%+C               if p.sequ<10 then ed_str("  ")
%+C               elsif p.sequ<100 then edchar(' ') endif;
%+C                            edOaddr(p+size(area));
%+C               edchar(' '); edOaddr(p.nxt);
%+C               edchar(' '); edOaddr(p.lim);
%+C               if p.startgc<>(p+size(area))
%+C               then edchar(' '); edOaddr(p.startgc); endif;
%+C               ed_out;
%+C               p:=p.suc
%+C            while p<>none       do endrepeat;
%+C            PRT("=========   storage  summary end   =========");
%+C            ed_out;
%+C       endif;
%+C end;

%+C Routine dmpBIO;
%+C begin ed_out; ed_str("   garbcount "); ed_int(garb_count);
%+C       if frozen then ed_str(" (frozen) bioref:")
%+C                 else ed_str("          bioref:") endif;
%+C       edOaddr(bioref); ed_out;
%+C       ed_str("   first/last/current pool: ");
%+C       ed_int  (first_pool.sequ); edchar(' ');
%+C       ed_int  (last_pool.sequ); edchar(' ');
%+C       ed_int  (current_pool.sequ); ed_out;
%+C       ed_str("BIO: nxtAdr/lstAdr ");
%+C       edOaddr(bio.nxtAdr); edchar(' '); edOaddr(bio.lstAdr); ed_out;
%+C       ed_str("     sysin/out:"); edOaddr(bio.sysin);
%+C       edchar(' '); edOaddr(bio.sysout); ed_out;
%+C       ed_str("     curins:"); edEobj(curins);
%+C       if bio.globalI<>none
%+C       then ed_str(" GLOBAL:"); edEobj(bio.globalI); endif;
%+C       ed_out;
%+C end;

%+C infix(string) STRGmess; boolean noDump; ref(entity) obj;
%+C boolean printOut,printAll,atest,preGC,postGC;

%+C Routine chkSTRG; import name(ref(entity)) xpnt;
%+C begin name(ref(entity)) q;
%+C       name(ref(inst)) qref; name(infix(txtqnt)) qtex;
%+C       infix(repdes) repdesc; infix(string) str;
%+C       ref(entity) y,z,zero; ref(ptp) pp; ref(area) p; ref(txtent) tx
%+C       ref(pntvec) refVec; ref(rptvec) repVec; ref(filent) F,N;
%+C       integer i,j,dim1,dim2;
%+C       range(0:MAX_PLV) plv; range(0:MAX_SORT) sort;
%+C       range(0:MAX_REP) np;
%+C       range(0:MAX_ACT) act; range(0:255) oldpgz;
%+C       boolean atst,printobj; -- local for efficiency

%+C       bio.thunks:=none;
%+C       oldpgz:=bio.pgsize; act:=actLvl; actLvl:=ACT_GC;
%+C       rstr_x:=none;  -- forget reusable save-object
%+C       noDump:=true; atst:=atest; printobj:=printout;
%+C       if printobj then dmpPOOL endif;
%+C       PASS1(xpnt);
---       p:=search_pool:=first_pool; obj:=none; CHK_pt(xpnt);
%+C       p:=             first_pool; obj:=none; CHK_pt(xpnt);
%+C       repeat
%+C        if printobj
%+C        then ed_out; ed_str("---   checking pool ");
%+C             ed_int(p.sequ); ed_str(", size of free area: ");
%+C             PTSIZE(WFIELD(12),p.lim-p.nxt); PRT("   ---") endif;
%+C        obj:=p+size(area); zero:=p.startgc;
%+C        repeat while obj < p.nxt
%+C        do if printobj
%+C           then edSort(obj);
%+C                if obj<zero then ed_str(" ### ") endif endif;
%+C           sort:=obj.sort; if sort > MAX_SORT
%+C           then dmpPool; edEobj(obj); PRT(" *** imposs. sort");
%+C                obj:=p.nxt; -- terminate scan of this pool
%+C           else case 0:MAX_SORT (sort)
%+C
%+C           when S_SUB,S_PRO,S_THK:
%+C                pp:= obj qua ref(inst).pp; plv:= 0; goto CHKref;
%+C
%+C           when S_ATT,S_DET,S_RES,S_TRM,S_PRE:
%+C                pp:= obj qua ref(inst).pp;
%+C                plv:= pp qua ref(claptp).plv;
%+C                obj qua ref(inst).gcl:=none;
%+C     CHKref:    chk_sldl;
%+C                repeat refVec:=pp.refVec;
%+C                    if refVec <> none
%+C                    then j:=0; repeat while j < refVec.npnt
%+C                         do q:= obj + refVec.pnt(j); j:=j+1;
%+C                            if var(q)<>none
%+C                            then sort:=var(q).sort;
%+C                                 if atst then chk_pt(q)
%+C                                 elsif sort>=S_ALLOC
%+C                                 then if sort<>S_TXTENT
%+C                                      then chk_pt(q) endif
%+C                                 elsif sort=0 then chk_pt(q) endif;
%+C                            endif;
%+C                         endrepeat;
%+C                    endif;
%+C                    if pp.repVec <> none
%+C                    then repVec:=pp.repVec;
%+C                         j:=repVec.npnt; repeat while j <> 0
%+C                         do j:=j-1;
%+C                            repdesc:=repvec.rep(j); np:=repdesc.nelt
%+C                            if repdesc.type=T_REF
%+C                            then qref:=obj+repdesc.fld;
%+C                                 repeat if var(qref)<>none
%+C                                    then chk_rf(var(qref)) endif
%+C                                 while np<>0 do np:=np-1;
%+C                                   qref:=name(var(qref)(1)) endrepeat
%+C                            else -- must be T_TXT
%+C                                 qtex:=obj+repdesc.fld;
%+C                                 repeat tx:=var(qtex).ent;
%+C                                    if tx<>none then
%+C                                       if (tx.sort<>S_TXTENT) or atst
%+C                                    then chk_tx(tx) endif endif;
%+C                                 while np<>0 do np:=np-1;
%+C                                   qtex:=name(var(qtex)(1)) endrepeat
%+C                            endif
%+C                         endrepeat
%+C                    endif
%+C                while plv<>0 do plv:=plv-1;
%+C                             pp:=pp qua ref(claptp).prefix(plv);
%+C                endrepeat;
%+C                obj:= obj + obj.pp.lng;
%+C
%+C           when S_TXTENT:
--%+C                if rec_size(txtent,obj.ncha)=size(entity)
%+C                if size(txtent:obj.ncha)=size(entity)
%+C                then -- MAYBE FREEZE LIMIT DUMMY OBJ
%+C                     if (obj+size(entity)<>p.startgc) or (not frozen)
%+C                     then dmpPool; PRT(" *** empty text obj") endif;
%+C                endif;
%+C                obj.sl:=none;
--%+C                obj:= obj + rec_size(txtent,obj.ncha);
%+C                obj:= obj + size(txtent:obj.ncha);
%+C
%+C           when S_ALLOC:
%+C                obj:= obj + obj.lng
%+C
%+C           when S_ARHEAD,S_ARENT1,S_ARENT2:
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARBODY:
%+C                CHK_hd;
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARBREF:
%+C                i:=obj qua ref(arbody).head.nelt; CHK_hd;
%+C                repeat while i >  0 do i:=i-1;
%+C                    y:=obj qua ref(refArr).elt(i);
%+C                    if y<>none then
%+C                       if atst then chk_rf(y)
%+C                       elsif y.sort>=S_ALLOC then chk_rf(y)
%+C                       elsif y.sort=0 then chk_rf(y) endif;
%+C                    endif;
%+C                endrepeat
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARBTXT:
%+C                i:=obj qua ref(arbody).head.nelt;
%+C                CHK_hd;
%+C                repeat while i >  0 do i:=i-1; 
%+C                    tx:=obj qua ref(txtArr).elt(i).ent;
%+C                    if tx<>none then if (tx.sort<>S_TXTENT) or atst
%+C                    then CHK_tx(tx) endif endif;
%+C                endrepeat;
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARREF2:
%+C                dim1:=obj qua ref(arent2).ub_1
%+C                     -obj qua ref(arent2).lb_1+1
%+C                dim2:=obj qua ref(arent2).ub_2
%+C                     -obj qua ref(arent2).lb_2+1
%+C                if dim1 > 0 then if dim2 > 0 then i:= dim1*dim2;
%+C                repeat i:= i - 1 while i >= 0 do
%+C                    y:=obj qua ref(refAr2).elt(i);
%+C                    if y<>none then
%+C                       if atst then chk_rf(y)
%+C                       elsif y.sort>=S_ALLOC then chk_rf(y)
%+C                       elsif y.sort=0 then chk_rf(y) endif;
%+C                    endif;
%+C                endrepeat
%+C                endif endif -- else (dim1 or dim2 <= 0) no elements
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARTXT2:
%+C                dim1:=obj qua ref(arent2).ub_1
%+C                     -obj qua ref(arent2).lb_1+1
%+C                dim2:=obj qua ref(arent2).ub_2
%+C                     -obj qua ref(arent2).lb_2+1
%+C                if dim1 > 0 then if dim2 > 0 then i:= dim1*dim2;
%+C                repeat i:= i - 1 while i >= 0 do
%+C                    tx:=obj qua ref(txtAr2).elt(i).ent;
%+C                    if tx<>none then if (tx.sort<>S_TXTENT) or atst
%+C                    then chk_tx(tx) endif endif;
%+C                endrepeat;
%+C                endif endif -- else (dim1 or dim2 <= 0) no elements
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARREF1:
%+C                i := obj qua ref(arent1).ub-obj qua ref(arent1).lb+1;
%+C                repeat while i >  0 do i:=i-1;
%+C                    y:=obj qua ref(refAr1).elt(i);
%+C                    if y<>none then
%+C                       if atst then chk_rf(y)
%+C                       elsif y.sort>=S_ALLOC then chk_rf(y)
%+C                       elsif y.sort=0 then chk_rf(y) endif;
%+C                    endif;
%+C                endrepeat
%+C                obj.sl:=none; obj:= obj + obj.lng
%+C
%+C           when S_ARTXT1:
%+C                i := obj qua ref(arent1).ub-obj qua ref(arent1).lb+1;
%+C                repeat while i >  0 do i:=i-1;
%+C                    tx:=obj qua ref(txtAr1).elt(i).ent;
%+C                    if tx<>none then if (tx.sort<>S_TXTENT) or atst
%+C                    then CHK_tx(tx) endif endif;
%+C                endrepeat;
%+C                obj.sort:=S_ARTXT1; obj.sl:=none; obj:= obj + obj.lng
%+C      
%+C           when S_SAV: chk_sldl;
%+C                if obj.lng > size(savent)
%+C                then init_pointer(obj+size(savent));
%+C                     repeat y:= get_pointer while y <> none
%+C                     do sort:=y.sort;
%+C                        if (sort=0) or (sort>MAX_SORT) or
%+C                           (sort=S_GAP) or atst
%+C                        then CHK_pt(name(y)) endif;
%+C                     endrepeat;
%+C                endif;
%+C                obj qua ref(inst).gcl:=none; obj:= obj + obj.lng;
%+C
%+C           otherwise -- NOSORT,GAP: ERROR - object of illegal sort
%+C                edEobj(obj); dmpPool; PRT(" *** illegal sort");
%+C                obj:= p.nxt; -- terminate scan of this pool
%+C
%+C           endcase;
%+C           if printobj then ed_out endif;
%+C          endif;
%+C        endrepeat;
%+C        if obj <> p.nxt
%+C        then  dmpPool; ed_str(" *** p.nxt inconsistent in pool ");
%+C              ed_int(p.sequ); ed_out;
%+C        endif;
%+C        --- perform spot-check in free area ---
%+C        y:=p.nxt; repeat while y < p.lim do
%+C           if y.sl<>none
%+C           then ed_str("NON-ZERO CONTENTS OF FREE AREA at ");
%+C                edOaddr(y); goto NXTP endif;
%+C           y:=y+size(entity) endrepeat;    NXTP:
---        search_pool:=p; p:=p.suc;
%+C                        p:=p.suc;
%+C
%+C       while p<>none       do endrepeat;
%+C       bio.gcl:=none; actLvl:=act;
%+C       if printobj then PRT("*********   CHK end   *********") endif;
%+C end;

end;
