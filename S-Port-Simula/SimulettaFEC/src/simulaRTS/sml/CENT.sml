Module cent("RTS");
 begin sysinsert rt,sysr,knwn,util,strg;

       -----------------------------------------------------------
       ---  COPYRIGHT 1988 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---           P O R T A B L E     S I M U L A           ---
       ---            R U N T I M E     S Y S T E M            ---
       ---                                                     ---
       ---          C o n t r o l    T r a n s f e r           ---
       ---                  a n d    T e x t                   ---
       ---                                                     ---
       -----------------------------------------------------------

%title ***  Scan prefix/suffix chain for dcl/stm/inr labels ***

 -- Optimising the jumping around between different aspects of an
 -- object. The compiler will have generated the prototype attributes
 -- DCL, STM, INR for each prefix level of an object. These routines
 -- scan these labels in correct sequence and return the program
 -- point at which current instance should continue execution
 -- FEC will generate a valid INR always - i.e. either the "first"
 -- after-inner sequence, if any, or the label E_OBJ.
 -- (it must be done like this since
 -- E_CLA_PRE cannot be called directly from another routine).
                        -- peter jensen june 88

 Visible macro FIND_DCL(0)
 begin
       repeat while plev <= cpp.plv do
%  +M       ED_STR("CENT.FIND_DCL: plev="); ED_INT(plev); ED_OUT;   
%  +M       ED_STR("CENT.FIND_DCL: cpp="); ED_OADDR(cpp); ED_OUT;   
%  +M       ED_STR("CENT.FIND_DCL: cpp.prefix(plev)="); ED_OADDR(cpp.prefix(plev)); ED_OUT;   
          psc:=cpp.prefix(plev).dcl;
          if psc <> nowhere then goto DFOUND endif;
          plev:=plev+1 endrepeat;
 endmacro;


 Visible macro FIND_STM(0)
 begin
       repeat while plev <= cpp.plv
       do psc:=cpp.prefix(plev).stm;
          if psc <> nowhere then goto SFOUND endif;
          plev:=plev+1 endrepeat;
       psc:=cpp.prefix(cpp.plv).cntInr;
 endmacro;


 Visible routine nxtDcl;
 import range(0:MAX_PLV) plev; -- the level to start the search
 exit label psc;            -- where to continue
 begin ref(claptp) cpp;
       cpp:=curins.pp;
                FIND_DCL();
       plev:=0; FIND_STM();
 SFOUND:DFOUND:
 end;


 Visible routine nxtStm;
 import range(0:MAX_PLV) plev; -- the level to start the search
 exit label psc;            -- where to continue
 begin ref(claptp) cpp;
       cpp:=curins.pp;
       FIND_STM();
 SFOUND:
 end;

%title ***   C l a s s  /  P r o c e d u r e   o b j e c t   ***

 Visible routine B_SUB;
 import ref(subptp) spp;
 begin ref(inst) ins;      -- The one we will allocate now.
       ---  Set the program point where current instance will continue.
       curins.lsc:=spp.cnt;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC2(S_SUB,ins,spp.lng);
       ---  Fill in Sub-Block Attributes.
       ins.sl:=ins.dl:=curins; ins.pp:=spp;
       ---  Enter sub-block instance, we will return to its code.
       curins:=ins;
%-X    if bio.trc then curins.lsc:=GTOUTM; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
% +M    DMPENT(curins);
 end;


 Visible routine B_PRO;
 import ref(inst) sl; ref(proptp) ppp; exit label psc;
 begin ref(inst) ins;     -- The one we will allocate now.
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_PRO,ins,ppp.lng,sl);
       ---  Fill in procedure attributes.
       ins.sl:=sl; ins.pp:=ppp; ins.dl:=curins;
       ---  Enter procedure instance's code.
       curins:=ins;
%-X    if bio.trc then curins.lsc:=ppp.start; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
       psc:=ppp.start;
 end;

 Visible routine A_PRO;
 import ref(inst) sl; ref(proptp) ppp;
 export ref(inst) ins;     -- The one we will allocate now.
 begin  ---  Allocate storage. If necessary, call the garbage collector.
%  +M	    ED_STR("CENT.A_PRO: BEFORE ALLOC lng="); ED_SIZE(ppp.lng); ED_STR(", bio.nxtAdr="); ED_OADDR(bio.nxtAdr); ED_STR(", bio.lstAdr="); ED_OADDR(bio.lstAdr); ED_OUT;

       ALLOC(S_PRO,ins,ppp.lng,sl);
        
%  +M	    ED_STR("CENT.A_PRO: AFTER  ALLOC lng="); ED_SIZE(ppp.lng); ED_STR(", bio.nxtAdr="); ED_OADDR(bio.nxtAdr); ED_STR(", bio.lstAdr="); ED_OADDR(bio.lstAdr); ED_OUT;
        ---  Fill in procedure attributes.
        ins.sl:=sl; ins.pp:=ppp;
 end;

 Visible routine I_PRO;
 import ref(inst) ins;      -- The procedure instance.
 exit label psc;
 begin label cont;  -- SIMOB only
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       ---  Link to dynamic enclosure.
       ins.dl:=curins;
       ---  Enter procedure instance's code.
       curins:=ins;
%+X    psc :=curins.pp qua ref(proptp).start;
%-X    cont:=curins.pp qua ref(proptp).start;
%-X    if bio.trc then curins.lsc:=cont; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
%-X    psc:=cont;
 end;

%  record anyPro:inst; begin infix(quant) val end;

%  infix(anyPro) dummy_pro;

-- Visible routine E_QUICK; -- end  notype quick procedure
-- exit label psc;
-- begin ref(inst) curproc; -- curins at entry, ZERO DL!!!
--       curproc:=curins; curins:=curins.dl; curproc.dl:=none;
--       psc:=curins.lsc;
--       -- if bio.trc then ???
-- end

