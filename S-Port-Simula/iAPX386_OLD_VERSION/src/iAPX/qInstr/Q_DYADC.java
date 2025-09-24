package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;
import svm.value.Value;

public class Q_DYADC extends Qpkt {
	public enum Subc { qAND, qOR, qXOR, qANDM, qORM, qXORM, qINCO, qDECO, qCMP, qADD, qSUB, qADDM, qSUBM, qADC, qSBB, qADDF,qSUBF, qADCF, qSBBF }
	Subc subc;
	Value value;
	
	public Q_DYADC(Subc subc, int reg, Value value) {
		this.subc = subc;
		this.reg = reg;
		this.value = value;
	}

	public String toString() {
		String s = "DYADR  " + subc + " " + Reg.edreg(reg) + " " + value;
		return s;
	}
}
