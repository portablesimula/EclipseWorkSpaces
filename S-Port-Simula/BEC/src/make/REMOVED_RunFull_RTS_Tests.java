/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package make;

import java.util.Vector;

import bec.BecCompiler;
import bec.util.Option;

public final class REMOVED_RunFull_RTS_Tests {
	private static long startTimeMs = System.currentTimeMillis();

	private static final String rtsSCodeDir = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/";
//	Global.scodeSource = "C:/Simuletta/SCode/simulaRTS/RT.scd";

	public static void main(String[] args) {
		Option.INLINE_TESTING = true;
		Option.verbose = true;
//		Scode.inputTrace = 4;
//		Scode.listing = true;
		Option.PRINT_GENERATED_SVM_CODE = true;

		// Set Compiler Options.
//		Option.EXTENSIONS=false;
//		Option.noExecution=true;

		Vector<String> names = new Vector<String>();
		names.add("RT.scd");
//		names.add("SYSR.scd");
//		names.add("KNWN.scd");
//		names.add("UTIL.scd");
//		
//		names.add("STRG.scd");
//		names.add("CENT.scd");
//		names.add("CINT.scd");
//		names.add("ARR.scd");
//		names.add("FORM.scd");
//		names.add("LIBR.scd");
//		names.add("FIL.scd");
//		names.add("SMST.scd");
//		names.add("SML.scd");
//		names.add("EDIT.scd");
//		names.add("MNTR.scd");

		for (String name : names) {
			String fileName = rtsSCodeDir + name;
//			try {
				new BecCompiler(fileName);
//			} catch (IOException e) { Util.IERR("Compiler Error: " + e); }
		}

		IO.println("\n--- END OF SportBEC TESTBATCH");
		long timeUsed = System.currentTimeMillis() - startTimeMs;
		IO.println("\nElapsed Time: Approximately " + timeUsed / 1000 + " sec.");
	}

}
