begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC;  info "TYPE";
	begin ref(ENT)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
	end;
 
 	Visible record ENT;
	begin integer xxx,yyy,ncha; character cha(0); end;
--	begin integer xxx,yyy,nstr; infix(string) str(0); end;

--	infix(ENT:3) xENT;
--	const infix(ENT:3) ident = record:ENT(cha=('A','B'), ncha=3 );
--	const infix(ENT:3) ident = record:ENT(cha=("ABC","DE"), nstr=3 );
   
	const infix(ENT:3) ident = record:ENT(cha="ABC", ncha=3 );
	const infix(REC) txt=record:REC(ent=ref(ident), cp=0, sp=0, lp = 10);

	infix(string) dst;
	infix(string) src;
	infix(ENT:3) xENT;
	infix(REC) img;
	integer tpos;
		
		img.cp:=1;
		img.ent:=ref(xENT);
		dst.chradr:=name(img.ent.cha(img.cp));
		src.chradr:=name(txt.ent.cha(tpos));
		src.nchr:=dst.nchr:=3;
		C_MOVE(src,dst);
 
 end;
	 