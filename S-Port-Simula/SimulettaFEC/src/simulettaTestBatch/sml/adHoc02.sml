begin
   	SYSINSERT RT,SYSR,KNWN,UTIL;    
   
 Visible routine txtAss;
 import name(infix(txtqnt)) dst; infix(txtqnt) src;
 begin infix(string) dst_str,src_str;
       src_str.nchr:=src.lp - src.sp;
       dst_str.nchr:=var(dst).lp - var(dst).sp;
% %+M	   ED_STR("EDIT.TXTASS: src.chradr="); ED_OADDR(src.chradr); ED_OUT;
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

%	record string; info ''TYPE'';
%	begin name(character) chradr; integer nchr end;
%
%	simple_variable
%		::= identifier
%		::= var ( general_reference'expression )

	name(character) dst; infix(string) s;
	character c;
	
	s := "ABRACADABRA";
	
%	s.chradr:=name(var(s.chradr)(3));
	c := var(s.chradr)(3);
	s.chradr:=name(dst);
    s.nchr:=s.nchr-3;  -- Increment 3 char.
	
	
	dst := name(s);
	s.nchr := 44;
	var(dst).nchr := 66;
	
 end;
	 