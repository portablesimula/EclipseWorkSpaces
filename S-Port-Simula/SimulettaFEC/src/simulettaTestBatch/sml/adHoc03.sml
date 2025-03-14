begin
   	SYSINSERT RT,SYSR,KNWN,UTIL;   
 
  Visible record RRRR; begin
       integer          edOflo;
       long real        initim;
       character        lwten;
       character        dcmrk;
       character        utbuff(30);    -- The output buffer
       integer          errmsg;   -- NOT in bioptp'refvec
 end;
   	
   	integer x,y; 
   	character c;
   	 Visible infix(RRRR) xxx;
   
	x := bio.utpos;
	y := utlng; 
	c := bio.utbuff;
	c := bio.utbuff(10);
	bio.utbuff(0) := 'A';
	bio.utbuff(10) := 'B';
	bio.utbuff(16) := 'C';
	bio.utbuff(17) := 'D';
%	bio.utbuff(199) := 'Z';
		ed_str("ERROR in first Case ");
	x := bio.utpos;
	y := utlng; 
		ed_str("ERROR in second Case ");

	TERMIN(0,"NORMAL END-OF-PROGRAM");

 end;
		 