
Module XXXXXX("XXX");
begin
 sysinsert rt,sysr,knwn,util,strg;


 Visible macro DEF_TXTENT(3)
 begin const infix(txtent: %2 ) %1 = record:txtent
       (sl=none, sort=S_TXTENT, misc=1, ncha = %2 , cha = %3 )
 endmacro;

 Visible macro REC_TXTQNT(2)
 begin record:txtqnt ( ent = ref( %1 ), cp=0, sp=0, lp = %2 )
 endmacro;

 DEF_TXTENT(%defdent%,%10%,
       %('!1!','!1!','!1!','!2!','!1!','!5!','!0!','!0!','!0!','!0!')%);

 -- const
  infix(txtqnt) acmdir= REC_TXTQNT(%defdent%,%10%);



  txttmp:=acmdir;
%  curins qua ref(filent).action:=COPY(txttmp);
       
%       ED_STR("FIL.FILDCL3: acmdir='"); ED_TXT(acmdir); ED_STR("', acmdir.ent="); ED_OADDR(acmdir.ent); ED_OUT;
%       ED_STR("FIL.FILDCL3: txttmp='"); ED_TXT(txttmp); ED_STR("', txttmp.ent="); ED_OADDR(txttmp.ent); ED_OUT;
%       ED_STR("FIL.FILDCL3: action='"); ED_TXT(curins qua ref(filent).action); ED_STR("', action.ent="); ED_OADDR(curins qua ref(filent).action.ent); ED_OUT;
%       ED_STR("FIL.FILDCL3: txttmp="); ED_TXT(txttmp); ED_OUT;
%       ED_STR("FIL.FILDCL3: action="); ED_TXT(curins qua ref(filent).action); ED_OUT;
%	   DMPENT(curins); -- &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
       

 end;
