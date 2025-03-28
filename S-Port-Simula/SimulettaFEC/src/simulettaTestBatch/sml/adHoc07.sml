begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

   Visible routine TEST; import integer i; export integer x;
   begin
   		integer j;
   		j := 44;
		SYSPRI("OK");
   		x := i + j;
   end;

%	prt("ABRA CA DAB");
	TEST(22);
%	SYSPRI("OK");

 end;
	 