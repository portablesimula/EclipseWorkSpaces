package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_MOV extends Qpkt {
	int reg2;
	
	public Q_MOV(int reg, int reg2) {
		this.reg = reg;
		this.reg2 = reg2;
	}

	public String toString() {
		String s = "MOV    " + Reg.edreg(reg) + " " + Reg.edreg(reg2);
		return s;
	}
}
