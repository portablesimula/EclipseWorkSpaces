package iapx.qInstr;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.MemAddr;

public class Q_LOADA extends Qpkt {
	MemAddr opr;
	
	public Q_LOADA(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
        writes(reg); minds(reg);
	}

	public String toString() {
		String s = "LOADA  " + Reg.edReg(reg) + " " + opr;
		return s;
	}
}
