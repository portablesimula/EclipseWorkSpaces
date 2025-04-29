begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    

Routine xTXTREL; -- text value relations
import infix(txtqnt) left,right; integer code;
export boolean rel;
begin integer i;            --  Loop index.
      integer dif;          --  Difference between lengths.
      integer lng;          --  Length of common parts.
      lng:=right.lp-right.sp; dif:=lng-(left.lp-left.sp);
      if dif <> 0
      then if code = 2 then rel:=false; goto EXX1;
           elsif code = 5 then rel:=true; goto EXX2 endif;
           if dif > 0 then lng:=left.lp - left.sp endif;
      endif;
      i:=0; repeat while i < lng
      do if right.ent.cha(right.sp + i) <> left.ent.cha(left.sp + i)
         then dif:=(right.ent.cha(right.sp+i) qua integer)
                  - (left.ent.cha(left.sp+i) qua integer);
              goto EXX3;
         endif;
         i:=i + 1;
      endrepeat;
EXX3:
      case 1:6 (code)
      when 1: rel:=0 <  dif
      when 2: rel:=0  = dif
      when 3: rel:=0 <= dif
      when 4: rel:=0 >  dif
      when 5: rel:=0 <> dif
      when 6: rel:=0 >= dif
      otherwise ERROR(ENO_ITN_1);
      endcase;
EXX1:EXX2:end;

	infix(txtqnt) left,right; integer code;
	boolean res;
	
	res := xTXTREL(left, right, 1);

 end;
	 