
begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
   integer i(8);
   infix(string) s,	q;
%   character c(8);
 
      q:=s:="ABRA CA DAB";
%	c(2) := 'Z';
      
%      c:=var(q.chradr)(2);
%	  s.chradr := name(c(2));
      
      s.chradr:=name(var(s.chradr)(3));
      s.nchr:=s.nchr-3;  -- Increment 3 char.
 
   TERMIN(0,s);

 end;
	 