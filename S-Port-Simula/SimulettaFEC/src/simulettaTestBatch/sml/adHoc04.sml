begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

   routine testCase; import integer code; begin
    infix(string) v;
      case 0:1 (code)
%      when 1:     v:="10"
%      when 2,3,4: v:="30"
%      when 5,6:   v:="60"
      otherwise   v:="99";
      endcase;
      
%	   ed_str(v);
   end;
   

      testCase(0);

 end;
	 