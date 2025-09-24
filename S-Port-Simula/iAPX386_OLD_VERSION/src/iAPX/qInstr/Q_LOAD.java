package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_LOAD extends Qpkt {
	MemAddr opr;
	
	public Q_LOAD(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
	}

	public String toString() {
		String s = "LOAD   " + Reg.edreg(reg) + " " + opr;
		return s;
	}
}
