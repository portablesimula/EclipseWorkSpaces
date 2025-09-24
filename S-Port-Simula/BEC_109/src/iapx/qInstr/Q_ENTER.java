package iapx.qInstr;

import iapx.qPkt.Qpkt;

public class Q_ENTER extends Qpkt {
	int nlocWords;
	
//	Qfunc.Qf2(Opcode.qENTER,visflag,0,0,nlocbyte);
	public Q_ENTER(int nlocWords) {
		this.nlocWords = nlocWords;
	}

	public String toString() {
		return "ENTER  nlocWords=" + nlocWords;
	}
}
