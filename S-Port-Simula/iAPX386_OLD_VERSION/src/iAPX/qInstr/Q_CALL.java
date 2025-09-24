package iAPX.qInstr;

import iAPX.dataAddress.MemAddr;
import iAPX.enums.PKind;
import iAPX.qPkt.Qpkt;

public class Q_CALL extends Qpkt {
//	enum Subc {	qEXIT, qXNX, qOS2, qC, qKNL }
//	Subc subc;
	
	PKind pKind;
	int nparWords;
	MemAddr entr;
	
//	Qfunc.Qf5(Opcode.qCALL,pkind.ordinal(),0,spec.nparWords,entr);
//	public Q_CALL(PKind pKind, int reg, int nparWords, MemAddr entr) {
//		this.reg = reg;
	public Q_CALL(PKind pKind, int nparWords, MemAddr entr) {
		this.nparWords = nparWords;
		this.entr = entr;
	}

	public String toString() {
		return "CALL   " + pKind + " " + nparWords + " " + entr; 
//	  outstring("CALL   "); outword(qi qua Qfrm5.subc);
//		        outchar(','); outword(qi qua Qfrm5.reg);
//		        outchar(','); outword(qi qua Qfrm5.aux);
//		        outchar(',');
//		       if qi qua Qfrm5.addr.kind=reladr -- Special Case: CALL [reg]
//		       then outopr(qi qua Qfrm5.addr);
//		       else
//		             outadr(qi qua Qfrm5.addr);
//		       endif;
	}
}
