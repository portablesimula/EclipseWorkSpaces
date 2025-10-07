begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
       
 const infix(txtent:10) defident2 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha="ABCDEFGHIJ" );

 const infix(txtqnt) txt=record:txtqnt(ent=ref(defident2), cp=6, sp=4, lp = 10);


 Visible routine ptintA;
 import name(infix(txtqnt)) adr; integer val;
 begin infix(string) item; infix(txtqnt) txt;
       txt:=var(adr);
%       assert txt.ent<>none skip ERROR(ENO_TXT_12) endskip;   -- notext?
%       assert txt.ent.misc=0 skip ERROR(ENO_TXT_13) endskip; -- Const?
       item.nchr:=txt.lp - txt.sp; item.chradr:=name(txt.ent.cha(txt.sp));
       PUTINT(item,val);
%      if status <> 0 then ERR_PUT(item) endif;
       var(adr).cp:=txt.lp;
 end;

%	infix (string) str;	str.chradr := name(txt.ent.cha(txt.sp));

	ptintA(@txt, 444);
 end;
	 