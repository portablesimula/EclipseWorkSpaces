begin
   SYSINSERT RT,SYSR,KNWN,UTIL
   ,strg,cent,cint,arr,form,libr,fil,smst,sml,edit,mntr;

%	infix(txtent) ent1 = record:txtent(cha=('a','b','c','d','e','f'));
	infix(txtent) ent1 = record:txtent(cha="   12345");
	infix(txtqnt) txt1;
	integer res;

	txt1.ent := ref(ent1);
	txt1.lp  := 8;
	
% 	res := gtintA(ref(txt1));
 	res := gtintT(txt1);
 end;
	 