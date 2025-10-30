/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

/// Options.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/util/Option.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class Option {

	public static boolean INLINE_TESTING = false;
	public static boolean debugMode = false;
	public static boolean verbose = false;
	public static boolean execVerbose = false;
	public static boolean nopopup = false;
	public static int traceMode = 0;
	public static boolean SCODE_INPUT_TRACE = false;
	public static boolean TRACE_ALLOC_FRAME = false;
	public static boolean PRINT_GENERATED_SVM_DATA = false;
	public static boolean PRINT_GENERATED_SVM_CODE = false;
	public static boolean ATTR_INPUT_TRACE = false;
	public static boolean ATTR_OUTPUT_TRACE = false;
	public static boolean ATTR_INPUT_DUMP = false;
	public static boolean ATTR_OUTPUT_DUMP = false;
	public static boolean SEGMENT_INPUT_DUMP = false;
	public static boolean SEGMENT_OUTPUT_DUMP = false;
	public static int EXEC_TRACE = 0;
	public static int CALL_TRACE_LEVEL = 0;
	public static boolean DUMPS_AT_EXIT = false;

}
