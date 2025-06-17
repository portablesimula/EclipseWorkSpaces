package bec.virtualMachine.sysrut;

import bec.segment.Segment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class SysInfo {

	/// Visible sysroutine("DMPSEG") DMPSEG;
	/// import infix(string) segnam; integer start,lng  end;

	/// Visible sysroutine("DMPENT") DMPENT;
	/// import ref() rtAddr;  end;
	public static void dmpent() {
		SVM_CALL_SYS.ENTER("DMPENT: ", 0, 1); // exportSize, importSize
//		int index = RTStack.popInt();
		ObjectAddress oaddr = RTStack.popOADDR();
		System.out.println("SVM_SYSCALL.dmpent: "+oaddr);
		RTUtil.dumpEntity(oaddr);
//		Util.IERR("");
		SVM_CALL_SYS.EXIT("DMPENT: ");
	}

	/// Visible sysroutine("DMPOOL") DMPOOL; -- Dump POOL_n
	/// import integer n;  end;

	/**
	 * Visible sysroutine("GINTIN") GINTIN;
	 *  import range(0:127) index; export integer result  end;
	 */
	public static void getIntinfo() {
		SVM_CALL_SYS.ENTER("GINTIN: ", 1, 1); // exportSize, importSize
		int index = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.getIntinfo: "+index);
		int result=0;
		switch(index) {
			case 19: result = 0; break; // 19 Should the symbolic debugger SIMOB be entered prior to the execution of the
			                            //    program, and at program termination? An answer greater than zero will give this effect.
			case 24: result = 1; break; // 24 How many work areas may be requested (see chapter 5)?
			case 33: result = 0; break; // 33 Result: 0 - no, this is not an interactive execution
			                            //            1 - yes, this is an interactive execution
			case 99: Segment.lookup("DSEG_RT").dump("",0,100); break; // AD'HOC DUMP UTILITY
			default: Util.IERR("");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, result), "EXPORT");
		SVM_CALL_SYS.EXIT("GINTIN: ");
	}
	
	/**
	 *  Visible sysroutine ("SIZEIN") SIZEIN;
	 *  	import range(0:127) index; range(0:255) ano;
	 *  	export size result  end;
	 */
	public static void sizein() {
		SVM_CALL_SYS.ENTER("SIZEIN: ", 1, 2); // exportSize, importSize
		int warea = RTStack.popInt();
		int index = RTStack.popInt();
		int result = 0;
		switch(index) {
			case 1: // The minimum size of this work area.
				result = 150000; break;
//				result = 1500; break;
			case 2: // The extension/contraction step size.
//				Util.IERR("The extension/contraction step size.");
				break;
			case 3: // The minimum gap left in this work area after a garbage collection if the area is the current work area.
//				Util.IERR("The minimum gap left in this work area after a garbage collection if the area is the current work area.");
				break;
			default: Util.IERR("");
		}
		if(Global.verbose) System.out.println("SVM_SYSCALL.sizein: index=" + index + ", warea=" + warea + ", result=" +result);
		RTStack.push(IntegerValue.of(Type.T_SIZE, result), "EXPORT");
		SVM_CALL_SYS.EXIT("SIZEIN: ");
	}

	/// Visible sysroutine("GVIINF")  GVIINF;
	/// import range(0:127) index; integer inform  end;
	public static void gviinf() {
		SVM_CALL_SYS.ENTER("GVIINF: ", 0, 2); // exportSize, importSize
		int inform = RTStack.popInt();
		int index = RTStack.popInt();
		System.out.println("SVM_SYSCALL.gviinf: index=" + index + ", inform=" + inform);
		switch(index) {
			case 6: // Garbage collection information. Info=0 signals the start of a garbage collection,
				    // Info=1 signals termination of g.c. (see 5.2).
				break;
			default: Util.IERR(""+index);
		}
		SVM_CALL_SYS.EXIT("GVIINF: ");
	}
	
}
