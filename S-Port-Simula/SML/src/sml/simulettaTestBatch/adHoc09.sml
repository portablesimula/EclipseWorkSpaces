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
   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT,FIL,MNTR;

	const infix(proptp) ppp1 = record:proptp(lng=size(inst),start=A_PRO1_START );
	const infix(proptp) ppp2 = record:proptp(lng=size(inst),start=A_PRO2_START );
	
	B_PROG;
	
	ED_STR("BEFORE A_PRO(1) - ppp1.lng="); ED_SIZE(ppp1.lng); ED_OUT;
	
	A_PRO(none,ref(ppp1));
	
A_PRO1_START:	

	ED_STR("BEFORE A_PRO(2) - ppp2.lng="); ED_SIZE(ppp2.lng); ED_OUT;
	
	A_PRO(none,ref(ppp2));
	
A_PRO2_START:	

 end;
	 