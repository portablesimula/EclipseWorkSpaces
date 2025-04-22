begin
   SYSINSERT RT,SYSR,KNWN,UTIL; 
      
 routine TXT2STR;
import infix (txtqnt) txt; export infix (string) str;
begin str.nchr:=txt.lp - txt.sp;
      str.chradr:=if txt.lp = 0 then noname
      else name(txt.ent.cha(txt.sp));
end;
      
    infix(String) str;
    
	infix(txtent:6) systxt = record:txtent(sort=S_TXTENT, misc=1, ncha=6, cha="SYSOUT"); 
	infix(txtqnt) sysid = record:txtqnt(ent=ref(systxt), lp=6);
	infix(filent) ent = record:filent(eof=false);
	ent.nam := sysid;
	
%	ED_STR("FIL.OPEN: sysid=");
	ED_TXT(sysid); ED_OUT;  

%	str := txt2str(sysid); 

 end;
	 