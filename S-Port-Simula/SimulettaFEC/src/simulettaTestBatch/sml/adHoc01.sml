
begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
   integer i(8);
   infix(string) s,	q;
   character c(8);
   character d;
 
	q:=s:="ABRA CA DAB";
%	s:="RABAABRA";
%	q:="ABRA CA DAB";
      
%	c := 'Z';
%	c(1) := 'Z';
%	c(2) := c;
%	c(2) := c(1);

%	i := q.nchr;

%	var(q.chradr) := 'Z';
%	var(q.chradr)(2) := 'W';
	
%	d:=var(q.chradr);
%	d:=var(q.chradr)(2);
      
%      c(0):=var(q.chradr)(2);
%      c(1):=var(q.chradr)(3);

%	  s.chradr := name(c(2));
%	i(7) := 44;
%	s.nchr := 13;
%	s.chradr:=name(var(q.chradr));
      
      s.chradr:=name(var(s.chradr)(3));
      s.nchr:=s.nchr-3;  -- Increment 3 char.
 
   TERMIN(0,s);

 end;
	 