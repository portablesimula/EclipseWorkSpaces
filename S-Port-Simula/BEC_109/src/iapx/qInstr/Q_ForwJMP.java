package iapx.qInstr;

import iapx.qPkt.Qpkt;
import iapx.util.Scode;

public class Q_ForwJMP extends Qpkt {
	Scode cond;
	
	public Q_ForwJMP(Scode cond) {
		this.cond = cond;
	}

	public String toString() {
		return "ForwJMP " + cond;
	}
	
//	Visible Routine ForwJMP; import range(0:20) subc;
//	export ref(Object) jmp;
//	begin Infix(WORD) smbx; smbx.val:=0;
//	      jmp:=FreeObj(K_Qfrm5);
//	      if jmp=none then jmp:=NEWOBX(size(Qfrm5));
//	%+D        ObjCount(K_Qfrm5):=ObjCount(K_Qfrm5)+1;
//	      else FreeObj(K_Qfrm5):=jmp qua FreeObject.next endif;
//	      jmp qua Qfrm5.fnc:=qJMP; jmp qua Qfrm5.subc:=subc;
//	      jmp qua Qfrm5.reg:=0;    jmp qua Qfrm5.aux:=0;
//	      jmp qua Qfrm5.addr:=NewFixAdr(CSEGID,smbx);
//	      jmp qua Qfrm5.dst:=jmp; jmp qua Qfrm5.type:=cANY;
//	      jmp.kind:=K_Qfrm5; AppendQinstr(jmp);
//	end;

}