-- Visible routine E_TYPE_Q; -- end  typed quick procedure
-- exit label psc;
-- begin ref(inst) curproc; -- curins at entry, ZERO DL!!!
--       curproc:=curins; curins:=curins.dl; curproc.dl:=none;
--       -- move value and set psc
--       dummy_pro.val:=curproc qua anyPro.val;
--       tmp.pnt:=ref(dummy_pro); psc:=curins.lsc;
--       -- if bio.trc then ???
-- end

 Visible routine E_PRO; -- end  notype-procedure/subblock;
 exit label psc;        -- changed for procedures, not for subblocks
 begin ref(inst) curproc; -- curins at entry
       curproc:=curins; curins:=curproc.dl;
%-X    if bio.trc then bio.obsEvt:=EVT_END; tmp.pnt:=curproc;
%-X       tmp.pnt.lsc:=GTOUTM ; call PSIMOB(smb); tmp.pnt:=none endif;
       if curproc.sort=S_PRO then psc:=curins.lsc endif;
       -- Now: check to see whether the instance is on top of stack
       if curproc+curproc.pp.lng = bio.nxtAdr -- expansion of RECLAIM
       then ZEROAREA(curproc,bio.nxtAdr); bio.nxtAdr:=curproc endif;
 end
 
 Visible routine E_FUNC; -- end  type procedure (keep instance)
 exit label psc;
 begin ref(inst) curproc; -- curins at entry
       curproc:=curins; curins:=curins.dl;
%-X    if bio.trc then bio.obsEvt:=EVT_END; tmp.pnt:=curproc;
%-X       tmp.pnt.lsc:=GTOUTM ; call PSIMOB(smb) endif;
	   tmp.pnt:=curproc;
       psc:=curins.lsc;
 end
 
%   Visible routine OLD_E_FUNC; -- end  type procedure
%   exit label psc;
%   begin ref(inst) curproc; -- curins at entry
%   
%  %+M	   size lng; lng:=curins.pp.lng;
%  % +M    DMPENT(curins);
%  
%         curproc:=curins; curins:=curins.dl;
%  %-X    if bio.trc then bio.obsEvt:=EVT_END; tmp.pnt:=curproc;
%  %-X       tmp.pnt.lsc:=GTOUTM ; call PSIMOB(smb) endif;
%         dummy_pro.val:=curproc qua anyPro.val; tmp.pnt:=ref(dummy_pro);
%  %+M	   ED_STR("CENT.E_FUNC: dummy_pro="); ED_OADDR(ref(dummy_pro)); ED_OUT;
%         psc:=curins.lsc;
%         -- Now: check to see whether the instance is on top of stack
%         if curproc+curproc.pp.lng = bio.nxtAdr -- expansion of RECLAIM
%         then ZEROAREA(curproc,bio.nxtAdr); bio.nxtAdr:=curproc;
%         endif;
%  %+M	   ED_STR("CENT.E_FUNC: AFTER  RECLAIM lng="); ED_SIZE(lng); ED_STR(", bio.nxtAdr="); ED_OADDR(bio.nxtAdr); ED_STR(", bio.lstAdr="); ED_OADDR(bio.lstAdr); ED_OUT;
%   end

 Visible routine B_CLA;
 import ref(inst) sl; ref(claptp) cpp; exit label psc;
 begin ref(inst) ins;     -- The one we will allocate now.
       range(0:255) plev; -- used by FIND_macros
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_ATT,ins,cpp.lng,sl);
       ---  Fill in class attributes.
       ins.sl:=sl;                  ins.pp:=cpp;
       ---  Enter object's code.
       plev:=0; FIND_DCL();    --   find dcl-code
       plev:=0; FIND_STM();    --   - or statements before/after inner
       if psc = E_OBJ
       then -- nothing found, perform simplified E_CLA_PRE
            ins.sort:=S_TRM; tmp.pnt:=ins;
            psc:=curins.lsc;
       else
 DFOUND: SFOUND: -- something to execute, set dynamic link
            ins.dl:=curins; curins:=ins;
%-X         if bio.trc then curins.lsc:=psc; bio.obsEvt:=EVT_BEG;
%-X            psc:=curins.dl.lsc;  -- enable GTOUTM
%-X            call PSIMOB(smb); psc:=curins.lsc endif;
       endif;
 end;

 Visible routine B_LOCL;
 import ref(proptp) ppp; exit label psc;
 begin ref(inst) ins;     -- The one we will allocate now.
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC2(S_PRO,ins,ppp.lng);
       ---  Fill in procedure attributes.
       ins.sl:=ins.dl:=curins; ins.pp:=ppp;
       ---  Enter procedure instance's code.
       curins:=ins;
%-X    if bio.trc then curins.lsc:=ppp.start; bio.obsEvt:=EVT_BEG;
%-X       call PSIMOB(smb) endif;
       psc:=ppp.start;
 end;

 Visible routine A_LOCL;
 import ref(ptp) pp;
 export ref(inst) ins;
 begin ---  Allocate storage. If necessary, call the garbage collector.
% +M	   ED_STR("CENT.A_LOCL: pp="); ED_OADDR(pp); ED_OUT;
% +M	   ED_STR("CENT.A_LOCL: lng="); ED_SIZE(pp.lng); ED_OUT;
       ALLOC2(S_PRO,ins,pp.lng);
       ---  Fill in class attributes.
       ins.sl:=curins; ins.pp:=pp;
% +M	   ED_STR("CENT.A_LOCL: EXPORT ins=");      ED_OADDR(ins); ED_OUT;
% +M	   ED_STR("CENT.A_LOCL: EXPORT ins.sort="); ED_INT(ins.sort); ED_OUT;
% +M	   ED_STR("CENT.A_LOCL: EXPORT ins.sl=");   ED_OADDR(ins.sl); ED_OUT;
 end;

 Visible routine B_REC; -- class with no code (with/without param)
 import ref(inst) sl; ref(claptp) cpp;
 export ref(inst) ins;    -- The one we will allocate now.
 begin ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_TRM,ins,cpp.lng,sl);
       ---  Fill in class attributes.
       ins.sl:=sl; ins.pp:=cpp;
 end;

 Visible routine A_CLA; -- allocate class with code
 import ref(inst) sl; ref(claptp) cpp;
 export ref(inst) ins;    -- The one we will allocate now.
 begin ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC(S_ATT,ins,cpp.lng,sl);
       ---  Fill in class attributes.
       ins.sl:=sl; ins.pp:=cpp;
 end;

 Visible routine I_CLA;
 import ref(inst) ins;     -- The object.
 exit label psc;
 begin range(0:255) plev; ref(claptp) cpp; -- used by FIND_macros
       ---  Set the program point where current instance will continue.
       curins.lsc:=psc;
       cpp:=   ins.pp qua ref(claptp);
       plev:=0; FIND_DCL();    --  find dcl-code
       plev:=0; FIND_STM();    --   - or statements before/after inner
       if psc = E_OBJ
       then -- nothing found, perform simplified E_CLA_PRE
            ins.sort:=S_TRM; tmp.pnt:=ins;
            psc:=curins.lsc;
       else
 DFOUND: SFOUND: -- something to execute, set dynamic link
            ins.dl:=curins; curins:=ins; curins.sort:=S_ATT;
