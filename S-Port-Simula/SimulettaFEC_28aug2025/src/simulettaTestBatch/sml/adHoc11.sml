begin
%   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT,CINT,ARR,FORM,LIBR,FIL,SMST,SML,EDIT,MNTR;    
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	routine REST1; export infix(string) s;
	begin s.chradr:=@bio.utbuff(bio.utpos); s.nchr:=utlng-bio.utpos; end;

	routine ED_STR1;   import infix(string) str;         begin bio.utpos:=bio.utpos+PUTSTR(REST1,str); end;

%	integer i, j;
%	i := 44;	j := i;
 	
 	ed_str1("ABRA");
 end;
	 