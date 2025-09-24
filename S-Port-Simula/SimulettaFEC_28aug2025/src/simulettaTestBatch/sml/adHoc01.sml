begin
   SYSINSERT RT,SYSR,KNWN,UTIL
%   ,strg,cent,cint,arr,form,libr,fil,smst,sml,edit,mntr;


   routine TEST1; import integer i,j,k; export integer res; begin
   	  integer loc1,loc2;
   	  loc1 := 444;
   	  loc2 := 555;
   	  res := 666;
   end;

   routine TEST2; import integer i,j; export integer res; begin
   	  integer loc1;
   	  loc1 := 999;
	  res := TEST1(111, 222, 333);
   end;
   
   integer z;


	z := TEST2(777, 888);
 end;
	 