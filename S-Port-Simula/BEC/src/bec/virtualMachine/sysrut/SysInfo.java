package bec.virtualMachine.sysrut;

import bec.segment.Segment;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_CALLSYS;

public abstract class SysInfo {
	
	/**
	 * Visible sysroutine("GINTIN") GINTIN;
	 *  import range(0:127) index; export integer result  end;
	 */
	public static void getIntinfo() {
		SVM_CALLSYS.ENTER("GINTIN: ", 1, 1); // exportSize, importSize
		int index = RTStack.popInt();
		System.out.println("SVM_SYSCALL.getIntinfo: "+index);
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
		SVM_CALLSYS.EXIT("GINTIN: ");
	}
	
	/**
	 *  Visible sysroutine ("SIZEIN") SIZEIN;
	 *  	import range(0:127) index; range(0:255) ano;
	 *  	export size result  end;
	 */
	public static void sizein() {
		SVM_CALLSYS.ENTER("SIZEIN: ", 1, 2); // exportSize, importSize
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
		System.out.println("SVM_SYSCALL.sizein: index=" + index + ", warea=" + warea + ", result=" +result);
		RTStack.push(IntegerValue.of(Type.T_SIZE, result), "EXPORT");
		SVM_CALLSYS.EXIT("SIZEIN: ");
	}

}
