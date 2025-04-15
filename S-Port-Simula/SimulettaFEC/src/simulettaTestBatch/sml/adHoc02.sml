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
		infix(LEVEL2) lv2;
	  end
	
	record LEVEL2; begin
		character cc2(2);
		infix(LEVEL3) lv3;
	  end
	
	record LEVEL3; begin
		character cc3(4);
	  end
	  
	record REC3; begin
		field() ff(2);
		name() nn(3);
	  end
	  
	record REC4; begin
		label lab(3);
		entry() entr(3);
		ref() pnt(3);
	  end
	  
%	infix(REC1) z1=record:REC1(ii=55)
%	infix(REC1) z2=record:REC1(rr=(0.0,3.14),ss=("1.0","222","333"),ii=(0,888),cc=('A','B'),zz=size(REC2),bb=(false,true))

	infix(REC2) z3=record:REC2(ff="0.0",lv2=record:LEVEL2(cc2=('A','B'),lv3=record:LEVEL3(cc3=('X','Y'))))
%	infix(REC2) z3=record:REC2(ff="0.0",lv2=record:LEVEL2(cc2=('A','B')))
	
%	infix(REC3) z4=record:REC3(ff=field(REC2.lv2))
%	infix(REC3) z5=record:REC3(ff=field(REC2.lv2.lv3.cc3))

	ref(REC2) refREC2=ref(z3);
	
	infix(REC3) z6=record:REC3(nn=name(refREC2))
	infix(REC3) z7=record:REC3(nn=name(refREC2.lv2))
	infix(REC3) z8=record:REC3(nn=name(refREC2.lv2.cc2))
	infix(REC3) z9=record:REC3(nn=name(refREC2.lv2.lv3.cc3))


 end;
	 