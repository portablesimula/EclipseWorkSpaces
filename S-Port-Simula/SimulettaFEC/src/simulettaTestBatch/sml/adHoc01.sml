begin
   SYSINSERT RT,SYSR,KNWN,UTIL
%   ,strg,cent,cint,arr,form,libr,fil,smst,sml,edit,mntr;


   routine TEST; import name(infix(txtqnt)) dst; begin
   	  integer i;
%      i := var(dst).lp - var(dst).sp;
      i := var(dst).lp;
% +M	   ED_STR("EDIT.TXTASS: dst_str.nchr="); ED_INT(dst_str.nchr); ED_OUT;
% +M	   ED_STR("EDIT.TXTASS: src_str.nchr="); ED_INT(src_str.nchr); ED_OUT;
   end;

	infix(txtent) ent1 = record:txtent(cha="   12345");
	infix(txtqnt) txt1;
	integer res;

	txt1.ent := ref(ent1);
	txt1.lp  := 8;
	
% 	res := gtintA(ref(txt1));
% 	res := gtintT(txt1);

	TEST(name(txt1));
 end;
	 