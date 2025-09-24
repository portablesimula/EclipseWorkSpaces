package iAPX.qInstr;

import iAPX.qPkt.Qpkt;

public class Q_LABEL extends Qpkt {
	public enum Subc {	NULL, qBPROC, qEPROC }
	Subc subc;
	int fixval;
	String smbx;
	
	public Q_LABEL(Subc subc, int fixval, String smbx) {
		this.subc = subc;
		this.fixval = fixval;
		this.smbx = smbx;
	}

	public String toString() {
		return "LABEL " + subc + " " + fixval + " " + smbx;
	}
}
