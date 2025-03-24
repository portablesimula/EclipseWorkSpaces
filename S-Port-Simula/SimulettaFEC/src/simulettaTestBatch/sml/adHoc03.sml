begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
%   integer nError ;
   integer traceCase;
   const infix(string) facit(4) = ( "AAA", "BBB", "CCC", "DDD" );
   
%   Visible routine trace; import infix(string) msg;
%   begin
%	  STREQL(msg,facit(traceCase));
%      traceCase:=traceCase+1;
%   end;

%      trace("XXX");
%      trace("ZZZ");
%	  STREQL("WWW",facit(traceCase));
	  SYSPRI(facit(traceCase+1));

 end;
		 