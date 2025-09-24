begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC; begin
		integer   i(4);
		integer   j;
	end;
	
	ref(REC) r1;
	integer i;
	infix(REC) RECOBJ;

	r1 := ref(RECOBJ);
		
	r1.j := 4444;
		
%	i := r1.j;
	
 end;
