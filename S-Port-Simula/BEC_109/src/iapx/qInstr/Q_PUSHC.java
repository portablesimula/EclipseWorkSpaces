package iapx.qInstr;

import static iapx.util.Reg.qSP;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;
import iapx.value.Value;

//The value is pushed onto the operand stack.
// See: SVM_PUSHC
public class Q_PUSHC extends Qpkt {
	Value value;
	
	public Q_PUSHC(int reg, Value value) {
		this.reg = reg;
		this.value = value;
        reads(qSP); writes(qSP);
	}

	public String toString() {
		//   when qPUSHC: ------ PUSHC   reg const ----------------  Format 2
		//                ------ PUSHC   reg fld addr -------------  Format 2b
		return "PUSHC  " + Reg.edReg(reg) + " " + value;
	}
}
