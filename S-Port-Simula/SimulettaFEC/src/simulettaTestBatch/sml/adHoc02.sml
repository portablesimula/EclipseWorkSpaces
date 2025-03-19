begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC;  info "TYPE";
	begin ref(ENT)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
	end;
 
 	Visible record ENT;
	begin integer xxx,yyy,ncha; character cha1; end;

	infix(ENT) xENT;
	infix(REC) img;
	integer tpos;
	
	img.ent:=ref(xENT);

	img.ent.ncha := 44;
	tpos := img.ent.ncha;
 
 end;
	 