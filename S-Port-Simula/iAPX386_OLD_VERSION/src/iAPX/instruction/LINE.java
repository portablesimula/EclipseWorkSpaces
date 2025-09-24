package iAPX.instruction;

import iAPX.ctStack.CTStack;
import iAPX.qInstr.Q_LINE;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Scode;
import svm.virtualMachine.RTRegister;
import svm.virtualMachine.SVM_LINE;

public abstract class LINE extends Instruction {
	/**
	 * 	info_setting
	 * 		: =  decl line:number
	 * 		: =  line line:number
	 * 		: =  stmt line:number
	 */
	public static void ofScode(Q_LINE.Subc subc) {
		int curline = Scode.inNumber();	
		if(subc == Q_LINE.Subc.stm) CTStack.checkStackEmpty();
//	    if SK1LIN <> 0
//	    then if curline >= SK1LIN
//	         then trc = SK1TRC; txx = trc/10; InputTrace =   trc-(10*txx);
//	              trc = txx; txx = trc/10;    TraceMode =    trc-(10*txx);
//	              trc = txx; txx = trc/10;    ModuleTrace =  trc-(10*txx);
//	              trc = txx; txx = trc/10;    listsw =       trc-(10*txx);
//	              trc = txx; txx = trc/10;    listq1 =       trc-(10*txx);
//	              trc = txx; txx = trc/10;    listq2 =       trc-(10*txx);
//	              if SK1TRC = 0 then SK1LIN = 0
//	              else SK1LIN = SK1LIN+5; SK1TRC = 0 endif;
//	         endif;
//	    endif;
		if(Option.GENERATE_Q_CODE) {
//		    Qfunc.Qf2(Opcode.qLINE,kind.ordinal(),0,Global.cANY,curline);
			new Q_LINE(subc , curline);
		} else {
			Global.PSEG.emit(new SVM_LINE(0, curline), "LINE: ");
			RTRegister.clearFreeRegs();
		}
	}

}
