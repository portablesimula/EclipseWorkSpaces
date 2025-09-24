package iapx.qInstr;

import static iapx.util.Reg.qM;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.MemAddr;

public class Q_STORE extends Qpkt {
	MemAddr opr;
	
	public Q_STORE(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
        reads(reg); writes(qM);

	}

	public String toString() {
		String s = "STORE  " + Reg.edReg(reg) + " " + opr;
		return s;
	}
}
