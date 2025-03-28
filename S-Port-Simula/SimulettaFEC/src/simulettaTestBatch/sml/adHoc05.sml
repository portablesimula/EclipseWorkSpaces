begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
   Visible entry(routineProfile) routineRef;

%  Visible global profile routineProfile;
   Visible profile routineProfile;
    import integer eno; ref(filent) fil;
           export infix(string) res;
   end;
 
   Visible body(routineProfile) routineBody;
    -- import integer eno; ref(filent) fil; export infix(string) res;
   begin ed_str("ERROR: "); ed_int(eno);
         res:=get_ed;
   end;

	integer i;
	infix(string) s;
	integer j;
	
	i:=4444; j:=8888;

   routineRef:=entry(routineBody);

   s := call routineProfile(routineRef)(34,none);
   SYSPRI(s);
             
 end;
	 