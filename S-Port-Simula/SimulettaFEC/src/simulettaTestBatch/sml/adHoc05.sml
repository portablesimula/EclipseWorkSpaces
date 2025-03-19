begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

   Visible routine trace; import infix(string) msg;
   begin
       ed_str(msg);
   end;

   infix(string) s,	q;
 
      q:=s:="ABRA CA DAB";
      
      s.chradr:=name(var(s.chradr)(3)); s.nchr:=s.nchr-3;  -- Increment 3 char.
%     STREQL(s,"ALLABABA");
       trace(s);
      

 end;
	 