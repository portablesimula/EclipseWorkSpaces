begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
	  
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
	  
	  
	infix(REC3) z5=record:REC3(ff=field(REC2.lv2.lv3.cc3))
	
       

 end;
	 