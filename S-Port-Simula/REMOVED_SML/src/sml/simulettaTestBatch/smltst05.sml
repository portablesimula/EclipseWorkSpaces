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
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
-- ===============================================   Main   program =============
--
   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 05";
   const infix(string) PURPOSE      ="Test CALL_TOS";
--
-- ==============================================================================
   
   integer nError ;
   integer traceCase;
   const infix(string) facit(2) = (
      "ERROR: 34",
      "END TEST"
   );
   
   Visible routine trace; import infix(string) msg;
   begin
      if verbose then ed_str(msg); ed_str("  TEST AGAINST FACIT:  "); prt(facit(traceCase)); endif;
      if( not STREQL(msg,facit(traceCase))) then
         nError:=nError+1; prt(" ");
         ed_str("ERROR in Case "); ed_int(traceCase); ed_out;
         ed_str("Trace: "); prt(msg);
         ed_str("Facit: "); prt(facit(traceCase));
      endif;
      traceCase:=traceCase+1;
   end;
   
   Visible entry(routineProfile) routineRef;

   Visible global profile routineProfile;
    import integer eno; ref(filent) fil;
           export infix(string) res;
   end;

   Visible routine test_CALL_TOS; export infix(string) res;
   begin res := call routineProfile(routineRef)(34,none);
   end;
 
   Visible body(routineProfile) routineBody;
    -- import integer eno; ref(filent) fil; export infix(string) res;
   begin ed_str("ERROR: "); ed_int(eno);
         res:=get_ed;
   end;

   infix(string) s;

   if verbose then prt2("--- ",PROGRAM_NAME); prt2("--- ",PURPOSE) endif;
-- ==============================================================================
 
      routineRef:=entry(routineBody);
      trace(test_CALL_TOS);
             
      trace("END TEST");

-- ==============================================================================
   
   IF nError = 0 then prt2("--- NO ERRORS FOUND IN ",PROGRAM_NAME) endif;
   if verbose then ed_str("--- END "); ed_str(PROGRAM_NAME); ed_str(" -- nError="); ed_int(nError); ed_out endif;

 end;
