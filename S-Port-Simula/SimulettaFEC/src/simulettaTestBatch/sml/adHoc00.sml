
begin
 sysinsert rt,sysr,knwn,util,strg;

Visible routine xREST; export infix(string) s; begin
	s.chradr:=@bio.utbuff(bio.utpos);
	s.nchr:=utlng-bio.utpos;
end;

Visible routine xED_STR; import infix(string) str; begin
	bio.utpos:=bio.utpos+PUTSTR(xREST,str);
end;

	infix(string) s,	q;
   	q:=s:="ABRA CA DAB";
%   	prt(s);
	xREST;
%	xED_STR(q);
%	bio.utpos:=bio.utpos+PUTSTR(xREST,q);
%	xREST;
      
%	TERMIN(0,q);

 end;
