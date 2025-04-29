begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
	integer code, res;
		
	case 1:3 (code)
		when 1: res := 444
		when 2: res := 666
		when 3: res := 888
		otherwise res := 222;
	endcase;

 end;
	 