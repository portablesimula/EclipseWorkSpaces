begin
   SYSINSERT RT,SYSR,KNWN,UTIL;--,STRG,CENT;

       
   const infix(txtent: 10 ) ent1 = record:txtent
       (sl=none, sort=S_TXTENT, misc=1, ncha = 10 , cha = ('A','B','C') )
   infix(txtent: 10 ) ent2;
   
   infix(string) src,dst;
   infix(txtqnt) img;
   infix(txtqnt) txt;
   integer tpos;
           
        txt.ent:=ref(ent1);
        img.ent:=ref(ent2);
        src.chradr:=name(txt.ent.cha(tpos));   src.nchr:=3;
        dst.chradr:=name(img.ent.cha(img.cp)); dst.nchr:=3;
        C_MOVE(src,dst);
%		ED_STR("dst="); ED_STR(dst); ed_Out;
	

 end;
	 