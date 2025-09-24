package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_PUSHR extends Qpkt {
	
	public Q_PUSHR(int reg) {
		this.reg = reg;
	}

	public String toString() {
	//   when qPUSHR: ------ PUSHR   reg ----------------------  Format 1
//      --- Q-Code:
		String s = "PUSHR  " + Reg.edreg(reg);
//      if Alist
//      then I_CODE;
//           outstring("PUSH   "); outreg(WholeReg(subc));
//      endif;
		return s;
	}
}
