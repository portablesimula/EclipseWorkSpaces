package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_POPR extends Qpkt {
	
	public Q_POPR(int reg) {
		this.reg = reg;
	}

	public String toString() {
	//   when qPUSHR: ------ PUSHR   reg ----------------------  Format 1
//      --- Q-Code:
		String s = "POPR   " + Reg.edreg(reg);
		return s;
	}
}
