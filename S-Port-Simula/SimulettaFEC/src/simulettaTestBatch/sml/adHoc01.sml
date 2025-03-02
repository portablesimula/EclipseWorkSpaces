begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
	
 Visible record txtquant;  info "TYPE";
 begin ref(txtent)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
 end;

 Visible const infix(txtquant) notext=record:txtquant(sp=0,lp=0,cp=0,ent=none);
       
 const infix(txtent:10) defident = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha=('!1!','!1!','!1!','!2!','!1!','!5!','!0!','!0!','!0!','!0!') );
 const infix(txtent:10) defident = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha=('!1!','!1!','!1!','!2!','!1!','!5!') );

 const infix(txtquant) acmdir=record:txtquant(ent=ref(defident), cp=0, sp=0, lp = 10);

 
 end;
	 