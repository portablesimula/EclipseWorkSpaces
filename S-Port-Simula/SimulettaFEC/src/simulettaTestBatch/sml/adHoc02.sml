begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
       
 const infix(txtent:10) defident2 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha="ABCDEFGHIJ" );

 const infix(txtqnt) acmdir2=record:txtqnt(ent=ref(defident2), cp=0, sp=0, lp = 10);

	routine TXT2STR;
	import infix (txtqnt) txt; export infix (string) str;
	begin
%	 str.nchr:=txt.lp - txt.sp;
%	      str.chradr:=if txt.lp = 0 then noname
%	      else name(txt.ent.cha(txt.sp));
%	      if txt.lp = 0 then str.chradr := noname; else	 
	      	 str.chradr := name(txt.ent.cha(txt.sp+7));
%	      endif;	 
	end;

		 TXT2STR(acmdir2);
		 TXT2STR(acmdir2);

 end;
	 