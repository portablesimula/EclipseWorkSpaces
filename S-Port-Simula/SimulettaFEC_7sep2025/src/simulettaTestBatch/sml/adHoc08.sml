begin
   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT,CINT,ARR,FORM,LIBR,FIL,SMST,SML,EDIT,MNTR;    
%   SYSINSERT RT,SYSR,KNWN,UTIL;    

       record area;                 -- Definition of storage pool
       begin ref(area) suc;         -- Used to organize the pool list
             ref(entity) nxt,lim;   -- Boundary pointers within the pool
             ref(entity) startgc;   -- "freeze-address" for the pool
             size stepsize;         -- extend/contract step
             size mingap;           -- for this pool
             short integer sequ;    -- Sequence number (1,2, ... )
       end;

 
 Visible routine xTESTING;
 begin ref(area) p;
 		size poolsize;
 		integer sequ;
 		poolsize := 400 qua size;
 		sequ := 444;
 		
        p:=DWAREA(poolsize,sequ);
		p.sequ:=sequ;
 end;
 

%	TESTING;
	
	B_PROG;
	
 end;
	 