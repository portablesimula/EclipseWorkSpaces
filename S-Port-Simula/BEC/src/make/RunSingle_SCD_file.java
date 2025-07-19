/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package make;

import bec.BecCompiler;
import bec.util.Global;

public final class RunSingle_SCD_file {
	
	private static final String sCodeFileName = "C:/Users/omyhr/AppData/Local/Temp/sim/tmp/HexDump.scd";

	public static void main(String[] args) {
		Global.INLINE_TESTING = true;
		
////		SVM_CALLSYS.RUNTIME_VERBOSE = true;
////		Global.verbose = true;
//		Global.SCODE_INPUT_TRACE = true;
//		Global.PRINT_GENERATED_SVM_CODE = true;
////		Global.PRINT_GENERATED_SVM_DATA = true;
//		Global.EXEC_TRACE = 1;
////		Global.CALL_TRACE_LEVEL = 2;
//		Global.DUMPS_AT_EXIT = true;
		
		// Set Compiler Options.
//		Option.EXTENSIONS=false;
//		Option.noExecution=true;

//		try {
			new BecCompiler(sCodeFileName);
//			System.out.println("RETURN FROM: BecCompiler");
//		} catch (Exception e) { Util.IERR("Compiler Error: " + e); }
	}

}
