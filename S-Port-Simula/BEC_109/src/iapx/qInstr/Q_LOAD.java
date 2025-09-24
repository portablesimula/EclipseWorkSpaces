package iapx.qInstr;

import static iapx.util.Reg.qM;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.MemAddr;

// reg := (opr)
public class Q_LOAD extends Qpkt {
	MemAddr opr;
	
	public Q_LOAD(int reg, MemAddr opr) {
		this.reg = reg;
		this.opr = opr;
        reads(qM); writes(reg); minds(reg);
	}

	public String toString() {
		String s = "LOAD   " + Reg.edReg(reg) + " " + opr;
		return s;
	}
}
