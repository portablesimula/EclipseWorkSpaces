begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    


 Visible known("ERROR") ERROR; import range(0:MAX_ENO) eno;
 begin call PEXERR(errorX)(eno,none) end;
	
	ERROR(44);
 end;
	 