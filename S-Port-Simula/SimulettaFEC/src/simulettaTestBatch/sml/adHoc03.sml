begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC:inst;
	begin integer  i(4);
    	  integer  j;
	end;

	Visible routine ALLOC;
	import size length;	export ref(inst) ins;
	begin ins:=bio.nxtAdr qua ref(inst);
		  bio.nxtAdr:= ( bio.nxtAdr + length) qua ref(entity);
		  ins.sort:= S_SUB;
	end;
       
	ref() pool;
	size poolsize;
	integer sequ;
	
	ref(REC) r1;
	ref(REC) x,w;

 	sequ := 1;
		poolsize:=SIZEIN(1,sequ);
		pool:=DWAREA(poolsize,sequ);
		bio.nxtAdr:=pool qua ref(entity);
		bio.lstAdr:=(pool+poolsize) qua ref(entity);
	
		r1:=ALLOC(size(REC));
		x:=ALLOC(size(REC));
		w:=ALLOC(size(REC));
		
		w.i(0):=1111;
		w.i(1):=2222;
		w.i(2):=3333;
		w.i(3):=4444;
		w.j:=5555;
		w.sl := x;
		ED_STR("w.j="); ED_INT(w.j); ED_OUT;
		ED_STR("r1.j="); ED_INT(r1.j); ED_OUT;
		
		MOVEIN(w, r1, size(REC));
		
		ED_STR("w.j="); ED_INT(w.j); ED_OUT;
		ED_STR("r1.j="); ED_INT(r1.j); ED_OUT;

 end;
		 