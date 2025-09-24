package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_POPM extends Qpkt {
	int size;
	MemAddr opr;
	
	public Q_POPM(int reg, int size, MemAddr opr) {
		this.reg = reg;
		this.size = size;
		this.opr = opr;
	}

	public String toString() {
		String s = "POPM   " + Reg.edreg(reg) + " " + opr + " " + size;
		return s;
	}
}
