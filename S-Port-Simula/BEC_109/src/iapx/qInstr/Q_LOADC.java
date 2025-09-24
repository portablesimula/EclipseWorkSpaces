package iapx.qInstr;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.Value;

// reg := value
public class Q_LOADC extends Qpkt {
	Value value;
	
	public Q_LOADC(int reg, Value value) {
		this.reg = reg;
		this.value = value;
        writes(reg); minds(reg);
	}

	public String toString() {
		//   when qLOADC: ------ LOADC   reg const ----------------  Format 2
		//      		  ------ LOADC   reg fld addr -------------  Format 2b
		return "LOADC  " + Reg.edReg(reg) + " " + value;
	}
}
