package iapx.qInstr;

import static iapx.util.Reg.qF;
import static iapx.util.Reg.qSP;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.MemAddr;

public class Q_POPM extends Qpkt {
	int size;
	MemAddr opr;
	
	public Q_POPM(int reg, int size, MemAddr opr) {
		this.reg = reg;
		this.size = size;
		this.opr = opr;
        writes(qSP, qF); reads(qSP);
	}

	public String toString() {
		String s = "POPM   " + Reg.edReg(reg) + " " + opr + " " + size;
		return s;
	}
}
