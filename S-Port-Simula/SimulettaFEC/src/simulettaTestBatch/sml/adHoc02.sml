begin
   SYSINSERT RT,SYSR,KNWN,UTIL,strg,cent,cint,arr,form,libr,fil,smst,sml,edit,mntr;

 Routine TEST; -- VAR: npAccA
 import name(infix(parqnt)) fnp;  --  parameter quantity address
 export name() adr;               --  actual parameter's address
 begin ref(pardes)   ap;          --  Local copy here for efficiency.
 	integer i;
 
%       ed_str("FORM.npAccA: fnp="); ed_GADDR(fnp); ed_out;
       ap:= var(fnp).ap;
%       ed_str("FORM.npAccA: ap="); ed_OADDR(fnp); ed_out;
%       ed_str("FORM.npAccA: ap.code="); ed_INT(ap.code); ed_out;
	i := ap.code;
end;


	Visible routine TEST2; -- VAR: EncInt
	import name(infix(parqnt)) fnp; export integer val; begin
%		E_FNP_ACC_CNV(%integer%,%"EncInt"%);
		name() adr;
		adr:= TEST(fnp);
	end;


	infix(parqnt) par;
	infix(pardes) pds;
	ref(pardes) ap;
	
	B_PROG;
	
	par.ap := ref(pds);
	ap := par.ap;
	ap.code := 44;
	
	TEST2(name(par));

 end;
	 