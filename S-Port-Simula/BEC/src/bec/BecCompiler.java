package bec;

import java.util.HashMap;
import bec.descriptor.Descriptor;
import bec.segment.Segment;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class BecCompiler {
	String programHead;
//	PREV_S_Module module;
	
	public static void main(String[] argv) {
		String scodeSource = null;
//		Scode.inputTrace = 4;
//		new Pick_FEC_Source();
//		new Pick_RTS_Source();		

		

		// Parse command line arguments.
		for(int i=0;i<argv.length;i++) {
			String arg=argv[i];
			if (arg.charAt(0) == '-') { // command line option
				if (arg.equalsIgnoreCase("-help")) help();
				else if (arg.equalsIgnoreCase("-inputTrace")) Global.SCODE_INPUT_TRACE = true;
				else if (arg.equalsIgnoreCase("-listing")) Global.PRINT_SVM_CODE = true;
				else if (arg.equalsIgnoreCase("-verbose")) Global.verbose=true;
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

//		Global.scodeSource = scodeSource;
//		Scode.initScode();
		new BecCompiler(scodeSource);
	}


	/**
	 * Print synopsis of standard options
	 */
	private static void help() {
		System.out.println("");
		System.out.println("Usage: java -jar SportFEC.jar  [options]  ScodeFile ");
		System.out.println("");
		System.out.println("possible options include:");
		System.out.println("  -help        Print this synopsis of standard options");
		System.out.println("  -inputTrace  Produce input Scode trace");
		System.out.println("  -listing     Produce pretty Scode listing");
		System.out.println("  -verbose     Output messages about what the compiler is doing");
		System.out.println("");
		System.out.println("sourceFile ::= S-Code Source File");

		System.exit(0);
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
		if(Global.verbose) System.out.println("START: BecCompiler: " + scodeSource);
		Global.scodeSource = scodeSource;
		Global.DISPL = new Array<Descriptor>();
		Global.SEGMAP = new HashMap<String, Segment>();
		Global.ifDepth = 0;
		Scode.initScode();
//		DataType.initDataTypes();
		Type.init();

		Scode.inputInstr();
		if(Scode.curinstr == Scode.S_PROGRAM) {
//	  		System.out.println("Parse.MONITOR: S_PROGRAM");
	  		Global.progIdent = Scode.inString();
			Scode.inputInstr();
			if(Scode.curinstr == Scode.S_GLOBAL) {
				new InterfaceModule();
				Scode.inputInstr();
			}
			while(Scode.curinstr == Scode.S_MODULE) {
				new ModuleDefinition();
				Scode.inputInstr();
			}
			if(Scode.curinstr == Scode.S_MAIN) {
				new MainProgram();
			}
		} else Util.IERR("Illegal S-Program");
		
		if(Global.verbose) System.out.println("DONE: BecCompiler: " + scodeSource);
	}

}
