/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.env;

import svm.RTStack;
import svm.RTUtil;
import svm.instruction.SVM_CALL_SYS;
import svm.value.ObjectAddress;

/// Basic and utility environment routines
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/svm/env/SysInfo.java"><b>Source File</b></a>.
/// 
/// @author Simula Standard
/// @author S-Port: The Environment Interface
/// @author Øystein Myhre Andersen
public class SysRut {

	/// Visible sysroutine("DMPSEG") DMPSEG;
	/// import infix(string) segnam; integer start,lng  end;

	/// Visible sysroutine("DMPENT") DMPENT;
	/// import ref() rtAddr;  end;
	public static void dmpent() {
		SVM_CALL_SYS.ENTER("DMPENT: ", 0, 1); // exportSize, importSize
		ObjectAddress oaddr = RTStack.popOADDR();
		IO.println("SVM_SYSCALL.dmpent: "+oaddr);
		RTUtil.dumpEntity(oaddr);
		SVM_CALL_SYS.EXIT("DMPENT: ");
	}

	/// Visible sysroutine("DMPOOL") DMPOOL; -- Dump POOL_n
	/// import integer n;  end;

}
