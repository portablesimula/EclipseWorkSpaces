package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_STORE extends Qpkt {
	MemAddr opr;
	
	public Q_STORE(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
	}

	public String toString() {
		String s = "STORE  " + Reg.edreg(reg) + " " + opr;
		return s;
	}
}
