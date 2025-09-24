package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_LOADA extends Qpkt {
	MemAddr opr;
	
	public Q_LOADA(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
	}

	public String toString() {
		String s = "LOADA  " + Reg.edreg(reg) + " " + opr;
		return s;
	}
}
