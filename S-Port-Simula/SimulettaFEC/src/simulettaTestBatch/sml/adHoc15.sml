begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

 Visible routine ptintA;
 import name(infix(txtqnt)) adr; integer val;
 begin infix(string) item; infix(txtqnt) txt;
       txt:=var(adr);
%       assert txt.ent<>none skip ERROR(ENO_TXT_12) endskip;   -- notext?
%       assert txt.ent.misc=0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       item.nchr:=txt.lp - txt.sp; item.chradr:=name(txt.ent.cha(txt.sp));
       PUTINT(item,val); if status <> 0 then ERR_PUT(item) endif;
       var(adr).cp:=txt.lp;
 end;
	
 end;
