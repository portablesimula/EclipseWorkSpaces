begin
   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT,CINT,ARR,FORM,LIBR,FIL,SMST,SML,EDIT,MNTR;    
%   SYSINSERT RT,SYSR,KNWN,UTIL;    

 routine TEST; import integer i; begin
 	integer j;
 	j := i;
 end

 routine xfOPEN;
 import name(ref(filent)) filnam; infix(txtqnt) img;
 export boolean success;
 begin
 	   range(0:MAX_KEY) key; ref(filent) fil;
       infix(string) nam,action; integer img_lng;
	   ED_STR("FIL.fOPEN: "); ED_OUT;   
       fil:=var(filnam);
       if fil.key <> 0 then goto FEX endif;  --- file already open

       nam:=TX2STR(fil.nam); action:=TX2STR(fil.action);
       img_lng:=img.lp - img.sp;
       ED_STR("FIL.OPEN: nam="); ED_STR(nam); ED_OUT;   

       --- save references (possible GC)
       bio.opfil:=fil; bio.opimg:=img.ent;
           key:=OPFILE(nam,fil.type,action,img_lng);
       --- restore reference (maybe changed by GC)
       var(filnam):=fil:=bio.opfil; img.ent:=bio.opimg;
       if fil=none then IERR_R("fOPEN-1") endif
       bio.smbP1:=none; bio.smbP2:=none;

       if status <> 0
       then FEX: status:=0; success:=false;
       else if bio.files <> none then  bio.files.prd:=fil  endif;
            fil.suc:=bio.files; bio.files:=fil; fil.img:=img;
            fil.key:=key; fil.loc:=1; fil.eof:= false; success:=true;
       endif;
 end;

 Visible routine xOPEN; -- corrected jan 87 pje
 import ref(filent) fil; infix(txtqnt) img; export boolean success;
 begin
 	   success:=xfOPEN(name(fil),img);
 end;
 
	infix(txtqnt) img;
 
	infix(txtent:6) systxt = record:txtent(sort=S_TXTENT, misc=1, ncha=6, cha="SYSOUT"); 
	infix(txtqnt) sysid = record:txtqnt(ent=ref(systxt),lp=6);
	infix(filent) ent = record:filent(eof=false);
	ent.nam := sysid;
	
	ED_STR("FIL.OPEN: sysid="); ED_TXT(sysid); ED_OUT;   
	
	TEST(444);
	
%	xOPEN(ref(ent), img);

	B_PROG;

    ---  Create and open sysout.
%    B_CLA(bioref,ref(pflPtp)); bio.sysout:=tmp.pnt;
%    bio.sysout.nam:=SYS_FIL_SPC(2);
%    if status <> 0 then TERMIN(3,"Cannot open SYSOUT") endif
%+M ED_STR("MNTR.BIODCL: sysout="); ED_TXT(bio.sysout.nam); ED_OUT;   
%    xOPEN(bio.sysout,BLANKS(outlth));
	
 end;
	 