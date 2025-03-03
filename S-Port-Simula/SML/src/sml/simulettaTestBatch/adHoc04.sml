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
--   sysinsert rt,sysr,knwn,util,strg,cent,arr,fil,edit;
              -- ,libr,smst,sml;

	Visible record ENT;
--	begin character cha(10); end;
	begin integer ncha; character cha(0); end;

	Visible record REC;  info "TYPE";
	begin ref(ENT)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
--       real r;
	end;

--	const infix(ENT:3) ident = record:ENT(cha=('A','B','C'), ncha=3 );
	const infix(ENT:3) ident = record:ENT(cha="ABC", ncha=3 );
--	const infix(REC) acmdir=record:txtqnt(ent=ref(ident), cp=0, sp=0, lp = 10);
	const infix(REC) acmdir=record:REC(ent=ref(ident), cp=0, sp=0, lp = 10);

	infix(string) dst;
	infix(string) src;
	infix(ENT:3) xENT;
	infix(REC) img;
	character c(8);
	integer i,tpos;
	
	routine UTTXT1; import infix(REC) txt;
--	routine UTTXT; import infix(txtqnt) txt;
	begin character a,b,c; integer i,j,k;
		c:=txt.ent.cha(tpos)
	end;
	
	routine UTTXT; import infix(REC) txt;
--	routine UTTXT; import infix(txtqnt) txt;
	begin
		src.chradr:=name(txt.ent.cha(tpos));
		src.nchr:=3;
		
		img.cp:=1; img.ent:=ref(xENT);
		dst.chradr:=name(img.ent.cha(img.cp));
		src.chradr:=name(txt.ent.cha(tpos));
		src.nchr:=dst.nchr:=3; C_MOVE(src,dst);
	end;
	
--	OUTTXT(0,acmdir);
--	UTTXT(notext);
	UTTXT(acmdir);
	
--	img.cp:=1; img.ent:=ref(xENT);
--	dst.chradr:=name(img.ent.cha(img.cp));
--	src.chradr:=name(acmdir.ent.cha(tpos));
--	src.nchr:=3; dst.nchr:=8; C_MOVE(src,dst);
	
--	UTTXT(acmdir);

--	dst.chradr:=@c; dst.nchr:=8;
--	src.nchr:=3;
--	C_MOVE(src,dst);
--	c:=var(src.chradr);
--	c:=var(src.chradr)(2);
--	c:=acmdir.ent.cha(tpos);
--	i:=bio.errmsg.nchr;
 
 end;
	 