package iapx.qInstr;

import iapx.qPkt.Qpkt;

public class Q_LEAVE extends Qpkt {
	int nlocWords;
	
//	Qfunc.Qf2(Opcode.qENTER,visflag,0,0,nlocbyte);
	public Q_LEAVE(int nlocWords) {
		this.nlocWords = nlocWords;
	}

	public String toString() {
		return "LEAVE  nlocWords=" + nlocWords;
	}
}
