begin
   SYSINSERT RT,SYSR,KNWN,UTIL,strg,cent,cint,arr,form,libr,fil,smst,sml,edit,mntr;

	Routine TEST; import infix(txtqnt) left,right; begin
		integer i;            --  Loop index.
		integer dif;          --  Difference between lengths.
		integer lng;          --  Length of common parts.
		lng:=right.lp-right.sp;
		dif:=lng-(left.lp-left.sp);
	end;
	

 Visible routine CONCAT;
   import range(0:MAX_DIM) npar; infix(txtqnt) param(MAX_DIM);
   export infix(txtqnt) res;
 begin short integer ncha; size lng; infix(txtqnt) tmp;
       infix(string) res_str,src_str; short integer i;
       
       infix(txtqnt) tmp2(10);
       
       ed_str("CONCAT:: npar="); ed_int(npar); ed_out;
       ed_str("CONCAT:: param(0)="); ed_txt(param(0)); ed_out;
       ed_str("CONCAT:: param(1)="); ed_txt(param(1)); ed_out;
       
       i:=ncha:=0;
       repeat while i<npar do
              --- move parameter into bio for update by possible GC
              tmp:=bio.conc.elt(i):=param(i);
              tmp:=tmp2(i):=param(i);
              
		      ed_str("CONCAT:1: tmp.lp="); ed_int(tmp.lp); ed_out;
		      ed_str("CONCAT:1: tmp2(i).lp="); ed_int(tmp2(i).lp); ed_out;
		      ed_str("CONCAT:1: bio.conc.elt(i).lp="); ed_int(bio.conc.elt(i).lp); ed_out;
		      
              ncha:=ncha+tmp.lp-tmp.sp; i:=i+1;
       endrepeat;
       if ncha=0
       then res:=notext;
            ---  Check that the save entity invariant still holds!!
            if bio.nxtAdr > bio.lstAdr then GARB2 endif;
       else ---  Create Text Object. If necessary call garbage collector
            NEW_TXT(bioref);
            ---  Move the parameters succesively into result object
            i:=ncha:=0;
            repeat while i<npar do
                   tmp:=bio.conc.elt(i); bio.conc.elt(i):=notext;
                   src_str.nchr:=res_str.nchr:=tmp.lp-tmp.sp;
                   
				   ed_str("CONCAT:: tmp.lp="); ed_int(tmp.lp); ed_out;
				   ed_str("CONCAT:: src_str.nchr="); ed_int(src_str.nchr); ed_out;
				   
				   if src_str.nchr > 0 then
                      src_str.chradr:=name(tmp.ent.cha(tmp.sp));
                      res_str.chradr:=name(res.ent.cha(ncha));
                      C_MOVE(src_str,res_str);
                   endif;
                   i:=i+1; ncha:=ncha+src_str.nchr;
            endrepeat;
       endif;
%-X    if bio.trc then bio.obsEVT:=EVT_TXT; bio.GCval:=ncha;
%-X       observ endif;

       ed_str("CONCAT:: res.lp="); ed_int(res.lp); ed_out;
       ed_str("CONCAT:: res="); ed_txt(res); ed_out;

 end;
	
	infix(txtent) ent1 = record:txtent(cha=('a','b','c','d','e','f','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'));
	infix(txtent) ent2 = record:txtent(cha=('A','B','R','A','e','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'));
	infix(txtqnt) txt1, txt2;

	txt1.ent := ref(ent1);
	txt1.lp  := 6;

	txt2.ent := ref(ent2);
	txt2.lp  := 4;
	
%	TEST(txt1, txt2);

	B_PROG;
	CONCAT(2,(txt1,txt2));
 end;
	 