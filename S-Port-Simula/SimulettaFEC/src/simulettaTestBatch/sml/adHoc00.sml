
begin
 sysinsert rt,sysr,knwn,util,strg;

Visible routine xREST; export infix(string) s; begin
	s.chradr:=@bio.utbuff(bio.utpos);
	s.nchr:=utlng-bio.utpos;
end;

Visible routine xED_STR; import infix(string) str; begin
	bio.utpos:=bio.utpos+PUTSTR(xREST,str);
end;

Visible routine xPRT2; import infix(string) t,t2;            begin xED_STR(t); xED_STR(t2); ED_OUT end;


   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 01";

	infix(string) s,	q;
   	q:=s:="ABRA CA DAB";
%   	prt(s);
%	xPRT2("--- ",PROGRAM_NAME);
%	xED_STR(PROGRAM_NAME);
	xREST;
	
%	xED_STR(q);
%	bio.utpos:=bio.utpos+PUTSTR(xREST,q);
%	xREST;
      
%	TERMIN(0,q);

 end;
