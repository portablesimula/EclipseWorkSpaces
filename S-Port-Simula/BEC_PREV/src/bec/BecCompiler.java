package bec;

import java.util.HashMap;
import bec.descriptor.Descriptor;
import bec.segment.Segment;
import bec.util.Array;
import bec.util.TextPad;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class BecCompiler {
	String programHead;
	static String scodeSource;
	
	public static void main(String[] argv) {
//		Global.console = new Terminal("Runtime Console");  // TODO: MÅ RETTES
		
//		Terminal terminal = new Terminal("Runtime Console");
//		System.setIn(terminal.getInputStream());
//		System.setOut(terminal.getOutputStream());

		// Parse command line arguments.
		for(int i=0;i<argv.length;i++) {
			String arg=argv[i];
			if (arg.charAt(0) == '-') { // command line option
				if (arg.equalsIgnoreCase("-help")) help();
				else if (arg.equalsIgnoreCase("-inputTrace")) BecGlobal.SCODE_INPUT_TRACE = true;
				else if (arg.equalsIgnoreCase("-traceSVM_CODE")) BecGlobal.PRINT_GENERATED_SVM_CODE = true;
				else if (arg.equalsIgnoreCase("-traceSVM_DATA")) BecGlobal.PRINT_GENERATED_SVM_DATA = true;
				else if (arg.equalsIgnoreCase("-verbose")) BecGlobal.verbose = true;
				else if (arg.equalsIgnoreCase("-execVerbose")) BecGlobal.execVerbose = true;
				else if (arg.equalsIgnoreCase("-execTrace")) BecGlobal.EXEC_TRACE = 1;
				else if (arg.equalsIgnoreCase("-callTrace")) BecGlobal.CALL_TRACE_LEVEL = 2;
				else if (arg.equalsIgnoreCase("-dumpsAtExit")) BecGlobal.DUMPS_AT_EXIT = true;
				else {
					Util.ERROR("Unknown option " + arg);
					help();
				}
			} else if(scodeSource==null) scodeSource = arg;
			else Util.ERROR("multiple input files specified");
		}
		
		if(scodeSource==null) {
			Util.ERROR("no input file specified");
			help();
		}
		
		new BecCompiler(scodeSource);
	}


	/**
	 * Print synopsis of standard options
	 */
	private static void help() {
		System.out.println("");
		System.out.println("Usage: java -jar SportBEC.jar  [options]  ScodeFile ");
		System.out.println("");
		System.out.println("possible options include:");
		System.out.println("  -becVerbose  Output messages about what the compiler is doing");
		System.out.println("  -execVerbose Output messages about what the executor is doing");
		System.out.println("  -help        Print this synopsis of standard options");
		System.out.println("  -inputTrace  Produce input Scode trace");
		System.out.println("  -listing     Produce pretty Scode listing");
		System.out.println("  -execTrace   Produce instruction trace during execution");
		System.out.println("  -callTrace   Produce routine call trace during execution");
		System.out.println("  -dumpsAtExit Produce certain dumps at en of execution");
		System.out.println("");
		System.out.println("sourceFile ::= S-Code Source File");

		System.out.println("BecCompiler.help - Exit: ");
		Thread.dumpStack();
//		System.exit(0);
	}


	/**
	 * S-program ::= program program_head:string
	 * 						 program_body endprogram
	 * 
	 * 	program_body 
	 * 		::= interface_module
	 * 		::= macro_definition_module
	 * 		::= <module_definition>*
	 * 		::= main <local_quantity>* <program_element>*
	 */
	public BecCompiler(String scodeSource) {
		if(BecGlobal.verbose) Util.println("BEC: Start BecCompiler with " + scodeSource);
		BecGlobal.scodeSource = scodeSource;
		BecGlobal.DISPL = new Array<Descriptor>();
		BecGlobal.SEGMAP = new HashMap<String, Segment>();
		BecGlobal.ifDepth = 0;
		Scode.initScode();
		Type.init();

		Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable e) {
				Util.println("BecCompiler.UncaughtExceptionHandler: BEC GOT Exception: " + e.getClass().getSimpleName());
//				e.printStackTrace();
				Thread.dumpStack();
				System.exit(-1);
		}});

		Scode.inputInstr();
		if(Scode.curinstr == Scode.S_PROGRAM) {
	  		BecGlobal.progIdent = Scode.inString();
			Scode.inputInstr();
			if(Scode.curinstr == Scode.S_GLOBAL) {
				new InterfaceModule();
				Scode.inputInstr();
			}
			while(Scode.curinstr == Scode.S_MODULE) {
				new ModuleDefinition();
				Scode.inputInstr();
				Util.println("*** WARNING: SCode file written to " + scodeSource);
			}
			if(Scode.curinstr == Scode.S_MAIN) {
				new MainProgram();
			}
		} else Util.IERR("Illegal S-Program");
		
		if(BecGlobal.verbose) Util.println("DONE: BecCompiler: " + scodeSource);
	}

}
