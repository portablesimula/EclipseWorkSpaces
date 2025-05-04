begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Routine TEST; import infix(txtqnt) left,right; begin
		integer i;            --  Loop index.
		integer dif;          --  Difference between lengths.
		integer lng;          --  Length of common parts.
		lng:=right.lp-right.sp;
		dif:=lng-(left.lp-left.sp);
	end;
	
	infix(txtent) ent = record:txtent(cha=('a','b','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'));
	infix(txtqnt) txt1, txt2;

	txt1.ent := ref(ent);
	txt1.lp  := 10;

	txt2.ent := ref(ent);
	txt2.lp  := 15;
	
	TEST(txt1, txt2);
 end;
	 