begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	record REC1; begin
		infix(string) ss(3);
		real rr(2);
		integer ii(2);
		character cc(2);
		size zz(2);
		boolean bb(2);
	  end
	  
	record REC2; begin
		infix(string) ff;
%		infix(LEVEL2) lv2;
	  end
	  
%	infix(REC1) z1=record:REC1(ii=55)
	infix(REC1) z2=record:REC1(rr=(0.0,3.14),ss=("1.0","222","333"),ii=(0,888),cc=('A','B'),zz=size(REC2),bb=(false,true))

 end;
	 