begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    

%   routine testCase; import
    integer code;
%   begin
    infix(string) v;
    
    code := 3;
    
      case 0:7 (code)
      when 1:     v:="10"
      when 2,3,4: v:="30"
      when 5,6:   v:="60"
      otherwise   v:="99";
      endcase;
%      trace(v);       
%   end;

%     testCase(3);

 end;
	 