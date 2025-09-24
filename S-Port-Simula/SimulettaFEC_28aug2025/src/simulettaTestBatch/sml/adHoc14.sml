begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Routine TXT2STR;
	import infix (txtqnt) txt; export infix (string) str;
	begin str.nchr:=txt.lp - txt.sp;
	      str.chradr:=if txt.lp = 0 then noname
	      else name(txt.ent.cha(txt.sp));
	end;

% Visible const infix(txtqnt) notext=record:txtqnt(sp=0,lp=0,cp=0,ent=none);
       
% const infix(txtent:10) defident1 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha=('A','B','C','D','E','F','G','H','I','J') );
 const infix(txtent:10) defident2 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha="ABCDEFGHIJ" );

% const infix(txtqnt) acmdir1=record:txtqnt(ent=ref(defident1), cp=0, sp=0, lp = 10);
 const infix(txtqnt) acmdir2=record:txtqnt(ent=ref(defident2), cp=0, sp=0, lp = 10);

      
%		ED_STR("NOTEXT="""); ED_TXT(notext); ED_CHA('"'); trace(get_ed); 
%		ED_STR("TEXT="""); ED_TXT(acmdir1); ED_CHA('"'); trace(get_ed); 


		 ED_TXT(acmdir2);

%	infix(string) str;
	
%	str := txt2str(acmdir2);
%	str := tx2str(acmdir2);
	

 end;
