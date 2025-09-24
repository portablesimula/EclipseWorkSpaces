package iAPX.qInstr;

import iAPX.qPkt.Qpkt;

public class Q_POPK extends Qpkt {
	int count;
	
	public Q_POPK(int count) {
		this.count = count;
	}

	public String toString() {
		String s = "POPK   " + count;
		return s;
	}
}