%-X         if bio.trc then curins.lsc:=psc; bio.obsEvt:=EVT_BEG;
%-X            psc:=curins.dl.lsc;  -- enable GTOUTM
%-X            call PSIMOB(smb); psc:=curins.lsc endif;
       endif;
 end;

 -- Visible routine E_CLA_PRE;
            routine E_CLA_PRE;
 exit label psc;
 begin ref(inst) dl;   -- A temporary to the instance dynamically
                           -- enclosing the resumed object ('curins').
       ref(inst) main; -- The head of the main component and also
                       -- the head of the quasi-parallel system.
       tmp.pnt:=curins;
       ---  Treat attached first, it is probably most common.
       if curins.sort = S_ATT -- set tmp, this may be NEW class
       then ---  Make the dynamic enclosure the new current instance.
            ---  Make sure that no garbage is accessible.
            curins:=curins.dl;
%-X         if bio.trc then bio.obsEvt:=EVT_END; call PSIMOB(smb) endif;
            tmp.pnt.sort:=S_TRM; tmp.pnt.dl:=none;

       elsif curins.sort = S_RES
       then ---  Treat a resumed and operating object.
            ---  It is the head of an object component. This component
            ---  contains no other instances than the class object. The
            ---  class object enters the terminated state, and the
            ---  object component disappears from its system. The main
            ---  component of that system takes its place as the
            ---  operating component of the system.
            ---  Invariant:  curins.sort = S_RES and curins.dl = main.sl
            ---  Find main component (and system) head. It must be the
            ---  static enclosure since the object has been RESUMEd.
            main  := curins.sl;
            ---  Ignore any temporary instances.
            repeat while main.dl.lsc = nowhere do main:=main.dl endrepeat
            ---  The main component becomes the operating component.
            curins:=main.dl; main.dl:=tmp.pnt.dl;
%-X         if bio.trc then bio.obsEvt:=EVT_END; call PSIMOB(smb) endif;
            tmp.pnt.sort:=S_TRM; tmp.pnt.dl:=none; -- avoid garbage

       elsif curins.sort = S_PRE
       then ---  Treat a prefixed block instance.
            ---  Invariant:  curins.sort = S_PRE, but check it anyway.
            curins:=curins.dl; -- update before "GC" below
%-X         if bio.trc then bio.obsEvt:=EVT_END; call PSIMOB(smb) endif;
            RECLAIM(tmp.pnt,tmp.pnt.pp.lng);  --  try reuse of instance
       else IERR_R("E_CLA_PRE") endif;
       psc:=curins.lsc;
 end;

 Visible routine B_PRE;
 import ref(claptp) cpp; exit label psc;
 begin ref(inst) ins;   -- The one we will allocate now.
       range(0:255) plev;
       label cont;      -- SIMOB only
       ---  Set the program point where current instance will continue.
%-X    cont:=psc;
       curins.lsc:=cpp.cntInr;
       ---  Allocate storage. If necessary, call the garbage collector.
       ALLOC2(S_PRE,ins,cpp.lng);
       ---  Fill in class attributes.
       ins.sl:=ins.dl:=curins; ins.pp:=cpp;
       ---  Enter prefixed block instance's code, i.e. start execution
       ---  of the declaration code for the class on prefix level zero.
       curins:=ins;
       plev:=0; FIND_DCL();    --  find dcl-code
       plev:=0; FIND_STM();    --   - or statements before/after inner
       --- no test here on psc: a prefixed block without statements
       --- is not worth wasting RTS-instructions !!!
 DFOUND: SFOUND:
%-X    if bio.trc then curins.lsc:=psc; bio.obsEvt:=EVT_BEG;
%-X       psc:=cont;  -- enable GTOUTM
%-X       call PSIMOB(smb); psc:=curins.lsc endif;
 end;

 Visible routine E_GOTO;
 import infix(labqnt) lab; exit label psc;
 begin ref(inst) dl;     -- Temporary to 'curins.dl'.
       ref(inst) ins;    -- Temporary to 'curins'.
       ref(inst) main;   -- The head of the main component and also
                             -- the head of the quasi-parallel system.
       ref(pntvec) refVec;  -- Vector containing connection-ref's.
%-X    if bio.trc then bio.smbP1:=curins endif;

       ---  Set the program point where the current instance may finish.
       curins.lsc:=psc;
       --- scan operating chain (etc., see S_RES) using temp 'ins'
       --- to ensure correct source position for ERROR message
       ins:=curins;
       repeat while ins <> lab.sl
       do ---  If jumping somewhere we shouldn't, report the error.
