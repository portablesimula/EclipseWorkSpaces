package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_DYADR extends Qpkt {
	public enum Subc { qAND, qOR, qXOR, qANDM, qORM, qXORM, qINCO, qDECO, qCMP, qADD, qSUB, qADDM, qSUBM, qADC, qSBB, qADDF,qSUBF, qADCF, qSBBF }
	Subc subc;
	int reg2;
	
	public Q_DYADR(Subc subc, int reg, int reg2) {
		this.subc = subc;
		this.reg = reg;
		this.reg2 = reg2;
	}

	public String toString() {
		String s = "DYADR  " + subc + " " + Reg.edreg(reg) + " " + Reg.edreg(reg2);
		return s;
	}
}
