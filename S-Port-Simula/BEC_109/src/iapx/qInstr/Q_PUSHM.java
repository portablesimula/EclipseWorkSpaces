package iapx.qInstr;

import static iapx.util.Reg.qM;
import static iapx.util.Reg.qSP;

import iapx.qPkt.Qpkt;
import iapx.value.MemAddr;

// Push 'size' byte from the address 'adr' onto the runtime stack


/**
 * Operation PUSHM rtAddr xReg size
 * 
 * Runtime Stack
 *    ... →
 *    ..., value1, value2, ... , value'size
 *
 * Push 'size' values from a data segment starting by pushing the value at 'rtAddr+xreg'.
 * After the operation the Runtime Stack looks like:
 * 
 * 		value1  =  rtAddre[0] 
 * 		value2  =  rtAddre[1]
 * 		...
 * 		value'size  =  rtAddre[size-1]
 */
public class Q_PUSHM extends Qpkt {
	int size;
	MemAddr opr;
	
	public Q_PUSHM(int size, MemAddr opr) {
		this.size = size;
		this.opr = opr;
        reads(qSP, qM); writes(qSP);
	}

	public String toString() {
		return "PUSHM  " + size + " " + opr;
	}
}