%         if ins = ref(bio) then ERROR(ENO_GOTO_1) endif;
          assert ins <> ref(bio) skip ERROR(ENO_GOTO_1) endskip;
          --- invariant: ins cannot be detached or terminated
          if ins.sort = S_ATT
          then dl:=ins.dl; ins.sort:=S_TRM; ins.dl:=none;
               ins:=dl;
          elsif ins.sort = S_RES
          then --  Terminate this component head, enter main component.
               dl:=ins.dl; ins.sort:=S_TRM; ins.dl:=none;
               main:=ins.sl; --  as if DETACH.
               ---  Ignore any temporary instances.
               ---  Those of ins have been dealt with in else-branch
               repeat while main.dl.lsc = nowhere
               do main:=main.dl endrepeat;
               ---  The main component becomes the operating component.
               ins:=main.dl; main.dl:=dl;
          else ins:=ins.dl endif;
       endrepeat;
       curins:=ins;

       ---  Remove any temporary instances connected to the destination
       ---  instance, which created the dynamic chain just exited.
       repeat while curins.dl.lsc = nowhere
       do curins.dl := curins.dl.dl endrepeat;

       ---  If the destination instance was previously executing
       ---  inside inspect-statements, make sure that we are zeroing the
       ---  connection-references for all inspect-statements we exited.
       refVec:=curins.pp.refVec;
       if refVec <> none then --- Inserted 1/8-84, GS
          repeat while lab.clv  < refVec.ncon
          do var((curins + (refVec.pnt(lab.clv qua integer)))
                             qua name(ref(inst))):=none;
             lab.clv:=lab.clv + 1;
          endrepeat;
       endif;
       ---  current instance updated. Update current program point too.
       ---  See if we are tracing the execution.
%-X    if bio.trc then curins.lsc:=lab.pad;
%-X       bio.obsEvt:=EVT_GOTO; call PSIMOB(smb) endif
       psc:=lab.pad;
 end;
%title ***   Q P S  -  r o u t i n e s   ***

 Visible routine DETACH;
 import ref(inst) ins;      --  Instance to be detached.
 exit label psc;
 begin ref(inst) dl;    --  Temporary reference to dynamic enclosure
       ref(inst) main;  --  The head of the main component and also
                            --  the head of the quasi-parallel system.
%-X    if bio.trc
%-X    then bio.smbP1:=curins; bio.smbP2:=ins; bio.GCval:=ins.sort endif

       ---  Set the program point where current instance is executing.
       curins.lsc:=psc;
----   ---  Detach on a prefixed block is a no-operation.
----   ---  TEMP - this check should be done by compiler
       if ins.sort <> S_PRE then
       --- Make sure that the object is on the operating chain.
       --- Note that a detached or terminated object cannot be on
       --- the operating chain.
       dl:=curins;
       repeat while dl <> ins
       do dl:=dl.dl;
%         if dl = none then  ERROR(ENO_DET_1)  endif;
          assert dl <> none skip ERROR(ENO_DET_1)  endskip;
       endrepeat;

       ---  Treat attached first because FEC uses that
       if ins.sort = S_ATT
       then ins.sort:=S_DET;
            --  Return pointer, if caller is executing NEW <class>
            tmp.pnt:=ins;
            ---  Ignore any temporary instances.
            repeat while ins.dl.lsc=nowhere do ins:=ins.dl endrepeat;
            ---  Swap the contents of object's 'dl' and 'curins'.
            ---  <ins.dl,curins>:=<curins,ins.dl>
            dl:=ins.dl;  ins.dl:=curins;  curins:=dl;
       elsif ins.sort = S_RES
            ---  Invariant:  ins.sort = S_RES, but test it anyway.
       then ins.sort := S_DET;
            -- Find main component for component to be detached.
            -- The main component head must be the static enclosure
            -- of the object, since the object has been RESUMEd.
            main:=ins.sl;
            -- Ignore any temporary instances.
            repeat while main.dl.lsc=nowhere do main:=main.dl endrepeat;
            repeat while ins.dl.lsc=nowhere do ins:=ins.dl endrepeat;
            -- Rotate the contents of curins, ins.dl and main.dl.
            -- <main.dl,ins.dl,curins>:=<ins.dl,curins,main.dl>
            dl:=main.dl;  main.dl:=ins.dl;  ins.dl  := curins;
            curins:=dl;
       else IERR_R("DETACH") endif;
       endif;
       ---  See if we are tracing the execution.
%-X    if bio.trc 
%-X    then bio.obsEvt:=EVT_deta; call PSIMOB(smb) endif;
       psc:=curins.lsc;
 end;

 Visible routine RESUME;
 import ref(inst) ins;      --  Object to be resumed.
 exit label psc;
 begin ref(inst) head;  --  Component head.
       ref(inst) comp;  --  Component pointer
       ref(inst) m_sl;   --  Static enclosure of main component head
       ref(inst) main;   --  The head of the main component and also
                             --  the head of the quasi-parallel system.

       ---  Set the program point where current instance is executing.
       curins.lsc:=psc;
%      if ins = none then  ERROR(ENO_RES_1)  endif;
       assert ins <> none skip ERROR(ENO_RES_1)  endskip;

%-X    if bio.trc then bio.smbP1:=curins; bio.GCval:=ins.sort endif;
       if ins.sort = S_DET
       then ins.sort:=S_RES;
            ---  The object to be resumed must be local to a system head
            main:=ins.sl;
%           if (main.sort <> S_PRE)  and (main.sort <> S_SUB)
%           then ERROR(ENO_RES_2) endif;
            assert (main.sort = S_PRE) or (main.sort = S_SUB)
                   skip ERROR(ENO_RES_2) endskip;
            ---  Find operating component of the quasi-parallel system.
            head:=comp:=curins; m_sl:=main.sl;
            repeat while comp.dl <> m_sl do  comp := comp.dl;
                -- find component head (last non-temp inst on chain)
                   if comp.lsc<>nowhere then head:=comp endif;
            endrepeat;
            if head.sort=S_RES then head.sort:=S_DET endif;
            ---  Ignore any temporary instances - those of the
            ---  operating component has already been dealt with.
            repeat while ins.dl.lsc=nowhere do ins:=ins.dl endrepeat;
            ---  Rotate the contents of ins.dl, comp.dl and curins.
            ---  Invariant:       comp.dl = m_sl
            ---  <ins.dl,comp.dl,curins>:=<comp.dl,curins,ins.dl>
            comp.dl:=curins; curins:=ins.dl; ins.dl:=m_sl;
       elsif ins.sort <> S_RES then  ERROR(ENO_RES_3)  endif;
       -- else a no-operation
%-X    if bio.trc
%-X    then bio.obsEvt:=EVT_resu; call PSIMOB(smb) endif;
       psc:=curins.lsc;
 end;

 Visible routine ATTACH;
 import ref(inst) ins; --  Object to be attached.
 exit label psc;
 begin ref(inst) dl;   --  Temporary reference to dynamic enclosure.
       ---  Set the program point where current instance is executing.
       curins.lsc:=psc;
