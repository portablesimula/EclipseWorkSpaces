package iapx.qInstr;

import static iapx.util.Reg.qSP;

import iapx.qPkt.Qpkt;
import iapx.util.Reg;

//POP RT-Stack'TOS --> Register 
//The value on the top of the operand stack is popped off and stored in reg.
public class Q_POPR extends Qpkt {

	private static final boolean DEBUG = false;

	public Q_POPR(int reg) {
		this.reg = reg;
		reads(qSP); writes(qSP, this.reg); minds(this.reg);
	}

	
//	@Override
//	public void execute() {
//		if(DEBUG) {
//			IO.println("SVM_STORE2REG: "+RTRegister.edReg(reg));
//			RTStack.dumpRTStack("SVM_STORE2REG: ");
//		}
//		Value value = RTStack.pop();
////		if(value != null) IO.println("SVM_STORE2REG: "+RTRegister.edReg(reg)+" value="+value.getClass().getSimpleName()+"  "+value);
//		RTRegister.putValue(reg, value);
//		if(DEBUG) {
//			IO.println("SVM_STORE2REG: "+RTRegister.toLine());
//		}
//		
//		Global.PSC.addOfst(1);
//	}

	public String toString() {
	//   when qPUSHR: ------ PUSHR   reg ----------------------  Format 1
//      --- Q-Code:
		String s = "POPR   " + Reg.edReg(reg);
		return s;
	}
}
