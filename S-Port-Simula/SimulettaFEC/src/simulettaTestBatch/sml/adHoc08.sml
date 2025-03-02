begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
       
 const infix(txtent:10) defident1 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha=('A','B','C','D','E','F','G','H','I','J') );

 const infix(txtqnt) acmdir1=record:txtqnt(ent=ref(defident1), cp=0, sp=0, lp = 10);

 const infix(string) ERR_MSG(5) = ( "AAA", "BBB", "CCC" );

		ED_TXT(acmdir1);

 end;
	 