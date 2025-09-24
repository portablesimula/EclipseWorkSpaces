package iapx.qInstr;

import iapx.qPkt.Qfrm5;
import iapx.qPkt.Qpkt;
import iapx.util.Util;

public class Q_DefFDEST extends Qpkt {
	Qpkt jmp;
	
	public Q_DefFDEST(Qpkt jmp) {
		if(jmp == null) Util.IERR("MASS.DefFDEST-1");
		if(jmp.fnc.ordinal() != Qfrm5.qJMP) Util.IERR("MASS.DefFDEST-2");
		this.jmp = jmp;
	}

	public String toString() {
		return "DefFDEST " + jmp;
	}
	
//	Visible Routine DefFDEST; import ref(Qfrm5) jmp;
//	begin ref(Object) dst;
//	%+D   RST(R_DefFDEST);
//	%+C   if jmp=none then IERR("MASS.DefFDEST-1") endif;
//	%+C   if jmp.fnc <> qJMP then IERR("MASS.DefFDEST-2") endif;
//	      if jmp.addr.kind=0 then DELETE(jmp); -- JMP to this FDEST is deleted --
//	%+C   elsif jmp.addr.kind<>fixadr then IERR("MASS.DefFDEST-3")
//	      else dst:=FreeObj(K_Qfrm6);
//	           if dst=none then dst:=NEWOBX(size(Qfrm6));
//	%+D             ObjCount(K_Qfrm6):=ObjCount(K_Qfrm6)+1;
//	           else FreeObj(K_Qfrm6):=dst qua FreeObject.next endif;
//	           dst qua Qfrm6.fnc:=qFDEST; dst qua Qfrm6.subc:=0;
//	           dst qua Qfrm6.reg:=0; dst qua Qfrm6.rela.val:=0;
//	           dst qua Qfrm6.type:=cANY;
//	           dst qua Qfrm6.aux:=jmp.addr.fix.val;
//	           dst qua Qfrm6.jmp:=jmp; jmp.dst:=dst;
//	           dst.kind:=K_Qfrm6; AppendQinstr(dst);
//	     endif;
//	end;

}
