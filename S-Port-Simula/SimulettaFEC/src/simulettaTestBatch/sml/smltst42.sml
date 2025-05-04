begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
-- ===============================================   Main   program =============
--
   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 42";
   const infix(string) PURPOSE      ="Test C_MOVE and BLNK";
--
-- ==============================================================================
   
   integer nError ;
   integer traceCase;
   const infix(string) facit(26) = (
      "BEGIN TEST",
      "XXX",
      "END TEST"
   );
   
   Visible routine trace; import infix(string) msg;
   begin
      if verbose then ed_str(msg); ed_str("  TEST AGAINST FACIT:  "); prt(facit(traceCase)); endif;
      if( not STREQL(msg,facit(traceCase))) then
         nError:=nError+1; prt(" ");
         ed_str("ERROR in Case "); ed_int(traceCase); ed_out;
         ed_str("Trace: "); ed_str("|"); ed_str(msg); prt("|");
         ed_str("Facit: "); ed_str(facit(traceCase)); prt("|");
      endif;
      traceCase:=traceCase+1;
   end;

   integer i,j;
   character c(10);
   
   infix(string) src = "Abra";
   infix(string) dst

   if verbose then prt2("--- ",PROGRAM_NAME); prt2("--- ",PURPOSE) endif;
-- ==============================================================================
 
      trace("BEGIN TEST");
      
      dst.chradr := ref(c) qua name(character);
      dst.nchr := 10;
      
      C_MOVE(src, dst);
      
%      ed_str(src); ed_str(""); ed_str(dst); trace(get_ed);
      
       
      trace("END TEST");

-- ==============================================================================
   
   IF nError = 0 then prt2("--- NO ERRORS FOUND IN ",PROGRAM_NAME) endif;
   if verbose then ed_str("--- END "); ed_str(PROGRAM_NAME); ed_str(" -- nError="); ed_int(nError); ed_out endif;

 end;