%      if ins = none        then ERROR(ENO_ATT_1) endif;
%      if ins.sort <> S_DET then ERROR(ENO_ATT_2) endif;
       assert ins <> none      skip ERROR(ENO_ATT_1) endskip;
       assert ins.sort = S_DET skip ERROR(ENO_ATT_2) endskip;
       ---  The object to be attached cannot be on the operating chain,
       ---  since its state would then be resumed and not detached
%-X    if bio.trc then bio.smbP1:=curins; bio.GCval:=ins.sort endif
       ins.sort:=S_ATT;
       ---  Swap the contents of 'curins' and object's 'dl'.
       ---  <ins.dl,curins>:=<curins,ins.dl>;
       ---  Ignore any temporary instances.
       repeat while ins.dl.lsc = nowhere do ins:=ins.dl endrepeat;
       dl:=ins.dl;  ins.dl  := curins;  curins:=dl;
       ---  From now on the object is in attached state.
       ---  It is no longer a component head.
%-X    if bio.trc
%-X    then bio.obsEvt:=EVT_call; call PSIMOB(smb) endif;
       psc:=curins.lsc;
 end;
%title *****   1 - d i m e n s i o n a l   a r r a y   *****

 Visible routine ar1new;
 import range(0:MAX_TYPE) type; integer lb,ub;
 export ref(arent1) arr;
 begin integer nelt; range(0:MAX_SORT) sort; size lng;
       if lb<=0 then
%         if ub>=(maxint+lb) then ERROR(ENO_ARR_1) endif endif;
          assert ub<(maxint+lb) skip ERROR(ENO_ARR_1) endskip endif;
       nelt:=ub-lb+1; if nelt<0 then nelt:=0 endif;
       sort:=S_ARENT1;
       ---  Compute the size of the array entity.
       case 0:MAX_TYPE (type)
       when T_BOO: lng:=size(booAr1:nelt);
       when T_CHA: lng:=size(chaAr1:nelt);
       when T_SIN: lng:=size(sinAr1:nelt);
       when T_INT: lng:=size(intAr1:nelt);
       when T_REA: lng:=size(reaAr1:nelt);
       when T_LRL: lng:=size(lrlAr1:nelt);
       when T_REF: lng:=size(refAr1:nelt); sort:=S_ARREF1;
       when T_PTR: lng:=size(ptrAr1:nelt);
       when T_TXT: lng:=size(txtAr1:nelt); sort:=S_ARTXT1;
       otherwise  IERR_R("ar1new")   endcase;

       ALLOC2(sort,arr,lng); arr.misc:=type;
       arr.lng :=lng; arr.lb:=lb; arr.ub:=ub;
%-X    if bio.trc then bio.obsEVT:=EVT_ARR; bio.smbP1:=arr; observ endif
 end;

%title ***  T e x t    H a n d l i n g  ***
 Visible macro  NEW_TXT(1);
 --  The argument is the name of an object reference variable,
 --  which must be updated by the garbage collector.
 begin lng:=size(txtent:ncha);         -- computed at run-time
       ---  Allocate storage. If necessary call the garbage collector.
       ---  An exact expansion of ALLOC(S_TXTENT,res.ent,lng,%1).
       res.ent:=bio.nxtAdr; bio.nxtAdr:=bio.nxtAdr + lng;
       if bio.nxtAdr > bio.lstAdr
       then res.ent:=GARB(res.ent,lng,name(%1)) endif;
       res.ent.sort:=S_TXTENT; res.ent.ncha:=ncha;
       --  A text has been created, give it an unique identification.
       --  res.ent.sno:=bio.txt_sno:=bio.txt_sno + 1;
       ---  Set the text quantity to refer to the allocated text object.
       res.cp:=0; res.sp:=0; res.lp:=ncha;
 endmacro;


 Visible routine txtVal;
 import ref(inst) parins; field(infix(txtqnt)) fld;
 export ref(inst) updatd;
 begin infix(string) res_str,src_str; infix(txtqnt) res,src;
       range(0:MAX_TXT) ncha; size lng;
       src:=var(parins + fld); ncha:=src.lp - src.sp;
       if ncha = 0
       then res:=notext;
            ---  Check the save entity allocation invariant !!
            if bio.nxtAdr > bio.lstAdr
            then GARB(bio.nxtAdr,nosize,name(parins)) endif;
       else ---  Create Text Object. If necessary call GC
            NEW_TXT(parins); src:=var(parins + fld);
            ---  Move character values into the allocated text entity.
            src_str.nchr:=res_str.nchr:=ncha;
            src_str.chradr:=name(src.ent.cha(src.sp));
            res_str.chradr:=name(res.ent.cha);              --  cha(0)
            C_MOVE(src_str,res_str);
       endif;
%-X    if bio.trc then bio.obsEVT:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;
       var( (parins+fld) qua name(infix(txtqnt)) ) := res;
       updatd:=parins;
 end;


 Visible routine txtAsT;
 import  infix(txtqnt) dst,src;
 begin infix(string) dst_str,src_str;
       src_str.nchr:=src.lp-src.sp; dst_str.nchr:=dst.lp-dst.sp;
%      if dst_str.nchr < src_str.nchr then ERROR(ENO_TXT_2) endif;
       assert dst_str.nchr >= src_str.nchr skip ERROR(ENO_TXT_2) endskip
       ---   dst = notext  ==>  src = notext
       if dst.ent<>none    --  notext?
       then
       		assert dst.ent.misc=0 skip ERROR(ENO_TXT_3) endskip;
            dst_str.chradr:=name(dst.ent.cha(dst.sp));
            src_str.chradr:=if src_str.nchr > 0
            then name(src.ent.cha(src.sp)) else noname;
            ---  Transfer all the characters in the source.
            ---  Blankfill any remaining characters of the destination.
            C_MOVE(src_str,dst_str);
       endif;
 end;


 Visible routine BLANKS;
 import integer ncha; export infix(txtqnt) res;
 begin infix(string) str; size lng;
       if ncha > 0
       then
