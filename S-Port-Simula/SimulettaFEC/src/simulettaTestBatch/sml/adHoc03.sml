begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
	record REC4; begin
%		label lab(3);
		entry() entr(3);
		ref() pnt(3);
	  end
	  
	entry(routineProfile) routineRef;
	infix(string) res;

%   Visible global profile routineProfile;
   Visible profile routineProfile;
    import integer eno; ref() fil;
           export infix(string) res;
   end;
	  
   	Visible body(routineProfile) routineBody;
    	-- import integer eno; ref(filent) fil; export infix(string) res;
    	begin
    	 --ed_str("ERROR: ");
    	 -- ed_int(eno);
    	 --     res:=get_ed;
    	end;
	  
%	const infix(REC4) w1=record:REC4(lab=(LL1,LL2,NOWHERE));
%	const infix(REC4) w2=record:REC4(entr=(entry(routineBody),NOBODY));
	const infix(REC4) w3=record:REC4(pnt=(ref(str),NONE));

	integer i;
	infix(string) str; --="ABRACADAB";

		i:=99;
LL1:	i:=0;
		i:=8888;
LL2:	

 end;
		 