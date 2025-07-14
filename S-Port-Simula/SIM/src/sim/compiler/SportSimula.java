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

		System.out.println("  -select characters         First, all selectors are reset.");
		System.out.println("                             Then, for each character, the corresponding selector is set");
		System.out.println("  -sport                     Enable all S-PORT extensions");
		
		System.out.println("SportSimula.help - Exit: ");
		Thread.dumpStack();
		System.exit(0);
	}

	public static void main(String[] argv) {
		try {
			System.out.println("RUN SPORT SIM Compiler.jar");
			

			// Parse command line arguments.
			for(int i=0;i<argv.length;i++) {
				String arg=argv[i];
				if (arg.charAt(0) == '-') { // command line option
					if (arg.equalsIgnoreCase("-help")) help();
//					else if (arg.equalsIgnoreCase("-noexec")) Option.noExecution=true;
//					else if (arg.equalsIgnoreCase("-nowarn")) Option.WARNINGS=false;
//					else if (arg.equalsIgnoreCase("-noextension")) Option.EXTENSIONS=false;
					else if (arg.equalsIgnoreCase("verbose")) Option.verbose=true;

					else if (arg.equalsIgnoreCase("-FEC:select")) selectors = argv[++i];
					else if (arg.equalsIgnoreCase("-FEC:Listing")) Option.fecListing = true;
					
					else if (arg.equalsIgnoreCase("-BEC:TraceSVM_CODE")) Option.becTraceSVM_CODE = true;
					else if (arg.equalsIgnoreCase("-BEC:TraceSVM_DATA")) Option.becTraceSVM_DATA = true;
//					else if (arg.equalsIgnoreCase("-output")) setOutputDir(argv[++i]);
//					else if (arg.equalsIgnoreCase("-extLib")) Global.extLib=new File(argv[++i]);
//					else if (arg.equalsIgnoreCase("-source")) Option.internal.SOURCE_FILE=argv[++i];
//					else if (arg.equalsIgnoreCase("-runtimeUserDir")) Option.internal.RUNTIME_USER_DIR=argv[++i];
//					else if (arg.equalsIgnoreCase("-noConsole")) noConsole = true;

//					else error("Unknown option "+arg);
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
//				help();
				String userDir="C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SIM";
//				Global.simulaRtsLib=new File(userDir,"bin"); // To use Eclipse Project's simula.runtime  Download
				Option.INIT();
				Global.sampleSourceDir=new File(userDir+"/src/sim/samplePrograms");
				Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
					public void uncaughtException(Thread thread, Throwable e) {
						System.out.print("SimulaEditor.UncaughtExceptionHandler: GOT Exception: " + e);
						e.printStackTrace();
				}});
				SPortEditor editor=new SPortEditor();
		    	editor.setVisible(true);
			}
						
//			INLINE_TEST();
			
			System.out.println("sourceFileName="+sourceFileName);
			File file = new File(sourceFileName);
//			String srcDir = file.getPath();
			String srcDir = file.getParent();
			String name = file.getName().replace(".sim", ".scd");
			System.out.println("srcDir="+srcDir);
			sCodeFileName = srcDir + "\\scode\\" + name;
			System.out.println("sCodeFileName="+sCodeFileName);

			INLINE_TEST();

		} catch(Exception e) { e.printStackTrace(); }
	}

	
	private static void INLINE_TEST() {
//		String name ="adHoc00";
//		String name ="adHoc01";
//			String name ="adHoc02";
//		String name ="adHoc03";
//		String name ="simtst00";
//		String name ="simtst01";
//		String name ="simtst02";
//		String name ="simtst03";
//		String name ="simtst04";
//		String name ="simtst05";
		
//		sourceFileName = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\"+name+".sim";
//		sCodeFileName  = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\scode\\"+name+".scd";
		
		Option.verbose = true;
//		fecListing = true;
//		fecSCodeTrace = true;
//		fecTraceLevel = 4;

//		becListing = true;
//		becSCodeTrace = true;
//		execTrace = 1;
//		callTrace = 1;//2;
//		dumpsAtExit = true;
		
		int execCode = SimulaFEC.callSimulaFEC();
		if(Option.verbose) System.out.println("RETURN FROM FEC: ExitCode = "+execCode+"\n\n");
		
		if(execCode == 0) SimulaBEC.callBEC();
	}
	

}
