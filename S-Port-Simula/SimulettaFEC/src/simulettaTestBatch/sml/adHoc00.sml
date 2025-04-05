begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    

		integer i,j;
 
 		j:=44;
	BB:
	    -- Forward Jumps
%		ED_STR("BB: Forward Jump: j="); ED_INT(j); trace(get_ed);
 		if j=44 then goto FF endif;
	    i:=88;
	    goto EE;

	    -- Backward Jumps
	FF:
%		ED_STR("FF: Backward Jump: j="); ED_INT(j); trace(get_ed);
	    j:=0;
%		ED_STR("Backward Jump: j="); ED_INT(j); trace(get_ed);
	    goto BB;
	    i:=88;
    EE: 
		

 end;
