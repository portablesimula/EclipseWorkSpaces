--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace
begin
   INSERT envir,modl1;
--   SYSINSERT RT,SYSR;
 
   Visible entry(routineProfile) routineRef;
   infix(string) res;

   Visible global profile routineProfile;
    import integer eno; ref() fil;
           export infix(string) res;
   end;

   Visible routine test_CALL_TOS; export infix(string) res;
   begin res := call routineProfile(routineRef)(34,none) end;
 
   Visible body(routineProfile) routineBody;
    -- import integer eno; ref(filent) fil; export infix(string) res;
   begin --ed_str("ERROR: "); ed_int(eno);
         --res:=get_ed;
   end;

--   res :=
    call routineProfile(routineRef)(34,none)
 end;
