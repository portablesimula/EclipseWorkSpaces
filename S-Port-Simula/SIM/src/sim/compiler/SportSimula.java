package sim.compiler;

import java.io.File;

import sim.editor.SPortEditor;

import static sim.compiler.Global.*;

public class SportSimula {

//	private final static String RELEASE_HOME  = "C:/SPORT";
//	private final static String SportSIM_ROOT = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SIM";
//	private final static String COMPILER_BIN  = SportSIM_ROOT+"/bin";

	/**
	 * Print synopsis of standard options
	 */
	private static void help() {
//		System.out.println(Global.sPortReleaseID+" See: https://github.com/portablesimula");
		System.out.println("");
		System.out.println("Usage: java -jar simula.jar  [options]  sourceFile ");
		System.out.println("");
		System.out.println("possible options include:");
		System.out.println("  -help                      Print this synopsis of standard options");
		System.out.println("  -caseSensitive             Source file is case sensitive.");	
		System.out.println("  -compilerMode modeString   Simula Compiler mode *) see below.");	
		System.out.println("  -noexec                    Don't execute generated .jar file");
		System.out.println("  -nowarn                    Generate no warnings");
		System.out.println("  -noextension               Disable all language extensions");
		System.out.println("                             In other words, follow the Simula Standard literally");
		System.out.println("  -verbose                   Output messages about what the compiler is doing");

		System.out.println("  -FEC:select characters     First, all selectors are reset.");
		System.out.println("                             Then, for each character, the corresponding selector is set");
		System.out.println("  -sport                     Enable all S-PORT extensions");
		
		System.out.println("SportSimula.help - Exit: ");
		Thread.dumpStack();
		System.exit(0);
	}

	public static void main(String[] argv) {
		try {
			if(argv.length == 0) {
				SPortEditor editor=new SPortEditor();
				editor.start();				
			} else {
				Global.initiate();
//				System.out.println("RUN SPORT SIM Compiler.jar  argv.length="+argv.length);
//				String cmdLine="";
//				for(int i=0;i<argv.length;i++) cmdLine=cmdLine+" "+argv[i];
//		        System.out.println("SPortSimula.main: command ="+cmdLine);
		        decodeArgv(argv);
				
//				Util.IERR("NOT IMPL");
//				Option.verbose = true;
				
		        File sourceFile = new File(sourceFileName);
				String name = sourceFile.getName();
				int pos = name.indexOf('.');
				if(pos > 0) name = name.substring(0, pos);
				File sCodeFile=new File(Global.getTempFileDir("sim/tmp/"), name + ".scd");
				sCodeFile.getParentFile().mkdirs();
				sCodeFileName = sCodeFile.toString();
				if(Option.verbose) Util.println("\n\nEditorMenues.doStartRunning: CALL FEC: Output ==> sCodeFileName = "+sCodeFileName);
				
				int execCode = SimulaFEC.callSimulaFEC();
				if(Option.verbose) System.out.println("RETURN FROM FEC: ExitCode = "+execCode+"\n\n");
				
				if(execCode == 0) SimulaBEC.callBEC();
				
			}		

		} catch(Exception e) { e.printStackTrace(); }
	}

	private static void decodeArgv(String[] argv) {
		// Parse command line arguments.
		for(int i=0;i<argv.length;i++) {
			String arg=argv[i];
			if (arg.charAt(0) == '-') { // command line option
				if (arg.equalsIgnoreCase("-help")) help();
				else if (arg.equalsIgnoreCase("-verbose")) Option.verbose=true;
				else if (arg.equalsIgnoreCase("-caseSensitive")) Option.CaseSensitive=true;
				else if (arg.equalsIgnoreCase("-nopopup")) Option.nopopup=true;
				else if (arg.equalsIgnoreCase("-simdir")) 	Global.simdir = new File(argv[++i]);

				else if (arg.equalsIgnoreCase("-FEC:Listing")) Option.fecListing = true;
				else if (arg.equalsIgnoreCase("-FEC:SCodeTrace")) Option.fecSCodeTrace = true;
//				else if (arg.equalsIgnoreCase("-FEC:TraceLevel")) Option.fecTraceLevel = true;
				
				else if (arg.equalsIgnoreCase("-BEC:SCodeTrace")) Option.becSCodeTrace = true;
				else if (arg.equalsIgnoreCase("-BEC:TraceSVM_CODE")) Option.becTraceSVM_CODE = true;
				else if (arg.equalsIgnoreCase("-BEC:TraceSVM_DATA")) Option.becTraceSVM_DATA = true;

				else if (arg.equalsIgnoreCase("-execTrace")) Option.execTrace=1;
				else if (arg.equalsIgnoreCase("-callTrace")) Option.callTrace=1;
				else if (arg.equalsIgnoreCase("-dumpsAtExit")) Option.dumpsAtExit=true;

				else {
					System.out.println("ERROR: Unknown option " + arg);
					help();
				}
			} else if(sourceFileName==null) sourceFileName = arg;
			else {
				System.out.println("ERROR: multiple input files specified");
				help();
			}
		}
		if(sourceFileName==null) {
			System.out.println("ERROR: no input file specified");
			help();
		}
	}

}
