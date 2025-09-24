package iapx.qInstr;

import static iapx.util.Reg.qF;
import static iapx.util.Reg.qSP;

import iapx.qPkt.Qpkt;

/**
 * Operation POPK n
 * 
 * Runtime Stack
 *    ..., value1, value2, ... , value'n →
 *    ...
 *
 * The 'n' values on the top of the Runtime Stack is popped off and forgotten.
 */
public class Q_POPK extends Qpkt {
	int count;
	
	public Q_POPK(int count) {
		this.count = count;
        writes(qSP, qF); reads(qSP);
	}

	public String toString() {
		String s = "POPK   " + count;
		return s;
	}
}
