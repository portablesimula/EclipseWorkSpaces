package iAPX.util;

public class Option {
	public static boolean GENERATE_DEBUG_CODE = false;
	public static boolean GENERATE_Q_CODE = false;

	public static boolean verbose;
	public static boolean SCODE_INPUT_TRACE;
	public static boolean SCODE_INPUT_DUMP;
	public static boolean PRINT_GENERATED_SVM_DATA = false;
	public static boolean PRINT_GENERATED_SVM_CODE = false;
	public static boolean DISPL_TRACE = false;
	public static boolean ATTR_INPUT_TRACE = false;
	public static boolean ATTR_OUTPUT_TRACE = false;
	public static boolean ATTR_INPUT_DUMP = false;
	public static boolean ATTR_OUTPUT_DUMP = false;
	public static boolean SEGMENT_INPUT_DUMP = false;
	public static boolean SEGMENT_OUTPUT_DUMP = false;
	public static int EXEC_TRACE = 0;
	public static int CALL_TRACE_LEVEL = 0;
	public static boolean DUMPS_AT_EXIT = false;

	public static int InputTrace;     // Input trace switch
	public static int listq1;         // Output Q-code 1 listing switch
	public static int listq2;         // Output Q-code 2 listing switch
	public static int listsw;         // Output I-code listing switch
	public static int traceMode;      // Processing trace switch
	public static int ModuleTrace;    // Module I/O trace switch
	public static int MASSLV;        // Massage depth level
	public static int MASSDP;        // Massage max recursion depth
	public static int BECDEB;      // Debugging level   (Debugging purposes)
	public static int TLIST;       // Option D - Major Event Trace Level - Skal slås sammen med verbose seinere

}
