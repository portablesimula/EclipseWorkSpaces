package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;

public class Q_LINE extends Qpkt {
	public enum Subc { src, dcl, stm }
	Subc subc;
	int line;
	
	public Q_LINE(Subc subc, int line) {
		this.subc = subc;
		this.line = line;
	}

	public String toString() {
		String s = "LINE   " + line + "   ";
		switch(subc) {
			case src: s += ";     SOURCE LINE "; break;
			case dcl: s += ";     DECLARATION LINE "; break;
			case stm: s += ";     STATEMENT LINE "; break;
		}
		return s;
	}
}
