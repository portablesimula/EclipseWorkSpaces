begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
   
   infix(txtent) ent1 = record:txtent(cha="   12345");
   infix(txtqnt) txt1;
   name(infix(txtqnt)) x;
  integer i;
	infix(string) img;

	routine xED_INT; import integer i; begin
%		bio.utpos:=bio.utpos+PUTINT2(xREST,i); end;
%		bio.utpos:=bio.utpos+PUTINT2(img,i); end;
		PUTINT2(img,i); end;

	  txt1.ent := ref(ent1);
	  txt1.sp  := 4;
	  txt1.lp  := 8;

	  x := name(txt1);
%	  xED_INT(var(x).lp);
	  i := var(x).lp;

 
 end;
	 