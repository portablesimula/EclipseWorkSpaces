--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace
begin
   SYSINSERT RT,SYSR,KNWN,UTIL,STRG,CENT;


	Visible routine XALLOC;
	import size length;	export ref(inst) ins;
	begin ins:=bio.nxtAdr; bio.nxtAdr:= bio.nxtAdr + length;
		  ins.sort:= S_SUB;
		  ED_STR("ALLOC - bio.nxtAdr="); ED_OADDR(bio.nxtAdr); ED_STR(", ins="); ED_OADDR(ins); ED_OUT;
	end;
       
	ref() pool;
	size poolsize;
	integer sequ;
	
	record anyPro:inst; begin infix(quant) val end;

	ref(inst) curproc;
	infix(anyPro) dummy_pro;
	infix(txtqnt) txt; -- text reference quantity
     
		poolsize:=SIZEIN(1,sequ);
		pool:=DWAREA(poolsize,sequ);
		bio.nxtAdr:=pool;
		bio.lstAdr:=pool+poolsize;
	
--		curproc:=ALLOC(size(anyPro));
		ED_STR("ALLOC - bio.nxtAdr="); ED_OADDR(bio.nxtAdr); ED_STR(", bio.lstAdr="); ED_OADDR(bio.lstAdr); ED_OUT;
		curins:=XALLOC(size(anyPro));
		curins:=XALLOC(size(anyPro));
		curins:=XALLOC(size(anyPro));
		
		E_FUNC;

 end;
	 