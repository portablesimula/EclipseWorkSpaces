begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    

 routine OUTTXT;
 import ref(filent) fil; infix(txtqnt) txt;
 begin infix(txtqnt) img;           --  Local copy here for efficiency.
       infix(string) src;           --  Copy from this string.
       infix(string) dst;           --  Copy to this string.
       integer imlength,tpos,tlen;  --  Used for long strings
       
%	   ed_str("*** OUTTXT: "); ed_oaddr(txt.ent);
%	   ed_str(", CP: "); ed_int(txt.cp);
%	   ed_str(", SP: "); ed_int(txt.sp);
%	   ed_str(", LP: "); ed_int(txt.lp); ed_out;
       
       img:=fil.img; tpos:=txt.sp; tlen:=txt.lp-tpos;
  end;
   
	name(infix(txtqnt)) qtex;
	
	qtex:=name(var(qtex)(1));

 end;
		 