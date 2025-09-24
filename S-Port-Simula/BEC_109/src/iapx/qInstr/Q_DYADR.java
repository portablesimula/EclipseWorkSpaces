package iapx.qInstr;

import static iapx.util.Reg.qF;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;

public class Q_DYADR extends Qpkt {
	public enum Subc { qAND, qOR, qXOR, qANDM, qORM, qXORM, qINCO, qDECO, qCMP, qADD, qSUB, qADDM, qSUBM, qADC, qSBB, qADDF,qSUBF, qADCF, qSBBF }
	Subc subc;
	int reg2;
	
	public Q_DYADR(Subc subc, int reg, int reg2) {
		this.subc = subc;
		this.reg = reg;
		this.reg2 = reg2;
		reads(reg, reg2);
		if(subc == Subc.qCMP) {
			writes(qF);
		} else {
			writes(reg, qF);
		}
	}

	public String toString() {
		String s = "DYADR  " + subc + " " + Reg.edReg(reg) + " " + Reg.edReg(reg2);
		return s;
	}
}
