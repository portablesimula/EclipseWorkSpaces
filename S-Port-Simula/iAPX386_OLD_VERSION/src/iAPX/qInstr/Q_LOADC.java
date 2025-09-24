package iAPX.qInstr;

import iAPX.qPkt.Qpkt;
import iAPX.util.Reg;
import svm.value.Value;

public class Q_LOADC extends Qpkt {
	Value value;
	
	public Q_LOADC(int reg, Value value) {
		this.reg = reg;
		this.value = value;
	}

	public String toString() {
		//   when qLOADC: ------ LOADC   reg const ----------------  Format 2
		//      		  ------ LOADC   reg fld addr -------------  Format 2b
		return "LOADC  " + Reg.edreg(reg) + " " + value;
	}
}
