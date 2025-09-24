begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC:inst;
	begin integer  i(4);
		  integer  k;
    	  integer  j;
    	  ref(inst) suc;
    	  infix(string) str;
    	  character c(3);
	end;

	Visible routine ALLOC;
	import size length;	export ref(inst) ins;
	begin ins:=bio.nxtAdr qua ref(inst);
		  bio.nxtAdr:= ( bio.nxtAdr + length) qua ref(entity);
		  ins.sort:= Scode.S_SUB;
	end;
       
	ref() pool;
	size poolsize;
	integer sequ;
	
	ref(REC) r1;
 
 		sequ := 1;
		poolsize:=SIZEIN(1,sequ);
		pool:=DWAREA(poolsize,sequ);
		bio.nxtAdr:=pool qua ref(entity);
		bio.lstAdr:=(pool+poolsize) qua ref(entity);
	
		r1:=ALLOC(size(REC));
		
		r1.j := 6666;
%		r1.i(1) := 222;
%		r1.i(4):=555;
%		r1.c := 'X';
%		ED_STR("j="); ED_INT(r1.j); ed_out;
		

 end;
	 