begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

    Visible record R;
	begin integer   i;
    	  integer   j;
    	  variant   integer  int;
    	  variant   real rea;
    	  variant   infix(string) str;
	end;

    Visible record Q:R;
	begin integer   k;
    	  integer   m(8);
    	  variant   integer a,b,c;
    	  variant   long real lrl;
 	end;

    infix(Q) IQ;
    name(infix(Q)) NQ;
    field(integer) fm;
    
    
      
      NQ:=name(IQ);
      fm:=field(Q.m);
      
      var(NQ+fm)(2):=333;

 end;
	 