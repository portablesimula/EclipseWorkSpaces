package iapx.qInstr;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;

public class Q_MOV extends Qpkt {
	int reg2;
	
	public Q_MOV(int reg, int reg2) {
		this.reg = reg;
		this.reg2 = reg2;
        reads(reg2); writes(reg);
	}

	public String toString() {
		String s = "MOV    " + Reg.edReg(reg) + " " + Reg.edReg(reg2);
		return s;
	}
}
