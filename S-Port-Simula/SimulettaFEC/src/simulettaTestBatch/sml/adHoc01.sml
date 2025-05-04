begin
   SYSINSERT RT,SYSR,KNWN,UTIL,CENT;    

 Visible record Z_txtqnt;  info "TYPE";
 begin ref(Z_txtent)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
 end;

 Visible record Z_txtent:entity;
 begin character cha(0); end;

	infix(txtent) ent = record:txtent(cha=('a','b','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'));
	infix(txtqnt) txt;
	boolean res;
	
	txt.ent := ref(ent);
	txt.lp  := 10;
	
	ptintA(name(txt),444);
	
 end;
	 