begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   infix(string) s,	q;
   character c(6);
   
%      q:=s:="ABRA CA DAB";
       
%      s.chradr:=name(var(s.chradr)(3)); s.nchr:=s.nchr-3;  -- Increment 3 char.
      
      c(0):='1'; c(1):='2'; c(2):='3';
      c(3):='4'; c(4):='5'; c(5):='6';
      q.chradr:=@c; q.nchr:=6;

 end;
