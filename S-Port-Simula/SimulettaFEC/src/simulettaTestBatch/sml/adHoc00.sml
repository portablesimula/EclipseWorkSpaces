
begin
 sysinsert rt,sysr,knwn,util,strg;

Visible routine xREST; export infix(string) s; begin
	s.chradr:=@bio.utbuff(bio.utpos);
	s.nchr:=utlng-bio.utpos;
end;

Visible routine xED_STR; import infix(string) str; begin
	bio.utpos:=bio.utpos+PUTSTR(xREST,str);
end;

Visible routine xPRT2; import infix(string) t,t2;            begin xED_STR(t); xED_STR(t2); xED_OUT end;
Visible routine xPRT;  import infix(string) t;               begin xED_STR(t); xED_OUT end
Visible routine xPRT2; import infix(string) t,t2;            begin xED_STR(t); xED_STR(t2); xED_OUT end;
Visible routine xPRT3; import infix(string) t,t2,t3;         begin xED_STR(t); xED_STR(t2); xED_STR(t3); xED_OUT end;

Visible routine xED_OUT;
begin infix(string) im; 
      if bio.utpos > 0 then
           im.chradr:=@bio.utbuff; im.nchr:=bio.utpos;
           SYSPRI(im); bio.utpos:=0;
      endif;
end;


   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 01";

	infix(string) s,	q;
   	q:=s:="ABRA CA DAB";
%	prt(s);
%	xPRT2("--- ",PROGRAM_NAME);
	xED_STR(PROGRAM_NAME);
	xED_OUT;
%	xREST;
	
%	xED_STR(q);
%	bio.utpos:=bio.utpos+PUTSTR(xREST,q);
%	xREST;
      
%	TERMIN(0,q);

 end;
