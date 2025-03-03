--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace
begin
--   SYSINSERT envir,modl1;
-- SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT;
 SYSINSERT RT,SYSR,KNWN,UTIL;    


	record REC4; begin
		label lab(3);
		entry() entr(3);
		ref() pnt(3);
	  end
	  
	
	const infix(REC4) w1=record:REC4(lab=(LL1,LL2,NOWHERE));

	integer i;

		i:=99;
LL1:	i:=0;
		i:=8888;
LL2:	


 end;
	 