%           if ncha > MAX_TXT then ERROR(ENO_TXT_1) endif;
            assert ncha <= MAX_TXT skip ERROR(ENO_TXT_1) endskip;
            ---  Create Text Object. If necessary call garbage collector
            NEW_TXT(bioref);
            ---  Blankfill all the characters.
            str.chradr:=name(res.ent.cha); -- Point to first char.
            str.nchr:=ncha; C_BLNK(str);
       elsif ncha = 0
       then res:=notext;
            ---  Check that the save entity allocation invariant holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       else ERROR(ENO_TXT_1) endif;
%-X    if bio.trc then bio.obsEVT:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;
 end;


 Visible routine COPY;
 import infix(txtqnt) src; export infix(txtqnt) res;
 begin range(0:MAX_TXT) ncha; infix(string) res_str,src_str; size lng;
       if src.ent = none
       then res:=notext;
            ---  Check that the save entity allocation invariant holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       else ---  Create Text Object. If necessary call GC
            ncha:=src.lp - src.sp;
            NEW_TXT(src.ent);

            ---  Move character values into the allocated text entity.
            src_str.nchr:=res_str.nchr:=ncha;
            src_str.chradr:=name(src.ent.cha(src.sp));
            res_str.chradr:=name(res.ent.cha);               --  cha(0)
            C_MOVE(src_str,res_str);
       endif;
%-X    if bio.trc then bio.obsEVT:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;
 end;


 Visible routine CONCAT;
   import range(0:MAX_DIM) npar; infix(txtqnt) param(MAX_DIM);
   export infix(txtqnt) res;
 begin short integer ncha; size lng; infix(txtqnt) tmp;
       infix(string) res_str,src_str; short integer i;
       i:=ncha:=0;
       repeat while i<npar do
              --- move parameter into bio for update by possible GC
              tmp:=bio.conc.elt(i):=param(i);
              ncha:=ncha+tmp.lp-tmp.sp; i:=i+1;
       endrepeat;
       if ncha=0
       then res:=notext;
            ---  Check that the save entity invariant still holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       else ---  Create Text Object. If necessary call garbage collector
            NEW_TXT(bioref);
            ---  Move the parameters succesively into result object
            i:=ncha:=0;
            repeat while i<npar do
                   tmp:=bio.conc.elt(i); bio.conc.elt(i):=notext;
                   src_str.nchr:=res_str.nchr:=tmp.lp-tmp.sp;
				   if src_str.nchr > 0 then
                      src_str.chradr:=name(tmp.ent.cha(tmp.sp));
                      res_str.chradr:=name(res.ent.cha(ncha));
                      C_MOVE(src_str,res_str);
                   endif;
                   i:=i+1; ncha:=ncha+src_str.nchr;
            endrepeat;
       endif;
%-X    if bio.trc then bio.obsEVT:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;
 end;


 Visible routine UPTX;
 import infix(txtqnt) txt; export infix(txtqnt) res;
 begin short integer n,c,l; boolean nonconstant;
       if txt.ent<>none -- notext?
       then n:=txt.lp; l:=txt.sp; nonconstant:=txt.ent.misc=0;
            repeat n:=n-1 while n >= l
            do c:=txt.ent.cha(n) qua integer;
               if c > 96 then if c < 123 then
%                 if not nonconstant then ERROR(ENO_TXT_3) endif;
                  assert nonconstant skip ERROR(ENO_TXT_3) endskip;
                  txt.ent.cha(n):=(c-32) qua character;
               endif endif;
            endrepeat;
            txt.cp:=txt.sp;
       endif;
       res:=txt;
 end;

 Visible routine LWTX;
 import infix(txtqnt) txt; export infix(txtqnt) res;
 begin short integer n,c,l; boolean nonconstant;
       if txt.ent<>none -- notext?
       then n:=txt.lp; l:=txt.sp; nonconstant:=txt.ent.misc=0;
            repeat n:=n-1 while n >= l
            do c:=txt.ent.cha(n) qua integer;
               if c > 64 then if c < 91 then
%                 if not nonconstant then ERROR(ENO_TXT_3) endif;
                  assert nonconstant skip ERROR(ENO_TXT_3) endskip;
                  txt.ent.cha(n):=(c+32) qua character;
               endif endif;
            endrepeat;
            txt.cp:=txt.sp;
       endif;
       res:=txt;
 end;

-- Visible routine LENGTH;
-- import infix(txtqnt) txt; export integer lng;
-- begin lng:=txt.lp - txt.sp end;


-- Visible routine MORE;
-- import infix(txtqnt) txt; export boolean mr;
-- begin mr:=txt.cp < txt.lp end;


-- Visible routine POS;
-- import infix(txtqnt) txt; export integer pos;
-- begin pos:=txt.cp - txt.sp + 1 end;


 Visible routine setPos;
 import name(infix(txtqnt)) adr; integer pos;
 begin infix(txtqnt) txt; txt:=var(adr);
       if pos <= 0 then pos:=txt.lp;
       else pos:=txt.sp + pos - 1;
            if pos > txt.lp then pos:=txt.lp endif;
       endif;
       var(adr).cp:=pos;
 end;

-- Visible routine START;
-- import infix(txtqnt) txt; export integer start;
-- begin start:=txt.sp + 1 end;


 Visible routine subAtr;
 import name(infix(txtqnt)) txt; integer m,n;
 export infix(txtqnt) res;
 begin
--     if m < 1 then ERROR(ENO_TXT_4);
--     elsif n < 0 then ERROR(ENO_TXT_5);
--     elsif n = 0 then res:=notext;
       assert m >= 1 skip ERROR(ENO_TXT_4) endskip;
       if n = 0 then res:=notext;
       else assert n > 0 skip ERROR(ENO_TXT_5) endskip;
            res.ent:=var(txt).ent; res.sp:=var(txt).sp + m - 1;
            res.lp:=res.sp + n; res.cp:=res.sp;
%           if res.lp > var(txt).lp then ERROR(ENO_TXT_6) endif;
            assert res.lp <= var(txt).lp skip ERROR(ENO_TXT_6) endskip;
       endif;
 end;
%title *****   GET - macroes and  GETINT/GETCHAR   *****

 Visible Macro GET_ITEM_ATR(1);
 --  The parameter is the name of the function (e.g. GETINT).
 begin infix(string) item;
       infix(txtqnt) txt;   --  Local copy here for efficiency.
       txt:=var(adr);
