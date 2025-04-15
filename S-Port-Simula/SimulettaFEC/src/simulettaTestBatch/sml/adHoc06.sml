begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

   boolean a,b,c=true,d=false,e=true;
   
	Visible routine xED_BOOL; import boolean b; begin
		if b then ED_STR("true") else ED_STR("false") endif;
	end;
 
	ed_bool(b and c);

 end;
	 