begin
%   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT,CINT,ARR,FORM,LIBR,FIL,SMST,SML,EDIT,MNTR;    
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	routine COPY;
	import infix(txtqnt) src; export infix(txtqnt) res;
	begin
		range(0:MAX_TXT) ncha; infix(string) res_str,src_str; size lng;
        if src.ent = none
        then res:=notext;
             ---  Check that the save entity allocation invariant holds!!
%            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
%       else ---  Create Text Object. If necessary call GC
%            ncha:=src.lp - src.sp;
%            NEW_TXT(src.ent);
% 
%            ---  Move character values into the allocated text entity.
%            src_str.nchr:=res_str.nchr:=ncha;
%            src_str.chradr:=name(src.ent.cha(src.sp));
%            res_str.chradr:=name(res.ent.cha);               --  cha(0)
%            C_MOVE(src_str,res_str);
        endif; 
	end;
	
	infix(txtqnt) src;
	infix(txtqnt) dst;

	dst := COPY(src);
 end;
	 