%      if txt.ent=none then ERROR(ENO_TXT_7) endif; -- notext?
       assert txt.ent<>none skip ERROR(ENO_TXT_7) endskip; -- notext?
       item.nchr:=txt.lp - txt.sp;
       item.chradr:=name(txt.ent.cha(txt.sp));
       res:=%1(item); if status <> 0 then ERR_GET endif;
       var(adr).cp:=txt.sp + itsize;
 endmacro;

 Visible Macro GET_ITEM_TMP(1);
 --  The parameter is the name of the function (e.g. GETINT).
 begin infix(string) item;
%   if txt.ent=none then ERROR(ENO_TXT_7) endif;       --  notext?
    assert txt.ent<>none skip ERROR(ENO_TXT_7) endskip;     --  notext?
    item.nchr:=txt.lp - txt.sp;
    item.chradr:=name(txt.ent.cha(txt.sp));
    res:=%1(item); if status <> 0 then ERR_GET endif;
 endmacro;

 Visible Routine ERR_GET;
 begin if status = 21 then status:=0; ERROR(ENO_TXT_8);
    elsif status = 22 then status:=0; ERROR(ENO_TXT_9);
    elsif status = 23 then status:=0; ERROR(ENO_TXT_10);
    else IERR_E endif; --- internal error in ENVIR
 end;


 Visible routine gtchaA;
 import name(infix(txtqnt)) adr; export character c;
 begin infix(txtqnt) txt;
       txt:=var(adr);
%      if txt.cp = txt.lp then ERROR(ENO_TXT_11) endif;
       assert txt.cp < txt.lp skip ERROR(ENO_TXT_11) endskip;
       c:=txt.ent.cha(txt.cp); var(adr).cp:=txt.cp + 1;
 end;


 Visible routine gtintA;
 import name(infix(txtqnt)) adr; export integer res;
 begin GET_ITEM_ATR(GETINT) end;

 Visible routine gtintT;
 import infix(txtqnt) txt; export integer res;
 begin GET_ITEM_TMP(GETINT) end;

%title *****   PUT - macroes and PUTINT/PUTCHAR   *****

 Visible Macro PUT_ITEM_ATR(2);
 --  First parameter is the name of the function (e.g. PUTINT).
 --  Second parameter contains the extra size arguments, if any.
 begin infix(string) item; infix(txtqnt) txt;
       txt:=var(adr);
%      if txt.ent=none then ERROR(ENO_TXT_12) endif;        --  notext?
%      if txt.ent.gcl <> none then ERROR(ENO_TXT_13) endif; --  Const?
       assert txt.ent<>none skip ERROR(ENO_TXT_12) endskip;   -- notext?
       assert txt.ent.misc=0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       item.nchr:=txt.lp - txt.sp; item.chradr:=name(txt.ent.cha(txt.sp));
       %1(item,val %2); if status <> 0 then ERR_PUT(item) endif;
       var(adr).cp:=txt.lp;
 endmacro;

 Visible Macro PUT_ITEM_TMP(2);
 --  First parameter is the name of the function (e.g. PUTINT).
 --  Second parameter contains the extra size arguments, if any.
 begin infix(string) item;
%      if txt.ent=none then ERROR(ENO_TXT_12) endif;        --  notext?
%      if txt.ent.gcl <> none then ERROR(ENO_TXT_13) endif; --  Const?
       assert txt.ent<>none skip ERROR(ENO_TXT_12) endskip;   -- notext?
       assert txt.ent.misc=0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       item.nchr:=txt.lp-txt.sp; item.chradr:=name(txt.ent.cha(txt.sp));
       %1(item,val %2); if status <> 0 then ERR_PUT(item) endif;
 endmacro;

 Visible Routine ERR_PUT; import infix(string) item;
 begin
% +M	   ED_STR("CENT.ERR_PUT: status="); ED_INT(status); ED_OUT;
 	   if status = 25 then status:=0; ERROR(ENO_TXT_14);
       elsif status <> 24 then IERR_E endif; -- internal error in ENVIR
       status:=0;
       ---  Overflow, not enough space for all the digits. Starfill.
       repeat while item.nchr > 0
       do item.nchr:=item.nchr - 1;
          var(item.chradr)(item.nchr):='*';
       endrepeat;
       bio.edOflo:=bio.edOflo + 1;
 end;


 Visible routine ptchaA;
 import name(infix(txtqnt)) adr; character c;
 begin infix(txtqnt) txt;
       txt:=var(adr);
%      if txt.cp = txt.lp then ERROR(ENO_TXT_15) endif;
%      if txt.ent.gcl <> none then ERROR(ENO_TXT_13) endif; --  Const?
       assert txt.cp < txt.lp skip ERROR(ENO_TXT_15) endskip;
       assert txt.ent.misc=0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       txt.ent.cha(txt.cp):=c; var(adr).cp:=txt.cp + 1;
 end;


 Visible routine ptintA;
 import name(infix(txtqnt)) adr; integer val;
 begin PUT_ITEM_ATR(PUTINT,%%) end;

 Visible routine ptintT;
 import infix(txtqnt) txt; integer val;
 begin PUT_ITEM_TMP(PUTINT,%%) end;


-- Visible routine putstr; -- ??? WHERE USED ???
-- import name(infix(txtqnt)) dst_adr; infix(string) src_str;
-- begin infix(string) dst_str; infix(txtqnt) dst;
--       if src_str.nchr <> 0  --  nostring?
--       then dst:=var(dst_adr);
--          if dst.ent.gcl <> none then IERR_R("putstr-1") endif;
--          if dst.lp-dst.cp<src_str.nchr then IERR_R("putstr-2") endif;
--          dst_str.nchr:=src_str.nchr;
--          dst_str.chradr:=name(dst.ent.cha(dst.cp));
--          ---  Transfer all the characters in the source.
--          ---  Blankfill any remaining characters of the destination.
--          C_MOVE(src_str,dst_str);
--          var(dst_adr).cp:=dst.cp + src_str.nchr;
--       endif;
-- end;

