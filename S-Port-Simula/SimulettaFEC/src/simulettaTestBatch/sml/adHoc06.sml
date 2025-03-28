begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
-- ===============================================   Main   program =============
--
   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 07";
   const infix(string) PURPOSE      ="Constant and initial values";
--
-- ==============================================================================
   
   integer nError ;
   integer traceCase;
   const infix(string) facit(20) = (
      "BEGIN TEST",
      "i=0",
      "j=54",
      "x=0.00&+00",
      "y=3.14&+00",
      "lx=0&+000",
      "ly=1&+004",
      "c=!0!",
      "d=B",
      "s=",
      "t=abracadab",
      "z.re=0.00&+00",
      "z.im=1.00&+00",
      "w.re=1.00&+00",
      "w.im=0.00&+00",
      "a=13",
      "b=(1,2,3,4)",
      "k=4",
      "q=(3.14&+00,2.83&+00)",
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

   integer i,j=54;
   real x,y=3.14;
   long real lx,ly=1.0&&4;
   character c,d='B';
   infix(string) s,t="abracadab";
   
	record complex; begin real re,im end
	infix(complex) z=record:complex(re=0.0,im=1.0)
	const infix(complex) w=record:complex(re=1.0,im=0.0)
	const integer a=13,b(4)=(1,2,3,4)
	const range(0:255) k=4;
	const real q(2)=(3.14,2.83)

   if verbose then prt2("--- ",PROGRAM_NAME); prt2("--- ",PURPOSE) endif;
-- ==============================================================================
 
      trace("BEGIN TEST");
      
      ed_str("i=");  ed_int(i);  trace(get_ed);
      ed_str("j=");  ed_int(j);  trace(get_ed);
      ed_str("x=");  ed_rea(x,3);  trace(get_ed);
      ed_str("y=");  ed_rea(y,3);  trace(get_ed);
      ed_str("lx="); ed_lrl(lx,1); trace(get_ed);
      ed_str("ly="); ed_lrl(ly,1); trace(get_ed);
      ed_str("c=");  ed_cha(c);  trace(get_ed);
      ed_str("d=");  ed_cha(d);  trace(get_ed);
      ed_str("s=");  ed_str(s);  trace(get_ed);
%      ed_str("t=");  ed_str(t);  trace(get_ed);

 
 end;
	 