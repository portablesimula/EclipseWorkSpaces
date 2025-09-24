package iapx.qInstr;

import iapx.qPkt.Qpkt;

public class Q_RET extends Qpkt {
	int nparWords;
	
	public Q_RET(int nparWords) {
		this.nparWords = nparWords;
	}

	public String toString() {
		return "RET    " + nparWords; 
	}
}