%title   ***********   S A V E   -   R E S T O R E   ************

 Visible routine presav;
 import size lng;  --  Length of S-Code save object part.
 export ref() obj; --  Courtesy of the environment.
 begin ref(savent) sav;     --  To the entity to be allocated
       --- Check if the save invariant still holds
%      if bio.nxtAdr > bio.lstAdr then IERR_R("CENT.presav") endif;
       assert bio.nxtAdr<=bio.lstAdr skip IERR_R("CENT.presav") endskip;
       sav:=bio.nxtAdr; sav.sort:=S_SAV; obj:=sav+size(savent);
       bio.nxtAdr:=obj+lng; sav.lng:=bio.nxtAdr-sav;
       --- Insert save entity in the current instance's dynamic chain.
       sav.dl:=curins.dl; curins.dl:=sav;
       if lng = nosize then obj:=none endif;
% +M	   ED_STR("CENT.PRESAV: lng="); ED_SIZE(lng); ED_STR(", sav="); ED_OADDR(sav); ED_STR(", obj="); ED_OADDR(obj); ED_OUT;
 end;

 Visible routine restor;
 begin ---  Remove the save entity from the dynamic chain.
       rstr_x:=curins.dl;
% X    if rstr_x.sort<>S_SAV then IERR_R("RESTORE") endif;
%-X    assert rstr_x.sort = S_SAV skip IERR_R("RESTORE") endskip;
       curins.dl:=rstr_x.dl;
       rstr:=if rstr_x.lng<>size(savent)
             then rstr_x+size(savent) else none;
%  +M	   ED_STR("CENT.RESTOR: lng="); ED_SIZE(rstr_x.lng); ED_STR(", rstr="); ED_OADDR(rstr); ED_OUT;
 end;

 Visible routine presto;
 begin ---  Try to reclaim save objects;
%  +M	   ED_STR("CENT.PRESTO: lng="); ED_SIZE(rstr_x.lng); ED_STR(", rstr_x="); ED_OADDR(rstr_x); ED_OUT;
       if rstr_x <> none
       then RECLAIM(rstr_x,rstr_x.lng); rstr_x:=none endif;
 end;
%title   ******   Miscellaneous  *******

 Visible routine timdat; export infix(txtqnt) res;
 begin range(0:MAX_BYT) n;
       n:=DATTIM(STRBUF(0));
       if status<>0 then res:=notext; status:=0 -- pje sep 87
       else res:=BLANKS(n); C_MOVE(STRBUF(n),TX2STR(res)) endif;
 end;

 Visible routine Tused; export long real val;
 begin
---    if bio.realAr then
          val:=CPUTIM - bio.initim;
          if status<>0 then status:=0; val:=0.0&&0 endif;
---    endif;
 end;

 Visible routine Tclock; export long real val;
 begin
---    if bio.realAr then
          val:=CLOCKT;
          if status<>0 then status:=0; val:=0.0&&0 endif;
---    endif;
 end;

-- Visible routine SRCLIN; export integer val;
-- begin val:=GTLNO(GTOUTM);
--       if status<>0 then ERR_STD("sourceline") endif;
-- end;

 Visible routine getII; import integer index; export integer val;
 begin val:=GINTIN(index); -- if status > 0 then ERROR0 endif end;
       if status<>0 then val:=-status; status:=0 endif end;

 Visible routine getTI; import integer index; export infix(txtqnt) res;
 begin integer n;
       n:=GTEXIN(index,STRBUF(0));
 --    if status<>0 then ERROR0 endif;
       if status<>0 then res:=notext; status:=0;
       else res:=BLANKS(n); C_MOVE(STRBUF(n),TX2STR(res)); endif;
 end;

 Visible routine giveII; import integer index,inf; export character res;
 begin GVIINF(index,inf); -- if status<>0 then ERROR0 endif end;
       res:=status qua character; status:=0;
 end;

 Visible routine giveTI; import integer index; infix(txtqnt) txt;
 export character res;
 begin GVTINF(index,TX2STR(txt)); -- if status<>0 then ERROR0 endif;
       res:=status qua character; status:=0;
 end;
%page

 Visible body(Palloc) xalloc;
 -- import integer nbytes; entry(Pmovit) movrut;
 -- export name() Nobj;
 begin size lng; ref(nonObj) obj;
       lng:=size(nonObj:nbytes); -- computed at runtime
       ALLOC2(S_ALLOC,obj,lng); obj.lng:=lng; obj.moveit:=movrut;
       Nobj:=name(obj.cha);
 end;

 Visible body(Pfree ) xfree ;
 -- import name() Nobj;
 begin ref(nonObj) obj;
--       obj:=conv_ref(Nobj); obj.sort:=S_ARENT1;
       obj:=Nobj qua ref(); obj.sort:=S_ARENT1;
 end;

 Visible routine errLab; import infix(labqnt) erh; name(integer) eno;
 begin bio.erh:=erh;
--       bio.ern.ins:=conv_ref(eno);
       bio.ern.ins:=eno qua ref();
--       bio.ern.fld:=conv_field(eno);
       bio.ern.fld:=eno qua field();
 end;

-- Visible routine MAKE_LAB;
-- import ref(inst) sl; label pad; range(0:MAX_CLV) clv;
-- export infix(labqnt) lab;
-- begin lab.sl:=sl; lab.pad:=pad; lab.clv:=clv end;

-- Visible routine MAKE_PRO;
-- import ref(inst) sl; ref(proptp) ppp; ref(claptp) qal;
-- export infix(proqnt) pro;
-- begin pro.sl:=sl; pro.ppp:=ppp; pro.qal:=qal end;

-- Visible routine MAKE_SWT;
-- import ref(inst) sl; ref(swtdes) des;
-- export infix(swtqnt) swt;
-- begin swt.sl:=sl; swt.des:=des end;

ref(inst) pins; --- used by end prior procedure
%visible -------------------

 --- end class instance ---
 E_OBJ: E_CLA_PRE;

 --- end prior procedure ---
 EPPRV: tmp.pnt:=curins;
 EPPRO: pins:=curins.dl; curins.dl:=none; curins:=pins;
        goto curins.lsc;

%hidden  -------------------

end;
