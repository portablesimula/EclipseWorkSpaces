begin
   SYSINSERT RT,SYSR,KNWN,UTIL;--,STRG,CENT;

%   const infix(string) PROGRAM_NAME ="SIMULETTA TEST NO 30";
       
   const infix(txtent: 10 ) ent1 = record:txtent
       (sl=none, sort=S_TXTENT, misc=1, ncha = 10 , cha = ('A','B','C') )
   
   infix(string) src;
   infix(txtqnt) txt;
   integer tpos;

%		PRINTO(0,PROGRAM_NAME,1); -- SKAL VÆRE: "ABC"
        
        txt.ent:=ref(ent1);
        src.chradr:=name(txt.ent.cha(tpos));   src.nchr:=3;
		PRINTO(0,src,1); -- SKAL VÆRE: "ABC"

 end;
