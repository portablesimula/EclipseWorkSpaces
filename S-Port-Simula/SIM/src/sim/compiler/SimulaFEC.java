package sim.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

import static sim.compiler.Global.*;

public abstract class SimulaFEC {
	
	private static String tempFileName;
	private static boolean fecTerminated;
	private static int exitCode;

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
	public static void main1(String[] args) {
		Option.verbose = true;
		Option.fecListing = true;
//		String name = "HelloWorld";
		String name = "Sudoku";
		sCodeFileName = "src/sim/samplePrograms/scode/"+name+".scd";
		sourceFileName = "src/sim/samplePrograms/"+name+".sim";
//		invokeSimulaFEC();
		callSimulaFEC();
	}
	public static void main(String[] args) {
		int code = getFEC_exitcode();
	}

	/// Called from EditorMenues 'run'
	public static int invokeSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("-nopopup");
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		JarFileLoader loader = JarFileLoader.of("C:/SPORT/SimulaFEC.jar");
		String[] args = cmds.toArray(new String[0]);
		
//		loader.invokeMain("simprog.FEC", args); // Invoke simprog.FEC.main(args)
		
		Runnable task = new Runnable() {
			public void run() {
				loader.invokeMain("simprog.FEC", args); // Invoke simprog.FEC.main(args)
				fecTerminated = true;
			}
		};
		fecTerminated = false;
		new Thread(task).start();
//		while(! fecTerminated) Thread.yield();
		while(! fecTerminated) {
			try {
				System.out.println("SimulaFEC.invokeSimulaFEC: SLEEP");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int exitCode = getFEC_exitcode();
		System.out.println("SimulaFEC.invokeSimulaFEC: exitCode="+exitCode);
		return(exitCode);
	}
	
	/// Called from EditorMenues 'run'
	public static int callSimulaFEC() {
//		startEcho_FEC_Sysout();
		
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\SimulaFEC.jar");
		cmds.add("-nopopup");
//		cmds.add("-sysout"); cmds.add(tempFileName);
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		Runnable task = new Runnable() {
			public void run() {
				try {
//					Util.exec(cmds);
//					exitCode = getFEC_exitcode();

					exitCode = Util.exec(cmds);
//					exitCode = Util.OLD_exec(cmds);
					fecTerminated = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		fecTerminated = false;
		new Thread(task).start();
		while(! fecTerminated) Thread.yield();
		System.out.println("SimulaFEC.callSimulaFEC: exitCode=" + exitCode);
		return exitCode;
	}
	
	private static int getFEC_exitcode() {
		JarFileLoader loader = JarFileLoader.of("C:/SPORT/SimulaFEC.jar");
		try {
			Class<?> RTS_ENVIRONMENT = loader.findMyLoadedClass("simula.runtime.RTS_ENVIRONMENT");
			Field field_exitCode = RTS_ENVIRONMENT.getDeclaredField("exitCode");
			System.out.println("Util.getConsoleReader: field_exitCode="+field_exitCode);
			Integer object = (Integer) field_exitCode.get(RTS_ENVIRONMENT);
			int res = object.intValue();
			System.out.println("Util.getConsoleReader: field_exitCode'value="+res);
			return res;
		} catch(Exception e) { e.printStackTrace(); return -1; }
	}
	
	private static void startEcho_FEC_Sysout() {
		String home = System.getProperty("user.home");
		tempFileName = home + "/Simula/Temp/sysout.tmp";
		System.out.println("SimulaFEC.callSimulaFEC: Temp file created at: " + tempFileName);
		File file = new File(tempFileName);
		if( file.exists()) file.delete();
		System.out.println("SimulaFEC.callSimulaFEC: exists=" + file.exists());
		
		Runnable task = new Runnable() {
			public void run() {
				File file = new File(tempFileName);
				while(! file.exists()) Thread.yield();
				try (FileInputStream inpt = new FileInputStream(tempFileName)) {
					int c = inpt.read();
					while(! fecTerminated) {
						if(c > 0) {
							Global.consolePanel.write(""+(char)c);
						}
						c = inpt.read();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(task).start();

	}

}
