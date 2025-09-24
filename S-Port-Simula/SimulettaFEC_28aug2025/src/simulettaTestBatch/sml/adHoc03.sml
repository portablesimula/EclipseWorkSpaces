begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC:inst;
	begin integer   i;
    	  integer   j;
    	  character c;
    	  variant   integer int;
    	            real    rea;
    	  variant   infix(string) str;
	end;

	Visible routine ALLOC;
	import size length;	export ref(inst) ins;
	begin ins:=bio.nxtAdr qua ref(inst);
		  bio.nxtAdr:= ( bio.nxtAdr + length) qua ref(entity);
		  ins.sort:= Scode.S_SUB;
	end;

	character c;
	integer i;
	real r;
	long real d;
       
	ref() pool;
	size poolsize;
	integer sequ;
	
	ref(REC) r1;
	ref(REC) x;
	field(character) f;
	name(ref(REC)) n;	

 
		poolsize:=SIZEIN(1,sequ);
		pool:=DWAREA(poolsize,sequ);
		bio.nxtAdr:=pool qua ref(entity);
		bio.lstAdr:=(pool+poolsize) qua ref(entity);
	
		r1:=ALLOC(size(REC)) --qua ref(REC);
		
		r1.c:='Q';
%		n:=name(r1.c);
%		x:=n qua ref(REC);
%		f:=n qua field(character);
%		c:=var(x+f) -- qua character;
		
	

 end;
		 