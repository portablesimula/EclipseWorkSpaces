begin
   SYSINSERT RT,SYSR,KNWN,UTIL;  
   const infix(string) facit(2) = (
      "BEGIN TEST",
      "END TEST"
   );

   integer nError ;
   integer traceCase;

   Visible routine trace; import infix(string) msg;
   begin
%      if verbose then ed_str(msg); ed_str("  TEST AGAINST FACIT:  "); prt(facit(traceCase)); endif;
      if( not STREQL(msg,facit(traceCase))) then
%         nError:=nError+1; prt(" ");
%         ed_str("ERROR in Case "); ed_int(traceCase); ed_out;
%         ed_str("Trace: "); prt(msg);
%         ed_str("Facit: "); prt(facit(traceCase));
		SYSPRI("ERROR ######################################################");
      endif;
      traceCase:=traceCase+1;
   end;
   
   boolean b;
   integer x;
   const infix(String) msg = "ABRA";
   
%   b := STREQL(msg,facit(1));

	trace("BEGIN TEST");
	trace("ABRA");


  end;
	 