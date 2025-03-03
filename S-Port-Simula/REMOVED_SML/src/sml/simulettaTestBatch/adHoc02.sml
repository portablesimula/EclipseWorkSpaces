
begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    
 
	const infix(txtent:10) ident = record:txtent(sl=none, sort=S_TXTENT, misc=1, ncha=10, cha=('A','B','C','D','E','F') );
 	const infix(txtqnt) text=record:txtqnt(ent=ref(ident), cp=0, sp=0, lp = 10);
 	
 	PRT("START")
	if notext = notext then PRT("RIKTIG: notext = notext") else PRT("FEIL: notext ne notext") endif; 

	if text = text     then PRT("RIKTIG: text = text") else PRT("FEIL: text ne text") endif; 

	if text ne notext  then PRT("RIKTIG: text ne notext") else PRT("FEIL: text = notext") endif; 

	if notext ne text  then PRT("RIKTIG: notext ne text") else PRT("FEIL: notext = text") endif; 

 end;
	 