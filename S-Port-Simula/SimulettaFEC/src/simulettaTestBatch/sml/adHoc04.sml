begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
       
 const infix(txtent:10) defident2 = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha="ABCDEFGHIJ" );

 const infix(txtqnt) acmdir2=record:txtqnt(ent=ref(defident2), cp=0, sp=0, lp = 10);

		 ED_TXT(acmdir2);
		 ed_out;
	

 end;
	 