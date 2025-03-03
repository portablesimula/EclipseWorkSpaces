begin
   SYSINSERT RT,SYSR,KNWN,UTIL;


 Visible record xPARQNT;  info  "TYPE";
 begin ref(atrdes) fp;
       ref(pardes) ap;
       ref(entity) ent;
       boolean sem;
       variant field() fld;
       variant label pad;
               range(0:MAX_CLV) clv;
       variant ref(proptp) ppp;
               ref(claptp) qal;
       variant ref(swtdes) des;
 end;

 Visible routine xE_FPT;
 import short integer n;       --  No. (position) of parameter.
        infix(xparqnt) par;     --  Transmitting this parameter.
        ref(inst) cla_sl;  --  Qualifying class declared here.
                               --  Different from none <0> ref type.
 export ref(inst) parins;  --  Transmitting to this procedure.
 begin
 end;
 
 end;
	 