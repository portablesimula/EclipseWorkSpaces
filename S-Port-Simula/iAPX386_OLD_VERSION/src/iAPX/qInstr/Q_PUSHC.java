package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;
import svm.value.Value;

public class Q_PUSHC extends Qpkt {
	Value value;
	
	public Q_PUSHC(int reg, Value value) {
		this.reg = reg;
		this.value = value;
	}

	public String toString() {
		//   when qPUSHC: ------ PUSHC   reg const ----------------  Format 2
		//                ------ PUSHC   reg fld addr -------------  Format 2b
		return "PUSHC  " + Reg.edreg(reg) + " " + value;
	}
}
