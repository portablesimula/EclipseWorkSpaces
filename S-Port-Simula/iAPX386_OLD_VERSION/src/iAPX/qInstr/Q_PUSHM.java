package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.qPkt.Qpkt;

public class Q_PUSHM extends Qpkt {
	int size;
	MemAddr opr;
	
	public Q_PUSHM(int size, MemAddr opr) {
		this.size = size;
		this.opr = opr;
	}

	public String toString() {
		return "PUSHM  " + size + " " + opr;
	}
}
