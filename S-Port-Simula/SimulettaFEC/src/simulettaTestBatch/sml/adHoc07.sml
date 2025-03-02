begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    


	
	record REC1; begin
		infix(string) ss(3);
		real rr(2);
		integer ii(3);
		character cc(2);
		size zz(2);
		boolean bb(2);
--		variant infix(string) tt(3); real xx(2);
--		variant character cccc; long real lr;
		infix(string) idents(0);
	  end
	  
	  
	infix(REC1:1) z1=record:REC1(ii=55)
	infix(REC1:2) z2=record:REC1(ii=(55,77))
	infix(REC1:2) z3=record:REC1(ii=55,idents=("AAA","BBB"))
	infix(REC1:2) z4=record:REC1(ii=55,ss=("AAA","BBB"))


 end;
	 