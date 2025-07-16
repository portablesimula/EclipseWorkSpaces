package sim.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import static sim.compiler.Global.*;

public abstract class SimulaFEC {
	
	private static boolean fecTerminated;
	private static int exitCode;

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
	public static void main(String[] args) {
		Option.verbose = true;
		Option.fecListing = true;
//		String name = "HelloWorld";
		String name = "Sudoku";
		sCodeFileName = "src/sim/samplePrograms/scode/"+name+".scd";
		sourceFileName = "src/sim/samplePrograms/"+name+".sim";
//		invokeSimulaFEC();
		callSimulaFEC();
	}

	public static int invokeSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
//		cmds.add("-nopopup");
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		JarFileLoader loader = new JarFileLoader("C:/SPORT/SimulaFEC.jar");
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
		while(! fecTerminated) Thread.yield();
		return 0;
	}
	
	public static int callSimulaFEC() {
		String tempFileName = null;
		String home = System.getProperty("user.home");
//		File tempDir = new File(home,"Temp");
//		tempFileName = tempDir+"/sysout.tmp";
		tempFileName = home + "/Simula/Temp/sysout.tmp";
		System.out.println("SimulaFEC.callSimulaFEC: Temp file created at: " + tempFileName);
		File file = new File(tempFileName);
		if( file.exists()) file.delete();
		System.out.println("SimulaFEC.callSimulaFEC: exists=" + file.exists());
		echoSysout(tempFileName);
		
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\SimulaFEC.jar");
//		cmds.add("-SPORT:noConsole");
		cmds.add("-nopopup");
		cmds.add("-sysout"); cmds.add(tempFileName);
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		// -SPORT:traceScode
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		Runnable task = new Runnable() {
			public void run() {
				try {
					exitCode = Util.exec(cmds);
					fecTerminated = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
	
	private static void echoSysout(String fileName) {
		Runnable task = new Runnable() {
			public void run() {
				File file = new File(fileName);
				while(! file.exists()) Thread.yield();
				try (FileInputStream inpt = new FileInputStream(fileName)) {
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
